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
package org.eclipse.n4js.test.helpers.tests

import org.eclipse.n4js.xpect.ui.common.QuickFixTestHelper
import org.junit.Test

import static extension org.junit.Assert.*

/**
 */
class QuickFixTestHelperTest {

	@Test
	def testChangeHelperNoChanges() {
		val String docu = createDocu().toString

		QuickFixTestHelper.extractSingleChangedLine(docu, docu).isEmpty.assertTrue
	}

	@Test
	def testChangeHelperSingelInsertion() {
		val String docu = createDocu().toString
		val what = "xyz"
		val docu2 = docu.insertNewLine(4, what).toString

		val change = QuickFixTestHelper.extractSingleChangedLine(docu, docu2)
		change.isEmpty.assertFalse
		change.isMoreThanOne.assertFalse

		"".assertEquals(change.first.before)
		what.assertEquals(change.first.after)
	}

	@Test
	def testChangeHelperMultipleInsertion() {
		val String docu = createDocu().toString
		val what = "xyz"
		var docu2 = docu.insertNewLine(4, what).toString
		docu2 = docu2.insertNewLine(4, what).toString

		val change = QuickFixTestHelper.extractSingleChangedLine(docu, docu2)
		change.isEmpty.assertFalse
		change.isMoreThanOne.assertTrue

		"4slakdjf".assertEquals(change.first.before)
		what.assertEquals(change.first.after)

	// changes after this are useless and not supported:
	//		"".assertEquals(change.get(1).before)
	//		what.assertEquals(change.get(1).after)
	}

	@Test
	def testChangeHelperLineDeleted() {
		val String docu = createDocu().toString
		val docu2 = docu.remove(0); // first line

		val change = QuickFixTestHelper.extractSingleChangedLine(docu, docu2)
		change.isEmpty.assertFalse
		change.isMoreThanOne.assertFalse
		"0abs".assertEquals(change.first.before)
		"".assertEquals(change.first.after)
	}

	@Test
	def testChangeHelperMultipleLineDeleted() {
		val String docu = createDocu().toString
		var docu2 = docu.remove(5); // lines 5+6
		docu2 = docu2.remove(5);

		val change = QuickFixTestHelper.extractSingleChangedLine(docu, docu2)
		change.isEmpty.assertFalse
		change.isMoreThanOne.assertTrue

		// Since the diff has more then one line, here the result is 'broken':
		"5asdf".assertEquals(change.first.before)
		"7asdf".assertEquals(change.first.after)
		// others are not covered.
	}

	@Test
	def testChangeHelperLineDeleted2() {
		val String docu = createDocu().toString
		val docu2 = docu.remove(5);

		val change = QuickFixTestHelper.extractSingleChangedLine(docu, docu2)
		change.isEmpty.assertFalse
		change.isMoreThanOne.assertFalse
		"5asdf".assertEquals(change.first.before)
		"".assertEquals(change.first.after)
	}

	@Test
	def selfTestLineOffset() {
		val String docu = createDocu.toString
		0.assertEquals(docu.lineOffset(0));
		5.assertEquals(docu.lineOffset(1));
		6.assertEquals(docu.lineOffset(2));
	}

	def insertNewLine(String where, int line, String what) {
		insert(where, where.lineOffset(line), what + '\n')
	}

	def insert(String where, int offset, String what) {
		new StringBuffer(where).insert(offset, what)
	}

	def remove(String where, int line) {
		val start = where.lineOffset(line)
		val end = where.lineOffset(line + 1)

		//		val what = where.substring(start+1, end-start )
		new StringBuffer(where).delete(start, end).toString
	}

	def int lineOffset(String string, int lineNo) {
		var int offset = 0;
		for (var i = 0; i < lineNo; i++) {
			offset = string.indexOf('\n', offset) + 1;
			if (offset === -1) return string.length;
		}
		return offset
	}

	def createDocu() {
			"0abs" + "\n" +
			"\n" +
			"\n" +
			"3cdef" + "\n" +
			"4slakdjf" + "\n" +
			"5asdf" + "\n" +
			"6asdf" + "\n" +
			"7asdf" + "\n" +
			"8a" + "\n" +
			"9sdf" + "\n" +
			"10sad" + "\n" +
			"11fa" + "\n" +
			"12sdf" + "\n" +
			"\n" +
			"14asd" + "\n" +
			"15f"
	}

}
