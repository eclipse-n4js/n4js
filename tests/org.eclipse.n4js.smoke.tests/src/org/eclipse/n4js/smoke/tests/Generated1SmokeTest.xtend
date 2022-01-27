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
 package org.eclipse.n4js.smoke.tests

import com.google.common.base.Supplier
import com.google.common.base.Suppliers
import com.google.inject.Inject
import com.google.inject.Provider
import com.google.inject.Singleton
import org.eclipse.emf.common.util.URI
import org.eclipse.n4js.analysis.ExceptionAnalyser
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.testing.smoketest.ProcessedBy
import org.eclipse.xtext.testing.smoketest.XtextSmokeTestRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

/**
 */
@RunWith(XtextSmokeTestRunner)
@SuiteClasses(GeneratedSmokeTestCases1)
@ProcessedBy(value = DefaultSmokeTester, processInParallel = true)
class Generated1SmokeTest {

	@Singleton
	static class DefaultSmokeTester extends UniqueScenarioProcessor {

		@Inject ExceptionAnalyser analyser;

		@Inject ParseHelper<Script> parseHelper;

		@Inject Provider<XtextResourceSet> resourceSetProvider;

		/**
		 * Used to ensure thread-safe initialization of Xtext infrastructure.
		 * <p>
		 * Before this tweak, the smoke tests sometimes ran into an {@link OutOfMemoryError} during the
		 * parallel execution of {@code parseHelper.parse()} in the first N test cases, seemingly caused
		 * by a race during Guice injector creation and/or Guice object tree creation and possibly other
		 * parts of Xtext infrastructure initialization.
		 */
		private final Supplier<Void> initializer = Suppliers.memoize[
			// invoked only once; parsing some dummy source code to trigger initialization of all components
			parseHelper.parse("let x = 42;", URI.createURI("sample.n4js"), resourceSetProvider.get());
			return null;
		];

		override doProcessFile(String data) throws Exception {
			initializer.get();
			val script = parseHelper.parse(data, URI.createURI("sample.n4js"), resourceSetProvider.get());
			analyser.analyse(script, "SmokeTest", data);
		}
	}
}
