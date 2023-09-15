/***********************************************************************
 * Copyright (c) 2008 Anyware Technologies
 * Copyright (c) 2015 IRT AESE (IRT Saint Exupery)
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    David Sciamma / Jacques Lescot (Anyware Technologies) - initial API and implementation
 *    Pierre Gaufillet (IRT Saint Exupery)                  - Extension to a general purpose 
 *                                                            EClass information view
 *
 * $Id: Messages.java,v 1.1 2008/05/19 09:26:31 jlescot Exp $
 **********************************************************************/
package org.eclipse.emf.ecoretools.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.ecoretools.ui.messages"; //$NON-NLS-1$

	public static String Activator_Error;

	public static String EClassHierarchyView_Ascendant;

	public static String EClassHierarchyView_Descendant;

	public static String EClassHierarchyView_RefreshHierarchy;

	public static String EReferencesView_RefreshReferences;

	public static String RefreshAction_Refresh;

	public static String RefreshAction_Refresh_description;

	public static String RefreshAction_Refresh_tooltip;

	public static String ToggleSynchronizeAction_Synchronize;

	public static String ToggleSynchronizeAction_Synchronize_description;

	public static String ToggleSynchronizeAction_Synchronize_tooltip;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
