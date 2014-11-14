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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecoretools.design.service.AutosizeTrigger;
import org.eclipse.emf.ecoretools.design.service.EOppositeSetUnset;
import org.eclipse.emf.ecoretools.design.service.EcoreToolsDesignPlugin;
import org.eclipse.emf.ecoretools.design.service.GenModelAutoReload;
import org.eclipse.emf.ecoretools.design.service.GenModelUpdateGenFeatureContainment;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManagerListener;

public class EcoreToolsSessionManagerListener extends SessionManagerListener.Stub {

    @SuppressWarnings("unchecked")
    @Override
    public void notifyAddSession(Session newSession) {
        final ResourceSet set = newSession.getTransactionalEditingDomain().getResourceSet();
        try {
            Field f = XMLResource.class.getField("OPTION_MISSING_PACKAGE_HANDLER");
            /*
             * we are in EMF 2.9 or superior, we can setup the missing package
             * handler.
             */
            GenModelMissingPackageHandler.setupPackageHandler(set);
        } catch (NoSuchFieldException e) {
        } catch (SecurityException e) {
        }

        Map<URI, URI> result = null;
        // Invoke computePlatformURIMap by reflection because this API
        // change in EMF
        try {
            Method computePlatformURIMap = EcorePlugin.class.getMethod("computePlatformURIMap", Boolean.class);
            result = (Map<URI, URI>) computePlatformURIMap.invoke(null, true);
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        } catch (InvocationTargetException e) {
        }
        try {
            Method computePlatformURIMap = EcorePlugin.class.getMethod("computePlatformURIMap");
            result = (Map<URI, URI>) computePlatformURIMap.invoke(null);
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        } catch (InvocationTargetException e) {
        }

        if (result != null) {
            set.getURIConverter().getURIMap().putAll(result);
        } else {
            IStatus status = new Status(IStatus.WARNING, EcoreToolsDesignPlugin.PLUGIN_ID, "The EMF API EcorePlugin.computePlatformURIMap has probably changed and is not supported yet by EcoreTools.");
            EcoreToolsDesignPlugin.getDefault().getLog().log(status);

        }

        newSession.getEventBroker().addLocalTrigger(GenModelAutoReload.SHOULD_RELOAD, new GenModelAutoReload(newSession.getTransactionalEditingDomain()));
        newSession.getEventBroker().addLocalTrigger(GenModelUpdateGenFeatureContainment.SHOULD_UPDATE, new GenModelUpdateGenFeatureContainment(newSession));
        newSession.getEventBroker().addLocalTrigger(AutosizeTrigger.IS_GMF_NODE_ATTACHMENT, new AutosizeTrigger(newSession.getTransactionalEditingDomain()));
        newSession.getEventBroker().addLocalTrigger(EOppositeSetUnset.SHOULD_UPDATE, new EOppositeSetUnset(newSession));
    }
}
