package org.eclipse.emf.ecoretools.design.properties.parts.forms;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.impl.utils.EEFUtils;
import org.eclipse.jface.viewers.IFilter;

public class EReferenceEOppositePropertiesEditionPartForm extends EReferencePropertiesEditionPartForm {

    /**
     * Provides the filter used by the plugin.xml to assign part forms.
     */
    public static class EditionFilter implements IFilter {

        /**
         * {@inheritDoc}
         * 
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         */
        public boolean select(Object toTest) {
            EObject eObj = EEFUtils.resolveSemanticObject(toTest);
            return eObj != null && EcorePackage.Literals.EREFERENCE == eObj.eClass() && ((EReference) eObj).getEOpposite() != null;
        }

    }

}
