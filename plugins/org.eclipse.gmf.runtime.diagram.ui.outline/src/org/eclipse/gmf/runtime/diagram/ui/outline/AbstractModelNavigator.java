/***********************************************************************
 * Copyright (c) 2007, 2008 Anyware Technologies
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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IViewerNotification;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.outline.internal.Activator;
import org.eclipse.gmf.runtime.diagram.ui.outline.internal.ModelElementComparer;
import org.eclipse.gmf.runtime.diagram.ui.outline.internal.OutlineDragAdapter;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.IPageSite;

/**
 * <b>Model navigator :</b><br>
 * Display the model as a tree and fill the contextual menu with diagrams and
 * EMF actions. <br>
 * 
 * Updated	: 18 feb. 2008
 * 
 * @author <a href="mailto:david.sciamma@anyware-tech.com">David Sciamma</a>
 * @author <a href="mailto:jacques.lescot@anyware-tech.com">Jacques LESCOT</a>
 */
public abstract class AbstractModelNavigator extends Composite implements IMenuListener {

	private IDiagramWorkbenchPart editor;
	
	private IDiagramGraphicalViewer diagramViewer;

	private TreeViewer viewer;

	private IPageSite site;

	private Adapter modelListener = new AdapterImpl() {

		/**
		 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 */
		@Override
		public void notifyChanged(Notification msg) {
			refreshViewer(true);
		}
	};

	/**
	 * This content provider filters the event from graphical object to only
	 * refresh when it's needed.
	 * 
	 * @author <a href="david.sciamma@anyware-tech.com">David Sciamma</a>
	 */
	protected class NavigatorAdapterFactoryContentProvider extends AdapterFactoryContentProvider {

		/**
		 * Constructor
		 * 
		 * @param adapterFactory
		 *            the factory
		 */
		public NavigatorAdapterFactoryContentProvider(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}

		/**
		 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 */
		@Override
		public void notifyChanged(Notification notification) {
			if (notification instanceof IViewerNotification) {
				Object element = ((IViewerNotification) notification).getElement();
				if (!(element instanceof View)) {
					// Filter only non-graphical object events
					super.notifyChanged(notification);
				} else if ((element instanceof Diagram) && ((IViewerNotification) notification).isLabelUpdate()) {
					// A diagram or a Diagrams is added or removed : refresh the
					// whole tree
					refreshViewer(true);
				}
			} else {
				super.notifyChanged(notification);
			}
		}
	}

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            the parent composite
	 * @param diagEditor
	 *            the viewer to edit as tree
	 * @param pageSite
	 *            the site
	 */
	public AbstractModelNavigator(Composite parent, IDiagramWorkbenchPart diagEditor, IPageSite pageSite) {
		super(parent, SWT.BORDER);

		this.editor = diagEditor;
		this.diagramViewer = diagEditor.getDiagramGraphicalViewer();
		site = pageSite;
		GridLayout gl = new GridLayout();
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		setLayout(gl);
		createContents(this);
	}

	/**
	 * Returns the TreeViewer used as navigator
	 * 
	 * @return the navigable tree
	 */
	public TreeViewer getTreeViewer() {
		return viewer;
	}

	/**
	 * Create the contents of the widget
	 * 
	 * @param parent
	 *            the current widget
	 */
	protected void createContents(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI);
		viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));

		initDragAndDrop();
		initProviders();
		initFilters();
		hookListeners();
		hookKeyListeners();

		// Add our custom ElementComparer that unwrap model objects before the
		// comparison
		viewer.setComparer(new ModelElementComparer());
		viewer.setInput(getModelResource());

		createContextMenu(viewer);

		refreshViewer();
	}

	/**
	 * The diagram model Resource
	 * 
	 * @return Resource
	 */
	protected Resource getDiagramResource() {
		Object model = diagramViewer.getContents().getModel();
		if (model instanceof Diagram) {
			return ((Diagram) model).eResource();
		}

		return null;
	}

	/**
	 * The domain model Resource
	 * 
	 * @return Resource
	 */
	protected Resource getModelResource() {
		Object model = diagramViewer.getContents().getModel();
		if (model instanceof Diagram) {
			EObject eObject = ((Diagram) model).getElement();
			return EcoreUtil.getRootContainer(eObject).eResource();
		}

		return null;
	}

	/**
	 * Add drag and drop ability between the outline to the editor.
	 */
	protected void initDragAndDrop() {
		int ops = DND.DROP_COPY | DND.DROP_MOVE;

		OutlineDragAdapter dragAdapter = new OutlineDragAdapter(viewer);
		viewer.addDragSupport(ops, dragAdapter.getSupportedDragTransfers(), dragAdapter);

		// Transfer[] transfers = new Transfer[]
		// {OutlineToDiagramTransfer.getInstance()};
		// viewer.addDragSupport(ops, transfers, new
		// OutlineDragAdapter(viewer));

		// // TODO restore EMF internal drag and drop (need model refactoring
		// // capabilities)
		// int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
		// Transfer[] transfers = new Transfer[] {LocalTransfer.getInstance()};
		// viewer.addDragSupport(dndOperations, transfers, new
		// ViewerDragAdapter(viewer));
		// viewer.addDropSupport(dndOperations, transfers, new
		// EditingDomainViewerDropAdapter(modeler.getEditingDomain(),
		// viewer));
	}

	/**
	 * Set the tree providers for the outline
	 */
	protected void initProviders() {
		AdapterFactoryContentProvider adapterContentProvider = new NavigatorAdapterFactoryContentProvider(getAdapterFactory());
		adapterContentProvider.inputChanged(viewer, null, null);
		viewer.setContentProvider(new NavigatorContentProvider(diagramViewer, adapterContentProvider));

		ILabelProvider fullLabelProvider = new DecoratingLabelProvider(new NavigatorLabelProvider(new AdapterFactoryLabelProvider(getAdapterFactory())), Activator.getDefault().getWorkbench()
				.getDecoratorManager().getLabelDecorator());
		viewer.setLabelProvider(fullLabelProvider);
	}

	/**
	 * Set the tree filters for the outline
	 * 
	 */
	protected void initFilters() {
		// TODO Complete This later
		// IPreferenceStore ps = modeler.getPreferenceStore();
		// Collection configs = FiltersAction.getFilterConfigurations(ps != null
		// ?
		// ps.getString(ModelerPreferenceConstants.FILTERS_PREF) : "");
		//
		// for (Iterator it = configs.iterator(); it.hasNext();)
		// {
		// FilterConfiguration config = (FilterConfiguration) it.next();
		// viewer.addFilter(config.getFilter());
		// }

	}

	/**
	 * Add listeners : <br> - on the model<br>
	 */
	protected void hookListeners() {
		if (getModelResource() != null && getModelResource().getResourceSet() != null) {
			getModelResource().getResourceSet().eAdapters().add(modelListener);
		}
		if (getDiagramResource() != null) {
			getDiagramResource().eAdapters().add(modelListener);
		}
	}

	/**
	 * Remove listeners
	 */
	protected void unhookListeners() {
		if (getDiagramResource() != null) {
			getDiagramResource().eAdapters().remove(modelListener);
		}
		if (getModelResource() != null && getModelResource().getResourceSet() != null) {
			getModelResource().getResourceSet().eAdapters().remove(modelListener);
		}
	}

	/**
	 * Add a key listener to the tree control. When a key is released, the
	 * DELETE key is filtered to fire the action.
	 */
	protected void hookKeyListeners() {
		KeyListener keyListener = new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// Do nothing
			}

			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.DEL) {
					// TODO Restore this
					// IAction deleteAction = createDeleteAction();
					// deleteAction.run();
				}
			}

		};
		viewer.getControl().addKeyListener(keyListener);
	}

	/**
	 * This creates a context menu for the viewer and adds a listener as well
	 * registering the menu for extension.
	 * 
	 * @param sViewer
	 *            the tree viewer
	 */
	protected void createContextMenu(StructuredViewer sViewer) {
		MenuManager contextMenu = new MenuManager("#PopUp"); //$NON-NLS-1$
		contextMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		contextMenu.setRemoveAllWhenShown(true);
		contextMenu.addMenuListener(this);
		Menu menu = contextMenu.createContextMenu(viewer.getControl());
		sViewer.getControl().setMenu(menu);
		site.registerContextMenu(getClass().getName(), contextMenu, viewer);
	}

	/**
	 * Get the resource associated with the given selected object
	 * 
	 * @param selection
	 *            the selected object
	 * @return the EMF resource
	 */
	private Resource getResourceFromSelection(Object selection) {
		Resource resource = null;

		if (selection instanceof EObject) {
			resource = ((EObject) selection).eResource();
		} else if (selection instanceof Resource) {
			resource = (Resource) selection;
		} else if ((selection instanceof IWrapperItemProvider) || (selection instanceof FeatureMap.Entry)) {
			resource = ((EObject) AdapterFactoryEditingDomain.unwrap(selection)).eResource();
		}

		return resource;
	}

	/**
	 * This implements {@link org.eclipse.jface.action.IMenuListener}to help
	 * fill the context menus with contributions from the Edit menu.
	 * 
	 * @param menuManager
	 *            the menu to fill
	 */
	public void menuAboutToShow(IMenuManager menuManager) {

		// Add our standard marker.
		//
		menuManager.add(new Separator(IOutlineMenuConstants.NEW_GROUP));
		menuManager.add(new Separator(IOutlineMenuConstants.EDIT_GROUP));
		menuManager.add(new Separator(IOutlineMenuConstants.ADDITIONS_GROUP));
		menuManager.add(new Separator(IOutlineMenuConstants.PROPERTIES_GROUP));
		menuManager.add(new Separator(IOutlineMenuConstants.ADDITIONS_END_GROUP));

		IStructuredSelection sel = (IStructuredSelection) viewer.getSelection();

		Object currentSel = sel.getFirstElement();
		Resource resource = getResourceFromSelection(currentSel);

		// Create context menu if the resource associated to the current
		// selection is writable.
		if (currentSel instanceof AdditionalResources || resource != null) // &&
		// !ResourceUtils.isReadOnly(resource)))
		{
			if (sel.size() == 1) {
				Object selection = sel.getFirstElement();
				createSingleSelectionMenu(menuManager, selection);
			}

			createMultiSelectionMenu(menuManager, sel);
		}

		// TODO Restore this
		// if (loadMenu == null)
		// {
		// MixedEditDomain domain = (MixedEditDomain)
		// modeler.getAdapter(MixedEditDomain.class);
		// String metamodelURI =
		// modeler.getDiagrams().getModel().eClass().getEPackage().getNsURI();
		// loadMenu = new RegisteredModelMenu("Load",
		// domain.getEMFEditingDomain(), metamodelURI);
		// }
		// menuManager.add(loadMenu);
	}

	/**
	 * Add to the MenuManager the actions for a multiple selection.
	 * 
	 * @param manager
	 *            The menu to fill
	 * @param selection
	 *            the selection
	 */
	protected void createMultiSelectionMenu(IMenuManager manager, IStructuredSelection selection) {
		// TODO Restore this
		// MixedEditDomain domain = (MixedEditDomain)
		// modeler.getAdapter(MixedEditDomain.class);
		// if (domain != null)
		// {
		// // Add the delete from model action
		// DeleteAction deleteAction = new DeleteAction(domain,
		// modeler.getDiagrams(), selection);
		// manager.appendToGroup(IOutlineMenuConstants.EDIT_GROUP,
		// deleteAction);
		//
		// // Add load resource action
		// LoadResourceAction loadAction = new
		// LoadResourceAction(domain.getEMFEditingDomain());
		// manager.appendToGroup(IOutlineMenuConstants.ADDITIONS_GROUP,
		// loadAction);
		// }
	}

	/**
	 * Add to the MenuManager the actions for a single object.
	 * 
	 * @param manager
	 *            The menu to fill
	 * @param selection
	 *            the selected object
	 */
	private void createSingleSelectionMenu(IMenuManager manager, Object selection) {
		EObject selectedObject = null;

		if (selection instanceof EObject) {
			selectedObject = (EObject) selection;
		} else if ((selection instanceof IWrapperItemProvider) || (selection instanceof FeatureMap.Entry)) {
			selectedObject = (EObject) AdapterFactoryEditingDomain.unwrap(selection);
		}

		if (selectedObject != null) {
			if (isEMFMenuEnabledFor(selectedObject)) {
				createEMFMenu(manager, selectedObject);
			}
			if (isDiagramsMenuEnabledFor(selectedObject)) {
				createDiagramsMenu(manager, selectedObject);
			}
			if (isControlActionEnabledFor(selectedObject)) {
				createControlActions(manager);
			}
		}

	}

	/**
	 * Subclasses should override this method to control enabling/disabling the
	 * EMF menu for the current selection.
	 * 
	 * Default returns true.
	 * 
	 * @param selectedObject
	 * @return whether the control action is enabled for the current selection
	 *         or not.
	 */
	protected boolean isEMFMenuEnabledFor(EObject selectedObject) {
		return true;
	}

	/**
	 * Subclasses should override this method to add their own actions related
	 * to EMF stuff
	 */
	protected void createEMFMenu(IMenuManager manager, EObject selectedObject) {

		if (!isEMFMenuEnabledFor(selectedObject)) {
			return;
		}

		// TODO Restore this
		// MixedEditDomain domain = (MixedEditDomain)
		// modeler.getAdapter(MixedEditDomain.class);
		//
		// if (domain != null)
		// {
		// IPreferenceStore ps = modeler.getPreferenceStore();
		// if (ps != null)
		// {
		// String id =
		// ps.getString(ModelerPreferenceConstants.CREATE_CHILD_MENU_PREF);
		// CreateChildMenuConfiguration config =
		// OutlineManager.getInstance().getCreateChildMenuConfiguration(id);
		// ICreateChildMenu createChildMenu = null;
		// if (config != null)
		// {
		// createChildMenu = config.getMenu();
		// }
		// if (createChildMenu == null)
		// {
		// createChildMenu = new DefaultCreateChildMenu();
		// }
		//
		// createChildMenu.removeAll();
		// createChildMenu.setMixedEditDomain(domain);
		// createChildMenu.setSelectedEObject(selectedObject);
		// createChildMenu.createMenuContents();
		//
		// manager.appendToGroup(IOutlineMenuConstants.NEW_GROUP,
		// createChildMenu);
		// }
		// }
	}

	/**
	 * Subclasses should override this method to control enabling/disabling the
	 * Diagrams menu for the current selection.
	 * 
	 * Default returns true.
	 * 
	 * @param selectedObject
	 * @return whether the control action is enabled for the current selection
	 *         or not.
	 */
	protected boolean isDiagramsMenuEnabledFor(EObject selectedObject) {
		return true;
	}

	/**
	 * Subclasses should override this method to add their own actions related
	 * to Diagram stuff
	 */
	protected void createDiagramsMenu(IMenuManager manager, EObject selectedObject) {
		// Do nothing by default
	}

	/**
	 * Subclasses should override this method to control enabling/disabling the
	 * control action for the current selection.
	 * 
	 * Default returns true.
	 * 
	 * @param selectedObject
	 * @return whether the control action is enabled for the current selection
	 *         or not.
	 */
	protected boolean isControlActionEnabledFor(EObject selectedObject) {
		return true;
	}

	/**
	 * Subclasses should override this method to add their own actions related
	 * to Control/Uncontrol actions
	 */
	private void createControlActions(IMenuManager manager) {
		// Empty implementation
	}

	/**
	 * Refresh the tree viewer in the UI thread if we are in a different thread
	 */
	protected final void refreshViewer() {
		refreshViewer(false);
	}

	/**
	 * Refresh the tree viewer in the UI thread if we are in a different thread
	 * 
	 * @param updateLabel
	 *            <code>true</code> if the label must be refreshed
	 */
	protected final void refreshViewer(final boolean updateLabel) {
		if ((viewer != null) && !viewer.getTree().isDisposed()) {
			if (Display.getCurrent() != Display.getDefault()) {
				syncRefreshViewer(updateLabel);
			} else {
				viewer.refresh(updateLabel);
			}
		}
	}

	/**
	 * Refresh the tree viewer in the UI thread
	 * 
	 * @param updateLabel
	 *            <code>true</code> if the label must be refreshed
	 */
	private void syncRefreshViewer(final boolean updateLabel) {
		viewer.getControl().getDisplay().syncExec(new Runnable() {

			public void run() {
				viewer.refresh(updateLabel);
			}
		});
	}

	/**
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	@Override
	public void dispose() {
		unhookListeners();
		super.dispose();
	}

	/**
	 * Return the IDiagramWorkbenchPart
	 * 
	 * @return IDiagramWorkbenchPart
	 */
	protected IDiagramWorkbenchPart getEditor() {
		return editor;
	}

	/**
	 * Get the AdapterFactory associated with an editor
	 * 
	 * @return AdapterFactory
	 */
	protected abstract AdapterFactory getAdapterFactory();


}
