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
package org.eclipse.n4js.typesystem.utils;

import org.eclipse.n4js.scoping.builtin.GlobalObjectScope;
import org.eclipse.n4js.scoping.builtin.VirtualBaseTypeScope;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;

/**
 * Facade class for accessing built-in types and global object from rule environment.
 */
public class PredefinedTypes {

	/**
	 * Key used to store predefined types in {@link RuleEnvironment}, see {@link RuleEnvironmentExtensions} for details.
	 */
	public final static Object PREDEFINED_TYPES_KEY = PredefinedTypes.class;

	/**
	 * Built in types, such as null, undef, any, number, string
	 */
	public final BuiltInTypeScope builtInTypeScope;
	/**
	 * Global object type.
	 */
	public final GlobalObjectScope globalObjectScope;

	/**
	 * Virtual types, mainly to access the ArgumentsType
	 */
	public final VirtualBaseTypeScope virtualBaseTypeScope;

	/**
	 * Creates facade with given scopes.
	 */
	public PredefinedTypes(BuiltInTypeScope builtInTypeScope, GlobalObjectScope globalObjectScope,
			VirtualBaseTypeScope virtualBaseTypeScope) {
		super();
		this.builtInTypeScope = builtInTypeScope;
		this.globalObjectScope = globalObjectScope;
		this.virtualBaseTypeScope = virtualBaseTypeScope;
	}

	@Override
	public String toString() {
		return "PredefinedTypes";
	}

}
