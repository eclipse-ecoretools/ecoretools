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
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.ecoretools.design.properties.parts.ExceptionsPropertiesEditionPart;
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
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.ui.widgets.TabElementTreeSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableContentProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

// End of user code

/**
 * 
 * 
 */
public class ExceptionsPropertiesEditionPartForm extends SectionPropertiesEditingPart implements IFormPropertiesEditionPart, ExceptionsPropertiesEditionPart {

	protected ReferencesTable eExceptions;
	protected List<ViewerFilter> eExceptionsBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> eExceptionsFilters = new ArrayList<ViewerFilter>();



	/**
	 * For {@link ISection} use only.
	 */
	public ExceptionsPropertiesEditionPartForm() { super(); }

	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public ExceptionsPropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
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
		CompositionSequence exceptionsStep = new BindingCompositionSequence(propertiesEditionComponent);
		exceptionsStep
			.addStep(EcoreViewsRepository.Exceptions.Exceptions_.class)
			.addStep(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions);
		
		
		composer = new PartComposer(exceptionsStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == EcoreViewsRepository.Exceptions.Exceptions_.class) {
					return createExceptionsGroup(widgetFactory, parent);
				}
				if (key == EcoreViewsRepository.Exceptions.Exceptions_.eExceptions) {
					return createEExceptionsReferencesTable(widgetFactory, parent);
				}
				return parent;
			}
		};
		composer.compose(view);
	}
	/**
	 * 
	 */
	protected Composite createExceptionsGroup(FormToolkit widgetFactory, final Composite parent) {
		Section exceptionsSection = widgetFactory.createSection(parent, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		exceptionsSection.setText(EcoreMessages.ExceptionsPropertiesEditionPart_ExceptionsGroupLabel);
		GridData exceptionsSectionData = new GridData(GridData.FILL_HORIZONTAL);
		exceptionsSectionData.horizontalSpan = 3;
		exceptionsSection.setLayoutData(exceptionsSectionData);
		Composite exceptionsGroup = widgetFactory.createComposite(exceptionsSection);
		GridLayout exceptionsGroupLayout = new GridLayout();
		exceptionsGroupLayout.numColumns = 3;
		exceptionsGroup.setLayout(exceptionsGroupLayout);
		exceptionsSection.setClient(exceptionsGroup);
		return exceptionsGroup;
	}

	/**
	 * 
	 */
	protected Composite createEExceptionsReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.eExceptions = new ReferencesTable(getDescription(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions, EcoreMessages.ExceptionsPropertiesEditionPart_EExceptionsLabel), new ReferencesTableListener	() {
			public void handleAdd() { addEExceptions(); }
			public void handleEdit(EObject element) { editEExceptions(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveEExceptions(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromEExceptions(element); }
			public void navigateTo(EObject element) { }
		});
		this.eExceptions.setHelpText(propertiesEditionComponent.getHelpContent(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions, EcoreViewsRepository.FORM_KIND));
		this.eExceptions.createControls(parent, widgetFactory);
		this.eExceptions.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ExceptionsPropertiesEditionPartForm.this, EcoreViewsRepository.Exceptions.Exceptions_.eExceptions, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData eExceptionsData = new GridData(GridData.FILL_HORIZONTAL);
		eExceptionsData.horizontalSpan = 3;
		this.eExceptions.setLayoutData(eExceptionsData);
		this.eExceptions.disableMove();
		eExceptions.setID(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions);
		eExceptions.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		// Start of user code for createEExceptionsReferencesTable

		// End of user code
		return parent;
	}

	/**
	 * 
	 */
	protected void addEExceptions() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(eExceptions.getInput(), eExceptionsFilters, eExceptionsBusinessFilters,
		"eExceptions", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ExceptionsPropertiesEditionPartForm.this, EcoreViewsRepository.Exceptions.Exceptions_.eExceptions,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				eExceptions.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveEExceptions(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ExceptionsPropertiesEditionPartForm.this, EcoreViewsRepository.Exceptions.Exceptions_.eExceptions, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		eExceptions.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromEExceptions(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ExceptionsPropertiesEditionPartForm.this, EcoreViewsRepository.Exceptions.Exceptions_.eExceptions, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		eExceptions.refresh();
	}

	/**
	 * 
	 */
	protected void editEExceptions(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				eExceptions.refresh();
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
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.ExceptionsPropertiesEditionPart#initEExceptions(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initEExceptions(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		eExceptions.setContentProvider(contentProvider);
		eExceptions.setInput(settings);
		boolean eefElementEditorReadOnlyState = isReadOnly(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions);
		if (eefElementEditorReadOnlyState && eExceptions.getTable().isEnabled()) {
			eExceptions.setEnabled(false);
			eExceptions.setToolTipText(EcoreMessages.Exceptions_ReadOnly);
		} else if (!eefElementEditorReadOnlyState && !eExceptions.getTable().isEnabled()) {
			eExceptions.setEnabled(true);
		}
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.ExceptionsPropertiesEditionPart#updateEExceptions()
	 * 
	 */
	public void updateEExceptions() {
	eExceptions.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.ExceptionsPropertiesEditionPart#addFilterEExceptions(ViewerFilter filter)
	 * 
	 */
	public void addFilterToEExceptions(ViewerFilter filter) {
		eExceptionsFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.ExceptionsPropertiesEditionPart#addBusinessFilterEExceptions(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToEExceptions(ViewerFilter filter) {
		eExceptionsBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.parts.ExceptionsPropertiesEditionPart#isContainedInEExceptionsTable(EObject element)
	 * 
	 */
	public boolean isContainedInEExceptionsTable(EObject element) {
		return ((ReferencesTableSettings)eExceptions.getInput()).contains(element);
	}






	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart#getTitle()
	 * 
	 */
	public String getTitle() {
		return EcoreMessages.Exceptions_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
