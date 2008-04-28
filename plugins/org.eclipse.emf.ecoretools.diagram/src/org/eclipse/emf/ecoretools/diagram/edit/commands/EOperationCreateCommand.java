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
 * $Id: EOperationCreateCommand.java,v 1.2 2008/04/28 08:41:32 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.diagram.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class EOperationCreateCommand extends CreateElementCommand {

	/**
	 * @generated
	 */
	public EOperationCreateCommand(CreateElementRequest req) {
		super(req);
	}

	/**
	 * @generated
	 */
	@Override
	protected EClass getEClassToEdit() {
		return EcorePackage.eINSTANCE.getEClass();
	}

	/**
	 * @generated
	 */
	@Override
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest()).getContainer();
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		return container;
	}

}
