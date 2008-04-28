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
 * 
 * $Id: TabbedPropertiesPlugin.java,v 1.2 2008/04/28 12:19:08 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.tabbedproperties.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 */
public class TabbedPropertiesPlugin extends AbstractUIPlugin {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "org.eclipse.emf.tabbedproperties"; //$NON-NLS-1$

	/** The shared instance */
	private static TabbedPropertiesPlugin plugin;

	/**
	 * The constructor
	 */
	public TabbedPropertiesPlugin() {
		plugin = this;
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static TabbedPropertiesPlugin getDefault() {
		return plugin;
	}

}
