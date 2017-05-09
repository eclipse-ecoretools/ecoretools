/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.ui.action;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class OpenFileInEditorAction implements IExternalJavaAction {

	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

	public void execute(Collection<? extends EObject> selections, final Map<String, Object> parameters) {

		Object wksPath = parameters.get("path");

		if (wksPath instanceof String) {
			final String path = (String) wksPath;
			Display.getDefault().syncExec(new Runnable() {

				public void run() {

					IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path.toString()));
					if (workspaceFile != null) {
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						try {
							IEditorPart openEditor = IDE.openEditor(page, workspaceFile);
						} catch (PartInitException e) {
						}
					}
				}
			});

		}

	}

}
