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
package org.eclipse.n4js.tests.scoping;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class AT_174_StructuralTypesTest {

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;
	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ValidationTestHelper valTestHelper;

	@Test
	public void test_01() throws Exception {
		Script script = parseHelper.parse("""
				function f(p: ~Object with {a: string; b: string;}) {
					var s: string;
					s = p.a;
					s = p.b;
				}
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_02() throws Exception {
		Script script = parseHelper.parse("""
				var r: ~Object with {a: string; b: string;};
				var s: string;
				s = r.a;
				s = r.b;
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_03() throws Exception {
		Script script = parseHelper.parse("""
				export var r: ~Object with {a: string; b: string;};
				var s: string;
				s = r.a;
				s = r.b;
				""");
		valTestHelper.assertNoErrors(script);
	}

	@Test
	public void test_04() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		parseHelper.parse("""
					export var r: ~Object with {a: string; b: string;};
				""", URI.createURI("A.n4js"), rs);
		Script script = parseHelper.parse("""
					import {r} from "A";
					var s: string;
					s = r.a;
					s = r.b;
				""", URI.createURI("B.n4js"), rs);
		valTestHelper.assertNoErrors(script);
	}

}
