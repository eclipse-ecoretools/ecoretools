/*******************************************************************************
 * Copyright (c) 2016 IRT AESE (IRT Saint Exupery) and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Pierre Gaufillet (IRT Saint Exupery) - initial API and implementation
 *     
 *******************************************************************************/
package org.eclipse.emf.ecoretools.ui.views;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.ui.Activator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

public class EClassReferencesHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.
	 * commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		if (selection != null) {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				Object o = structuredSelection.getFirstElement();
				if (o instanceof IAdaptable) {
					Object adaptedObj = ((IAdaptable) o).getAdapter(EObject.class);
					if (adaptedObj instanceof EObject) {
						o = adaptedObj;
					}
				}
				if (o instanceof DSemanticDecorator) {
					o = ((DSemanticDecorator) o).getTarget();
				}

				if (o instanceof EObject) {
					EObject eObject = (EObject) o;
					if (!(eObject instanceof EClass)) {
						eObject = eObject.eClass();
					}
					if (eObject != null) {
						try {
							IWorkbenchPart activePart = HandlerUtil.getActivePartChecked(event);
							IViewPart view = activePart.getSite().getPage().showView(EReferencesView.VIEW_ID);
							((EReferencesView) view).setAnalyzedObject(eObject);
						} catch (PartInitException exception) {
							Activator.log(exception);
						}
					}
				}
			}
		}
		return null;
	}

}
