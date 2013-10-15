/*******************************************************************************
 * Copyright (c) 2013 THALES GLOBAL SERVICES and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.emf.ecoretools.design.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.business.api.helper.SiriusUtil;
import org.eclipse.sirius.business.api.query.DDiagramElementQuery;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.common.tools.api.util.AllContents;
import org.eclipse.sirius.viewpoint.DDiagram;
import org.eclipse.sirius.viewpoint.DNodeList;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.DSemanticDiagram;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

/**
 * Generic Ecore services usable from a VSM.
 */
public class EcoreService {
	/**
	 * Returns all the root objects of all the resources in the same
	 * resource-set as the specified object.
	 * 
	 * @param any
	 *            an EObject.
	 * @return all the root objects in the same resource-set as <code>any</code>
	 *         or an empty collection if <code>any</code> is not inside a
	 *         resource-set.
	 */
	public Collection<EObject> allRoots(EObject any) {
		Resource res = any.eResource();
		if (res != null && res.getResourceSet() != null) {
			Collection<EObject> roots = new ArrayList<EObject>();
			for (Resource childRes : res.getResourceSet().getResources()) {
				roots.addAll(childRes.getContents());
			}
			return roots;
		} else {
			return Collections.emptySet();
		}
	}

	public boolean hasNoClassifier(DSemanticDiagram diagram) {
		Iterator<DSemanticDecorator> it = Iterators
				.filter(diagram.getOwnedDiagramElements().iterator(),
						DSemanticDecorator.class);
		while (it.hasNext()) {
			DSemanticDecorator dec = it.next();
			if (dec.getTarget() instanceof EClassifier)
				return true;
		}
		return false;
	}

	public Collection<EClass> getDisplayedEClasses(DSemanticDiagram diagram) {
		Set<EClass> result = Sets.newLinkedHashSet();
		Iterator<DSemanticDecorator> it = Iterators.filter(
				diagram.eAllContents(), DSemanticDecorator.class);
		while (it.hasNext()) {
			DSemanticDecorator dec = it.next();
			if (dec.getTarget() instanceof EClass)
				result.add((EClass) dec.getTarget());
		}
		return result;
	}

	public Collection<EObject> getDisplayedEModelElements(
			DSemanticDiagram diagram) {
		Set<EObject> modelelements = Sets.newLinkedHashSet();
		Iterator<DSemanticDecorator> it = Iterators.filter(
				Iterators.concat(Iterators.singletonIterator(diagram),
						diagram.eAllContents()), DSemanticDecorator.class);
		while (it.hasNext()) {
			DSemanticDecorator dec = it.next();
			if (dec.getTarget() instanceof EModelElement)
				modelelements.add((EModelElement) dec.getTarget());
		}
		return modelelements;
	}

	public List<EObject> getValidsForDiagram(final EObject element,
			final DSemanticDecorator containerView) {
		Predicate<EObject> validForClassDiagram = new Predicate<EObject>() {

			public boolean apply(EObject input) {
				return input instanceof EPackage
						|| input instanceof EClassifier;
			}
		};
		return allValidSessionElements(element, validForClassDiagram);
	}

	public List<EObject> getRelated(EObject root) {
		return new RelatedElementsSwitch().getRelatedElements(root);
	}

	private List<EObject> allValidSessionElements(EObject cur,
			Predicate<EObject> validForClassDiagram) {
		Session found = SessionManager.INSTANCE.getSession(cur);
		List<EObject> result = Lists.newArrayList();
		if (found != null) {
			for (Resource res : found.getSemanticResources()) {
				if (res.getURI().isPlatformResource()
						|| res.getURI().isPlatformPlugin()) {
					Iterators.addAll(result, Iterators.filter(
							res.getAllContents(), validForClassDiagram));
				}
			}
		}
		return result;
	}

	/**
	 * Gets the containing resource name, or null.
	 * 
	 * @param current
	 *            is the object
	 * @return the resource
	 */
	public String eResourceName(final EObject current) {
		if (current != null && current.eResource() != null) {
			return current.eResource().getURI().lastSegment();
		} else {
			return null;
		}
	}

	/**
	 * Computes the label of an EAttribute.
	 */
	public String render(EAttribute attr) {
		return new EAttributeServices().render(attr);
	}

	/**
	 * Performs a "direct edit" operation on an EAttribute.
	 */
	public EAttribute performEdit(EAttribute attr, String editString) {
		return new EAttributeServices().performEdit(attr, editString);
	}

	/**
	 * Computes the label of an EOperation.
	 */
	public String render(EOperation op) {
		return new EOperationServices().render(op);
	}

	public String renderTooltip(EObject current) {
		String result = "";
		Optional<Diagnostic> diag = DiagnosticAttachment.get(current);
		if (diag.isPresent()) {
			result += prettyMessage(diag.get());
		}
		return result;
	}

	private String prettyMessage(Diagnostic diag) {
		String result = "";
		for (Diagnostic child : diag.getChildren()) {
			result += "\n" + severityLabel(child.getSeverity()) + " : "
					+ child.getMessage();
			result += prettyMessage(child);
		}
		return result;
	}

	private String severityLabel(int severity) {
		switch (severity) {
		case Diagnostic.ERROR:
			return "ERROR";
		case Diagnostic.CANCEL:
			return "CANCEL";
		case Diagnostic.INFO:
			return "INFO";
		case Diagnostic.WARNING:
			return "WARNING";
		case Diagnostic.OK:
			return "OK";

		}
		return "UNKNOWN";
	}

	/**
	 * Computes the tooltip of an EOperation.
	 * 
	 * @param op
	 *            the operation to get the tooltip from
	 * @return the tooltip of the given EOperation.
	 */
	public String renderEOperationTooltip(EOperation op) {
		return new EOperationServices().renderEOperationTooltip(op);
	}

	/**
	 * Performs a "direct edit" operation on an EOperation.
	 */
	public EOperation performEdit(EOperation op, String editString) {
		return new EOperationServices().performEdit(op, editString);
	}

	public List<ENamedElement> getAllAssociatedElements(EOperation op) {
		return new EOperationServices().getAllAssociatedElements(op);
	}

	public String renderTooltip(EClass klass) {
		// [eContainer()->filter(ecore::ENamedElement).name + '.' + name/]
		return ((EPackage) klass.eContainer()).getName() + "."
				+ klass.getName();
	}

	/**
	 * Computes the label of an EReference.
	 */
	public String render(EReference ref) {
		return new EReferenceServices().render(ref);
	}

	/**
	 * Performs a "direct edit" operation on an EReference.
	 */
	public EReference performEdit(EReference ref, String editString) {
		return new EReferenceServices().performEdit(ref, editString);
	}

	/**
	 * Finds a type matching the specified name (case-insensitive) in the same
	 * resource-set as obj, or inside Ecore itself if none could be found.
	 * 
	 * @param obj
	 *            the object defining the context in which to look.
	 * @param name
	 *            the name of the type to look for (case-insensitive). Only
	 *            basic type names are supported (no qualified names).
	 *            Whitespace before or after the name is ignored.
	 * @return the first type found in the resource set or Ecore itself which
	 *         matches the specified name.
	 */
	public EClassifier findTypeByName(EObject obj, String name) {
		EClassifier result = findTypeByName(allRoots(obj), name);
		if (result == null) {
			result = findTypeByNameFrom(EcorePackage.eINSTANCE, name);
		}
		return result;
	}

	/**
	 * Returns the root container; it may be this object itself
	 * 
	 * @param eObject
	 *            the object to get the root container for.
	 * @return the root container.
	 */
	public EObject getRootContainer(EObject eObject) {
		return EcoreUtil.getRootContainer(eObject);
	}

	private EClassifier findTypeByName(Iterable<EObject> roots, String name) {
		for (EObject root : roots) {
			EClassifier result = findTypeByNameFrom(root, name);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	private EClassifier findTypeByNameFrom(EObject root, String name) {
		if (root instanceof EClassifier
				&& nameMatches((EClassifier) root, name)) {
			return (EClassifier) root;
		}
		for (EObject obj : AllContents.of(root)) {
			if (obj instanceof EClassifier
					&& nameMatches((EClassifier) obj, name)) {
				return (EClassifier) obj;
			}
		}
		return null;
	}

	private boolean nameMatches(EClassifier type, String name) {
		if (type != null && type.getName() != null && name != null) {
			return type.getName().trim().equalsIgnoreCase(name.trim());
		} else {
			return false;
		}
	}

	public Boolean hasError(EObject eObj) {
		if (eObj instanceof EClass || eObj instanceof EStructuralFeature) {
			Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObj);
			DiagnosticAttachment.getOrCreate(eObj, diagnostic);
			return diagnostic.getSeverity() == Diagnostic.ERROR;
		}
		return false;
	}

	public Boolean isEDataType(EObject eObj) {
		return eObj.eClass() == EcorePackage.eINSTANCE.getEDataType();
	}

	public List<EObject> eOperationSemanticElements(EOperation eOp) {
		List result = Lists.newArrayList(Ordering.natural()
				.onResultOf(new Function<EParameter, String>() {

					public String apply(EParameter arg0) {
						return arg0.getName();
					}
				}).sortedCopy(eOp.getEParameters()));
		result.add(0, eOp);
		return result;
	}

	public Boolean viewContainerNotSemanticContainer(DSemanticDiagram pview,
			EObject container) {
		return pview.getTarget() != container;
	}

	public Boolean noEOpposite(EReference ref) {
		return ref.getEOpposite() == null;
	}

	public boolean hasNoDocAnnotation(EObject eObj) {
		// Error[eAnnotations.details->select(key = 'documentation')->size() =
		// 0/]
		if (eObj instanceof EModelElement) {
			return EcoreUtil.getDocumentation((EModelElement) eObj) == null;
		}
		return true;
	}

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

	public Integer getDependenciesAmount(EPackage a, EPackage b) {
		// TODO
		return 2;
	}

	public String getDependenciesTooltip(EClass clazz, DSemanticDecorator view) {
		DDiagram diag = SiriusUtil.findDiagram(view);
		Set<String> explanations = Sets.newLinkedHashSet();
		if (diag != null) {
			for (EPackage dep : externalDependencies(clazz)) {
				explanations.add("Dependency to " + dep.getNsURI() + ": \n"
						+ externalDependenciesExplanation(dep, clazz));
			}
		}
		return Joiner.on('\n').join(explanations);
	}

	public String getDependenciesLabel(EClass clazz) {
		return clazz.getName() + " (" + externalDependencies(clazz).size()
				+ ")";
	}
	

	public Collection<EClassifier> getElementsIntroducingDependencies(
			EPackage source, DSemanticDiagram diagram) {
		Set<EClassifier> classifiersIntroducingDependencies = Sets
				.newLinkedHashSet();

		Set<EPackage> otherPackages = Sets.newLinkedHashSet();
		for (DNodeList list : Iterables.filter(
				diagram.getOwnedDiagramElements(), DNodeList.class)) {
			if (!new DDiagramElementQuery(list).isHidden()
					&& list.getTarget() instanceof EPackage) {
				otherPackages.add((EPackage) list.getTarget());
			}
		}
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

	private String externalDependenciesExplanation(EPackage dep, EClass clazz) {
		Set<String> explanations = Sets.newLinkedHashSet();
		for (EStructuralFeature eRef : clazz.getEAllStructuralFeatures()) {
			if (eRef.getEType() != null && eRef.getEType().getEPackage() == dep) {
				explanations.add(" - " + eRef.eClass().getName() +" " + eRef.getName()
						+ " refers to type " + eRef.getEType().getName()
						+ " in package " + dep.getName());
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