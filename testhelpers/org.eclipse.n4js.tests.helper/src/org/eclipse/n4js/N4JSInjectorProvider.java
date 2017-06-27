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

import org.eclipse.xtext.junit4.GlobalRegistries;
import org.eclipse.xtext.junit4.GlobalRegistries.GlobalStateMemento;
import org.eclipse.xtext.junit4.IInjectorProvider;
import org.eclipse.xtext.junit4.IRegistryConfigurator;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.util.ResourceHelper;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.service.DefaultRuntimeModule;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.validation.IDiagnosticConverter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import org.eclipse.n4js.n4JS.Script;

/***/
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
		this(new DefaultTestModule());
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
	 */
	public static class BaseTestModule extends AbstractGenericModule {
		/**
		 * We need to bind the class loader here in order to override the default binding: REBIND: contributed by
		 * org.eclipse.xtext.generator.grammarAccess.GrammarAccessFragment
		 */
		public java.lang.ClassLoader bindClassLoaderToInstance() {
			return getClass().getClassLoader();
		}
	}

	/** */
	public static class DefaultTestModule extends BaseTestModule {
		/** */
		public Class<? extends IDiagnosticConverter> bindDiagnosticConverter() {
			return ExceptionAwareDiagnosticConverter.class;
		}

		/** */
		public Class<? extends N4JSParseHelper> bindN4JSParseHelper() {
			return SmokeTestWriter.class;
		}

		/** */
		@SingletonBinding
		public Class<? extends ResourceHelper> bindResourceHelper() {
			return ResourceHelper.class;
		}

		/** */
		public Class<? extends ParseHelper<Script>> bindParseHelperScript() {
			return SmokeTestWriter.class;
		}

	}

}
