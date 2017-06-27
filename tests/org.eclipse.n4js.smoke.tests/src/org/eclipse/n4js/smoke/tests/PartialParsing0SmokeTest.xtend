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
import java.io.StringReader
import org.eclipse.xtext.junit4.smoketest.ProcessedBy
import org.eclipse.xtext.junit4.smoketest.Scenarios
import org.eclipse.xtext.junit4.smoketest.XtextSmokeTestRunner
import org.eclipse.xtext.junit4.smoketest.processors.PartialParsingProcessor
import org.eclipse.xtext.parser.IParser
import org.eclipse.xtext.util.ReplaceRegion
import org.junit.ComparisonFailure
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

/**
 * This is not a collection, but used for single test cases basically during development (for debugging etc.).
 * Checked in, so that we do not have to copy the infrastructure again.
 */
@RunWith(XtextSmokeTestRunner)
@SuiteClasses(GeneratedSmokeTestCases0)
@ProcessedBy(value=PartialParsing0SmokeTestProcessor, processInParallel=true)
@Scenarios(SkipThreeCharactersInBetween)
class PartialParsing0SmokeTest {
	@Singleton
	static class PartialParsing0SmokeTestProcessor extends PartialParsingProcessor {
		@Inject private IParser parser;

		override String processFile(String completeData, String data, int offset, int len,
			String change) throws Exception {

			// val IParseResult initialParseResult =
			parser.parse(new StringReader(data));
			val String newData = applyDelta(data, offset, len, change);
			val ReplaceRegion replaceRegion = new ReplaceRegion(offset, len, change);

			val StringBuilder strb = new StringBuilder(data);
			replaceRegion.applyTo(strb);
			println("------------------------------------------")
			println(data);
			println("..........................................")
			println(strb.toString);
			println("------------------------------------------")
			println(("Change: " + replaceRegion));
			println("------------------------------------------")
			try {
//				val IParseResult reparsed = parser.reparse(initialParseResult, replaceRegion);
//				val IParseResult parsedFromScratch = parser.parse(new StringReader(newData));
//				assertEqual(data, newData, parsedFromScratch, reparsed);
				return newData;
			} catch (Throwable e) {
				val ComparisonFailure throwMe = new ComparisonFailure(e.getMessage(), newData,
					replaceRegion + ":" + data);
				throwMe.initCause(e);
				throw throwMe;
			}
		}
	}
}
