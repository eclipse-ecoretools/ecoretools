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
 **********************************************************************/

package org.eclipse.gmf.tests.runtime.diagram.ui.core.fixture;

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
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.util.DiagramFileCreator;
import org.eclipse.gmf.tests.runtime.diagram.ui.core.util.TestDiagramEditorUtil;
import org.eclipse.ui.PlatformUI;

/**
 * AbstractDiagramTestFixture which add the facility to create project, diagram,
 * and resource.
 * 
 * @author Simon Bernard
 */
public abstract class AbstractEmptyDiagramTestFixture extends AbstractDiagramTestFixture {

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

		EObject rootModel = createRootModel();

		IFile diagramFile = TestDiagramEditorUtil.createNewDiagramFile(getDiagramFileCreator(), getProject().getFullPath(), getFileName(), TestDiagramEditorUtil.getInitialContents(),
				getSemanticResourcePath(), PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new NullProgressMonitor(), (String) null, rootModel, getPreferencesHint());
		setDiagramFile(diagramFile);

		openDiagram();

		rootModelEObject = getDiagramEditPart().resolveSemanticElement();
		modelResource = rootModelEObject.eResource();
	}

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

	public String getFileName() {
		return getSemanticResourcePath() + "Test";
	}

	public abstract String getSemanticResourcePath();

	public abstract DiagramFileCreator getDiagramFileCreator();

	public abstract EObject createRootModel();
}
