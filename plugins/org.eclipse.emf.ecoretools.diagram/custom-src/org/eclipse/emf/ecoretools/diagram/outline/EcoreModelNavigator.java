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

package org.eclipse.emf.ecoretools.diagram.outline;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.gmf.runtime.diagram.ui.outline.AbstractModelNavigator;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.IPageSite;

/**
 * A navigator that gives a model-oriented view in the outline.
 * 
 * @author <a href="mailto:david.sciamma@anyware-tech.com">David Sciamma</a>
 */
public class EcoreModelNavigator extends AbstractModelNavigator {

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            the parent Composite
	 * @param viewer
	 *            the Viewer
	 * @param pageSite
	 *            the IPageSite
	 */
	public EcoreModelNavigator(Composite parent, IDiagramGraphicalViewer viewer, IPageSite pageSite) {
		super(parent, viewer, pageSite);
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.outline.AbstractModelNavigator#getAdapterFactory()
	 */
	@Override
	protected AdapterFactory getAdapterFactory() {
		return EcoreDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory();
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.outline.AbstractModelNavigator#getPreferenceStore()
	 */
	@Override
	protected IPreferenceStore getPreferenceStore() {
		return EcoreDiagramEditorPlugin.getInstance().getPreferenceStore();
	}

}
