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
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.sirius.business.api.session.ModelChangeTrigger;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;

import com.google.common.collect.Lists;

/**
 * a {@link ModelChangeTrigger} which update EOpposite's opposite based on the
 * EReference change.
 * 
 * @author Cedric Brun <cedric.brun@obeo.fr>
 * 
 */
public class EOppositeSetUnset implements ModelChangeTrigger {

    private Session session;

    public EOppositeSetUnset(Session set) {
        this.session = set;
    }

    public static final NotificationFilter SHOULD_UPDATE = new NotificationFilter.Custom() {

        public boolean matches(Notification notification) {
            if (!notification.isTouch() && notification.getFeature() == EcorePackage.eINSTANCE.getEReference_EOpposite() && notification.getNotifier() instanceof EReference) {
                return ((EReference) notification.getNotifier()).getEOpposite() != null;
            }
            return false;
        }
    };

    public Option<Command> localChangesAboutToCommit(Collection<Notification> notifications) {

        final Collection<EReference> toBeCleared = Lists.newArrayList();
        final Collection<EReference> toBeSet = Lists.newArrayList();

        for (Notification notif : notifications) {
            if (SHOULD_UPDATE.matches(notif)) {
                /*
                 * this is an EReference now being containment or no more.
                 */
                EReference updatedRef = (EReference) notif.getNotifier();
                if (notif.getEventType() == Notification.SET) {

                    toBeSet.add(updatedRef);
                }
                if (notif.getEventType() == Notification.UNSET) {
                    toBeCleared.add(updatedRef);
                }
            }
        }
        if (toBeCleared.size() > 0 || toBeSet.size() > 0) {
            Command result = new RecordingCommand(session.getTransactionalEditingDomain()) {

                @Override
                protected void doExecute() {
                    for (EReference toClear : toBeCleared) {
                        if (toClear.getEOpposite() != null) {
                            toClear.getEOpposite().setEOpposite(null);
                        }

                    }
                    for (EReference toSet : toBeSet) {
                        if (toSet.getEOpposite() != null) {
                            toSet.getEOpposite().setEOpposite(toSet);
                        }
                    }

                }
            };
            return Options.newSome(result);
        }
        return Options.newNone();
    }

    public int priority() {
        return GenModelAutoReload.PRIORITY + 1;
    }

}
