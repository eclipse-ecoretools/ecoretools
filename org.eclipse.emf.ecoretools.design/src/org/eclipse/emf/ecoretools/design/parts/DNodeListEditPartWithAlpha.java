/*******************************************************************************
 * Copyright (c) 2014 Obeo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.parts;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeList;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeListEditPart;

/**
 * An editpart which change the alpha level based on the mapping of the element.
 * 
 * @author cedric
 * 
 */
public class DNodeListEditPartWithAlpha extends DNodeListEditPart {

	public DNodeListEditPartWithAlpha(View view) {
		super(view);
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		DDiagramElement elem = resolveDiagramElement();
		if (elem instanceof DNodeList
				&& ((DNodeList) elem).getActualMapping() != null
				&& "EC External EClasses".equals(((DNodeList) elem)
						.getActualMapping().getName())) {
			IFigure fig = getMainFigure();
			if (fig instanceof TransparencyFigure) {
				((TransparencyFigure) fig).setAlpha(110);
			}
		}
	}

	@Override
	protected NodeFigure createNodePlate() {
		return new TransparencyFigure(getMapMode().DPtoLP(40), getMapMode()
				.DPtoLP(40));
	}

	class TransparencyFigure extends DefaultSizeNodeFigure {

		private int alpha = 255;

		public TransparencyFigure(int width, int height) {
			super(width, height);
		}

		public void setAlpha(int i) {
			this.alpha = i;

		}

		@Override
		protected void paintChildren(Graphics graphics) {
			int prevAlpha = graphics.getAlpha();
			graphics.setAlpha(alpha);
			super.paintChildren(graphics);
			graphics.setAlpha(prevAlpha);

		}
	}
}
