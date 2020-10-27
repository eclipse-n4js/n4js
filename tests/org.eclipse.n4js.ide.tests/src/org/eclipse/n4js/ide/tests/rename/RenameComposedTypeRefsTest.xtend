/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.rename

import org.eclipse.n4js.ide.tests.helper.server.AbstractRenameTest
import org.junit.Test

/**
 * Tests to ensure that various kinds of AST nodes can be renamed.
 */
class RenameComposedTypeRefsTest extends AbstractRenameTest {

	@Test
	def void testUnion_atRef() {
		testAtCursors('''
			class Cls1 { field; }
			class Cls2 { field; }

			let cls: Cls1 | Cls2;
			cls.<|>fi<|>eld<|>;

			let cls1: Cls1;
			cls1.field;
			let cls2: Cls2;
			cls2.field;
		''', "fieldNew", '''
			class Cls1 { fieldNew; }
			class Cls2 { fieldNew; }

			let cls: Cls1 | Cls2;
			cls.fieldNew;

			let cls1: Cls1;
			cls1.fieldNew;
			let cls2: Cls2;
			cls2.fieldNew;
		''');
	}

	/** Not perfectly supported yet. Probably rename should be rejected in this case. */
	@Test
	def void testUnion_atDecl() {
		testAtCursors('''
			class Cls1 { <|>fi<|>eld<|>; }
			class Cls2 { field; }

			let cls: Cls1 | Cls2;
			cls.field;

			let cls1: Cls1;
			cls1.field;
			let cls2: Cls2;
			cls2.field;
		''', "fieldNew", '''
			class Cls1 { fieldNew; }
			class Cls2 { field; }

			let cls: Cls1 | Cls2;
			cls.fieldNew;«/* error: Member fieldNew not present in all types of union; missing from: Cls2. */»

			let cls1: Cls1;
			cls1.fieldNew;
			let cls2: Cls2;
			cls2.field;
		''');
	}

	@Test
	def void testIntersection_atRef() {
		testAtCursors('''
			interface Cls1 { field; }
			interface Cls2 { field; }

			let cls: Cls1 & Cls2;
			cls.<|>fi<|>eld<|>;

			let cls1: Cls1;
			cls1.field;
			let cls2: Cls2;
			cls2.field;
		''', "fieldNew", '''
			interface Cls1 { fieldNew; }
			interface Cls2 { fieldNew; }

			let cls: Cls1 & Cls2;
			cls.fieldNew;

			let cls1: Cls1;
			cls1.fieldNew;
			let cls2: Cls2;
			cls2.fieldNew;
		''');
	}

	/** Not perfectly supported yet. Probably rename should be rejected in this case. */
	@Test
	def void testIntersection_atDecl() {
		testAtCursors('''
			interface Cls1 { <|>fi<|>eld<|>; }
			interface Cls2 { field; }

			let cls: Cls1 & Cls2;
			cls.field;

			let cls1: Cls1;
			cls1.field;
			let cls2: Cls2;
			cls2.field;
		''', "fieldNew", '''
			interface Cls1 { fieldNew; }
			interface Cls2 { field; }

			let cls: Cls1 & Cls2;
			cls.fieldNew;«/* no error, but code behavior changes! */»

			let cls1: Cls1;
			cls1.fieldNew;
			let cls2: Cls2;
			cls2.field;
		''');
	}
}
