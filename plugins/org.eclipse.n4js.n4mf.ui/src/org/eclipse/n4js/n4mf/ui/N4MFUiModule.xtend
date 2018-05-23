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
package org.eclipse.n4js.n4mf.ui

import org.eclipse.n4js.n4mf.ui.editor.hyperlinking.N4MFHyperlinker
import org.eclipse.n4js.n4mf.ui.internal.N4MFDirtyStateEditorSupport
import org.eclipse.n4js.n4mf.ui.wizard.N4JSProjectCreator
import org.eclipse.n4js.utils.ui.editor.AvoidRefreshDocumentProvider
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.ui.editor.DirtyStateEditorSupport
import org.eclipse.xtext.ui.editor.XtextEditor
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkHelper
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider
import org.eclipse.xtext.ui.wizard.IProjectCreator
import org.eclipse.xtext.ui.editor.quickfix.MarkerResolutionGenerator
import org.eclipse.n4js.ui.quickfix.N4JSMarkerResolutionGenerator
import org.eclipse.xtext.ui.util.IssueUtil
import org.eclipse.n4js.ui.quickfix.N4JSIssue

/**
 * Use this class to register components to be used within the Eclipse IDE.
 */
@FinalFieldsConstructor
class N4MFUiModule extends AbstractN4MFUiModule {

	/**
	 * Returns the type of {@link N4JSProjectCreator}.
	 */
	def Class<? extends IProjectCreator> bindIProjectCreator() {
		return N4JSProjectCreator;
	}

	/**
	 * Returns the type of {@link N4MFHyperlinker}, supports hyperlinking in manifest editor.
	 */
	def Class<? extends IHyperlinkHelper> bindIHyperlinkHelper() {
		return N4MFHyperlinker;
	}

	/**
	 * Returns with the type of {@link N4MFDirtyStateEditorSupport}, support interactive validation in manifest editors.
	 */
	def Class<? extends DirtyStateEditorSupport> bindDirtyStateEditorSupport() {
		return N4MFDirtyStateEditorSupport;
	}

	/**
	 * Bind custom XtextEditor.
	 */
	def Class<? extends XtextEditor> bindXtextEditor() {
		return N4MFEditor;
	}

	/** Workaround for the problem: file is refreshed when opened */
	def Class<? extends XtextDocumentProvider> bindXtextDocumentProvider() {
		return AvoidRefreshDocumentProvider;
	}

	def configureMarkerResolutionGenerator(com.google.inject.Binder binder) {
		if (org.eclipse.ui.PlatformUI.isWorkbenchRunning()) {
			binder.bind(MarkerResolutionGenerator).to(N4JSMarkerResolutionGenerator);
		}
	}

	/**
	 * Bind custom IssueUtil.
	 */
	def Class<? extends IssueUtil> bindIssueUtil() {
		return N4JSIssue.Util;
	}
}
