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
package org.eclipse.n4js.tester;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Naive helper to keep build text indentation.
 */
class IndendLevel {
	private final LinkedList<String> indedation = new LinkedList<>();
	private final String indend;
	private int index = 0;

	IndendLevel(String indend) {
		this.indend = indend;
		indedation.add("");
	}

	public String get() {
		return this.indedation.get(index);
	}

	public void increase() {
		this.index += 1;

		if (this.indedation.size() == this.index)
			this.indedation.add(times(index, this.indend));

	}

	public void decrease() {
		if (this.index == 0)
			throw new RuntimeException("Cannot decrease.");

		this.index -= 1;
	}

	private String times(int n, String s) {
		return String.join("", Collections.nCopies(n, s));
	}

}