/**
 * Copyright (c) 2008 {INITIAL COPYRIGHT OWNER}
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 */
package org.eclipse.emf.ecoretools.diagram.edit.figures;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gmf.runtime.notation.View;

/**
 * 
 * TODO Describe the class here <br>
 * creation : 8 janv. 08
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class FigureFromLabelUtils {

	public static String getQualifiedName(EObject semanticElement) {
		return "(from " + getParentName(semanticElement) + ")";
	}

	private static String getParentName(EObject semanticElement) {
		if (semanticElement == null) {
			return "Unresolved Element";
		}
		if (semanticElement.eContainer() instanceof ENamedElement) {
			return ((ENamedElement) semanticElement.eContainer()).getName();
		}
		return "Unknown Element";
	}

	private static View getVisualContainer(View notationView) {
		View visualContainer = null;
		for (EObject currentView = notationView.eContainer(); currentView != null; currentView = currentView.eContainer()) {
			if (false == currentView instanceof View) {
				continue;
			}
			if (((View) currentView).getElement() instanceof EPackage) {
				visualContainer = (View) currentView;
				break;
			}
		}
		return visualContainer;
	}

	public static boolean needFromLabel(EObject semanticElement, View notationView) {
		if (semanticElement == null || notationView == null) {
			return false;
		}
		EObject semanticContainer = semanticElement.eContainer();
		View visualContainer = getVisualContainer(notationView);
		if (visualContainer != null && visualContainer.getElement() == semanticContainer) {
			return false;
		}

		return true;
	}
}
