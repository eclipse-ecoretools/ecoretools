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

package org.eclipse.emf.ecoretools.diagram.edit.policies;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageEditPart;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.OpenEditPolicy;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.MultiDiagramLinkStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
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
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * @generated NOT
 */
public class OpenDiagramEditPolicy extends OpenEditPolicy {

	@Override
	protected Command getOpenCommand(Request request) {
		EditPart targetEditPart = getTargetEditPart(request);
		if (false == targetEditPart.getModel() instanceof View) {
			return null;
		}
		View view = (View) targetEditPart.getModel();
		Style link = view.getStyle(NotationPackage.eINSTANCE.getMultiDiagramLinkStyle());
		if (false == link instanceof MultiDiagramLinkStyle) {
			return null;
		}
		return new ICommandProxy(new OpenDiagramCommand((MultiDiagramLinkStyle) link));
	}

	private static class OpenDiagramCommand extends AbstractTransactionalCommand {

		private MultiDiagramLinkStyle multiDiagramFacet;

		OpenDiagramCommand(MultiDiagramLinkStyle multiDiagramLinkStyle) {
			// editing domain is taken for original diagram,
			// if we open diagram from another file, we should use another
			// editing domain
			super(TransactionUtil.getEditingDomain(multiDiagramLinkStyle), "Open Diagram", null);
			multiDiagramFacet = multiDiagramLinkStyle;
		}

		private static final int DELETE = 1010;

		private static final int CREATE = 1020;

		private class ChooseDiagramToOpenDialog extends Dialog {

			private static final int DELETE_ID = IDialogConstants.CLIENT_ID + 1010;

			private static final int CREATE_ID = IDialogConstants.CLIENT_ID + 1020;

			private static final String DELETE_LABEL = "Delete";

			private static final String CREATE_LABEL = "Create";

			private TreeViewer myTreeViewer;

			private MultiDiagramLinkStyle multiDiagramFacet;

			private Diagram selectedDiagram;

			public ChooseDiagramToOpenDialog(Shell parentShell, MultiDiagramLinkStyle multiDiagramFacet) {
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
				return buttonBar;
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
				myTreeViewer.setContentProvider(new DiagramsTreeContentProvider());
				myTreeViewer.setLabelProvider(new DiagramsTreeLabelProvider());
				myTreeViewer.setInput(multiDiagramFacet);
				myTreeViewer.addSelectionChangedListener(new OkButtonListener());
			}

			private class DiagramsTreeContentProvider implements ITreeContentProvider {

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

			private class DiagramsTreeLabelProvider implements ILabelProvider {

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

			private class OkButtonListener implements ISelectionChangedListener {

				public void selectionChanged(SelectionChangedEvent event) {
					if (event.getSelection() instanceof IStructuredSelection) {
						IStructuredSelection selection = (IStructuredSelection) event.getSelection();
						if (selection.size() == 1) {
							Object selectedElement = selection.getFirstElement();
							if (selectedElement instanceof Diagram) {
								selectedDiagram = (Diagram) selectedElement;
								return;
							}
						}
					}
					selectedDiagram = null;
				}
			}

			@Override
			protected void buttonPressed(int buttonId) {
				switch (buttonId) {
				case IDialogConstants.OK_ID:
					okPressed();
					break;
				case CREATE_ID:
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
		}

		// FIXME canExecute if !(readOnly && getDiagramToOpen == null), i.e.
		// open works on ro diagrams only when there's associated diagram
		// already
		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			// Open dialog
			final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getSite().getShell();
			ChooseDiagramToOpenDialog dialog = new ChooseDiagramToOpenDialog(shell, multiDiagramFacet);
			switch (dialog.open()) {
			// Cancel pressed
			case Window.CANCEL: {
				// nothing to do
				break;
			}
				// OK pressed
			case Window.OK: {
				okPressed(dialog.getSelectedDiagram());
				break;
			}
				// Create pressed
			case CREATE: {
				createPressed();
				break;
			}
				// Delete pressed
			case DELETE: {
				deletePressed(dialog.getSelectedDiagram());
				break;
			}
				// Things gone bad ...
			default: {
				return CommandResult.newErrorCommandResult("Diagram operation failed !");
			}
			}
			return CommandResult.newOKCommandResult();
		}

		private void okPressed(Diagram diagram) {
			openEditor(diagram);
		}

		private IEditorInput getEditorInput(Diagram diagram) {
			URI parentUri = diagram.eResource().getURI();
			URI uri = parentUri.appendFragment(diagram.eResource().getURIFragment(diagram));
			return new URIEditorInput(uri);
		}

		private void openEditor(Diagram diagram) {
			if (diagram != null) {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					page.openEditor(getEditorInput(diagram), getEditorID());
				} catch (PartInitException e) {
					EcoreDiagramEditorPlugin.getInstance().logError("Can't open Ecore Diagram Editor !");
				}
			}
		}

		private void createPressed() {
			try {
				openEditor(intializeNewDiagram());
			} catch (ExecutionException e) {
				EcoreDiagramEditorPlugin.getInstance().logError("Can't open Ecore Diagram Editor !");
			}
		}

		protected void deletePressed(Diagram diagram) {
			if (diagram != null) { // Close associated diagram
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IEditorPart editor = page.findEditor(getEditorInput(diagram));
				if (editor != null) {
					page.closeEditor(editor, true);
				}
				assert multiDiagramFacet.eResource() != null;
				multiDiagramFacet.eResource().getContents().remove(diagram);
				multiDiagramFacet.getDiagramLinks().remove(diagram);
			}
		}

		/**
		 * @generated
		 */
		protected Diagram intializeNewDiagram() throws ExecutionException {
			Diagram d = ViewService.createDiagram(getDiagramDomainElement(), getDiagramKind(), getPreferencesHint());
			if (d == null) {
				throw new ExecutionException("Can't create diagram of '" + getDiagramKind() + "' kind");
			}
			setDefaultNameForDiagram(d);
			multiDiagramFacet.getDiagramLinks().add(d);
			assert multiDiagramFacet.eResource() != null;
			multiDiagramFacet.eResource().getContents().add(d);

			/*
			 * try { new WorkspaceModifyOperation() {
			 * 
			 * protected void execute(IProgressMonitor monitor) throws
			 * CoreException, InvocationTargetException, InterruptedException {
			 * try { for (Iterator it =
			 * multiDiagramFacet.eResource().getResourceSet().getResources().iterator();
			 * it.hasNext();) { Resource nextResource = (Resource) it.next(); if
			 * (nextResource.isLoaded() &&
			 * !getEditingDomain().isReadOnly(nextResource)) {
			 * nextResource.save(EcoreDiagramEditorUtil.getSaveOptions()); } } }
			 * catch (IOException ex) { throw new InvocationTargetException(ex,
			 * "Save operation failed"); } } }.run(null); } catch
			 * (InvocationTargetException e) { throw new
			 * ExecutionException("Can't create diagram of '" + getDiagramKind() +
			 * "kind", e); } catch (InterruptedException e) { throw new
			 * ExecutionException("Can't create diagram of '" + getDiagramKind() +
			 * "kind", e); }
			 */

			return d;
		}

		protected EObject getDiagramDomainElement() {
			// use same element as associated with EP
			return ((View) multiDiagramFacet.eContainer()).getElement();
		}

		protected PreferencesHint getPreferencesHint() {
			// XXX prefhint from target diagram's editor?
			return EcoreDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
		}

		protected String getDiagramKind() {
			return EPackageEditPart.MODEL_ID;
		}

		protected String getEditorID() {
			return EcoreDiagramEditor.ID;
		}

		protected void setDefaultNameForDiagram(Diagram elementToConfigure) {
			EPackage pseudoContainer = (EPackage) elementToConfigure.getElement();
			String baseString = pseudoContainer.getName() + "_Diagram";
			int count = 0;
			for (Iterator it = multiDiagramFacet.getDiagramLinks().iterator(); it.hasNext();) {
				Diagram diagram = (Diagram) it.next();
				if (diagram.getName().equals(baseString + count)) {
					count++;
				}
			}
			elementToConfigure.setName(baseString + count);
		}
	}
}