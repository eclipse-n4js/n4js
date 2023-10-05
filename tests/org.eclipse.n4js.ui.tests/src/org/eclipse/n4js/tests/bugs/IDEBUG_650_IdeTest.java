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
package org.eclipse.n4js.tests.bugs;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.junit.Test;

import com.google.common.base.Strings;
import com.google.common.io.Files;

/**
 * Test for checking that the correctly polyfilled non-accessor members will not have
 * auto-generated{@code API not implemented yet} stubs.
 */
public class IDEBUG_650_IdeTest extends ConvertedIdeTest {

	static Pattern PATTERN = Pattern.compile("static\\s+system\\s*\\(\\s*\\)\\s*\\{");

	@Test
	public void buildCheckStubsNotGeneratedForPolyfilledMember_AssertGeneratedOnce() throws IOException {
		importProband(new File("probands", "IDEBUG_650"));
		assertNoIssues();

		File project = getProjectRootForImportedProject("A");
		File file = new File(project, "src-gen/n4/model/common/TimezoneRegion.js");
		assertTrue("TimezoneRegion.js compiled file does not exist.", file.exists());

		String actualContent = Files.asCharSource(file, Charset.defaultCharset()).read();
		assertTrue("Generated file content was empty: " + file, !Strings.isNullOrEmpty(actualContent));
		Matcher matcher = PATTERN.matcher(actualContent);
		int matchCount = 0;
		while (matcher.find()) {
			matchCount++;
		}
		assertTrue("Expected exactly one occurrence of the generated member. Got " + matchCount + " instead.",
				1 == matchCount);
	}
}
