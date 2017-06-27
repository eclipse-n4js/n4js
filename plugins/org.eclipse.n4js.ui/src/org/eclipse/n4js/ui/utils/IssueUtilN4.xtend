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
package org.eclipse.n4js.ui.utils;

import com.google.inject.Inject
import com.google.inject.Injector
import org.eclipse.n4js.ui.N4JSEditor
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.text.source.ISourceViewer
import org.eclipse.ui.IEditorPart
import org.eclipse.ui.PlatformUI
import org.eclipse.xtext.ui.editor.IURIEditorOpener
import org.eclipse.xtext.ui.editor.XtextEditor
import org.eclipse.xtext.ui.editor.model.IXtextDocument
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext
import org.eclipse.xtext.ui.editor.model.edit.IssueModificationContext
import org.eclipse.xtext.ui.util.IssueUtil
import org.eclipse.xtext.validation.Issue

/**
 * Some utility methods for {@link Issue}s.
 * Note that there is also an Xtext utility class for this purpose, called {@link IssueUtil}.
 */
class IssueUtilN4 {

	@Inject
	private IssueModificationContext.Factory modificationContextFactory;

	// ==== BEGIN injection work-around (see N4JSUiModule#configureMarkerResolutionGenerator()) ====

//	@Inject
//	private IURIEditorOpener editorOpener;
	@Inject
	private Injector tempInjector;
	def private IURIEditorOpener getEditorOpener() {
		if(PlatformUI.isWorkbenchRunning())
			tempInjector.getInstance(IURIEditorOpener)
		else
			null;
	}

	// ==== END injection work-around ====


	def IModificationContext getContext(Issue issue) {
		modificationContextFactory.createModificationContext(issue)
	}


	def IXtextDocument getDocument(Issue issue) {
		issue?.getEditor(XtextEditor)?.document
	}


	def ISourceViewer getViewer(Issue issue) {
		issue?.getEditor(N4JSEditor)?.sourceViewer2  // TODO is there a better way to obtain the ISourceViewer?
	}


	/**
	 * Returns the semantic element for the given issue.
	 * If the editor contents above the issue's offset have changed, the result is undefined.
	 */
	def EObject getElement(IXtextDocument document, Issue issue) {
		document?.readOnly[resource|
			resource.getEObject(issue.uriToProblem.fragment)
		]
	}


	private def <T extends IEditorPart> T getEditor(Issue issue, Class<T> expectedType) {
		// note: trimming fragment in previous line makes this more stable in case of changes to the
		// Xtext editor's contents (we are not interested in a particular element, just the editor)
		val uriToEditor = issue?.uriToProblem?.trimFragment;
		if(uriToEditor!==null) {
			val IEditorPart editor = editorOpener?.open(uriToEditor, false);
			if(editor!==null && expectedType.isAssignableFrom(editor.getClass))
				return editor as T;
		}
		return null;
	}
}
