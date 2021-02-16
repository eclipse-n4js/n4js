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

import java.util.List
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TypingStrategy

/**
 * Base class for structural typing test, providing some assert methods.
 */
public abstract class AbstractStructuralTypingTest extends AbstractParserTest {
	def assertField(String expectedType, String expectedName, TMember member) {
		val field = assertType(TField, member);
		assertEquals(expectedType, field.typeRef?.typeRefAsString)
		assertEquals(expectedName, field.name);
	}

	def assertMethod(String expectedType, List<String> expectedFPars, String expectedName, TMember member) {
		val method = assertType(TMethod, member);
		assertEquals(expectedType, method.returnTypeRef?.typeRefAsString);
		assertEquals(expectedName, method.name);
		var expected = '''«FOR p : expectedFPars SEPARATOR ","»«p»«ENDFOR»''';
		var actual = '''«FOR m : method.fpars SEPARATOR ","»«m.typeRef.typeRefAsString»«ENDFOR»''';
		assertEquals(expected, actual);
	}

	def assertGetter(String expectedType, String expectedName, TMember member) {
		val getter = assertType(TGetter, member);
		assertEquals(expectedType, getter.typeRef?.typeRefAsString)
		assertEquals(expectedName, getter.name);
	}

	def assertSetter(String expectedType, String expectedName, TMember member) {
		val setter = assertType(TSetter, member);
		assertEquals(expectedType, setter.typeRef?.typeRefAsString)
		assertEquals(expectedName, setter.name);
	}

	def assertAdditionalFieldsPTR(TypingStrategy expectedStrategy, List<Pair<String, String>> pairs, TypeRef ref) {
		assertType(ParameterizedTypeRefStructural, ref)
		val ptrs = ref as ParameterizedTypeRefStructural
		assertEquals("Expected " + expectedStrategy.getName + " but was " + ptrs.typingStrategy?.getName + ": ",
			expectedStrategy, ptrs.typingStrategy);

		var expected = '''«FOR p : pairs SEPARATOR ","»«p.value» «p.key»«ENDFOR»''';
		var actual = '''«FOR m : ptrs.structuralMembers.filter(TField) SEPARATOR ","»«m.typeRef?.typeRefAsString» «m.
			name»«ENDFOR»''';
		assertEquals(expected, actual);
	}

	def assertAdditionalFieldsThis(TypingStrategy expectedStrategy, List<Pair<String, String>> pairs, TypeRef ref) {
		assertType(ThisTypeRefStructural, ref)
		val ptrs = ref as ThisTypeRefStructural
		assertEquals("Expected " + expectedStrategy.getName + " but was " + ptrs.typingStrategy?.getName + ": ",
			expectedStrategy, ptrs.typingStrategy);

		var expected = '''«FOR p : pairs SEPARATOR ","»«p.value» «p.key»«ENDFOR»''';
		var actual = '''«FOR m : ptrs.structuralMembers.filter(TField) SEPARATOR ","»«m.typeRef?.typeRefAsString» «m.
			name»«ENDFOR»''';
		assertEquals(expected, actual);
	}

	def <T> T assertType(Class<T> type, Object obj) {
		assertTrue("Expected type " + type.simpleName + " but got " + obj.class.simpleName, type.isInstance(obj));
		return obj as T
	}
}
