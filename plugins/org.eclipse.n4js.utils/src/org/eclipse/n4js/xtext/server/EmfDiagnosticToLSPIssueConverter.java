/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.server;

import static com.google.common.collect.Lists.newArrayList;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.AbstractValidationDiagnostic;
import org.eclipse.xtext.validation.DiagnosticConverterImpl;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.IssueImpl;

/**
 * This class handles some cases to better deal with errors and diagnostics.
 */
public class EmfDiagnosticToLSPIssueConverter extends DiagnosticConverterImpl {

	@Override
	public void convertValidatorDiagnostic(org.eclipse.emf.common.util.Diagnostic diagnostic,
			IAcceptor<Issue> acceptor) {

		Severity severity = getSeverity(diagnostic);
		if (severity == null)
			return;
		IssueImpl issue = new Issue.IssueImpl();
		issue.setSeverity(severity);

		IssueLocation locationData = getLocationData(diagnostic);
		if (locationData != null) {
			issue.setLineNumber(locationData.lineNumber);
			issue.setColumn(locationData.column);
			issue.setOffset(locationData.offset);
			issue.setLength(locationData.length);
			issue.setLineNumberEnd(locationData.lineNumberEnd);
			issue.setColumnEnd(locationData.columnEnd);
		}
		final EObject causer = getCauser(diagnostic);
		if (causer != null) {
			issue.setUriToProblem(EcoreUtil.getURI(causer));
		} else {
			// START CHANGE see N4JSValidator.N4JSMethodWrapperCancelable#invoke()
			for (Object obj : diagnostic.getData()) {
				if (obj instanceof URI) {
					issue.setUriToProblem((URI) obj);
				}
			}
			// END CHANGE
		}

		issue.setCode(getIssueCode(diagnostic));
		issue.setType(getIssueType(diagnostic));
		issue.setData(getIssueData(diagnostic));

		// marker.put(IXtextResourceChecker.DIAGNOSTIC_KEY, diagnostic);
		issue.setMessage(diagnostic.getMessage());
		// marker.put(IMarker.PRIORITY, Integer.valueOf(IMarker.PRIORITY_LOW));
		acceptor.accept(issue);
	}

	/** Overwritten to catch stack trace. */
	@Override
	protected String[] getIssueData(org.eclipse.emf.common.util.Diagnostic diagnostic) {
		if (diagnostic instanceof AbstractValidationDiagnostic) {
			AbstractValidationDiagnostic diagnosticImpl = (AbstractValidationDiagnostic) diagnostic;
			return diagnosticImpl.getIssueData();
		} else {
			// replace any EObjects by their URIs
			EObject causer = getCauser(diagnostic);
			List<String> issueData = newArrayList();
			for (Object object : diagnostic.getData()) {
				if (object != causer && object instanceof EObject) {
					EObject eObject = (EObject) object;
					issueData.add(EcoreUtil.getURI(eObject).toString());
				} else if (object instanceof String) {
					issueData.add((String) object);
					// START CHANGE
				} else if (object instanceof Throwable) {
					/** Catch this throwable and put it into the message */
					Throwable thr = ((Throwable) object);
					StringWriter sw = new StringWriter();
					thr.printStackTrace(new PrintWriter(sw));
					String stacktraceAsString = sw.toString();
					issueData.add(stacktraceAsString);
					// END CHANGE
				}
			}
			return issueData.toArray(new String[issueData.size()]);
		}
	}
}
