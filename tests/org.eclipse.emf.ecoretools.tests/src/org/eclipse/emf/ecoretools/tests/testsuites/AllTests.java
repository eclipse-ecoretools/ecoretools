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
 * $Id: AllTests.java,v 1.3 2008/04/28 12:38:10 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.tests.testsuites;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.emf.ecoretools.tests.testcases.diagram.creation.connector.EReferenceCreationTest;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.creation.shape.EClassCreationTest;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.creation.shape.EPackageCreationTest;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.deletion.shape.EPackageDeletionTest;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.dragndrop.DropInEPackageTest;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.helpers.DefaultNameTest;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.multidiagram.MultiDiagramTest;
import org.eclipse.emf.ecoretools.tests.testcases.diagram.openclose.OpenSimpleDiagramTest;

/**
 * @author Simon Bernard
 */
public class AllTests {

	/**
	 * The main class for running all the testcases
	 * 
	 * @return Test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.eclipse.emf.ecoretools.tests.testcases"); //$NON-NLS-1$
		// $JUnit-BEGIN$
		suite.addTestSuite(DefaultNameTest.class);

		suite.addTestSuite(DropInEPackageTest.class);

		suite.addTestSuite(EPackageCreationTest.class);
		suite.addTestSuite(EClassCreationTest.class);

		suite.addTestSuite(EReferenceCreationTest.class);

		suite.addTestSuite(EPackageDeletionTest.class);

		suite.addTestSuite(OpenSimpleDiagramTest.class);

		suite.addTestSuite(MultiDiagramTest.class);
		// $JUnit-END$
		return suite;
	}

}
