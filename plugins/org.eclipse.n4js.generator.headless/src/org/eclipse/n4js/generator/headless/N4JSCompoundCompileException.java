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
package org.eclipse.n4js.generator.headless;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;

/**
 * Signaling severe Problems in Compilation
 */
public class N4JSCompoundCompileException extends N4JSCompileException {

	ArrayList<N4JSCompileErrorException> collectedErrors = new ArrayList<>();

	/**
	 * @see java.util.ArrayList#size()
	 */
	public int size() {
		return collectedErrors.size();
	}

	/**
	 * @see java.util.ArrayList#isEmpty()
	 */
	public boolean isEmpty() {
		return collectedErrors.isEmpty();
	}

	/**
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public boolean add(N4JSCompileErrorException e) {
		return collectedErrors.add(e);
	}

	/**
	 * @see java.util.ArrayList#iterator()
	 */
	public Iterator<N4JSCompileErrorException> iterator() {
		return collectedErrors.iterator();
	}

	/**
	 * @see java.util.ArrayList#spliterator()
	 */
	public Spliterator<N4JSCompileErrorException> spliterator() {
		return collectedErrors.spliterator();
	}

	/**
	 * @param message
	 *            user-message
	 */
	public N4JSCompoundCompileException(String message) {
		super(message);
	}

	/**
	 *
	 * @param message
	 *            user-message
	 * @param nested
	 *            wrapped exception
	 */
	public N4JSCompoundCompileException(String message, N4JSCompileErrorException nested) {
		super(message);
		add(nested);
	}

	@Override
	public void userDump(PrintStream stream) {
		super.userDump(stream);
		for (N4JSCompileErrorException ce : collectedErrors) {
			ce.userDump(stream);
		}
	}
}
