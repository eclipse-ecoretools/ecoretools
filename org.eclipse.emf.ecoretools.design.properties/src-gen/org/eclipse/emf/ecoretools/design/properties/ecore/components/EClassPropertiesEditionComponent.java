/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.ecore.components;

// Start of user code for imports

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart;
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
public class EClassPropertiesEditionComponent extends ComposedPropertiesEditionComponent {

	/**
	 * The Base part
	 * 
	 */
	private EClassPropertiesEditionPart basePart;

	/**
	 * The EClassBasePropertiesEditionComponent sub component
	 * 
	 */
	protected EClassBasePropertiesEditionComponent eClassBasePropertiesEditionComponent;

	/**
	 * The Instanciation part
	 * 
	 */
	private InstanciationPropertiesEditionPart instanciationPart;

	/**
	 * The EClassInstanciationPropertiesEditionComponent sub component
	 * 
	 */
	protected EClassInstanciationPropertiesEditionComponent eClassInstanciationPropertiesEditionComponent;

	/**
	 * Parameterized constructor
	 * 
	 * @param eClass the EObject to edit
	 * 
	 */
	public EClassPropertiesEditionComponent(PropertiesEditingContext editingContext, EObject eClass, String editing_mode) {
		super(editingContext, editing_mode);
		if (eClass instanceof EClass) {
			PropertiesEditingProvider provider = null;
			provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eClass, PropertiesEditingProvider.class);
			eClassBasePropertiesEditionComponent = (EClassBasePropertiesEditionComponent)provider.getPropertiesEditingComponent(editingContext, editing_mode, EClassBasePropertiesEditionComponent.BASE_PART, EClassBasePropertiesEditionComponent.class);
			addSubComponent(eClassBasePropertiesEditionComponent);
			provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eClass, PropertiesEditingProvider.class);
			eClassInstanciationPropertiesEditionComponent = (EClassInstanciationPropertiesEditionComponent)provider.getPropertiesEditingComponent(editingContext, editing_mode, EClassInstanciationPropertiesEditionComponent.INSTANCIATION_PART, EClassInstanciationPropertiesEditionComponent.class);
			addSubComponent(eClassInstanciationPropertiesEditionComponent);
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
		if (EClassBasePropertiesEditionComponent.BASE_PART.equals(key)) {
			basePart = (EClassPropertiesEditionPart)eClassBasePropertiesEditionComponent.getPropertiesEditionPart(kind, key);
			return (IPropertiesEditionPart)basePart;
		}
		if (EClassInstanciationPropertiesEditionComponent.INSTANCIATION_PART.equals(key)) {
			instanciationPart = (InstanciationPropertiesEditionPart)eClassInstanciationPropertiesEditionComponent.getPropertiesEditionPart(kind, key);
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
		if (EcoreViewsRepository.EClass.class == key) {
			super.setPropertiesEditionPart(key, kind, propertiesEditionPart);
			basePart = (EClassPropertiesEditionPart)propertiesEditionPart;
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
		if (key == EcoreViewsRepository.EClass.class) {
			super.initPart(key, kind, element, allResource);
		}
		if (key == EcoreViewsRepository.Instanciation.class) {
			super.initPart(key, kind, element, allResource);
		}
	}
}
