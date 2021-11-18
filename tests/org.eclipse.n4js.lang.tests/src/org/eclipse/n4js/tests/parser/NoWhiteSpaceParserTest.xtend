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
package org.eclipse.n4js.tests.parser

import java.math.BigDecimal
import org.eclipse.n4js.n4JS.LiteralAnnotationArgument
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.NumericLiteral
import org.eclipse.n4js.n4JS.StringLiteral
import org.junit.Test

class NoWhiteSpaceParserTest extends AbstractParserTest {

	// White-space between the an annotation's name and its arguments list is not allowed:

	@Test
	def void testAnnotation01() {
		val script = '''
			@Annotation
			class Cls {}
		'''.parseN4jsSuccessfully;
		val ann = script.eAllContents.filter(N4ClassifierDeclaration).head?.annotations?.head;
		assertNotNull(ann);
		assertEquals("Annotation", ann.name);
		assertEquals(0, ann.args.size);
	}

	@Test
	def void testAnnotation02() {
		val script = '''
			@Annotation()
			class Cls {}
		'''.parseN4jsSuccessfully;
		val ann = script.eAllContents.filter(N4ClassifierDeclaration).head?.annotations?.head;
		assertNotNull(ann);
		assertEquals("Annotation", ann.name);
		assertEquals(0, ann.args.size);
	}

	@Test
	def void testAnnotation03() {
		val script = '''
			@Annotation( /* white-space is legal at this location */ )
			class Cls {}
		'''.parseN4jsSuccessfully;
		val ann = script.eAllContents.filter(N4ClassifierDeclaration).head?.annotations?.head;
		assertNotNull(ann);
		assertEquals("Annotation", ann.name);
		assertEquals(0, ann.args.size);
	}

	@Test
	def void testAnnotation04() {
		val script = '''
			@Annotation("")
			class Cls {}
		'''.parseN4jsSuccessfully;
		val ann = script.eAllContents.filter(N4ClassifierDeclaration).head?.annotations?.head;
		assertNotNull(ann);
		assertEquals("Annotation", ann.name);
		assertEquals(1, ann.args.size);
		assertTrue(ann.args.head instanceof LiteralAnnotationArgument);
	}

	@Test
	def void testAnnotation05() {
		val script = '''
			@Annotation( // white space
				"arg1"  ,
				"arg2"  ,
				42 /*  */ )
			class Cls {}
		'''.parseN4jsSuccessfully;
		val ann = script.eAllContents.filter(N4ClassifierDeclaration).head?.annotations?.head;
		assertNotNull(ann);
		assertEquals("Annotation", ann.name);
		assertEquals(3, ann.args.size);
		assertEquals("arg1",                 ((ann.args.get(0) as LiteralAnnotationArgument).literal as StringLiteral).value);
		assertEquals("arg2",                 ((ann.args.get(1) as LiteralAnnotationArgument).literal as StringLiteral).value);
		assertEquals(BigDecimal.valueOf(42), ((ann.args.get(2) as LiteralAnnotationArgument).literal as NumericLiteral).value);
	}

	@Test
	def void testAnnotation06_bad() {
		'''
			@Annotation ()
			class Cls {}
		'''.parseN4jsWithError;
	}

	// In contrast to the above, white-space between the @ and the annotation's name is allowed:

	@Test
	def void testAnnotation07() {
		val script = '''
			@  Annotation
			class Cls {}
		'''.parseN4jsSuccessfully;
		val ann = script.eAllContents.filter(N4ClassifierDeclaration).head?.annotations?.head;
		assertNotNull(ann);
		assertEquals("Annotation", ann.name);
		assertEquals(0, ann.args.size);
	}

	// Disallowing white-space between an annotation's name and its argument list solves some
	// ambiguities of annotated call signatures:

	@Test
	def void testCallSignatureWithAnnotation01() {
		val script = '''
			interface Ifc {
				@Override
				(p: string): number;
			}
		'''.parseN4jsSuccessfully;
		val decl = script.scriptElements.head as N4ClassifierDeclaration;
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	def void testCallSignatureWithAnnotation02() {
		val script = '''
			interface Ifc {
				@Override (p: string): number;
			}
		'''.parseN4jsSuccessfully;
		val decl = script.scriptElements.head as N4ClassifierDeclaration;
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	def void testCallSignatureWithAnnotation03() {
		val script = '''
			interface Ifc {
				@Override/**/(p: string): number;
			}
		'''.parseN4jsSuccessfully;
		val decl = script.scriptElements.head as N4ClassifierDeclaration;
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	def void testCallSignatureWithAnnotation04() {
		val script = '''
			interface Ifc {
				@Override()(p: string): number;
			}
		'''.parseN4jsSuccessfully;
		val decl = script.scriptElements.head as N4ClassifierDeclaration;
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	def void testCallSignatureWithAnnotation05_bad() {
		'''
			interface Ifc {
				@Override(p: string): number;
			}
		'''.parseN4jsWithError;
	}

	def private void assertCallSignatureWithAnnotation(N4ClassifierDeclaration decl) {
		val members = decl.ownedMembersRaw;
		assertEquals(1, members.size);
		val m = members.head;
		assertTrue(m.isCallSignature);
		val annotations = m.annotations;
		assertEquals(1, annotations.size);
		val ann = annotations.head;
		assertEquals("Override", ann.name);
	}
}
