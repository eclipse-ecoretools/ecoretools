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
 * $Id: EcoreCreateShortcutAction.java,v 1.2 2008/04/28 08:41:31 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.diagram.part;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.diagram.edit.commands.EcoreCreateShortcutDecorationsCommand;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageEditPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @generated
 */
public class EcoreCreateShortcutAction implements IObjectActionDelegate {

	/**
	 * @generated
	 */
	private EPackageEditPart mySelectedElement;

	/**
	 * @generated
	 */
	private Shell myShell;

	/**
	 * @generated
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		myShell = targetPart.getSite().getShell();
	}

	/**
	 * @generated
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		mySelectedElement = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() == 1 && structuredSelection.getFirstElement() instanceof EPackageEditPart) {
				mySelectedElement = (EPackageEditPart) structuredSelection.getFirstElement();
			}
		}
		action.setEnabled(isEnabled());
	}

	/**
	 * @generated
	 */
	private boolean isEnabled() {
		return mySelectedElement != null;
	}

	/**
	 * @generated
	 */
	public void run(IAction action) {
		final View view = (View) mySelectedElement.getModel();
		EcoreElementChooserDialog elementChooser = new EcoreElementChooserDialog(myShell, view);
		int result = elementChooser.open();
		if (result != Window.OK) {
			return;
		}
		URI selectedModelElementURI = elementChooser.getSelectedModelElementURI();
		final EObject selectedElement;
		try {
			selectedElement = mySelectedElement.getEditingDomain().getResourceSet().getEObject(selectedModelElementURI, true);
		} catch (WrappedException e) {
			EcoreDiagramEditorPlugin.getInstance().logError("Exception while loading object: " + selectedModelElementURI.toString(), e); //$NON-NLS-1$
			return;
		}

		if (selectedElement == null) {
			return;
		}
		CreateViewRequest.ViewDescriptor viewDescriptor = new CreateViewRequest.ViewDescriptor(new EObjectAdapter(selectedElement), Node.class, null, EcoreDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
		ICommand command = new CreateCommand(mySelectedElement.getEditingDomain(), viewDescriptor, view);
		command = command.compose(new EcoreCreateShortcutDecorationsCommand(mySelectedElement.getEditingDomain(), view, viewDescriptor));
		try {
			OperationHistoryFactory.getOperationHistory().execute(command, new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
			EcoreDiagramEditorPlugin.getInstance().logError("Unable to create shortcut", e); //$NON-NLS-1$
		}
	}
}
