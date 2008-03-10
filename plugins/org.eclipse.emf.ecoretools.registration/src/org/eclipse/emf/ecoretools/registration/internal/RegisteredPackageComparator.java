/*$Id: RegisteredPackageComparator.java,v 1.1 2008/03/10 09:40:02 jlescot Exp $ */

/* **********************************************************************
 * Copyright (c) 2007, 2008 INRIA and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    INRIA - initial API and implementation
 **********************************************************************/

package org.eclipse.emf.ecoretools.registration.internal;

import org.eclipse.jface.viewers.ViewerComparator;

/**
 * Comparator for Registered package
 */
public class RegisteredPackageComparator extends ViewerComparator {

	/** Ascending order */
	static public final int ASCENDING_ORDER = 0;

	/** Descending order */
	static public final int DESCENDING_ORDER = 1;

	private int order;

	/**
	 * Constructor
	 * 
	 * @param order the default order (ascending or descending)
	 */
	public RegisteredPackageComparator(int order) {
		this.order = order;
	}

	/**
	 * Get the order property
	 * 
	 * @return the order property
	 */
	public int getOrder() {
		return order;
	}

}
