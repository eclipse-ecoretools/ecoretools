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

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.tabbedproperties.sections.AbstractStringPropertySection;

/**
 * The section for the key property of an EStringToStringMapEntry Object.
 * 
 * Creation 5 avr. 2006
 * 
 * @author jlescot
 */
public class DetailEntryKeyPropertySection extends AbstractStringPropertySection {

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#getLabelText()
	 */
	protected String getLabelText() {
		return "Key:";
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTextPropertySection#getFeature()
	 */
	protected EStructuralFeature getFeature() {
		return EcorePackage.eINSTANCE.getEStringToStringMapEntry_Key();
	}
}