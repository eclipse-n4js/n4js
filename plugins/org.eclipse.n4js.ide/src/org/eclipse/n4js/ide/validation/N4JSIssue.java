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
package org.eclipse.n4js.ide.validation;

import org.eclipse.xtext.validation.Issue.IssueImpl;

/**
 * GH-1537
 */
public class N4JSIssue extends IssueImpl {
	private int lineNumberEnd;
	private int columnEnd;

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

}
