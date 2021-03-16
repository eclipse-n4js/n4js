/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js;

import org.eclipse.n4js.N4JSInjectorProvider.BaseTestModule;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xpect.setup.XpectGuiceModule;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.validation.IDiagnosticConverter;

/**
 * A Guice module that is used when running standalone tests.
 *
 * In case of Xpect tests, the Xpect runner will use this module to override the default runtime module bindings of the
 * language in use.
 *
 */
@XpectGuiceModule
public class N4JSStandaloneTestsModule extends BaseTestModule {

	/** Constructor enables JS support */
	public N4JSStandaloneTestsModule() {
		JSActivationUtil.enableJSSupport();
	}

	/**
	 * This bindings triggers a registration of the language services (validators, resource description managers, etc.)
	 * provided by this module with the global EMF registry.
	 *
	 * Due to its eager-singleton binding, it will be executed at the time of injector creation.
	 */
	@SingletonBinding(eager = true)
	public Class<? extends N4JSStandaloneRegistrationHelper> bindRegistrationHelper() {
		return N4JSStandaloneRegistrationHelper.class;
	}

	/** */
	public Class<? extends IDiagnosticConverter> bindDiagnosticConverter() {
		return ExceptionAwareDiagnosticConverter.class;
	}

	/** */
	public Class<? extends N4JSParseHelper> bindN4JSParseHelper() {
		return SmokeTestWriter.class;
	}

	/** */
	public Class<? extends ParseHelper<Script>> bindParseHelperScript() {
		return SmokeTestWriter.class;
	}

	/**
	 * Bind a custom IAllContainerState in a testing context. See {@link N4JSTestsAllContainerState} for an explanation.
	 */
	public Class<? extends IAllContainersState.Provider> bindAllContainerState() {
		return N4JSTestsAllContainerState.Provider.class;
	}
}
