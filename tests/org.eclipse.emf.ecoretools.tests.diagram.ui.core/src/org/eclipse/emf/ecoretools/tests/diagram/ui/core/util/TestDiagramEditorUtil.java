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

package org.eclipse.emf.ecoretools.tests.diagram.ui.core.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.AbstractEMFOperation;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.util.IDEEditorUtil;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.util.DiagramFileCreator;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

public class TestDiagramEditorUtil extends IDEEditorUtil {

	/**
	 * Creates a new diagram file resource in the selected container and with
	 * the selected name. Creates any missing resource containers along the
	 * path; does nothing if the container resources already exist.
	 * <p>
	 * In normal usage, this method is invoked after the user has pressed Finish
	 * on the wizard; the enablement of the Finish button implies that all
	 * controls on on this page currently contain valid values.
	 * </p>
	 * <p>
	 * Note that this page caches the new file once it has been successfully
	 * created; subsequent invocations of this method will answer the same file
	 * resource without attempting to create it again.
	 * </p>
	 * <p>
	 * This method should be called within a workspace modify operation since it
	 * creates resources.
	 * </p>
	 * 
	 * @return the created file resource, or <code>null</code> if the file was
	 *         not created
	 */
	public static final IFile createNewDiagramFile(DiagramFileCreator diagramFileCreator, IPath containerFullPath, String fileName, InputStream initialContents, final String kind, Shell shell,
			final IProgressMonitor progressMonitor, final String semanticResourcePath, final EObject rootModel, final PreferencesHint preferencesHit) {

		/** cache of newly-created file */
		final IFile newDiagramFile = diagramFileCreator.createNewFile(containerFullPath, fileName, initialContents, shell, new IRunnableContext() {

			public void run(boolean fork, boolean cancelable, IRunnableWithProgress runnable) throws InvocationTargetException, InterruptedException {
				runnable.run(progressMonitor);
			}
		});

		TransactionalEditingDomain domain = GMFEditingDomainFactory.getInstance().createEditingDomain();
		final ResourceSet resourceSet = domain.getResourceSet();

		AbstractEMFOperation op = new AbstractEMFOperation(domain, "File creation") {

			protected IStatus doExecute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

				IFile semanticFile = null;
				boolean semanticFileIsNew = false;
				if (semanticResourcePath != null && semanticResourcePath.length() > 0) {
					semanticFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(semanticResourcePath));
					if (!semanticFile.exists()) {
						semanticFileIsNew = true;
						try {
							semanticFile.create(new ByteArrayInputStream(new byte[0]), false, progressMonitor);
						} catch (CoreException e) {
							// TODO log this
						}
					}
				}

				// Fill the contents of the file dynamically
				Resource notationModel = null;
				EObject semanticModel = null;

				try {
					newDiagramFile.refreshLocal(IResource.DEPTH_ZERO, null); // RATLC00514368
					if (semanticFile != null) {
						semanticFile.refreshLocal(IResource.DEPTH_ZERO, null);
					}

					InputStream stream = newDiagramFile.getContents();
					final String completeFileName = newDiagramFile.getLocation().toOSString();

					try {
						// Empty file....
						notationModel = resourceSet.createResource(URI.createFileURI(completeFileName));

						if (semanticFileIsNew) {
							semanticModel = rootModel;
							Resource semanticResource = resourceSet.createResource(URI.createPlatformResourceURI(semanticResourcePath, true));

							semanticResource.getContents().add(semanticModel);
						} else if (semanticFile != null) {
							semanticModel = (EPackage) resourceSet.getResource(URI.createPlatformResourceURI(semanticResourcePath, true), true).getContents().get(0);
						}

					} finally {
						stream.close();
					}

				} catch (Exception e) {
					// TODO log this
				}

				if (notationModel != null) {
					if (semanticModel == null) {
						semanticModel = EcoreFactory.eINSTANCE.createEPackage();
						notationModel.getContents().add(semanticModel);
					}

					Diagram view = ViewService.createDiagram(semanticModel, kind, preferencesHit);

					if (view != null) {
						notationModel.getContents().add(0, view);
						view.getDiagram().setName(newDiagramFile.getName());
					}

					try {
						notationModel.save(Collections.EMPTY_MAP);
						semanticModel.eResource().save(Collections.EMPTY_MAP);
					} catch (IOException e) {
						// TODO log this
					}
				}

				return Status.OK_STATUS;
			}
		};

		try {
			op.execute(new NullProgressMonitor(), null);

		} catch (ExecutionException e) {
			// TODO log this
		}

		return newDiagramFile;
	}
}
