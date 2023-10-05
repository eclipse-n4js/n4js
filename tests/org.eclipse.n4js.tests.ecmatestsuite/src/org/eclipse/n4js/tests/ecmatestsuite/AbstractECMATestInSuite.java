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
package org.eclipse.n4js.tests.ecmatestsuite;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.AbstractJSLibTest;
import org.eclipse.n4js.JSLibSingleTestConfig;
import org.eclipse.n4js.TestCodeProvider;
import org.eclipse.n4js.analysis.Analyser;
import org.eclipse.n4js.analysis.NegativeAnalyser;
import org.eclipse.n4js.analysis.PositiveAnalyser;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.serializer.SerializerTestHelper;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 */
public abstract class AbstractECMATestInSuite extends AbstractJSLibTest {

	/**
	 * The logger to use.
	 */
	protected static final Logger logger = Logger.getLogger("debugECMA");

	
	@Inject
	protected ParseHelper<Script> parserN4JS;
	
	@Inject
	protected Provider<XtextResourceSet> resourceSetProvider;
	
	@Inject
	protected ValidationTestHelper validationTestHelper;
	
	@Inject
	protected SerializerTestHelper tester;

	/**
	 * @param config
	 *            the config to use
	 */
	protected AbstractECMATestInSuite(JSLibSingleTestConfig config) {
		super(config);
	}

	/**
	 * generated instances of the tests will use this base implementation
	 */
	@Test
	public void test() throws Exception {
		if (this.parserN4JS == null) {
			throw new Error("parser instance is null");
		}

		String code = TestCodeProvider.getContentsFromFileEntry(config.entry, config.resourceName);
		if (code == null) {
			throw new Error("test data code instance is null");
		}

		Analyser analyser = createAnalyzer(code);
		XtextResourceSet resourceSet = resourceSetProvider.get();
		URI uri = URI.createURI(config.entry.getName());
		if (isStrictModeTestCase(code)) {
			uri = uri.trimFileExtension().appendFileExtension("n4js");
		}
		try {
			Script script = doParse(code, uri, resourceSet, analyser);
			if (config.isValidator()) {
				Analyser syntaxAnalyzer = new PositiveAnalyser(logger, tester);
				syntaxAnalyzer.analyse(script, config.entry.getName(), code);
				// TODO figure out why we ignore the validation result
				validationTestHelper.validate(script);
			}

			analyser.analyse(script, config.entry.getName(), code);
		} catch (Throwable t) {
			if (!t.getStackTrace()[10].getClassName().contains("Black")) {
				System.out.println(config.entry.getName());
			}
			throw t;
		}
	}

	/**
	 * Subclasses may override to reuse test suite for sublanguages. Analyzer is only passed in order to check whether
	 * test is positive or negative.
	 */
	protected Script doParse(String code, URI uri, XtextResourceSet resourceSet,
			@SuppressWarnings("unused") Analyser analyser) throws Exception {
		return parserN4JS.parse(code, uri, resourceSet);
	}

	/**
	 * Returns true, if code contains "flags: [onlyStrict]"
	 */
	protected boolean isStrictModeTestCase(String code) {
		return code.contains("flags: [onlyStrict]");
	}

	/**
	 * Creates the analyser for the given test code.
	 */
	protected Analyser createAnalyzer(String code) {
		Analyser analyser;
		if (code.contains("@negative")) {
			if (code.contains("@negative TypeError")) {
				analyser = new PositiveAnalyser(logger, tester);
			} else {
				analyser = new NegativeAnalyser(logger);
			}
		} else {
			analyser = new PositiveAnalyser(logger, tester);
		}
		return analyser;
	}

}
