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
package org.eclipse.n4js.ui.changes;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

import org.eclipse.n4js.ui.changes.IAtomicChange.AbstractAtomicChange;

/**
 */
public class Replacement extends AbstractAtomicChange implements IReplacement {

	private final String text;

	/**
	 * Constructs a new text replacement.
	 *
	 * @see IReplacement
	 */
	public Replacement(URI uri, int offset, int length, String text) {
		super(uri, offset, length);
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void apply(IDocument document) throws BadLocationException {
		document.replace(getOffset(), getLength(), getText());
	}
}
