/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.sourcemap;

import java.io.IOException;

/**
 * TODO: IDE-2203
 */
public class SourceMapGeneratorDummy implements SourceMapGenerator {

	@Override
	public void appendTo(Appendable appOut, String fileName) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMapping(String srcName, String symbolName, FilePosition srcStartPos, FilePosition outStartPos,
			FilePosition outEndPos) {
		// TODO Auto-generated method stub

	}

}
