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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.diagram.edit.commands.UpdateEditPartCommand;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

public class PackageDiagramDragDropEditPolicy extends DiagramDragDropEditPolicy {

	public Command getDropObjectsCommand(DropObjectsRequest dropRequest) {
		List viewDescriptors = new ArrayList();
		for (Iterator it = dropRequest.getObjects().iterator(); it.hasNext();) {
			Object nextObject = it.next();
			if (false == nextObject instanceof EObject) {
				continue;
			}
			// Continue if element already in diagram
			if (isElementInDiagram(nextObject, dropRequest)) {
				continue;
			}
			viewDescriptors.add(new CreateViewRequest.ViewDescriptor(new EObjectAdapter((EObject) nextObject), Node.class, null, getDiagramPreferencesHint()));
		}
		return createShortcutsCommand(dropRequest, viewDescriptors);
	}

	private boolean isElementInDiagram(Object nextObject, Request request) {
		if (getView(request).getDiagram().getElement().equals(nextObject)) {
			return true;
		}
		for (Iterator it = getView(request).getDiagram().getChildren().iterator(); it.hasNext();) {
			View nextView = (View) it.next();
			if(nextView.getElement() == null)
			{
				continue;
			}
			if (nextView.getElement().equals(nextObject)) {
				return true;
			}
		}
		return false;
	}

	private Command createShortcutsCommand(DropObjectsRequest dropRequest, List viewDescriptors) {
		Command command = createViewsAndArrangeCommand(dropRequest, viewDescriptors);
		if (command instanceof CompoundCommand) {
			((CompoundCommand)command).add(new ICommandProxy(new UpdateEditPartCommand(getEditingDomain(), getHost())));
			
			return command;
		}
		return null;
	}

	private TransactionalEditingDomain getEditingDomain() {
		if (getHost() instanceof IGraphicalEditPart)
		{
			return ((IGraphicalEditPart)getHost()).getEditingDomain();
		}
		return null;
	}

	protected View getView(Request request) {
		EditPart targetEditPart = getTargetEditPart(request);
		return (View) targetEditPart.getModel();
	}

	protected PreferencesHint getDiagramPreferencesHint() {
		return EcoreDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
	}
}
