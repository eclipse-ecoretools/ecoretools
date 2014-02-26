/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.parts.forms;

// Start of user code for imports
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.design.properties.ecore.providers.EcoreMessages;
import org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.part.impl.SectionPropertiesEditingPart;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.providers.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.parts.PartComposer;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.BindingCompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionStep;
import org.eclipse.emf.eef.runtime.ui.utils.EditingUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.FormUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.HorizontalBox;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.ui.widgets.TabElementTreeSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableContentProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
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
public class EClassPropertiesEditionPartForm extends SectionPropertiesEditingPart implements IFormPropertiesEditionPart, EClassPropertiesEditionPart {

	protected Text name;
	protected Button abstract_;
	protected Button interface_;
	protected ReferencesTable eSuperTypes;
	protected List<ViewerFilter> eSuperTypesBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> eSuperTypesFilters = new ArrayList<ViewerFilter>();
	protected ReferencesTable eGenericSuperTypes;
	protected List<ViewerFilter> eGenericSuperTypesBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> eGenericSuperTypesFilters = new ArrayList<ViewerFilter>();



	/**
	 * For {@link ISection} use only.
	 */
	public EClassPropertiesEditionPartForm() { super(); }

	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public EClassPropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
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
		CompositionSequence eClassStep = new BindingCompositionSequence(propertiesEditionComponent);
		CompositionStep propertiesStep = eClassStep.addStep(EcoreViewsRepository.EClass.Properties.class);
		propertiesStep.addStep(EcoreViewsRepository.EClass.Properties.name);
		CompositionStep abstractionStep = propertiesStep.addStep(EcoreViewsRepository.EClass.Properties.Abstraction.class);
		abstractionStep.addStep(EcoreViewsRepository.EClass.Properties.Abstraction.abstract_);
		abstractionStep.addStep(EcoreViewsRepository.EClass.Properties.Abstraction.interface_);
		
		
		CompositionStep inheritanceStep = eClassStep.addStep(EcoreViewsRepository.EClass.Inheritance.class);
		inheritanceStep.addStep(EcoreViewsRepository.EClass.Inheritance.eSuperTypes);
		inheritanceStep.addStep(EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes);
		
		
		composer = new PartComposer(eClassStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == EcoreViewsRepository.EClass.Properties.class) {
					return createPropertiesGroup(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.EClass.Properties.name) {
					return createNameText(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.EClass.Properties.Abstraction.class) {
					return createAbstractionHBox(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.EClass.Properties.Abstraction.abstract_) {
					return createAbstract_Checkbox(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.EClass.Properties.Abstraction.interface_) {
					return createInterface_Checkbox(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.EClass.Inheritance.class) {
					return createInheritanceGroup(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.EClass.Inheritance.eSuperTypes) {
					return createESuperTypesReferencesTable(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes) {
					return createEGenericSuperTypesTableComposition(widgetFactory, parent);
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
		propertiesSection.setText(EcoreMessages.EClassPropertiesEditionPart_PropertiesGroupLabel);
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
		createDescription(parent, EcoreViewsRepository.EClass.Properties.name, EcoreMessages.EClassPropertiesEditionPart_NameLabel);
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
							EClassPropertiesEditionPartForm.this,
							EcoreViewsRepository.EClass.Properties.name,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
					propertiesEditionComponent
							.firePropertiesChanged(new PropertiesEditionEvent(
									EClassPropertiesEditionPartForm.this,
									EcoreViewsRepository.EClass.Properties.name,
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
									EClassPropertiesEditionPartForm.this,
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}
		});
		EditingUtils.setID(name, EcoreViewsRepository.EClass.Properties.name);
		EditingUtils.setEEFtype(name, "eef::Text"); //$NON-NLS-1$
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EClass.Properties.name, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createNameText

		// End of user code
		return parent;
	}

	
	protected Composite createAbstractionHBox(FormToolkit widgetFactory, Composite parent) {
		Composite container = widgetFactory.createComposite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan=3;
		container.setLayoutData(gridData);
				HorizontalBox abstractionHBox = new HorizontalBox(container);
		//Apply constraint for checkbox
		GridData constraint = new GridData(GridData.FILL_HORIZONTAL);
		constraint.horizontalAlignment = GridData.BEGINNING;
		abstractionHBox.setLayoutData(constraint);
		widgetFactory.adapt(abstractionHBox);
		return abstractionHBox;
	}

	
	protected Composite createAbstract_Checkbox(FormToolkit widgetFactory, Composite parent) {
		abstract_ = widgetFactory.createButton(parent, getDescription(EcoreViewsRepository.EClass.Properties.Abstraction.abstract_, EcoreMessages.EClassPropertiesEditionPart_Abstract_Label), SWT.CHECK);
		abstract_.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Properties.Abstraction.abstract_, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(abstract_.getSelection())));
			}

		});
		GridData abstract_Data = new GridData(GridData.FILL_HORIZONTAL);
		abstract_Data.horizontalSpan = 2;
		abstract_.setLayoutData(abstract_Data);
		EditingUtils.setID(abstract_, EcoreViewsRepository.EClass.Properties.Abstraction.abstract_);
		EditingUtils.setEEFtype(abstract_, "eef::Checkbox"); //$NON-NLS-1$
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EClass.Properties.Abstraction.abstract_, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createAbstract_Checkbox

		// End of user code
		return parent;
	}

	
	protected Composite createInterface_Checkbox(FormToolkit widgetFactory, Composite parent) {
		interface_ = widgetFactory.createButton(parent, getDescription(EcoreViewsRepository.EClass.Properties.Abstraction.interface_, EcoreMessages.EClassPropertiesEditionPart_Interface_Label), SWT.CHECK);
		interface_.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Properties.Abstraction.interface_, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(interface_.getSelection())));
			}

		});
		GridData interface_Data = new GridData(GridData.FILL_HORIZONTAL);
		interface_Data.horizontalSpan = 2;
		interface_.setLayoutData(interface_Data);
		EditingUtils.setID(interface_, EcoreViewsRepository.EClass.Properties.Abstraction.interface_);
		EditingUtils.setEEFtype(interface_, "eef::Checkbox"); //$NON-NLS-1$
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EClass.Properties.Abstraction.interface_, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createInterface_Checkbox

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createInheritanceGroup(FormToolkit widgetFactory, final Composite parent) {
		Section inheritanceSection = widgetFactory.createSection(parent, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		inheritanceSection.setText(EcoreMessages.EClassPropertiesEditionPart_InheritanceGroupLabel);
		GridData inheritanceSectionData = new GridData(GridData.FILL_HORIZONTAL);
		inheritanceSectionData.horizontalSpan = 3;
		inheritanceSection.setLayoutData(inheritanceSectionData);
		Composite inheritanceGroup = widgetFactory.createComposite(inheritanceSection);
		GridLayout inheritanceGroupLayout = new GridLayout();
		inheritanceGroupLayout.numColumns = 3;
		inheritanceGroup.setLayout(inheritanceGroupLayout);
		inheritanceSection.setClient(inheritanceGroup);
		return inheritanceGroup;
	}

	/**
	 * 
	 */
	protected Composite createESuperTypesReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.eSuperTypes = new ReferencesTable(getDescription(EcoreViewsRepository.EClass.Inheritance.eSuperTypes, EcoreMessages.EClassPropertiesEditionPart_ESuperTypesLabel), new ReferencesTableListener	() {
			public void handleAdd() { addESuperTypes(); }
			public void handleEdit(EObject element) { editESuperTypes(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveESuperTypes(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromESuperTypes(element); }
			public void navigateTo(EObject element) { }
		});
		this.eSuperTypes.setHelpText(propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EClass.Inheritance.eSuperTypes, EcoreViewsRepository.FORM_KIND));
		this.eSuperTypes.createControls(parent, widgetFactory);
		this.eSuperTypes.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Inheritance.eSuperTypes, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData eSuperTypesData = new GridData(GridData.FILL_HORIZONTAL);
		eSuperTypesData.horizontalSpan = 3;
		this.eSuperTypes.setLayoutData(eSuperTypesData);
		this.eSuperTypes.disableMove();
		eSuperTypes.setID(EcoreViewsRepository.EClass.Inheritance.eSuperTypes);
		eSuperTypes.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		// Start of user code for createESuperTypesReferencesTable

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected void addESuperTypes() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(eSuperTypes.getInput(), eSuperTypesFilters, eSuperTypesBusinessFilters,
		"eSuperTypes", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Inheritance.eSuperTypes,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				eSuperTypes.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveESuperTypes(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Inheritance.eSuperTypes, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		eSuperTypes.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromESuperTypes(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Inheritance.eSuperTypes, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		eSuperTypes.refresh();
	}

	/**
	 * 
	 */
	protected void editESuperTypes(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				eSuperTypes.refresh();
			}
		}
	}

	/**
	 * @param container
	 * 
	 */
	protected Composite createEGenericSuperTypesTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.eGenericSuperTypes = new ReferencesTable(getDescription(EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes, EcoreMessages.EClassPropertiesEditionPart_EGenericSuperTypesLabel), new ReferencesTableListener() {
			public void handleAdd() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, null));
				eGenericSuperTypes.refresh();
			}
			public void handleEdit(EObject element) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.EDIT, null, element));
				eGenericSuperTypes.refresh();
			}
			public void handleMove(EObject element, int oldIndex, int newIndex) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
				eGenericSuperTypes.refresh();
			}
			public void handleRemove(EObject element) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
				eGenericSuperTypes.refresh();
			}
			public void navigateTo(EObject element) { }
		});
		for (ViewerFilter filter : this.eGenericSuperTypesFilters) {
			this.eGenericSuperTypes.addFilter(filter);
		}
		this.eGenericSuperTypes.setHelpText(propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes, EcoreViewsRepository.FORM_KIND));
		this.eGenericSuperTypes.createControls(parent, widgetFactory);
		this.eGenericSuperTypes.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EClassPropertiesEditionPartForm.this, EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData eGenericSuperTypesData = new GridData(GridData.FILL_HORIZONTAL);
		eGenericSuperTypesData.horizontalSpan = 3;
		this.eGenericSuperTypes.setLayoutData(eGenericSuperTypesData);
		this.eGenericSuperTypes.setLowerBound(0);
		this.eGenericSuperTypes.setUpperBound(-1);
		eGenericSuperTypes.setID(EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes);
		eGenericSuperTypes.setEEFType("eef::AdvancedTableComposition"); //$NON-NLS-1$
		// Start of user code for createEGenericSuperTypesTableComposition

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
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#getName()
	 * 
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#setName(String newValue)
	 * 
	 */
	public void setName(String newValue) {
		if (newValue != null) {
			name.setText(newValue);
		} else {
			name.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EClass.Properties.name);
		if (eefElementEditorReadOnlyState && name.isEnabled()) {
			name.setEnabled(false);
			name.setToolTipText(EcoreMessages.EClass_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !name.isEnabled()) {
			name.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#getAbstract_()
	 * 
	 */
	public Boolean getAbstract_() {
		return Boolean.valueOf(abstract_.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#setAbstract_(Boolean newValue)
	 * 
	 */
	public void setAbstract_(Boolean newValue) {
		if (newValue != null) {
			abstract_.setSelection(newValue.booleanValue());
		} else {
			abstract_.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EClass.Properties.Abstraction.abstract_);
		if (eefElementEditorReadOnlyState && abstract_.isEnabled()) {
			abstract_.setEnabled(false);
			abstract_.setToolTipText(EcoreMessages.EClass_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !abstract_.isEnabled()) {
			abstract_.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#getInterface_()
	 * 
	 */
	public Boolean getInterface_() {
		return Boolean.valueOf(interface_.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#setInterface_(Boolean newValue)
	 * 
	 */
	public void setInterface_(Boolean newValue) {
		if (newValue != null) {
			interface_.setSelection(newValue.booleanValue());
		} else {
			interface_.setSelection(false);
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EClass.Properties.Abstraction.interface_);
		if (eefElementEditorReadOnlyState && interface_.isEnabled()) {
			interface_.setEnabled(false);
			interface_.setToolTipText(EcoreMessages.EClass_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !interface_.isEnabled()) {
			interface_.setEnabled(true);
		}	
		
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#initESuperTypes(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initESuperTypes(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		eSuperTypes.setContentProvider(contentProvider);
		eSuperTypes.setInput(settings);
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EClass.Inheritance.eSuperTypes);
		if (eefElementEditorReadOnlyState && eSuperTypes.getTable().isEnabled()) {
			eSuperTypes.setEnabled(false);
			eSuperTypes.setToolTipText(EcoreMessages.EClass_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eSuperTypes.getTable().isEnabled()) {
			eSuperTypes.setEnabled(true);
		}
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#updateESuperTypes()
	 * 
	 */
	public void updateESuperTypes() {
	eSuperTypes.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#addFilterESuperTypes(ViewerFilter filter)
	 * 
	 */
	public void addFilterToESuperTypes(ViewerFilter filter) {
		eSuperTypesFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#addBusinessFilterESuperTypes(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToESuperTypes(ViewerFilter filter) {
		eSuperTypesBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#isContainedInESuperTypesTable(EObject element)
	 * 
	 */
	public boolean isContainedInESuperTypesTable(EObject element) {
		return ((ReferencesTableSettings)eSuperTypes.getInput()).contains(element);
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#initEGenericSuperTypes(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initEGenericSuperTypes(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		eGenericSuperTypes.setContentProvider(contentProvider);
		eGenericSuperTypes.setInput(settings);
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EClass.Inheritance.eGenericSuperTypes);
		if (eefElementEditorReadOnlyState && eGenericSuperTypes.isEnabled()) {
			eGenericSuperTypes.setEnabled(false);
			eGenericSuperTypes.setToolTipText(EcoreMessages.EClass_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eGenericSuperTypes.isEnabled()) {
			eGenericSuperTypes.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#updateEGenericSuperTypes()
	 * 
	 */
	public void updateEGenericSuperTypes() {
	eGenericSuperTypes.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#addFilterEGenericSuperTypes(ViewerFilter filter)
	 * 
	 */
	public void addFilterToEGenericSuperTypes(ViewerFilter filter) {
		eGenericSuperTypesFilters.add(filter);
		if (this.eGenericSuperTypes != null) {
			this.eGenericSuperTypes.addFilter(filter);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#addBusinessFilterEGenericSuperTypes(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToEGenericSuperTypes(ViewerFilter filter) {
		eGenericSuperTypesBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EClassPropertiesEditionPart#isContainedInEGenericSuperTypesTable(EObject element)
	 * 
	 */
	public boolean isContainedInEGenericSuperTypesTable(EObject element) {
		return ((ReferencesTableSettings)eGenericSuperTypes.getInput()).contains(element);
	}






	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart#getTitle()
	 * 
	 */
	public String getTitle() {
		return EcoreMessages.EClass_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
