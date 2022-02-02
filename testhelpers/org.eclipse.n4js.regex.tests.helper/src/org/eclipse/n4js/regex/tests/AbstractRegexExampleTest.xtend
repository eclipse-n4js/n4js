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
package org.eclipse.n4js.regex.tests

import org.junit.Assert
import org.junit.Test

abstract class AbstractRegexExampleTest extends Assert {

	def void assertValid(CharSequence expression)

	@Test
	def void testSampleFromJSREGEXcom() {
		'/[Tt]he\\sr\\w+/'.assertValid
	}

	@Test
	def void testFiveDigits() {
		'/^\\d{5}$/'.assertValid
	}

	@Test
	def void testDigits_01() {
		'/\\d+/g'.assertValid
	}

	@Test
	def void testDigits_02() {
		'/[\\(\\)-]/g'.assertValid
	}

	@Test
	def void testCommaWithWS() {
		'/\\s*,\\s*/'.assertValid
	}

	@Test
	def void testTwoWords() {
		'''/(\w+)\s(\w+)/'''.assertValid
	}

	@Test
	def void testTwoWords_02() {
		'''/(\w.+)\s(\w.+)/'''.assertValid
	}

	@Test
	def void testValidNumber() {
		'''/(^-*\d+$)|(^-*\d+\.\d+$)/'''.assertValid
	}

	@Test
	def void testValidDateFormat() {
		'''/^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/'''.assertValid
	}

	@Test
	def void testHtmlTags() {
		'''/(<)|(>)/g'''.assertValid
	}

	@Test
	def void testSelfhtml() {
		'''/(http:\/\/\S*)/g'''.assertValid
	}

}
