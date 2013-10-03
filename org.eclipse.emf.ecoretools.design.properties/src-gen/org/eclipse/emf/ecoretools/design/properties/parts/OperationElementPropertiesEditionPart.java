/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.parts;

// Start of user code for imports
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;

import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;

import org.eclipse.jface.viewers.ViewerFilter;


// End of user code

/**
 * 
 * 
 */
public interface OperationElementPropertiesEditionPart {

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
	 * @return the eType
	 * 
	 */
	public EObject getEType();

	/**
	 * Init the eType
	 * @param settings the combo setting
	 */
	public void initEType(EObjectFlatComboSettings settings);

	/**
	 * Defines a new eType
	 * @param newValue the new eType to set
	 * 
	 */
	public void setEType(EObject newValue);

	/**
	 * Defines the button mode
	 * @param newValue the new mode to set
	 * 
	 */
	public void setETypeButtonMode(ButtonsModeEnum newValue);

	/**
	 * Adds the given filter to the eType edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addFilterToEType(ViewerFilter filter);

	/**
	 * Adds the given filter to the eType edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addBusinessFilterToEType(ViewerFilter filter);


	/**
	 * @return the ordered
	 * 
	 */
	public Boolean getOrdered();

	/**
	 * Defines a new ordered
	 * @param newValue the new ordered to set
	 * 
	 */
	public void setOrdered(Boolean newValue);


	/**
	 * @return the unique
	 * 
	 */
	public Boolean getUnique();

	/**
	 * Defines a new unique
	 * @param newValue the new unique to set
	 * 
	 */
	public void setUnique(Boolean newValue);


	/**
	 * @return the lowerBound
	 * 
	 */
	public String getLowerBound();

	/**
	 * Defines a new lowerBound
	 * @param newValue the new lowerBound to set
	 * 
	 */
	public void setLowerBound(String newValue);


	/**
	 * @return the upperBound
	 * 
	 */
	public String getUpperBound();

	/**
	 * Defines a new upperBound
	 * @param newValue the new upperBound to set
	 * 
	 */
	public void setUpperBound(String newValue);





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
