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

package org.eclipse.gmf.runtime.diagram.ui.outline;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.outline.internal.Activator;
import org.eclipse.gmf.runtime.diagram.ui.outline.internal.OverviewComposite;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

/**
 * <b>Outline of the modeler editor</b> <br>
 * The outline displays the model tree and the diagrams associated with the
 * model objects. <br>
 * Double-clicking on a diagram change the active diagram edited by the modeler.
 * <br>
 * 
 * @author <a href="mailto:david.sciamma@anyware-tech.com">David Sciamma </a>
 */
public abstract class AbstractDiagramsOutlinePage extends Page implements IContentOutlinePage, ISelectionChangedListener, IAdaptable {

	/** Flag set during selection modification */
	private boolean isDispatching = false;

	private IDiagramGraphicalViewer viewer;

	private AbstractModelNavigator navigator;

	private Composite overview;

	private SashForm sashComp;

	/** Actions */
	private IAction showTreeAction;

	private IAction showOverviewAction;

	private IAction showAllAction;

	private DiagramEditor editor;

	/**
	 * Constructor
	 * 
	 * @param ed
	 *            the editor linked with the outline
	 */
	public AbstractDiagramsOutlinePage(DiagramEditor ed) {
		this.viewer = ed.getDiagramGraphicalViewer();
		this.editor = ed;
	}

	/**
	 * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (navigator != null && !navigator.isDisposed()) {
			navigator.getTreeViewer().addSelectionChangedListener(listener);
		}
	}

	/**
	 * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		if (navigator != null && !navigator.isDisposed()) {
			navigator.getTreeViewer().removeSelectionChangedListener(listener);
		}
	}

	/**
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	public ISelection getSelection() {
		return navigator.getTreeViewer().getSelection();
	}

	/**
	 * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	public void setSelection(ISelection selection) {
		navigator.getTreeViewer().setSelection(selection);
	}

	/**
	 * Add listeners on the tree :
	 * <ul>
	 * <li>Listen to double-click</li>
	 * </ul>
	 */
	protected void hookListeners() {
		navigator.getTreeViewer().addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				handleDoubleClickEvent();
			}
		});

		viewer.addSelectionChangedListener(this);
	}

	/**
	 * Handles a double click on the outline tree : if the selected element is a
	 * diagram, the currently edited diagram is switch in the editor.
	 */
	protected void handleDoubleClickEvent() {
		// IStructuredSelection selection = (IStructuredSelection)
		// navigator.getTreeViewer().getSelection();
		// Object selectedObject = selection.getFirstElement();
		//
		// // TODO Handle Double-click
		// if (selectedObject instanceof Diagram && editor.getActiveDiagram() !=
		// (Diagram) selectedObject)
		// {
		// editor.setActiveDiagram((Diagram) selectedObject);
		// }
		// else if (AdapterFactoryEditingDomain.unwrap(selectedObject)
		// instanceof EObject)
		// {
		// editor.gotoEObject((EObject)
		// AdapterFactoryEditingDomain.unwrap(selectedObject));
		// }
	}

	/**
	 * Creates the contents of the outline
	 * 
	 * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {

		// SashForm
		sashComp = new SashForm(parent, SWT.VERTICAL);
		sashComp.setLayoutData(new GridData(GridData.FILL_BOTH));

		RootEditPart rootEditPart = viewer.getRootEditPart();
		if (rootEditPart instanceof ScalableFreeformRootEditPart) {
			overview = createOverview(sashComp, (ScalableFreeformRootEditPart) rootEditPart);
			overview.setLayoutData(new GridData(GridData.FILL_BOTH));
		}

		navigator = createNavigator(sashComp, viewer, getSite());

		sashComp.setWeights(new int[] { 30, 70 });

		getSite().setSelectionProvider(navigator.getTreeViewer());

		createActions();
		hookListeners();
	}

	/**
	 * Create the composite that shows an overview of the model
	 * 
	 * @param parent
	 *            the parent
	 * @param rootEditPart
	 *            the root edit part
	 * @return the overview composite
	 */
	protected Composite createOverview(Composite parent, ScalableFreeformRootEditPart rootEditPart) {
		return new OverviewComposite(parent, rootEditPart);
	}

	/**
	 * Create the composite that shows a tree view of the model
	 * 
	 * @param parent
	 *            the parent
	 * @param viewer
	 *            the Viewer
	 * @param pageSite
	 *            the IPageSite
	 * 
	 * @return the navigation composite
	 */
	protected abstract AbstractModelNavigator createNavigator(Composite parent, IDiagramGraphicalViewer viewer, IPageSite pageSite);

	/**
	 * Add the actions to the view toolbar
	 */
	protected void createActions() {
		IToolBarManager tbm = getSite().getActionBars().getToolBarManager();

		createSorterActions(tbm);

		tbm.add(new Separator());

		createShowOutlineActions(tbm);

		fillDropDownMenu(getSite().getActionBars().getMenuManager());
	}

	/**
	 * Returns the PreferenceStore to use
	 * 
	 * @return IPreferenceStore
	 */
	protected abstract IPreferenceStore getPreferenceStore();

	/**
	 * Create the show outline actions in the given tool bar manager.
	 * 
	 * @param tbm
	 *            the outline tool bar manager
	 */
	private void createShowOutlineActions(IToolBarManager tbm) {
		final IPreferenceStore ps = getPreferenceStore();
		showTreeAction = new Action("Show Navigator", IAction.AS_RADIO_BUTTON) {

			public void run() {
				if (navigator != null && !navigator.isDisposed()) {
					performShowAction(navigator, ps, 1);
				}
			}
		};
		showTreeAction.setToolTipText(showTreeAction.getText());
		showTreeAction.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/tree.gif"));
		tbm.add(showTreeAction);

		showOverviewAction = new Action("Show Overview", IAction.AS_RADIO_BUTTON) {

			public void run() {
				if (overview != null && !overview.isDisposed()) {
					performShowAction(overview, ps, 2);
				}
			}
		};
		showOverviewAction.setToolTipText(showOverviewAction.getText());
		showOverviewAction.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/overview.gif"));
		tbm.add(showOverviewAction);

		showAllAction = new Action("Show both", IAction.AS_RADIO_BUTTON) {

			public void run() {
				if (sashComp != null && !sashComp.isDisposed()) {
					performShowAction(null, ps, 0);
				}
			}
		};
		showAllAction.setToolTipText(showAllAction.getText());
		showAllAction.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/all.gif"));
		tbm.add(showAllAction);

		if (ps != null) {
			int showAction = 0;// TODO restore
								// ps.getInt(ModelerPreferenceConstants.OUTLINE_SHOW_ACTION_PREF);
			Control control = null;
			switch (showAction) {
			case 1:
				control = navigator;
				showTreeAction.setChecked(true);
				break;

			case 2:
				control = overview;
				showOverviewAction.setChecked(true);
				break;

			default:
				control = null;
				showAllAction.setChecked(true);
			}
			performShowAction(control, ps, showAction);
		} else {
			showAllAction.setChecked(true);
			performShowAction(null, ps, 0);
		}

	}

	/**
	 * Create the sorter actions in the given tool bar manager.
	 * 
	 * @param tbm
	 *            the outline tool bar manager
	 */
	private void createSorterActions(IToolBarManager tbm) {
		// final IPreferenceStore ps = getPreferenceStore();
		// final ArrayList sorterActions = new ArrayList();
		//
		// // TODO Restore extension point
		// // Get all extended sorters
		// Collection sorters =
		// OutlineManager.getInstance().getSorters(editor.getSite().getId());
		// // Get the actual sorter id from the preference store
		// String sorterId = ps != null ?
		// ps.getString(ModelerPreferenceConstants.OUTLINE_SORTER_PREF) : null;
		// for (Iterator it = sorters.iterator(); it.hasNext();)
		// {
		// final SorterConfiguration config = (SorterConfiguration) it.next();
		// // Create an action for each sorter configuration
		// IAction sorterAction = new Action(config.getName(),
		// IAction.AS_CHECK_BOX)
		// {
		// public void run()
		// {
		// navigator.getTreeViewer().setSorter(isChecked() ? config.getSorter()
		// : null);
		// for (Iterator it2 = sorterActions.iterator(); it2.hasNext();)
		// {
		// IAction action = (IAction) it2.next();
		// if (action != this)
		// {
		// action.setChecked(false);
		// }
		// }
		// // Store the selected sorter in the preference store
		// if (ps != null)
		// {
		// ps.setValue(ModelerPreferenceConstants.OUTLINE_SORTER_PREF,
		// isChecked() ? config.getId() : "");
		// }
		// }
		//
		// };
		// // Add the newly created action in the actions cache
		// sorterActions.add(sorterAction);
		//
		// // Configure the newly created action
		// sorterAction.setChecked(config.getId().equals(sorterId));
		// sorterAction.setImageDescriptor(config.getIcon());
		// sorterAction.setToolTipText(config.getName());
		//
		// // Set the actual sorter
		// if (config.getId().equals(sorterId))
		// {
		// navigator.getTreeViewer().setSorter(config.getSorter());
		// }
		//
		// tbm.add(sorterAction);
		// }

	}

	private void performShowAction(Control control, IPreferenceStore ps, int pref) {
		sashComp.setMaximizedControl(control);
		if (ps != null) {
			// TODO Restore
			// ps.setValue(ModelerPreferenceConstants.OUTLINE_SHOW_ACTION_PREF,
			// pref);
		}
	}

	private void fillDropDownMenu(IMenuManager menu) {
		// TODO Restore
		// Collection configs =
		// OutlineManager.getInstance().getCreateChildMenus(editor.getSite().getId());
		// final IPreferenceStore ps = editor.getPreferenceStore();
		// if (configs.size() > 1 && ps != null)
		// {
		// IMenuManager createChildMenu = new MenuManager("Create child menu");
		// menu.add(createChildMenu);
		//
		// for (Iterator it = configs.iterator(); it.hasNext();)
		// {
		// final CreateChildMenuConfiguration config =
		// (CreateChildMenuConfiguration) it.next();
		// IAction action = new Action(config.getName(),
		// IAction.AS_RADIO_BUTTON)
		// {
		// public void run()
		// {
		// ps.setValue(ModelerPreferenceConstants.CREATE_CHILD_MENU_PREF,
		// config.getId());
		// }
		// };
		// action.setChecked(ps.getString(ModelerPreferenceConstants.CREATE_CHILD_MENU_PREF).equals(config.getId()));
		//
		// createChildMenu.add(action);
		// }
		// }
		//
		// menu.add(new Separator());
		//
		// menu.add(new FiltersAction(navigator.getTreeViewer(), editor));
	}

	/**
	 * @see org.eclipse.ui.part.IPage#getControl()
	 */
	public Control getControl() {
		return sashComp;
	}

	/**
	 * @see org.eclipse.ui.part.Page#init(org.eclipse.ui.part.IPageSite)
	 */
	public void init(IPageSite pageSite) {
		super.init(pageSite);

		// TODO Restore
		// IActionBars bars = pageSite.getActionBars();
		// String id = ActionFactory.UNDO.getId();
		// bars.setGlobalActionHandler(id,
		// editor.getPublicActionRegistry().getAction(id));
		// id = ActionFactory.REDO.getId();
		// bars.setGlobalActionHandler(id,
		// editor.getPublicActionRegistry().getAction(id));
		// bars.updateActionBars();
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (adapter == EditingDomain.class) {
			return editor.getEditingDomain();
		}
		return null;
	}

	/**
	 * @see org.eclipse.ui.part.IPage#setFocus()
	 */
	public void setFocus() {
		getControl().setFocus();
	}

	/**
	 * Called when the selection changed in the editor
	 * 
	 * @param event
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		if (isDispatching) {
			return;
		}

		ISelection selection = event.getSelection();
		syncSelection(selection);
	}

	/**
	 * Synchronize the outline with the given selection. It also filters the
	 * 
	 * @param selection
	 */
	private void syncSelection(ISelection selection) {
		isDispatching = true;

		// TODO restore
		// List newSelection = new ArrayList();
		// if (selection instanceof IStructuredSelection)
		// {
		// Iterator it = ((IStructuredSelection) selection).iterator();
		// while (it.hasNext())
		// {
		// Object selectedObject = it.next();
		// if (selectedObject instanceof EMFGraphNodeEditPart)
		// {
		// newSelection.add(((MixedEditDomain)
		// editor.getAdapter(MixedEditDomain.class)).getEMFEditingDomain().getWrapper(
		// ((EMFGraphNodeEditPart) selectedObject).getEObject()));
		// }
		// else if (selectedObject instanceof EMFGraphEdgeEditPart)
		// {
		// newSelection.add(((MixedEditDomain)
		// editor.getAdapter(MixedEditDomain.class)).getEMFEditingDomain().getWrapper(
		// ((EMFGraphEdgeEditPart) selectedObject).getEObject()));
		// }
		// else if (selectedObject instanceof GraphElement)
		// {
		// EObject model = Utils.getElement((GraphElement) selectedObject);
		// if (model != null)
		// {
		// newSelection.add(model);
		// }
		// }
		// else if (selectedObject instanceof EObject)
		// {
		// newSelection.add(selectedObject);
		// }
		// else if (selectedObject instanceof DiagramEditPart)
		// {
		// newSelection.add(Utils.getElement((GraphElement) ((MixedEditDomain)
		// editor.getAdapter(MixedEditDomain.class)).getEMFEditingDomain().getWrapper(
		// ((DiagramEditPart) selectedObject).getModel())));
		// }
		// }
		// }
		// setSelection(new StructuredSelection(newSelection));

		isDispatching = false;
	}

	/**
	 * Releases the objects
	 * 
	 * @see org.eclipse.ui.part.IPage#dispose()
	 */
	public void dispose() {
		unhookListeners();
		overview.dispose();
		navigator.dispose();
		super.dispose();
	}

	/**
	 * Stop all the listeners
	 */
	protected void unhookListeners() {
		viewer.removeSelectionChangedListener(this);
	}
}
