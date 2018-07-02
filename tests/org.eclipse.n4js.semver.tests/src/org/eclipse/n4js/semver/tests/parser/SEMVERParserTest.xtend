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
package org.eclipse.n4js.semver.tests.parser

import com.google.inject.Inject
import org.eclipse.n4js.semver.SEMVERInjectorProvider
import org.eclipse.n4js.semver.SEMVERParseHelper
import org.eclipse.xtext.serializer.ISerializer
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

/**
 * Tests for parsing SEMVER files.
 */
@RunWith(XtextRunner)
@InjectWith(SEMVERInjectorProvider)
class SEMVERParserTest {

	@Inject extension SEMVERParseHelper;
	@Inject ISerializer serializer;

	String[] data = #[
		"",
		" 1",
		" 1 ",
		"1 ",
		"v1.2",
		"1.2.3",
		"2.3.4",
		"0.2.3",
		"<2.0.0",
		">=1.2.7",
		"1.2.7",
		"1.2.8",
		"2.5.3",
		"1.3.9",
		"1.2.6",
		"1.1.0",
		">=1.2.7 <1.3.0",
		"1.2.7 || >=1.2.9 <2.0.0",
		"~1.2.3-alpha.4",
		">1.2.3-alpha.4",
		"1.2.3-alpha.7",
		"3.4.5-alpha.9",
		"1.2.4-beta.0",
		"1.2.4-beta.1",
		"1.2.3 - 2.3.4",
		">=1.2.3 <=2.3.4",
		"1.2 - 2.3.4",
		">=1.2.0 <=2.3.4",
		"1.2.3 - 2.3",
		">=1.2.3 <2.4.0",
		"1.2.3 - 2",
		">=1.2.3 <3.0.0",
		"1.2.x",
		"1.X",
		"1.2.*",
		"*",
		">=0.0.0",
		"1.x",
		">=1.0.0 <2.0.0",
		"1.2.x",
		">=1.2.0 <1.3.0",
		">=0.0.0",
		"1",
		"1.x.x",
		">=1.0.0 <2.0.0",
		"1.2",
		"1.2.x",
		">=1.2.0 <1.3.0",
		"~1.2.3 ",
		"~1.2",
		"~1",
		"~1.2.3",
		">=1.2.3 <1.3.0",
		"~1.2",
		">=1.2.0 <1.3.0",
		"~1",
		">=1.0.0 <1.0.0",
		">=1.0.0 <2.0.0",
		"~0.2.3",
		">=0.2.3 <0.3.0",
		"~0.2",
		">=0.2.0 <0.3.0",
		"~0",
		">=0.0.0 <1.0.0",
		"~1.2.3-beta.2",
		">=1.2.3-beta.2 <1.3.0",
		"1.2.3-beta.4",
		"1.2.4-beta.2",
		"^1.2.3",
		"^0.2.5",
		"^0.0.4",
		"0.X >=0.1.0",
		"0.0.X",
		"^1.2.3",
		">=1.2.3 <2.0.0",
		"^0.2.3",
		">=0.2.3 <0.3.0",
		"^0.0.3",
		">=0.0.3 <0.0.4",
		"^1.2.3-beta.2",
		">=1.2.3-beta.2 <2.0.0",
		"1.2.3-beta.4",
		"1.2.4-beta.2",
		"^0.0.3-beta",
		">=0.0.3-beta <0.0.4",
		"0.0.3-pr.2",
		"^1.2.x",
		">=1.2.0 <2.0.0",
		"^0.0.x",
		">=0.0.0 <0.1.0",
		"^0.0",
		">=0.0.0 <0.1.0",
		"^1.x",
		">=1.0.0 <2.0.0",
		"^0.x",
		">=0.0.0 <1.0.0"
	];

	/** Checks empty strings. */
	@Test
	def void testEmtyStrings() {
		''''''.parseSuccessfully // empty document
		'''	 '''.parseSuccessfully // whitespace document
		'''		'''.parseSuccessfully // whitespace document
	}

	/** Checks a range. */
	@Test
	def void testParseAndToString() {
		for (String entry : data) {
			val versionRangeSet = entry.parseSuccessfully // empty document
			assertTrue(versionRangeSet !== null);
			val serialized = serializer.serialize(versionRangeSet);
			assertEquals(entry.trim, serialized);
		}
	}

}
