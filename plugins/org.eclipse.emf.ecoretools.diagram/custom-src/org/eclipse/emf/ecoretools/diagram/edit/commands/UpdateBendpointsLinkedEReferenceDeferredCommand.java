/**
 * Copyright (c) 2008 {INITIAL COPYRIGHT OWNER}
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 */
package org.eclipse.emf.ecoretools.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EReferenceEditPart;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.util.EditPartUtil;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Bendpoints;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.NotationFactory;

/**
 * 
 * TODO Describe the class here <br>
 * creation : 17 janv. 08
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class UpdateBendpointsLinkedEReferenceDeferredCommand extends AbstractTransactionalCommand {

	private EReferenceEditPart part1;

	private EReferenceEditPart part2;

	public UpdateBendpointsLinkedEReferenceDeferredCommand(TransactionalEditingDomain domain, EReferenceEditPart part1, EReferenceEditPart part2) {
		super(domain, "LinkEReferenceDeferredCommand", null);
		this.part1 = part1;
		this.part2 = part2;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (part1 == null || part2 == null) {
			return CommandResult.newWarningCommandResult("Unable to proceed with null parts", null);
		}
		RunnableWithResult refreshRunnable = new RunnableWithResult() {

			private IStatus status;

			private Object result;

			public Object getResult() {
				return result;
			}

			public void setStatus(IStatus status) {
				this.status = status;
			}

			public IStatus getStatus() {
				return status;
			}

			public void run() {
				if (false == part1.getNotationView() instanceof Edge || false == part2.getNotationView() instanceof Edge) {
					return;
				}
				Edge edge1 = (Edge) part1.getNotationView();
				Edge edge2 = (Edge) part2.getNotationView();
				if (edge1.getSourceAnchor() == null) {
					edge1.setSourceAnchor(NotationFactory.eINSTANCE.createIdentityAnchor());
				}
				if (edge1.getTargetAnchor() == null) {
					edge1.setTargetAnchor(NotationFactory.eINSTANCE.createIdentityAnchor());
				}

				edge2.setBendpoints((Bendpoints) EcoreUtil.copy(edge1.getBendpoints()));
			}
		};

		EditPartUtil.synchronizeRunnableToMainThread(part1, refreshRunnable);
		return CommandResult.newOKCommandResult();
	}
}
