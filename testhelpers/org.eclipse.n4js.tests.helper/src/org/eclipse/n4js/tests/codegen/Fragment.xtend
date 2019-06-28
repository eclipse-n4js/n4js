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
package org.eclipse.n4js.tests.codegen

/**
 * Abstract base class for constructs that can be abstract or concrete.
 */
abstract class Fragment<T extends Fragment<T>> {
	/**
	 * Possible values for abstractness.
	 */
	public static enum Abstract {
		YES,
		NO
	}

	/**
	 * Extension methods for enumeration {@link Abstract}.
	 */
	public static abstract class AbstractExtensions {
		/**
		 * Return a name for the given classifier name and abstractness. The created name
		 * has the classifier name as a prefix. If the given value indicates an abstract
		 * construct, then the prefix is followed by an underscore and the word 'abstract'.
		 * Otherwise, there is no suffix.
		 *
		 * @param abstract_ whether or not the construct of interest is abstract
		 * @param classifierName the classifier name prefix
		 *
		 * @return the generated name
		 */
		static def String makeName(Abstract abstract_, String classifierName) {
			classifierName + abstract_.classifierExtension
		}

		/**
		 * Return the appropriate extension for a classifier name depending on the given value.
		 *
		 * @param abstract_ whether or not the construct of interest is abstract
		 *
		 * @return the name extension
		 */
		static def String getClassifierExtension(Abstract abstract_) {
			switch abstract_ {
				case YES: "_abstract"
				case NO: ""
			}
		}

		/**
		 * Returns the generated string for the given value.
		 *
		 * @param abstract_ whether or not the construct of interest is abstract
		 *
		 * @return the generated string
		 */
		static def String generate(Abstract abstract_) {
			switch abstract_ {
				case YES: "abstract "
				case NO: ""
			}
		}
	}

	Abstract abstract_ = Abstract.NO;

	/**
	 * Creates a new instance.
	 */
	protected new() {}

	/**
	 * Specifies that the this fragment should be abstract.
	 */
	public def T makeAbstract() {
		abstract_ = Abstract.YES;
		return this as T;
	}

	/**
	 * Indicates whether this construct is abstract.
	 *
	 * @return <code>true</code> if this construct is abstract and <code>false</code> otherwise
	 */
	protected def boolean isAbstract() {
		abstract_ == Abstract.YES
	}

	/**
	 * Generates the appropriate N4JS code for this construct.
	 *
	 * @return the generated code
	 */
	abstract def CharSequence generate()

	/**
	 * Generates the appropriate keyword for this fragment, depending on whether or not it is abstract.
	 *
	 * @return the generated keyword, followed by a blank
	 */
	protected def generateAbstract() {
		AbstractExtensions.generate(abstract_)
	}
}
