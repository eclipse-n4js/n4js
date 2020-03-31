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
package org.eclipse.n4js.generator;

import org.eclipse.n4js.utils.RecursionGuard;

/**
 * Options used by generators to turn on/off optional features. At time of writing, this is mostly used by the
 * EcmaScript transpiler to activate and deactivate optional transformations.
 */
public enum GeneratorOption {

	/**
	 * Transforms ES2015 template literals to a corresponding ES5 equivalent.
	 * <p>
	 * See {@code TemplateStringTransformation}.
	 */
	TemplateStringLiterals,

	/**
	 * Transforms ES2015 arrow functions to an ES5 equivalent.
	 * <p>
	 * See {@code ArrowFunction_Part1_Transformation} and {@code ArrowFunction_Part2_Transformation}.
	 */
	ArrowFunctions,

	/**
	 * Transforms ES2015 destructuring patterns into equivalent ES5 code.
	 * <p>
	 * See {@code DestructuringTransformation}.
	 */
	Destructuring,

	/**
	 * Transforms ES2015 rest parameters to an ES5 equivalent.
	 * <p>
	 * See {@code RestParameterTransformation}.
	 */
	RestParameters,

	/**
	 * Composite option that activates transformation of N4JS to output code that is as close to ES5 as currently
	 * supported by the N4JS compiler (some language features that cannot currently be transpiled to ES5 include
	 * <code>for..of</code> loops, let/const, yield).
	 * <p>
	 * Includes {@link #TemplateStringLiterals}, {@link #ArrowFunctions}, {@link #Destructuring},
	 * {@link #RestParameters}.
	 */
	ES5plus(TemplateStringLiterals,
			ArrowFunctions,
			Destructuring,
			RestParameters),

	/**
	 * Option to configure the transpiler to produce output code that makes use of all enhanced JavaScript features
	 * supported by the latest stable(!) release of node.js.
	 */
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

	/**
	 * A generator option A can include another option B, meaning that whenever A is activated, B will also be activated
	 * implicitly. For example, option {@link #ES5plus} includes option {@link #ArrowFunctions}.
	 */
	private final GeneratorOption[] includedOptions;

	private GeneratorOption() {
		this.includedOptions = new GeneratorOption[0];
	}

	/**
	 * @param includedOptions
	 *            see {@link #includedOptions}.
	 */
	private GeneratorOption(GeneratorOption... includedOptions) {
		this.includedOptions = includedOptions;
	}

	/** Tells if the receiving option is among the given active options or their included options. */
	public boolean isActiveIn(GeneratorOption... activeOptions) {
		return isActiveIn(this, activeOptions);
	}

	/** Tells if the given <code>optionToTest</code> is among the given active options or their included options. */
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
