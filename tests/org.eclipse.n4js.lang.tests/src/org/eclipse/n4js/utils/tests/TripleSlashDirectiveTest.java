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
package org.eclipse.n4js.utils.tests;

import org.eclipse.n4js.dts.utils.TripleSlashDirective;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

/**
 * Tests for {@link TripleSlashDirective}s.
 */
public class TripleSlashDirectiveTest {

	@Test
	public void testNoAttr() {
		assertTSD("<name/>", "name");
	}

	@Test
	public void testNoAttr_whiteSpace() {
		assertTSD("<   name  />", "name");
	}

	@Test
	public void testAttr_emptyValue() {
		assertTSD("<name attr=\"\"/>", "name", "attr", "");
	}

	@Test
	public void testAttr_nonEmptyValue() {
		assertTSD("<name attr=\"value\"/>", "name", "attr", "value");
	}

	@Test
	public void testAttr_whiteSpace() {
		assertTSD("<  name   attr  =  \" some value \"  />", "name", "attr", " some value ");
	}

	@Test
	public void testAttr_singleQuotes() {
		assertTSD("<name attr='value'/>", "name", "attr", "value");
	}

	@Test
	public void testFailed01() {
		Assert.assertNull(TripleSlashDirective.parse("< />"));
	}

	@Test
	public void testFailed02() {
		Assert.assertNull(TripleSlashDirective.parse("<name attr/>"));
	}

	@Test
	public void testFailed03() {
		Assert.assertNull(TripleSlashDirective.parse("<name attr=/>"));
	}

	@Test
	public void testFailed04() {
		Assert.assertNull(TripleSlashDirective.parse("<attr=\"value\"/>"));
	}

	@Test
	public void testFailed05() {
		Assert.assertNull(TripleSlashDirective.parse("<name attr='value\"/>"));
	}

	@Test
	public void testRealWorld_amdModule() {
		assertTSD("<amd-module/>", "amd-module");
	}

	@Test
	public void testRealWorld_amdDependency() {
		assertTSD("<amd-dependency/>", "amd-dependency");
	}

	@Test
	public void testRealWorld_referencePath() {
		assertTSD("<reference path=\"./../a/b/c/module.js\"/>", "reference", "path", "./../a/b/c/module.js");
	}

	@Test
	public void testRealWorld_referenceTypes() {
		assertTSD("<reference types=\"./../a/b/c/module.d.ts\"/>", "reference", "types", "./../a/b/c/module.d.ts");
	}

	@Test
	public void testRealWorld_referenceLib() {
		assertTSD("<reference lib=\"es2017.string\"/>", "reference", "lib", "es2017.string");
	}

	@Test
	public void testRealWorld_referenceNoDefaultLib() {
		assertTSD("<reference no-default-lib=\"true\"/>", "reference", "no-default-lib", "true");
	}

	private void assertTSD(CharSequence cs, String expectedName) {
		assertTSD(cs, expectedName, Optional.absent());
	}

	private void assertTSD(CharSequence cs, String expectedName, String expectedAttrName, String expectedAttrValue) {
		assertTSD(cs, expectedName, Optional.of(Pair.of(expectedAttrName, expectedAttrValue)));
	}

	private void assertTSD(CharSequence cs, String expectedName, Optional<Pair<String, String>> expectedAttr) {
		TripleSlashDirective tsd = TripleSlashDirective.parse(cs.toString());
		Assert.assertEquals(expectedName, tsd.name);
		Assert.assertEquals(expectedAttr, tsd.attr);
	}
}
