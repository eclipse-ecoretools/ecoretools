/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.parts.forms;

// Start of user code for imports
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart;
import org.eclipse.emf.ecoretools.design.properties.providers.EcoreMessages;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.part.impl.SectionPropertiesEditingPart;
import org.eclipse.emf.eef.runtime.ui.parts.PartComposer;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.BindingCompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionStep;
import org.eclipse.emf.eef.runtime.ui.utils.EditingUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.EObjectFlatComboViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.FormUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

// End of user code

/**
 * 
 * 
 */
public class OperationElementPropertiesEditionPartForm extends SectionPropertiesEditingPart implements IFormPropertiesEditionPart, OperationElementPropertiesEditionPart {

	protected Text name;
	protected EObjectFlatComboViewer eType;
	protected Button ordered;
	protected Button unique;
	protected Text lowerBound;
	protected Text upperBound;



	/**
	 * For {@link ISection} use only.
	 */
	public OperationElementPropertiesEditionPartForm() { super(); }

	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public OperationElementPropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
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
		CompositionSequence operationElementStep = new BindingCompositionSequence(propertiesEditionComponent);
		CompositionStep propertiesStep = operationElementStep.addStep(EcoreViewsRepository.OperationElement.Properties.class);
		propertiesStep.addStep(EcoreViewsRepository.OperationElement.Properties.name);
		propertiesStep.addStep(EcoreViewsRepository.OperationElement.Properties.eType);
		propertiesStep.addStep(EcoreViewsRepository.OperationElement.Properties.ordered);
		propertiesStep.addStep(EcoreViewsRepository.OperationElement.Properties.unique);
		
		CompositionStep cardinalityStep = operationElementStep.addStep(EcoreViewsRepository.OperationElement.Cardinality.class);
		cardinalityStep.addStep(EcoreViewsRepository.OperationElement.Cardinality.lowerBound);
		cardinalityStep.addStep(EcoreViewsRepository.OperationElement.Cardinality.upperBound);
		
		
		composer = new PartComposer(operationElementStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == EcoreViewsRepository.OperationElement.Properties.class) {
					return createPropertiesGroup(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Properties.name) {
					return createNameText(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Properties.eType) {
					return createETypeFlatComboViewer(parent, widgetFactory);
				}
				if (key == EcoreViewsRepository.OperationElement.Properties.ordered) {
					return createOrderedCheckbox(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Properties.unique) {
					return createUniqueCheckbox(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Cardinality.class) {
					return createCardinalityGroup(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Cardinality.lowerBound) {
					return createLowerBoundText(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Cardinality.upperBound) {
					return createUpperBoundText(widgetFactory, parent);
				}
				return parent;
			}
		};
		composer.compose(view);
	}
	/**
	 * 
	 */
	protected Composite createPropertiesGroup(FormToolkit widgetFactory, final Composite parent) {
		Section propertiesSection = widgetFactory.createSection(parent, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		propertiesSection.setText(EcoreMessages.OperationElementPropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesSectionData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesSectionData.horizontalSpan = 3;
		propertiesSection.setLayoutData(propertiesSectionData);
		Composite propertiesGroup = widgetFactory.createComposite(propertiesSection);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		propertiesSection.setClient(propertiesGroup);
		return propertiesGroup;
	}

	
	protected Composite createNameText(FormToolkit widgetFactory, Composite parent) {
		createDescription(parent, EcoreViewsRepository.OperationElement.Properties.name, EcoreMessages.OperationElementPropertiesEditionPart_NameLabel);
		name = widgetFactory.createText(parent, ""); //$NON-NLS-1$
		name.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		widgetFactory.paintBordersFor(parent);
		GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(nameData);
		name.addFocusListener(new FocusAdapter() {
			/**
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							OperationElementPropertiesEditionPartForm.this,
							EcoreViewsRepository.OperationElement.Properties.name,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
					propertiesEditionComponent
							.firePropertiesChanged(new PropertiesEditionEvent(
									OperationElementPropertiesEditionPartForm.this,
									EcoreViewsRepository.OperationElement.Properties.name,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_LOST,
									null, name.getText()));
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
									OperationElementPropertiesEditionPartForm.this,
									null,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_GAINED,
									null, null));
				}
			}
		});
		name.addKeyListener(new KeyAdapter() {
			/**
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartForm.this, EcoreViewsRepository.OperationElement.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}
		});
		EditingUtils.setID(name, EcoreViewsRepository.OperationElement.Properties.name);
		EditingUtils.setEEFtype(name, "eef::Text"); //$NON-NLS-1$
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Properties.name, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createNameText

		// End of user code
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * @param widgetFactory factory to use to instanciante widget of the form
	 * 
	 */
	protected Composite createETypeFlatComboViewer(Composite parent, FormToolkit widgetFactory) {
		createDescription(parent, EcoreViewsRepository.OperationElement.Properties.eType, EcoreMessages.OperationElementPropertiesEditionPart_ETypeLabel);
		eType = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(EcoreViewsRepository.OperationElement.Properties.eType, EcoreViewsRepository.FORM_KIND));
		widgetFactory.adapt(eType);
		eType.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		GridData eTypeData = new GridData(GridData.FILL_HORIZONTAL);
		eType.setLayoutData(eTypeData);
		eType.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			public void selectionChanged(SelectionChangedEvent event) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartForm.this, EcoreViewsRepository.OperationElement.Properties.eType, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getEType()));
			}

		});
		eType.setID(EcoreViewsRepository.OperationElement.Properties.eType);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Properties.eType, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createETypeFlatComboViewer

		// End of user code
		return parent;
	}

	
	protected Composite createOrderedCheckbox(FormToolkit widgetFactory, Composite parent) {
		ordered = widgetFactory.createButton(parent, getDescription(EcoreViewsRepository.OperationElement.Properties.ordered, EcoreMessages.OperationElementPropertiesEditionPart_OrderedLabel), SWT.CHECK);
		ordered.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartForm.this, EcoreViewsRepository.OperationElement.Properties.ordered, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(ordered.getSelection())));
			}

		});
		GridData orderedData = new GridData(GridData.FILL_HORIZONTAL);
		orderedData.horizontalSpan = 2;
		ordered.setLayoutData(orderedData);
		EditingUtils.setID(ordered, EcoreViewsRepository.OperationElement.Properties.ordered);
		EditingUtils.setEEFtype(ordered, "eef::Checkbox"); //$NON-NLS-1$
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Properties.ordered, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createOrderedCheckbox

		// End of user code
		return parent;
	}

	
	protected Composite createUniqueCheckbox(FormToolkit widgetFactory, Composite parent) {
		unique = widgetFactory.createButton(parent, getDescription(EcoreViewsRepository.OperationElement.Properties.unique, EcoreMessages.OperationElementPropertiesEditionPart_UniqueLabel), SWT.CHECK);
		unique.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartForm.this, EcoreViewsRepository.OperationElement.Properties.unique, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(unique.getSelection())));
			}

		});
		GridData uniqueData = new GridData(GridData.FILL_HORIZONTAL);
		uniqueData.horizontalSpan = 2;
		unique.setLayoutData(uniqueData);
		EditingUtils.setID(unique, EcoreViewsRepository.OperationElement.Properties.unique);
		EditingUtils.setEEFtype(unique, "eef::Checkbox"); //$NON-NLS-1$
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Properties.unique, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createUniqueCheckbox

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createCardinalityGroup(FormToolkit widgetFactory, final Composite parent) {
		Section cardinalitySection = widgetFactory.createSection(parent, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		cardinalitySection.setText(EcoreMessages.OperationElementPropertiesEditionPart_CardinalityGroupLabel);
		GridData cardinalitySectionData = new GridData(GridData.FILL_HORIZONTAL);
		cardinalitySectionData.horizontalSpan = 3;
		cardinalitySection.setLayoutData(cardinalitySectionData);
		Composite cardinalityGroup = widgetFactory.createComposite(cardinalitySection);
		GridLayout cardinalityGroupLayout = new GridLayout();
		cardinalityGroupLayout.numColumns = 3;
		cardinalityGroup.setLayout(cardinalityGroupLayout);
		cardinalitySection.setClient(cardinalityGroup);
		return cardinalityGroup;
	}

	
	protected Composite createLowerBoundText(FormToolkit widgetFactory, Composite parent) {
		createDescription(parent, EcoreViewsRepository.OperationElement.Cardinality.lowerBound, EcoreMessages.OperationElementPropertiesEditionPart_LowerBoundLabel);
		lowerBound = widgetFactory.createText(parent, ""); //$NON-NLS-1$
		lowerBound.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		widgetFactory.paintBordersFor(parent);
		GridData lowerBoundData = new GridData(GridData.FILL_HORIZONTAL);
		lowerBound.setLayoutData(lowerBoundData);
		lowerBound.addFocusListener(new FocusAdapter() {
			/**
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							OperationElementPropertiesEditionPartForm.this,
							EcoreViewsRepository.OperationElement.Cardinality.lowerBound,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, lowerBound.getText()));
					propertiesEditionComponent
							.firePropertiesChanged(new PropertiesEditionEvent(
									OperationElementPropertiesEditionPartForm.this,
									EcoreViewsRepository.OperationElement.Cardinality.lowerBound,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_LOST,
									null, lowerBound.getText()));
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
									OperationElementPropertiesEditionPartForm.this,
									null,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_GAINED,
									null, null));
				}
			}
		});
		lowerBound.addKeyListener(new KeyAdapter() {
			/**
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartForm.this, EcoreViewsRepository.OperationElement.Cardinality.lowerBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, lowerBound.getText()));
				}
			}
		});
		EditingUtils.setID(lowerBound, EcoreViewsRepository.OperationElement.Cardinality.lowerBound);
		EditingUtils.setEEFtype(lowerBound, "eef::Text"); //$NON-NLS-1$
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Cardinality.lowerBound, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createLowerBoundText

		// End of user code
		return parent;
	}

	
	protected Composite createUpperBoundText(FormToolkit widgetFactory, Composite parent) {
		createDescription(parent, EcoreViewsRepository.OperationElement.Cardinality.upperBound, EcoreMessages.OperationElementPropertiesEditionPart_UpperBoundLabel);
		upperBound = widgetFactory.createText(parent, ""); //$NON-NLS-1$
		upperBound.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		widgetFactory.paintBordersFor(parent);
		GridData upperBoundData = new GridData(GridData.FILL_HORIZONTAL);
		upperBound.setLayoutData(upperBoundData);
		upperBound.addFocusListener(new FocusAdapter() {
			/**
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							OperationElementPropertiesEditionPartForm.this,
							EcoreViewsRepository.OperationElement.Cardinality.upperBound,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, upperBound.getText()));
					propertiesEditionComponent
							.firePropertiesChanged(new PropertiesEditionEvent(
									OperationElementPropertiesEditionPartForm.this,
									EcoreViewsRepository.OperationElement.Cardinality.upperBound,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_LOST,
									null, upperBound.getText()));
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
									OperationElementPropertiesEditionPartForm.this,
									null,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_GAINED,
									null, null));
				}
			}
		});
		upperBound.addKeyListener(new KeyAdapter() {
			/**
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartForm.this, EcoreViewsRepository.OperationElement.Cardinality.upperBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, upperBound.getText()));
				}
			}
		});
		EditingUtils.setID(upperBound, EcoreViewsRepository.OperationElement.Cardinality.upperBound);
		EditingUtils.setEEFtype(upperBound, "eef::Text"); //$NON-NLS-1$
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Cardinality.upperBound, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createUpperBoundText

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
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#getName()
	 * 
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#setName(String newValue)
	 * 
	 */
	public void setName(String newValue) {
		if (newValue != null) {
			name.setText(newValue);
		} else {
			name.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.OperationElement.Properties.name);
		if (eefElementEditorReadOnlyState && name.isEnabled()) {
			name.setEnabled(false);
			name.setToolTipText(EcoreMessages.OperationElement_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !name.isEnabled()) {
			name.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#getEType()
	 * 
	 */
	public EObject getEType() {
		if (eType.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) eType.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#initEType(EObjectFlatComboSettings)
	 */
	public void initEType(EObjectFlatComboSettings settings) {
		eType.setInput(settings);
		if (current != null) {
			eType.setSelection(new StructuredSelection(settings.getValue()));
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.OperationElement.Properties.eType);
		if (eefElementEditorReadOnlyState && eType.isEnabled()) {
			eType.setEnabled(false);
			eType.setToolTipText(EcoreMessages.OperationElement_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eType.isEnabled()) {
			eType.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#setEType(EObject newValue)
	 * 
	 */
	public void setEType(EObject newValue) {
		if (newValue != null) {
			eType.setSelection(new StructuredSelection(newValue));
		} else {
			eType.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.OperationElement.Properties.eType);
		if (eefElementEditorReadOnlyState && eType.isEnabled()) {
			eType.setEnabled(false);
			eType.setToolTipText(EcoreMessages.OperationElement_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eType.isEnabled()) {
			eType.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#setETypeButtonMode(ButtonsModeEnum newValue)
	 */
	public void setETypeButtonMode(ButtonsModeEnum newValue) {
		eType.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#addFilterEType(ViewerFilter filter)
	 * 
	 */
	public void addFilterToEType(ViewerFilter filter) {
		eType.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#addBusinessFilterEType(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToEType(ViewerFilter filter) {
		eType.addBusinessRuleFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#getOrdered()
	 * 
	 */
	public Boolean getOrdered() {
		return Boolean.valueOf(ordered.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#setOrdered(Boolean newValue)
	 * 
	 */
	public void setOrdered(Boolean newValue) {
		if (newValue != null) {
			ordered.setSelection(newValue.booleanValue());
		} else {
			ordered.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.OperationElement.Properties.ordered);
		if (eefElementEditorReadOnlyState && ordered.isEnabled()) {
			ordered.setEnabled(false);
			ordered.setToolTipText(EcoreMessages.OperationElement_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !ordered.isEnabled()) {
			ordered.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#getUnique()
	 * 
	 */
	public Boolean getUnique() {
		return Boolean.valueOf(unique.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#setUnique(Boolean newValue)
	 * 
	 */
	public void setUnique(Boolean newValue) {
		if (newValue != null) {
			unique.setSelection(newValue.booleanValue());
		} else {
			unique.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.OperationElement.Properties.unique);
		if (eefElementEditorReadOnlyState && unique.isEnabled()) {
			unique.setEnabled(false);
			unique.setToolTipText(EcoreMessages.OperationElement_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !unique.isEnabled()) {
			unique.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#getLowerBound()
	 * 
	 */
	public String getLowerBound() {
		return lowerBound.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#setLowerBound(String newValue)
	 * 
	 */
	public void setLowerBound(String newValue) {
		if (newValue != null) {
			lowerBound.setText(newValue);
		} else {
			lowerBound.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.OperationElement.Cardinality.lowerBound);
		if (eefElementEditorReadOnlyState && lowerBound.isEnabled()) {
			lowerBound.setEnabled(false);
			lowerBound.setToolTipText(EcoreMessages.OperationElement_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !lowerBound.isEnabled()) {
			lowerBound.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#getUpperBound()
	 * 
	 */
	public String getUpperBound() {
		return upperBound.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart#setUpperBound(String newValue)
	 * 
	 */
	public void setUpperBound(String newValue) {
		if (newValue != null) {
			upperBound.setText(newValue);
		} else {
			upperBound.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.OperationElement.Cardinality.upperBound);
		if (eefElementEditorReadOnlyState && upperBound.isEnabled()) {
			upperBound.setEnabled(false);
			upperBound.setToolTipText(EcoreMessages.OperationElement_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !upperBound.isEnabled()) {
			upperBound.setEnabled(true);
		}	
		
	}






	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart#getTitle()
	 * 
	 */
	public String getTitle() {
		return EcoreMessages.OperationElement_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
