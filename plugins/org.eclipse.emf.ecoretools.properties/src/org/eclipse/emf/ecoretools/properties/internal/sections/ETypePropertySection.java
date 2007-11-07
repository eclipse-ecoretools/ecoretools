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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.tabbedproperties.sections.AbstractChooserPropertySection;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * A section for the shot property of a selected player Object.
 * 
 * Creation 5 avr. 2006
 * 
 * @author jlescot
 */
public class ETypePropertySection extends AbstractChooserPropertySection {

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTabbedPropertySection#getFeature()
	 */
	protected EStructuralFeature getFeature() {
		return EcorePackage.eINSTANCE.getETypedElement_EType();
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTabbedPropertySection#getLabelText()
	 */
	protected String getLabelText() {
		return "EType:";
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractChooserPropertySection#getComboFeatureValues()
	 */
	protected Object[] getComboFeatureValues() {
		for (Adapter adapter : getEObject().eAdapters()) {
			if (adapter instanceof ItemProviderAdapter) {
				IItemPropertyDescriptor descriptor = ((ItemProviderAdapter) adapter).getPropertyDescriptor(getEObject(), "EType");
				if (descriptor != null) {
					return descriptor.getChoiceOfValues(getEObject()).toArray();
				}
			}
		}
		return new String[] { "" };
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractChooserPropertySection#getLabelProvider()
	 */
	protected ILabelProvider getLabelProvider() {
		return new AdapterFactoryLabelProvider(new EcoreItemProviderAdapterFactory());
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractChooserPropertySection#getFeatureValue()
	 */
	protected Object getFeatureValue() {
		return ((ETypedElement) getEObject()).getEType();
	}

}