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
 * $Id: ShowHierarchyAction.java,v 1.2 2008/04/28 08:41:20 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.ui.actions;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.ui.Activator;
import org.eclipse.emf.ecoretools.ui.views.EClassHierarchyView;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;

/**
 * This action shows the hierarchy to the selected object
 * 
 * @author <a href="david.sciamma@anyware-tech.com">David Sciamma</a>
 */
public class ShowHierarchyAction implements IObjectActionDelegate {

	private IWorkbenchPart activePart;

	private EClass activeEClass;

	/**
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		activePart = targetPart;
	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (activeEClass != null) {
			try {
				IViewPart view = activePart.getSite().getPage().showView(EClassHierarchyView.VIEW_ID);
				((EClassHierarchyView) view).setAnalyzedObject(activeEClass);
			} catch (PartInitException exception) {
				Activator.log(exception);
			}
		}
	}

	/**
	 * Returns the selected EObject from the given selection
	 * 
	 * @param selection
	 *            the selection which contains the EObject
	 * @return the selected EObject
	 */
	protected EObject getSelection(IStructuredSelection selection) {
		Object selectedObject = selection.getFirstElement();
		if (selectedObject != null) {
			if (selectedObject instanceof EObject) {
				return (EObject) selectedObject;
			}

			if (selectedObject instanceof IAdaptable) {
				Object adaptedObj = ((IAdaptable) selectedObject).getAdapter(EObject.class);
				if (adaptedObj instanceof EObject) {
					return (EObject) adaptedObj;
				}
			}

			IAdapterManager adapterManager = Platform.getAdapterManager();
			if (adapterManager.hasAdapter(selectedObject, EObject.class.getName())) {
				Object adaptedObj = adapterManager.loadAdapter(selectedObject, EObject.class.getName());
				if (adaptedObj instanceof EObject) {
					return (EObject) adaptedObj;
				}
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		activeEClass = null;
		if (selection instanceof IStructuredSelection) {
			EObject selectedEObject = getSelection((IStructuredSelection) selection);
			if (selectedEObject instanceof EClass) {
				activeEClass = (EClass) selectedEObject;
			}
		}
	}

}
