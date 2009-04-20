/***********************************************************************
 * Copyright (c) 2007, 2009 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 *
 * $Id: EClassOperationsEditPart.java,v 1.5 2009/04/20 14:35:13 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.diagram.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecoretools.diagram.edit.policies.EClassOperationsCanonicalEditPolicy;
import org.eclipse.emf.ecoretools.diagram.edit.policies.EClassOperationsItemSemanticEditPolicy;
import org.eclipse.emf.ecoretools.diagram.edit.policies.EcoreCreationEditPolicy;
import org.eclipse.emf.ecoretools.diagram.part.Messages;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class EClassOperationsEditPart extends ListCompartmentEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 5002;

	/**
	 * @generated
	 */
	public EClassOperationsEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected boolean hasModelChildrenChanged(Notification evt) {
		return false;
	}

	/**
	 * @generated
	 */
	public String getCompartmentName() {
		return Messages.EClassOperationsEditPart_title;
	}

	/**
	 * @generated
	 */
	public IFigure createFigure() {
		ResizableCompartmentFigure result = (ResizableCompartmentFigure) super.createFigure();
		result.setTitleVisibility(false);
		return result;
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new ResizableCompartmentEditPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new EClassOperationsItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new EcoreCreationEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new DragDropEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE, new EClassOperationsCanonicalEditPolicy());
	}

	/**
	 * @generated
	 */
	protected void setRatio(Double ratio) {
		// nothing to do -- parent layout does not accept Double constraints as
		// ratio
		// super.setRatio(ratio);
	}

	@Override
	public boolean isSelectable() {
		// Bug 238814 : Compartment must not be selectable
		return false;
	}
}
