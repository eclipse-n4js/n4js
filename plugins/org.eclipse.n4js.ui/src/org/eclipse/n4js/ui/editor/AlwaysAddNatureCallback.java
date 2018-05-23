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
package org.eclipse.n4js.ui.editor;

import org.eclipse.core.resources.IResource;
import org.eclipse.xtext.builder.nature.ToggleXtextNatureCommand;
import org.eclipse.xtext.ui.editor.IXtextEditorCallback;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.inject.Inject;

/**
 * Callback that makes the Xtext nature is added to the project as soon as an N4JS file is opened.
 */
@SuppressWarnings("restriction")
public class AlwaysAddNatureCallback extends IXtextEditorCallback.NullImpl {

	@Inject
	private ToggleXtextNatureCommand toggleNature;

	@Override
	public void afterCreatePartControl(XtextEditor editor) {
		IResource resource = editor.getResource();
		if (resource != null && !toggleNature.hasNature(resource.getProject()) && resource.getProject().isAccessible()
				&& !resource.getProject().isHidden()) {
			toggleNature.toggleNature(resource.getProject());
		}
	}
}
