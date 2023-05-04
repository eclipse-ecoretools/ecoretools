/*******************************************************************************
 * Copyright (c) 2013, 2023 THALES GLOBAL SERVICES and Others
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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenDelegationKind;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.DNodeList;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.EdgeArrows;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.diagram.business.api.query.DDiagramQuery;
import org.eclipse.sirius.diagram.business.internal.helper.task.operations.CreateViewTask;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.tool.CreateView;
import org.eclipse.sirius.diagram.description.tool.ToolFactory;
import org.eclipse.sirius.diagram.model.business.internal.helper.ContentHelper;
import org.eclipse.sirius.diagram.model.business.internal.helper.MappingHelper;
import org.eclipse.sirius.diagram.model.business.internal.query.DDiagramInternalQuery;
import org.eclipse.sirius.ecore.extender.business.api.accessor.ModelAccessor;
import org.eclipse.sirius.ecore.extender.business.api.accessor.exception.FeatureNotFoundException;
import org.eclipse.sirius.ecore.extender.business.api.accessor.exception.MetaClassNotFoundException;
import org.eclipse.sirius.ext.emf.AllContents;
import org.eclipse.sirius.tools.api.command.CommandContext;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.FontFormat;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

/**
 * Generic Ecore services usable from a VSM.
 */
@SuppressWarnings("restriction")
public class DesignServices extends EReferenceServices {
	private static final String CLASS_DIAGRAM_CLASS_MAPPINGID = "EC EClass";

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
	public List<EObject> allRoots(EObject any) {
		Resource res = any.eResource();
		if (res != null && res.getResourceSet() != null) {
			List<EObject> roots = new ArrayList<EObject>();
			for (Resource childRes : res.getResourceSet().getResources()) {
				roots.addAll(childRes.getContents());
			}
			return roots;
		} else {
			return Collections.emptyList();
		}
	}

	public Set<EPackage> rootEPackages(EObject any) {
		return Sets.newLinkedHashSet(Iterables.filter(allRoots(any), EPackage.class));
	}

	public Boolean isEOperation(EObject any) {
		return any instanceof EOperation;
	}

	public Boolean isEStructuralFeature(EObject any) {
		return any instanceof EStructuralFeature;
	}

	public Boolean isEPackage(EObject any) {
		return any instanceof EPackage;
	}

	public Boolean isEClass(EObject any) {
		return any instanceof EClass;
	}

	public Boolean isEEnum(EObject any) {
		return any instanceof EEnum;
	}

	protected static final String GEN_MODEL_PACKAGE_NS_URI = "http://www.eclipse.org/emf/2002/GenModel";

	protected static final String ECORE_PACKAGE_NS_URI = "http://www.eclipse.org/emf/2002/Ecore";

	public EObject markForAutosize(EObject any) {
		if (any != null) {
			any.eAdapters().add(AutosizeTrigger.AUTO_SIZE_MARKER);
		}
		return any;
	}

	public EObject eContainerEContainer(EObject any) {
		if (any.eContainer() != null)
			return any.eContainer().eContainer();
		return null;
	}

	public Set<EStringToStringMapEntryImpl> getVisibleDocAnnotations(EObject self, DSemanticDiagram diag) {
		// [diagram.getDisplayedEModelElements().oclAsType(ecore::EModelElement).eAnnotations.details->select(key
		// = 'documentation')/]
		Set<EStringToStringMapEntryImpl> result = Sets.newLinkedHashSet();
		for (EModelElement displayed : getDisplayedEModelElements(diag)) {
			if (!(displayed instanceof EAttribute) && !(displayed instanceof EEnumLiteral)
					&& !(displayed instanceof EOperation)) {
				EAnnotation eAnnot = displayed.getEAnnotation(GEN_MODEL_PACKAGE_NS_URI);
				if (eAnnot != null) {
					for (EStringToStringMapEntryImpl mapEntry : Iterables.filter(eAnnot.getDetails(),
							EStringToStringMapEntryImpl.class)) {
						if ("documentation".equals(mapEntry.getKey())) {
							result.add(mapEntry);
						}
					}
				}
			}

		}
		return result;
	}

	public Set<EStringToStringMapEntryImpl> getVisibleConstraintsAnnotations(EObject self,
			DSemanticDiagram diag) {
		Set<EStringToStringMapEntryImpl> result = Sets.newLinkedHashSet();
		for (EModelElement displayed : getDisplayedEModelElements(diag)) {
			if (!(displayed instanceof EAttribute) && !(displayed instanceof EEnumLiteral)
					&& !(displayed instanceof EOperation)) {
				EAnnotation eAnnot = displayed.getEAnnotation(ECORE_PACKAGE_NS_URI);
				if (eAnnot != null) {
					for (EStringToStringMapEntryImpl mapEntry : Iterables.filter(eAnnot.getDetails(),
							EStringToStringMapEntryImpl.class)) {
						if ("constraints".equals(mapEntry.getKey())) {
							result.add(mapEntry);
						}
					}
				}
			}

		}
		return result;
	}

	public boolean hasNoClassifier(DSemanticDiagram diagram) {
		Iterator<DSemanticDecorator> it = Iterators.filter(diagram.getOwnedDiagramElements().iterator(),
				DSemanticDecorator.class);
		while (it.hasNext()) {
			DSemanticDecorator dec = it.next();
			if (dec.getTarget() instanceof EClassifier)
				return true;
		}
		return false;
	}

	public Set<EReference> getNonDisplayedEReferences(EClass self, DSemanticDiagram diag) {
		Set<EReference> result = Sets.newLinkedHashSet();
		Set<EClass> displayedEClasses = null;
		for (EReference eReference : self.getEAllReferences()) {
			if (eReference.getEType() != null) {
				if (displayedEClasses == null) {
					displayedEClasses = getDisplayedEClasses(diag);
				}
				/*
				 * if the target of the EReference is not visible, we *have* to
				 * display it as a node except if it iis from a supertype which
				 * is visible itself, then the reference will be already
				 * displayed. but if the reference is owned by a super type and
				 * this supertype is not visible, even if the target is visible,
				 * then we have to display it as a node to!
				 */
				boolean targetTypeIsVisible = displayedEClasses.contains(eReference.getEType());
				boolean referenceIsInherited = eReference.getEContainingClass() != self;
				boolean referenceHostIsVisible = displayedEClasses.contains(eReference.getEContainingClass());
				if (!referenceIsInherited && !targetTypeIsVisible) {
					result.add(eReference);
				}
				if (referenceIsInherited && (!referenceHostIsVisible)) {
					result.add(eReference);
				}
			}
		}
		return result;
	}

    public Set<EClass> getDisplayedEClasses(DSemanticDiagram diagram) {
		Set<EClass> result = Sets.newLinkedHashSet();
		Iterator<DNodeList> it = Iterators.filter(new DDiagramInternalQuery(diagram).getContainers().iterator(),
				DNodeList.class);
		while (it.hasNext()) {
			DNodeList dec = it.next();
			if (dec.getTarget() instanceof EClass && dec.isVisible()) {
				result.add((EClass) dec.getTarget());
			}
		}
		return result;
	}

	public Set<EClassifier> getDisplayedEClassifiers(DSemanticDiagram diagram) {
		Set<EClassifier> result = Sets.newLinkedHashSet();
        Iterator<DNodeList> it = Iterators.filter(new DDiagramInternalQuery(diagram).getContainers().iterator(),
                DNodeList.class);
        while (it.hasNext()) {
			DNodeList dec = it.next();
			if (dec.getTarget() instanceof EClassifier && dec.isVisible()) {
				result.add((EClassifier) dec.getTarget());
			}
		}
		return result;
	}

	private Set<EClass> getInternalEClasses(DSemanticDiagram diagram) {
		Set<EClass> result = Sets.newLinkedHashSet();
        Iterator<DNodeList> it = Iterators.filter(new DDiagramInternalQuery(diagram).getContainers().iterator(),
                DNodeList.class);
		while (it.hasNext()) {
			DNodeList dec = it.next();
			if (dec.getTarget() instanceof EClass) {
				if (dec.getActualMapping() != null
						&& CLASS_DIAGRAM_CLASS_MAPPINGID.equals(dec.getActualMapping().getName())) {
					result.add((EClass) dec.getTarget());
				}
			}
		}
		return result;
	}

	public Set<EClass> getDirectSuperTypesOrMostSpecificVisibleOnes(EClass self, DSemanticDiagram diagram) {
		Set<EClass> result = Sets.newLinkedHashSet();
		Set<EClass> displayed = getDisplayedEClasses(diagram);
		for (EClass directSuperType : self.getESuperTypes()) {
			if (displayed.contains(directSuperType)) {
				result.add(directSuperType);
			} else {
				Set<EClass> mostSpecificDisplayed = findMostSpecificAndVisible(directSuperType.getESuperTypes(),
						displayed);
				if (mostSpecificDisplayed != null) {
					result.addAll(mostSpecificDisplayed);
				}
			}
		}
		return result;
	}

	private Set<EClass> findMostSpecificAndVisible(Collection<EClass> superTypes, Set<EClass> displayed) {
		Set<EClass> result = Sets.newLinkedHashSet();
		for (EClass eClass : superTypes) {
			if (displayed.contains(eClass)) {
				result.add(eClass);
			} else {
				result.addAll(findMostSpecificAndVisible(eClass.getESuperTypes(), displayed));
			}
		}
		return result;
	}

	public Set<EClass> getExternalEClasses(EPackage root, DSemanticDiagram diagram) {

		Set<EClass> related = Sets.newLinkedHashSet();
		Set<EClass> eClasses = getInternalEClasses(diagram);
		RelatedElementsSwitch relations = new RelatedElementsSwitch();
		for (EClass eClass : eClasses) {
			for (EClass other : Iterables.filter(relations.getRelatedElements(eClass), EClass.class)) {
				related.add(other);
			}
		}

		return Sets.difference(related, eClasses);
	}

	public Set<EReference> getEReferencesToDisplay(EPackage root, DSemanticDiagram diagram) {
		// [diagram.getDisplayedEClasses().oclAsType(ecore::EClass).eAllReferences->flatten()/]
		Collection<EClass> eClasses = getDisplayedEClasses(diagram);
		Set<EReference> eRefs = Sets.newLinkedHashSet();
		for (EClass clazz : eClasses) {
			eRefs.addAll(clazz.getEAllReferences());
		}
		return eRefs;
	}

	public Boolean targetIsInterface(EClass clazz, EObject view) {
		if (view instanceof DEdge) {
			EdgeTarget target = ((DEdge) view).getTargetNode();
			if (target instanceof DSemanticDecorator && ((DSemanticDecorator) target).getTarget() instanceof EClass) {
				return ((EClass) ((DSemanticDecorator) target).getTarget()).isInterface();
			}
		}
		return false;
	}

	public List<EReference> getEOppositeSemanticElements(EReference ref) {
		Set<EReference> allRefs = Sets.newLinkedHashSet();
		allRefs.add(ref);
		if (ref.getEOpposite() != null)
			allRefs.add(ref.getEOpposite());
		return Ordering.natural().onResultOf(new Function<EReference, String>() {

			public String apply(EReference input) {
				return input.getName();
			}
		}).sortedCopy(allRefs);
	}

	public Set<EModelElement> getDisplayedEModelElements(DSemanticDiagram diagram) {
		Set<EModelElement> modelelements = Sets.newLinkedHashSet();
		Iterator<DSemanticDecorator> it = Iterators.filter(Iterators.concat(Iterators.singletonIterator(diagram),
				new DDiagramQuery(diagram).getAllDiagramElements().iterator()), DSemanticDecorator.class);
		while (it.hasNext()) {
			DSemanticDecorator dec = it.next();
			if (dec.getTarget() instanceof EModelElement)
				modelelements.add((EModelElement) dec.getTarget());
		}
		return modelelements;
	}

	public List<EObject> getValidsForDiagram(final EObject element, final DSemanticDecorator containerView) {
		Predicate<EObject> validForClassDiagram = new Predicate<EObject>() {

			public boolean apply(EObject input) {
				return input instanceof EPackage || input instanceof EClassifier;
			}
		};
		return allValidSessionElements(element, validForClassDiagram);
	}

	public Set<EObject> getRelated(EObject firstView, List<EObject> allSelectedViews, DDiagram diag) {
		Set<EObject> relateds = Sets.newLinkedHashSet();
		for (DSemanticDecorator decorator : Iterables.filter(allSelectedViews, DSemanticDecorator.class)) {
			relateds.addAll(new RelatedElementsSwitch().getRelatedElements(decorator.getTarget()));
		}
		return relateds;
	}

	public Set<EObject> getRelated(EObject firstView, EObject aView, DDiagram diag) {
		return getRelated(firstView, Lists.newArrayList(aView), diag);
	}

	private List<EObject> allValidSessionElements(EObject cur, Predicate<EObject> validForClassDiagram) {
		Session found = SessionManager.INSTANCE.getSession(cur);
		List<EObject> result = Lists.newArrayList();
		if (found != null) {
			for (Resource res : found.getSemanticResources()) {
				if (res.getURI().isPlatformResource() || res.getURI().isPlatformPlugin()) {
					Iterators.addAll(result, Iterators.filter(res.getAllContents(), validForClassDiagram));
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
	 * Replace spaces by camel case value.
	 * 
	 * @param any
	 * @param from
	 * @return
	 */
	public String toCamelCase(EObject any, String from) {
		if (from != null) {
			StringBuilder buffer = new StringBuilder(from.length());
			for (String word : Splitter.on(CharMatcher.whitespace()).trimResults().split(from)) {
				buffer.append(toU1Case(word));
			}
			return buffer.toString();
		}
		return from;
	}

	private String toU1Case(String word) {
		if (word != null && word.length() > 0) {
			return new StringBuilder(word.length()).append(Ascii.toUpperCase(word.charAt(0))).append(word.substring(1))
					.toString();
		}
		return word;
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
	public EStructuralFeature performEdit(EAttribute attr, String editString) {
		return new EAttributeServices().performEdit(attr, editString);
	}

	/**
	 * Performs a "direct edit" operation on an EAttribute.
	 */
	public EStructuralFeature performEditAsAttribute(EStructuralFeature attr, String editString) {
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
			String message = child.getMessage();
			/*
			 * we remove any substring which could be the toString of some data
			 * and replace it with something which will not change on subsequent
			 * executions.
			 */
			for (EObject data : Iterables.filter(child.getData(), EObject.class)) {
				String instanceVariableString = data.getClass().getName() + "@" + Integer.toHexString(data.hashCode());
				message = message.replace(instanceVariableString, data.eClass().getName());
			}

			result += "\n" + severityLabel(child.getSeverity()) + " : " + message;
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
		String validationTooltip = renderTooltip(op);
		String operationSignature = new EOperationServices().renderEOperationTooltip(op);
		if (validationTooltip != null && validationTooltip.length() > 0) {
			return validationTooltip + "\n" + operationSignature;
		} else {
			return operationSignature;
		}
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

	/**
	 * Computes the label of an EReference.
	 */
	@Override
	public String render(EReference ref) {
		return new EReferenceServices().render(ref);
	}

	/**
	 * Computes the label of an EReference.
	 */
	@Override
	public String renderAsNode(EReference ref) {
		return new EReferenceServices().renderAsNode(ref);
	}

	public String renderEOpposite(EReference ref) {
		if (ref.getEOpposite() != null) {
			return new EReferenceServices().render(ref.getEOpposite());
		}
		return "";
	}

	/**
	 * Performs a "direct edit" operation on an EReference.
	 */
	@Override
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
		if (root instanceof EClassifier && nameMatches((EClassifier) root, name)) {
			return (EClassifier) root;
		}
		for (EObject obj : AllContents.of(root)) {
			if (obj instanceof EClassifier && nameMatches((EClassifier) obj, name)) {
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
			DiagnosticAttachment attachment = DiagnosticAttachment.getAttachment(eObj);
			if (attachment == null) {
				Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObj);
				attachment = DiagnosticAttachment.getOrCreate(eObj, diagnostic);
			}
			Diagnostic diag = attachment.getDiagnostic();
			if (diag != null) {
				return diag.getSeverity() == Diagnostic.ERROR;
			}
		}
		return false;
	}

	public Boolean isEDataType(EObject eObj) {
		return eObj.eClass() == EcorePackage.eINSTANCE.getEDataType();
	}

	public Boolean isDDiagram(EObject any, EObject view) {
		return view instanceof DDiagram;
	}

	public List<EObject> eOperationSemanticElements(EOperation eOp) {
		List<EObject> result = Lists.newArrayList(Ordering.natural().onResultOf(EParameter::getName).sortedCopy(eOp.getEParameters()));
		result.add(0, eOp);
		return result;
	}

	public Boolean viewContainerNotSemanticContainer(EObject self, DSemanticDiagram diag,
			DSemanticDecorator containerView) {
		return containerView.getTarget() != self.eContainer();
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

	public EdgeArrows arrowsFillDiamond(EObject any) {
		return EdgeArrows.FILL_DIAMOND_LITERAL;
	}

	public FontFormat fontFormatBold(EObject any) {
		return FontFormat.BOLD_LITERAL;
	}

	public void reconnectESuperTypeSource(EObject element, EObject target, EObject source, EObject otherEnd,
			EObject edgeView, EObject sourceView) {
		if (edgeView instanceof DEdge && ((DEdge) edgeView).getSourceNode() instanceof DSemanticDecorator
				&& ((DEdge) edgeView).getTargetNode() instanceof DSemanticDecorator) {
			EObject newSource = ((DSemanticDecorator) ((DEdge) edgeView).getSourceNode()).getTarget();
			EObject newTarget = ((DSemanticDecorator) ((DEdge) edgeView).getTargetNode()).getTarget();

			/*
			 * reconnect source:
			 */
			if (newSource instanceof EClass && newTarget instanceof EClass && source instanceof EClass) {
				((EClass) source).getESuperTypes().remove(newTarget);
				((EClass) newSource).getESuperTypes().add((EClass) newTarget);

			}
		}

	}

	public void reconnectESuperTypeTarget(EObject element, EObject target, EObject source, EObject otherEnd,
			EObject edgeView, EObject sourceView) {
		if (edgeView instanceof DEdge && ((DEdge) edgeView).getSourceNode() instanceof DSemanticDecorator
				&& ((DEdge) edgeView).getTargetNode() instanceof DSemanticDecorator) {
			EObject newTarget = ((DSemanticDecorator) ((DEdge) edgeView).getTargetNode()).getTarget();

			/*
			 * reconnect target:
			 */
			if (newTarget instanceof EClass && target instanceof EClass && element instanceof EClass) {
				((EClass) element).getESuperTypes().remove(newTarget);
				((EClass) element).getESuperTypes().add((EClass) target);

			}
		}

	}

	public void reconnectEReferenceSource(EObject element, EObject newValue) {
		if (newValue instanceof EClass && element instanceof EReference) {

			EReference eRef = (EReference) element;
			EClass srcClass = (EClass) newValue;

			if (eRef.eContainer() != srcClass) {
				srcClass.getEStructuralFeatures().add(eRef);
			}

		}
	}

	public void reconnectEReferenceTarget(EObject element, EObject newValue) {
		if (newValue != null && element instanceof EReference) {

			EReference eRef = (EReference) element;

			if (newValue instanceof EClass) {
				EClass targetClass = (EClass) newValue;
				if (eRef.getEType() != newValue) {
					eRef.setEType(targetClass);
				}
			} else if (newValue instanceof ETypeParameter) {
				if (eRef.getEType() != newValue) {
					EGenericsServices.setETypeWithGenerics(eRef, newValue);
				}
			}

		}
	}

	/**
	 * Create view.
	 * 
	 * @param semanticElement
	 *            Semantic element
	 * @param containerView
	 *            Container view
	 * @param session
	 *            Session
	 * @param containerViewVariable
	 *            Name of the container view variable
	 */
	private void createView(final EObject semanticElement, final DSemanticDecorator containerView,
			final Session session, final String containerViewVariable) {
		// Get all available mappings applicable for the copiedElement in the
		// current container
		List<DiagramElementMapping> semanticElementMappings = getMappings(semanticElement, containerView, session);

		// Build a createView tool
		final CreateView createViewOp = ToolFactory.eINSTANCE.createCreateView();
		for (DiagramElementMapping copiedElementMapping : semanticElementMappings) {
			final DiagramElementMapping tmpCopiedElementMapping = copiedElementMapping;
			createViewOp.setMapping(tmpCopiedElementMapping);
			final String containerViewExpression = "var:" + containerViewVariable;
			createViewOp.setContainerViewExpression(containerViewExpression);

			session.getTransactionalEditingDomain().getCommandStack()
					.execute(new RecordingCommand(session.getTransactionalEditingDomain()) {

						@Override
						protected void doExecute() {
							try {
								// Get the command context
								DRepresentation representation = null;
								if (containerView instanceof DRepresentation) {
									representation = (DRepresentation) containerView;
								} else if (containerView instanceof DDiagramElement) {
									representation = ((DDiagramElement) containerView).getParentDiagram();
								}

								final CommandContext context = new CommandContext(semanticElement, representation);

								// Execute the create view task
								new CreateViewTask(context, session.getModelAccessor(), createViewOp,
										session.getInterpreter()).execute();
							} catch (MetaClassNotFoundException | FeatureNotFoundException e) {
								EcoreToolsDesignPlugin.INSTANCE.log(e);
							} 
						}
					});
		}
	}

	/**
	 * Paste a semantic element and create the corresponding view in the given
	 * container
	 * 
	 * @param container
	 *            Semantic container
	 * @param semanticElement
	 *            Element to paste
	 * @param containerView
	 *            Container view
	 */
	public void paste(final EObject container, final EObject semanticElement, final DSemanticDecorator elementView,
			final DSemanticDecorator containerView) {
		// Paste the semantic element from the clipboard to the selected
		// container
		final Session session = SessionManager.INSTANCE.getSession(container);
		TransactionalEditingDomain domain = session.getTransactionalEditingDomain();
		// The feature is set to null because the domain will deduce it
		Command cmd = AddCommand.create(domain, container, null, semanticElement);
		if (cmd.canExecute()) {
			cmd.execute();
		}
		// Create the view for the pasted element
		createView(semanticElement, containerView, session, "containerView");
	}

	/**
	 * Get mappings available for a semantic element and a given container view.
	 * 
	 * @param semanticElement
	 *            Semantic element
	 * @param containerView
	 *            Container view
	 * @param session
	 *            Session
	 * @return List of mappings which could not be null
	 */
	private List<DiagramElementMapping> getMappings(final EObject semanticElement,
			final DSemanticDecorator containerView, Session session) {
		ModelAccessor modelAccessor = session.getModelAccessor();
		List<DiagramElementMapping> mappings = new ArrayList<DiagramElementMapping>();

		if (containerView instanceof DSemanticDiagram) {

            for (DiagramElementMapping mapping : ContentHelper.getAllContainerMappings((((DSemanticDiagram) containerView).getDescription()), false)) {
				String domainClass = ((AbstractNodeMapping) mapping).getDomainClass();
				if (modelAccessor.eInstanceOf(semanticElement, domainClass) && !mapping.isCreateElements()) {
					mappings.add(mapping);
				}
			}
            for (DiagramElementMapping mapping : ContentHelper.getAllNodeMappings((((DSemanticDiagram) containerView).getDescription()), false)) {
				String domainClass = ((AbstractNodeMapping) mapping).getDomainClass();
				if (modelAccessor.eInstanceOf(semanticElement, domainClass) && !mapping.isCreateElements()) {
					mappings.add(mapping);
				}
			}
		} else if (containerView instanceof DNodeContainer) {
            for (DiagramElementMapping mapping : MappingHelper.getAllContainerMappings((((DNodeContainer) containerView).getActualMapping()))) {
				String domainClass = ((AbstractNodeMapping) mapping).getDomainClass();
				if (modelAccessor.eInstanceOf(semanticElement, domainClass) && !mapping.isCreateElements()) {
					mappings.add(mapping);
				}
			}
            for (DiagramElementMapping mapping : MappingHelper.getAllNodeMappings((((DNodeContainer) containerView).getActualMapping()))) {
				String domainClass = ((AbstractNodeMapping) mapping).getDomainClass();
				if (modelAccessor.eInstanceOf(semanticElement, domainClass) && !mapping.isCreateElements()) {
					mappings.add(mapping);
				}
			}
		}
		return mappings;
	}

	public String getClassesTableName(EObject cur) {
		// [(if (oclIsKindOf(ecore::EStructuralFeature) and
		// oclAsType(ecore::EStructuralFeature).derived) then '/' else '' endif)
		// + name/]
		if (cur instanceof EStructuralFeature) {
			if (((EStructuralFeature) cur).isDerived()) {
				return "/" + ((EStructuralFeature) cur).getName();
			}
		}
		if (cur instanceof ENamedElement) {
			return ((ENamedElement) cur).getName();
		}
		return cur.eClass().getName();
	}

	public boolean hasCDOBundle(EObject cur) {
		return Platform.getBundle("org.eclipse.emf.cdo") != null;
	}

	public EObject enableCDOGen(EObject cur, Set<GenModel> genmodels) throws IOException {
		for (GenModel genModel : genmodels) {
			if (isNotFromPluginsOrRegistry(genModel)) {
				genModel.getModelPluginVariables().add("CDO=org.eclipse.emf.cdo");
				genModel.setRootExtendsInterface("org.eclipse.emf.cdo.CDOObject");
				genModel.setRootExtendsClass("org.eclipse.emf.internal.cdo.CDOObjectImpl");
				genModel.setFeatureDelegation(GenDelegationKind.REFLECTIVE_LITERAL);
				genModel.setProviderRootExtendsClass("org.eclipse.emf.cdo.edit.CDOItemProviderAdapter");

				if (genModel.eResource().getURI().isPlatformResource()) {
					URI cdoMFURI = getCDOMFUri(genModel);
					if (!URIConverter.INSTANCE.exists(cdoMFURI, Collections.EMPTY_MAP)) {
						try (OutputStream out = URIConverter.INSTANCE.createOutputStream(cdoMFURI,
								Collections.EMPTY_MAP)) {
							new PrintWriter(out).write("This is a marker file for bundles with CDO native models.");
						}
					}

				}

			}
		}
		return cur;
	}

	private boolean isNotFromPluginsOrRegistry(GenModel genModel) {
		return genModel.eResource() != null && genModel.eResource().getURI() != null
				&& !genModel.eResource().getURI().isPlatformPlugin();
	}

	private URI getCDOMFUri(GenModel genModel) {
		return URI.createPlatformResourceURI(genModel.eResource().getURI().segment(1), true).appendSegment("META-INF")
				.appendSegment("CDO.MF");
	}

	public EObject disableCDOGen(EObject cur, Set<GenModel> genmodels) throws IOException {
		for (GenModel genModel : genmodels) {
			if (isNotFromPluginsOrRegistry(genModel)) {
				genModel.getModelPluginVariables().remove("CDO=org.eclipse.emf.cdo");
				genModel.setRootExtendsInterface("org.eclipse.emf.ecore.EObject");
				genModel.setRootExtendsClass("org.eclipse.emf.ecore.impl.MinimalEObjectImpl$Container");
				genModel.setFeatureDelegation(GenDelegationKind.NONE_LITERAL);
				genModel.setProviderRootExtendsClass(null);

				if (genModel.eResource().getURI().isPlatformResource()) {
					URI cdoMFURI = getCDOMFUri(genModel);
					if (URIConverter.INSTANCE.exists(cdoMFURI, Collections.EMPTY_MAP)) {
						URIConverter.INSTANCE.delete(cdoMFURI, Collections.EMPTY_MAP);
					}

				}
			}
		}
		return cur;
	}

	public String getEClassItemIconPath(GenClass cur) throws IOException {
		String r = "/org.eclipse.emf.ecoretools.design/icons/full/obj16/empty.gif";
		if (cur != null && EMFPlugin.IS_RESOURCES_BUNDLE_AVAILABLE) {
			String icon = cur.getItemIconFileName();
			IFile f = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(icon));
			if (f.exists() && f.isAccessible()) {
				r = icon;
			}
		}
		return r;
	}

}
