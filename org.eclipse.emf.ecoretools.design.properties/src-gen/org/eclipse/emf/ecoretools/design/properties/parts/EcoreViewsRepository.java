/**
 * Generated with Acceleo
 */
package org.eclipse.emf.ecoretools.design.properties.parts;

/**
 * 
 * 
 */
public class EcoreViewsRepository {

	public static final int SWT_KIND = 0;

	public static final int FORM_KIND = 1;


	/**
	 * EAttribute view descriptor
	 * 
	 */
	public static class EAttribute {
		public static class Properties {
	
			
			public static String name = "ecore::EAttribute::Properties::name";
			
			
			public static String eType = "ecore::EAttribute::Properties::eType";
			
			
			public static String defaultValueLiteral = "ecore::EAttribute::Properties::defaultValueLiteral";
			
			
			public static String iD = "ecore::EAttribute::Properties::iD";
			
	
		}
	
		public static class Cardinality {
	
			
			public static String lowerBound = "ecore::EAttribute::Cardinality::lowerBound";
			
			
			public static String upperBound = "ecore::EAttribute::Cardinality::upperBound";
			
				public static class ListProperties {
			
					
					public static String unique = "ecore::EAttribute::Cardinality::List Properties::unique";
					
					
					public static String ordered = "ecore::EAttribute::Cardinality::List Properties::ordered";
					
			
				}
			
	
		}
	
		public static class Behavior {
	
				public static class Changeability {
			
					
					public static String derived = "ecore::EAttribute::Behavior::Changeability::derived";
					
					
					public static String changeable = "ecore::EAttribute::Behavior::Changeability::changeable";
					
					
					public static String unsettable = "ecore::EAttribute::Behavior::Changeability::unsettable";
					
			
				}
			
				public static class Others {
			
					
					public static String transient_ = "ecore::EAttribute::Behavior::Others::transient";
					
					
					public static String volatile_ = "ecore::EAttribute::Behavior::Others::volatile";
					
			
				}
			
	
		}
	
	}

	/**
	 * EAnnotation view descriptor
	 * 
	 */
	public static class EAnnotation {
		public static class Properties {
	
			
			public static String source = "ecore::EAnnotation::properties::source";
			
			
			public static String details = "ecore::EAnnotation::properties::details";
			
			
			public static String eModelElement = "ecore::EAnnotation::properties::eModelElement";
			
			
			public static String contents = "ecore::EAnnotation::properties::contents";
			
			
			public static String references = "ecore::EAnnotation::properties::references";
			
	
		}
	
	}

	/**
	 * EClass view descriptor
	 * 
	 */
	public static class EClass {
		public static class Properties {
	
			
			public static String name = "ecore::EClass::properties::name";
			
				public static class Abstraction {
			
					
					public static String abstract_ = "ecore::EClass::properties::abstraction::abstract";
					
					
					public static String interface_ = "ecore::EClass::properties::abstraction::interface";
					
			
				}
			
	
		}
	
		public static class Inheritance {
	
			
			public static String eSuperTypes = "ecore::EClass::Inheritance::eSuperTypes";
			
			
			public static String eGenericSuperTypes = "ecore::EClass::Inheritance::eGenericSuperTypes";
			
	
		}
	
	}

	/**
	 * Instanciation view descriptor
	 * 
	 */
	public static class Instanciation {
	
	public static String instanceClassName = "ecore::Instanciation::instanceClassName";
	
	
	public static String instanceTypeName = "ecore::Instanciation::instanceTypeName";
	
	}

	/**
	 * Data Type view descriptor
	 * 
	 */
	public static class DataType {
		public static class Properties {
	
			
			public static String name = "ecore::Data Type::properties::name";
			
			
			public static String serializable = "ecore::Data Type::properties::serializable";
			
	
		}
	
	}

	/**
	 * EEnumLiteral view descriptor
	 * 
	 */
	public static class EEnumLiteral {
		public static class Properties {
	
			
			public static String name = "ecore::EEnumLiteral::Properties::name";
			
			
			public static String value = "ecore::EEnumLiteral::Properties::value";
			
			
			public static String literal = "ecore::EEnumLiteral::Properties::literal";
			
	
		}
	
	}

	/**
	 * Exceptions view descriptor
	 * 
	 */
	public static class Exceptions {
		public static class Exceptions_ {
	
			
			public static String eExceptions = "ecore::Exceptions::Exceptions_::eExceptions";
			
	
		}
	
	}

	/**
	 * EPackage view descriptor
	 * 
	 */
	public static class EPackage {
		public static class Properties {
	
			
			public static String name = "ecore::EPackage::properties::name";
			
			
			public static String nsURI = "ecore::EPackage::properties::nsURI";
			
			
			public static String nsPrefix = "ecore::EPackage::properties::nsPrefix";
			
	
		}
	
	}

	/**
	 * Operation Element view descriptor
	 * 
	 */
	public static class OperationElement {
		public static class Properties {
	
			
			public static String name = "ecore::Operation Element::Properties::name";
			
			
			public static String eType = "ecore::Operation Element::Properties::eType";
			
			
			public static String ordered = "ecore::Operation Element::Properties::ordered";
			
			
			public static String unique = "ecore::Operation Element::Properties::unique";
			
	
		}
	
		public static class Cardinality {
	
			
			public static String lowerBound = "ecore::Operation Element::Cardinality::lowerBound";
			
			
			public static String upperBound = "ecore::Operation Element::Cardinality::upperBound";
			
	
		}
	
	}

	/**
	 * EReference view descriptor
	 * 
	 */
	public static class EReference {
		public static class Properties {
	
			
			public static String name = "ecore::EReference::Properties::name";
			
			
			public static String eType = "ecore::EReference::Properties::eType";
			
			
			public static String defaultValueLiteral = "ecore::EReference::Properties::defaultValueLiteral";
			
			
			public static String eOpposite = "ecore::EReference::Properties::eOpposite";
			
			
			public static String eKeys = "ecore::EReference::Properties::eKeys";
			
	
		}
	
		public static class Characteristics {
	
				public static class GeneralCharacteristics {
			
					
					public static String containment = "ecore::EReference::Characteristics::General Characteristics::containment";
					
					
					public static String unique = "ecore::EReference::Characteristics::General Characteristics::unique";
					
					
					public static String ordered = "ecore::EReference::Characteristics::General Characteristics::ordered";
					
			
				}
			
				public static class ProxiesManagement {
			
					
					public static String resolveProxies = "ecore::EReference::Characteristics::Proxies management::resolveProxies";
					
			
				}
			
	
		}
	
		public static class Cardinality {
	
			
			public static String lowerBound = "ecore::EReference::Cardinality::lowerBound";
			
			
			public static String upperBound = "ecore::EReference::Cardinality::upperBound";
			
	
		}
	
		public static class Behavior {
	
				public static class Changeability {
			
					
					public static String derived = "ecore::EReference::Behavior::Changeability::derived";
					
					
					public static String changeable = "ecore::EReference::Behavior::Changeability::changeable";
					
					
					public static String unsettable = "ecore::EReference::Behavior::Changeability::unsettable";
					
			
				}
			
				public static class Others {
			
					
					public static String transient_ = "ecore::EReference::Behavior::Others::transient";
					
					
					public static String volatile_ = "ecore::EReference::Behavior::Others::volatile";
					
			
				}
			
	
		}
	
	}

}
