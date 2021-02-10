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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.n4js.ide.tests.helper.server.StringLSP4J;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.eclipse.n4js.utils.Strings;
import org.junit.Assert;

import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 *
 */
public class XtIssueHelper {
	static final String MESSAGE = "MESSAGE";
	static final String AT = "AT";
	static final Pattern PATTERN_MESSAGE_AT = Pattern
			.compile("\\\"(?<" + MESSAGE + ">[^\\\"]*)\\\"\\s*at\\s*\\\"(?<" + AT + ">[^\\\"]+)\\\"");

	final StringLSP4J strLsp4j;
	final XtFileData xtData; // sorted by position
	final TreeSet<Diagnostic> errors; // sorted by position
	final TreeSet<Diagnostic> warnings; // sorted by position
	final Multimap<MethodData, Diagnostic> testToErrors;
	final Multimap<MethodData, Diagnostic> testToWarnings;

	int compareDiagnosticByOffset(Diagnostic i1, Diagnostic i2) {
		return xtData.getOffset(i1.getRange().getStart()) - xtData.getOffset(i2.getRange().getStart());
	}

	XtIssueHelper(StringLSP4J strLsp4j, XtFileData xtData, Collection<Diagnostic> unsortedIssues,
			List<MethodData> issueTests) {

		this.strLsp4j = strLsp4j;
		this.xtData = xtData;
		this.errors = new TreeSet<>(this::compareDiagnosticByOffset);
		this.warnings = new TreeSet<>(this::compareDiagnosticByOffset);
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

		LinkedHashMap<Integer, MethodData> positionToErrors = new LinkedHashMap<>();
		LinkedHashMap<Integer, MethodData> positionToWarnings = new LinkedHashMap<>();
		for (MethodData test : issueTests) {
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

		testToErrors = getTestToIssues(strLsp4j, xtData, errors, positionToErrors);
		testToWarnings = getTestToIssues(strLsp4j, xtData, warnings, positionToWarnings);
	}

	static private Multimap<MethodData, Diagnostic> getTestToIssues(StringLSP4J strLsp4j, XtFileData xtData,
			TreeSet<Diagnostic> issues, LinkedHashMap<Integer, MethodData> positionToTest) {

		Multimap<MethodData, Diagnostic> testToIssues = LinkedHashMultimap.create();
		if (issues.isEmpty()) {
			return testToIssues;
		}

		Diagnostic firstDiagnostic = issues.first();

		Iterator<Map.Entry<Integer, MethodData>> posTestIter = positionToTest.entrySet().iterator();
		Assert.assertTrue(
				"Unexpected issue found: " + strLsp4j.toString(firstDiagnostic),
				posTestIter.hasNext() || issues.isEmpty());

		if (posTestIter.hasNext()) {
			Map.Entry<Integer, MethodData> currPosTest = posTestIter.next();
			Assert.assertTrue(
					"Unexpected issue found: " + strLsp4j.toString(firstDiagnostic),
					getOffset(xtData, firstDiagnostic) > currPosTest.getKey());

			Map.Entry<Integer, MethodData> nextPosTest = posTestIter.hasNext() ? posTestIter.next() : null;
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

	void noerrors(MethodData data) {
		Multimap<String, String> atToActualMessages = getAtToAcutalMessage(testToErrors, data);
		String msg = "Expected no errors, but found: "
				+ Strings.toString(e -> issueToString(e.getValue(), e.getKey()), atToActualMessages.entries());
		Assert.assertTrue(msg, atToActualMessages.isEmpty());
	}

	void nowarnings(MethodData data) {
		Multimap<String, String> atToActualMessages = getAtToAcutalMessage(testToWarnings, data);
		String msg = "Expected no warnings, but found: "
				+ Strings.toString(e -> issueToString(e.getValue(), e.getKey()), atToActualMessages.entries());
		Assert.assertTrue(msg, atToActualMessages.isEmpty());
	}

	void errors(MethodData data) {
		issues(testToErrors, data, "error");
	}

	void warnings(MethodData data) {
		issues(testToWarnings, data, "warning");
	}

	private void issues(Multimap<MethodData, Diagnostic> testToIssues, MethodData data, String msgIssue) {
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

	/**  */
	private Multimap<String, String> getAtToExpectedMessage(MethodData data) {
		Multimap<String, String> atToExpectedMessages = LinkedHashMultimap.create();

		Matcher matcher = PATTERN_MESSAGE_AT.matcher(data.expectation);
		while (matcher.find()) {
			String message = matcher.group(MESSAGE);
			String atString = matcher.group(AT);
			atToExpectedMessages.put(atString, message);
		}

		return atToExpectedMessages;
	}

	/**  */
	private Multimap<String, String> getAtToAcutalMessage(Multimap<MethodData, Diagnostic> testToIssues,
			MethodData data) {

		Multimap<String, String> atToActualMessages = LinkedHashMultimap.create();
		Collection<Diagnostic> relatedErrors = testToIssues.get(data);
		for (Diagnostic error : relatedErrors) {
			String atString = xtData.getText(error.getRange());
			atToActualMessages.put(atString, error.getMessage());
		}
		return atToActualMessages;
	}

	private String issueToString(String message, String atLocation) {
		return "'" + message + "' at '" + atLocation + "'";
	}
}
