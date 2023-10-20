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
package org.eclipse.n4js.types;

import static org.junit.Assert.fail;

import org.eclipse.emf.common.util.EList;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;

import com.google.inject.Inject;

/**
 */
public abstract class AbstractContainerTypesTest<T extends ContainerType<?>> {
	@Inject
	ParseHelper<Script> parseHelper;
	@Inject
	ContainerTypesHelper containerTypesHelper;

	/**
	 * Parses the given text and returns the first top level type casted to the type variable.
	 */
	@SuppressWarnings("unchecked")
	protected T parseAndGetFirstType(CharSequence text) {
		try {
			return (T) parseHelper.parse(text).getModule().getTypes().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}

	/**
	 * Parses the given text and returns the top level types.
	 */
	protected EList<Type> parseAndGetTypes(CharSequence text) {
		try {
			return parseHelper.parse(text).getModule().getTypes();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
			return null;
		}
	}

	/**
	 * Asserts that both member are the same and that their FQN is similar.
	 */
	protected void assertSame(TMember expected, TMember actual) {
		Assert.assertEquals(((Type) (expected.eContainer())).getName() + '.' + expected.getName(),
				(((Type) (actual.eContainer())).getName() + '.' + actual.getName()));
		Assert.assertSame(expected, actual);
	}
}
