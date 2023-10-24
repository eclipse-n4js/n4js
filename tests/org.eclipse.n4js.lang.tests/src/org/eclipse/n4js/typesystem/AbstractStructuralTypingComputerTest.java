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
package org.eclipse.n4js.typesystem;

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.sort;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.typesystem.utils.StructuralTypingComputer;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;

abstract public class AbstractStructuralTypingComputerTest extends AbstractTypeSystemHelperTests {

	@Inject
	protected StructuralTypingComputer structuralTypingComputer;

	/**
	 * Asserts that the names of the collected members are equal to the expected members. The expected members and the
	 * actual members are sorted alphabetically to make test more robust.
	 *
	 * @expectedMembers comma separated list of members
	 */
	public <T> void assertMembers(String expectedMembers, Iterable<T> collectedMembers) {
		String sortedExpectedMembers = join(", ", sort(map(Arrays.asList(expectedMembers.split(",")), m -> m.trim())));
		String actual = join(", ", sort(map(collectedMembers, m -> memberToString(m))));
		assertEquals(sortedExpectedMembers, actual);
	}

	public String memberToString(Object member) {

		if (member instanceof TField) {
			return ((TField) member).getName();
		}
		if (member instanceof TSetter) {
			return "set " + ((TSetter) member).getName();
		}
		if (member instanceof TGetter) {
			return "get " + ((TGetter) member).getName();
		}
		if (member instanceof TMethod) {
			return ((TMethod) member).getName() + "()";
		}
		if (member instanceof Pair<?, ?>) {
			Pair<?, ?> pair = (Pair<?, ?>) member;
			return (pair.getKey() instanceof TGetter)
					? ((TGetter) pair.getKey()).getName()
					: String.valueOf(pair.getKey());
		}

		return String.valueOf(member) + "??";

	}
}
