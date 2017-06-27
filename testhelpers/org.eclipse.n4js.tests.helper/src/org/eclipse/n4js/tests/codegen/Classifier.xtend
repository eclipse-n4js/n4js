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

import java.util.List
import java.util.Objects

/**
 * Abstract base class for classifiers.
 */
abstract class Classifier<T extends Classifier<T>> extends Fragment<T> {
	/**
	 * Possible visibility modifiers for classifiers.
	 */
	public static enum Visibility {
		PRIVATE,
		PROJECT,
		PUBLIC_INTERNAL,
		PUBLIC
	}

	/**
	 * Extension methods for the {@link Visibility} enumeration.
	 */
	public static class VisibilityExtensions {
		/**
		 * Builds a classifier name from the given name and visibility by appending an appropriate string
		 * to the given name.
		 *
		 * @param visibility the visibility value
		 * @param the classifier name prefix
		 *
		 * @return the newly created classifier name
		 */
		static def String makeName(Visibility visibility, String classifierName) {
			classifierName + visibility.nameExtension
		}

		/**
		 * Returns an appropriate classifier name extension depending on the given visibility.
		 *
		 * @param visibility the visibility value
		 *
		 * @return the name extension
		 */
		static def String getNameExtension(Visibility visibility) {
			switch visibility {
				case PRIVATE: "_private"
				case PROJECT: "_project"
				case PUBLIC_INTERNAL: "_public_internal"
				case PUBLIC: "_public"
			}
		}

		/**
		 * Builds an appropriate code fragment for the given classifier visibility.
		 *
		 * @param visibility the visibility value
		 *
		 * @return the code fragment
		 */
		static def String generate(Visibility visibility) {
			switch visibility {
				case PRIVATE: "/* private */"
				case PROJECT: "export project"
				case PUBLIC_INTERNAL: "export @Internal public"
				case PUBLIC: "export public"
			}
		}
	}

	Visibility visibility = Visibility.PRIVATE;
	String name;
	List<Member<?>> members;

	/**
	 * Creates a new classifier instance with the given name.
	 *
	 * @param name the name of the new classifier
	 */
	protected new(String name) {
		this.name = Objects.requireNonNull(name);
	}

	/**
	 * Returns the name of this classifier.
	 *
	 * @return the name of this classifier
	 */
	public def String getName() {
		return name;
	}

	/**
	 * Set the visibility to <code>project</code>.
	 */
	public def T makeProjectVisible() {
		return setVisibility(Visibility.PROJECT);
	}

	/**
	 * Set the visibility to <code>public @Internal</code>.
	 */
	public def T makePublicInternal() {
		return setVisibility(Visibility.PUBLIC_INTERNAL);
	}

	/**
	 * Set the visibility to <code>public</code>.
	 */
	public def T makePublic() {
		return setVisibility(Visibility.PUBLIC);
	}

	/**
	 * Set the visibility.
	 *
	 * @param visibility the visibility to set
	 */
	public def T setVisibility(Visibility visibility) {
		this.visibility = visibility;
		return this as T;
	}

	/**
	 * Add the given member to this builder.
	 *
	 * @param member the member to add
	 */
	public def T addMember(Member<?> member) {
		if (members === null)
			members = newLinkedList();
		members.add(Objects.requireNonNull(member));
		return this as T;
	}

	override def generate() '''
		«generateVisibility()»«generateAbstract()»«generateType()»«name»«generateTypeRelations()» «IF !hasMembers»{}«ELSE»{
			«generateMembers()»
		}
		«ENDIF»
	'''

	/**
	 * Generate an appropriate code fragment for this classifier's visibility.
	 *
	 * @return the generated visibility code fragment
	 */
	protected def generateVisibility() '''«VisibilityExtensions.generate(visibility)» '''

	/**
	 * Generate the code fragments for each of this classifier's members.
	 *
	 * @return the generated member code fragment
	 */
	protected def generateMembers() '''
		«FOR m : members»
			«m.generate()»
		«ENDFOR»
	'''

	/**
	 * Generates a code fragment for the actual type of this classifier.
	 *
	 * @return the generated code fragment
	 */
	protected abstract def CharSequence generateType()

	/**
	 * Generates a code fragment for the type relations of this classifier, e.g.
	 * its base types or implemented interfaces.
	 */
	protected abstract def CharSequence generateTypeRelations()

	private def boolean hasMembers() {
		members !== null && !members.empty
	}

	override public def String toString() {
		return generate().toString();
	}
}
