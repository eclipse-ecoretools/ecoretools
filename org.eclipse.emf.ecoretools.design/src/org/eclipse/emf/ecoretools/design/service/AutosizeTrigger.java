/*******************************************************************************
 * Copyright (c) 2014, 2024 Obeo
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

import java.util.Collection;
import java.util.LinkedHashSet;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.sirius.business.api.session.ModelChangeTrigger;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;

public class AutosizeTrigger implements ModelChangeTrigger {

	public static final Adapter AUTO_SIZE_MARKER = new AdapterImpl();
	private TransactionalEditingDomain domain;

	public AutosizeTrigger(TransactionalEditingDomain domain) {
		this.domain = domain;
	}

	public static final NotificationFilter IS_GMF_NODE_ATTACHMENT = new NotificationFilter.Custom() {
		public boolean matches(Notification input) {
			return input.getNewValue() instanceof Node && input.getFeature() instanceof EReference ref && ref.isContainment();
		}
	};

	public Option<Command> localChangesAboutToCommit(Collection<Notification> notifications) {
		Collection<Node> toMakeAutosize = collectElementsToAutoSize(notifications);
		if (!toMakeAutosize.isEmpty()) {
			Command result = new RecordingCommand(domain) {
				@Override
				protected void doExecute() {
					for (Node node : toMakeAutosize) {
						if (node.getLayoutConstraint() instanceof Bounds bounds) {
						    bounds.setWidth(-1);
						    bounds.setHeight(-1);
						}
					}
				}
			};
			return Options.newSome(result);
		}
		return Options.newNone();
	}

    private Collection<Node> collectElementsToAutoSize(Collection<Notification> notifications) {
        Collection<Node> toMakeAutosize = new LinkedHashSet<>();
		for (Notification notif : notifications) {
			Node nd = (Node) notif.getNewValue();
            if (nd.getElement() instanceof DSemanticDecorator semanticDecorator) {
                EObject semanticObject = semanticDecorator.getTarget();
                if (semanticObject != null) {
                    var iter = semanticObject.eAdapters().iterator();
                    while (iter.hasNext()) {
                        Adapter adapter = iter.next();
                        if (adapter == AUTO_SIZE_MARKER) {
                            iter.remove();
                            toMakeAutosize.add(nd);
                            break;
                        }
                    }
                }
            }
		}
        return toMakeAutosize;
    }

	public int priority() {
		return 0;
	}

}
