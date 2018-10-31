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

import org.eclipse.n4js.ts.types.Type;

/**
 * Provides replacements for types that will be considered by the type system in subtype checks (and only in subtype
 * checks). Instances have to be registered in a rule environment via method
 * {@link RuleEnvironmentExtensions#setTypeReplacement(RuleEnvironment, ITypeReplacementProvider)}.
 */
public interface ITypeReplacementProvider {

	/**
	 * Return replacement type for given type or <code>null</code> if the given type is not to be replaced.
	 * <p>
	 * This method may or may not be called again by client code for a type returned by this method and in this case
	 * this method should return <code>null</code>, so the set of types for which a replacement exists and the set of
	 * types that serve as replacements must be disjoint. In other words, for all types T it must hold:
	 *
	 * <pre>
	 * getReplacement(T) == T'  ==>  getReplacement(T') == null
	 * </pre>
	 */
	<T extends Type> T getReplacement(T type);
}
