/*******************************************************************************
 * Copyright (c) 2013, 2024 Obeo.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.sirius.diagram.BackgroundStyle;
import org.eclipse.sirius.diagram.description.style.FlatContainerStyleDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class GradientConsistencyTests {

	private FlatContainerStyleDescription underTest;

	public GradientConsistencyTests(FlatContainerStyleDescription expression) {
		this.underTest = expression;
	}

	@Test
	public void hasObliqueGradient() {
		assertTrue(underTest.getBackgroundStyle() == BackgroundStyle.LIQUID_LITERAL);
	}

	@Parameters
	public static Collection<Object[]> data() {
		List<Object[]> parameters = new ArrayList<>();
		Set<FlatContainerStyleDescription> allExpressions = new LinkedHashSet<>();
		EcoreToolsViewpointSpecificationModels spec = new EcoreToolsViewpointSpecificationModels();
		spec.eAllContents().forEachRemaining(eObject -> {
		    if (eObject instanceof FlatContainerStyleDescription style) {
		        allExpressions.add(style);
		    }
		});
		return parameters;
	}

}