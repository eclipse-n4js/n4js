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
package org.eclipse.n4js.xsemantics;

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Map;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * N4JS Spec Test: 6.1.5. Object Literal, Type Inference
 *
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class N6_1_05_ObjectLiteralTypesystemTest extends AbstractTypesystemTest {

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	ContainerTypesHelper containerTypesHelper;

	final static CharSequence scriptPrefix = """
			class A{}
			class B extends A{}
			var A a;
			var B b;
			var number n1;
			var number n2;
			var string s1;
			var string s2;
			var boolean f1;
			var boolean f2;
			""";

	public void assertObjectLiteralType(String expectedType, String literal,
			Map<String, String> expectedPropertiesWithType) {

		try {
			Script script = parseHelper.parse(scriptPrefix + """
					var ol = %s;
					""".formatted(literal));

			assertNotNull(script);
			RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(script);
			Expression ol = ((VariableStatement) last(script.getScriptElements())).getVarDecl().get(0).getExpression();

			assertTrue("Expected ObjectLiteral, was " + ol.eClass().getName(), ol instanceof ObjectLiteral);
			TypeRef type = checkedType(G, ol);
			assertEquals(expectedType, type.getTypeRefAsString());

			assertTrue(type instanceof ParameterizedTypeRefStructural);
			assertNotNull(((ParameterizedTypeRefStructural) type).getStructuralType());
			TStructuralType oltype = ((ParameterizedTypeRefStructural) type).getStructuralType();
			MemberList<TMember> oltypeMembers = containerTypesHelper.fromContext(script).members(oltype);

			String expect = join(",", map(oltypeMembers,
					m -> (m.isWriteable() ? "" : "ro ") + m.getMemberAccessModifier()
							+ " " + m.getContainingType().getName() + "." + m.getName()));

			assertEquals(
					expect,
					expectedPropertiesWithType.size(), oltypeMembers.size());

			for (Map.Entry<String, String> entry : expectedPropertiesWithType.entrySet()) {
				String expectedPropertyName = entry.getKey();
				String expectedPropertyType = entry.getValue();
				boolean searchForSetter = expectedPropertyName.endsWith("=");
				String searchedNamed = searchForSetter
						? expectedPropertyName.substring(0, expectedPropertyName.length() - 1)
						: expectedPropertyName;
				boolean staticAccess = false;
				TMember member = containerTypesHelper.fromContext(script).findMember(oltype, searchedNamed,
						searchForSetter,
						staticAccess);
				assertNotNull("No property found with name " + expectedPropertyName, member);
				TypeRef propertyType = checkedType(G, member);
				assertEquals(expectedPropertyType, propertyType.getTypeRefAsString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testEmptyObjectLiteralType() {
		assertObjectLiteralType("~Object", "{}", Collections.emptyMap());
	}

	@Test
	public void testWithPropertyNameValuePair1() {
		assertObjectLiteralType("~Object with { name: string }",
				"""
						{
							name: "Walter"
						}
						""", Map.of("name", "string"));
	}

	@Test
	public void testWithPropertyNameValuePair2() {
		assertObjectLiteralType("~Object with { name: string }",
				"""
						{
							name: "Walter" + "White"
						}
						""", Map.of("name", "string"));
	}

	@Test
	public void testWithPropertyNameValuePairWithThis() {
		assertObjectLiteralType("~Object with { name: any }",
				"""
						{
							name: this.name
						}
						""", Map.of("name", "any"));
	}

	@Test
	public void testWithPropertyNameValuePairDeclared() {
		assertObjectLiteralType("~Object with { name: string }",
				"""
						{
							string name: null
						}
						""", Map.of("name", "string"));
	}

	@Test
	public void testWithPropertyNameValuePairWithThisAndAdd() {
		assertObjectLiteralType("~Object with { name: string }",
				"""
						{
							name: this.name+"Walter"
						}
						""", Map.of("name", "string"));
	}

	@Test
	public void testWithGetter1() {
		assertObjectLiteralType("~Object with { get name(): string }",
				"""
						{
							get name(): string { return "walter" }
						}
						""", Map.of("name", "string"));
	}

	@Test
	public void testWithGetter2() {
		assertObjectLiteralType("~Object with { get name(): any }",
				"""
						{
							get name() { return "walter" }
						}
						""", Map.of("name", "any"));
	}

	@Test
	public void testWithSetter() {
		assertObjectLiteralType("~Object with { set name(x: any) }",
				"""
						{
							set name(x) {  }
						}
						""", Map.of("name=", "any"));
	}

	@Test
	public void testWithTypedSetter() {
		assertObjectLiteralType("~Object with { set name(x: string) }",
				"""
						{
							set name(x: string) {  }
						}
						""", Map.of("name=", "string"));
	}

	@Test
	public void testWithSetterTypedFromGetter() {
		assertObjectLiteralType("~Object with { get name(): string; set name(x: string) }",
				"""
						{
						get name(): string { return "walter" },
							set name(x) {  }
						}
						""", Map.of("name", "string", "name=", "string"));
	}

	@Test
	public void testWithGetterTypedFromSetter() {
		assertObjectLiteralType("~Object with { get name(): string; set name(x: string) }",
				"""
						{
						get name() { return null },
							set name(x: string) {  }
						}
						""", Map.of("name", "string", "name=", "string"));
	}

	@Test
	public void testWithLongerObjectLiteral() {
		assertObjectLiteralType("~Object with { get name(): string; set name(x: string); age: int }",
				"""
						{
						get name() { return null },
						set name(x: string) {  },
							age: 10
						}
						""", Map.of("name", "string", "name=", "string", "age", "int"));
	}
}
