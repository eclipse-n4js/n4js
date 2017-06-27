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
package org.eclipse.n4js.tester;

import static com.google.common.cache.CacheBuilder.newBuilder;
import static com.google.inject.Guice.createInjector;
import static com.google.inject.name.Names.bindProperties;
import static org.eclipse.n4js.tester.TesterModuleDefaults.DEFAULT_FSM_TIMEOUT_KEY;
import static org.eclipse.n4js.tester.TesterModuleDefaults.DEFAULT_FSM_TIMEOUT_VALUE;
import static org.eclipse.n4js.tester.TesterModuleDefaults.DUMP_SERVER_ON_STOP_KEY;
import static org.eclipse.n4js.tester.TesterModuleDefaults.DUMP_SERVER_ON_STOP_VALUE;
import static org.eclipse.n4js.tester.TesterModuleDefaults.HTTP_SERVER_PORT_KEY;
import static org.eclipse.n4js.tester.TesterModuleDefaults.HTTP_SERVER_PORT_VALUE;
import static org.eclipse.n4js.tester.TesterModuleDefaults.MAX_THREAD_COUNT_KEY;
import static org.eclipse.n4js.tester.TesterModuleDefaults.MAX_THREAD_COUNT_VALUE;
import static org.eclipse.n4js.tester.TesterModuleDefaults.MIN_THREAD_COUNT_KEY;
import static org.eclipse.n4js.tester.TesterModuleDefaults.MIN_THREAD_COUNT_VALUE;
import static org.eclipse.n4js.tester.TesterModuleDefaults.SETUP_FSM_TIMEOUT_KEY;
import static org.eclipse.n4js.tester.TesterModuleDefaults.SETUP_FSM_TIMEOUT_VALUE;
import static org.eclipse.n4js.tester.TesterModuleDefaults.TEST_TREE_TIMEOUT_KEY;
import static org.eclipse.n4js.tester.TesterModuleDefaults.TEST_TREE_TIMEOUT_VALUE;
import static org.eclipse.n4js.tester.TesterModuleDefaults.THREAD_POOL_BLOCKING_CAPACITY_KEY;
import static org.eclipse.n4js.tester.TesterModuleDefaults.THREAD_POOL_BLOCKING_CAPACITY_VALUE;
import static java.io.File.separator;
import static java.lang.String.valueOf;
import static org.apache.log4j.Logger.getLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;

import org.eclipse.n4js.tester.fsm.TestFsm;
import org.eclipse.n4js.tester.fsm.TestFsmImpl;
import org.eclipse.n4js.tester.fsm.TestFsmRegistry;
import org.eclipse.n4js.tester.fsm.TestFsmRegistryImpl;
import org.eclipse.n4js.tester.internal.DefaultTestTreeTransformer;
import org.eclipse.n4js.tester.internal.InternalTestTreeRegistry;
import org.eclipse.n4js.tester.internal.TestTreeRegistryImpl;
import org.eclipse.n4js.tester.internal.TesterFacadeImpl;
import org.eclipse.n4js.tester.internal.Utf8UrlDecoderService;
import org.eclipse.n4js.tester.server.HttpServerManager;
import org.eclipse.n4js.tester.server.JettyManager;

/**
 * Module for the N4 tester core component.
 */
public class TesterModule extends AbstractModule {

	private static final Logger LOGGER = getLogger(TesterModule.class);

	/**
	 * The ID for the N4 tester core module.
	 */
	public static final String N4_TESTER_MODULE_ID = TesterModule.class.getName();

	private static final LoadingCache<String, Injector> INJECTORS = newBuilder().build(
			new CacheLoader<String, Injector>() {
				@Override
				public Injector load(final String moduleId) throws Exception {
					if (N4_TESTER_MODULE_ID.equals(moduleId)) {
						return createInjector(new TesterModule());
					}
					throw new IllegalArgumentException("Unknown module ID: " + moduleId);
				}
			});

	/**
	 * Returns with the injector instance for a particular module given as the module ID argument.
	 *
	 * @param moduleId
	 *            the unique ID of the module.
	 * @return the injector instance. Never {@code null}.
	 */
	public static Injector getInjector(final String moduleId) {
		return INJECTORS.getUnchecked(moduleId);
	}

	@Override
	protected void configure() {
		bind(TestFsm.class).to(TestFsmImpl.class);
		bind(HttpServerManager.class).to(JettyManager.class);
		bind(TestFsmRegistry.class).to(TestFsmRegistryImpl.class);
		bind(TesterFacade.class).to(TesterFacadeImpl.class);
		bind(TestTreeRegistry.class).to(InternalTestTreeRegistry.class);
		bind(InternalTestTreeRegistry.class).to(TestTreeRegistryImpl.class);
		bind(TestTreeTransformer.class).to(DefaultTestTreeTransformer.class);
		bind(UrlDecoderService.class).to(Utf8UrlDecoderService.class);
		bindProperties(binder(), getProperties());
	}

	private Properties getProperties() {
		final Properties properties = new Properties(new DefaultModuleProperties());
		try {
			final URL url = new URL("platform:/plugin/org.eclipse.n4js.tester/module.properties");
			return tryLoadProperties(url, properties);
		} catch (final IOException e) {
			// Assuming OSGi platform is not running
			if (e instanceof MalformedURLException) {
				LOGGER.info(
						"Cannot locate module properties. Does the platform running? Trying to load properties for tests.");
				try {
					final ProtectionDomain domain = getClass().getProtectionDomain();
					if (null != domain) {
						final CodeSource source = domain.getCodeSource();
						if (null != source) {
							final String parent = new File(source.getLocation().toURI()).getParent();
							final File file = new File(parent + separator + "module.properties");
							if (!file.exists() || !file.canRead()) {
								LOGGER.info("Cannot access properties file at " + file.getPath() + ".");
							}
							final URL url = file.toURI().toURL();
							return tryLoadProperties(url, properties);
						}
					}
				} catch (IOException | URISyntaxException ee) {
					if (ee instanceof FileNotFoundException) {
						LOGGER.warn("Cannot load module properties. Falling back to defaults.");
					} else {
						LOGGER.warn("Cannot load module properties. Falling back to defaults.", ee);
					}
				}
			}
			return properties;
		}
	}

	private Properties tryLoadProperties(final URL url, final Properties properties) throws IOException {
		try (final InputStream is = url.openStream()) {
			properties.load(is);
			LOGGER.info("Module properties have been successfully loaded.");
			return properties;
		}
	}

	/**
	 * Default properties for the N4 tester core module.
	 */
	private static final class DefaultModuleProperties extends Properties {

		private DefaultModuleProperties() {
			setProperty(HTTP_SERVER_PORT_KEY, valueOf(HTTP_SERVER_PORT_VALUE));
			setProperty(SETUP_FSM_TIMEOUT_KEY, valueOf(SETUP_FSM_TIMEOUT_VALUE));
			setProperty(DEFAULT_FSM_TIMEOUT_KEY, valueOf(DEFAULT_FSM_TIMEOUT_VALUE));
			setProperty(MIN_THREAD_COUNT_KEY, valueOf(MIN_THREAD_COUNT_VALUE));
			setProperty(MAX_THREAD_COUNT_KEY, valueOf(MAX_THREAD_COUNT_VALUE));
			setProperty(DUMP_SERVER_ON_STOP_KEY, valueOf(DUMP_SERVER_ON_STOP_VALUE));
			setProperty(TEST_TREE_TIMEOUT_KEY, valueOf(TEST_TREE_TIMEOUT_VALUE));
			setProperty(THREAD_POOL_BLOCKING_CAPACITY_KEY, valueOf(THREAD_POOL_BLOCKING_CAPACITY_VALUE));
		}

	}

}
