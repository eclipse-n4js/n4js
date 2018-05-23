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
package org.eclipse.n4js.n4mf.utils.parsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Container for data created during parsing process.
 *
 * Note that result can contain instance of {@link #ast} and {@link #errors} at the same time. It is up to the caller to
 * decide how to proceed.
 */
public final class ParserResults<T> {
	T ast = null;
	final List<String> errors = new ArrayList<>();

	/** Instance of the object during parsing. Can be <code>null</code> in case of errors. */
	public T getAST() {
		return this.ast;
	}

	/** Error messages collected during parsing. */
	public List<String> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	void addErrors(List<String> messages) {
		errors.addAll(messages);
	}

	void addErrors(String message) {
		errors.add(message);
	}

}
