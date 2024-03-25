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

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

@RunWith(value = Parameterized.class)
public class TypeNameTests {

	private InterpretedExpression underTest;

	public TypeNameTests(InterpretedExpression expression) {
		this.underTest = expression;
	}

	@Parameters
	public static Collection<Object[]> data() {
		List<Object[]> parameters = new ArrayList<>();
		Map<String, Integer> allExpressions = new HashMap<>();
        collectExpressionFromEcoreToolsViewpoints(parameters, allExpressions, "Design");
        collectExpressionFromEcoreToolsViewpoints(parameters, allExpressions, "Archetype");
        collectExpressionFromEcoreToolsViewpoints(parameters, allExpressions, "Review");
        collectExpressionFromEcoreToolsViewpoints(parameters, allExpressions, "Generation");
		for (String expr : allExpressions.keySet()) {
			System.out.println(allExpressions.get(expr) + " : " + expr);
		}
		return parameters;
	}

    private static void collectExpressionFromEcoreToolsViewpoints(List<Object[]> parameters, Map<String, Integer> allExpressions, String vpName) {
        Viewpoint structural = ViewpointRegistry.getInstance().getViewpoint(URI.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/" + vpName));
        collectExpressionsFromViewpoint(parameters, structural, allExpressions);
    }

	private static void collectExpressionsFromViewpoint(List<Object[]> parameters, Viewpoint structural, Map<String, Integer> allExpressions) {
		Iterator<EObject> it = structural.eAllContents();
		while (it.hasNext()) {
			EObject underTest = it.next();
			for (EAttribute attr : underTest.eClass().getEAllAttributes()) {
				if (attr.getEType() == DescriptionPackage.eINSTANCE.getTypeName()) {
					Object expr = underTest.eGet(attr);
					if (expr instanceof String && ((String)expr).length() > 0) {
						parameters
								.add(new Object[] {new InterpretedExpression((String)expr, underTest, attr)});
						int newCount = allExpressions.getOrDefault((String) expr, 0) + 1;
						allExpressions.put((String) expr, newCount);
					}
				}
			}
		}
	}

    @Test
    public void isNotQualified() {
        if (underTest.getExpression().indexOf(".") == -1) {
            fail("TypeName : " + underTest.getExpression() + " is not in qualified form :" + underTest.getFeature().getName() + " of object " + EcoreUtil.getURI(underTest.getDeclaration()));
        }
    }

}
