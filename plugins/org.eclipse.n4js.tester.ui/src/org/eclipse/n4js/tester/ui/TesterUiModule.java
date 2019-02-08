/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tester.ui;

import org.eclipse.n4js.runner.ui.RunnerFrontEndUI;
import org.eclipse.n4js.tester.ui.resultsview.TestResultsView;
import org.eclipse.n4js.ui.editor.EditorContentExtractor;
import org.eclipse.n4js.ui.projectModel.IN4JSEclipseCore;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.editor.XtextPresentationReconciler;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Defines bindings to N4JS-UI instances.
 */
public class TesterUiModule implements Module {

	final private Injector n4jsInjector;

	TesterUiModule(Injector n4jsInjector) {
		this.n4jsInjector = n4jsInjector;
	}

	@Override
	public void configure(Binder binder) {
		// define all bindings to N4JS-UI here (ui packages)
		binder.bind(XtextPresentationReconciler.class)
				.toProvider(() -> n4jsInjector.getInstance(XtextPresentationReconciler.class));
		binder.bind(EditorContentExtractor.class)
				.toProvider(() -> n4jsInjector.getInstance(EditorContentExtractor.class));
		binder.bind(IN4JSEclipseCore.class)
				.toProvider(() -> n4jsInjector.getInstance(IN4JSEclipseCore.class));
		binder.bind(IURIEditorOpener.class)
				.toProvider(() -> n4jsInjector.getInstance(IURIEditorOpener.class));
		binder.bind(RunnerFrontEndUI.class)
				.toProvider(() -> n4jsInjector.getInstance(RunnerFrontEndUI.class));

		binder.bind(TestResultsView.class);
		binder.bind(TestConfigurationConverter.class);
	}

}
