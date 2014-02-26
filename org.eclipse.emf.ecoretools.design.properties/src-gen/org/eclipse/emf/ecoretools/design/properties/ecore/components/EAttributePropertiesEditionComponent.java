/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.ecore.components;

// Start of user code for imports
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.eef.runtime.api.notify.EStructuralFeatureNotificationFilter;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.notify.NotificationFilter;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EReferencePropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.components.SinglePartPropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.utils.EEFConverterUtil;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.impl.CreateEditingPolicy;
import org.eclipse.emf.eef.runtime.providers.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;


// End of user code

/**
 * 
 * 
 */
public class EAttributePropertiesEditionComponent extends SinglePartPropertiesEditingComponent {

	
	public static String BASE_PART = "Base"; //$NON-NLS-1$

	
	/**
	 * Settings for eType EObjectFlatComboViewer
	 */
	private EObjectFlatComboSettings eTypeSettings;
	
	
	/**
	 * Default constructor
	 * 
	 */
	public EAttributePropertiesEditionComponent(PropertiesEditingContext editingContext, EObject eAttribute, String editing_mode) {
		super(editingContext, eAttribute, editing_mode);
		parts = new String[] { BASE_PART };
		repositoryKey = EcoreViewsRepository.class;
		partKey = EcoreViewsRepository.EAttribute.class;
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
			
			final EAttribute eAttribute = (EAttribute)elt;
			final EAttributePropertiesEditionPart basePart = (EAttributePropertiesEditionPart)editingPart;
			// init values
			if (isAccessible(EcoreViewsRepository.EAttribute.Properties.name))
				basePart.setName(EEFConverterUtil.convertToString(EcorePackage.Literals.ESTRING, eAttribute.getName()));
			
			if (isAccessible(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered)) {
				basePart.setOrdered(eAttribute.isOrdered());
			}
			if (isAccessible(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique)) {
				basePart.setUnique(eAttribute.isUnique());
			}
			if (isAccessible(EcoreViewsRepository.EAttribute.Cardinality.lowerBound)) {
				basePart.setLowerBound(EEFConverterUtil.convertToString(EcorePackage.Literals.EINT, eAttribute.getLowerBound()));
			}
			
			if (isAccessible(EcoreViewsRepository.EAttribute.Cardinality.upperBound)) {
				basePart.setUpperBound(EEFConverterUtil.convertToString(EcorePackage.Literals.EINT, eAttribute.getUpperBound()));
			}
			
			if (isAccessible(EcoreViewsRepository.EAttribute.Properties.eType)) {
				// init part
				eTypeSettings = new EObjectFlatComboSettings(eAttribute, EcorePackage.eINSTANCE.getETypedElement_EType());
				basePart.initEType(eTypeSettings);
				// set the button mode
				basePart.setETypeButtonMode(ButtonsModeEnum.BROWSE);
			}
			if (isAccessible(EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable)) {
				basePart.setChangeable(eAttribute.isChangeable());
			}
			if (isAccessible(EcoreViewsRepository.EAttribute.Behavior.Others.volatile_)) {
				basePart.setVolatile_(eAttribute.isVolatile());
			}
			if (isAccessible(EcoreViewsRepository.EAttribute.Behavior.Others.transient_)) {
				basePart.setTransient_(eAttribute.isTransient());
			}
			if (isAccessible(EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral))
				basePart.setDefaultValueLiteral(EEFConverterUtil.convertToString(EcorePackage.Literals.ESTRING, eAttribute.getDefaultValueLiteral()));
			
			if (isAccessible(EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable)) {
				basePart.setUnsettable(eAttribute.isUnsettable());
			}
			if (isAccessible(EcoreViewsRepository.EAttribute.Behavior.Changeability.derived)) {
				basePart.setDerived(eAttribute.isDerived());
			}
			if (isAccessible(EcoreViewsRepository.EAttribute.Properties.iD)) {
				basePart.setID(eAttribute.isID());
			}
			// init filters
			
			
			
			
			
			if (isAccessible(EcoreViewsRepository.EAttribute.Properties.eType)) {
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
		if (editorKey == EcoreViewsRepository.EAttribute.Properties.name) {
			return EcorePackage.eINSTANCE.getENamedElement_Name();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered) {
			return EcorePackage.eINSTANCE.getETypedElement_Ordered();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique) {
			return EcorePackage.eINSTANCE.getETypedElement_Unique();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Cardinality.lowerBound) {
			return EcorePackage.eINSTANCE.getETypedElement_LowerBound();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Cardinality.upperBound) {
			return EcorePackage.eINSTANCE.getETypedElement_UpperBound();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Properties.eType) {
			return EcorePackage.eINSTANCE.getETypedElement_EType();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_Changeable();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Behavior.Others.volatile_) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_Volatile();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Behavior.Others.transient_) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_Transient();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_DefaultValueLiteral();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_Unsettable();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Behavior.Changeability.derived) {
			return EcorePackage.eINSTANCE.getEStructuralFeature_Derived();
		}
		if (editorKey == EcoreViewsRepository.EAttribute.Properties.iD) {
			return EcorePackage.eINSTANCE.getEAttribute_ID();
		}
		return super.associatedFeature(editorKey);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updateSemanticModel(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void updateSemanticModel(final IPropertiesEditionEvent event) {
		EAttribute eAttribute = (EAttribute)semanticObject;
		if (EcoreViewsRepository.EAttribute.Properties.name == event.getAffectedEditor()) {
			eAttribute.setName((java.lang.String)EEFConverterUtil.createFromString(EcorePackage.Literals.ESTRING, (String)event.getNewValue()));
		}
		if (EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered == event.getAffectedEditor()) {
			eAttribute.setOrdered((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique == event.getAffectedEditor()) {
			eAttribute.setUnique((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EAttribute.Cardinality.lowerBound == event.getAffectedEditor()) {
			eAttribute.setLowerBound((EEFConverterUtil.createIntFromString(EcorePackage.Literals.EINT, (String)event.getNewValue())));
		}
		if (EcoreViewsRepository.EAttribute.Cardinality.upperBound == event.getAffectedEditor()) {
			eAttribute.setUpperBound((EEFConverterUtil.createIntFromString(EcorePackage.Literals.EINT, (String)event.getNewValue())));
		}
		if (EcoreViewsRepository.EAttribute.Properties.eType == event.getAffectedEditor()) {
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
		if (EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable == event.getAffectedEditor()) {
			eAttribute.setChangeable((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EAttribute.Behavior.Others.volatile_ == event.getAffectedEditor()) {
			eAttribute.setVolatile((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EAttribute.Behavior.Others.transient_ == event.getAffectedEditor()) {
			eAttribute.setTransient((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral == event.getAffectedEditor()) {
			eAttribute.setDefaultValueLiteral((java.lang.String)EEFConverterUtil.createFromString(EcorePackage.Literals.ESTRING, (String)event.getNewValue()));
		}
		if (EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable == event.getAffectedEditor()) {
			eAttribute.setUnsettable((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EAttribute.Behavior.Changeability.derived == event.getAffectedEditor()) {
			eAttribute.setDerived((Boolean)event.getNewValue());
		}
		if (EcoreViewsRepository.EAttribute.Properties.iD == event.getAffectedEditor()) {
			eAttribute.setID((Boolean)event.getNewValue());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updatePart(org.eclipse.emf.common.notify.Notification)
	 */
	public void updatePart(Notification msg) {
		super.updatePart(msg);
		if (editingPart.isVisible()) {
			EAttributePropertiesEditionPart basePart = (EAttributePropertiesEditionPart)editingPart;
			if (EcorePackage.eINSTANCE.getENamedElement_Name().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Properties.name)) {
				if (msg.getNewValue() != null) {
					basePart.setName(EcoreUtil.convertToString(EcorePackage.Literals.ESTRING, msg.getNewValue()));
				} else {
					basePart.setName("");
				}
			}
			if (EcorePackage.eINSTANCE.getETypedElement_Ordered().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered))
				basePart.setOrdered((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getETypedElement_Unique().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique))
				basePart.setUnique((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getETypedElement_LowerBound().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Cardinality.lowerBound)) {
				if (msg.getNewValue() != null) {
					basePart.setLowerBound(EcoreUtil.convertToString(EcorePackage.Literals.EINT, msg.getNewValue()));
				} else {
					basePart.setLowerBound("");
				}
			}
			if (EcorePackage.eINSTANCE.getETypedElement_UpperBound().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Cardinality.upperBound)) {
				if (msg.getNewValue() != null) {
					basePart.setUpperBound(EcoreUtil.convertToString(EcorePackage.Literals.EINT, msg.getNewValue()));
				} else {
					basePart.setUpperBound("");
				}
			}
			if (EcorePackage.eINSTANCE.getETypedElement_EType().equals(msg.getFeature()) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Properties.eType))
				basePart.setEType((EObject)msg.getNewValue());
			if (EcorePackage.eINSTANCE.getEStructuralFeature_Changeable().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable))
				basePart.setChangeable((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEStructuralFeature_Volatile().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Behavior.Others.volatile_))
				basePart.setVolatile_((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEStructuralFeature_Transient().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Behavior.Others.transient_))
				basePart.setTransient_((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEStructuralFeature_DefaultValueLiteral().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral)) {
				if (msg.getNewValue() != null) {
					basePart.setDefaultValueLiteral(EcoreUtil.convertToString(EcorePackage.Literals.ESTRING, msg.getNewValue()));
				} else {
					basePart.setDefaultValueLiteral("");
				}
			}
			if (EcorePackage.eINSTANCE.getEStructuralFeature_Unsettable().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable))
				basePart.setUnsettable((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEStructuralFeature_Derived().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Behavior.Changeability.derived))
				basePart.setDerived((Boolean)msg.getNewValue());
			
			if (EcorePackage.eINSTANCE.getEAttribute_ID().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAttribute.Properties.iD))
				basePart.setID((Boolean)msg.getNewValue());
			
			
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
			EcorePackage.eINSTANCE.getEAttribute_ID()		);
		return new NotificationFilter[] {filter,};
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.Object, int)
	 * 
	 */
	public String getHelpContent(Object key, int kind) {
		if (key == EcoreViewsRepository.EAttribute.Properties.name)
			return "The name of this model element"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered)
			return "Whether the order in which values occur is meaningful"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique)
			return "Whether the same value may occur more than once"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Cardinality.lowerBound)
			return "The minimum number of values that must occur"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Cardinality.upperBound)
			return "The maximum number of values that may occur; -1 represents unbounded and -2 represents unspecified"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Properties.eType)
			return "The type of this element"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable)
			return "Whether the value of this feature can be changed"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Behavior.Others.volatile_)
			return "Whether no field will be generated for this feature"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Behavior.Others.transient_)
			return "Whether the value of this feature will be serialized"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral)
			return "The literal representation of the default value for this feature"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable)
			return "Whether the value space for this feature includes the state of not being set"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Behavior.Changeability.derived)
			return "Whether the value of this feature is derived from the values of other features"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAttribute.Properties.iD)
			return "Whether the value of this attribute uniquely identifies an object within its containing resource"; //$NON-NLS-1$
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
				if (EcoreViewsRepository.EAttribute.Properties.name == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getENamedElement_Name().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getENamedElement_Name().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getETypedElement_Ordered().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getETypedElement_Ordered().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getETypedElement_Unique().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getETypedElement_Unique().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EAttribute.Cardinality.lowerBound == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getETypedElement_LowerBound().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getETypedElement_LowerBound().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EAttribute.Cardinality.upperBound == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getETypedElement_UpperBound().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getETypedElement_UpperBound().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_Changeable().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_Changeable().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EAttribute.Behavior.Others.volatile_ == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_Volatile().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_Volatile().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EAttribute.Behavior.Others.transient_ == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_Transient().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_Transient().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_DefaultValueLiteral().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_DefaultValueLiteral().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_Unsettable().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_Unsettable().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EAttribute.Behavior.Changeability.derived == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEStructuralFeature_Derived().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEStructuralFeature_Derived().getEAttributeType(), newValue);
				}
				if (EcoreViewsRepository.EAttribute.Properties.iD == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEAttribute_ID().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEAttribute_ID().getEAttributeType(), newValue);
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
