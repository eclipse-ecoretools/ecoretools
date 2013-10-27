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
import java.util.Iterator;
import java.util.List;

import org.eclipse.sirius.viewpoint.description.EdgeMapping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

@RunWith(value = Parameterized.class)
public class EdgeMappingConsistencyTests {

	private EdgeMapping underTest;

	public EdgeMappingConsistencyTests(EdgeMapping edgemapping) {
		this.underTest = edgemapping;
	}

	@Test
	public void hasReconnect() {
		assertTrue("Edge Mapping +" + underTest.getName()
				+ "has no reconnect tool",
				underTest.getReconnections().size() > 0);
	}

	@Parameters
	public static Collection<Object[]> data() {
		List<Object[]> parameters = Lists.newArrayList();
		EcoreToolsViewpointSpecificationModels spec = new EcoreToolsViewpointSpecificationModels();
		Iterator<EdgeMapping> it = Iterators.filter(spec.eAllContents(),
				EdgeMapping.class);
		while (it.hasNext()) {
			parameters.add(new Object[] { it.next() });
		}
		return parameters;
	}

}