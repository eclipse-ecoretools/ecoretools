/*******************************************************************************
 * Copyright (c) 2013 THALES GLOBAL SERVICES and Others
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
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreSwitch;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

/**
 * A switch implementation retrieving all the elements which might be related to
 * a single one.
 */
public class RelatedElementsSwitch extends EcoreSwitch<List<EObject>> {

	private Set<EObject> relateds;

	private Collection<Setting> xRefs;

	private ECrossReferenceAdapter referencer;

	public RelatedElementsSwitch() {

	}

	public RelatedElementsSwitch(ECrossReferenceAdapter xRef) {
		this.referencer = xRef;
	}

	public List<EObject> getRelatedElements(EObject ctx) {
		Session sess = SessionManager.INSTANCE.getSession(ctx);
		relateds = Sets.newLinkedHashSet();
		if (sess != null) {
			xRefs = sess.getSemanticCrossReferencer().getInverseReferences(ctx);
		} else if (referencer != null) {
			xRefs = referencer.getInverseReferences(ctx);
		}
		doSwitch(ctx);
		relateds.remove(ctx);
		// hack to prevent some null element in relateds for a unknown reason.
		relateds.remove(null);
		return ImmutableList.copyOf(relateds);
	}

	@Override
	public List<EObject> caseEClass(EClass object) {
		relateds.addAll(object.getESuperTypes());
		for (Setting xRef : xRefs) {
			if (xRef.getEObject() instanceof EClass) {
				relateds.add(xRef.getEObject());
			} else if (xRef.getEObject() instanceof EReference && xRef.getEStructuralFeature() == EcorePackage.eINSTANCE.getEReference_EReferenceType()) {
				relateds.add(((EReference) xRef.getEObject())
						.getEContainingClass());
			}
		}
		for (EReference eRef : object.getEReferences()) {
			if (eRef.getEType()!=null) {
				relateds.add(eRef.getEType());
			}
		}
		return super.caseEClass(object);
	}

	@Override
	public List<EObject> caseEOperation(EOperation object) {
		if (object.getEType() != null) {
			relateds.add(object.getEType());
		}

		for (EParameter param : object.getEParameters()) {
			if (param.getEType() != null) {
				relateds.add(param.getEType());
			}
		}

		return super.caseEOperation(object);
	}

	@Override
	public List<EObject> caseEReference(EReference object) {
		relateds.add(object.getEType());
		return super.caseEReference(object);
	}

	@Override
	public List<EObject> caseEPackage(EPackage object) {
		relateds.addAll(object.getEClassifiers());
		return super.caseEPackage(object);
	}

}
