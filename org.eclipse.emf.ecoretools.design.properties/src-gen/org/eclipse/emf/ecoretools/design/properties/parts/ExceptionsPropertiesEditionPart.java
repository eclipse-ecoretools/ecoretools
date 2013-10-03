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
public interface ExceptionsPropertiesEditionPart {



	/**
	 * Init the eExceptions
	 * @param settings settings for the eExceptions ReferencesTable 
	 */
	public void initEExceptions(ReferencesTableSettings settings);

	/**
	 * Update the eExceptions
	 * @param newValue the eExceptions to update
	 * 
	 */
	public void updateEExceptions();

	/**
	 * Adds the given filter to the eExceptions edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addFilterToEExceptions(ViewerFilter filter);

	/**
	 * Adds the given filter to the eExceptions edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addBusinessFilterToEExceptions(ViewerFilter filter);

	/**
	 * @return true if the given element is contained inside the eExceptions table
	 * 
	 */
	public boolean isContainedInEExceptionsTable(EObject element);





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
