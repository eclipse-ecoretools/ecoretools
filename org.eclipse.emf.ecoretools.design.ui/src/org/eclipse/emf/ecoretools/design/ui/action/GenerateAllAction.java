/*******************************************************************************
 * Copyright (c) 2016 Obeo.
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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.CodeGenEcorePlugin;
import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.codegen.ecore.genmodel.presentation.GeneratorUIUtil;
import org.eclipse.emf.codegen.ecore.genmodel.provider.GenModelEditPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class GenerateAllAction implements IExternalJavaAction {

	/**
	 * The action expects a single EClass as input.
	 */
	public boolean canExecute(Collection<? extends EObject> selections) {
		return true;
	}

	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		Object genmodelsParamValue = parameters.get("genmodels");
		String scope = (String) parameters.get("scope");
		if (scope == null) {
			scope = "model, edit, editor, tests";
		}
		if (genmodelsParamValue != null && genmodelsParamValue instanceof Collection) {
			launchEMFGeneration(genmodelsParamValue, scope);

		}

	}

	private void launchEMFGeneration(Object genmodelsParamValue, final String scope) {
		final List<GenModel> gens = Lists
				.newArrayList(Iterables.filter((Collection<?>) genmodelsParamValue, GenModel.class));

		if (gens.size() > 0) {
			Display.getDefault().syncExec(new Runnable() {

				public void run() {
					IEditorPart activeEditorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.getActiveEditor();
					GeneratorUIUtil.GeneratorOperation operation = new GeneratorUIUtil.GeneratorOperation(
							activeEditorPart.getSite().getShell());
					for (GenModel genModel : gens) {
						genModel.reconcile();
						genModel.setCanGenerate(true);
						Generator generator = new Generator();
						generator.setInput(genModel);
						if (scope.contains("model")) {
							operation.addGeneratorAndArguments(generator, genModel,
									GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE,
									CodeGenEcorePlugin.INSTANCE.getString("_UI_ModelProject_name"));
						}
						if (scope.contains("edit")) {
							operation.addGeneratorAndArguments(generator, genModel,
									GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE,
									CodeGenEcorePlugin.INSTANCE.getString("_UI_EditProject_name"));
						}
						if (scope.contains("editor")) {
							operation.addGeneratorAndArguments(generator, genModel,
									GenBaseGeneratorAdapter.EDITOR_PROJECT_TYPE,
									CodeGenEcorePlugin.INSTANCE.getString("_UI_EditorProject_name"));
						}
						if (scope.contains("tests")) {
							operation.addGeneratorAndArguments(generator, genModel,
									GenBaseGeneratorAdapter.TESTS_PROJECT_TYPE,
									CodeGenEcorePlugin.INSTANCE.getString("_UI_TestsProject_name"));
						}
					}
					try {

						IWorkbench wb = PlatformUI.getWorkbench();
						IProgressService ps = wb.getProgressService();
						ps.busyCursorWhile(operation);

					} catch (Exception exception) {
						// Something went wrong that shouldn't.
						//
						GenModelEditPlugin.INSTANCE.log(exception);
					}
				}
			});
		}
	}

}
