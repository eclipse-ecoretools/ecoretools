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
 * $Id: EcoreDiagramExampleWizard.java,v 1.2 2008/04/28 10:59:51 jlescot Exp $
 **********************************************************************/
package org.eclipse.emf.ecoretools.examples.internal.wizard;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecoretools.examples.internal.EcoreToolsExamplesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.IDE;

/**
 * This wizard create a new Ecore.ecore and Ecore.ecorediag files into the
 * selected container path.
 * 
 * Creation : 5 Dec. 2007
 * 
 * @author <a href="mailto:jacques.lescot@anyware-tech.com">Jacques LESCOT</a>
 */
public class EcoreDiagramExampleWizard extends Wizard implements INewWizard {

	private IWorkbench workbench;

	private IStructuredSelection selection;

	private EcoreDiagramExampleWizardPage directorySelectionPage;

	/**
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench aWorkbench, IStructuredSelection currentSelection) {
		workbench = aWorkbench;
		selection = currentSelection;
	}

	/**
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	public void addPages() {
		directorySelectionPage = new EcoreDiagramExampleWizardPage(selection);
		addPage(directorySelectionPage);
	}

	/**
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() {
		IFile domainFile = createDomainFile();
		IFile diagramFile = createDiagramFile();

		if (domainFile == null || diagramFile == null)
			return false; // ie.- creation was unsuccessful

		// Since the file resource was created fine, open it for editing
		// if requested by the user
		try {
			IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = dwindow.getActivePage();
			if (page != null)
				IDE.openEditor(page, diagramFile, true);
		} catch (org.eclipse.ui.PartInitException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private IFile createDomainFile() {
		IContainer container = (IContainer) ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(directorySelectionPage.getContainerName()));
		URL pluginFileURL = EcoreToolsExamplesPlugin.getDefault().getBundle().getEntry("/models/Ecore.ecore"); //$NON-NLS-1$
		if (pluginFileURL != null) {
			try {
				InputStream inStream = pluginFileURL.openStream();
				// create the IFile for the project file
				container.getFile(new Path("Ecore.ecore")).create(inStream, true, null); //$NON-NLS-1$
				inStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return container.getFile(new Path("Ecore.ecore")); //$NON-NLS-1$
	}

	private IFile createDiagramFile() {
		IContainer container = (IContainer) ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(directorySelectionPage.getContainerName()));
		URL pluginFileURL = EcoreToolsExamplesPlugin.getDefault().getBundle().getEntry("/models/Ecore.ecorediag"); //$NON-NLS-1$
		if (pluginFileURL != null) {
			try {
				InputStream inStream = pluginFileURL.openStream();
				// create the IFile for the project file
				container.getFile(new Path("Ecore.ecorediag")).create(inStream, true, null); //$NON-NLS-1$
				inStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return container.getFile(new Path("Ecore.ecorediag")); //$NON-NLS-1$
	}

}
