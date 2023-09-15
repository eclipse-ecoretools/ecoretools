/*******************************************************************************
 * Copyright (c) 2015 Obeo
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.internal;

import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl.BinaryIO.Version;

public class EcoreBinResourceImpl extends BinaryResourceImpl {

    public EcoreBinResourceImpl(URI uri) {
        super(uri);
        this.defaultLoadOptions = new HashMap<Object, Object>();
        this.defaultLoadOptions.put(OPTION_VERSION, Version.VERSION_1_1);
        this.defaultSaveOptions = new HashMap<Object, Object>();
        this.defaultSaveOptions.put(OPTION_VERSION, Version.VERSION_1_1);
    }

}
