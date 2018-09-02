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
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.internal.ui.views.console.ProcessConsole;
import org.eclipse.jdt.internal.debug.ui.JDIDebugUIPlugin;
//import org.eclipse.jdt.internal.debug.ui.JDIDebugUIPlugin;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.n4js.transpiler.sourcemap.MappingEntry;
import org.eclipse.n4js.transpiler.sourcemap.SourceMap;
import org.eclipse.n4js.transpiler.sourcemap.SourceMapFileLocator;
import org.eclipse.n4js.ui.internal.N4JSGracefulActivator;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import com.google.inject.Inject;

/**
 * Extracts link text and scans for javascript file, the source map and the original source. Instances must be injected,
 * usually via a provider. After creating an instance, the console must be set.
 *
 * @see org.eclipse.jdt.internal.debug.ui.console.JavaStackTraceHyperlink
 */
public class N4JSStackTraceHyperlink implements IHyperlink {

	public static class JSLinkData {

		Pattern linkPattern = Pattern.compile("\\((.*):([0-9]+):([0-9]+)\\)");

		/**
		 * @param linkText
		 *            "(/absolute/path.js:line:col)"
		 */
		public JSLinkData(String linkText) {
			Matcher matcher = linkPattern.matcher(linkText);
			if (matcher.matches()) {
				fileName = matcher.group(1);
				line = Integer.parseInt(matcher.group(2)) - 1; // 0 based
				column = Integer.parseInt(matcher.group(3));
			}
		}

		public String fileName;
		public int line;
		public int column;
	}

	@Inject
	private SourceMapFileLocator sourceMapFileLocator;

	private TextConsole fConsole;

	/**
	 * Needs to be called directly after construction.
	 */
	public void setTextConsole(TextConsole console) {
		this.fConsole = console;
		IConsoleManager man = ConsolePlugin.getDefault().getConsoleManager();
		IConsole[] cons = man.getConsoles();
		ProcessConsole con = null;
		for (IConsole c : cons) {
			if (c instanceof ProcessConsole) {
				con = (ProcessConsole) c;
				break;
			}
		}
		// org.eclipse.debug.ui.processTypeTest
		// fType = org.eclipse.debug.ui.ProcessConsoleType
		// attributes.put(IProcess.ATTR_PROCESS_TYPE, IAntLaunchConstants.ID_ANT_PROCESS_TYPE);
	}

	/**
	 * Returns the console this link is contained in.
	 *
	 * @return console
	 */
	protected TextConsole getConsole() {
		return fConsole;
	}

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleHyperlink#linkEntered()
	 */
	@Override
	public void linkEntered() {
	}

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleHyperlink#linkExited()
	 */
	@Override
	public void linkExited() {
	}

	/**
	 * @see org.eclipse.debug.ui.console.IConsoleHyperlink#linkActivated()
	 */
	@Override
	public void linkActivated() {
		JSLinkData jsLink;
		try {
			String linkText = getLinkText();
			jsLink = new JSLinkData(linkText);
		} catch (CoreException e1) {
			ErrorDialog.openError(
					N4JSGracefulActivator.getActiveWorkbenchShell(),
					ConsoleMessages.msgHyperlinkError(),
					ConsoleMessages.msgHyperlinkError(),
					e1.getStatus());
			return;
		}

		startSourceSearch(jsLink);
	}

	boolean showOriginal() {
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(N4JSConsolePreferenceKeys.N4JS_CONSOLE_PREF_NODE);
		String value = prefs.get(N4JSConsolePreferenceKeys.TRACE_LINK_TO_ORIGINAL, "true");
		return Boolean.parseBoolean(value);
	}

	/**
	 * Searches the JS and N4JS file in the workspace.
	 */
	protected void startSourceSearch(final JSLinkData jsLink) {
		Job search = new Job(ConsoleMessages.msgHyperlinkSearching()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {

				File file = new File(jsLink.fileName);
				if (file.exists() && file.isFile()) {
					try {
						if (showOriginal()) {
							Path path = file.toPath();
							File sourceMapFile = sourceMapFileLocator.resolveSourceMapFromGen(path);
							SourceMap sourceMap = SourceMap.loadAndResolve(sourceMapFile.toPath());
							MappingEntry mappingEntry = sourceMap.findMappingForGenPosition(jsLink.line, jsLink.column);

							Path sourcePath = sourceMap.getResolvedSources().get(mappingEntry.srcIndex);
							searchCompleted(sourcePath.toFile().getAbsolutePath(), mappingEntry.srcLine);
						} else {
							searchCompleted(jsLink.fileName, jsLink.line);
						}
					} catch (Exception ex) {
						searchCompleted(jsLink.fileName, jsLink.line);
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
	 */
	protected void searchCompleted(final String fileName, final int line) {
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
						revealLocationInFile(fileName, line, editorPart);
					} catch (PartInitException e) {
						// Put your exception handler here if you wish to
					} catch (CoreException e) {
						JDIDebugUIPlugin.statusDialog(e.getStatus());
					}

				}

				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.schedule();
	}

	private void revealLocationInFile(String typeName, int lineNumber, IEditorPart editorPart)
			throws CoreException {
		if (editorPart instanceof ITextEditor && lineNumber >= 0) {
			ITextEditor textEditor = (ITextEditor) editorPart;
			IDocumentProvider provider = textEditor.getDocumentProvider();
			IEditorInput editorInput = editorPart.getEditorInput();
			provider.connect(editorInput);
			IDocument document = provider.getDocument(editorInput);
			try {
				IRegion line = document.getLineInformation(lineNumber);
				textEditor.selectAndReveal(line.getOffset(), line.getLength());
			} catch (BadLocationException e) {
				MessageDialog.openInformation(N4JSGracefulActivator.getActiveWorkbenchShell(),
						ConsoleMessages.msgInvalidLineNumberTitle(),
						ConsoleMessages.msgInvalidLineNumberIn(
								(lineNumber + 1) + "",
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
			IStatus status = new Status(IStatus.ERROR, JDIDebugUIPlugin.getUniqueIdentifier(), 0,
					ConsoleMessages.msgUnableToParseLinkText(), e);
			throw new CoreException(status);
		}
	}

}
