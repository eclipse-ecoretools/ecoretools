/*******************************************************************************
 * Copyright (c) 2013 Obeo.
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

import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Lists;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

@RunWith(value = Parameterized.class)
public class TypeNameTests {

	private InterpretedExpression underTest;

	public TypeNameTests(InterpretedExpression expression) {
		this.underTest = expression;
	}

	@Parameters
	public static Collection<Object[]> data() {
		List<Object[]> parameters = Lists.newArrayList();
		SortedMultiset<String> allExpressions = TreeMultiset.create();
		collectExpressionFromEcoreToolsViewpoints(parameters, allExpressions,
				"Design");
		collectExpressionFromEcoreToolsViewpoints(parameters, allExpressions,
				"Archetype");
		collectExpressionFromEcoreToolsViewpoints(parameters, allExpressions,
				"Review");
		collectExpressionFromEcoreToolsViewpoints(parameters, allExpressions,
				"Generation");
		for (String expr : allExpressions.elementSet()) {
			System.out.println(allExpressions.count(expr) + " : " + expr);
		}
		return parameters;
	}

	private static void collectExpressionFromEcoreToolsViewpoints(
			List<Object[]> parameters, SortedMultiset<String> allExpressions,
			String vpName) {
		Viewpoint structural = ViewpointRegistry.getInstance().getViewpoint(
				URI.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/" + vpName));
		collectExpressionsFromViewpoint(parameters, structural,allExpressions);
	}

	private static void collectExpressionsFromViewpoint(List<Object[]> parameters, Viewpoint structural, SortedMultiset<String> allExpressions) {
		Iterator<EObject> it = structural.eAllContents();
		while (it.hasNext()) {
			EObject underTest = it.next();
			for (EAttribute attr : underTest.eClass().getEAllAttributes()) {
				if (attr.getEType() == DescriptionPackage.eINSTANCE.getTypeName()) {
					Object expr = underTest.eGet(attr);
					if (expr instanceof String && ((String)expr).length() > 0) {
						parameters
								.add(new Object[] {new InterpretedExpression((String)expr, underTest, attr)});
						allExpressions.add((String)expr);
					}
				}
			}
		}
	}

	@Test
	public void isNotQualified() {
		if (underTest.getExpression().indexOf(".") == -1) {
			fail("TypeName : " + underTest.getExpression() + " is not in qualified form :"
					+ underTest.getFeature().getName() + " of object "
					+ EcoreUtil.getURI(underTest.getDeclaration()));
		} 		
	}

}
