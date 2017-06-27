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
package org.eclipse.n4js.n4mf.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.editor.DirtyStateEditorSupport;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkHelper;

import org.eclipse.n4js.n4mf.ui.editor.hyperlinking.N4MFHyperlinker;
import org.eclipse.n4js.n4mf.ui.internal.N4MFDirtyStateEditorSupport;
import org.eclipse.n4js.n4mf.ui.wizard.N4JSProjectCreator;

/**
 * Use this class to register components to be used within the IDE.
 */
public class N4MFUiModule extends org.eclipse.n4js.n4mf.ui.AbstractN4MFUiModule {
	/**
	 * Create a new UIModule in the given plugin.
	 */
	public N4MFUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	/**
	 * Returns the type of {@link N4JSProjectCreator}.
	 */
	public Class<? extends org.eclipse.xtext.ui.wizard.IProjectCreator> bindIProjectCreator() {
		return N4JSProjectCreator.class;
	}

	/**
	 * Returns the type of {@link N4MFHyperlinker}, supports hyperlinking in manifest editor.
	 */
	public Class<? extends IHyperlinkHelper> bindIHyperlinkHelper() {
		return N4MFHyperlinker.class;
	}

	/**
	 * Returns with the type of {@link N4MFDirtyStateEditorSupport}, support interactive validation in manifest editors.
	 */
	public Class<? extends DirtyStateEditorSupport> bindDirtyStateEditorSupport() {
		return N4MFDirtyStateEditorSupport.class;
	}

	/**
	 * Bind custom XtextEditor.
	 */
	public Class<? extends XtextEditor> bindXtextEditor() {
		return N4MFEditor.class;
	}

}
