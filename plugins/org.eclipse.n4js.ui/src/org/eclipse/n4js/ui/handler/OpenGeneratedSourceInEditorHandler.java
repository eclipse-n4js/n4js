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
package org.eclipse.n4js.ui.handler;

import static org.eclipse.ui.PlatformUI.getWorkbench;
import static org.eclipse.ui.handlers.HandlerUtil.getActiveEditor;
import static org.eclipse.ui.handlers.HandlerUtil.getCurrentSelection;

import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Handler for opening the generated JS file for a selected N4JS file in the editor.
 */
public class OpenGeneratedSourceInEditorHandler extends AbstractHandler {

	@Inject
	private GeneratedJsFileLocator fileLocator;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final ISelection selection = getCurrentSelection(event);
		if (null == selection) {
			return null;
		}

		if (selection.isEmpty()) {
			return null;
		}

		final AtomicReference<IFile> fileRef = new AtomicReference<>();

		if (selection instanceof IStructuredSelection) {
			final Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof IFile) {
				fileRef.set((IFile) element);
			}
		} else if (selection instanceof ITextSelection) {
			final IEditorPart editorPart = getActiveEditor(event);
			if (null != editorPart) {
				final IEditorInput input = editorPart.getEditorInput();
				if (input instanceof IFileEditorInput) {
					fileRef.set(((IFileEditorInput) input).getFile());
				}
			}
		}

		if (null != fileRef.get()) {
			final Optional<IFile> generatedSource = fileLocator.tryGetGeneratedSourceForN4jsFile(fileRef.get());
			if (generatedSource.isPresent()) {
				tryOpenFileInEditor(fileRef.get(), generatedSource.get());
			}
		}

		return null;
	}

	private void tryOpenFileInEditor(final IFile file, final IFile generatedFile) {
		final IEditorDescriptor desc = getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
		if (null != desc) {
			final IWorkbenchPage page = getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				page.openEditor(new FileEditorInput(generatedFile), desc.getId());
			} catch (final PartInitException e) {
				throw new RuntimeException("Error while trying to open generated JS file for " + file, e);
			}
		}
	}

}
