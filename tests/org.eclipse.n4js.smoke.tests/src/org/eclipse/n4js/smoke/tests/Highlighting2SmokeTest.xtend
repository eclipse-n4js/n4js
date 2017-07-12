/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
 package org.eclipse.n4js.smoke.tests

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.n4js.editor.syntaxcoloring.HighlightingParserTester
import org.eclipse.xtext.testing.smoketest.ProcessedBy
import org.eclipse.xtext.testing.smoketest.XtextSmokeTestRunner
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses
import org.junit.ComparisonFailure
import java.io.StringWriter
import java.io.PrintWriter

/**
 */
@RunWith(XtextSmokeTestRunner)
@SuiteClasses(GeneratedSmokeTestCases2)
@ProcessedBy(value = HighlightingSmokeTester, processInParallel = true)
class Highlighting2SmokeTest {

	@Singleton
	static class HighlightingSmokeTester extends UniqueScenarioProcessor {

		@Inject HighlightingParserTester highlightingParserTester

		override doProcessFile(String data) throws Exception {
			try {
				highlightingParserTester.getTokens(data)
			} catch(Throwable th) {
				val writer = new StringWriter();
				th.printStackTrace(new PrintWriter(writer))
				throw new ComparisonFailure(th.getMessage, data, writer.toString)
			}
		}
	}

}
