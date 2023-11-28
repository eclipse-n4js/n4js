/**
 * Copyright (c) 2023 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.validation;

import static org.eclipse.xtext.diagnostics.Severity.ERROR;
import static org.eclipse.xtext.diagnostics.Severity.WARNING;

import java.util.List;

import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.xtext.diagnostics.Severity;

/**
 * Enum contains all issues
 */
@SuppressWarnings("javadoc")
public enum IssueCodes {

	/** The following message is intended for ES6 features that are implemented in N4JS but */
	/** are not yet supported in current version of V8 or some other relevant runtime environment. */
	/** 0: description of the feature that is not supported */
	UNSUPPORTED(ERROR, "Unsupported feature: %s."),

	/** Not necessary. */
	SCR_HASHBANG_WRONG_LOCATION(ERROR, "Hashbang must start at the absolute start of a script or module"),

	/** 0: file names of files that contain errors */
	EXTERNAL_LIBRARY_ERRORS(ERROR, "External library error: %s"),

	/** 0: file names of files that contain warnings */
	EXTERNAL_LIBRARY_WARNINGS(ERROR, "External library warning: %s"),

	/**
	 * 0: kind of throwable ('error', 'exception', or 'throwable'), 1: simple name of throwable, 2: message of throwable
	 */
	POST_PROCESSING_FAILED(ERROR, "%s %s thrown during post-processing (please report this!): %s"),

	/** Unknown type ref in resource */
	TYS_UNKNOWN_TYPE_REF(ERROR, "Resource contains unknown type ref (please report this!)"),

	/** 0: namespace name, 1: import module name, that both occur multiple times in this combination */
	IMP_STMT_DUPLICATE_NAMESPACE(ERROR, "Duplicate namespace import statement with name %s and imported module %s."),

	/** 0: imported element local name, 1: import module name, that both occur multiple times in this combination */
	IMP_STMT_DUPLICATE_NAMED(ERROR, "Duplicate named import statement with local name %s and imported module %s."),

	/** 0: type1 imported name1, 1: import module name, 2: name under which type is already imported */
	IMP_DUPLICATE(ERROR, "%s from %s is already imported as %s."),

	/** 0: import module name, 1: namespace name already importing those elements */
	IMP_DUPLICATE_NAMESPACE(ERROR, "Element %s of %s is already imported in namespace %s."),

	/**
	 * 0: local name (import name, alias name or namespace name), 1: import module name, 2: name under which type is
	 * already imported
	 */
	IMP_LOCAL_NAME_CONFLICT(ERROR, "Name %s is already used as %s."),

	/** 0: type name, 1: alias name */
	IMP_PLAIN_ACCESS_OF_ALIASED_TYPE(ERROR, "%s has been imported as %s."),

	/** 0: type name, 1: full name (namespace + '.' + type name) */
	IMP_PLAIN_ACCESS_OF_NAMESPACED_TYPE(ERROR, "%s has been imported as %s."),

	/** 0: imported name */
	IMP_UNUSED_IMPORT(WARNING, "The import of %s is unused."),

	/** 0: imported name */
	IMP_NOT_EXPORTED(ERROR, "Element %s is not exported."),

	/** 0: type import name */
	IMP_UNRESOLVED(ERROR, "Import of %s cannot be resolved."),

	/** Not necessary. */
	IMP_DISCOURAGED_PLACEMENT(WARNING, "The import statement should be placed on top of other statements."),

	/** 0: type or variable, 1: type name, 2: comma separated list of types - the sources of the imports */
	IMP_AMBIGUOUS(ERROR, "The %s %s is ambiguously imported from %s."),

	/** 0: type or variable, 1: type name, 2: comma separated list of types - the sources of the imports */
	IMP_AMBIGUOUS_WILDCARD(ERROR, "The %s %s is ambiguously imported from %s."),

	/** 0: imported type name, 1: qualified name of the module */
	IMP_CONFLICT(ERROR, "Conflicting import of %s with import from %s."),

	/** 0: imported type1 name, 1: type1 alias, 2: type2 imported name, 3: type2 import module name */
	IMP_CONFLICT_ALIAS_TYPE(ERROR,
			"Conflicting import of %s as %s. Cannot import same type with different name (%s) from %s."),

	/** 0: type1 imported name, 1: type2 alias, 2: type2 import module name */
	IMP_CONFLICT_ALIASES(ERROR,
			"Conflicting import of %s. Cannot import same type with different name (%s) from %s."),

	/** 0: type1 imported name1, that occur multiple times, 1: import module name */
	IMP_DUPLICATEX(ERROR, "Duplicate import of %s in import from %s."),

	/** 0: type1 imported name1, 1: type1 imported name2, 2: import module name */
	IMP_DUPLICATE_ALIAS(ERROR, "Duplicate import of %s, already defined as %s in import from %s."),

	/** 0: module in wildcard specifier */
	IMP_EMPTY_WILDCARD_IMPORT(WARNING,
			"The wild-card import from %s doesn't import anything because nothing is exported there."),

	/** 0: alias in namespace import */
	IMP_STATIC_IMPORT_PLAIN_JS(ERROR, "Use dynamic import in order to access elements of %s."),

	/** 0: alias in namespace import */
	IMP_DYNAMIC_IMPORT_N4JS(ERROR, "N4JS module %s must not be imported dynamically."),

	/** 0: variant type, 1: alias in namespace import */
	IMP_DYNAMIC_IMPORT_N4JSD(WARNING, "The %s module %s should not be imported dynamically."),

	/** no parameters required */
	IMP_DEFAULT_EXPORT_DUPLICATE(ERROR, "Duplicate default export."),

	/** 0: local name of the imported element */
	IMP_IMPORTED_ELEMENT_READ_ONLY(ERROR, "Imported element %s is read-only."),

	/** 0: local name of the imported element, 1: re-exporting project name to use, 2: imported project name */
	IMP_IMPORTED_ELEMENT_FROM_REEXPORTING_PROJECT(ERROR,
			"The imported element %s is defined in the re-exporting project %s but project %s was imported. Import from %s instead."),

	/** 0: method, field, getter, setter, member, 1: member name */
	VIS_ILLEGAL_MEMBER_ACCESS(ERROR, "The %s %s is not visible."),

	/** 0: function name */
	VIS_ILLEGAL_FUN_ACCESS(ERROR, "The function %s is not visible."),

	/** 0: type name */
	VIS_ILLEGAL_TYPE_ACCESS(ERROR, "The type %s is not visible."),

	/** 0: variable name */
	VIS_ILLEGAL_VARIABLE_ACCESS(ERROR, "The variable %s is not visible."),

	/** 0: member name, 1: type defining member */
	VIS_ILLEGAL_STATIC_MEMBER_WRITE_ACCESS(ERROR,
			"Write access to the static member %s defined in %s must use %s directly."),

	/** 0: member name, 1: type defining member, 2: member alias */
	VIS_ILLEGAL_STATIC_MEMBER_WRITE_ACCESS_WITH_ALIAS(ERROR,
			"Write access to the static member %s defined in %s must use the alias %s directly."),

	/**
	 * 0: member kind, 1: member name, 2: 'read-only', when a read-only member was used with write access or
	 * 'write-only' when a write-only member was used with read access.
	 */
	VIS_WRONG_READ_WRITE_ACCESS(ERROR, "The %s %s is %s."),

	/** 0: static or non-static, 1: member name, 2: static or non-static */
	VIS_WRONG_STATIC_ACCESSOR(ERROR, "The %s member %s cannot be accessed from a %s context."),

	/** 0: type variable name */
	VIS_WRONG_TYPE_VARIABLE_ACCESSOR(ERROR, "The type variable %s cannot be accessed from a static context."),

	/** 0: type parameter name, 1: kind of type hidden (e.g. class, type variable, etc.) */
	VIS_TYPE_PARAMETER_HIDES_TYPE(WARNING, "The type parameter %s hides %s %s."),

	/** 0: concept which is not allowed, 1: Mode (n4js,strict,...) in which it is not allowed. */
	VIS_RESTRITCTED_USAGE(ERROR, "The usage of %s is not allowed in %s mode."),

	/**
	 * 0: 'constructor' or 'construct signature', 1: classifier name whose constructor / construct signature is not
	 * visible.
	 */
	VIS_NEW_CANNOT_INSTANTIATE_INVISIBLE_CONSTRUCTOR(ERROR, "The %s of %s is not visible."),

	/** no parameters required */
	TYP_TYPE_PARAM_MANDATORY_AFTER_OPTIONAL(ERROR,
			"Mandatory type parameters may not follow optional type parameters."),

	/** no parameters required */
	TYP_TYPE_PARAM_DEFAULT_REFERENCES_LATER_TYPE_PARAM(ERROR,
			"Default type arguments may only reference type parameters that are declared before the current type parameter."),

	/** 0: name of type parameter, 1: error message of subtype check */
	TYP_TYPE_PARAM_DEFAULT_NOT_SUBTYPE_OF_BOUND(ERROR,
			"Default argument of optional type parameter %s must comply to its upper bound, but: %s"),

	/** no parameters required */
	CLF_MUST_BE_NOMINAL(ERROR, "Classes cannot be structural."),

	/** no parameters required */
	CLF_CTOR_RETURN_TYPE(ERROR, "A constructor must not have a return type declaration."),

	/** no parameters required */
	CLF_CTOR_ILLEGAL_MODIFIER(ERROR,
			"A constructor must not be declared abstract, static or final and may not be annotated with @Override."),

	/** no parameters required */
	CLF_CALL_CONSTRUCT_SIG_ONLY_IN_N4JSD(ERROR, "Call and construct signatures may only be used in .n4jsd files."),

	/** no parameters required */
	CLF_CALL_CONSTRUCT_SIG_NOT_IN_N4JS(ERROR,
			"Call and construct signatures are not allowed in n4js classifiers. Use shapes or @EcmaScript."),

	/** no parameters required */
	CLF_CALL_CONSTRUCT_SIG_BODY(ERROR, "Call and construct signatures must not have a body."),

	/** 0: kind of signature ('call' or 'construct') */
	CLF_CALL_CONSTRUCT_SIG_DUPLICATE(ERROR, "Duplicate %s signature."),

	/** no parameters required */
	CLF_CALL_CONSTRUCT_SIG_CANNOT_IMPLEMENT(ERROR,
			"An interface with a call or construct signature cannot be implemented by a class."),

	/** no parameters required */
	CLF_CONSTRUCT_SIG_ONLY_IN_INTERFACE(ERROR, "Construct signatures may only be used in interfaces."),

	/** no parameters required */
	CLF_CONSTRUCT_SIG_VOID_RETURN_TYPE(ERROR, "Construct signatures must have a non-void return type."),

	/** no parameters required */
	CLF_CTOR_NO_TYPE_PARAMETERS(ERROR, "Constructors must not have any type parameters."),

	/** no parameters required */
	CLF_NAME_DOLLAR(ERROR, "Member names must not start with a dollar character."),

	/** 0: the human readable name of the violating discouraged character */
	CLF_NAME_CONTAINS_DISCOURAGED_CHARACTER(WARNING, "Name should not contain %s character."),

	/** 0: static or const keywords */
	CLF_NAME_DOES_NOT_MATCH_WITH_PATTERN_FOR_STATIC_OR_CONST(WARNING,
			"Name should be all upper case with words separated by underscores for variables with %s modifier."),

	/** 0: element meta type */
	CLF_NAME_DOES_NOT_START_UPPERCASE(WARNING, "%s names should start with upper case letter."),

	/** 0: element meta type */
	CLF_NAME_DOES_NOT_START_LOWERCASE(WARNING, "%s names should start with lower case letter."),

	/** 0: element description, 1: conflicting category */
	CLF_NAME_INDISTINGUISHABLE(ERROR, "%s may be indistinguishable with %s."),

	/** 0: element description, 1: conflicting category */
	CLF_NAME_RESERVED(WARNING, "%s may be confused with %s."),

	/** no parameters required */
	CLF_NAME_CONFLICTS_WITH_CONSTRUCTOR(WARNING, "Name may be confused with constructor."),

	/** 0: element description, 1: conflicting type name 2: actual type */
	CLF_NAME_DIFFERS_TYPE(WARNING, "%s is not of type %s but of type %s."),

	/** 0: name of final type, 1: name of type parameter */
	CLF_UPPER_BOUND_FINAL(WARNING,
			"Final type %s should not be used as upper bound of type parameter %s. Final types cannot be extended."),

	/** no parameters required */
	CLF_DEF_SITE_VARIANCE_ONLY_IN_CLASSIFIER(ERROR,
			"Declaration of definition-site variance only allowed in generic classes and interfaces, not in generic functions or methods."),

	/** 0: 'covariant' or 'contravariant', 1: 'covariant', 'contravariant', or 'invariant' */
	CLF_TYPE_VARIABLE_AT_INVALID_POSITION(ERROR, "Cannot use %s type variable at %s position."),

	/** 0: the actual primitive type */
	CLF_EXTENDS_PRIMITIVE_GENERIC_TYPE(ERROR, "Cannot subclass primitive type %s."),

	/** 0: class name */
	CLF_EXTEND_FINAL(ERROR, "Cannot extend final class %s."),

	/** 0: keyword, 1: name of class */
	CLF_EXTEND_NON_ACCESSIBLE_CTOR(ERROR, "Cannot extend %s %s because its constructor is not accessible."),

	/** 0: name of class, 1: name of super class */
	CLF_OBSERVABLE_MISSING(ERROR,
			"Class %s extends observable class %s and must therefore be annotated with @Observable."),

	/** no parameters required */
	CLF_TEST_CLASS_NOT_EXPORTED(WARNING,
			"Classes containing test methods must be abstract or marked with modifier export."),

	/** /** 0: module name, 1: path to other module */
	CLF_DUP_MODULE(ERROR, "A duplicate module %s is also defined in %s."),

	/** /** 0: module name, 1: js module, 2: path to other module */
	CLF_DUP_DEF_MODULE(ERROR, "A duplicate definition module %s for %s is also defined in %s."),

	/**
	 * /** 0: first name with line ({@link ValidatorMessageHelper#nameWithLine(TMember,TMember)}, 1: other name with
	 * line ({@link ValidatorMessageHelper#nameWithLine(TMember,TMember)}
	 */
	CLF_DUP_CTOR(ERROR, "Constructor line %s duplicates constructor in line %s."),

	/**
	 * /** 0: first short description ({@link ValidatorMessageHelper#descriptionWithLine(TMember,TMember)}, 1: other
	 * short description ({@link ValidatorMessageHelper#descriptionWithLine(TMember,TMember)}
	 */
	CLF_DUP_MEMBER(ERROR, "The %s duplicates %s."),

	/** /** 0: first short description ({@link ValidatorMessageHelper#descriptionWithLine(TMember,TMember)} */
	CLF_DUP_WITH_MEMBER(ERROR,
			"The %s conflicts with the structural this type in the inherit constructor definition."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 2: setter or
	 * getter
	 */
	CLF_OVERRIDE_FIELD_REQUIRES_ACCESSOR_PAIR(ERROR, "The %s cannot override %s, matching %s missing."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_CONSUMED_OVERRIDE_FIELD(ERROR, "The consumed %s cannot override %s."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * 'overriding' or 'implementing' 2: overridden description
	 * ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDE_ANNOTATION(ERROR, "The %s %s %s must be annotated with @Override."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_REDEFINED_NON_ACCESSIBLE(ERROR, "The %s cannot override or implement non-accessible %s."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDE_FINAL(ERROR, "The %s cannot override final %s."),

	/**
	 * 0: overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1: 'final'
	 * or 'const'
	 */
	CLF_OVERRIDE_WITH_FINAL_OR_CONST_FIELD(ERROR, "Cannot override %s with a %s field."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDE_CONST(ERROR, "The %s cannot override %s."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDEN_CONCRETE_WITH_ABSTRACT(ERROR, "The abstract %s cannot override concrete %s."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDE_VISIBILITY(ERROR, "The %s cannot reduce the visibility of %s."),

	/** 0: method, getter or setter, 1: member name */
	CLF_OVERRIDE_NON_EXISTENT(ERROR, "The %s %s must override or implement a %s from a super class or interface."),

	/** 0: member description, 1: member description */
	CLF_OVERRIDE_NON_EXISTENT_INTERFACE(WARNING,
			"The %s does not override %s (no inheritance of static members in interfaces); remove annotation @Override."),

	/**
	 * 0: description of constructor, 1: description of classifier inheriting the constructor, 2: description of
	 * classifier owning the constructor
	 */
	CLF_PSEUDO_REDEFINED_SPEC_CTOR_INCOMPATIBLE(ERROR,
			"Inherited %s in context of %s not compatible to itself in context of %s."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden/implemented/consumed, 2: overridden description
	 * ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)} 3: additional members or error
	 * information (may be empty)
	 */
	CLF_REDEFINED_TYPE_NOT_SAME_TYPE(ERROR, "Type of %s must equal type of %s %s.%s"),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden/implemented/consumed, 2: overridden description
	 * ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_REDEFINED_BY_CONSUMED_TYPE_NOT_SAME_TYPE(ERROR, "Type of inherited %s must equal type of %s %s."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden/implemented/consumed, 3: signature type error message, 4: additional members or error information (may
	 * be empty)
	 */
	CLF_REDEFINED_MEMBER_TYPE_INVALID(ERROR, "Type of %s does not conform to %s %s: %s.%s"),

	/**
	 * 0: consumed/inherited, 1: overriding description
	 * ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 2: overridden/implemented/consumed, 3:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 4: signature
	 * type error message
	 */
	CLF_REDEFINED_BY_CONSUMED_MEMBER_TYPE_INVALID(ERROR, "Type of %s %s does not conform to %s %s: %s."),

	/** 0: comma separated description of conflicting members */
	CLF_CONSUMED_MEMBER_SOLVABLE_CONFLICT(ERROR, "Redefine ambiguously consumed members: %s."),

	/** 0: comma separated description of conflicting members */
	CLF_CONSUMED_MEMBER_UNSOLVABLE_CONFLICT(ERROR, "Incompatible consumed members: %s."),

	/** 0: inherited member leading to conflict, 1: comma separated description of conflicting members */
	CLF_CONSUMED_INHERITED_MEMBER_UNSOLVABLE_CONFLICT(ERROR, "Inherited %s cannot implement %s."),

	/**
	 * 0: overriding description, should usually start with 'Signature of '
	 * ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1: overridden/implemented/consumed, 2:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 3: signature
	 * type error message, 4: additional members or error information (may be empty)
	 */
	CLF_REDEFINED_METHOD_TYPE_CONFLICT(ERROR, "%s does not conform to %s %s: %s.%s"),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDE_MEMBERTYPE_INCOMPATIBLE(ERROR, "Cannot override %s with %s."),

	/**
	 * TODO remove?! 0: overriding description
	 * ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1: overridden description
	 * ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_CONSUMED_OVERRIDE_MEMBERTYPE_INCOMPATIBLE(ERROR, "Cannot override %s with consumed %s."),

	/**
	 * 0: getter/setter, 0: overridden descriptions (CSV)
	 * ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_CONSUMED_FIELD_ACCESSOR_PAIR_INCOMPLETE(ERROR, "Missing %s to completely override consumed %s."),

	/** no parameters required */
	CLF_FIELD_CONST_FINAL(ERROR, "A field may not be declared both final and const."),

	/** no parameters required */
	CLF_FIELD_CONST_STATIC(ERROR, "All const fields are static. Remove unnecessary modifier 'static'."),

	/** 0: name of const field */
	CLF_FIELD_CONST_MISSING_INIT(ERROR, "Const field %s must be provided with an initializer."),

	/** no parameters required */
	CLF_FIELD_FINAL_STATIC(ERROR, "A field may not be declared both final and static. Use a const field instead."),

	/** 0: name of final field */
	CLF_FIELD_FINAL_MISSING_INIT(ERROR,
			"Final field %s must be provided with an initializer or must be initialized in the constructor."),

	/** 0: name of final field */
	CLF_FIELD_FINAL_MISSING_INIT_IN_STATIC_POLYFILL(ERROR,
			"Final field %s must be provided with an initializer or must be initialized in the constructor. HINT: You might want to check the polyfilled constructor."),

	/** 0: name of final field (that has an initializer expression) */
	CLF_FIELD_FINAL_REINIT_IN_CTOR(ERROR,
			"Final field %s has an initializer and may therefore not be initialized in the constructor."),

	/** no parameters required */
	CLF_FIELD_OPTIONAL_OLD_SYNTAX(ERROR,
			"This syntax for optional fields is no longer allowed. Place the ? right after the field's name."),

	/** no parameters required */
	CLF_IMPLEMENT_EXTEND_WITH_WILDCARD(ERROR,
			"Wildcards may not be used as type argument when declaring an extended or implemented type."),

	/**
	 * 0: 'implement' or 'extend', 0: name of interface, 1: name of type variable, 2: comma-separated list of type
	 * arguments
	 */
	CLF_IMPLEMENT_EXTEND_SAME_INTERFACE_INCONSISTENTLY(ERROR,
			"Cannot %s interface %s multiple times with different type arguments for %s: %s."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper#descriptionDifferentFrom(TMember,TMember)} 2: additional
	 * error information
	 */
	CLF_IMPLEMENT_MEMBERTYPE_INCOMPATIBLE(ERROR, "Cannot implement %s with %s.%s"),

	/** 0: descriptions of missing members, 1: the consumer */
	CLF_IMPLEMENT_MIXIN_CONFLICTS(ERROR, "The %s cannot be mixed in %s due to conflicts."),

	/** 0: name of the current classifier, 1: abstract non overridden descriptions */
	CLF_MISSING_IMPLEMENTATION(ERROR, "Class %s must either be declared abstract or implement %s."),

	/** 0: name of the current classifier, 1: abstract non overridden descriptions */
	CLF_MISSING_IMPLEMENTATION_EXT(WARNING,
			"External class %s must either be declared abstract or explicit declare %s."),

	/** 0: class, method, getter or setter */
	CLF_ABSTRACT_FINAL(ERROR, "An abstract %s cannot be declared final."),

	/** no parameters required, will be changed to error (IDE-1236) */
	CLF_NO_FINAL_INTERFACE_MEMBER(WARNING, "In interfaces, only methods may be declared final."),

	/** no parameters required */
	CLF_NO_THIS_IN_STATIC_FIELD(ERROR, "The 'this' literal may not be used in initializers of static data fields."),

	/** no parameters required */
	CLF_NO_THIS_IN_FIELD_OF_INTERFACE(ERROR,
			"In interfaces, the 'this' literal may not be used in initializers of data fields."),

	/** no parameters required */
	CLF_NO_THIS_IN_STATIC_MEMBER_OF_INTERFACE(ERROR,
			"In interfaces, the 'this' literal may not be used in static methods or field accessors and in initializers of static data fields."),

	/** no parameters required */
	CLF_INVALID_ACCESS_OF_STATIC_MEMBER_OF_INTERFACE(ERROR,
			"Static members of interfaces may only be accessed directly via the type name of their containing interface."),

	/** 0: method, getter or setter, 1: name of method/getter/setter */
	CLF_STATIC_ABSTRACT(ERROR, "The %s %s may not be both static and abstract."),

	/** no parameters required */
	CLF_ABSTRACT_BODY(ERROR, "Abstract methods do not specify a body."),

	/** 0: method, getter or setter, 1: member name, 2: class name */
	CLF_ABSTRACT_MISSING(ERROR, "The abstract %s %s in class %s can only be defined in an abstract class."),

	/** 0: method, getter or setter, 1: member name */
	CLF_MISSING_BODY(ERROR, "The %s %s has to have either a body or must be defined abstract."),

	/** no parameters required */
	CLF_MISSING_CTOR_BODY(ERROR, "Constructors must have a body."),

	/** no parameters required */
	CLF_VOID_ACCESSOR(ERROR, "Void is not a valid type for getters and setters."),

	/** 0: accessor description, 1: verb, 2: missing accessor type */
	CLF_UNMATCHED_ACCESSOR_OVERRIDE(ERROR, "%s cannot be %s without overriding the corresponding %s."),

	/** 0: accessor description, 1: verb, 2: missing accessor type */
	CLF_UNMATCHED_ACCESSOR_OVERRIDE_JS(WARNING, "%s should not be %s without overriding the corresponding %s."),

	/** 0: interface name */
	CLF_MULTIPLE_ROLE_CONSUME(ERROR, "Cannot consume %s multiple times."),

	/** 0: 'Classes' or 'Roles' */
	CLF_EXT_EXTERNAL_N4JSD(ERROR, "%s declared as external have to be placed in a file with file extension 'n4jsd'."),

	/** none */
	CLF_EXT_PROVIDED_BY_RUNTIME_IN_RUNTIME_TYPE(ERROR,
			"Only runtime environments/libraries may specify elements provided by runtime."),

	/** no parameters required */
	CLF_EXT_UNALLOWED_N4JSD(ERROR,
			"Only namespaces, classes, interfaces, enums, type aliases and functions declared as external as well as structurally typed interfaces are allowed in n4jsd files."),

	/** no parameters required */
	CLF_EXT_EXPORTED(ERROR, "External elements have to be marked with modifier export."),

	/** no parameters required */
	CLF_EXT_NOT_ANNOTATED_EXTEND_N4OBJECT(ERROR,
			"External classes annotated with @EcmaScript are not allowed to inherit from n4js classes."),

	/** 0: classes or interfaces */
	CLF_EXT_CONSUME_NON_EXT(ERROR, "External %s are not allowed to consume a non external interface."),

	/** 0: classes or interfaces */
	CLF_EXT_IMPLEMENTS_NON_EXT(ERROR, "External %s are not allowed to implement an interface from a non n4jsd file."),

	/** 0: classes or interfaces */
	CLF_EXT_PUBLIC(ERROR, "External %s have to be marked as public (and without @Internal)."),

	/** no parameters required */
	CLF_EXT_INTF_PUBLIC(ERROR,
			"Structural typed interfaces in n4jsd files have to be marked as public (and without @Internal)."),

	/** 0: classes or interfaces */
	CLF_EXT_EXTERNAL(ERROR, "External %s have to be marked as external in n4jsd files."),

	/** 0: methods, getters or setters, 1: classes or interfaces */
	CLF_EXT_NO_METHOD_BODY(ERROR, "%s in external %s have to have no body."),

	/** 0: classes or interfaces */
	CLF_EXT_NO_FIELD_EXPR(ERROR, "Fields in external %s have to have no right side."),

	/** no parameters required */
	CLF_EXT_PUBLIC_CONSTRUCTOR(ERROR, "External classes annotated with @EcmaScript have to have a public constructor."),

	/** 0: classes or interfaces */
	CLF_EXT_NO_OBSERV_ANNO(ERROR, "External %s must not have the Observable annotation."),

	/** 0: methods, getters or setters, 1: classes or interfaces, 2: Observable or Nfon */
	CLF_EXT_METHOD_NO_ANNO(ERROR, "%s in external %s must not have the %s annotation."),

	/** no parameters required */
	CLF_EXT_LITERAL_NO_VALUE(ERROR, "An enumeration literal in a n4jsd file isn't allowed to define a value."),

	/** no parameters required */
	CLF_EXT_FUN_NO_BODY(ERROR, "External function declarations have to have no body."),

	/** 0 var or constant */
	CLF_EXT_VAR_NO_VAL(ERROR, "External %s declaration cannot be initialized."),

	/** spec: Component/Manifest/General-Constraint, no parameters required */
	CLF_EXT_NO_MATCH(WARNING,
			"For the given n4jsd file no corresponding external file resp. no matching implemented by expression can be found."),

	/**
	 * doc: validate duplicates between src/src-test and external (so there is e.g. file A.n4js that compiles to A.js
	 * that would be name conflict with A.js in external), spec: Component/Manifest/General-Contraint, 0: file, 1: path
	 */
	CLF_EXT_DUPLICATE_PATH_SRC_EXTERNAL(ERROR, "Duplicate external file %s, has been already defined in %s."),

	/** no parameters required */
	CLF_IN_DEFINITION_PRJ_NON_N4JS(WARNING,
			"Class declarations in definition projects should be annotated with @EcmaScript."),

	/** 0: annotation name, 1: type (interface or classifier) */
	CLF_EXT_PROVIDES_IMPL_ONLY_IN_DEFFILES(ERROR, "@%s must only be used in external %s in n4jsd files."),

	/** 0: annotation name, 1: type (interface or classifier) */
	CLF_EXT_PROVIDES_IMPL_ONLY_IN_INTERFACE_MEMBERS(ERROR, "@%s must only be used in %s."),

	/** 0: annotation name, 1: type (interface or classifier) */
	CLF_EXT_PROVIDES_IMPL_ONLY_IN_N4JS_INTERFACES(ERROR, "@%s must only be used in n4js interfaces or classes."),

	/** 0: type name, 1: access modifier name */
	CLF_LOW_ACCESSOR_WITH_INTERNAL(ERROR, "A %s with visibility %s shouldn't be annotated with @Internal."),

	/** 0: 'extend' or 'implement', 1: type name, 2: description of members causing problems */
	CLF_NON_ACCESSIBLE_ABSTRACT_MEMBERS(ERROR,
			"Cannot %s %s: cannot implement one or more non-accessible abstract members: %s."),

	/** no parameters required */
	CLF_MINIMAL_ACCESSIBILITY_IN_INTERFACES(ERROR, "Members of interfaces must not be declared private."),

	/** no parameters required */
	CLF_SPEC_WRONG_TYPE(ERROR, "Annotation @Spec may only be used with formal parameters of type ~i~this."),

	/** 0: name of field, 1: type of member, 2: type system error message */
	CLF_SPEC_WRONG_ADD_MEMBERTYPE(ERROR,
			"Type of structural member %s of spec parameter must be a subtype of %s: %s."),

	/** no parameters required */
	CLF_SPEC_MULTIPLE(ERROR, "Only a single formal parameter in each constructor may be annotated with @Spec."),

	/** 0: name of the superfluous property, 1: the type not defining the property */
	CLF_SPEC_SUPERFLUOUS_PROPERTIES(WARNING,
			"%s is not defined in %s; it will not have any effect in the spec constructor."),

	/**
	 * 0: name of the superfluous property, 1: the type not defining the property, 2: name of variable the object
	 * literal is assigned to
	 */
	CLF_SUPERFLUOUS_PROPERTIES(WARNING, "%s is not defined in %s; it will not be accessible from %s."),

	/** 0: name of the property, %s built-in/provided by runtime interface */
	CLF_SPEC_BUILT_IN_OR_PROVIDED_BY_RUNTIME_OR_EXTENAL_WITHOUT_N4JS_ANNOTATION(WARNING,
			"%s is a property of a built-in, provided by runtime, or external module with @EcmaScript annotation. Hence the interface %s can not be initialized in a spec constructor."),

	/** 0:the cycle */
	CLF_INHERITANCE_CYCLE(ERROR, "Inheritance cycle detected: %s."),

	/** no param */
	CLF_INTERNAL_BAD_WITH_PRIVATE_OR_PROJECT(ERROR, "@Internal is only allowed for public and protected."),

	/** no param */
	CLF_CANNOT_CALL_ABSTRACT_SUPER_METHOD(ERROR, "Cannot call super method since it is abstract."),

	/** no param */
	CLF_CANNOT_REFER_TO_DEFAULT_METHOD_WITH_SUPER(ERROR,
			"Cannot refer to default method of an implemented interface with super."),

	/** 0: name of polyfill, 1: 'class' or 'interface' including indefinite article */
	CLF_POLYFILL_EXTEND_MISSING(ERROR, "Polyfill %s must explicitly extend %s."),

	/** 0: name of polyfill */
	CLF_POLYFILL_NO_TOPLEVEL(ERROR, "Polyfill %s can only extend top level class declaration."),

	/**
	 * 0: 'class' or 'interface', 1: name of correct classifier kind (without article), 2: name of correct classifier
	 * kind including indefinite article, 3: name of *incorrect* classifier kind including indefinite article
	 */
	CLF_POLYFILL_DIFFERENT_CLASSIFIER_KIND(ERROR, "Polyfill for %s %s must be %s, not %s."),

	/** 0: name of polyfill, 1: 'class' or 'interface', 2: name of extended class */
	CLF_POLYFILL_DIFFERENT_NAME(ERROR, "Name of polyfill %s must equal name of filled %s %s."),

	/** 0: name of polyfill, 1: name polyfill's module, 2: name of filled class' module */
	CLF_POLYFILL_DIFFERENT_MODULE_SPECIFIER(ERROR,
			"Specifier %s of module containing polyfill %s must equal name of filled classes module specifier %s."),

	/** 0: name of polyfill, 1: global/not global polyfill module, 2: not/empty global polyfill */
	CLF_POLYFILL_DIFFERENT_GLOBALS(ERROR, "Module containing polyfill %s is %s, but filled classes module is %s."),

	/** 0: name of polyfill */
	CLF_POLYFILL_FILLED_NOT_PROVIDEDBYRUNTIME(ERROR, "Polyfill %s cannot fill class not provided by runtime."),

	/** 0: name of polyfill */
	CLF_POLYFILL_NOT_PROVIDEDBYRUNTIME(ERROR, "Polyfill %s must be provided by runtime."),

	/** 0: name of polyfill */
	CLF_POLYFILL_NO_IMPLEMENTS(ERROR, "Polyfill %s must not implement any interfaces."),

	/** 0: name of polyfill */
	CLF_POLYFILL_NO_EXTENDS_ADDITIONAL(ERROR, "Polyfill %s must not extend any additional interfaces."),

	/** 0: name of polyfill */
	CLF_POLYFILL_NOT_DIRECTLY_EXPORTED(ERROR, "Polyfill %s must be exported."),

	/** 0: name of polyfill, 1: name of polyfill modifier, 2: name of filled modifier, 3: 'class' or 'interface' */
	CLF_POLYFILL_DIFFERENT_MODIFIER(ERROR,
			"Polyfill %s cannot be declared %s, must be defined %s just as the filled %s."),

	/** 0: name of polyfill, 1: type system error message */
	CLF_POLYFILL_CTOR_NOT_OVERRIDE_COMPATIBLE(ERROR,
			"Constructor of polyfill %s must be override compatible with inherited constructor: %s"),

	/** 0: name of polyfill, 1: 'class' or 'interface' */
	CLF_POLYFILL_DIFFERENT_TYPEPARS(ERROR, "Polyfill %s must declare same type parameters as the filled %s."),

	/** 0: name of polyfill */
	CLF_POLYFILL_INCOMPLETE_TYPEARGS(ERROR,
			"Polyfill %s must pass all type parameters to type arguments (even optional ones)."),

	/** 0: name of polyfill, 1: type par, 2: type arg */
	CLF_POLYFILL_TYPEPARS_DIFFER_TYPEARGS(ERROR,
			"Polyfill %s must pass type parameters to type arguments in same order and without modifications, but %s differs from %s."),

	/** 0: list of modules name with polyfills, 0: member name */
	CLF_POLYFILL_MULTIPOLYFILLS_MEMBER_CONFLICT(ERROR,
			"Polyfills in %s provide member %s - only one provider per member is allowed."),

	/** 0: name of polyfill */
	CLF_POLYFILL_STATIC_FILLED_TYPE_NOT_AWARE(ERROR,
			"For static polyfills, the module of the filled type %s must be annotated with @StaticPolyfillAware."),

	/** 0: name of polyfill, 1: file extension (.n4js or .n4jsd) */
	CLF_POLYFILL_STATIC_DIFFERENT_VARIANT(ERROR,
			"Since static polyfill %s is declared in an %s file, the filled type must also be declared in an %s file."),

	/** 0: string that is used as name for more than one literal */
	ENM_DUPLICTAE_LITERALS(ERROR, "Multiple literals with name %s."),

	/** no parameters required */
	ENM_INVALID_VALUE_EXPRESSION(ERROR,
			"Only string literals and number literals are allowed as value of an enum literal."),

	/** 0: string with the name of the meta property */
	ENM_LITERALS_HIDE_META(ERROR, "EnumLiteral cannot have the same name as Enum meta property <%s>."),

	/** no parameters required */
	ENM_WITHOUT_LITERALS(ERROR, "An enum type must declare at least one literal."),

	/** no parameters required */
	ENM_BOTH_NUMBER_AND_STRING_BASED(ERROR, "An enum may not be annotated with both @NumberBased and @StringBased."),

	/** no parameters required */
	ENM_ILLEGAL_NUMERIC_VALUE(ERROR, "Values of type number may only be used for literals of @NumberBased enums."),

	/** no parameters required */
	ENM_ILLEGAL_STRING_VALUE(ERROR, "Values of literals in @NumberBased enums must be of type number."),

	/** no parameters required */
	ENM_INVALID_USE_OF_NUM_OR_STR_BASED_ENUM(ERROR,
			"A @NumberBased or @StringBased enum may only be used in type annotations and in property access expressions to access either one of its literals or the static getter called 'literals'."),

	/** no parameters required */
	/**
	 * ITF_MEMBER_ACCESSIBILITY(ERROR, "Member of an interface must not have lower accessibility than the containing
	 * interface."),
	 */

	/** no parameters required */
	ITF_NO_FINAL(ERROR, "Interfaces must not be declared final."),

	/** 0: name of field, 1: name of interface */
	ITF_NO_FIELD_INITIALIZER(ERROR,
			"Cannot initialize field in interface. Only classes or interfaces implementing %s can initialize %s."),

	/** 0: property kind name, 1: structural /nominal /<blank> */
	ITF_NO_PROPERTY_BODY(ERROR, "%s in %sinterfaces must not have a body."),

	/** no parameters required */
	ITF_CONSTRUCTOR_COVARIANCE(ERROR, "Constructors in interfaces must be annotated with @CovariantConstructor."),

	/** no parameters required */
	ITF_IN_DEFINITION_PRJ_NON_N4JS(WARNING,
			"Nominal interface declarations in definition projects should instead be structural (use '~')."),

	/** no parameters required */
	STRCT_ITF_CANNOT_EXTEND_INTERFACE(ERROR, "Structural interfaces cannot extend nominal interfaces."),

	/** no parameters required */
	STRCT_ITF_CANNOT_CONTAIN_STATIC_MEMBERS(ERROR, "Structural interfaces cannot contain static members."),

	/** no parameters required */
	STRCT_ITF_MEMBER_MUST_BE_PUBLIC(WARNING, "Non-public members of structural interfaces are effectless."),

	/** 0: name of cyclic type aliases */
	ALI_CYCLIC_TYPE_ALIAS(ERROR, "Cyclic type alias declaration: %s."),

	/** no parameters required */
	ALI_INVALID_MODIFIER(ERROR, "A type alias may be referenced %s only if its aliased type may be referenced %s."),

	/** 0: the string '{}' */
	ALI_INVALID_TYPE_ALIAS_IN_TYPE_TYPE_REF(ERROR,
			"A type alias may be used inside type%s or constructor%s only if its aliased type may be used at this location."),

	/** 0: passed parameter count, 1: expected parameter count */
	FUN_PARAM_COUNT(ERROR, "Passed parameter count %s doesn't match with expected parameter count %s."),

	/** no parameters required */
	FUN_BLOCK(WARNING,
			"Functions declarations should not be placed in blocks. Use a function expression or move the statement to the top of the outer function."),

	/** no parameters required */
	FUN_BODY(ERROR, "Functions have to have a body."),

	/** 0: element description, 1: conflicting category */
	FUN_NAME_RESERVED(ERROR, "%s may be confused with %s."),

	/** no parameters */
	FUN_NAME_MISSING(ERROR, "Function declarations must have a name."),

	/** 0: formal parameter */
	FUN_PARAM_OPTIONAL_WRONG_SYNTAX(ERROR, "Wrong syntax: Use %s=undefined instead of ?."),

	/** no parameters */
	FUN_PARAM_INITIALIZER_ILLEGAL_FORWARD_REFERENCE(ERROR,
			"Illegal forward reference to formal parameter of same function."),

	/** no parameters */
	FUN_PARAM_INITIALIZER_ONLY_UNDEFINED_ALLOWED(ERROR,
			"Only ''undefined'' allowed for initializers of default parameters in function types."),

	/** 0: formal parameter */
	/** 1: identifier */
	FUN_PARAM_INITIALIZER_ILLEGAL_REFERENCE_TO_BODY_VARIABLE(ERROR,
			"Initializer of parameter ''%s'' cannot reference the identifier ''%s'' declared in the body."),

	/** 0: formal parameter */
	FUN_PARAM_INITIALIZER_ILLEGAL_AWAIT_CALL(ERROR,
			"Illegal await-expression in initializer of formal parameter ''%s''."),

	/**
	 * 0: name of actually used generator type, 1: expected generator kind (synchronous or asynchronous), 2: name of
	 * expected generator type
	 */
	FUN_GENERATOR_RETURN_TYPE_MISMATCH(WARNING,
			"Type %s is intended for %s generator functions; consider using type %s instead."),

	/** no parameters required */
	AST_SEPARATE_DEFAULT_EXPORT_WITHOUT_FROM(ERROR, "Missing from-clause in default re-export."),

	/** 0: element, 1: kind of value */
	AST_ELEMENT_MISUSED_AS_VALUE_OR_TYPE(ERROR, "%s cannot be used as a %s."),

	/** no parameters required */
	AST_INVALID_NEW_TARGET(ERROR, "Invalid new.target."),

	/** no parameters required */
	AST_INVALID_YIELD_EXPRESSION(ERROR,
			"The parameter initializer of a generator may not contain a `yield` expression."),

	/** no parameters required */
	AST_CONST_IN_STATEMENT_POSITION(ERROR, "Const variable statements must be on the top level or nested in a block."),

	/** no parameters required */
	AST_LET_IN_STATEMENT_POSITION(ERROR, "Let declarations may not appear in statement position."),

	/** 0: name of blank const variable */
	AST_CONST_HAS_NO_INITIALIZER(ERROR, "Const variable %s must be provided with an initializer."),

	/** no parameters required */
	AST_STR_NO_OCTALS(ERROR, "octal literals and octal escape sequences are not allowed in strict mode."),

	/** 0: operand (decrement or increment) */
	AST_INVALID_OPERAND(ERROR, "Invalid %s operand."),

	/** no parameters required */
	AST_EXP_INVALID_LHS_ASS(ERROR, "Invalid assignment left-hand side."),

	/** 0: identifier */
	AST_RESERVED_IDENTIFIER(ERROR, "%s is a reserved identifier."),

	/** no parameters required */
	AST_STR_NO_WITH_STMT(ERROR, "With statement not allowed."),

	/** no parameters required, not used yet in AST, was before in N4JSStrictValidator */
	AST_STR_FUN_NOT_NESTED(ERROR, "Functions must only be declared on script level or as part of other expressions"),

	/** no parameters required */
	AST_INVALID_RETURN(ERROR, "return statement without enclosing function"),

	/** no parameters required */
	AST_INVALID_CONTINUE(ERROR, "continue statement without enclosing iteration"),

	/** no parameters required */
	AST_INVALID_BREAK(ERROR, "break statement without enclosing iteration or case block"),

	/** no parameters required */
	AST_INVALID_LABEL(ERROR, "Label must be of an enclosing iteration statement but may not cross function boundaries"),

	/** no parameters required */
	AST_THIS_WRONG_PLACE(ERROR,
			"The 'this' type isn't allowed on this place. (Please refer to Spec for valid use cases.)"),

	/** 0: variable name */
	AST_VAR_DECL_RECURSIVE(WARNING,
			"Reference to variable %s within the initializer expression of the declaration of %s."),

	/** no parameters required */
	AST_VAR_DECL_IN_FOR_INVALID_INIT(ERROR,
			"The iteration variable of a for..in or for..of loop must not be provided with an initializer."),

	/** no parameters required */
	AST_INVALID_FOR_AWAIT(ERROR, "Only for..of loops may be used for asynchronous iteration with 'for await'."),

	/** no parameters required */
	AST_INVALID_OPTIONAL_TYPE_PARAMS(ERROR,
			"Only type parameters of classes, interfaces, and type aliases may be declared optional."),

	/** no parameters required */
	AST_NO_TYPE_ARGS_IN_CLASSIFIERTYPEREF(ERROR, "Only raw types can be used in classifier type references."),

	/** no parameters required */
	AST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF(ERROR,
			"Function types are not allowed in classifier type references."),

	/** no parameters required */
	AST_INVALID_EXPR_IN_LHS_DESTRUCTURING_PATTERN(ERROR,
			"Only a variable or nested destructuring pattern is allowed at this location within a destructuring pattern."),

	/** no parameters required */
	AST_INVALID_PROPERTY_METHOD_IN_LHS_DESTRUCTURING_PATTERN(ERROR,
			"Property methods are not allowed within an object destructuring pattern."),

	/** no parameters required */
	AST_INVALID_DEFAULT_EXPR_SINGLE_NAME_PROPERTY(ERROR,
			"A default value is only allowed within a destructuring pattern."),

	/** no parameters required */
	AST_REST_MUST_APPEAR_AT_END(ERROR, "Rest operator only allowed on last element in an array destructuring pattern."),

	/** no parameters required */
	AST_REST_WITH_INITIALIZER(ERROR, "Rest operator does not support an initializer."),

	/** no parameters required */
	AST_TYPE_DECL_MISSING_NAME(ERROR, "Name missing in type declaration."),

	/** no type annotation for catch variable */
	AST_CATCH_VAR_TYPED(ERROR, "Catch variable must not be typed."),

	/** 0: name of the JavaScript variant of the context */
	AST_TOP_LEVEL_STATEMENTS(ERROR, "Top-level statements are not supported in %s files."),

	/** No parameters */
	AST_VAR_STMT_NO_DECL(ERROR, "A variable statement must at least contain one variable declaration."),

	/** 0: script annotation name */
	AST_SCRIPT_ANNO_INVALID_PLACEMENT(ERROR, "The script annotation @@%s must be placed at the top of the module."),

	/** 0: Forbidden Type e.g. null / undefined */
	AST_BINARY_LOGICAL_EXPRESSION_MISSING_PART(ERROR, "The logical expression is missing the %s."),

	/** no parameters required */
	AST_IMPORT_CALL_SPREAD(ERROR, "The spread operator is not allowed in import calls."),

	/** 0: Invalid parent e.g. && / || */
	AST_INVALID_COALESCE_PARENT(ERROR, "Nullish coalescing expressions cannot be contained within an %s operation. "),

	/** 0: Invalid child e.g. && / || */
	AST_INVALID_COALESCE_CHILD(ERROR, "Nullish coalescing expressions cannot immediately contain an %s operation."),

	/** 0: local variable name */
	CFG_LOCAL_VAR_UNUSED(WARNING, "The local variable %s is never used"),

	/** 0: local variable name */
	CFG_USED_BEFORE_DECLARED(WARNING, "Variable %s is used before it is declared"),

	/** 0: local variable name */
	/** 1: 'is' or 'may be' */
	/** 2: 'null' or 'undefined' */
	/** 3: reason (optional) */
	DFG_NULL_DEREFERENCE(WARNING, "Variable %s %s %s%s"),

	/** 0: shadowing object, 1: shadowed object */
	AST_NAME_SHADOW_WARN(WARNING, "%s shadows %s."),

	/** 0: shadowing object, 1: shadowed object */
	AST_NAME_SHADOW_ERR(ERROR, "%s shadows %s."),

	/** 0: hiding parameter 1: hidden parameter */
	AST_GLOBAL_NAME_SHADOW_ERR(ERROR, "%s shadows %s from global scope."),

	/** 0: name */
	AST_GLOBAL_JS_NAME_CONFLICT(ERROR, "Globally defined element named %s must be defined in runtime environment."),

	/** 0: name */
	AST_GLOBAL_NAME_CONFLICT(ERROR, "Globally defined element must not be named %s."),

	/** 0: duplicating object, 1: duplicated object */
	AST_NAME_DUPLICATE_ERR(ERROR, "%s duplicates %s."),

	/** 0: duplicating object, 1: duplicated object */
	AST_NAME_DUPLICATE_WARN(WARNING, "%s duplicates %s."),

	/** 0: grammar rule, 1: value */
	VCO_DOUBLE_NEGATIVE(ERROR, "%s-value may not be negative (value: %s)."),

	/** no parameters required */
	VCO_DOUBLE_CONVERT_EMPTY_STR(ERROR, "Couldn't convert empty string to a double value."),

	/** 0: the string value */
	VCO_DOUBLE_CONVERT_STR(ERROR, "Couldn't convert %s to a double value."),

	/** 0: grammar rule, 1: value */
	VCO_HEXINT_NEGATIVE(ERROR, "%s-value may not be negative (value: %s)."),

	/** no parameters required */
	VCO_HEXINT_CONVERT_EMPTY_STR(ERROR, "Couldn't convert empty string to an hex int value."),

	/** 0: the string value */
	VCO_HEXINT_CONVERT_TOO_SHORT(ERROR, "Couldn't convert %s to an int value."),

	/** 0: the string value */
	VCO_HEXINT_CONVERT_STR(ERROR, "Couldn't convert %s to an hex int value."),

	/** 0: grammar rule, 1: value */
	VCO_BINARYINT_NEGATIVE(ERROR, "%s-value may not be negative (value: %s)."),

	/** no parameters required */
	VCO_BINARYINT_CONVERT_EMPTY_STR(ERROR, "Couldn't convert empty string to a binary int value."),

	/** 0: the string value */
	VCO_BINARYINT_CONVERT_TOO_SHORT(ERROR, "Couldn't convert %s to an int value."),

	/** 0: the string value */
	VCO_BINARYINT_CONVERT_STR(ERROR, "Couldn't convert %s to a binary int value."),

	/** 0: grammar rule, 1: value */
	VCO_OCTALINT_NEGATIVE(ERROR, "%s-value may not be negative (value: %s)."),

	/** no parameters required */
	VCO_OCTALINT_CONVERT_EMPTY_STR(ERROR, "Couldn't convert empty string to an octal int value."),

	/** 0: the string value */
	VCO_OCTALINT_CONVERT_TOO_SHORT(ERROR, "Couldn't convert %s to an int value."),

	/** 0: the string value */
	VCO_OCTALINT_LEADING_ZEROS(ERROR, "Don't use extra leading zeros %s.'"),

	/** 0: the string value */
	VCO_OCTALINT_CONVERT_STR(ERROR, "Couldn't convert %s to an octal int value."),

	/** 0: grammar rule, 1: value */
	VCO_SCIINT_NEGATIVE(ERROR, "%s-value may not be negative (value: %s)."),

	/** no parameters required */
	VCO_SCIINT_CONVERT_EMPTY_STR(ERROR, "Couldn't convert empty string to an scientific int value."),

	/** 0: the string value */
	VCO_SCIINT_CONVERT_STR(ERROR, "Couldn't convert %s to an scientific int value."),

	/** 0: identifier, 1: position */
	VCO_IDENT_ESCAPE_SEQ(ERROR, "Illegal escape sequence in identifier %s at position %s."),

	/** 0: result, 1: identifier, 2: position */
	VCO_IDENT_ILLEGAL_CHAR_WITH_RESULT(ERROR, "Illegal character in identifier ''%s'' (%s) at position %s."),

	/** 0: identifier, 1: position */
	VCO_IDENT_ILLEGAL_CHAR(ERROR, "Illegal character in identifier ''%s'' at position %s."),

	/** no parameters required */
	VCO_JSXIDENT_WHITESPACE_COMMENT(ERROR,
			"JSX attribute names may not contain whitespace or comments. Attribute names ending with a '-' and directly followed by another attribute name are merged and must not contain whitespace or comments."),

	/** no parameters required */
	VCO_STRING_DOUBLE_QUOTE(ERROR, "String literal is not properly closed by a double-quote."),

	/** no parameters required */
	VCO_STRING_QUOTE(ERROR, "String literal is not properly closed by a quote."),

	/** no parameters required */
	VCO_TEMPLATE_QUOTE(ERROR, "Template literal is not properly closed by a backtick."),

	/** no parameters required */
	VCO_TEMPLATE_MIDDLE(ERROR, "Template literal is not properly closed by ${."),

	/** no parameters required */
	VCO_TEMPLATE_IN_OPT_CHAIN(ERROR, "Tagged template expressions are not permitted in an optional chain."),

	/** no parameters required */
	VCO_STRING_BAD_ESCAPE_WARN(WARNING, "Bad escapement"),

	/** no parameters required */
	VCO_STRING_BAD_ESCAPE_ERROR(ERROR, "Bad escapement"),

	/** no parameters required */
	VCO_REGEX_INVALID(ERROR, "Invalid regular expression literal"),

	/** 0: literal */
	VCO_REGEX_ILLEGAL_ESCAPE(ERROR, "Illegal escape sequence in regular expression %s"),

	/** no parameters required */
	VCO_REGEX_NAMED_GROUP(WARNING, "Named capture groups are not supported on all platforms."),

	/** no parameters required */
	VCO_NPE(ERROR,
			"A NullPointerException occurred. This indicates a missing value converter or a bug in its implementation."),

	/** 0: feature name */
	VCO_NULL_FEATURE(ERROR, "ValueConverter returned null for primitive feature %s"),

	/** no parameters required */
	BIT_SYMBOL_INVALID_USE(ERROR,
			"Invalid use of 'Symbol': may only be used to create symbols (i.e. Symbol()) or to access built-in symbols (e.g. Symbol.iterator)."),

	/** no parameters required */
	BIT_SYMBOL_NOT_A_CTOR(ERROR, "Symbol is not a constructor, use Symbol() without new."),

	/** no parameters required */
	TYS_MISSING(ERROR, "Types model hasn't been built for this file. Please report this bug to a N4JS developer."),

	/** 0: expression */
	TYS_CANNOT_TYPE(ERROR, "cannot type %s."),

	/** 0: left expression, 1: right expression */
	TYS_NO_SUBTYPE(ERROR, "%s is not a subtype of %s."),

	/** 0: expected type, 1: actual type */
	TYS_NO_SUPERTYPE_WRITE_ACCESS(ERROR, "expecting write-access for type %s but %s is not a super type of %s."),

	/** no parameters required */
	TYS_NULL_OBJECT(ERROR, "passed null object to system at %s."),

	/** no parameters required */
	TYS_VOID_AT_WRONG_LOCATION(ERROR,
			"Type 'void' may only be used to declare the return type of functions and methods."),

	/** 0: member type, 1: member name, 2: type name */
	TYS_MEMBER_NOT_IN_STRUCTURAL_TYPE_DEF_SITE(ERROR, "%s %s is not available for structurally defined type %s."),

	/** 0: member type, 1: member name, 2: type name */
	TYS_MEMBER_NOT_IN_STRUCTURAL_TYPE_USE_SITE(ERROR, "%s %s is not available for structurally referenced type %s."),

	/** 0: member type, 1: member name, 2: type name */
	TYS_MEMBER_NOT_IN_STRUCTURAL_FIELDS_TYPE_USE_SITE(ERROR,
			"%s %s is not available for fields-only-referenced type %s."),

	/** no parameters required */
	TYS_INSTANCEOF_NOT_SUPPORTED_FOR_USE_SITE_STRUCTURAL(ERROR,
			"'instanceof' cannot be used with use site structural typing."),

	/** 0: type name */
	TYS_INSTANCEOF_NOT_SUPPORTED_FOR_BUILT_IN_INTERFACES(ERROR,
			"'instanceof' cannot be used with built-in interface %s."),

	/** no parameters required */
	TYS_INSTANCEOF_NOT_SUPPORTED_FOR_PRIMITIVE_TYPES(ERROR, "'instanceof' cannot be used with primitive types."),

	/** 0: property name */
	TYS_PROPERTY_HAS_NO_SETTER(ERROR, "Property %s has no setter."),

	/** 0: type name */
	TYS_PRIMITIVE_TYPE_DYNAMIC(ERROR, "Primitive type %s must not be referenced dynamically."),

	/** no parameters required */
	TYS_COMPOUND_MISSING_GETTER(ERROR, "Missing getter on left-hand side of compound assignment."),

	/** no parameters required */
	TYS_NON_VOID_ASYNC(ERROR,
			"Internal error: Only Promise allowed as inferred return type of an async FunctionDefinition"),

	/** no parameters required */
	TYS_NON_THIS_ASYNC(ERROR, "The return type of an async function is not allowed to refer to the this-type."),

	/** 0: type name */
	TYS_FOR_IN_VAR_STRING(ERROR, "Type of for-in-loop variable must be a super type of string but %s is not."),

	/** no parameters required */
	TYS_ADDITIONAL_STRUCTURAL_MEMBERS_ON_TYPE_VARS(ERROR,
			"No additional structural members allowed on type variables, since they can cause collisions."),

	/** no parameters required */
	TYS_FUNCTION_DISALLOWED_AS_TYPE(ERROR, "The name of a declared function may not be used as a type name."),

	/** no parameters required */
	TYS_STRUCTURAL_PRIMITIVE(WARNING, "Structural type operator ~ does not have any effect on primitive types."),

	/** no parameters required */
	EXP_USE_OF_UNDEF_EXPR(WARNING,
			"The type of this expression is 'undefined', so it will never evaluate to a value other than 'undefined'."),

	/** 0: actual type */
	EXP_CALL_NOT_A_FUNCTION(ERROR, "Not a function or method: %s."),

	/** 0: comma-separated list of conflicting callable type references */
	EXP_CALL_CONFLICT_IN_INTERSECTION(ERROR, "More than one callable type in intersection: %s."),

	/** no parameters required */
	EXP_CALL_CLASS_CTOR(ERROR, "Cannot directly invoke class constructor functions; use 'new' instead."),

	/** 0: actual type */
	EXP_NEW_NOT_A_CTOR(ERROR, "Not a reference to a constructor: %s."),

	/** 0: intersection type with conflicting ctors or construct signatures */
	EXP_NEW_CONFLICT_IN_INTERSECTION(ERROR, "More than one constructor or construct signature in intersection: %s."),

	/** 0: type arg name of constructor, 1: static type */
	EXP_NEW_WILDCARD_NO_COVARIANT_CTOR(ERROR,
			"Cannot instantiate %s, because %s does not have a @CovariantConstructor."),

	/** 0: type arg name of constructor */
	EXP_NEW_WILDCARD_OR_TYPEVAR(ERROR, "Cannot instantiate %s."),

	/** 0: abstract class, interface, 1: classifier name */
	EXP_NEW_CANNOT_INSTANTIATE(ERROR, "Cannot instantiate %s %s."),

	/** 0: number of expected type args, 1: number of actual type args */
	EXP_WRONG_NUMBER_OF_TYPEARGS(ERROR, "Incorrect number of type arguments: expected %s, got %s."),

	/**
	 * 0: class, interface, method, function 1: name of classifier/method/function, 2: number of expected type args, 3:
	 * number of actual type args
	 */
	EXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ELEMENT(ERROR,
			"Incorrect number of type arguments for %s %s: expected %s, got %s."),

	/** 0: maximum number of element types in an ArrayN */
	EXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ITERABLE_N_SYNTAX(ERROR,
			"The ArrayN types are available for a maximum of %s element types only (i.e. type Array%s)."),

	/** 0: upper or lower, 1: covariant or contravariant */
	EXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG(ERROR,
			"Cannot use wildcard with %s bound as argument to a %s type parameter."),

	/** 0: covariant or contravariant, 1: covariant or contravariant */
	EXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG_IN_OUT(ERROR, "Cannot combine %s on use site with %s on definition site."),

	/** 0: expected num of params, 1: actual num of params */
	EXP_NUM_OF_ARGS_TOO_FEW(ERROR, "Incorrect number of arguments: expected %s, got %s."),

	/** 0: expected num of params, 1: actual num of params */
	EXP_NUM_OF_ARGS_TOO_MANY(ERROR, "Incorrect number of arguments: expected %s, got %s."),

	/** 0: from type ref, 1: target type ref */
	EXP_CAST_FAILED(ERROR, "Cannot cast from %s to %s"),

	/** 0: from type ref, 1: target type ref */
	EXP_CAST_UNNECESSARY(WARNING, "Unnecessary cast from %s to %s"),

	/** no parameter required */
	EXP_CAST_INVALID_TARGET(ERROR,
			"Can only cast to class, interface, enum, function, primitive, union or intersection types"),

	/** 0: recognized type, 2: result, e.g. NaN */
	EXP_MATH_OPERATION_RESULT_IS_CONSTANT(WARNING, "Operand %s will always result in %s."),

	/** 0: recognized type, 1: constant value */
	EXP_MATH_OPERAND_IS_CONSTANT(WARNING, "Operand of type %s will be interpreted as %s."),

	/** 0: recognized type, 2: result, e.g. NaN */
	EXP_MATH_TYPE_NOT_PERMITTED(ERROR, "Operand of type %s cannot be converted to number."),

	/** 0: left type 1: rght type 2: result */
	EXP_WARN_CONSTANT_EQUALITY_TEST(WARNING,
			"Neither %s is a subtype of %s nor %s is a subtype of %s. The expression will always evaluate to %s."),

	/** 0: Forbidden Type e.g. null / undefined */
	EXP_FORBIDDEN_TYPE_IN_BINARY_LOGICAL_EXPRESSION(ERROR,
			"%s is not allowed on the left hand side of a logical expression."),

	/** 0: member description, 1: type of target, 2: declared this type (from @This annotation) */
	EXP_ACCESS_INVALID_TYPE_OF_TARGET(ERROR,
			"Target of property access not a subtype of the declared @This type of %s: %s is not a subtype of %s."),

	/** 0: method name */
	EXP_METHOD_REF_UNATTACHED_FROM_RECEIVER(ERROR,
			"A reference to method %s is created detached from a (correct) this-instance."),

	/** 0: await expression, 1 async annotation */
	EXP_MISPLACED_AWAIT(ERROR, "%s is allowed only inside functions annotated with %s."),

	/** no parameter required */
	EXP_MISSNG_AWAIT_FOR_ASYNC_TARGET(WARNING,
			"Calling async function without await, Promise should be made explicit."),

	/** 0: Suspicious expression, 1: boolean value, 2:left-hand/right-hand */
	EXP_WARN_DISPENSABLE_CONDITIONAL_EXPRESSION(WARNING,
			"Dispensable use of conditional expression. The expression ''%s'' always evaluates to %s, so only the %s side will ever be evaluated."),

	/** no parameter required */
	EXP_AWAIT_NON_ASYNC(WARNING,
			"await should only be used on expressions of type Promise<?,?> since otherwise it has no effect."),

	/** 0: recognized expression */
	EXP_AWAIT_NON_ASYNC_SPECIAL(WARNING, "await should not be used on ''%s'' since it has no effect here."),

	/** no parameter required */
	FUN_RETURNTYPE_VOID_FOR_SETTER_VIOLATED(ERROR, "Set accessors must not return anything."),

	/** 0: name of return type */
	FUN_MISSING_RETURN_EXPRESSION(ERROR, "Return statement must have an expression of type %s"),

	/** no parameter required */
	FUN_MISSING_RETURN_OR_THROW_STATEMENT(ERROR, "Missing return or throw statement."),

	/** no parameter required */
	FUN_MISSING_ELSE_BRANCH_FOR_CONDITIONAL_LAST_CONTROL_FLOW(ERROR,
			"Last if statement on control flow path is missing required else branch."),

	/** no parameter required */
	FUN_MISSING_RETURN_OR_THROW_STATEMENT_WHILE_CANNOT_BE_CHECKED(ERROR,
			"Missing return or throw statement after while-expression. Correct termination of while-loops cannot be verified. Please append return/throw."),

	/** no parameter required */
	FUN_MISSING_RETURN_OR_THROW_STATEMENT_FOR_CANNOT_BE_CHECKED(ERROR,
			"Missing return or throw statement after for-expression. Correct termination of for-loops cannot be verified. Please append return/throw."),

	/** no parameter required */
	FUN_DEAD_CODE(WARNING, "Dead code."),

	/** 0: last executed statement name */
	FUN_DEAD_CODE_WITH_PREDECESSOR(WARNING, "Dead code. No execution possible after %s."),

	/** no parameters required */
	FUN_SETTER_CANT_BE_VARIADIC(ERROR, "Variadic parameter is not allowed in setter declarations."),

	/** no parameters required */
	FUN_SETTER_CANT_BE_DEFAULT(ERROR, "Default parameter is not allowed in setter declarations."),

	/** no parameters required */
	FUN_PARAM_VARIADIC_ONLY_LAST(ERROR, "Only the last formal parameter can be variadic."),

	/** 0: name of parameter */
	FUN_PARAM_IMPLICIT_DEFAULT_PARAM(WARNING,
			"This parameter is changed to the default parameter ''%s=undefined'' since it follows a default parameter."),

	/** no parameter required */
	FUN_PARAM_VARIADIC_WITH_INITIALIZER(ERROR, "Variadic parameters must not have a default initializer."),

	/** no parameter required */
	FUN_SINGLE_EXP_LAMBDA_IMPLICIT_RETURN_ALLOWED_UNLESS_VOID(ERROR,
			"An arrow-function is used in a context where its body is expected to have some value as opposed to being void."),

	/** 0: type parameter name */
	FUN_UNUSED_GENERIC_TYPE_PARAM(WARNING, "Type variable %s not used in parameters or return type."),

	/** no parameters required */
	KEY_SUP_INVALID_USAGE(ERROR,
			"Keyword super may only be used in member access expressions, call expressions or new expressions."),

	/** no parameters required */
	KEY_SUP_ACCESS_INVALID_LOC(ERROR,
			"Super member access may only be used in constructors, methods, getters, or setters."),

	/** no parameters required */
	KEY_SUP_ACCESS_NO_EXTENDS(ERROR, "Super member access requires a declared super type."),

	/** no parameters required */
	KEY_SUP_ACCESS_INVALID_LOC_INTERFACE(ERROR, "Super member access may not be used in interfaces."),

	/** no parameters required */
	KEY_SUP_ACCESS_FIELD(ERROR, "Super member access may not be used to access a field."),

	/** no parameters required */
	KEY_SUP_CTOR_INVALID_LOC(ERROR, "Super calls may only be used in constructors."),

	/** no parameters required */
	KEY_SUP_NEW_NOT_SUPPORTED(ERROR, "Keyword super with in expressions is not supported yet."),

	/** no parameters required */
	KEY_SUP_NESTED(ERROR, "Super call must not be nested in function expressions."),

	/** no parameters required */
	KEY_SUP_CTOR_EXPRSTMT(ERROR, "Super constructor call must no be used in a composed expression."),

	/** 0: container in which super is nested (with line number). */
	KEY_SUP_CTOR_NESTED(ERROR,
			"Super constructor call must only be directly contained in constructor body, i.e. not nested in %s."),

	/** 0: container in which super is nested (with line number). */
	KEY_SUP_CTOR_INVALID_EXPR_BEFORE(ERROR, "Super constructor call must not be preceded by %s."),

	/** 0: super class name */
	KEY_SUP_REQUIRE_EXPLICIT_SUPERCTOR_CALL(ERROR, "Must explicitly invoke constructor of super class %s."),

	/** no parameters required */
	KEY_SUP_CALL_NO_INDEXACCESS(ERROR, "Super member access may not be used with index access."),

	/** no parameters required */
	KEY_THIS_REJECTED_IN_TOP_LEVEL_LAMBDA(ERROR,
			"In a top-level arrow function, the 'this' keyword refers to nothing."),

	/** no parameters required */
	EXP_OPTIONAL_INVALID_PLACE(ERROR, "The optional modifier isn't allowed here."),

	/** 0: name of const variable */
	EXP_ASSIGN_CONST_VARIABLE(ERROR, "Const variable %s is read-only."),

	/** 0: names of legal built-in symbol */
	EXP_INDEXED_ACCESS_SYMBOL_INVALID(ERROR, "Indexed access with built-in symbols is only allowed for %s."),

	/** 0: name of built-in symbol, 1: name of expected receiver type */
	EXP_INDEXED_ACCESS_SYMBOL_WRONG_TYPE(ERROR,
			"Access of property Symbol.%s only allowed for instances of %s, immediate instances of Object, and dynamic types."),

	/** 0: name of built-in symbol, 1: name of receiver type */
	EXP_INDEXED_ACCESS_SYMBOL_READONLY(ERROR, "Access to property Symbol.%s of an %s is read-only."),

	/** no parameters required */
	EXP_INDEXED_ACCESS_ENUM(ERROR, "Indexed access is not allowed for enumerations."),

	/** no parameters required */
	EXP_INDEXED_ACCESS_FORBIDDEN(ERROR,
			"Indexed access is only allowed for strings, arrays and iterables and for immediate(!) instances of Object."),

	/** 0: index-key in indexed-access */
	EXP_INDEXED_ACCESS_COMPUTED_NOTFOUND(ERROR, "Member %s not found."),

	/** no parameters required */
	EXP_INDEXED_ACCESS_IMPL_RESTRICTION(ERROR,
			"Implementation restriction: member name clashes with compiler-internal, synthetic, mangled name."),

	/** no parameters required */
	EXP_PROMISIFY_INVALID_USE(ERROR,
			"@Promisify may only be applied to a call expression with a @Promisifiable function or method as target."),

	/** no parameters required */
	EXP_COMPUTED_PROP_NAME_DISCOURAGED(WARNING,
			"Computed property name using an expression other than a compile-time expression; this property won't be type-checked at compile time."),

	/** 0: detailed message */
	EXP_COMPILE_TIME_MANDATORY(ERROR, "Not a compile-time expression: %s."),

	/**
	 * 0: usage type ('invoke' or 'instantiate'), 1: name of interface with call/construct signature, 2: signature kind
	 * ('call' or 'construct')
	 */
	EXP_CALL_CONSTRUCT_SIG_OF_INTERFACE_DIRECTLY_USED(ERROR,
			"Cannot directly %s interface %s; its %s signature applies to values of type %s, not to %s itself."),

	/** 0: type names, submessage -- full message is a concatenation of these messages */
	COMP_SUBMESSAGES(ERROR, "%s in %s."),

	/** 0: member name */
	UNI_UNCOMMON(ERROR, "Member %s not present in all types of union or incompatible."),

	/** 0: member name, 1: list of types in union that do not contain a member of that name */
	UNI_MISSING(ERROR, "Member %s not present in all types of union; missing from: %s."),

	/** 0: either 'getters' or 'setters', 1: member name, 2: either 'read-only' or 'write-only' */
	UNI_INVALID_COMBINATION(ERROR, "Union combines fields and %s with name %s and therefore property %s is %s."),

	/** 0: member name */
	UNI_INVALID_COMBINATION_SETTER_VS_READ_ONLY_FIELD(ERROR,
			"Union combines fields and setters with name %s but still write-access is not allowed because one or more fields are read-only (const or @Final)."),

	/** 0: member name, 1: list of kinds */
	UNI_MULTIPLE_KINDS(ERROR, "Member %s not of same kind in all types of union: %s."),

	/** 0: member name, 1: list of types */
	UNI_DIFFERENT_TYPES(ERROR, "Member %s not of same type in all types of union: %s."),

	/** no parameters required */
	UNI_ANY_USED(WARNING, "The use of the any type in a union type is discouraged."),

	/** no parameters required */
	UNI_REDUNDANT_SUBTYPE(WARNING, "The use of redundant subtypes is discouraged."),

	/** no parameters required */
	INTER_ANY_USED(WARNING, "The use of the any type in an intersection type is discouraged."),

	/** no parameters required */
	INTER_ONLY_ONE_CLASS_ALLOWED(WARNING,
			"An intersection type should not contain more than one class. Otherwise there cannot exist a value of such a type."),

	/** no parameters required */
	INTER_TYEPARGS_ONLY_ONE_CLASS_ALLOWED(WARNING,
			"Type arguments for the same covariant type parameter in an intersection type should not contain more than one class. Otherwise there cannot exist a value of such a type."),

	/** no parameters required */
	INTER_WITH_ONE_GENERIC(WARNING,
			"An intersection type should not contain different type arguments for the same invariant type parameter. Otherwise is can be instantiated only with undefined."),

	/** no parameters required */
	INTER_REDUNDANT_SUPERTYPE(WARNING, "The use of redundant supertypes is discouraged."),

	/** 0: method member name, 1: list of kinds */
	INTER_MEMBER_TYPE_CONFLICT(ERROR,
			"Member method %s is in conflict with non-method members in types of intersection: %s."),

	/** 0: member name */
	INTER_UNCOMMON(ERROR, "Member %s is incompatible in types of intersection."),

	/** no parameters required */
	ANN__N4JS_NO_EFFECT(WARNING, "This annotation is deprecated and has no effect."),

	/** no parameters required */
	ANN__ONLY_IN_N4JS(ERROR, "Annotations are a N4JS feature and cannot be used in JS mode."),

	/** 0: annotation name */
	ANN_NOT_DEFINED(ERROR, "The annotation @%s is not defined."),

	/** 0: annotation name, 1: expected number of arguments, 2: actual number of arguments */
	ANN_WRONG_NUMBER_OF_ARGUMENTS(ERROR, "Wrong number of annotation arguments: @%s expects %s but got %s."),

	/** 0: annotation name, 1: expected type */
	ANN_WRONG_ARGUMENT_TYPE(ERROR, "The annotation @%s expects a %s here."),

	/** 0: annotation name */
	ANN_DISALLOWED_AT_LOCATION(ERROR, "The annotation @%s is disallowed for this location."),

	/** 0: annotation name, 1: list of language variants */
	ANN_ONLY_ALLOWED_LOCATION_CONSTRUCTORS(ERROR,
			"The annotation @%s may only be applied at a parameter of a constructor."),

	/** 0: name of annotation (without @) */
	ANN_NON_REPEATABLE(ERROR, "Duplicate annotation of non-repeatable type @%s."),

	/** 0: name of annotation */
	ANN_UNNECESSARY(WARNING, "Unnecessary @%s."),

	/** no parameters required */
	ANN_THIS_DISALLOWED_ON_STATIC_MEMBER_OF_INTERFACE(ERROR,
			"@This annotation not allowed on static members of interfaces."),

	/** 0: member description, 1: description of containing type, 2: required type */
	ANN_THIS_NOT_SUBTYPE_OF_CONTAINING_TYPE(ERROR, "Declared @This type of %s in %s must be a subtype of %s."),

	/** no parameters required */
	ANN_POLY_STATIC_POLY_ONLY_IN_POLYFILL_MODULE(ERROR,
			"The annotation StaticPolyfill is only allowed in modules annotated as StaticPolyfillModule."),

	/** no parameters required */
	ANN_POLY_AWARE_AND_MODULE_MUTUAL_EXCLUSIVE(ERROR,
			"A module cannot be annotated as filler and filling at the same time."),

	/** no parameters required */
	ANN_PROMISIFIABLE_MISSING_CALLBACK(ERROR,
			"The annotation @Promisifiable is only allowed on functions/methods that take a function as a last argument (i.e. the callback)."),

	/** no parameters required */
	ANN_PROMISIFIABLE_BAD_CALLBACK_MORE_THAN_ONE_ERROR(ERROR,
			"The callback of a @Promisifiable function/method must not have more than one parameter of type Error."),

	/** no parameters required */
	ANN_PROMISIFIABLE_BAD_CALLBACK_ERROR_NOT_FIRST_ARG(ERROR,
			"If the callback of a @Promisifiable function/method has a parameter of type Error, this parameter must be the first parameter."),

	/** 0: annotation name */
	ANN_DISALLOWED_IN_NONDEFINTION_FILE(ERROR, "The annotation @%s can only be applied in definition files."),

	/** 0: annotation name */
	ANN_DISALLOWED_IN_NON_RUNTIME_COMPONENT(ERROR, "The annotation @%s can only be applied in a runtime component."),

	/** 0: bug id */
	ANN_UNUSED_IDEBUG(ERROR,
			"No matching error found, apparently bug IDEBUG-%s has been fixed or does not occur here."),

	/** 0: name of the violating annotation. */
	ANN_REQUIRES_TEST(ERROR, "Only methods annotated with @Test could be annotated with %s."),

	/** 0: annotation name, 1: list of language variants */
	ANN_ONL_ALLOWED_IN_VARIANTS(ERROR, "The annotation @%s may only be applied in %s files."),

	/** 0: annotation name, 1: list of language variants */
	ANN_ONL_ALLOWED_AT_CLASSES_IN_N4JSD(ERROR, "The annotation @%s may only be applied at classes in n4jsd files."),

	/** 0: annotation name, 1: list of language variants */
	ANN_DISALLOWED_ON_SHAPES(ERROR, "Only nominal interfaces can be annotated with @%s."),

	/** 0: annotation name */
	ANN__TEST_ONLY_IN_TEST_SOURCES(WARNING,
			"Test annotation @%s may only be used in test source folders (defined in package.json)."),

	/** 0: library name, 1: String of concatenated polyfilled things. */
	POLY_ERROR_IN_RUNTIMEDEPENDENCY(ERROR, "Erroneous library %s provides contradicting polyfills for %s."),

	/** 0: String of concatenated library names, 1: String of concatenated polyfilled things. */
	POLY_CLASH_IN_RUNTIMEDEPENDENCY(ERROR,
			"The libraries %s provide polyfills for the same element %s and cannot be used together."),

	/** 0: String of concatenated library names, 1: String of concatenated polyfilled things. */
	POLY_CLASH_IN_RUNTIMEDEPENDENCY_MULTI(ERROR,
			"The libraries %s provide polyfills for the same elements %s and cannot be used together."),

	/** 0: String of conflicting static-polyfilling modules. */
	POLY_CLASH_IN_STATIC_POLYFILL_MODULE(ERROR,
			"Only one module annotated with @@StaticPolyfillModule is allowed per project. Conflicting with %s."),

	/** no param */
	POLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES(ERROR,
			"Only top-level classes annotated as StaticPolyfill are allowed in a module annotated with StaticPolyfillModule."),

	/** no param */
	POLY_IMPLEMENTING_INTERFACE_NOT_ALLOWED(ERROR,
			"The filling class cannot introduce additional interfaces; all interfaces must be declared on the filled class."),

	/** doc: detected dependency cycle 0:String describing the cycle. */
	PROJECT_DEPENDENCY_CYCLE(ERROR, "Dependency cycle of the projects: %s."),

	/** 0: ID of the non-existing project. */
	NON_EXISTING_PROJECT(ERROR, "Project does not exist with project ID: %s."),

	/** 0: ID of the non-existing project. */
	MISSING_YARN_WORKSPACE(ERROR,
			"Project depends on workspace project %s which is missing in the node_modules folder. Either install project %s or introduce a yarn workspace of both of the projects."),

	/** 0: human readable name of the obsolete feature block. */
	OBSOLETE_BLOCK(WARNING, "Obsolete %s block."),

	/** 0: project project ID, 1: human readable name of the project type, 2: human readable name of the feature. */
	INVALID_PROJECT_TYPE_REF(WARNING, "Project %s of type %s cannot be declared among the %s."),

	/** 0: library project ID */
	INVALID_API_PROJECT_DEPENDENCY(WARNING,
			"Library project %s with an implementation ID cannot be declared among the dependencies of an API project."),

	/** 0: violating feature/block name, 1: actual project type name */
	INVALID_FEATURE_FOR_PROJECT_TYPE(WARNING, "%s cannot be specified for %s projects."),

	/** 0: name used in duplicate */
	DUPLICATE_PROJECT_REF(ERROR, "Duplicate project reference %s."),

	/** 0: name of the containing folder, 1: name of the nested folder */
	OUTPUT_AND_SOURCES_FOLDER_NESTING(ERROR, "%s must not be located inside %s."),

	/** no parameters required */
	PROJECT_REFERENCES_ITSELF(ERROR, "Project cannot reference itself."),

	/** 0: the type of the deprecated project 1: alternatives */
	DEPRECATED_PROJECT_TYPE(WARNING, "Project type ''%s'' is deprecated and will be removed soon. %s"),

	/** no parameters are required. */
	MISMATCHING_TESTED_PROJECT_TYPES(WARNING, "Tested projects should have the same project type."),

	/**
	 * 0: current project implementation ID, 1: the violating project's ID, 2: the implementation ID of the violating
	 * project
	 */
	MISMATCHING_IMPLEMENTATION_ID(WARNING,
			"Implementation ID mismatch. Current project belongs to ''%s'' implementation while %s project belongs to ''%s'' implementation."),

	/** 0: test library name */
	SRCTEST_NO_TESTLIB_DEP(ERROR, "Project with source folder of type test should depend on %s."),

	/**
	 * 0: ID of the violating transitive external project dependency, 1: ID of the workspace project that is required by
	 * an external one.
	 */
	EXTERNAL_PROJECT_REFERENCES_WORKSPACE_PROJECT(WARNING,
			"Transitive external project dependency of project ''%s'' requires a workspace project ''%s''. Current project setup could result in runtime oddities. It is highly recommended to import project ''%s'' into workspace as well."),

	/** 0: project type of containing project */
	INVALID_FILE_TYPE_FOR_PROJECT_TYPE(ERROR, "An n4js file may not be contained in a project of type ''%s''."),

	/** 0: resource name */
	NO_PROJECT_FOUND(WARNING, "No project found for resource %s."),

	/** 0: part of the specifier that contains a dot, 1: the module specifier */
	MOD_NAME_MUST_NOT_CONTAIN_DOTS(ERROR, "%s of this module contain(s) the disallowed character ''.'' : ''%s''."),

	/** 0: dependency name, 1: required version, 2: present version */
	NO_MATCHING_VERSION(WARNING, "Project %s is required in version %s, but only version %s is present."),

	/**
	 * doc: checks if the module specifier wildcard can be resolved to an existing resource (not applied for
	 * implementation provided by runtime), spec: Component/Manifest/ModuleSpecifier-Constraint, 0: the module specifier
	 */
	NON_EXISTING_MODULE_SPECIFIER(WARNING, "Module specifier %s doesn't exist."),

	/**
	 * doc: checks for invalid wildcards, spec: Component/Manifest/ModuleSpecifierWildcardConstraints, 0: the invalid
	 * part of the wild card
	 */
	INVALID_WILDCARD(ERROR, "'%s' isn't a valid character sequence in a wild card."),

	/**
	 * doc: disallow relative navigation, spec: Component/Manifest/ModuleSpecifierWildcardConstraints, no parameters
	 * required
	 */
	NO_RELATIVE_NAVIGATION(ERROR, "Relative navigation isn't allowed in a module specifier."),

	/**
	 * doc: disallow switching off semantic validation for n4js files, spec:
	 * Component/Manifest/ModuleSpecifier-Constraint, 0: module filter type (e.g. noValidate)
	 */
	DISALLOWED_NO_VALIDATE_FOR_N4JS(ERROR, "%s paths shouldn't match n4js files."),

	/** 0: sub description, 1: extend/implement, 2: super description, 3: implements/extends */
	SYN_KW_EXTENDS_IMPLEMENTS_MIXED_UP(ERROR, "The %s cannot %s %s, use '%s'."),

	/** no parameters required */
	SYN_KW_EXTENDS_IMPLEMENTS_WRONG_ORDER(ERROR, "Extended class must be declared before implemented interfaces."),

	/** 0: class description, 1: keyword, 2: super description */
	CLF_WRONG_META_TYPE(ERROR, "The %s cannot %s %s."),

	/** 0: modifier name, 1: type of element that may not have this modifier (e.g. 'class', 'interface', etc.) */
	SYN_MODIFIER_INVALID(ERROR, "Modifier %s is not allowed on a %s."),

	/** 0: modifier name */
	SYN_MODIFIER_DUPLICATE(ERROR, "Duplicate modifier %s."),

	/** no parameters required */
	SYN_MODIFIER_ACCESS_SEVERAL(ERROR, "Only a single access modifier may be provided."),

	/** 0: modifiers in correct order */
	SYN_MODIFIER_BAD_ORDER(ERROR, "Modifiers should appear in this order: %s."),

	/** 0: element {modifier|keyword}, 1: name of modifier or keyword element (e.g. public) */
	SYN_UNNECESSARY_ELEMENT(WARNING, "Unnecessary %s %s."),

	/** no parameters required */
	EXP_ONLY_TOP_LEVEL_ELEMENTS(ERROR, "Only top level elements can be exported."),

	/** 0: element kind, 1: element name */
	EXP_PRIVATE_ELEMENT(ERROR, "Private %s %s cannot be exported."),

	/** no parameters required */
	DESTRUCT_EMPTY_PATTERN(ERROR,
			"Empty destructuring pattern (disallowed for time being, since ES6 implementations have different semantics)."),

	/** 0: name of missing property, 1: type of value to be destructured */
	DESTRUCT_PROP_MISSING(ERROR,
			"Value to be destructured does not contain a property, field or getter named '%s': %s."),

	/** 0: name of property that returned AbstractDescriptionWithError, 1: error message */
	DESTRUCT_PROP_WITH_ERROR(ERROR, "Property named '%s' is not readable: %s."),

	/**
	 * 0: name of variable, 1: 'at index N' or 'of property NAME', 2: error message from type system (must not end in a
	 * dot!)
	 */
	DESTRUCT_TYPE_ERROR_VAR(ERROR, "Variable %s cannot hold destructured value %s: %s."),

	/**
	 * 0: kind of destructuring pattern ('Array' or 'Object' or 'Nested array' or 'Nested object'), 1: 'destructured
	 * value at index N' or 'destructured value of property NAME', 2: error message from type system (must not end in a
	 * dot!)
	 */
	DESTRUCT_TYPE_ERROR_PATTERN(ERROR, "%s destructuring pattern cannot be applied to %s: %s."),

	////// Dependency Injection
	/**
	 * 0: type name, 1: additional description identifying the place of the violating context (name of the
	 * member/parameter)
	 */
	DI_NOT_INJECTABLE(ERROR,
			"Type %s is not injectable%s: only user-defined, non-generic, nominally typed interfaces and classes are allowed."),

	/** no parameters are required */
	DI_VARARGS_NOT_INJECTABLE(ERROR, "Injection of parameters that are variadic or optional is not supported."),

	/** 0: type name */
	DI_MUST_BE_INJECTED(WARNING,
			"Type %s must be injected, because it contains or inherits one or more members annotated with @Inject."),

	/** 0: annotation name, 1: name of required annotation on containing class */
	DI_ANN_ONLY_ON_CLASS_ANNOTATED_WITH(ERROR, "The annotation @%s is only allowed on classes annotated with @%s."),

	/** 0: annotation name, 1: name of required annotation on containing class */
	DI_ANN_ONLY_ON_METHOD_IN_CLASS_ANNOTATED_WITH(ERROR,
			"The annotation @%s is only allowed on methods contained in a class annotated with @%s."),

	/** 0: annotation name, 1: name of required annotation on argument class */
	DI_ANN_ARG_MUST_BE_ANNOTATED_WITH(ERROR, "Argument to annotation @%s must be a class annotated with @%s."),

	/** 0: the name of the violating annotation. */
	DI_ANN_BIND_SECOND_MUST_BE_SUBTYPE_FIRST(ERROR, "Second argument to @%s must be a subtype of the first."),

	/** no parameters required */
	DI_ANN_PROVIDES_METHOD_MUST_RETURN_VALUE(ERROR, "A provider method must return a value."),

	/** no parameters required */
	DI_ANN_INTERFACE_INJECTION_NOT_SUPPORTED(ERROR, "Injection inside interfaces is not supported."),

	/** no parameters required */
	DI_ANN_INJECTOR_EXTENDS(ERROR, "Classes annotated with @GenerateInjector cannot extend other class."),

	/** 0: the binded type name */
	DI_ANN_BIND_SINGLETON_TARGET_SHOULD_BE_DEFINED_AS_SINGLETON(ERROR,
			"%s can be defined as a singleton if it is annotated with @Singleton on the definition site."),

	/** no parameters required */
	DI_ANN_INJECTOR_CANNOT_BE_INJECTED_INTO_INJECTOR(ERROR,
			"Types annotated with @GenerateInjector cannot be injected. Use @WithParentInjector instead for creating nested injectors."),

	/** 0: the cyclic graph as a string */
	DI_ANN_USE_INJECTOR_CYCLE(ERROR, "A cycle was detected among the parent injectors: %s."),

	/** 0: the name of the unavailable field */
	DI_FIELD_IS_NOT_INJECTED_YET(ERROR, "%s is not yet injected at this point."),

	/** 0: super class type name, 1: violating type name */
	DI_CTOR_BREAKS_INJECTION_CHAIN(WARNING,
			"Constructor at super class %s is annotated with @Inject. Omitting the @Inject annotation from constructor at class %s could break injection chain."),

	/** no parameters required */
	DI_ANN_BINDER_NOT_APPLICABLE(ERROR,
			"Annotation @Binder is applicable only for (exported) non-abstract class definitions."),

	/** no parameters required */
	DI_ANN_BINDER_AND_INJECTOR_DONT_GO_TOGETHER(ERROR,
			"Annotations @Binder and @GenerateInjector may not be applied both on the same class definition."),

	/** no parameters required */
	DI_ANN_DUPLICATE_BINDING(ERROR,
			"Duplicate @Binding-s (two different bindings share the same key, for the same @Binder)."),

	/** no parameters required */
	DI_ANN_BIND_ABSTRACT_TARGET(ERROR, "The target of a @Binding must be a non-abstract class."),

	/** no parameters required */
	DI_ANN_INJECT_METHOD_NOT_SUPPORTED_YET(ERROR, "Method injection not supported yet."),

	/** no parameters required */
	DI_ANN_BINDER_EXTENDS(ERROR, "Classes annotated with @Binder may not extend another class."),

	/** no parameters required */
	DI_ANN_INJECTOR_CTOR_MUST_BE_INJECT(ERROR,
			"The constructor of an injector must itself be injected unless it declares no parameters."),

	/** no parameters required */
	DI_ANN_INJECTOR_REQUIRED(ERROR, "Only types annotated with @GenerateInjector can be used here."),

	/** 0: list of missing binders (as a list of type names) */
	DI_ANN_MISSING_PROVIDED_BINDERS(ERROR,
			"No binders are provided (third param for this callsite). The following binders are used by the injector and themselves require injection: %s."),

	/** no parameters required */
	DI_ANN_INJECTOR_MISSING(ERROR, "An instance of N4Injector is required here."),

	/** 0: list of missing binders (as a list of type names) */
	DI_ANN_MISSING_NEEDED_BINDERS(ERROR,
			"Instances are missing for the following binders (they are used by the injector and themselves require injection): %s."),

	/** no parameters required */
	DI_API_INJECTED(ERROR,
			"The class being instantiated (or one of its super-types) has been marked @Injected in an API project."),

	/** no parameters required */
	DI_ANN_INJECTED_NOT_APPLICABLE(ERROR, "@Injected annotates a class (abstract or not) defined in an API project."),

	////// JSX */
	/** 0: name of opening JSX element, 1: name of closing JSX element */
	JSX_JSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH(ERROR,
			"Opening element %s does not match with closing element %s."),

	/** 0: name of the type JSX element is binding to */
	JSX_REACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR(ERROR,
			"JSX element is expected to bind to either a function or class, but bind to type %s instead."),

	/** No parameter */
	JSX_REACT_ELEMENT_CLASS_MUST_NOT_BE_ABSTRACT(ERROR, "JSX element class must not be abstract."),

	/** 0: name of the return type of the function that JSX element is binding to */
	JSX_REACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR(ERROR,
			"Expecting a function returning a value of type %s but the return type is %s."),

	/** no argument */
	JSX_REACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR(ERROR, "The referred class is not a subtype of React.Component"),

	/** 0: name of the function that JSX element is binding to */
	JSX_REACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE(ERROR,
			"React functional component %s cannot start with lower case."),

	/** 0: name of the class that JSX element is binding to */
	JSX_REACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE(ERROR,
			"React class component %s cannot start with lower case."),

	/** 0: name of JSX element is binding to */
	JSX_JSXELEMENT_NOT_BIND_TO_REACT_COMPONENT(ERROR, "JSX element %s does not bind to any valid React component."),

	/** 0: name of JSX element is binding to */
	JSX_TAG_UNKNOWN(WARNING, "Tag %s is neither a known HTML tag nor an SVG tag."),

	/** 0: name of JSX property */
	JSX_JSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED(ERROR,
			"Non-optional property %s should be specified."),

	/**
	 * 0: The name of attribute in spread operator, 1: type of the attribute, 2: the declared type of the corresponding
	 * property in 'props'
	 */
	JSX_JSXSPREADATTRIBUTE_WRONG_SUBTYPE(ERROR, "Attribute %s has wrong type because %s not subtype of %s."),

	/** 0: The name of attribute in spread operator, 1: name of JSX element */
	JSX_JSXSPREADATTRIBUTE_NOT_DECLARED_IN_PROPS(WARNING,
			"Attribute %s is not a declared property in the props of %s."),

	/** 0: The name of attribute in JSX property, 1: name of JSX element */
	JSX_JSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS(WARNING,
			"Attribute %s is not a declared property in the props of %s."),

	/** 0: JSX element in non-JSX file */
	JSX_JSXELEMENT_IN_NON_JSX_RESOURCE(ERROR, "JSX element is expected to be placed in JSX like resource, was %s."),

	/** No parameter */
	JSX_REACT_NAMESPACE_NOT_ALLOWED(ERROR, "Namespace to react must be React."),

	/** No parameter */
	JSX_REACT_NOT_RESOLVED(ERROR, "Cannot resolve JSX implementation."),

	/** No parameter */
	JSX_NAME_CANNOT_BE_REACT(ERROR, "Element cannot be named React in N4JSX file."),

	////// THIRD PARTY */
	/** no parameters required */
	THIRD_PARTY_BABEL_LET_CONST_IN_FUN_EXPR(WARNING,
			"This code is prone to Babel bug #6302. If you use Babel in your build pipeline, you should rename this let/const or the containing function expression."),

	////// N4JS package.json */
	/** no parameters */
	PKGJ_MISSING_DEPENDENCY_N4JS_RUNTIME(ERROR,
			"Missing dependency to 'n4js-runtime' (mandatory for all N4JS projects of type library, application, test)."),

	/** no parameters */
	PKGJ_WRONG_DEPENDENCY_N4JS_RUNTIME(ERROR,
			"Dependency to 'n4js-runtime' should be defined below key 'dependencies', not 'devDependencies'."),

	/** 0: The package name without scope, as declared in the package.json, 1: project folder name */
	PKGJ_PACKAGE_NAME_MISMATCH(WARNING,
			"As a convention the package name '%s' should match the name of the project folder '%s' on the file system."),

	/** 0: The scope name, as declared in the package.json, 1: parent folder name */
	PKGJ_SCOPE_NAME_MISMATCH(WARNING,
			"As a convention the scope name '%s' should match the name of the project folder's parent folder '%s' on the file system."),

	/** 0: The project name without scope */
	PKGJ_INVALID_PROJECT_NAME(ERROR, "The name '%s' is not a valid package name."),

	/** 0: The scope name */
	PKGJ_INVALID_SCOPE_NAME(ERROR, "The name '%s' is not a valid scope name."),

	/** 0: Invalid source container type name */
	PKGJ_INVALID_SOURCE_CONTAINER_TYPE(ERROR, "Invalid source container type '%s'."),

	/** 0: the non-existing path */
	PKGJ_NON_EXISTING_PATH(ERROR, "Path %s does not exist."),

	/** no parameters */
	PKGJ_EMPTY_SOURCE_PATH(ERROR, "Source container paths must not be empty."),

	/** 0: the non-existing source container path */
	PKGJ_NON_EXISTING_SOURCE_PATH(WARNING, "Source container path %s does not exist."),

	/** 0: the path 1: 'in'-clause with leading space (e.g. ' in external, test') */
	PKGJ_DUPLICATE_SOURCE_CONTAINER(WARNING, "Duplicate path '%s' has already been declared as source container%s."),

	/** 0: the container source path */
	PKGJ_NESTED_SOURCE_CONTAINER(ERROR,
			"A source container must not be nested within other source containers (nested in %s)"),

	/** no parameters */
	PKGJ_NO_OUTPUT_FOLDER(ERROR, "There is no output folder defined, so compilation isn't possible."),

	/** 0: the invalid path */
	PKGJ_INVALID_PATH(ERROR, "'%s' is not a valid path."),

	/** 0: the absolute path */
	PKGJ_INVALID_ABSOLUTE_PATH(ERROR, "Path '%s' must not be absolute."),

	/** 0: the non-directory path */
	PKGJ_EXPECTED_DIRECTORY_PATH(ERROR, "Path '%s' does not point to a directory."),

	/** 0: the non-existing main module specifier */
	PKGJ_NON_EXISTING_MAIN_MODULE(ERROR, "Main module specifier %s does not exist."),

	/** 0: invalid module filter type, 1: all valid module filter types */
	PKGJ_INVALID_MODULE_FILTER_TYPE(ERROR, "Invalid module filter type '%s'. Valid filter types are %s."),

	/** 0: invalid module specifier */
	PKGJ_INVALID_MODULE_FILTER_SPECIFIER(ERROR,
			"Invalid module specifier. Use simple strings or object syntax instead."),

	/** no parameters */
	PKGJ_INVALID_MODULE_FILTER_SPECIFIER_EMPTY(ERROR,
			"The filter specifier and declared source container must not be empty."),

	/** no parameters */
	PKGJ_DUPLICATE_MODULE_FILTER_SPECIFIER(ERROR, "Duplicate module filter specifier."),

	/** no parameters */
	PKGJ_SRC_IN_FILTER_IS_NO_DECLARED_SOURCE(ERROR,
			"The given source container '%s' has not been declared as source container."),

	/** 0: the invalid part of a wildcard */
	PKGJ_INVALID_WILDCARD(ERROR, "'%s' is not a valid character sequence in a wildcard."),

	/** no parameters */
	PKGJ_NO_RELATIVE_NAVIGATION(ERROR, "Relative navigation is not allowed in a module filter specifier."),

	/** 0: the module filter specifier */
	PKGJ_MODULE_FILTER_DOES_NOT_MATCH(WARNING, "Module filter '%s' does not match any modules."),

	/** 0: module filter type (e.g. noValidate) */
	PKGJ_FILTER_NO_N4JS_MATCH(ERROR, "Module filters of type %s must not match N4JS modules/files."),

	/** no parameters */
	PKGJ_APIIMPL_MISSING_IMPL_PROJECTS(ERROR,
			"When defining an implementation ID, you also have to define one or more API projects that are implemented by this project using property 'n4js.implementedProjects'."),

	/** no parameters */
	PKGJ_APIIMPL_REFLEXIVE(ERROR, "An implementation project may not implement itself."),

	/** no parameters required */
	PKGJ_APIIMPL_MISSING_IMPL_ID(ERROR,
			"When defining one or more implemented projects, you also have to define an implementation ID, using property 'implementationId'."),

	/** 0: invalid project type */
	PKGJ_INVALID_PROJECT_TYPE(ERROR, "Invalid project type '%s'."),

	/** 0: project id 1: section label (e.g. required runtime libraries) */
	PKGJ_PROJECT_REFERENCE_MUST_BE_DEPENDENCY(ERROR,
			"The project reference %s in %s must also be declared as explicit project dependency in 'dependencies' or 'devDependencies'."),

	/** 0: message */
	PKGJ_SEMVER_ERROR(ERROR, "%s"),

	/** 0: message */
	PKGJ_SEMVER_WARNING(WARNING, "%s"),

	/** 0: version number string representation, 1: reason */
	PKGJ_INVALID_VERSION_NUMBER(ERROR, "Invalid version number '%s': %s."),

	/** 0: version constraint string representation, 1: reason */
	PKGJ_INVALID_VERSION_REQUIREMENT(WARNING, "Invalid version requirement '%s': %s. Will assume empty string."),

	/** 0: project type */
	PKGJ_PROJECT_TYPE_MANDATORY_OUTPUT_AND_SOURCES(ERROR,
			"Projects of type %s must always declare an output folder and at least one source container."),

	/** no parameters */
	PKGJ_EMPTY_PROJECT_REFERENCE(ERROR, "A project reference must not be empty."),

	/** no parameters */
	PKGJ_EMPTY_INIT_MODULE(ERROR, "An init module specifier must not be empty."),

	/** 0: project type, 1: empty or 'not ', 2: property name */
	PKGJ_DEFINES_PROPERTY(ERROR, "A project of type '%s' must %sdefine the property '%s'."),

	/** 0: property name */
	PKGJ_PROPERTY_UNKNOWN(WARNING, "Property '%s' is unknown."),

	/** 0: implementation project name, 1: type definition project name */
	PKGJ_IMPL_PROJECT_IS_MISSING_FOR_TYPE_DEF(WARNING,
			"The implementation project %s of type definition project %s is missing from the dependencies section."),

	/** 0: 'Source' or 'Output code' */
	PKGJ_REWRITE_MODULE_SPECIFIERS__EMPTY_SPECIFIER(ERROR, "%s module specifier must not be empty."),

	/** no parameters */
	PKGJ_REWRITE_MODULE_SPECIFIERS__INVALID_VALUE(ERROR,
			"String expected (i.e. the module specifier to use in the output code)."),

	/** 0: dependency cycle (optional) */
	LTD_ILLEGAL_LOADTIME_REFERENCE(ERROR,
			"Load-time references to the same or other modules are not allowed within a runtime dependency cycle (except in extends/implements clauses).%s"),

	/**
	 * 0: name of load-time dependency target module, 1: comma-separated list of other load-time dependency source
	 * modules (including the prefix 'modules ' or 'module '), 2: dependency cycle (optional)
	 */
	LTD_LOADTIME_DEPENDENCY_CONFLICT(ERROR,
			"A load-time dependency target module %s must only be imported once within the same runtime dependency cycle, but %s is also imported by %s."),

	/** 0: dependency cycle (optional) */
	LTD_LOADTIME_DEPENDENCY_CYCLE(ERROR,
			"Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.%s"),

	/**
	 * 0: name of load-time dependency target module, 1: modules that could heal this reference (including the prefix
	 * 'one of the modules ' or 'module '), 2: dependency cycle (optional)
	 */
	LTD_REFERENCE_TO_LOADTIME_DEPENDENCY_TARGET(ERROR,
			"When importing modules from a runtime cycle, those that are the target of a load-time dependency (marked with * below) may only be imported after first importing one of the others. Thus, import of module %s must be preceded by an import of %s.%s"),

	;

	public final Severity severity;
	private final String msgTemplate;

	IssueCodes(Severity severity, String msgTemplate) {
		this.severity = severity;
		this.msgTemplate = msgTemplate;
	}

	public String getMessage(Object... values) {
		if (values == null) {
			return msgTemplate;
		}
		for (int i = 0; i < values.length; i++) {
			if (!(values[i] instanceof String)) {
				values[i] = values[i].toString();
			}
		}
		return msgTemplate.formatted(values);
	}

	public IssueItem toIssueItem(Object... values) {
		return new IssueItem(this, severity, getMessage(values));
	}

	public IssueItem toIssueItemWithData(List<String> data, Object... values) {
		return new IssueItem(this, severity, getMessage(values), data.toArray(new String[0]));
	}

	static public Severity getSeverityForName(String issueName) {
		try {
			return valueOf(issueName).severity;
		} catch (Exception e) {
			return null;
		}
	}
}
