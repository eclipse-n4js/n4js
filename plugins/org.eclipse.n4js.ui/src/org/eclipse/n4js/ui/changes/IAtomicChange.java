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
import org.eclipse.jface.text.IRegion;

/**
 * An atomic, i.e. non-composite, textual change.
 * <p>
 * Since the singleton identity change is implemented as an (empty) composite change (cf. {@link IChange#IDENTITY}), all
 * instances of IAtomicChange are "actual", i.e. non-identity, changes.
 */
public interface IAtomicChange extends IChange, IRegion, Comparable<IAtomicChange> {

	/**
	 * URI of the file containing the document to be changed.
	 */
	URI getURI();

	/**
	 * The offset of the beginning of the text region that is to be changed.
	 */
	@Override
	int getOffset();

	/**
	 * The length of the text region that is to be changed.
	 */
	@Override
	int getLength();

	/**
	 * Apply the receiving change to the given document.
	 */
	void apply(IDocument document) throws BadLocationException;

	/**
	 * Base class for all {@link IAtomicChange}s.
	 */
	public static abstract class AbstractAtomicChange extends AbstractChange implements IAtomicChange {

		private final URI uri;
		private final int offset;
		private final int length;

		/**
		 * Constructs a new atomic change.
		 *
		 * @see IAtomicChange
		 */
		public AbstractAtomicChange(URI uri, int offset, int length) {
			this.uri = uri.trimFragment();
			this.offset = offset;
			this.length = length;
		}

		@Override
		public URI getURI() {
			return uri;
		}

		@Override
		public int getOffset() {
			return offset;
		}

		@Override
		public int getLength() {
			return length;
		}

		@Override
		public int compareTo(IAtomicChange other) {
			return Integer.compare(this.getOffset(), other.getOffset());
		}
	}
}
