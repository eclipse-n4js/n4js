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
package org.eclipse.n4js.ui.console;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
//import org.eclipse.jdt.internal.debug.ui.JDIDebugUIPlugin;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.n4js.transpiler.sourcemap.MappingEntry;
import org.eclipse.n4js.transpiler.sourcemap.SourceMap;
import org.eclipse.n4js.transpiler.sourcemap.SourceMapFileLocator;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.n4js.ui.internal.N4JSGracefulActivator;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import com.google.inject.Inject;

/**
 * Extracts link text and scans for javascript file, the source map and the original source.
 *
 * Instances must be injected, usually via a provider. After creating an instance, the console must be set.
 */
public class N4JSStackTraceHyperlink implements IHyperlink {

	@Inject
	private SourceMapFileLocator sourceMapFileLocator;

	private TextConsole console;

	/**
	 * Needs to be called directly after construction.
	 */
	public void setTextConsole(TextConsole console) {
		this.console = console;
	}

	/**
	 * Returns the console this link is contained in.
	 *
	 * @return console
	 */
	protected TextConsole getConsole() {
		return console;
	}

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleHyperlink#linkEntered()
	 */
	@Override
	public void linkEntered() {
		// we do nothing here
	}

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleHyperlink#linkExited()
	 */
	@Override
	public void linkExited() {
		// we do nothing here
	}

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleHyperlink#linkActivated()
	 */
	@Override
	public void linkActivated() {
		JSStackTraceLocationText locationText;
		try {
			String linkText = getLinkText();
			locationText = new JSStackTraceLocationText(linkText);
		} catch (CoreException e1) {
			ErrorDialog.openError(
					N4JSGracefulActivator.getActiveWorkbenchShell(),
					ConsoleMessages.msgHyperlinkError(),
					ConsoleMessages.msgHyperlinkError(),
					e1.getStatus());
			return;
		}

		startSourceSearch(locationText);
	}

	/**
	 * Searches the JS and N4JS file in the workspace.
	 */
	protected void startSourceSearch(final JSStackTraceLocationText jsLink) {
		Job search = new Job(ConsoleMessages.msgHyperlinkSearching()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {

				File file = new File(jsLink.fileName);
				if (file.exists() && file.isFile()) {
					try {
						Path path = file.toPath();
						File sourceMapFile = sourceMapFileLocator.resolveSourceMapFromGen(path);
						SourceMap sourceMap = SourceMap.loadAndResolve(sourceMapFile.toPath());
						MappingEntry mappingEntry = sourceMap.findMappingForGenPosition(jsLink.line - 1,
								jsLink.column - 1);

						Path sourcePath = sourceMap.getResolvedSources().get(mappingEntry.srcIndex);
						searchCompleted(sourcePath.toFile().getAbsolutePath(), mappingEntry.srcLine,
								mappingEntry.srcColumn);
					} catch (IOException ex) {
						searchCompleted(jsLink.fileName, jsLink.line - 1, jsLink.column - 1);
					}

				} else {
					// Do something if the file does not exist
				}
				return Status.OK_STATUS;
			}

		};
		search.schedule();
	}

	/**
	 * Reported back to when results are found
	 *
	 * @param line
	 *            0-based line number
	 * @param column
	 *            0-based column
	 */
	protected void searchCompleted(final String fileName, final int line, final int column) {
		UIJob job = new UIJob("link search complete") { //$NON-NLS-1$
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {

				File file = new File(fileName);

				if (!file.exists()) {
					// did not find source
					MessageDialog.openInformation(N4JSGracefulActivator.getActiveWorkbenchShell(),
							ConsoleMessages.msgSourceNotFoundTitle(),
							ConsoleMessages.msgSourceNotFoundFor(fileName));
				} else {
					IFileStore fileStore = EFS.getLocalFileSystem().getStore(file.toURI());
					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					try {
						IEditorPart editorPart = IDE.openEditorOnFileStore(page, fileStore);
						revealLocationInFile(fileName, line, column, editorPart);
					} catch (PartInitException e) {
						// Put your exception handler here if you wish to
					} catch (CoreException e) {

						N4JSGracefulActivator.statusDialog(e.getStatus());
					}

				}

				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.schedule();
	}

	private void revealLocationInFile(String typeName, int line, int column, IEditorPart editorPart)
			throws CoreException {
		if (editorPart instanceof ITextEditor && line >= 0) {
			ITextEditor textEditor = (ITextEditor) editorPart;
			IDocumentProvider provider = textEditor.getDocumentProvider();
			IEditorInput editorInput = editorPart.getEditorInput();
			provider.connect(editorInput);
			IDocument document = provider.getDocument(editorInput);
			try {
				IRegion regionOfLine = document.getLineInformation(line);
				// only used to reveal the location
				textEditor.selectAndReveal(regionOfLine.getOffset(), regionOfLine.getLength());
				int startOffset = regionOfLine.getOffset() + column;
				int length = regionOfLine.getLength() - column;
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
								(line + 1) + "",
								typeName));
			}
			provider.disconnect(editorInput);
		}
	}

	/**
	 * Returns this link's text
	 *
	 * @return the complete text of the link, never <code>null</code>
	 * @exception CoreException
	 *                if unable to retrieve the text
	 */
	protected String getLinkText() throws CoreException {
		try {
			IDocument document = getConsole().getDocument();
			IRegion region = getConsole().getRegion(this);
			int regionOffset = region.getOffset();

			int lineNumber = document.getLineOfOffset(regionOffset);
			IRegion lineInformation = document.getLineInformation(lineNumber);
			int lineOffset = lineInformation.getOffset();
			String line = document.get(lineOffset, lineInformation.getLength());

			int regionOffsetInLine = regionOffset - lineOffset;

			int linkEnd = line.indexOf(')', regionOffsetInLine);
			int linkStart = line.lastIndexOf(' ', regionOffsetInLine);

			return line.substring(linkStart == -1 ? 0 : linkStart + 1, linkEnd + 1);
		} catch (BadLocationException e) {
			IStatus status = new Status(IStatus.ERROR, N4JSActivator.PLUGIN_ID, 0,
					ConsoleMessages.msgUnableToParseLinkText(), e);
			throw new CoreException(status);
		}
	}

}
