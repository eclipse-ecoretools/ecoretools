/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.components;

// Start of user code for imports
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecoretools.design.properties.parts.EPackagePropertiesEditionPart;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.eef.runtime.api.notify.EStructuralFeatureNotificationFilter;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.notify.NotificationFilter;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.impl.components.SinglePartPropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.impl.utils.EEFConverterUtil;


// End of user code

/**
 * 
 * 
 */
public class EPackagePropertiesEditionComponent extends SinglePartPropertiesEditingComponent {

	
	public static String BASE_PART = "Base"; //$NON-NLS-1$

	
	
	/**
	 * Default constructor
	 * 
	 */
	public EPackagePropertiesEditionComponent(PropertiesEditingContext editingContext, EObject ePackage, String editing_mode) {
		super(editingContext, ePackage, editing_mode);
		parts = new String[] { BASE_PART };
		repositoryKey = EcoreViewsRepository.class;
		partKey = EcoreViewsRepository.EPackage.class;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Object, int, org.eclipse.emf.ecoretools.design.properties.EObject, 
	 *      org.eclipse.emf.ecoretools.design.properties.resource.ResourceSet)
	 * 
	 */
	public void initPart(Object key, int kind, EObject elt, ResourceSet allResource) {
		setInitializing(true);
		if (editingPart != null && key == partKey) {
			editingPart.setContext(elt, allResource);
			
			final EPackage ePackage = (EPackage)elt;
			final EPackagePropertiesEditionPart basePart = (EPackagePropertiesEditionPart)editingPart;
			// init values
			if (isAccessible(EcoreViewsRepository.EPackage.Properties.name))
				basePart.setName(EEFConverterUtil.convertToString(EcorePackage.Literals.ESTRING, ePackage.getName()));
			
			if (isAccessible(EcoreViewsRepository.EPackage.Properties.nsURI))
				basePart.setNsURI(EEFConverterUtil.convertToString(EcorePackage.Literals.ESTRING, ePackage.getNsURI()));
			
			if (isAccessible(EcoreViewsRepository.EPackage.Properties.nsPrefix))
				basePart.setNsPrefix(EEFConverterUtil.convertToString(EcorePackage.Literals.ESTRING, ePackage.getNsPrefix()));
			
			// init filters
			
			
			
			// init values for referenced views
			
			// init filters for referenced views
			
		}
		setInitializing(false);
	}






	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#associatedFeature(java.lang.Object)
	 */
	public EStructuralFeature associatedFeature(Object editorKey) {
		if (editorKey == EcoreViewsRepository.EPackage.Properties.name) {
			return EcorePackage.eINSTANCE.getENamedElement_Name();
		}
		if (editorKey == EcoreViewsRepository.EPackage.Properties.nsURI) {
			return EcorePackage.eINSTANCE.getEPackage_NsURI();
		}
		if (editorKey == EcoreViewsRepository.EPackage.Properties.nsPrefix) {
			return EcorePackage.eINSTANCE.getEPackage_NsPrefix();
		}
		return super.associatedFeature(editorKey);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updateSemanticModel(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void updateSemanticModel(final IPropertiesEditionEvent event) {
		EPackage ePackage = (EPackage)semanticObject;
		if (EcoreViewsRepository.EPackage.Properties.name == event.getAffectedEditor()) {
			ePackage.setName((java.lang.String)EEFConverterUtil.createFromString(EcorePackage.Literals.ESTRING, (String)event.getNewValue()));
		}
		if (EcoreViewsRepository.EPackage.Properties.nsURI == event.getAffectedEditor()) {
			ePackage.setNsURI((java.lang.String)EEFConverterUtil.createFromString(EcorePackage.Literals.ESTRING, (String)event.getNewValue()));
		}
		if (EcoreViewsRepository.EPackage.Properties.nsPrefix == event.getAffectedEditor()) {
			ePackage.setNsPrefix((java.lang.String)EEFConverterUtil.createFromString(EcorePackage.Literals.ESTRING, (String)event.getNewValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updatePart(org.eclipse.emf.common.notify.Notification)
	 */
	public void updatePart(Notification msg) {
		super.updatePart(msg);
		if (editingPart.isVisible()) {
			EPackagePropertiesEditionPart basePart = (EPackagePropertiesEditionPart)editingPart;
			if (EcorePackage.eINSTANCE.getENamedElement_Name().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EPackage.Properties.name)) {
				if (msg.getNewValue() != null) {
					basePart.setName(EcoreUtil.convertToString(EcorePackage.Literals.ESTRING, msg.getNewValue()));
				} else {
					basePart.setName("");
				}
			}
			if (EcorePackage.eINSTANCE.getEPackage_NsURI().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EPackage.Properties.nsURI)) {
				if (msg.getNewValue() != null) {
					basePart.setNsURI(EcoreUtil.convertToString(EcorePackage.Literals.ESTRING, msg.getNewValue()));
				} else {
					basePart.setNsURI("");
				}
			}
			if (EcorePackage.eINSTANCE.getEPackage_NsPrefix().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EPackage.Properties.nsPrefix)) {
				if (msg.getNewValue() != null) {
					basePart.setNsPrefix(EcoreUtil.convertToString(EcorePackage.Literals.ESTRING, msg.getNewValue()));
				} else {
					basePart.setNsPrefix("");
				}
			}
			
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getNotificationFilters()
	 */
	@Override
	protected NotificationFilter[] getNotificationFilters() {
		NotificationFilter filter = new EStructuralFeatureNotificationFilter(
			EcorePackage.eINSTANCE.getENamedElement_Name(),
			EcorePackage.eINSTANCE.getEPackage_NsURI(),
			EcorePackage.eINSTANCE.getEPackage_NsPrefix()		);
		return new NotificationFilter[] {filter,};
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.Object, int)
	 * 
	 */
	public String getHelpContent(Object key, int kind) {
		if (key == EcoreViewsRepository.EPackage.Properties.name)
			return "The name of this model element"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EPackage.Properties.nsURI)
			return "The universally unique namespace identifier, typically an absolute URI, for this package"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EPackage.Properties.nsPrefix)
			return "The namespace prefix used by default when serializing instances of the package's classes"; //$NON-NLS-1$
		return super.getHelpContent(key, kind);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#validateValue(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public Diagnostic validateValue(IPropertiesEditionEvent event) {
		Diagnostic ret = Diagnostic.OK_INSTANCE;
		if (event.getNewValue() != null) {
			try {
				if (EcoreViewsRepository.EPackage.Properties.name == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getENamedElement_Name().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getENamedElement_Name().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EPackage.Properties.nsURI == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEPackage_NsURI().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEPackage_NsURI().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EPackage.Properties.nsPrefix == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEPackage_NsPrefix().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEPackage_NsPrefix().getEAttributeType(), newValue);
				}
			} catch (IllegalArgumentException iae) {
				ret = BasicDiagnostic.toDiagnostic(iae);
			} catch (WrappedException we) {
				ret = BasicDiagnostic.toDiagnostic(we);
			}
		}
		return ret;
	}


	

}
