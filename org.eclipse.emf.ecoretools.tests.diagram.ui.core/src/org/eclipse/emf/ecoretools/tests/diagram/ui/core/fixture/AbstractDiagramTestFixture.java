/***********************************************************************
 * Copyright (c) 2008 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 **********************************************************************/

package org.eclipse.emf.ecoretools.tests.diagram.ui.core.fixture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.tests.diagram.ui.core.bypassedclasses.DeleteFromDiagramBypassedAction;
import org.eclipse.emf.ecoretools.tests.diagram.ui.core.bypassedclasses.DeleteFromModelBypassedAction;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.dnd.DND;

/**
 * An AbstractPresentationFixture with common methods which makes test writing
 * easier
 * 
 * @author Simon Bernard
 * @author choang
 */
public abstract class AbstractDiagramTestFixture extends AbstractPresentationTestFixture {

	/** Executes the supplied command. */
	protected Collection execute(ICommand cmd) {
		ICommandProxy command = new ICommandProxy(cmd);
		execute(command);
		return DiagramCommandStack.getReturnValues(command);
	}

	/** Executes the supplied command. */
	public void execute(Command cmd) {
		getCommandStack().execute(cmd);
	}

	/**
	 * Given an <code>IElementType</code>, gets the creation request that can
	 * be used to retrieve the command to creation the element for the type.
	 * 
	 * @param elementType
	 * @return
	 */
	public CreateRequest getCreationRequest(IElementType elementType) {
		class CreationTool extends org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool {

			public CreationTool(IElementType theElementType) {
				super(theElementType);
			}

			/** Make public. */
			public Request createTargetRequest() {
				return super.createTargetRequest();
			}

			protected PreferencesHint getPreferencesHint() {
				return PreferencesHint.USE_DEFAULTS;
			}
		}

		CreationTool tool = new CreationTool(elementType);
		CreateRequest request = (CreateRequest) tool.createTargetRequest();
		return request;
	}

	/**
	 * Creates a new shape using the request created by the
	 * <code>CreationTool</code>.
	 * 
	 * @param elementType
	 *            the type of the shape/element to be created
	 * @param location
	 *            the location for the new shape
	 * @return the new shape's editpart
	 */
	public ShapeEditPart createShapeUsingTool(IElementType elementType, Point location, Dimension size, IGraphicalEditPart containerEP) {

		CreateRequest request = getCreationRequest(elementType);
		request.setLocation(location);
		if (size != null) {
			request.setSize(size);
		}
		Command cmd = containerEP.getCommand(request);

		int previousNumChildren = containerEP.getChildren().size();

		getCommandStack().execute(cmd);
		assertEquals(previousNumChildren + 1, containerEP.getChildren().size());

		Object newView = ((IAdaptable) ((List<Object>) request.getNewObject()).get(0)).getAdapter(View.class);
		assertNotNull(newView);
		assertTrue(!ViewUtil.isTransient((View) newView));

		EObject element = ((View) newView).getElement();

		getCommandStack().undo();
		assertEquals(previousNumChildren, containerEP.getChildren().size());

		getCommandStack().redo();
		assertEquals(previousNumChildren + 1, containerEP.getChildren().size());

		IGraphicalEditPart newShape = null;
		if (element != null) {
			List<EditPart> children = containerEP.getChildren();
			ListIterator<EditPart> li = children.listIterator();
			while (li.hasNext()) {
				IGraphicalEditPart gep = (IGraphicalEditPart) li.next();
				if (gep.getNotationView() != null && element.equals(gep.getNotationView().getElement())) {
					newShape = gep;
				}
			}
		} else {
			newShape = (ShapeEditPart) getDiagramEditPart().getViewer().getEditPartRegistry().get(newView);
			assertNotNull(newShape);
		}

		assertTrue(newShape != null && newShape instanceof ShapeEditPart);
		return (ShapeEditPart) newShape;
	}

	/**
	 * Creates a new shape using the request created by the
	 * <code>CreationTool</code>.
	 * 
	 * @param elementType
	 *            the type of the shape/element to be created
	 * @param location
	 *            the location for the new shape
	 * @return the new shape's editpart
	 */
	public ShapeEditPart createShapeUsingTool(IElementType elementType, Point location) {

		return createShapeUsingTool(elementType, location, getDiagramEditPart());

	}

	/**
	 * Creates a new shape using the request created by the
	 * <code>CreationTool</code>.
	 * 
	 * @param elementType
	 *            the type of the shape/element to be created
	 * @param location
	 *            the location for the new shape
	 * @return the new shape's editpart
	 */
	public ShapeEditPart createShapeUsingTool(IElementType elementType, Point location, Dimension size) {

		return createShapeUsingTool(elementType, location, size, getDiagramEditPart());

	}

	/**
	 * Creates a new shape using the request created by the
	 * <code>CreationTool</code>.
	 * 
	 * @param elementType
	 *            the type of the shape/element to be created
	 * @param location
	 *            the location for the new shape
	 * @param containerEP
	 *            the editpart in which the new shape will be added
	 * @return the new shape's editpart
	 */
	public ShapeEditPart createShapeUsingTool(IElementType elementType, Point location, IGraphicalEditPart containerEP) {
		return createShapeUsingTool(elementType, location, null, containerEP);
	}

	/**
	 * Creates a new connector using the request created by the
	 * <code>ConnectionCreationTool</code>.
	 * 
	 * @param sourceEditPart
	 *            the new connector's source
	 * @param targetEditPart
	 *            the new connector's target
	 * @param elementType
	 *            the type of the connector/relationship to be created
	 * @return the new connector's editpart
	 */
	public ConnectionEditPart createConnectorUsingTool(final IGraphicalEditPart sourceEditPart, final IGraphicalEditPart targetEditPart, IElementType elementType) {

		class ConnectorCreationTool extends org.eclipse.gmf.runtime.diagram.ui.tools.ConnectionCreationTool {

			public ConnectorCreationTool(IElementType theElementType) {
				super(theElementType);
			}

			/** Make public. */
			public Request createTargetRequest() {
				return super.createTargetRequest();
			}

			protected PreferencesHint getPreferencesHint() {
				return PreferencesHint.USE_DEFAULTS;
			}
		}

		ConnectorCreationTool tool = new ConnectorCreationTool(elementType);
		CreateConnectionRequest request = (CreateConnectionRequest) tool.createTargetRequest();
		request.setTargetEditPart(sourceEditPart);
		request.setType(RequestConstants.REQ_CONNECTION_START);
		sourceEditPart.getCommand(request);
		request.setSourceEditPart(sourceEditPart);
		request.setTargetEditPart(targetEditPart);
		request.setType(RequestConstants.REQ_CONNECTION_END);
		Command cmd = targetEditPart.getCommand(request);

		int previousNumConnectors = getDiagramEditPart().getConnections().size();

		getCommandStack().execute(cmd);
		assertEquals(previousNumConnectors + 1, getDiagramEditPart().getConnections().size());
		getCommandStack().undo();
		assertEquals(previousNumConnectors, getDiagramEditPart().getConnections().size());
		getCommandStack().redo();
		assertEquals(previousNumConnectors + 1, getDiagramEditPart().getConnections().size());

		Object newView = ((IAdaptable) request.getNewObject()).getAdapter(View.class);
		assertNotNull(newView);

		ConnectionEditPart newConnector = (ConnectionEditPart) getDiagramEditPart().getViewer().getEditPartRegistry().get(newView);
		assertNotNull(newConnector);

		return newConnector;
	}

	/**
	 * Drop an object on a given editpart à a given location
	 * 
	 * @param target
	 * @param location
	 * @param objectBeingDropped
	 */
	public void dropObject(EditPart target, Point location, Object objectBeingDropped) {
		List<Object> objectsBeingDropped = new ArrayList<Object>();
		objectsBeingDropped.add(objectBeingDropped);
		dropObject(target, location, objectsBeingDropped);
	}

	/**
	 * Drop objects on a given editpart à a given location
	 * 
	 * @param target
	 * @param location
	 * @param objectBeingDropped
	 */
	public void dropObject(EditPart target, Point location, List<Object> objectsBeingDropped) {
		DropObjectsRequest request = new DropObjectsRequest();

		request.setObjects(objectsBeingDropped);
		request.setAllowedDetail(DND.DROP_MOVE);
		request.setLocation(location);

		Command command = target.getCommand(request);
		if (command != null && command.canExecute()) {
			command.execute();
		}
	}

	/**
	 * Execute a deleteFromModelAction on a given editpart
	 */
	public void deleteFromModel(final EditPart toDelete) {
		DeleteFromModelBypassedAction action = new DeleteFromModelBypassedAction(getDiagramWorkbenchPart(), toDelete);
		execute(action.getCommand());
	}

	/**
	 * Execute a deleteFromDiagramAction on a given editpart
	 */
	public void deleteFromDiagram(final EditPart toDelete) {
		DeleteFromDiagramBypassedAction action = new DeleteFromDiagramBypassedAction(getDiagramWorkbenchPart(), toDelete);
		execute(action.getCommand());
	}

	/**
	 * return true if the editpart is a shortcut
	 */
	public boolean isShortcut(EditPart editpart) {
		View view = (View) editpart.getModel();
		return view.getEAnnotation("Shortcut") != null; //$NON-NLS-1$
	}

}
