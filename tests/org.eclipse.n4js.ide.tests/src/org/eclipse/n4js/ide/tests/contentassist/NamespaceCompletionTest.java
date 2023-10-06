/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.contentassist;

import java.util.List;

import org.eclipse.n4js.ide.tests.helper.server.AbstractCompletionTest;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Test;

/**
 * Code completion tests for camel case scenarios
 */

public class NamespaceCompletionTest extends AbstractCompletionTest {

	/** Some default modules that export a number of classes for all tests. */
	@Override
	public final List<Pair<String, String>> getDefaultTestProject() {
		return List.of(
				Pair.of("Exporter", """
						export external namespace NS {
							class NSC {}
						}

						export external class NC {

						}
						"""));
	}

	@Test
	public void test01() {
		testAtCursorPartially("""
				let x : N<|>
				""",
				"""
						(NC, Class, Exporter, , , 00019, , , , ([0:8 - 0:9], NC), [([0:0 - 0:0], import {NC} from "Exporter";)], [], , )
						(NS, Color, Exporter, , , 00020, , , , ([0:8 - 0:9], NS), [([0:0 - 0:0], import {NS} from "Exporter";)], [], , )
						""");
	}

}
