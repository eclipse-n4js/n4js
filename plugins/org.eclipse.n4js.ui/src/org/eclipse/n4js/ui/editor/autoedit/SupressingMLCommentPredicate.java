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
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.xtext.ui.editor.autoedit.SingleLineTerminalsStrategy;
import org.eclipse.xtext.ui.editor.autoedit.SingleLineTerminalsStrategy.StrategyPredicate;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;

import org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeToPartitionMapper;

/**
 */
public class SupressingMLCommentPredicate implements StrategyPredicate {

	@Override
	public boolean isInsertClosingBracket(IDocument doc, int offset) throws BadLocationException {
		if (offset >= 2) {
			ITypedRegion prevPartition = doc.getPartition(offset - 1);
			String prevPartitionType = prevPartition.getType();
			if (TerminalsTokenTypeToPartitionMapper.SL_COMMENT_PARTITION.equals(prevPartitionType)) {
				return false;
			}
			if (TokenTypeToPartitionMapper.REG_EX_PARTITION.equals(prevPartitionType)) {
				return prevPartition.getLength() == 1;
			}
		}
		return SingleLineTerminalsStrategy.DEFAULT.isInsertClosingBracket(doc, offset);
	}

}
