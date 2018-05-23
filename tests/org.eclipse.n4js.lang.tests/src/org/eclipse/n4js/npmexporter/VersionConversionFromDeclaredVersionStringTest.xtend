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
package org.eclipse.n4js.npmexporter

import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.external.version.VersionConstraintFormatUtil
import org.eclipse.n4js.n4mf.DeclaredVersion
import org.eclipse.n4js.n4mf.utils.parsing.ManifestValuesParsingUtil
import java.util.List
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

import static extension org.eclipse.n4js.external.version.VersionConstraintFormatUtil.npmFormat
import static extension org.eclipse.n4js.n4mf.utils.parsing.ManifestValuesParsingUtil.parseDeclaredVersion

/**
 * Tests conversion to npm version format done by {@link VersionConstraintFormatUtil}.
 * Checks are performed for the conversion of {@link DeclaredVersion}. To obtain its instances this tests follow path of parsing
 * manifest fragments. This is similar what library manager UI is doing to validate user input.
 * Effectively those tests are also testing some parts of the {@link ManifestValuesParsingUtil}
 * but it is not focused on it (i.e. not all its execution paths might be tested here).
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class VersionConversionFromDeclaredVersionStringTest {

	@Test def convertBasic() {
		'''@1.2.3'''.from('''1.2.3''');
	}

	@Test def convertPartial2() {
		'''@1.2.0'''.from('''1.2''');
	}

	@Test def convertPartial1() {
		'''@1.0.0'''.from('''1''');
	}

	@Test def convertNull() {
		''''''.from('''«null»''');
	}

	@Test def convertWithQualifier() {
		'''@1.2.3-alpha'''.from('''1.2.3-alpha''');
	}

	@Test def parseErrorInt() {
		#["mismatched input 'two.3' expecting RULE_INT"].errors('''1.two.3''');
	}

	@Test def parseErrorGarbage() {
		#["mismatched input 'one.two.three' expecting RULE_INT"].errors('''one.two.three''');
	}

////////////////////////////////////////////////////////////////////////
// test utils
	private def from(CharSequence expected, CharSequence input) {
		assertEquals(expected.toString, input.toString.parseDeclaredVersion.getAST.npmFormat)
	}

	private def errors(List<String> expected, CharSequence input) {
		assertEquals(String.join(",", expected), String.join(",", input.toString.parseDeclaredVersion.errors))
	}
}
