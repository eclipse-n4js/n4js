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

import org.eclipse.n4js.ide.tests.server.AbstractRenameTest
import org.junit.Ignore
import org.junit.Test

/**
 * Tests to ensure that various AST nodes can be renamed.
 */
class RenameBasicTest extends AbstractRenameTest {

	@Test def void testVar_topLevel_atDecl() {
		testAtCursors("var <|>na<|>me<|> = ''; name;", "nameNew", "var nameNew = ''; nameNew;");
	}

	@Test def void testVar_topLevel_atRef() {
		testAtCursors("var name = ''; <|>na<|>me<|>;", "nameNew", "var nameNew = ''; nameNew;");
	}

	@Test def void testVar_exported_atDecl() {
		testAtCursors("export var <|>na<|>me<|> = ''; name;", "nameNew", "export var nameNew = ''; nameNew;");
	}

	@Test def void testVar_exported_atRef() {
		testAtCursors("export var name = ''; <|>na<|>me<|>;", "nameNew", "export var nameNew = ''; nameNew;");
	}

	@Test def void testVar_local_atDecl() {
		testAtCursors("function foo() { var <|>na<|>me<|> = ''; name; }", "nameNew", "function foo() { var nameNew = ''; nameNew; }");
	}

	@Test def void testVar_local_atRef() {
		testAtCursors("function foo() { var name = ''; <|>na<|>me<|>; }", "nameNew", "function foo() { var nameNew = ''; nameNew; }");
	}

	@Test def void testLet_topLevel_atDecl() {
		testAtCursors("let <|>na<|>me<|> = ''; name;", "nameNew", "let nameNew = ''; nameNew;");
	}

	@Test def void testLet_topLevel_atRef() {
		testAtCursors("let name = ''; <|>na<|>me<|>;", "nameNew", "let nameNew = ''; nameNew;");
	}

	@Test def void testLet_exported_atDecl() {
		testAtCursors("export let <|>na<|>me<|> = ''; name;", "nameNew", "export let nameNew = ''; nameNew;");
	}

	@Test def void testLet_exported_atRef() {
		testAtCursors("export let name = ''; <|>na<|>me<|>;", "nameNew", "export let nameNew = ''; nameNew;");
	}

	@Test def void testLet_local_atDecl() {
		testAtCursors("function foo() { let <|>na<|>me<|> = ''; name; }", "nameNew", "function foo() { let nameNew = ''; nameNew; }");
	}

	@Test def void testLet_local_atRef() {
		testAtCursors("function foo() { let name = ''; <|>na<|>me<|>; }", "nameNew", "function foo() { let nameNew = ''; nameNew; }");
	}

	@Test def void testFunctionDecl_atDecl() {
		testAtCursors("function <|>na<|>me<|>() {} name();", "nameNew", "function nameNew() {} nameNew();");
	}

	@Test def void testFunctionDecl_atRef() {
		testAtCursors("function name() {} <|>na<|>me<|>();", "nameNew", "function nameNew() {} nameNew();");
	}

	@Test def void testFunctionDecl_FormalParameter_atDecl() {
		testAtCursors("function foo(<|>na<|>me<|>, param2) { name; param2; }", "nameNew", "function foo(nameNew, param2) { nameNew; param2; }");
	}

	@Test def void testFunctionDecl_FormalParameter_atRef() {
		testAtCursors("function foo(name, param2) { <|>na<|>me<|>; param2; }", "nameNew", "function foo(nameNew, param2) { nameNew; param2; }");
	}

	@Test def void testFunctionExpr_atDecl() {
		testAtCursors("let fn = function <|>na<|>me<|>() { name(); }", "nameNew", "let fn = function nameNew() { nameNew(); }");
	}

	@Test def void testFunctionExpr_atRef() {
		testAtCursors("let fn = function name() { <|>na<|>me<|>(); }", "nameNew", "let fn = function nameNew() { nameNew(); }");
	}

	@Test def void testClassDecl_atDecl() {
		testAtCursors("class <|>Na<|>me<|> {} new Name();", "NameNew", "class NameNew {} new NameNew();");
	}

	@Test def void testClassDecl_atRef() {
		testAtCursors("class Name {} new <|>Na<|>me<|>();", "NameNew", "class NameNew {} new NameNew();");
	}

	@Test def void testField_atDecl() {
		testAtCursors("class Cls { <|>na<|>me<|>; } new Cls().name;", "nameNew", "class Cls { nameNew; } new Cls().nameNew;");
	}

	@Test def void testField_atRef() {
		testAtCursors("class Cls { name; } new Cls().<|>na<|>me<|>;", "nameNew", "class Cls { nameNew; } new Cls().nameNew;");
	}

	@Test def void testGetter_atDecl() {
		testAtCursors("class Cls { get <|>na<|>me<|>() { return this.name; } } new Cls().name;", "nameNew", "class Cls { get nameNew() { return this.nameNew; } } new Cls().nameNew;");
	}

	@Test def void testGetter_atRefs() {
		testAtCursors("class Cls { get name() { return this.<|>na<|>me<|>; } } new Cls().<|>na<|>me<|>;", "nameNew", "class Cls { get nameNew() { return this.nameNew; } } new Cls().nameNew;");
	}

	@Test def void testSetter_atDecl() {
		testAtCursors("class Cls { set <|>na<|>me<|>(value) { this.name = null; } } new Cls().name = null;", "nameNew", "class Cls { set nameNew(value) { this.nameNew = null; } } new Cls().nameNew = null;");
	}

	@Test def void testSetter_atRefs() {
		testAtCursors("class Cls { set name(value) { this.<|>na<|>me<|> = null; } } new Cls().<|>na<|>me<|> = null;", "nameNew", "class Cls { set nameNew(value) { this.nameNew = null; } } new Cls().nameNew = null;");
	}

	@Test def void testSetter_FormalParameter_atDecl() {
		testAtCursors("class Cls { set setter(<|>na<|>me<|>) { name; } }", "nameNew", "class Cls { set setter(nameNew) { nameNew; } }");
	}

	@Test def void testSetter_FormalParameter_atRef() {
		testAtCursors("class Cls { set setter(name) { <|>na<|>me<|>; } }", "nameNew", "class Cls { set setter(nameNew) { nameNew; } }");
	}

	@Test def void testMethod_atDecl() {
		testAtCursors("class Cls { <|>na<|>me<|>() { return this.name(); } } new Cls().name();", "nameNew", "class Cls { nameNew() { return this.nameNew(); } } new Cls().nameNew();");
	}

	@Test def void testMethod_atRefs() {
		testAtCursors("class Cls { name() { return this.<|>na<|>me<|>(); } } new Cls().<|>na<|>me<|>();", "nameNew", "class Cls { nameNew() { return this.nameNew(); } } new Cls().nameNew();");
	}

	@Test def void testMethod_FormalParameter_atDecl() {
		testAtCursors("class Cls { method(<|>na<|>me<|>, param2) { name; param2; } }", "nameNew", "class Cls { method(nameNew, param2) { nameNew; param2; } }");
	}

	@Test def void testMethod_FormalParameter_atRef() {
		testAtCursors("class Cls { method(name, param2) { <|>na<|>me<|>; param2; } }", "nameNew", "class Cls { method(nameNew, param2) { nameNew; param2; } }");
	}

	@Ignore("class expressions not supported")
	@Test def void testClassExpr_atDecl() {
		testAtCursors("let cls = class <|>Na<|>me<|> { m() { Name; } };", "NameNew", "let cls = class NameNew { m() { NameNew; } };");
	}

	@Ignore("class expressions not supported")
	@Test def void testClassExpr_atRef() {
		testAtCursors("let cls = class Name { m() { <|>Na<|>me<|>; } };", "NameNew", "let cls = class NameNew { m() { NameNew; } };");
	}

	@Test def void testEnumDecl_atDecl() {
		testAtCursors("enum <|>Na<|>me<|> { Lit1, Lit2 } Name.Lit1;", "NameNew", "enum NameNew { Lit1, Lit2 } NameNew.Lit1;");
	}

	@Test def void testEnumDecl_atRef() {
		testAtCursors("enum Name { Lit1, Lit2 } <|>Na<|>me<|>.Lit1;", "NameNew", "enum NameNew { Lit1, Lit2 } NameNew.Lit1;");
	}

	@Test def void testEnumLiteral_atDecl() {
		testAtCursors("enum E { <|>Na<|>me<|> } E.Name;", "NameNew", "enum E { NameNew } E.NameNew;");
	}

	@Test def void testEnumLiteral_atRef() {
		testAtCursors("enum E { Name } E.<|>Na<|>me<|>;", "NameNew", "enum E { NameNew } E.NameNew;");
	}

	@Test def void testLabeledBlock_atDecl() {
		testAtCursors("<|>na<|>me<|>: { break name; }", "nameNew", "nameNew: { break nameNew; }");
	}

	@Test def void testLabeledBlock_atRef() {
		testAtCursors("name: { break <|>na<|>me<|>; }", "nameNew", "nameNew: { break nameNew; }");
	}

	@Test def void testLabeledLoop_atDecl() {
		testAtCursors("<|>na<|>me<|>: for(;;) { for(;;) { continue name; } }", "nameNew", "nameNew: for(;;) { for(;;) { continue nameNew; } }");
	}

	@Test def void testLabeledLoop_atRef() {
		testAtCursors("name: for(;;) { for(;;) { continue <|>na<|>me<|>; } }", "nameNew", "nameNew: for(;;) { for(;;) { continue nameNew; } }");
	}

	// FIXME: more cases (esp. subclasses of PropertyNameOwner, object literals, destructuring, structural types)

	@Test def void testProperty_atDecl() {
		testAtCursors("({<|>na<|>me<|>: ''}).name;", "nameNew", "({nameNew: ''}).nameNew;");
	}

	@Test def void testProperty_atRef() {
		testAtCursors("({name: ''}).<|>na<|>me<|>;", "nameNew", "({nameNew: ''}).nameNew;");
	}

	@Test def void testProperty_viaInferredType_atDecl() {
		testAtCursors("let obj = {<|>na<|>me<|>: ''}; obj.name;", "nameNew", "let obj = {nameNew: ''}; obj.nameNew;");
	}

	@Test def void testProperty_viaInferredType_atRef() {
		testAtCursors("let obj = {name: ''}; obj.<|>na<|>me<|>;", "nameNew", "let obj = {nameNew: ''}; obj.nameNew;");
	}

	@Test def void testProperty_viaDeclaredType_atDecl() {
		testAtCursors("let obj: ~Object with {<|>na<|>me<|>: string}; obj.name;", "nameNew", "let obj: ~Object with {nameNew: string}; obj.nameNew;");
	}

	@Test def void testProperty_viaDeclaredType_atRef() {
		testAtCursors("let obj: ~Object with {name: string}; obj.<|>na<|>me<|>;", "nameNew", "let obj: ~Object with {nameNew: string}; obj.nameNew;");
	}
}
