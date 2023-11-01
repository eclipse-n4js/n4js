/**
 * Copyright (c) 2023 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt;

import org.eclipse.xtext.util.ITextRegion;

public class TextRegion implements ITextRegion {
	final int offset;
	final int length;

	public TextRegion(int offset, int length) {
		this.offset = offset;
		this.length = length;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public int getLength() {
		return length;
	}

	public int getEndOffset() {
		return offset + length;
	}

	@Override
	public ITextRegion merge(ITextRegion region) {
		throw new IllegalAccessError();
	}

	@Override
	public boolean contains(ITextRegion other) {
		throw new IllegalAccessError();
	}

	@Override
	public boolean contains(int _offset) {
		throw new IllegalAccessError();
	}

}
