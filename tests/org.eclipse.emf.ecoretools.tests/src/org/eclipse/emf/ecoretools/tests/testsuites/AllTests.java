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

package org.eclipse.emf.ecoretools.tests.testsuites;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.emf.ecoretools.tests.testcases.DummyTestCase;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for org.eclipse.emf.ecoretools.tests.testcases");
		// $JUnit-BEGIN$
		suite.addTestSuite(DummyTestCase.class);
		// $JUnit-END$
        return suite;
    }

}
