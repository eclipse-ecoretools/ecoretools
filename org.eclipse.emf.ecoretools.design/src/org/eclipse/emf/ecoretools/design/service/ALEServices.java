/*******************************************************************************
 * Copyright (c) 2017, 2023 Obeo.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.service;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

public class ALEServices {

	public static boolean isConfiguredForALE(EPackage pak) {
		return false;
	}

	public static void configureForALE(EPackage pak) {
	}

	/**
	 * Should return all the "things" which can be launched from the given
	 * EClassifier.
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<? extends EObject> getAllExecutables(EClassifier clazz) {
		return List.of(clazz);
	}

	public static EObject addExecutable(EClassifier clazz) {
		return null;
	}

	/**
	 * return the simple name for something which can be executed, a name which
	 * would be unambiguous for a user which has to pick among many.
	 * 
	 * @param anExecutable
	 * @return the simple name for something which can be executed.
	 */
	public static String getExecutableName(EObject anExecutable) {
		return "ALE body";
	}

	/**
	 * Return the executable body as text from the given EObject.
	 * 
	 * @param anExecutable
	 *            something which can be executed.
	 * @return Return the executable body as text.
	 */
	public static String getExecutableBody(EObject anExecutable) {
		return "";
	}

	/**
	 * Set the executable body for the given EObject.
	 * 
	 * @param anExecutable
	 *            something which can be executed.
	 * @param newBody:
	 *            body to set.
	 */
	public static void setExecutableBody(EObject anExecutable, String newBody) {
	}

	/**
	 * return true if the body is syntactically and semantically correct.
	 * 
	 * @param anExecutable
	 *            something which can be executed.
	 */
	public static boolean isValidBody(EObject anExecutable) {
		return false;
	}

	/**
	 * return true if some executables are associated with this element.
	 * 
	 * @param e
	 *            any thing from Ecore.
	 * @return true if some executables are associated with this element.
	 */
	public static boolean hasExecutables(EModelElement e) {
		return false;
	}

	public static List<EObject> getJavaImports(EModelElement e) {
		List<EObject> result = new ArrayList<>();
		if (e instanceof EPackage) {
			result.add(e);
		}
		return result;
	}

	public static String getQualifiedName(EObject javaImport) {
		return "com.mycompany.dsl.Service";
	}

	public static EObject setQualifiedName(EObject javaImport, String newVal) {
		return javaImport;
	}

	public static boolean isValidImport(EObject javaImport) {
		return true;
	}

	/**
	 * Configure a sourceviewer to provide completion & validation, might be
	 * called each time the selection changes.
	 * 
	 * @param anExecutable
	 *            something which can be executed.
	 * @param sourceViewer
	 *            the sourceViewer
	 */
	public static void configureSourceViewer(EObject anExecutable, Object sourceViewer) {

	}

}
