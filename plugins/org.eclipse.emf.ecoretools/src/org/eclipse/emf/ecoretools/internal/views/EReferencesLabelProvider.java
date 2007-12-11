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

package org.eclipse.emf.ecoretools.internal.views;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Display text for references
 * 
 * @author <a href="david.sciamma@anyware-tech.com">David Sciamma</a>
 */
public class EReferencesLabelProvider extends LabelProvider {

	private ILabelProvider delegateProvider;

	/**
	 * Constructor
	 */
	public EReferencesLabelProvider() {
		delegateProvider = new AdapterFactoryLabelProvider(new EcoreItemProviderAdapterFactory());
	}

	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (element instanceof EStructuralFeature.Setting) {
			return delegateProvider.getImage(((EStructuralFeature.Setting) element).getEObject().eContainer());
		}
		return delegateProvider.getImage(element);
	}

	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof EStructuralFeature.Setting) {
			return delegateProvider.getText(((EStructuralFeature.Setting) element).getEObject().eContainer());
		}
		return delegateProvider.getText(element);
	}
}
