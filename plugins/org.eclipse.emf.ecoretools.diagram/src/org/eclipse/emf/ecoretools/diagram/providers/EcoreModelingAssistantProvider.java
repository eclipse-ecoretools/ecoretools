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
 * $Id: EcoreModelingAssistantProvider.java,v 1.2 2008/04/28 08:41:30 jlescot Exp $
 **********************************************************************/

package org.eclipse.emf.ecoretools.diagram.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EAnnotationEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EClass2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EClassEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EEnum2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EEnumEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageContentsEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageEditPart;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;
import org.eclipse.emf.ecoretools.diagram.part.Messages;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * @generated
 */
public class EcoreModelingAssistantProvider extends ModelingAssistantProvider {

	/**
	 * @generated
	 */
	@Override
	public List getTypesForPopupBar(IAdaptable host) {
		IGraphicalEditPart editPart = (IGraphicalEditPart) host.getAdapter(IGraphicalEditPart.class);
		if (editPart instanceof EClassEditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EAttribute_2001);
			types.add(EcoreElementTypes.EOperation_2002);
			return types;
		}
		if (editPart instanceof EAnnotationEditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EStringToStringMapEntry_2007);
			return types;
		}
		if (editPart instanceof EEnumEditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EEnumLiteral_2006);
			return types;
		}
		if (editPart instanceof EClass2EditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EAttribute_2001);
			types.add(EcoreElementTypes.EOperation_2002);
			return types;
		}
		if (editPart instanceof EEnum2EditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EEnumLiteral_2006);
			return types;
		}
		if (editPart instanceof EPackageContentsEditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EClass_2003);
			types.add(EcoreElementTypes.EDataType_2004);
			types.add(EcoreElementTypes.EEnum_2005);
			return types;
		}
		if (editPart instanceof EPackageEditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EClass_1001);
			types.add(EcoreElementTypes.EPackage_1002);
			types.add(EcoreElementTypes.EAnnotation_1003);
			types.add(EcoreElementTypes.EDataType_1004);
			types.add(EcoreElementTypes.EEnum_1005);
			return types;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	@Override
	public List getRelTypesOnSource(IAdaptable source) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		if (sourceEditPart instanceof EClassEditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EReference_3002);
			types.add(EcoreElementTypes.EClassESuperTypes_3003);
			return types;
		}
		if (sourceEditPart instanceof EAnnotationEditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EAnnotationReferences_3001);
			return types;
		}
		if (sourceEditPart instanceof EClass2EditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EReference_3002);
			types.add(EcoreElementTypes.EClassESuperTypes_3003);
			return types;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	@Override
	public List getRelTypesOnTarget(IAdaptable target) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		if (targetEditPart instanceof EClassEditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EClassESuperTypes_3003);
			return types;
		}
		if (targetEditPart instanceof EClass2EditPart) {
			List types = new ArrayList();
			types.add(EcoreElementTypes.EClassESuperTypes_3003);
			return types;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	@Override
	public List getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		if (sourceEditPart instanceof EClassEditPart) {
			List types = new ArrayList();
			if (targetEditPart instanceof EClassEditPart) {
				types.add(EcoreElementTypes.EClassESuperTypes_3003);
			}
			if (targetEditPart instanceof EClass2EditPart) {
				types.add(EcoreElementTypes.EClassESuperTypes_3003);
			}
			return types;
		}
		if (sourceEditPart instanceof EAnnotationEditPart) {
			List types = new ArrayList();
			return types;
		}
		if (sourceEditPart instanceof EClass2EditPart) {
			List types = new ArrayList();
			if (targetEditPart instanceof EClassEditPart) {
				types.add(EcoreElementTypes.EClassESuperTypes_3003);
			}
			if (targetEditPart instanceof EClass2EditPart) {
				types.add(EcoreElementTypes.EClassESuperTypes_3003);
			}
			return types;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	@Override
	public List getTypesForSource(IAdaptable target, IElementType relationshipType) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		if (targetEditPart instanceof EClassEditPart) {
			List types = new ArrayList();
			if (relationshipType == EcoreElementTypes.EClassESuperTypes_3003) {
				types.add(EcoreElementTypes.EClass_1001);
			}
			if (relationshipType == EcoreElementTypes.EClassESuperTypes_3003) {
				types.add(EcoreElementTypes.EClass_2003);
			}
			return types;
		}
		if (targetEditPart instanceof EClass2EditPart) {
			List types = new ArrayList();
			if (relationshipType == EcoreElementTypes.EClassESuperTypes_3003) {
				types.add(EcoreElementTypes.EClass_1001);
			}
			if (relationshipType == EcoreElementTypes.EClassESuperTypes_3003) {
				types.add(EcoreElementTypes.EClass_2003);
			}
			return types;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	@Override
	public List getTypesForTarget(IAdaptable source, IElementType relationshipType) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		if (sourceEditPart instanceof EClassEditPart) {
			List types = new ArrayList();
			if (relationshipType == EcoreElementTypes.EClassESuperTypes_3003) {
				types.add(EcoreElementTypes.EClass_1001);
			}
			if (relationshipType == EcoreElementTypes.EClassESuperTypes_3003) {
				types.add(EcoreElementTypes.EClass_2003);
			}
			return types;
		}
		if (sourceEditPart instanceof EAnnotationEditPart) {
			List types = new ArrayList();
			return types;
		}
		if (sourceEditPart instanceof EClass2EditPart) {
			List types = new ArrayList();
			if (relationshipType == EcoreElementTypes.EClassESuperTypes_3003) {
				types.add(EcoreElementTypes.EClass_1001);
			}
			if (relationshipType == EcoreElementTypes.EClassESuperTypes_3003) {
				types.add(EcoreElementTypes.EClass_2003);
			}
			return types;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	@Override
	public EObject selectExistingElementForSource(IAdaptable target, IElementType relationshipType) {
		return selectExistingElement(target, getTypesForSource(target, relationshipType));
	}

	/**
	 * @generated
	 */
	@Override
	public EObject selectExistingElementForTarget(IAdaptable source, IElementType relationshipType) {
		return selectExistingElement(source, getTypesForTarget(source, relationshipType));
	}

	/**
	 * @generated
	 */
	protected EObject selectExistingElement(IAdaptable host, Collection types) {
		if (types.isEmpty()) {
			return null;
		}
		IGraphicalEditPart editPart = (IGraphicalEditPart) host.getAdapter(IGraphicalEditPart.class);
		if (editPart == null) {
			return null;
		}
		Diagram diagram = (Diagram) editPart.getRoot().getContents().getModel();
		Collection elements = new HashSet();
		for (Iterator it = diagram.getElement().eAllContents(); it.hasNext();) {
			EObject element = (EObject) it.next();
			if (isApplicableElement(element, types)) {
				elements.add(element);
			}
		}
		if (elements.isEmpty()) {
			return null;
		}
		return selectElement((EObject[]) elements.toArray(new EObject[elements.size()]));
	}

	/**
	 * @generated
	 */
	protected boolean isApplicableElement(EObject element, Collection types) {
		IElementType type = ElementTypeRegistry.getInstance().getElementType(element);
		return types.contains(type);
	}

	/**
	 * @generated
	 */
	protected EObject selectElement(EObject[] elements) {
		Shell shell = Display.getCurrent().getActiveShell();
		ILabelProvider labelProvider = new AdapterFactoryLabelProvider(EcoreDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, labelProvider);
		dialog.setMessage(Messages.EcoreModelingAssistantProviderMessage);
		dialog.setTitle(Messages.EcoreModelingAssistantProviderTitle);
		dialog.setMultipleSelection(false);
		dialog.setElements(elements);
		EObject selected = null;
		if (dialog.open() == Window.OK) {
			selected = (EObject) dialog.getFirstResult();
		}
		return selected;
	}
}
