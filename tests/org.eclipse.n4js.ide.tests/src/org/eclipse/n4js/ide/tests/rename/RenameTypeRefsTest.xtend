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
 * Tests to ensure that various AST nodes can be renamed.
 */
class RenameTypeRefsTest extends AbstractRenameTest {

	@Test def void testParameterizedTypeRef_atDecl() {
		testAtCursors("class <|>Na<|>me<|> {} let x: Name;", "NameNew", "class NameNew {} let x: NameNew;");
	}

	@Test def void testParameterizedTypeRef_atRef() {
		testAtCursors("class Name {} let x: <|>Na<|>me<|>;", "NameNew", "class NameNew {} let x: NameNew;");
	}

	@Test def void testParameterizedTypeRef_withEnum_atDecl() {
		testAtCursors("enum <|>Na<|>me<|> { L } let x: Name;", "NameNew", "enum NameNew { L } let x: NameNew;");
	}

	@Test def void testParameterizedTypeRef_withEnum_atRef() {
		testAtCursors("enum Name { L } let x: <|>Na<|>me<|>;", "NameNew", "enum NameNew { L } let x: NameNew;");
	}

	@Test def void testClassifierTypeRef_atDecl() {
		testAtCursors("class <|>Na<|>me<|> {} let x: type{Name};", "NameNew", "class NameNew {} let x: type{NameNew};");
	}

	@Test def void testClassifierTypeRef_atRef() {
		testAtCursors("class Name {} let x: type{<|>Na<|>me<|>};", "NameNew", "class NameNew {} let x: type{NameNew};");
	}

	@Test def void testClassifierTypeRef_withWildcard_atDecl() {
		testAtCursors("class <|>Na<|>me<|> {} let x: type{? extends Name};", "NameNew", "class NameNew {} let x: type{? extends NameNew};");
	}

	@Test def void testClassifierTypeRef_withWildcard_atRef() {
		testAtCursors("class Name {} let x: type{? extends <|>Na<|>me<|>};", "NameNew", "class NameNew {} let x: type{? extends NameNew};");
	}

	@Test def void testTypeArgument_atDecl() {
		testAtCursors("class <|>Na<|>me<|> {} let x: Array<Name>;", "NameNew", "class NameNew {} let x: Array<NameNew>;");
	}

	@Test def void testTypeArgument_atRef() {
		testAtCursors("class Name {} let x: Array<<|>Na<|>me<|>>;", "NameNew", "class NameNew {} let x: Array<NameNew>;");
	}

	@Test def void testTypeArgument_withWildcard_atDecl() {
		testAtCursors("class <|>Na<|>me<|> {} let x: Array<? extends Name>;", "NameNew", "class NameNew {} let x: Array<? extends NameNew>;");
	}

	@Test def void testTypeArgument_withWildcard_atRef() {
		testAtCursors("class Name {} let x: Array<? extends <|>Na<|>me<|>>;", "NameNew", "class NameNew {} let x: Array<? extends NameNew>;");
	}

	@Test def void testTypeArgument_inGenericMethod_atDecl() {
		testAtCursors("class <|>Na<|>me<|> {} class Cls { <T extends Name> m(p: T) {} }", "NameNew", "class NameNew {} class Cls { <T extends NameNew> m(p: T) {} }");
	}

	@Test def void testTypeArgument_inGenericMethod_atRef() {
		testAtCursors("class Name {} class Cls { <T extends <|>Na<|>me<|>> m(p: T) {} }", "NameNew", "class NameNew {} class Cls { <T extends NameNew> m(p: T) {} }");
	}

	@Test def void testTypeParameter_atDecl() {
		testAtCursors("class Cls<<|>Na<|>me<|>> { field: Name; }", "NameNew", "class Cls<NameNew> { field: NameNew; }");
	}

	@Test def void testTypeParameter_atRef() {
		testAtCursors("class Cls<Name> { field: <|>Na<|>me<|>; }", "NameNew", "class Cls<NameNew> { field: NameNew; }");
	}
}
