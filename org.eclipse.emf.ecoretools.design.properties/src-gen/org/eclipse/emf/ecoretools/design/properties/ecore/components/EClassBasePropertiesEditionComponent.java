/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.ecore.components;

// Start of user code for imports
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.eef.runtime.api.notify.EStructuralFeatureNotificationFilter;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.notify.NotificationFilter;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.context.impl.EReferencePropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.components.SinglePartPropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.utils.EEFConverterUtil;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.impl.CreateEditingPolicy;
import org.eclipse.emf.eef.runtime.providers.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;


// End of user code

/**
 * 
 * 
 */
public class EClassBasePropertiesEditionComponent extends SinglePartPropertiesEditingComponent {

	
	public static String BASE_PART = "Base"; //$NON-NLS-1$

	
	/**
	 * Settings for eSuperTypes ReferencesTable
	 */
	private ReferencesTableSettings eSuperTypesSettings;
	
	/**
	 * Settings for eGenericSuperTypes ReferencesTable
	 */
	protected ReferencesTableSettings eGenericSuperTypesSettings;
	
	
	/**
	 * Default constructor
	 * 
	 */
	public EClassBasePropertiesEditionComponent(PropertiesEditingContext editingContext, EObject eClass, String editing_mode) {
		super(editingContext, eClass, editing_mode);
		parts = new String[] { BASE_PART };
		repositoryKey = EcoreViewsRepository.class;
		partKey = EcoreViewsRepository.EClass.class;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Object, int, org.eclipse.emf.ecore.EObject, 
	 *      org.eclipse.emf.ecore.resource.ResourceSet)
	 * 
	 */
	public void initPart(Object key, int kind, EObject elt, ResourceSet allResource) {
		setInitializing(true);
		if (editingPart != null && key == partKey) {
			editingPart.setContext(elt, allResource);
			
			final EClass eClass = (EClass)elt;
			final EClassPropertiesEditionPart basePart = (EClassPropertiesEditionPart)editingPart;
			// init values
			if (isAccessible(EcoreViewsRepository.EClass.Properties.name))
				basePart.setName(EEFConverterUtil.convertToString(EcorePackage.Literals.ESTRING, eClass.getName()));
			
			if (isAccessible(EcoreViewsRepository.EClass.Properties.Abstraction.abstract_)) {
				basePart.setAbstract_(eClass.isAbstract());
			}
			if (isAccessible(EcoreViewsRepository.EClass.Properties.Abstraction.interface_)) {
				basePart.setInterface_(eClass.isInterface());
			}
			if (isAccessible(EcoreViewsRepository.EClass.Inheritance.eSuperTypes)) {
				eSuperTypesSettings = new ReferencesTableSettings(eClass, EcorePackage.eINSTANCE.getEClass_ESuperTypes());
				basePart.initESuperTypes(eSuperTypesSettings);
			}
			if (isAccessible(EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes)) {
				eGenericSuperTypesSettings = new ReferencesTableSettings(eClass, EcorePackage.eINSTANCE.getEClass_EGenericSuperTypes());
				basePart.initEGenericSuperTypes(eGenericSuperTypesSettings);
			}
			// init filters
			
			
			
			if (isAccessible(EcoreViewsRepository.EClass.Inheritance.eSuperTypes)) {
				basePart.addFilterToESuperTypes(new EObjectFilter(EcorePackage.Literals.ECLASS));
				// Start of user code for additional businessfilters for eSuperTypes
				// End of user code
			}
			if (isAccessible(EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes)) {
				basePart.addFilterToEGenericSuperTypes(new ViewerFilter() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof EGenericType); //$NON-NLS-1$ 
					}
			
				});
				// Start of user code for additional businessfilters for eGenericSuperTypes
				// End of user code
			}
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
		if (editorKey == EcoreViewsRepository.EClass.Properties.name) {
			return EcorePackage.eINSTANCE.getENamedElement_Name();
		}
		if (editorKey == EcoreViewsRepository.EClass.Properties.Abstraction.abstract_) {
			return EcorePackage.eINSTANCE.getEClass_Abstract();
		}
		if (editorKey == EcoreViewsRepository.EClass.Properties.Abstraction.interface_) {
			return EcorePackage.eINSTANCE.getEClass_Interface();
		}
		if (editorKey == EcoreViewsRepository.EClass.Inheritance.eSuperTypes) {
			return EcorePackage.eINSTANCE.getEClass_ESuperTypes();
		}
		if (editorKey == EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes) {
			return EcorePackage.eINSTANCE.getEClass_EGenericSuperTypes();
		}
		return super.associatedFeature(editorKey);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updateSemanticModel(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void updateSemanticModel(final IPropertiesEditionEvent event) {
		EClass eClass = (EClass)semanticObject;
		if (EcoreViewsRepository.EClass.Properties.name == event.getAffectedEditor()) {
			eClass.setName((java.lang.String)EEFConverterUtil.createFromString(EcorePackage.Literals.ESTRING, (String)event.getNewValue()));
		}
		if (EcoreViewsRepository.EClass.Properties.Abstraction.abstract_ == event.getAffectedEditor()) {
			eClass.setAbstract((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EClass.Properties.Abstraction.interface_ == event.getAffectedEditor()) {
			eClass.setInterface((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EClass.Inheritance.eSuperTypes == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.ADD) {
				if (event.getNewValue() instanceof EClass) {
					eSuperTypesSettings.addToReference((EObject) event.getNewValue());
				}
			} else if (event.getKind() == PropertiesEditionEvent.REMOVE) {
				eSuperTypesSettings.removeFromReference((EObject) event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.MOVE) {
				eSuperTypesSettings.move(event.getNewIndex(), (EClass) event.getNewValue());
			}
		}
		if (EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.ADD) {
				EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, eGenericSuperTypesSettings, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(semanticObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy instanceof CreateEditingPolicy) {
						policy.execute();
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.EDIT) {
				EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, (EObject) event.getNewValue(), editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt((EObject) event.getNewValue(), PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy editionPolicy = provider.getPolicy(context);
					if (editionPolicy != null) {
						editionPolicy.execute();
					}
				}
			} else if (event.getKind() == PropertiesEditionEvent.REMOVE) {
				eGenericSuperTypesSettings.removeFromReference((EObject) event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.MOVE) {
				eGenericSuperTypesSettings.move(event.getNewIndex(), (EGenericType) event.getNewValue());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updatePart(org.eclipse.emf.common.notify.Notification)
	 */
	public void updatePart(Notification msg) {
		super.updatePart(msg);
		if (editingPart.isVisible()) {
			EClassPropertiesEditionPart basePart = (EClassPropertiesEditionPart)editingPart;
			if (EcorePackage.eINSTANCE.getENamedElement_Name().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EClass.Properties.name)) {
				if (msg.getNewValue() != null) {
					basePart.setName(EcoreUtil.convertToString(EcorePackage.Literals.ESTRING, msg.getNewValue()));
				} else {
					basePart.setName("");
				}
			}
			if (EcorePackage.eINSTANCE.getEClass_Abstract().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EClass.Properties.Abstraction.abstract_))
				basePart.setAbstract_((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEClass_Interface().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EClass.Properties.Abstraction.interface_))
				basePart.setInterface_((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEClass_ESuperTypes().equals(msg.getFeature())  && isAccessible(EcoreViewsRepository.EClass.Inheritance.eSuperTypes))
				basePart.updateESuperTypes();
			if (EcorePackage.eINSTANCE.getEClass_EGenericSuperTypes().equals(msg.getFeature()) && isAccessible(EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes))
				basePart.updateEGenericSuperTypes();
			
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
			EcorePackage.eINSTANCE.getEClass_Abstract(),
			EcorePackage.eINSTANCE.getEClass_Interface(),
			EcorePackage.eINSTANCE.getEClass_ESuperTypes(),
			EcorePackage.eINSTANCE.getEClass_EGenericSuperTypes()		);
		return new NotificationFilter[] {filter,};
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.Object, int)
	 * 
	 */
	public String getHelpContent(Object key, int kind) {
		if (key == EcoreViewsRepository.EClass.Properties.name)
			return "The name of this model element"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.Instanciation.instanceClassName)
			return "The erased instance class name denoted by this classifier"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.Instanciation.instanceTypeName)
			return "The full instance type name denoted by this classifier"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EClass.Properties.Abstraction.abstract_)
			return "Whether instances of this class can be created"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EClass.Properties.Abstraction.interface_)
			return "Whether no corresponding implementation will be generated for this class"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EClass.Inheritance.eSuperTypes)
			return "The immediate super types of this class"; //$NON-NLS-1$
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
				if (EcoreViewsRepository.EClass.Properties.name == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getENamedElement_Name().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getENamedElement_Name().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EClass.Properties.Abstraction.abstract_ == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEClass_Abstract().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEClass_Abstract().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EClass.Properties.Abstraction.interface_ == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEClass_Interface().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEClass_Interface().getEAttributeType(), newValue);
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
