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

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.eclipse.n4js.utils.Strings;

/**
 * Abstract base class for classifiers.
 */
abstract public class Classifier<T extends Classifier<T>> extends Fragment<T> {
	/**
	 * Possible visibility modifiers for classifiers.
	 */
	public static enum Visibility {
		PRIVATE, PROJECT, PUBLIC_INTERNAL, PUBLIC
	}

	/**
	 * Extension methods for the {@link Visibility} enumeration.
	 */
	public static class VisibilityExtensions {
		/**
		 * Builds a classifier name from the given name and visibility by appending an appropriate string to the given
		 * name.
		 *
		 * @param visibility
		 *            the visibility value
		 * @param classifierName
		 *            classifier name prefix
		 *
		 * @return the newly created classifier name
		 */
		static String makeName(Visibility visibility, String classifierName) {
			return classifierName + getNameExtension(visibility);
		}

		/**
		 * Returns an appropriate classifier name extension depending on the given visibility.
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
			case PUBLIC_INTERNAL:
				return "_public_internal";
			case PUBLIC:
				return "_public";
			}
			return "";
		}

		/**
		 * Builds an appropriate code fragment for the given classifier visibility.
		 *
		 * @param visibility
		 *            the visibility value
		 *
		 * @return the code fragment
		 */
		static String generate(Visibility visibility) {
			switch (visibility) {
			case PRIVATE:
				return "/* private */";
			case PROJECT:
				return "export project";
			case PUBLIC_INTERNAL:
				return "export @Internal public";
			case PUBLIC:
				return "export public";
			}
			return "";
		}
	}

	Visibility visibility = Visibility.PRIVATE;
	String name;
	List<Member<?>> members = new LinkedList<>();

	/**
	 * Creates a new classifier instance with the given name.
	 *
	 * @param name
	 *            the name of the new classifier
	 */
	protected Classifier(String name) {
		this.name = Objects.requireNonNull(name);
	}

	/**
	 * Returns the name of this classifier.
	 *
	 * @return the name of this classifier
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the visibility to <code>project</code>.
	 */
	public T makeProjectVisible() {
		return setVisibility(Visibility.PROJECT);
	}

	/**
	 * Set the visibility to <code>public @Internal</code>.
	 */
	public T makePublicInternal() {
		return setVisibility(Visibility.PUBLIC_INTERNAL);
	}

	/**
	 * Set the visibility to <code>public</code>.
	 */
	public T makePublic() {
		return setVisibility(Visibility.PUBLIC);
	}

	/**
	 * Set the visibility.
	 *
	 * @param visibility
	 *            the visibility to set
	 */
	@SuppressWarnings("unchecked")
	public T setVisibility(Visibility visibility) {
		this.visibility = visibility;
		return (T) this;
	}

	/**
	 * Add the given member to this builder.
	 *
	 * @param member
	 *            the member to add
	 */
	@SuppressWarnings("unchecked")
	public T addMember(Member<?> member) {
		members.add(Objects.requireNonNull(member));
		return (T) this;
	}

	@Override
	public String generate() {
		String result = generateVisibility() + generateAbstract() + generateType() + name + generateTypeRelations();
		if (hasMembers()) {
			result += generateMembers();
		} else {
			result += "{}";
		}
		return result;
	}

	/**
	 * Generate an appropriate code fragment for this classifier's visibility.
	 *
	 * @return the generated visibility code fragment
	 */
	protected String generateVisibility() {
		return VisibilityExtensions.generate(visibility) + " ";
	}

	/**
	 * Generate the code fragments for each of this classifier's members.
	 *
	 * @return the generated member code fragment
	 */
	protected String generateMembers() {
		return Strings.join("\n", m -> m.generate(), members);
	}

	/**
	 * Generates a code fragment for the actual type of this classifier.
	 *
	 * @return the generated code fragment
	 */
	protected abstract CharSequence generateType();

	/**
	 * Generates a code fragment for the type relations of this classifier, e.g. its base types or implemented
	 * interfaces.
	 */
	protected abstract CharSequence generateTypeRelations();

	private boolean hasMembers() {
		return members != null && !members.isEmpty();
	}

	@Override
	public String toString() {
		return generate().toString();
	}
}
