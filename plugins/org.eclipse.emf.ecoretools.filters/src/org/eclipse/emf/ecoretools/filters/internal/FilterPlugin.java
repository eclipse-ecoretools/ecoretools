/***********************************************************************
 * Copyright (c) 2007 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.ecoretools.filters.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * 
 * TODO Describe the class here <br>
 * creation : 13 nov. 07
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class FilterPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.emf.ecoretools.filters";

	public static final String FILTERED_DIAGRAM_TYPE_EXTENSION_ID = "filteredDiagramType";

	// The shared instance
	private static FilterPlugin plugin;

	/**
	 * Creates an extension. If the extension plugin has not been loaded a busy
	 * cursor will be activated during the duration of the load.
	 * 
	 * @param element
	 *            the config element defining the extension
	 * @param classAttribute
	 *            the name of the attribute carrying the class
	 * @return the extension object
	 */
	public static Object createExtension(final IConfigurationElement element, final String classAttribute) throws CoreException {
		try {
			// If plugin has been loaded create extension.
			// Otherwise, show busy cursor then create extension.
			Bundle extensionBundle = Platform.getBundle(element.getDeclaringExtension().getNamespaceIdentifier());
			if (extensionBundle.getState() == Bundle.ACTIVE) {
				return element.createExecutableExtension(classAttribute);
			}

			final Object[] ret = new Object[1];
			final CoreException[] exc = new CoreException[1];
			BusyIndicator.showWhile(null, new Runnable() {

				public void run() {
					try {
						ret[0] = element.createExecutableExtension(classAttribute);
					} catch (CoreException e) {
						exc[0] = e;
					}
				}
			});
			if (exc[0] != null)
				throw exc[0];

			return ret[0];
		} catch (CoreException core) {
			throw core;
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.ERROR, "Unable to create extension.", e));
		}
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static FilterPlugin getInstance() {
		return plugin;
	}

	public static void log(final IStatus status) {
		getInstance().getLog().log(status);
	}

	public static void log(final Throwable thr) {
		String defaultMsg = "No details available."; //$NON-NLS-1$
		String msg = thr.getMessage() == null ? defaultMsg : thr.getMessage();
		IStatus status = new Status(IStatus.ERROR, PLUGIN_ID, 0, msg, thr);
		getInstance().getLog().log(status);
	}

	public static void logError(String error) {
		logError(error, null);
	}

	public static void logError(String error, Throwable throwable) {
		if (error == null && throwable != null) {
			error = throwable.getMessage();
		}
		log(new Status(IStatus.ERROR, FilterPlugin.PLUGIN_ID, IStatus.OK, error, throwable));
	}

	public static void logWarning(final Throwable thr) {
		String defaultMsg = "No details available."; //$NON-NLS-1$
		String msg = thr.getMessage() == null ? defaultMsg : thr.getMessage();
		IStatus status = new Status(IStatus.WARNING, PLUGIN_ID, 0, msg, thr);
		getInstance().getLog().log(status);
	}

	private ResourceBundle resourceBundle;

	private ResourceBundle untranslatedResourceBundle;

	/**
	 * The constructor
	 */
	public FilterPlugin() {
	}

	/**
	 * This method is used to retrieve an Image object from an internal resource
	 * specified by the 'key' parameter.
	 * 
	 * @param key -
	 *            a string that contains the path to the image file, relative to
	 *            'icons' folder and without extension (e.g. for a key like
	 *            'importing/bsw_file' is searched for
	 *            /icons/importinh/bsw_file.gif' file)
	 */
	public Image getImage(String key) {
		ImageRegistry reg = getImageRegistry();
		Image result = reg.get(key);
		if (result == null) {
			reg.put(key, getImageDescriptor("icons/" + key));
			result = reg.get(key);
		}
		return result;
	}

	public String getString(String key) {
		return getString(key, true);
	}

	public String getString(String key, boolean translate) {
		ResourceBundle bundle = translate ? resourceBundle : untranslatedResourceBundle;
		if (bundle == null) {
			if (translate) {
				bundle = resourceBundle = Platform.getResourceBundle(getBundle());
			} else {
				String resourceName = getBundle().getEntry("/").toString() + "plugin.properties";
				try {
					InputStream inputStream = new URL(resourceName).openStream();
					bundle = untranslatedResourceBundle = new PropertyResourceBundle(inputStream);
					inputStream.close();
				} catch (IOException ioException) {
					throw new MissingResourceException("Missing properties: " + resourceName, getClass().getName(), "plugin.properties");
				}
			}
		}
		return bundle.getString(key);
	}

	public String getString(String key, Object param) {
		return getString(key, new Object[] { param }, true);
	}

	public String getString(String key, Object param1, Object param2) {
		return getString(key, new Object[] { param1, param2 }, true);
	}

	public String getString(String key, Object param1, Object param2, Object param3) {
		return getString(key, new Object[] { param1, param2, param3 }, true);
	}

	public String getString(String key, Object[] substitutions) {
		return getString(key, substitutions, true);
	}

	public String getString(String key, Object[] substitutions, boolean translate) {
		return MessageFormat.format(getString(key, translate), substitutions);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

}
