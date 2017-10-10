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

import java.lang.reflect.Field;
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
	private final N4JSDocumentLocker stateAccess;

	/**
	 * Creates a new instance, delegates to {@link XtextDocument#XtextDocument(DocumentTokenSource, ITextEditComposer)}.
	 */
	@Inject
	public N4JSDocument(DocumentTokenSource tokenSource, ITextEditComposer composer) {
		super(tokenSource, composer);
		stateAccess = (N4JSDocumentLocker) readField(XtextDocument.class, "stateAccess", this);
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
		return new N4JSDocumentLocker();
	}

	/** See {@link N4JSDocument}. */
	protected class N4JSDocumentLocker extends XtextDocumentLocker {

		/** Copy of private field with same name from super class for local use. */
		private final Lock readLock;

		/** Creates a new instance. */
		public N4JSDocumentLocker() {
			super();
			readLock = (Lock) readField(XtextDocumentLocker.class, "readLock", this);
		}

		/** See {@link N4JSDocument#tryReadOnly(IUnitOfWork, Object)}. */
		public <T> T tryReadOnly(IUnitOfWork<T, XtextResource> work, T defaultValue) {
			if (readLock.tryLock()) {
				try {
					// note: to avoid having to copy the entire body of method #internalReadOnly() from super class, we
					// simply obtain two read locks (which is possible, because 'readLock' is a reentrant lock) ...
					return internalReadOnly(work, false);
				} finally {
					readLock.unlock();
				}
			}
			return defaultValue;
		}
	}

	/** Utility method used to read two private fields from super classes required for the above adjustments. */
	private static final <T> Object readField(Class<? super T> clazz, String fieldName, T instance) {
		try {
			final Field f = clazz.getDeclaredField(fieldName);
			final boolean oldAccessible = f.isAccessible();
			if (!oldAccessible) {
				f.setAccessible(true);
			}
			try {
				return f.get(instance);
			} finally {
				if (!oldAccessible) {
					f.setAccessible(oldAccessible);
				}
			}
		} catch (Throwable th) {
			throw new RuntimeException("failed to read field \"" + fieldName + "\" of " + clazz.getSimpleName(), th);
		}
	}
}
