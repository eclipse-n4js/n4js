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
package org.eclipse.n4js.generator.common;

import org.eclipse.n4js.utils.RecursionGuard;

public enum GeneratorOption {

	//
	AsyncAwait,

	//
	TemplateStringLiterals,

	//
	ArrowFunctions,

	//
	Destructuring,

	//
	RestParameters,

	//
	ES5plus(AsyncAwait,
			TemplateStringLiterals,
			ArrowFunctions,
			Destructuring,
			RestParameters),

	//
	NodeCurrent(/* no included options at this time */);

	/**
	 * Generator options used by default. Most importantly, the main transpiler generating the output code in the output
	 * folders uses these options.
	 */
	public static final GeneratorOption[] DEFAULT_OPTIONS = { NodeCurrent };

	/**
	 * Generator options that activate as many optional transpiler transformations as possible. This is used as an
	 * additional scenario for testing (in addition to {@link #DEFAULT_OPTIONS}).
	 */
	public static final GeneratorOption[] MAX_TRANSPILE_OPTIONS = { ES5plus };

	private final GeneratorOption[] includedOptions;

	private GeneratorOption() {
		this.includedOptions = new GeneratorOption[0];
	}

	private GeneratorOption(GeneratorOption... includedOptions) {
		this.includedOptions = includedOptions;
	}

	public boolean isActiveIn(GeneratorOption... activeOptions) {
		return isActiveIn(this, activeOptions);
	}

	public static final boolean isActiveIn(GeneratorOption optionToTest, GeneratorOption... activeOptions) {
		return isActiveIn(new RecursionGuard<GeneratorOption>(), optionToTest, activeOptions);
	}

	private static final boolean isActiveIn(RecursionGuard<GeneratorOption> guard, GeneratorOption optionToTest,
			GeneratorOption[] activeOptions) {
		for (int i = 0; i < activeOptions.length; i++) {
			final GeneratorOption activeOption = activeOptions[i];
			if (guard.tryNext(activeOption)) {
				if (optionToTest == activeOption || isActiveIn(guard, optionToTest, activeOption.includedOptions)) {
					return true;
				}
			}
		}
		return false;
	}
}
