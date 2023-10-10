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

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;

import org.eclipse.n4js.n4JS.Script;

/**
 * Expects errors in provided {@link Script} object. If script errors are not found it will rise Error
 */
public class NegativeAnalyser extends BaseAnalyser implements Analyser {

	
	// TODO after java update bring back null analysis
	// public NegativeAnalyser(final @Nonnull Logger logger) {
	public NegativeAnalyser(final Logger logger) {
		super(logger);
	}

	
	public NegativeAnalyser(final Logger logger, final boolean logCode) {
		super(logger, logCode);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.n4js.tests.libraryparsing.analysis.DiagnosticAnalyser #analyse(java.util.List,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void analyse(Script script, String codeName, String code) {
		List<Diagnostic> errors = super.getScriptErrors(script);
		if (errors.isEmpty()) {
			String msg = "expected errors in " + codeName + " but there was not a single one";
			if (this.logger.isDebugEnabled()) {
				StringBuilder sb = new StringBuilder(msg).append("\n");
				sb.append("========== " + codeName + " ==========\n");
				if (this.logCode) {
					sb.append(code).append("\n");
				}
				sb.append("========================================").append("\n");
				this.logger.debug(sb);
			}
			assertEquals(msg, code, codeName);
		}
	}

	@Override
	public boolean isNegative() {
		return true;
	}

}
