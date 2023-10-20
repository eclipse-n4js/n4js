/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.utils;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.AbstractN4JSTest;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.utils.StructuralMembersTriple;
import org.eclipse.n4js.utils.StructuralMembersTripleIterator;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for creating {@link StructuralMembersTriple}s from structural members.
 */
public class StructuralMembersTripleIteratorTest extends AbstractN4JSTest {

	private static TGetter L_getter;
	private static TSetter L_setter;
	private static TField L_field;
	private static TGetter R_getter;
	private static TSetter R_setter;
	private static TField R_field;

	private static boolean isMemberPreparationDone = false;

	@Before
	public void prepareMembers() {
		if (!isMemberPreparationDone) {
			try {
				Script script = testHelper.parseAndValidateSuccessfully("""
						class L {
							public get f(): string {return null;}
							public set f(value:string) {}
						}
						class LF {
							public f: string;
						}
						class R {
							public get f(): string {return null;}
							public set f(value:string) {}
						}
						class RF {
							public f: string;
						}
						""");

				Iterable<TClass> classes = filter(
						map(filter(script.eContents(), N4ClassDeclaration.class), cd -> cd.getDefinedType()),
						TClass.class);

				TClass L = findFirst(classes, c -> Objects.equals(c.getName(), "L"));
				TClass LF = findFirst(classes, c -> Objects.equals(c.getName(), "LF"));
				TClass R = findFirst(classes, c -> Objects.equals(c.getName(), "R"));
				TClass RF = findFirst(classes, c -> Objects.equals(c.getName(), "RF"));

				L_getter = head(filter(L.getOwnedMembers(), TGetter.class));
				L_setter = head(filter(L.getOwnedMembers(), TSetter.class));
				L_field = head(filter(LF.getOwnedMembers(), TField.class));
				R_getter = head(filter(R.getOwnedMembers(), TGetter.class));
				R_setter = head(filter(R.getOwnedMembers(), TSetter.class));
				R_field = head(filter(RF.getOwnedMembers(), TField.class));

				isMemberPreparationDone = true;

			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}

		assertNotNull(L_getter);
		assertNotNull(L_setter);
		assertNotNull(L_field);
		assertNotNull(R_getter);
		assertNotNull(R_setter);
		assertNotNull(R_field);
	}

	@Test
	public void testAccessors01() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_setter), List.of(R_setter),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_setter, R_setter), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors02() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_getter), List.of(R_getter),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_getter, R_getter), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors03a() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(), List.of(R_setter),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(null, R_setter), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors03b() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_getter), List.of(R_setter),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(null, R_setter), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors04a() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(), List.of(R_getter),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(null, R_getter), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors04b() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_setter), List.of(R_getter),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(null, R_getter), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors05a() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_setter),
				List.of(R_getter, R_setter),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(null, null, R_getter), iter.next());
		assertEquals(tripleOf(L_setter, null, R_setter), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors05b() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_getter),
				List.of(R_getter, R_setter),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_getter, null, R_getter), iter.next());
		assertEquals(tripleOf(null, null, R_setter), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors06a() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_getter, L_setter),
				List.of(R_field),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_getter, L_setter, R_field), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors06b() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_getter),
				List.of(R_field),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_getter, null, R_field), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors06c() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_setter),
				List.of(R_field),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_setter, null, R_field), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors07a() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_field),
				List.of(R_getter, R_setter),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_field, null, R_getter), iter.next());
		assertEquals(tripleOf(L_field, null, R_setter), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors07b() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_field),
				List.of(R_getter),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_field, null, R_getter), iter.next());
		assertFalse(iter.hasNext());
	}

	@Test
	public void testAccessors07c() {
		StructuralMembersTripleIterator iter = StructuralMembersTripleIterator.ofUnprepared(
				List.of(L_field),
				List.of(R_setter),
				TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_field, null, R_setter), iter.next());
		assertFalse(iter.hasNext());
	}

	private StructuralMembersTriple tripleOf(TMember left, TMember right) {
		return tripleOf(left, null, right);
	}

	private StructuralMembersTriple tripleOf(TMember left, FieldAccessor leftOtherAccessor, TMember right) {
		return new StructuralMembersTriple(left, right, leftOtherAccessor);
	}
}
