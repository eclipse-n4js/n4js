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
package org.eclipse.n4js.ui.wizard.generator;

/**
 * A {@link ContentBlock} represents a paragraph in a wizard content preview.
 *
 * It additionally holds a state which toggles syntax highlighting for the paragraph.
 */
public final class ContentBlock {
	/** The active state of the block */
	public final boolean highlighted;
	/** The content of the block */
	public final String content;

	/** Returns a new active content block with the given content */
	public static ContentBlock highlighted(String content) {
		return new ContentBlock(content, true);
	}

	/** Returns a new inactive content block with the given content */
	public static ContentBlock unhighlighted(String content) {
		return new ContentBlock(content, false);
	}

	private ContentBlock(String content, boolean highlighted) {
		this.highlighted = highlighted;
		this.content = content;
	}

	@Override
	public String toString() {
		return "{active=" + highlighted + ", content='" + content + "'}";
	}
}
