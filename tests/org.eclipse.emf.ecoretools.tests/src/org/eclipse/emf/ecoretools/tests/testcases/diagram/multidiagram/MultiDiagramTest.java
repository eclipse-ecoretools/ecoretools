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

package org.eclipse.emf.ecoretools.tests.testcases.diagram.multidiagram;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackage2EditPart;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.emf.ecoretools.diagram.providers.EcoreElementTypes;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.fixture.EmptyEcoreDiagramTestFixture;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.helpers.DefaultNameTest;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.util.MultiDiagramUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.tests.runtime.diagram.ui.core.test.AbstractTestBase;

public class MultiDiagramTest extends AbstractTestBase {

	public MultiDiagramTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(DefaultNameTest.class);
	}

	protected void setTestFixture() {
		testFixture = new EmptyEcoreDiagramTestFixture();
	}

	protected EmptyEcoreDiagramTestFixture getFixture() {
		return (EmptyEcoreDiagramTestFixture) testFixture;
	}

	public void testCreateOpenCloseDeleteTest() throws Exception {

		final DiagramEditPart diagramEP = getFixture().getDiagramEditPart();
		assertTrue(diagramEP.getChildren().isEmpty());

		// Add epackage
		EPackage2EditPart epackage1EditPart = (EPackage2EditPart) getFixture().createShapeUsingTool(EcoreElementTypes.EPackage_1002, new Point(10, 10));

		Diagram diag = getFixture().createInnerDiagram(epackage1EditPart);

		MultiDiagramUtil.closeDiagram(diag);

		MultiDiagramUtil.openDiagram(diag);

		MultiDiagramUtil.closeDiagram(diag);

		getFixture().deleteInnerDiagram(diag, epackage1EditPart);
	}

	public void testAddPackageOnMultiDiagramTest() throws Exception {

		final DiagramEditPart diagramEP = getFixture().getDiagramEditPart();
		assertTrue(diagramEP.getChildren().isEmpty());

		// Add epackage
		EPackage2EditPart epackage1EditPart = (EPackage2EditPart) getFixture().createShapeUsingTool(EcoreElementTypes.EPackage_1002, new Point(10, 10));
		EPackage epackage1 = (EPackage) epackage1EditPart.resolveSemanticElement();

		Diagram diag = getFixture().createInnerDiagram(epackage1EditPart);

		EcoreDiagramEditor ecoreEditor = MultiDiagramUtil.getEditorPart(diag);

		EPackage2EditPart epackage1EditPart2 = (EPackage2EditPart) getFixture().createShapeUsingTool(EcoreElementTypes.EPackage_1002, new Point(10, 10), ecoreEditor.getDiagramEditPart());

		EPackage package2 = (EPackage) epackage1EditPart2.resolveSemanticElement();

		assertTrue(epackage1.getESubpackages().contains(package2));
	}
}
