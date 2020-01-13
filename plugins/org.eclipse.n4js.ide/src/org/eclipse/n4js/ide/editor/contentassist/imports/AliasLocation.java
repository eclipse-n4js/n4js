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

	public int getBaseOffset() {
		return baseOffset;
	}

	public void setBaseOffset(int baseOffset) {
		this.baseOffset = baseOffset;
	}

	public int getRelativeOffset() {
		return relativeOffset;
	}

	public void setRelativeOffset(int relativeOffset) {
		this.relativeOffset = relativeOffset;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
