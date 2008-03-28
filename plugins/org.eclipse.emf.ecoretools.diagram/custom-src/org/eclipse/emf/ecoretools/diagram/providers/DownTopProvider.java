/***********************************************************************
 * Copyright (c) 2008 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 **********************************************************************/
package org.eclipse.emf.ecoretools.diagram.providers;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.providers.TopDownProvider;

/**
 * Override the default behavior until the bug#159558 is fixed
 */
public class DownTopProvider extends TopDownProvider {

	/**
	 * Code snippet to be added in the plugin.xml file : <extension
	 * id="presentationLayoutProvider" name="%ext.presentationLayoutProvider"
	 * point="org.eclipse.gmf.runtime.diagram.ui.layoutProviders">
	 * <layoutProvider
	 * class="org.eclipse.emf.ecoretools.diagram.providers.DownTopProvider">
	 * <Priority name="Medium"/> </layoutProvider> </extension>
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.providers.TopDownProvider#layoutTopDown(org.eclipse.gef.ConnectionEditPart)
	 */
	protected boolean layoutTopDown(ConnectionEditPart poly) {
		return true;
	}
}
