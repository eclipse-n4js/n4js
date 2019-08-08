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
package org.eclipse.n4js;

import java.util.Arrays;

import org.eclipse.n4js.internal.FileBasedWorkspace;
import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.xpect.projects.AutoDiscoveryFileBasedWorkspace;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.service.DefaultRuntimeModule;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.testing.GlobalRegistries;
import org.eclipse.xtext.testing.GlobalRegistries.GlobalStateMemento;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.IRegistryConfigurator;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

/**
 * An injector provider for standalone JUnit tests. See {@link InjectWith}
 *
 * Do not use this for Plugin tests. Plugin tests have a dedicated injector provider.
 */
public class N4JSInjectorProvider implements IInjectorProvider, IRegistryConfigurator {

	/***/
	protected GlobalStateMemento stateBeforeInjectorCreation;
	/***/
	protected GlobalStateMemento stateAfterInjectorCreation;
	/***/
	protected Injector injector;

	/**
	 * The runtimeModule with bindings, created in {@link #createRuntimeModule()} since subtypes may create different
	 * modules.
	 */
	protected final Module runtimeModule;

	static {
		GlobalRegistries.initializeDefaults();
	}

	/** Default constructor */
	public N4JSInjectorProvider() {
		this(new N4JSStandaloneTestsModule());
	}

	/**
	 * Creates a new injector combining all of the given runtime modules
	 */
	public N4JSInjectorProvider(Module... modules) {
		this.runtimeModule = Modules.override(createRuntimeModule()).with(Arrays.asList(modules));
	}

	/**
	 * Called in constructor to create runtime module, may be overridden in subclasses for derived languages.
	 */
	protected DefaultRuntimeModule createRuntimeModule() {
		return new N4JSRuntimeModule();
	}

	@Override
	public Injector getInjector() {
		if (injector == null) {
			stateBeforeInjectorCreation = GlobalRegistries.makeCopyOfGlobalState();
			try {
				this.injector = internalCreateInjector();

			} catch (Throwable e) {
				// #############################################################################
				// IDE-2514: Temporarily exists on exception due to PolymorphicDispatcher problem
				boolean polymorphicDispatchProblem = false;
				if (e.getMessage().contains("Comparison method violates its general contract!")) {
					String pdName = PolymorphicDispatcher.class.getName();
					for (StackTraceElement ste : e.getStackTrace()) {
						String steName = ste.toString();
						if (steName.contains(pdName)) {
							polymorphicDispatchProblem = true;
						}
					}
				}

				if (polymorphicDispatchProblem) {
					String msg = "Comparison method violates its general contract!\n\t";
					msg += "at org.eclipse.n4js.N4JSInjectorProvider.getInjector(N4JSInjectorProvider.java:90)\\n\\t";
					msg += "Reason might be the PolymorphicDispatcher";
					msg += "Exit.";
					System.err.println(msg);
					System.exit(-1);
				} else {
					throw e;
				}
				// Fail fast End
				// #############################################################################
			}
			stateAfterInjectorCreation = GlobalRegistries.makeCopyOfGlobalState();
		}
		return injector;
	}

	/**
	 * Maybe overridden in subclasses for derived languages.
	 */
	protected Injector internalCreateInjector() {
		return new N4JSStandaloneSetup() {
			@Override
			public Injector createInjector() {
				return Guice.createInjector(runtimeModule);
			}
		}.createInjectorAndDoEMFRegistration();
	}

	@Override
	public void restoreRegistry() {
		stateBeforeInjectorCreation.restoreGlobalState();
	}

	@Override
	public void setupRegistry() {
		getInjector();
		stateAfterInjectorCreation.restoreGlobalState();
	}

	/**
	 * Common Guice module for overriding bindings of the N4JSRuntimeModule. Most importantly this class re-binds the
	 * ClassLoader to the one of this package.
	 *
	 * Note that when sub-classing this class in a different bundle, it is essential to re-bind
	 * {@link #bindClassLoaderToInstance()}.
	 */
	public static class BaseTestModule extends AbstractGenericModule {
		/**
		 * We need to bind the class loader here in order to override the default binding: REBIND: contributed by
		 * org.eclipse.xtext.generator.grammarAccess.GrammarAccessFragment
		 */
		public java.lang.ClassLoader bindClassLoaderToInstance() {
			return getClass().getClassLoader();
		}

		/** Bind custom workspace implementation that automatically discovers existing projects on-the-fly. */
		@SingletonBinding
		public Class<? extends InternalN4JSWorkspace<?>> bindInternalN4JSWorkspace() {
			return AutoDiscoveryFileBasedWorkspace.class;
		}

		/** @see #bindInternalN4JSWorkspace() */
		@SingletonBinding
		public Class<? extends FileBasedWorkspace> bindFileBasedWorkspace() {
			return AutoDiscoveryFileBasedWorkspace.class;
		}
	}
}
