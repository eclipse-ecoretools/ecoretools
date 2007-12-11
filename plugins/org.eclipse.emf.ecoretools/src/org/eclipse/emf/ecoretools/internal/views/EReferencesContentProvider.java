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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Returns references to an EClass and the associated StructuralFeatures
 * 
 * @author <a href="david.sciamma@anyware-tech.com">David Sciamma</a>
 */
public class EReferencesContentProvider implements ITreeContentProvider {

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object element) {
		if (element instanceof EStructuralFeature.Setting) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) element;
			return new Object[] { setting.getEObject() };
		}
		return new Object[0];
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	public Object getParent(Object element) {
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	public boolean hasChildren(Object element) {
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof EClass) {
			EClass eClass = (EClass) inputElement;
			List<EStructuralFeature.Setting> result = new ArrayList<EStructuralFeature.Setting>();
			for (EStructuralFeature.Setting setting : EcoreUtil.UsageCrossReferencer.find(eClass, eClass.eResource())) {
				if (setting.getEObject() instanceof EReference && !isContained(result, setting)) {
					result.add(setting);
				}
			}
			return result.toArray();
		}

		return getChildren(inputElement);
	}

	private boolean isContained(List<EStructuralFeature.Setting> l, EStructuralFeature.Setting setting) {
		boolean found = false;
		Iterator<EStructuralFeature.Setting> it = l.iterator();
		while (it.hasNext() && !found) {
			EStructuralFeature.Setting contained = it.next();
			if (contained.getEObject().equals(setting.getEObject())) {
				found = true;
			}
		}

		return found;
	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// Do nothing

	}

	/**
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Do nothing
	}

}
