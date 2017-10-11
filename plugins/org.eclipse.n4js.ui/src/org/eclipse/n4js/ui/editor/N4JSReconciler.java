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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.n4js.ui.N4JSEditor;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.reconciler.XtextDocumentReconcileStrategy;
import org.eclipse.xtext.ui.editor.reconciler.XtextReconciler;

import com.google.inject.Inject;

/**
 * Customized to notify the corresponding {@link N4JSEditor} that reconciliation was started / completed.
 */
public class N4JSReconciler extends XtextReconciler {

	/** Copy of private field with same name from super class for local use. */
	private XtextEditor editor;

	/** Creates a new instance. */
	@Inject
	public N4JSReconciler(XtextDocumentReconcileStrategy strategy) {
		super(strategy);
	}

	@Override
	public void setEditor(XtextEditor editor) {
		super.setEditor(editor);
		this.editor = editor;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		final N4JSEditor editorCasted = editor instanceof N4JSEditor ? (N4JSEditor) editor : null;
		try {
			if (editorCasted != null) {
				editorCasted.setReconciling(true);
			}
			return super.run(monitor);
		} finally {
			if (editorCasted != null) {
				editorCasted.setReconciling(false);
			}
		}
	}
}
