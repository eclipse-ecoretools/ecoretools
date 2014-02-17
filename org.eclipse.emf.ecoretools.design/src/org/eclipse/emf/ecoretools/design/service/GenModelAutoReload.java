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

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.session.ModelChangeTrigger;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

/**
 * a {@link ModelChangeTrigger} which reconcile any gemodel in the editing
 * context.
 * 
 * @author Cedric Brun <cedric.brun@obeo.fr>
 * 
 */
public class GenModelAutoReload implements ModelChangeTrigger {

	private TransactionalEditingDomain domain;

	public GenModelAutoReload(TransactionalEditingDomain set) {
		this.domain = set;
	}

	public static final Predicate<Notification> IS_TOUCH = new Predicate<Notification>() {

		public boolean apply(Notification input) {
			return input.isTouch();
		}
	};

	public static final Predicate<Notification> IS_ECORE = new Predicate<Notification>() {

		public boolean apply(Notification input) {
			return (input.getNotifier() instanceof EObject)
					&& ((EObject) input.getNotifier()).eClass().getEPackage() == EcorePackage.eINSTANCE;
		}
	};

	public static final Predicate<Notification> IS_ATTACHMENT = new Predicate<Notification>() {

		public boolean apply(Notification input) {
			return (input.getFeature() instanceof EReference && ((EReference) input
					.getFeature()).isContainment() == true);

		}
	};

	public static final Predicate<Notification> IS_EREFENCE_CONTAINMENT = new Predicate<Notification>() {

		public boolean apply(Notification input) {
			return (input.getFeature() == EcorePackage.eINSTANCE
					.getEReference_Containment());

		}
	};

	public static final Predicate<Notification> SHOULD_RELOAD = Predicates.and(
			Predicates.not(IS_TOUCH), IS_ECORE, IS_ATTACHMENT);

	public static final int PRIORITY = 0;

	public Option<Command> localChangesAboutToCommit(
			Collection<Notification> notifications) {
		final Collection<GenModel> genModels = Lists.newArrayList();
		for (Resource res : domain.getResourceSet().getResources()) {
			for (EObject root : res.getContents()) {
				if (root instanceof GenModel) {
					genModels.add((GenModel) root);
				}
			}
		}
		if (genModels.size() > 0) {
			Command result = new RecordingCommand(domain) {

				@Override
				protected void doExecute() {
					for (GenModel genmodel : genModels) {
						genmodel.reconcile();
					}

				}
			};
			return Options.newSome(result);
		}
		return Options.newNone();
	}

	public int priority() {
		return PRIORITY;
	}

}
