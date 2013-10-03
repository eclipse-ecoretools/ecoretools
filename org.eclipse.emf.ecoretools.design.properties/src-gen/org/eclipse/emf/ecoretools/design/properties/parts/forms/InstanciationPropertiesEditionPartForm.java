/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.parts.forms;

// Start of user code for imports
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.ecoretools.design.properties.parts.InstanciationPropertiesEditionPart;
import org.eclipse.emf.ecoretools.design.properties.providers.EcoreMessages;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.part.impl.SectionPropertiesEditingPart;
import org.eclipse.emf.eef.runtime.ui.parts.PartComposer;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.BindingCompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionSequence;
import org.eclipse.emf.eef.runtime.ui.utils.EditingUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.FormUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

// End of user code

/**
 * 
 * 
 */
public class InstanciationPropertiesEditionPartForm extends SectionPropertiesEditingPart implements IFormPropertiesEditionPart, InstanciationPropertiesEditionPart {

	protected Text instanceClassName;
	protected Text instanceTypeName;



	/**
	 * For {@link ISection} use only.
	 */
	public InstanciationPropertiesEditionPartForm() { super(); }

	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public InstanciationPropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
		super(editionComponent);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart#
	 *  createFigure(org.eclipse.swt.widgets.Composite, org.eclipse.ui.forms.widgets.FormToolkit)
	 * 
	 */
	public Composite createFigure(final Composite parent, final FormToolkit widgetFactory) {
		ScrolledForm scrolledForm = widgetFactory.createScrolledForm(parent);
		Form form = scrolledForm.getForm();
		view = form.getBody();
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		view.setLayout(layout);
		createControls(widgetFactory, view);
		return scrolledForm;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart#
	 *  createControls(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 * 
	 */
	public void createControls(final FormToolkit widgetFactory, Composite view) {
		CompositionSequence instanciationStep = new BindingCompositionSequence(propertiesEditionComponent);
		instanciationStep.addStep(EcoreViewsRepository.Instanciation.instanceClassName);
		instanciationStep.addStep(EcoreViewsRepository.Instanciation.instanceTypeName);
		
		composer = new PartComposer(instanciationStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == EcoreViewsRepository.Instanciation.instanceClassName) {
					return createInstanceClassNameText(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.Instanciation.instanceTypeName) {
					return createInstanceTypeNameText(widgetFactory, parent);
				}
				return parent;
			}
		};
		composer.compose(view);
	}
	
	protected Composite createInstanceClassNameText(FormToolkit widgetFactory, Composite parent) {
		createDescription(parent, EcoreViewsRepository.Instanciation.instanceClassName, EcoreMessages.InstanciationPropertiesEditionPart_InstanceClassNameLabel);
		instanceClassName = widgetFactory.createText(parent, ""); //$NON-NLS-1$
		instanceClassName.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		widgetFactory.paintBordersFor(parent);
		GridData instanceClassNameData = new GridData(GridData.FILL_HORIZONTAL);
		instanceClassName.setLayoutData(instanceClassNameData);
		instanceClassName.addFocusListener(new FocusAdapter() {
			/**
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							InstanciationPropertiesEditionPartForm.this,
							EcoreViewsRepository.Instanciation.instanceClassName,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, instanceClassName.getText()));
					propertiesEditionComponent
							.firePropertiesChanged(new PropertiesEditionEvent(
									InstanciationPropertiesEditionPartForm.this,
									EcoreViewsRepository.Instanciation.instanceClassName,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_LOST,
									null, instanceClassName.getText()));
				}
			}

			/**
			 * @see org.eclipse.swt.events.FocusAdapter#focusGained(org.eclipse.swt.events.FocusEvent)
			 */
			@Override
			public void focusGained(FocusEvent e) {
				if (propertiesEditionComponent != null) {
					propertiesEditionComponent
							.firePropertiesChanged(new PropertiesEditionEvent(
									InstanciationPropertiesEditionPartForm.this,
									null,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_GAINED,
									null, null));
				}
			}
		});
		instanceClassName.addKeyListener(new KeyAdapter() {
			/**
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InstanciationPropertiesEditionPartForm.this, EcoreViewsRepository.Instanciation.instanceClassName, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, instanceClassName.getText()));
				}
			}
		});
		EditingUtils.setID(instanceClassName, EcoreViewsRepository.Instanciation.instanceClassName);
		EditingUtils.setEEFtype(instanceClassName, "eef::Text"); //$NON-NLS-1$
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.Instanciation.instanceClassName, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createInstanceClassNameText

		// End of user code
		return parent;
	}

	
	protected Composite createInstanceTypeNameText(FormToolkit widgetFactory, Composite parent) {
		createDescription(parent, EcoreViewsRepository.Instanciation.instanceTypeName, EcoreMessages.InstanciationPropertiesEditionPart_InstanceTypeNameLabel);
		instanceTypeName = widgetFactory.createText(parent, ""); //$NON-NLS-1$
		instanceTypeName.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		widgetFactory.paintBordersFor(parent);
		GridData instanceTypeNameData = new GridData(GridData.FILL_HORIZONTAL);
		instanceTypeName.setLayoutData(instanceTypeNameData);
		instanceTypeName.addFocusListener(new FocusAdapter() {
			/**
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							InstanciationPropertiesEditionPartForm.this,
							EcoreViewsRepository.Instanciation.instanceTypeName,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, instanceTypeName.getText()));
					propertiesEditionComponent
							.firePropertiesChanged(new PropertiesEditionEvent(
									InstanciationPropertiesEditionPartForm.this,
									EcoreViewsRepository.Instanciation.instanceTypeName,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_LOST,
									null, instanceTypeName.getText()));
				}
			}

			/**
			 * @see org.eclipse.swt.events.FocusAdapter#focusGained(org.eclipse.swt.events.FocusEvent)
			 */
			@Override
			public void focusGained(FocusEvent e) {
				if (propertiesEditionComponent != null) {
					propertiesEditionComponent
							.firePropertiesChanged(new PropertiesEditionEvent(
									InstanciationPropertiesEditionPartForm.this,
									null,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_GAINED,
									null, null));
				}
			}
		});
		instanceTypeName.addKeyListener(new KeyAdapter() {
			/**
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InstanciationPropertiesEditionPartForm.this, EcoreViewsRepository.Instanciation.instanceTypeName, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, instanceTypeName.getText()));
				}
			}
		});
		EditingUtils.setID(instanceTypeName, EcoreViewsRepository.Instanciation.instanceTypeName);
		EditingUtils.setEEFtype(instanceTypeName, "eef::Text"); //$NON-NLS-1$
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.Instanciation.instanceTypeName, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createInstanceTypeNameText

		// End of user code
		return parent;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void firePropertiesChanged(IPropertiesEditionEvent event) {
		// Start of user code for tab synchronization
		
		// End of user code
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.InstanciationPropertiesEditionPart#getInstanceClassName()
	 * 
	 */
	public String getInstanceClassName() {
		return instanceClassName.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.InstanciationPropertiesEditionPart#setInstanceClassName(String newValue)
	 * 
	 */
	public void setInstanceClassName(String newValue) {
		if (newValue != null) {
			instanceClassName.setText(newValue);
		} else {
			instanceClassName.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.Instanciation.instanceClassName);
		if (eefElementEditorReadOnlyState && instanceClassName.isEnabled()) {
			instanceClassName.setEnabled(false);
			instanceClassName.setToolTipText(EcoreMessages.Instanciation_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !instanceClassName.isEnabled()) {
			instanceClassName.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.InstanciationPropertiesEditionPart#getInstanceTypeName()
	 * 
	 */
	public String getInstanceTypeName() {
		return instanceTypeName.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.InstanciationPropertiesEditionPart#setInstanceTypeName(String newValue)
	 * 
	 */
	public void setInstanceTypeName(String newValue) {
		if (newValue != null) {
			instanceTypeName.setText(newValue);
		} else {
			instanceTypeName.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.Instanciation.instanceTypeName);
		if (eefElementEditorReadOnlyState && instanceTypeName.isEnabled()) {
			instanceTypeName.setEnabled(false);
			instanceTypeName.setToolTipText(EcoreMessages.Instanciation_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !instanceTypeName.isEnabled()) {
			instanceTypeName.setEnabled(true);
		}	
		
	}






	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart#getTitle()
	 * 
	 */
	public String getTitle() {
		return EcoreMessages.Instanciation_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
