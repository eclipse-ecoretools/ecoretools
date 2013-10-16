/*******************************************************************************
 * Copyright (c) 2009 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.internal.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.internal.views.EReferencesContentProvider.WrappedEClass;
import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

/**
 * A simple drag target adapter for {@link LocalSelectionTransfer}.
 * 
 * @author mchauvin
 */
public class EcoreToolsViewsDragTargetAdapter extends DragSourceAdapter
		implements TransferDragSourceListener {

	private ISelectionProvider provider;

	/**
	 * Construct a new drag target adapter from the selection provider given as
	 * parameter.
	 * 
	 * @param provider
	 *            the selection provider
	 */
	public EcoreToolsViewsDragTargetAdapter(final ISelectionProvider provider) {
		this.provider = provider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.util.TransferDragSourceListener#getTransfer()
	 */
	public Transfer getTransfer() {
		return LocalSelectionTransfer.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragStart(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	@Override
	public void dragStart(final DragSourceEvent event) {
		final ISelection selection = provider.getSelection();
		if (selection instanceof StructuredSelection) {
			StructuredSelection sSelection = (StructuredSelection) selection;
			List<EObject> ecoreObjects = new ArrayList<EObject>(
					sSelection.size());
			Iterator<Object> it = sSelection.iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				if (obj instanceof EObject) {
					ecoreObjects.add((EObject) obj);
				} else if (obj instanceof WrappedEClass) {
					ecoreObjects.add(((WrappedEClass) obj).getWrappedEClass());
				}

			}
			ISelection unwrapped = new StructuredSelection(ecoreObjects);
			LocalSelectionTransfer.getInstance().setSelection(unwrapped);
			LocalSelectionTransfer.getInstance().setSelectionSetTime(
					event.time & 0xFFFFFFFFL);
			event.doit = true;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	@Override
	public void dragSetData(final DragSourceEvent event) {
		event.data = LocalSelectionTransfer.getInstance().getSelection();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.dnd.DragSourceAdapter#dragFinished(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	@Override
	public void dragFinished(final DragSourceEvent event) {
		LocalSelectionTransfer.getInstance().setSelection(null);
		LocalSelectionTransfer.getInstance().setSelectionSetTime(0);
	}
}
