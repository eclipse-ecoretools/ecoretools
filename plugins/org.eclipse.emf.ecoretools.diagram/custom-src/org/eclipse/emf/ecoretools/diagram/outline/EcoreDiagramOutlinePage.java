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
 *
 * $Id: EcoreDiagramOutlinePage.java,v 1.4 2008/04/28 15:23:59 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.diagram.outline;

import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.gmf.runtime.diagram.ui.outline.AbstractDiagramsOutlinePage;
import org.eclipse.gmf.runtime.diagram.ui.outline.AbstractModelNavigator;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.IPageSite;

/**
 * A customized outline page for rendering both a Thumbnail view of the editor
 * and/or a tree structure of the underlying model
 * 
 * @author <a href="mailto:david.sciamma@anyware-tech.com">David Sciamma</a>
 * @author <a href="mailto:jacques.lescot@anyware-tech.com">Jacques LESCOT</a>
 */
public class EcoreDiagramOutlinePage extends AbstractDiagramsOutlinePage {

	/**
	 * Constructor
	 * 
	 * @param editor
	 *            the Editor
	 */
	public EcoreDiagramOutlinePage(DiagramEditor editor) {
		super(editor);
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.outline.AbstractDiagramsOutlinePage#createNavigator(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.part.IPageSite)
	 */
	@Override
	protected AbstractModelNavigator createNavigator(Composite parent, IPageSite pageSite) {
		return new EcoreModelNavigator(parent, getEditor(), pageSite);
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.outline.AbstractDiagramsOutlinePage#getPreferenceStore()
	 */
	@Override
	protected IPreferenceStore getPreferenceStore() {
		return EcoreDiagramEditorPlugin.getInstance().getPreferenceStore();
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.outline.AbstractDiagramsOutlinePage#getEditorID()
	 */
	@Override
	protected String getEditorID() {
		return "org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorID"; //$NON-NLS-1$
	}
}
