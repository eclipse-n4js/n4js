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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtMethodHelper;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.IEObjectCoveringRegion;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.expectation.IStringExpectation;
import org.eclipse.xpect.expectation.StringExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;

/**
 */
@XpectImport(N4JSOffsetAdapter.class)
public class AccessModifierXpectMethod {

	/**
	 * This xpect method can evaluate the accessibility of {@link TMember}s. For example, given a field of a class or a
	 * {@link ParameterizedPropertyAccessExpression}, the xpect methods returns their explicit or implicit declared
	 * accessibility such as {@code public} or {@code private}.
	 */
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	@Xpect
	public void accessModifier(
			@StringExpectation IStringExpectation expectation,
			IEObjectCoveringRegion offset) {

		EObject context = offset.getEObject();
		String actual = XtMethodHelper.getAccessModifierString(context);
		expectation.assertEquals(actual);
	}

}
