/*******************************************************************************
 * Copyright (c) 2015 Obeo
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
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.session.ModelChangeTrigger;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;

import com.google.common.collect.Sets;

public class LiveValidationTrigger implements ModelChangeTrigger {

	public static final NotificationFilter IS_ECORE_CHANGE = new NotificationFilter.Custom() {

		public boolean matches(Notification notification) {
			return (!notification.isTouch() && notification.getFeature() instanceof EStructuralFeature
					&& ((EStructuralFeature) notification.getFeature()).getEContainingClass()
							.getEPackage() == EcorePackage.eINSTANCE);
		}
	};

	private TransactionalEditingDomain domain;

	/**
	 * We need to be triggered before the refresh mechanism takes place so that the
	 * diagnostic attachements are up-to date when computing colors.
	 */
	public static final int PRIORITY = 0;

	public LiveValidationTrigger(TransactionalEditingDomain domain) {
		this.domain = domain;
	}

	public Option<Command> localChangesAboutToCommit(Collection<Notification> notifications) {
		final Set<EObject> changedEcoreObjects = Sets.newLinkedHashSet();
		for (Notification notif : notifications) {
			Object obj = notif.getNotifier();
			if (obj instanceof EObject && ((EObject) obj).eClass() != null
					&& ((EObject) obj).eClass().getEPackage() == EcorePackage.eINSTANCE) {
				changedEcoreObjects.add((EObject) obj);
			}
		}
		if (changedEcoreObjects.size() > 0) {

			Command revalidateEObjects = new RecordingCommand(domain) {

				@Override
				protected void doExecute() {
					Set<EObject> containers = Sets.newLinkedHashSet();
					for (EObject eObj : changedEcoreObjects) {
						revalidate(eObj);
						EObject container = eObj.eContainer();
						if (container != null) {
							containers.add(container);
							/*
							 * in the case of a changed EParameter or annotationwe really want to include
							 * the parent EClass in the revalidation.
							 */
							if ((eObj instanceof EParameter || eObj instanceof EStringToStringMapEntryImpl)
									&& container.eContainer() instanceof EClass) {
								containers.add(container.eContainer());
							}
						}
					}
					/*
					 * When an Ecore object changes it is likely the container might have a new
					 * validation error (or an error might be gone) even if it has suffered no
					 * change itself. Example : two EStructural features having the same name and
					 * contained in the same EClass will trigger an error on such EClass. This state
					 * should be updated when one of the EStructuralFeature got renamed.
					 */
					for (EObject container : containers) {
						revalidate(container);
					}
				}

				protected void revalidate(EObject eObj) {
					DiagnosticAttachment diag = DiagnosticAttachment.getAttachment(eObj);
					/*
					 * If the EObject had a validation marker, then we need to update its state,
					 * otherwise nobody cared about its validation status, no need to pre-compute
					 * it.
					 */
					if (diag != null) {
						try {
							/*
							 * anything could happen within the validate. We make sure we won't fail the
							 * whole post-process in this case.
							 */
							Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObj);
							/*
							 * We attach the result of the validation on the EObject through the eAdapters
							 * list. It might be consumed by some modelers to change colors or update
							 * tooltips.
							 */
							diag.setDiagnostic(diagnostic);
						} catch (Throwable e) {
							/*
							 * Anything which happens here might not be a concern.
							 */
						}
					}
				}
			};

			return Options.newSome(revalidateEObjects);
		}

		return Options.newNone();
	}

	public int priority() {
		return PRIORITY;
	}

}
