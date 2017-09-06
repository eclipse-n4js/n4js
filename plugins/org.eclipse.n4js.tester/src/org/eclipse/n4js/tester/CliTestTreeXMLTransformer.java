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
package org.eclipse.n4js.tester;

import static java.util.Collections.emptyMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.n4js.tester.domain.TestCase;
import org.eclipse.n4js.tester.domain.TestResult;
import org.eclipse.n4js.tester.domain.TestStatus;
import org.eclipse.n4js.tester.domain.TestSuite;
import org.eclipse.n4js.tester.domain.TestTree;

/**
 * {@link TestTree Test tree} transformer for the CLI. Assumes the test tree contains results, based on that produces
 * junit like xml report.
 * <p>
 * Naive implementation that follows some mangelhaft assumptions.
 * <p>
 * Report is being build manually, consider switch to jackson object mapper
 *
 * See <a href="https://github.com/FasterXML/jackson-dataformat-xml">jackson-dataformat-xml</a>
 *
 */
public class CliTestTreeXMLTransformer implements TestTreeTransformer {
	private static final String START_TESTSUITE = "<testsuite";
	private static final String START_TESTCASE = "<testcase";
	private static final String START_ERROR = "<error";
	private static final String END_NL = ">\n";
	private static final String END_TESTCASE = "</testcase>\n";
	private static final String END_TESTSUITE = "</testsuite>\n";
	private static final String END_QUOTES = "\"";

	private static final String SP_NAME_EQ = " name=\"";
	private static final String SP_TIMESTAMP_EQ = " timestamp=\"";
	private static final String SP_TESTS_EQ = " tests=\"";
	private static final String SP_CLASSNAME_EQ = " classname=\"";
	private static final String SP_ERRORS_EQ = " errors=\"";
	private static final String SP_FAILURES_EQ = " failures=\"";
	private static final String SP_SKIPPED_EQ = " skipped=\"";
	private static final String SP_TIME_EQ = " time=\"";
	private static final String SP_MESSAGE_EQ = " message=\"";

	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");

	@Override
	public Object apply(final TestTree tree) {
		return apply(tree, emptyMap());
	}

	@Override
	public Object apply(final TestTree tree, final Map<String, Object> properties) {
		final IndentLevel indendLevel = new IndentLevel("\t");
		final StringBuilder sb = new StringBuilder();

		// custom properties, behavior as in mangelhaft junit reporter
		String n4TestName = (String) properties.get("n4-test-name");
		String n4TestPackage = (String) properties.get("n4-test-package");

		sb.append("<?xml version=\"1.0\"?>\n");
		sb.append("<testsuites>\n");
		indendLevel.increase();
		tree.getSuites().forEach(suite -> {
			sb.append(stringifyTestSuite(suite, indendLevel, n4TestName, n4TestPackage));
		});
		indendLevel.decrease();

		sb.append("</testsuites>\n");
		return sb;
	}

	private StringBuilder stringifyTestSuite(TestSuite testSuite, IndentLevel indendLevel, String n4TestName,
			String n4TestPackage) {
		StringBuilder sb = new StringBuilder();
		String name = (n4TestName == null || n4TestName.isEmpty()) ? testSuite.getName() : n4TestName;
		String packageOrEmpty = (n4TestPackage == null || n4TestPackage.isEmpty()) ? ""
				: (" package=\"" + n4TestPackage + "\"");

		// no parallel processing, just convenience to use in lambda
		final AtomicInteger testCases = new AtomicInteger(0);
		final AtomicInteger testCasesErrors = new AtomicInteger(0);
		final AtomicInteger testCasesFailures = new AtomicInteger(0);
		final AtomicInteger testCasesSkipped = new AtomicInteger(0);
		final AtomicLong time = new AtomicLong(0);

		// TODO double iteration of the test cases,see call to #stringifyTestCase
		testSuite.forEach(tc -> {
			testCases.incrementAndGet();
			time.addAndGet(tc.getResult().getElapsedTime());

			switch (tc.getResult().getTestStatus()) {
			case PASSED:
				break;

			case SKIPPED:
			case SKIPPED_NOT_IMPLEMENTED:
			case SKIPPED_PRECONDITION:
			case SKIPPED_IGNORE:
			case SKIPPED_FIXME:
				testCasesSkipped.incrementAndGet();
				break;

			case ERROR:
				testCasesErrors.incrementAndGet();
				break;

			case FAILED:
				testCasesFailures.incrementAndGet();
				break;

			default:
				throw new RuntimeException("unhandled status " + tc.getResult().getTestStatus());
			}
		});

		sb.append(START_TESTSUITE)
				.append(packageOrEmpty)
				.append(SP_NAME_EQ).append(name).append(END_QUOTES)
				.append(SP_TESTS_EQ).append(testCases).append(END_QUOTES)
				.append(SP_ERRORS_EQ).append(testCasesErrors).append(END_QUOTES)
				.append(SP_FAILURES_EQ).append(testCasesFailures).append(END_QUOTES)
				.append(SP_SKIPPED_EQ).append(testCasesSkipped).append(END_QUOTES)
				.append(SP_TIME_EQ).append(converTime(time.get())).append(END_QUOTES)
				.append(SP_TIMESTAMP_EQ).append(df.format(new Date())).append(END_QUOTES)
				.append(END_NL);

		indendLevel.increase();
		testSuite.getTestCases().forEach(testCase -> sb.append(stringifyTestCase(testCase, indendLevel)));

		indendLevel.decrease();
		sb.append(END_TESTSUITE);
		return sb;
	}

	private StringBuilder stringifyTestCase(TestCase testCase, IndentLevel indendLevel) {
		StringBuilder sb = new StringBuilder();
		TestResult result = testCase.getResult();
		TestStatus status = result.getTestStatus();

		sb.append(indendLevel.get())
				.append(START_TESTCASE)
				.append(SP_NAME_EQ).append(testCase.getName()).append(END_QUOTES)
				.append(SP_CLASSNAME_EQ).append(testCase.getClassName()).append(END_QUOTES)
				.append(SP_TIME_EQ).append(converTime(testCase.getResult().getElapsedTime())).append(END_QUOTES)
				.append(END_NL);

		// add concrete data
		switch (status) {
		case PASSED:
			// no extra data
			break;

		case SKIPPED:
		case SKIPPED_NOT_IMPLEMENTED:
		case SKIPPED_PRECONDITION:
		case SKIPPED_IGNORE:
		case SKIPPED_FIXME:
			indendLevel.increase();
			sb.append(indendLevel.get()).append("<skipped/>\n");
			indendLevel.decrease();
			break;

		case FAILED:
		case ERROR:
			indendLevel.increase();
			sb.append(indendLevel.get())
					.append(START_ERROR)
					.append(SP_MESSAGE_EQ).append(escapeString(testCase.getResult().getMessage())).append(END_QUOTES)
					.append(END_NL);
			indendLevel.decrease();
			break;

		default:
			throw new RuntimeException("unhandled status " + status);
		}

		sb.append(indendLevel.get()).append(END_TESTCASE);
		return sb;
	}

	private double converTime(long miliseconds) {
		return miliseconds / 1000.000;
	}

	private String escapeString(String message) {
		// escape strings for embedding in the XML
		return message;
	}

}
