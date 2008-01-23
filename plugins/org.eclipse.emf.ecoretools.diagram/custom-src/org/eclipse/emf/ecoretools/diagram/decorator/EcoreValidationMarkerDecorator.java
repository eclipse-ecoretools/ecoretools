/***********************************************************************
 * Copyright (c) 2008 Anyware Technologies and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.ecoretools.diagram.decorator;

import org.eclipse.gmf.runtime.diagram.ui.outline.decorator.AbstractValidationMarkerDecorator;
import org.eclipse.gmf.runtime.diagram.ui.outline.decorator.IMarkerReader;

/**
 * An implementation of the AbstractValidationMarkerDecorator applied to the
 * Ecore Tools outline
 */
public class EcoreValidationMarkerDecorator extends AbstractValidationMarkerDecorator {

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.outline.decorator.AbstractValidationMarkerDecorator#createMarkerReader()
	 */
	protected IMarkerReader createMarkerReader() {
		return new EcoreValidationMarkerReader();
	}

}
