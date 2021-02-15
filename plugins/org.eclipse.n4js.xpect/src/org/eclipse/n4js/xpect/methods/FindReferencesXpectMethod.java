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
package org.eclipse.n4js.xpect.methods;

import java.util.List;

import org.eclipse.n4js.ide.tests.helper.server.xt.IEObjectCoveringRegion;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtMethods;
import org.eclipse.n4js.xpect.common.N4JSXpectRunner;
import org.eclipse.n4js.xpect.methods.scoping.IN4JSCommaSeparatedValuesExpectation;
import org.eclipse.n4js.xpect.methods.scoping.N4JSCommaSeparatedValuesExpectation;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.xtext.lib.setup.XtextStandaloneSetup;
import org.eclipse.xpect.xtext.lib.setup.XtextWorkspaceSetup;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * This class provides a Xpect method to specify tests regarding the {@link IReferenceFinder}
 */
@SuppressWarnings("restriction")
@RunWith(N4JSXpectRunner.class)
@XpectImport({ XtextStandaloneSetup.class, XtextWorkspaceSetup.class })
public class FindReferencesXpectMethod {
	@Inject
	XtMethods mh;

	/**
	 * This Xpect methods compares all computed references at a given EObject to the expected references. The expected
	 * references include the line number.
	 */
	@Xpect
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	public void findReferences(
			@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset) {

		List<String> result = mh.getFindReferences(offset.getEObject(), offset.getOffset());
		expectation.assertEquals(result);
	}
}
