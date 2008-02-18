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
 **********************************************************************/

package org.eclipse.emf.ecoretools.diagram.outline;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageEditPart;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.gmf.runtime.diagram.ui.outline.AbstractModelNavigator;
import org.eclipse.gmf.runtime.diagram.ui.outline.IOutlineMenuConstants;
import org.eclipse.gmf.runtime.diagram.ui.outline.actions.CreateDiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.outline.actions.DeleteDiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.outline.actions.RenameDiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.IPageSite;

/**
 * A navigator that gives a model-oriented view in the outline.
 * 
 * @author <a href="mailto:david.sciamma@anyware-tech.com">David Sciamma</a>
 * @author <a href="mailto:jacques.lescot@anyware-tech.com">Jacques LESCOT</a>
 */
public class EcoreModelNavigator extends AbstractModelNavigator {

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            the parent Composite
	 * @param diagEditor
	 *            the diagram editor
	 * @param pageSite
	 *            the IPageSite
	 */
	public EcoreModelNavigator(Composite parent, IDiagramWorkbenchPart diagEditor, IPageSite pageSite) {
		super(parent, diagEditor, pageSite);
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.outline.AbstractModelNavigator#getAdapterFactory()
	 */
	@Override
	protected AdapterFactory getAdapterFactory() {
		return EcoreDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory();
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.outline.AbstractModelNavigator#createDiagramsMenu(org.eclipse.jface.action.IMenuManager,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void createDiagramsMenu(IMenuManager manager, EObject selectedObject) {
		if (selectedObject instanceof EPackage) {
			manager.appendToGroup(IOutlineMenuConstants.NEW_GROUP, new CreateDiagramAction(selectedObject, getDiagramResource(), EPackageEditPart.MODEL_ID,
					EcoreDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT));
		}
		if (selectedObject instanceof Diagram) {
			manager.appendToGroup(IOutlineMenuConstants.NEW_GROUP, new RenameDiagramAction((Diagram) selectedObject));
			// Check that this is not the current diagram
			if (getEditor().getDiagram() != selectedObject) {
				manager.appendToGroup(IOutlineMenuConstants.NEW_GROUP, new DeleteDiagramAction((Diagram) selectedObject));
			}
		}
	}

}
