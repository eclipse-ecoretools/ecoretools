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
 * 
 * $Id: SimpleEcoreDiagramTestFixture.java,v 1.3 2008/05/27 09:54:59 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.tests.testcases.diagram.fixture;

/**
 * A fixture to make test on an simple Ecore diagram
 * 
 * @author Simon Bernard
 */
public class SimpleEcoreDiagramTestFixture extends ExistingEcoreDiagramTestFixture {

	/**
	 * @see org.eclipse.emf.ecoretools.tests.testcases.diagram.fixture.ExistingEcoreDiagramTestFixture#getEcoreDiagramPath()
	 */
	public String getEcoreDiagramPath() {
		return ("models/simple/simple.ecorediag"); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.emf.ecoretools.tests.testcases.diagram.fixture.ExistingEcoreDiagramTestFixture#getEcoreModelPath()
	 */
	public String getEcoreModelPath() {
		return ("models/simple/simple.ecore"); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.emf.ecoretools.tests.diagram.ui.core.fixture.AbstractExistingDiagramTestFixture#getDiagramFileName()
	 */
	public String getDiagramFileName() {
		return "simple.ecorediag"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.emf.ecoretools.tests.diagram.ui.core.fixture.AbstractExistingDiagramTestFixture#getModelFileName()
	 */
	public String getModelFileName() {
		return "simple.ecore"; //$NON-NLS-1$
	}
}
