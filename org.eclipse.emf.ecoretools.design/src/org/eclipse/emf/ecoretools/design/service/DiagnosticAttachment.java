/*******************************************************************************
 * Copyright (c) 2013 THALES GLOBAL SERVICES and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

	public static DiagnosticAttachment getOrCreate(EObject cur, Diagnostic diag) {
		Iterator<DiagnosticAttachment> it = Iterators.filter(cur.eAdapters()
				.iterator(), DiagnosticAttachment.class);
		DiagnosticAttachment found = null;
		if (it.hasNext()) {
			found = it.next();
		} else {
			found = new DiagnosticAttachment();
			cur.eAdapters().add(found);
			found.setDiagnostic(diag);
		}
		return found;
	}

	public static Optional<Diagnostic> get(EObject cur) {
		Iterator<DiagnosticAttachment> it = Iterators.filter(cur.eAdapters()
				.iterator(), DiagnosticAttachment.class);
		DiagnosticAttachment found = null;
		if (it.hasNext()) {
			found = it.next();
			return Optional.fromNullable(found.getDiagnostic());
		}
		return Optional.absent();
	}

}
