/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.xtext.server.build;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.collect.ImmutableList;

/**
 * Wrapper exception to report incompletely built files from {@link XStatefulIncrementalBuilder#launch()} to the
 * outside, i.e. to {@link XBuildManager#doIncrementalBuild(boolean, CancelIndicator)}.
 * <p>
 * Temporary solution, see GH-1793.
 */
@SuppressWarnings("javadoc")
public class BuildCanceledException extends RuntimeException {

	public final ImmutableList<URI> incompletelyBuiltFiles;

	/*
	 * Review feedback:
	 *
	 * Preferably overload the constructor with the two allowed exception types.
	 *
	 */
	public BuildCanceledException(Iterable<URI> incompletelyBuiltFiles, RuntimeException cancellationThrowable) {
		super(cancellationThrowable);
		this.incompletelyBuiltFiles = ImmutableList.copyOf(incompletelyBuiltFiles);
	}

	public BuildCanceledException(Iterable<URI> incompletelyBuiltFiles, Error cancellationThrowable) {
		super(cancellationThrowable);
		this.incompletelyBuiltFiles = ImmutableList.copyOf(incompletelyBuiltFiles);
	}

	public void rethrowOriginalCancellation() {
		Throwable cause = getCause();
		if (cause instanceof RuntimeException) {
			throw (RuntimeException) cause;
		} else if (cause instanceof Error) {
			throw (Error) cause;
		} else {
			throw new IllegalStateException(); // cannot happen, see overloaded constructors
		}
	}
}
