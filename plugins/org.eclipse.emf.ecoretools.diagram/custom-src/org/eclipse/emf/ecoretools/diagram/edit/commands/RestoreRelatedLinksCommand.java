/**
 * Copyright (c) 2008 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 */
package org.eclipse.emf.ecoretools.diagram.edit.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.resources.LinkDescription;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EClass2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EClassEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EDataType2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EDataTypeEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EEnum2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EEnumEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackage2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EReferenceEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EReferenceUtils;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramUpdater;
import org.eclipse.emf.ecoretools.diagram.part.EcoreLinkDescriptor;
import org.eclipse.emf.ecoretools.diagram.part.EcoreVisualIDRegistry;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.commands.SetPropertyCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.internal.properties.Properties;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * 
 * Restores the outgoing or incoming links for the selected parts <br>
 * creation : 15 avr. 2008
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class RestoreRelatedLinksCommand extends AbstractTransactionalCommand {

	protected List<IGraphicalEditPart> parts;

	protected Diagram diagram;

	protected DiagramEditPart host;

	public RestoreRelatedLinksCommand(DiagramEditPart diagramEditPart, List<IGraphicalEditPart> selection) {
		super(diagramEditPart.getEditingDomain(), "Restore Related Links", null);
		this.host = diagramEditPart;
		this.diagram = host.getDiagramView();
		this.parts = selection;
	}

	/**
	 * 
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor,
	 *      org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		for (IGraphicalEditPart part : parts) {
			refreshRelatedLinks(part);
		}
		return CommandResult.newOKCommandResult();
	}

	/**
	 * Create related links corresponding to linkDescriptions
	 * 
	 * @param part
	 * @param linkDescriptors
	 * @param domain2NotationMap
	 */
	protected void createRelatedLinks(IGraphicalEditPart part, Collection linkDescriptors, Map domain2NotationMap) {
		// map diagram
		mapModel(diagram, domain2NotationMap);

		for (Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator.hasNext();) {
			final EcoreLinkDescriptor nextLinkDescriptor = (EcoreLinkDescriptor) linkDescriptorsIterator.next();
			EditPart sourceEditPart = getEditPart(nextLinkDescriptor.getSource(), domain2NotationMap);
			EditPart targetEditPart = getEditPart(nextLinkDescriptor.getDestination(), domain2NotationMap);

			// If the parts are still null...
			if (sourceEditPart == null || targetEditPart == null) {
				continue;
			}

			CreateConnectionViewRequest.ConnectionViewDescriptor descriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(nextLinkDescriptor.getSemanticAdapter(), null, ViewUtil.APPEND,
					false, part.getDiagramPreferencesHint());
			CreateConnectionViewRequest ccr = new CreateConnectionViewRequest(descriptor);
			ccr.setType(RequestConstants.REQ_CONNECTION_START);
			ccr.setSourceEditPart(sourceEditPart);
			sourceEditPart.getCommand(ccr);
			ccr.setTargetEditPart(targetEditPart);
			ccr.setType(RequestConstants.REQ_CONNECTION_END);
			Command cmd = targetEditPart.getCommand(ccr);
			if (cmd != null && cmd.canExecute()) {
				EReferenceUtils.executeCommand(cmd, part);
			}
		}
	}

	/**
	 * Retrieves editpart corresponding to domainModelElement
	 * 
	 * @param domainModelElement
	 * @param domain2NotationMap
	 */
	protected EditPart getEditPart(EObject domainModelElement, Map domain2NotationMap) {
		View view = (View) domain2NotationMap.get(domainModelElement);
		if (view != null) {
			return (EditPart) host.getViewer().getEditPartRegistry().get(view);
		}
		return null;
	}

	/**
	 * Refresh related links for graphicalEditPart
	 * 
	 * @param graphicalEditPart
	 */
	protected void refreshRelatedLinks(IGraphicalEditPart graphicalEditPart) {
		Map domain2NotationMap = new HashMap();

		// Create related links
		Collection linkDescriptors = getLinkDescriptorToProcess(graphicalEditPart, domain2NotationMap);
		createRelatedLinks(graphicalEditPart, linkDescriptors, domain2NotationMap);
	}

	/**
	 * Get linkdescriptors of the related links for graphicalEditPart
	 * 
	 * @param graphicalEditPart
	 * @param domain2NotationMap
	 * 
	 * @return linkDescritors
	 */
	protected Collection getLinkDescriptorToProcess(IGraphicalEditPart graphicalEditPart, Map domain2NotationMap) {
		View notationView = graphicalEditPart.getNotationView();

		// Collect all related link from semantic model
		Collection linkDescriptors = collectPartRelatedLinks(notationView, domain2NotationMap);

		// Collect all related link from graphical model
		Collection existingLinks = new LinkedList();
		for (Object edge : notationView.getTargetEdges()) {
			if (edge instanceof Edge && false == existingLinks.contains(edge)) {
				existingLinks.add(edge);
			}
		}
		for (Object edge : notationView.getSourceEdges()) {
			if (edge instanceof Edge && false == existingLinks.contains(edge)) {
				existingLinks.add(edge);
			}
		}

		// Set all existing related link visible
		setConnectionsVisible(graphicalEditPart, existingLinks);

		// Remove already existing links
		for (Iterator linksIterator = existingLinks.iterator(); linksIterator.hasNext();) {
			Edge nextDiagramLink = (Edge) linksIterator.next();
			int diagramLinkVisualID = EcoreVisualIDRegistry.getVisualID(nextDiagramLink);
			if (diagramLinkVisualID == -1) {
				if (nextDiagramLink.getSource() != null && nextDiagramLink.getTarget() != null) {
					linksIterator.remove();
				}
				continue;
			}
			EObject diagramLinkObject = nextDiagramLink.getElement();
			EObject diagramLinkSrc = nextDiagramLink.getSource().getElement();
			EObject diagramLinkDst = nextDiagramLink.getTarget().getElement();
			for (Iterator LinkDescriptorsIterator = linkDescriptors.iterator(); LinkDescriptorsIterator.hasNext();) {
				EcoreLinkDescriptor nextLinkDescriptor = (EcoreLinkDescriptor) LinkDescriptorsIterator.next();
				if (diagramLinkObject == nextLinkDescriptor.getModelElement() && diagramLinkSrc == nextLinkDescriptor.getSource() && diagramLinkDst == nextLinkDescriptor.getDestination()
						&& diagramLinkVisualID == nextLinkDescriptor.getVisualID()) {
					linksIterator.remove();
					LinkDescriptorsIterator.remove();
				}
			}
		}
		return linkDescriptors;
	}

	/**
	 * Set links visible
	 * 
	 * @param part
	 * @param existingLinks
	 */
	protected void setConnectionsVisible(IGraphicalEditPart part, Collection existingLinks) {
		for (Iterator it = existingLinks.iterator(); it.hasNext();) {
			Edge edge = (Edge) it.next();
			if (edge.isVisible()) {
				continue;
			}
			SetPropertyCommand cmd = new SetPropertyCommand(part.getEditingDomain(), "Hide Link", new EObjectAdapter((View) edge), Properties.ID_ISVISIBLE, Boolean.TRUE);
			if (cmd != null && cmd.canExecute()) {
				EReferenceUtils.executeCommand(new ICommandProxy(cmd), part);
			}
		}
	}

	/**
	 * Collects all related links for view
	 * 
	 * @param view
	 * @param domain2NotationMap
	 * 
	 * @return linkdescriptors
	 */
	protected Collection collectPartRelatedLinks(View view, Map domain2NotationMap) {
		Collection result = new LinkedList();

		switch (EcoreVisualIDRegistry.getVisualID(view)) {
		case EPackageEditPart.VISUAL_ID:
		case EClassEditPart.VISUAL_ID:
		case EPackage2EditPart.VISUAL_ID:
		case EAnnotationEditPart.VISUAL_ID:
		case EDataTypeEditPart.VISUAL_ID:
		case EEnumEditPart.VISUAL_ID:
		case EClass2EditPart.VISUAL_ID:
		case EDataType2EditPart.VISUAL_ID:
		case EEnum2EditPart.VISUAL_ID:
		case EReferenceEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement())) {
				// result.addAll(EcoreDiagramUpdater.getContainedLinks(view));
				// We must prevent duplicate descriptors
				List outgoingDescriptors = EcoreDiagramUpdater.getOutgoingLinks(view);
				cleanAdd(result, outgoingDescriptors);

				List incomingDescriptors = EcoreDiagramUpdater.getIncomingLinks(view);
				cleanAdd(result, incomingDescriptors);
			}
			if (!domain2NotationMap.containsKey(view.getElement()) || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		}

		return result;
	}

	private void cleanAdd(Collection result, List descriptors) {
		for (Object object : descriptors) {
			if (false == object instanceof EcoreLinkDescriptor) {
				continue;
			}
			EcoreLinkDescriptor descriptor = (EcoreLinkDescriptor) object;
			if (cleanContains(result, descriptor)) {
				continue;
			}
			result.add(descriptor);
		}
	}

	private boolean cleanContains(Collection result, EcoreLinkDescriptor eCoreLinkDescriptor) {
		for (Object object : result) {
			if (false == object instanceof EcoreLinkDescriptor) {
				continue;
			}
			EcoreLinkDescriptor descriptor = (EcoreLinkDescriptor) object;
			if (descriptor.getModelElement() == eCoreLinkDescriptor.getModelElement() && descriptor.getSource() == eCoreLinkDescriptor.getSource()
					&& descriptor.getDestination() == eCoreLinkDescriptor.getDestination() && descriptor.getVisualID() == eCoreLinkDescriptor.getVisualID()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Maps view
	 * 
	 * @param view
	 * @param domain2NotationMap
	 */
	protected void mapModel(View view, Map domain2NotationMap) {
		if (!EPackageEditPart.MODEL_ID.equals(EcoreVisualIDRegistry.getModelID(view))) {
			return;
		}

		switch (EcoreVisualIDRegistry.getVisualID(view)) {
		case EPackageEditPart.VISUAL_ID:
		case EClassEditPart.VISUAL_ID:
		case EPackage2EditPart.VISUAL_ID:
		case EAnnotationEditPart.VISUAL_ID:
		case EDataTypeEditPart.VISUAL_ID:
		case EEnumEditPart.VISUAL_ID:
		case EClass2EditPart.VISUAL_ID:
		case EDataType2EditPart.VISUAL_ID:
		case EEnum2EditPart.VISUAL_ID:
		case EReferenceEditPart.VISUAL_ID: {
			if (!domain2NotationMap.containsKey(view.getElement()) || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		}

		for (Iterator children = view.getChildren().iterator(); children.hasNext();) {
			mapModel((View) children.next(), domain2NotationMap);
		}
		for (Iterator edges = view.getSourceEdges().iterator(); edges.hasNext();) {
			mapModel((View) edges.next(), domain2NotationMap);
		}
	}

}
