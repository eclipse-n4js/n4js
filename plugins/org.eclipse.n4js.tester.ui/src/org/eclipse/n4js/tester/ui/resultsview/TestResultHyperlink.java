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
package org.eclipse.n4js.tester.ui.resultsview;

import java.io.File;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.n4js.ui.console.ConsoleMessages;
import org.eclipse.n4js.ui.console.JSStackTraceLocationText;
import org.eclipse.n4js.ui.internal.N4JSGracefulActivator;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Hyperlink used in the test result view, created by the {@link TestResultHyperlinkDetector}.
 */
public class TestResultHyperlink implements IHyperlink {

	private final IRegion region;
	private final JSStackTraceLocationText locationText;
	private final String hyperlinkText;

	/**
	 * Creates a hyperlink for the given region and target location. This is used only in the TestResultView, the region
	 * is the whole line containing the stack trace.
	 *
	 * The simple filename and its extension are used as hyperlink text and type label.
	 */
	public TestResultHyperlink(IRegion region, JSStackTraceLocationText locationText) {
		this.region = region;
		this.locationText = locationText;
		this.hyperlinkText = locationText.getSimpleName() + ":" + locationText.line + ":" + locationText.column;
	}

	@Override
	public IRegion getHyperlinkRegion() {
		return region;
	}

	@Override
	public String getTypeLabel() {
		return null;
	}

	@Override
	public String getHyperlinkText() {
		return hyperlinkText;
	}

	@Override
	public void open() {
		File file = new File(locationText.fileName);

		if (file.exists()) {
			IFileStore fileStore = EFS.getLocalFileSystem().getStore(file.toURI());
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				IEditorPart editorPart = IDE.openEditorOnFileStore(page, fileStore);
				revealLocationInFile(editorPart);
			} catch (PartInitException e) {
				e.printStackTrace();
				// Put your exception handler here if you wish to
			} catch (CoreException e) {
				e.printStackTrace();
				// Put your exception handler here if you wish to
			}

		}

	}

	private void revealLocationInFile(IEditorPart editorPart)
			throws CoreException {
		if (editorPart instanceof ITextEditor && locationText.line > 0) {
			ITextEditor textEditor = (ITextEditor) editorPart;
			IDocumentProvider provider = textEditor.getDocumentProvider();
			IEditorInput editorInput = editorPart.getEditorInput();
			provider.connect(editorInput);
			IDocument document = provider.getDocument(editorInput);
			try {
				IRegion regionOfLine = document.getLineInformation(locationText.line - 1);
				// only used to reveal the location
				textEditor.selectAndReveal(regionOfLine.getOffset(), regionOfLine.getLength());
				int startOffset = regionOfLine.getOffset() + locationText.column - 1;
				int length = regionOfLine.getLength() - locationText.column;
				if (startOffset >= document.getLength()) {
					startOffset = document.getLength() - 1;
				}
				if (length + startOffset >= document.getLength()) {
					length = document.getLength() - startOffset - 1;
				}
				textEditor.setHighlightRange(startOffset, length, true);
			} catch (BadLocationException e) {
				MessageDialog.openInformation(N4JSGracefulActivator.getActiveWorkbenchShell(),
						ConsoleMessages.msgInvalidLineNumberTitle(),
						ConsoleMessages.msgInvalidLineNumberIn(
								(locationText.line) + "",
								locationText.fileName));
			}
			provider.disconnect(editorInput);
		}
	}

}
