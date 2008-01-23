package org.eclipse.gmf.tests.runtime.diagram.ui.core.fixture;

import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;

public abstract class AbstractExistingDiagramTestFixture extends AbstractDiagramTestFixture {

	private EObject rootModelEObject;

	private Resource modelResource;

	/**
	 * create the project
	 */
	protected void createProject() throws Exception {
		IWorkspace workspace = null;
		String aProjectName = getProjectName(); //$NON-NLS-1$

		workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsroot = workspace.getRoot();
		IProject project = wsroot.getProject(aProjectName);
		IProjectDescription desc = workspace.newProjectDescription(project.getName());

		IPath locationPath = Platform.getLocation();
		locationPath = null;
		desc.setLocation(locationPath);
		if (!project.exists())
			project.create(desc, null);
		if (!project.isOpen())
			project.open(null);

		setProject(project);
	}

	/**
	 * create a diagramFile then open it
	 */
	protected void createDiagram() throws Exception {

		URL modelFileURL = getSourceModelURL();
		IFile modelIFile = getProject().getFile(getModelFileName());
		modelIFile.create(modelFileURL.openStream(), true, new NullProgressMonitor());

		URL diagramFileURL = getSourceDiagramURL();
		IFile diagramIFile = getProject().getFile(getDiagramFileName());
		diagramIFile.create(diagramFileURL.openStream(), true, new NullProgressMonitor());

		setDiagramFile(diagramIFile);

		openDiagram();

		rootModelEObject = getDiagramEditPart().resolveSemanticElement();
		modelResource = rootModelEObject.eResource();
	}

	public abstract String getModelFileName();

	public abstract String getDiagramFileName();

	protected abstract URL getSourceDiagramURL();

	protected abstract URL getSourceModelURL();

	public PreferencesHint getPreferencesHint() {
		return PreferencesHint.USE_DEFAULTS;
	}

	public EObject getRootModelEObject() {
		return rootModelEObject;
	}

	public Resource getModelResource() {
		return modelResource;
	}

	public abstract String getProjectName();

	@Override
	protected void createShapesAndConnectors() throws Exception {
		// nothing to do;
	}
}
