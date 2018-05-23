/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tester.ui;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static org.eclipse.n4js.tester.TesterModule.N4_TESTER_MODULE_ID;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

import org.eclipse.n4js.tester.TestTreeRegistry;
import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.TesterFacade;
import org.eclipse.n4js.tester.TesterModule;
import org.eclipse.n4js.ui.internal.N4JSActivator;

/**
 * Module for the N4 tester UI component.
 */
public class N4TesterUiModule extends AbstractModule {

	/**
	 * The ID of the N4 tester UI module.
	 */
	public static final String N4_TESTER_UI_MODULE_ID = N4TesterUiModule.class.getName();

	private static final LoadingCache<String, Injector> INJECTORS = newBuilder().build(
			new CacheLoader<String, Injector>() {
				@Override
				public Injector load(final String moduleId) throws Exception {
					if (N4_TESTER_UI_MODULE_ID.equals(moduleId)) {
						final Injector parentInjector = N4JSActivator.getInstance().getInjector(
								N4JSActivator.ORG_ECLIPSE_N4JS_N4JS);
						return parentInjector.createChildInjector(Modules.override(new TesterModule()).with(
								new N4TesterUiModule()));
					}
					throw new IllegalArgumentException("Unknown module ID: " + moduleId);
				}
			});

	/**
	 * Returns with the injector instance for a particular module given as the unique module ID argument.
	 *
	 * @param id
	 *            the unique module ID.
	 * @return the injector instance. Never {@code null}.
	 */
	public static Injector getInjector(final String id) {
		return INJECTORS.getUnchecked(id);
	}

	@Override
	protected void configure() {
		// to ensure singletons are same among all modules.
		bind(TesterEventBus.class).toProvider(() -> getTesterInjector().getInstance(TesterEventBus.class));
		bind(TesterFacade.class).toProvider(() -> getTesterInjector().getInstance(TesterFacade.class));
		bind(TestTreeRegistry.class).toProvider(() -> getTesterInjector().getInstance(TestTreeRegistry.class));
	}

	private Injector getTesterInjector() {
		return TesterModule.getInjector(N4_TESTER_MODULE_ID);
	}
}
