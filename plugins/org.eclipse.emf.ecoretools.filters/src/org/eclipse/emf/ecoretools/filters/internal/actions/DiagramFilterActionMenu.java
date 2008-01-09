/***********************************************************************
 * Copyright (c) 2007 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.ecoretools.filters.internal.actions;

import org.eclipse.emf.ecoretools.filters.internal.FilterPlugin;
import org.eclipse.emf.ecoretools.filters.internal.extension.FilteredDiagramTypeExtensionManager;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;


/**
 * 
 * TODO Describe the class here <br>
 * creation : 5 oct. 07
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class DiagramFilterActionMenu extends Action {

	private class MenuCreator implements IMenuCreator {
		public void dispose() {
			// Menu will not be disposed
		}

		public Menu getMenu(Control parent) {
			if (menu == null) {
				menu = new Menu(parent);
				initialize();
			}
			return menu;
		}

		public Menu getMenu(Menu parent) {
			return null;
		}
	}

	public static String ID = "diagramFilterActionMenu";

	private Menu menu;

	protected IGraphicalEditPart host;

	public DiagramFilterActionMenu() {
		super("", AS_DROP_DOWN_MENU);
		setId(ID);
		setText("Filter Elements");
		setToolTipText("Filter Elements");
		setImageDescriptor(FilterPlugin.getImageDescriptor("icons/etool16/filter_edit.gif"));		
		setMenuCreator(new MenuCreator()); // set menu creator for sub menus
	}

	private Diagram getCurrentDiagram() {
		IEditorPart editorPart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (false == editorPart instanceof DiagramEditor) {
			return null;
		}
		host = ((DiagramEditor) editorPart).getDiagramEditPart();
		if (false == host instanceof IGraphicalEditPart) {
			return null;
		}
		View view = (View) host.getModel();

		Diagram diagram = view.getDiagram();

		return diagram;
	}

	private void initialize() {
		ActionContributionItem configure = new ActionContributionItem(
				new ConfigureFilterDiagramAction());
		configure.fill(menu, -1);

		ActionContributionItem apply = new ActionContributionItem(
				new ApplyFilterDiagramAction());
		apply.fill(menu, 1);
	}

	@Override
	public boolean isHandled() {
		Diagram diagram = getCurrentDiagram();
		if (diagram == null) {
			return false;
		}

		return (FilteredDiagramTypeExtensionManager.getInstance()
				.getFilteredDiagramTypeExtension(diagram.getType()) != null);
	}

	@Override
	public void run() {
		// do something for top menu
	}

}
