/***********************************************************************
 * Copyright (c) 2008 Anyware Technologies
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 *
 * $Id: Messages.java,v 1.1 2008/05/19 09:26:31 jlescot Exp $
 **********************************************************************/
package org.eclipse.emf.ecoretools.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.ecoretools.internal.messages"; //$NON-NLS-1$

	public static String Activator_Error;

	public static String NewEcoreProjectWizard_CaseSensitive_warning;

	public static String NewEcoreProjectWizard_CreationProblems;

	public static String NewEcoreProjectWizard_EcoreProject_description;

	public static String NewEcoreProjectWizard_EcoreProject_title;

	public static String NewEcoreProjectWizard_InternalError;

	public static String NewEcoreProjectWizard_NewEcoreProject;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
