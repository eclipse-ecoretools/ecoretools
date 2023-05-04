/*******************************************************************************
 * Copyright (c) 2017, 2023 Obeo.
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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnum;
import org.eclipse.emf.codegen.ecore.genmodel.GenEnumLiteral;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

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

	public List<EObject> removeSemanticElementsToHide(EObject ctx, Collection<EObject> unfiltered,
			DSemanticDecorator selection) {
		List<EObject> filtered = Lists.newArrayList();
		for (EObject eObject : unfiltered) {
			if (!(eObject instanceof EParameter)) {
				filtered.add(eObject);
			}
		}
		if (selection instanceof DEdge && ctx instanceof EClass
				&& ((DEdge) selection).getActualMapping() instanceof EdgeMapping
				&& "EC ESupertypes".equals(((EdgeMapping) ((DEdge) selection).getActualMapping()).getName())) {
			filtered.addAll(((EClass) ctx).getEGenericSuperTypes());
			for (EGenericType genType : ((EClass) ctx).getEGenericSuperTypes()) {
				filtered.addAll(genType.getETypeArguments());
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
			@SuppressWarnings("unchecked")
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
            @SuppressWarnings("unchecked")
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

	public boolean isAccessible(String path) {
		final boolean res;

		if (path != null) {
			final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
			res = file.exists() && file.isAccessible();
		} else {
			res = false;
		}

		return res;
	}

	public String selectIcon(String targetPath) {
		final IFile targetFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(targetPath));
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		final FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
		fileDialog.setText("Select icon");
		fileDialog.setFileName(targetFile.getLocation().toOSString());
		final String[] filterExt = { "*.gif", "*.png", "*.jpg", "*.jpeg" };
		fileDialog.setFilterExtensions(filterExt);
		final String source = fileDialog.open();
		if (source != null) {
			try {
				Files.copy(new File(source).toPath(), targetFile.getLocation().toFile().toPath(),
						StandardCopyOption.REPLACE_EXISTING);
				targetFile.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (IOException | CoreException e) {
				final String message = String.format("An error occurred while copying %s to %s", source,
						targetFile.getLocation().toFile().toPath().toString());
				EcoreToolsDesignPlugin.getPlugin()
						.log(new Status(IStatus.ERROR, EcoreToolsDesignPlugin.PLUGIN_ID, message, e));
			}
		}

		removeImageFromCache(ExtendedImageRegistry.INSTANCE, targetFile.getLocation());

		return targetPath;
	}

	/**
	 * Removes the {@link Image} from the cache used by EEF2 to ensure loading of
	 * the new content.
	 * 
	 * @param registry   the {@link ExtendedImageRegistry}
	 * @param targetPath the image {@link IPath}
	 */
	private void removeImageFromCache(ExtendedImageRegistry registry, IPath targetPath) {
		try {
			final ImageDescriptor descriptor = ImageDescriptor
					.createFromURL(targetPath.toFile().toURI().toURL());

			for (Field field : registry.getClass().getDeclaredFields()) {
				if ("table".equals(field.getName())) {
					field.setAccessible(true);
					@SuppressWarnings("unchecked")
					final Map<Object, Image> table = (Map<Object, Image>) field.get(registry);
					final Image image = table.remove(descriptor);
					if (image != null) {
						image.dispose();
					}
					field.setAccessible(false);
					break;
				}
			}

		} catch (MalformedURLException | IllegalArgumentException | IllegalAccessException e) {
			EcoreToolsDesignPlugin.getPlugin()
					.log(new Status(IStatus.ERROR, EcoreToolsDesignPlugin.PLUGIN_ID, "Couldn't reload icon.", e));
		}
	}
	
}
