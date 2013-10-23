/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.tests;

import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.viewpoint.description.Viewpoint;

import com.google.common.collect.Iterators;

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
		return ViewpointRegistry.getInstance().getViewpoint(
				URI.createURI("viewpoint:/org.eclipse.emf.ecoretools.design/"
						+ name));
	}

	Iterator<EObject> eAllContents() {
		return Iterators.concat(design.eAllContents(), review.eAllContents(),
				archetype.eAllContents(), generation.eAllContents());
	}

}
