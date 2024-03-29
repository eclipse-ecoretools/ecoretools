/***********************************************************************
 * Copyright (c) 2007, 2023 Anyware Technologies and others
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
 * $Id: AnalysisView.java,v 1.5 2008/05/19 09:26:31 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.ui.views;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.ui.actions.RefreshAction;
import org.eclipse.emf.ecoretools.ui.actions.ToggleSynchronizeAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * This class implements the basic behavior of an analysis view for Ecore.
 * 
 * @author <a href="david.sciamma@anyware-tech.com">David Sciamma</a>
 */
public abstract class AnalysisView extends ViewPart implements ISelectionListener {

	 private static final Transfer TRANSFER = LocalSelectionTransfer.getTransfer();
	
	private EObject lastValidObject;

	private EObject analyzedObject;

	private boolean isSynchronized = false;

	private ToggleSynchronizeAction toggleSynchroAction;

	private RefreshAction refreshAction;

	/**
	 * Constructor
	 */
	public AnalysisView() {
		ISelectionService selService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		selService.addSelectionListener(this);

		toggleSynchroAction = new ToggleSynchronizeAction(this);
		refreshAction = new RefreshAction(this);
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		ISelectionService selService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		selService.removeSelectionListener(this);

		super.dispose();
	}

	/**
	 * Return the analyzed object
	 * 
	 * @return the EObject analyzed by this view
	 */
	protected EObject getAnalyzedObject() {
		return analyzedObject;
	}

	/**
	 * Changes the analyzed object and refresh the view
	 * 
	 * @param obj
	 *            the EObject that must be analyzed
	 */
	public void setAnalyzedObject(EObject obj) {
		if (obj != null) {
			analyzedObject = obj;
			refresh(analyzedObject);
		} 
	}

	/**
	 * Returns the synchronization state of this view
	 * 
	 * @return <code>true</code> if the view is synchronized
	 */
	public boolean isSynchronized() {
		return isSynchronized;
	}

	/**
	 * Changes the synchronization of the view
	 * 
	 * @param synchro
	 *            the new synchronization state
	 */
	public void setSynchronized(boolean synchro) {
		isSynchronized = synchro;
	}

	/**
	 * Returns the current selection of the workbench
	 * 
	 * @return the selected EObject
	 */
	protected EObject getActiveSelection() {
		ISelectionService selService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();

		if (selService.getSelection() instanceof IStructuredSelection) {
			return getSelection((IStructuredSelection) selService.getSelection());
		}
		return null;
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
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			EObject selectedObject = getSelection((IStructuredSelection) selection);
			
			if (selectedObject != null) {
				lastValidObject = selectedObject;
			}

			if (isSynchronized()) {
				setAnalyzedObject(selectedObject);
			}
		}
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		fillActionBars();
	}

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		// Do nothing
	}

	/**
	 * Build the action bars of the view
	 */
	protected void fillActionBars() {
		IActionBars actionBars = getViewSite().getActionBars();
		setGlobalActionHandlers(actionBars);
		fillToolBar(actionBars.getToolBarManager());
		fillViewMenu(actionBars.getMenuManager());

	}

	/**
	 * Registers the global action handler
	 * 
	 * @param actionBars
	 *            the view action bars
	 */
	protected void setGlobalActionHandlers(IActionBars actionBars) {
		// Do nothing
	}

	/**
	 * Build the view toolbar
	 * 
	 * @param toolBar
	 *            the view toolbar
	 */
	protected void fillToolBar(IToolBarManager toolBar) {
		toolBar.add(toggleSynchroAction);
		toolBar.add(refreshAction);
	}

	/**
	 * Build the view menu
	 * 
	 * @param menu
	 *            the view menu
	 */
	protected void fillViewMenu(IMenuManager menu) {
		// Do nothing
	}

	/**
	 * Refresh the view for the current selected object
	 */
	public void refresh() {
		setAnalyzedObject(lastValidObject);
	}
	

	protected static void prepareViewerForDragToSirius(TreeViewer viewer) {
		/* Configure viewer drag and drop behavior */
        final Transfer[] transfers = new Transfer[] { TRANSFER };        

        final int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
        // final Transfer[] transfers = new Transfer[] {
        // LocalTransfer.getInstance() };
        viewer.addDragSupport(dndOperations, transfers, new EcoreToolsViewsDragTargetAdapter(viewer));
	}

	/**
	 * Refresh the analysis view
	 * 
	 * @param object
	 *            the analyzed object
	 */
	protected abstract void refresh(EObject object);
}
