/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.ui.action;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecoretools.design.EcoreToolsViewpoints;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.sirius.business.api.modelingproject.ModelingProject;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ui.business.api.session.IEditingSession;
import org.eclipse.sirius.ui.business.api.session.SessionUIManager;
import org.eclipse.sirius.ui.business.api.viewpoint.ViewpointSelectionCallback;
import org.eclipse.sirius.ui.tools.api.project.ModelingProjectManager;
import org.eclipse.sirius.ui.tools.internal.views.common.modelingproject.OpenRepresentationsFileJob;
import org.eclipse.sirius.ui.tools.internal.wizards.CreateRepresentationWizard;
import org.eclipse.sirius.ui.tools.internal.wizards.CreateSessionResourceWizard;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public class EcoreInitDiagramFileAction implements IObjectActionDelegate {

    private IWorkbenchPart targetPart;

    private URI domainModelURI;

    private IProject containingProject;

    private IStructuredSelection selection;

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        this.targetPart = targetPart;
    }

    public void selectionChanged(IAction action, ISelection selection) {
        domainModelURI = null;
        action.setEnabled(false);
        if (selection instanceof IStructuredSelection == false || selection.isEmpty()) {
            return;
        }
        this.selection = (IStructuredSelection) selection;
        if (this.selection.getFirstElement() instanceof IFile) {
            IFile file = (IFile) this.selection.getFirstElement();
            containingProject = file.getProject();
            domainModelURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
            action.setEnabled(true);
        }
    }

    private Shell getShell() {
        return targetPart.getSite().getShell();
    }

    public void run(IAction action) {
        Session existingSession = null;

        for (Session session : SessionManager.INSTANCE.getSessions()) {
            ResourceSet set = session.getTransactionalEditingDomain().getResourceSet();
            for (Resource res : set.getResources()) {
                if (res.getURI() != null) {
                    if (set.getURIConverter().normalize(res.getURI()).equals(set.getURIConverter().normalize(domainModelURI))) {
                        existingSession = session;
                    }
                }
            }
        }
        if (existingSession == null) {
            /*
             * we could not find a session already having this file in its
             * resources. We will use the containing project if it is a modeling
             * project but we have to add the resource as a semantic resource.
             */
            final Option<ModelingProject> prj = ModelingProject.asModelingProject(containingProject);
            if (prj.some()) {
                existingSession = prj.get().getSession();
                if (existingSession == null) {
                    Option<URI> optionalMainSessionFileURI = prj.get().getMainRepresentationsFileURI(new NullProgressMonitor(), false, false);
                    if (optionalMainSessionFileURI.some()) {
                        // Load the main representations file of this modeling
                        // project if it's not already loaded or during loading.
                        ModelingProjectManager.INSTANCE.loadAndOpenRepresentationsFile(optionalMainSessionFileURI.get());
                    }
                }
                if (OpenRepresentationsFileJob.shouldWaitOtherJobs()) {
                    // We are loading session(s), wait loading is finished
                    // before continuing.
                    OpenRepresentationsFileJob.waitOtherJobs();
                }
                existingSession = prj.get().getSession();
                if (existingSession != null) {
                    existingSession.getTransactionalEditingDomain().getCommandStack().execute(new RecordingCommand(existingSession.getTransactionalEditingDomain()) {

                        @Override
                        protected void doExecute() {
                            prj.get().getSession().addSemanticResource(domainModelURI, new NullProgressMonitor());

                        }
                    });
                    existingSession.addSemanticResource(domainModelURI, new NullProgressMonitor());
                }

            }
        }

        if (existingSession == null) {
            existingSession = createSession();
        }

        if (existingSession != null) {
            openCreateRepresentationWizard(existingSession);
        }

    }

    private Session createSession() {
        final CreateSessionResourceWizard wizard = new CreateSessionResourceWizard(selection);
        wizard.init(PlatformUI.getWorkbench(), selection);
        final WizardDialog dialog = new WizardDialog(getShell(), wizard);
        dialog.create();
        dialog.getShell().setText("Create Representation File");
        if (dialog.open() == Dialog.OK) {

            wizard.getCreatedSession().getTransactionalEditingDomain().getCommandStack().execute(new RecordingCommand(wizard.getCreatedSession().getTransactionalEditingDomain()) {

                @Override
                protected void doExecute() {
                    wizard.getCreatedSession().addSemanticResource(domainModelURI, new NullProgressMonitor());

                }
            });
            return wizard.getCreatedSession();
        }
        return null;

    }

    protected void openCreateRepresentationWizard(final Session existingSession) {
        /*
         * Workaround to last minute NPE in
         * org.eclipse.sirius.ui.tools.internal.
         * actions.creation.CreateRepresentationAction$1
         * .run(CreateRepresentationAction.java:126) We are forcing to create
         * the uiSession so that the wizard does not fail. To reproduce the
         * problem you have to use this action on an .ecore file from the Java
         * perspective.
         */
        IEditingSession uiSession = SessionUIManager.INSTANCE.getOrCreateUISession(existingSession);
        uiSession.open();
        existingSession.getTransactionalEditingDomain().getCommandStack().execute(new RecordingCommand(existingSession.getTransactionalEditingDomain()) {

            @Override
            protected void doExecute() {
                Set<Viewpoint> fromEcoreTools = EcoreToolsViewpoints.fromViewpointRegistry().all();
                ViewpointSelectionCallback selection = new ViewpointSelectionCallback();
                for (Viewpoint previouslySelected : existingSession.getSelectedViewpoints(false)) {
                    if (fromEcoreTools.contains(previouslySelected)) {
                        selection.deselectViewpoint(previouslySelected, existingSession, new NullProgressMonitor());
                    }
                }
                selection.selectViewpoint(EcoreToolsViewpoints.fromViewpointRegistry().design(), existingSession, new NullProgressMonitor());
                selection.selectViewpoint(EcoreToolsViewpoints.fromViewpointRegistry().review(), existingSession, new NullProgressMonitor());
                selection.selectViewpoint(EcoreToolsViewpoints.fromViewpointRegistry().archetype(), existingSession, new NullProgressMonitor());
                selection.selectViewpoint(EcoreToolsViewpoints.fromViewpointRegistry().generation(), existingSession, new NullProgressMonitor());
            }
        });

        CreateRepresentationWizard wizard = new CreateRepresentationWizard(existingSession);
        wizard.init();
        final WizardDialog dialog = new WizardDialog(getShell(), wizard);
        dialog.create();
        dialog.getShell().setText("Create Representation Wizard");
        dialog.open();
    }
}
