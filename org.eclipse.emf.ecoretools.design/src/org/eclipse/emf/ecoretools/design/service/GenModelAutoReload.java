/*******************************************************************************
 * Copyright (c) 2014, 2023 Obeo
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenBase;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenOperation;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenParameter;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.sirius.business.api.session.ModelChangeTrigger;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;

/**
 * a {@link ModelChangeTrigger} which reconcile any gemodel in the editing
 * context.
 * 
 * @author Cedric Brun <cedric.brun@obeo.fr>
 * 
 */
public class GenModelAutoReload implements ModelChangeTrigger {

	private Session session;

	public GenModelAutoReload(Session newSession) {
		this.session = newSession;
	}

	public static final NotificationFilter IS_TOUCH = new NotificationFilter.Custom() {

		public boolean matches(Notification input) {
			return input.isTouch();
		}
	};

	public static final NotificationFilter IS_ECORE = new NotificationFilter.Custom() {

		public boolean matches(Notification input) {
			return (input.getNotifier() instanceof EObject)
					&& ((EObject) input.getNotifier()).eClass().getEPackage() == EcorePackage.eINSTANCE;
		}
	};

	public static final NotificationFilter IS_ATTACHMENT = new NotificationFilter.Custom() {

		public boolean matches(Notification input) {
			return (input.getFeature() instanceof EReference
					&& ((EReference) input.getFeature()).isContainment() == true);

		}
	};

	public static final NotificationFilter IS_EREFENCE_CONTAINMENT = new NotificationFilter.Custom() {

		public boolean matches(Notification input) {
			return (input.getFeature() == EcorePackage.eINSTANCE.getEReference_Containment());

		}
	};

	public static final NotificationFilter SHOULD_RELOAD = IS_TOUCH.negated().and(IS_ECORE.and(IS_ATTACHMENT));

	public static final int PRIORITY = -2;

	public Option<Command> localChangesAboutToCommit(Collection<Notification> notifications) {
		final Collection<GenModel> genModels = new ArrayList<>();
		for (Resource res : session.getTransactionalEditingDomain().getResourceSet().getResources()) {
			for (EObject root : res.getContents()) {
				if (root instanceof GenModel) {
					genModels.add((GenModel) root);
				}
			}
		}
		if (genModels.size() > 0) {
			Command result = new ProcessGenModels(session, genModels);
			return Options.newSome(result);
		}
		return Options.newNone();
	}

	public int priority() {
		return PRIORITY;
	}

}

class ProcessGenModels extends RecordingCommand {

	private Session session;

	private Collection<GenModel> genmodels;

	public ProcessGenModels(Session session, Collection<GenModel> genmodels) {
		super(session.getTransactionalEditingDomain());
		this.session = session;
		this.genmodels = genmodels;
	}

	@Override
	protected void doExecute() {

		/*
		 * The genXXX.reconcile() methods are relying on the fact that the
		 * ecoreXXX reference is an unresolvable proxy to cleanup the
		 * corresponding element. This is not the case when the model is being
		 * edited "in live" as the Java reference still holds.
		 */
		Set<GenBase> toDelete = new LinkedHashSet<>();
		for (GenModel genmodel : genmodels) {

			for (GenPackage genpackage : genmodel.getGenPackages()) {
				cleanupGenPackages(toDelete, genpackage);

				cleanupGenClasses(toDelete, genpackage);

			}

		}
		for (GenBase genFeature : toDelete) {
			session.getModelAccessor().eDelete(genFeature, session.getSemanticCrossReferencer());
		}

		for (GenModel genmodel : genmodels) {
			genmodel.reconcile();			
		}

	}

	private void cleanupGenPackages(Set<GenBase> toDelete, GenPackage genpackage) {
		for (GenPackage subpackage : genpackage.getSubGenPackages()) {
			if (subpackage.getEcorePackage() != null
					&& subpackage.getEcorePackage().getESuperPackage() != genpackage.getEcorePackage()) {
				toDelete.add(subpackage);
			} else {
				cleanupGenClasses(toDelete, subpackage);
			}
		}
	}

	private void cleanupGenClasses(Set<GenBase> toDelete, GenPackage genpackage) {
		for (GenClass genclass : genpackage.getGenClasses()) {

			if (genclass.getEcoreClass() != null
					&& genclass.getEcoreClass().getEPackage() != genpackage.getEcorePackage()) {
				toDelete.add(genclass);
			} else {
				/*
				 * any feature/eclass vs genfeature/genclass mismatch should be
				 * fixed by deleting the corresponding genfeature.
				 */
				for (GenFeature feat : genclass.getGenFeatures()) {
					if (feat.getEcoreFeature() != null && feat.getEcoreFeature().getEContainingClass() != feat
							.getGenClass().getEcoreClassifier()) {
						toDelete.add(feat);
					}
				}
				for (GenOperation op : genclass.getGenOperations()) {
					if (op.getEcoreOperation() != null
							&& op.getEcoreOperation().getEContainingClass() != op.getGenClass().getEcoreClassifier()) {
						toDelete.add(op);
					}
					for (GenParameter param : op.getGenParameters()) {
						if (param.getEcoreParameter() != null
								&& param.getEcoreParameter().getEOperation() != op.getEcoreOperation()) {
							toDelete.add(param);
						}
					}
				}

			}
		}
	}

}
