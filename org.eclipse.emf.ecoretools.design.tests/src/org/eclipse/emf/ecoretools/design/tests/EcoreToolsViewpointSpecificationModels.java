/*******************************************************************************
 * Copyright (c) 2013, 2024 Obeo.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.tests;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.viewpoint.description.Viewpoint;

public class EcoreToolsViewpointSpecificationModels {

	private Viewpoint design;
	private Viewpoint review;
	private Viewpoint archetype;
	private Viewpoint generation;

	public EcoreToolsViewpointSpecificationModels() {
		this.design = getEcoreToolsViewpointRegisteredInstance("Design");
		this.review = getEcoreToolsViewpointRegisteredInstance("Review");
		this.archetype = getEcoreToolsViewpointRegisteredInstance("Archetype");
		this.generation = getEcoreToolsViewpointRegisteredInstance("Generation");
	}

    public static Viewpoint getEcoreToolsViewpointRegisteredInstance(String name) {
        return ViewpointRegistry.getInstance().getViewpoint(URI.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/" + name));
    }

	Iterator<EObject> eAllContents() {
	    return Stream.of(design, review, archetype, generation).flatMap(vsm -> {
	        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(vsm.eAllContents(), Spliterator.ORDERED), false);
	    }).iterator();
	}

}
