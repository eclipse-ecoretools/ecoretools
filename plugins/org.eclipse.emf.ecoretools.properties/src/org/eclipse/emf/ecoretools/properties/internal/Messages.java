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
 * $Id: Messages.java,v 1.2 2008/05/23 14:56:54 jlescot Exp $
 **********************************************************************/
package org.eclipse.emf.ecoretools.properties.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.ecoretools.properties.internal.messages"; //$NON-NLS-1$

	public static String AffiliationSection_Affiliation;

	public static String BaseTypeSection_BaseType;

	public static String ContentKindSection_ContentKind;

	public static String DetailEntryKeyPropertySection_Key;

	public static String DetailEntryValuePropertySection_Value;

	public static String EAnnotationPropertySection_Add;

	public static String EAnnotationPropertySection_Annotations;

	public static String EAnnotationPropertySection_AnnotationsGroup;

	public static String EAnnotationPropertySection_ChooseReferences;

	public static String EAnnotationPropertySection_EAnnotation;

	public static String EAnnotationPropertySection_EntriesGroup;

	public static String EAnnotationPropertySection_EntryValue;

	public static String EAnnotationPropertySection_EStringToStringMapEntry;

	public static String EAnnotationPropertySection_Key;

	public static String EAnnotationPropertySection_References;

	public static String EAnnotationPropertySection_Remove;

	public static String EAnnotationPropertySection_Source;

	public static String EAnnotationPropertySection_Value;

	public static String EAnnotationSourcePropertySection_Source;

	public static String EEnumLiteralLiteralPropertySection_Literal;

	public static String EEnumLiteralValuePropertySection_Value;

	public static String EOperationParameterSection_Name;

	public static String EOperationParameterSection_ParameterDetails;

	public static String EOperationParameterSection_Parameters;

	public static String EOperationParameterSection_Type;

	public static String EOppositePropertySection_EOpposite;

	public static String ETypePropertySection_EType;

	public static String FeatureKindSection_FeatureKind;

	public static String FractionDigitsSection_FractionDigits;

	public static String GenModelDocumentationPropertySection_ChangeComments;

	public static String GenModelDocumentationPropertySection_GenModelDocumentation;

	public static String GroupSection_Group;

	public static String InstanceClassNamePropertySection_InstanceClassName;

	public static String IsAbstractPropertySection_IsAbstract;

	public static String IsContainmentPropertySection_IsContainment;

	public static String IsInterfacePropertySection_IsInterface;

	public static String IsSerializablePropertySection_IsSerializable;

	public static String ItemTypeSection_ItemType;

	public static String LengthSection_Length;

	public static String LowerBoundPropertySection_LowerBound;

	public static String MaxExclusiveSection_MaximumConstraint;

	public static String MaxInclusiveSection_MaximumConstraint;

	public static String MaxLengthSection_MaximumLength;

	public static String MinExclusiveSection_MinimumConstraint;

	public static String MinInclusiveSection_MinimumConstraint;

	public static String MinLengthSection_MinimumLength;

	public static String NamePropertySection_Name;

	public static String NameSection_Name;

	public static String NamespaceSection_Namespace;

	public static String NsPrefixPropertySection_NsPrefix;

	public static String NsURIPropertySection_NsURI;

	public static String ProcessingKindSection_ProcessingKind;

	public static String QualifiedSection_Qualified;

	public static String TotalDigitsSection_TotalDigits;

	public static String UpperBoundPropertySection_UpperBound;

	public static String WhitespaceSection_Whitespace;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
