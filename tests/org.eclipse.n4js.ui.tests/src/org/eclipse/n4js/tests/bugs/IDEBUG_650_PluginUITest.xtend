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
package org.eclipse.n4js.tests.bugs

import com.google.common.io.Files
import java.io.File
import java.nio.charset.Charset
import java.util.regex.Pattern
import org.eclipse.core.resources.IFile
import org.eclipse.core.resources.IProject
import org.eclipse.core.runtime.CoreException
import org.junit.Test

import static org.eclipse.core.resources.ResourcesPlugin.getWorkspace
import static org.eclipse.n4js.N4JSLanguageConstants.METHOD_STACKTRACE_SUFFIX
import static org.eclipse.xtext.ui.testing.util.IResourcesSetupUtil.fullBuild
import static org.junit.Assert.*

/**
 * Test for checking that the correctly polyfilled non-accessor members will not have auto-generated{@code API not implemented yet} stubs.
 */
@SuppressWarnings("deprecation")
class IDEBUG_650_PluginUITest extends AbstractIDEBUG_Test {

	static val PATTERN = Pattern.compile('''system:\s+[{]\s+value:\s+function\s+system«METHOD_STACKTRACE_SUFFIX»\s*[(][)]''');

	@Test
	def void buildCheckStubsNotGeneratedForPolyfilledMember_AssertGeneratedOnce() throws CoreException {
		LOGGER.info('Running full clean build for all projects...');
		fullBuild();
		LOGGER.info('Full clean build successfully completed.');
		val IProject project = getWorkspace.root.getProject('A');
		val IFile file = project.
			getFile('''src-gen/n4/model/common/TimezoneRegion.js''');
		assertTrue('TimezoneRegion.js compiled file does not exist.', file.exists);

		val actualContent = Files.asCharSource(file.location.toFile, Charset.defaultCharset()).read();
		assertTrue('Generated file content was empty: ' + file, !actualContent.nullOrEmpty)
		val matcher = PATTERN.matcher(actualContent);
		var matchCount = 0;
		while (matcher.find) {
			matchCount++;
		}
		assertTrue('Expected exactly one occurrence of the generated member. Got ' + matchCount + ' instead.', 1 == matchCount);
	}

	override protected ProjectImporter getProjectImporter() {
		return new ProjectImporter(new File(new File('probands/IDEBUG_650/').absolutePath));
	}

}
