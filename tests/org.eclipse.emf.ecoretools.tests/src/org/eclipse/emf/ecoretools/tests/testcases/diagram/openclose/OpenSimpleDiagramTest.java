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
 * $Id: OpenSimpleDiagramTest.java,v 1.2 2008/04/28 12:38:10 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.tests.testcases.diagram.openclose;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.emf.ecoretools.tests.testcases.diagram.fixture.SimpleEcoreDiagramTestFixture;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.helpers.DefaultNameTest;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.tests.runtime.diagram.ui.core.test.AbstractTestBase;

public class OpenSimpleDiagramTest extends AbstractTestBase {

	public OpenSimpleDiagramTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(DefaultNameTest.class);
	}

	protected void setTestFixture() {
		testFixture = new SimpleEcoreDiagramTestFixture();
	}

	protected SimpleEcoreDiagramTestFixture getFixture() {
		return (SimpleEcoreDiagramTestFixture) testFixture;
	}

	public void testOpenDiagram() throws Exception {

		DiagramEditPart diagramEP1 = getFixture().getDiagramEditPart();
		assertTrue(diagramEP1.getChildren().size() == 2);

		getFixture().deleteFromModel((EditPart) diagramEP1.getChildren().get(0));
		assertTrue(diagramEP1.getChildren().size() == 1);

		saveDiagram();
		closeDiagram();
		openDiagram();

		DiagramEditPart diagramEP2 = getFixture().getDiagramEditPart();
		assertNotSame(diagramEP1, diagramEP2);
		assertTrue(diagramEP2.getChildren().size() == 1);
	}
}
