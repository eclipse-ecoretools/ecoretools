/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.components;

// Start of user code for imports

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecoretools.design.properties.parts.DataTypePropertiesEditionPart;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.ecoretools.design.properties.parts.InstanciationPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.providers.PropertiesEditingProvider;

// End of user code

/**
 * 
 * 
 */
public class EEnumPropertiesEditionComponent extends ComposedPropertiesEditionComponent {

	/**
	 * The Data Type part
	 * 
	 */
	private DataTypePropertiesEditionPart dataTypePart;

	/**
	 * The EEnumDataTypePropertiesEditionComponent sub component
	 * 
	 */
	protected EEnumDataTypePropertiesEditionComponent eEnumDataTypePropertiesEditionComponent;

	/**
	 * The Instanciation part
	 * 
	 */
	private InstanciationPropertiesEditionPart instanciationPart;

	/**
	 * The EEnumInstanciationPropertiesEditionComponent sub component
	 * 
	 */
	protected EEnumInstanciationPropertiesEditionComponent eEnumInstanciationPropertiesEditionComponent;

	/**
	 * Parameterized constructor
	 * 
	 * @param eEnum the EObject to edit
	 * 
	 */
	public EEnumPropertiesEditionComponent(PropertiesEditingContext editingContext, EObject eEnum, String editing_mode) {
		super(editingContext, editing_mode);
		if (eEnum instanceof EEnum) {
			PropertiesEditingProvider provider = null;
			provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eEnum, PropertiesEditingProvider.class);
			eEnumDataTypePropertiesEditionComponent = (EEnumDataTypePropertiesEditionComponent)provider.getPropertiesEditingComponent(editingContext, editing_mode, EEnumDataTypePropertiesEditionComponent.DATATYPE_PART, EEnumDataTypePropertiesEditionComponent.class);
			addSubComponent(eEnumDataTypePropertiesEditionComponent);
			provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eEnum, PropertiesEditingProvider.class);
			eEnumInstanciationPropertiesEditionComponent = (EEnumInstanciationPropertiesEditionComponent)provider.getPropertiesEditingComponent(editingContext, editing_mode, EEnumInstanciationPropertiesEditionComponent.INSTANCIATION_PART, EEnumInstanciationPropertiesEditionComponent.class);
			addSubComponent(eEnumInstanciationPropertiesEditionComponent);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent#
	 *      getPropertiesEditionPart(int, java.lang.String)
	 * 
	 */
	public IPropertiesEditionPart getPropertiesEditionPart(int kind, String key) {
		if (EEnumDataTypePropertiesEditionComponent.DATATYPE_PART.equals(key)) {
			dataTypePart = (DataTypePropertiesEditionPart)eEnumDataTypePropertiesEditionComponent.getPropertiesEditionPart(kind, key);
			return (IPropertiesEditionPart)dataTypePart;
		}
		if (EEnumInstanciationPropertiesEditionComponent.INSTANCIATION_PART.equals(key)) {
			instanciationPart = (InstanciationPropertiesEditionPart)eEnumInstanciationPropertiesEditionComponent.getPropertiesEditionPart(kind, key);
			return (IPropertiesEditionPart)instanciationPart;
		}
		return super.getPropertiesEditionPart(kind, key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent#
	 *      setPropertiesEditionPart(java.lang.Object, int,
	 *      org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart)
	 * 
	 */
	public void setPropertiesEditionPart(java.lang.Object key, int kind, IPropertiesEditionPart propertiesEditionPart) {
		if (EcoreViewsRepository.DataType.class == key) {
			super.setPropertiesEditionPart(key, kind, propertiesEditionPart);
			dataTypePart = (DataTypePropertiesEditionPart)propertiesEditionPart;
		}
		if (EcoreViewsRepository.Instanciation.class == key) {
			super.setPropertiesEditionPart(key, kind, propertiesEditionPart);
			instanciationPart = (InstanciationPropertiesEditionPart)propertiesEditionPart;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent#
	 *      initPart(java.lang.Object, int, org.eclipse.emf.ecoretools.design.properties.EObject,
	 *      org.eclipse.emf.ecoretools.design.properties.resource.ResourceSet)
	 * 
	 */
	public void initPart(java.lang.Object key, int kind, EObject element, ResourceSet allResource) {
		if (key == EcoreViewsRepository.DataType.class) {
			super.initPart(key, kind, element, allResource);
		}
		if (key == EcoreViewsRepository.Instanciation.class) {
			super.initPart(key, kind, element, allResource);
		}
	}
}
