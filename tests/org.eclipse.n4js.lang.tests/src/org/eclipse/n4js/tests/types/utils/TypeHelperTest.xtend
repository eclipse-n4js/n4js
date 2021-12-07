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
package org.eclipse.n4js.tests.types.utils

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.types.utils.TypeHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.types.utils.TypeUtils.*

/*
 * Tests for {@link TypeHelper}
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class TypeHelperTest  {

	@Inject
	extension ParseHelper<Script>

	@Inject
	TypeHelper tsh;

	def String asString(Iterable<TypeRef> typeRefs) {
		return typeRefs.map[getTypeRefAsString].join(",")
	}

	@Test
	def void testCollectAllSuperTypesSimpleClasses() {
		val script = '''
			public class A{}
			public class B extends A {}
			public class C extends B {}
			public class D extends C {}

		'''.parse()

		var A = script.module.types.get(0).createTypeRef()
		var B = script.module.types.get(1).createTypeRef()
		var C = script.module.types.get(2).createTypeRef();
		var D = script.module.types.get(3).createTypeRef();

		assertEquals("", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(A, false).asString());
		assertEquals("A", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(B, false).asString());
		assertEquals("B,A", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(C, false).asString());
		assertEquals("C,B,A", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(D, false).asString());
	}

	@Test
	def void testCollectAllSuperTypesSimpleClassesReflexive() {
		val script = '''
			public class A{}
			public class B extends A {}
			public class C extends B {}
			public class D extends C {}

		'''.parse()

		var A = script.module.types.get(0).createTypeRef();
		var B = script.module.types.get(1).createTypeRef();
		var C = script.module.types.get(2).createTypeRef();
		var D = script.module.types.get(3).createTypeRef();

		assertEquals("A", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(A, true).asString());
		assertEquals("B,A", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(B, true).asString());
		assertEquals("C,B,A", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(C, true).asString());
		assertEquals("D,C,B,A", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(D, true).asString());
	}

	@Test
	def void testCollectAllSuperTypesRecursiveClasses() {
		val script = '''
			public class A extends D {}
			public class B extends A {}
			public class C extends B {}
			public class D extends C {}

		'''.parse()

		var A = script.module.types.get(0).createTypeRef();
		var B = script.module.types.get(1).createTypeRef();
		var C = script.module.types.get(2).createTypeRef();
		var D = script.module.types.get(3).createTypeRef();

		assertEquals("D,C,B", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(A, false).asString());
		assertEquals("A,D,C", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(B, false).asString());
		assertEquals("B,A,D", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(C, false).asString());
		assertEquals("C,B,A", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(D, false).asString());
	}

	@Test
	def void testCollectAllSuperTypesXtended() {
		val script = '''
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

		'''.parse()

		var A = script.module.types.get(0).createTypeRef();
		var B = script.module.types.get(1).createTypeRef();
		var C = script.module.types.get(2).createTypeRef();
		var D = script.module.types.get(3).createTypeRef();

		assertEquals("I", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(A, false).asString());
		assertEquals("A,I,L,K", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(B, false).asString());
		assertEquals("B,A,I,L,K,R", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(C, false).asString());
		assertEquals("C,B,A,I,L,K,R,T,S", tsh.collectAllDeclaredSuperTypesTypeargsIgnored(D, false).asString());
	}
}
