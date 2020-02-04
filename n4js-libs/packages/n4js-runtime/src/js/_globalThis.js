/*
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */

function getGlobalObject() {
	// Implementation note: don't use the idiom "Function('return this')()" for obtaining
	// the global object, because this may cause Content Security Policy (CSP) violations
	// in certain contexts and with certain CSP configurations.
	if (typeof globalThis === "object") {
		return globalThis;
	}
	if (typeof global === "object") {
		return global;
	}
	return self;
}

export default getGlobalObject();
