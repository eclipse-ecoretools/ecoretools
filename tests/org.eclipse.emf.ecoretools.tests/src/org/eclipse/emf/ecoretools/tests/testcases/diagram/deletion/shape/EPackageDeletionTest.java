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
 * $Id: EPackageDeletionTest.java,v 1.2 2008/04/28 12:38:09 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.tests.testcases.diagram.deletion.shape;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackage2EditPart;
import org.eclipse.emf.ecoretools.diagram.providers.EcoreElementTypes;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.fixture.EmptyEcoreDiagramTestFixture;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.tests.runtime.diagram.ui.core.test.AbstractTestBase;

/**
 * @author Simon Bernard
 */
public class EPackageDeletionTest extends AbstractTestBase {

	public EPackageDeletionTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(EPackageDeletionTest.class);
	}

	protected void setTestFixture() {
		testFixture = new EmptyEcoreDiagramTestFixture();
	}

	protected EmptyEcoreDiagramTestFixture getFixture() {
		return (EmptyEcoreDiagramTestFixture) testFixture;
	}

	/**
	 * Test EPackage default name attribution
	 */
	public void testEPackageWithoutEClassModelDeletion() throws Exception {

		final DiagramEditPart diagramEP = getFixture().getDiagramEditPart();
		assertTrue(diagramEP.getChildren().isEmpty());

		// Add epackage
		EPackage2EditPart epackage1EditPart = (EPackage2EditPart) getFixture().createShapeUsingTool(EcoreElementTypes.EPackage_1002, new Point(10, 10));
		EPackage epackage1 = (EPackage) epackage1EditPart.resolveSemanticElement();

		assertTrue(epackage1.eResource() == getFixture().getModelResource());

		// remove epackage from model
		getFixture().deleteFromModel(epackage1EditPart);

		assertTrue(diagramEP.getChildren().isEmpty());
		assertTrue(getFixture().getRootEPackage().getESubpackages().isEmpty());
	}

	public void testEPackageWithoutEClassDiagramDeletion() throws Exception {

		final DiagramEditPart diagramEP = getFixture().getDiagramEditPart();
		assertTrue(diagramEP.getChildren().isEmpty());

		// Add epackage
		EPackage2EditPart epackage1EditPart = (EPackage2EditPart) getFixture().createShapeUsingTool(EcoreElementTypes.EPackage_1002, new Point(10, 10));
		EPackage epackage1 = (EPackage) epackage1EditPart.resolveSemanticElement();

		assertTrue(epackage1.eResource() == getFixture().getModelResource());

		// remove epackage from diagram
		getFixture().deleteFromDiagram(epackage1EditPart);

		assertTrue(diagramEP.getChildren().isEmpty());
		assertEquals(getFixture().getRootEPackage().getESubpackages().size(), 1);
	}
}
