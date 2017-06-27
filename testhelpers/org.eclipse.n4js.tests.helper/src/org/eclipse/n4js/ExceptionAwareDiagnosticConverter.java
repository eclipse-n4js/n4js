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
package org.eclipse.n4js;

import java.util.List;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.validation.CompositeEValidator;
import org.eclipse.xtext.validation.DiagnosticConverterImpl;
import org.eclipse.xtext.validation.Issue;

import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;

/**
 * By default Xtext validators catch all exceptions and log them only to console. So the tests may still be green
 * although exceptions has been thrown during execution. To avoid that in {@link AbstractN4JSDeclarativeValidator} has
 * been overridden so that NullPointerExceptions are thrown to top. But this isn't enough as {@link CompositeEValidator}
 * catches all exceptions and wraps them as diagnostics (that are logged out only). So the tests still wouldn't fail.
 * Therefore the diagnostic converter (that is used to convert the exceptions catched in CompositeEValidator to
 * diagnostics) is overridden here to throw an {@link AssertionError} (that doesn't extends java.lang.Exception) so this
 * will eventually fails the test.
 */
public class ExceptionAwareDiagnosticConverter extends DiagnosticConverterImpl {

	@Override
	public void convertValidatorDiagnostic(org.eclipse.emf.common.util.Diagnostic diagnostic, IAcceptor<Issue> acceptor) {
		super.convertValidatorDiagnostic(diagnostic, acceptor);
		handleExceptions(diagnostic);
	}

	private void handleExceptions(org.eclipse.emf.common.util.Diagnostic diagnostic) {
		if (Diagnostic.OK == diagnostic.getSeverity()) {
			return;
		}
		if (diagnostic instanceof BasicDiagnostic) {
			List<?> data = ((BasicDiagnostic) diagnostic).getData();
			for (Object entry : data) {
				if (entry instanceof Throwable) {
					throw new AssertionError(entry);
				}
			}
		}
	}
}
