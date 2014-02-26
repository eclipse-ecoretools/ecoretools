/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.providers;

import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;

import org.eclipse.emf.ecoretools.design.properties.parts.forms.DataTypePropertiesEditionPartForm;
import org.eclipse.emf.ecoretools.design.properties.parts.forms.EAnnotationPropertiesEditionPartForm;
import org.eclipse.emf.ecoretools.design.properties.parts.forms.EAttributePropertiesEditionPartForm;
import org.eclipse.emf.ecoretools.design.properties.parts.forms.EClassPropertiesEditionPartForm;
import org.eclipse.emf.ecoretools.design.properties.parts.forms.EEnumLiteralPropertiesEditionPartForm;
import org.eclipse.emf.ecoretools.design.properties.parts.forms.EPackagePropertiesEditionPartForm;
import org.eclipse.emf.ecoretools.design.properties.parts.forms.EReferencePropertiesEditionPartForm;
import org.eclipse.emf.ecoretools.design.properties.parts.forms.ExceptionsPropertiesEditionPartForm;
import org.eclipse.emf.ecoretools.design.properties.parts.forms.InstanciationPropertiesEditionPartForm;
import org.eclipse.emf.ecoretools.design.properties.parts.forms.OperationElementPropertiesEditionPartForm;

import org.eclipse.emf.ecoretools.design.properties.parts.impl.DataTypePropertiesEditionPartImpl;
import org.eclipse.emf.ecoretools.design.properties.parts.impl.EAnnotationPropertiesEditionPartImpl;
import org.eclipse.emf.ecoretools.design.properties.parts.impl.EAttributePropertiesEditionPartImpl;
import org.eclipse.emf.ecoretools.design.properties.parts.impl.EClassPropertiesEditionPartImpl;
import org.eclipse.emf.ecoretools.design.properties.parts.impl.EEnumLiteralPropertiesEditionPartImpl;
import org.eclipse.emf.ecoretools.design.properties.parts.impl.EPackagePropertiesEditionPartImpl;
import org.eclipse.emf.ecoretools.design.properties.parts.impl.EReferencePropertiesEditionPartImpl;
import org.eclipse.emf.ecoretools.design.properties.parts.impl.ExceptionsPropertiesEditionPartImpl;
import org.eclipse.emf.ecoretools.design.properties.parts.impl.InstanciationPropertiesEditionPartImpl;
import org.eclipse.emf.ecoretools.design.properties.parts.impl.OperationElementPropertiesEditionPartImpl;

import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;

import org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart;

import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPartProvider;

/**
 * 
 * 
 */
public class EcorePropertiesEditionPartProvider implements IPropertiesEditionPartProvider {

	/** 
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPartProvider#provides(java.lang.Object)
	 * 
	 */
	public boolean provides(Object key) {
		return key == EcoreViewsRepository.class;
	}

	/** 
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPartProvider#getPropertiesEditionPart(java.lang.Object, int, org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent)
	 * 
	 */
	public IPropertiesEditionPart getPropertiesEditionPart(Object key, int kind, IPropertiesEditionComponent component) {
		if (key == EcoreViewsRepository.EAttribute.class) {
			if (kind == EcoreViewsRepository.SWT_KIND)
				return new EAttributePropertiesEditionPartImpl(component);
			if (kind == EcoreViewsRepository.FORM_KIND)
				return new EAttributePropertiesEditionPartForm(component);
		}
		if (key == EcoreViewsRepository.EAnnotation.class) {
			if (kind == EcoreViewsRepository.SWT_KIND)
				return new EAnnotationPropertiesEditionPartImpl(component);
			if (kind == EcoreViewsRepository.FORM_KIND)
				return new EAnnotationPropertiesEditionPartForm(component);
		}
		if (key == EcoreViewsRepository.EClass.class) {
			if (kind == EcoreViewsRepository.SWT_KIND)
				return new EClassPropertiesEditionPartImpl(component);
			if (kind == EcoreViewsRepository.FORM_KIND)
				return new EClassPropertiesEditionPartForm(component);
		}
		if (key == EcoreViewsRepository.Instanciation.class) {
			if (kind == EcoreViewsRepository.SWT_KIND)
				return new InstanciationPropertiesEditionPartImpl(component);
			if (kind == EcoreViewsRepository.FORM_KIND)
				return new InstanciationPropertiesEditionPartForm(component);
		}
		if (key == EcoreViewsRepository.DataType.class) {
			if (kind == EcoreViewsRepository.SWT_KIND)
				return new DataTypePropertiesEditionPartImpl(component);
			if (kind == EcoreViewsRepository.FORM_KIND)
				return new DataTypePropertiesEditionPartForm(component);
		}
		if (key == EcoreViewsRepository.EEnumLiteral.class) {
			if (kind == EcoreViewsRepository.SWT_KIND)
				return new EEnumLiteralPropertiesEditionPartImpl(component);
			if (kind == EcoreViewsRepository.FORM_KIND)
				return new EEnumLiteralPropertiesEditionPartForm(component);
		}
		if (key == EcoreViewsRepository.Exceptions.class) {
			if (kind == EcoreViewsRepository.SWT_KIND)
				return new ExceptionsPropertiesEditionPartImpl(component);
			if (kind == EcoreViewsRepository.FORM_KIND)
				return new ExceptionsPropertiesEditionPartForm(component);
		}
		if (key == EcoreViewsRepository.EPackage.class) {
			if (kind == EcoreViewsRepository.SWT_KIND)
				return new EPackagePropertiesEditionPartImpl(component);
			if (kind == EcoreViewsRepository.FORM_KIND)
				return new EPackagePropertiesEditionPartForm(component);
		}
		if (key == EcoreViewsRepository.OperationElement.class) {
			if (kind == EcoreViewsRepository.SWT_KIND)
				return new OperationElementPropertiesEditionPartImpl(component);
			if (kind == EcoreViewsRepository.FORM_KIND)
				return new OperationElementPropertiesEditionPartForm(component);
		}
		if (key == EcoreViewsRepository.EReference.class) {
			if (kind == EcoreViewsRepository.SWT_KIND)
				return new EReferencePropertiesEditionPartImpl(component);
			if (kind == EcoreViewsRepository.FORM_KIND)
				return new EReferencePropertiesEditionPartForm(component);
		}
		return null;
	}

}
