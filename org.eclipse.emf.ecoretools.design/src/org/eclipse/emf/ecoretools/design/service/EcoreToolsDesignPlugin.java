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

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.ResourceSet;
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
		viewpoints.addAll(ViewpointRegistry.getInstance().registerFromPlugin(
				PLUGIN_ID + "/description/ecore.odesign"));

		notifWhenSessionAreCreated = new SessionManagerListener.Stub() {

			@Override
			public void notifyAddSession(Session newSession) {
				ResourceSet set = newSession.getTransactionalEditingDomain()
						.getResourceSet();
				set.getURIConverter().getURIMap()
						.putAll(EcorePlugin.computePlatformURIMap(true));
				newSession.getEventBroker().addLocalTrigger(
						GenModelAutoReload.SHOULD_RELOAD,
						new GenModelAutoReload(newSession
								.getTransactionalEditingDomain()));

				newSession.getEventBroker().addLocalTrigger(
						GenModelUpdateGenFeatureContainment.SHOULD_UPDATE,
						new GenModelUpdateGenFeatureContainment(newSession));
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
			SessionManager.INSTANCE
					.removeSessionsListener(notifWhenSessionAreCreated);
		}

		super.stop(context);
	}
}
