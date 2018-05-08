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
package org.eclipse.n4js.json.tests.parser

import com.google.inject.Inject
import java.math.BigDecimal
import org.eclipse.n4js.json.JSON.JSONArray
import org.eclipse.n4js.json.JSON.JSONBooleanLiteral
import org.eclipse.n4js.json.JSON.JSONNullLiteral
import org.eclipse.n4js.json.JSON.JSONNumericLiteral
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.JSON.JSONStringLiteral
import org.eclipse.n4js.json.JSON.JSONValue
import org.eclipse.n4js.json.JSONInjectorProvider
import org.eclipse.n4js.json.JSONParseHelper
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Tests for parsing JSON files.
 */
@RunWith(XtextRunner)
@InjectWith(JSONInjectorProvider)
class JsonParserTest {

	@Inject extension JSONParseHelper

	/** Checks that all supported types of value literals are parsed correctly. */
	@Test def void testPlainValues() {
		''''''.parseSuccessfully // empty document
		'''	 '''.parseSuccessfully // whitespace document
		
		'''{}'''.parseSuccessfully;
		'''[]'''.parseSuccessfully;
		'''42'''.parseSuccessfully;
		'''42.42'''.parseSuccessfully;
		'''42e+42'''.parseSuccessfully;
		'''42E+42'''.parseSuccessfully;
		'''42.42E-42'''.parseSuccessfully;
		'''"string"'''.parseSuccessfully;
		'''null'''.parseSuccessfully;
		'''true'''.parseSuccessfully;
		'''false'''.parseSuccessfully;
	}
	
	/** Checks that all supported escape sequences are parsed correctly. */
	@Test def void testStringEscapeSequences() {
		'''"f\\no"'''.parseSuccessfully
		'''"\f"'''.parseSuccessfully
		'''"\n"'''.parseSuccessfully
		'''"\r"'''.parseSuccessfully
		'''"\t"'''.parseSuccessfully
		'''"\""'''.parseSuccessfully
		'''"\u2028"'''.parseSuccessfully
		'''"\/"'''.parseSuccessfully
		'''"\\"'''.parseSuccessfully
	}
	
	/** Checks that various different variations of numeric literals are parsed correctly. */
	@Test def void testNumericValues() {
		'''2'''.parseSuccessfully
		'''4'''.parseSuccessfully
		'''0.2'''.parseSuccessfully
		'''2.4'''.parseSuccessfully
		'''2.44444'''.parseSuccessfully
		'''4E+2'''.parseSuccessfully
		'''4.2E+2'''.parseSuccessfully
		'''4e+2'''.parseSuccessfully
		'''4.2e+2'''.parseSuccessfully
		'''4.2E2'''.parseSuccessfully
		'''4e2'''.parseSuccessfully
		
		'''-4'''.parseSuccessfully
		'''-4.2'''.parseSuccessfully
		'''-31.42'''.parseSuccessfully
		'''-42e+42'''.parseSuccessfully
		'''-42E-42'''.parseSuccessfully
		
		'''-42e42'''.parseSuccessfully
		'''-42E42'''.parseSuccessfully
		
		assertEqualsValue(new BigDecimal("-0"), '''-0'''.parseSuccessfully.content);
	}
	
	/** Checks that various different variations of string literals are parsed correctly. */
	@Test def void testStringValues() {
		'''""'''.parseSuccessfully
		'''"   "'''.parseSuccessfully
		'''"\t\n"'''.parseSuccessfully
		assertEqualsValue("\u1F604", '''"\u1F604"'''.parseSuccessfully.content); // parse unicode
		
		// use non-standard line/paragraph terminators in string literal
		'''"\u2028"'''.parseSuccessfully
		'''"\u2029"'''.parseSuccessfully
		
	}
	
	@Test def void testInvalidJSON() {
		'''NaN'''.parseUnsuccessfully;
		'''Infinity'''.parseUnsuccessfully;
		'''f()'''.parseUnsuccessfully // JS style function calls
		'''[[[identifier], 2], 3]'''.parseUnsuccessfully // JS style identifier use
		'''function f() {}'''.parseUnsuccessfully // JS style function declaration
		'''() => {}'''.parseUnsuccessfully // JS style arrow function
		
		// JS number literals that are invalid JSON number
		'''.42'''.parseUnsuccessfully
		'''+42'''.parseUnsuccessfully
		'''42.'''.parseUnsuccessfully
		'''[.42]'''.parseUnsuccessfully
		'''[-.42]'''.parseUnsuccessfully
		'''[-42.]'''.parseUnsuccessfully
		
		// JS string literals that are invalid JSON
		"''".parseUnsuccessfully // wrong quotes
		'''"\a"'''.parseUnsuccessfully // invalid escape character
		"\"\\\'\"".parseUnsuccessfully // invalid escape character
		'''"This string never ends'''.parseUnsuccessfully // unterminated string
		'''"This string never ends\"'''.parseUnsuccessfully // unterminated string
	
		// JS object literals that are invalid JSON
		'''{no; "keyquotes"'''.parseUnsuccessfully;
		'''{true: false}'''.parseUnsuccessfully;
		'''{0:0}'''.parseUnsuccessfully;
		'''{-42:24}'''.parseUnsuccessfully;
		'''{id: 2"}'''.parseUnsuccessfully
		'''{null: null"}'''.parseUnsuccessfully
		
		// un-escaped unicode control characters in strings
		'''"	"'''.parseUnsuccessfully; // un-escaped tab in string literal
		'''"
		"'''.parseUnsuccessfully // un-escaped linebreak in string literal
	}
	
	@Test def void testComments() {
		'''//single line comment
		{"content": 1}'''.parseSuccessfully;
		
		'''/* multi
		line
		comment*/ {"content" : 2}'''.parseSuccessfully
	}
	
	@Test def void testSimpleObjects() {
		val obj1 = assertIsObject('''{"a": 1, "b": 2}'''.parseSuccessfully.content);
		assertEqualsValue(1, assertHasKey(obj1, "a"));
		assertEqualsValue(2, assertHasKey(obj1, "b"));
		
		val obj2 = assertIsObject('''{"c": "str", "d": []}'''.parseSuccessfully.content);
		assertEqualsValue("str", assertHasKey(obj2, "c"));
		assertIsArray(0, assertHasKey(obj2, "d"));
	}
	
	/** Checks that an array of numeric literals is parsed correctly. */
	@Test def void testNumericArray() {
		val doc = '''[1, 2, 3]'''.parseSuccessfully;
		assertTrue(doc.content instanceof JSONArray);
		
		val arrayElements = assertIsArray(3, doc.content).elements;

		assertEqualsValue(1, arrayElements.get(0));
		assertEqualsValue(2, arrayElements.get(1));
		assertEqualsValue(3, arrayElements.get(2));
	}
	
	/** Checks that an array of mixed values is parsed correctly. */
	@Test def void testMixedArray() {
		val doc = '''[1, "str", {"v" : 1}, [], null, true, 42.42, 12e+2]'''.parseSuccessfully;
		assertTrue(doc.content instanceof JSONArray);
		
		val arrayElements = assertIsArray(8, doc.content).elements;
		
		assertEqualsValue(1, arrayElements.get(0));
		assertEqualsValue("str", arrayElements.get(1));
		
		val obj = assertIsObject(arrayElements.get(2));
		val vVal = assertHasKey(obj, "v");
		assertEqualsValue(1, vVal);
		
		assertIsArray(0, arrayElements.get(3));
		
		assertIsNullValue(arrayElements.get(4));
		assertEqualsValue(true, arrayElements.get(5));
		assertEqualsValue(42.42, arrayElements.get(6));
		assertEqualsValue(new BigDecimal(12e2), arrayElements.get(7));
	}
	
	/** Checks that the parsing of a nested JSON object works as intended. */
	@Test def void testNestedObject() {
		val doc = '''{
			"a": {
				"b": {
					"c": {
						"d": {
							"e": [1]
						}
					}
				}
			}
		}'''.parseSuccessfully;
	
		val objA = assertIsObject(doc.content);
		val objB = assertIsObject(assertHasKey(objA, "a"));
		val objC = assertIsObject(assertHasKey(objB, "b"));
		val objD = assertIsObject(assertHasKey(objC, "c"));
		val objE = assertIsObject(assertHasKey(objD, "d"));
		val arrE = assertIsArray(1, assertHasKey(objE, "e"));
		
		assertEqualsValue(1, arrE.elements.get(0));
	}
	
	/** Checks that the parsing of a nested JSON array works as intended. */
	@Test def void testNestedArray() {
		val doc = '''
		[
			[
				[
					[
						[]
					]
				]
			]
		]'''.parseSuccessfully;
	
		val arr1 = assertIsArray(1, doc.content);
		val arr2 = assertIsArray(1, arr1.elements.get(0));
		val arr3 = assertIsArray(1, arr2.elements.get(0));
		val arr4 = assertIsArray(1, arr3.elements.get(0));
		assertIsArray(0, arr4.elements.get(0));
	}
	
	/** Checks that the parsing of a simple Node.js package.json file works as intended. */
	@Test def void testSimpleNodePackageJson() {
		val doc = '''
		{
		  "name": "test-npm",
		  "version": "1.0.0",
		  "description": "",
		  "main": "index.js",
		  "scripts": {
		    "test": "echo \"Error: no test specified\" && exit 1"
		  },
		  "keywords": [],
		  "author": "",
		  "license": "ISC",
		  "dependencies": {
		    "a": "^16.3.2",
		    "b": "^0.3.2"
		  }
		}
		'''.parseSuccessfully;
		
		
		val rootObject = assertIsObject(doc.content);
		
		assertEqualsValue("test-npm", assertHasKey(rootObject, "name"));
		assertEqualsValue("1.0.0", assertHasKey(rootObject, "version"));
		assertEqualsValue("", assertHasKey(rootObject, "description"));
		assertEqualsValue("index.js", assertHasKey(rootObject, "main"));
		
		val scriptsObject = assertIsObject(assertHasKey(rootObject, "scripts"));
		assertEqualsValue("echo \"Error: no test specified\" && exit 1", assertHasKey(scriptsObject, "test"));
		
		assertIsArray(0, assertHasKey(rootObject, "keywords"));
		assertEqualsValue("", assertHasKey(rootObject, "author"));
		assertEqualsValue("ISC", assertHasKey(rootObject, "license"));
		
		val dependenciesObject = assertIsObject(assertHasKey(rootObject, "dependencies"));
		assertEqualsValue("^16.3.2", assertHasKey(dependenciesObject, "a"));
		assertEqualsValue("^0.3.2", assertHasKey(dependenciesObject, "b"));
		
	}
	
	/**  */
	@Test def testUnicodeControlCharacters() {
		'''["a a"]'''.parseUnsuccessfully
	}

	/** Asserts that the given {@code actual} JSON value represents a number of value {@code numberValue}. */
	private def assertEqualsValue(long numberValue, JSONValue actual) {
		assertTrue("Value is expected to be a JSONNumericLiteral", actual instanceof JSONNumericLiteral);
		assertEquals(numberValue, (actual as JSONNumericLiteral).value.longValue);
	}
	
	/** Asserts that the given {@code actual} JSON value represents a number of value {@code numberValue}. */
	private def assertEqualsValue(double numberValue, JSONValue actual) {
		assertTrue("Value is expected to be a JSONNumericLiteral", actual instanceof JSONNumericLiteral);
		assertEquals(numberValue, (actual as JSONNumericLiteral).value.doubleValue, 0.0);
	}
	
	/** Asserts that the given decimal JSON value {@code actual} represents (compareTo == 0) the given {@code bigDecimalValue}. */
	private def assertEqualsValue(BigDecimal bigDecimalValue, JSONValue actual) {
		assertTrue("Value is expected to be a JSONNumericLiteral", actual instanceof JSONNumericLiteral);
		assertTrue(bigDecimalValue.compareTo((actual as JSONNumericLiteral).value) == 0);
	}
	
	/** Asserts that the given {@code actual} JSON value represents a string with value {@code stringValue}. */
	private def assertEqualsValue(String stringValue, JSONValue actual) {
		assertTrue("Value is expected to be a JSONStringLiteral", actual instanceof JSONStringLiteral);
		assertEquals(stringValue, (actual as JSONStringLiteral).value);
	}
	
	/** Asserts that the given {@code actual} JSON value represents a boolean value with value {@code booleanValue}. */
	private def assertEqualsValue(boolean booleanValue, JSONValue actual) {
		assertTrue("Value is expected to be a JSONBooleanLiteral", actual instanceof JSONBooleanLiteral);
		assertEquals(booleanValue, (actual as JSONBooleanLiteral).booleanValue);
	}
	
	/** Asserts that the given {@code actual} JSON value represents a null value. */
	private def assertIsNullValue(JSONValue actual) {
		assertTrue("Value is expected to be a JSONNullLiteral", actual instanceof JSONNullLiteral);
	}
	
	/** Asserts the given {@link JSONValue} is an array of given size and returns its elements. */
	private def JSONArray assertIsArray(int size, JSONValue actual) {
		assertTrue("Value is expected to be a JSONArray", actual instanceof JSONArray);
		assertEquals(size, (actual as JSONArray).elements.size);
		return (actual as JSONArray);
	}
	
	/** Asserts the given {@link JSONValue} is an object of given size and returns it. */
	private def JSONObject assertIsObject(JSONValue actual) {
		assertTrue("Value is expected to be a JSONObject", actual instanceof JSONObject);
		return (actual as JSONObject);
	}
	
	/** Asserts that the given {@link JSONObject} has a value for the given key and returns the value. */
	private def JSONValue assertHasKey(JSONObject object, String key) {
		val valuesByName = object.nameValuePairs.toList.toMap([pair | pair.name], [pair | pair.value]);
		assertTrue("Object " + object + " is expected to have a value for key " + key, valuesByName.keySet.contains(key));
		return valuesByName.get(key);
	}
}
