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
package org.eclipse.n4js.utils;

import static java.lang.Character.MAX_VALUE;
import static java.lang.Character.MIN_VALUE;
import static java.lang.Character.valueOf;

import com.google.common.collect.DiscreteDomain;

/**
 * Discrete domain implementation for {@link Character}s.
 */
public class CharDiscreteDomain extends DiscreteDomain<Character> {

	/** Returns with the discrete domain singleton for {@link Character}s. */
	public static DiscreteDomain<Character> chars() {
		return INSTANCE;
	}

	/** Shared singleton. */
	private static final CharDiscreteDomain INSTANCE = new CharDiscreteDomain();

	private CharDiscreteDomain() {
	}

	@Override
	public Character next(final Character value) {
		int i = value.charValue();
		return MAX_VALUE == i ? null : valueOf((char) (i + 1));
	}

	@Override
	public Character previous(final Character value) {
		int i = value.charValue();
		return MIN_VALUE == i ? null : valueOf((char) (i - 1));
	}

	@Override
	public long distance(final Character start, final Character end) {
		return end.charValue() - start.charValue();
	}

	@Override
	public Character minValue() {
		return valueOf(MIN_VALUE);
	}

	@Override
	public Character maxValue() {
		return valueOf(MAX_VALUE);
	}

}
