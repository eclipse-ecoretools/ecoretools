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
package org.eclipse.emf.ecoretools.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecoretools.diagram.part.Messages;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * 
 * TODO Describe the class here <br>
 * creation : 2 janv. 2008
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles Cannenterre</a>
 */
public class UpdateEditPartCommand extends AbstractTransactionalCommand {

	private EditPart part;

	public UpdateEditPartCommand(TransactionalEditingDomain domain, EditPart part) {
		super(domain, Messages.CommandName_UpdateEditPart, null);
		this.part = part;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		EditPolicy editPolicy = part.getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
		if (editPolicy instanceof CanonicalEditPolicy)
		{
			((CanonicalEditPolicy)editPolicy).refresh();
		}
		return CommandResult.newOKCommandResult();
	}
	
}
