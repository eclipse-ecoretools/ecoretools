/*******************************************************************************
 * Copyright (c) 2009, 2023 THALES GLOBAL SERVICES and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.ui.views;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.ui.views.EReferencesContentProvider.WrappedEClass;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.util.TransferDragSourceListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;

/**
 * A simple drag target adapter for {@link LocalSelectionTransfer}.
 *
 * @author mchauvin
 */
public class EcoreToolsViewsDragTargetAdapter extends DragSourceAdapter implements TransferDragSourceListener {

    private ISelectionProvider provider;

    /**
     * Construct a new drag target adapter from the selection provider given as parameter.
     *
     * @param provider
     *            the selection provider
     */
    public EcoreToolsViewsDragTargetAdapter(final ISelectionProvider provider) {
        this.provider = provider;
    }

    @Override
    public Transfer getTransfer() {
        return LocalSelectionTransfer.getTransfer();
    }

    @Override
    public void dragStart(final DragSourceEvent event) {
        final ISelection selection = provider.getSelection();
        if (selection instanceof StructuredSelection) {
            StructuredSelection sSelection = (StructuredSelection) selection;
            List<EObject> ecoreObjects = new ArrayList<>(sSelection.size());
            Iterator<?> it = sSelection.iterator();
            while (it.hasNext()) {
                Object obj = it.next();
                if (obj instanceof EObject) {
                    ecoreObjects.add((EObject) obj);
                } else if (obj instanceof WrappedEClass) {
                    ecoreObjects.add(((WrappedEClass) obj).getWrappedEClass());
                }
            }
            ISelection unwrapped = new StructuredSelection(ecoreObjects);
            LocalSelectionTransfer.getTransfer().setSelection(unwrapped);
            LocalSelectionTransfer.getTransfer().setSelectionSetTime(event.time & 0xFFFFFFFFL);
            event.doit = true;
        }
    }

    @Override
    public void dragSetData(final DragSourceEvent event) {
        event.data = LocalSelectionTransfer.getTransfer().getSelection();
    }

    @Override
    public void dragFinished(final DragSourceEvent event) {
        LocalSelectionTransfer.getTransfer().setSelection(null);
        LocalSelectionTransfer.getTransfer().setSelectionSetTime(0);
    }
}
