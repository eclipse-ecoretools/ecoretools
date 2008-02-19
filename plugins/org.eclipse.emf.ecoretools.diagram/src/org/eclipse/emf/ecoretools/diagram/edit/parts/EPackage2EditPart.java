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

package org.eclipse.emf.ecoretools.diagram.edit.parts;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecoretools.diagram.edit.figures.FigureFromLabelUtils;
import org.eclipse.emf.ecoretools.diagram.edit.figures.PackageLabelRectangle;
import org.eclipse.emf.ecoretools.diagram.edit.policies.EPackage2ItemSemanticEditPolicy;
import org.eclipse.emf.ecoretools.diagram.edit.policies.OpenDiagramEditPolicy;
import org.eclipse.emf.ecoretools.diagram.part.EcoreVisualIDRegistry;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class EPackage2EditPart extends ShapeNodeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 1002;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public EPackage2EditPart(View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {

		super.createDefaultEditPolicies();
		// // To manage delete from diagram when there is an associated diagram
		// with the package
		// installEditPolicy(EditPolicy.COMPONENT_ROLE,
		// new PackageComponentEditPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new EPackage2ItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		installEditPolicy(EditPolicyRoles.OPEN_ROLE, new OpenDiagramEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that
		// would let children add reasonable editpolicies
		removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		LayoutEditPolicy lep = new LayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}

			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		PackageFigure figure = new PackageFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public PackageFigure getPrimaryShape() {
		return (PackageFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof EPackageNameEditPart) {
			((EPackageNameEditPart) childEditPart).setLabel(getPrimaryShape().getFigurePackageNameLabel());
			return true;
		}
		if (childEditPart instanceof EPackageContentsEditPart) {
			IFigure pane = getPrimaryShape().getFigurePackageBodyRectangle();
			setupContentPane(pane); // FIXME each comparment should handle his
									// content pane in his own way
			pane.add(((EPackageContentsEditPart) childEditPart).getFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {

		if (childEditPart instanceof EPackageContentsEditPart) {
			IFigure pane = getPrimaryShape().getFigurePackageBodyRectangle();
			setupContentPane(pane); // FIXME each comparment should handle his
									// content pane in his own way
			pane.remove(((EPackageContentsEditPart) childEditPart).getFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {

		if (editPart instanceof EPackageContentsEditPart) {
			return getPrimaryShape().getFigurePackageBodyRectangle();
		}
		return super.getContentPaneFor(editPart);
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(getMapMode().DPtoLP(40), getMapMode().DPtoLP(40));
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model so
	 * you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane. Respects
	 * layout one may have set for generated figure.
	 * 
	 * @param nodeShape
	 *            instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(getMapMode().DPtoLP(5));
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(EcoreVisualIDRegistry.getType(EPackageNameEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	protected void handleNotificationEvent(Notification event) {
		if (event.getNotifier() == getModel() && EcorePackage.eINSTANCE.getEModelElement_EAnnotations().equals(event.getFeature())) {
			handleMajorSemanticChange();
		} else {
			super.handleNotificationEvent(event);
		}
	}

	@Override
	protected void refreshVisuals() {
		EObject semanticElement = resolveSemanticElement();
		if (FigureFromLabelUtils.needFromLabel(semanticElement, getNotationView())) {
			getPrimaryShape().updateFromLabel(FigureFromLabelUtils.getQualifiedName(semanticElement));
			getPrimaryShape().addFromLabel();
		} else {
			getPrimaryShape().removeFromLabel();
		}
		super.refreshVisuals();
	}

	/**
	 * @generated
	 */
	public class PackageFigure extends RectangleFigure {

		/**
		 * @generated
		 */
		private RectangleFigure fFigurePackageBodyRectangle;

		/**
		 * @generated
		 */
		private WrappingLabel fFigurePackageNameLabel;

		private RectangleFigure packageLabelRectangle0;

		// private PackageLabelRectangle packageLabelRectangle0;

		/**
		 * @generated
		 */
		public PackageFigure() {

			GridLayout layoutThis = new GridLayout();
			layoutThis.numColumns = 2;
			layoutThis.makeColumnsEqualWidth = false;
			layoutThis.horizontalSpacing = 0;
			layoutThis.verticalSpacing = 0;
			layoutThis.marginWidth = 0;
			layoutThis.marginHeight = 0;
			this.setLayoutManager(layoutThis);

			this.setFill(false);
			this.setOutline(false);
			this.setLineWidth(2);
			this.setMinimumSize(new Dimension(getMapMode().DPtoLP(110), getMapMode().DPtoLP(100)));
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			packageLabelRectangle0 = new PackageLabelRectangle();
			packageLabelRectangle0.setLineWidth(2);

			packageLabelRectangle0.setBorder(new MarginBorder(getMapMode().DPtoLP(5), getMapMode().DPtoLP(5), getMapMode().DPtoLP(5), getMapMode().DPtoLP(5)));

			GridData constraintPackageLabelRectangle0 = new GridData();
			constraintPackageLabelRectangle0.verticalAlignment = GridData.BEGINNING;
			constraintPackageLabelRectangle0.horizontalAlignment = GridData.BEGINNING;
			constraintPackageLabelRectangle0.horizontalIndent = 0;
			constraintPackageLabelRectangle0.horizontalSpan = 1;
			constraintPackageLabelRectangle0.verticalSpan = 1;
			constraintPackageLabelRectangle0.grabExcessHorizontalSpace = false;
			constraintPackageLabelRectangle0.grabExcessVerticalSpace = false;
			this.add(packageLabelRectangle0, constraintPackageLabelRectangle0);

			ToolbarLayout layoutPackageLabelRectangle0 = new ToolbarLayout();
			layoutPackageLabelRectangle0.setStretchMinorAxis(false);
			layoutPackageLabelRectangle0.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);

			layoutPackageLabelRectangle0.setSpacing(5);
			layoutPackageLabelRectangle0.setVertical(true);

			packageLabelRectangle0.setLayoutManager(layoutPackageLabelRectangle0);

			fFigurePackageNameLabel = new WrappingLabel();
			fFigurePackageNameLabel.setText("<..>");

			packageLabelRectangle0.add(fFigurePackageNameLabel);

			fFigureFromLabel = new WrappingLabel();
			fFigureFromLabel.setAlignment(PositionConstants.TOP);
			fFigureFromLabel.setText("<..>");

			RectangleFigure fillerFigure0 = new RectangleFigure();

			GridData constraintFillerFigure0 = new GridData();
			constraintFillerFigure0.verticalAlignment = GridData.CENTER;
			constraintFillerFigure0.horizontalAlignment = GridData.CENTER;
			constraintFillerFigure0.horizontalIndent = 0;
			constraintFillerFigure0.horizontalSpan = 1;
			constraintFillerFigure0.verticalSpan = 1;
			constraintFillerFigure0.grabExcessHorizontalSpace = false;
			constraintFillerFigure0.grabExcessVerticalSpace = false;
			this.add(fillerFigure0, constraintFillerFigure0);

			fFigurePackageBodyRectangle = new RectangleFigure();
			fFigurePackageBodyRectangle.setLineWidth(2);

			GridData constraintFFigurePackageBodyRectangle = new GridData();
			constraintFFigurePackageBodyRectangle.verticalAlignment = GridData.FILL;
			constraintFFigurePackageBodyRectangle.horizontalAlignment = GridData.FILL;
			constraintFFigurePackageBodyRectangle.horizontalIndent = 0;
			constraintFFigurePackageBodyRectangle.horizontalSpan = 1;
			constraintFFigurePackageBodyRectangle.verticalSpan = 1;
			constraintFFigurePackageBodyRectangle.grabExcessHorizontalSpace = true;
			constraintFFigurePackageBodyRectangle.grabExcessVerticalSpace = true;
			this.add(fFigurePackageBodyRectangle, constraintFFigurePackageBodyRectangle);
		}

		/**
		 * @generated
		 */
		private boolean myUseLocalCoordinates = false;

		private boolean canRemovedFromLabel;

		private WrappingLabel fFigureFromLabel;

		/**
		 * @generated
		 */
		protected boolean useLocalCoordinates() {
			return myUseLocalCoordinates;
		}

		/**
		 * @generated
		 */
		protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
			myUseLocalCoordinates = useLocalCoordinates;
		}

		/**
		 * @generated
		 */
		public RectangleFigure getFigurePackageBodyRectangle() {
			return fFigurePackageBodyRectangle;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigurePackageNameLabel() {
			return fFigurePackageNameLabel;
		}

		// @Override
		// public void setBounds(Rectangle rect) {
		// int preferenceHeight = rect.height -
		// packageLabelRectangle0.getBounds().height - lineWidth / 2;
		// if (preferenceHeight < 0) {
		// preferenceHeight = 0;
		// }
		// getFigurePackageBodyRectangle().setPreferredSize(rect.width,
		// preferenceHeight);
		// super.setBounds(rect);
		// }

		public void addFromLabel() {
			packageLabelRectangle0.add(getFigureFromLabel(), 1);
			canRemovedFromLabel = true;
		}

		public WrappingLabel getFigureFromLabel() {
			return fFigureFromLabel;
		}

		public void removeFromLabel() {
			if (canRemovedFromLabel) {
				packageLabelRectangle0.remove(getFigureFromLabel());
				canRemovedFromLabel = false;
			}
		}

		public void updateFromLabel(String text) {
			getFigureFromLabel().setText(text);
		}
	}
}
