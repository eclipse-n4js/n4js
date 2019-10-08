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

/**
 * Overwrites some bindings of N4jscFactory
 */
public class N4jscTestFactory extends N4jscFactory {

	/** Enable overwriting bindings */
	static public void set() {
		resetInjector();
		N4jscFactory.INSTANCE = new N4jscTestFactory(false);
	}

	/** Enable overwriting bindings. Deactivates the cli backend. */
	static public void setAndDeactivateBackend() {
		resetInjector();
		N4jscFactory.INSTANCE = new N4jscTestFactory(true);
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

	final private boolean deactivateBackend;

	N4jscTestFactory(boolean deactivateBackend) {
		this.deactivateBackend = deactivateBackend;
	}

	/** Thrown when the backend is called. */
	static class NoopBackend extends N4jscBackend {
		@Override
		public void goalApi(N4jscOptions options) {
			// do nothing
		}

		@Override
		public void goalClean(N4jscOptions options) {
			// do nothing
		}

		@Override
		public void goalCompile(N4jscOptions options) {
			// do nothing
		}

		@Override
		public void goalLsp(N4jscOptions options) {
			// do nothing
		}

		@Override
		public void goalWatch(N4jscOptions options) {
			// do nothing
		}
	}

	@Override
	N4jscBackend internalCreateBackend() throws Exception {
		if (deactivateBackend) {
			return new NoopBackend();
		} else {
			return super.internalCreateBackend();
		}
	}

	@Override
	N4jscLanguageClient internalGetLanguageClient() {
		N4jscLanguageClient callback = getOrCreateInjector().getInstance(N4jscTestLanguageClient.class);
		return callback;
	}

}
