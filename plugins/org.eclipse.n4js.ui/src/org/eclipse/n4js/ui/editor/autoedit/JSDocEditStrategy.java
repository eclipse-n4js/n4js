/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.editor.autoedit;

import org.eclipse.xtext.ui.editor.autoedit.MultiLineTerminalsEditStrategy;

public class JSDocEditStrategy extends MultiLineTerminalsEditStrategy {

	public JSDocEditStrategy(String leftTerminal, String indentationString, String rightTerminal) {
		super(leftTerminal, indentationString, rightTerminal, false);
	}

}
