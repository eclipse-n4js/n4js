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
package org.eclipse.n4js.csv;

import java.util.Iterator
import java.util.List
import org.junit.Test

import static org.junit.Assert.*

/**
 * A few test cases for the CSV parser.
 */
public class CSVParserTest {

	private static def void assertResult(List<List<String>> expected, CSVData actual) {
		var Iterator<List<String>> expectedIt = expected.iterator();
		var Iterator<CSVRecord> actualIt = actual.iterator();

		while (expectedIt.hasNext() || actualIt.hasNext()) {
			assertEquals(expectedIt.hasNext(), actualIt.hasNext());
			val List<String> expectedRecord = expectedIt.next();
			val CSVRecord actualRecord = actualIt.next();
			assertRecords(expectedRecord, actualRecord);
		}
	}

	private static def void assertRecords(Iterable<String> expected, Iterable<String> actual) {
		var Iterator<String> expectedIt = expected.iterator();
		var Iterator<String> actualIt = actual.iterator();

		while (expectedIt.hasNext() && actualIt.hasNext()) {
			assertEquals(expectedIt.hasNext(), actualIt.hasNext());
			assertEquals(expectedIt.next(), actualIt.next());
		}
	}

	@Test
	public def void testWindowsLineEndings() {
		assertResult(#[
			#["1", "2", "3"],
			#["1", "2", "3"]
		],
		CSVParser.parse("1,2,3\r\n1,2,3"));

		assertResult(#[
			#["1", "2", ""],
			#["1", "2", "3"]
		],
		CSVParser.parse("1,2,\r\n1,2,3"));

		assertResult(#[
			#["1", "2", ""],
			#["", "2", "3"]
		],
		CSVParser.parse("1,2,\r\n,2,3"));

		assertResult(#[
			#["", "", "Supplier", "Class","",""],
			#["", "", "Subject of Access", "Field, Accessor, Method", "", "Static Field, Getter, Setter, Method", "", ""]
		],
		CSVParser.parse(",,Supplier,Class,,\r\n,,Subject of Access,\"Field, Accessor, Method\",,\"Static Field, Getter, Setter, Method\",,\r\n"));
	}

	@Test
	public def void testEmptyString() {
		assertResult(#[], CSVParser.parse(""));
	}

	@Test
	public def void testOneEmptyField() {
		assertResult(#[
			#[""]
		], CSVParser.parse("\n"));
	}

	@Test
	public def void testTwoRowsWithOneFieldEach() {
		assertResult(#[
			#[""],
			#[""]
		], CSVParser.parse("\n\n"));
	}

	@Test
	public def void testOneRowWithThreeFields() {
		assertResult(#[
			#["One", "Two", "Three"]
		], CSVParser.parse('''
		One,Two,Three
		'''));
	}

	@Test
	public def void testOneRowWithThreeEmptyFields() {
		assertResult(#[
			#["", "", ""]
		], CSVParser.parse('''
		,,
		'''));
	}

	@Test
	public def void testOneRowWithSomeEmptyFields() {
		assertResult(#[
			#["One", "", "", ""]
		],
		CSVParser.parse('''
		One,,,
		'''));

		assertResult(#[
			#["", "One", "", ""]
		],
		CSVParser.parse('''
		,One,,
		'''));

		assertResult(#[
			#["", "", "One", ""]
		],
		CSVParser.parse('''
		,,One,
		'''));

		assertResult(#[
			#["", "", "", "One"]
		],
		CSVParser.parse('''
		,,,One
		'''));

		assertResult(#[
			#["One", "", "Two", ""]
		],
		CSVParser.parse('''
		One,,Two,
		'''));

		assertResult(#[
			#["One", "", "", "Two"]
		],
		CSVParser.parse('''
		One,,,Two
		'''));

		assertResult(#[
			#["", "One", "Two", ""]
		],
		CSVParser.parse('''
		,One,Two,
		'''));
	}

	@Test
	public def void testSeveralRowsOfSameLength() {
		assertResult(#[
			#["One", "Two", "Three", "Four"],
			#["Five", "Six", "Seven", "Eight"]
		],
		CSVParser.parse('''
		One,Two,Three,Four
		Five,Six,Seven,Eight
		'''));

		assertResult(#[
			#["", "Two", "Three", ""],
			#["", "Six", "Seven", ""]
		],
		CSVParser.parse('''
		,Two,Three,
		,Six,Seven,
		'''));

		assertResult(#[
			#["One", "Two", "Three", "Four"],
			#["", "", "", ""],
			#["Five", "Six", "Seven", "Eight"],
			#["", "", "", ""]
		],
		CSVParser.parse('''
		One,Two,Three,Four
		,,,
		Five,Six,Seven,Eight
		,,,
		'''));
	}

	@Test
	public def void testSeveralRowsOfDifferingLengths() {
		assertResult(#[
			#["One", "Two", "Three"],
			#["Five", "Six", "Seven", "Eight"]
		],
		CSVParser.parse('''
		One,Two,Three
		Five,Six,Seven,Eight
		'''));

		assertResult(#[
			#[""],
			#["Five", "Six", "Seven", "Eight"]
		],
		CSVParser.parse('''

		Five,Six,Seven,Eight
		'''));

		assertResult(#[
			#["Five", "Six", "Seven", "Eight"],
			#[""]
		],
		CSVParser.parse('''
		Five,Six,Seven,Eight

		'''));

		assertResult(#[
			#["Five", "Six", "Seven", "Eight"],
			#[""],
			#["", "", ""]
		],
		CSVParser.parse('''
		Five,Six,Seven,Eight

		,,
		'''));

		assertResult(#[
			#["One", "Two", "Three"],
			#[""],
			#["", ""],
			#[""],
			#["Five", "Six", "Seven", "Eight"]
		],
		CSVParser.parse('''
		One,Two,Three

		,

		Five,Six,Seven,Eight
		'''));
	}


	@Test
	public def void testFieldsWithControlChars() {
		assertResult(#[
			#["One,Two, Three"],
			#["Five", "Six", "Seven", "Eight"]
		],
		CSVParser.parse('''
		"One,Two, Three"
		Five,Six,Seven,Eight
		'''));

		assertResult(#[
			#["One,Two, \n\nThree"],
			#["\n"],
			#["Five", "Six", "Seven", "Eight"]
		],
		CSVParser.parse('''
		"One,Two,

		Three"
		"
		"
		Five,Six,Seven,Eight
		'''));

		assertResult(#[
			#["\"This is a quote!\"", "This isn't", "This is not, either"],
			#["Five", "Six", "Seven", "Eight"]
		],
		CSVParser.parse('''
		"""This is a quote!""",This isn't,"This is not, either"
		Five,Six,Seven,Eight
		'''));
	}
}
