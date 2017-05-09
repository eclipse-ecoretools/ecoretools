/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnum;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnumLiteral;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

/**
 * The services class used by the Properties View support.
 */
public class PropertiesServices {

	protected static final String GEN_MODEL_PACKAGE_NS_URI = "http://www.eclipse.org/emf/2002/GenModel";

	public List<EStructuralFeature> removeFeaturesToHide(EObject ctx, Collection<EStructuralFeature> unfiltered) {
		List<EStructuralFeature> toBeFilterd = Lists.newArrayList(unfiltered);
		toBeFilterd.remove(GenModelPackage.Literals.GEN_CLASS__ECORE_CLASS);
		toBeFilterd.remove(GenModelPackage.Literals.GEN_PACKAGE__ECORE_PACKAGE);
		toBeFilterd.remove(GenModelPackage.Literals.GEN_CLASS__ECORE_CLASS);
		toBeFilterd.remove(GenModelPackage.Literals.GEN_FEATURE__ECORE_FEATURE);
		if (ctx instanceof EReference) {
			toBeFilterd.remove(EcorePackage.Literals.ESTRUCTURAL_FEATURE__DEFAULT_VALUE_LITERAL);
		}
		toBeFilterd.remove(GenModelPackage.Literals.GEN_ENUM__ECORE_ENUM);
		toBeFilterd.remove(GenModelPackage.Literals.GEN_TYPE_PARAMETER__ECORE_TYPE_PARAMETER);

		PriorityComparator<EStructuralFeature> comparator = new PriorityComparator<EStructuralFeature>(ImmutableList.of(
				EcorePackage.Literals.ENAMED_ELEMENT__NAME, EcorePackage.Literals.ETYPED_ELEMENT__ETYPE,
				EcorePackage.Literals.ETYPED_ELEMENT__LOWER_BOUND, EcorePackage.Literals.ETYPED_ELEMENT__UPPER_BOUND,
				EcorePackage.Literals.ECLASSIFIER__INSTANCE_CLASS_NAME,
				EcorePackage.Literals.ECLASSIFIER__INSTANCE_TYPE_NAME,
				EcorePackage.Literals.ESTRUCTURAL_FEATURE__DEFAULT_VALUE_LITERAL,
				EcorePackage.Literals.EREFERENCE__EOPPOSITE, EcorePackage.Literals.EREFERENCE__CONTAINMENT,
				EcorePackage.Literals.ESTRUCTURAL_FEATURE__TRANSIENT,
				EcorePackage.Literals.ESTRUCTURAL_FEATURE__DERIVED));
		/*
		 * reorder features
		 */

		return Ordering.from(comparator).sortedCopy(toBeFilterd);
	}

	class PriorityComparator<T> implements Comparator<T> {
		private final List<T> values;

		public PriorityComparator(List<T> values) {
			this.values = values;
		}

		@Override
		public int compare(T o1, T o2) {
			int idx1 = values.indexOf(o1);
			int idx2 = values.indexOf(o2);
			if (idx1 > -1) {
				return idx2 > -1 ? idx1 - idx2 : -1;
			}
			return idx2 > -1 ? 1 : 0;
		}
	}

	public List<EObject> removeSemanticElementsToHide(EObject ctx, Collection<EObject> unfiltered) {
		List<EObject> filtered = Lists.newArrayList();
		for (EObject eObject : unfiltered) {
			if (!(eObject instanceof EParameter)) {
				filtered.add(eObject);
			}
		}
		return filtered;
	}

	public EStringToStringMapEntryImpl getVisibleDocAnnotations(EObject self) {
		if (self instanceof EModelElement) {
			EAnnotation eAnnot = ((EModelElement) self).getEAnnotation(GEN_MODEL_PACKAGE_NS_URI);
			if (eAnnot != null) {
				for (EStringToStringMapEntryImpl mapEntry : Iterables.filter(eAnnot.getDetails(),
						EStringToStringMapEntryImpl.class)) {
					if ("documentation".equals(mapEntry.getKey())) {
						return mapEntry;
					}
				}
			}

		} else if (self instanceof EAnnotation) {
			for (EStringToStringMapEntryImpl mapEntry : Iterables.filter(((EAnnotation) self).getDetails(),
					EStringToStringMapEntryImpl.class)) {
				if ("documentation".equals(mapEntry.getKey())) {
					return mapEntry;
				}
			}
		} else if (self instanceof EStringToStringMapEntryImpl) {
			if ("documentation".equals(((EStringToStringMapEntryImpl) self).getKey())) {
				return (EStringToStringMapEntryImpl) self;
			}
		}
		return null;
	}

	public EObject setDocAnnotation(EObject self, String value) {
		if (self instanceof EModelElement) {
			EAnnotation eAnnot = ((EModelElement) self).getEAnnotation(GEN_MODEL_PACKAGE_NS_URI);
			if (eAnnot != null) {
				for (EStringToStringMapEntryImpl mapEntry : Iterables.filter(eAnnot.getDetails(),
						EStringToStringMapEntryImpl.class)) {
					if ("documentation".equals(mapEntry.getKey())) {
						mapEntry.setValue(value);
					}
				}
			} else {
				EAnnotation newAnnot = EcoreFactory.eINSTANCE.createEAnnotation();
				newAnnot.setSource(GEN_MODEL_PACKAGE_NS_URI);
				newAnnot.getDetails().put("documentation", value);
				((EModelElement) self).getEAnnotations().add(newAnnot);
			}

		} else if (self instanceof EAnnotation) {
			for (EStringToStringMapEntryImpl mapEntry : Iterables.filter(((EAnnotation) self).getDetails(),
					EStringToStringMapEntryImpl.class)) {
				if ("documentation".equals(mapEntry.getKey())) {
					mapEntry.setValue(value);
				}
			}

		} else if (self instanceof EStringToStringMapEntryImpl) {
			if ("documentation".equals(((EStringToStringMapEntryImpl) self).getKey())) {
				((EStringToStringMapEntryImpl) self).setValue(value);
			}
		}
		return self;
	}

	public boolean isJavaFileGenerated(EObject cur) {
		URI javaImplementationURI = getJavaImplementationURI(cur);
		if (javaImplementationURI != null && cur.eResource() != null && cur.eResource().getResourceSet() != null
				&& cur.eResource().getResourceSet().getURIConverter() != null) {
			return cur.eResource().getResourceSet().getURIConverter().exists(javaImplementationURI,
					Collections.EMPTY_MAP);
		}
		return false;
	}

	public String getJavaImplementationPath(EObject cur) {
		URI targetFile = getJavaImplementationURI(cur);
		if (targetFile != null) {
			return targetFile.toString();
		}
		return null;
	}

	private URI getJavaImplementationURI(EObject cur) {
		GenClassifier gClass = null;
		if (cur instanceof GenFeature) {
			gClass = ((GenFeature) cur).getGenClass();
		}
		if (cur instanceof GenEnumLiteral) {
			gClass = ((GenEnumLiteral) cur).getGenEnum();
		}
		if (cur instanceof GenClassifier) {
			gClass = (GenClassifier) cur;
		}
		if (cur instanceof GenEnum) {
			gClass = (GenClassifier) cur;
		}
		String className = "";
		if (gClass instanceof GenClass) {
			className = ((GenClass) gClass).getClassName();
		} else if (gClass instanceof GenEnum) {
			className = ((GenEnum) gClass).getClassifierInstanceName();
		}

		if (gClass != null && gClass.getGenPackage() != null) {
			String packageName = gClass.getGenPackage().getClassPackageName();
			URI targetDirectory = URI.createURI(gClass.getGenPackage().getGenModel().getModelDirectory())
					.appendSegments(packageName.split("\\."));
			URI targetFile = targetDirectory.appendSegment(className + ".java");
			return targetFile;
		}
		return null;
	}

	public String upperBoundDisplay(ETypedElement host) {
		if (host.getUpperBound() == -1) {
			return "*";
		}
		return Integer.valueOf(host.getUpperBound()).toString();
	}

	public ETypedElement setUpperBound(ETypedElement host, String newValue) {
		if ("*".equals(newValue)) {
			host.setUpperBound(-1);
		} else {
			host.setUpperBound(Integer.valueOf(newValue));
		}
		return host;
	}

	public EObject eGetMonoRef(EObject cur, EStructuralFeature ref) {
		return (EObject) cur.eGet(ref);
	}

	public EObject moveUpInContainer(EObject cur) {
		EObject container = cur.eContainer();
		if (container != null) {
			EList<EObject> siblings = (EList<EObject>) container.eGet(cur.eContainingFeature());
			int oldPosition = siblings.indexOf(cur);
			int newPosition = oldPosition - 1;
			if (newPosition < 0) {
				newPosition = 0;
			}
			siblings.move(newPosition, cur);
		}
		return cur;
	}

	public EObject moveDownInContainer(EObject cur) {
		EObject container = cur.eContainer();
		if (container != null) {
			EList<EObject> siblings = (EList<EObject>) container.eGet(cur.eContainingFeature());
			int oldPosition = siblings.indexOf(cur);
			int newPosition = oldPosition + 1;
			if (newPosition > siblings.size() - 1) {
				newPosition = siblings.size() - 1;
			}
			siblings.move(newPosition, cur);
		}
		return cur;
	}

}
