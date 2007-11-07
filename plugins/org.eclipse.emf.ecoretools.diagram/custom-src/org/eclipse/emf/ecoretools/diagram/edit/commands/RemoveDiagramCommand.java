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

package org.eclipse.emf.ecoretools.diagram.edit.commands;

import java.util.Iterator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.MultiDiagramLinkStyle;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class RemoveDiagramCommand extends AbstractTransactionalCommand {

	private MultiDiagramLinkStyle diagramFacet;

	public RemoveDiagramCommand(MultiDiagramLinkStyle linkStyle) {
		// editing domain is taken for original diagram,
		// if we open diagram from another file, we should use another editing
		// domain
		super(TransactionUtil.getEditingDomain(linkStyle), "RemoveDiagram", null);
		diagramFacet = linkStyle;
	}

	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		try {
			for (Iterator it = diagramFacet.getDiagramLinks().iterator(); it.hasNext();) {
				Diagram diagram = (Diagram) it.next();
				if (diagram != null) {
					// Close associated diagram
					URI uri = diagram.eResource().getURI();
					uri = uri.appendFragment(diagram.eResource().getURIFragment(diagram));
					IEditorInput editorInput = new URIEditorInput(uri);
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					IEditorPart editor = page.findEditor(editorInput);
					if (editor != null) {
						page.closeEditor(editor, true);
					}
					// Remove from ressource
					assert diagramFacet.eResource() != null;
					diagramFacet.eResource().getContents().remove(diagram);
				}
			}
			diagramFacet.getDiagramLinks().clear();
			return CommandResult.newOKCommandResult();
		} catch (Exception ex) {
			throw new ExecutionException("Can't remove diagram", ex);
		}
	}
}
