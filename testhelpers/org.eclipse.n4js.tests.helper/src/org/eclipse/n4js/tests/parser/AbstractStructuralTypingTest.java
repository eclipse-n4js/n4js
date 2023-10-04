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

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Base class for structural typing test, providing some assert methods.
 */
public abstract class AbstractStructuralTypingTest extends AbstractParserTest {

	
	public void assertField(String expectedType, String expectedName, TMember member) {
		TField field = assertType(TField.class, member);
		assertEquals(expectedType, field.getTypeRef() == null ? null : field.getTypeRef().getTypeRefAsString());
		assertEquals(expectedName, field.getName());
	}

	
	public void assertMethod(String expectedType, List<String> expectedFPars, String expectedName, TMember member) {
		TMethod method = assertType(TMethod.class, member);
		TypeRef returnTypeRef = method.getReturnTypeRef();
		assertEquals(expectedType, returnTypeRef == null ? null : returnTypeRef.getTypeRefAsString());
		assertEquals(expectedName, method.getName());
		String expected = Strings.join(",", expectedFPars);
		String actual = Strings.join(",", fpar -> fpar.getTypeRef().getTypeRefAsString(), method.getFpars());
		assertEquals(expected, actual);
	}

	
	public void assertGetter(String expectedType, String expectedName, TMember member) {
		TGetter getter = assertType(TGetter.class, member);
		assertEquals(expectedType, getter.getTypeRef() == null ? null : getter.getTypeRef().getTypeRefAsString());
		assertEquals(expectedName, getter.getName());
	}

	
	public void assertSetter(String expectedType, String expectedName, TMember member) {
		TSetter setter = assertType(TSetter.class, member);
		assertEquals(expectedType, setter.getTypeRef() == null ? null : setter.getTypeRef().getTypeRefAsString());
		assertEquals(expectedName, setter.getName());
	}

	
	public void assertAdditionalFieldsPTR(TypingStrategy expectedStrategy, List<Pair<String, String>> pairs,
			TypeRef ref) {
		ParameterizedTypeRefStructural ptrs = assertType(ParameterizedTypeRefStructural.class, ref);
		assertEquals("Expected " + expectedStrategy.getName() + " but was " + ptrs.getTypingStrategy().getName() + ": ",
				expectedStrategy, ptrs.getTypingStrategy());

		String expected = Strings.join(",", p -> p.getValue() + " " + p.getKey(), pairs);
		String actual = Strings.join(",", m -> m.getTypeRef().getTypeRefAsString() + " " + m.getName(),
				IterableExtensions.filter(ptrs.getStructuralMembers(), TField.class));
		assertEquals(expected, actual);
	}

	
	public void assertAdditionalFieldsThis(TypingStrategy expectedStrategy, List<Pair<String, String>> pairs,
			TypeRef ref) {
		ThisTypeRefStructural ptrs = assertType(ThisTypeRefStructural.class, ref);
		assertEquals("Expected " + expectedStrategy.getName() + " but was " + ptrs.getTypingStrategy().getName() + ": ",
				expectedStrategy, ptrs.getTypingStrategy());

		String expected = Strings.join(",", p -> p.getValue() + " " + p.getKey(), pairs);
		String actual = Strings.join(",", m -> m.getTypeRef().getTypeRefAsString() + " " + m.getName(),
				IterableExtensions.filter(ptrs.getStructuralMembers(), TField.class));
		assertEquals(expected, actual);
	}

	
	@SuppressWarnings("unchecked")
	<T> T assertType(Class<T> type, Object obj) {
		assertTrue("Expected type " + type.getSimpleName() + " but got " + obj.getClass().getSimpleName(),
				type.isInstance(obj));
		return (T) obj;
	}
}
