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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;


public class InterpretedExpression {

	private String expression;
	
	private EObject declaration;
	
	private EAttribute feature;

	public InterpretedExpression(String expression, EObject declaration, EAttribute feature) {
		super();
		this.expression = expression;
		this.declaration = declaration;
		this.feature = feature;
	}

	public String getExpression() {
		return expression;
	}

	public EObject getDeclaration() {
		return declaration;
	}

	public EAttribute getFeature() {
		return feature;
	}
	
	

}
