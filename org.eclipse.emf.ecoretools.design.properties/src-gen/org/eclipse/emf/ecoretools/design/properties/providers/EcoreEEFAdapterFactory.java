/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.providers;

import org.eclipse.emf.common.notify.Adapter;

import org.eclipse.emf.ecore.util.EcoreAdapterFactory;

/**
 * 
 * 
 */
public class EcoreEEFAdapterFactory extends EcoreAdapterFactory {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecoretools.design.properties.util.EcoreAdapterFactory#createEAttributeAdapter()
	 * 
	 */
	public Adapter createEAttributeAdapter() {
		return new EAttributePropertiesEditionProvider();
	}
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecoretools.design.properties.util.EcoreAdapterFactory#createEAnnotationAdapter()
	 * 
	 */
	public Adapter createEAnnotationAdapter() {
		return new EAnnotationPropertiesEditionProvider();
	}
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecoretools.design.properties.util.EcoreAdapterFactory#createEClassAdapter()
	 * 
	 */
	public Adapter createEClassAdapter() {
		return new EClassPropertiesEditionProvider();
	}
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecoretools.design.properties.util.EcoreAdapterFactory#createEDataTypeAdapter()
	 * 
	 */
	public Adapter createEDataTypeAdapter() {
		return new EDataTypePropertiesEditionProvider();
	}
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecoretools.design.properties.util.EcoreAdapterFactory#createEEnumAdapter()
	 * 
	 */
	public Adapter createEEnumAdapter() {
		return new EEnumPropertiesEditionProvider();
	}
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecoretools.design.properties.util.EcoreAdapterFactory#createEEnumLiteralAdapter()
	 * 
	 */
	public Adapter createEEnumLiteralAdapter() {
		return new EEnumLiteralPropertiesEditionProvider();
	}
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecoretools.design.properties.util.EcoreAdapterFactory#createEOperationAdapter()
	 * 
	 */
	public Adapter createEOperationAdapter() {
		return new EOperationPropertiesEditionProvider();
	}
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecoretools.design.properties.util.EcoreAdapterFactory#createEPackageAdapter()
	 * 
	 */
	public Adapter createEPackageAdapter() {
		return new EPackagePropertiesEditionProvider();
	}
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecoretools.design.properties.util.EcoreAdapterFactory#createEParameterAdapter()
	 * 
	 */
	public Adapter createEParameterAdapter() {
		return new EParameterPropertiesEditionProvider();
	}
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecoretools.design.properties.util.EcoreAdapterFactory#createEReferenceAdapter()
	 * 
	 */
	public Adapter createEReferenceAdapter() {
		return new EReferencePropertiesEditionProvider();
	}

}
