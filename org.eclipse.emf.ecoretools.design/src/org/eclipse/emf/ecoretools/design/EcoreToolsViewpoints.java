/*******************************************************************************
 * Copyright (c) 2014, 2023 Obeo.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.viewpoint.description.Viewpoint;

public class EcoreToolsViewpoints {

    private ViewpointRegistry registry;

    public EcoreToolsViewpoints(ViewpointRegistry registry) {
        this.registry = registry;
    }

    public Viewpoint design() {
        URI uri = URI.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/Design");
        return getViewpointOrNull(uri);
    }

    protected Viewpoint getViewpointOrNull(URI uri) {
        try {
            return registry.getViewpoint(uri);
        } catch (Exception e) {
            /*
             * If we can't retrieve the viewpoint then it means we don't
             * contribute it.
             */
        }
        return null;
    }

    public Viewpoint archetype() {
        return getViewpointOrNull(URI.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/Archetype"));
    }

    public Viewpoint review() {
        return getViewpointOrNull(URI.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/Review"));
    }

    public Viewpoint generation() {
        return getViewpointOrNull(URI.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/Generation"));
    }

    public Set<Viewpoint> all() {
        Set<Viewpoint> ecoreToolsViewpoints = new LinkedHashSet<>();
        Viewpoint vp = design();
        if (vp != null)
            ecoreToolsViewpoints.add(vp);
        vp = archetype();
        if (vp != null)
            ecoreToolsViewpoints.add(vp);
        vp = review();
        if (vp != null)
            ecoreToolsViewpoints.add(vp);
        vp = generation();
        if (vp != null)
            ecoreToolsViewpoints.add(vp);
        return ecoreToolsViewpoints;
    }

    public static EcoreToolsViewpoints fromViewpointRegistry() {
        return new EcoreToolsViewpoints(ViewpointRegistry.getInstance());
    }
}
