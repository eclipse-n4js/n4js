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
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.IEObjectCoveringRegion;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.expectation.IStringExpectation;
import org.eclipse.xpect.expectation.StringExpectation;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;

import com.google.inject.Inject;

/**
 */
@XpectImport(N4JSOffsetAdapter.class)
public class TypeXpectMethod {
	@Inject
	private XtMethodHelper mh;

	/**
	 * Checks that an element/expression has a certain type. Usage:
	 *
	 * <pre>
	 * // Xpect type of 'location' --&gt; Type
	 * </pre>
	 *
	 * The location (of) is optional.
	 *
	 * @param arg1
	 *            the location identified by the offset. Note that there are different implementations of IEObjectOwner,
	 *            and we need IEStructuralFeatureAndEObject, while ICrossEReferenceAndEObject or IEAttributeAndEObject
	 *            would not work in all cases (as not all eobjects we test have cross references or attributes, but
	 *            feature is the join of both).
	 */
	@ParameterParser(syntax = "('of' arg1=OFFSET)?")
	@Xpect
	public void type(
			@StringExpectation IStringExpectation expectation,
			IEObjectCoveringRegion arg1) { // ICrossEReferenceAndEObject arg1) {
		String actual = getTypeString(arg1, false);
		if (expectation == null) {
			throw new IllegalStateException("No expectation specified, add '--> Type'");
		}
		expectation.assertEquals(actual);
	}

	/**
	 * Checks that an element/expression has a certain expected type (i.e. Xsemantics judgment expectedTypeIn). Usage:
	 *
	 * <pre>
	 * // Xpect expectedType at 'location' --&gt; Type
	 * </pre>
	 *
	 * The location (at) is optional.
	 *
	 * @param arg1
	 *            the location identified by the offset. Note that there are different implementations of IEObjectOwner,
	 *            and we need IEStructuralFeatureAndEObject, while ICrossEReferenceAndEObject or IEAttributeAndEObject
	 *            would not work in all cases (as not all eobjects we test have cross references or attributes, but
	 *            feature is the join of both).
	 */
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	@Xpect
	public void expectedType(
			@StringExpectation IStringExpectation expectation,
			IEObjectCoveringRegion arg1) { // ICrossEReferenceAndEObject arg1) {
		String actual = getTypeString(arg1, true);
		if (expectation == null) {
			throw new IllegalStateException("No expectation specified, add '--> Type'");
		}
		expectation.assertEquals(actual);
	}

	private String getTypeString(IEObjectCoveringRegion offset, boolean expectedType) {
		EObject eobject = offset.getEObject();
		String calculatedString = mh.getTypeString(eobject, expectedType);
		return calculatedString;
	}

	/**
	 * Checks that a call expression to a generic function/method has the correct type arguments. Mostly intended for
	 * checking the automatically inferred type arguments in case of a non-parameterized call expression.
	 *
	 * <pre>
	 * class C {
	 *     &lt;S,T> m(S p1, T p2) {}
	 * }
	 * var C c;
	 *
	 * // Xpect typeArgs of 'm' --> number, string
	 * c.m(42,"hello");
	 * </pre>
	 *
	 * Note that the offset denotes the target(!) of the call expression, not the call expression itself. Usually it is
	 * enough to provide the last IdentifierRef before the call expression's parentheses.
	 */
	@ParameterParser(syntax = "'of' arg1=OFFSET")
	@Xpect
	public void typeArgs(@StringExpectation IStringExpectation expectation, IEObjectCoveringRegion arg1) {
		final String actual = getTypeArgumentsString(arg1);
		if (expectation == null) {
			throw new IllegalStateException("no expectation specified, add '--> type arguments string'");
		}
		expectation.assertEquals(actual);
	}

	private String getTypeArgumentsString(IEObjectCoveringRegion offset) {
		final EObject eobject = offset != null ? offset.getEObject() : null;
		String calculatedString = mh.getTypeArgumentsString(eobject);
		return calculatedString;
	}
}
