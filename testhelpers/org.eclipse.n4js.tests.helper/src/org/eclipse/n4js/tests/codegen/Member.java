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
package org.eclipse.n4js.tests.codegen;

/**
 * Abstract base class for code generators that generate code for members of a {@link Classifier}.
 */
abstract public class Member<T extends Member<T>> extends Fragment<T> {
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
		 * Builds a member name from the given name and visibility by appending an appropriate string to the given name.
		 *
		 * @param visibility
		 *            the visibility value
		 * @param memberName
		 *            member name prefix
		 *
		 * @return the newly created member name
		 */
		static String makeName(Visibility visibility, String memberName) {
			return memberName + getNameExtension(visibility);
		}

		/**
		 * Returns an appropriate member name extension depending on the given visibility.
		 *
		 * @param visibility
		 *            the visibility value
		 *
		 * @return the name extension
		 */
		static String getNameExtension(Visibility visibility) {
			switch (visibility) {
			case PRIVATE:
				return "_private";
			case PROJECT:
				return "_project";
			case PROTECTED_INTERNAL:
				return "_protected_internal";
			case PROTECTED:
				return "_protected";
			case PUBLIC_INTERNAL:
				return "_public_internal";
			case PUBLIC:
				return "_public";
			}
			return "";
		}

		/**
		 * Builds an appropriate code fragment for the given member visibility.
		 *
		 * @param visibility
		 *            the visibility value
		 *
		 * @return the code fragment
		 */
		static String generate(Visibility visibility) {
			switch (visibility) {
			case PRIVATE:
				return "private";
			case PROJECT:
				return "project";
			case PROTECTED_INTERNAL:
				return "@Internal protected";
			case PROTECTED:
				return "protected";
			case PUBLIC_INTERNAL:
				return "@Internal public";
			case PUBLIC:
				return "public";
			}
			return "";
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
	static enum HasOverride {
		YES, NO
	}

	/**
	 * Extensions for {@link Static} enumeration.
	 */
	public static class StaticExtensions {
		/**
		 * Builds a member name from the given name and static specifier by appending an appropriate string to the given
		 * name.
		 *
		 * @param static_
		 *            whether or not the member is static
		 * @param classifierName
		 *            name prefix
		 *
		 * @return the newly created member name
		 */
		static String makeName(Static static_, String classifierName) {
			return classifierName + getNameExtension(static_);
		}

		/**
		 * Returns an appropriate member name extension depending on the given static specification.
		 *
		 * @param static_
		 *            whether or not the member is static
		 *
		 * @return the name extension
		 */
		static String getNameExtension(Static static_) {
			switch (static_) {
			case YES:
				return "_static";
			case NO:
				return "";
			}
			return "";
		}

		/**
		 * Returns an appropriate code fragment depending on the given static specification.
		 *
		 * @param static_
		 *            whether or not the member is static
		 *
		 * @return the code fragment
		 */
		static String generate(Static static_) {
			switch (static_) {
			case YES:
				return "static ";
			case NO:
				return "";
			}
			return "";
		}
	}

	protected Visibility visibility = Visibility.PUBLIC;
	protected Static static_ = Static.NO;
	protected String name;
	protected HasOverride override_ = HasOverride.NO;

	/**
	 * Creates a new member with the given parameters.
	 *
	 * @param name
	 *            the name of the member
	 */
	protected Member(String name) {
		this.name = name;
	}

	/**
	 * Sets visibility to project visible.
	 */
	public T makeProjectVisible() {
		return setVisibility(Visibility.PROJECT);
	}

	/**
	 * Sets visibility to internal protected.
	 */
	public T makeProtectedInternal() {
		return setVisibility(Visibility.PROTECTED_INTERNAL);
	}

	/**
	 * Sets visibility to protected.
	 */
	public T makeProtected() {
		return setVisibility(Visibility.PROTECTED);
	}

	/**
	 * Sets visibility to internal public.
	 */
	public T makePublicInternal() {
		return setVisibility(Visibility.PUBLIC_INTERNAL);
	}

	/**
	 * Sets visibility to public.
	 */
	public T makePublic() {
		return setVisibility(Visibility.PUBLIC);
	}

	/**
	 * Set the visibility.
	 *
	 * @param visibility
	 *            the visibility to set
	 *
	 * @return this builder
	 */
	@SuppressWarnings("unchecked")
	public T setVisibility(Visibility visibility) {
		this.visibility = visibility;
		return (T) this;
	}

	/**
	 * Make the member static.
	 *
	 * @return this builder
	 */
	public T makeStatic() {
		return setStatic(Static.YES);
	}

	/**
	 * Specify whether the member is static.
	 *
	 * @param static_
	 *            whether or not the member is static
	 */
	@SuppressWarnings("unchecked")
	public T setStatic(Static static_) {
		this.static_ = static_;
		return (T) this;
	}

	/**
	 * Indicates whether this member is static.
	 *
	 * @return <code>true</code> if this member is static and <code>false</code> otherwise
	 */
	public boolean isStatic() {
		return static_ == Static.YES;
	}

	/**
	 * Set the member to override.
	 */
	public T makeOverride() {
		return setOverride(HasOverride.YES);
	}

	/**
	 * Set the override status of the member.
	 *
	 * @param override_
	 *            the override status
	 */
	@SuppressWarnings("unchecked")
	public T setOverride(HasOverride override_) {
		this.override_ = override_;
		return (T) this;
	}

	/**
	 * Indicates whether this member overrides.
	 *
	 * @return <code>true</code> if this member overrides
	 */
	public boolean isOverride() {
		return override_ == HasOverride.YES;
	}

	@Override
	public String generate() {
		return generateOverride() + generateVisibility() + generateStatic() + generateMember();
	}

	private String generateOverride() {
		if (isOverride()) {
			return "@Override ";
		}
		return "";
	}

	/**
	 * Generates a code fragment for the visibility of this member.
	 *
	 * @return the code fragment
	 */
	private String generateVisibility() {
		return VisibilityExtensions.generate(visibility) + " ";
	}

	/**
	 * Generates a code fragment according to whether this member is static or not.
	 *
	 * @return the code fragment
	 */
	private String generateStatic() {
		return StaticExtensions.generate(static_);
	}

	/**
	 * Abstract method that generates the actual member code.
	 *
	 * @return the generated code fragment
	 */
	protected abstract CharSequence generateMember();

	@Override
	public String toString() {
		return generate().toString();
	}
}
