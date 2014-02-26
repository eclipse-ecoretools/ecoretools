/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.ecore.components;

// Start of user code for imports

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.ecoretools.design.properties.parts.ExceptionsPropertiesEditionPart;
import org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.impl.components.ComposedPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.providers.PropertiesEditingProvider;

// End of user code

/**
 * 
 * 
 */
public class EOperationPropertiesEditionComponent extends ComposedPropertiesEditionComponent {

	/**
	 * The Operation Element part
	 * 
	 */
	private OperationElementPropertiesEditionPart operationElementPart;

	/**
	 * The EOperationOperationElementPropertiesEditionComponent sub component
	 * 
	 */
	protected EOperationOperationElementPropertiesEditionComponent eOperationOperationElementPropertiesEditionComponent;

	/**
	 * The Exceptions part
	 * 
	 */
	private ExceptionsPropertiesEditionPart exceptionsPart;

	/**
	 * The EOperationExceptionsPropertiesEditionComponent sub component
	 * 
	 */
	protected EOperationExceptionsPropertiesEditionComponent eOperationExceptionsPropertiesEditionComponent;

	/**
	 * Parameterized constructor
	 * 
	 * @param eOperation the EObject to edit
	 * 
	 */
	public EOperationPropertiesEditionComponent(PropertiesEditingContext editingContext, EObject eOperation, String editing_mode) {
		super(editingContext, editing_mode);
		if (eOperation instanceof EOperation) {
			PropertiesEditingProvider provider = null;
			provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eOperation, PropertiesEditingProvider.class);
			eOperationOperationElementPropertiesEditionComponent = (EOperationOperationElementPropertiesEditionComponent)provider.getPropertiesEditingComponent(editingContext, editing_mode, EOperationOperationElementPropertiesEditionComponent.OPERATIONELEMENT_PART, EOperationOperationElementPropertiesEditionComponent.class);
			addSubComponent(eOperationOperationElementPropertiesEditionComponent);
			provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eOperation, PropertiesEditingProvider.class);
			eOperationExceptionsPropertiesEditionComponent = (EOperationExceptionsPropertiesEditionComponent)provider.getPropertiesEditingComponent(editingContext, editing_mode, EOperationExceptionsPropertiesEditionComponent.EXCEPTIONS_PART, EOperationExceptionsPropertiesEditionComponent.class);
			addSubComponent(eOperationExceptionsPropertiesEditionComponent);
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
		if (EOperationOperationElementPropertiesEditionComponent.OPERATIONELEMENT_PART.equals(key)) {
			operationElementPart = (OperationElementPropertiesEditionPart)eOperationOperationElementPropertiesEditionComponent.getPropertiesEditionPart(kind, key);
			return (IPropertiesEditionPart)operationElementPart;
		}
		if (EOperationExceptionsPropertiesEditionComponent.EXCEPTIONS_PART.equals(key)) {
			exceptionsPart = (ExceptionsPropertiesEditionPart)eOperationExceptionsPropertiesEditionComponent.getPropertiesEditionPart(kind, key);
			return (IPropertiesEditionPart)exceptionsPart;
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
		if (EcoreViewsRepository.OperationElement.class == key) {
			super.setPropertiesEditionPart(key, kind, propertiesEditionPart);
			operationElementPart = (OperationElementPropertiesEditionPart)propertiesEditionPart;
		}
		if (EcoreViewsRepository.Exceptions.class == key) {
			super.setPropertiesEditionPart(key, kind, propertiesEditionPart);
			exceptionsPart = (ExceptionsPropertiesEditionPart)propertiesEditionPart;
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
		if (key == EcoreViewsRepository.OperationElement.class) {
			super.initPart(key, kind, element, allResource);
		}
		if (key == EcoreViewsRepository.Exceptions.class) {
			super.initPart(key, kind, element, allResource);
		}
	}
}
