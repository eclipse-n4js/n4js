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
package org.eclipse.n4js.parser.conversion;

import org.eclipse.xtext.conversion.impl.STRINGValueConverter;

/**
 */
public class HexChars {

	private static class Accessible extends STRINGValueConverter {
		private static final Accessible INSTANCE = new Accessible();

		@Override
		protected boolean isHexSequence(char[] in, int off, int chars) {
			return super.isHexSequence(in, off, chars);
		}
	}

	/**
	 * Returns true if the given array of chars contains at least {@code chars} valid hex characters starting at offset
	 * {@code off};
	 */
	public static boolean isHexSequence(char[] in, int off, int chars) {
		// not enough chars available - return false
		if (off + chars > in.length)
			return false;
		return Accessible.INSTANCE.isHexSequence(in, off, chars);
	}

}
