/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.imports;

import java.util.Objects;

import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;

/**
 * Can be removed when https://github.com/eclipse/xtext-core/pull/1355 was merged
 */
public class XReplaceRegion extends ReplaceRegion {

	/** Constructor */
	public XReplaceRegion(ITextRegion region, String text) {
		super(region, text);
	}

	/** Constructor */
	public XReplaceRegion(int offset, int length, String text) {
		super(offset, length, text);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ReplaceRegion) {
			ReplaceRegion rr = (ReplaceRegion) object;

			boolean equals = true;
			equals = equals && Objects.equals(getOffset(), rr.getOffset());
			equals = equals && Objects.equals(getLength(), rr.getLength());
			equals = equals && Objects.equals(getText(), rr.getText());
			return equals;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getOffset(), getLength(), getText());
	}

}
