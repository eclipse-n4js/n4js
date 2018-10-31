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
package org.eclipse.n4js.typesystem

import com.google.inject.Inject
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.typesystem.utils.StructuralTypingComputer

import static org.junit.Assert.*

/**
 */
class AbstractStructuralTypingComputerTest extends AbstractTypeSystemHelperTests {

	@Inject
	protected StructuralTypingComputer structuralTypingComputer;

	/**
	 * Asserts that the names of the collected members are equal to the expected members.
	 * The expected members and the actual members are sorted alphabetically to make test more robust.
	 * @expectedMembers comma separated list of members
	 */
	def <T> void assertMembers(String expectedMembers, Iterable<T> collectedMembers) {
		val sortedExpectedMembers = expectedMembers.split(",").map[it.trim].sort.join(", ")
		val actual = collectedMembers.map[memberToString(it)].sort.join(", ")
		assertEquals(sortedExpectedMembers, actual);
	}

	def String memberToString(Object member) {

		switch (member) {
			TField: member.name
			TSetter: "set " + member.name
			TGetter: "get " + member.name
			TMethod: member.name + "()"
			Pair<?, ?>: if (member.key instanceof TGetter) (member.key as TGetter).name else String.valueOf(member.key)
			default: String.valueOf(member) + "??"
		}

	}
}
