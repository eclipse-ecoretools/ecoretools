/*******************************************************************************
 * Copyright (c) 2015 IRT AESE (IRT Saint Exupery) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Pierre Gaufillet (IRT Saint Exupery) - initial API and implementation
 *     
 *******************************************************************************/
package org.eclipse.emf.ecoretools.ui.views;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;

/**
 * @author pierre.gaufillet
 * 
 */
public class FeatureLabelProvider extends StyledCellLabelProvider {

	private ILabelProvider delegateProvider;

	private EClass selection;

	/**
	 * 
	 */
	private static final Styler INHERITED_STYLER = new Styler() {
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.StyledString.Styler#applyStyles(org.eclipse.swt.graphics.TextStyle)
		 */
		@Override
		public void applyStyles(TextStyle textStyle) {
			Font italic = JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT);
			textStyle.font = italic;
			textStyle.foreground = JFaceResources.getColorRegistry().get(JFacePreferences.QUALIFIER_COLOR);
		}
	};

	private static final Styler DERIVED_STYLER = new Styler() {
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.StyledString.Styler#applyStyles(org.eclipse.swt.graphics.TextStyle)
		 */
		@Override
		public void applyStyles(TextStyle textStyle) {
			Font italic = JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT);
			textStyle.font = italic;
			textStyle.foreground = JFaceResources.getColorRegistry().get(JFacePreferences.COUNTER_COLOR);
		}
	};

	public EClass getSelection() {
		return selection;
	}

	public void setSelection(EClass selection) {
		this.selection = selection;
	}

	public FeatureLabelProvider() {
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

		if (obj instanceof EStructuralFeature) {
			EStructuralFeature feature = (EStructuralFeature) obj;
			if (selection != null) {
				List<EStructuralFeature> localFeatures = selection.getEStructuralFeatures();
				if (!localFeatures.contains(feature)) {
					styledString.append(getName(obj) + " [" + ((EClass) feature.eContainer()).getName() + "]", INHERITED_STYLER);
				} else
					styledString.append(getName(obj));
				if (feature.isDerived()) {
					styledString.append(" (derived)", DERIVED_STYLER);
				}
			}
		}

		cell.setText(styledString.toString());
		cell.setStyleRanges(styledString.getStyleRanges());
		cell.setImage(getImage(obj));
		super.update(cell);
	}

	/**
	 * @param element
	 * @return
	 */
	public Image getImage(Object element) {
		return delegateProvider.getImage(element);
	}

	/**
	 * @param element
	 * @return
	 */
	public String getName(Object element) {
		return delegateProvider.getText(element);
	}
}
