/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.ecore.components;

// Start of user code for imports
import java.util.Map.Entry;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart;
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
public class EAnnotationPropertiesEditionComponent extends SinglePartPropertiesEditingComponent {

	
	public static String BASE_PART = "Base"; //$NON-NLS-1$

	
	/**
	 * Settings for details ReferencesTable
	 */
	protected ReferencesTableSettings detailsSettings;
	
	/**
	 * Settings for eModelElement EObjectFlatComboViewer
	 */
	private EObjectFlatComboSettings eModelElementSettings;
	
	/**
	 * Settings for contents ReferencesTable
	 */
	protected ReferencesTableSettings contentsSettings;
	
	/**
	 * Settings for references ReferencesTable
	 */
	private ReferencesTableSettings referencesSettings;
	
	
	/**
	 * Default constructor
	 * 
	 */
	public EAnnotationPropertiesEditionComponent(PropertiesEditingContext editingContext, EObject eAnnotation, String editing_mode) {
		super(editingContext, eAnnotation, editing_mode);
		parts = new String[] { BASE_PART };
		repositoryKey = EcoreViewsRepository.class;
		partKey = EcoreViewsRepository.EAnnotation.class;
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
			
			final EAnnotation eAnnotation = (EAnnotation)elt;
			final EAnnotationPropertiesEditionPart basePart = (EAnnotationPropertiesEditionPart)editingPart;
			// init values
			if (isAccessible(EcoreViewsRepository.EAnnotation.Properties.source))
				basePart.setSource(EEFConverterUtil.convertToString(EcorePackage.Literals.ESTRING, eAnnotation.getSource()));
			
			if (isAccessible(EcoreViewsRepository.EAnnotation.Properties.details)) {
				detailsSettings = new ReferencesTableSettings(eAnnotation, EcorePackage.eINSTANCE.getEAnnotation_Details());
				basePart.initDetails(detailsSettings);
			}
			if (isAccessible(EcoreViewsRepository.EAnnotation.Properties.eModelElement)) {
				// init part
				eModelElementSettings = new EObjectFlatComboSettings(eAnnotation, EcorePackage.eINSTANCE.getEAnnotation_EModelElement());
				basePart.initEModelElement(eModelElementSettings);
				// set the button mode
				basePart.setEModelElementButtonMode(ButtonsModeEnum.BROWSE);
			}
			if (isAccessible(EcoreViewsRepository.EAnnotation.Properties.contents)) {
				contentsSettings = new ReferencesTableSettings(eAnnotation, EcorePackage.eINSTANCE.getEAnnotation_Contents());
				basePart.initContents(contentsSettings);
			}
			if (isAccessible(EcoreViewsRepository.EAnnotation.Properties.references)) {
				referencesSettings = new ReferencesTableSettings(eAnnotation, EcorePackage.eINSTANCE.getEAnnotation_References());
				basePart.initReferences(referencesSettings);
			}
			// init filters
			
			if (isAccessible(EcoreViewsRepository.EAnnotation.Properties.details)) {
				basePart.addFilterToDetails(new ViewerFilter() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof Entry); //$NON-NLS-1$ 
					}
			
				});
				// Start of user code for additional businessfilters for details
				// End of user code
			}
			if (isAccessible(EcoreViewsRepository.EAnnotation.Properties.eModelElement)) {
				basePart.addFilterToEModelElement(new ViewerFilter() {
				
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof EModelElement); //$NON-NLS-1$ 
					}
					
				});
				// Start of user code for additional businessfilters for eModelElement
				// End of user code
			}
			if (isAccessible(EcoreViewsRepository.EAnnotation.Properties.contents)) {
				basePart.addFilterToContents(new ViewerFilter() {
					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof EObject); //$NON-NLS-1$ 
					}
			
				});
				// Start of user code for additional businessfilters for contents
				// End of user code
			}
			if (isAccessible(EcoreViewsRepository.EAnnotation.Properties.references)) {
				basePart.addFilterToReferences(new EObjectFilter(EcorePackage.Literals.EOBJECT));
				// Start of user code for additional businessfilters for references
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
		if (editorKey == EcoreViewsRepository.EAnnotation.Properties.source) {
			return EcorePackage.eINSTANCE.getEAnnotation_Source();
		}
		if (editorKey == EcoreViewsRepository.EAnnotation.Properties.details) {
			return EcorePackage.eINSTANCE.getEAnnotation_Details();
		}
		if (editorKey == EcoreViewsRepository.EAnnotation.Properties.eModelElement) {
			return EcorePackage.eINSTANCE.getEAnnotation_EModelElement();
		}
		if (editorKey == EcoreViewsRepository.EAnnotation.Properties.contents) {
			return EcorePackage.eINSTANCE.getEAnnotation_Contents();
		}
		if (editorKey == EcoreViewsRepository.EAnnotation.Properties.references) {
			return EcorePackage.eINSTANCE.getEAnnotation_References();
		}
		return super.associatedFeature(editorKey);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updateSemanticModel(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void updateSemanticModel(final IPropertiesEditionEvent event) {
		EAnnotation eAnnotation = (EAnnotation)semanticObject;
		if (EcoreViewsRepository.EAnnotation.Properties.source == event.getAffectedEditor()) {
			eAnnotation.setSource((java.lang.String)EEFConverterUtil.createFromString(EcorePackage.Literals.ESTRING, (String)event.getNewValue()));
		}
		if (EcoreViewsRepository.EAnnotation.Properties.details == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.ADD) {
				EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, detailsSettings, editingContext.getAdapterFactory());
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
				detailsSettings.removeFromReference((EObject) event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.MOVE) {
//				detailsSettings.move(event.getNewIndex(), (Entry) event.getNewValue());
			}
		}
		if (EcoreViewsRepository.EAnnotation.Properties.eModelElement == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.SET) {
				eModelElementSettings.setToReference((EModelElement)event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.ADD) {
				EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, eModelElementSettings, editingContext.getAdapterFactory());
				PropertiesEditingProvider provider = (PropertiesEditingProvider)editingContext.getAdapterFactory().adapt(semanticObject, PropertiesEditingProvider.class);
				if (provider != null) {
					PropertiesEditingPolicy policy = provider.getPolicy(context);
					if (policy instanceof CreateEditingPolicy) {
						policy.execute();
					}
				}
			}
		}
		if (EcoreViewsRepository.EAnnotation.Properties.contents == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.ADD) {
				EReferencePropertiesEditionContext context = new EReferencePropertiesEditionContext(editingContext, this, contentsSettings, editingContext.getAdapterFactory());
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
				contentsSettings.removeFromReference((EObject) event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.MOVE) {
				contentsSettings.move(event.getNewIndex(), (EObject) event.getNewValue());
			}
		}
		if (EcoreViewsRepository.EAnnotation.Properties.references == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.ADD) {
				if (event.getNewValue() instanceof EObject) {
					referencesSettings.addToReference((EObject) event.getNewValue());
				}
			} else if (event.getKind() == PropertiesEditionEvent.REMOVE) {
				referencesSettings.removeFromReference((EObject) event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.MOVE) {
				referencesSettings.move(event.getNewIndex(), (EObject) event.getNewValue());
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
			EAnnotationPropertiesEditionPart basePart = (EAnnotationPropertiesEditionPart)editingPart;
			if (EcorePackage.eINSTANCE.getEAnnotation_Source().equals(msg.getFeature()) && msg.getNotifier().equals(semanticObject) && basePart != null && isAccessible(EcoreViewsRepository.EAnnotation.Properties.source)) {
				if (msg.getNewValue() != null) {
					basePart.setSource(EcoreUtil.convertToString(EcorePackage.Literals.ESTRING, msg.getNewValue()));
				} else {
					basePart.setSource("");
				}
			}
			if (EcorePackage.eINSTANCE.getEAnnotation_Details().equals(msg.getFeature()) && isAccessible(EcoreViewsRepository.EAnnotation.Properties.details))
				basePart.updateDetails();
			if (EcorePackage.eINSTANCE.getEAnnotation_EModelElement().equals(msg.getFeature()) && basePart != null && isAccessible(EcoreViewsRepository.EAnnotation.Properties.eModelElement))
				basePart.setEModelElement((EObject)msg.getNewValue());
			if (EcorePackage.eINSTANCE.getEAnnotation_Contents().equals(msg.getFeature()) && isAccessible(EcoreViewsRepository.EAnnotation.Properties.contents))
				basePart.updateContents();
			if (EcorePackage.eINSTANCE.getEAnnotation_References().equals(msg.getFeature())  && isAccessible(EcoreViewsRepository.EAnnotation.Properties.references))
				basePart.updateReferences();
			
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
			EcorePackage.eINSTANCE.getEAnnotation_Source(),
			EcorePackage.eINSTANCE.getEAnnotation_Details(),
			EcorePackage.eINSTANCE.getEAnnotation_EModelElement(),
			EcorePackage.eINSTANCE.getEAnnotation_Contents(),
			EcorePackage.eINSTANCE.getEAnnotation_References()		);
		return new NotificationFilter[] {filter,};
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.Object, int)
	 * 
	 */
	public String getHelpContent(Object key, int kind) {
		if (key == EcoreViewsRepository.EAnnotation.Properties.source)
			return "An identifier, typically an absolute URI, that uniquely identifies this kind of annotation"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.EAnnotation.Properties.references)
			return "Objects referenced by this annotation"; //$NON-NLS-1$
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
				if (EcoreViewsRepository.EAnnotation.Properties.source == event.getAffectedEditor()) {
					Object newValue = event.getNewValue();
					if (newValue instanceof String) {
						newValue = EEFConverterUtil.createFromString(EcorePackage.eINSTANCE.getEAnnotation_Source().getEAttributeType(), (String)newValue);
					}
					ret = Diagnostician.INSTANCE.validate(EcorePackage.eINSTANCE.getEAnnotation_Source().getEAttributeType(), newValue);
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
