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
package org.eclipse.n4js.ui.editor.autoedit;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.xtext.ui.editor.autoedit.SingleLineTerminalsStrategy.StrategyPredicate;

/**
 * Suppresses the closing bracket if the next char is a digit. This is used in regular expressions for exact
 * quantifiers.
 */
public class IntStrategyPredicate implements StrategyPredicate {

	@Override
	public boolean isInsertClosingBracket(IDocument doc, int offset) throws BadLocationException {
		if (doc.getLength() <= offset)
			return true;
		char charAtOffset = doc.getChar(offset);
		boolean result = !Character.isDigit(charAtOffset);
		return result;
	}

}
