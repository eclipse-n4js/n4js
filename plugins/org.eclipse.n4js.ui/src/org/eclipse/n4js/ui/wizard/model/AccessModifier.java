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
package org.eclipse.n4js.ui.wizard.model;

/**
 * Enumeration types for access modifiers.
 */
public enum AccessModifier {

	/**
	 * For public visibility.
	 */
	PUBLIC,

	/**
	 * For project visibility.
	 */
	PROJECT,

	/**
	 * For private visibility
	 */
	PRIVATE;

	/**
	 * Returns the representation of the modifier as it is used in N4JS code.
	 *
	 */
	public String toCodeRepresentation() {
		switch (this) {
		case PROJECT:
			return "project";
		case PUBLIC:
			return "public";
		default: // PRIVATE:
			return "";
		}
	}
}
