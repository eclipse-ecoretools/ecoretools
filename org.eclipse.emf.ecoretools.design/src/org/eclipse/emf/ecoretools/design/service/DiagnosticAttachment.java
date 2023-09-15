/*******************************************************************************
 * Copyright (c) 2013 THALES GLOBAL SERVICES and Others
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.service;

import java.util.Iterator;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Optional;
import com.google.common.collect.Iterators;

public class DiagnosticAttachment extends AdapterImpl {

    private Diagnostic diagnostic;

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }

    public static DiagnosticAttachment getAttachment(EObject cur) {
        Iterator<DiagnosticAttachment> it = Iterators.filter(cur.eAdapters().iterator(), DiagnosticAttachment.class);
        DiagnosticAttachment found = null;
        if (it.hasNext()) {
            found = it.next();
        }
        return found;
    }

    public static DiagnosticAttachment getOrCreate(EObject cur, Diagnostic diag) {
        DiagnosticAttachment found = getAttachment(cur);
        if (found == null) {
            found = new DiagnosticAttachment();
            cur.eAdapters().add(found);
            found.setDiagnostic(diag);
        }
        return found;
    }

    public static Optional<Diagnostic> get(EObject cur) {
        DiagnosticAttachment found = getAttachment(cur);
        if (found != null) {
            return Optional.fromNullable(found.getDiagnostic());
        }
        return Optional.absent();
    }

}
