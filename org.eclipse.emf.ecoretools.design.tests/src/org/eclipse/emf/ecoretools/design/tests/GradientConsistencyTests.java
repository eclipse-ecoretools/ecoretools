/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.tests;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.sirius.viewpoint.BackgroundStyle;
import org.eclipse.sirius.viewpoint.description.style.FlatContainerStyleDescription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
		List<Object[]> parameters = Lists.newArrayList();
		Set<FlatContainerStyleDescription> allExpressions = Sets
				.newLinkedHashSet();
		EcoreToolsViewpointSpecificationModels spec = new EcoreToolsViewpointSpecificationModels();
		allExpressions.addAll(Lists.newArrayList(Iterators.filter(
				spec.eAllContents(), FlatContainerStyleDescription.class)));
		return parameters;
	}

}