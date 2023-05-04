/*******************************************************************************
 * Copyright (c) 2014, 2023 Obeo
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.ui.parts;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeList;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeListEditPart;
import org.eclipse.sirius.ext.gmf.runtime.gef.ui.figures.SiriusDefaultSizeNodeFigure;

/**
 * An editpart which change the alpha level based on the mapping of the element.
 * 
 * @author cedric
 * 
 */
@SuppressWarnings("restriction")
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

    class TransparencyFigure extends SiriusDefaultSizeNodeFigure {

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
