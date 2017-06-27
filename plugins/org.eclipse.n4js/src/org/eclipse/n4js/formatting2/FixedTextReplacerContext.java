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

import java.lang.reflect.Field;

import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.ITextReplacer;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.TextReplacerContext;
import org.eclipse.xtext.formatting2.regionaccess.ITextReplacement;

import com.google.common.base.Preconditions;

/**
 * workaround for https://github.com/eclipse/xtext-core/issues/14
 */
@SuppressWarnings("restriction")
public class FixedTextReplacerContext extends TextReplacerContext {

	/***/
	public FixedTextReplacerContext(IFormattableDocument document) {
		super(document);
	}

	private boolean isIdentityEdit(ITextReplacement repl) {
		return repl.getReplacementText().equals(repl.getText());
	}

	@Override
	public void addReplacement(ITextReplacement replacement) {
		if (getDocument().getRequest().allowIdentityEdits() || !isIdentityEdit(replacement)) {
			super.addReplacement(replacement);
		}
	}

	/***/
	public FixedTextReplacerContext(IFormattableDocument document, ITextReplacerContext previous, int indentation,
			ITextReplacer replacer) {
		super(document, previous, indentation, replacer);
	}

	private boolean isNextReplacerIsChild() {
		try {
			Field field = TextReplacerContext.class.getDeclaredField("nextReplacerIsChild");
			field.setAccessible(true);
			return field.getBoolean(this);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ITextReplacerContext withDocument(IFormattableDocument document) {
		TextReplacerContext context = new FixedTextReplacerContext(document, this, getIndentation(), null);
		if (this.isNextReplacerIsChild())
			context.setNextReplacerIsChild();
		return context;
	}

	@Override
	public ITextReplacerContext withIndentation(int indentation) {
		return new FixedTextReplacerContext(getDocument(), this, indentation, null);
	}

	@Override
	public ITextReplacerContext withReplacer(ITextReplacer replacer) {
		ITextReplacerContext current = this;
		while (current != null) {
			ITextReplacer lastReplacer = current.getReplacer();
			if (lastReplacer != null) {
				if (isNextReplacerIsChild()) {
					Preconditions.checkArgument(lastReplacer.getRegion().contains(replacer.getRegion()));
				} else {
					Preconditions
							.checkArgument(lastReplacer.getRegion().getEndOffset() <= replacer.getRegion().getOffset());
				}
				break;
			}
			current = current.getPreviousContext();
		}
		return new FixedTextReplacerContext(getDocument(), this, getIndentation(), replacer);
	}

}
