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

import static java.io.File.separator;
import static java.lang.String.valueOf;
import static org.apache.log4j.Logger.getLogger;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.n4js.fileextensions.FileExtensionsRegistry;
import org.eclipse.n4js.packagejson.PackageJsonHelper;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.runner.RunnerFrontEnd;
import org.eclipse.n4js.runner.RunnerHelper;
import org.eclipse.n4js.runner.extension.RunnerRegistry;
import org.eclipse.n4js.tester.extension.TesterRegistry;
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
import org.eclipse.n4js.tester.server.resources.ResourceProvider;
import org.eclipse.n4js.tester.server.resources.ServletHolderBuilder;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.N4ExecutableExtensionFactory;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.n4js.utils.StatusHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.name.Names;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * Defines bindings. In case this module is used in the ui case, bindings are defined to N4JS instances. In case of the
 * headless case, these bindings are not necessary since the headless injector is comprised of the N4JSModules, as well.
 */
public class TesterModule implements Module {
	private static final Logger LOGGER = getLogger(TesterModule.class);

	final private Injector n4jsInjector;

	/** Called in the ui case */
	public TesterModule(Injector n4jsInjector) {
		this.n4jsInjector = n4jsInjector;
	}

	/** Called in the headless case */
	public TesterModule() {
		this.n4jsInjector = null;
	}

	@Override
	public void configure(Binder binder) {
		if (n4jsInjector != null) {
			bindListenerForN4jsSingletons(binder);

			// define all bindings to N4JS here (non-ui packages)
			binder.bind(ObjectMapper.class)
					.toProvider(() -> n4jsInjector.getInstance(ObjectMapper.class));
			binder.bind(RunnerFrontEnd.class)
					.toProvider(() -> n4jsInjector.getInstance(RunnerFrontEnd.class));
			binder.bind(FileExtensionsRegistry.class)
					.toProvider(() -> n4jsInjector.getInstance(FileExtensionsRegistry.class));
			binder.bind(IN4JSCore.class)
					.toProvider(() -> n4jsInjector.getInstance(IN4JSCore.class));
			binder.bind(ResourceNameComputer.class)
					.toProvider(() -> n4jsInjector.getInstance(ResourceNameComputer.class));
			binder.bind(ContainerTypesHelper.class)
					.toProvider(() -> n4jsInjector.getInstance(ContainerTypesHelper.class));
			binder.bind(N4ExecutableExtensionFactory.class)
					.toProvider(() -> n4jsInjector.getInstance(N4ExecutableExtensionFactory.class));
			binder.bind(RunnerHelper.class)
					.toProvider(() -> n4jsInjector.getInstance(RunnerHelper.class));
			binder.bind(RunnerRegistry.class)
					.toProvider(() -> n4jsInjector.getInstance(RunnerRegistry.class));
			binder.bind(StatusHelper.class)
					.toProvider(() -> n4jsInjector.getInstance(StatusHelper.class));
			binder.bind(ProjectDescriptionLoader.class)
					.toProvider(() -> n4jsInjector.getInstance(ProjectDescriptionLoader.class));
			binder.bind(PackageJsonHelper.class)
					.toProvider(() -> n4jsInjector.getInstance(PackageJsonHelper.class));
		}

		binder.bind(TesterRegistry.class);
		binder.bind(TesterEventBus.class);
		binder.bind(TesterFrontEnd.class);
		binder.bind(ResourceProvider.class);
		binder.bind(TestDiscoveryHelper.class);
		binder.bind(TestCatalogSupplier.class);
		binder.bind(ServletHolderBuilder.class);
		binder.bind(DefaultTestTreeTransformer.class);

		binder.bind(TestFsm.class).to(TestFsmImpl.class);
		binder.bind(HttpServerManager.class).to(JettyManager.class);
		binder.bind(TestFsmRegistry.class).to(TestFsmRegistryImpl.class);
		binder.bind(TesterFacade.class).to(TesterFacadeImpl.class);
		binder.bind(TestTreeRegistry.class).to(InternalTestTreeRegistry.class);
		binder.bind(InternalTestTreeRegistry.class).to(TestTreeRegistryImpl.class);
		binder.bind(TestTreeTransformer.class).to(DefaultTestTreeTransformer.class);
		binder.bind(UrlDecoderService.class).to(Utf8UrlDecoderService.class);

		Names.bindProperties(binder, getProperties());
	}

	/**
	 * This listener detects whether @Singletons from the non-tester context are bound by the Tester-Injector which
	 * would be a problem.
	 */
	private void bindListenerForN4jsSingletons(Binder binder) {
		Matcher<TypeLiteral<?>> m = new AbstractMatcher<TypeLiteral<?>>() {
			@Override
			public boolean matches(TypeLiteral<?> t) {
				checkAndThrowMissingBindingException(t);
				return false;
			}

			private void checkAndThrowMissingBindingException(TypeLiteral<?> t) {
				Type type = t.getType();
				if (type instanceof Class) {
					String name = t.toString();
					Singleton singleton = null;
					try {
						singleton = ((Class<?>) type).getAnnotation(Singleton.class);
					} catch (Exception e) {
						LOGGER.warn("Could not check whether injected type is @Singleton", e);
					}

					if (singleton != null) {
						boolean allowedPrefix = false;
						allowedPrefix |= name.startsWith("org.eclipse.n4js.tester.");
						allowedPrefix |= name.startsWith("org.eclipse.n4js.runner.");
						allowedPrefix |= name.startsWith("org.eclipse.n4js.utils.");
						if (!allowedPrefix) {
							String msg = "All dependencies to @Singleton classes must be bound explicitly.";
							throw new RuntimeException(msg);
						}
					}
				}
			}
		};

		TypeListener tl = new TypeListener() {
			@Override
			public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
				return;
			}
		};
		binder.bindListener(m, tl);
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
