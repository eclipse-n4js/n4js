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
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.utils.ReflectionUtils;
import org.eclipse.xtext.diagnostics.AbstractDiagnostic;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.util.ITextRegionWithLineInformation;
import org.eclipse.xtext.util.LineAndColumn;
import org.eclipse.xtext.validation.AbstractValidationDiagnostic;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.DiagnosticConverterImpl;
import org.eclipse.xtext.validation.FeatureBasedDiagnostic;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.RangeBasedDiagnostic;

/**
 * GH-1537: Purpose of this class is to extend the {@link Issue} by information about the end-line and the end-column.
 * Reason for this extension is that this information is needed when publishing diagnostics the LSP client.
 */
@SuppressWarnings("deprecation")
public class EmfDiagnosticToLSPIssueConverter extends DiagnosticConverterImpl {

	/**
	 * Line and column numbers are one-based
	 */
	static class ExtendedIssueLocation extends IssueLocation {
		int lineNumberEnd;
		int columnEnd;
	}

	@Override
	public void convertResourceDiagnostic(Diagnostic diagnostic, Severity severity, IAcceptor<Issue> acceptor) {
		// START: Following copied from super method
		LSPIssue issue = new LSPIssue(); // Changed
		issue.setSyntaxError(diagnostic instanceof XtextSyntaxDiagnostic);
		issue.setSeverity(severity);
		issue.setLineNumber(diagnostic.getLine());
		issue.setColumn(diagnostic.getColumn());
		issue.setMessage(diagnostic.getMessage());

		if (diagnostic instanceof org.eclipse.xtext.diagnostics.Diagnostic) {
			org.eclipse.xtext.diagnostics.Diagnostic xtextDiagnostic = (org.eclipse.xtext.diagnostics.Diagnostic) diagnostic;
			issue.setOffset(xtextDiagnostic.getOffset());
			issue.setLength(xtextDiagnostic.getLength());
		}
		if (diagnostic instanceof AbstractDiagnostic) {
			AbstractDiagnostic castedDiagnostic = (AbstractDiagnostic) diagnostic;
			issue.setUriToProblem(castedDiagnostic.getUriToProblem());
			issue.setCode(castedDiagnostic.getCode());
			issue.setData(castedDiagnostic.getData());

			// START: Changes here
			INode node = ReflectionUtils.getMethodReturn(AbstractDiagnostic.class, "getNode", diagnostic);
			int posEnd = castedDiagnostic.getOffset() + castedDiagnostic.getLength();
			LineAndColumn lineAndColumn = NodeModelUtils.getLineAndColumn(node, posEnd);
			issue.setLineNumberEnd(lineAndColumn.getLine());
			issue.setColumnEnd(lineAndColumn.getColumn());
			// END: Changes here
		}
		issue.setType(CheckType.FAST);
		acceptor.accept(issue);
		// END: Following copied from super method
	}

	@Override
	public void convertValidatorDiagnostic(org.eclipse.emf.common.util.Diagnostic diagnostic,
			IAcceptor<Issue> acceptor) {

		// START: Following copied from super method
		LSPIssue issue = new LSPIssue(); // Changed
		Severity severity = getSeverity(diagnostic);
		if (severity == null)
			return;
		issue.setSeverity(severity);

		IssueLocation locationData = getLocationData(diagnostic);
		if (locationData != null) {
			issue.setLineNumber(toInt(locationData.lineNumber));
			issue.setColumn(toInt(locationData.column));
			issue.setOffset(toInt(locationData.offset));
			issue.setLength(toInt(locationData.length));
			// START: Changes here
			if (locationData instanceof ExtendedIssueLocation) {
				ExtendedIssueLocation n4jsLocationData = (ExtendedIssueLocation) locationData;
				issue.setLineNumberEnd(n4jsLocationData.lineNumberEnd);
				issue.setColumnEnd(n4jsLocationData.columnEnd);
			}
			// END: Changes here
		}
		final EObject causer = getCauser(diagnostic);
		if (causer != null) {
			issue.setUriToProblem(EcoreUtil.getURI(causer));
		} else {
			// see N4JSValidator.N4JSMethodWrapperCancelable#invoke()
			for (Object obj : diagnostic.getData()) {
				if (obj instanceof URI) {
					issue.setUriToProblem((URI) obj);
				}
			}
		}

		issue.setCode(getIssueCode(diagnostic));
		issue.setType(getIssueType(diagnostic));
		issue.setData(getIssueData(diagnostic));

		// marker.put(IXtextResourceChecker.DIAGNOSTIC_KEY, diagnostic);
		issue.setMessage(diagnostic.getMessage());
		// marker.put(IMarker.PRIORITY, Integer.valueOf(IMarker.PRIORITY_LOW));
		acceptor.accept(issue);
		// END: Following copied from super method
	}

	static int toInt(Integer integer) {
		return integer == null ? 0 : (int) integer;
	}

	@Override
	protected IssueLocation getLocationData(org.eclipse.emf.common.util.Diagnostic diagnostic) {
		// START: Following copied from super method
		EObject causer = getCauser(diagnostic);
		if (causer != null) {
			if (diagnostic instanceof RangeBasedDiagnostic) {
				RangeBasedDiagnostic castedDiagnostic = (RangeBasedDiagnostic) diagnostic;
				INode parserNode = NodeModelUtils.getNode(causer);
				ExtendedIssueLocation result = new ExtendedIssueLocation();
				if (parserNode != null) {
					// START: Changes here
					LineAndColumn lineAndColumnStart = NodeModelUtils.getLineAndColumn(parserNode,
							castedDiagnostic.getOffset());
					result.lineNumber = lineAndColumnStart.getLine();
					result.column = lineAndColumnStart.getColumn();

					LineAndColumn lineAndColumnEnd = NodeModelUtils.getLineAndColumn(parserNode,
							castedDiagnostic.getOffset() + castedDiagnostic.getLength());
					result.lineNumberEnd = lineAndColumnEnd.getLine();
					result.columnEnd = lineAndColumnEnd.getColumn();
					// END: Changes here
				}
				result.offset = castedDiagnostic.getOffset();
				result.length = castedDiagnostic.getLength();
				return result;
			} else if (diagnostic instanceof FeatureBasedDiagnostic) {
				FeatureBasedDiagnostic castedDiagnostic = (FeatureBasedDiagnostic) diagnostic;
				return getLocationData(causer, castedDiagnostic.getFeature(), castedDiagnostic.getIndex());
			} else {
				// feature is the second element see Diagnostician.getData
				List<?> data = diagnostic.getData();
				Object feature = data.size() > 1 ? data.get(1) : null;
				EStructuralFeature structuralFeature = resolveStructuralFeature(causer, feature);
				return getLocationData(causer, structuralFeature, 0);
			}
		}
		return null;
		// END: Following copied from super method
	}

	@Override
	protected IssueLocation getLocationForNode(INode node) {
		ITextRegionWithLineInformation nodeRegion = node.getTextRegionWithLineInformation();
		ExtendedIssueLocation result = new ExtendedIssueLocation();
		result.offset = nodeRegion.getOffset();
		result.length = nodeRegion.getLength();

		LineAndColumn lineAndColumnStart = NodeModelUtils.getLineAndColumn(node, result.offset);
		result.lineNumber = lineAndColumnStart.getLine();
		result.column = lineAndColumnStart.getColumn();

		LineAndColumn lineAndColumnEnd = NodeModelUtils.getLineAndColumn(node, result.offset + result.length);
		result.lineNumberEnd = lineAndColumnEnd.getLine();
		result.columnEnd = lineAndColumnEnd.getColumn();
		return result;
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
				} else if (object instanceof Throwable) {
					/** Catch this throwable and put it into the message */
					Throwable thr = ((Throwable) object);
					StringWriter sw = new StringWriter();
					thr.printStackTrace(new PrintWriter(sw));
					String stacktraceAsString = sw.toString();
					issueData.add(stacktraceAsString);
				}
			}
			return issueData.toArray(new String[issueData.size()]);
		}
	}
}
