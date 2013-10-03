/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.parts.impl;

// Start of user code for imports
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.ecoretools.design.properties.parts.OperationElementPropertiesEditionPart;
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
public class OperationElementPropertiesEditionPartImpl extends CompositePropertiesEditionPart implements ISWTPropertiesEditionPart, OperationElementPropertiesEditionPart {

	protected Text name;
	protected EObjectFlatComboViewer eType;
	protected Button ordered;
	protected Button unique;
	protected Text lowerBound;
	protected Text upperBound;



	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public OperationElementPropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
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
					return createPropertiesGroup(parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Properties.name) {
					return createNameText(parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Properties.eType) {
					return createETypeFlatComboViewer(parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Properties.ordered) {
					return createOrderedCheckbox(parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Properties.unique) {
					return createUniqueCheckbox(parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Cardinality.class) {
					return createCardinalityGroup(parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Cardinality.lowerBound) {
					return createLowerBoundText(parent);
				}
				if (key == EcoreViewsRepository.OperationElement.Cardinality.upperBound) {
					return createUpperBoundText(parent);
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
		propertiesGroup.setText(EcoreMessages.OperationElementPropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesGroupData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesGroupData.horizontalSpan = 3;
		propertiesGroup.setLayoutData(propertiesGroupData);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		return propertiesGroup;
	}

	
	protected Composite createNameText(Composite parent) {
		createDescription(parent, EcoreViewsRepository.OperationElement.Properties.name, EcoreMessages.OperationElementPropertiesEditionPart_NameLabel);
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartImpl.this, EcoreViewsRepository.OperationElement.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartImpl.this, EcoreViewsRepository.OperationElement.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}

		});
		EditingUtils.setID(name, EcoreViewsRepository.OperationElement.Properties.name);
		EditingUtils.setEEFtype(name, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Properties.name, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createNameText

		// End of user code
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createETypeFlatComboViewer(Composite parent) {
		createDescription(parent, EcoreViewsRepository.OperationElement.Properties.eType, EcoreMessages.OperationElementPropertiesEditionPart_ETypeLabel);
		eType = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(EcoreViewsRepository.OperationElement.Properties.eType, EcoreViewsRepository.SWT_KIND));
		eType.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		eType.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartImpl.this, EcoreViewsRepository.OperationElement.Properties.eType, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, getEType()));
			}

		});
		GridData eTypeData = new GridData(GridData.FILL_HORIZONTAL);
		eType.setLayoutData(eTypeData);
		eType.setID(EcoreViewsRepository.OperationElement.Properties.eType);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Properties.eType, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createETypeFlatComboViewer

		// End of user code
		return parent;
	}

	
	protected Composite createOrderedCheckbox(Composite parent) {
		ordered = new Button(parent, SWT.CHECK);
		ordered.setText(getDescription(EcoreViewsRepository.OperationElement.Properties.ordered, EcoreMessages.OperationElementPropertiesEditionPart_OrderedLabel));
		ordered.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartImpl.this, EcoreViewsRepository.OperationElement.Properties.ordered, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(ordered.getSelection())));
			}

		});
		GridData orderedData = new GridData(GridData.FILL_HORIZONTAL);
		orderedData.horizontalSpan = 2;
		ordered.setLayoutData(orderedData);
		EditingUtils.setID(ordered, EcoreViewsRepository.OperationElement.Properties.ordered);
		EditingUtils.setEEFtype(ordered, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Properties.ordered, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createOrderedCheckbox

		// End of user code
		return parent;
	}

	
	protected Composite createUniqueCheckbox(Composite parent) {
		unique = new Button(parent, SWT.CHECK);
		unique.setText(getDescription(EcoreViewsRepository.OperationElement.Properties.unique, EcoreMessages.OperationElementPropertiesEditionPart_UniqueLabel));
		unique.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartImpl.this, EcoreViewsRepository.OperationElement.Properties.unique, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(unique.getSelection())));
			}

		});
		GridData uniqueData = new GridData(GridData.FILL_HORIZONTAL);
		uniqueData.horizontalSpan = 2;
		unique.setLayoutData(uniqueData);
		EditingUtils.setID(unique, EcoreViewsRepository.OperationElement.Properties.unique);
		EditingUtils.setEEFtype(unique, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Properties.unique, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createUniqueCheckbox

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createCardinalityGroup(Composite parent) {
		Group cardinalityGroup = new Group(parent, SWT.NONE);
		cardinalityGroup.setText(EcoreMessages.OperationElementPropertiesEditionPart_CardinalityGroupLabel);
		GridData cardinalityGroupData = new GridData(GridData.FILL_HORIZONTAL);
		cardinalityGroupData.horizontalSpan = 3;
		cardinalityGroup.setLayoutData(cardinalityGroupData);
		GridLayout cardinalityGroupLayout = new GridLayout();
		cardinalityGroupLayout.numColumns = 3;
		cardinalityGroup.setLayout(cardinalityGroupLayout);
		return cardinalityGroup;
	}

	
	protected Composite createLowerBoundText(Composite parent) {
		createDescription(parent, EcoreViewsRepository.OperationElement.Cardinality.lowerBound, EcoreMessages.OperationElementPropertiesEditionPart_LowerBoundLabel);
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartImpl.this, EcoreViewsRepository.OperationElement.Cardinality.lowerBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, lowerBound.getText()));
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartImpl.this, EcoreViewsRepository.OperationElement.Cardinality.lowerBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, lowerBound.getText()));
				}
			}

		});
		EditingUtils.setID(lowerBound, EcoreViewsRepository.OperationElement.Cardinality.lowerBound);
		EditingUtils.setEEFtype(lowerBound, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Cardinality.lowerBound, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		// Start of user code for createLowerBoundText

		// End of user code
		return parent;
	}

	
	protected Composite createUpperBoundText(Composite parent) {
		createDescription(parent, EcoreViewsRepository.OperationElement.Cardinality.upperBound, EcoreMessages.OperationElementPropertiesEditionPart_UpperBoundLabel);
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartImpl.this, EcoreViewsRepository.OperationElement.Cardinality.upperBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, upperBound.getText()));
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OperationElementPropertiesEditionPartImpl.this, EcoreViewsRepository.OperationElement.Cardinality.upperBound, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, upperBound.getText()));
				}
			}

		});
		EditingUtils.setID(upperBound, EcoreViewsRepository.OperationElement.Cardinality.upperBound);
		EditingUtils.setEEFtype(upperBound, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.OperationElement.Cardinality.upperBound, EcoreViewsRepository.SWT_KIND), null); //$NON-NLS-1$
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
