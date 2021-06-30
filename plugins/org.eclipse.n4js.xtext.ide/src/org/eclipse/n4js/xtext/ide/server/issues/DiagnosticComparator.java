/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.ide.server.issues;

import java.util.Comparator;

import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Position;

import com.google.common.collect.ComparisonChain;

/**
 * Sorts diagnostics according to line, position, severity, etc.
 */
public class DiagnosticComparator implements Comparator<Diagnostic> {

	private final Comparator<Diagnostic> delegate = (Diagnostic d1, Diagnostic d2) -> {
		Position pStart1 = d1.getRange().getStart();
		Position pStart2 = d2.getRange().getStart();
		Position pEnd1 = d1.getRange().getEnd();
		Position pEnd2 = d2.getRange().getEnd();
		int result = ComparisonChain.start()
				.compare(pStart1.getLine(), pStart2.getLine())
				.compare(pStart1.getCharacter(), pStart2.getCharacter())
				.compare(pEnd1.getLine(), pEnd2.getLine())
				.compare(pEnd1.getCharacter(), pEnd2.getCharacter())
				.compare(
						d1.getSeverity() != null ? d1.getSeverity() : DiagnosticSeverity.Hint,
						d2.getSeverity() != null ? d2.getSeverity() : DiagnosticSeverity.Hint)
				.compare(
						d1.getCode() != null ? d1.getCode() : "",
						d2.getCode() != null ? d2.getCode() : "")
				.compare(
						d1.getMessage() != null ? d1.getMessage() : "",
						d2.getMessage() != null ? d2.getMessage() : "")
				.compare(
						d1.getSource() != null ? d1.getSource() : "",
						d2.getSource() != null ? d2.getSource() : "")
				.result();
		return result;
	};

	@Override
	public int compare(Diagnostic o1, Diagnostic o2) {
		return delegate.compare(o1, o2);
	}

}
