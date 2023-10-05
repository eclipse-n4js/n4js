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

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.n4js.tests.utils.ConvertedIdeTest;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

import com.google.common.collect.FluentIterable;

/**
 * Tests parsing and validation of ListBase and underscore.
 */
// converted from ListBasePluginTest
public class ListBaseIdeTest extends ConvertedIdeTest {

	@Override
	public Set<String> getIgnoredIssueCodes() {
		return FluentIterable.concat(
				super.getIgnoredIssueCodes(),
				Collections.singleton(IssueCodes.CLF_NAME_CONTAINS_DISCOURAGED_CHARACTER)).toSet();
	}

	@Test
	public void testListBase() throws Exception {
		importProband(new File("probands", "ListBase"));
		assertIssues2(
				Pair.of("ListBase", List.of(
						"(Warning, [62:17 - 62:37], Unnecessary cast from ChangeEvent to ChangeEvent)")),
				Pair.of("DataObject", List.of(
						"(Warning, [11:9 - 11:19], The import of ChangeType is unused.)",
						"(Warning, [12:9 - 12:29], The import of PropertyChangedEvent is unused.)")));
	}
}
