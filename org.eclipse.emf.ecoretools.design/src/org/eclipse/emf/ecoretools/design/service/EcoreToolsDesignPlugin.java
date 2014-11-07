/*******************************************************************************
 * Copyright (c) 2013 THALES GLOBAL SERVICES and Others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.ecoretools.design.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecoretools.design.internal.GenModelMissingPackageHandler;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.business.api.session.SessionManagerListener;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author Laurent Goubet <a
 *         href="mailto:laurent.goubet@obeo.fr">laurent.goubet@obeo.fr</a>
 */
public class EcoreToolsDesignPlugin extends Plugin {
    /** The plug-in ID. */
    public static final String PLUGIN_ID = "org.eclipse.emf.ecoretools.design"; //$NON-NLS-1$

    /** This plug-in's shared instance. */
    private static EcoreToolsDesignPlugin plugin;

    private static Set<Viewpoint> viewpoints;

    private SessionManagerListener notifWhenSessionAreCreated;

    /**
     * Default constructor for the plugin.
     */
    public EcoreToolsDesignPlugin() {
        plugin = this;
    }

    /**
     * Returns the shared instance.
     * 
     * @return the shared instance
     */
    public static EcoreToolsDesignPlugin getDefault() {
        return plugin;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(final BundleContext context) throws Exception {
        super.start(context);
        viewpoints = new HashSet<Viewpoint>();
        viewpoints.addAll(ViewpointRegistry.getInstance().registerFromPlugin(PLUGIN_ID + "/description/ecore.odesign"));

        notifWhenSessionAreCreated = new SessionManagerListener.Stub() {

            @SuppressWarnings("unchecked")
            @Override
            public void notifyAddSession(Session newSession) {
                final ResourceSet set = newSession.getTransactionalEditingDomain().getResourceSet();
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
                    IStatus status = new Status(IStatus.WARNING, EcoreToolsDesignPlugin.PLUGIN_ID,
                            "The EMF API EcorePlugin.computePlatformURIMap has probably changed and is not supported yet by EcoreTools.");
                    EcoreToolsDesignPlugin.getDefault().getLog().log(status);

                }
                
                
                try {
                    Field f = XMLResource.class.getField("OPTION_MISSING_PACKAGE_HANDLER");
                    /*
                     * we are in EMF 2.9 or superior, we can setup the missing package handler.
                     */
                    GenModelMissingPackageHandler.setupPackageHandler(set);
                } catch (NoSuchFieldException e) {
                } catch (SecurityException e) {
                }
                
               

                newSession.getEventBroker().addLocalTrigger(GenModelAutoReload.SHOULD_RELOAD, new GenModelAutoReload(newSession.getTransactionalEditingDomain()));
                newSession.getEventBroker().addLocalTrigger(GenModelUpdateGenFeatureContainment.SHOULD_UPDATE, new GenModelUpdateGenFeatureContainment(newSession));
                newSession.getEventBroker().addLocalTrigger(AutosizeTrigger.IS_GMF_NODE_ATTACHMENT, new AutosizeTrigger(newSession.getTransactionalEditingDomain()));
                newSession.getEventBroker().addLocalTrigger(EOppositeSetUnset.SHOULD_UPDATE, new EOppositeSetUnset(newSession));
            }
        };
        SessionManager.INSTANCE.addSessionsListener(notifWhenSessionAreCreated);

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(final BundleContext context) throws Exception {
        plugin = null;
        if (viewpoints != null) {
            for (final Viewpoint viewpoint : viewpoints) {
                ViewpointRegistry.getInstance().disposeFromPlugin(viewpoint);
            }
            viewpoints.clear();
            viewpoints = null;
        }
        if (notifWhenSessionAreCreated != null) {
            SessionManager.INSTANCE.removeSessionsListener(notifWhenSessionAreCreated);
        }

        super.stop(context);
    }
}
