/***********************************************************************
 * Copyright (c) 2007 Anyware Technologies
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 *
 * $Id: ToggleSynchronizeAction.java,v 1.5 2008/05/19 09:26:31 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.ui.actions;

import org.eclipse.emf.ecoretools.ui.Activator;
import org.eclipse.emf.ecoretools.ui.Messages;
import org.eclipse.emf.ecoretools.ui.views.AnalysisView;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Action that activates the synchronization of an Analysis view.
 * 
 * @author <a href="david.sciamma@anyware-tech.com">David Sciamma</a>
 */
public class ToggleSynchronizeAction extends Action {

	private AnalysisView analysisView;

	/**
	 * Constructor
	 * 
	 * @param view
	 *            the AnalysisView
	 */
	public ToggleSynchronizeAction(AnalysisView view) {
		super(Messages.ToggleSynchronizeAction_Synchronize);
		setDescription(Messages.ToggleSynchronizeAction_Synchronize_description);
		setToolTipText(Messages.ToggleSynchronizeAction_Synchronize_tooltip);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/elcl16/synced.gif")); //$NON-NLS-1$
		setDisabledImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/dlcl16/synced.gif")); //$NON-NLS-1$
		setChecked(view.isSynchronized());

		analysisView = view;
	}

	/**
	 * Runs the action.
	 */
	@Override
	public void run() {
		analysisView.setSynchronized(isChecked());
	}
}
