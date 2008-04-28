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
 * $Id: DropInEPackageTest.java,v 1.2 2008/04/28 12:38:10 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.tests.testcases.diagram.dragndrop;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EClass2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackage2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageContentsEditPart;
import org.eclipse.emf.ecoretools.diagram.providers.EcoreElementTypes;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.fixture.EmptyEcoreDiagramTestFixture;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.tests.runtime.diagram.ui.core.test.AbstractTestBase;

/**
 * @author Simon Bernard
 */
public class DropInEPackageTest extends AbstractTestBase {

	public DropInEPackageTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(DropInEPackageTest.class);
	}

	protected void setTestFixture() {
		testFixture = new EmptyEcoreDiagramTestFixture();
	}

	protected EmptyEcoreDiagramTestFixture getFixture() {
		return (EmptyEcoreDiagramTestFixture) testFixture;
	}

	/**
	 * Test drop EPackage in the diagram (TopEPackage)
	 * 
	 * @throws Exception
	 */
	public void testDropEPackageInTopEPackage() throws Exception {

		final DiagramEditPart diagramEP = getFixture().getDiagramEditPart();
		assertTrue(diagramEP.getChildren().isEmpty());
		assertTrue(getFixture().getRootEPackage().getEClassifiers().isEmpty());

		// drop an external epackage
		EPackage extEPackage1 = EcoreFactory.eINSTANCE.createEPackage();
		extEPackage1.setName("pack1"); //$NON-NLS-1$
		getFixture().dropObject(diagramEP, new Point(10, 10), extEPackage1);

		assertEquals(diagramEP.getChildren().size(), 1);
		assertTrue(getFixture().getRootEPackage().getESubpackages().isEmpty());

		EPackage2EditPart extEPackage2EditPart1 = (EPackage2EditPart) diagramEP.getChildren().get(0);
		assertTrue(getFixture().isShortcut(extEPackage2EditPart1));

		// drop a model epackage
		final EPackage modelEPackage1 = EcoreFactory.eINSTANCE.createEPackage();
		getFixture().execute(new Command() {

			@Override
			public void execute() {
				getFixture().getRootEPackage().getESubpackages().add(modelEPackage1);
			}
		});
		getFixture().dropObject(diagramEP, new Point(100, 100), modelEPackage1);

		assertEquals(diagramEP.getChildren().size(), 2);
		assertEquals(getFixture().getRootEPackage().getESubpackages().size(), 1);

		EPackage2EditPart modelEPackage2EditPart1 = (EPackage2EditPart) diagramEP.getChildren().get(1);
		assertFalse(getFixture().isShortcut(modelEPackage2EditPart1));
	}

	/**
	 * Test drop EClass in EPackage
	 * 
	 * @throws Exception
	 */
	public void testDropEClassesInEPackage() throws Exception {

		final DiagramEditPart diagramEP = getFixture().getDiagramEditPart();
		assertTrue(diagramEP.getChildren().isEmpty());
		assertTrue(getFixture().getRootEPackage().getEClassifiers().isEmpty());

		// create a package
		EPackage2EditPart ePackage2EditPart = (EPackage2EditPart) getFixture().createShapeUsingTool(EcoreElementTypes.EPackage_1002, new Point(10, 10));
		EPackageContentsEditPart epackage2EDPartContent = (EPackageContentsEditPart) ePackage2EditPart.getChildren().get(1);

		assertTrue(epackage2EDPartContent.getChildren().isEmpty());

		// drop an external eClass in this package
		EClass extEClass = EcoreFactory.eINSTANCE.createEClass();
		extEClass.setName("class1"); //$NON-NLS-1$
		getFixture().dropObject(epackage2EDPartContent, new Point(30, 70), extEClass);

		assertEquals(epackage2EDPartContent.getChildren().size(), 1);

		EClass2EditPart extEClassEditPart = (EClass2EditPart) epackage2EDPartContent.getChildren().get(0);
		assertTrue(getFixture().isShortcut(extEClassEditPart));
		assertFalse(((EPackage) ePackage2EditPart.resolveSemanticElement()).getEClassifiers().contains(extEClass));

	}
}