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

import org.eclipse.n4js.tester.TestCatalogSupplier;
import org.eclipse.n4js.tester.TestTreeRegistry;
import org.eclipse.n4js.tester.TesterEventBus;
import org.eclipse.n4js.tester.TesterFacade;
import org.eclipse.n4js.tester.fsm.TestFsmRegistryImpl;
import org.eclipse.n4js.tester.internal.TestTreeRegistryImpl;
import org.eclipse.n4js.tester.internal.TesterActivator;
import org.eclipse.n4js.tester.internal.Utf8UrlDecoderService;
import org.eclipse.n4js.tester.server.JettyManager;
import org.eclipse.n4js.tester.server.resources.ServletHolderBuilder;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;

/**
 * Module for the N4 tester UI component.
 */
public class N4TesterUiModule extends AbstractModule {

	@Override
	protected void configure() {
		// to ensure singletons are same among all modules.
		bind(TesterEventBus.class).toProvider(() -> getTesterInjector().getInstance(TesterEventBus.class));
		bind(TesterFacade.class).toProvider(() -> getTesterInjector().getInstance(TesterFacade.class));
		bind(TestTreeRegistry.class).toProvider(() -> getTesterInjector().getInstance(TestTreeRegistry.class));
		bind(ServletHolderBuilder.class).toProvider(() -> getTesterInjector().getInstance(ServletHolderBuilder.class));
		bind(Utf8UrlDecoderService.class)
				.toProvider(() -> getTesterInjector().getInstance(Utf8UrlDecoderService.class));
		bind(TestFsmRegistryImpl.class).toProvider(() -> getTesterInjector().getInstance(TestFsmRegistryImpl.class));
		bind(TestTreeRegistryImpl.class).toProvider(() -> getTesterInjector().getInstance(TestTreeRegistryImpl.class));
		bind(JettyManager.class).toProvider(() -> getTesterInjector().getInstance(JettyManager.class));
		bind(TestCatalogSupplier.class);
	}

	private Injector getTesterInjector() {
		return TesterActivator.getInjector();
	}
}
