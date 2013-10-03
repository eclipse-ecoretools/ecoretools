/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.parts.impl;

// Start of user code for imports
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.ecoretools.design.properties.providers.EcoreMessages;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.ui.parts.PartComposer;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.BindingCompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionStep;
import org.eclipse.emf.eef.runtime.ui.utils.EditingUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.EObjectFlatComboViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.HorizontalBox;
import org.eclipse.emf.eef.runtime.ui.widgets.SWTUtils;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

// End of user code

/**
 * 
 * 
 */
public class EAttributePropertiesEditionPartImpl extends CompositePropertiesEditionPart implements ISWTPropertiesEditionPart, EAttributePropertiesEditionPart {

	protected Text name;
	protected EObjectFlatComboViewer eType;
	protected Text defaultValueLiteral;
	protected Button iD;
	protected Text lowerBound;
	protected Text upperBound;
	protected Button unique;
	protected Button ordered;
	protected Button derived;
	protected Button changeable;
	protected Button unsettable;
	protected Button transient_;
	protected Button volatile_;



	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public EAttributePropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
		super(editionComponent);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart#
	 * 			createFigure(org.eclipse.swt.widgets.Composite)
	 * 
	 */
	public Composite createFigure(final Composite parent) {
		view = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		view.setLayout(layout);
		createControls(view);
		return view;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart#
	 * 			createControls(org.eclipse.swt.widgets.Composite)
	 * 
	 */
	public void createControls(Composite view) { 
		CompositionSequence eAttributeStep = new BindingCompositionSequence(propertiesEditionComponent);
		CompositionStep propertiesStep = eAttributeStep.addStep(EcoreViewsRepository.EAttribute.Properties.class);
		propertiesStep.addStep(EcoreViewsRepository.EAttribute.Properties.name);
		propertiesStep.addStep(EcoreViewsRepository.EAttribute.Properties.eType);
		propertiesStep.addStep(EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral);
		propertiesStep.addStep(EcoreViewsRepository.EAttribute.Properties.iD);
		
		CompositionStep cardinalityStep = eAttributeStep.addStep(EcoreViewsRepository.EAttribute.Cardinality.class);
		cardinalityStep.addStep(EcoreViewsRepository.EAttribute.Cardinality.lowerBound);
		cardinalityStep.addStep(EcoreViewsRepository.EAttribute.Cardinality.upperBound);
		CompositionStep listPropertiesStep = cardinalityStep.addStep(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.class);
		listPropertiesStep.addStep(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique);
		listPropertiesStep.addStep(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered);
		
		
		CompositionStep behaviorStep = eAttributeStep.addStep(EcoreViewsRepository.EAttribute.Behavior.class);
		CompositionStep changeabilityStep = behaviorStep.addStep(EcoreViewsRepository.EAttribute.Behavior.Changeability.class);
		changeabilityStep.addStep(EcoreViewsRepository.EAttribute.Behavior.Changeability.derived);
		changeabilityStep.addStep(EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable);
		changeabilityStep.addStep(EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable);
		
		CompositionStep othersStep = behaviorStep.addStep(EcoreViewsRepository.EAttribute.Behavior.Others.class);
		othersStep.addStep(EcoreViewsRepository.EAttribute.Behavior.Others.transient_);
		othersStep.addStep(EcoreViewsRepository.EAttribute.Behavior.Others.volatile_);
		
		
		
		composer = new PartComposer(eAttributeStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == EcoreViewsRepository.EAttribute.Properties.class) {
					return createPropertiesGroup(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Properties.name) {
					return createNameText(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Properties.eType) {
					return createETypeFlatComboViewer(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral) {
					return createDefaultValueLiteralText(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Properties.iD) {
					return createIDCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Cardinality.class) {
					return createCardinalityGroup(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Cardinality.lowerBound) {
					return createLowerBoundText(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Cardinality.upperBound) {
					return createUpperBoundText(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Cardinality.ListProperties.class) {
					return createListPropertiesHBox(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique) {
					return createUniqueCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered) {
					return createOrderedCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Behavior.class) {
					return createBehaviorGroup(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Behavior.Changeability.class) {
					return createChangeabilityHBox(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Behavior.Changeability.derived) {
					return createDerivedCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable) {
					return createChangeableCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable) {
					return createUnsettableCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Behavior.Others.class) {
					return createOthersHBox(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Behavior.Others.transient_) {
					return createTransient_Checkbox(parent);
				}
				if (key == EcoreViewsRepository.EAttribute.Behavior.Others.volatile_) {
					return createVolatile_Checkbox(parent);
				}
				return parent;
			}
		};
		composer.compose(view);
	}

	/**
	 * 
	 */
	protected Composite createPropertiesGroup(Composite parent) {
		Group propertiesGroup = new Group(parent, SWT.NONE);
		propertiesGroup.setText(EcoreMessages.EAttributePropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesGroupData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesGroupData.horizontalSpan = 3;
		propertiesGroup.setLayoutData(propertiesGroupData);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		return propertiesGroup;
	}

	
	protected Composite createNameText(Composite parent) {
		createDescription(parent, EcoreViewsRepository.EAttribute.Properties.name, EcoreMessages.EAttributePropertiesEditionPart_NameLabel);
		name = SWTUtils.createScrollableText(parent, SWT.BORDER);
		GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(nameData);
		name.addFocusListener(new FocusAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
			}

		});
		name.addKeyListener(new KeyAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}

		});
		EditingUtils.setID(name, EcoreViewsRepository.EAttribute.Properties.name);
		EditingUtils.setEEFtype(name, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Properties.name, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createNameText

		// End of user code
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createETypeFlatComboViewer(Composite parent) {
		createDescription(parent, EcoreViewsRepository.EAttribute.Properties.eType, EcoreMessages.EAttributePropertiesEditionPart_ETypeLabel);
		eType = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(EcoreViewsRepository.EAttribute.Properties.eType, EcoreViewsRepository.SWT_KIND));
		eType.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		eType.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Properties.eType, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, getEType()));
			}

		});
		GridData eTypeData = new GridData(GridData.FILL_HORIZONTAL);
		eType.setLayoutData(eTypeData);
		eType.setID(EcoreViewsRepository.EAttribute.Properties.eType);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Properties.eType, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createETypeFlatComboViewer

		// End of user code
		return parent;
	}

	
	protected Composite createDefaultValueLiteralText(Composite parent) {
		createDescription(parent, EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral, EcoreMessages.EAttributePropertiesEditionPart_DefaultValueLiteralLabel);
		defaultValueLiteral = SWTUtils.createScrollableText(parent, SWT.BORDER);
		GridData defaultValueLiteralData = new GridData(GridData.FILL_HORIZONTAL);
		defaultValueLiteral.setLayoutData(defaultValueLiteralData);
		defaultValueLiteral.addFocusListener(new FocusAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, defaultValueLiteral.getText()));
			}

		});
		defaultValueLiteral.addKeyListener(new KeyAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, defaultValueLiteral.getText()));
				}
			}

		});
		EditingUtils.setID(defaultValueLiteral, EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral);
		EditingUtils.setEEFtype(defaultValueLiteral, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createDefaultValueLiteralText

		// End of user code
		return parent;
	}

	
	protected Composite createIDCheckbox(Composite parent) {
		iD = new Button(parent, SWT.CHECK);
		iD.setText(getDescription(EcoreViewsRepository.EAttribute.Properties.iD, EcoreMessages.EAttributePropertiesEditionPart_IDLabel));
		iD.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Properties.iD, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(iD.getSelection())));
			}

		});
		GridData iDData = new GridData(GridData.FILL_HORIZONTAL);
		iDData.horizontalSpan = 2;
		iD.setLayoutData(iDData);
		EditingUtils.setID(iD, EcoreViewsRepository.EAttribute.Properties.iD);
		EditingUtils.setEEFtype(iD, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Properties.iD, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createIDCheckbox

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createCardinalityGroup(Composite parent) {
		Group cardinalityGroup = new Group(parent, SWT.NONE);
		cardinalityGroup.setText(EcoreMessages.EAttributePropertiesEditionPart_CardinalityGroupLabel);
		GridData cardinalityGroupData = new GridData(GridData.FILL_HORIZONTAL);
		cardinalityGroupData.horizontalSpan = 3;
		cardinalityGroup.setLayoutData(cardinalityGroupData);
		GridLayout cardinalityGroupLayout = new GridLayout();
		cardinalityGroupLayout.numColumns = 3;
		cardinalityGroup.setLayout(cardinalityGroupLayout);
		return cardinalityGroup;
	}

	
	protected Composite createLowerBoundText(Composite parent) {
		createDescription(parent, EcoreViewsRepository.EAttribute.Cardinality.lowerBound, EcoreMessages.EAttributePropertiesEditionPart_LowerBoundLabel);
		lowerBound = SWTUtils.createScrollableText(parent, SWT.BORDER);
		GridData lowerBoundData = new GridData(GridData.FILL_HORIZONTAL);
		lowerBound.setLayoutData(lowerBoundData);
		lowerBound.addFocusListener(new FocusAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Cardinality.lowerBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, lowerBound.getText()));
			}

		});
		lowerBound.addKeyListener(new KeyAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Cardinality.lowerBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, lowerBound.getText()));
				}
			}

		});
		EditingUtils.setID(lowerBound, EcoreViewsRepository.EAttribute.Cardinality.lowerBound);
		EditingUtils.setEEFtype(lowerBound, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Cardinality.lowerBound, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createLowerBoundText

		// End of user code
		return parent;
	}

	
	protected Composite createUpperBoundText(Composite parent) {
		createDescription(parent, EcoreViewsRepository.EAttribute.Cardinality.upperBound, EcoreMessages.EAttributePropertiesEditionPart_UpperBoundLabel);
		upperBound = SWTUtils.createScrollableText(parent, SWT.BORDER);
		GridData upperBoundData = new GridData(GridData.FILL_HORIZONTAL);
		upperBound.setLayoutData(upperBoundData);
		upperBound.addFocusListener(new FocusAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Cardinality.upperBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, upperBound.getText()));
			}

		});
		upperBound.addKeyListener(new KeyAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Cardinality.upperBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, upperBound.getText()));
				}
			}

		});
		EditingUtils.setID(upperBound, EcoreViewsRepository.EAttribute.Cardinality.upperBound);
		EditingUtils.setEEFtype(upperBound, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Cardinality.upperBound, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createUpperBoundText

		// End of user code
		return parent;
	}

	
	protected Composite createListPropertiesHBox(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		container.setLayoutData(gridData);
		HorizontalBox listPropertiesHBox = new HorizontalBox(container);
		//Apply constraint for checkbox
		GridData constraint = new GridData(GridData.FILL_HORIZONTAL);
		constraint.horizontalAlignment = GridData.BEGINNING;
		listPropertiesHBox.setLayoutData(constraint);
		return listPropertiesHBox;
	}

	
	protected Composite createUniqueCheckbox(Composite parent) {
		unique = new Button(parent, SWT.CHECK);
		unique.setText(getDescription(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique, EcoreMessages.EAttributePropertiesEditionPart_UniqueLabel));
		unique.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(unique.getSelection())));
			}

		});
		GridData uniqueData = new GridData(GridData.FILL_HORIZONTAL);
		uniqueData.horizontalSpan = 2;
		unique.setLayoutData(uniqueData);
		EditingUtils.setID(unique, EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique);
		EditingUtils.setEEFtype(unique, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createUniqueCheckbox

		// End of user code
		return parent;
	}

	
	protected Composite createOrderedCheckbox(Composite parent) {
		ordered = new Button(parent, SWT.CHECK);
		ordered.setText(getDescription(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered, EcoreMessages.EAttributePropertiesEditionPart_OrderedLabel));
		ordered.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(ordered.getSelection())));
			}

		});
		GridData orderedData = new GridData(GridData.FILL_HORIZONTAL);
		orderedData.horizontalSpan = 2;
		ordered.setLayoutData(orderedData);
		EditingUtils.setID(ordered, EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered);
		EditingUtils.setEEFtype(ordered, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createOrderedCheckbox

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createBehaviorGroup(Composite parent) {
		Group behaviorGroup = new Group(parent, SWT.NONE);
		behaviorGroup.setText(EcoreMessages.EAttributePropertiesEditionPart_BehaviorGroupLabel);
		GridData behaviorGroupData = new GridData(GridData.FILL_HORIZONTAL);
		behaviorGroupData.horizontalSpan = 3;
		behaviorGroup.setLayoutData(behaviorGroupData);
		GridLayout behaviorGroupLayout = new GridLayout();
		behaviorGroupLayout.numColumns = 3;
		behaviorGroup.setLayout(behaviorGroupLayout);
		return behaviorGroup;
	}

	
	protected Composite createChangeabilityHBox(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		container.setLayoutData(gridData);
		HorizontalBox changeabilityHBox = new HorizontalBox(container);
		//Apply constraint for checkbox
		GridData constraint = new GridData(GridData.FILL_HORIZONTAL);
		constraint.horizontalAlignment = GridData.BEGINNING;
		changeabilityHBox.setLayoutData(constraint);
		return changeabilityHBox;
	}

	
	protected Composite createDerivedCheckbox(Composite parent) {
		derived = new Button(parent, SWT.CHECK);
		derived.setText(getDescription(EcoreViewsRepository.EAttribute.Behavior.Changeability.derived, EcoreMessages.EAttributePropertiesEditionPart_DerivedLabel));
		derived.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Behavior.Changeability.derived, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(derived.getSelection())));
			}

		});
		GridData derivedData = new GridData(GridData.FILL_HORIZONTAL);
		derivedData.horizontalSpan = 2;
		derived.setLayoutData(derivedData);
		EditingUtils.setID(derived, EcoreViewsRepository.EAttribute.Behavior.Changeability.derived);
		EditingUtils.setEEFtype(derived, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Behavior.Changeability.derived, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createDerivedCheckbox

		// End of user code
		return parent;
	}

	
	protected Composite createChangeableCheckbox(Composite parent) {
		changeable = new Button(parent, SWT.CHECK);
		changeable.setText(getDescription(EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable, EcoreMessages.EAttributePropertiesEditionPart_ChangeableLabel));
		changeable.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(changeable.getSelection())));
			}

		});
		GridData changeableData = new GridData(GridData.FILL_HORIZONTAL);
		changeableData.horizontalSpan = 2;
		changeable.setLayoutData(changeableData);
		EditingUtils.setID(changeable, EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable);
		EditingUtils.setEEFtype(changeable, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createChangeableCheckbox

		// End of user code
		return parent;
	}

	
	protected Composite createUnsettableCheckbox(Composite parent) {
		unsettable = new Button(parent, SWT.CHECK);
		unsettable.setText(getDescription(EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable, EcoreMessages.EAttributePropertiesEditionPart_UnsettableLabel));
		unsettable.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(unsettable.getSelection())));
			}

		});
		GridData unsettableData = new GridData(GridData.FILL_HORIZONTAL);
		unsettableData.horizontalSpan = 2;
		unsettable.setLayoutData(unsettableData);
		EditingUtils.setID(unsettable, EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable);
		EditingUtils.setEEFtype(unsettable, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createUnsettableCheckbox

		// End of user code
		return parent;
	}

	
	protected Composite createOthersHBox(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		container.setLayoutData(gridData);
		HorizontalBox othersHBox = new HorizontalBox(container);
		//Apply constraint for checkbox
		GridData constraint = new GridData(GridData.FILL_HORIZONTAL);
		constraint.horizontalAlignment = GridData.BEGINNING;
		othersHBox.setLayoutData(constraint);
		return othersHBox;
	}

	
	protected Composite createTransient_Checkbox(Composite parent) {
		transient_ = new Button(parent, SWT.CHECK);
		transient_.setText(getDescription(EcoreViewsRepository.EAttribute.Behavior.Others.transient_, EcoreMessages.EAttributePropertiesEditionPart_Transient_Label));
		transient_.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Behavior.Others.transient_, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(transient_.getSelection())));
			}

		});
		GridData transient_Data = new GridData(GridData.FILL_HORIZONTAL);
		transient_Data.horizontalSpan = 2;
		transient_.setLayoutData(transient_Data);
		EditingUtils.setID(transient_, EcoreViewsRepository.EAttribute.Behavior.Others.transient_);
		EditingUtils.setEEFtype(transient_, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Behavior.Others.transient_, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createTransient_Checkbox

		// End of user code
		return parent;
	}

	
	protected Composite createVolatile_Checkbox(Composite parent) {
		volatile_ = new Button(parent, SWT.CHECK);
		volatile_.setText(getDescription(EcoreViewsRepository.EAttribute.Behavior.Others.volatile_, EcoreMessages.EAttributePropertiesEditionPart_Volatile_Label));
		volatile_.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAttributePropertiesEditionPartImpl.this, EcoreViewsRepository.EAttribute.Behavior.Others.volatile_, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(volatile_.getSelection())));
			}

		});
		GridData volatile_Data = new GridData(GridData.FILL_HORIZONTAL);
		volatile_Data.horizontalSpan = 2;
		volatile_.setLayoutData(volatile_Data);
		EditingUtils.setID(volatile_, EcoreViewsRepository.EAttribute.Behavior.Others.volatile_);
		EditingUtils.setEEFtype(volatile_, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAttribute.Behavior.Others.volatile_, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createVolatile_Checkbox

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
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getName()
	 * 
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setName(String newValue)
	 * 
	 */
	public void setName(String newValue) {
		if (newValue != null) {
			name.setText(newValue);
		} else {
			name.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Properties.name);
		if (eefElementEditorReadOnlyState && name.isEnabled()) {
			name.setEnabled(false);
			name.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !name.isEnabled()) {
			name.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getEType()
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
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#initEType(EObjectFlatComboSettings)
	 */
	public void initEType(EObjectFlatComboSettings settings) {
		eType.setInput(settings);
		if (current != null) {
			eType.setSelection(new StructuredSelection(settings.getValue()));
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Properties.eType);
		if (eefElementEditorReadOnlyState && eType.isEnabled()) {
			eType.setEnabled(false);
			eType.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eType.isEnabled()) {
			eType.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setEType(EObject newValue)
	 * 
	 */
	public void setEType(EObject newValue) {
		if (newValue != null) {
			eType.setSelection(new StructuredSelection(newValue));
		} else {
			eType.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Properties.eType);
		if (eefElementEditorReadOnlyState && eType.isEnabled()) {
			eType.setEnabled(false);
			eType.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eType.isEnabled()) {
			eType.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setETypeButtonMode(ButtonsModeEnum newValue)
	 */
	public void setETypeButtonMode(ButtonsModeEnum newValue) {
		eType.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#addFilterEType(ViewerFilter filter)
	 * 
	 */
	public void addFilterToEType(ViewerFilter filter) {
		eType.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#addBusinessFilterEType(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToEType(ViewerFilter filter) {
		eType.addBusinessRuleFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getDefaultValueLiteral()
	 * 
	 */
	public String getDefaultValueLiteral() {
		return defaultValueLiteral.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setDefaultValueLiteral(String newValue)
	 * 
	 */
	public void setDefaultValueLiteral(String newValue) {
		if (newValue != null) {
			defaultValueLiteral.setText(newValue);
		} else {
			defaultValueLiteral.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Properties.defaultValueLiteral);
		if (eefElementEditorReadOnlyState && defaultValueLiteral.isEnabled()) {
			defaultValueLiteral.setEnabled(false);
			defaultValueLiteral.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !defaultValueLiteral.isEnabled()) {
			defaultValueLiteral.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getID()
	 * 
	 */
	public Boolean getID() {
		return Boolean.valueOf(iD.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setID(Boolean newValue)
	 * 
	 */
	public void setID(Boolean newValue) {
		if (newValue != null) {
			iD.setSelection(newValue.booleanValue());
		} else {
			iD.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Properties.iD);
		if (eefElementEditorReadOnlyState && iD.isEnabled()) {
			iD.setEnabled(false);
			iD.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !iD.isEnabled()) {
			iD.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getLowerBound()
	 * 
	 */
	public String getLowerBound() {
		return lowerBound.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setLowerBound(String newValue)
	 * 
	 */
	public void setLowerBound(String newValue) {
		if (newValue != null) {
			lowerBound.setText(newValue);
		} else {
			lowerBound.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Cardinality.lowerBound);
		if (eefElementEditorReadOnlyState && lowerBound.isEnabled()) {
			lowerBound.setEnabled(false);
			lowerBound.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !lowerBound.isEnabled()) {
			lowerBound.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getUpperBound()
	 * 
	 */
	public String getUpperBound() {
		return upperBound.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setUpperBound(String newValue)
	 * 
	 */
	public void setUpperBound(String newValue) {
		if (newValue != null) {
			upperBound.setText(newValue);
		} else {
			upperBound.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Cardinality.upperBound);
		if (eefElementEditorReadOnlyState && upperBound.isEnabled()) {
			upperBound.setEnabled(false);
			upperBound.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !upperBound.isEnabled()) {
			upperBound.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getUnique()
	 * 
	 */
	public Boolean getUnique() {
		return Boolean.valueOf(unique.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setUnique(Boolean newValue)
	 * 
	 */
	public void setUnique(Boolean newValue) {
		if (newValue != null) {
			unique.setSelection(newValue.booleanValue());
		} else {
			unique.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.unique);
		if (eefElementEditorReadOnlyState && unique.isEnabled()) {
			unique.setEnabled(false);
			unique.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !unique.isEnabled()) {
			unique.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getOrdered()
	 * 
	 */
	public Boolean getOrdered() {
		return Boolean.valueOf(ordered.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setOrdered(Boolean newValue)
	 * 
	 */
	public void setOrdered(Boolean newValue) {
		if (newValue != null) {
			ordered.setSelection(newValue.booleanValue());
		} else {
			ordered.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Cardinality.ListProperties.ordered);
		if (eefElementEditorReadOnlyState && ordered.isEnabled()) {
			ordered.setEnabled(false);
			ordered.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !ordered.isEnabled()) {
			ordered.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getDerived()
	 * 
	 */
	public Boolean getDerived() {
		return Boolean.valueOf(derived.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setDerived(Boolean newValue)
	 * 
	 */
	public void setDerived(Boolean newValue) {
		if (newValue != null) {
			derived.setSelection(newValue.booleanValue());
		} else {
			derived.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Behavior.Changeability.derived);
		if (eefElementEditorReadOnlyState && derived.isEnabled()) {
			derived.setEnabled(false);
			derived.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !derived.isEnabled()) {
			derived.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getChangeable()
	 * 
	 */
	public Boolean getChangeable() {
		return Boolean.valueOf(changeable.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setChangeable(Boolean newValue)
	 * 
	 */
	public void setChangeable(Boolean newValue) {
		if (newValue != null) {
			changeable.setSelection(newValue.booleanValue());
		} else {
			changeable.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Behavior.Changeability.changeable);
		if (eefElementEditorReadOnlyState && changeable.isEnabled()) {
			changeable.setEnabled(false);
			changeable.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !changeable.isEnabled()) {
			changeable.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getUnsettable()
	 * 
	 */
	public Boolean getUnsettable() {
		return Boolean.valueOf(unsettable.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setUnsettable(Boolean newValue)
	 * 
	 */
	public void setUnsettable(Boolean newValue) {
		if (newValue != null) {
			unsettable.setSelection(newValue.booleanValue());
		} else {
			unsettable.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Behavior.Changeability.unsettable);
		if (eefElementEditorReadOnlyState && unsettable.isEnabled()) {
			unsettable.setEnabled(false);
			unsettable.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !unsettable.isEnabled()) {
			unsettable.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getTransient_()
	 * 
	 */
	public Boolean getTransient_() {
		return Boolean.valueOf(transient_.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setTransient_(Boolean newValue)
	 * 
	 */
	public void setTransient_(Boolean newValue) {
		if (newValue != null) {
			transient_.setSelection(newValue.booleanValue());
		} else {
			transient_.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Behavior.Others.transient_);
		if (eefElementEditorReadOnlyState && transient_.isEnabled()) {
			transient_.setEnabled(false);
			transient_.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !transient_.isEnabled()) {
			transient_.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#getVolatile_()
	 * 
	 */
	public Boolean getVolatile_() {
		return Boolean.valueOf(volatile_.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAttributePropertiesEditionPart#setVolatile_(Boolean newValue)
	 * 
	 */
	public void setVolatile_(Boolean newValue) {
		if (newValue != null) {
			volatile_.setSelection(newValue.booleanValue());
		} else {
			volatile_.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAttribute.Behavior.Others.volatile_);
		if (eefElementEditorReadOnlyState && volatile_.isEnabled()) {
			volatile_.setEnabled(false);
			volatile_.setToolTipText(EcoreMessages.EAttribute_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !volatile_.isEnabled()) {
			volatile_.setEnabled(true);
		}	
		
	}






	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart#getTitle()
	 * 
	 */
	public String getTitle() {
		return EcoreMessages.EAttribute_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
