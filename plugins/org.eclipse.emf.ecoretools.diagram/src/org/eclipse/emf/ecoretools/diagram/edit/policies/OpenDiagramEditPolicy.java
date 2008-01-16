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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.diagram.edit.dialogs.ManageDiagramsDialog;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageNameEditPart;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramContentInitializer;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.OpenEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutService;
import org.eclipse.gmf.runtime.diagram.ui.services.layout.LayoutType;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.MultiDiagramLinkStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * @generated NOT
 */
public class OpenDiagramEditPolicy extends OpenEditPolicy {

	@Override
	protected Command getOpenCommand(Request request) {
		EditPart targetEditPart = getTargetEditPart(request);
		if (targetEditPart instanceof EPackageNameEditPart && targetEditPart.getParent() != null) {
			targetEditPart = targetEditPart.getParent();
		}
		if (false == targetEditPart instanceof IGraphicalEditPart) {
			return UnexecutableCommand.INSTANCE;
		}

		View view = ((IGraphicalEditPart) targetEditPart).getNotationView();
		Style link = view.getStyle(NotationPackage.eINSTANCE.getMultiDiagramLinkStyle());
		if (false == link instanceof MultiDiagramLinkStyle) {
			return UnexecutableCommand.INSTANCE;
		}

		return new ICommandProxy(new OpenDiagramCommand((MultiDiagramLinkStyle) link));
	}

	private static class OpenDiagramCommand extends AbstractTransactionalCommand {

		private MultiDiagramLinkStyle multiDiagramFacet;

		OpenDiagramCommand(MultiDiagramLinkStyle multiDiagramLinkStyle) {
			super(TransactionUtil.getEditingDomain(multiDiagramLinkStyle), "Open Diagram", null);
			multiDiagramFacet = multiDiagramLinkStyle;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			// Open dialog
			final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getSite().getShell();
			ManageDiagramsDialog manageDiagramsDialog = new ManageDiagramsDialog(shell, multiDiagramFacet);
			switch (manageDiagramsDialog.open()) {
			// Cancel pressed
			case Window.CANCEL: {
				// nothing to do
				break;
			}
				// OK pressed
			case Window.OK: {
				okPressed(manageDiagramsDialog.getSelectedDiagram());
				break;
			}
				// Create pressed
			case ManageDiagramsDialog.CREATE: {
				createPressed(manageDiagramsDialog.getInitializeContentButtonState());
				break;
			}
				// Delete pressed
			case ManageDiagramsDialog.DELETE: {
				deletePressed(manageDiagramsDialog.getSelectedDiagram());
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

		private void createPressed(boolean initializeContent) {
			try {
				openEditor(intializeNewDiagram(initializeContent));
			} catch (ExecutionException e) {
				EcoreDiagramEditorPlugin.getInstance().logError("Can't open Ecore Diagram Editor !");
			}
		}

		protected void deletePressed(Diagram diagram) {
			if (diagram != null) {
				// Close associated diagram
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
		 * @param initializeContent
		 * @generated
		 */
		protected Diagram intializeNewDiagram(boolean initializeContent) throws ExecutionException {
			Diagram diagram = ViewService.createDiagram(getDiagramDomainElement(), getDiagramKind(), getPreferencesHint());
			if (diagram == null) {
				throw new ExecutionException("Can't create diagram of '" + getDiagramKind() + "' kind");
			}
			setDefaultNameForDiagram(diagram);
			multiDiagramFacet.getDiagramLinks().add(diagram);
			assert multiDiagramFacet.eResource() != null;
			multiDiagramFacet.eResource().getContents().add(diagram);

			if (initializeContent) {
				// Initialize diagram content
				EcoreDiagramContentInitializer initializer = new EcoreDiagramContentInitializer();
				initializer.setInitEPackageContent(false);
				initializer.initDiagramContent(diagram);

				// Layout diagram content if necessary
				if (false == diagram.getChildren().isEmpty()) {
					List<Node> nodes = new ArrayList<Node>();
					for (Object view : diagram.getChildren()) {
						if (view instanceof Node) {
							nodes.add((Node) view);
						}
					}
					LayoutService.getInstance().layoutNodes(nodes, true, LayoutType.DEFAULT);
				}
			}
			return diagram;
		}

		protected EObject getDiagramDomainElement() {
			return ((View) multiDiagramFacet.eContainer()).getElement();
		}

		protected PreferencesHint getPreferencesHint() {
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