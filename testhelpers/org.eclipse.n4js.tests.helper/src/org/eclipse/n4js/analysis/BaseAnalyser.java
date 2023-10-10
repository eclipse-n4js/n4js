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
package org.eclipse.n4js.analysis;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.xtext.diagnostics.ExceptionDiagnostic;

import org.eclipse.n4js.n4JS.Script;

/**
 */
public abstract class BaseAnalyser {

	
	protected final Logger logger;
	
	protected final boolean logCode;

	/**
	 *
	 */
	public BaseAnalyser(Logger logger) {
		this(logger, false);
	}

	/**
	 *
	 */
	public BaseAnalyser(Logger logger, boolean logCode) {
		this.logger = logger;
		this.logCode = logCode;
	}

	
	// TODO after java update bring back null analysis
	// protected StringBuilder agregateDiagnosticsToStringBuilder(@Nonnull final List<Diagnostic> issues) {
	protected StringBuilder aggregateDiagnosticsToStringBuilder(String codeName, final List<Diagnostic> issues) {
		StringBuilder result = new StringBuilder(codeName).append('\n');
		for (Diagnostic diagnostic : issues) {
			if (diagnostic instanceof ExceptionDiagnostic) {
				((ExceptionDiagnostic) diagnostic).getException().printStackTrace();
			}
			result.append(" - line: " + diagnostic.getLine() + ", message: " + diagnostic.getMessage() + "\n");
		}
		return result;
	}

	
	// TODO after java update bring back null analysis
	// protected List<Diagnostic> getScriptErrors(final @Nonnull Script script) {
	protected List<Diagnostic> getScriptErrors(final Script script) {
		// may throw NPE
		return script.eResource().getErrors();
	}

}
