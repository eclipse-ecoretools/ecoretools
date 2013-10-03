/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.parts.forms;

// Start of user code for imports
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart;
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.ecoretools.design.properties.providers.EcoreMessages;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
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
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.EObjectFlatComboViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.FormUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
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
public class EAnnotationPropertiesEditionPartForm extends SectionPropertiesEditingPart implements IFormPropertiesEditionPart, EAnnotationPropertiesEditionPart {

	protected Text source;
	protected ReferencesTable details;
	protected List<ViewerFilter> detailsBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> detailsFilters = new ArrayList<ViewerFilter>();
	protected EObjectFlatComboViewer eModelElement;
	protected ReferencesTable contents;
	protected List<ViewerFilter> contentsBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> contentsFilters = new ArrayList<ViewerFilter>();
	protected ReferencesTable references;
	protected List<ViewerFilter> referencesBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> referencesFilters = new ArrayList<ViewerFilter>();



	/**
	 * For {@link ISection} use only.
	 */
	public EAnnotationPropertiesEditionPartForm() { super(); }

	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public EAnnotationPropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
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
		CompositionSequence eAnnotationStep = new BindingCompositionSequence(propertiesEditionComponent);
		CompositionStep propertiesStep = eAnnotationStep.addStep(EcoreViewsRepository.EAnnotation.Properties.class);
		propertiesStep.addStep(EcoreViewsRepository.EAnnotation.Properties.source);
		propertiesStep.addStep(EcoreViewsRepository.EAnnotation.Properties.details);
		propertiesStep.addStep(EcoreViewsRepository.EAnnotation.Properties.eModelElement);
		propertiesStep.addStep(EcoreViewsRepository.EAnnotation.Properties.contents);
		propertiesStep.addStep(EcoreViewsRepository.EAnnotation.Properties.references);
		
		
		composer = new PartComposer(eAnnotationStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == EcoreViewsRepository.EAnnotation.Properties.class) {
					return createPropertiesGroup(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.EAnnotation.Properties.source) {
					return createSourceText(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.EAnnotation.Properties.details) {
					return createDetailsTableComposition(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.EAnnotation.Properties.eModelElement) {
					return createEModelElementFlatComboViewer(parent, widgetFactory);
				}
				if (key == EcoreViewsRepository.EAnnotation.Properties.contents) {
					return createContentsTableComposition(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.EAnnotation.Properties.references) {
					return createReferencesReferencesTable(widgetFactory, parent);
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
		propertiesSection.setText(EcoreMessages.EAnnotationPropertiesEditionPart_PropertiesGroupLabel);
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

	
	protected Composite createSourceText(FormToolkit widgetFactory, Composite parent) {
		createDescription(parent, EcoreViewsRepository.EAnnotation.Properties.source, EcoreMessages.EAnnotationPropertiesEditionPart_SourceLabel);
		source = widgetFactory.createText(parent, ""); //$NON-NLS-1$
		source.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		widgetFactory.paintBordersFor(parent);
		GridData sourceData = new GridData(GridData.FILL_HORIZONTAL);
		source.setLayoutData(sourceData);
		source.addFocusListener(new FocusAdapter() {
			/**
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							EAnnotationPropertiesEditionPartForm.this,
							EcoreViewsRepository.EAnnotation.Properties.source,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, source.getText()));
					propertiesEditionComponent
							.firePropertiesChanged(new PropertiesEditionEvent(
									EAnnotationPropertiesEditionPartForm.this,
									EcoreViewsRepository.EAnnotation.Properties.source,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_LOST,
									null, source.getText()));
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
									EAnnotationPropertiesEditionPartForm.this,
									null,
									PropertiesEditionEvent.FOCUS_CHANGED, PropertiesEditionEvent.FOCUS_GAINED,
									null, null));
				}
			}
		});
		source.addKeyListener(new KeyAdapter() {
			/**
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.source, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, source.getText()));
				}
			}
		});
		EditingUtils.setID(source, EcoreViewsRepository.EAnnotation.Properties.source);
		EditingUtils.setEEFtype(source, "eef::Text"); //$NON-NLS-1$
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAnnotation.Properties.source, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createSourceText

		// End of user code
		return parent;
	}

	/**
	 * @param container
	 * 
	 */
	protected Composite createDetailsTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.details = new ReferencesTable(getDescription(EcoreViewsRepository.EAnnotation.Properties.details, EcoreMessages.EAnnotationPropertiesEditionPart_DetailsLabel), new ReferencesTableListener() {
			public void handleAdd() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.details, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, null));
				details.refresh();
			}
			public void handleEdit(EObject element) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.details, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.EDIT, null, element));
				details.refresh();
			}
			public void handleMove(EObject element, int oldIndex, int newIndex) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.details, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
				details.refresh();
			}
			public void handleRemove(EObject element) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.details, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
				details.refresh();
			}
			public void navigateTo(EObject element) { }
		});
		for (ViewerFilter filter : this.detailsFilters) {
			this.details.addFilter(filter);
		}
		this.details.setHelpText(propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAnnotation.Properties.details, EcoreViewsRepository.FORM_KIND));
		this.details.createControls(parent, widgetFactory);
		this.details.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.details, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData detailsData = new GridData(GridData.FILL_HORIZONTAL);
		detailsData.horizontalSpan = 3;
		this.details.setLayoutData(detailsData);
		this.details.setLowerBound(0);
		this.details.setUpperBound(-1);
		details.setID(EcoreViewsRepository.EAnnotation.Properties.details);
		details.setEEFType("eef::AdvancedTableComposition"); //$NON-NLS-1$
		// Start of user code for createDetailsTableComposition

		// End of user code
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * @param widgetFactory factory to use to instanciante widget of the form
	 * 
	 */
	protected Composite createEModelElementFlatComboViewer(Composite parent, FormToolkit widgetFactory) {
		createDescription(parent, EcoreViewsRepository.EAnnotation.Properties.eModelElement, EcoreMessages.EAnnotationPropertiesEditionPart_EModelElementLabel);
		eModelElement = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(EcoreViewsRepository.EAnnotation.Properties.eModelElement, EcoreViewsRepository.FORM_KIND));
		widgetFactory.adapt(eModelElement);
		eModelElement.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		GridData eModelElementData = new GridData(GridData.FILL_HORIZONTAL);
		eModelElement.setLayoutData(eModelElementData);
		eModelElement.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			public void selectionChanged(SelectionChangedEvent event) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.eModelElement, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getEModelElement()));
			}

		});
		eModelElement.setID(EcoreViewsRepository.EAnnotation.Properties.eModelElement);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAnnotation.Properties.eModelElement, EcoreViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		// Start of user code for createEModelElementFlatComboViewer

		// End of user code
		return parent;
	}

	/**
	 * @param container
	 * 
	 */
	protected Composite createContentsTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.contents = new ReferencesTable(getDescription(EcoreViewsRepository.EAnnotation.Properties.contents, EcoreMessages.EAnnotationPropertiesEditionPart_ContentsLabel), new ReferencesTableListener() {
			public void handleAdd() {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.contents, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, null));
				contents.refresh();
			}
			public void handleEdit(EObject element) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.contents, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.EDIT, null, element));
				contents.refresh();
			}
			public void handleMove(EObject element, int oldIndex, int newIndex) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.contents, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
				contents.refresh();
			}
			public void handleRemove(EObject element) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.contents, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
				contents.refresh();
			}
			public void navigateTo(EObject element) { }
		});
		for (ViewerFilter filter : this.contentsFilters) {
			this.contents.addFilter(filter);
		}
		this.contents.setHelpText(propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAnnotation.Properties.contents, EcoreViewsRepository.FORM_KIND));
		this.contents.createControls(parent, widgetFactory);
		this.contents.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.contents, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData contentsData = new GridData(GridData.FILL_HORIZONTAL);
		contentsData.horizontalSpan = 3;
		this.contents.setLayoutData(contentsData);
		this.contents.setLowerBound(0);
		this.contents.setUpperBound(-1);
		contents.setID(EcoreViewsRepository.EAnnotation.Properties.contents);
		contents.setEEFType("eef::AdvancedTableComposition"); //$NON-NLS-1$
		// Start of user code for createContentsTableComposition

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createReferencesReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.references = new ReferencesTable(getDescription(EcoreViewsRepository.EAnnotation.Properties.references, EcoreMessages.EAnnotationPropertiesEditionPart_ReferencesLabel), new ReferencesTableListener	() {
			public void handleAdd() { addReferences(); }
			public void handleEdit(EObject element) { editReferences(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveReferences(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromReferences(element); }
			public void navigateTo(EObject element) { }
		});
		this.references.setHelpText(propertiesEditionComponent.getHelpContent(EcoreViewsRepository.EAnnotation.Properties.references, EcoreViewsRepository.FORM_KIND));
		this.references.createControls(parent, widgetFactory);
		this.references.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.references, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData referencesData = new GridData(GridData.FILL_HORIZONTAL);
		referencesData.horizontalSpan = 3;
		this.references.setLayoutData(referencesData);
		this.references.disableMove();
		references.setID(EcoreViewsRepository.EAnnotation.Properties.references);
		references.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		// Start of user code for createReferencesReferencesTable

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected void addReferences() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(references.getInput(), referencesFilters, referencesBusinessFilters,
		"references", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.references,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				references.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveReferences(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.references, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		references.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromReferences(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(EAnnotationPropertiesEditionPartForm.this, EcoreViewsRepository.EAnnotation.Properties.references, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		references.refresh();
	}

	/**
	 * 
	 */
	protected void editReferences(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				references.refresh();
			}
		}
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
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#getSource()
	 * 
	 */
	public String getSource() {
		return source.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#setSource(String newValue)
	 * 
	 */
	public void setSource(String newValue) {
		if (newValue != null) {
			source.setText(newValue);
		} else {
			source.setText(""); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAnnotation.Properties.source);
		if (eefElementEditorReadOnlyState && source.isEnabled()) {
			source.setEnabled(false);
			source.setToolTipText(EcoreMessages.EAnnotation_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !source.isEnabled()) {
			source.setEnabled(true);
		}	
		
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#initDetails(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initDetails(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		details.setContentProvider(contentProvider);
		details.setInput(settings);
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAnnotation.Properties.details);
		if (eefElementEditorReadOnlyState && details.isEnabled()) {
			details.setEnabled(false);
			details.setToolTipText(EcoreMessages.EAnnotation_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !details.isEnabled()) {
			details.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#updateDetails()
	 * 
	 */
	public void updateDetails() {
	details.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#addFilterDetails(ViewerFilter filter)
	 * 
	 */
	public void addFilterToDetails(ViewerFilter filter) {
		detailsFilters.add(filter);
		if (this.details != null) {
			this.details.addFilter(filter);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#addBusinessFilterDetails(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToDetails(ViewerFilter filter) {
		detailsBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#isContainedInDetailsTable(EObject element)
	 * 
	 */
	public boolean isContainedInDetailsTable(EObject element) {
		return ((ReferencesTableSettings)details.getInput()).contains(element);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#getEModelElement()
	 * 
	 */
	public EObject getEModelElement() {
		if (eModelElement.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) eModelElement.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#initEModelElement(EObjectFlatComboSettings)
	 */
	public void initEModelElement(EObjectFlatComboSettings settings) {
		eModelElement.setInput(settings);
		if (current != null) {
			eModelElement.setSelection(new StructuredSelection(settings.getValue()));
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAnnotation.Properties.eModelElement);
		if (eefElementEditorReadOnlyState && eModelElement.isEnabled()) {
			eModelElement.setEnabled(false);
			eModelElement.setToolTipText(EcoreMessages.EAnnotation_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eModelElement.isEnabled()) {
			eModelElement.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#setEModelElement(EObject newValue)
	 * 
	 */
	public void setEModelElement(EObject newValue) {
		if (newValue != null) {
			eModelElement.setSelection(new StructuredSelection(newValue));
		} else {
			eModelElement.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAnnotation.Properties.eModelElement);
		if (eefElementEditorReadOnlyState && eModelElement.isEnabled()) {
			eModelElement.setEnabled(false);
			eModelElement.setToolTipText(EcoreMessages.EAnnotation_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eModelElement.isEnabled()) {
			eModelElement.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#setEModelElementButtonMode(ButtonsModeEnum newValue)
	 */
	public void setEModelElementButtonMode(ButtonsModeEnum newValue) {
		eModelElement.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#addFilterEModelElement(ViewerFilter filter)
	 * 
	 */
	public void addFilterToEModelElement(ViewerFilter filter) {
		eModelElement.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#addBusinessFilterEModelElement(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToEModelElement(ViewerFilter filter) {
		eModelElement.addBusinessRuleFilter(filter);
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#initContents(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initContents(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		contents.setContentProvider(contentProvider);
		contents.setInput(settings);
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAnnotation.Properties.contents);
		if (eefElementEditorReadOnlyState && contents.isEnabled()) {
			contents.setEnabled(false);
			contents.setToolTipText(EcoreMessages.EAnnotation_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !contents.isEnabled()) {
			contents.setEnabled(true);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#updateContents()
	 * 
	 */
	public void updateContents() {
	contents.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#addFilterContents(ViewerFilter filter)
	 * 
	 */
	public void addFilterToContents(ViewerFilter filter) {
		contentsFilters.add(filter);
		if (this.contents != null) {
			this.contents.addFilter(filter);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#addBusinessFilterContents(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToContents(ViewerFilter filter) {
		contentsBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#isContainedInContentsTable(EObject element)
	 * 
	 */
	public boolean isContainedInContentsTable(EObject element) {
		return ((ReferencesTableSettings)contents.getInput()).contains(element);
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#initReferences(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initReferences(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		references.setContentProvider(contentProvider);
		references.setInput(settings);
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.EAnnotation.Properties.references);
		if (eefElementEditorReadOnlyState && references.getTable().isEnabled()) {
			references.setEnabled(false);
			references.setToolTipText(EcoreMessages.EAnnotation_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !references.getTable().isEnabled()) {
			references.setEnabled(true);
		}
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#updateReferences()
	 * 
	 */
	public void updateReferences() {
	references.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#addFilterReferences(ViewerFilter filter)
	 * 
	 */
	public void addFilterToReferences(ViewerFilter filter) {
		referencesFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#addBusinessFilterReferences(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToReferences(ViewerFilter filter) {
		referencesBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.EAnnotationPropertiesEditionPart#isContainedInReferencesTable(EObject element)
	 * 
	 */
	public boolean isContainedInReferencesTable(EObject element) {
		return ((ReferencesTableSettings)references.getInput()).contains(element);
	}






	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart#getTitle()
	 * 
	 */
	public String getTitle() {
		return EcoreMessages.EAnnotation_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
