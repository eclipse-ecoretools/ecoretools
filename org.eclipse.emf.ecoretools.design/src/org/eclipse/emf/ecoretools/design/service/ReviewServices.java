/*******************************************************************************
 * Copyright (c) 2013, 2024 Obeo
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
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeList;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.business.api.query.DDiagramElementQuery;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;

public class ReviewServices extends DesignServices {

	public Collection<EPackage> getPackageDependencies(EPackage source) {
		// used to be
		// [self->filter(ecore::EPackage).eClassifiers->filter(ecore::EClass).eAllStructuralFeatures.eType->union(self->filter(ecore::EPackage).eClassifiers->filter(ecore::EClass).eAllSuperTypes).eContainer(ecore::EPackage)->asOrderedSet()->asSequence()->excluding(self)/]
		Set<EPackage> dependencies = new LinkedHashSet<>();

		for (EClassifier classifier : source.getEClassifiers()) {
		    if (classifier instanceof EClass eClass) {
		        dependencies.addAll(externalDependencies(eClass));
		    }
        }

		dependencies.remove(source);
		return dependencies;
	}

	private Set<EPackage> externalDependencies(EClass containedClassifiers) {
		Set<EPackage> dependencies = new LinkedHashSet<>();
		for (EStructuralFeature eRef : containedClassifiers
				.getEAllStructuralFeatures()) {
			if (eRef.getEType() != null
					&& eRef.getEType().getEPackage() != null) {
				dependencies.add(eRef.getEType().getEPackage());
			}
		}
		for (EClass superClazz : containedClassifiers.getEAllSuperTypes()) {
			if (superClazz.getEPackage() != null) {
				dependencies.add(superClazz.getEPackage());
			}
		}
		return dependencies;
	}

	public Integer getDependenciesAmount(EObject a) {
		// TODO
		return 2;
	}

	public String getDependenciesTooltip(EClass clazz, DSemanticDecorator view) {
		Option<DDiagram> diag = new org.eclipse.sirius.diagram.business.api.query.EObjectQuery(view).getParentDiagram();
		if (diag.some()) {
			Set<EPackage> displayedPackages = getDisplayedEPackages(diag.get());
			// we don't want coupling errors with our own package.
			displayedPackages.remove(clazz.getEPackage());
			Set<String> explanations = new LinkedHashSet<>();
			if (diag != null) {
				for (EPackage dep : externalDependencies(clazz)) {
					if (displayedPackages.contains(dep)) {
						explanations.add("Dependency to " + dep.getNsURI()
								+ ": \n"
								+ externalDependenciesExplanation(dep, clazz));
					}
				}
			}
			return String.join("\n", explanations);
		}
		return "";
	}

	public String getDependenciesLabel(EClass clazz) {
		return clazz.getName() + " (" + externalDependencies(clazz).size() + ")";
	}

    public Collection<EClassifier> getElementsIntroducingDependencies(EPackage source, DSemanticDiagram diagram) {
		Set<EClassifier> classifiersIntroducingDependencies = new LinkedHashSet<>();

		Set<EPackage> otherPackages = getDisplayedEPackages(diagram);
		otherPackages.remove(source);

        for (EClassifier classifier : source.getEClassifiers()) {
            if (classifier instanceof EClass eClass) {
                for (EStructuralFeature eRef : eClass.getEAllStructuralFeatures()) {
                    if (eRef.getEType() != null && otherPackages.contains(eRef.getEType().getEPackage())) {
                        classifiersIntroducingDependencies.add(eRef.getEContainingClass());
                    }
                }
                for (EClass superClazz : eClass.getEAllSuperTypes()) {
                    if (otherPackages.contains(superClazz.getEPackage())) {
                        classifiersIntroducingDependencies.add(eClass);
                    }
                }
            }
        }
		return classifiersIntroducingDependencies;
	}

	private Set<EPackage> getDisplayedEPackages(DDiagram diagram) {
		Set<EPackage> otherPackages = new LinkedHashSet<>();
        for (DDiagramElement element : diagram.getOwnedDiagramElements()) {
            if (element instanceof DNodeList list && !new DDiagramElementQuery(list).isHidden() && list.getTarget() instanceof EPackage) {
                otherPackages.add((EPackage) list.getTarget());
            }
        }
		return otherPackages;
	}

	private String externalDependenciesExplanation(EPackage dep, EClass clazz) {
		Set<String> explanations = new LinkedHashSet<>();
		for (EStructuralFeature eRef : clazz.getEAllStructuralFeatures()) {
			if (eRef.getEType() != null && eRef.getEType().getEPackage() == dep) {
				explanations.add(" - " + eRef.eClass().getName() + " "
						+ eRef.getName() + " refers to type "
						+ eRef.getEType().getName() + " in package "
						+ dep.getName());
			}
		}
		for (EClass superClazz : clazz.getEAllSuperTypes()) {
			if (superClazz.getEPackage() == dep) {
				explanations.add(" - EClass" + clazz.getName()
						+ " extends type " + superClazz.getName()
						+ " in package " + dep.getName());
			}
		}
		return String.join("\n", explanations);
	}
}
