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
package org.eclipse.n4js.ui.editor;

import java.util.concurrent.locks.Lock;

import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.ITextEditComposer;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;

/**
 * Minor customization of {@link XtextDocument} in order to make {@link Lock#tryLock()} available to clients of Xtext
 * documents.
 */
public class N4JSDocument extends XtextDocument {

	/** Copy of private field with same name from super class for local use. */
	private N4JSDocumentLocker stateAccess;

	/**
	 * Creates a new instance, delegates to {@link XtextDocument#XtextDocument(DocumentTokenSource, ITextEditComposer)}.
	 */
	@Inject
	public N4JSDocument(DocumentTokenSource tokenSource, ITextEditComposer composer) {
		super(tokenSource, composer);
	}

	/**
	 * Like {@link #readOnly(IUnitOfWork)}, but won't block if a read-lock cannot be acquired at this time; instead, the
	 * given default value will be returned in this case.
	 */
	public <T> T tryReadOnly(IUnitOfWork<T, XtextResource> work, T defaultValue) {
		return stateAccess.tryReadOnly(work, defaultValue);
	}

	@Override
	protected XtextDocumentLocker createDocumentLocker() {
		N4JSDocumentLocker result = new N4JSDocumentLocker();
		this.stateAccess = result;
		return result;
	}

	/** See {@link N4JSDocument}. */
	protected class N4JSDocumentLocker extends XtextDocumentLocker {

		/** Creates a new instance. */
		public N4JSDocumentLocker() {
			super();
		}

		/** See {@link N4JSDocument#tryReadOnly(IUnitOfWork, Object)}. */
		public <T> T tryReadOnly(IUnitOfWork<T, XtextResource> work, T defaultValue) {
			// not super safe multithreading, but ok'ish for our purpose
			if (getReadHoldCount() == 0 && getWriteHoldCount() == 0) {
				// we managed to obtain the readLock
				// since we have exclusive read access, we either have also the resource lock, or we are prone to
				// deadlocking
				return internalReadOnly(work, false);
			}
			return defaultValue;
		}
	}

}
