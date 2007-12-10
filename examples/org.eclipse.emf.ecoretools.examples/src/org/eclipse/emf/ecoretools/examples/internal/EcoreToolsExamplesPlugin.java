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

package org.eclipse.emf.ecoretools.examples.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Creation : 5 Dec. 2007
 * 
 * @author <a href="mailto:jacques.lescot@anyware-tech.com">Jacques LESCOT</a>
 */
public class EcoreToolsExamplesPlugin extends AbstractUIPlugin {

	// The shared instance.
	private static EcoreToolsExamplesPlugin plugin;

	/**
	 * Constructor
	 */
	public EcoreToolsExamplesPlugin() {
		super();
		plugin = this;
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @return the plugin singleton
	 */
	public static EcoreToolsExamplesPlugin getDefault() {
		return plugin;
	}
}
