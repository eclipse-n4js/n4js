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
import org.eclipse.n4js.analysis.ExceptionAnalyser;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.tests.parser.AbstractParserTest;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Extracted cases from smoke tests (see Smoketests.xtend)
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ErrorTest extends AbstractParserTest {

	@Inject
	ExceptionAnalyser analyser;

	@Inject
	protected Provider<XtextResourceSet> resourceSetProvider;

	@Test
	public void testNoStackoverflow_01() throws Exception {
		Script script = parseHelper.parse("""
				var a = a || {};
				a.b
				""", URI.createURI("sample.n4js"), resourceSetProvider.get());
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoStackoverflow_02() throws Exception {
		Script script = parseHelper.parse("""
				var u =
				u.either
				""", URI.createURI("sample.n4js"), resourceSetProvider.get());
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoNPE_01() throws Exception {
		Script script = parseHelper.parse("""
				class C<T> {
				    boolean b = null
				    x() {
					    spec
				    }
				    y(number? n) {
				    }
				}
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoNPE_02() throws Exception {
		Script script = parseHelper.parse("""
				class C<T> {
				    m(? p) {
				    }
				    n() {
				        this.m();
				    }
				}
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoNPE_03() throws Exception {
		Script script = parseHelper.parse("""
				var target = {
					s: "hello",
					set x
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoNPE_04() throws Exception {
		Script script = parseHelper.parse("""
				class C {}
				function f( ~C with {
					s: string;
					n;
					f1(string)
					void f2()
					number f3(boolean, C)
					C get y()
					get z()
					set a(
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoNPE_05() throws Exception {
		Script script = parseHelper.parse("""
				class C {}
				function f( ~C with {
					s: string;
					n;
					f1(string)
					void f2()
					number f3(boolean, C)
					C get y()
					get z()
					set a()
				} p) {}
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoNPE_06() throws Exception {
		Script script = parseHelper.parse("""
				mport * as 1 from 'a/X';
				var N1.x: X;""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoCyclicResolution_01() throws Exception {
		Script script = parseHelper.parse("""
				function(a){
					return a.b
				}
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoIllegalArgumentException_01() throws Exception {
		Script script = parseHelper.parse("""
				interface R1 {
					void f(p: number) {}
				}
				interface R2 {
					void f(p: string) {
				class C implements R1, R2 {}
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoIllegalArgumentException_02() throws Exception {
		Script script = parseHelper.parse("""
				interface R1 {
					x: string;
				}
				class S {
					void x() {
				class C1 extends S implements R1 {}
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoIllegalStateException_01() throws Exception {
		Script script = parseHelper.parse("""
				var fo = 5,/*
							*/
							class A { a = new A(); }
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoIllegalStateException_02() throws Exception {
		Script script = parseHelper.parse("""
				public class C {
					f0;
					private f1;
					project
					@Internal protected f4;
					protected f5;
					@Internal public f6;
					public f7;
				}
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoIllegalArgumentException_03() throws Exception {
		Script script = parseHelper.parse("""
				import * as N2 from "a/X";
				import * as 1 from "a/X";
				var N1.X x;
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoIllegalArgumentException_04() throws Exception {
		Script script = parseHelper.parse("""
				import X1 from "a/X";
				import X2 from "a/X";
				var X1 x = X2;
				""");
		analyser.analyse(script, "script", "script");
	}

	@Test
	public void testNoIllegalStateException_03() throws Exception {
		Script script = parseHelper.parse("""
				var ol = {
					et target() {return null}
				}
				ol.target=null;
				""");
		analyser.analyse(script, "script", "script");
	}

}
