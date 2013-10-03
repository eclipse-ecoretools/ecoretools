/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.parts;

// Start of user code for imports
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;

import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;

import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings;

import org.eclipse.jface.viewers.ViewerFilter;


// End of user code

/**
 * 
 * 
 */
public interface EAnnotationPropertiesEditionPart {

	/**
	 * @return the source
	 * 
	 */
	public String getSource();

	/**
	 * Defines a new source
	 * @param newValue the new source to set
	 * 
	 */
	public void setSource(String newValue);




	/**
	 * Init the details
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initDetails(ReferencesTableSettings settings);

	/**
	 * Update the details
	 * @param newValue the details to update
	 * 
	 */
	public void updateDetails();

	/**
	 * Adds the given filter to the details edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addFilterToDetails(ViewerFilter filter);

	/**
	 * Adds the given filter to the details edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addBusinessFilterToDetails(ViewerFilter filter);

	/**
	 * @return true if the given element is contained inside the details table
	 * 
	 */
	public boolean isContainedInDetailsTable(EObject element);


	/**
	 * @return the eModelElement
	 * 
	 */
	public EObject getEModelElement();

	/**
	 * Init the eModelElement
	 * @param settings the combo setting
	 */
	public void initEModelElement(EObjectFlatComboSettings settings);

	/**
	 * Defines a new eModelElement
	 * @param newValue the new eModelElement to set
	 * 
	 */
	public void setEModelElement(EObject newValue);

	/**
	 * Defines the button mode
	 * @param newValue the new mode to set
	 * 
	 */
	public void setEModelElementButtonMode(ButtonsModeEnum newValue);

	/**
	 * Adds the given filter to the eModelElement edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addFilterToEModelElement(ViewerFilter filter);

	/**
	 * Adds the given filter to the eModelElement edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addBusinessFilterToEModelElement(ViewerFilter filter);




	/**
	 * Init the contents
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initContents(ReferencesTableSettings settings);

	/**
	 * Update the contents
	 * @param newValue the contents to update
	 * 
	 */
	public void updateContents();

	/**
	 * Adds the given filter to the contents edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addFilterToContents(ViewerFilter filter);

	/**
	 * Adds the given filter to the contents edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addBusinessFilterToContents(ViewerFilter filter);

	/**
	 * @return true if the given element is contained inside the contents table
	 * 
	 */
	public boolean isContainedInContentsTable(EObject element);




	/**
	 * Init the references
	 * @param settings settings for the references ReferencesTable 
	 */
	public void initReferences(ReferencesTableSettings settings);

	/**
	 * Update the references
	 * @param newValue the references to update
	 * 
	 */
	public void updateReferences();

	/**
	 * Adds the given filter to the references edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addFilterToReferences(ViewerFilter filter);

	/**
	 * Adds the given filter to the references edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 * 
	 */
	public void addBusinessFilterToReferences(ViewerFilter filter);

	/**
	 * @return true if the given element is contained inside the references table
	 * 
	 */
	public boolean isContainedInReferencesTable(EObject element);





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
