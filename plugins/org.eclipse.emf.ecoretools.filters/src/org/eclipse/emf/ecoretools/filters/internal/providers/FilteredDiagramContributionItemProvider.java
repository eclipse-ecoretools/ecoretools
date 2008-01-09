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
package org.eclipse.emf.ecoretools.filters.internal.providers;

import org.eclipse.emf.ecoretools.filters.internal.actions.ApplyFilterDiagramAction;
import org.eclipse.emf.ecoretools.filters.internal.actions.DiagramFilterActionMenu;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.jface.action.IAction;


/**
 * 
 * TODO Describe the class here <br>
 * creation : 13 nov. 07
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class FilteredDiagramContributionItemProvider extends
		AbstractContributionItemProvider {

	@Override
	protected IAction createAction(String actionId,
			IWorkbenchPartDescriptor partDescriptor) {
		if (actionId.equals(DiagramFilterActionMenu.ID)) {
			return new DiagramFilterActionMenu();
		}
		if (actionId.equals(ApplyFilterDiagramAction.ID)) {
			return new ApplyFilterDiagramAction();
		}
		return super.createAction(actionId, partDescriptor);
	}

}
