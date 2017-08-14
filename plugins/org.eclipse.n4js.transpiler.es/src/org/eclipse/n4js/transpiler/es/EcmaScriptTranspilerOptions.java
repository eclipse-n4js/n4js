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
package org.eclipse.n4js.transpiler.es;

import org.eclipse.n4js.transpiler.TranspilerOption;

/**
 *
 */
public interface EcmaScriptTranspilerOptions {

	interface LetConst extends TranspilerOption {
	}

	interface AsyncAwait extends TranspilerOption {
	}

	interface TemplateStringLiterals extends TranspilerOption {
	}

	interface ArrowFunctions extends TranspilerOption {
	}

	interface Destructuring extends TranspilerOption {
	}

	interface RestParameters extends TranspilerOption {
	}

	interface ES5plus extends
			LetConst,
			AsyncAwait,
			TemplateStringLiterals,
			ArrowFunctions,
			Destructuring,
			RestParameters {
	}

	interface NodeCurrent extends TranspilerOption {
	}
}
