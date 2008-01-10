/**
 * Copyright (c) 2008 {INITIAL COPYRIGHT OWNER}
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Anyware Technologies - initial API and implementation
 */
package org.eclipse.emf.ecoretools.diagram.edit.policies;

/**
 * 
 * TODO Describe the class here <br>
 * creation : 10 janv. 08
 * 
 * @author <a href="mailto:gilles.cannenterre@anyware-tech.com">Gilles
 *         Cannenterre</a>
 */
public class EcoretoolsEditPolicyRoles {

	/**
	 * Synchronization on removal Modifyin the semantic model will trigger a
	 * refresh that will only remove orphaned views.
	 */
	public static final String PSEUDO_CANONICAL_ROLE = "PseudoCanonical";

}
