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
import org.eclipse.n4js.n4mf.VersionConstraint
import org.eclipse.n4js.n4mf.utils.parsing.ManifestValuesParsingUtil
import java.util.List
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.eclipse.n4js.external.version.VersionConstraintFormatUtil.npmFormat
import static org.junit.Assert.*

import static extension org.eclipse.n4js.n4mf.utils.parsing.ManifestValuesParsingUtil.parseDependency

/**
 * Tests conversion to npm version format done by {@link VersionConstraintFormatUtil}.
 * Checks are performed for the conversion of {@link VersionConstraint}. To obtain its instances this tests follow path of parsing
 * manifest fragments. This is similar what N4JS Quick Fix for manifest is doing is doing to obtain data from manifest content.
 * Effectively those tests are also testing some parts of the {@link ManifestValuesParsingUtil}
 * but it is not focused on it (i.e. not all its execution paths might be tested here).
 *
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class VersionConversionFromManifestDataTest {

	@Test def convertRangeInclusive() {
		'''sax@">=1.0.0 <=3.0.0"'''.fromDependency('''sax [1.0.0, 3.0.0]''');
	}

	@Test def convertRangeExclusive() {
		'''sax@">1.0.0 <3.0.0"'''.fromDependency('''sax (1.0.0, 3.0.0)''');
	}

	@Test def convertPartial2() {
		'''sax@">1.0.0 <3.0.0"'''.fromDependency('''sax (1.0, 3.0)''');
	}

	@Test def convertPartial1() {
		'''sax@">1.0.0 <3.0.0"'''.fromDependency('''sax (1, 3)''');
	}

	@Test def convertWithQualifiers() {
		'''sax@">1.0.0-alpha <3.0.0-beta"'''.fromDependency('''sax (1.0.0-alpha, 3.0.0-beta)''');
	}

	@Test def parseWithMeta() {
		'''sax@">1.0.0-betaexp.sha.5114f85 <3.0.0-alphaexp.sha.e134a85"'''.fromDependency('''sax (1.0.0-betaexp.sha.5114f85, 3.0.0-alphaexp.sha.e134a85)''');
	}

	@Test def parseErrors() {
		#["no viable alternative at input 'beta'"].errors('''sax (1.0.0-alpha beta, 3.0.0-beta gamma)''');
	}

	@Test def parseErrors2() {
		#["no viable alternative at input '@'"].errors('''sax@">1.0.0-alpha <3.0.0-beta"''');
	}

////////////////////////////////////////////////////////////////////////
// test utils
	private def fromDependency(CharSequence expected, CharSequence input) {
		val dependency = input.toString.parseDependency.getAST;
		val packageName = dependency.project.projectId;
		val packageVersion = npmFormat(dependency.versionConstraint)
		assertEquals(expected.toString, packageName + packageVersion)
	}

	private def errors(List<String> expected, CharSequence input) {
		assertEquals(String.join(",", expected), String.join(",", input.toString.parseDependency.errors))
	}
}
