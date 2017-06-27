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
package org.eclipse.n4js.formatting2;

import org.eclipse.xtext.formatting2.IHiddenRegionFormatting;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;

/** */
@SuppressWarnings("restriction")
public class N4WhitespaceReplacer extends WhitespaceReplacer {

	/** */
	public N4WhitespaceReplacer(ITextSegment whitespace, IHiddenRegionFormatting formatting) {
		super(whitespace, formatting);
	}

	@Override
	public ITextReplacerContext createReplacements(ITextReplacerContext context) {
		return super.createReplacements(context);
	}

	@Override
	protected int computeNewLineCount(ITextReplacerContext context) {
		// In case no information is configured, we do not want to swallow any lines (super give 0 here):
		IHiddenRegionFormatting formatting = getFormatting();
		if (formatting.getNewLineDefault() == null
				&& formatting.getNewLineMin() == null
				&& formatting.getNewLineMax() == null) {
			// return the actual newlines:
			return getRegion().getLineCount() - 1;
		}

		// all other cases are handled as always:
		return super.computeNewLineCount(context);
	}
}
