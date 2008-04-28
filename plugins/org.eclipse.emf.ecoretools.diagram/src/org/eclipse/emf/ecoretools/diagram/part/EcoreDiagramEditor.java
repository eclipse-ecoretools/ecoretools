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
 * $Id: EcoreDiagramEditor.java,v 1.12 2008/04/28 08:41:31 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.diagram.part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecoretools.diagram.navigator.EcoreNavigatorItem;
import org.eclipse.emf.ecoretools.diagram.outline.EcoreDiagramOutlinePage;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramDropTargetListener;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * @generated
 */
public class EcoreDiagramEditor extends DiagramDocumentEditor implements IGotoMarker {

	@Override
	protected void setPartName(String partName) {
		String title = null;

		if (getDiagram() != null) {
			title = getDiagram().getName();
		}

		if ((title != null) && (!title.equals(""))) {
			super.setPartName(title);
		} else {
			super.setPartName(partName);
		}
	}

	/**
	 * @generated
	 */
	public static final String ID = "org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final String CONTEXT_ID = "org.eclipse.emf.ecoretools.diagram.ui.diagramContext"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public EcoreDiagramEditor() {
		super(true);
	}

	/**
	 * @generated
	 */
	@Override
	protected String getContextID() {
		return CONTEXT_ID;
	}

	/**
	 * @generated
	 */
	@Override
	protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot) {
		PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
		new EcorePaletteFactory().fillPalette(root);
		return root;
	}

	/**
	 * @generated
	 */
	@Override
	protected PreferencesHint getPreferencesHint() {
		return EcoreDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
	}

	/**
	 * @generated
	 */
	@Override
	public String getContributorId() {
		return EcoreDiagramEditorPlugin.ID;
	}

	/**
	 * Handle IContentOutlinePage adapter
	 * 
	 * @generated NOT
	 */
	@Override
	public Object getAdapter(Class type) {
		if (type == IShowInTargetList.class) {
			return new IShowInTargetList() {

				public String[] getShowInTargetIds() {
					return new String[] { ProjectExplorer.VIEW_ID };
				}
			};
		}
		if (type == IContentOutlinePage.class) {
			return new EcoreDiagramOutlinePage(this);
		}
		return super.getAdapter(type);
	}

	/**
	 * @generated
	 */
	@Override
	protected IDocumentProvider getDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput || input instanceof URIEditorInput) {
			return EcoreDiagramEditorPlugin.getInstance().getDocumentProvider();
		}
		return super.getDocumentProvider(input);
	}

	/**
	 * @generated
	 */
	@Override
	public TransactionalEditingDomain getEditingDomain() {
		IDocument document = getEditorInput() != null ? getDocumentProvider().getDocument(getEditorInput()) : null;
		if (document instanceof IDiagramDocument) {
			return ((IDiagramDocument) document).getEditingDomain();
		}
		return super.getEditingDomain();
	}

	/**
	 * @generated
	 */
	@Override
	protected void setDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput || input instanceof URIEditorInput) {
			setDocumentProvider(EcoreDiagramEditorPlugin.getInstance().getDocumentProvider());
		} else {
			super.setDocumentProvider(input);
		}
	}

	/**
	 * @generated
	 */
	public void gotoMarker(IMarker marker) {
		MarkerNavigationService.getInstance().gotoMarker(this, marker);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * @generated
	 */
	@Override
	public void doSaveAs() {
		performSaveAs(new NullProgressMonitor());
	}

	/**
	 * @generated
	 */
	@Override
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		IEditorInput input = getEditorInput();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		IFile original = input instanceof IFileEditorInput ? ((IFileEditorInput) input).getFile() : null;
		if (original != null) {
			dialog.setOriginalFile(original);
		}
		dialog.create();
		IDocumentProvider provider = getDocumentProvider();
		if (provider == null) {
			// editor has been programmatically closed while the dialog was open
			return;
		}
		if (provider.isDeleted(input) && original != null) {
			String message = NLS.bind(Messages.EcoreDiagramEditor_SavingDeletedFile, original.getName());
			dialog.setErrorMessage(null);
			dialog.setMessage(message, IMessageProvider.WARNING);
		}
		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IPath filePath = dialog.getResult();
		if (filePath == null) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = workspaceRoot.getFile(filePath);
		final IEditorInput newInput = new FileEditorInput(file);
		// Check if the editor is already open
		IEditorMatchingStrategy matchingStrategy = getEditorDescriptor().getEditorMatchingStrategy();
		IEditorReference[] editorRefs = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
		for (int i = 0; i < editorRefs.length; i++) {
			if (matchingStrategy.matches(editorRefs[i], newInput)) {
				MessageDialog.openWarning(shell, Messages.EcoreDiagramEditor_SaveAsErrorTitle, Messages.EcoreDiagramEditor_SaveAsErrorMessage);
				return;
			}
		}
		boolean success = false;
		try {
			provider.aboutToChange(newInput);
			getDocumentProvider(newInput).saveDocument(progressMonitor, newInput, getDocumentProvider().getDocument(getEditorInput()), true);
			success = true;
		} catch (CoreException x) {
			IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				ErrorDialog.openError(shell, Messages.EcoreDiagramEditor_SaveErrorTitle, Messages.EcoreDiagramEditor_SaveErrorMessage, x.getStatus());
			}
		} finally {
			provider.changed(newInput);
			if (success) {
				setInput(newInput);
			}
		}
		if (progressMonitor != null) {
			progressMonitor.setCanceled(!success);
		}
	}

	/**
	 * @generated
	 */
	@Override
	public ShowInContext getShowInContext() {
		return new ShowInContext(getEditorInput(), getNavigatorSelection());
	}

	/**
	 * @generated
	 */
	private ISelection getNavigatorSelection() {
		IDiagramDocument document = getDiagramDocument();
		if (document == null) {
			return StructuredSelection.EMPTY;
		}
		Diagram diagram = document.getDiagram();
		IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
		if (file != null) {
			EcoreNavigatorItem item = new EcoreNavigatorItem(diagram, file, false);
			return new StructuredSelection(item);
		}
		return StructuredSelection.EMPTY;
	}

	/**
	 * @generated
	 */
	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		getDiagramGraphicalViewer().addDropTargetListener(new DropTargetListener(getDiagramGraphicalViewer(), LocalSelectionTransfer.getTransfer()) {

			@Override
			protected Object getJavaObject(TransferData data) {
				return LocalSelectionTransfer.getTransfer().nativeToJava(data);
			}

		});
		getDiagramGraphicalViewer().addDropTargetListener(new DropTargetListener(getDiagramGraphicalViewer(), LocalTransfer.getInstance()) {

			@Override
			protected Object getJavaObject(TransferData data) {
				return LocalTransfer.getInstance().nativeToJava(data);
			}

		});
	}

	/**
	 * @generated
	 */
	private abstract class DropTargetListener extends DiagramDropTargetListener {

		/**
		 * @generated
		 */
		public DropTargetListener(EditPartViewer viewer, Transfer xfer) {
			super(viewer, xfer);
		}

		/**
		 * @generated
		 */
		@Override
		protected List getObjectsBeingDropped() {
			TransferData data = getCurrentEvent().currentDataType;
			Collection uris = new HashSet();

			Object transferedObject = getJavaObject(data);
			if (transferedObject instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection) transferedObject;
				for (Iterator it = selection.iterator(); it.hasNext();) {
					Object nextSelectedObject = it.next();
					if (nextSelectedObject instanceof EcoreNavigatorItem) {
						View view = ((EcoreNavigatorItem) nextSelectedObject).getView();
						nextSelectedObject = view.getElement();
					} else if (nextSelectedObject instanceof IAdaptable) {
						IAdaptable adaptable = (IAdaptable) nextSelectedObject;
						nextSelectedObject = adaptable.getAdapter(EObject.class);
					}

					if (nextSelectedObject instanceof EObject) {
						EObject modelElement = (EObject) nextSelectedObject;
						Resource modelElementResource = modelElement.eResource();
						uris.add(modelElementResource.getURI().appendFragment(modelElementResource.getURIFragment(modelElement)));
					}
				}
			}

			List result = new ArrayList();
			for (Iterator it = uris.iterator(); it.hasNext();) {
				URI nextURI = (URI) it.next();
				EObject modelObject = getEditingDomain().getResourceSet().getEObject(nextURI, true);
				result.add(modelObject);
			}
			return result;
		}

		/**
		 * @generated
		 */
		protected abstract Object getJavaObject(TransferData data);

	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor#shouldAddUndoContext(org.eclipse.core.commands.operations.IUndoableOperation)
	 */
	@Override
	protected boolean shouldAddUndoContext(IUndoableOperation operation) {
		// Add command from the property sheets
		if (operation instanceof EMFCommandOperation) {
			return true;
		}
		if (operation.getLabel() != null && (operation.getLabel().equals("Copy") || operation.getLabel().equals("Cut") || operation.getLabel().equals("Paste"))) {
			return true;
		}
		return false;
	}

	private IUndoContext localUndoContext;

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor#getUndoContext()
	 */
	@Override
	protected IUndoContext getUndoContext() {
		if (localUndoContext == null) {
			localUndoContext = new ObjectUndoContext(this);
			setUndoContext(localUndoContext);
			getOperationHistory().setLimit(localUndoContext, 50);
		}
		return localUndoContext;
	}

	/**
	 * Remove this method when the feature 210642 will be implemented
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor#initializeGraphicalViewerContents()
	 */
	@Override
	protected void initializeGraphicalViewerContents() {
		super.initializeGraphicalViewerContents();
		// Scroll-wheel Zoom
		getGraphicalViewer().setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.CTRL), MouseWheelZoomHandler.SINGLETON);
	}

	@Override
	protected int getInitialDockLocation() {
		return PositionConstants.WEST;
	}

}
