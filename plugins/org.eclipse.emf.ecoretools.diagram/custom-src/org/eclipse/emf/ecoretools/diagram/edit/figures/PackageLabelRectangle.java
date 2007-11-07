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

package org.eclipse.emf.ecoretools.diagram.edit.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;

public class PackageLabelRectangle extends RectangleFigure {

	@Override
	protected void outlineShape(Graphics graphics) {
		Rectangle r = getBounds();
		int x = r.x + lineWidth / 2;
		int y = r.y + lineWidth / 2;
		int h = r.height;
		int w = r.width - Math.max(1, lineWidth);
		WrappingLabel label = (WrappingLabel) getChildren().get(0);
		Insets inset = ((MarginBorder) getBorder()).getInsets(this);
		int labelWidth = label.getBounds().width + inset.left + inset.right;
		if (labelWidth > w) {
			labelWidth = w - 1;
		}

		Point point1 = new Point(x, y);
		Point point3 = new Point(x + labelWidth, y + h);

		Rectangle desiredBounds = new Rectangle(point1, point3);
		graphics.drawRectangle(desiredBounds);
	}

	@Override
	protected void fillShape(Graphics graphics) {
		Rectangle r = getBounds();
		int x = r.x + lineWidth / 2;
		int y = r.y + lineWidth / 2;
		int h = r.height;
		WrappingLabel label = (WrappingLabel) getChildren().get(0);
		Insets inset = ((MarginBorder) getBorder()).getInsets(this);
		int labelWidth = label.getBounds().width + inset.left + inset.right;

		Point point1 = new Point(x, y);
		Point point3 = new Point(x + labelWidth, y + h);

		Rectangle desiredBounds = new Rectangle(point1, point3);
		graphics.fillRectangle(desiredBounds);
	}
}