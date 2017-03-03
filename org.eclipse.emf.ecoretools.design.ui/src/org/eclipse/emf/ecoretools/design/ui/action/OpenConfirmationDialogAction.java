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

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class OpenConfirmationDialogAction implements IExternalJavaAction {

	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		Object messageObj = parameters.get("message");
		Object titleObj = parameters.get("title");
		if (messageObj instanceof String) {
			final String message = (String) messageObj;
			final String title = getTitleFromParameter(titleObj);
			Display.getDefault().syncExec(new Runnable() {

				public void run() {
					IEditorPart activeEditorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.getActiveEditor();

					if (!MessageDialog.openConfirm(activeEditorPart.getSite().getShell(), title, message)) {

						throw new OperationCanceledException("user canceled");
					}

				}
			});

		}

	}

	private String getTitleFromParameter(Object titleObj) {
		String title = "Confirmation ?";
		if (titleObj instanceof String) {
			title = (String) titleObj;
		}
		return title;
	}

}
