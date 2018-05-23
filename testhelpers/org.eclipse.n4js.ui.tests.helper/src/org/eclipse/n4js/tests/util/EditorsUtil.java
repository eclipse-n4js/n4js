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
package org.eclipse.n4js.tests.util;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.ui.editor.XtextEditor;

/**
 * Utility for working with editors (cares to logic in UI thread).
 */
public class EditorsUtil {

	static Logger logger = Logger.getLogger(EditorsUtil.class);

	/**
	 * Get {@link IWorkbenchPage} of currently running workbench. Returned value is wrapped in Optional, thus it is
	 * never null.
	 */
	public static final Optional<IWorkbenchPage> getActivePage() {
		AtomicReference<IWorkbenchPage> wrappedPage = new AtomicReference<>();

		Display.getDefault().syncExec(() -> wrappedPage.set(unsafeGetWorkbenchPage()));

		IWorkbenchPage page = wrappedPage.get();
		if (page == null)
			logger.warn("Cannot obtain IWorkbenchPage, will return empty Optional");

		return Optional.ofNullable(page);
	}

	static private final IWorkbenchPage unsafeGetWorkbenchPage() {
		IWorkbenchPage page = null;
		if (PlatformUI.isWorkbenchRunning()) {
			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
			page = win.getActivePage();
		}
		return page;
	}

	/** If {@link IWorkbenchPage} is available, will force all editors to be closed (without save). */
	public static final void forceCloseAllEditors() {
		Display.getDefault().syncExec(() -> {
			IWorkbenchPage page = unsafeGetWorkbenchPage();
			if (page != null) {
				boolean allClosed = page.closeAllEditors(false);
				logger.info("All closed: " + allClosed);
			} else {
				logger.info(" Closing all editors: No active page.");
			}
		});
	}

	/** If {@link IWorkbenchPage} is available, will try to force close (without save) provided editor. */
	public static final void forceCloseEditor(IEditorPart editor) {
		Display.getDefault().syncExec(() -> {
			IWorkbenchPage page = unsafeGetWorkbenchPage();
			if (page != null) {
				boolean allClosed = page.closeEditor(editor, false);
				logger.info("Editor closed: " + allClosed);
			} else {
				logger.info(" Closing all editors: No active page.");
			}
		});
	}

	/**
	 * Obtains or opens {@link XtextEditor} for provided {@link Resource} and editor id. Upon activation or opening of
	 * the editor waits a moment for editor to become active.
	 *
	 * @param uri
	 *            URI to be opened
	 * @param editorExtensionID
	 *            {String} defining the id of the editor extension to use
	 * @return {@link Optional} instance of {@link XtextEditor} for given {@link Resource}.
	 */
	public static final Optional<XtextEditor> openXtextEditor(URI uri, String editorExtensionID) {

		String platformStr = uri.toString().replace("platform:/resource/", "");
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(platformStr));

		Optional<IWorkbenchPage> page = getActivePage();
		Optional<IEditorPart> internalFileEditor = getFileEditor(file, page.get(), editorExtensionID);

		if ((page.isPresent() && internalFileEditor.isPresent()) == false) {
			logger.warn("Cannot obtain editor components for " + file.getRawLocationURI());
			return Optional.empty();
		}

		IEditorPart ieditorPart = internalFileEditor.get();
		if (ieditorPart instanceof XtextEditor == false) {
			logger.warn("cannot obtain Xtext editor for file " + file.getRawLocation());
			return Optional.empty();
		}

		waitForEditorToBeActive(ieditorPart, page.get());

		return Optional.ofNullable((XtextEditor) ieditorPart);
	}

	/** Get editor for provided file, returns once editor is active or after timeout */
	private static final void waitForEditorToBeActive(IEditorPart internalFileEditor, IWorkbenchPage page) {
		long start = System.currentTimeMillis();
		long end = start;
		do {
			end = System.currentTimeMillis();
		} while (page.getActiveEditor() != internalFileEditor && (end - start) < 5000);

		if (page.getActiveEditor() != internalFileEditor)
			logger.warn("selected editor was not activated within timout");
	}

	/** get editor for given file */
	private static final Optional<IEditorPart> getFileEditor(IFile file, IWorkbenchPage page, String editorID) {
		AtomicReference<IEditorPart> wrappedEditor = new AtomicReference<>();

		Display.getDefault().syncExec(() -> {
			try {
				wrappedEditor.set(IDE.openEditor(page, file, editorID, true));
			} catch (Exception e) {
				logger.error("exception when opening editor for " + file.getRawLocationURI(), e);
			}
		});

		IEditorPart internalFileEditor = wrappedEditor.get();
		if (internalFileEditor == null)
			logger.warn("cannot obtain editor for a file " + file.getRawLocationURI());

		return Optional.ofNullable(internalFileEditor);
	}
}
