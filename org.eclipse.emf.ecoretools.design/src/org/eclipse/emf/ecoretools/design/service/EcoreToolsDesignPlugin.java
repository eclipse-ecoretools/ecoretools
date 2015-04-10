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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.sirius.business.api.componentization.ViewpointRegistry;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.sirius.business.api.session.SessionManagerListener;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author Laurent Goubet <a
 *         href="mailto:laurent.goubet@obeo.fr">laurent.goubet@obeo.fr</a>
 */
public class EcoreToolsDesignPlugin extends EMFPlugin {

    public static final EcoreToolsDesignPlugin INSTANCE = new EcoreToolsDesignPlugin();

    /**
     * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     */
    private static Implementation plugin;

    /** The plug-in ID. */
    public static final String PLUGIN_ID = "org.eclipse.emf.ecoretools.design"; //$NON-NLS-1$

    private static Set<Viewpoint> viewpoints;

    /**
     * Default constructor for the plugin.
     */
    public EcoreToolsDesignPlugin() {
        super(new ResourceLocator[] {});
    }

    /**
     * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return the singleton instance.
     * @generated
     */
    @Override
    public ResourceLocator getPluginResourceLocator() {
        return plugin;
    }

    /**
     * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @return the singleton instance.
     * @generated
     */
    public static Implementation getPlugin() {
        return plugin;
    }

    /**
     * The actual implementation of the Eclipse <b>Plugin</b>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public static class Implementation extends EclipsePlugin {

        private SessionManagerListener notifWhenSessionAreCreated;

        /**
         * Creates an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        public Implementation() {
            super();

            // Remember the static instance.
            //
            plugin = this;
        }

        /**
         * The actual implementation of the purely OSGi-compatible <b>Bundle
         * Activator</b>. <!-- begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        public static final class Activator extends EMFPlugin.OSGiDelegatingBundleActivator {
            @Override
            protected BundleActivator createBundle() {
                return new Implementation();
            }
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

}
