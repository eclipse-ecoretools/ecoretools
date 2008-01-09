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
import org.eclipse.emf.ecoretools.diagram.edit.commands.EcoreCreateShortcutDecorationsCommand;
import org.eclipse.emf.ecoretools.diagram.edit.commands.UpdateEditPartCommand;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
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
			// if (isElementInView(nextObject, dropRequest)) {
			// continue;
			// }

			// Continue if element is the diagram canvas
			if (getView().getElement().equals(nextObject)) {
				continue;
			}

			viewDescriptors.add(new CreateViewRequest.ViewDescriptor(new EObjectAdapter((EObject) nextObject), Node.class, null, getDiagramPreferencesHint()));
		}
		return createShortcutsCommand(dropRequest, viewDescriptors);
	}

	private boolean isElementInView(Object nextObject, Request request) {
		for (Iterator it = getView().getChildren().iterator(); it.hasNext();) {
			View nextView = (View) it.next();
			if (nextView.getElement() == null) {
				continue;
			}
			if (nextView.getElement().equals(nextObject)) {
				return true;
			}
		}
		return false;
	}

	private Command createShortcutsCommand(DropObjectsRequest dropRequest, List viewDescriptors) {
		Command command = createViews(dropRequest, viewDescriptors);
		if (command != null) {
			Command createShorcutCommand = command.chain(new ICommandProxy(new EcoreCreateShortcutDecorationsCommand(getEditingDomain(), getView(), viewDescriptors)));
			if (createShorcutCommand != null) {
				return createShorcutCommand.chain(new ICommandProxy(new UpdateEditPartCommand(getEditingDomain(), getHost())));
			}
			return command;
		}
		return null;
	}

	protected Command createViews(DropObjectsRequest dropRequest, List viewDescriptors) {
		CreateViewRequest createViewRequest = new CreateViewRequest(viewDescriptors);
		createViewRequest.setLocation(dropRequest.getLocation());
		Command createCommand = getHost().getCommand(createViewRequest);

		return createCommand;
	}

	private TransactionalEditingDomain getEditingDomain() {
		if (getHost() instanceof IGraphicalEditPart) {
			return ((IGraphicalEditPart) getHost()).getEditingDomain();
		}
		return null;
	}

	protected View getView() {
		return (View) getHost().getModel();
	}

	protected PreferencesHint getDiagramPreferencesHint() {
		return EcoreDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
	}
}
