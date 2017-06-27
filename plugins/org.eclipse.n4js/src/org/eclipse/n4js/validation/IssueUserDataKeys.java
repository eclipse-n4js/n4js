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
package org.eclipse.n4js.validation;

/**
 * Issue user data is additional information passed by the Issues' user data field. This class defines the keys
 * classified by Issue code.
 */
public class IssueUserDataKeys {

	/**
	 * IssueUserDataKeys regarding VIS_ILLEGAL_MEMBER_ACCESS
	 */
	public static class VIS_ILLEGAL_MEMBER_ACCESS {
		/**
		 * Suggested access modifier to make member visible.
		 */
		public static final String ACCESS_SUGGESTION = "org.eclipse.n4js.scoping.accessModifiers.InvisibleMemberDescription.accessModifierSuggestion";
		/**
		 * EObjectURI of member declaration
		 */
		public static final String DECLARATION_OBJECT_URI = "org.eclipse.n4js.scoping.accessModifiers.InvisibleMemberDescription.declarationObjectUri";
	}

	/**
	 * IssueUserDataKeys wrt VIS_ILLEGAL_TYPE_ACCESS
	 */
	public static class VIS_ILLEGAL_TYPE_ACCESS {
		/**
		 * Suggested access modifier to make type visible
		 */
		public static final String ACCESS_SUGGESTION = "org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription.accessModifierSuggestion";
		/**
		 * EObjectURI of type declaration
		 */
		public static final String DECLARATION_OBJECT_URI = "org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription.declarationObjectUri";
	}

	/**
	 * IssueUserDataKeys wrt VIS_ILLEGAL_FUN_ACCESS
	 */
	public static class VIS_ILLEGAL_FUN_ACCESS {
		/**
		 * Suggested access modifier to make type visible
		 */
		public static final String ACCESS_SUGGESTION = "org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription.accessModifierSuggestion";
		/**
		 * EObjectURI of type declaration
		 */
		public static final String DECLARATION_OBJECT_URI = "org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription.declarationObjectUri";
	}

	/**
	 * IssueUserDataKeys wrt VIS_ILLEGAL_VARIABLE_ACCESS
	 */
	public static class VIS_ILLEGAL_VARIABLE_ACCESS {
		/**
		 * Suggested access modifier to make type visible
		 */
		public static final String ACCESS_SUGGESTION = "org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription.accessModifierSuggestion";
		/**
		 * EObjectURI of type declaration
		 */
		public static final String DECLARATION_OBJECT_URI = "org.eclipse.n4js.scoping.accessModifiers.InvisibleTypeOrVariableDescription.declarationObjectUri";
	}

	/**
	 * IssueUserDataKeys wrt CLF_OVERRIDE_VISIBILITY
	 */
	public static class CLF_OVERRIDE_VISIBILITY {

		/**
		 * Access modifier of the overridden member
		 */
		public static final String OVERRIDDEN_MEMBER_ACCESS_MODIFIER = "org.eclipse.n4js.validation.validators.N4JSMemberRedefinitationValidator.overridden_member_modifier";
		/**
		 * Name of the member
		 */
		public static final String OVERRIDDEN_MEMBER_NAME = "org.eclipse.n4js.validation.validators.N4JSMemberRedefinitationValidator.overridden_member_name";
		/**
		 * Super class name
		 */
		public static final String SUPER_CLASS_NAME = "org.eclipse.n4js.validation.validators.N4JSMemberRedefinitationValidator.super_class_name";
	}

	/**
	 * IssueUserDataKeys wrt CLF_EXTEND_FINAL
	 */
	public static class CLF_EXTEND_FINAL {
		/**
		 * URI to super type declaration
		 */
		public static final String SUPER_TYPE_DECLARATION_URI = "org.eclipse.n4js.validation.validators.N4JSClassValidator.super_type_declaration_uri";
	}

	/**
	 * IssueUserDataKeys wrt CLF_OVERRIDE_FINAL
	 */
	public static class CLF_OVERRIDE_FINAL {
		/**
		 * URI to the overridden member declaration
		 */
		public static final String OVERRIDDEN_MEMBER_URI = "org.eclipse.n4js.validation.validators.N4JSMemberRedefinitionValidator.overridden_member_uri";
	}
}
