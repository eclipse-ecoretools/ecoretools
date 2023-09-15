/*******************************************************************************
 * Copyright (c) 2014 Obeo
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.service;

import java.util.Collection;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.sirius.business.api.session.ModelChangeTrigger;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;

import com.google.common.collect.Lists;

/**
 * a {@link ModelChangeTrigger} which update GenFeature children/notify/create
 * child based on the EReference.containment change. context.
 * 
 * @author Cedric Brun <cedric.brun@obeo.fr>
 * 
 */
public class GenModelUpdateGenFeatureContainment implements ModelChangeTrigger {

	private Session session;

	public GenModelUpdateGenFeatureContainment(Session set) {
		this.session = set;
	}

	public static final NotificationFilter IS_EREFENCE_CONTAINMENT = new NotificationFilter.Custom() {

		public boolean matches(Notification notification) {
			return (notification.getFeature() == EcorePackage.eINSTANCE
					.getEReference_Containment());
		}
	};

	//
	public static final NotificationFilter SHOULD_UPDATE = GenModelAutoReload.IS_TOUCH
			.negated().and(
					GenModelAutoReload.IS_ECORE.and(IS_EREFENCE_CONTAINMENT));

	public Option<Command> localChangesAboutToCommit(
			Collection<Notification> notifications) {

		final Collection<GenFeature> toBeUpdated = Lists.newArrayList();
		for (Notification notif : notifications) {
			if (SHOULD_UPDATE.matches(notif)) {
				/*
				 * this is an EReference now being containment or no more.
				 */
				EReference updatedRef = (EReference) notif.getNotifier();
				for (Setting xRef : session.getSemanticCrossReferencer()
						.getInverseReferences(updatedRef)) {

					if (xRef.getEObject() instanceof GenFeature) {
						toBeUpdated.add((GenFeature) xRef.getEObject());
					}
				}
			}
		}
		if (toBeUpdated.size() > 0) {
			Command result = new RecordingCommand(
					session.getTransactionalEditingDomain()) {

				@Override
				protected void doExecute() {
					for (GenFeature genFeature : toBeUpdated) {
						if (genFeature.getEcoreFeature() instanceof EReference) {

							if (((EReference) genFeature.getEcoreFeature())
									.isContainment()) {
								genFeature.setChildren(true);
								genFeature.setNotify(true);
								genFeature.setCreateChild(true);
							} else {
								genFeature.setChildren(false);
								genFeature.setNotify(false);
								genFeature.setCreateChild(false);
							}

						}
					}

				}
			};
			return Options.newSome(result);
		}
		return Options.newNone();
	}

	public int priority() {
		return GenModelAutoReload.PRIORITY + 1;
	}

}
