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
 * Transformation assistants are helper classes for AST transformations.
 * <p>
 * Just as transformations, they can use convenience delegation methods to access the transpiler state without the need
 * to always pass around the state object. The difference to transformations is that assistants are never invoked by the
 * transpiler directly (thus they do not have a {@link Transformation#transform() transform()} method) but only if some
 * other transformation injects and invokes them.
 * <p>
 * Transformation assistants may use dependency injection (i.e. instances will be created via an appropriate injector).
 */
public abstract class TransformationAssistant extends TranspilerComponent {
	// empty
}
