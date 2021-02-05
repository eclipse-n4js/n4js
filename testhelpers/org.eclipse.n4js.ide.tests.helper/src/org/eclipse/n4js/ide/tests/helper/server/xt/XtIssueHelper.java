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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 *
 */
public class XtIssueHelper {
	static final String MESSAGE = "MESSAGE";
	static final String AT = "AT";
	static final Pattern PATTERN_MESSAGE_AT = Pattern
			.compile("\\\"(?<" + MESSAGE + ">[^\\\"]*)\\\"\\s*at\\s*\\\"(?<" + AT + ">[^\\\"]+)\\\"");

	final XtFileData xtData; // sorted by position
	final TreeSet<Diagnostic> errors; // sorted by position
	final TreeSet<Diagnostic> warnings; // sorted by position
	final Multimap<MethodData, Diagnostic> testToErrors;
	final Multimap<MethodData, Diagnostic> testToWarnings;

	static int compareDiagnosticByOffset(Diagnostic i1, Diagnostic i2) {
		return (i1.getRange().getStart().getLine() + i1.getRange().getStart().getCharacter())
				- (i2.getRange().getStart().getLine() + i2.getRange().getStart().getCharacter());
	}

	XtIssueHelper(XtFileData xtData, Collection<Diagnostic> unsortedIssues, List<MethodData> issueTests) {
		this.xtData = xtData;
		this.errors = new TreeSet<>(XtIssueHelper::compareDiagnosticByOffset);
		this.warnings = new TreeSet<>(XtIssueHelper::compareDiagnosticByOffset);
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

		testToErrors = getTestToIssues(errors, positionToErrors);
		testToWarnings = getTestToIssues(warnings, positionToErrors);
	}

	static private Multimap<MethodData, Diagnostic> getTestToIssues(TreeSet<Diagnostic> issues,
			LinkedHashMap<Integer, MethodData> positionToTest) {

		Iterator<Map.Entry<Integer, MethodData>> posTestIter = positionToTest.entrySet().iterator();
		Preconditions.checkState(posTestIter.hasNext() || issues.isEmpty(),
				"Unexpected issue found: " + issues.get(0)); // TODO

		Multimap<MethodData, Diagnostic> testToIssues = HashMultimap.create();
		if (posTestIter.hasNext()) {
			Map.Entry<Integer, MethodData> currPosTest = posTestIter.next();
			Preconditions.checkState(getOffset(issues.get(0)) > currPosTest.getKey(),
					"Unexpected issue found: " + issues.get(0)); // TODO

			Map.Entry<Integer, MethodData> nextPosTest = posTestIter.next();
			for (Diagnostic diag : issues) {
				if (nextPosTest != null && getOffset(diag) > nextPosTest.getKey()) {
					currPosTest = nextPosTest;
					nextPosTest = posTestIter.hasNext() ? posTestIter.next() : null;
				}
				testToIssues.put(currPosTest.getValue(), diag);
			}
		}
		return testToIssues;
	}

	static private int getOffset(Diagnostic diag) {
		return diag.getRange().getStart().getLine() + diag.getRange().getStart().getCharacter();
	}

	void noerrors(MethodData data) {
		Preconditions.checkState(testToErrors.isEmpty(),
				"Expected no errors, but found: " + getStringLSP4J().toString(testToErrors));
	}

	void nowarnings(MethodData data) {
		Preconditions.checkState(testToWarnings.isEmpty(),
				"Expected no warnings, but found: " + getStringLSP4J().toString(testToErrors));
	}

	void errors(MethodData data) {
		Multimap<String, String> atToExpectedMessages = getAtToExpectedMessage(data);
		Multimap<String, String> atToActualMessages = getAtToAcutalMessage(data);

		Set<String> onlyExpectedAts = new HashSet<>(atToExpectedMessages.keySet());
		onlyExpectedAts.removeAll(atToActualMessages.keySet());
		Set<String> onlyActualAts = new HashSet<>(atToActualMessages.keySet());
		onlyActualAts.removeAll(atToExpectedMessages.keySet());

		Preconditions.checkState(onlyExpectedAts.isEmpty(),
				"Expected error not found: " + getStringLSP4J().toString(onlyExpectedAts)); // TODO

		Preconditions.checkState(onlyActualAts.isEmpty(),
				"Unexpected error found: " + getStringLSP4J().toString(onlyActualAts)); // TODO

		for (String atString : atToExpectedMessages.keySet()) {
			Set<String> onlyExpectMessages = new HashSet<>(atToExpectedMessages.get(atString));
			Set<String> onlyActualMessages = new HashSet<>(atToActualMessages.get(atString));

			onlyExpectMessages.removeAll(atToActualMessages.keySet());
			onlyActualMessages.removeAll(atToExpectedMessages.keySet());

			Preconditions.checkState(onlyExpectMessages.isEmpty(),
					"Expected error not found: " + getStringLSP4J().toString(onlyExpectMessages)); // TODO

			Preconditions.checkState(onlyActualMessages.isEmpty(),
					"Unexpected error found: " + getStringLSP4J().toString(onlyActualMessages)); // TODO
		}
	}

	void warnings(MethodData data) {

	}

	/**  */
	private Multimap<String, String> getAtToExpectedMessage(MethodData data) {
		Multimap<String, String> atToExpectedMessages = HashMultimap.create();

		Matcher matcher = PATTERN_MESSAGE_AT.matcher(data.expectation);
		while (matcher.find()) {
			String message = matcher.group(MESSAGE);
			String atString = matcher.group(AT);
			atToExpectedMessages.put(atString, message);
		}

		return atToExpectedMessages;
	}

	/**  */
	private Multimap<String, String> getAtToAcutalMessage(MethodData data) {
		Multimap<String, String> atToActualMessages = HashMultimap.create();
		Collection<Diagnostic> relatedErrors = testToErrors.get(data);
		for (Diagnostic error : relatedErrors) {
			String atString = xtData.getText(error.getRange());
			atToActualMessages.put(atString, error.getMessage());
		}
		return atToActualMessages;
	}
}
