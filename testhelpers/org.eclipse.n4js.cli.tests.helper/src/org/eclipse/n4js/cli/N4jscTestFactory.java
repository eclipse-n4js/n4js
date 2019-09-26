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
		N4jscFactory.INSTANCE = new N4jscTestFactory();
	}

	/** Disable overwriting bindings */
	static public void unset() {
		N4jscFactory.INSTANCE = new N4jscFactory();
	}

	@Override
	N4jscCallback internalCreateN4jscCallback(Injector injector) {
		N4jscCallback callback = injector.getInstance(N4jscTestCallback.class);
		return callback;
	}

}
