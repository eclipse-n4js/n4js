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
package org.eclipse.n4js.ui.formatting2;

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionAccess;
import org.eclipse.xtext.formatting2.regionaccess.ITextReplacement;
import org.eclipse.xtext.ui.editor.formatting2.ContentFormatter;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

/**
 * Use a single {@link ReplaceEdit} instead of a {@link MultiTextEdit} for performance reasons.
 *
 * See https://github.com/NumberFour/n4js/issues/246 for details.
 *
 */
@SuppressWarnings("restriction")
public class FixedContentFormatter extends ContentFormatter {

	@Override
	protected TextEdit createTextEdit(List<ITextReplacement> replacements) {
		if (replacements.isEmpty()) {
			return new ReplaceEdit(0, 0, "");
		}
		ITextRegionAccess regionAccess = replacements.get(0).getTextRegionAccess();
		String newDocument = regionAccess.getRewriter().renderToString(replacements);
		return new ReplaceEdit(0, regionAccess.regionForDocument().getLength(), newDocument);
	}

	/**
	 * This method makes sure that no changes are applied (no dirty state), if there are no changes. This fixes bug
	 * GHOLD-272
	 */
	@Override
	public void format(IDocument document, IRegion region) {
		IXtextDocument doc = (IXtextDocument) document;
		TextEdit e = doc.priorityReadOnly(new FormattingUnitOfWork(doc, region));

		if (e == null)
			return;
		if (e instanceof ReplaceEdit) {
			ReplaceEdit r = (ReplaceEdit) e;
			if ((r.getOffset() == 0) && (r.getLength() == 0) && (r.getText().isEmpty())) {
				return;
			}
		}
		try {
			e.apply(document);
		} catch (BadLocationException ex) {
			throw new RuntimeException(ex);
		}

	}
}
