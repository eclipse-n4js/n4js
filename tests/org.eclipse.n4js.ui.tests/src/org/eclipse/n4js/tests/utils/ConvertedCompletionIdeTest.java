/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.utils;

import org.eclipse.n4js.ide.tests.helper.server.AbstractCompletionTest;

public abstract class ConvertedCompletionIdeTest extends AbstractCompletionTest {

	protected void performTest(String sourceCode, CharSequence[] expectedProposals) {
	}

	private class LegacyBuilder {
		private String sourceCode;

		public LegacyBuilder append(CharSequence sourceCode) {
			this.sourceCode = sourceCode + "<|>";
			return this;
		}

		public void assertText(CharSequence... expectedProposals) {
			performTest(sourceCode, expectedProposals);
		}
	}
}
