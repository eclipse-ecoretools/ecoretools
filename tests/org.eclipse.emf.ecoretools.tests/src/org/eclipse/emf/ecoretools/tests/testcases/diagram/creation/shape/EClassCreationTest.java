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
 * $Id: EClassCreationTest.java,v 1.2 2008/04/28 12:38:10 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.tests.testcases.diagram.creation.shape;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EClass2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EClassEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackage2EditPart;
import org.eclipse.emf.ecoretools.diagram.providers.EcoreElementTypes;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.fixture.EmptyEcoreDiagramTestFixture;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.tests.runtime.diagram.ui.core.test.AbstractTestBase;

/**
 * @author Simon Bernard
 */
public class EClassCreationTest extends AbstractTestBase {

	public EClassCreationTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(EClassCreationTest.class);
	}

	protected void setTestFixture() {
		testFixture = new EmptyEcoreDiagramTestFixture();
	}

	protected EmptyEcoreDiagramTestFixture getFixture() {
		return (EmptyEcoreDiagramTestFixture) testFixture;
	}

	/**
	 * Test EClass creation
	 */
	public void testEClassCreation() throws Exception {

		final DiagramEditPart diagramEP = getFixture().getDiagramEditPart();
		assertTrue(diagramEP.getChildren().isEmpty());

		// add eclass
		EClassEditPart eclass1EditPart = (EClassEditPart) getFixture().createShapeUsingTool(EcoreElementTypes.EClass_1001, new Point(10, 10));
		EClass eclass1 = (EClass) eclass1EditPart.resolveSemanticElement();

		assertEquals(eclass1.eResource(), getFixture().getModelResource());
	}

	/**
	 * Test EClass creation
	 */
	public void testEClassCreationInEPackage() throws Exception {

		final DiagramEditPart diagramEP = getFixture().getDiagramEditPart();
		assertTrue(diagramEP.getChildren().isEmpty());

		// Add epackage
		EPackage2EditPart epackage1EditPart = (EPackage2EditPart) getFixture().createShapeUsingTool(EcoreElementTypes.EPackage_1002, new Point(10, 10));
		EPackage epackage1 = (EPackage) epackage1EditPart.resolveSemanticElement();

		assertEquals(epackage1.eResource(), getFixture().getModelResource());

		// add Eclass in this one
		EClass2EditPart eclass1EditPart = (EClass2EditPart) getFixture().createShapeUsingTool(EcoreElementTypes.EClass_2003, new Point(20, 50), null,
				(IGraphicalEditPart) epackage1EditPart.getChildren().get(1));
		EClass eclass1 = (EClass) eclass1EditPart.resolveSemanticElement();

		assertEquals(eclass1.eContainer(), epackage1);
		assertEquals(eclass1.eResource(), getFixture().getModelResource());
	}

}
