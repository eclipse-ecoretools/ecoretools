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
 * $Id: EClassHierarchyContentProvider.java,v 1.2 2008/04/28 08:41:19 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.ui.views;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Computes the ascendant hierarchy for an EClass.
 * 
 * @author <a href="david.sciamma@anyware-tech.com">David Sciamma</a>
 */
public class EClassHierarchyContentProvider implements ITreeContentProvider {

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object element) {
		if (element instanceof EClass) {
			return ((EClass) element).getESuperTypes().toArray();
		}
		return new Object[0];
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	@Override
	public Object getParent(Object element) {
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof EClass) {
			return !((EClass) element).getESuperTypes().isEmpty();
		}
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof EClass[]) {
			return (EClass[]) inputElement;
		}

		return getChildren(inputElement);
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// Do nothing

	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Do nothing
	}

}
