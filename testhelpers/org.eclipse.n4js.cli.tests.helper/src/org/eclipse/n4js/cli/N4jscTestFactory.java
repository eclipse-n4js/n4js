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

import org.eclipse.n4js.cli.compiler.N4jscCallback;
import org.eclipse.n4js.cli.helper.N4jscTestCallback;

import com.google.inject.Injector;

/**
 * Overwrites some bindings of N4jscFactory
 */
public class N4jscTestFactory extends N4jscFactory {

	/** Enable overwriting bindings */
	static public void set() {
		N4jscFactory.INSTANCE = new N4jscTestFactory(false);
	}

	/** Enable overwriting bindings. Deactivates the cli backend. */
	static public void setAndDeactivateBackend() {
		N4jscFactory.INSTANCE = new N4jscTestFactory(true);
	}

	/** Disable overwriting bindings */
	static public void unset() {
		N4jscFactory.INSTANCE = new N4jscFactory();
	}

	static private Injector injector;

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
	Injector internalCreateInjector() {
		injector = super.internalCreateInjector();
		return injector;
	}

	/** @return the last {@link Injector} that was created by {@link N4jscFactory#createInjector()} */
	static public Injector getLastCreatedInjector() {
		return injector;
	}

	@Override
	N4jscCallback internalCreateN4jscCallback(Injector pInjector) {
		N4jscCallback callback = pInjector.getInstance(N4jscTestCallback.class);
		return callback;
	}

}
