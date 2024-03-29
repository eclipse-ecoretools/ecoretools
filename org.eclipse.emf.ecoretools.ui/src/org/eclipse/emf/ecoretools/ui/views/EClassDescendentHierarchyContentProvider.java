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
 * $Id: EClassDescendentHierarchyContentProvider.java,v 1.2 2008/04/28 08:41:19 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Computes the descendant hierarchy for an EClass.
 * 
 * @author <a href="david.sciamma@anyware-tech.com">David Sciamma</a>
 */
public class EClassDescendentHierarchyContentProvider implements ITreeContentProvider {

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object element) {
		if (element instanceof EClass) {
			EClass eClass = (EClass) element;
			List<EObject> result = new ArrayList<EObject>();
			for (EStructuralFeature.Setting setting : EcoreUtil.UsageCrossReferencer.find(eClass, eClass.eResource())) {
				if (setting.getEObject() instanceof EClass && setting.getEStructuralFeature().getFeatureID() == EcorePackage.ECLASS__ESUPER_TYPES) {
					if (!result.contains(setting.getEObject())) {
						result.add(setting.getEObject());
					}
				}
			}
			return result.toArray();
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
			EClass eClass = (EClass) element;
			for (EStructuralFeature.Setting setting : EcoreUtil.UsageCrossReferencer.find(eClass, eClass.eResource())) {
				if (setting.getEObject() instanceof EClass && setting.getEStructuralFeature().getFeatureID() == EcorePackage.ECLASS__ESUPER_TYPES) {
					return true;
				}
			}
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
