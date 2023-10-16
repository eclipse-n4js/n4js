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
package org.eclipse.n4js.tests.naming;

import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression;
import org.eclipse.n4js.N4JSParseHelper;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameKind;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests the properties of PropertyNameOwner and LiteralOrComputedPropertyName.
 */
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProviderWithIssueSuppression.class)
public class PropertyNameOwnerTest {

	private static final String SYM_PREFIX = N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX;

	@Inject
	private N4JSParseHelper parseHelper;
	@Inject
	private ValidationTestHelper valTestHelper;

	@Test
	public void testNameIsIdentifier() {
		Script script = prepareScript("""
				class C {
					field;
					get getter() {return null;}
					set setter(value) {}
					method() {}
				}
				""");
		assertPropertyName(script, N4FieldDeclaration.class, PropertyNameKind.IDENTIFIER, "field");
		assertPropertyName(script, N4GetterDeclaration.class, PropertyNameKind.IDENTIFIER, "getter");
		assertPropertyName(script, N4SetterDeclaration.class, PropertyNameKind.IDENTIFIER, "setter");
		assertPropertyName(script, N4MethodDeclaration.class, PropertyNameKind.IDENTIFIER, "method");
	}

	@Test
	public void testNameIsIdentifier2() {
		Script script = prepareScript("""
				var ol = {
					prop: undefined,
					get pgetter() {return null;},
					set psetter(value) {},
					pmethod() {}
				};
				""");
		assertPropertyName(script, PropertyNameValuePair.class, PropertyNameKind.IDENTIFIER, "prop");
		assertPropertyName(script, PropertyGetterDeclaration.class, PropertyNameKind.IDENTIFIER, "pgetter");
		assertPropertyName(script, PropertySetterDeclaration.class, PropertyNameKind.IDENTIFIER, "psetter");
		assertPropertyName(script, PropertyMethodDeclaration.class, PropertyNameKind.IDENTIFIER, "pmethod");
	}

	@Test
	public void testNameIsString() {
		Script script = prepareScript("""
				class C {
					'field';
					get 'getter'() {return null;}
					set 'setter'(value) {}
					'method'() {}
				}
				""");
		assertPropertyName(script, N4FieldDeclaration.class, PropertyNameKind.STRING, "field");
		assertPropertyName(script, N4GetterDeclaration.class, PropertyNameKind.STRING, "getter");
		assertPropertyName(script, N4SetterDeclaration.class, PropertyNameKind.STRING, "setter");
		assertPropertyName(script, N4MethodDeclaration.class, PropertyNameKind.STRING, "method");
	}

	@Test
	public void testNameIsString2() {
		Script script = prepareScript("""
				var ol = {
					'prop': undefined,
					get 'pgetter'() {return null;},
					set 'psetter'(value) {},
					'pmethod'() {}
				};
				""");
		assertPropertyName(script, PropertyNameValuePair.class, PropertyNameKind.STRING, "prop");
		assertPropertyName(script, PropertyGetterDeclaration.class, PropertyNameKind.STRING, "pgetter");
		assertPropertyName(script, PropertySetterDeclaration.class, PropertyNameKind.STRING, "psetter");
		assertPropertyName(script, PropertyMethodDeclaration.class, PropertyNameKind.STRING, "pmethod");
	}

	@Test
	public void testNameIsNumber() {
		Script script = prepareScript("""
				class C {
					39;
					get 40() {return null;}
					set 41(value) {}
					42() {}
				}
				""");
		assertPropertyName(script, N4FieldDeclaration.class, PropertyNameKind.NUMBER, "39");
		assertPropertyName(script, N4GetterDeclaration.class, PropertyNameKind.NUMBER, "40");
		assertPropertyName(script, N4SetterDeclaration.class, PropertyNameKind.NUMBER, "41");
		assertPropertyName(script, N4MethodDeclaration.class, PropertyNameKind.NUMBER, "42");
	}

	@Test
	public void testNameIsNumber2() {
		Script script = prepareScript("""
				var ol = {
					39: undefined,
					get 40() {return null;},
					set 41(value) {},
					42() {}
				};
				""");
		assertPropertyName(script, PropertyNameValuePair.class, PropertyNameKind.NUMBER, "39");
		assertPropertyName(script, PropertyGetterDeclaration.class, PropertyNameKind.NUMBER, "40");
		assertPropertyName(script, PropertySetterDeclaration.class, PropertyNameKind.NUMBER, "41");
		assertPropertyName(script, PropertyMethodDeclaration.class, PropertyNameKind.NUMBER, "42");
	}

	@Test
	public void testNameIsComputed() {
		Script script = prepareScript("""
				class C {
					['field'];
					get ['getter']() {return null;}
					set ['setter'](value) {}
					['method']() {}
				}
				""");
		assertPropertyName(script, N4FieldDeclaration.class, PropertyNameKind.COMPUTED, "field");
		assertPropertyName(script, N4GetterDeclaration.class, PropertyNameKind.COMPUTED, "getter");
		assertPropertyName(script, N4SetterDeclaration.class, PropertyNameKind.COMPUTED, "setter");
		assertPropertyName(script, N4MethodDeclaration.class, PropertyNameKind.COMPUTED, "method");
	}

	@Test
	public void testNameIsComputed2() {
		Script script = prepareScript("""
				var ol = {
					['prop']: undefined,
					get ['pgetter']() {return null;},
					set ['psetter'](value) {},
					['pmethod']() {}
				};
				""");
		assertPropertyName(script, PropertyNameValuePair.class, PropertyNameKind.COMPUTED, "prop");
		assertPropertyName(script, PropertyGetterDeclaration.class, PropertyNameKind.COMPUTED, "pgetter");
		assertPropertyName(script, PropertySetterDeclaration.class, PropertyNameKind.COMPUTED, "psetter");
		assertPropertyName(script, PropertyMethodDeclaration.class, PropertyNameKind.COMPUTED, "pmethod");
	}

	@Test
	public void testNameIsSymbol() {
		Script script = prepareScript("""
				class C {
					[Symbol.iterator]() {}
				}
				""");
		assertPropertyName(script, N4MethodDeclaration.class, PropertyNameKind.COMPUTED, SYM_PREFIX + "iterator");
	}

	@Test
	public void testNameIsSymbol2() {
		Script script = prepareScript("""
				var ol = {
					[Symbol.iterator]: undefined
				}
				""");
		assertPropertyName(script, PropertyNameValuePair.class, PropertyNameKind.COMPUTED, SYM_PREFIX + "iterator");
	}

	@Test
	public void testNameIsSymbol3() {
		Script script = prepareScript("""
				var ol = {
					[Symbol.iterator]() {}
				}
				""");
		assertPropertyName(script, PropertyMethodDeclaration.class, PropertyNameKind.COMPUTED, SYM_PREFIX + "iterator");
	}

	@Test
	public void testNameOfBindingProperty() {
		assertPropertyName(prepareScript("""
				var {prop: myVar} = {prop: undefined};
				"""), BindingProperty.class, PropertyNameKind.IDENTIFIER, "prop");
		assertPropertyName(prepareScript("""
				var {'prop': myVar} = {prop: undefined};
				"""), BindingProperty.class, PropertyNameKind.STRING, "prop");
		assertPropertyName(prepareScript("""
				var {42: myVar} = {42: undefined};
				"""), BindingProperty.class, PropertyNameKind.NUMBER, "42");
		assertPropertyName(prepareScript("""
				var {['prop']: myVar} = {prop: undefined};
				"""), BindingProperty.class, PropertyNameKind.COMPUTED, "prop");
		assertPropertyName(prepareScript("""
				var {[Symbol.iterator]: myVar} = {[Symbol.iterator]: undefined};
				"""), BindingProperty.class, PropertyNameKind.COMPUTED,
				SYM_PREFIX + "iterator");
	}

	/**
	 * Searches the 1st element of type 'elementType' and performs property-name-related assertions on that element.
	 */
	private void assertPropertyName(Script script, Class<? extends PropertyNameOwner> propOwnerType,
			PropertyNameKind expectedKind, String expectedName) {
		// PART 1: check the AST node
		PropertyNameOwner node = head(filter(script.eAllContents(), propOwnerType));
		assertNotNull("no property name owner found of type: " + propOwnerType.getName(), node);
		if (expectedKind == PropertyNameKind.STRING
				|| expectedKind == PropertyNameKind.NUMBER) {

			assertSame(expectedKind, node.getDeclaredName().getKind());
			assertEquals(expectedName, node.getDeclaredName().getLiteralName());
			assertNull(node.getDeclaredName().getComputedName());
			assertNull(node.getDeclaredName().getExpression());
			assertEquals(expectedName, node.getName());

		} else if (expectedKind == PropertyNameKind.IDENTIFIER) {

			if (node instanceof BindingProperty) {
				assertNotNull(((BindingProperty) node).getProperty());
				assertEquals(expectedName, ((BindingProperty) node).getPropertyAsText());
				assertNull(node.getDeclaredName());
				assertEquals(expectedName, node.getName());
			} else if (node instanceof PropertyNameValuePair) {
				assertNotNull(((PropertyNameValuePair) node).getProperty());
				assertEquals(expectedName, ((PropertyNameValuePair) node).getPropertyAsText());
				assertNull(node.getDeclaredName());
				assertEquals(expectedName, node.getName());
			} else {
				assertSame(expectedKind, node.getDeclaredName().getKind());
				assertEquals(expectedName, node.getDeclaredName().getLiteralName());
				assertNull(node.getDeclaredName().getComputedName());
				assertNull(node.getDeclaredName().getExpression());
				assertEquals(expectedName, node.getName());
			}

		} else if (expectedKind == PropertyNameKind.COMPUTED) {

			assertSame(PropertyNameKind.COMPUTED, node.getDeclaredName().getKind());
			assertNull(node.getDeclaredName().getLiteralName());
			assertEquals(expectedName, node.getDeclaredName().getComputedName());
			assertNotNull(node.getDeclaredName().getExpression());
			assertEquals(expectedName, node.getName());

		} else {
			throw new IllegalArgumentException();
		}

		// PART 2: check name of the corresponding type model element
		if (!(node instanceof BindingProperty)) {
			// does not apply to binding properties (do not have a TModule element)
			EObject element = N4JSASTUtils.getCorrespondingTypeModelElement(node);
			assertNotNull(element);
			assertTrue(element instanceof IdentifiableElement);
			assertEquals(expectedName, ((IdentifiableElement) element).getName());
		}
	}

	private Script prepareScript(CharSequence csq) {
		Script script;
		try {
			script = parseHelper.parseN4js(csq);
			parseHelper.assertNoParseErrors(script);
			valTestHelper.validate(script);
			valTestHelper.assertNoIssues(script);
			return script;
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}
}
