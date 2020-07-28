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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.validation.Issue.IssueImpl;

import com.google.common.base.Strings;

/**
 * GH-1537
 *
 * All line and column numbers are one-based. Offset is zero-based.
 *
 * @deprecated Obsolete with Xtext 2.22
 *
 */
@Deprecated
public class LSPIssue extends IssueImpl {

	/**
	 * Cast the given issue to a {@link LSPIssue}. Provides a slightly more helpful error message over a plain cast via
	 * {@code (LSPIssue)issue}.
	 */
	public static LSPIssue cast(Issue issue) {
		if (issue instanceof LSPIssue) {
			return (LSPIssue) issue;
		}
		throw new ClassCastException("Cannot cast " + issue.getClass().getName()
				+ " to LSPIssue. Issue was not created from EmfDiagnosticToLSPIssueConverter.");
	}

	/**
	 * Unsafe, but checked cast of the given list of issues to a list of {@link LSPIssue lsp issues}. Provides a
	 * slightly more helpful error message over a plain cast via {@code (LSPIssue)issue}.
	 *
	 * If the given list is modified after this invocation, we are doomed.
	 */
	@SuppressWarnings("unchecked")
	public static List<? extends LSPIssue> cast(List<? extends Issue> issues) {
		for (Issue issue : issues) {
			cast(issue);
		}
		return Collections.unmodifiableList((List<? extends LSPIssue>) (issues));
	}

	private static final String NULL = "";
	private int lineNumberEnd;
	private int columnEnd;

	/** Constructor with initializers to avoid NPEs on Integers */
	public LSPIssue() {
		this.setOffset(0);
		this.setLength(0);
		this.setColumn(0);
		this.setColumnEnd(0);
		this.setLineNumber(0);
		this.setLineNumberEnd(0);
		this.setCode("");
		this.setMessage("");
		this.setUriToProblem(null);
		this.setSeverity(Severity.IGNORE);
		this.setType(CheckType.FAST);
	}

	/**
	 * Copy constructor
	 */
	public LSPIssue(Issue copyFrom) {
		this();
		if (copyFrom.getOffset() != null)
			this.setOffset(copyFrom.getOffset());
		if (copyFrom.getLength() != null)
			this.setLength(copyFrom.getLength());
		if (copyFrom.getColumn() != null)
			this.setColumn(copyFrom.getColumn());
		if (copyFrom.getLineNumber() != null)
			this.setLineNumber(copyFrom.getLineNumber());
		if (copyFrom.getCode() != null)
			this.setCode(copyFrom.getCode());
		if (copyFrom.getMessage() != null)
			this.setMessage(copyFrom.getMessage());
		if (copyFrom.getUriToProblem() != null)
			this.setUriToProblem(copyFrom.getUriToProblem());
		if (copyFrom.getSeverity() != null)
			this.setSeverity(copyFrom.getSeverity());
		if (copyFrom.getType() != null)
			this.setType(copyFrom.getType());
		if (copyFrom.getData() != null)
			this.setData(copyFrom.getData().clone());

		if (copyFrom instanceof LSPIssue) {
			this.setLineNumberEnd(((LSPIssue) copyFrom).getLineNumberEnd());
			this.setColumnEnd(((LSPIssue) copyFrom).getColumnEnd());
		}
	}

	/** @return line of end of issue */
	public int getLineNumberEnd() {
		return lineNumberEnd;
	}

	/** sets line of end of issue */
	public void setLineNumberEnd(int lineNumberEnd) {
		this.lineNumberEnd = lineNumberEnd;
	}

	/** @return column of end of issue */
	public int getColumnEnd() {
		return columnEnd;
	}

	/** sets line of column of issue */
	public void setColumnEnd(int columnEnd) {
		this.columnEnd = columnEnd;
	}

	/**
	 * @see Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	public void writeExternal(DataOutput out) throws IOException {
		out.writeInt(this.getOffset());
		out.writeInt(this.getLength());
		out.writeInt(this.getColumn());
		out.writeInt(this.getColumnEnd());
		out.writeInt(this.getLineNumber());
		out.writeInt(this.getLineNumberEnd());
		out.writeUTF(Strings.nullToEmpty(this.getCode()));
		out.writeUTF(Strings.nullToEmpty(this.getMessage()));

		URI uriToProblem = this.getUriToProblem();
		String uriToProblemStr = uriToProblem == null ? NULL : uriToProblem.toString();
		out.writeUTF(uriToProblemStr);

		Severity severity = this.getSeverity();
		int severityKey = severity == null ? 0 : severity.ordinal() + 1;
		out.writeInt(severityKey);

		CheckType checkType = this.getType();
		int checkTypeKey = checkType == null ? 0 : checkType.ordinal() + 1;
		out.writeInt(checkTypeKey);

		String[] data = getData();
		if (data == null) {
			out.writeInt(0);
		} else {
			out.writeInt(data.length);
			for (String s : data) {
				out.writeUTF(s);
			}
		}
	}

	/**
	 * @see Externalizable#readExternal(java.io.ObjectInput)
	 */
	public void readExternal(DataInput in) throws IOException {
		this.setOffset(in.readInt());
		this.setLength(in.readInt());
		this.setColumn(in.readInt());
		this.setColumnEnd(in.readInt());
		this.setLineNumber(in.readInt());
		this.setLineNumberEnd(in.readInt());
		this.setCode(in.readUTF());
		this.setMessage(in.readUTF());

		String uriToProblemStr = in.readUTF();
		URI uriToProblem = NULL.equals(uriToProblemStr) ? null : URI.createURI(uriToProblemStr);
		this.setUriToProblem(uriToProblem);

		int severityKey = in.readInt();
		Severity severity = severityFromKey(severityKey);
		this.setSeverity(severity);

		int checkTypeKey = in.readInt();
		CheckType checkType = checkTypeFromKey(checkTypeKey);
		this.setType(checkType);

		int dataLength = in.readInt();
		if (dataLength > 0) {
			String[] data = new String[dataLength];
			for (int i = 0; i < dataLength; i++) {
				data[i] = in.readUTF();
			}
			this.setData(data);
		}
	}

	private static final Severity[] severities = Severity.values();

	private static Severity severityFromKey(int severityKey) {
		switch (severityKey) {
		case 0:
			return null;
		default:
			return severities[severityKey - 1];
		}
	}

	private static final CheckType[] checkTypes = CheckType.values();

	private static CheckType checkTypeFromKey(int checkTypeKey) {
		switch (checkTypeKey) {
		case 0:
			return null;
		default:
			return checkTypes[checkTypeKey - 1];
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LSPIssue)) {
			return false;
		}
		LSPIssue n4Issue = (LSPIssue) obj;
		boolean equals = true;
		equals &= Objects.equals(n4Issue.getOffset(), getOffset());
		equals &= Objects.equals(n4Issue.getLength(), getLength());
		equals &= Objects.equals(n4Issue.getColumn(), getColumn());
		equals &= n4Issue.getColumnEnd() == getColumnEnd();
		equals &= Objects.equals(n4Issue.getLineNumber(), getLineNumber());
		equals &= n4Issue.getLineNumberEnd() == getLineNumberEnd();
		equals &= Objects.equals(n4Issue.getCode(), getCode());
		equals &= Objects.equals(n4Issue.getMessage(), getMessage());
		equals &= n4Issue.getUriToProblem() == getUriToProblem();
		equals &= n4Issue.getSeverity() == getSeverity();
		equals &= n4Issue.getType() == getType();
		return equals;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getOffset(), getLength(), getColumn(), getColumnEnd(), getLineNumber(), getLineNumberEnd(),
				getCode(), getMessage(), getUriToProblem(), getSeverity(), getType());
	}
}
