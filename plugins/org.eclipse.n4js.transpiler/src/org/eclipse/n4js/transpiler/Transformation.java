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
package org.eclipse.n4js.transpiler;

/**
 * Abstract base class for all AST transformations used by the transpiler to gradually transform the intermediate model
 * into the representation of the output code.
 * <p>
 * Transformations may use dependency injection (i.e. instances will be created via an appropriate injector). In
 * particular, they may inject one or more {@link TransformationAssistant}s.
 */
public abstract class Transformation extends TranspilerComponent {

	/**
	 * The transpiler will invoke this method for all transformations on the initial transpiler state, i.e. before any
	 * changes have been applied to the intermediate model.
	 * <p>
	 * This gives sub classes a chance to perform some early analysis up-front, store the information obtained in a
	 * private field and access this information when the main {@link #transform()} method is being invoked.
	 * <p>
	 * <b>IMPORTANT: this method may not change the transpiler state!</b>
	 */
	public abstract void analyze();

	/**
	 * Main transformation method that actually performs the modifications on the intermediate model of the transpiler
	 * state obtained via {@link #getState()}.
	 * <p>
	 * This is the only method in this class that may alter the transpiler state (and is expected to do so).
	 */
	public abstract void transform();

	/**
	 * Should be implemented to assert pre conditions for this transformation. Will only be invoked during debugging,
	 * not in the shipped product.
	 * <p>
	 * <b>IMPORTANT: this method may not change the transpiler state!</b>
	 */
	public abstract void assertPreConditions();

	/**
	 * Should be implemented to assert post conditions for this transformation. Will only be invoked during debugging,
	 * not in the shipped product.
	 * <p>
	 * <b>IMPORTANT: this method may not change the transpiler state!</b>
	 */
	public abstract void assertPostConditions();
}
