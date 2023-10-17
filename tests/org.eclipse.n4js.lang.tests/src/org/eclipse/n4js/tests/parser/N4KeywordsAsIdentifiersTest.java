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

import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.junit.Test;

public class N4KeywordsAsIdentifiersTest extends AbstractParserTest {

	@Test
	public void testTypeExpressionKeywordsAsIdentifiers() {
		parseESSuccessfully("""
				var abstract = "variable named abstract";
				var any = "variable named any";
				var as = "variable named as";
				var autoboxedType = "variable named autoboxedType";
				var from = "variable named from";
				var get = "variable named get";
				var implements = "variable named implements";
				var interface = "variable named interface";
				var intersection = "variable named intersection";
				var notnull = "variable named notnull";
				var nullable = "variable named nullable";
				var primitive = "variable named primitive";
				var private = "variable named private";
				var project = "variable named project";
				var protected = "variable named protected";
				var protectedApi = "variable named protectedApi";
				var public = "variable named public";
				var publicApi = "variable named publicApi";
				var record = "variable named record";
				var role = "variable named role";
				var set = "variable named set";
				var static = "variable named static";
				var type = "variable named type";
				var undefined = "variable named undefined";
				var union = "variable named union";
				var This = "variable named This";
				""");

	}

	@Test
	public void testKeywordsAsMemberNames() {
		Script script = parseESSuccessfully("""
				class C {
					var;
					catch;
					if;
					class;
					true;
					private;
				}
				""");
		var members = ((N4ClassDeclaration) script.getScriptElements().get(0)).getOwnedMembers();
		List<String> expectedNames = List.of("var", "catch", "if", "class", "true", "private");
		for (int i = 0; i < expectedNames.size(); i = i + 1) {
			assertEquals(expectedNames.get(i), members.get(i).getName());
		}
	}

	@Test
	public void testUseKeywordsAsMemberNames() {
		parseESSuccessfully("""
				class C {
					var;
					catch;
					if;
					class;
					true;
					private;
				}

				var c: C = new C();
				c.var = 1;
				c.catch = c.if;
				if (c.class == c.true) {}
				c.private = 0;
				""");
	}

}
