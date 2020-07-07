/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli;

import org.eclipse.n4js.cli.compiler.N4jscLanguageClient;
import org.eclipse.n4js.cli.helper.N4jscTestLanguageClient;
import org.eclipse.xtext.testing.GlobalRegistries;

import com.google.common.base.Optional;
import com.google.inject.Module;

/**
 * Overwrites some bindings of N4jscFactory
 */
public class N4jscTestFactory extends N4jscFactory {

	/** Enable overwriting bindings */
	static public void set(boolean isEnabledBackend, Optional<Class<? extends Module>> overridingModule) {
		resetInjector();
		N4jscFactory.INSTANCE = new N4jscTestFactory(isEnabledBackend, overridingModule);
	}

	/** Disable overwriting bindings */
	static public void unset() {
		resetInjector();
		N4jscFactory.INSTANCE = new N4jscFactory();
	}

	/** Forces to create new injector */
	static public void resetInjector() {
		if (isInjectorCreated()) {
			GlobalRegistries.clearGlobalRegistries();
			N4jscFactory.INSTANCE.injector = null;
		}
	}

	/** @return true iff an injector was created already */
	static public boolean isInjectorCreated() {
		return N4jscFactory.INSTANCE.injector != null;
	}

	final private boolean isEnabledBackend;
	final private Optional<Class<? extends Module>> overridingModule;

	N4jscTestFactory(boolean isEnabledBackend, Optional<Class<? extends Module>> overridingModule) {
		this.isEnabledBackend = isEnabledBackend;
		this.overridingModule = overridingModule;
	}

	/** Thrown when the backend is called. */
	static class NoopBackend extends N4jscBackend {
		@Override
		public N4jscExitState goalApi(N4jscOptions options) {
			// do nothing
			return N4jscExitState.SUCCESS;
		}

		@Override
		public N4jscExitState goalClean(N4jscOptions options) {
			// do nothing
			return N4jscExitState.SUCCESS;
		}

		@Override
		public N4jscExitState goalCompile(N4jscOptions options) {
			// do nothing
			return N4jscExitState.SUCCESS;
		}

		@Override
		public N4jscExitState goalLsp(N4jscOptions options) {
			// do nothing
			return N4jscExitState.SUCCESS;
		}

		@Override
		public N4jscExitState goalWatch(N4jscOptions options) {
			// do nothing
			return N4jscExitState.SUCCESS;
		}
	}

	@Override
	N4jscBackend internalCreateBackend() throws Exception {
		if (isEnabledBackend) {
			return super.internalCreateBackend();
		} else {
			return new NoopBackend();
		}
	}

	@Override
	Optional<Class<? extends Module>> internalGetOverridingModule() {
		return overridingModule;
	}

	@Override
	N4jscLanguageClient internalGetLanguageClient() {
		N4jscLanguageClient callback = getOrCreateInjector().getInstance(N4jscTestLanguageClient.class);
		return callback;
	}

}
