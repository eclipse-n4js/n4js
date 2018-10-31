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
package org.eclipse.n4js.xsemantics

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural
import java.util.Map
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * N4JS Spec Test: 6.1.5. Object Literal, Type Inference
 *
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class N6_1_05_ObjectLiteralTypesystemTest extends AbstractTypesystemTest {

	@Inject
	extension ParseHelper<Script>

	@Inject ContainerTypesHelper containerTypesHelper;

	final static CharSequence scriptPrefix = '''
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
	'''

	def void assertObjectLiteralType(String expectedType, String literal, Map<String, String> expectedPropertiesWithType) {
		val script = (scriptPrefix + '''
		var ol = «literal»;'''
			).parse();
		assertNotNull(script);
		val G = RuleEnvironmentExtensions.newRuleEnvironment(script);
		val ol = (script.scriptElements.last as VariableStatement).varDecl.head.expression;

		assertTrue("Expected ObjectLiteral, was " + ol.eClass.name, ol instanceof ObjectLiteral)
		val type = checkedType(G, ol)
		assertEquals(expectedType, type.typeRefAsString);

		assertTrue(type instanceof ParameterizedTypeRefStructural);
		assertNotNull((type as ParameterizedTypeRefStructural).structuralType)
		val oltype = (type as ParameterizedTypeRefStructural).structuralType
		val oltypeMembers = containerTypesHelper.fromContext(script).members(oltype);
		assertEquals(
			oltypeMembers.map[(if (it.isWriteable) "" else "ro ")+it.memberAccessModifier + " " + it.containingType.name+"."+it.name].join(","),
			expectedPropertiesWithType.size, oltypeMembers.size
		)

		expectedPropertiesWithType.forEach [ expectedPropertyName, expectedPropertyType |
			val searchForSetter = expectedPropertyName.endsWith("=");
			val searchedNamed = if (searchForSetter) {
					expectedPropertyName.substring(0, expectedPropertyName.length - 1)
				} else {
					expectedPropertyName
				}
			val staticAccess = false
			var member = containerTypesHelper.fromContext(script).findMember(oltype, searchedNamed, searchForSetter, staticAccess);
			assertNotNull("No property found with name " + expectedPropertyName, member)
			val propertyType = checkedType(G, member)
			assertEquals(expectedPropertyType, propertyType.typeRefAsString)
		]
	}

	@Test
	def void testEmptyObjectLiteralType() {
		assertObjectLiteralType("~Object", '''{}''', #{});
	}

	@Test
	def void testWithPropertyNameValuePair1() {
		assertObjectLiteralType("~Object with { name: string }",
			'''{
			name: "Walter"
		}''', #{"name" -> "string"});
	}

	@Test
	def void testWithPropertyNameValuePair2() {
		assertObjectLiteralType("~Object with { name: string }",
			'''{
			name: "Walter" + "White"
		}''', #{"name" -> "string"});
	}

	@Test
	def void testWithPropertyNameValuePairWithThis() {
		assertObjectLiteralType("~Object with { name: any }",
			'''{
			name: this.name
		}''', #{"name" -> "any"});
	}

	@Test
	def void testWithPropertyNameValuePairDeclared() {
		assertObjectLiteralType("~Object with { name: string }",
			'''{
			string name: null
		}''', #{"name" -> "string"});
	}

	@Test
	def void testWithPropertyNameValuePairWithThisAndAdd() {
		assertObjectLiteralType("~Object with { name: string }",
			'''{
			name: this.name+"Walter"
		}''', #{"name" -> "string"});
	}

	@Test
	def void testWithGetter1() {
		assertObjectLiteralType("~Object with { get name(): string }",
			'''{
			get name(): string { return "walter" }
		}''', #{"name" -> "string"});
	}

	@Test
	def void testWithGetter2() {
		assertObjectLiteralType("~Object with { get name(): any }",
			'''{
			get name() { return "walter" }
		}''', #{"name" -> "any"});
	}

	@Test
	def void testWithSetter() {
		assertObjectLiteralType("~Object with { set name(x: any) }",
			'''{
			set name(x) {  }
		}''', #{"name=" -> "any"});
	}

	@Test
	def void testWithTypedSetter() {
		assertObjectLiteralType("~Object with { set name(x: string) }",
			'''{
			set name(x: string) {  }
		}''', #{"name=" -> "string"});
	}

	@Test
	def void testWithSetterTypedFromGetter() {
		assertObjectLiteralType("~Object with { get name(): string; set name(x: string) }",
			'''{
			get name(): string { return "walter" },
			set name(x) {  }
		}''', #{"name" -> "string", "name=" -> "string"});
	}

	@Test
	def void testWithGetterTypedFromSetter() {
		assertObjectLiteralType("~Object with { get name(): string; set name(x: string) }",
			'''{
			get name() { return null },
			set name(x: string) {  }
		}''', #{"name" -> "string", "name=" -> "string"});
	}

	@Test
	def void testWithLongerObjectLiteral() {
		assertObjectLiteralType("~Object with { get name(): string; set name(x: string); age: int }",
			'''{
			get name() { return null },
			set name(x: string) {  },
			age: 10
		}''', #{"name" -> "string", "name=" -> "string", "age"->"int"});
	}
}
