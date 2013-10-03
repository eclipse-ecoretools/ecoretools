/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.parts;

// Start of user code for imports
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings;

import org.eclipse.jface.viewers.ViewerFilter;


// End of user code

/**
 * 
 * 
 */
public interface EClassPropertiesEditionPart {

	/**
	 * @return the name
	 * 
	 */
	public String getName();

	/**
	 * Defines a new name
	 * @param newValue the new name to set
	 * 
	 */
	public void setName(String newValue);


	/**
	 * @return the abstract
	 * 
	 */
	public Boolean getAbstract_();

	/**
	 * Defines a new abstract
	 * @param newValue the new abstract to set
	 * 
	 */
	public void setAbstract_(Boolean newValue);


	/**
	 * @return the interface
	 * 
	 */
	public Boolean getInterface_();

	/**
	 * Defines a new interface
	 * @param newValue the new interface to set
	 * 
	 */
	public void setInterface_(Boolean newValue);




	/**
	 * Init the eSuperTypes
	 * @param settings settings for the eSuperTypes ReferencesTable 
	 */
	public void initESuperTypes(ReferencesTableSettings settings);

	/**
	 * Update the eSuperTypes
	 * @param newValue the eSuperTypes to update
	 * 
	 */
	public void updateESuperTypes();

	/**
	 * Adds the given filter to the eSuperTypes edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addFilterToESuperTypes(ViewerFilter filter);

	/**
	 * Adds the given filter to the eSuperTypes edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addBusinessFilterToESuperTypes(ViewerFilter filter);

	/**
	 * @return true if the given element is contained inside the eSuperTypes table
	 * 
	 */
	public boolean isContainedInESuperTypesTable(EObject element);




	/**
	 * Init the eGenericSuperTypes
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initEGenericSuperTypes(ReferencesTableSettings settings);

	/**
	 * Update the eGenericSuperTypes
	 * @param newValue the eGenericSuperTypes to update
	 * 
	 */
	public void updateEGenericSuperTypes();

	/**
	 * Adds the given filter to the eGenericSuperTypes edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addFilterToEGenericSuperTypes(ViewerFilter filter);

	/**
	 * Adds the given filter to the eGenericSuperTypes edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addBusinessFilterToEGenericSuperTypes(ViewerFilter filter);

	/**
	 * @return true if the given element is contained inside the eGenericSuperTypes table
	 * 
	 */
	public boolean isContainedInEGenericSuperTypesTable(EObject element);





	/**
	 * Returns the internationalized title text.
	 * 
	 * @return the internationalized title text.
	 * 
	 */
	public String getTitle();

	// Start of user code for additional methods
	
	// End of user code

}
