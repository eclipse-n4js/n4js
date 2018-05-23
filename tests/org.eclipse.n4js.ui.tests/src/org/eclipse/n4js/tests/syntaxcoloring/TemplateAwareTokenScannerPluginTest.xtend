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
package org.eclipse.n4js.tests.syntaxcoloring

import com.google.inject.Inject
import org.eclipse.n4js.N4JSUiInjectorProvider
import org.eclipse.jface.text.rules.ITokenScanner
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert
import org.eclipse.jface.text.rules.Token

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSUiInjectorProvider)
class TemplateAwareTokenScannerPluginTest extends Assert {
	@Inject XtextDocument document
	@Inject ITokenScanner scanner

	def void tokenize(String input) {
		document.set(input)
		scanner.setRange(document, 0, input.length)
	}

	@Test
	def void testNoSubstLiteral() {
		tokenize("`text`")
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals(0, scanner.tokenOffset)
		assertEquals("`text`".length, scanner.tokenLength)
		assertSame(Token.EOF, scanner.nextToken)
	}

	@Test
	def void testTemplateStart() {
		tokenize("`text${")
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals(0, scanner.tokenOffset)
		assertEquals("`text".length, scanner.tokenLength)
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals("`text".length, scanner.tokenOffset)
		assertEquals("${".length, scanner.tokenLength)
		assertSame(Token.EOF, scanner.nextToken)
	}

	@Test
	def void testTemplateEmptySubst() {
		tokenize("`${}text`")
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals(0, scanner.tokenOffset)
		assertEquals(1, scanner.tokenLength)
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals(1, scanner.tokenOffset)
		assertEquals("${".length, scanner.tokenLength)
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals(3, scanner.tokenOffset)
		assertEquals("}".length, scanner.tokenLength)
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals(4, scanner.tokenOffset)
		assertEquals("text`".length, scanner.tokenLength)
		assertSame(Token.EOF, scanner.nextToken)
	}

	@Test
	def void testTemplateSubst() {
		tokenize("`${a}text`")
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals(0, scanner.tokenOffset)
		assertEquals(1, scanner.tokenLength)
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals(1, scanner.tokenOffset)
		assertEquals("${".length, scanner.tokenLength)
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals(3, scanner.tokenOffset)
		assertEquals("a".length, scanner.tokenLength)
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals(4, scanner.tokenOffset)
		assertEquals("}".length, scanner.tokenLength)
		assertNotSame(Token.EOF, scanner.nextToken)
		assertEquals(5, scanner.tokenOffset)
		assertEquals("text`".length, scanner.tokenLength)
		assertSame(Token.EOF, scanner.nextToken)
	}

	@Test
	def void testTemplateSubstSomethingSubst() {
		tokenize("`aaa${0}bbb${0}ccc`");
		var currOffset = 0;
		currOffset = assertNextToken(currOffset, "`aaa".length);
		currOffset = assertNextToken(currOffset, "${".length);
		currOffset = assertNextToken(currOffset, "0".length);
		currOffset = assertNextToken(currOffset, "}".length);
		currOffset = assertNextToken(currOffset, "bbb".length);
		currOffset = assertNextToken(currOffset, "${".length);
		currOffset = assertNextToken(currOffset, "0".length);
		currOffset = assertNextToken(currOffset, "}".length);
		currOffset = assertNextToken(currOffset, "ccc`".length);
		assertNextTokenEOF();
	}

	@Test
	def void testTemplateSubstNothingSubst() {
		tokenize("`aaa${0}${0}ccc`");
		var currOffset = 0;
		currOffset = assertNextToken(currOffset, "`aaa".length);
		currOffset = assertNextToken(currOffset, "${".length);
		currOffset = assertNextToken(currOffset, "0".length);
		currOffset = assertNextToken(currOffset, "}".length);
		currOffset = assertNextToken(currOffset, "${".length);
		currOffset = assertNextToken(currOffset, "0".length);
		currOffset = assertNextToken(currOffset, "}".length);
		currOffset = assertNextToken(currOffset, "ccc`".length);
		assertNextTokenEOF();
	}

	/** Returns the next expected offset, i.e. {@code expectedOffset} + {@code expectedLength}. */
	def private int assertNextToken(int expectedOffset, int expectedLength) {
		assertNotSame(Token.EOF, scanner.nextToken);
		assertEquals(expectedOffset, scanner.tokenOffset);
		assertEquals(expectedLength, scanner.tokenLength);
		return expectedOffset + expectedLength;
	}

	def private void assertNextTokenEOF() {
		assertSame(Token.EOF, scanner.nextToken)
	}
}
