/***********************************************************************
 * Copyright (c) 2008 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 **********************************************************************/

package org.eclipse.emf.ecoretools.tests.testcases.diagram.fixture;

import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.util.IDEEditorFileCreator;

/**
 * Ecore File Creator
 * 
 * @author Simon Bernard
 */
public class EcoreDiagramFileCreator extends IDEEditorFileCreator {

	private static EcoreDiagramFileCreator INSTANCE = new EcoreDiagramFileCreator();

	/**
	 * Method getInstance. This class is a singleton that can only be accessed
	 * through this static method.
	 * 
	 * @return EcoreDiagramFileCreator The singleton instance
	 */
	static public EcoreDiagramFileCreator getInstance() {
		return INSTANCE;
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.resources.editor.util.DiagramFileCreator#getExtension()
	 */
	public String getExtension() {
		return ".ecorediag"; //$NON-NLS-1$
	}
}
