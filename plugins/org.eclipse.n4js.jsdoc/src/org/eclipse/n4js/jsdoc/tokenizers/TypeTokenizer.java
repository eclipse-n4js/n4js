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
package org.eclipse.n4js.jsdoc.tokenizers;

/**
 * Types are to be enclosed in curly brackets, this tokenizer returns the string contained within these brackets. Nested
 * pairs of brackets are allowd, but not line breaks. Enclosing brackets are not returned. Escape character is
 * backslash.
 */
public class TypeTokenizer extends RegionTokenizer {

	@SuppressWarnings("javadoc")
	public final static TypeTokenizer INSTANCE = new TypeTokenizer();

	/**
	 * Contructor with default values: for start/end markers ({/}), allows nesting, escape chars (\\), doesn't include
	 * markers in token returned in from parsing, doesn't allow for linebreaks. For details refer to supertype
	 * {@link RegionTokenizer}
	 */
	public TypeTokenizer() {
		super("{", "}", true, '\\', false, false);
	}

	/**
	 * Contructor with all default values but allowLinebreaks {@link RegionTokenizer}
	 */
	public TypeTokenizer(boolean allowLinebreaks) {
		// TODO this is shortcut for proper types in client
		/*
		 * allows to configure allowLinebreaks while maintaining inheritance from this type, shortuct for proper types
		 * on client, proper types should just extend {@link RegionTokenizer}
		 */
		super("{", "}", true, '\\', false, allowLinebreaks);
	}

}
