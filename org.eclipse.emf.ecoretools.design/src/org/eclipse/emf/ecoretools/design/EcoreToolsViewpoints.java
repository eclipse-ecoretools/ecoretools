/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.viewpoint.description.Viewpoint;

import com.google.common.collect.Sets;

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
        Set<Viewpoint> ecoreToolsViewpoints = Sets.newLinkedHashSet();
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
