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

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;

import java.math.BigDecimal;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.LiteralAnnotationArgument;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.junit.Test;

public class NoWhiteSpaceParserTest extends AbstractParserTest {

	// White-space between the an annotation's name and its arguments list is not allowed:

	@Test
	public void testAnnotation01() {
		Script script = parseN4jsSuccessfully("""
					@Annotation
					class Cls {}
				""");
		Annotation ann = head(filter(script.eAllContents(), N4ClassifierDeclaration.class)).getAnnotations().get(0);
		assertNotNull(ann);
		assertEquals("Annotation", ann.getName());
		assertEquals(0, ann.getArgs().size());
	}

	@Test
	public void testAnnotation02() {
		Script script = parseN4jsSuccessfully("""
					@Annotation()
					class Cls {}
				""");
		Annotation ann = head(filter(script.eAllContents(), N4ClassifierDeclaration.class)).getAnnotations().get(0);
		assertNotNull(ann);
		assertEquals("Annotation", ann.getName());
		assertEquals(0, ann.getArgs().size());
	}

	@Test
	public void testAnnotation03() {
		Script script = parseN4jsSuccessfully("""
					@Annotation( /* white-space is legal at this location */ )
					class Cls {}
				""");
		Annotation ann = head(filter(script.eAllContents(), N4ClassifierDeclaration.class)).getAnnotations().get(0);
		assertNotNull(ann);
		assertEquals("Annotation", ann.getName());
		assertEquals(0, ann.getArgs().size());
	}

	@Test
	public void testAnnotation04() {
		Script script = parseN4jsSuccessfully("""
					@Annotation("")
					class Cls {}
				""");
		Annotation ann = head(filter(script.eAllContents(), N4ClassifierDeclaration.class)).getAnnotations().get(0);
		assertNotNull(ann);
		assertEquals("Annotation", ann.getName());
		assertEquals(1, ann.getArgs().size());
		assertTrue(ann.getArgs().get(0) instanceof LiteralAnnotationArgument);
	}

	@Test
	public void testAnnotation05() {
		Script script = parseN4jsSuccessfully("""
					@Annotation( // white space
						"arg1"  ,
						"arg2"  ,
						42 /*  */ )
					class Cls {}
				""");
		Annotation ann = head(filter(script.eAllContents(), N4ClassifierDeclaration.class)).getAnnotations().get(0);
		assertNotNull(ann);
		assertEquals("Annotation", ann.getName());
		assertEquals(3, ann.getArgs().size());
		assertEquals("arg1",
				((StringLiteral) ((LiteralAnnotationArgument) ann.getArgs().get(0)).getLiteral()).getValue());
		assertEquals("arg2",
				((StringLiteral) ((LiteralAnnotationArgument) ann.getArgs().get(1)).getLiteral()).getValue());
		assertEquals(BigDecimal.valueOf(42),
				((NumericLiteral) ((LiteralAnnotationArgument) ann.getArgs().get(2)).getLiteral()).getValue());
	}

	@Test
	public void testAnnotation06_bad() throws Exception {
		parseN4jsWithError("""
					@Annotation ()
					class Cls {}
				""");
	}

	// In contrast to the above, white-space between the @ and the annotation's name is allowed:

	@Test
	public void testAnnotation07() {
		Script script = parseN4jsSuccessfully("""
					@  Annotation
					class Cls {}
				""");
		Annotation ann = head(filter(script.eAllContents(), N4ClassifierDeclaration.class)).getAnnotations().get(0);
		assertNotNull(ann);
		assertEquals("Annotation", ann.getName());
		assertEquals(0, ann.getArgs().size());
	}

	// Disallowing white-space between an annotation's name and its argument list solves some
	// ambiguities of annotated call signatures:

	@Test
	public void testCallSignatureWithAnnotation01() {
		Script script = parseN4jsSuccessfully("""
					interface Ifc {
						@Override
						(p: string): number;
					}
				""");
		N4ClassifierDeclaration decl = (N4ClassifierDeclaration) script.getScriptElements().get(0);
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	public void testCallSignatureWithAnnotation02() {
		Script script = parseN4jsSuccessfully("""
					interface Ifc {
						@Override (p: string): number;
					}
				""");
		N4ClassifierDeclaration decl = (N4ClassifierDeclaration) script.getScriptElements().get(0);
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	public void testCallSignatureWithAnnotation03() {
		Script script = parseN4jsSuccessfully("""
					interface Ifc {
						@Override/**/(p: string): number;
					}
				""");
		N4ClassifierDeclaration decl = (N4ClassifierDeclaration) script.getScriptElements().get(0);
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	public void testCallSignatureWithAnnotation04() {
		Script script = parseN4jsSuccessfully("""
					interface Ifc {
						@Override()(p: string): number;
					}
				""");
		N4ClassifierDeclaration decl = (N4ClassifierDeclaration) script.getScriptElements().get(0);
		assertCallSignatureWithAnnotation(decl);
	}

	@Test
	public void testCallSignatureWithAnnotation05_bad() throws Exception {
		parseN4jsWithError("""
					interface Ifc {
						@Override(p: string): number;
					}
				""");
	}

	private void assertCallSignatureWithAnnotation(N4ClassifierDeclaration decl) {
		EList<N4MemberDeclaration> members = decl.getOwnedMembersRaw();
		assertEquals(1, members.size());
		N4MemberDeclaration m = members.get(0);
		assertTrue(m.isCallSignature());
		EList<Annotation> annotations = m.getAnnotations();
		assertEquals(1, annotations.size());
		Annotation ann = annotations.get(0);
		assertEquals("Override", ann.getName());
	}
}
