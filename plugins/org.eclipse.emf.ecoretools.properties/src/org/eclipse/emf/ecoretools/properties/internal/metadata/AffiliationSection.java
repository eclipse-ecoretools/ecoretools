/***********************************************************************
 * Copyright (c) 2007 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 **********************************************************************/

package org.eclipse.emf.ecoretools.properties.internal.metadata;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.tabbedproperties.EMFRecordingChangeCommand;
import org.eclipse.emf.tabbedproperties.sections.AbstractChooserPropertySection;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * Section to edit affiliation extended metadata annotation
 * 
 * @see ExtendedMetaData#setAffiliation(org.eclipse.emf.ecore.EStructuralFeature,
 *      org.eclipse.emf.ecore.EStructuralFeature)
 * @author <a href="david.sciamma@anyware-tech.com">David Sciamma</a>
 */
public class AffiliationSection extends AbstractChooserPropertySection {

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTabbedPropertySection#getFeature()
	 */
	protected EStructuralFeature getFeature() {
		return null;
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractTabbedPropertySection#getLabelText()
	 */
	protected String getLabelText() {
		return "Affiliation";
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractChooserPropertySection#getComboFeatureValues()
	 */
	protected Object[] getComboFeatureValues() {
		return getChoices(getEObject(), EcorePackage.eINSTANCE.getEStructuralFeature());
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractChooserPropertySection#getLabelProvider()
	 */
	protected ILabelProvider getLabelProvider() {
		return new AdapterFactoryLabelProvider(new EcoreItemProviderAdapterFactory());
	}

	/**
	 * @see org.eclipse.emf.tabbedproperties.sections.AbstractChooserPropertySection#getFeatureValue()
	 */
	protected Object getFeatureValue() {
		return ExtendedMetaData.INSTANCE.getAffiliation((EStructuralFeature) getEObject());
	}

	/**
	 * Handle the combo modified event.
	 */
	protected void handleComboModified() {
		if (!isRefreshing()) {
			final Object newAffiliation = getCSingleObjectChooser().getSelection();
			EditingDomain editingDomain = getEditingDomain();
			if (getEObjectList().size() == 1) {
				EStructuralFeature oldAffiliation = ExtendedMetaData.INSTANCE.getAffiliation((EStructuralFeature) getEObject());
				if (oldAffiliation != newAffiliation) {
					editingDomain.getCommandStack().execute(new EMFRecordingChangeCommand(getEObject().eResource()) {

						protected void doExecute() {
							ExtendedMetaData.INSTANCE.setAffiliation((EStructuralFeature) getEObject(), (EStructuralFeature) newAffiliation);
						}
					});
				}
			} else {
				CompoundCommand compoundCommand = new CompoundCommand();
				/* apply the property change to all selected elements */
				for (final EObject nextObject : getEObjectList()) {
					EStructuralFeature oldAffiliation = ExtendedMetaData.INSTANCE.getAffiliation((EStructuralFeature) nextObject);
					if (oldAffiliation != newAffiliation) {
						editingDomain.getCommandStack().execute(new EMFRecordingChangeCommand(nextObject.eResource()) {

							protected void doExecute() {
								ExtendedMetaData.INSTANCE.setAffiliation((EStructuralFeature) nextObject, (EStructuralFeature) newAffiliation);
							}
						});
					}
				}
				editingDomain.getCommandStack().execute(compoundCommand);
			}
		}
	}
}
