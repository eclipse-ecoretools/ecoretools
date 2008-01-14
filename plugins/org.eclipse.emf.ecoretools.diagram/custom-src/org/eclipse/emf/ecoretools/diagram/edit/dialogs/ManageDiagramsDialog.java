/**
 * Copyright (c) 2008 {INITIAL COPYRIGHT OWNER}
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 */
package org.eclipse.emf.ecoretools.diagram.edit.dialogs;

import java.util.Collections;

import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.MultiDiagramLinkStyle;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * 
 * TODO Describe the class here <br>
 * creation : 14 janv. 08
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class ManageDiagramsDialog extends Dialog {

	private static final int DELETE_ID = IDialogConstants.CLIENT_ID + 1010;

	private static final int CREATE_ID = IDialogConstants.CLIENT_ID + 1020;

	private static final String DELETE_LABEL = "Delete";

	private static final String CREATE_LABEL = "Create";

	private TreeViewer myTreeViewer;

	private MultiDiagramLinkStyle multiDiagramFacet;

	private Diagram selectedDiagram;

	private Button initializeContentButton;

	private boolean initializeContentButtonState;

	public static final int CREATE = 1020;

	public static final int DELETE = 1010;

	public ManageDiagramsDialog(Shell parentShell, MultiDiagramLinkStyle multiDiagramFacet) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.multiDiagramFacet = multiDiagramFacet;

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		getShell().setText("Manage diagrams");
		createDiagramTreeBrowser(composite);
		return composite;
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Control buttonBar = super.createButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setText("Open");
		getButton(IDialogConstants.OK_ID).setToolTipText("Open selected diagram");
		getButton(CREATE_ID).setToolTipText("Create a new diagram an open it");
		getButton(DELETE_ID).setToolTipText("Delete selected diagram");
		getButton(IDialogConstants.CANCEL_ID).setToolTipText("Close the dialog");
		validateDialog();
		return buttonBar;
	}

	private void validateDialog() {
		if (getSelectedDiagram() == null) {
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			getButton(DELETE_ID).setEnabled(false);
		} else {
			getButton(IDialogConstants.OK_ID).setEnabled(true);
			getButton(DELETE_ID).setEnabled(true);
		}
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, CREATE_ID, CREATE_LABEL, false);
		createButton(parent, DELETE_ID, DELETE_LABEL, false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	private void createDiagramTreeBrowser(Composite composite) {
		myTreeViewer = new TreeViewer(composite, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.heightHint = 300;
		layoutData.widthHint = 300;
		myTreeViewer.getTree().setLayoutData(layoutData);
		myTreeViewer.setContentProvider(new DiagramTreeContentProvider());
		myTreeViewer.setLabelProvider(new DiagramTreeLabelProvider());
		myTreeViewer.setInput(multiDiagramFacet);
		myTreeViewer.addSelectionChangedListener(new DiagramTreeSelectionChangedListener());
		initializeContentButton = new Button(composite, SWT.CHECK);
		initializeContentButton.setText("Initialize newly created diagram content");
		initializeContentButton.setSelection(true);
	}

	private class DiagramTreeContentProvider implements ITreeContentProvider {

		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof MultiDiagramLinkStyle) {
				return ((MultiDiagramLinkStyle) parentElement).getDiagramLinks().toArray();
			}
			return Collections.EMPTY_LIST.toArray();
		}

		public Object getParent(Object element) {
			return null;
		}

		public boolean hasChildren(Object element) {
			if (element instanceof MultiDiagramLinkStyle) {
				return ((MultiDiagramLinkStyle) element).getDiagramLinks().isEmpty();
			}
			return false;
		}

		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	private class DiagramTreeLabelProvider implements ILabelProvider {

		private WorkbenchLabelProvider myWorkbenchLabelProvider = new WorkbenchLabelProvider();

		private AdapterFactoryLabelProvider myAdapterFactoryLabelProvider = new AdapterFactoryLabelProvider(EcoreDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());

		public Image getImage(Object element) {
			return EcoreDiagramEditorPlugin.getInstance().getBundledImage("icons/EPackage.gif");
		}

		public String getText(Object element) {
			return ((Diagram) element).getName();
		}

		public void addListener(ILabelProviderListener listener) {
			myWorkbenchLabelProvider.addListener(listener);
			myAdapterFactoryLabelProvider.addListener(listener);
		}

		public void dispose() {
			myWorkbenchLabelProvider.dispose();
			myAdapterFactoryLabelProvider.dispose();
		}

		public boolean isLabelProperty(Object element, String property) {
			return myWorkbenchLabelProvider.isLabelProperty(element, property) || myAdapterFactoryLabelProvider.isLabelProperty(element, property);
		}

		public void removeListener(ILabelProviderListener listener) {
			myWorkbenchLabelProvider.removeListener(listener);
			myAdapterFactoryLabelProvider.removeListener(listener);
		}
	}

	private class DiagramTreeSelectionChangedListener implements ISelectionChangedListener {

		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSelection() instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.size() == 1) {
					Object selectedElement = selection.getFirstElement();
					if (selectedElement instanceof Diagram) {
						selectedDiagram = (Diagram) selectedElement;
						validateDialog();
						return;
					}
				}
			}
			selectedDiagram = null;
			validateDialog();
		}
	}

	@Override
	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case IDialogConstants.OK_ID:
			okPressed();
			break;
		case CREATE_ID:
			initializeContentButtonState = initializeContentButton.getSelection();
			setReturnCode(CREATE);
			close();
			break;
		case DELETE_ID:
			setReturnCode(DELETE);
			close();
			break;
		case IDialogConstants.CANCEL_ID:
			cancelPressed();
			break;
		default:
			break;
		}
	}

	public MultiDiagramLinkStyle getMultiDiagramFacet() {
		return multiDiagramFacet;
	}

	public Diagram getSelectedDiagram() {
		return selectedDiagram;
	}

	/**
	 * @return the initializeContentButtonState
	 */
	public boolean getInitializeContentButtonState() {
		return initializeContentButtonState;
	}
}