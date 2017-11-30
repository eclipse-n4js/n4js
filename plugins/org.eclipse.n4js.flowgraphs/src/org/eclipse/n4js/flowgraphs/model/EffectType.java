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
package org.eclipse.n4js.flowgraphs.model;

/**
 * Defines the type of an effect on a variable.
 * <p/>
 * Note that the term <i>used variable</i> represents all effect types except for {@link #Declaration}.
 */
public enum EffectType {

	/** Happens when a variable is declared */
	Declaration,
	/** Happens when a variable is written/defined */
	Write,
	/** Happens when a variable is read */
	Read,
	/** Happens when a variable is the receiver of a method call */
	MethodCall,
	/** Happens when a variable is read in an instanceof expression */
	TypeGuard

}
