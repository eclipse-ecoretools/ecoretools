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
 * $Id: Messages.java,v 1.1 2008/04/28 10:59:51 jlescot Exp $
 **********************************************************************/
package org.eclipse.emf.ecoretools.examples;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.ecoretools.examples.messages"; //$NON-NLS-1$

	public static String EcoreDiagramExampleWizardPage_Browse;

	public static String EcoreDiagramExampleWizardPage_Container;

	public static String EcoreDiagramExampleWizardPage_FileContainerExist;

	public static String EcoreDiagramExampleWizardPage_ProjectWritable;

	public static String EcoreDiagramExampleWizardPage_SelectNewFileContainer;

	public static String EcoreDiagramExampleWizardPage_SpecifyFileContainer;

	public static String EcoreDiagramExampleWizardPage_Wizard_description;

	public static String EcoreDiagramExampleWizardPage_Wizard_title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
