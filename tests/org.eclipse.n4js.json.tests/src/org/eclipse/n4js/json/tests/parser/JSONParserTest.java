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
package org.eclipse.n4js.json.tests.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.json.JSONInjectorProvider;
import org.eclipse.n4js.json.JSONParseHelper;
import org.eclipse.n4js.json.JSON.JSONArray;
import org.eclipse.n4js.json.JSON.JSONBooleanLiteral;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONNullLiteral;
import org.eclipse.n4js.json.JSON.JSONNumericLiteral;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;

/**
 * Tests for parsing JSON files.
 */
@RunWith(XtextRunner.class)
@InjectWith(JSONInjectorProvider.class)

public class JSONParserTest {

	@Inject
	JSONParseHelper jsonParserHelper;

	/** Checks that all supported types of value literals are parsed correctly. */
	@Test
	public void testPlainValues() throws Exception {
		jsonParserHelper.parseSuccessfully("");// empty document
		jsonParserHelper.parseSuccessfully("	 "); // whitespace document
		jsonParserHelper.parseSuccessfully("{}");
		jsonParserHelper.parseSuccessfully("[]");
		jsonParserHelper.parseSuccessfully("42");
		jsonParserHelper.parseSuccessfully("42.42");
		jsonParserHelper.parseSuccessfully("42e+42");
		jsonParserHelper.parseSuccessfully("42E+42");
		jsonParserHelper.parseSuccessfully("42.42E-42");
		jsonParserHelper.parseSuccessfully("\"string\"");
		jsonParserHelper.parseSuccessfully("null");
		jsonParserHelper.parseSuccessfully("true");
		jsonParserHelper.parseSuccessfully("false");
	}

	/** Checks that all supported escape sequences are parsed correctly. */
	@Test
	public void testStringEscapeSequences() throws Exception {
		jsonParserHelper.parseSuccessfully("\"f\\no\"");
		jsonParserHelper.parseSuccessfully("\"\\f\"");
		jsonParserHelper.parseSuccessfully("\"\\n\"");
		jsonParserHelper.parseSuccessfully("\"\\r\"");
		jsonParserHelper.parseSuccessfully("\"\\t\"");
		jsonParserHelper.parseSuccessfully("\"\\\"\"");
		jsonParserHelper.parseSuccessfully("\"\\u2028\"");
		jsonParserHelper.parseSuccessfully("\"\\/\"");
		jsonParserHelper.parseSuccessfully("\"\\\\\"");
	}

	/** Checks that various different variations of numeric literals are parsed correctly. */
	@Test
	public void testNumericValues() throws Exception {
		jsonParserHelper.parseSuccessfully("2");
		jsonParserHelper.parseSuccessfully("4");
		jsonParserHelper.parseSuccessfully("0.2");
		jsonParserHelper.parseSuccessfully("2.4");
		jsonParserHelper.parseSuccessfully("2.44444");
		jsonParserHelper.parseSuccessfully("4E+2");
		jsonParserHelper.parseSuccessfully("4.2E+2");
		jsonParserHelper.parseSuccessfully("4e+2");
		jsonParserHelper.parseSuccessfully("4.2e+2");
		jsonParserHelper.parseSuccessfully("4.2E2");
		jsonParserHelper.parseSuccessfully("4e2");

		jsonParserHelper.parseSuccessfully("-4");
		jsonParserHelper.parseSuccessfully("-4.2");
		jsonParserHelper.parseSuccessfully("-31.42");
		jsonParserHelper.parseSuccessfully("-42e+42");
		jsonParserHelper.parseSuccessfully("-42E-42");

		jsonParserHelper.parseSuccessfully("-42e42");
		jsonParserHelper.parseSuccessfully("-42E42");

		assertEqualsValue(new BigDecimal("-0"), jsonParserHelper.parseSuccessfully("-0").getContent());
	}

	/** Checks that various different variations of string literals are parsed correctly. */
	@Test
	public void testStringValues() throws Exception {
		jsonParserHelper.parseSuccessfully("\"\"");
		jsonParserHelper.parseSuccessfully("\"   \"");
		jsonParserHelper.parseSuccessfully("\"\\t\\n\"");
		assertEqualsValue("\u1F604", jsonParserHelper.parseSuccessfully("\"\u1F604\"").getContent()); // parse unicode

		// use non-standard line/paragraph terminators in string literal
		jsonParserHelper.parseSuccessfully("\"\u2028\"");
		jsonParserHelper.parseSuccessfully("\"\u2029\"");

	}

	@Test
	public void testInvalidJSON() throws Exception {
		jsonParserHelper.parseUnsuccessfully("NaN");
		jsonParserHelper.parseUnsuccessfully("Infinity");
		jsonParserHelper.parseUnsuccessfully("f()"); // JS style function calls
		jsonParserHelper.parseUnsuccessfully("[[[identifier], 2], 3]"); // JS style identifier use
		jsonParserHelper.parseUnsuccessfully("function f() {}"); // JS style function declaration
		jsonParserHelper.parseUnsuccessfully("() => {}"); // JS style arrow function

		// JS number literals that are invalid JSON number
		jsonParserHelper.parseUnsuccessfully(".42");
		jsonParserHelper.parseUnsuccessfully("+42");
		jsonParserHelper.parseUnsuccessfully("42.");
		jsonParserHelper.parseUnsuccessfully("[.42]");
		jsonParserHelper.parseUnsuccessfully("[-.42]");
		jsonParserHelper.parseUnsuccessfully("[-42.]");

		// JS string literals that are invalid JSON
		jsonParserHelper.parseUnsuccessfully("''");// wrong quotes
		jsonParserHelper.parseUnsuccessfully("\"\\a\""); // invalid escape character
		jsonParserHelper.parseUnsuccessfully("\"\\\'\""); // invalid escape character
		jsonParserHelper.parseUnsuccessfully("\"This string never ends"); // unterminated string
		jsonParserHelper.parseUnsuccessfully("\"This string never ends\\\""); // unterminated string

		// JS object literals that are invalid JSON
		jsonParserHelper.parseUnsuccessfully("{no; \"keyquotes\"");
		jsonParserHelper.parseUnsuccessfully("{true: false}");
		jsonParserHelper.parseUnsuccessfully("{0:0}");
		jsonParserHelper.parseUnsuccessfully("{-42:24}");
		jsonParserHelper.parseUnsuccessfully("{id: 2\"}");
		jsonParserHelper.parseUnsuccessfully("{null: null\"}");

		// un-escaped unicode control characters in strings
		jsonParserHelper.parseUnsuccessfully("\"	\""); // un-escaped tab in string literal
		jsonParserHelper.parseUnsuccessfully("""
				"
				"""); // un-escaped linebreak in string literal
	}

	@Test
	public void testComments() throws Exception {
		jsonParserHelper.parseSuccessfully("""
						//single line comment
				{"content": 1}""");

		jsonParserHelper.parseSuccessfully("""
						/* multi
				line
				comment*/ {"content" : 2}""");
	}

	@Test
	public void testSimpleObjects() throws Exception {
		JSONObject obj1 = assertIsObject(jsonParserHelper.parseSuccessfully("{\"a\": 1, \"b\": 2}").getContent());
		assertEqualsValue(1, assertHasKey(obj1, "a"));
		assertEqualsValue(2, assertHasKey(obj1, "b"));

		JSONObject obj2 = assertIsObject(
				jsonParserHelper.parseSuccessfully("{\"c\": \"str\", \"d\": []}").getContent());
		assertEqualsValue("str", assertHasKey(obj2, "c"));
		assertIsArray(0, assertHasKey(obj2, "d"));
	}

	/** Checks that an array of numeric literals is parsed correctly. */
	@Test
	public void testNumericArray() throws Exception {
		JSONDocument doc = jsonParserHelper.parseSuccessfully("[1, 2, 3]");
		assertTrue(doc.getContent() instanceof JSONArray);

		EList<JSONValue> arrayElements = assertIsArray(3, doc.getContent()).getElements();

		assertEqualsValue(1, arrayElements.get(0));
		assertEqualsValue(2, arrayElements.get(1));
		assertEqualsValue(3, arrayElements.get(2));
	}

	/** Checks that an array of mixed values is parsed correctly. */
	@Test
	public void testMixedArray() throws Exception {
		JSONDocument doc = jsonParserHelper.parseSuccessfully("""
				[1, "str", {"v" : 1}, [], null, true, 42.42, 12e+2]""");
		assertTrue(doc.getContent() instanceof JSONArray);

		EList<JSONValue> arrayElements = assertIsArray(8, doc.getContent()).getElements();

		assertEqualsValue(1, arrayElements.get(0));
		assertEqualsValue("str", arrayElements.get(1));

		JSONObject obj = assertIsObject(arrayElements.get(2));
		JSONValue vVal = assertHasKey(obj, "v");
		assertEqualsValue(1, vVal);

		assertIsArray(0, arrayElements.get(3));

		assertIsNullValue(arrayElements.get(4));
		assertEqualsValue(true, arrayElements.get(5));
		assertEqualsValue(42.42, arrayElements.get(6));
		assertEqualsValue(new BigDecimal(12e2), arrayElements.get(7));
	}

	/** Checks that the parsing of a nested JSON object works as intended. */
	@Test
	public void testNestedObject() throws Exception {
		JSONDocument doc = jsonParserHelper.parseSuccessfully("""
				{
					"a": {
						"b": {
							"c": {
								"d": {
									"e": [1]
								}
							}
						}
					}
				}""");

		JSONObject objA = assertIsObject(doc.getContent());
		JSONObject objB = assertIsObject(assertHasKey(objA, "a"));
		JSONObject objC = assertIsObject(assertHasKey(objB, "b"));
		JSONObject objD = assertIsObject(assertHasKey(objC, "c"));
		JSONObject objE = assertIsObject(assertHasKey(objD, "d"));
		JSONArray arrE = assertIsArray(1, assertHasKey(objE, "e"));

		assertEqualsValue(1, arrE.getElements().get(0));
	}

	/** Checks that the parsing of a nested JSON array works as intended. */
	@Test
	public void testNestedArray() throws Exception {
		JSONDocument doc = jsonParserHelper.parseSuccessfully("""
				[
					[
						[
							[
								[]
							]
						]
					]
				]""");

		JSONArray arr1 = assertIsArray(1, doc.getContent());
		JSONArray arr2 = assertIsArray(1, arr1.getElements().get(0));
		JSONArray arr3 = assertIsArray(1, arr2.getElements().get(0));
		JSONArray arr4 = assertIsArray(1, arr3.getElements().get(0));
		assertIsArray(0, arr4.getElements().get(0));
	}

	/** Checks that the parsing of a simple Node.js package.json file works as intended. */
	@Test
	public void testSimpleNodePackageJson() throws Exception {
		JSONDocument doc = jsonParserHelper.parseSuccessfully("""
				{
				  "name": "test-npm",
				  "version": "1.0.0",
				  "description": "",
				  "main": "index.js",
				  "scripts": {
				    "test": "echo \\"Error: no test specified\\" && exit 1"
				  },
				  "keywords": [],
				  "author": "",
				  "license": "ISC",
				  "dependencies": {
				    "a": "^16.3.2",
				    "b": "^0.3.2"
				  }
				}
				""");

		JSONObject rootObject = assertIsObject(doc.getContent());

		assertEqualsValue("test-npm", assertHasKey(rootObject, "name"));
		assertEqualsValue("1.0.0", assertHasKey(rootObject, "version"));
		assertEqualsValue("", assertHasKey(rootObject, "description"));
		assertEqualsValue("index.js", assertHasKey(rootObject, "main"));

		JSONObject scriptsObject = assertIsObject(assertHasKey(rootObject, "scripts"));
		assertEqualsValue("echo \"Error: no test specified\" && exit 1", assertHasKey(scriptsObject, "test"));

		assertIsArray(0, assertHasKey(rootObject, "keywords"));
		assertEqualsValue("", assertHasKey(rootObject, "author"));
		assertEqualsValue("ISC", assertHasKey(rootObject, "license"));

		JSONObject dependenciesObject = assertIsObject(assertHasKey(rootObject, "dependencies"));
		assertEqualsValue("^16.3.2", assertHasKey(dependenciesObject, "a"));
		assertEqualsValue("^0.3.2", assertHasKey(dependenciesObject, "b"));

	}

	/**  */
	@Test
	public void testUnicodeControlCharacters() throws Exception {
		jsonParserHelper.parseUnsuccessfully("[\"a a\"]");
	}

	/** Asserts that the given {@code actual} JSON value represents a number of value {@code numberValue}. */
	private void assertEqualsValue(long numberValue, JSONValue actual) {
		assertTrue("Value is expected to be a JSONNumericLiteral", actual instanceof JSONNumericLiteral);
		assertEquals(numberValue, ((JSONNumericLiteral) actual).getValue().longValue());
	}

	/** Asserts that the given {@code actual} JSON value represents a number of value {@code numberValue}. */
	private void assertEqualsValue(double numberValue, JSONValue actual) {
		assertTrue("Value is expected to be a JSONNumericLiteral", actual instanceof JSONNumericLiteral);
		assertEquals(numberValue, ((JSONNumericLiteral) actual).getValue().doubleValue(), 0.0);
	}

	/**
	 * Asserts that the given decimal JSON value {@code actual} represents (compareTo == 0) the given
	 * {@code bigDecimalValue}.
	 */
	private void assertEqualsValue(BigDecimal bigDecimalValue, JSONValue actual) {
		assertTrue("Value is expected to be a JSONNumericLiteral", actual instanceof JSONNumericLiteral);
		assertTrue(bigDecimalValue.compareTo(((JSONNumericLiteral) actual).getValue()) == 0);
	}

	/** Asserts that the given {@code actual} JSON value represents a string with value {@code stringValue}. */
	private void assertEqualsValue(String stringValue, JSONValue actual) {
		assertTrue("Value is expected to be a JSONStringLiteral", actual instanceof JSONStringLiteral);
		assertEquals(stringValue, ((JSONStringLiteral) actual).getValue());
	}

	/** Asserts that the given {@code actual} JSON value represents a boolean value with value {@code booleanValue}. */
	private void assertEqualsValue(boolean booleanValue, JSONValue actual) {
		assertTrue("Value is expected to be a JSONBooleanLiteral", actual instanceof JSONBooleanLiteral);
		assertEquals(booleanValue, ((JSONBooleanLiteral) actual).isBooleanValue());
	}

	/** Asserts that the given {@code actual} JSON value represents a null value. */
	private void assertIsNullValue(JSONValue actual) {
		assertTrue("Value is expected to be a JSONNullLiteral", actual instanceof JSONNullLiteral);
	}

	/** Asserts the given {@link JSONValue} is an array of given size and returns its elements. */
	private JSONArray assertIsArray(int size, JSONValue actual) {
		assertTrue("Value is expected to be a JSONArray", actual instanceof JSONArray);
		assertEquals(size, ((JSONArray) actual).getElements().size());
		return (JSONArray) actual;
	}

	/** Asserts the given {@link JSONValue} is an object of given size and returns it. */
	private JSONObject assertIsObject(JSONValue actual) {
		assertTrue("Value is expected to be a JSONObject", actual instanceof JSONObject);
		return (JSONObject) actual;
	}

	/** Asserts that the given {@link JSONObject} has a value for the given key and returns the value. */
	private JSONValue assertHasKey(JSONObject object, String key) {
		Map<String, JSONValue> valuesByName = new HashMap<>();
		for (NameValuePair nvPair : object.getNameValuePairs()) {
			valuesByName.put(nvPair.getName(), nvPair.getValue());
		}
		assertTrue("Object " + object + " is expected to have a value for key " + key,
				valuesByName.keySet().contains(key));
		return valuesByName.get(key);
	}
}
