/***********************************************************************
 * Copyright (c) 2009 Anyware Technologies
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Jacques Lescot (Anyware Technologies) - initial API and implementation
 *
 * $Id: ChangeActiveDiagramAction.java,v 1.1 2009/03/19 14:35:47 jlescot Exp $
 **********************************************************************/
package org.eclipse.emf.ecoretools.diagram.edit.actions;

import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.action.Action;

/**
 * Create a new Diagram linked with a model object. <br>
 * 
 * @author <a href="mailto:jacques.lescot@anyware-tech.com">Jacques LESCOT</a>
 */
public class ChangeActiveDiagramAction extends Action {

	private EcoreDiagramEditor editor;

	private Diagram newDiagram;

	/**
	 * Constructor
	 * 
	 * @param ed
	 *            the Editor
	 * @param diag
	 *            the new active diagram
	 */
	public ChangeActiveDiagramAction(EcoreDiagramEditor ed, Diagram diag) {
		super("Change Active Diagram");
		this.editor = ed;
		this.newDiagram = diag;
	}

	/**
	 * Execute the Action
	 * 
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		editor.setDiagram(newDiagram);
	}

}
