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
package org.eclipse.n4js.tests.naming

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.N4JSParseHelper
import org.eclipse.n4js.n4JS.BindingProperty
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration
import org.eclipse.n4js.n4JS.PropertyNameKind
import org.eclipse.n4js.n4JS.PropertyNameOwner
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.PropertySetterDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.parser.conversion.ComputedPropertyNameValueConverter
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Tests the properties of PropertyNameOwner and LiteralOrComputedPropertyName.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
class PropertyNameOwnerTest {

	private static final String SYM_PREFIX = ComputedPropertyNameValueConverter.SYMBOL_IDENTIFIER_PREFIX;

	@Inject
	private extension N4JSParseHelper
	@Inject
	private extension ValidationTestHelper;

	@Test
	def void testNameIsIdentifier() {
		val script = '''
			class C {
				field;
				get getter() {return null;}
				set setter(value) {}
				method() {}
			}
		'''.prepareScript
		script.assertPropertyName(N4FieldDeclaration, PropertyNameKind.IDENTIFIER, "field");
		script.assertPropertyName(N4GetterDeclaration, PropertyNameKind.IDENTIFIER, "getter");
		script.assertPropertyName(N4SetterDeclaration, PropertyNameKind.IDENTIFIER, "setter");
		script.assertPropertyName(N4MethodDeclaration, PropertyNameKind.IDENTIFIER, "method");
	}

	@Test
	def void testNameIsIdentifier2() {
		val script = '''
			var ol = {
				prop: undefined,
				get pgetter() {return null;},
				set psetter(value) {},
				pmethod() {}
			};
		'''.prepareScript
		script.assertPropertyName(PropertyNameValuePair, PropertyNameKind.IDENTIFIER, "prop");
		script.assertPropertyName(PropertyGetterDeclaration, PropertyNameKind.IDENTIFIER, "pgetter");
		script.assertPropertyName(PropertySetterDeclaration, PropertyNameKind.IDENTIFIER, "psetter");
		script.assertPropertyName(PropertyMethodDeclaration, PropertyNameKind.IDENTIFIER, "pmethod");
	}

	@Test
	def void testNameIsString() {
		val script = '''
			class C {
				'field';
				get 'getter'() {return null;}
				set 'setter'(value) {}
				'method'() {}
			}
		'''.prepareScript
		script.assertPropertyName(N4FieldDeclaration, PropertyNameKind.STRING, "field");
		script.assertPropertyName(N4GetterDeclaration, PropertyNameKind.STRING, "getter");
		script.assertPropertyName(N4SetterDeclaration, PropertyNameKind.STRING, "setter");
		script.assertPropertyName(N4MethodDeclaration, PropertyNameKind.STRING, "method");
	}

	@Test
	def void testNameIsString2() {
		val script = '''
			var ol = {
				'prop': undefined,
				get 'pgetter'() {return null;},
				set 'psetter'(value) {},
				'pmethod'() {}
			};
		'''.prepareScript
		script.assertPropertyName(PropertyNameValuePair, PropertyNameKind.STRING, "prop");
		script.assertPropertyName(PropertyGetterDeclaration, PropertyNameKind.STRING, "pgetter");
		script.assertPropertyName(PropertySetterDeclaration, PropertyNameKind.STRING, "psetter");
		script.assertPropertyName(PropertyMethodDeclaration, PropertyNameKind.STRING, "pmethod");
	}

	@Test
	def void testNameIsNumber() {
		val script = '''
			class C {
				39;
				get 40() {return null;}
				set 41(value) {}
				42() {}
			}
		'''.prepareScript
		script.assertPropertyName(N4FieldDeclaration, PropertyNameKind.NUMBER, "39");
		script.assertPropertyName(N4GetterDeclaration, PropertyNameKind.NUMBER, "40");
		script.assertPropertyName(N4SetterDeclaration, PropertyNameKind.NUMBER, "41");
		script.assertPropertyName(N4MethodDeclaration, PropertyNameKind.NUMBER, "42");
	}

	@Test
	def void testNameIsNumber2() {
		val script = '''
			var ol = {
				39: undefined,
				get 40() {return null;},
				set 41(value) {},
				42() {}
			};
		'''.prepareScript
		script.assertPropertyName(PropertyNameValuePair, PropertyNameKind.NUMBER, "39");
		script.assertPropertyName(PropertyGetterDeclaration, PropertyNameKind.NUMBER, "40");
		script.assertPropertyName(PropertySetterDeclaration, PropertyNameKind.NUMBER, "41");
		script.assertPropertyName(PropertyMethodDeclaration, PropertyNameKind.NUMBER, "42");
	}

	@Test
	def void testNameIsComputed() {
		val script = '''
			class C {
				['field'];
				get ['getter']() {return null;}
				set ['setter'](value) {}
				['method']() {}
			}
		'''.prepareScript
		script.assertPropertyName(N4FieldDeclaration, PropertyNameKind.COMPUTED, "field");
		script.assertPropertyName(N4GetterDeclaration, PropertyNameKind.COMPUTED, "getter");
		script.assertPropertyName(N4SetterDeclaration, PropertyNameKind.COMPUTED, "setter");
		script.assertPropertyName(N4MethodDeclaration, PropertyNameKind.COMPUTED, "method");
	}

	@Test
	def void testNameIsComputed2() {
		val script = '''
			var ol = {
				['prop']: undefined,
				get ['pgetter']() {return null;},
				set ['psetter'](value) {},
				['pmethod']() {}
			};
		'''.prepareScript
		script.assertPropertyName(PropertyNameValuePair, PropertyNameKind.COMPUTED, "prop");
		script.assertPropertyName(PropertyGetterDeclaration, PropertyNameKind.COMPUTED, "pgetter");
		script.assertPropertyName(PropertySetterDeclaration, PropertyNameKind.COMPUTED, "psetter");
		script.assertPropertyName(PropertyMethodDeclaration, PropertyNameKind.COMPUTED, "pmethod");
	}

	@Test
	def void testNameIsSymbol() {
		val script = '''
			class C {
				[Symbol.iterator]() {}
			}
		'''.prepareScript
		script.assertPropertyName(N4MethodDeclaration, PropertyNameKind.COMPUTED, SYM_PREFIX + "iterator");
	}

	@Test
	def void testNameIsSymbol2() {
		val script = '''
			var ol = {
				[Symbol.iterator]: undefined
			}
		'''.prepareScript
		script.assertPropertyName(PropertyNameValuePair, PropertyNameKind.COMPUTED, SYM_PREFIX + "iterator");
	}

	@Test
	def void testNameIsSymbol3() {
		val script = '''
			var ol = {
				[Symbol.iterator]() {}
			}
		'''.prepareScript
		script.assertPropertyName(PropertyMethodDeclaration, PropertyNameKind.COMPUTED, SYM_PREFIX + "iterator");
	}

	@Test
	def void testNameOfBindingProperty() {
		'''
			var {prop: myVar} = {prop: undefined};
		'''.prepareScript.assertPropertyName(BindingProperty, PropertyNameKind.IDENTIFIER, "prop");
		'''
			var {'prop': myVar} = {prop: undefined};
		'''.prepareScript.assertPropertyName(BindingProperty, PropertyNameKind.STRING, "prop");
		'''
			var {42: myVar} = {42: undefined};
		'''.prepareScript.assertPropertyName(BindingProperty, PropertyNameKind.NUMBER, "42");
		'''
			var {['prop']: myVar} = {prop: undefined};
		'''.prepareScript.assertPropertyName(BindingProperty, PropertyNameKind.COMPUTED, "prop");
		'''
			var {[Symbol.iterator]: myVar} = {[Symbol.iterator]: undefined};
		'''.prepareScript.assertPropertyName(BindingProperty, PropertyNameKind.COMPUTED, SYM_PREFIX + "iterator");
	}

	/**
	 * Searches the 1st element of type 'elementType' and performs property-name-related assertions on that element.
	 */
	def private void assertPropertyName(Script script, Class<? extends PropertyNameOwner> propOwnerType, PropertyNameKind expectedKind, String expectedName) {
		// PART 1: check the AST node
		val node = script.eAllContents.filter(propOwnerType).head;
		assertNotNull("no property name owner found of type: " + propOwnerType.getName(), node);
		if (expectedKind === PropertyNameKind.IDENTIFIER
			|| expectedKind === PropertyNameKind.STRING
			|| expectedKind === PropertyNameKind.NUMBER) {

			assertSame(expectedKind, node.declaredName.kind);
			assertEquals(expectedName, node.declaredName.literalName);
			assertNull(node.declaredName.computedName);
			assertNull(node.declaredName.expression);
			assertEquals(expectedName, node.name);

		} else if(expectedKind === PropertyNameKind.COMPUTED) {

			assertSame(PropertyNameKind.COMPUTED, node.declaredName.kind);
			assertNull(node.declaredName.literalName);
			assertEquals(expectedName, node.declaredName.computedName);
			assertNotNull(node.declaredName.expression);
			assertEquals(expectedName, node.name);

		} else {
			throw new IllegalArgumentException();
		}
		// PART 2: check name of the corresponding type model element
		if(!(node instanceof BindingProperty)) { // does not apply to binding properties (do not have a TModule element)
			val element = N4JSASTUtils.getCorrespondingTypeModelElement(node);
			assertNotNull(element);
			assertTrue(element instanceof IdentifiableElement);
			assertEquals(expectedName, (element as IdentifiableElement).name);
		}
	}

	def private Script prepareScript(CharSequence csq) {
		val script = csq.parseN4js;
		script.assertNoParseErrors;
		script.validate;
		script.assertNoIssues;
		return script;
	}
}
