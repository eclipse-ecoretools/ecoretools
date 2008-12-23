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
 * $Id: PackageDiagramDragDropEditPolicy.java,v 1.14 2008/12/23 11:12:38 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.diagram.edit.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.diagram.edit.commands.EcoreCreateShortcutDecorationsCommand;
import org.eclipse.emf.ecoretools.diagram.edit.commands.RestoreRelatedLinksCommand;
import org.eclipse.emf.ecoretools.diagram.edit.commands.UpdateEditPartCommand;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * Custom drag and drop edit policy that creates shorcuts only for elements
 * already present in target diagram <br>
 * creation : 01 sept. 2007
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class PackageDiagramDragDropEditPolicy extends DiagramDragDropEditPolicy {

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy#getDropCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
	 */
	@Override
	protected Command getDropCommand(ChangeBoundsRequest dropRequest) {
		List<CreateViewRequest.ViewDescriptor> shortcutViewDescriptors = new ArrayList<CreateViewRequest.ViewDescriptor>();
		List<CreateViewRequest.ViewDescriptor> normalViewDescriptors = new ArrayList<CreateViewRequest.ViewDescriptor>();
		for (Object nextPart : dropRequest.getEditParts()) {
			if (false == nextPart instanceof IGraphicalEditPart) {
				continue;
			}
			EObject nextObject = ((IGraphicalEditPart) nextPart).resolveSemanticElement();
			if (nextObject == null) {
				continue;
			}

			// Continue if element is the diagram canvas
			if (getView().getElement().equals(nextObject)) {
				continue;
			}

			// Continue if element already in diagram
			if (false == isElementInView(nextObject, dropRequest) && getView().getElement() == nextObject.eContainer()) {
				normalViewDescriptors.add(new CreateViewRequest.ViewDescriptor(new EObjectAdapter(nextObject), Node.class, null, getDiagramPreferencesHint()));
			} else {
				shortcutViewDescriptors.add(new CreateViewRequest.ViewDescriptor(new EObjectAdapter(nextObject), Node.class, null, getDiagramPreferencesHint()));
			}
		}

		Command shortcutCommand = null;
		if (false == shortcutViewDescriptors.isEmpty()) {
			shortcutCommand = createShortcutsCommand(dropRequest, shortcutViewDescriptors);
		}
		Command normalCommand = null;
		if (false == normalViewDescriptors.isEmpty()) {
			normalCommand = createNormalViewCommand(dropRequest, normalViewDescriptors);
		}
		if (shortcutCommand != null) {
			Command createBoth = shortcutCommand.chain(normalCommand);
			return createBoth;
		}

		return normalCommand;
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy#getDropObjectsCommand(org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest)
	 */
	@Override
	public Command getDropObjectsCommand(DropObjectsRequest dropRequest) {
		List<CreateViewRequest.ViewDescriptor> shortcutViewDescriptors = new ArrayList<CreateViewRequest.ViewDescriptor>();
		List<CreateViewRequest.ViewDescriptor> normalViewDescriptors = new ArrayList<CreateViewRequest.ViewDescriptor>();
		for (Object nextObject : dropRequest.getObjects()) {
			if (false == nextObject instanceof EObject) {
				continue;
			}

			// Continue if element is the diagram canvas
			if (getView().getElement().equals(nextObject)) {
				continue;
			}

			// Continue if element already in diagram
			if (false == isElementInView(nextObject, dropRequest) && getView().getElement() == ((EObject) nextObject).eContainer()) {
				normalViewDescriptors.add(new CreateViewRequest.ViewDescriptor(new EObjectAdapter((EObject) nextObject), Node.class, null, getDiagramPreferencesHint()));
			} else {
				shortcutViewDescriptors.add(new CreateViewRequest.ViewDescriptor(new EObjectAdapter((EObject) nextObject), Node.class, null, getDiagramPreferencesHint()));
			}
		}

		Command shortcutCommand = null;
		if (false == shortcutViewDescriptors.isEmpty()) {
			shortcutCommand = createShortcutsCommand(dropRequest, shortcutViewDescriptors);
		}
		Command normalCommand = null;
		if (false == normalViewDescriptors.isEmpty()) {
			normalCommand = createNormalViewCommand(dropRequest, normalViewDescriptors);
		}
		if (shortcutCommand != null) {
			Command createBoth = shortcutCommand.chain(normalCommand);
			return createBoth.chain(new ICommandProxy(new UpdateEditPartCommand(getEditingDomain(), getHost())));
		}
		if (normalCommand != null) {
			return normalCommand.chain(new ICommandProxy(new UpdateEditPartCommand(getEditingDomain(), getHost())));
		}
		return UnexecutableCommand.INSTANCE;
	}

	protected boolean isElementInView(Object nextObject, Request request) {
		@SuppressWarnings("unchecked")
		EList<View> views = getView().getChildren();
		for (View nextView : views) {
			if (nextView.getEAnnotation("Shortcut") != null) { //$NON-NLS-1$
				continue;
			}
			if (nextView.getElement() == null) {
				continue;
			}
			if (nextView.getElement().equals(nextObject)) {
				return true;
			}
		}
		return false;
	}

	private Command createShortcutsCommand(DropObjectsRequest dropRequest, List<ViewDescriptor> viewDescriptors) {
		Command command = createViews(dropRequest, viewDescriptors);
		if (command != null) {
			return command.chain(new ICommandProxy(new EcoreCreateShortcutDecorationsCommand(getEditingDomain(), getView(), viewDescriptors)));
		}
		return null;
	}

	private Command createShortcutsCommand(ChangeBoundsRequest dropRequest, List<ViewDescriptor> viewDescriptors) {
		Command command = createViews(dropRequest, viewDescriptors);
		if (command != null) {
			return command.chain(new ICommandProxy(new EcoreCreateShortcutDecorationsCommand(getEditingDomain(), getView(), viewDescriptors)));
		}
		return null;
	}

	private Command createNormalViewCommand(DropObjectsRequest dropRequest, List<ViewDescriptor> viewDescriptors) {
		return createViewsAndRestoreRelatedLinks(dropRequest, viewDescriptors);
	}

	private Command createNormalViewCommand(ChangeBoundsRequest dropRequest, List<ViewDescriptor> viewDescriptors) {
		return createViewsAndRestoreRelatedLinks(dropRequest, viewDescriptors);
	}

	protected Command createViews(DropObjectsRequest dropRequest, List<ViewDescriptor> viewDescriptors) {
		CreateViewRequest createViewRequest = new CreateViewRequest(viewDescriptors);
		createViewRequest.setLocation(dropRequest.getLocation());
		Command createCommand = getHost().getCommand(createViewRequest);

		return createCommand;
	}
	
	protected Command createViewsAndRestoreRelatedLinks(DropObjectsRequest dropRequest, List<ViewDescriptor> viewDescriptors) {
		CreateViewRequest createViewRequest = new CreateViewRequest(viewDescriptors);
		createViewRequest.setLocation(dropRequest.getLocation());
		Command createCommand = getHost().getCommand(createViewRequest);
		// Chain restore related links
		restoreRelatedLinks(createViewRequest, createCommand);

		return createCommand;
	}

	private void restoreRelatedLinks(CreateViewRequest createViewRequest, Command createCommand) {
		List<?> viewAdapters = (List<?>) createViewRequest.getNewObject();
		createCommand.chain(new ICommandProxy(new RestoreRelatedLinksCommand(getDiagramEditPart(), viewAdapters)));
	}

	private DiagramEditPart getDiagramEditPart() {
		if (getHost() instanceof DiagramEditPart) {
			return (DiagramEditPart) getHost();
		}
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (false == editorPart instanceof DiagramEditor) {
			return null;
		}
		return ((DiagramEditor) editorPart).getDiagramEditPart();
	}

	protected Command createViews(ChangeBoundsRequest dropRequest, List<ViewDescriptor> viewDescriptors) {
		CreateViewRequest createViewRequest = new CreateViewRequest(viewDescriptors);
		createViewRequest.setLocation(dropRequest.getLocation());
		Command createCommand = getHost().getCommand(createViewRequest);

		return createCommand;
	}
	
	protected Command createViewsAndRestoreRelatedLinks(ChangeBoundsRequest dropRequest, List<ViewDescriptor> viewDescriptors) {
		CreateViewRequest createViewRequest = new CreateViewRequest(viewDescriptors);
		createViewRequest.setLocation(dropRequest.getLocation());
		Command createCommand = getHost().getCommand(createViewRequest);
		// Chain restore related links
		restoreRelatedLinks(createViewRequest, createCommand);

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
