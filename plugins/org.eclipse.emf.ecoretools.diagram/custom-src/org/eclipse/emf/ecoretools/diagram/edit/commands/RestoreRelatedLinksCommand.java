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
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * 
 * Restores the outgoing or ingoing links for the selected parts <br>
 * creation : 15 avr. 2008
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class RestoreRelatedLinksCommand extends AbstractTransactionalCommand {

	private List<IGraphicalEditPart> parts;

	private Diagram diagram;

	private DiagramEditPart host;

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
			refreshConnections(part);
		}
		return CommandResult.newOKCommandResult();
	}

	private void createConnections(IGraphicalEditPart part, Collection linkDescriptors, Map domain2NotationMap) {
		// map elements
		mapModel(diagram, domain2NotationMap);

		for (Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator.hasNext();) {
			final EcoreLinkDescriptor nextLinkDescriptor = (EcoreLinkDescriptor) linkDescriptorsIterator.next();
			EditPart sourceEditPart = getEditPart(nextLinkDescriptor.getSource(), domain2NotationMap);
			EditPart targetEditPart = getEditPart(nextLinkDescriptor.getDestination(), domain2NotationMap);
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
	 * Taken from the EPackageCanonicalEditPolicy class
	 */
	private EditPart getEditPart(EObject domainModelElement, Map domain2NotationMap) {
		View view = (View) domain2NotationMap.get(domainModelElement);
		if (view != null) {
			return (EditPart) host.getViewer().getEditPartRegistry().get(view);
		}
		return null;
	}

	private void refreshConnections(IGraphicalEditPart graphicalEditPart) {
		Map domain2NotationMap = new HashMap();

		View notationView = graphicalEditPart.getNotationView();

		// Collect all related link from semantic model
		Collection linkDescriptors = collectPartRelatedLinks(notationView, domain2NotationMap);

		// Collect all related link from graphical model
		Collection existingLinks = new LinkedList();
		for (Object edge : notationView.getTargetEdges()) {
			if (edge instanceof Edge) {
				existingLinks.add(edge);
			}
		}
		for (Object edge : notationView.getSourceEdges()) {
			if (edge instanceof Edge) {
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

		// Create connection for all semantic link not yet in graphical model
		createConnections(graphicalEditPart, linkDescriptors, domain2NotationMap);
	}

	private void setConnectionsVisible(IGraphicalEditPart part, Collection existingLinks) {
		for (Iterator it = existingLinks.iterator(); it.hasNext();) {
			Edge edge = (Edge) it.next();
			if (edge.isVisible()) {
				continue;
			}
			SetPropertyCommand cmd = new SetPropertyCommand(part.getEditingDomain(), DiagramUIMessages.Command_hideLabel_Label, new EObjectAdapter((View) edge), Properties.ID_ISVISIBLE, Boolean.TRUE);
			if (cmd != null && cmd.canExecute()) {
				EReferenceUtils.executeCommand(new ICommandProxy(cmd), part);
			}
		}
	}

	private Collection collectPartRelatedLinks(View view, Map domain2NotationMap) {
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
				result.addAll(EcoreDiagramUpdater.getOutgoingLinks(view));
				result.addAll(EcoreDiagramUpdater.getIncomingLinks(view));
			}
			if (!domain2NotationMap.containsKey(view.getElement()) || view.getEAnnotation("Shortcut") == null) { //$NON-NLS-1$
				domain2NotationMap.put(view.getElement(), view);
			}
			break;
		}
		}

		return result;
	}

	private void mapModel(View view, Map domain2NotationMap) {
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
