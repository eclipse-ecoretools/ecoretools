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
 * $Id: Messages.java,v 1.2 2008/04/28 12:19:08 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.tabbedproperties.internal.utils;

import org.eclipse.osgi.util.NLS;

/**
 * TODO Comment this class
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.ui.internal.forms.Messages"; //$NON-NLS-1$

	private Messages() {
		// Do nothing
	}

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	/** Warning message */
	public static String MessageManager_pWarningSummary;

	/** Error message */
	public static String MessageManager_pErrorSummary;
}