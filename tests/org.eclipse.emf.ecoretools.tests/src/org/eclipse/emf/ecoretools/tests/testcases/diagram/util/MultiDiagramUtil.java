/***********************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 * 
 * $Id: MultiDiagramUtil.java,v 1.3 2008/04/28 12:38:09 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.tests.testcases.diagram.util;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecoretools.diagram.edit.policies.OpenDiagramEditPolicy.OpenDiagramCommand;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.MultiDiagramLinkStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

/**
 * Some useful methods to handle multi diagram file
 * 
 * Updated 14 feb. 08
 * 
 * @author sbernard
 * @author <a href="mailto:jacques.lescot@anyware-tech.com">Jacques LESCOT</a>
 */
public class MultiDiagramUtil {

	private static class OpenDiagramTestCommand extends OpenDiagramCommand {

		/**
		 * Constructor with a MultiDiagramLinkStyle as input
		 * 
		 * @param multiDiagramLinkStyle
		 *            the MultiDiagramLinkStyle
		 */
		public OpenDiagramTestCommand(MultiDiagramLinkStyle multiDiagramLinkStyle) {
			super(((View) multiDiagramLinkStyle.eContainer()).getElement(), multiDiagramLinkStyle.eResource());
		}

		/**
		 * Constructor
		 * 
		 * @param domainElt
		 *            the model element
		 * @param diagResource
		 *            the Resource associated with the diagram file
		 */
		public OpenDiagramTestCommand(EObject domainElt, Resource diagResource) {
			super(domainElt, diagResource);
		}

		/**
		 * @see org.eclipse.emf.ecoretools.diagram.edit.policies.OpenDiagramEditPolicy.OpenDiagramCommand#createPressed(boolean)
		 */
		public Diagram createPressed(boolean initializedContext) {
			return super.createPressed(initializedContext);
		}

		/**
		 * @see org.eclipse.emf.ecoretools.diagram.edit.policies.OpenDiagramEditPolicy.OpenDiagramCommand#openEditor(org.eclipse.gmf.runtime.notation.Diagram)
		 */
		public void openEditor(Diagram diagram) {
			super.openEditor(diagram);
		}

		/**
		 * @see org.eclipse.emf.ecoretools.diagram.edit.policies.OpenDiagramEditPolicy.OpenDiagramCommand#deletePressed(org.eclipse.gmf.runtime.notation.Diagram)
		 */
		public void deletePressed(Diagram diagram) {
			super.deletePressed(diagram);
		}

	}

	private static MultiDiagramLinkStyle getMultiDiagramLinkStyleFromEditPart(EditPart target) {
		if (false == target.getModel() instanceof View) {
			return null;
		}
		View view = (View) target.getModel();
		Style link = view.getStyle(NotationPackage.eINSTANCE.getMultiDiagramLinkStyle());
		if (false == link instanceof MultiDiagramLinkStyle) {
			return null;
		}
		return (MultiDiagramLinkStyle) link;
	}

	/**
	 * create a diagram in the given editPart
	 * 
	 * @param targetEditPart
	 *            EditPart on which you want create a diagram
	 * @param initializedContent
	 *            if true, initialized the content of the diagram with the
	 *            contain of the targetEditPart
	 * @return Diagram the newly created Diagram
	 */
	public static Diagram createDiagram(EditPart targetEditPart, boolean initializedContent) {
		final OpenDiagramTestCommand command = new OpenDiagramTestCommand(getMultiDiagramLinkStyleFromEditPart(targetEditPart));

		return command.createPressed(initializedContent);
	}

	/**
	 * Close the editor part associated with the current diagram
	 * 
	 * @param diagram
	 *            the Diagram to close
	 */
	public static void closeDiagram(Diagram diagram) {
		if (diagram != null) {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

			page.closeEditor(getEditorPart(diagram), false);
		}
	}

	/**
	 * save the editor which edit the given diagram
	 * 
	 * @param diagram
	 */
	public static void saveDiagram(Diagram diagram) {
		if (diagram != null) {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

			page.saveEditor(getEditorPart(diagram), false);
		}
	}

	/**
	 * open an editor to edit the given diagram
	 * 
	 * @param diagram
	 */
	public static void openDiagram(Diagram diagram) {
		OpenDiagramTestCommand Command = new OpenDiagramTestCommand(diagram.getElement(), diagram.eResource());

		Command.openEditor(diagram);
	}

	/**
	 * delete the diagram on the given editPart
	 * 
	 * @param diagram
	 * @param targetEditPart
	 */
	public static void deleteDiagram(Diagram diagram, EditPart targetEditPart) {
		OpenDiagramTestCommand command = new OpenDiagramTestCommand(getMultiDiagramLinkStyleFromEditPart(targetEditPart));

		command.deletePressed(diagram);
	}

	/**
	 * Return the EcoreDiagramEditor of the diagram
	 * 
	 * @param diagram
	 * @return EcoreDiagramEditor
	 */
	public static EcoreDiagramEditor getEditorPart(Diagram diagram) {
		if (diagram != null) {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

			for (IEditorReference editorRef : page.getEditorReferences()) {
				IEditorPart editor = editorRef.getEditor(false);
				if (editor instanceof EcoreDiagramEditor) {
					EcoreDiagramEditor ecoreEditor = (EcoreDiagramEditor) editor;
					if (ecoreEditor.getDiagram() == diagram) {
						return ecoreEditor;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Get all the diagram link to this editPart
	 * 
	 * @param targetEditPart
	 * @return List the list of Diagram elements
	 */
	public static List<Diagram> getDiagramChildren(EditPart targetEditPart) {
		MultiDiagramLinkStyle style = getMultiDiagramLinkStyleFromEditPart(targetEditPart);
		return style.getDiagramLinks();
	}
}