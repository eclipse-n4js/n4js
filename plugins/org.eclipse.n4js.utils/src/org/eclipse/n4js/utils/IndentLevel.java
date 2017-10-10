/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Naive helper to keep build text indentation.
 */
public class IndentLevel {
	private final LinkedList<String> cachedIndedations = new LinkedList<>();
	private final String indend;
	private int index = 0;

	/** Creates new instance with specific string used as indentation sequence. */
	public IndentLevel(String indend) {
		this.indend = indend;
		// initial indentation, 0 index
		cachedIndedations.add("");
	}

	/** returns string with indent sequence repeated {@code n} times, based on current indedation level. */
	public String get() {
		return this.cachedIndedations.get(index);
	}

	/** Increases current indentation level. */
	public void increase() {
		this.index += 1;

		if (this.cachedIndedations.size() == this.index)
			this.cachedIndedations.add(times(index, this.indend));
	}

	/** decreases current indentation level. */
	public void decrease() {
		if (this.index == 0)
			throw new RuntimeException("Cannot decrease below 0.");

		this.index -= 1;
	}

	/** Creates string containing {@code n} copies of {@code s} */
	private String times(int n, String s) {
		return String.join("", Collections.nCopies(n, s));
	}
}
