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
import org.eclipse.emf.ecoretools.design.properties.parts.EcoreViewsRepository;
import org.eclipse.emf.ecoretools.design.properties.parts.ExceptionsPropertiesEditionPart;
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
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.ui.widgets.TabElementTreeSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableContentProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

// End of user code

/**
 * 
 * 
 */
public class ExceptionsPropertiesEditionPartImpl extends CompositePropertiesEditionPart implements ISWTPropertiesEditionPart, ExceptionsPropertiesEditionPart {

	protected ReferencesTable eExceptions;
	protected List<ViewerFilter> eExceptionsBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> eExceptionsFilters = new ArrayList<ViewerFilter>();



	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public ExceptionsPropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
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
		CompositionSequence exceptionsStep = new BindingCompositionSequence(propertiesEditionComponent);
		exceptionsStep
			.addStep(EcoreViewsRepository.Exceptions.Exceptions_.class)
			.addStep(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions);
		
		
		composer = new PartComposer(exceptionsStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == EcoreViewsRepository.Exceptions.Exceptions_.class) {
					return createExceptionsGroup(parent);
				}
				if (key == EcoreViewsRepository.Exceptions.Exceptions_.eExceptions) {
					return createEExceptionsAdvancedReferencesTable(parent);
				}
				return parent;
			}
		};
		composer.compose(view);
	}

	/**
	 * 
	 */
	protected Composite createExceptionsGroup(Composite parent) {
		Group exceptionsGroup = new Group(parent, SWT.NONE);
		exceptionsGroup.setText(EcoreMessages.ExceptionsPropertiesEditionPart_ExceptionsGroupLabel);
		GridData exceptionsGroupData = new GridData(GridData.FILL_HORIZONTAL);
		exceptionsGroupData.horizontalSpan = 3;
		exceptionsGroup.setLayoutData(exceptionsGroupData);
		GridLayout exceptionsGroupLayout = new GridLayout();
		exceptionsGroupLayout.numColumns = 3;
		exceptionsGroup.setLayout(exceptionsGroupLayout);
		return exceptionsGroup;
	}

	/**
	 * 
	 */
	protected Composite createEExceptionsAdvancedReferencesTable(Composite parent) {
		String label = getDescription(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions, EcoreMessages.ExceptionsPropertiesEditionPart_EExceptionsLabel);		 
		this.eExceptions = new ReferencesTable(label, new ReferencesTableListener() {
			public void handleAdd() { addEExceptions(); }
			public void handleEdit(EObject element) { editEExceptions(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveEExceptions(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromEExceptions(element); }
			public void navigateTo(EObject element) { }
		});
		this.eExceptions.setHelpText(propertiesEditionComponent.getHelpContent(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions, EcoreViewsRepository.SWT_KIND));
		this.eExceptions.createControls(parent);
		this.eExceptions.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ExceptionsPropertiesEditionPartImpl.this, EcoreViewsRepository.Exceptions.Exceptions_.eExceptions, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData eExceptionsData = new GridData(GridData.FILL_HORIZONTAL);
		eExceptionsData.horizontalSpan = 3;
		this.eExceptions.setLayoutData(eExceptionsData);
		this.eExceptions.disableMove();
		eExceptions.setID(EcoreViewsRepository.Exceptions.Exceptions_.eExceptions);
		eExceptions.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ExceptionsPropertiesEditionPartImpl.this, EcoreViewsRepository.Exceptions.Exceptions_.eExceptions,
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ExceptionsPropertiesEditionPartImpl.this, EcoreViewsRepository.Exceptions.Exceptions_.eExceptions, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		eExceptions.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromEExceptions(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ExceptionsPropertiesEditionPartImpl.this, EcoreViewsRepository.Exceptions.Exceptions_.eExceptions, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
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
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.ExceptionsPropertiesEditionPart#initEExceptions(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
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
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.ExceptionsPropertiesEditionPart#updateEExceptions()
	 * 
	 */
	public void updateEExceptions() {
	eExceptions.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.ExceptionsPropertiesEditionPart#addFilterEExceptions(ViewerFilter filter)
	 * 
	 */
	public void addFilterToEExceptions(ViewerFilter filter) {
		eExceptionsFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.ExceptionsPropertiesEditionPart#addBusinessFilterEExceptions(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToEExceptions(ViewerFilter filter) {
		eExceptionsBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecoretools.design.properties.ecore.parts.ExceptionsPropertiesEditionPart#isContainedInEExceptionsTable(EObject element)
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
