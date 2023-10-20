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
package org.eclipse.n4js.tests.parser;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class N4_SuperLiteralParsingTest extends AbstractParserTest {

	@Test
	public void testSuperLiteralInScript_01() {
		Script script = parseESWithError("""
					super;
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInScript_02() {
		Script script = parseESWithError("""
					super();
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInScript_03() {
		Script script = parseESWithError("""
					super.m();
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInBlock_01() {
		Script script = parseESWithError("""
					{ super; }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInBlock_02() {
		Script script = parseESWithError("""
					{ super(); }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInBlock_03() {
		Script script = parseESWithError("""
					{ super.m(); }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInObjectLiteral_01() {
		Script script = parseESWithError("""
					{ a: super; }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInObjectLiteral_02() {
		Script script = parseESWithError("""
					{ a: super(); }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInObjectLiteral_03() {
		Script script = parseESWithError("""
					{ a: super.m(); }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInFunction_01() {
		Script script = parseESWithError("""
					(function() { super; })
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInFunction_02() {
		Script script = parseESWithError("""
					(function() { super(); })
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInFunction_03() {
		Script script = parseESWithError("""
					(function() { super.m(); })
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInMethod_01() {
		Script script = parseESWithError("""
					class C { m() { super; } }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
		String msg = errors.get(0).getMessage();
		// TODO wording could be better
		assertEquals("Super member access requires a declared super type.", msg);
	}

	@Test
	public void testSuperLiteralInMethod_02() {
		Script script = parseESWithError("""
					class C { m() { super(); } }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInMethod_03() {
		parseESSuccessfully("""
					class C { m() { super.a(); } }
				""");
	}

	@Test
	public void testSuperLiteralInConstructor_01() {
		Script script = parseESWithError("""
					class C { constructor() { super; } }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
		String msg = errors.get(0).getMessage();
		// TODO wording could be better
		assertEquals("Super member access requires a declared super type.", msg);
	}

	@Test
	public void testSuperLiteralInConstructor_02() {
		Script script = parseESWithError("""
					class C { constructor() { super(); } }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
		String msg = errors.get(0).getMessage();
		// TODO wording could be better
		assertEquals("Super calls may only be used in constructors.", msg);
	}

	@Test
	public void testSuperLiteralInConstructor_03() {
		parseESSuccessfully("""
					class C { constructor() { super.a(); } }
				""");
	}

	@Test
	public void testSuperLiteralInInterface_01() {
		Script script = parseESWithError("""
					interface C { m() { super; } }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInInterface_02() {
		Script script = parseESWithError("""
					interface C { m() { super(); } }
				""");

		List<Diagnostic> errors = script.eResource().getErrors();
		assertEquals(errors.toString(), 1, errors.size());
	}

	@Test
	public void testSuperLiteralInInterface_03() {
		parseESSuccessfully("""
					interface C { m() { super.a(); } }
				""");
	}
}
