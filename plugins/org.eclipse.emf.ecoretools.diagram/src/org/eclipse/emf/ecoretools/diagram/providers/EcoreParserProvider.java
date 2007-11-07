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

package org.eclipse.emf.ecoretools.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EAnnotationSourceEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EAttributeEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EClassName2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EClassNameEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EDataTypeInstanceClass2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EDataTypeInstanceClassEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EDataTypeName2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EDataTypeNameEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EEnumLiteralEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EEnumName2EditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EEnumNameEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EOperationEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EPackageNameEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EReferenceLowerBoundUpperBoundEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EReferenceNameEditPart;
import org.eclipse.emf.ecoretools.diagram.edit.parts.EStringToStringMapEntryEditPart;
import org.eclipse.emf.ecoretools.diagram.parsers.MessageFormatParser;
import org.eclipse.emf.ecoretools.diagram.part.EcoreVisualIDRegistry;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class EcoreParserProvider extends AbstractProvider implements IParserProvider {

	/**
	 * @generated
	 */
	private IParser eClassName_4001Parser;

	/**
	 * @generated
	 */
	private IParser getEClassName_4001Parser() {
		if (eClassName_4001Parser == null) {
			eClassName_4001Parser = createEClassName_4001Parser();
		}
		return eClassName_4001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEClassName_4001Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getENamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser ePackageName_4006Parser;

	/**
	 * @generated
	 */
	private IParser getEPackageName_4006Parser() {
		if (ePackageName_4006Parser == null) {
			ePackageName_4006Parser = createEPackageName_4006Parser();
		}
		return ePackageName_4006Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEPackageName_4006Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getENamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eAnnotationSource_4007Parser;

	/**
	 * @generated
	 */
	private IParser getEAnnotationSource_4007Parser() {
		if (eAnnotationSource_4007Parser == null) {
			eAnnotationSource_4007Parser = createEAnnotationSource_4007Parser();
		}
		return eAnnotationSource_4007Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEAnnotationSource_4007Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getEAnnotation_Source(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eDataTypeName_4008Parser;

	/**
	 * @generated
	 */
	private IParser getEDataTypeName_4008Parser() {
		if (eDataTypeName_4008Parser == null) {
			eDataTypeName_4008Parser = createEDataTypeName_4008Parser();
		}
		return eDataTypeName_4008Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEDataTypeName_4008Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getENamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eDataTypeInstanceClassName_4009Parser;

	/**
	 * @generated
	 */
	private IParser getEDataTypeInstanceClassName_4009Parser() {
		if (eDataTypeInstanceClassName_4009Parser == null) {
			eDataTypeInstanceClassName_4009Parser = createEDataTypeInstanceClassName_4009Parser();
		}
		return eDataTypeInstanceClassName_4009Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEDataTypeInstanceClassName_4009Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getEClassifier_InstanceClassName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		parser.setViewPattern("<<javaclass>> {0}");
		parser.setEditorPattern("<<javaclass>> {0}");
		parser.setEditPattern("<<javaclass>> {0}");
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eEnumName_4010Parser;

	/**
	 * @generated
	 */
	private IParser getEEnumName_4010Parser() {
		if (eEnumName_4010Parser == null) {
			eEnumName_4010Parser = createEEnumName_4010Parser();
		}
		return eEnumName_4010Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEEnumName_4010Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getENamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eAttribute_2001Parser;

	/**
	 * @generated
	 */
	private IParser getEAttribute_2001Parser() {
		if (eAttribute_2001Parser == null) {
			eAttribute_2001Parser = createEAttribute_2001Parser();
		}
		return eAttribute_2001Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEAttribute_2001Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getENamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eOperation_2002Parser;

	/**
	 * @generated
	 */
	private IParser getEOperation_2002Parser() {
		if (eOperation_2002Parser == null) {
			eOperation_2002Parser = createEOperation_2002Parser();
		}
		return eOperation_2002Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEOperation_2002Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getENamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eClassName_4002Parser;

	/**
	 * @generated
	 */
	private IParser getEClassName_4002Parser() {
		if (eClassName_4002Parser == null) {
			eClassName_4002Parser = createEClassName_4002Parser();
		}
		return eClassName_4002Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEClassName_4002Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getENamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eDataTypeName_4003Parser;

	/**
	 * @generated
	 */
	private IParser getEDataTypeName_4003Parser() {
		if (eDataTypeName_4003Parser == null) {
			eDataTypeName_4003Parser = createEDataTypeName_4003Parser();
		}
		return eDataTypeName_4003Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEDataTypeName_4003Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getENamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eDataTypeInstanceClassName_4004Parser;

	/**
	 * @generated
	 */
	private IParser getEDataTypeInstanceClassName_4004Parser() {
		if (eDataTypeInstanceClassName_4004Parser == null) {
			eDataTypeInstanceClassName_4004Parser = createEDataTypeInstanceClassName_4004Parser();
		}
		return eDataTypeInstanceClassName_4004Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEDataTypeInstanceClassName_4004Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getEClassifier_InstanceClassName(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		parser.setViewPattern("<<javaclass>> {0}");
		parser.setEditorPattern("<<javaclass>> {0}");
		parser.setEditPattern("<<javaclass>> {0}");
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eEnumName_4005Parser;

	/**
	 * @generated
	 */
	private IParser getEEnumName_4005Parser() {
		if (eEnumName_4005Parser == null) {
			eEnumName_4005Parser = createEEnumName_4005Parser();
		}
		return eEnumName_4005Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEEnumName_4005Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getENamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eEnumLiteral_2006Parser;

	/**
	 * @generated
	 */
	private IParser getEEnumLiteral_2006Parser() {
		if (eEnumLiteral_2006Parser == null) {
			eEnumLiteral_2006Parser = createEEnumLiteral_2006Parser();
		}
		return eEnumLiteral_2006Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEEnumLiteral_2006Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getENamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eStringToStringMapEntry_2007Parser;

	/**
	 * @generated
	 */
	private IParser getEStringToStringMapEntry_2007Parser() {
		if (eStringToStringMapEntry_2007Parser == null) {
			eStringToStringMapEntry_2007Parser = createEStringToStringMapEntry_2007Parser();
		}
		return eStringToStringMapEntry_2007Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEStringToStringMapEntry_2007Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getEStringToStringMapEntry_Key(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eReferenceName_4011Parser;

	/**
	 * @generated
	 */
	private IParser getEReferenceName_4011Parser() {
		if (eReferenceName_4011Parser == null) {
			eReferenceName_4011Parser = createEReferenceName_4011Parser();
		}
		return eReferenceName_4011Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEReferenceName_4011Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getENamedElement_Name(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		return parser;
	}

	/**
	 * @generated
	 */
	private IParser eReferenceLowerBoundUpperBound_4012Parser;

	/**
	 * @generated
	 */
	private IParser getEReferenceLowerBoundUpperBound_4012Parser() {
		if (eReferenceLowerBoundUpperBound_4012Parser == null) {
			eReferenceLowerBoundUpperBound_4012Parser = createEReferenceLowerBoundUpperBound_4012Parser();
		}
		return eReferenceLowerBoundUpperBound_4012Parser;
	}

	/**
	 * @generated
	 */
	protected IParser createEReferenceLowerBoundUpperBound_4012Parser() {
		EAttribute[] features = new EAttribute[] { EcorePackage.eINSTANCE.getETypedElement_LowerBound(), EcorePackage.eINSTANCE.getETypedElement_UpperBound(), };
		MessageFormatParser parser = new MessageFormatParser(features);
		parser.setViewPattern("{0}..{1,choice,-1#*|-1<{1}}");
		parser.setEditorPattern("{0}..{1,choice,-1#*|-1<{1}}");
		parser.setEditPattern("{0}..{1}");
		return parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case EClassNameEditPart.VISUAL_ID:
			return getEClassName_4001Parser();
		case EPackageNameEditPart.VISUAL_ID:
			return getEPackageName_4006Parser();
		case EAnnotationSourceEditPart.VISUAL_ID:
			return getEAnnotationSource_4007Parser();
		case EDataTypeNameEditPart.VISUAL_ID:
			return getEDataTypeName_4008Parser();
		case EDataTypeInstanceClassEditPart.VISUAL_ID:
			return getEDataTypeInstanceClassName_4009Parser();
		case EEnumNameEditPart.VISUAL_ID:
			return getEEnumName_4010Parser();
		case EAttributeEditPart.VISUAL_ID:
			return getEAttribute_2001Parser();
		case EOperationEditPart.VISUAL_ID:
			return getEOperation_2002Parser();
		case EClassName2EditPart.VISUAL_ID:
			return getEClassName_4002Parser();
		case EDataTypeName2EditPart.VISUAL_ID:
			return getEDataTypeName_4003Parser();
		case EDataTypeInstanceClass2EditPart.VISUAL_ID:
			return getEDataTypeInstanceClassName_4004Parser();
		case EEnumName2EditPart.VISUAL_ID:
			return getEEnumName_4005Parser();
		case EEnumLiteralEditPart.VISUAL_ID:
			return getEEnumLiteral_2006Parser();
		case EStringToStringMapEntryEditPart.VISUAL_ID:
			return getEStringToStringMapEntry_2007Parser();
		case EReferenceNameEditPart.VISUAL_ID:
			return getEReferenceName_4011Parser();
		case EReferenceLowerBoundUpperBoundEditPart.VISUAL_ID:
			return getEReferenceLowerBoundUpperBound_4012Parser();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(EcoreVisualIDRegistry.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(EcoreVisualIDRegistry.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (EcoreElementTypes.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		public Object getAdapter(Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}

}
