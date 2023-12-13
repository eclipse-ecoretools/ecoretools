/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.tests;

import static org.junit.Assert.fail;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.sirius.ecore.extender.tool.api.ModelUtils;
import org.eclipse.sirius.viewpoint.description.Group;
import org.junit.Before;
import org.junit.Test;

/**
 * Run validation tests on VSM.
 * 
 * @author Melanie Bats <a
 *         href="mailto:melanie.bats@obeo.fr">melanie.bats@obeo.fr</a>
 */
public class VsmValidationTests {
    private Group modeler;

    @Before
    public void setup() throws Exception {
        ResourceSet set = new ResourceSetImpl();
        modeler = (Group) ModelUtils.load(URI.createPlatformPluginURI("/org.eclipse.emf.ecoretools.design/description/ecore.odesign", true), set);

    }

    /**
     * Test VSM validation. Test there in no error when validate VSM.
     */
    @Test
    public void isValid() {
        // Test that the modeler is valid.
        Diagnostician diagnostician = new Diagnostician();
        Diagnostic diagnostic = diagnostician.validate(modeler);
        switch (diagnostic.getSeverity()) {
        case Diagnostic.ERROR:
            fail("The VSM is not valid it has errors : " + diagnostic);
            break;
        }
    }
}
