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
package org.eclipse.n4js.ts.types.util;

import static org.eclipse.n4js.ts.types.MemberAccessModifier.PRIVATE;
import static org.eclipse.n4js.ts.types.MemberAccessModifier.PROJECT;
import static org.eclipse.n4js.ts.types.MemberAccessModifier.PROTECTED;
import static org.eclipse.n4js.ts.types.MemberAccessModifier.PROTECTED_INTERNAL;
import static org.eclipse.n4js.ts.types.MemberAccessModifier.PUBLIC;
import static org.eclipse.n4js.ts.types.MemberAccessModifier.PUBLIC_INTERNAL;
import static org.eclipse.n4js.ts.types.util.AccessModifiers.less;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.junit.Test;

/**
 * Test for {@link AccessModifiers}.
 */
public class AccessModifiersTest {

	void assertLess(MemberAccessModifier left, MemberAccessModifier right) {
		assertTrue(left + "<" + right, less(left, right));
		assertFalse(right + "<" + left, less(right, left));
	}

	void assertNotLess(MemberAccessModifier left, MemberAccessModifier right) {
		assertFalse(left + "<" + right, less(left, right));
		assertFalse(right + "<" + left, less(right, left));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.n4js.ts.types.util.AccessModifiers#less(org.eclipse.n4js.ts.types.MemberAccessModifier, org.eclipse.n4js.ts.types.MemberAccessModifier)}
	 * .
	 */
	@Test
	public void testLessMemberAccessModifierMemberAccessModifier() {
		assertNotLess(PRIVATE, PRIVATE);
		assertLess(PRIVATE, PROJECT);
		assertLess(PRIVATE, PROTECTED_INTERNAL);
		assertLess(PRIVATE, PROTECTED);
		assertLess(PRIVATE, PUBLIC_INTERNAL);
		assertLess(PRIVATE, PUBLIC);

		assertNotLess(PROJECT, PROJECT);
		assertLess(PROJECT, PROTECTED_INTERNAL);
		assertLess(PROJECT, PROTECTED);
		assertLess(PROJECT, PUBLIC_INTERNAL);
		assertLess(PROJECT, PUBLIC);

		assertNotLess(PROTECTED_INTERNAL, PROTECTED_INTERNAL);
		assertLess(PROTECTED_INTERNAL, PROTECTED);
		assertLess(PROTECTED_INTERNAL, PUBLIC_INTERNAL);
		assertLess(PROTECTED_INTERNAL, PUBLIC);

		assertNotLess(PROTECTED, PROTECTED);
		assertNotLess(PROTECTED, PUBLIC_INTERNAL);
		assertLess(PROTECTED, PUBLIC);

		assertNotLess(PUBLIC_INTERNAL, PUBLIC_INTERNAL);
		assertLess(PUBLIC_INTERNAL, PUBLIC);

		assertNotLess(PUBLIC, PUBLIC);

	}

}
