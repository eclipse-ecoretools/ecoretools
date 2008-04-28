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
 * 
 * $Id: AbstractIntegerPropertySection.java,v 1.2 2008/04/28 12:19:08 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.tabbedproperties.sections;

import java.util.regex.Pattern;

import org.eclipse.emf.tabbedproperties.Messages;
import org.eclipse.emf.tabbedproperties.internal.utils.ColorRegistry;
import org.eclipse.swt.widgets.Event;

/**
 * An abstract implementation of a section for a field with a String property
 * value.
 * 
 * Creation 5 apr. 2006 Updated 7 aug. 2006
 * 
 * @author Jacques Lescot
 * @author Alfredo Serrano
 */
public abstract class AbstractIntegerPropertySection extends AbstractTextPropertySection {

	/** Predefined string pattern value for numerics and absolute with '-' : -25 */
	public static final String ABS_NUMERICS_PATTERN = "^[-\\d][\\d]*"; //$NON-NLS-1$   

	/** The Pattern used to check an Integer value */
	public static final Pattern INTEGER_PATTERN = Pattern.compile(ABS_NUMERICS_PATTERN);

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#verifyField(Event)
	 */
	protected void verifyField(Event e) {
		String value = getText().getText();
		if (value == null || value.equals("") || isTextValid()) { //$NON-NLS-1$
			setErrorMessage(null);
			getText().setBackground(null);
			e.doit = true;
		} else {
			setErrorMessage(Messages.AbstractIntegerPropertySection_UnvalidCharacter);
			getText().setBackground(ColorRegistry.COLOR_ERROR);
			e.doit = false;
		}
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#getFeatureAsString()
	 */
	protected String getFeatureAsString() {
		return getFeatureInteger().toString();
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#getOldFeatureValue()
	 */
	protected Object getOldFeatureValue() {
		return getFeatureInteger();
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#getNewFeatureValue(java.lang.String)
	 */
	protected Object getNewFeatureValue(String newText) {
		return new Integer(Integer.parseInt(newText));
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#isTextValid()
	 */
	protected boolean isTextValid() {
		return INTEGER_PATTERN.matcher(getText().getText()).matches();
	}

	/**
	 * Get the text value of the feature for the text field for the section.
	 * 
	 * @return the text value of the feature.
	 */
	protected abstract Integer getFeatureInteger();

}