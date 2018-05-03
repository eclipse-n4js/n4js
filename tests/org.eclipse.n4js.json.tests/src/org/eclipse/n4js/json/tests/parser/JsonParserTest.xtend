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
import java.util.List
import org.eclipse.n4js.json.JSON.JSONArray
import org.eclipse.n4js.json.JSON.JSONBooleanLiteral
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONNullLiteral
import org.eclipse.n4js.json.JSON.JSONNumericLiteral
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.JSON.JSONStringLiteral
import org.eclipse.n4js.json.JSON.JSONValue
import org.eclipse.n4js.json.JSONInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Tests for parsing JSON files.
 */
@RunWith(XtextRunner)
@InjectWith(JSONInjectorProvider)
class JsonParserTest {

	@Inject extension ParseHelper<JSONDocument>

	/** Checks that all supported types of value literals are parsed correctly. */
	@Test def void testPlainValues() {
		''''''.parseSuccessfully // empty document
		''' 	 '''.parseSuccessfully // whitespace document
		
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
		
		'''-4'''.parseSuccessfully
		'''-4.2'''.parseSuccessfully
		'''-31.42'''.parseSuccessfully
		'''-42e+42'''.parseSuccessfully
		'''-42E-42'''.parseSuccessfully
		
		assertEqualsValue(new BigDecimal("-0"), '''-0'''.parseSuccessfully.content);
	}
	
	/** Checks that various different variations of string literals are parsed correctly. */
	@Test def void testStringValues() {
		'''""'''.parseSuccessfully
		'''"   "'''.parseSuccessfully
	}
	
	/** Checks that an array of numeric literals is parsed correctly. */
	@Test def void testNumericArray() {
		val doc = '''[1, 2, 3]'''.parseSuccessfully;
		assertTrue(doc.content instanceof JSONArray);
		
		val array = assertIsArray(3, doc.content);

		assertEqualsValue(1, array.get(0));
		assertEqualsValue(2, array.get(1));
		assertEqualsValue(3, array.get(2));
	}
	
	/** Checks that an array of mixed values is parsed correctly. */
	@Test def void testMixedArray() {
		val doc = '''[1, "str", {"v" : 1}, [], null, true, 42.42, 12e+2]'''.parseSuccessfully;
		assertTrue(doc.content instanceof JSONArray);
		
		val array = assertIsArray(8, doc.content);
		
		assertEqualsValue(1, array.get(0));
		assertEqualsValue("str", array.get(1));
		
		val obj = assertIsObject(array.get(2));
		val vVal = assertHasKey(obj, "v");
		assertEqualsValue(1, vVal);
		
		assertIsArray(0, array.get(3));
		
		assertIsNullValue(array.get(4));
		assertEqualsValue(true, array.get(5));
		assertEqualsValue(42.42, array.get(6));
		assertEqualsValue(new BigDecimal(12e2), array.get(7));
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
	private def List<JSONValue> assertIsArray(int size, JSONValue actual) {
		assertTrue("Value is expected to be a JSONArray", actual instanceof JSONArray);
		assertEquals(size, (actual as JSONArray).elements.size);
		return (actual as JSONArray).elements;
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
	
	/** 
	 * Asserts that the given {@code json} character sequence can be parsed correctly. Returns the
	 * resulting {@link JSONDocument} instance.
	 */
	protected def JSONDocument parseSuccessfully(CharSequence json) {
		val doc = json.parse;
		assertTrue('''"«json»" ''' + doc.eResource.errors.join('\n')[line + ': ' + message], doc.eResource.errors.empty)
		return doc
	}
}
