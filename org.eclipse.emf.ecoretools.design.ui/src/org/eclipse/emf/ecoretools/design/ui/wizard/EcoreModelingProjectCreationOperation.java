/*******************************************************************************
 * Copyright (c) 2013, 2024 THALES GLOBAL SERVICES and others
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.ui.wizard;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.codegen.ecore.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecoretools.design.service.EcoreToolsDesignPlugin;
import org.eclipse.emf.importer.ecore.EcoreImporter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.modelingproject.ModelingProject;
import org.eclipse.sirius.business.api.session.DefaultLocalSessionCreationOperation;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionCreationOperation;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.tools.api.command.semantic.AddSemanticResourceCommand;
import org.eclipse.sirius.ui.business.api.viewpoint.ViewpointSelectionCallback;
import org.eclipse.sirius.ui.business.internal.commands.ChangeViewpointSelectionCommand;
import org.eclipse.sirius.ui.tools.api.project.ModelingProjectManager;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * A {@link WorkspaceModifyOperation} to create a new Ecore Modeling Project
 * from a Empty EMF project.
 * 
 * @author <a href="mailto:esteban.dugueperoux@obeo.fr">Esteban Dugueperoux</a>
 */
@SuppressWarnings("restriction")
public class EcoreModelingProjectCreationOperation extends WorkspaceModifyOperation {

	private IProject project;

	private EObject rootObject;

	private String ecoreResourceName;

	private String genModelResourceName;

	private String representationsResourceName;

	private Set<Viewpoint> selectedViewpoints;

	private IFile ecoreModel;

	private IWorkbench workbench;

	private IPath genModelContainerPath;

	private IPath genModelProjectLocation;

	private IWorkingSet[] selectedWorkingSets;

	private Set<DRepresentation> createdRepresentations =  new LinkedHashSet<>();

	/**
	 * Default constructor.
	 * 
	 * @param project
	 *            the empty EMF project
	 * @param rootObject
	 *            the root object of the semantic resource (i.e. the metamodel)
	 * @param representationsResourceName
	 *            the name of the representations resource
	 * @param selectedViewpoints
	 *            the set of {@link Viewpoint} to have selected on this created
	 *            Modeling Project
	 */
	public EcoreModelingProjectCreationOperation(EObject rootObject, String ecoreResourceName,
			String genModelResourceName, String representationsResourceName, Set<Viewpoint> selectedViewpoints,
			IWorkbench workbench, IPath genModelContainerPath, IPath genModelProjectLocation,
			IWorkingSet[] selectedWorkingSets) {
		super();
		this.workbench = workbench;
		this.rootObject = rootObject;
		this.ecoreResourceName = ecoreResourceName;
		this.genModelContainerPath = genModelContainerPath;
		this.genModelProjectLocation = genModelProjectLocation;
		this.genModelResourceName = genModelResourceName;
		this.representationsResourceName = representationsResourceName;
		this.selectedViewpoints = selectedViewpoints;
		this.selectedWorkingSets = selectedWorkingSets;
	}

	@Override
	protected void execute(final IProgressMonitor monitor)
			throws CoreException, InvocationTargetException, InterruptedException {
		try {
			monitor.beginTask("Create modeling resources: ", 100); //$NON-NLS-1$
			try {
				modifyWorkspace(monitor);
			} catch (UnsupportedEncodingException e) {
				logError(e);
			} catch (IOException e) {
				logError(e);
			}
			/*
			 * Create modeling resources: ecore, genmodel and aird : 50
			 */
			final Session createdSession = createModelingResources(project, monitor);
			if (createdSession != null) {
				/*
				 * Create an entities diagram for the EPackage we just created.
				 */

				URI ecoreUri = URI.createPlatformResourceURI(ecoreModel.getFullPath().toOSString(), true);
				Resource ecoreRes = null;
				Iterator<Resource> it = createdSession.getSemanticResources().iterator();
				while (ecoreRes == null && it.hasNext()) {
					Resource sem = it.next();
					if (ecoreUri.equals(sem.getURI())) {
						ecoreRes = sem;
					}
				}
				if (ecoreRes != null) {
                    for (EObject root : ecoreRes.getContents()) {
                        if (root instanceof EPackage ePackage) {
                            final RepresentationDescription entities = findRepresentationDescription(ePackage, "Entities", createdSession);
                            if (entities != null) {
                                createdSession.getTransactionalEditingDomain().getCommandStack().execute(new RecordingCommand(createdSession.getTransactionalEditingDomain()) {

                                    @Override
                                    protected void doExecute() {
                                        DRepresentation created = DialectManager.INSTANCE.createRepresentation(ePackage.getName(), ePackage, entities, createdSession, monitor);
                                        if (created != null) {
                                            createdRepresentations.add(created);
                                        }
                                    }
                                });
                                createdSession.save(monitor);
                            }
                        }
                    }
				}
			}
			monitor.subTask("prepare the modeling project..."); //$NON-NLS-1$
			convertToModelingProject(new SubProgressMonitor(monitor, 20));
		} finally {
			monitor.done();
		}
	}

	private RepresentationDescription findRepresentationDescription(final EPackage ePackage, String descriptionName,
			Session session) {
		RepresentationDescription found = null;
		Iterator<RepresentationDescription> it = DialectManager.INSTANCE
				.getAvailableRepresentationDescriptions(session.getSelectedViewpoints(true), ePackage).iterator();
		while (found == null && it.hasNext()) {
			RepresentationDescription desc = it.next();
			if (descriptionName.equals(desc.getName())) {
				found = desc;
			}
		}
		return found;
	}

	protected void logError(Throwable e) {
		final IStatus status = new Status(IStatus.ERROR, EcoreToolsDesignPlugin.PLUGIN_ID, IStatus.ERROR,
				e.getMessage(), e);
		EcoreToolsDesignPlugin.INSTANCE.log(status);
	}

	public void modifyWorkspace(IProgressMonitor progressMonitor)
			throws CoreException, UnsupportedEncodingException, IOException {
		project = Generator.createEMFProject(new Path(genModelContainerPath.toString()), genModelProjectLocation,
				Collections.<IProject>emptyList(), progressMonitor,
				Generator.EMF_MODEL_PROJECT_STYLE | Generator.EMF_PLUGIN_PROJECT_STYLE);

		if (selectedWorkingSets != null) {
			workbench.getWorkingSetManager().addToWorkingSets(project, selectedWorkingSets);
		}

		CodeGenUtil.EclipseUtil.findOrCreateContainer(new Path("/" + genModelContainerPath.segment(0) + "/model"), true,
				genModelProjectLocation, progressMonitor);

		PrintStream manifest = new PrintStream(URIConverter.INSTANCE.createOutputStream(
				URI.createPlatformResourceURI("/" + genModelContainerPath.segment(0) + "/META-INF/MANIFEST.MF", true),
				null), false, "UTF-8");
		manifest.println("Manifest-Version: 1.0");
		manifest.println("Bundle-ManifestVersion: 2");
		manifest.print("Bundle-Name: ");
		manifest.println(genModelContainerPath.segment(0));
		manifest.print("Bundle-SymbolicName: ");
		manifest.print(CodeGenUtil.validPluginID(genModelContainerPath.segment(0)));
		manifest.println("; singleton:=true");
		manifest.println("Bundle-Version: 0.1.0.qualifier");
		manifest.print("Require-Bundle: ");
		String[] requiredBundles = getRequiredBundles();
		for (int i = 0, size = requiredBundles.length; i < size;) {
			manifest.print(requiredBundles[i]);
			if (++i == size) {
				manifest.println();
				break;
			} else {
				manifest.println(",");
				manifest.print(" ");
			}
		}
		manifest.close();
	}

	protected String[] getRequiredBundles() {
		return new String[] { "org.eclipse.emf.ecore;visibility:=reexport","org.eclipse.core.runtime" };
	}

	/**
	 * .
	 * 
	 * @param project
	 * @return the semantic ecore model.
	 */
	private Session createModelingResources(IProject project, IProgressMonitor monitor) {

		/* create the ecore file */
		monitor.subTask("create the ecore model."); //$NON-NLS-1$
		String modelPath = '/' + project.getName() + "/model/"; //$NON-NLS-1$
		String ecorePath = createEcoreResource(modelPath);
		IFile ecoreFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(ecorePath));
		monitor.worked(10);

		/* create the genmodel file */
		monitor.subTask("create the genmodel..."); //$NON-NLS-1$
		createGenModel(project, ecoreFile, modelPath, new SubProgressMonitor(monitor, 15));

		/*
		 * Create the aird file and create a Session from the session model URI
		 */
		monitor.subTask("create the representation model..."); //$NON-NLS-1$
		final Session session = createAird(project,
				URI.createPlatformResourceURI(modelPath + representationsResourceName, true), monitor);
		monitor.worked(15);

		if (session == null) {
			return null;
		} else {
			/* prepare session ressource set for ecore models */
			session.getTransactionalEditingDomain().getResourceSet().getURIConverter().getURIMap()
					.putAll(EcorePlugin.computePlatformURIMap(false));
		}

		monitor.subTask("prepare ecore modeling project..."); //$NON-NLS-1$
		CompoundCommand cc = new CompoundCommand("Prepare Ecore Modeling Project"); //$NON-NLS-1$
		cc.append(new AddSemanticResourceCommand(session, URI.createPlatformResourceURI(ecorePath, true),
				new SubProgressMonitor(monitor, 1)));
		cc.append(new ChangeViewpointSelectionCommand(session, new ViewpointSelectionCallback(), selectedViewpoints,
				Collections.<Viewpoint>emptySet(), new SubProgressMonitor(monitor, 1)));

		monitor.subTask("link the created models..."); //$NON-NLS-1$
		session.getTransactionalEditingDomain().getCommandStack().execute(cc);
		monitor.worked(10);

		session.save(monitor);
		monitor.worked(15);

		ecoreModel = ecoreFile;
		return session;
	}

	private void convertToModelingProject(IProgressMonitor monitor) {
		/* Convert the created emf project to a modeling project. */
		try {
			ModelingProjectManager.INSTANCE.convertToModelingProject(project, monitor);
		} catch (CoreException e) {
			logError(e);
		}
	}

	private Session createAird(IProject project, URI representationsURI, IProgressMonitor monitor) {
		final Session session;
		Option<ModelingProject> modelingProject = ModelingProject.asModelingProject(project);
		if (modelingProject.some()) {
			session = modelingProject.get().getSession();
		} else {
			Session tempSession = null;
			SessionCreationOperation sessionCreationOperation = new DefaultLocalSessionCreationOperation(
					representationsURI, monitor);
			try {
				sessionCreationOperation.execute();
				tempSession = sessionCreationOperation.getCreatedSession();
			} catch (CoreException e) {
				logError(e);
			}
			if (tempSession != null) {
				session = tempSession;
			} else {
				session = null;
			}
		}
		return session;
	}

	private void createGenModel(IProject project, IFile ecoreModel, String modelPath,
			IProgressMonitor progressMonitor) {
		final EcoreImporter genModelCreator = new EcoreImporter();

		genModelCreator.setGenModelContainerPath(new Path(modelPath));
		genModelCreator.setGenModelFileName(genModelResourceName);
		genModelCreator.setModelFile(ecoreModel);
		genModelCreator.getFileExtensions().clear();
		genModelCreator.getFileExtensions().add(ecoreModel.getFileExtension());

		Monitor monitor = BasicMonitor.toMonitor(progressMonitor);
		try {
			genModelCreator.computeEPackages(monitor);
			genModelCreator.adjustEPackages(monitor);
			genModelCreator.prepareGenModelAndEPackages(monitor);
			if (genModelCreator.getGenModel() != null && project.getName() != null) {
				changeDefaultSettings(project.getName(), genModelCreator.getGenModel());
			}

			genModelCreator.saveGenModelAndEPackages(monitor);
		} catch (Exception exception) {
			IStatus status = new Status(IStatus.ERROR, EcoreToolsDesignPlugin.PLUGIN_ID, IStatus.ERROR,
					"Error occurs during generating the genmodel file.", exception);//$NON-NLS-1$
			EcoreToolsDesignPlugin.INSTANCE.log(status);
		} finally {
			monitor.done();
		}

	}

	private void changeDefaultSettings(String projectName, GenModel genModel) {

		if (genModel.getModelDirectory() != null && genModel.getModelDirectory().endsWith("/src")) {
			genModel.setModelDirectory(genModel.getModelDirectory() + "-gen");
		}
		if (genModel.getEditDirectory() != null && genModel.getEditDirectory().endsWith("/src")) {
			genModel.setEditDirectory(genModel.getEditDirectory() + "-gen");
		}
		if (genModel.getEditorDirectory() != null && genModel.getEditorDirectory().endsWith("/src")) {
			genModel.setEditorDirectory(genModel.getEditorDirectory() + "-gen");
		}

		genModel.setTestsDirectory(null);
		genModel.setCodeFormatting(true);
		genModel.setCreationIcons(false);

		List<String> prjNameSegments = new ArrayList<>(List.of(projectName.toLowerCase().split(Pattern.quote("."))));
		for (GenPackage p : genModel.getGenPackages()) {
			if (!prjNameSegments.isEmpty() && p.getEcorePackage() != null && p.getEcorePackage().getName() != null
					&& prjNameSegments.get(prjNameSegments.size() - 1).equalsIgnoreCase(p.getEcorePackage().getName())) {
				prjNameSegments.remove(prjNameSegments.size() - 1);
			}
			p.setBasePackage(String.join(".", prjNameSegments));
		}
	}

	private String createEcoreResource(String modelPath) {
		/*
		 * Create a resource for this file. Don't specify acontent type, as it
		 * could be Ecore or EMOF.Create in a other resourceset and let the
		 * workspace monitor for modeling project add it as semantic resource.
		 */
		final ResourceSet rs = new ResourceSetImpl();
		rs.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap(false));
		String platformPath = modelPath + ecoreResourceName;
		final URI semanticModelURI = URI.createPlatformResourceURI(platformPath, true);
		final Resource resource = rs.createResource(semanticModelURI);

		/* Add the initial model object to the contents. */
		if (rootObject != null) {
			resource.getContents().add(rootObject);
		}

		try {
			Map<Object, Object> options = new HashMap<Object, Object>();
			options.put(XMLResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
			resource.save(options);
		} catch (IOException e) {
			/* do nothing it should always work */
		}
		return platformPath;
	}

	public IFile getEcoreModel() {
		return ecoreModel;
	}

	public IProject getNewProject() {
		return this.project;
	}
}
