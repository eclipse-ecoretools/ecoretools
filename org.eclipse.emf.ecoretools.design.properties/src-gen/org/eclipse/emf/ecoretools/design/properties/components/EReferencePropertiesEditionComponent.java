/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.components;

// Start of user code for imports
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecoretools.design.properties.parts.EReferencePropertiesEditionPart;
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
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;


// End of user code

/**
 * 
 * 
 */
public class EReferencePropertiesEditionComponent extends SinglePartPropertiesEditingComponent {

	
	public static String BASE_PART = "Base"; //$NON-NLS-1$

	
	/**
	 * Settings for eType EObjectFlatComboViewer
	 */
	private EObjectFlatComboSettings eTypeSettings;
	
	/**
	 * Settings for eOpposite EObjectFlatComboViewer
	 */
	private EObjectFlatComboSettings eOppositeSettings;
	
	/**
	 * Settings for eKeys ReferencesTable
	 */
	private ReferencesTableSettings eKeysSettings;
	
	
	/**
	 * Default constructor
	 * 
	 */
	public EReferencePropertiesEditionComponent(PropertiesEditingContext editingContext, EObject eReference, String editing_mode) {
		super(editingContext, eReference, editing_mode);
		parts = new String[] { BASE_PART };
		repositoryKey = EcoreViewsRepository.class;
		partKey = EcoreViewsRepository.EReference.class;
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
			
			final EReference eReference = (EReference)elt;
			final EReferencePropertiesEditionPart basePart = (EReferencePropertiesEditionPart)editingPart;
			// init values
			if (isAccessible(EcoreViewsRepository.EReference.Properties.name))
				basePart.setName(EEFConverterUtil.convertToString(EcorePackage.Literals.ESTRING, eReference.getName()));
			
			if (isAccessible(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered)) {
				basePart.setOrdered(eReference.isOrdered());
			}
			if (isAccessible(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique)) {
				basePart.setUnique(eReference.isUnique());
			}
			if (isAccessible(EcoreViewsRepository.EReference.Cardinality.lowerBound)) {
				basePart.setLowerBound(EEFConverterUtil.convertToString(EcorePackage.Literals.EINT, eReference.getLowerBound()));
			}
			
			if (isAccessible(EcoreViewsRepository.EReference.Cardinality.upperBound)) {
				basePart.setUpperBound(EEFConverterUtil.convertToString(EcorePackage.Literals.EINT, eReference.getUpperBound()));
			}
			
			if (isAccessible(EcoreViewsRepository.EReference.Properties.eType)) {
				// init part
				eTypeSettings = new EObjectFlatComboSettings(eReference, EcorePackage.eINSTANCE.getETypedElement_EType());
				basePart.initEType(eTypeSettings);
				// set the button mode
				basePart.setETypeButtonMode(ButtonsModeEnum.BROWSE);
			}
			if (isAccessible(EcoreViewsRepository.EReference.Behavior.Changeability.changeable)) {
				basePart.setChangeable(eReference.isChangeable());
			}
			if (isAccessible(EcoreViewsRepository.EReference.Behavior.Others.volatile_)) {
				basePart.setVolatile_(eReference.isVolatile());
			}
			if (isAccessible(EcoreViewsRepository.EReference.Behavior.Others.transient_)) {
				basePart.setTransient_(eReference.isTransient());
			}
			if (isAccessible(EcoreViewsRepository.EReference.Properties.defaultValueLiteral))
				basePart.setDefaultValueLiteral(EEFConverterUtil.convertToString(EcorePackage.Literals.ESTRING, eReference.getDefaultValueLiteral()));
			
			if (isAccessible(EcoreViewsRepository.EReference.Behavior.Changeability.unsettable)) {
				basePart.setUnsettable(eReference.isUnsettable());
			}
			if (isAccessible(EcoreViewsRepository.EReference.Behavior.Changeability.derived)) {
				basePart.setDerived(eReference.isDerived());
			}
			if (isAccessible(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment)) {
				basePart.setContainment(eReference.isContainment());
			}
			if (isAccessible(EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies)) {
				basePart.setResolveProxies(eReference.isResolveProxies());
			}
			if (isAccessible(EcoreViewsRepository.EReference.Properties.eOpposite)) {
				// init part
				eOppositeSettings = new EObjectFlatComboSettings(eReference, EcorePackage.eINSTANCE.getEReference_EOpposite());
				basePart.initEOpposite(eOppositeSettings);
				// set the button mode
				basePart.setEOppositeButtonMode(ButtonsModeEnum.BROWSE);
			}
			if (isAccessible(EcoreViewsRepository.EReference.Properties.eKeys)) {
				eKeysSettings = new ReferencesTableSettings(eReference, EcorePackage.eINSTANCE.getEReference_EKeys());
				basePart.initEKeys(eKeysSettings);
			}
			// init filters
			
			
			
			
			
			if (isAccessible(EcoreViewsRepository.EReference.Properties.eType)) {
				basePart.addFilterToEType(new ViewerFilter() {
				
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof EClassifier); //$NON-NLS-1$ 
					}
					
				});
				// Start of user code for additional businessfilters for eType
				// End of user code
			}
			
			
			
			
			
			
			
			
			if (isAccessible(EcoreViewsRepository.EReference.Properties.eOpposite)) {
				basePart.addFilterToEOpposite(new ViewerFilter() {
				
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof EReference); //$NON-NLS-1$ 
					}
					
				});
				// Start of user code for additional businessfilters for eOpposite
				// End of user code
			}
			if (isAccessible(EcoreViewsRepository.EReference.Properties.eKeys)) {
				basePart.addFilterToEKeys(new EObjectFilter(EcorePackage.Literals.EATTRIBUTE));
				// Start of user code for additional businessfilters for eKeys
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
		if (editorKey == EcoreViewsRepository.EReference.Properties.name) {
			return EcorePackage.eINSTANCE.getENamedElement_Name();
		}
		if (editorKey == EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered) {
			return EcorePackage.eINSTANCE.getETypedElement_Ordered();
		}
		if (editorKey == EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique) {
			return EcorePackage.eINSTANCE.getETypedElement_Unique();
		}
		if (editorKey == EcoreViewsRepository.EReference.Cardinality.lowerBound) {
			return EcorePackage.eINSTANCE.getETypedElement_LowerBound();
		}
		if (editorKey == EcoreViewsRepository.EReference.Cardinality.upperBound) {
			return EcorePackage.eINSTANCE.getETypedElement_UpperBound();
		}
		if (editorKey == EcoreViewsRepository.EReference.Properties.eType) {
			return EcorePackage.eINSTANCE.getETypedElement_EType();
		}
		if (editorKey == EcoreViewsRepository.EReference.Behavior.Changeability.changeable) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_Changeable();
		}
		if (editorKey == EcoreViewsRepository.EReference.Behavior.Others.volatile_) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_Volatile();
		}
		if (editorKey == EcoreViewsRepository.EReference.Behavior.Others.transient_) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_Transient();
		}
		if (editorKey == EcoreViewsRepository.EReference.Properties.defaultValueLiteral) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_DefaultValueLiteral();
		}
		if (editorKey == EcoreViewsRepository.EReference.Behavior.Changeability.unsettable) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_Unsettable();
		}
		if (editorKey == EcoreViewsRepository.EReference.Behavior.Changeability.derived) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_Derived();
		}
		if (editorKey == EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment) {
			return EcorePackage.eINSTANCE.getEReference_Containment();
		}
		if (editorKey == EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies) {
			return EcorePackage.eINSTANCE.getEReference_ResolveProxies();
		}
		if (editorKey == EcoreViewsRepository.EReference.Properties.eOpposite) {
			return EcorePackage.eINSTANCE.getEReference_EOpposite();
		}
		if (editorKey == EcoreViewsRepository.EReference.Properties.eKeys) {
			return EcorePackage.eINSTANCE.getEReference_EKeys();
		}
		return super.associatedFeature(editorKey);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updateSemanticModel(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void updateSemanticModel(final IPropertiesEditionEvent event) {
		EReference eReference = (EReference)semanticObject;
		if (EcoreViewsRepository.EReference.Properties.name == event.getAffectedEditor()) {
			eReference.setName((java.lang.String)EEFConverterUtil.createFromString(EcorePackage.Literals.ESTRING, (String)event.getNewValue()));
		}
		if (EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered == event.getAffectedEditor()) {
			eReference.setOrdered((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique == event.getAffectedEditor()) {
			eReference.setUnique((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EReference.Cardinality.lowerBound == event.getAffectedEditor()) {
			eReference.setLowerBound((EEFConverterUtil.createIntFromString(EcorePackage.Literals.EINT, (String)event.getNewValue())));
		}
		if (EcoreViewsRepository.EReference.Cardinality.upperBound == event.getAffectedEditor()) {
			eReference.setUpperBound((EEFConverterUtil.createIntFromString(EcorePackage.Literals.EINT, (String)event.getNewValue())));
		}
		if (EcoreViewsRepository.EReference.Properties.eType == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.SET) {
				eTypeSettings.setToReference((EClassifier)event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.ADD) {
				EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, eTypeSettings, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(semanticObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy instanceof CreateEditingPolicy) {
						policy.execute();
					}
				}
			}
		}
		if (EcoreViewsRepository.EReference.Behavior.Changeability.changeable == event.getAffectedEditor()) {
			eReference.setChangeable((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EReference.Behavior.Others.volatile_ == event.getAffectedEditor()) {
			eReference.setVolatile((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EReference.Behavior.Others.transient_ == event.getAffectedEditor()) {
			eReference.setTransient((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EReference.Properties.defaultValueLiteral == event.getAffectedEditor()) {
			eReference.setDefaultValueLiteral((java.lang.String)EEFConverterUtil.createFromString(EcorePackage.Literals.ESTRING, (String)event.getNewValue()));
		}
		if (EcoreViewsRepository.EReference.Behavior.Changeability.unsettable == event.getAffectedEditor()) {
			eReference.setUnsettable((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EReference.Behavior.Changeability.derived == event.getAffectedEditor()) {
			eReference.setDerived((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment == event.getAffectedEditor()) {
			eReference.setContainment((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies == event.getAffectedEditor()) {
			eReference.setResolveProxies((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EReference.Properties.eOpposite == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.SET) {
				eOppositeSettings.setToReference((EReference)event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.ADD) {
				EReference eObject = EcoreFactory.eINSTANCE.createEReference();
				EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(editingContext, this, eObject, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(eObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy != null) {
						policy.execute();
					}
				}
				eOppositeSettings.setToReference(eObject);
			}
		}
		if (EcoreViewsRepository.EReference.Properties.eKeys == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.ADD) {
				if (event.getNewValue() instanceof EAttribute) {
					eKeysSettings.addToReference((EObject) event.getNewValue());
				}
			} else if (event.getKind() == PropertiesEditionEvent.REMOVE) {
				eKeysSettings.removeFromReference((EObject) event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.MOVE) {
				eKeysSettings.move(event.getNewIndex(), (EAttribute) event.getNewValue());
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
			EReferencePropertiesEditionPart basePart = (EReferencePropertiesEditionPart)editingPart;
			if (EcorePackage.eINSTANCE.getENamedElement_Name().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Properties.name)) {
				if (msg.getNewValue() != null) {
					basePart.setName(EcoreUtil.convertToString(EcorePackage.Literals.ESTRING, msg.getNewValue()));
				} else {
					basePart.setName("");
				}
			}
			if (EcorePackage.eINSTANCE.getETypedElement_Ordered().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered))
				basePart.setOrdered((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getETypedElement_Unique().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique))
				basePart.setUnique((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getETypedElement_LowerBound().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Cardinality.lowerBound)) {
				if (msg.getNewValue() != null) {
					basePart.setLowerBound(EcoreUtil.convertToString(EcorePackage.Literals.EINT, msg.getNewValue()));
				} else {
					basePart.setLowerBound("");
				}
			}
			if (EcorePackage.eINSTANCE.getETypedElement_UpperBound().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Cardinality.upperBound)) {
				if (msg.getNewValue() != null) {
					basePart.setUpperBound(EcoreUtil.convertToString(EcorePackage.Literals.EINT, msg.getNewValue()));
				} else {
					basePart.setUpperBound("");
				}
			}
			if (EcorePackage.eINSTANCE.getETypedElement_EType().equals(msg.getFeature()) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Properties.eType))
				basePart.setEType((EObject)msg.getNewValue());
			if (EcorePackage.eINSTANCE.getEStructuralFeature_Changeable().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Behavior.Changeability.changeable))
				basePart.setChangeable((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEStructuralFeature_Volatile().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Behavior.Others.volatile_))
				basePart.setVolatile_((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEStructuralFeature_Transient().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Behavior.Others.transient_))
				basePart.setTransient_((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEStructuralFeature_DefaultValueLiteral().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Properties.defaultValueLiteral)) {
				if (msg.getNewValue() != null) {
					basePart.setDefaultValueLiteral(EcoreUtil.convertToString(EcorePackage.Literals.ESTRING, msg.getNewValue()));
				} else {
					basePart.setDefaultValueLiteral("");
				}
			}
			if (EcorePackage.eINSTANCE.getEStructuralFeature_Unsettable().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Behavior.Changeability.unsettable))
				basePart.setUnsettable((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEStructuralFeature_Derived().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Behavior.Changeability.derived))
				basePart.setDerived((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEReference_Containment().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment))
				basePart.setContainment((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEReference_ResolveProxies().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies))
				basePart.setResolveProxies((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEReference_EOpposite().equals(msg.getFeature()) && basePart != null && isAccessible(EcoreViewsRepository.EReference.Properties.eOpposite))
				basePart.setEOpposite((EObject)msg.getNewValue());
			if (EcorePackage.eINSTANCE.getEReference_EKeys().equals(msg.getFeature())  && isAccessible(EcoreViewsRepository.EReference.Properties.eKeys))
				basePart.updateEKeys();
			
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
			EcorePackage.eINSTANCE.getETypedElement_Ordered(),
			EcorePackage.eINSTANCE.getETypedElement_Unique(),
			EcorePackage.eINSTANCE.getETypedElement_LowerBound(),
			EcorePackage.eINSTANCE.getETypedElement_UpperBound(),
			EcorePackage.eINSTANCE.getETypedElement_EType(),
			EcorePackage.eINSTANCE.getEStructuralFeature_Changeable(),
			EcorePackage.eINSTANCE.getEStructuralFeature_Volatile(),
			EcorePackage.eINSTANCE.getEStructuralFeature_Transient(),
			EcorePackage.eINSTANCE.getEStructuralFeature_DefaultValueLiteral(),
			EcorePackage.eINSTANCE.getEStructuralFeature_Unsettable(),
			EcorePackage.eINSTANCE.getEStructuralFeature_Derived(),
			EcorePackage.eINSTANCE.getEReference_Containment(),
			EcorePackage.eINSTANCE.getEReference_ResolveProxies(),
			EcorePackage.eINSTANCE.getEReference_EOpposite(),
			EcorePackage.eINSTANCE.getEReference_EKeys()		);
		return new NotificationFilter[] {filter,};
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.Object, int)
	 * 
	 */
	public String getHelpContent(Object key, int kind) {
		if (key == EcoreViewsRepository.EReference.Properties.name)
			return "The name of this model element"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered)
			return "Whether the order in which values occur is meaningful"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique)
			return "Whether the same value may occur more than once"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Cardinality.lowerBound)
			return "The minimum number of values that must occur"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Cardinality.upperBound)
			return "The maximum number of values that may occur; -1 represents unbounded and -2 represents unspecified"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Properties.eType)
			return "The type of this element"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Behavior.Changeability.changeable)
			return "Whether the value of this feature can be changed"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Behavior.Others.volatile_)
			return "Whether no field will be generated for this feature"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Behavior.Others.transient_)
			return "Whether the value of this feature will be serialized"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Properties.defaultValueLiteral)
			return "The literal representation of the default value for this feature"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Behavior.Changeability.unsettable)
			return "Whether the value space for this feature includes the state of not being set"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Behavior.Changeability.derived)
			return "Whether the value of this feature is derived from the values of other features"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment)
			return "Whether this reference represents a composite relationship"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies)
			return "Whether this reference resolves proxies automatically"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Properties.eOpposite)
			return "The reference that represents the bidirectional opposite of this reference"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EReference.Properties.eKeys)
			return "The attributes of the referenced class that uniquely identify a referenced instance"; //$NON-NLS-1$
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
				if (EcoreViewsRepository.EReference.Properties.name == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getENamedElement_Name().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getENamedElement_Name().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getETypedElement_Ordered().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getETypedElement_Ordered().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getETypedElement_Unique().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getETypedElement_Unique().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Cardinality.lowerBound == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getETypedElement_LowerBound().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getETypedElement_LowerBound().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Cardinality.upperBound == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getETypedElement_UpperBound().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getETypedElement_UpperBound().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Behavior.Changeability.changeable == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_Changeable().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_Changeable().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Behavior.Others.volatile_ == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_Volatile().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_Volatile().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Behavior.Others.transient_ == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_Transient().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_Transient().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Properties.defaultValueLiteral == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_DefaultValueLiteral().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_DefaultValueLiteral().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Behavior.Changeability.unsettable == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_Unsettable().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_Unsettable().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Behavior.Changeability.derived == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_Derived().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_Derived().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEReference_Containment().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEReference_Containment().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEReference_ResolveProxies().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEReference_ResolveProxies().getEAttributeType(), newValue);
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
