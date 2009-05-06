/***********************************************************************
 * Copyright (c) 2008 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 * 
 * $Id: Messages.java,v 1.2 2009/05/06 13:54:15 jlescot Exp $
 **********************************************************************/
package org.eclipse.emf.ecoretools.tests.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.ecoretools.tests.internal.messages"; //$NON-NLS-1$

	public static String EmptyEcoreDiagramTestFixture_CreationDiagramError;

	public static String EmptyEcoreDiagramTestFixture_DeletionDiagramError;

	public static String ExistingEcoreDiagramTestFixture_ErrorDuringConversion;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
