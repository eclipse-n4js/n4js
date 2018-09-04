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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.n4js.ui.internal.N4JSActivator;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.console.IConsoleDocumentPartitioner;
import org.eclipse.ui.console.TextConsole;
import org.eclipse.ui.progress.WorkbenchJob;

/**
 * Based on {@code org.eclipse.jdt.internal.debug.ui.console.JavaStackTraceConsole}
 */
public class N4JSStackTraceConsole extends TextConsole {

	/**
	 * Provides a partitioner for this console type.
	 */
	class N4JSStackTraceConsolePartitioner extends FastPartitioner implements IConsoleDocumentPartitioner {

		public N4JSStackTraceConsolePartitioner() {
			super(new RuleBasedPartitionScanner(), null);
			getDocument().setDocumentPartitioner(this);
		}

		@Override
		public boolean isReadOnly(int offset) {
			return false;
		}

		@Override
		public StyleRange[] getStyleRanges(int offset, int length) {
			return null;
		}

	}

	/**
	 * Console type, used in plugin.xml to connect the hyper link tracker.
	 */
	public final static String CONSOLE_TYPE = "n4jsStackTraceConsole"; //$NON-NLS-1$

	/**
	 * Name of the temporary file containing the console text.
	 */
	private final static String FILE_NAME = N4JSActivator.getInstance().getStateLocation().toOSString() + File.separator
			+
			CONSOLE_TYPE + ".txt"; //$NON-NLS-1$

	private final N4JSStackTraceConsolePartitioner partitioner = new N4JSStackTraceConsolePartitioner();

	/**
	 * Constructor
	 */
	public N4JSStackTraceConsole() {
		super(ConsoleMessages.msgN4JSStackTraceConsole(), CONSOLE_TYPE, null, true);
		Font font = JFaceResources.getFont(IDebugUIConstants.PREF_CONSOLE_FONT);
		setFont(font);
		partitioner.connect(getDocument());
	}

	@Override
	protected IConsoleDocumentPartitioner getPartitioner() {
		return partitioner;
	}

	/**
	 *
	 */
	public void initializeDocument() {
		File file = new File(FILE_NAME);
		if (file.exists()) {
			try (InputStream fin = new BufferedInputStream(new FileInputStream(file))) {
				int len = (int) file.length();
				byte[] b = new byte[len];
				int read = 0;
				while (read < len) {
					read += fin.read(b);
				}
				getDocument().set(new String(b));
			} catch (IOException e) {
				// just ignore that, not important
			}
		} else {
			getDocument().set(ConsoleMessages.msgConsoleCallToAction());
		}

	}

	/**
	 *
	 */
	public void saveDocument() {
		try (FileOutputStream fout = new FileOutputStream(FILE_NAME)) {
			IDocument document = getDocument();
			if (document != null) {
				if (document.getLength() > 0) {
					String contents = document.get();
					fout.write(contents.getBytes());
				} else {
					File file = new File(FILE_NAME);
					file.delete();
				}
			}
		} catch (IOException e) {
			// just ignore that, not important
		}
	}

	/**
	 *
	 */
	public void useSourceMap() {
		WorkbenchJob job = new WorkbenchJob("Location Translator with Source Maps") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				IJobManager jobManager = Job.getJobManager();
				try {
					jobManager.join(this, monitor);
				} catch (OperationCanceledException e1) {
					return Status.CANCEL_STATUS;
				} catch (InterruptedException e1) {
					return Status.CANCEL_STATUS;
				}
				IDocument document = getDocument();
				String orig = document.get();
				if (orig != null && orig.length() > 0) {
					document.set(useSourceMap(orig));
				}

				return Status.OK_STATUS;
			}

		};
		job.setSystem(true);
		job.schedule();

	}

	/**
	 * Replaces...
	 *
	 * Sample stack trace which can be handled:
	 *
	 * <pre>
	 * <code>
	 * (SystemJS) error thrown
	 * 	Error: error thrown
	 * 	    at SVDemo.foo___n4 (/ws/SVDemo/src-gen/pac/SVDemo.js:16:14)
	 * 	    at execute (/ws/SVDemo/src-gen/pac/SVDemo.js:39:18)
	 * 	Error loading SVDemo/src-gen/pac/SVDemo
	 * </code>
	 * </pre>
	 */
	private String useSourceMap(String origStackTrace) {
		return origStackTrace.replaceAll("\\.js:", ".n4js:");
	}
}
