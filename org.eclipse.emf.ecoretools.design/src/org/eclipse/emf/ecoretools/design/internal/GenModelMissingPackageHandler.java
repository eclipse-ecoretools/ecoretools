/*******************************************************************************
 * Copyright (c) 2014 Obeo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.internal;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;

public class GenModelMissingPackageHandler {

    public static void setupPackageHandler(final ResourceSet set) {
        // If we're in the reflective editor, set up an option to handle missing
        // packages.
        //
        final EPackage genModelEPackage = set.getPackageRegistry().getEPackage("http://www.eclipse.org/emf/2002/GenModel");
        if (genModelEPackage != null) {
            set.getLoadOptions().put(XMLResource.OPTION_MISSING_PACKAGE_HANDLER, new XMLResource.MissingPackageHandler() {
                protected EClass genModelEClass;

                protected EStructuralFeature genPackagesFeature;

                protected EClass genPackageEClass;

                protected EStructuralFeature ecorePackageFeature;

                protected Map<String, URI> ePackageNsURIToGenModelLocationMap;

                public EPackage getPackage(String nsURI) {
                    // Initialize the metadata for accessing the GenModel
                    // reflective the first time.
                    //
                    if (genModelEClass == null) {
                        genModelEClass = (EClass) genModelEPackage.getEClassifier("GenModel");
                        genPackagesFeature = genModelEClass.getEStructuralFeature("genPackages");
                        genPackageEClass = (EClass) genModelEPackage.getEClassifier("GenPackage");
                        ecorePackageFeature = genPackageEClass.getEStructuralFeature("ecorePackage");
                    }

                    // Initialize the map from registered package namespaces to
                    // their GenModel locations the first time.
                    //
                    if (ePackageNsURIToGenModelLocationMap == null) {
                        ePackageNsURIToGenModelLocationMap = EcorePlugin.getEPackageNsURIToGenModelLocationMap(true);
                    }

                    // Look up the namespace URI in the map.
                    //
                    EPackage ePackage = null;
                    URI uri = ePackageNsURIToGenModelLocationMap.get(nsURI);
                    if (uri != null) {
                        // If we find it, demand load the model.
                        //
                        Resource resource = set.getResource(uri, true);

                        // Locate the GenModel and fetech it's genPackages.
                        //
                        EObject genModel = (EObject) EcoreUtil.getObjectByType(resource.getContents(), genModelEClass);
                        @SuppressWarnings("unchecked")
                        List<EObject> genPackages = (List<EObject>) genModel.eGet(genPackagesFeature);
                        for (EObject genPackage : genPackages) {
                            // Check if that package's Ecore Package has them
                            // matching namespace URI.
                            //
                            EPackage dynamicEPackage = (EPackage) genPackage.eGet(ecorePackageFeature);
                            if (nsURI.equals(dynamicEPackage.getNsURI())) {
                                // If so, that's the package we want to return,
                                // and we add it to the registry so it's easy to
                                // find from now on.
                                //
                                ePackage = dynamicEPackage;
                                set.getPackageRegistry().put(nsURI, ePackage);
                                break;
                            }
                        }
                    }
                    return ePackage;
                }
            });
        }

    }

}
