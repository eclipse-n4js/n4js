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

import java.util.ArrayDeque;
import java.util.Deque;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.common.types.access.TypeResource;
import org.eclipse.xtext.resource.XtextResource;
import org.junit.Assert;
import org.xpect.expectation.IStringExpectation;
import org.xpect.expectation.StringExpectation;
import org.xpect.parameter.ParameterParser;
import org.xpect.runner.Xpect;
import org.xpect.xtext.lib.tests.LinkingTest;
import org.xpect.xtext.lib.util.XtextOffsetAdapter.ICrossEReferenceAndEObject;

import com.google.common.base.Joiner;

import org.eclipse.n4js.n4JS.NamedElement;

/**
 */
@SuppressWarnings("restriction")
public class LinkingXpectMethod extends LinkingTest {

	/**
	 * Similar to {@link #linkedName(IStringExpectation, ICrossEReferenceAndEObject)} but concatenating the fully
	 * qualified name again instead of using the qualified name provider, as the latter may not create a valid name for
	 * non-globally available elements.
	 * <p>
	 * The qualified name created by retrieving all "name" properties of the target and its containers, using '/' as
	 * separator.
	 */
	@Xpect
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	public void linkedPathname(@StringExpectation IStringExpectation expectation,
			ICrossEReferenceAndEObject arg1) {
		EObject targetObject = (EObject) arg1.getEObject().eGet(arg1.getCrossEReference());
		if (targetObject == null) {
			Assert.fail("Reference is null");
			return; // to avoid warnings in the following
		}
		if (targetObject.eIsProxy())
			Assert.fail("Reference is a Proxy: " + ((InternalEObject) targetObject).eProxyURI());
		Resource targetResource = targetObject.eResource();
		if (targetResource instanceof TypeResource)
			targetResource = arg1.getEObject().eResource();
		if (!(targetResource instanceof XtextResource))
			Assert.fail("Referenced EObject is not in an XtextResource.");

		Deque<String> segments = new ArrayDeque<>();
		do {
			EStructuralFeature nameFeature = targetObject.eClass().getEStructuralFeature("name");
			if (nameFeature != null) {
				Object obj = targetObject.eGet(nameFeature);
				if (obj instanceof String) {
					segments.push((String) obj);
				}
			} else {
				if (targetObject instanceof NamedElement) {
					segments.push(((NamedElement) targetObject).getName());
				}
			}
			targetObject = targetObject.eContainer();
		} while (targetObject != null);
		String pathname = Joiner.on('/').join(segments);
		expectation.assertEquals(pathname);
	}
}
