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

package org.eclipse.emf.ecoretools.diagram.edit.helpers;

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;

/**
 * @generated
 */
public class EEnumLiteralEditHelper extends EcoreBaseEditHelper {

	private final static String baseString = "EEnumLiteral";

	@Override
	protected ICommand getConfigureCommand(ConfigureRequest req) {
		if (req.getElementToConfigure() instanceof EEnumLiteral) {
			setDefaultNameForElement((EEnumLiteral) req.getElementToConfigure());
			setDefaultValueForElement((EEnumLiteral) req.getElementToConfigure());
		}
		return super.getConfigureCommand(req);
	}

	private void setDefaultNameForElement(EEnumLiteral elementToConfigure) {
		EEnum container = elementToConfigure.getEEnum();
		elementToConfigure.setName(baseString + (container.getELiterals().size() - 1));
		// TODO : Wait for this issue to be fixed :
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=201446
		// EEnum container = elementToConfigure.getEEnum();
		// Boolean foundName = false;
		// int count = 0;
		// while (!foundName && count < 10000) {
		// if (container.getEEnumLiteral(baseString + count) == null) {
		// elementToConfigure.setName(baseString + count);
		// foundName = true;
		// }
		// count++;
		// }
	}

	private void setDefaultValueForElement(EEnumLiteral elementToConfigure) {
		EEnum container = elementToConfigure.getEEnum();
		Boolean foundValue = false;
		int count = 0;
		while (!foundValue && count < 10000) {
			EEnumLiteral currentLiteral = container.getEEnumLiteral(count);
			// As the EEnumLiteral value is initialized with 0, we should ensure
			// that the returned EEnumLiteral is not the elementToConfigure
			if (currentLiteral == null || currentLiteral == elementToConfigure) {
				elementToConfigure.setValue(count);
				foundValue = true;
			}
			count++;
		}
	}
}