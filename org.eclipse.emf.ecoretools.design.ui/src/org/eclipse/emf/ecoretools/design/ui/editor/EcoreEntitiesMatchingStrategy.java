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
package org.eclipse.emf.ecoretools.design.ui.editor;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.ui.business.api.session.SessionSpecificEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;

public class EcoreEntitiesMatchingStrategy implements IEditorMatchingStrategy {

	public boolean matches(IEditorReference editorRef, IEditorInput input) {
		boolean result = false;
		SessionSpecificEditorInput sseiRef = getSessionSpecificEditorInput(editorRef);

		if (sseiRef != null) {
			if (sseiRef.equals(input)) {
				result = true;
			} else if (input instanceof IFileEditorInput) {
				Session sessionRef = sseiRef.getSession();
				URI inputUri = URI.createPlatformResourceURI(
						((IFileEditorInput) input).getFile().getFullPath()
								.toString(), true);

				if (sessionRef != null && sessionRef.isOpen()) {
					Resource currentRes = sessionRef
							.getTransactionalEditingDomain().getResourceSet()
							.getResource(inputUri, false);
					result = currentRes != null
							&& sessionRef.getSemanticResources().contains(
									currentRes);
				}
			}
		}

		return result;
	}

	private SessionSpecificEditorInput getSessionSpecificEditorInput(
			IEditorReference editorRef) {
		SessionSpecificEditorInput ssei = null;
		try {
			IEditorInput editorInput = editorRef.getEditorInput();
			if (editorInput instanceof SessionSpecificEditorInput) {
				ssei = (SessionSpecificEditorInput) editorInput;
			}
		} catch (PartInitException e) {
			// DO nothing.
		}
		return ssei;
	}

}
