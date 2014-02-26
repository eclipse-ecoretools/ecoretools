/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.ecore.components;

// Start of user code for imports

import org.eclipse.emf.ecore.EDataType;
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
public class EDataTypePropertiesEditionComponent extends ComposedPropertiesEditionComponent {

	/**
	 * The Data Type part
	 * 
	 */
	private DataTypePropertiesEditionPart dataTypePart;

	/**
	 * The EDataTypeDataTypePropertiesEditionComponent sub component
	 * 
	 */
	protected EDataTypeDataTypePropertiesEditionComponent eDataTypeDataTypePropertiesEditionComponent;

	/**
	 * The Instanciation part
	 * 
	 */
	private InstanciationPropertiesEditionPart instanciationPart;

	/**
	 * The EDataTypeInstanciationPropertiesEditionComponent sub component
	 * 
	 */
	protected EDataTypeInstanciationPropertiesEditionComponent eDataTypeInstanciationPropertiesEditionComponent;

	/**
	 * Parameterized constructor
	 * 
	 * @param eDataType the EObject to edit
	 * 
	 */
	public EDataTypePropertiesEditionComponent(PropertiesEditingContext editingContext, EObject eDataType, String editing_mode) {
		super(editingContext, editing_mode);
		if (eDataType instanceof EDataType) {
			PropertiesEditingProvider provider = null;
			provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eDataType, PropertiesEditingProvider.class);
			eDataTypeDataTypePropertiesEditionComponent = (EDataTypeDataTypePropertiesEditionComponent)provider.getPropertiesEditingComponent(editingContext, editing_mode, EDataTypeDataTypePropertiesEditionComponent.DATATYPE_PART, EDataTypeDataTypePropertiesEditionComponent.class);
			addSubComponent(eDataTypeDataTypePropertiesEditionComponent);
			provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eDataType, PropertiesEditingProvider.class);
			eDataTypeInstanciationPropertiesEditionComponent = (EDataTypeInstanciationPropertiesEditionComponent)provider.getPropertiesEditingComponent(editingContext, editing_mode, EDataTypeInstanciationPropertiesEditionComponent.INSTANCIATION_PART, EDataTypeInstanciationPropertiesEditionComponent.class);
			addSubComponent(eDataTypeInstanciationPropertiesEditionComponent);
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
		if (EDataTypeDataTypePropertiesEditionComponent.DATATYPE_PART.equals(key)) {
			dataTypePart = (DataTypePropertiesEditionPart)eDataTypeDataTypePropertiesEditionComponent.getPropertiesEditionPart(kind, key);
			return (IPropertiesEditionPart)dataTypePart;
		}
		if (EDataTypeInstanciationPropertiesEditionComponent.INSTANCIATION_PART.equals(key)) {
			instanciationPart = (InstanciationPropertiesEditionPart)eDataTypeInstanciationPropertiesEditionComponent.getPropertiesEditionPart(kind, key);
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
