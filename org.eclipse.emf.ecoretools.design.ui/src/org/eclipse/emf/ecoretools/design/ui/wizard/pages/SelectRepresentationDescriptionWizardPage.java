/*******************************************************************************
 * Copyright (c) 2016, 2024 Obeo
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.ui.wizard.pages;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.common.tools.api.util.StringUtil;
import org.eclipse.sirius.ui.tools.api.views.ViewHelper;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.sirius.viewpoint.provider.SiriusEditPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;

/**
 * A wizard page to select representation descriptions based on the Viewpoints
 * currently availables.
 * 
 * @author Cedric Brun <cedric.brun@obeo.fr>
 */
public class SelectRepresentationDescriptionWizardPage extends WizardPage
		implements Supplier<RepresentationDescription> {

	private class ViewpointsTableLabelProvider extends AdapterFactoryLabelProvider implements ITableLabelProvider {

		public ViewpointsTableLabelProvider() {
			super(ViewHelper.INSTANCE.createAdapterFactory());
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			Image image = null;
			if (columnIndex == 0) {
				if (element instanceof Viewpoint) {
					final Viewpoint vp = (Viewpoint) element;
					if (vp.getIcon() != null && vp.getIcon().length() > 0) {
						final ImageDescriptor desc = SiriusEditPlugin.Implementation.findImageDescriptor(vp.getIcon());
						if (desc != null) {
							image = SiriusEditPlugin.getPlugin().getImage(desc);
							image = getEnhancedImage(image, vp);
						}
					}
					if (image == null) {
						image = SiriusEditPlugin.getPlugin()
								.getImage(SiriusEditPlugin.getPlugin().getItemImageDescriptor(vp));
						image = getEnhancedImage(image, vp);
					}
				} else {
					image = super.getImage(element);
				}
			}
			return image;
		}

		private Image getEnhancedImage(final Image image, final Viewpoint viewpoint) {
			if (ViewpointRegistry.getInstance().isFromPlugin(viewpoint) && image != null) {
				return SiriusEditPlugin.getPlugin()
						.getImage(getOverlayedDescriptor(image, "icons/full/ovr16/plugin_ovr.gif"));
			}
			return image;
		}

		private ImageDescriptor getOverlayedDescriptor(final Image baseImage, final String decoratorPath) {
			final ImageDescriptor decoratorDescriptor = SiriusEditPlugin.Implementation
					.getBundledImageDescriptor(decoratorPath);
			return new DecorationOverlayIcon(baseImage, decoratorDescriptor, IDecoration.BOTTOM_LEFT);
		}

	}

	private static final String PAGE_MESSAGE = "Select the type of representation to create.";

	/** The title of the page. */
	private static final String PAGE_TITLE = "Select type of representation";

	/** The browser for documentation */
	private Browser browser;

	/**
	 * The list of selected representation descriptions
	 */
	private Set<RepresentationDescription> descriptions;

	private Composite pageComposite;

	/**
	 * List of description names that must be activate by default
	 */
	private Set<String> representationsURIsToActivateByDefault;

	private Supplier<Session> sessionSupplier;

	/** The table viewer. */
	private TableViewer tableViewer;

	/**
	 * Create a new <code>RepresentationSelectionWizardPage</code> with default
	 * viewpoints activation. This constructor makes this page optional.
	 * 
	 * @param supplier
	 *            the session
	 * @param representationsToSelectByDefault
	 *            list of viewpoints URIS to activate by default (in the form of
	 *            viewpoint:/someplugin/Some Viewpoint.
	 */
	public SelectRepresentationDescriptionWizardPage(final Supplier<Session> supplier,
			Collection<String> representationsToSelectByDefault) {
		super(PAGE_TITLE);
		this.setTitle(PAGE_TITLE);
		this.setMessage(PAGE_MESSAGE);
		this.sessionSupplier = supplier;
		this.descriptions = new LinkedHashSet<>();
		this.representationsURIsToActivateByDefault = new HashSet<>(representationsToSelectByDefault);
	}

	private StringBuilder appendCss(StringBuilder content) {
		Font currentFont = JFaceResources.getDialogFont();
		FontData data = currentFont.getFontData()[0];
		String fontName = data.getName();
		int fontHeight = data.getHeight() + 3;
		content.append("<style type=\"text/css\">");
		content.append("body{font-family:" + fontName + ",Arial, sans-serif;}");
		content.append("body{font-size:" + fontHeight + "px;}");
		content.append("</style>");
		return content;
	}

	private SelectRepresentationDescriptionWizardPage begin(StringBuilder content) {
		content.append("<html>");
		return this;
	}

	private SelectRepresentationDescriptionWizardPage body(StringBuilder content,
			RepresentationDescription repDescription) {
		content.append("<body>");

		if (repDescription == null) {
			content.append("<br><br><center><b>Documentation</b></center>");
		} else {
			final String endUserDocumentation = repDescription.getDocumentation();
			if (!StringUtil.isEmpty(endUserDocumentation))
				content.append(repDescription.getEndUserDocumentation());
			else
				content.append("no documentation for this representation");
		}
		content.append("</body>");
		return this;
	}

	/*
	 * The following code (HTML handling ) and methods could move to another
	 * class.
	 */
	private boolean containsHTMLDocumentation(RepresentationDescription description) {
		if (description != null) {
			final String doc = description.getDocumentation();
			if (!StringUtil.isEmpty(doc))
				return doc.startsWith("<html>");
		}
		return false;
	}

	private Browser createBrowser(final Composite parent) {

		try {
			Browser aBrowser = new Browser(parent, SWT.NONE);
			final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
			aBrowser.setLayoutData(gridData);
			return aBrowser;
		} catch (SWTError error) {
			/*
			 * the browser could not be created, do not display further
			 * information
			 */
			return null;
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(final Composite parent) {
		initializeDialogUnits(parent);

		pageComposite = new Composite(parent, SWT.NONE);
		pageComposite.setLayout(GridLayoutFactory.swtDefaults().numColumns(2).equalWidth(true).create());
		pageComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		this.tableViewer = createTableViewer(pageComposite);

		this.browser = createBrowser(pageComposite);
		setBrowserInput(null);

		setControl(pageComposite);
	}

	/**
	 * Create the table viewer.
	 * 
	 * @param parent
	 *            the parent composite.
	 * @return the table viewer.
	 */
	private TableViewer createTableViewer(final Composite parent) {
		final int style = SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;

		TableViewer viewer = new TableViewer(parent, style);

		final GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		viewer.getControl().setLayoutData(gridData);

		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new ViewpointsTableLabelProvider());

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IStructuredSelection selection) {
					descriptions.clear();
					for (Object selectedElement : selection) {
                        if (selectedElement instanceof RepresentationDescription representationDescription) {
                            descriptions.add(representationDescription);
                        }
                    }
				}
			}
		});

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object firstElement = ((IStructuredSelection) selection).getFirstElement();
					if (firstElement instanceof RepresentationDescription) {
						setBrowserInput((RepresentationDescription) firstElement);
					}
				}
			}
		});

		return viewer;
	}

	private String end(StringBuilder content) {
		content.append("</html>");
		return content.toString();
	}

	private void extractUrlToRewrite(String document, Set<String> urlToRewrite) {
		String imgSrcPattern = "img src=\"";
		int patternStartIndex = document.indexOf(imgSrcPattern);
		if (patternStartIndex != -1) {
			int imgSrcStartIndex = patternStartIndex + imgSrcPattern.length();
			int imgSrcStopIndex = document.indexOf("\"", imgSrcStartIndex);
			if (imgSrcStopIndex != -1) {
				String newToRewrite = document.substring(imgSrcStartIndex, imgSrcStopIndex);
				urlToRewrite.add(newToRewrite);
				extractUrlToRewrite(document.substring(imgSrcStopIndex), urlToRewrite);
			}
		}
	}

	public RepresentationDescription get() {
		if (descriptions.size() > 0) {
			return descriptions.iterator().next();
		}
		return null;
	}

	private Collection<RepresentationDescription> getAvailableDescriptions(Session session) {
		List<RepresentationDescription> result = new ArrayList<>();
		for (Viewpoint vp : session.getSelectedViewpoints(false)) {
			result.addAll(vp.getOwnedRepresentations());
		}
		return result;

	}

	private String getContentWhenHtml(RepresentationDescription viewpoint) {

		final String document = viewpoint.getDocumentation();

		Set<String> urlToRewrite = new LinkedHashSet<>();
		extractUrlToRewrite(document, urlToRewrite);

		return rewriteURLs(viewpoint, document, urlToRewrite);
	}

	private String getContentWhenNoHtml(RepresentationDescription viewpoint) {
		StringBuilder content = new StringBuilder();
		return begin(content).head(content).body(content, viewpoint).end(content);
	}

	private SelectRepresentationDescriptionWizardPage head(StringBuilder content) {
		content.append("<head>");
		appendCss(content);
		content.append("</head>");
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		String errorMessage = null;
		boolean complete = false;
		if (descriptions.size() == 0) {
			errorMessage = "You should select at least one type of representation.";
		} else {
			complete = true;
		}
		setErrorMessage(errorMessage);
		return complete;
	}

	private String rewriteURL(RepresentationDescription viewpoint, String url) {
		final URI uri = viewpoint.eResource().getURI();
		String pluginId = uri.segment(1);

		String rewrittenURL = "";

		if (uri.isPlatformPlugin()) {
			Bundle bundle = Platform.getBundle(pluginId);
			URL imageURL = bundle.getEntry(url);
			rewrittenURL = imageURL != null ? imageURL.toString() : rewrittenURL;
			if (imageURL != null) {
				try {
					URL fileURL = FileLocator.toFileURL(imageURL);
					rewrittenURL = fileURL.toString();
				} catch (IOException e) {
					// do nothing
				}
			}

		} else {
			final IWorkspace workspace = ResourcesPlugin.getWorkspace();
			final IPath path = new Path("/" + pluginId + url);
			if (workspace.getRoot().exists(path)) {
				IResource resource = workspace.getRoot().findMember(path);
				rewrittenURL = resource.getLocation().toFile().toURI().toString();
			}
		}

		return rewrittenURL;
	}

	private String rewriteURLs(RepresentationDescription viewpoint, String document, Set<String> urls) {

		String newDocument = document;

		for (final String url : urls) {
			newDocument = newDocument.replace(url, rewriteURL(viewpoint, url));
		}

		StringBuilder css = new StringBuilder();
		appendCss(css);
		String headClose = "</head>";
		newDocument = newDocument.replace(headClose, css.append(headClose));

		return newDocument;
	}

	/***
	 * Set the browser input.A jface like browser viewer would have been better.
	 * 
	 * @param viewpoint
	 *            the viewpoint to document
	 */
	protected void setBrowserInput(final RepresentationDescription viewpoint) {

		/* browser may be null if its creation fail */
		if (browser != null) {
			String content = null;
			if (containsHTMLDocumentation(viewpoint)) {
				content = getContentWhenHtml(viewpoint);
			} else {
				content = getContentWhenNoHtml(viewpoint);
			}
			browser.setText(content);
		}
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			/*
			 * shouldn't we make sure the "get" is called in a job ?
			 */
			Session s = sessionSupplier.get();
			if (s != null) {
				tableViewer.setInput(getAvailableDescriptions(s));
				if (!representationsURIsToActivateByDefault.isEmpty()) {
					// Search the viewpoints to activate by their name
					for (int i = 0; i < tableViewer.getTable().getItemCount(); i++) {
						Object object = tableViewer.getElementAt(i);
						if (object instanceof RepresentationDescription) {
							RepresentationDescription representationDescription = (RepresentationDescription) object;
							if (representationDescription.getName() != null && representationsURIsToActivateByDefault
									.contains(representationDescription.getName())) {
								descriptions.add((RepresentationDescription) object);
							}
						}
					}
					if (!descriptions.isEmpty()) {
						// Set the focus on the first one
						tableViewer.setSelection(new StructuredSelection(descriptions.iterator().next()));
					}
				}
			}
		}
		super.setVisible(visible);
	}
}
