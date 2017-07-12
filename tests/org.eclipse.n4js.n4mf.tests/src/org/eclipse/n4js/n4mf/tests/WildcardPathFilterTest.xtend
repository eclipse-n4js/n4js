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
package org.eclipse.n4js.n4mf.tests

import org.eclipse.n4js.n4mf.N4MFInjectorProvider
import static org.eclipse.n4js.n4mf.validation.WildcardPathFilter.*
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import com.google.common.base.StandardSystemProperty

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4MFInjectorProvider)
class WildcardPathFilterTest {

	@Test
	def testCase1() {
		val matchString = "src/**/juergensHacks/*"
		val absoluteProjectPath = '''«StandardSystemProperty.USER_DIR.value()»«File.separatorChar»testdata''';
		val files = collectAllFilesByWildcardPath(absoluteProjectPath, matchString)
		Assert.assertEquals(2, files.size)
		Assert.assertTrue(files.exists[it.endsWith("A.js")])
		Assert.assertTrue(files.exists[it.endsWith("B.js")])
	}

	@Test
	def testCase2() {
		val matchString = "src/**/juergensHacks"
		val absoluteProjectPath = '''«StandardSystemProperty.USER_DIR.value()»«File.separatorChar»testdata''';
		val folders = collectAllFoldersByWildcardPath(absoluteProjectPath, matchString)
		Assert.assertEquals(1, folders.size)
		Assert.assertTrue(folders.exists[it.endsWith("juergensHacks")])
	}
}
