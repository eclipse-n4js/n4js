/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.building;

import org.eclipse.n4js.ui.external.OutdatedPackageJsonQueue;
import org.eclipse.n4js.ui.external.ProjectStateChangeListener;
import org.eclipse.xtext.builder.impl.ProjectOpenedOrClosedListener;
import org.eclipse.xtext.ui.shared.contribution.IEagerContribution;
import org.eclipse.xtext.ui.shared.contribution.SharedStateContribution;
import org.eclipse.xtext.ui.shared.internal.DefaultSharedContribution;
import org.eclipse.xtext.ui.shared.internal.SharedStateContributionRegistryImpl;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;

/**
 * Overrides the {@link DefaultSharedContribution} to specialize the {@link ProjectOpenedOrClosedListener}. We have to
 * use a little trick here and override the {@link DefaultSharedContribution} to replace the original binding.
 */
@SuppressWarnings("restriction")
@Singleton
public class DefaultSharedContributionOverridingRegistry extends SharedStateContributionRegistryImpl {

	/**
	 * This module is used to override the binding for the {@link ProjectOpenedOrClosedListener}.
	 */
	private static class ProjectStateChangeListenerModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(ProjectOpenedOrClosedListener.class).to(ProjectStateChangeListener.class);
			binder.bind(OutdatedPackageJsonQueue.class);
			binder.bind(IEagerContribution.class).to(ListenerRegistrarWithoutRecoveryBuild.class);
		}
	}

	/**
	 * Creates a new registry.
	 *
	 * @param injector
	 *            the shared injector.
	 */
	@Inject
	public DefaultSharedContributionOverridingRegistry(Injector injector) {
		super(injector);
	}

	/**
	 * Here we override the childModule if it is a {@link DefaultSharedContribution}. In that case, we enhance it with
	 * the {@link ProjectStateChangeListenerModule}.
	 *
	 * @param childModule
	 *            the module that shall be used to configure the contribution.
	 */
	@Override
	public SharedStateContribution createContribution(Module childModule) {
		if (childModule.getClass().equals(DefaultSharedContribution.class)) {
			childModule = Modules.override(childModule).with(new ProjectStateChangeListenerModule());
		}
		return super.createContribution(childModule);
	}

}