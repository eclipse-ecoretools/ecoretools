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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecoretools.diagram.expressions.EcoreAbstractExpression;
import org.eclipse.emf.ecoretools.diagram.expressions.EcoreOCLFactory;
import org.eclipse.emf.ecoretools.diagram.part.EcoreDiagramEditorPlugin;

/**
 * @generated
 */
public class ElementInitializers {

	/**
	 * @generated
	 */
	public static class Initializers {

		/**
		 * @generated
		 */
		public static final IObjectInitializer EReference_3002 = new ObjectInitializer(EcorePackage.eINSTANCE.getEReference()) {

			protected void init() {
				add(createExpressionFeatureInitializer(EcorePackage.eINSTANCE.getEReference_Containment(), EcoreOCLFactory.getExpression("false", //$NON-NLS-1$
						EcorePackage.eINSTANCE.getEReference())));
			}
		}; // EReference_3002 ObjectInitializer

		/**
		 * @generated
		 */
		private Initializers() {
		}

		/**
		 * @generated
		 */
		public static interface IObjectInitializer {

			/**
			 * @generated
			 */
			public void init(EObject instance);
		}

		/**
		 * @generated
		 */
		public static abstract class ObjectInitializer implements IObjectInitializer {

			/**
			 * @generated
			 */
			final EClass element;

			/**
			 * @generated
			 */
			private List featureInitializers = new ArrayList();

			/**
			 * @generated
			 */
			ObjectInitializer(EClass element) {
				this.element = element;
				init();
			}

			/**
			 * @generated
			 */
			protected abstract void init();

			/**
			 * @generated
			 */
			protected final IFeatureInitializer add(IFeatureInitializer initializer) {
				featureInitializers.add(initializer);
				return initializer;
			}

			/**
			 * @generated
			 */
			public void init(EObject instance) {
				for (Iterator it = featureInitializers.iterator(); it.hasNext();) {
					IFeatureInitializer nextExpr = (IFeatureInitializer) it.next();
					try {
						nextExpr.init(instance);
					} catch (RuntimeException e) {
						EcoreDiagramEditorPlugin.getInstance().logError("Feature initialization failed", e); //$NON-NLS-1$						
					}
				}
			}
		} // end of ObjectInitializer

		/**
		 * @generated
		 */
		interface IFeatureInitializer {

			/**
			 * @generated
			 */
			void init(EObject contextInstance);
		}

		/**
		 * @generated
		 */
		static IFeatureInitializer createNewElementFeatureInitializer(EStructuralFeature initFeature, ObjectInitializer[] newObjectInitializers) {
			final EStructuralFeature feature = initFeature;
			final ObjectInitializer[] initializers = newObjectInitializers;
			return new IFeatureInitializer() {

				public void init(EObject contextInstance) {
					for (int i = 0; i < initializers.length; i++) {
						EObject newInstance = initializers[i].element.getEPackage().getEFactoryInstance().create(initializers[i].element);
						if (feature.isMany()) {
							((Collection) contextInstance.eGet(feature)).add(newInstance);
						} else {
							contextInstance.eSet(feature, newInstance);
						}
						initializers[i].init(newInstance);
					}
				}
			};
		}

		/**
		 * @generated
		 */
		static IFeatureInitializer createExpressionFeatureInitializer(EStructuralFeature initFeature, EcoreAbstractExpression valueExpression) {
			final EStructuralFeature feature = initFeature;
			final EcoreAbstractExpression expression = valueExpression;
			return new IFeatureInitializer() {

				public void init(EObject contextInstance) {
					expression.assignTo(feature, contextInstance);
				}
			};
		}

		/**
		 * @generated
		 */
		static class Java {
		}
	} // end of Initializers
}
