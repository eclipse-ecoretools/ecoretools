/*******************************************************************************
 * Copyright (c) 2014 Obeo
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

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;

public class AutosizeTrigger implements ModelChangeTrigger {

	public static final Adapter AUTO_SIZE_MARKER = new AdapterImpl();
	private TransactionalEditingDomain domain;

	public AutosizeTrigger(TransactionalEditingDomain domain) {
		super();
		this.domain = domain;
	}

	public static final NotificationFilter IS_GMF_NODE_ATTACHMENT = new NotificationFilter.Custom() {

		public boolean matches(Notification input) {
			return input.getNewValue() instanceof Node
					&& input.getFeature() instanceof EReference
					&& ((EReference) input.getFeature()).isContainment();

		}
	};

	public Option<Command> localChangesAboutToCommit(
			Collection<Notification> notifications) {
		final Collection<Node> toMakeAutosize = Sets.newLinkedHashSet();
		for (Notification notif : notifications) {
			Node nd = (Node) notif.getNewValue();
			if (nd.getElement() instanceof DSemanticDecorator) {
				EObject semanticObject = ((DSemanticDecorator) nd.getElement())
						.getTarget();
				if (semanticObject instanceof EObject) {
					UnmodifiableIterator<Adapter> filter = Iterators.filter(
							semanticObject.eAdapters().iterator(),
							new Predicate<Adapter>() {
								public boolean apply(Adapter input) {
									return input == AUTO_SIZE_MARKER;
								}
							});

					if (filter.hasNext()) {
						semanticObject.eAdapters().remove(filter.next());
						toMakeAutosize.add(nd);
					}
				}
			}
		}
		if (toMakeAutosize.size() > 0) {
			Command result = new RecordingCommand(domain) {

				@Override
				protected void doExecute() {
					for (Node node : toMakeAutosize) {
						if (node.getLayoutConstraint() instanceof Bounds) {
							((Bounds) node.getLayoutConstraint()).setWidth(-1);
							((Bounds) node.getLayoutConstraint()).setHeight(-1);
						}
					}
				}
			};
			return Options.newSome(result);
		}
		return Options.newNone();
	}

	public int priority() {
		return 0;
	}

}
