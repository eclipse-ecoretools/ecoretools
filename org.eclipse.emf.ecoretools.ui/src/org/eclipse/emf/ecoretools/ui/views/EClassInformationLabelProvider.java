/***********************************************************************
 * Copyright (c) 2015 IRT AESE (IRT Saint Exupery)
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Pierre Gaufillet (IRT Saint Exupery) - initial API and implementation
 *
 **********************************************************************/

package org.eclipse.emf.ecoretools.ui.views;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;

public class EClassInformationLabelProvider extends StyledCellLabelProvider {


	private ILabelProvider delegateProvider;

	private static final Styler EMPHASIZED_STYLER = new Styler() {
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.StyledString.Styler#applyStyles(org.eclipse.swt.graphics.TextStyle)
		 */
		@Override
		public void applyStyles(TextStyle textStyle) {
			Font italic = JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT);
			textStyle.font = italic;
		}
	};

	public EClassInformationLabelProvider() {
		delegateProvider = new AdapterFactoryLabelProvider(new EcoreItemProviderAdapterFactory());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.StyledCellLabelProvider#update(org.eclipse.
	 * jface.viewers.ViewerCell)
	 */
	@Override
	public void update(ViewerCell cell) {
		Object obj = cell.getElement();
		StyledString styledString = new StyledString();

		if (obj instanceof EClass) {
			EClass eClass = (EClass) obj;
			if (eClass.isAbstract())
				styledString.append(getName(obj), EMPHASIZED_STYLER);
			else
				styledString.append(getName(obj));
		}

		cell.setText(styledString.toString());
		cell.setStyleRanges(styledString.getStyleRanges());
		cell.setImage(getImage(obj));
		super.update(cell);
	}

	/**
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		return delegateProvider.getImage(element);
	}

	/**
	 * @param element
	 * @return
	 */
	public String getName(Object element) {
		if (element instanceof EClass) {
			EClass eClass = (EClass) element;
			String result = "";
			if (eClass.getName() != null) {
				result = eClass.getName();
			}
			return result;
		} else {
			return delegateProvider.getText(element);
		}
	}
}
