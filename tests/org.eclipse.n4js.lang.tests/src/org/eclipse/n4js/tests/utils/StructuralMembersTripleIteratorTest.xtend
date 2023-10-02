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
package org.eclipse.n4js.tests.utils

import org.eclipse.n4js.AbstractN4JSTest
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.ts.types.FieldAccessor
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.utils.StructuralMembersTriple
import org.eclipse.n4js.utils.StructuralMembersTripleIterator
import org.junit.Before
import org.junit.Test

/**
 * Tests for creating {@link StructuralMembersTriple}s from structural members.
 */
class StructuralMembersTripleIteratorTest extends AbstractN4JSTest {

	private static TGetter L_getter;
	private static TSetter L_setter;
	private static TField L_field;
	private static TGetter R_getter;
	private static TSetter R_setter;
	private static TField R_field;

	private static boolean isMemberPreparationDone = false;

	@Before
	def void prepareMembers() {
		if(!isMemberPreparationDone) {
			val script = testHelper.parseAndValidateSuccessfully('''
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
			''');

			val classes = script.eContents.filter(N4ClassDeclaration).map[definedType].filter(TClass);

			val L = classes.findFirst[name=='L'];
			val LF = classes.findFirst[name=='LF'];
			val R = classes.findFirst[name=='R'];
			val RF = classes.findFirst[name=='RF'];

			L_getter = L.ownedMembers.filter(TGetter).head;
			L_setter = L.ownedMembers.filter(TSetter).head;
			L_field = LF.ownedMembers.filter(TField).head;
			R_getter = R.ownedMembers.filter(TGetter).head;
			R_setter = R.ownedMembers.filter(TSetter).head;
			R_field = RF.ownedMembers.filter(TField).head;

			isMemberPreparationDone = true;
		}

		assertNotNull(L_getter);
		assertNotNull(L_setter);
		assertNotNull(L_field);
		assertNotNull(R_getter);
		assertNotNull(R_setter);
		assertNotNull(R_field);
	}

	@Test
	def void testAccessors01() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_setter ], #[ R_setter ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_setter,R_setter), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors02() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_getter ], #[ R_getter ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_getter,R_getter), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors03a() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ ], #[ R_setter ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(null,R_setter), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors03b() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_getter ], #[ R_setter ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(null,R_setter), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors04a() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ ], #[ R_getter ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(null,R_getter), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors04b() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_setter ], #[ R_getter ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(null,R_getter), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors05a() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_setter ],
			#[ R_getter, R_setter ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(null,null,R_getter), iter.next);
		assertEquals(tripleOf(L_setter,null,R_setter), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors05b() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_getter ],
			#[ R_getter, R_setter ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_getter,null,R_getter), iter.next);
		assertEquals(tripleOf(null,null,R_setter), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors06a() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_getter, L_setter ],
			#[ R_field ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_getter,L_setter,R_field), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors06b() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_getter ],
			#[ R_field ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_getter,null,R_field), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors06c() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_setter ],
			#[ R_field ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_setter,null,R_field), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors07a() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_field ],
			#[ R_getter, R_setter ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_field,null,R_getter), iter.next);
		assertEquals(tripleOf(L_field,null,R_setter), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors07b() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_field ],
			#[ R_getter ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_field,null,R_getter), iter.next);
		assertFalse(iter.hasNext);
	}

	@Test
	def void testAccessors07c() {
		val iter = StructuralMembersTripleIterator.ofUnprepared(
			#[ L_field ],
			#[ R_setter ],
			TypingStrategy.NOMINAL, TypingStrategy.STRUCTURAL_FIELDS);

		assertEquals(tripleOf(L_field,null,R_setter), iter.next);
		assertFalse(iter.hasNext);
	}


	def private StructuralMembersTriple tripleOf(TMember left, TMember right) {
		return tripleOf(left, null, right);
	}
	def private StructuralMembersTriple tripleOf(TMember left, FieldAccessor leftOtherAccessor, TMember right) {
		return new StructuralMembersTriple(left, right, leftOtherAccessor);
	}
}
