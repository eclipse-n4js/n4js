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
package org.eclipse.n4js.n4jsx.tests.helper;

import org.eclipse.n4js.ExceptionAwareDiagnosticConverter;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.N4JSParseHelper;
import org.eclipse.n4js.SmokeTestWriter;
import org.eclipse.n4js.n4jsx.N4JSXRuntimeModule;
import org.eclipse.n4js.n4jsx.N4JSXStandaloneSetup;
import org.eclipse.xtext.junit4.util.ResourceHelper;
import org.eclipse.xtext.service.DefaultRuntimeModule;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.validation.IDiagnosticConverter;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Replaces N4JSRuntimeModele with N4JSXRuntimeVersion in constructor
 */
public class N4JSXInjectorProvider extends N4JSInjectorProvider {

	/** Default constructor */
	public N4JSXInjectorProvider() {
		this(new DefaultTestModule());
	}

	/**
	 * Creates a new injector combining all of the given runtime modules
	 */
	public N4JSXInjectorProvider(Module... modules) {
		super(modules);
	}

	@Override
	protected DefaultRuntimeModule createRuntimeModule() {
		return new N4JSXRuntimeModule();
	}

	/**
	 * Maybe overridden in subclasses for derived languages.
	 */
	@Override
	protected Injector internalCreateInjector() {
		return new N4JSXStandaloneSetup() {
			@Override
			public Injector createInjector() {
				return Guice.createInjector(runtimeModule);
			}
		}.createInjectorAndDoEMFRegistration();
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

		/**
		 * IMPORTANT, this re-binding is required since we are not in the same
		 * bundle as our base class.
		 */
		@Override
		public ClassLoader bindClassLoaderToInstance() {
			return getClass().getClassLoader();
		}

	}

}
