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
package org.eclipse.n4js.n4jsx.ecmatestsuite;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.JSLibSingleTestConfig;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.analysis.Analyser;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.ecmatestsuite.ECMA5TestSuite;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;

/**
 *
 */
@InjectWith(N4JSInjectorProvider.class)
public class ECMA5TestSuiteForJSX extends ECMA5TestSuite {

	@Override
	protected Script doParse(String code, URI uri, XtextResourceSet resourceSet, Analyser analyser) throws Exception {
		if (isStrictModeTestCase(code)) {
			uri = uri.trimFileExtension().appendFileExtension("n4jsx");
		} else {
			uri = uri.trimFileExtension().appendFileExtension("jsx");
		}
		// ensure code contains JSX syntax to fail in case of wrong
		// configuration
		if (!analyser.isNegative()) {
			code = code + "\nvar x42 = <div/>\n";
		}
		return parserN4JS.parse(code, uri, resourceSet);
	}

	/**
	 * Constructor
	 */
	public ECMA5TestSuiteForJSX(JSLibSingleTestConfig config) {
		super(config);
	}

}
