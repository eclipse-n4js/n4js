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

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.n4js.utils.SimpleParserException;
import org.junit.Test;

/**
 * A few test cases for the CSV parser.
 */
public class CSVParserTest {

	private static void assertResult(List<List<String>> expected, CSVData actual) {
		Iterator<List<String>> expectedIt = expected.iterator();
		Iterator<CSVRecord> actualIt = actual.iterator();

		while (expectedIt.hasNext() || actualIt.hasNext()) {
			assertEquals(expectedIt.hasNext(), actualIt.hasNext());
			List<String> expectedRecord = expectedIt.next();
			CSVRecord actualRecord = actualIt.next();
			assertRecords(expectedRecord, actualRecord);
		}
	}

	private static void assertRecords(Iterable<String> expected, Iterable<String> actual) {
		Iterator<String> expectedIt = expected.iterator();
		Iterator<String> actualIt = actual.iterator();

		while (expectedIt.hasNext() && actualIt.hasNext()) {
			assertEquals(expectedIt.hasNext(), actualIt.hasNext());
			assertEquals(expectedIt.next(), actualIt.next());
		}
	}

	/***/
	@Test
	public void testWindowsLineEndings() throws SimpleParserException {
		assertResult(List.of(
				List.of("1", "2", "3"),
				List.of("1", "2", "3")),
				CSVParser.parse("1,2,3\r\n1,2,3"));

		assertResult(List.of(
				List.of("1", "2", ""),
				List.of("1", "2", "3")),
				CSVParser.parse("1,2,\r\n1,2,3"));

		assertResult(List.of(
				List.of("1", "2", ""),
				List.of("", "2", "3")),
				CSVParser.parse("1,2,\r\n,2,3"));

		assertResult(List.of(
				List.of("", "", "Supplier", "Class", "", ""),
				List.of("", "", "Subject of Access", "Field, Accessor, Method", "",
						"Static Field, Getter, Setter, Method", "", "")),
				CSVParser.parse(
						",,Supplier,Class,,\r\n,,Subject of Access,\"Field, Accessor, Method\",,\"Static Field, Getter, Setter, Method\",,\r\n"));
	}

	/***/
	@Test
	public void testEmptyString() throws SimpleParserException {
		assertResult(Collections.emptyList(), CSVParser.parse(""));
	}

	/***/
	@Test
	public void testOneEmptyField() throws SimpleParserException {
		assertResult(List.of(
				List.of("")), CSVParser.parse("\n"));
	}

	/***/
	@Test
	public void testTwoRowsWithOneFieldEach() throws SimpleParserException {
		assertResult(List.of(
				List.of(""),
				List.of("")), CSVParser.parse("\n\n"));
	}

	/***/
	@Test
	public void testOneRowWithThreeFields() throws SimpleParserException {
		assertResult(List.of(
				List.of("One", "Two", "Three")), CSVParser.parse("""
						One,Two,Three
						"""));
	}

	/***/
	@Test
	public void testOneRowWithThreeEmptyFields() throws SimpleParserException {
		assertResult(List.of(
				List.of("", "", "")), CSVParser.parse("""
						,,
						"""));
	}

	/***/
	@Test
	public void testOneRowWithSomeEmptyFields() throws SimpleParserException {
		assertResult(List.of(
				List.of("One", "", "", "")),
				CSVParser.parse("""
						One,,,
						"""));

		assertResult(List.of(
				List.of("", "One", "", "")),
				CSVParser.parse("""
						,One,,
						"""));

		assertResult(List.of(
				List.of("", "", "One", "")),
				CSVParser.parse("""
						,,One,
						"""));

		assertResult(List.of(
				List.of("", "", "", "One")),
				CSVParser.parse("""
						,,,One
						"""));

		assertResult(List.of(
				List.of("One", "", "Two", "")),
				CSVParser.parse("""
						One,,Two,
						"""));

		assertResult(List.of(
				List.of("One", "", "", "Two")),
				CSVParser.parse("""
						One,,,Two
						"""));

		assertResult(List.of(
				List.of("", "One", "Two", "")),
				CSVParser.parse("""
						,One,Two,
						"""));
	}

	/***/
	@Test
	public void testSeveralRowsOfSameLength() throws SimpleParserException {
		assertResult(List.of(
				List.of("One", "Two", "Three", "Four"),
				List.of("Five", "Six", "Seven", "Eight")),
				CSVParser.parse("""
						One,Two,Three,Four
						Five,Six,Seven,Eight
						"""));

		assertResult(List.of(
				List.of("", "Two", "Three", ""),
				List.of("", "Six", "Seven", "")),
				CSVParser.parse("""
						,Two,Three,
						,Six,Seven,
						"""));

		assertResult(List.of(
				List.of("One", "Two", "Three", "Four"),
				List.of("", "", "", ""),
				List.of("Five", "Six", "Seven", "Eight"),
				List.of("", "", "", "")),
				CSVParser.parse("""
						One,Two,Three,Four
						,,,
						Five,Six,Seven,Eight
						,,,
						"""));
	}

	/***/
	@Test
	public void testSeveralRowsOfDifferingLengths() throws SimpleParserException {
		assertResult(List.of(
				List.of("One", "Two", "Three"),
				List.of("Five", "Six", "Seven", "Eight")),
				CSVParser.parse("""
						One,Two,Three
						Five,Six,Seven,Eight
						"""));

		assertResult(List.of(
				List.of(""),
				List.of("Five", "Six", "Seven", "Eight")),
				CSVParser.parse("""

						Five,Six,Seven,Eight
						"""));

		assertResult(List.of(
				List.of("Five", "Six", "Seven", "Eight"),
				List.of("")),
				CSVParser.parse("""
						Five,Six,Seven,Eight

						"""));

		assertResult(List.of(
				List.of("Five", "Six", "Seven", "Eight"),
				List.of(""),
				List.of("", "", "")),
				CSVParser.parse("""
						Five,Six,Seven,Eight

						,,
						"""));

		assertResult(List.of(
				List.of("One", "Two", "Three"),
				List.of(""),
				List.of("", ""),
				List.of(""),
				List.of("Five", "Six", "Seven", "Eight")),
				CSVParser.parse("""
						One,Two,Three

						,

						Five,Six,Seven,Eight
						"""));
	}

	/***/
	@Test
	public void testFieldsWithControlChars() throws SimpleParserException {
		assertResult(List.of(
				List.of("One,Two, Three"),
				List.of("Five", "Six", "Seven", "Eight")),
				CSVParser.parse("""
						"One,Two, Three"
						Five,Six,Seven,Eight
						"""));

		assertResult(List.of(
				List.of("One, Two,\n\nThree"),
				List.of("\n"),
				List.of("Five", "Six", "Seven", "Eight")),
				CSVParser.parse("""
						"One, Two,

						Three"
						"
						"
						Five,Six,Seven,Eight
						"""));

		assertResult(List.of(
				List.of("\"This is a quote!\"", "This isn't", "This is not, either"),
				List.of("Five", "Six", "Seven", "Eight")),
				CSVParser.parse("""
						\"\"\"This is a quote!\"\"\",This isn't,"This is not, either"
						Five,Six,Seven,Eight
						"""));
	}
}
