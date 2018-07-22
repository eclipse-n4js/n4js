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
package org.eclipse.n4js.smith.ui;

import java.util.function.Consumer;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Listens to part changes and calls one call-back function with active editor in all cases.
 */
public class ActiveEditorChangeListener implements IPartListener {

	Consumer<IEditorPart> updateFunction;

	/**
	 * Creates the listener with the given call-back function.
	 */
	public ActiveEditorChangeListener(Consumer<IEditorPart> updateFunction) {
		this.updateFunction = updateFunction;
	}

	private void doUpdate(IWorkbenchPart part) {
		final IEditorPart editorPart = part.getSite().getPage().getActiveEditor();
		updateFunction.accept(editorPart);

	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		doUpdate(part);
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		doUpdate(part);
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		doUpdate(part);
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		doUpdate(part);
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		doUpdate(part);
	}

}
