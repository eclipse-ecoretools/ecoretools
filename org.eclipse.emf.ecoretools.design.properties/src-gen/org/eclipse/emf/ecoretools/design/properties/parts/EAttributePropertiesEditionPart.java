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
public interface EAttributePropertiesEditionPart {

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
	 * @return the defaultValueLiteral
	 * 
	 */
	public String getDefaultValueLiteral();

	/**
	 * Defines a new defaultValueLiteral
	 * @param newValue the new defaultValueLiteral to set
	 * 
	 */
	public void setDefaultValueLiteral(String newValue);


	/**
	 * @return the iD
	 * 
	 */
	public Boolean getID();

	/**
	 * Defines a new iD
	 * @param newValue the new iD to set
	 * 
	 */
	public void setID(Boolean newValue);


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
	 * @return the derived
	 * 
	 */
	public Boolean getDerived();

	/**
	 * Defines a new derived
	 * @param newValue the new derived to set
	 * 
	 */
	public void setDerived(Boolean newValue);


	/**
	 * @return the changeable
	 * 
	 */
	public Boolean getChangeable();

	/**
	 * Defines a new changeable
	 * @param newValue the new changeable to set
	 * 
	 */
	public void setChangeable(Boolean newValue);


	/**
	 * @return the unsettable
	 * 
	 */
	public Boolean getUnsettable();

	/**
	 * Defines a new unsettable
	 * @param newValue the new unsettable to set
	 * 
	 */
	public void setUnsettable(Boolean newValue);


	/**
	 * @return the transient
	 * 
	 */
	public Boolean getTransient_();

	/**
	 * Defines a new transient
	 * @param newValue the new transient to set
	 * 
	 */
	public void setTransient_(Boolean newValue);


	/**
	 * @return the volatile
	 * 
	 */
	public Boolean getVolatile_();

	/**
	 * Defines a new volatile
	 * @param newValue the new volatile to set
	 * 
	 */
	public void setVolatile_(Boolean newValue);





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
