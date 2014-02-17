/*******************************************************************************
 * Copyright (c) 2014 Obeo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.sirius.business.api.session.ModelChangeTrigger;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
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

	public static final Predicate<Notification> IS_EREFENCE_CONTAINMENT = new Predicate<Notification>() {

		public boolean apply(Notification input) {
			return (input.getFeature() == EcorePackage.eINSTANCE
					.getEReference_Containment());

		}
	};

	public static final Predicate<Notification> SHOULD_UPDATE = Predicates.and(
			Predicates.not(GenModelAutoReload.IS_TOUCH),
			GenModelAutoReload.IS_ECORE, IS_EREFENCE_CONTAINMENT);

	public Option<Command> localChangesAboutToCommit(
			Collection<Notification> notifications) {

		final Collection<GenFeature> toBeUpdated = Lists.newArrayList();
		for (Notification notif : Iterables
				.filter(notifications, SHOULD_UPDATE)) {
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
