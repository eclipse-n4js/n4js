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
package org.eclipse.n4js.tests.types.utils;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.junit.Assert.assertEquals;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.types.utils.TypeHelper;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for {@link TypeHelper}
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class TypeHelperTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	TypeHelper tsh;

	String asString(Iterable<TypeRef> typeRefs) {
		return Strings.join(",", map(typeRefs, tr -> tr.getTypeRefAsString()));
	}

	@Test
	public void testCollectAllSuperTypesSimpleClasses() throws Exception {
		Script script = parseHelper.parse("""
				public class A{}
				public class B extends A {}
				public class C extends B {}
				public class D extends C {}

				""");

		TypeRef A = TypeUtils.createTypeRef(script.getModule().getTypes().get(0));
		TypeRef B = TypeUtils.createTypeRef(script.getModule().getTypes().get(1));
		TypeRef C = TypeUtils.createTypeRef(script.getModule().getTypes().get(2));
		TypeRef D = TypeUtils.createTypeRef(script.getModule().getTypes().get(3));

		assertEquals("", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(A, false)));
		assertEquals("A", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(B, false)));
		assertEquals("B,A", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(C, false)));
		assertEquals("C,B,A", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(D, false)));
	}

	@Test
	public void testCollectAllSuperTypesSimpleClassesReflexive() throws Exception {
		Script script = parseHelper.parse("""
				public class A{}
				public class B extends A {}
				public class C extends B {}
				public class D extends C {}

				""");

		TypeRef A = TypeUtils.createTypeRef(script.getModule().getTypes().get(0));
		TypeRef B = TypeUtils.createTypeRef(script.getModule().getTypes().get(1));
		TypeRef C = TypeUtils.createTypeRef(script.getModule().getTypes().get(2));
		TypeRef D = TypeUtils.createTypeRef(script.getModule().getTypes().get(3));

		assertEquals("A", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(A, true)));
		assertEquals("B,A", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(B, true)));
		assertEquals("C,B,A", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(C, true)));
		assertEquals("D,C,B,A", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(D, true)));
	}

	@Test
	public void testCollectAllSuperTypesRecursiveClasses() throws Exception {
		Script script = parseHelper.parse("""
				public class A extends D {}
				public class B extends A {}
				public class C extends B {}
				public class D extends C {}

				""");

		TypeRef A = TypeUtils.createTypeRef(script.getModule().getTypes().get(0));
		TypeRef B = TypeUtils.createTypeRef(script.getModule().getTypes().get(1));
		TypeRef C = TypeUtils.createTypeRef(script.getModule().getTypes().get(2));
		TypeRef D = TypeUtils.createTypeRef(script.getModule().getTypes().get(3));

		assertEquals("D,C,B", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(A, false)));
		assertEquals("A,D,C", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(B, false)));
		assertEquals("B,A,D", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(C, false)));
		assertEquals("C,B,A", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(D, false)));
	}

	@Test
	public void testCollectAllSuperTypesXtended() throws Exception {
		Script script = parseHelper.parse("""
				public class A implements I {}
				public class B extends A implements L {}
				public class C extends B implements R {}
				public class D extends C implements T {}
				public interface R {}
				public interface S extends R{}
				public interface T extends S, I,K{}
				public interface I{}
				public interface K{}
				public interface L extends I,K

				""");

		TypeRef A = TypeUtils.createTypeRef(script.getModule().getTypes().get(0));
		TypeRef B = TypeUtils.createTypeRef(script.getModule().getTypes().get(1));
		TypeRef C = TypeUtils.createTypeRef(script.getModule().getTypes().get(2));
		TypeRef D = TypeUtils.createTypeRef(script.getModule().getTypes().get(3));

		assertEquals("I", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(A, false)));
		assertEquals("A,I,L,K", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(B, false)));
		assertEquals("B,A,I,L,K,R", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(C, false)));
		assertEquals("C,B,A,I,L,K,R,T,S", asString(tsh.collectAllDeclaredSuperTypesTypeargsIgnored(D, false)));
	}
}
