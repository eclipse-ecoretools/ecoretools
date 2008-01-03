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

import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.tabbedproperties.sections.AbstractIntegerPropertySection;

/**
 * A section for the upper bound property of an ETypedElement Object.
 * 
 * Creation 5 avr. 2006
 * 
 * @author jlescot
 */
public class UpperBoundPropertySection extends AbstractIntegerPropertySection {

	/**
	 * Predefined string pattern value for numerics and absolute with '-' : -25
	 * '?' (for -2 value) and '*' (for -1 value) characters are also accepted
	 * */
	public static final String UPPER_BOUND_PATTERN = "\\*|\\?|^[-\\d][\\d]*"; //$NON-NLS-1$   

	/** The Pattern used to check an Integer value */
	public static final Pattern UPPER_PATTERN = Pattern.compile(UPPER_BOUND_PATTERN);

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#getFeature()
	 */
	protected EStructuralFeature getFeature() {
		return EcorePackage.eINSTANCE.getETypedElement_UpperBound();
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractIntegerPropertySection#getFeatureInteger()
	 */
	protected Integer getFeatureInteger() {
		return new Integer(((ETypedElement) getEObject()).getUpperBound());
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#getLabelText()
	 */
	protected String getLabelText() {
		return "Upper Bound:";
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#isTextValid()
	 */
	protected boolean isTextValid() {
		return UPPER_PATTERN.matcher(getText().getText()).matches();
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#getNewFeatureValue(java.lang.String)
	 */
	protected Object getNewFeatureValue(String newText) {
		String text = newText;
		if ("*".equals(newText)) {
			text = "-1";
		} else if ("?".equals(newText)) {
			text = "-2";
		}
		return new Integer(Integer.parseInt(text));
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#getFeatureAsString()
	 */
	protected String getFeatureAsString() {
		Integer upper = getFeatureInteger();
		if (upper.intValue() == -1) {
			return "*";
		} else if (upper.intValue() == -2) {
			return "?";
		} else {
			return getFeatureInteger().toString();
		}
	}
}