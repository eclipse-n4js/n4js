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
package org.eclipse.n4js.jsdoc.lookup;

/**
 * Convieninece abstraction over comemnt, used when browsing through colelction of Comemnts.
 */
public class CommentCandidate {
	private String text = "";
	private boolean startsWithDoubleStar = false;

	/**
	 * @throws InstantiationException
	 *             If comment start&end markers check fails
	 *
	 */
	public CommentCandidate(String text) throws InstantiationException {
		if (!text.startsWith("/*") && !text.endsWith("*/")) {
			throw new InstantiationException("CommentCandidate input string invalid");
		}

		if (text.startsWith("/**")) {
			startsWithDoubleStar = true;
		}
		this.text = text;
	}

	/**
	 * @return String representation of given comment.
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return true if given comment starts with slash and two stars
	 */
	public boolean startsWithDoubleStar() {
		return this.startsWithDoubleStar;
	}
}
