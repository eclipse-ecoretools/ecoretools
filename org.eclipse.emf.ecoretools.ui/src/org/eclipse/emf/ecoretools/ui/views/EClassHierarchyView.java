/***********************************************************************
 * Copyright (c) 2007 Anyware Technologies
 * Copyright (c) 2015 IRT AESE (IRT Saint Exupery)
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    David Sciamma / Jacques Lescot (Anyware Technologies) - initial API and implementation
 *    Pierre Gaufillet (IRT Saint Exupery)                  - Extension to a general purpose 
 *                                                            EClass information view
 *
 * $Id: EClassHierarchyView.java,v 1.6 2008/05/19 09:26:31 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.ui.views;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.ui.Activator;
import org.eclipse.emf.ecoretools.ui.Messages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.progress.WorkbenchJob;

/**
 * This view analyzes the hierarchy of an EClass (ascendant, descendant...)
 * 
 * @author <a href="david.sciamma@anyware-tech.com">David Sciamma</a>
 * @author <a href="pierre.gaufillet@irt-saintexupery.com">Pierre Gaufillet</a>
 */
public class EClassHierarchyView extends AnalysisView {

	/**
	 * The ID of the view
	 */
	public static final String VIEW_ID = "org.eclipse.emf.ecoretools.internal.views.EClassHierarchyView"; //$NON-NLS-1$

	private static enum SortDirection {
		ASCENDANT, DESCENDANT
	};

	private final static int JOB_DELAY = 0; 
	/**
	 * UI parts
	 */
	private SashForm splitter;

	private TreeViewer hierarchyTree;

	private TreeViewer featuresViewer;

	private FeatureLabelProvider featureLabelProvider;

	private SortDirection kind = SortDirection.ASCENDANT;

	private ISelectionChangedListener hierarchicalTreeSelectionChangedListener;

	/**
	 * The job used to refresh the EClass hierarchy.
	 */
	private Job refreshHierarchyJob;

	/**
	 * The job used to refresh the features.
	 */
	private Job refreshFeaturesJob;

	/**
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		splitter = new SashForm(parent, SWT.HORIZONTAL);

		hierarchicalTreeSelectionChangedListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				doSelectionChanged(event);
			};
		};
		hierarchyTree = new TreeViewer(splitter, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		hierarchyTree.addPostSelectionChangedListener(hierarchicalTreeSelectionChangedListener);
		hierarchyTree.getControl().addDisposeListener(new DisposeListener() {

			/**
			 * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
			 */
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (refreshHierarchyJob != null) {
					refreshHierarchyJob.cancel();
				}
				if (refreshFeaturesJob != null) {
					refreshFeaturesJob.cancel();
				}
			}
		});

		prepareViewerForDragToSirius(hierarchyTree);
		setKind(SortDirection.ASCENDANT);

		featuresViewer = new TreeViewer(splitter, SWT.H_SCROLL | SWT.V_SCROLL);
		featuresViewer.setContentProvider(new FeatureContentProvider());
		featureLabelProvider = new FeatureLabelProvider();
		featuresViewer.setLabelProvider(featureLabelProvider);

		refresh();
	}

	private void doSelectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if (selection instanceof IStructuredSelection) {
			EObject selectedObject = getSelection((IStructuredSelection) selection);
			if (selectedObject != null /* && sProvider.equals(hierarchyTree) */) {
				refreshFeatures(findEClass(selectedObject));
			}
		}
	}

	/**
	 * Changes the kind of hierarchy displayed in this view
	 * 
	 * @param hierarchyKind
	 *            the new kind of hierarchy
	 */
	private void setKind(SortDirection hierarchyKind) {
		kind = hierarchyKind;
		hierarchyTree.setLabelProvider(new EClassInformationLabelProvider());
		switch (kind) {
		case ASCENDANT:
			hierarchyTree.setContentProvider(new EClassHierarchyContentProvider());
			break;
		case DESCENDANT:
			hierarchyTree.setContentProvider(new EClassDescendentHierarchyContentProvider());
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.ecoretools.ui.AnalysisView#refresh(org.eclipse.emf.ecore.EObject
	 * )
	 */
	@Override
	protected void refresh(EObject object) {
		refreshHierarchy(object);
	}

	private void refreshHierarchy(EObject object) {
		// cancel currently running job first, to prevent unnecessary redraw
		if (refreshHierarchyJob != null) {
			refreshHierarchyJob.cancel();
		}

		EClass selectedEClass = findEClass(object);
		if (selectedEClass != null) {
			refreshHierarchyJob = createRefreshHierarchyJob(selectedEClass);
			refreshHierarchyJob.schedule(JOB_DELAY);
		}
	}

	private void refreshFeatures(EObject object) {
		// cancel currently running job first, to prevent unnecessary redraw
		if (refreshFeaturesJob != null) {
			refreshFeaturesJob.cancel();
		}

		EClass selectedEClass = findEClass(object);
		if (selectedEClass != null) {
			refreshFeaturesJob = createRefreshFeaturesJob(selectedEClass);
			refreshFeaturesJob.schedule(JOB_DELAY);
		}
	}

	private EClass findEClass(EObject object) {
		if (object instanceof DSemanticDecorator) {
			object = ((DSemanticDecorator) object).getTarget();
		}

		if (!(object instanceof EClass)) {
			object = object.eClass();
		}
		return (EClass) object;
	}

	private Job createRefreshHierarchyJob(final EClass selection) {
		Job job = new WorkbenchJob(Messages.EClassHierarchyView_RefreshHierarchy) {

			/**
			 * @see org.eclipse.ui.progress.UIJob#runInUIThread(org.eclipse.core.runtime.IProgressMonitor)
			 */
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (hierarchyTree.getControl().isDisposed()) {
					return Status.CANCEL_STATUS;
				}
				try {
					hierarchyTree.getControl().setRedraw(false);
					hierarchyTree.setInput(new EClass[] { selection });
					hierarchyTree.refresh();
					hierarchyTree.expandToLevel(2);
					hierarchyTree.setSelection(new StructuredSelection(selection), true);

				} finally {
					// done updating the tree - set redraw back to true
					hierarchyTree.getControl().setRedraw(true);
				}
				return Status.OK_STATUS;
			}

		};
		job.setSystem(true);

		return job;
	}

	private Job createRefreshFeaturesJob(final EClass selection) {
		Job job = new WorkbenchJob(Messages.EClassHierarchyView_RefreshHierarchy) {

			/**
			 * @see org.eclipse.ui.progress.UIJob#runInUIThread(org.eclipse.core.runtime.IProgressMonitor)
			 */
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (featuresViewer.getControl().isDisposed()) {
					return Status.CANCEL_STATUS;
				}
				try {
					featuresViewer.getControl().setRedraw(false);
					featuresViewer.setInput(new EClass[] { selection });
					featureLabelProvider.setSelection(selection);
					featuresViewer.refresh();
				} finally {
					// done updating the tree - set redraw back to true
					featuresViewer.getControl().setRedraw(true);
				}
				return Status.OK_STATUS;
			}

		};
		job.setSystem(true);

		return job;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.ecoretools.ui.AnalysisView#fillToolBar(org.eclipse.jface.action
	 * .IToolBarManager)
	 */
	@Override
	protected void fillToolBar(IToolBarManager toolBar) {
		IAction ascendantAction = new Action(Messages.EClassHierarchyView_Ascendant, IAction.AS_RADIO_BUTTON) {

			@Override
			public void run() {
				EClassHierarchyView.this.setKind(SortDirection.ASCENDANT);
				refresh();
			}
		};
		ascendantAction.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/elcl16/super_co.gif")); //$NON-NLS-1$
		ascendantAction.setChecked(true);

		IAction descendantAction = new Action(Messages.EClassHierarchyView_Descendant, IAction.AS_RADIO_BUTTON) {

			@Override
			public void run() {
				EClassHierarchyView.this.setKind(SortDirection.DESCENDANT);
				refresh();
			}
		};
		descendantAction.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/elcl16/sub_co.gif")); //$NON-NLS-1$

		toolBar.add(ascendantAction);
		toolBar.add(descendantAction);
		super.fillToolBar(toolBar);
	}
}
