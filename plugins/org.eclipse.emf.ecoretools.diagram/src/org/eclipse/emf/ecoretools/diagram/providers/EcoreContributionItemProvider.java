/*
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
 * $Id: EcoreContributionItemProvider.java,v 1.2 2008/04/28 08:41:30 jlescot Exp $
 */
package org.eclipse.emf.ecoretools.diagram.providers;

import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.printing.actions.PrintPreviewAction;
import org.eclipse.gmf.runtime.diagram.ui.printing.render.actions.EnhancedPrintActionHelper;
import org.eclipse.gmf.runtime.diagram.ui.printing.render.actions.RenderedPrintPreviewAction;
import org.eclipse.jface.action.IAction;

/**
 * @generated
 */
public class EcoreContributionItemProvider extends AbstractContributionItemProvider {

	/**
	 * @generated
	 */
	@Override
	protected IAction createAction(String actionId, IWorkbenchPartDescriptor partDescriptor) {
		if (actionId.equals(PrintPreviewAction.ID)) {
			return new RenderedPrintPreviewAction(new EnhancedPrintActionHelper());
		}
		return super.createAction(actionId, partDescriptor);
	}
}
