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
package org.eclipse.n4js;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class TypeExpressionsRuntimeModule extends AbstractTypeExpressionsRuntimeModule {

	/***/
	// contributed by org.eclipse.xtext.generator.formatting2.Formatter2Fragment
	public Class<? extends org.eclipse.xtext.formatting2.IFormatter2> bindIFormatter2() {
		return org.eclipse.n4js.formatting2.TypeExpressionFormatterNoOp.class;
	}
}
