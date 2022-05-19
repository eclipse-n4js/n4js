/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.N4jsLibsAccess;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * Tests constants defined in {@link N4JSGlobals}
 */
public class N4JSGlobalsTest {

	/** Validates constant {@link N4JSGlobals#ALL_N4JS_LIBS} */
	@Test
	public void allN4jsLibsTest() {
		String msg = "value of constant N4JSGlobals#ALL_N4JS_LIBS is incorrect";
		Set<N4JSPackageName> actualN4jsLibs = N4jsLibsAccess.findAllN4jsLibs().keySet();
		assertTrue(msg, Sets.difference(N4JSGlobals.ALL_N4JS_LIBS, actualN4jsLibs).isEmpty());
		assertTrue(msg, Sets.difference(actualN4jsLibs, N4JSGlobals.ALL_N4JS_LIBS).isEmpty());
	}
}
