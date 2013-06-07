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
 *
 * $Id: DownTopProvider.java,v 1.3 2008/12/24 13:58:21 jlescot Exp $
 **********************************************************************/
package org.eclipse.emf.ecoretools.diagram.providers;

import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageEditPart;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.providers.TopDownProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Override the default behavior until the bug#159558 is fixed
 */
public class DownTopProvider extends TopDownProvider {

	@Override
	public boolean provides(IOperation operation) {
		View cview = getContainer(operation);
		if (cview == null)
			return false;
		Diagram diag = cview.getDiagram();
		if (diag != null) {
			return EPackageEditPart.MODEL_ID.equals(diag.getType());
		}
		return super.provides(operation);
	}

	/**
	 * Code snippet to be added in the plugin.xml file : <extension
	 * id="presentationLayoutProvider" name="%ext.presentationLayoutProvider"
	 * point="org.eclipse.gmf.runtime.diagram.ui.layoutProviders">
	 * <layoutProvider
	 * class="org.eclipse.emf.ecoretools.diagram.providers.DownTopProvider">
	 * <Priority name="Medium"/> </layoutProvider> </extension>
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.providers.DefaultProvider#layoutTopDown(org.eclipse.gef.ConnectionEditPart)
	 */
	@Override
	protected boolean layoutTopDown(ConnectionEditPart poly) {
		return true;
	}
}
