/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.components;

// Start of user code for imports
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.ecoretools.design.properties.parts.ExceptionsPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.notify.EStructuralFeatureNotificationFilter;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.notify.NotificationFilter;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.impl.components.SinglePartPropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.utils.EEFConverterUtil;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings;


// End of user code

/**
 * 
 * 
 */
public class EOperationExceptionsPropertiesEditionComponent extends SinglePartPropertiesEditingComponent {

	
	public static String EXCEPTIONS_PART = "Exceptions"; //$NON-NLS-1$

	
	/**
	 * Settings for eExceptions ReferencesTable
	 */
	private ReferencesTableSettings eExceptionsSettings;
	
	
	/**
	 * Default constructor
	 * 
	 */
	public EOperationExceptionsPropertiesEditionComponent(PropertiesEditingContext editingContext, EObject eOperation, String editing_mode) {
		super(editingContext, eOperation, editing_mode);
		parts = new String[] { EXCEPTIONS_PART };
		repositoryKey = EcoreViewsRepository.class;
		partKey = EcoreViewsRepository.Exceptions.class;
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
			
			final EOperation eOperation = (EOperation)elt;
			final ExceptionsPropertiesEditionPart exceptionsPart = (ExceptionsPropertiesEditionPart)editingPart;
			// init values
			if (isAccessible(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions)) {
				eExceptionsSettings = new ReferencesTableSettings(eOperation, EcorePackage.eINSTANCE.getEOperation_EExceptions());
				exceptionsPart.initEExceptions(eExceptionsSettings);
			}
			// init filters
			if (isAccessible(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions)) {
				exceptionsPart.addFilterToEExceptions(new EObjectFilter(EcorePackage.Literals.ECLASSIFIER));
				// Start of user code for additional businessfilters for eExceptions
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
		if (editorKey == EcoreViewsRepository.Exceptions.Exceptions_.eExceptions) {
			return EcorePackage.eINSTANCE.getEOperation_EExceptions();
		}
		return super.associatedFeature(editorKey);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#updateSemanticModel(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void updateSemanticModel(final IPropertiesEditionEvent event) {
		EOperation eOperation = (EOperation)semanticObject;
		if (EcoreViewsRepository.Exceptions.Exceptions_.eExceptions == event.getAffectedEditor()) {
			if (event.getKind() == PropertiesEditionEvent.ADD) {
				if (event.getNewValue() instanceof EClassifier) {
					eExceptionsSettings.addToReference((EObject) event.getNewValue());
				}
			} else if (event.getKind() == PropertiesEditionEvent.REMOVE) {
				eExceptionsSettings.removeFromReference((EObject) event.getNewValue());
			} else if (event.getKind() == PropertiesEditionEvent.MOVE) {
				eExceptionsSettings.move(event.getNewIndex(), (EClassifier) event.getNewValue());
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
			ExceptionsPropertiesEditionPart exceptionsPart = (ExceptionsPropertiesEditionPart)editingPart;
			if (EcorePackage.eINSTANCE.getEOperation_EExceptions().equals(msg.getFeature())  && isAccessible(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions))
				exceptionsPart.updateEExceptions();
			
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
			EcorePackage.eINSTANCE.getEOperation_EExceptions()		);
		return new NotificationFilter[] {filter,};
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.Object, int)
	 * 
	 */
	public String getHelpContent(Object key, int kind) {
		if (key == EcoreViewsRepository.OperationElement.Properties.name)
			return "The name of this model element"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.OperationElement.Properties.ordered)
			return "Whether the order in which values occur is meaningful"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.OperationElement.Properties.unique)
			return "Whether the same value may occur more than once"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.OperationElement.Cardinality.lowerBound)
			return "The minimum number of values that must occur"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.OperationElement.Cardinality.upperBound)
			return "The maximum number of values that may occur; -1 represents unbounded and -2 represents unspecified"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.OperationElement.Properties.eType)
			return "The type of this element"; //$NON-NLS-1$
		if (key == EcoreViewsRepository.Exceptions.Exceptions_.eExceptions)
			return "The exceptions thrown by this operation"; //$NON-NLS-1$
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
			} catch (IllegalArgumentException iae) {
				ret = BasicDiagnostic.toDiagnostic(iae);
			} catch (WrappedException we) {
				ret = BasicDiagnostic.toDiagnostic(we);
			}
		}
		return ret;
	}


	

}
