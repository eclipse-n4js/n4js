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

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.testing.util.ParseHelper;

import com.google.inject.Inject;

import org.eclipse.n4js.n4JS.Script;

/**
 * Small utility that will permute some input string and simulate a user typing into that document. It'll ensure that no
 * exceptions surface to the user.
 *
 * Clients may inject this helper into their test class and user the {@link #assertNoException(CharSequence) assertion}
 * to verify the robustness of the validator and the type inference.
 */
public class SmokeTester extends AbstractSmokeTester {
	@Inject
	private ExceptionAnalyser analyser;

	@Inject
	private ParseHelper<Script> parseHelper;

	
	@Override
	protected void processFile(String input) throws Exception {
		Script script = parseHelper.parse(input, URI.createURI("sample.n4js"), newResourceSet());
		analyser.analyse(script, "SmokeTest", input);
	}
}
