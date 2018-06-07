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

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Test rule used along with {@link RepeatTest} to let a test loop for a couple of rounds.
 */
public class RepeatedTestRule implements TestRule {
	@Override
	public Statement apply(Statement statement, Description description) {
		RepeatTest repeat = description.getAnnotation(RepeatTest.class);
		if (repeat == null) {
			repeat = description.getTestClass().getAnnotation(RepeatTest.class);
		}
		if (repeat != null) {
			return new RepeatableTestStatement(repeat.times(), statement);
		}
		return statement;
	}
}
