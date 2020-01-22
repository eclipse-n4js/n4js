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
package org.eclipse.n4js.ide.editor.contentassist.imports;

/**
 * Offsets of alias declaration in an import statement
 */
class AliasLocation {
	int baseOffset;
	int relativeOffset;
	String alias;

	/** Constructor */
	public AliasLocation(int baseOffset, int relativeOffset, String alias) {
		this.baseOffset = baseOffset;
		this.relativeOffset = relativeOffset;
		this.alias = alias;
	}

	int getBaseOffset() {
		return baseOffset;
	}

	void setBaseOffset(int baseOffset) {
		this.baseOffset = baseOffset;
	}

	int getRelativeOffset() {
		return relativeOffset;
	}

	void setRelativeOffset(int relativeOffset) {
		this.relativeOffset = relativeOffset;
	}

	String getAlias() {
		return alias;
	}

	void setAlias(String alias) {
		this.alias = alias;
	}

}
