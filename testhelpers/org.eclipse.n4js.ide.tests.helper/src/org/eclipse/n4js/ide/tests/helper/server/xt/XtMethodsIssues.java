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
package org.eclipse.n4js.ide.tests.helper.server.xt;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.n4js.utils.Strings;
import org.junit.Assert;

import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * Class to relate expected issues to actual issues
 */
public class XtMethodsIssues {
	static final String MESSAGE = "MESSAGE";
	static final String AT = "AT";
	static final Pattern PATTERN_MESSAGE_AT = Pattern
			.compile("\\\"(?<" + MESSAGE + ">.*)\\\"\\s*at\\s*\\\"(?<" + AT + ">.*)\\\"");

	final Comparator<Diagnostic> issueComparator;

	final XtFileData xtData; // sorted by position
	final TreeSet<Diagnostic> errors; // sorted by position
	final TreeSet<Diagnostic> warnings; // sorted by position
	final Multimap<XtMethodData, Diagnostic> testToErrors;
	final Multimap<XtMethodData, Diagnostic> testToWarnings;

	XtMethodsIssues(XtFileData xtData, Collection<Diagnostic> unsortedIssues, List<XtMethodData> issueTests) {
		this.xtData = xtData;
		this.issueComparator = Comparator
				.comparing((Diagnostic d) -> xtData.getOffset(d.getRange().getStart()))
				.thenComparing(Diagnostic::getMessage);
		this.errors = new TreeSet<>(issueComparator);
		this.warnings = new TreeSet<>(issueComparator);

		for (Diagnostic issue : unsortedIssues) {
			switch (issue.getSeverity()) {
			case Error:
				errors.add(issue);
				break;
			case Warning:
				warnings.add(issue);
				break;

			default:
				Preconditions.checkState(false, "Unsupported issue");
			}
		}

		Multimap<Integer, XtMethodData> positionToErrors = LinkedHashMultimap.create();
		Multimap<Integer, XtMethodData> positionToWarnings = LinkedHashMultimap.create();
		for (XtMethodData test : issueTests) {
			switch (test.name) {
			case "errors":
			case "noerrors":
				positionToErrors.put(test.offset, test);
				break;
			case "warnings":
			case "nowarnings":
				positionToWarnings.put(test.offset, test);
				break;

			default:
				Preconditions.checkState(false, "Unexpected test");
			}
		}

		testToErrors = getTestToIssues(xtData, errors, positionToErrors);
		testToWarnings = getTestToIssues(xtData, warnings, positionToWarnings);
	}

	/** Implementation for {@link XtIdeTest#noerrors(XtMethodData)} */
	public void getNoerrors(XtMethodData data) {
		Multimap<String, String> atToActualMessages = getAtToAcutalMessage(testToErrors, data);
		String msg = "Expected no errors, but found: "
				+ Strings.toString(e -> issueToString(e.getValue(), e.getKey()), atToActualMessages.entries());
		Assert.assertTrue(msg, atToActualMessages.isEmpty());
	}

	/** Implementation for {@link XtIdeTest#nowarnings(XtMethodData)} */
	public void getNowarnings(XtMethodData data) {
		Multimap<String, String> atToActualMessages = getAtToAcutalMessage(testToWarnings, data);
		String msg = "Expected no warnings, but found: "
				+ Strings.toString(e -> issueToString(e.getValue(), e.getKey()), atToActualMessages.entries());
		Assert.assertTrue(msg, atToActualMessages.isEmpty());
	}

	/** Implementation for {@link XtIdeTest#errors(XtMethodData)} */
	public void getErrors(XtMethodData data) {
		issues(testToErrors, data, "error");
	}

	/** Implementation for {@link XtIdeTest#warnings(XtMethodData)} */
	public void getWarnings(XtMethodData data) {
		issues(testToWarnings, data, "warning");
	}

	private void issues(Multimap<XtMethodData, Diagnostic> testToIssues, XtMethodData data, String msgIssue) {
		Multimap<String, String> atToExpectedMessages = getAtToExpectedMessage(data);
		Multimap<String, String> atToActualMessages = getAtToAcutalMessage(testToIssues, data);

		Set<String> onlyExpectedAts = new HashSet<>(atToExpectedMessages.keySet());
		onlyExpectedAts.removeAll(atToActualMessages.keySet());
		Set<String> onlyActualAts = new HashSet<>(atToActualMessages.keySet());
		onlyActualAts.removeAll(atToExpectedMessages.keySet());

		Assert.assertTrue("No " + msgIssue + " found at: " + Strings.join(", ", onlyExpectedAts),
				onlyExpectedAts.isEmpty());

		Assert.assertTrue("Unexpected " + msgIssue + " found at: " + Strings.join(", ",
				at -> Strings.join(", ", msg -> issueToString(msg, at), atToActualMessages.get(at)),
				onlyActualAts),
				onlyActualAts.isEmpty());

		for (String atString : atToExpectedMessages.keySet()) {
			Set<String> onlyExpectMessages = new LinkedHashSet<>(atToExpectedMessages.get(atString));
			Set<String> onlyActualMessages = new LinkedHashSet<>(atToActualMessages.get(atString));

			onlyExpectMessages.removeAll(atToActualMessages.keySet());
			onlyActualMessages.removeAll(atToExpectedMessages.keySet());

			Assert.assertEquals(Strings.join(", ", onlyExpectMessages), Strings.join(", ", onlyActualMessages));
		}
	}

	private Multimap<String, String> getAtToExpectedMessage(XtMethodData data) {
		Multimap<String, String> atToExpectedMessages = LinkedHashMultimap.create();

		Matcher matcher = PATTERN_MESSAGE_AT.matcher(data.expectation);
		while (matcher.find()) {
			String message = matcher.group(MESSAGE).replace("\\\"", "\"");
			String atString = matcher.group(AT).replace("\\\"", "\"").replaceAll("\\s+", " ");
			atToExpectedMessages.put(atString, message);
		}

		return atToExpectedMessages;
	}

	private Multimap<String, String> getAtToAcutalMessage(Multimap<XtMethodData, Diagnostic> testToIssues,
			XtMethodData data) {

		Multimap<String, String> atToActualMessages = LinkedHashMultimap.create();
		Collection<Diagnostic> relatedErrors = testToIssues.get(data);
		for (Diagnostic error : relatedErrors) {
			String atString = xtData.getText(error.getRange()).replaceAll("\\s+", " ");
			atToActualMessages.put(atString, error.getMessage());
		}
		return atToActualMessages;
	}

	static private Multimap<XtMethodData, Diagnostic> getTestToIssues(XtFileData xtData,
			TreeSet<Diagnostic> issues, Multimap<Integer, XtMethodData> positionToTest) {

		Multimap<XtMethodData, Diagnostic> testToIssues = LinkedHashMultimap.create();
		if (issues.isEmpty()) {
			return testToIssues;
		}

		Diagnostic firstDiagnostic = issues.first();
		boolean ignoreIssues = xtData.isModifierIgnoreIssues();

		Iterator<Map.Entry<Integer, XtMethodData>> posTestIter = positionToTest.entries().iterator();
		Assert.assertTrue(
				"Unexpected issue found: " + issueToString(xtData, firstDiagnostic),
				ignoreIssues || (posTestIter.hasNext() || issues.isEmpty()));

		if (posTestIter.hasNext()) {
			Map.Entry<Integer, XtMethodData> currPosTest = posTestIter.next();
			Assert.assertTrue(
					"Unexpected issue found: " + issueToString(xtData, firstDiagnostic),
					ignoreIssues || (getOffset(xtData, firstDiagnostic) > currPosTest.getKey()));

			Map.Entry<Integer, XtMethodData> nextPosTest = posTestIter.hasNext() ? posTestIter.next() : null;
			for (Diagnostic diag : issues) {
				while (nextPosTest != null && getOffset(xtData, diag) > nextPosTest.getKey()) {
					currPosTest = nextPosTest;
					nextPosTest = posTestIter.hasNext() ? posTestIter.next() : null;
				}
				testToIssues.put(currPosTest.getValue(), diag);
			}
		}
		return testToIssues;
	}

	private static int getOffset(XtFileData xtData, Diagnostic diag) {
		return xtData.getOffset(diag.getRange().getStart());
	}

	static private String issueToString(XtFileData xtData, Diagnostic diag) {
		String msg = diag.getMessage();
		String atLocation = xtData.getText(diag.getRange());
		return issueToString(msg, atLocation);
	}

	static private String issueToString(String message, String atLocation) {
		return "'" + message + "' at '" + atLocation + "'";
	}
}
