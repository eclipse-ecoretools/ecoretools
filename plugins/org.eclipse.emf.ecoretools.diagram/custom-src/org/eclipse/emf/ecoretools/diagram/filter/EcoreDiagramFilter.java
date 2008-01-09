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

package org.eclipse.emf.ecoretools.diagram.filter;

import java.util.Collection;

import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.emf.ecoretools.diagram.providers.EcoreElementTypes;
import org.eclipse.emf.ecoretools.filters.diagramfilters.AbstractDiagramFilter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * 
 * TODO Describe the class here <br>
 * creation : 4 oct. 07
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class EcoreDiagramFilter extends AbstractDiagramFilter {

	@Override
	public IPreferenceStore getPreferenceStore() {
		return EcoreDiagramEditorPlugin.getInstance().getPreferenceStore();
	}

	@Override
	public Collection<IElementType> getIElementTypes() {
		return EcoreElementTypes.getElements().keySet();
	}

}
