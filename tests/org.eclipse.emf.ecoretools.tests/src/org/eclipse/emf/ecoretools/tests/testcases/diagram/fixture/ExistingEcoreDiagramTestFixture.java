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
 * $Id: ExistingEcoreDiagramTestFixture.java,v 1.3 2008/05/19 09:25:18 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.tests.testcases.diagram.fixture;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecoretools.tests.internal.Activator;
import org.eclipse.emf.ecoretools.tests.internal.Messages;
import org.eclipse.gmf.tests.runtime.diagram.ui.core.fixture.AbstractExistingDiagramTestFixture;

/**
 * a fixture to make test on an existing ecore diagram
 * 
 * @author Simon Bernard
 */
public abstract class ExistingEcoreDiagramTestFixture extends AbstractExistingDiagramTestFixture {

	private URL sourceDiagramURL;

	private URL sourceModelURL;

	/**
	 * @see org.eclipse.gmf.tests.runtime.diagram.ui.core.fixture.AbstractExistingDiagramTestFixture#getSourceDiagramURL()
	 */
	public URL getSourceDiagramURL() {
		if (sourceDiagramURL == null) {
			sourceDiagramURL = pathToFileURL(getEcoreDiagramPath());
		}
		return sourceDiagramURL;
	}

	/**
	 * @see org.eclipse.gmf.tests.runtime.diagram.ui.core.fixture.AbstractExistingDiagramTestFixture#getSourceModelURL()
	 */
	public URL getSourceModelURL() {
		if (sourceModelURL == null) {
			sourceModelURL = pathToFileURL(getEcoreModelPath());
		}
		return sourceModelURL;
	}

	private URL pathToFileURL(String path) {
		URL url = Activator.getDefault().getBundle().getEntry(path);

		try {
			return FileLocator.toFileURL(url);
		} catch (IOException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.ExistingEcoreDiagramTestFixture_ErrorDuringConversion, e));
		}
		return null;
	}

	/**
	 * @see org.eclipse.gmf.tests.runtime.diagram.ui.core.fixture.AbstractExistingDiagramTestFixture#getProjectName()
	 */
	public String getProjectName() {
		return "ecoreProj"; //$NON-NLS-1$
	}

	/**
	 * Get the Path as a String corresponding to the Domain model file
	 * 
	 * @return String domain model path
	 */
	public abstract String getEcoreModelPath();

	/**
	 * Get the Path as a String corresponding to the Diagram model file
	 * 
	 * @return String diagram model path
	 */
	public abstract String getEcoreDiagramPath();

}
