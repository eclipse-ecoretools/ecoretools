/*******************************************************************************
 * Copyright (c) 2016 Obeo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.ui.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecoretools.design.EcoreToolsViewpoints;
import org.eclipse.emf.ecoretools.design.ui.wizard.pages.SelectAirdWizardPage;
import org.eclipse.emf.ecoretools.design.ui.wizard.pages.SelectRepresentationDescriptionWizardPage;
import org.eclipse.emf.ecoretools.design.ui.wizard.pages.SelectRootSemanticElementWizardPage;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.sirius.business.api.dialect.command.CreateRepresentationCommand;
import org.eclipse.sirius.business.api.helper.SiriusUtil;
import org.eclipse.sirius.business.api.modelingproject.AbstractRepresentationsFileJob;
import org.eclipse.sirius.business.api.modelingproject.ModelingProject;
import org.eclipse.sirius.business.api.session.DefaultLocalSessionCreationOperation;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionCreationOperation;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.common.ui.SiriusTransPlugin;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ui.business.api.dialect.DialectUIManager;
import org.eclipse.sirius.ui.business.api.session.EditingSessionEvent;
import org.eclipse.sirius.ui.business.api.session.IEditingSession;
import org.eclipse.sirius.ui.business.api.session.SessionUIManager;
import org.eclipse.sirius.ui.business.api.viewpoint.ViewpointSelectionCallback;
import org.eclipse.sirius.ui.tools.api.project.ModelingProjectManager;
import org.eclipse.sirius.ui.tools.internal.views.common.modelingproject.OpenRepresentationsFileJob;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.sirius.viewpoint.provider.Messages;
import org.eclipse.sirius.viewpoint.provider.SiriusEditPlugin;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.google.common.base.Supplier;
import com.google.common.collect.Sets;

public class InitializeEcoreDiagramWizard extends Wizard {

	private SelectAirdWizardPage airdFilePage;

	private IProject containingProject;

	private DRepresentation createdDRepresentation;

	private Collection<URI> domainModelURI;

	private Session existingSession = null;

	private SelectRepresentationDescriptionWizardPage representationWizardPage;

	private SelectRootSemanticElementWizardPage selectElementPage;

	private IStructuredSelection selection;

	/**
	 * Constructor.
	 * 
	 * @param selection
	 *            currently selected file.
	 */
	public InitializeEcoreDiagramWizard(IStructuredSelection selection, Collection<URI> domainModelURI,
			IProject containingProject) {
		this.selection = selection;
		this.domainModelURI = domainModelURI;
		this.containingProject = containingProject;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		airdFilePage = new SelectAirdWizardPage("DiagramModelFile", selection, SiriusUtil.SESSION_RESOURCE_EXTENSION);
		airdFilePage.setDescription("Pick an .aird file which will store the diagram, tree, or tables related data.");
		final Option<ModelingProject> prj = ModelingProject.asModelingProject(containingProject);
		final boolean userMightSelectAird = !prj.some();
		if (userMightSelectAird) {
			/*
			 * the modeling project does not allow for several .aird files,as
			 * such we should not even ask the user which file to pick.
			 */
			addPage(airdFilePage);
		}
		Supplier<Session> sSupplier = new Supplier<Session>() {

			public Session get() {
				if (existingSession == null) {
					for (Session session : SessionManager.INSTANCE.getSessions()) {
						ResourceSet set = session.getTransactionalEditingDomain().getResourceSet();
						for (Resource res : set.getResources()) {
							if (res.getURI() != null) {

								for (URI uri : domainModelURI) {
									if (set.getURIConverter().normalize(res.getURI())
											.equals(set.getURIConverter().normalize(uri))) {
										existingSession = session;
									}

								}
							}
						}
					}
					if (existingSession == null) {
						/*
						 * we could not find a session already having this file
						 * in its resources. We will use the containing project
						 * if it is a modeling project but we have to add the
						 * resource as a semantic resource.
						 */
						final Option<ModelingProject> prj = ModelingProject.asModelingProject(containingProject);
						if (prj.some()) {
							existingSession = prj.get().getSession();
							if (existingSession == null) {
								Option<URI> optionalMainSessionFileURI = prj.get()
										.getMainRepresentationsFileURI(new NullProgressMonitor(), false, false);
								if (optionalMainSessionFileURI.some()) {
									/*
									 * Load the main representations file of
									 * this modeling project if it's not already
									 * loaded or during loading.
									 */
									ModelingProjectManager.INSTANCE
											.loadAndOpenRepresentationsFile(optionalMainSessionFileURI.get());
								}
							}
							if (OpenRepresentationsFileJob.shouldWaitOtherJobs()) {
								/*
								 * We are loading session(s), wait loading is
								 * finished before continuing.
								 */
								try {
									Job.getJobManager().join(AbstractRepresentationsFileJob.FAMILY,
											new NullProgressMonitor());
								} catch (InterruptedException e) {
									// Do nothing
								}
							}
							existingSession = prj.get().getSession();
							if (existingSession != null) {
								existingSession.getTransactionalEditingDomain().getCommandStack()
										.execute(new RecordingCommand(existingSession.getTransactionalEditingDomain()) {

											@Override
											protected void doExecute() {
												for (URI uri : domainModelURI) {
													prj.get().getSession().addSemanticResource(uri,
															new NullProgressMonitor());
												}

											}
										});

							}

						}
					}

					if (existingSession == null || (userMightSelectAird && pickedAnotherAird(existingSession))) {

						WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

							@Override
							protected void execute(IProgressMonitor monitor)
									throws CoreException, InvocationTargetException, InterruptedException {
								SessionCreationOperation sessionCreationOperation = new DefaultLocalSessionCreationOperation(
										airdFilePage.getURI(), monitor);
								sessionCreationOperation.execute();
								existingSession = sessionCreationOperation.getCreatedSession();
							}
						};
						try {
							getContainer().run(false, true, op);
						} catch (final InterruptedException e) {
						} catch (final InvocationTargetException e) {
							if (e.getTargetException() instanceof CoreException) {
								ErrorDialog.openError(getContainer().getShell(),
										Messages.CreateSessionResourceWizard_resourceCreationError, null,
										((CoreException) e.getTargetException()).getStatus());
							} else {
								SiriusTransPlugin.getPlugin().error(
										Messages.CreateSessionResourceWizard_sessionDataCreationError,
										e.getTargetException());
							}
						}

					}

					if (existingSession != null)

					{

						existingSession.getTransactionalEditingDomain().getCommandStack()
								.execute(new RecordingCommand(existingSession.getTransactionalEditingDomain()) {

									@Override
									protected void doExecute() {
										for (URI uri : domainModelURI) {
											existingSession.addSemanticResource(uri, new NullProgressMonitor());
										}

									}
								});
						/*
						 * Workaround to last minute NPE in
						 * org.eclipse.sirius.ui.tools.internal.
						 * actions.creation.CreateRepresentationAction$1
						 * .run(CreateRepresentationAction.java:126) We are
						 * forcing to create the uiSession so that the wizard
						 * does not fail. To reproduce the problem you have to
						 * use this action on an .ecore file from the Java
						 * perspective.
						 */
						IEditingSession uiSession = SessionUIManager.INSTANCE.getOrCreateUISession(existingSession);
						uiSession.open();
						existingSession.getTransactionalEditingDomain().getCommandStack()
								.execute(new RecordingCommand(existingSession.getTransactionalEditingDomain()) {

									@Override
									protected void doExecute() {
										Set<Viewpoint> fromEcoreTools = EcoreToolsViewpoints.fromViewpointRegistry()
												.all();
										ViewpointSelectionCallback selection = new ViewpointSelectionCallback();
										for (Viewpoint previouslySelected : existingSession
												.getSelectedViewpoints(false)) {
											if (fromEcoreTools.contains(previouslySelected)) {
												selection.deselectViewpoint(previouslySelected, existingSession,
														new NullProgressMonitor());
											}
										}
										selectViewpoint(selection,
												EcoreToolsViewpoints.fromViewpointRegistry().design(), existingSession,
												new NullProgressMonitor());
										selectViewpoint(selection,
												EcoreToolsViewpoints.fromViewpointRegistry().review(), existingSession,
												new NullProgressMonitor());
										selectViewpoint(selection,
												EcoreToolsViewpoints.fromViewpointRegistry().archetype(),
												existingSession, new NullProgressMonitor());
										selectViewpoint(selection,
												EcoreToolsViewpoints.fromViewpointRegistry().generation(),
												existingSession, new NullProgressMonitor());
									}

									private void selectViewpoint(ViewpointSelectionCallback selection, Viewpoint vp,
											Session session, NullProgressMonitor progressMonitor) {
										if (vp != null) {
											selection.selectViewpoint(vp, session, progressMonitor);
										}
									}
								});
					}
				}
				return existingSession;
			}

		};
		representationWizardPage = new SelectRepresentationDescriptionWizardPage(sSupplier,
				Sets.newHashSet("Entities"));
		selectElementPage = new SelectRootSemanticElementWizardPage(sSupplier, representationWizardPage);

		addPage(representationWizardPage);
		addPage(selectElementPage);
	}

	private boolean pickedAnotherAird(Session existingSession) {
		if (existingSession.getSessionResource() != null) {
			URI sessURI = existingSession.getSessionResource().getURI();
			URIConverter conv = existingSession.getTransactionalEditingDomain().getResourceSet().getURIConverter();
			if (sessURI != null && conv != null) {
				return !conv.normalize(airdFilePage.getURI()).equals(conv.normalize(sessURI));
			}

		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		if (existingSession != null && representationWizardPage.get() != null) {
			if (selectElementPage.getSelectedElement() != null) {
				return true;
			}
		}

		return super.canFinish();
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		super.dispose();
		selectElementPage.dispose();
		representationWizardPage.dispose();
		airdFilePage.dispose();
	}

	/**
	 * Initialize the wizard.
	 */
	public void init() {
		setWindowTitle("Create Representation");
		setNeedsProgressMonitor(true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		EObject element = selectElementPage.getSelectedElement();
		if (element != null && representationWizardPage.get() != null) {

			try {
				final String representationName = selectElementPage.get();

				IRunnableWithProgress representationCreationRunnable = new IRunnableWithProgress() {

					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						try {
							monitor.beginTask(
									org.eclipse.sirius.viewpoint.provider.Messages.CreateRepresentationAction_creationTask,
									5);
							CreateRepresentationCommand createRepresentationCommand = new CreateRepresentationCommand(
									existingSession, representationWizardPage.get(),
									selectElementPage.getSelectedElement(), representationName,
									new SubProgressMonitor(monitor, 4));

							IEditingSession editingSession = SessionUIManager.INSTANCE.getUISession(existingSession);
							editingSession
									.notify(EditingSessionEvent.REPRESENTATION_ABOUT_TO_BE_CREATED_BEFORE_OPENING);
							existingSession.getTransactionalEditingDomain().getCommandStack()
									.execute(createRepresentationCommand);
							editingSession.notify(EditingSessionEvent.REPRESENTATION_CREATED_BEFORE_OPENING);
							createdDRepresentation = createRepresentationCommand.getCreatedRepresentation();
							monitor.worked(1);
						} finally {
							monitor.done();
						}
					}
				};

				getContainer().run(true, false, representationCreationRunnable);
				IRunnableWithProgress runnable = new IRunnableWithProgress() {

					public void run(final IProgressMonitor monitor) {
						try {
							monitor.beginTask(
									org.eclipse.sirius.viewpoint.provider.Messages.CreateRepresentationAction_openingTask,
									1);
							DialectUIManager.INSTANCE.openEditor(existingSession, createdDRepresentation,
									new SubProgressMonitor(monitor, 1));
						} finally {
							monitor.done();
						}
					}

				};
				Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				IRunnableContext context = new ProgressMonitorDialog(shell);
				PlatformUI.getWorkbench().getProgressService().runInUI(context, runnable, null);
			} catch (final InvocationTargetException e) {
				SiriusEditPlugin.getPlugin().getLog()
						.log(new Status(IStatus.ERROR, SiriusEditPlugin.ID, e.getLocalizedMessage(), e));
			} catch (final InterruptedException e) {
				SiriusEditPlugin.getPlugin().getLog()
						.log(new Status(IStatus.ERROR, SiriusEditPlugin.ID, e.getLocalizedMessage(), e));
			}

		}
		return true;
	}

}
