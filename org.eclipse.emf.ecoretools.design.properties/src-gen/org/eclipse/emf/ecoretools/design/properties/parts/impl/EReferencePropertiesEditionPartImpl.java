/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.parts.impl;

// Start of user code for imports
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.design.properties.ecore.providers.EcoreMessages;
import org.eclipse.emf.ecoretools.design.properties.parts.EReferencePropertiesEditionPart;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.providers.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.parts.PartComposer;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.BindingCompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionStep;
import org.eclipse.emf.eef.runtime.ui.utils.EditingUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.EObjectFlatComboViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.HorizontalBox;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.ui.widgets.SWTUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.TabElementTreeSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableContentProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
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
public class EReferencePropertiesEditionPartImpl extends CompositePropertiesEditionPart implements ISWTPropertiesEditionPart, EReferencePropertiesEditionPart {

	protected Text name;
	protected EObjectFlatComboViewer eType;
	protected Text defaultValueLiteral;
	protected EObjectFlatComboViewer eOpposite;
	protected ReferencesTable eKeys;
	protected List<ViewerFilter> eKeysBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> eKeysFilters = new ArrayList<ViewerFilter>();
	protected Button containment;
	protected Button unique;
	protected Button ordered;
	protected Button resolveProxies;
	protected Text lowerBound;
	protected Text upperBound;
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
	public EReferencePropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
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
		CompositionSequence eReferenceStep = new BindingCompositionSequence(propertiesEditionComponent);
		CompositionStep propertiesStep = eReferenceStep.addStep(EcoreViewsRepository.EReference.Properties.class);
		propertiesStep.addStep(EcoreViewsRepository.EReference.Properties.name);
		propertiesStep.addStep(EcoreViewsRepository.EReference.Properties.eType);
		propertiesStep.addStep(EcoreViewsRepository.EReference.Properties.defaultValueLiteral);
		propertiesStep.addStep(EcoreViewsRepository.EReference.Properties.eOpposite);
		propertiesStep.addStep(EcoreViewsRepository.EReference.Properties.eKeys);
		
		CompositionStep characteristicsStep = eReferenceStep.addStep(EcoreViewsRepository.EReference.Characteristics.class);
		CompositionStep generalCharacteristicsStep = characteristicsStep.addStep(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.class);
		generalCharacteristicsStep.addStep(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment);
		generalCharacteristicsStep.addStep(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique);
		generalCharacteristicsStep.addStep(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered);
		
		characteristicsStep
			.addStep(EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.class)
			.addStep(EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies);
		
		
		CompositionStep cardinalityStep = eReferenceStep.addStep(EcoreViewsRepository.EReference.Cardinality.class);
		cardinalityStep.addStep(EcoreViewsRepository.EReference.Cardinality.lowerBound);
		cardinalityStep.addStep(EcoreViewsRepository.EReference.Cardinality.upperBound);
		
		CompositionStep behaviorStep = eReferenceStep.addStep(EcoreViewsRepository.EReference.Behavior.class);
		CompositionStep changeabilityStep = behaviorStep.addStep(EcoreViewsRepository.EReference.Behavior.Changeability.class);
		changeabilityStep.addStep(EcoreViewsRepository.EReference.Behavior.Changeability.derived);
		changeabilityStep.addStep(EcoreViewsRepository.EReference.Behavior.Changeability.changeable);
		changeabilityStep.addStep(EcoreViewsRepository.EReference.Behavior.Changeability.unsettable);
		
		CompositionStep othersStep = behaviorStep.addStep(EcoreViewsRepository.EReference.Behavior.Others.class);
		othersStep.addStep(EcoreViewsRepository.EReference.Behavior.Others.transient_);
		othersStep.addStep(EcoreViewsRepository.EReference.Behavior.Others.volatile_);
		
		
		
		composer = new PartComposer(eReferenceStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == EcoreViewsRepository.EReference.Properties.class) {
					return createPropertiesGroup(parent);
				}
				if (key == EcoreViewsRepository.EReference.Properties.name) {
					return createNameText(parent);
				}
				if (key == EcoreViewsRepository.EReference.Properties.eType) {
					return createETypeFlatComboViewer(parent);
				}
				if (key == EcoreViewsRepository.EReference.Properties.defaultValueLiteral) {
					return createDefaultValueLiteralText(parent);
				}
				if (key == EcoreViewsRepository.EReference.Properties.eOpposite) {
					return createEOppositeFlatComboViewer(parent);
				}
				if (key == EcoreViewsRepository.EReference.Properties.eKeys) {
					return createEKeysAdvancedReferencesTable(parent);
				}
				if (key == EcoreViewsRepository.EReference.Characteristics.class) {
					return createCharacteristicsGroup(parent);
				}
				if (key == EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.class) {
					return createGeneralCharacteristicsHBox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment) {
					return createContainmentCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique) {
					return createUniqueCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered) {
					return createOrderedCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.class) {
					return createProxiesManagementHBox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies) {
					return createResolveProxiesCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Cardinality.class) {
					return createCardinalityGroup(parent);
				}
				if (key == EcoreViewsRepository.EReference.Cardinality.lowerBound) {
					return createLowerBoundText(parent);
				}
				if (key == EcoreViewsRepository.EReference.Cardinality.upperBound) {
					return createUpperBoundText(parent);
				}
				if (key == EcoreViewsRepository.EReference.Behavior.class) {
					return createBehaviorGroup(parent);
				}
				if (key == EcoreViewsRepository.EReference.Behavior.Changeability.class) {
					return createChangeabilityHBox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Behavior.Changeability.derived) {
					return createDerivedCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Behavior.Changeability.changeable) {
					return createChangeableCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Behavior.Changeability.unsettable) {
					return createUnsettableCheckbox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Behavior.Others.class) {
					return createOthersHBox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Behavior.Others.transient_) {
					return createTransient_Checkbox(parent);
				}
				if (key == EcoreViewsRepository.EReference.Behavior.Others.volatile_) {
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
		propertiesGroup.setText(EcoreMessages.EReferencePropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesGroupData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesGroupData.horizontalSpan = 3;
		propertiesGroup.setLayoutData(propertiesGroupData);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		return propertiesGroup;
	}

	
	protected Composite createNameText(Composite parent) {
		createDescription(parent, EcoreViewsRepository.EReference.Properties.name, EcoreMessages.EReferencePropertiesEditionPart_NameLabel);
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}

		});
		EditingUtils.setID(name, EcoreViewsRepository.EReference.Properties.name);
		EditingUtils.setEEFtype(name, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Properties.name, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createNameText

		// End of user code
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createETypeFlatComboViewer(Composite parent) {
		createDescription(parent, EcoreViewsRepository.EReference.Properties.eType, EcoreMessages.EReferencePropertiesEditionPart_ETypeLabel);
		eType = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(EcoreViewsRepository.EReference.Properties.eType, EcoreViewsRepository.SWT_KIND));
		eType.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		eType.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Properties.eType, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, getEType()));
			}

		});
		GridData eTypeData = new GridData(GridData.FILL_HORIZONTAL);
		eType.setLayoutData(eTypeData);
		eType.setID(EcoreViewsRepository.EReference.Properties.eType);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Properties.eType, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createETypeFlatComboViewer

		// End of user code
		return parent;
	}

	
	protected Composite createDefaultValueLiteralText(Composite parent) {
		createDescription(parent, EcoreViewsRepository.EReference.Properties.defaultValueLiteral, EcoreMessages.EReferencePropertiesEditionPart_DefaultValueLiteralLabel);
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Properties.defaultValueLiteral, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, defaultValueLiteral.getText()));
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Properties.defaultValueLiteral, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, defaultValueLiteral.getText()));
				}
			}

		});
		EditingUtils.setID(defaultValueLiteral, EcoreViewsRepository.EReference.Properties.defaultValueLiteral);
		EditingUtils.setEEFtype(defaultValueLiteral, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Properties.defaultValueLiteral, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createDefaultValueLiteralText

		// End of user code
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createEOppositeFlatComboViewer(Composite parent) {
		createDescription(parent, EcoreViewsRepository.EReference.Properties.eOpposite, EcoreMessages.EReferencePropertiesEditionPart_EOppositeLabel);
		eOpposite = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(EcoreViewsRepository.EReference.Properties.eOpposite, EcoreViewsRepository.SWT_KIND));
		eOpposite.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		eOpposite.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Properties.eOpposite, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, getEOpposite()));
			}

		});
		GridData eOppositeData = new GridData(GridData.FILL_HORIZONTAL);
		eOpposite.setLayoutData(eOppositeData);
		eOpposite.setID(EcoreViewsRepository.EReference.Properties.eOpposite);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Properties.eOpposite, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createEOppositeFlatComboViewer

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createEKeysAdvancedReferencesTable(Composite parent) {
		String label = getDescription(EcoreViewsRepository.EReference.Properties.eKeys, EcoreMessages.EReferencePropertiesEditionPart_EKeysLabel);		 
		this.eKeys = new ReferencesTable(label, new ReferencesTableListener() {
			public void handleAdd() { addEKeys(); }
			public void handleEdit(EObject element) { editEKeys(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveEKeys(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromEKeys(element); }
			public void navigateTo(EObject element) { }
		});
		this.eKeys.setHelpText(propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Properties.eKeys, EcoreViewsRepository.SWT_KIND));
		this.eKeys.createControls(parent);
		this.eKeys.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Properties.eKeys, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData eKeysData = new GridData(GridData.FILL_HORIZONTAL);
		eKeysData.horizontalSpan = 3;
		this.eKeys.setLayoutData(eKeysData);
		this.eKeys.disableMove();
		eKeys.setID(EcoreViewsRepository.EReference.Properties.eKeys);
		eKeys.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected void addEKeys() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(eKeys.getInput(), eKeysFilters, eKeysBusinessFilters,
		"eKeys", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Properties.eKeys,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				eKeys.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveEKeys(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Properties.eKeys, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		eKeys.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromEKeys(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Properties.eKeys, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		eKeys.refresh();
	}

	/**
	 * 
	 */
	protected void editEKeys(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				eKeys.refresh();
			}
		}
	}

	/**
	 * 
	 */
	protected Composite createCharacteristicsGroup(Composite parent) {
		Group characteristicsGroup = new Group(parent, SWT.NONE);
		characteristicsGroup.setText(EcoreMessages.EReferencePropertiesEditionPart_CharacteristicsGroupLabel);
		GridData characteristicsGroupData = new GridData(GridData.FILL_HORIZONTAL);
		characteristicsGroupData.horizontalSpan = 3;
		characteristicsGroup.setLayoutData(characteristicsGroupData);
		GridLayout characteristicsGroupLayout = new GridLayout();
		characteristicsGroupLayout.numColumns = 3;
		characteristicsGroup.setLayout(characteristicsGroupLayout);
		return characteristicsGroup;
	}

	
	protected Composite createGeneralCharacteristicsHBox(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		container.setLayoutData(gridData);
		HorizontalBox generalCharacteristicsHBox = new HorizontalBox(container);
		//Apply constraint for checkbox
		GridData constraint = new GridData(GridData.FILL_HORIZONTAL);
		constraint.horizontalAlignment = GridData.BEGINNING;
		generalCharacteristicsHBox.setLayoutData(constraint);
		return generalCharacteristicsHBox;
	}

	
	protected Composite createContainmentCheckbox(Composite parent) {
		containment = new Button(parent, SWT.CHECK);
		containment.setText(getDescription(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment, EcoreMessages.EReferencePropertiesEditionPart_ContainmentLabel));
		containment.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(containment.getSelection())));
			}

		});
		GridData containmentData = new GridData(GridData.FILL_HORIZONTAL);
		containmentData.horizontalSpan = 2;
		containment.setLayoutData(containmentData);
		EditingUtils.setID(containment, EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment);
		EditingUtils.setEEFtype(containment, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createContainmentCheckbox

		// End of user code
		return parent;
	}

	
	protected Composite createUniqueCheckbox(Composite parent) {
		unique = new Button(parent, SWT.CHECK);
		unique.setText(getDescription(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique, EcoreMessages.EReferencePropertiesEditionPart_UniqueLabel));
		unique.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(unique.getSelection())));
			}

		});
		GridData uniqueData = new GridData(GridData.FILL_HORIZONTAL);
		uniqueData.horizontalSpan = 2;
		unique.setLayoutData(uniqueData);
		EditingUtils.setID(unique, EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique);
		EditingUtils.setEEFtype(unique, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createUniqueCheckbox

		// End of user code
		return parent;
	}

	
	protected Composite createOrderedCheckbox(Composite parent) {
		ordered = new Button(parent, SWT.CHECK);
		ordered.setText(getDescription(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered, EcoreMessages.EReferencePropertiesEditionPart_OrderedLabel));
		ordered.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(ordered.getSelection())));
			}

		});
		GridData orderedData = new GridData(GridData.FILL_HORIZONTAL);
		orderedData.horizontalSpan = 2;
		ordered.setLayoutData(orderedData);
		EditingUtils.setID(ordered, EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered);
		EditingUtils.setEEFtype(ordered, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createOrderedCheckbox

		// End of user code
		return parent;
	}

	
	protected Composite createProxiesManagementHBox(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 3;
		container.setLayoutData(gridData);
		HorizontalBox proxiesManagementHBox = new HorizontalBox(container);
		//Apply constraint for checkbox
		GridData constraint = new GridData(GridData.FILL_HORIZONTAL);
		constraint.horizontalAlignment = GridData.BEGINNING;
		proxiesManagementHBox.setLayoutData(constraint);
		return proxiesManagementHBox;
	}

	
	protected Composite createResolveProxiesCheckbox(Composite parent) {
		resolveProxies = new Button(parent, SWT.CHECK);
		resolveProxies.setText(getDescription(EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies, EcoreMessages.EReferencePropertiesEditionPart_ResolveProxiesLabel));
		resolveProxies.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(resolveProxies.getSelection())));
			}

		});
		GridData resolveProxiesData = new GridData(GridData.FILL_HORIZONTAL);
		resolveProxiesData.horizontalSpan = 2;
		resolveProxies.setLayoutData(resolveProxiesData);
		EditingUtils.setID(resolveProxies, EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies);
		EditingUtils.setEEFtype(resolveProxies, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createResolveProxiesCheckbox

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createCardinalityGroup(Composite parent) {
		Group cardinalityGroup = new Group(parent, SWT.NONE);
		cardinalityGroup.setText(EcoreMessages.EReferencePropertiesEditionPart_CardinalityGroupLabel);
		GridData cardinalityGroupData = new GridData(GridData.FILL_HORIZONTAL);
		cardinalityGroupData.horizontalSpan = 3;
		cardinalityGroup.setLayoutData(cardinalityGroupData);
		GridLayout cardinalityGroupLayout = new GridLayout();
		cardinalityGroupLayout.numColumns = 3;
		cardinalityGroup.setLayout(cardinalityGroupLayout);
		return cardinalityGroup;
	}

	
	protected Composite createLowerBoundText(Composite parent) {
		createDescription(parent, EcoreViewsRepository.EReference.Cardinality.lowerBound, EcoreMessages.EReferencePropertiesEditionPart_LowerBoundLabel);
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Cardinality.lowerBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, lowerBound.getText()));
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Cardinality.lowerBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, lowerBound.getText()));
				}
			}

		});
		EditingUtils.setID(lowerBound, EcoreViewsRepository.EReference.Cardinality.lowerBound);
		EditingUtils.setEEFtype(lowerBound, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Cardinality.lowerBound, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createLowerBoundText

		// End of user code
		return parent;
	}

	
	protected Composite createUpperBoundText(Composite parent) {
		createDescription(parent, EcoreViewsRepository.EReference.Cardinality.upperBound, EcoreMessages.EReferencePropertiesEditionPart_UpperBoundLabel);
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Cardinality.upperBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, upperBound.getText()));
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Cardinality.upperBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, upperBound.getText()));
				}
			}

		});
		EditingUtils.setID(upperBound, EcoreViewsRepository.EReference.Cardinality.upperBound);
		EditingUtils.setEEFtype(upperBound, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Cardinality.upperBound, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createUpperBoundText

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createBehaviorGroup(Composite parent) {
		Group behaviorGroup = new Group(parent, SWT.NONE);
		behaviorGroup.setText(EcoreMessages.EReferencePropertiesEditionPart_BehaviorGroupLabel);
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
		derived.setText(getDescription(EcoreViewsRepository.EReference.Behavior.Changeability.derived, EcoreMessages.EReferencePropertiesEditionPart_DerivedLabel));
		derived.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Behavior.Changeability.derived, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(derived.getSelection())));
			}

		});
		GridData derivedData = new GridData(GridData.FILL_HORIZONTAL);
		derivedData.horizontalSpan = 2;
		derived.setLayoutData(derivedData);
		EditingUtils.setID(derived, EcoreViewsRepository.EReference.Behavior.Changeability.derived);
		EditingUtils.setEEFtype(derived, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Behavior.Changeability.derived, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createDerivedCheckbox

		// End of user code
		return parent;
	}

	
	protected Composite createChangeableCheckbox(Composite parent) {
		changeable = new Button(parent, SWT.CHECK);
		changeable.setText(getDescription(EcoreViewsRepository.EReference.Behavior.Changeability.changeable, EcoreMessages.EReferencePropertiesEditionPart_ChangeableLabel));
		changeable.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Behavior.Changeability.changeable, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(changeable.getSelection())));
			}

		});
		GridData changeableData = new GridData(GridData.FILL_HORIZONTAL);
		changeableData.horizontalSpan = 2;
		changeable.setLayoutData(changeableData);
		EditingUtils.setID(changeable, EcoreViewsRepository.EReference.Behavior.Changeability.changeable);
		EditingUtils.setEEFtype(changeable, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Behavior.Changeability.changeable, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createChangeableCheckbox

		// End of user code
		return parent;
	}

	
	protected Composite createUnsettableCheckbox(Composite parent) {
		unsettable = new Button(parent, SWT.CHECK);
		unsettable.setText(getDescription(EcoreViewsRepository.EReference.Behavior.Changeability.unsettable, EcoreMessages.EReferencePropertiesEditionPart_UnsettableLabel));
		unsettable.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Behavior.Changeability.unsettable, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(unsettable.getSelection())));
			}

		});
		GridData unsettableData = new GridData(GridData.FILL_HORIZONTAL);
		unsettableData.horizontalSpan = 2;
		unsettable.setLayoutData(unsettableData);
		EditingUtils.setID(unsettable, EcoreViewsRepository.EReference.Behavior.Changeability.unsettable);
		EditingUtils.setEEFtype(unsettable, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Behavior.Changeability.unsettable, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
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
		transient_.setText(getDescription(EcoreViewsRepository.EReference.Behavior.Others.transient_, EcoreMessages.EReferencePropertiesEditionPart_Transient_Label));
		transient_.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Behavior.Others.transient_, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(transient_.getSelection())));
			}

		});
		GridData transient_Data = new GridData(GridData.FILL_HORIZONTAL);
		transient_Data.horizontalSpan = 2;
		transient_.setLayoutData(transient_Data);
		EditingUtils.setID(transient_, EcoreViewsRepository.EReference.Behavior.Others.transient_);
		EditingUtils.setEEFtype(transient_, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Behavior.Others.transient_, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createTransient_Checkbox

		// End of user code
		return parent;
	}

	
	protected Composite createVolatile_Checkbox(Composite parent) {
		volatile_ = new Button(parent, SWT.CHECK);
		volatile_.setText(getDescription(EcoreViewsRepository.EReference.Behavior.Others.volatile_, EcoreMessages.EReferencePropertiesEditionPart_Volatile_Label));
		volatile_.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EReferencePropertiesEditionPartImpl.this, EcoreViewsRepository.EReference.Behavior.Others.volatile_, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(volatile_.getSelection())));
			}

		});
		GridData volatile_Data = new GridData(GridData.FILL_HORIZONTAL);
		volatile_Data.horizontalSpan = 2;
		volatile_.setLayoutData(volatile_Data);
		EditingUtils.setID(volatile_, EcoreViewsRepository.EReference.Behavior.Others.volatile_);
		EditingUtils.setEEFtype(volatile_, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EReference.Behavior.Others.volatile_, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
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
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getName()
	 * 
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setName(String newValue)
	 * 
	 */
	public void setName(String newValue) {
		if (newValue != null) {
			name.setText(newValue);
		} else {
			name.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Properties.name);
		if (eefElementEditorReadOnlyState && name.isEnabled()) {
			name.setEnabled(false);
			name.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !name.isEnabled()) {
			name.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getEType()
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
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#initEType(EObjectFlatComboSettings)
	 */
	public void initEType(EObjectFlatComboSettings settings) {
		eType.setInput(settings);
		if (current != null) {
			eType.setSelection(new StructuredSelection(settings.getValue()));
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Properties.eType);
		if (eefElementEditorReadOnlyState && eType.isEnabled()) {
			eType.setEnabled(false);
			eType.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eType.isEnabled()) {
			eType.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setEType(EObject newValue)
	 * 
	 */
	public void setEType(EObject newValue) {
		if (newValue != null) {
			eType.setSelection(new StructuredSelection(newValue));
		} else {
			eType.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Properties.eType);
		if (eefElementEditorReadOnlyState && eType.isEnabled()) {
			eType.setEnabled(false);
			eType.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eType.isEnabled()) {
			eType.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setETypeButtonMode(ButtonsModeEnum newValue)
	 */
	public void setETypeButtonMode(ButtonsModeEnum newValue) {
		eType.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#addFilterEType(ViewerFilter filter)
	 * 
	 */
	public void addFilterToEType(ViewerFilter filter) {
		eType.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#addBusinessFilterEType(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToEType(ViewerFilter filter) {
		eType.addBusinessRuleFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getDefaultValueLiteral()
	 * 
	 */
	public String getDefaultValueLiteral() {
		return defaultValueLiteral.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setDefaultValueLiteral(String newValue)
	 * 
	 */
	public void setDefaultValueLiteral(String newValue) {
		if (newValue != null) {
			defaultValueLiteral.setText(newValue);
		} else {
			defaultValueLiteral.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Properties.defaultValueLiteral);
		if (eefElementEditorReadOnlyState && defaultValueLiteral.isEnabled()) {
			defaultValueLiteral.setEnabled(false);
			defaultValueLiteral.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !defaultValueLiteral.isEnabled()) {
			defaultValueLiteral.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getEOpposite()
	 * 
	 */
	public EObject getEOpposite() {
		if (eOpposite.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) eOpposite.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#initEOpposite(EObjectFlatComboSettings)
	 */
	public void initEOpposite(EObjectFlatComboSettings settings) {
		eOpposite.setInput(settings);
		if (current != null) {
			eOpposite.setSelection(new StructuredSelection(settings.getValue()));
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Properties.eOpposite);
		if (eefElementEditorReadOnlyState && eOpposite.isEnabled()) {
			eOpposite.setEnabled(false);
			eOpposite.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eOpposite.isEnabled()) {
			eOpposite.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setEOpposite(EObject newValue)
	 * 
	 */
	public void setEOpposite(EObject newValue) {
		if (newValue != null) {
			eOpposite.setSelection(new StructuredSelection(newValue));
		} else {
			eOpposite.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Properties.eOpposite);
		if (eefElementEditorReadOnlyState && eOpposite.isEnabled()) {
			eOpposite.setEnabled(false);
			eOpposite.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eOpposite.isEnabled()) {
			eOpposite.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setEOppositeButtonMode(ButtonsModeEnum newValue)
	 */
	public void setEOppositeButtonMode(ButtonsModeEnum newValue) {
		eOpposite.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#addFilterEOpposite(ViewerFilter filter)
	 * 
	 */
	public void addFilterToEOpposite(ViewerFilter filter) {
		eOpposite.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#addBusinessFilterEOpposite(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToEOpposite(ViewerFilter filter) {
		eOpposite.addBusinessRuleFilter(filter);
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#initEKeys(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initEKeys(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		eKeys.setContentProvider(contentProvider);
		eKeys.setInput(settings);
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Properties.eKeys);
		if (eefElementEditorReadOnlyState && eKeys.getTable().isEnabled()) {
			eKeys.setEnabled(false);
			eKeys.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eKeys.getTable().isEnabled()) {
			eKeys.setEnabled(true);
		}
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#updateEKeys()
	 * 
	 */
	public void updateEKeys() {
	eKeys.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#addFilterEKeys(ViewerFilter filter)
	 * 
	 */
	public void addFilterToEKeys(ViewerFilter filter) {
		eKeysFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#addBusinessFilterEKeys(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToEKeys(ViewerFilter filter) {
		eKeysBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#isContainedInEKeysTable(EObject element)
	 * 
	 */
	public boolean isContainedInEKeysTable(EObject element) {
		return ((ReferencesTableSettings)eKeys.getInput()).contains(element);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getContainment()
	 * 
	 */
	public Boolean getContainment() {
		return Boolean.valueOf(containment.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setContainment(Boolean newValue)
	 * 
	 */
	public void setContainment(Boolean newValue) {
		if (newValue != null) {
			containment.setSelection(newValue.booleanValue());
		} else {
			containment.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.containment);
		if (eefElementEditorReadOnlyState && containment.isEnabled()) {
			containment.setEnabled(false);
			containment.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !containment.isEnabled()) {
			containment.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getUnique()
	 * 
	 */
	public Boolean getUnique() {
		return Boolean.valueOf(unique.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setUnique(Boolean newValue)
	 * 
	 */
	public void setUnique(Boolean newValue) {
		if (newValue != null) {
			unique.setSelection(newValue.booleanValue());
		} else {
			unique.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.unique);
		if (eefElementEditorReadOnlyState && unique.isEnabled()) {
			unique.setEnabled(false);
			unique.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !unique.isEnabled()) {
			unique.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getOrdered()
	 * 
	 */
	public Boolean getOrdered() {
		return Boolean.valueOf(ordered.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setOrdered(Boolean newValue)
	 * 
	 */
	public void setOrdered(Boolean newValue) {
		if (newValue != null) {
			ordered.setSelection(newValue.booleanValue());
		} else {
			ordered.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Characteristics.GeneralCharacteristics.ordered);
		if (eefElementEditorReadOnlyState && ordered.isEnabled()) {
			ordered.setEnabled(false);
			ordered.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !ordered.isEnabled()) {
			ordered.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getResolveProxies()
	 * 
	 */
	public Boolean getResolveProxies() {
		return Boolean.valueOf(resolveProxies.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setResolveProxies(Boolean newValue)
	 * 
	 */
	public void setResolveProxies(Boolean newValue) {
		if (newValue != null) {
			resolveProxies.setSelection(newValue.booleanValue());
		} else {
			resolveProxies.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Characteristics.ProxiesManagement.resolveProxies);
		if (eefElementEditorReadOnlyState && resolveProxies.isEnabled()) {
			resolveProxies.setEnabled(false);
			resolveProxies.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !resolveProxies.isEnabled()) {
			resolveProxies.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getLowerBound()
	 * 
	 */
	public String getLowerBound() {
		return lowerBound.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setLowerBound(String newValue)
	 * 
	 */
	public void setLowerBound(String newValue) {
		if (newValue != null) {
			lowerBound.setText(newValue);
		} else {
			lowerBound.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Cardinality.lowerBound);
		if (eefElementEditorReadOnlyState && lowerBound.isEnabled()) {
			lowerBound.setEnabled(false);
			lowerBound.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !lowerBound.isEnabled()) {
			lowerBound.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getUpperBound()
	 * 
	 */
	public String getUpperBound() {
		return upperBound.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setUpperBound(String newValue)
	 * 
	 */
	public void setUpperBound(String newValue) {
		if (newValue != null) {
			upperBound.setText(newValue);
		} else {
			upperBound.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Cardinality.upperBound);
		if (eefElementEditorReadOnlyState && upperBound.isEnabled()) {
			upperBound.setEnabled(false);
			upperBound.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !upperBound.isEnabled()) {
			upperBound.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getDerived()
	 * 
	 */
	public Boolean getDerived() {
		return Boolean.valueOf(derived.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setDerived(Boolean newValue)
	 * 
	 */
	public void setDerived(Boolean newValue) {
		if (newValue != null) {
			derived.setSelection(newValue.booleanValue());
		} else {
			derived.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Behavior.Changeability.derived);
		if (eefElementEditorReadOnlyState && derived.isEnabled()) {
			derived.setEnabled(false);
			derived.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !derived.isEnabled()) {
			derived.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getChangeable()
	 * 
	 */
	public Boolean getChangeable() {
		return Boolean.valueOf(changeable.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setChangeable(Boolean newValue)
	 * 
	 */
	public void setChangeable(Boolean newValue) {
		if (newValue != null) {
			changeable.setSelection(newValue.booleanValue());
		} else {
			changeable.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Behavior.Changeability.changeable);
		if (eefElementEditorReadOnlyState && changeable.isEnabled()) {
			changeable.setEnabled(false);
			changeable.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !changeable.isEnabled()) {
			changeable.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getUnsettable()
	 * 
	 */
	public Boolean getUnsettable() {
		return Boolean.valueOf(unsettable.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setUnsettable(Boolean newValue)
	 * 
	 */
	public void setUnsettable(Boolean newValue) {
		if (newValue != null) {
			unsettable.setSelection(newValue.booleanValue());
		} else {
			unsettable.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Behavior.Changeability.unsettable);
		if (eefElementEditorReadOnlyState && unsettable.isEnabled()) {
			unsettable.setEnabled(false);
			unsettable.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !unsettable.isEnabled()) {
			unsettable.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getTransient_()
	 * 
	 */
	public Boolean getTransient_() {
		return Boolean.valueOf(transient_.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setTransient_(Boolean newValue)
	 * 
	 */
	public void setTransient_(Boolean newValue) {
		if (newValue != null) {
			transient_.setSelection(newValue.booleanValue());
		} else {
			transient_.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Behavior.Others.transient_);
		if (eefElementEditorReadOnlyState && transient_.isEnabled()) {
			transient_.setEnabled(false);
			transient_.setToolTipText(EcoreMessages.EReference_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !transient_.isEnabled()) {
			transient_.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#getVolatile_()
	 * 
	 */
	public Boolean getVolatile_() {
		return Boolean.valueOf(volatile_.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.EReferencePropertiesEditionPart#setVolatile_(Boolean newValue)
	 * 
	 */
	public void setVolatile_(Boolean newValue) {
		if (newValue != null) {
			volatile_.setSelection(newValue.booleanValue());
		} else {
			volatile_.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EReference.Behavior.Others.volatile_);
		if (eefElementEditorReadOnlyState && volatile_.isEnabled()) {
			volatile_.setEnabled(false);
			volatile_.setToolTipText(EcoreMessages.EReference_ReadOnly);
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
		return EcoreMessages.EReference_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
