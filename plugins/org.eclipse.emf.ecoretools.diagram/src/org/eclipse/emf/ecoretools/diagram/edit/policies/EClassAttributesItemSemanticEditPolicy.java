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
 *
 * $Id: EClassAttributesItemSemanticEditPolicy.java,v 1.2 2008/04/28 08:41:31 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.diagram.edit.policies;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecoretools.diagram.edit.commands.EAttributeCreateCommand;
import org.eclipse.emf.ecoretools.diagram.providers.EcoreElementTypes;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

/**
 * @generated
 */
public class EClassAttributesItemSemanticEditPolicy extends EcoreBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	@Override
	protected Command getCreateCommand(CreateElementRequest req) {
		if (EcoreElementTypes.EAttribute_2001 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(EcorePackage.eINSTANCE.getEClass_EStructuralFeatures());
			}
			return getGEFWrapper(new EAttributeCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}
}
