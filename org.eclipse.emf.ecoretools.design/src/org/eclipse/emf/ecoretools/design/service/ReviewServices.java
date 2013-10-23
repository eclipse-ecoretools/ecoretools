package org.eclipse.emf.ecoretools.design.service;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.business.api.helper.SiriusUtil;
import org.eclipse.sirius.business.api.query.DDiagramElementQuery;
import org.eclipse.sirius.viewpoint.DDiagram;
import org.eclipse.sirius.viewpoint.DNodeList;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.DSemanticDiagram;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

public class ReviewServices extends DesignServices{

	public Collection<EPackage> getPackageDependencies(EPackage source) {
		// used to be
		// [self->filter(ecore::EPackage).eClassifiers->filter(ecore::EClass).eAllStructuralFeatures.eType->union(self->filter(ecore::EPackage).eClassifiers->filter(ecore::EClass).eAllSuperTypes).eContainer(ecore::EPackage)->asOrderedSet()->asSequence()->excluding(self)/]
		Set<EPackage> dependencies = Sets.newLinkedHashSet();

		for (EClass containedClassifiers : Iterables.filter(
				source.getEClassifiers(), EClass.class)) {
			dependencies.addAll(externalDependencies(containedClassifiers));
		}

		dependencies.remove(source);
		return dependencies;
	}

	private Set<EPackage> externalDependencies(EClass containedClassifiers) {
		Set<EPackage> dependencies = Sets.newLinkedHashSet();
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
		DDiagram diag = SiriusUtil.findDiagram(view);
		if (diag != null) {
			Set<EPackage> displayedPackages = getDisplayedEPackages(diag);
			// we don't want coupling errors with our own package.
			displayedPackages.remove(clazz.getEPackage());
			Set<String> explanations = Sets.newLinkedHashSet();
			if (diag != null) {
				for (EPackage dep : externalDependencies(clazz)) {
					if (displayedPackages.contains(dep)) {
						explanations.add("Dependency to " + dep.getNsURI()
								+ ": \n"
								+ externalDependenciesExplanation(dep, clazz));
					}
				}
			}
			return Joiner.on('\n').join(explanations);
		}
		return "";
	}

	public String getDependenciesLabel(EClass clazz) {
		return clazz.getName() + " (" + externalDependencies(clazz).size()
				+ ")";
	}

	public Collection<EClassifier> getElementsIntroducingDependencies(
			EPackage source, DSemanticDiagram diagram) {
		Set<EClassifier> classifiersIntroducingDependencies = Sets
				.newLinkedHashSet();

		Set<EPackage> otherPackages = getDisplayedEPackages(diagram);
		otherPackages.remove(source);

		for (EClass containedClassifiers : Iterables.filter(
				source.getEClassifiers(), EClass.class)) {
			for (EStructuralFeature eRef : containedClassifiers
					.getEAllStructuralFeatures()) {
				if (eRef.getEType() != null
						&& otherPackages
								.contains(eRef.getEType().getEPackage())) {
					classifiersIntroducingDependencies.add(eRef
							.getEContainingClass());
				}
			}
			for (EClass superClazz : containedClassifiers.getEAllSuperTypes()) {
				if (otherPackages.contains(superClazz.getEPackage())) {
					classifiersIntroducingDependencies
							.add(containedClassifiers);
				}
			}
		}
		return classifiersIntroducingDependencies;
	}

	private Set<EPackage> getDisplayedEPackages(DDiagram diagram) {
		Set<EPackage> otherPackages = Sets.newLinkedHashSet();
		for (DNodeList list : Iterables.filter(
				diagram.getOwnedDiagramElements(), DNodeList.class)) {
			if (!new DDiagramElementQuery(list).isHidden()
					&& list.getTarget() instanceof EPackage) {
				otherPackages.add((EPackage) list.getTarget());
			}
		}
		return otherPackages;
	}

	private String externalDependenciesExplanation(EPackage dep, EClass clazz) {
		Set<String> explanations = Sets.newLinkedHashSet();
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
		return Joiner.on('\n').join(explanations);
	}
}
