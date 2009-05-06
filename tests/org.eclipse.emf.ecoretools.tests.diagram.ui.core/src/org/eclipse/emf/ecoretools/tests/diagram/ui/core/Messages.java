package org.eclipse.emf.ecoretools.tests.diagram.ui.core;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.ecoretools.tests.diagram.ui.core.messages"; //$NON-NLS-1$

	public static String TestDiagramEditorUtil_FileCreation;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
