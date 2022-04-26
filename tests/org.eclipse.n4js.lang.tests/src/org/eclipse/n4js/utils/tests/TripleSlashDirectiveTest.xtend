/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.tests

import com.google.common.base.Optional
import org.eclipse.n4js.dts.utils.TripleSlashDirective
import org.junit.Assert
import org.junit.Test

/**
 * Tests for {@link TripleSlashDirective}s.
 */
class TripleSlashDirectiveTest {

	@Test
	def void testNoAttr() {
		'''<name/>'''.assertTSD("name");
	}

	@Test
	def void testNoAttr_whiteSpace() {
		'''<   name  />'''.assertTSD("name");
	}

	@Test
	def void testAttr_emptyValue() {
		'''<name attr=""/>'''.assertTSD("name", "attr", "");
	}

	@Test
	def void testAttr_nonEmptyValue() {
		'''<name attr="value"/>'''.assertTSD("name", "attr", "value");
	}

	@Test
	def void testAttr_whiteSpace() {
		'''<  name   attr  =  " some value "  />'''.assertTSD("name", "attr", " some value ");
	}

	@Test
	def void testAttr_singleQuotes() {
		'''<name attr='value'/>'''.assertTSD("name", "attr", "value");
	}

	@Test
	def void testFailed01() {
		Assert.assertNull(TripleSlashDirective.parse('''< />'''));
	}

	@Test
	def void testFailed02() {
		Assert.assertNull(TripleSlashDirective.parse('''<name attr/>'''));
	}

	@Test
	def void testFailed03() {
		Assert.assertNull(TripleSlashDirective.parse('''<name attr=/>'''));
	}

	@Test
	def void testFailed04() {
		Assert.assertNull(TripleSlashDirective.parse('''<attr="value"/>'''));
	}

	@Test
	def void testFailed05() {
		Assert.assertNull(TripleSlashDirective.parse('''<name attr='value"/>'''));
	}

	@Test
	def void testRealWorld_amdModule() {
		'''<amd-module/>'''.assertTSD("amd-module");
	}

	@Test
	def void testRealWorld_amdDependency() {
		'''<amd-dependency/>'''.assertTSD("amd-dependency");
	}

	@Test
	def void testRealWorld_referencePath() {
		'''<reference path="./../a/b/c/module.js"/>'''.assertTSD("reference", "path", "./../a/b/c/module.js");
	}

	@Test
	def void testRealWorld_referenceTypes() {
		'''<reference types="./../a/b/c/module.d.ts"/>'''.assertTSD("reference", "types", "./../a/b/c/module.d.ts");
	}

	@Test
	def void testRealWorld_referenceLib() {
		'''<reference lib="es2017.string"/>'''.assertTSD("reference", "lib", "es2017.string");
	}

	@Test
	def void testRealWorld_referenceNoDefaultLib() {
		'''<reference no-default-lib="true"/>'''.assertTSD("reference", "no-default-lib", "true");
	}

	def private void assertTSD(CharSequence cs, String expectedName) {
		assertTSD(cs, expectedName, Optional.absent);
	}

	def private void assertTSD(CharSequence cs, String expectedName, String expectedAttrName, String expectedAttrValue) {
		assertTSD(cs, expectedName, Optional.of(expectedAttrName -> expectedAttrValue));
	}

	def private void assertTSD(CharSequence cs, String expectedName, Optional<Pair<String, String>> expectedAttr) {
		val tsd = TripleSlashDirective.parse(cs.toString);
		Assert.assertEquals(expectedName, tsd.name);
		Assert.assertEquals(expectedAttr, tsd.attr);
	}
}
