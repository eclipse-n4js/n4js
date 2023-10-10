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
package org.eclipse.n4js.smoke.tests;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.analysis.ExceptionAnalyser;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.smoketest.ProcessedBy;
import org.eclipse.xtext.testing.smoketest.XtextSmokeTestRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@RunWith(XtextSmokeTestRunner.class)
@SuiteClasses(GeneratedSmokeTestCases3.class)
@ProcessedBy(value = Generated3SmokeTest.DefaultSmokeTester.class, processInParallel = true)
public class Generated3SmokeTest {

	@Singleton
	static public class DefaultSmokeTester extends UniqueScenarioProcessor {

		@Inject
		ExceptionAnalyser analyser;

		@Inject
		ParseHelper<Script> parseHelper;

		@Inject
		Provider<XtextResourceSet> resourceSetProvider;

		@Override
		public void doProcessFile(String data) throws Exception {
			Script script = parseHelper.parse(data, URI.createURI("sample.n4js"), resourceSetProvider.get());
			analyser.analyse(script, "SmokeTest", data);
		}
	}

}
