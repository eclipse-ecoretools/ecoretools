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
package org.eclipse.emf.ecoretools.diagram.edit.policies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;

/**
 * 
 * TODO Describe the class here <br>
 * creation : 11 janv. 08
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class DestroyElementUtils {

	/**
	 * Returns the list of views to be deleted
	 */
	public static List<View> findShortcutViews(EObject semanticElement, View notationElement) {
		if (notationElement == null || notationElement.eResource() == null) {
			return Collections.emptyList();
		}
		if (semanticElement == null || semanticElement.eResource() == null) {
			return Collections.emptyList();
		}
		List<View> views = new ArrayList<View>();
		for (Iterator<EObject> it = notationElement.eResource().getAllContents(); it.hasNext();) {
			EObject eObject = it.next();
			if (false == eObject instanceof View) {
				continue;
			}
			View potentialView = (View) eObject;
			if (potentialView.getEAnnotation("Shortcut") == null) {
				continue;
			}
			if (potentialView.getElement() != semanticElement) {
				continue;
			}
			if ((potentialView.eContainer() instanceof View) && (semanticElement.eContainer() instanceof EObject) && (semanticElement.eContainer() == ((View) potentialView.eContainer()).getElement())) {
				continue;
			}
			views.add(potentialView);
		}
		return views;
	}
}
