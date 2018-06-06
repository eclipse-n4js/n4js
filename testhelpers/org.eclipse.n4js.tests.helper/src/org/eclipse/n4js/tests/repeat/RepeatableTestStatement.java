/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.repeat;

import org.junit.runners.model.Statement;

/**
 * Encapsulates the logic for tests that should be executed in a tight loop.
 */
class RepeatableTestStatement extends Statement {
	private final int times;
	private final Statement statement;

	RepeatableTestStatement(int times, Statement statement) {
		this.times = times;
		this.statement = statement;
	}

	@Override
	public void evaluate() throws Throwable {
		for (int i = 1; i <= times; i++) {
			System.out.printf("Run %d of %d\n", i, times);
			statement.evaluate();
		}
	}
}