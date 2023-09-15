/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.ui.action;

import java.util.Collection;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecoretools.design.ui.wizard.InitializeEcoreDiagramWizard;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.google.common.collect.Lists;

public class EcoreInitDiagramFileAction implements IObjectActionDelegate {

	private IWorkbenchPart targetPart;

	private Collection<URI> domainModelURI = Lists.newArrayList();

	private IProject containingProject;

	private IStructuredSelection selection;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		domainModelURI.clear();
		action.setEnabled(false);
		if (selection instanceof IStructuredSelection == false || selection.isEmpty()) {
			return;
		}
		this.selection = (IStructuredSelection) selection;
		if (this.selection.getFirstElement() instanceof IFile) {
			IFile file = (IFile) this.selection.getFirstElement();
			containingProject = file.getProject();
			domainModelURI.add(URI.createPlatformResourceURI(file.getFullPath().toString(), true));
			action.setEnabled(true);
			IFile genmodel = containingProject
					.getFile(file.getProjectRelativePath().removeFileExtension().addFileExtension("genmodel"));
			if (genmodel.exists() && genmodel.isAccessible()) {
				domainModelURI.add(URI.createPlatformResourceURI(genmodel.getFullPath().toString(), true));
			}

		}
	}

	private Shell getShell() {
		return targetPart.getSite().getShell();
	}

	public void run(IAction action) {
		openCreateRepresentationWizard();
	}

	protected void openCreateRepresentationWizard() {
		InitializeEcoreDiagramWizard wizard = new InitializeEcoreDiagramWizard(selection, domainModelURI,
				containingProject);
		wizard.init();
		final WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.setPageSize(750, 400);
		dialog.create();
		dialog.getShell().setText("Create Representation Wizard");
		dialog.open();
	}
}
