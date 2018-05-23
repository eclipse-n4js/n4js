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
package org.eclipse.n4js.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.XtextEditorErrorTickUpdater;

import com.google.inject.Inject;

/**
 * Minor customization to enable the {@link N4JSEditor} to obtain its corresponding {@link XtextEditorErrorTickUpdater}.
 */
public class N4JSEditorErrorTickUpdater extends XtextEditorErrorTickUpdater {

	/** Copy of private field with same name from super class for local use. */
	private XtextEditor editor;

	@Inject
	private IImageDescriptorHelper imageDescriptorHelper;

	@Override
	public void afterCreatePartControl(XtextEditor xtextEditor) {
		editor = xtextEditor;
		if (xtextEditor instanceof N4JSEditor) {
			((N4JSEditor) xtextEditor).setErrorTickUpdater(this);
		}
		super.afterCreatePartControl(xtextEditor);
	}

	@Override
	public void beforeDispose(XtextEditor xtextEditor) {
		editor = null;
		if (xtextEditor instanceof N4JSEditor) {
			((N4JSEditor) xtextEditor).setErrorTickUpdater(null);
		}
		super.beforeDispose(xtextEditor);
	}

	// increase visibility from 'protected' to 'public'
	@Override
	public void updateEditorImage(XtextEditor xtextEditor) {
		super.updateEditorImage(xtextEditor);
	}

	@Override
	public void scheduleUpdateEditor(ImageDescriptor titleImageDescription) {
		if (editor instanceof N4JSEditor && titleImageDescription != null) {
			titleImageDescription = ((N4JSEditor) editor).applyTitleImageOverlays(titleImageDescription);
		}
		super.scheduleUpdateEditor(titleImageDescription);
	}

	@Override
	public void scheduleUpdateEditor(Image titleImage) {
		if (editor instanceof N4JSEditor && titleImage != null) {
			final ImageDescriptor titleImageDesc = imageDescriptorHelper.getImageDescriptor(titleImage);
			final ImageDescriptor titleImageDescWithOverlay = ((N4JSEditor) editor)
					.applyTitleImageOverlays(titleImageDesc);
			if (titleImageDescWithOverlay != titleImageDesc) {
				super.scheduleUpdateEditor(titleImageDescWithOverlay);
				return;
			}
		}
		super.scheduleUpdateEditor(titleImage);
	}
}
