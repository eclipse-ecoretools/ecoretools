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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.sirius.common.ui.tools.api.selection.EObjectSelectionWizard;
import org.eclipse.sirius.diagram.ui.provider.DiagramUIPlugin;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class OpenSelectModelElementAction implements IExternalJavaAction {

	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

	public void execute(Collection<? extends EObject> selections, final Map<String, Object> parameters) {

		Object messageObj = parameters.get("message");
		Object titleObj = parameters.get("title");
		Object candidates = parameters.get("candidates");
		final Collection<EObject> toSelectFrom = Lists.newArrayList();
		if (candidates instanceof EObject) {
			toSelectFrom.add((EObject) candidates);
		} else if (candidates instanceof Iterable) {
			for (EObject eObject : Iterables.filter((Iterable) candidates, EObject.class)) {
				toSelectFrom.add(eObject);
			}
		}
		if (messageObj instanceof String) {
			final String message = (String) messageObj;
			final String title = getTitleFromParameter(titleObj);
			Display.getDefault().syncExec(new Runnable() {

				public void run() {
					Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
					final EObjectSelectionWizard wizard = new EObjectSelectionWizard(title, message, null, toSelectFrom,
							DiagramUIPlugin.getPlugin().getItemProvidersAdapterFactory());
					wizard.setMany(false);
					final WizardDialog dlg = new WizardDialog(shell, wizard);
					final int result = dlg.open();
					if (result == Window.OK) {

						Object featureObj = parameters.get("feature");
						Object hostObj = parameters.get("host");
						if (featureObj instanceof EStructuralFeature && hostObj instanceof EObject) {
							((EObject) hostObj).eSet((EStructuralFeature) featureObj, wizard.getSelectedEObject());
						}

					} else {
						throw new OperationCanceledException("user canceled");
					}

				}
			});

		}

	}

	private String getTitleFromParameter(Object titleObj) {
		String title = "Select Model Element";
		if (titleObj instanceof String) {
			title = (String) titleObj;
		}
		return title;
	}

}
