/***********************************************************************
 * Copyright (c) 2007 Anyware Technologies
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 *
 * $Id: EReferencesLabelProvider.java,v 1.3 2008/04/28 08:41:19 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.ui.views;

import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecoretools.ui.views.EReferencesContentProvider.WrappedEClass;
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
	@Override
	public Image getImage(Object element) {
		if (element instanceof WrappedEClass) {
			return delegateProvider.getImage(((WrappedEClass) element).getWrappedEClass());
		}
		return delegateProvider.getImage(element);
	}

	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof WrappedEClass) {
			return delegateProvider.getText(((WrappedEClass) element).getWrappedEClass());
		}
		return delegateProvider.getText(element);
	}
}
