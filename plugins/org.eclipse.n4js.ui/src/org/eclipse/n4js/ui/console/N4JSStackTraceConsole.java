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

/**
 * Based on {@code org.eclipse.jdt.internal.debug.ui.console.JavaStackTraceConsole}. Users can open this console and
 * just paste any text into it. Via extension point the {@link org.eclipse.n4js.ui.console.N4JSExceptionConsoleTracker}
 * is registered and will detect hyperlinks in the text, i.e. stacktrace information.
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
	 * Constructor creating the console with the console font.
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
	 * Loads the text previously pasted into the console and stored via {@link #saveDocument()}.
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
	 * Saves the pasted text into a hidden document so that it will be available the next time the console will be
	 * opened.
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
}
