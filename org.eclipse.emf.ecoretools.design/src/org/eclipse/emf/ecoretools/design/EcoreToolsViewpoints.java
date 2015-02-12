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

import org.eclipse.emf.common.util.URI;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.viewpoint.description.Viewpoint;

public class EcoreToolsViewpoints {

	private ViewpointRegistry registry;

	public EcoreToolsViewpoints(ViewpointRegistry registry) {
		this.registry = registry;
	}

	public Viewpoint design() {
		return registry
				.getViewpoint(URI
						.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/Design"));
	}

	public Viewpoint archetype() {
		return registry
				.getViewpoint(URI
						.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/Archetype"));
	}

	public Viewpoint review() {
		return registry
				.getViewpoint(URI
						.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/Review"));
	}

	public Viewpoint generation() {
		return registry
				.getViewpoint(URI
						.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/Generation"));
	}

	public static EcoreToolsViewpoints fromViewpointRegistry() {
		return new EcoreToolsViewpoints(ViewpointRegistry.getInstance());
	}
}
