/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.properties;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.eef.runtime.EEFRuntimePlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyComposite;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyTitle;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Utility class in order to inject a validation label in a {@link TabbedPropertySheetPage}.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class ValidationMessageInjector {

	private static final String VALIDATION_MESSAGE_KEY = "org.eclipse.emf.eef.runtime.part.impl.util.ValidationMessageInjector";

	private TabbedPropertySheetPage page;

	private TabbedPropertyComposite propertyComposite;

	private TabbedPropertyTitle propertyTitle;

	private CLabel propertyTitleLabel;

	/**
	 * @param page
	 *            to inject.
	 */
	public ValidationMessageInjector(TabbedPropertySheetPage page) {
		this.page = page;
		initialize();
	}

	public void setMessage(String message, int severity) {
		if (isValide()) {
			if (severity == IStatus.ERROR) {
				CLabel messageControl = getMessage();
				messageControl.setText("Errors");
				messageControl.setForeground(propertyComposite.getDisplay().getSystemColor(SWT.COLOR_RED));
				messageControl.setImage(EEFRuntimePlugin.getImage(EEFRuntimePlugin.ICONS_16x16
						+ "ValidationErrors.gif"));
				messageControl.setToolTipText(message);
			} else if (severity == IStatus.WARNING) {
				CLabel messageControl = getMessage();
				messageControl.setText("Warnings");
				messageControl.setForeground(propertyComposite.getDisplay().getSystemColor(
						SWT.COLOR_DARK_YELLOW));
				messageControl.setImage(EEFRuntimePlugin.getImage(EEFRuntimePlugin.ICONS_16x16
						+ "ValidationWarnings.gif"));
				messageControl.setToolTipText(message);
			} else {
				dispose();
			}
		}
	}

	public final void dispose() {
		if (isValide()) {
			CLabel message = getMessage();
			if (message != null && !message.isDisposed()) {
				FormData data = (FormData)message.getLayoutData();
				data.left = new FormAttachment(0, 0);
				data.right = new FormAttachment(0, 0);
				data.top = new FormAttachment(0, 0);
				data.bottom = new FormAttachment(0, 0);
				message.setVisible(false);
				propertyTitle.layout();
				message.dispose();
			}
			if (propertyTitleLabel.getLayoutData() instanceof FormData) {
				FormData data = (FormData)propertyTitleLabel.getLayoutData();
				data.right = new FormAttachment(100, 0);
			}
			propertyTitle.layout();
			propertyComposite.setData(VALIDATION_MESSAGE_KEY, null);
		}
	}

	/**
	 * @return <code>true</code> if the injector has been correctly initialized.
	 */
	public final boolean isValide() {
		return propertyComposite != null && !propertyComposite.isDisposed() && propertyTitleLabel != null;
	}

	private void initialize() {
		if (page.getControl() instanceof TabbedPropertyComposite) {
			propertyComposite = (TabbedPropertyComposite)page.getControl();
			propertyTitle = searchTitle(propertyComposite);
			if (propertyTitle != null) {
				propertyTitleLabel = searchLabel(propertyTitle);
			}
		}

	}

	private CLabel getMessage() {
		if (propertyComposite.getData(VALIDATION_MESSAGE_KEY) != null
				&& !(((CLabel)propertyComposite.getData(VALIDATION_MESSAGE_KEY)).isDisposed())) {
			return (CLabel)propertyComposite.getData(VALIDATION_MESSAGE_KEY);
		} else {
			CLabel message = instanciateMessageAt(propertyTitle, propertyTitleLabel);
			relayoutTitleLabel(propertyTitleLabel);
			propertyTitle.layout();
			propertyComposite.layout();
			propertyComposite.setData(VALIDATION_MESSAGE_KEY, message);
			return message;
		}
	}

	private TabbedPropertyTitle searchTitle(Composite composite) {
		return (TabbedPropertyTitle)searchComposite(composite, TabbedPropertyTitle.class);
	}

	private CLabel searchLabel(TabbedPropertyTitle title) {
		return (CLabel)searchComposite(title, CLabel.class);
	}

	private Composite searchComposite(Composite composite, Class<? extends Composite> clazz) {
		for (int i = 0; i < composite.getChildren().length; i++) {
			Control control = composite.getChildren()[i];
			if (clazz.isInstance(control)) {
				return (Composite)control;
			} else if (control instanceof Composite) {
				Composite searchedComposite = searchComposite((Composite)control, clazz);
				if (searchedComposite != null) {
					return searchedComposite;
				}
			}
		}
		return null;
	}

	private CLabel instanciateMessageAt(final TabbedPropertyTitle title, final CLabel titleLabel) {
		TabbedPropertySheetWidgetFactory factory = page.getWidgetFactory();
		CLabel result = factory.createCLabel(title, "default");
		result.setBackground(new Color[] {factory.getColors().getColor(IFormColors.H_GRADIENT_END),
				factory.getColors().getColor(IFormColors.H_GRADIENT_START)}, new int[] {100}, true);
		FormData errordata = new FormData();
		errordata.left = new FormAttachment(90, 5);
		errordata.top = new FormAttachment(0, 0);
		errordata.right = new FormAttachment(100, 5);
		errordata.bottom = new FormAttachment(100, 0);
		result.setLayoutData(errordata);
		return result;
	}

	private boolean relayoutTitleLabel(CLabel label) {
		if (label.getLayoutData() instanceof FormData) {
			FormData data = (FormData)label.getLayoutData();
			data.right = new FormAttachment(90, -5);
			return true;
		}
		return false;
	}

}
