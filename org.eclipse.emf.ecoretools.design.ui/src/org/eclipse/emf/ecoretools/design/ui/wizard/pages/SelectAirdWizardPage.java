/*******************************************************************************
 * Copyright (c) 2016 Obeo
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.ui.wizard.pages;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

/**
 * Wizard page to pick (and create if needed) a .aird file.
 * 
 * @author Cedric Brun <cedric.brun@obeo.fr>
 */
public class SelectAirdWizardPage extends WizardNewFileCreationPage {

	private final String fileExtension;

	private IFile selectedFile;

	/**
	 * Default Constructor to the wizard for session resource creation.
	 * 
	 * @param pageName
	 *            the page name.
	 * @param selection
	 *            the selection
	 * @param fileExtension
	 *            the file extension
	 */
	public SelectAirdWizardPage(final String pageName, final IStructuredSelection selection,
			final String fileExtension) {
		super(pageName, selection);
		this.fileExtension = fileExtension;
		if (selection.getFirstElement() instanceof IFile) {
			selectedFile = (IFile) selection.getFirstElement();
		}
		setAllowExistingResources(true);
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		setFileName(getDefaultFileName() + "." + getExtension());
		setPageComplete(validatePage());
		setErrorMessage(null);
		setMessage(null);

	}

	/**
	 * Get the default file name.
	 * 
	 * @return the default file name
	 */
	public String getDefaultFileName() {
		if (selectedFile != null && selectedFile.getFullPath().removeFileExtension().lastSegment() != null) {
			final String name = selectedFile.getFullPath().removeFileExtension().lastSegment();
			return name;
		}
		return getNoselectionFileName();
	}

	/**
	 * Get the file extension. Override to create files with this extension.
	 * 
	 * @return the file extension
	 */
	protected String getExtension() {
		return fileExtension;
	}

	/**
	 * Get the file path.
	 * 
	 * @return the file path
	 */
	protected IPath getFilePath() {
		IPath path = getContainerFullPath();
		if (path == null) {
			path = new Path(""); //$NON-NLS-1$
		}
		final String fileName = getFileName();
		if (fileName != null) {
			path = path.append(fileName);
		}
		return path;
	}

	/**
	 * Get the default file name to use when there is no selected file.
	 * 
	 * @return the default file name to use when there is no selected file.
	 */
	protected String getNoselectionFileName() {
		return "representations";
	}

	/**
	 * Get the URI.
	 * 
	 * @return the URI
	 */
	public URI getURI() {
		return URI.createPlatformResourceURI(getFilePath().toString(), true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validatePage()
	 */
	@Override
	protected boolean validatePage() {
		boolean result = true;
		if (!super.validatePage()) {
			return false;
		}

		/*
		 * we don't want a warning if the file already exists, just an info.
		 */
		IPath resourcePath = getContainerFullPath().append(getFileName());
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		if (workspace.getRoot().getFile(resourcePath).exists()) {
			setMessage("The representation will be added in the file.", IMessageProvider.INFORMATION);
		} else {
			setMessage("A new file will be created to store the representation.", IMessageProvider.INFORMATION);
		}
		// Validate that entered extension is the expected one.
		final String extension = getExtension();
		if (extension != null && !getFilePath().toString().endsWith("." + extension)) {
			setErrorMessage("The file extension is wrong. It should be " + extension);
			result = false;
		}
		return result;
	}
}
