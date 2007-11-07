/***********************************************************************
 * Copyright (c) 2007 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 **********************************************************************/

package org.eclipse.emf.ecoretools.diagram.part;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * @generated
 */
public class EcoreDomainModelElementTester extends PropertyTester {

	/**
	 * @generated
	 */
	public boolean test(Object receiver, String method, Object[] args, Object expectedValue) {
		if (false == receiver instanceof EObject) {
			return false;
		}
		EObject eObject = (EObject) receiver;
		EClass eClass = eObject.eClass();
		if (eClass == EcorePackage.eINSTANCE.getEAttribute()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEAnnotation()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEClass()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEClassifier()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEDataType()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEEnum()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEEnumLiteral()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEFactory()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEModelElement()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getENamedElement()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEObject()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEOperation()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEPackage()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEParameter()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEReference()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEStructuralFeature()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getETypedElement()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEStringToStringMapEntry()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getEGenericType()) {
			return true;
		}
		if (eClass == EcorePackage.eINSTANCE.getETypeParameter()) {
			return true;
		}
		return false;
	}

}
