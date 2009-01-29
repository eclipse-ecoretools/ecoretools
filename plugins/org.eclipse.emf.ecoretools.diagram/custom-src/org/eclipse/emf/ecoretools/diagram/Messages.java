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
 * $Id: Messages.java,v 1.3 2009/01/29 10:02:08 jlescot Exp $
 **********************************************************************/
package org.eclipse.emf.ecoretools.diagram;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.ecoretools.diagram.messages"; //$NON-NLS-1$

	public static String CommandName_InitializeAndLayoutDiagram;

	public static String CommandName_UpdateEditPart;

	public static String EcoreAbstractExpression_ExpressionEvaluationFailure;

	public static String EcoreCreationWizard_ErrorOnCreation;

	public static String EcoreCreationWizardPage_Browse;

	public static String EcoreCreationWizardPage_ChooseAnEPackageElement;

	public static String EcoreCreationWizardPage_ChooseAnExistingDomainFile;

	public static String EcoreCreationWizardPage_ChooseDestinationDirectory;

	public static String EcoreCreationWizardPage_ChooseExistingDomainFile;

	public static String EcoreCreationWizardPage_CreateFromExistingModel;

	public static String EcoreCreationWizardPage_CreateNewModel;

	public static String EcoreCreationWizardPage_DiagramFileAlreadyExists;

	public static String EcoreCreationWizardPage_Directory;

	public static String EcoreCreationWizardPage_DomainFileAlreadyExists;

	public static String EcoreCreationWizardPage_DomainFileName;

	public static String EcoreCreationWizardPage_DomainFileNotEmpty;

	public static String EcoreCreationWizardPage_DomainModel;

	public static String EcoreCreationWizardPage_FilesAlreadyExist;

	public static String EcoreCreationWizardPage_InitializeWithExistingElements;

	public static String EcoreCreationWizardPage_Select;

	public static String EcoreCreationWizardPage_UnvalidDestinationContainer;

	public static String EcoreCreationWizardPageExtensionError;

	public static String EcoreDiagramContentInitializer_IncorrectDiagramAsParameter;

	public static String EcoreDiagramContentInitializer_IncorrectElementSpecified;

	public static String EcoreVisualIDRegistry_UnableToParseView;

	public static String EReferenceUtils_CanNotExecute;

	public static String FigureFromLabelUtils_UnknownElement;

	public static String FigureFromLabelUtils_UnresolvedElement;

	public static String InitializeAndLayoutDiagramCommand_UnableToProceed;

	public static String ManageDiagramsDialog_CloseDialog;

	public static String ManageDiagramsDialog_Create;

	public static String ManageDiagramsDialog_CreateNewDiagram;

	public static String ManageDiagramsDialog_Delete;

	public static String ManageDiagramsDialog_DeleteSelectedDiagram;

	public static String ManageDiagramsDialog_InitializeDiagramContents;

	public static String ManageDiagramsDialog_ManageDiagrams;

	public static String ManageDiagramsDialog_Open;

	public static String ManageDiagramsDialog_OpenSelectedDiagram;

	public static String OpenDiagramEditPolicy_CanNotCreateDiagram;

	public static String OpenDiagramEditPolicy_CanNotOpen;

	public static String OpenDiagramEditPolicy_OpenDiagram;

	public static String OpenDiagramEditPolicy_OperationFailed;

	public static String RemoveDiagramCommand_CanNotRemove;

	public static String RemoveDiagramCommand_RemoveDiagram;

	public static String RestoreRelatedLinksAction_RestoreRelatedElements;

	public static String RestoreRelatedLinksAction_RestoreRelatedElements_tooltip;

	public static String RestoreRelatedLinksAction_RestoreRelatedLinks;

	public static String RestoreRelatedLinksCommand_RestoreRelatedLinks;

	public static String RestoreRelatedLinksCommand_ShowView;

	public static String RestoreRelatedMissingNodesCommand_CreateMissingNodes;

	public static String UpdateEditPartCommand_UnableToProceed;

	public static String UpdateLinkedEReferenceDeferredCommand_UnableToProceed;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
