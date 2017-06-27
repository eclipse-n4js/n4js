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
package org.eclipse.n4js.jsdoc;

import org.eclipse.emf.ecore.EObject;

/**
 * JSDocTokens are continuous strings found in a JSDoc comment, the concrete syntax of a token depends on the
 * {@link IJSDocTokenizer} creating it. However, no characters must have been skipped when scanning the token. In other
 * words, the token string must be contained in the raw comment string.
 */
public class JSDocToken {

	/**
	 * String value of this token
	 */
	public final String token;
	/**
	 * Start position of this token in source String
	 */
	public final int start;
	/**
	 * End position of this token in source String
	 */
	public final int end;

	/**
	 * @param token
	 *            String value of this token
	 * @param start
	 *            Start position of this token in source String
	 * @param end
	 *            End position of this token in source String
	 */
	public JSDocToken(String token, int start, int end) {
		this.start = start;
		this.end = end;
		this.token = token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + end;
		result = prime * result + start;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JSDocToken other = (JSDocToken) obj;
		if (end != other.end)
			return false;
		if (start != other.start)
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	/**
	 * Returns the AST with the internal structure of the token, or null, if the token has no internal structure. The
	 * default implementation of JSToken always returns null, only sub classes may provide that feature, e.g., type
	 * expression tokenizers.
	 *
	 * @return EObject root element or null
	 */
	public EObject getTokenAST() {
		return null;
	}

	@Override
	public String toString() {
		return "\"" + token + "\" [" + start + ", " + end + "]";
	}

}
