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
 * Abstract base class for code generators that generate code for members of a {@link Classifier}.
 */
abstract class Member<T extends Member<T>> extends Fragment<T> {
	/**
	 * Possible visibilities for members.
	 */
	public static enum Visibility {
		/**
		 * Private visibility.
		 */
		PRIVATE,
		/**
		 * Project visibility.
		 */
		PROJECT,
		/**
		 * Internal protected visibility.
		 */
		PROTECTED_INTERNAL,
		/**
		 * Protected visibility.
		 */
		PROTECTED,
		/**
		 * Internal public visibility.
		 */
		PUBLIC_INTERNAL,
		/**
		 * Public visibility.
		 */
		PUBLIC
	}

	/**
	 * Extensions for the {@link Visibility} enumeration.
	 */
	public static class VisibilityExtensions {
		/**
		 * Builds a member name from the given name and visibility by appending an appropriate string
		 * to the given name.
		 *
		 * @param visibility the visibility value
		 * @param the member name prefix
		 *
		 * @return the newly created member name
		 */
		static def String makeName(Visibility visibility, String memberName) {
			memberName + visibility.nameExtension
		}

		/**
		 * Returns an appropriate member name extension depending on the given visibility.
		 *
		 * @param visibility the visibility value
		 *
		 * @return the name extension
		 */
		static def String getNameExtension(Visibility visibility) {
			switch visibility {
				case PRIVATE: "_private"
				case PROJECT: "_project"
				case PROTECTED_INTERNAL: "_protected_internal"
				case PROTECTED: "_protected"
				case PUBLIC_INTERNAL: "_public_internal"
				case PUBLIC: "_public"
			}
		}

		/**
		 * Builds an appropriate code fragment for the given member visibility.
		 *
		 * @param visibility the visibility value
		 *
		 * @return the code fragment
		 */
		static def String generate(Visibility visibility) {
			switch visibility {
				case PRIVATE: "private"
				case PROJECT: "project"
				case PROTECTED_INTERNAL: "@Internal protected"
				case PROTECTED: "protected"
				case PUBLIC_INTERNAL: "@Internal public"
				case PUBLIC: "public"
			}
		}
	}

	/**
	 * Possible values for whether or not a member is static.
	 */
	public enum Static {
		/**
		 * Static.
		 */
		YES,
		/**
		 * Non-static.
		 */
		NO
	}

	/**
	 * Possible values for whether or not a member overrides an inherited member.
	 */
	static enum Override {
		YES,
		NO
	}

	/**
	 * Extensions for {@link Static} enumeration.
	 */
	public static class StaticExtensions {
		/**
		 * Builds a member name from the given name and static specifier by appending an appropriate string
		 * to the given name.
		 *
		 * @param static_ whether or not the member is static
		 * @param the member name prefix
		 *
		 * @return the newly created member name
		 */
		static def String makeName(Static static_, String classifierName) {
			classifierName + static_.nameExtension
		}

		/**
		 * Returns an appropriate member name extension depending on the given static specification.
		 *
		 * @param static_ whether or not the member is static
		 *
		 * @return the name extension
		 */
		static def String getNameExtension(Static static_) {
			switch static_ {
				case YES: "_static"
				case NO: ""
			}
		}

		/**
		 * Returns an appropriate code fragment depending on the given static specification.
		 *
		 * @param static_ whether or not the member is static
		 *
		 * @return the code fragment
		 */
		static def String generate(Static static_) {
			switch static_ {
				case YES: "static "
				case NO: ""
			}
		}
	}

	protected Visibility visibility = Visibility.PUBLIC;
	protected Static static_ = Static.NO;
	protected String name;
	protected Override override_ = Override.NO;

	/**
	 * Creates a new member with the given parameters.
	 *
	 * @param name the name of the member
	 */
	protected new(String name) {
		this.name = name
	}

	/**
	 * Sets visibility to project visible.
	 */
	public def T makeProjectVisible() {
		return setVisibility(Visibility.PROJECT);
	}

	/**
	 * Sets visibility to internal protected.
	 */
	public def T makeProtectedInternal() {
		return setVisibility(Visibility.PROTECTED_INTERNAL);
	}

	/**
	 * Sets visibility to protected.
	 */
	public def T makeProtected() {
		return setVisibility(Visibility.PROTECTED);
	}

	/**
	 * Sets visibility to internal public.
	 */
	public def T makePublicInternal() {
		return setVisibility(Visibility.PUBLIC_INTERNAL);
	}

	/**
	 * Sets visibility to public.
	 */
	public def T makePublic() {
		return setVisibility(Visibility.PUBLIC);
	}

	/**
	 * Set the visibility.
	 *
	 * @param visibility the visibility to set
	 *
	 * @return this builder
	 */
	public def T setVisibility(Visibility visibility) {
		this.visibility = visibility;
		return this as T;
	}

	/**
	 * Make the member static.
	 *
	 * @return this builder
	 */
	public def T makeStatic() {
		return setStatic(Static.YES);
	}

	/**
	 * Specify whether the member is static.
	 *
	 * @param static_ whether or not the member is static
	 */
	public def T setStatic(Static static_) {
		this.static_ = static_;
		return this as T;
	}

	/**
	 * Indicates whether this member is static.
	 *
	 * @return <code>true</code> if this member is static and <code>false</code> otherwise
	 */
	public def boolean isStatic() {
		static_ == Static.YES
	}

	/**
	 * Set the member to override.
	 */
	public def T makeOverride() {
		return setOverride(Override.YES);
	}

	/**
	 * Set the override status of the member.
	 *
	 * @param override_ the override status
	 */
	public def T setOverride(Override override_) {
		this.override_ = override_
		return this as T;
	}

	/**
	 * Indicates whether this member overrides.
	 *
	 * @return <code>true</code> if this member overrides
	 */
	public def boolean isOverride() {
		return override_== Override.YES;
	}

	override def generate() '''
		«generateOverride()»«generateVisibility()»«generateStatic()»«generateMember()»
	'''

	private def generateOverride() '''«IF override»@Override «ENDIF»'''

	/**
	 * Generates a code fragment for the visibility of this member.
	 *
	 * @return the code fragment
	 */
	private def generateVisibility() '''«VisibilityExtensions.generate(visibility)» '''

	/**
	 * Generates a code fragment according to whether this member is static or not.
	 *
	 * @return the code fragment
	 */
	private def generateStatic() {
		StaticExtensions.generate(static_)
	}

	/**
	 * Abstract method that generates the actual member code.
	 *
	 * @return the generated code fragment
	 */
	protected abstract def CharSequence generateMember()

	override public def String toString() {
		return generate().toString();
	}
}
