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
package org.eclipse.n4js.tests.realworld;

import com.google.common.collect.FluentIterable
import java.io.File
import java.util.Collections
import org.eclipse.n4js.tests.utils.ConvertedIdeTest
import org.eclipse.n4js.validation.IssueCodes
import org.junit.Test

/**
 * Tests parsing and validation of ListBase and underscore.
 */
// converted from ListBasePluginTest
public class ListBaseIdeTest extends ConvertedIdeTest {

	override getIgnoredIssueCodes() {
		return FluentIterable.concat(
				super.getIgnoredIssueCodes(),
				Collections.singleton(IssueCodes.CLF_NAME_CONTAINS_DISCOURAGED_CHARACTER)).toSet();
	}

	@Test
	def void testListBase() throws Exception {
		importProband(new File("probands", "ListBase"));
		assertIssues(
			"ListBase" -> #[
				"(Warning, [62:17 - 62:37], Unnecessary cast from ChangeEvent to ChangeEvent)"
			],
			"DataObject" -> #[
				"(Warning, [11:9 - 11:19], The import of ChangeType is unused.)",
				"(Warning, [12:9 - 12:29], The import of PropertyChangedEvent is unused.)"
			]
		);
	}
}
