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
package org.eclipse.n4js.smoke.tests;

import java.io.StringReader;

import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.testing.smoketest.ProcessedBy;
import org.eclipse.xtext.testing.smoketest.Scenario;
import org.eclipse.xtext.testing.smoketest.Scenarios;
import org.eclipse.xtext.testing.smoketest.XtextSmokeTestRunner;
import org.eclipse.xtext.testing.smoketest.processors.PartialParsingProcessor;
import org.eclipse.xtext.util.ReplaceRegion;
import org.junit.ComparisonFailure;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * This is not a collection, but used for single test cases basically during development (for debugging etc.). Checked
 * in, so that we do not have to copy the infrastructure again.
 */
@RunWith(XtextSmokeTestRunner.class)
@SuiteClasses(GeneratedSmokeTestCases0.class)
@ProcessedBy(value = PartialParsing0SmokeTest.PartialParsing0SmokeTestProcessor.class, processInParallel = true)
@Scenarios(Scenario.SkipThreeCharactersInBetween)
public class PartialParsing0SmokeTest {
	@Singleton
	static public class PartialParsing0SmokeTestProcessor extends PartialParsingProcessor {
		@Inject
		private IParser parser;

		@Override
		public String processFile(String completeData, String data, int offset, int len, String change)
				throws Exception {

			// val IParseResult initialParseResult =
			parser.parse(new StringReader(data));
			String newData = applyDelta(data, offset, len, change);
			ReplaceRegion replaceRegion = new ReplaceRegion(offset, len, change);

			StringBuilder strb = new StringBuilder(data);
			replaceRegion.applyTo(strb);
			System.out.println("------------------------------------------");
			System.out.println(data);
			System.out.println("..........................................");
			System.out.println(strb.toString());
			System.out.println("------------------------------------------");
			System.out.println(("Change: " + replaceRegion));
			System.out.println("------------------------------------------");
			try {
				// val IParseResult reparsed = parser.reparse(initialParseResult, replaceRegion);
				// val IParseResult parsedFromScratch = parser.parse(new StringReader(newData));
				// assertEqual(data, newData, parsedFromScratch, reparsed);
				return newData;
			} catch (Throwable e) {
				ComparisonFailure throwMe = new ComparisonFailure(e.getMessage(), newData,
						replaceRegion + ":" + data);
				throwMe.initCause(e);
				throw throwMe;
			}
		}
	}
}
