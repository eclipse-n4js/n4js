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
package org.eclipse.n4js.xpect.tests;

class T {
	int a;;
	int i;

	void foo() {
	};
}

class S extends T {
	@Override
	void foo() {
		super.foo();

		System.out.println(this.a);

		this.i = 0;
	}
}
