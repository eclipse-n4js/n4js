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

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.console.IConsoleDocumentPartitioner;
import org.eclipse.ui.console.TextConsole;

/**
 *
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

	public final static String CONSOLE_TYPE = "n4jsStackTraceConsole"; //$NON-NLS-1$

	private final N4JSStackTraceConsolePartitioner partitioner = new N4JSStackTraceConsolePartitioner();

	/**
	 * Constructor
	 */
	public N4JSStackTraceConsole() {
		super("N4JS Stack Trace Console", CONSOLE_TYPE, null, true);
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
		// File file = new File(FILE_NAME);
		// if (file.exists()) {
		// try (InputStream fin = new BufferedInputStream(new FileInputStream(file))) {
		// int len = (int) file.length();
		// byte[] b = new byte[len];
		// int read = 0;
		// while (read < len) {
		// read += fin.read(b);
		// }
		// getDocument().set(new String(b));
		// } catch (IOException e) {
		// }
		// } else {
		// getDocument().set(ConsoleMessages.JavaStackTraceConsole_0);
		// }

	}

	/**
	 *
	 */
	public void saveDocument() {
		// TODO Auto-generated method stub

	}

}
