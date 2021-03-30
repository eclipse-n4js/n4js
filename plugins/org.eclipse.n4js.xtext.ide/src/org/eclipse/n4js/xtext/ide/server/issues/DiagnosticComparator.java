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
import org.eclipse.lsp4j.Position;

import com.google.common.collect.ComparisonChain;

/**
 * Sorts diagnostics according to line and position.
 */
public class DiagnosticComparator implements Comparator<Diagnostic> {

	private final Comparator<Diagnostic> delegate = (Diagnostic d1, Diagnostic d2) -> {
		Position p1 = d1.getRange().getStart();
		Position p2 = d2.getRange().getStart();
		int result = ComparisonChain.start()
				.compare(p1.getLine(), p2.getLine())
				.compare(p2.getCharacter(), p2.getCharacter())
				.result();
		return result;
	};

	@Override
	public int compare(Diagnostic o1, Diagnostic o2) {
		return delegate.compare(o1, o2);
	}

}
