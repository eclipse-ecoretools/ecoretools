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

package org.eclipse.emf.ecoretools.properties.internal.sections;

import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.tabbedproperties.sections.AbstractIntegerPropertySection;

/**
 * A section for the value property of an EEnumLiteral Object.
 * 
 * Creation 5 avr. 2006
 * 
 * @author jlescot
 */
public class EEnumLiteralValuePropertySection extends AbstractIntegerPropertySection {

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#getFeature()
	 */
	protected EStructuralFeature getFeature() {
		return EcorePackage.eINSTANCE.getEEnumLiteral_Value();
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractIntegerPropertySection#getFeatureInteger()
	 */
	protected Integer getFeatureInteger() {
		return new Integer(((EEnumLiteral) getEObject()).getValue());
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#getLabelText()
	 */
	protected String getLabelText() {
		return "Value:";
	}

}