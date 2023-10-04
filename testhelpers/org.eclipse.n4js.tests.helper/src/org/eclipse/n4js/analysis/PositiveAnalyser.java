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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.testing.serializer.SerializerTestHelper;

import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.common.io.LineProcessor;

/**
 * Expects errors in provided {@link Script} object. If script errors are not found it will rise Error
 */
public class PositiveAnalyser extends BaseAnalyser implements Analyser {
	/**
	 * Currently unused serializer tester
	 */
	protected final SerializerTestHelper serializerTester;

	
	// TODO after java update bring back null analysis
	// public PositiveAnalyser(final @Nonnull Logger logger) {
	public PositiveAnalyser(final Logger logger, SerializerTestHelper serializerTester) {
		super(logger);
		this.serializerTester = serializerTester;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.n4js.tests.libraryparsing.analysis.DiagnosticAnalyser #analyse(java.util.List, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void analyse(Script script, String codeName, String code) {
		List<Diagnostic> errors = null;
		try {
			errors = getScriptErrors(script);
			// if (serializerTester != null) {
			// serializerTester.assertSerializeWithNodeModel(script);
			// }
		} catch (Throwable t) {
			StringWriter writer = new StringWriter();
			t.printStackTrace(new PrintWriter(writer));
			assertEquals(code, writer.toString());
			return;
		}
		if (!errors.isEmpty()) {
			String msg = "expected no errors in " + codeName + " but I got : " + errors.size();
			if (this.logger.isDebugEnabled()) {
				StringBuilder sb = new StringBuilder(msg).append("\n");
				sb.append("========== " + codeName + " ==========\n");
				if (this.logCode) {
					sb.append(code).append("\n");
				}
				sb.append(">>>> errors: ").append("\n");
				sb.append(this.aggregateDiagnosticsToStringBuilder(codeName, errors));
				sb.append(">>>> warnings: ").append("\n");
				sb.append("<<<<").append("\n");
				sb.append("========================================").append("\n");
				this.logger.debug(sb);
			}
			assertEquals(msg, withLineNumbers(code),
					this.aggregateDiagnosticsToStringBuilder(codeName, errors).toString());
		}
	}

	private String withLineNumbers(String code) {
		try {
			return CharStreams.readLines(new StringReader(code), new LineProcessor<String>() {

				private final StringBuilder lines = new StringBuilder();
				private int lineNo = 1;

				@Override
				public boolean processLine(String line) throws IOException {
					lines.append(Strings.padStart(String.valueOf(lineNo++), 3, ' ')).append(": ").append(line)
							.append("\n");
					return true;
				}

				@Override
				public String getResult() {
					return lines.toString();
				}
			});
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage());
		}
	}

	@Override
	public boolean isNegative() {
		return false;
	}

}
