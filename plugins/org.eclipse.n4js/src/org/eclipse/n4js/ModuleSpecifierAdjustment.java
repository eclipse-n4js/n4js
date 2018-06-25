/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js;

/**
 * Data class for defining how the transpiler should adjust module specifiers depending on module loader supported by
 * the target module (i.e. the module imported from).
 */
public final class ModuleSpecifierAdjustment {
	/** Prefix to be added to the module specifier, e.g. "@node". */
	public final String prefix;
	/**
	 * Normally the output code contains complete module specifiers for imported modules; if this is set to
	 * <code>true</code> the transpiler will emit a plain module specifier (without project name) instead.
	 */
	public final boolean usePlainModuleSpecifier;

	/**
	 * See {@link ModuleSpecifierAdjustment} for details.
	 */
	public ModuleSpecifierAdjustment(String prefix, boolean usePlainModuleSpecifier) {
		if (prefix == null)
			throw new IllegalArgumentException("prefix may not be null (but you may use an empty string)");
		this.prefix = prefix;
		this.usePlainModuleSpecifier = usePlainModuleSpecifier;
	}
}
