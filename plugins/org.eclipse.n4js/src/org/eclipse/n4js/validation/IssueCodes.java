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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.xtext.diagnostics.Severity;

import com.google.common.base.Preconditions;

/**
 * Enum contains all issues
 */
@SuppressWarnings("javadoc")
public enum IssueCodes {

	/** Only for internal use. */
	INVALID_ISSUE_CODE(WARNING, null),

	/**
	 * The following message is intended for ES6 features that are implemented in N4JS but are not yet supported in
	 * current version of V8 or some other relevant runtime environment. 0: description of the feature that is not
	 * supported
	 */
	UNSUPPORTED(ERROR, "Unsupported feature: {0}."),

	/** Not necessary. */
	SCR_HASHBANG_WRONG_LOCATION(ERROR, "Hashbang must start at the absolute start of a script or module"),

	/** 0: file names of files that contain errors */
	EXTERNAL_LIBRARY_ERRORS(ERROR, "External library error: {0}"),

	/** 0: file names of files that contain warnings */
	EXTERNAL_LIBRARY_WARNINGS(ERROR, "External library warning: {0}"),

	/**
	 * 0: kind of throwable ('error', 'exception', or 'throwable'), 1: simple name of throwable, 2: message of throwable
	 */
	POST_PROCESSING_FAILED(ERROR, "{0} {1} thrown during post-processing (please report this!): {2}"),

	/** Unknown type ref in resource */
	TYS_UNKNOWN_TYPE_REF(ERROR, "Resource contains unknown type ref (please report this!)"),

	/** 0: namespace name, 1: import module name, that both occur multiple times in this combination */
	IMP_STMT_DUPLICATE_NAMESPACE(ERROR, "Duplicate namespace import statement with name {0} and imported module {1}."),

	/** 0: imported element local name, 1: import module name, that both occur multiple times in this combination */
	IMP_STMT_DUPLICATE_NAMED(ERROR, "Duplicate named import statement with local name {0} and imported module {1}."),

	/** 0: type1 imported name1, 1: import module name, 2: name under which type is already imported */
	IMP_DUPLICATE(ERROR, "{0} from {1} is already imported as {2}."),

	/** 0: import module name, 1: namespace name already importing those elements */
	IMP_DUPLICATE_NAMESPACE(ERROR, "Element {0} of {1} is already imported in namespace {2}."),

	/**
	 * 0: local name (import name, alias name or namespace name), 1: import module name, 2: name under which type is
	 * already imported
	 */
	IMP_LOCAL_NAME_CONFLICT(ERROR, "Name {0} is already used as {1}."),

	/** 0: type name, 1: alias name */
	IMP_PLAIN_ACCESS_OF_ALIASED_TYPE(ERROR, "{0} has been imported as {1}."),

	/** 0: type name, 1: full name (namespace + '.' + type name) */
	IMP_PLAIN_ACCESS_OF_NAMESPACED_TYPE(ERROR, "{0} has been imported as {1}."),

	/** 0: imported name */
	IMP_UNUSED_IMPORT(WARNING, "The import of {0} is unused."),

	/** 0: imported name */
	IMP_NOT_EXPORTED(ERROR, "Element {0} is not exported."),

	/** 0: type import name */
	IMP_UNRESOLVED(ERROR, "Import of {0} cannot be resolved."),

	/** Not necessary. */
	IMP_DISCOURAGED_PLACEMENT(WARNING, "The import statement should be placed on top of other statements."),

	/** 0: type or variable, 1: type name, 2: comma separated list of types - the sources of the imports */
	IMP_AMBIGUOUS(ERROR, "The {0} {1} is ambiguously imported from {2}."),

	/** 0: type or variable, 1: type name, 2: comma separated list of types - the sources of the imports */
	IMP_AMBIGUOUS_WILDCARD(ERROR, "The {0} {1} is ambiguously imported from {2}."),

	/** 0: imported type name, 1: qualified name of the module */
	IMP_CONFLICT(ERROR, "Conflicting import of {0} with import from {1}."),

	/** 0: imported type1 name, 1: type1 alias, 2: type2 imported name, 3: type2 import module name */
	IMP_CONFLICT_ALIAS_TYPE(ERROR,
			"Conflicting import of {0} as {1}. Cannot import same type with different name ({2}) from {3}."),

	/** 0: type1 imported name, 1: type2 alias, 2: type2 import module name */
	IMP_CONFLICT_ALIASES(ERROR,
			"Conflicting import of {0}. Cannot import same type with different name ({1}) from {2}."),

	/** 0: type1 imported name1, that occur multiple times, 1: import module name */
	IMP_DUPLICATEX(ERROR, "Duplicate import of {0} in import from {1}."),

	/** 0: type1 imported name1, 1: type1 imported name2, 2: import module name */
	IMP_DUPLICATE_ALIAS(ERROR, "Duplicate import of {0}, already defined as {1} in import from {2}."),

	/** 0: module in wildcard specifier */
	IMP_EMPTY_WILDCARD_IMPORT(WARNING,
			"The wild-card import from {0} doesn't import anything because nothing is exported there."),

	/** 0: alias in namespace import */
	IMP_STATIC_IMPORT_PLAIN_JS(ERROR, "Use dynamic import in order to access elements of {0}."),

	/** 0: alias in namespace import */
	IMP_DYNAMIC_IMPORT_N4JS(ERROR, "N4JS module {0} must not be imported dynamically."),

	/** 0: variant type, 1: alias in namespace import */
	IMP_DYNAMIC_IMPORT_N4JSD(WARNING, "The {0} module {1} should not be imported dynamically."),

	/** no parameters required */
	IMP_DEFAULT_EXPORT_DUPLICATE(ERROR, "Duplicate default export."),

	/** 0: local name of the imported element */
	IMP_IMPORTED_ELEMENT_READ_ONLY(ERROR, "Imported element {0} is read-only."),

	/** 0: local name of the imported element, 1: re-exporting project name to use, 2: imported project name */
	IMP_IMPORTED_ELEMENT_FROM_REEXPORTING_PROJECT(ERROR,
			"The imported element {0} is defined in the re-exporting project {1} but project {2} was imported. Import from {1} instead."),

	/** 0: method, field, getter, setter, member, 1: member name */
	VIS_ILLEGAL_MEMBER_ACCESS(ERROR, "The {0} {1} is not visible."),

	/** 0: function name */
	VIS_ILLEGAL_FUN_ACCESS(ERROR, "The function {0} is not visible."),

	/** 0: type name */
	VIS_ILLEGAL_TYPE_ACCESS(ERROR, "The type {0} is not visible."),

	/** 0: variable name */
	VIS_ILLEGAL_VARIABLE_ACCESS(ERROR, "The variable {0} is not visible."),

	/** 0: member name, 1: type defining member */
	VIS_ILLEGAL_STATIC_MEMBER_WRITE_ACCESS(ERROR,
			"Write access to the static member {1} defined in {0} must use {0} directly."),

	/** 0: member name, 1: type defining member, 2: member alias */
	VIS_ILLEGAL_STATIC_MEMBER_WRITE_ACCESS_WITH_ALIAS(ERROR,
			"Write access to the static member {1} defined in {0} must use the alias {2} directly."),

	/**
	 * 0: member kind, 1: member name, 2: 'read-only', when a read-only member was used with write access or
	 * 'write-only' when a write-only member was used with read access.
	 */
	VIS_WRONG_READ_WRITE_ACCESS(ERROR, "The {0} {1} is {2}."),

	/** 0: static or non-static, 1: member name, 2: static or non-static */
	VIS_WRONG_STATIC_ACCESSOR(ERROR, "The {0} member {1} cannot be accessed from a {2} context."),

	/** 0: type variable name */
	VIS_WRONG_TYPE_VARIABLE_ACCESSOR(ERROR, "The type variable {0} cannot be accessed from a static context."),

	/** 0: type parameter name, 1: kind of type hidden (e.g. class, type variable, etc.) */
	VIS_TYPE_PARAMETER_HIDES_TYPE(WARNING, "The type parameter {0} hides {1} {0}."),

	/** 0: concept which is not allowed, 1: Mode (n4js,strict,...) in which it is not allowed. */
	VIS_RESTRITCTED_USAGE(ERROR, "The usage of {0} is not allowed in {1} mode."),

	/**
	 * 0: 'constructor' or 'construct signature', 1: classifier name whose constructor / construct signature is not
	 * visible.
	 */
	VIS_NEW_CANNOT_INSTANTIATE_INVISIBLE_CONSTRUCTOR(ERROR, "The {0} of {1} is not visible."),

	/** no parameters required */
	TYP_TYPE_PARAM_MANDATORY_AFTER_OPTIONAL(ERROR,
			"Mandatory type parameters may not follow optional type parameters."),

	/** no parameters required */
	TYP_TYPE_PARAM_DEFAULT_REFERENCES_LATER_TYPE_PARAM(ERROR,
			"Default type arguments may only reference type parameters that are declared before the current type parameter."),

	/** 0: name of type parameter, 1: error message of subtype check */
	TYP_TYPE_PARAM_DEFAULT_NOT_SUBTYPE_OF_BOUND(ERROR,
			"Default argument of optional type parameter {0} must comply to its upper bound, but: {1}"),

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
	CLF_CALL_CONSTRUCT_SIG_DUPLICATE(ERROR, "Duplicate {0} signature."),

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
	CLF_NAME_CONTAINS_DISCOURAGED_CHARACTER(WARNING, "Name should not contain {0} character."),

	/** 0: static or const keywords */
	CLF_NAME_DOES_NOT_MATCH_WITH_PATTERN_FOR_STATIC_OR_CONST(WARNING,
			"Name should be all upper case with words separated by underscores for variables with {0} modifier."),

	/** 0: element meta type */
	CLF_NAME_DOES_NOT_START_UPPERCASE(WARNING, "{0} names should start with upper case letter."),

	/** 0: element meta type */
	CLF_NAME_DOES_NOT_START_LOWERCASE(WARNING, "{0} names should start with lower case letter."),

	/** 0: element description, 1: conflicting category */
	CLF_NAME_INDISTINGUISHABLE(ERROR, "{0} may be indistinguishable with {1}."),

	/** 0: element description, 1: conflicting category */
	CLF_NAME_RESERVED(WARNING, "{0} may be confused with {1}."),

	/** no parameters required */
	CLF_NAME_CONFLICTS_WITH_CONSTRUCTOR(WARNING, "Name may be confused with constructor."),

	/** 0: element description, 1: conflicting type name 2: actual type */
	CLF_NAME_DIFFERS_TYPE(WARNING, "{0} is not of type {1} but of type {2}."),

	/** 0: name of final type, 1: name of type parameter */
	CLF_UPPER_BOUND_FINAL(WARNING,
			"Final type {0} should not be used as upper bound of type parameter {1}. Final types cannot be extended."),

	/** no parameters required */
	CLF_DEF_SITE_VARIANCE_ONLY_IN_CLASSIFIER(ERROR,
			"Declaration of definition-site variance only allowed in generic classes and interfaces, not in generic functions or methods."),

	/** 0: 'covariant' or 'contravariant', 1: 'covariant', 'contravariant', or 'invariant' */
	CLF_TYPE_VARIABLE_AT_INVALID_POSITION(ERROR, "Cannot use {0} type variable at {1} position."),

	/** 0: the actual primitive type */
	CLF_EXTENDS_PRIMITIVE_GENERIC_TYPE(ERROR, "Cannot subclass primitive type {0}."),

	/** 0: class name */
	CLF_EXTEND_FINAL(ERROR, "Cannot extend final class {0}."),

	/** 0: keyword, 1: name of class */
	CLF_EXTEND_NON_ACCESSIBLE_CTOR(ERROR, "Cannot extend {0} {1} because its constructor is not accessible."),

	/** 0: name of class, 1: name of super class */
	CLF_OBSERVABLE_MISSING(ERROR,
			"Class {0} extends observable class {1} and must therefore be annotated with @Observable."),

	/** no parameters required */
	CLF_TEST_CLASS_NOT_EXPORTED(WARNING,
			"Classes containing test methods must be abstract or marked with modifier export."),

	/** /** 0: module name, 1: path to other module */
	CLF_DUP_MODULE(ERROR, "A duplicate module {0} is also defined in {1}."),

	/** /** 0: module name, 1: js module, 2: path to other module */
	CLF_DUP_DEF_MODULE(ERROR, "A duplicate definition module {0} for {1} is also defined in {2}."),

	/**
	 * /** 0: first name with line ({@link ValidatorMessageHelper/**nameWithLine(TMember,TMember)}, 1: other name with
	 * line ({@link ValidatorMessageHelper/**nameWithLine(TMember,TMember)}
	 */
	CLF_DUP_CTOR(ERROR, "Constructor line {0} duplicates constructor in line {1}."),

	/**
	 * /** 0: first short description ({@link ValidatorMessageHelper/**descriptionWithLine(TMember,TMember)}, 1: other
	 * short description ({@link ValidatorMessageHelper/**descriptionWithLine(TMember,TMember)}
	 */
	CLF_DUP_MEMBER(ERROR, "The {0} duplicates {1}."),

	/** /** 0: first short description ({@link ValidatorMessageHelper/**descriptionWithLine(TMember,TMember)} */
	CLF_DUP_WITH_MEMBER(ERROR,
			"The {0} conflicts with the structural this type in the inherit constructor definition."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 2: setter or
	 * getter
	 */
	CLF_OVERRIDE_FIELD_REQUIRES_ACCESSOR_PAIR(ERROR, "The {0} cannot override {1}, matching {2} missing."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_CONSUMED_OVERRIDE_FIELD(ERROR, "The consumed {0} cannot override {1}."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * 'overriding' or 'implementing' 2: overridden description
	 * ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDE_ANNOTATION(ERROR, "The {0} {1} {2} must be annotated with @Override."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_REDEFINED_NON_ACCESSIBLE(ERROR, "The {0} cannot override or implement non-accessible {1}."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDE_FINAL(ERROR, "The {0} cannot override final {1}."),

	/**
	 * 0: overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1: 'final'
	 * or 'const'
	 */
	CLF_OVERRIDE_WITH_FINAL_OR_CONST_FIELD(ERROR, "Cannot override {0} with a {1} field."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDE_CONST(ERROR, "The {0} cannot override {1}."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDEN_CONCRETE_WITH_ABSTRACT(ERROR, "The abstract {0} cannot override concrete {1}."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDE_VISIBILITY(ERROR, "The {0} cannot reduce the visibility of {1}."),

	/** 0: method, getter or setter, 1: member name */
	CLF_OVERRIDE_NON_EXISTENT(ERROR, "The {0} {1} must override or implement a {0} from a super class or interface."),

	/** 0: member description, 1: member description */
	CLF_OVERRIDE_NON_EXISTENT_INTERFACE(WARNING,
			"The {0} does not override {1} (no inheritance of static members in interfaces); remove annotation @Override."),

	/**
	 * 0: description of constructor, 1: description of classifier inheriting the constructor, 2: description of
	 * classifier owning the constructor
	 */
	CLF_PSEUDO_REDEFINED_SPEC_CTOR_INCOMPATIBLE(ERROR,
			"Inherited {0} in context of {1} not compatible to itself in context of {2}."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden/implemented/consumed, 2: overridden description
	 * ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)} 3: additional members or error
	 * information (may be empty)
	 */
	CLF_REDEFINED_TYPE_NOT_SAME_TYPE(ERROR, "Type of {0} must equal type of {1} {2}.{3}"),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden/implemented/consumed, 2: overridden description
	 * ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_REDEFINED_BY_CONSUMED_TYPE_NOT_SAME_TYPE(ERROR, "Type of inherited {0} must equal type of {1} {2}."),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden/implemented/consumed, 3: signature type error message, 4: additional members or error information (may
	 * be empty)
	 */
	CLF_REDEFINED_MEMBER_TYPE_INVALID(ERROR, "Type of {0} does not conform to {2} {1}: {3}.{4}"),

	/**
	 * 0: consumed/inherited, 1: overriding description
	 * ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 2: overridden/implemented/consumed,
	 * 3: overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 4:
	 * signature type error message
	 */
	CLF_REDEFINED_BY_CONSUMED_MEMBER_TYPE_INVALID(ERROR, "Type of {0} {1} does not conform to {2} {3}: {4}."),

	/** 0: comma separated description of conflicting members */
	CLF_CONSUMED_MEMBER_SOLVABLE_CONFLICT(ERROR, "Redefine ambiguously consumed members: {0}."),

	/** 0: comma separated description of conflicting members */
	CLF_CONSUMED_MEMBER_UNSOLVABLE_CONFLICT(ERROR, "Incompatible consumed members: {0}."),

	/** 0: inherited member leading to conflict, 1: comma separated description of conflicting members */
	CLF_CONSUMED_INHERITED_MEMBER_UNSOLVABLE_CONFLICT(ERROR, "Inherited {0} cannot implement {1}."),

	/**
	 * 0: overriding description, should usually start with 'Signature of '
	 * ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1: overridden/implemented/consumed,
	 * 2: overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 3:
	 * signature type error message, 4: additional members or error information (may be empty)
	 */
	CLF_REDEFINED_METHOD_TYPE_CONFLICT(ERROR, "{0} does not conform to {1} {2}: {3}.{4}"),

	/**
	 * 0: overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_OVERRIDE_MEMBERTYPE_INCOMPATIBLE(ERROR, "Cannot override {1} with {0}."),

	/**
	 * TODO remove?! 0: overriding description
	 * ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1: overridden description
	 * ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_CONSUMED_OVERRIDE_MEMBERTYPE_INCOMPATIBLE(ERROR, "Cannot override {1} with consumed {0}."),

	/**
	 * 0: getter/setter, 0: overridden descriptions (CSV)
	 * ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}
	 */
	CLF_CONSUMED_FIELD_ACCESSOR_PAIR_INCOMPLETE(ERROR, "Missing {0} to completely override consumed {1}."),

	/** no parameters required */
	CLF_FIELD_CONST_FINAL(ERROR, "A field may not be declared both final and const."),

	/** no parameters required */
	CLF_FIELD_CONST_STATIC(ERROR, "All const fields are static. Remove unnecessary modifier 'static'."),

	/** 0: name of const field */
	CLF_FIELD_CONST_MISSING_INIT(ERROR, "Const field {0} must be provided with an initializer."),

	/** no parameters required */
	CLF_FIELD_FINAL_STATIC(ERROR, "A field may not be declared both final and static. Use a const field instead."),

	/** 0: name of final field */
	CLF_FIELD_FINAL_MISSING_INIT(ERROR,
			"Final field {0} must be provided with an initializer or must be initialized in the constructor."),

	/** 0: name of final field */
	CLF_FIELD_FINAL_MISSING_INIT_IN_STATIC_POLYFILL(ERROR,
			"Final field {0} must be provided with an initializer or must be initialized in the constructor. HINT: You might want to check the polyfilled constructor."),

	/** 0: name of final field (that has an initializer expression) */
	CLF_FIELD_FINAL_REINIT_IN_CTOR(ERROR,
			"Final field {0} has an initializer and may therefore not be initialized in the constructor."),

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
			"Cannot {0} interface {1} multiple times with different type arguments for {2}: {3}."),

	/**
	 * 0: overridden description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 1:
	 * overriding description ({@link ValidatorMessageHelper/**descriptionDifferentFrom(TMember,TMember)}, 2: additional
	 * error information
	 */
	CLF_IMPLEMENT_MEMBERTYPE_INCOMPATIBLE(ERROR, "Cannot implement {0} with {1}.{2}"),

	/** 0: descriptions of missing members, 1: the consumer */
	CLF_IMPLEMENT_MIXIN_CONFLICTS(ERROR, "The {0} cannot be mixed in {1} due to conflicts."),

	/** 0: name of the current classifier, 1: abstract non overridden descriptions */
	CLF_MISSING_IMPLEMENTATION(ERROR, "Class {0} must either be declared abstract or implement {1}."),

	/** 0: name of the current classifier, 1: abstract non overridden descriptions */
	CLF_MISSING_IMPLEMENTATION_EXT(WARNING,
			"External class {0} must either be declared abstract or explicit declare {1}."),

	/** 0: class, method, getter or setter */
	CLF_ABSTRACT_FINAL(ERROR, "An abstract {0} cannot be declared final."),

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
	CLF_STATIC_ABSTRACT(ERROR, "The {0} {1} may not be both static and abstract."),

	/** no parameters required */
	CLF_ABSTRACT_BODY(ERROR, "Abstract methods do not specify a body."),

	/** 0: method, getter or setter, 1: member name, 2: class name */
	CLF_ABSTRACT_MISSING(ERROR, "The abstract {0} {1} in class {2} can only be defined in an abstract class."),

	/** 0: method, getter or setter, 1: member name */
	CLF_MISSING_BODY(ERROR, "The {0} {1} has to have either a body or must be defined abstract."),

	/** no parameters required */
	CLF_MISSING_CTOR_BODY(ERROR, "Constructors must have a body."),

	/** no parameters required */
	CLF_VOID_ACCESSOR(ERROR, "Void is not a valid type for getters and setters."),

	/** 0: accessor description, 1: verb, 2: missing accessor type */
	CLF_UNMATCHED_ACCESSOR_OVERRIDE(ERROR, "{0} cannot be {1} without overriding the corresponding {2}."),

	/** 0: accessor description, 1: verb, 2: missing accessor type */
	CLF_UNMATCHED_ACCESSOR_OVERRIDE_JS(WARNING, "{0} should not be {1} without overriding the corresponding {2}."),

	/** 0: interface name */
	CLF_MULTIPLE_ROLE_CONSUME(ERROR, "Cannot consume {0} multiple times."),

	/** 0: 'Classes' or 'Roles' */
	CLF_EXT_EXTERNAL_N4JSD(ERROR, "{0} declared as external have to be placed in a file with file extension 'n4jsd'."),

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
	CLF_EXT_CONSUME_NON_EXT(ERROR, "External {0} are not allowed to consume a non external interface."),

	/** 0: classes or interfaces */
	CLF_EXT_IMPLEMENTS_NON_EXT(ERROR, "External {0} are not allowed to implement an interface from a non n4jsd file."),

	/** 0: classes or interfaces */
	CLF_EXT_PUBLIC(ERROR, "External {0} have to be marked as public (and without @Internal)."),

	/** no parameters required */
	CLF_EXT_INTF_PUBLIC(ERROR,
			"Structural typed interfaces in n4jsd files have to be marked as public (and without @Internal)."),

	/** 0: classes or interfaces */
	CLF_EXT_EXTERNAL(ERROR, "External {0} have to be marked as external in n4jsd files."),

	/** 0: methods, getters or setters, 1: classes or interfaces */
	CLF_EXT_NO_METHOD_BODY(ERROR, "{0} in external {1} have to have no body."),

	/** 0: classes or interfaces */
	CLF_EXT_NO_FIELD_EXPR(ERROR, "Fields in external {0} have to have no right side."),

	/** no parameters required */
	CLF_EXT_PUBLIC_CONSTRUCTOR(ERROR, "External classes annotated with @EcmaScript have to have a public constructor."),

	/** 0: classes or interfaces */
	CLF_EXT_NO_OBSERV_ANNO(ERROR, "External {0} must not have the Observable annotation."),

	/** 0: methods, getters or setters, 1: classes or interfaces, 2: Observable or Nfon */
	CLF_EXT_METHOD_NO_ANNO(ERROR, "{0} in external {1} must not have the {2} annotation."),

	/** no parameters required */
	CLF_EXT_LITERAL_NO_VALUE(ERROR, "An enumeration literal in a n4jsd file isn't allowed to define a value."),

	/** no parameters required */
	CLF_EXT_FUN_NO_BODY(ERROR, "External function declarations have to have no body."),

	/** 0 var or constant */
	CLF_EXT_VAR_NO_VAL(ERROR, "External {0} declaration cannot be initialized."),

	/** spec: Component/Manifest/General-Constraint, no parameters required */
	CLF_EXT_NO_MATCH(WARNING,
			"For the given n4jsd file no corresponding external file resp. no matching implemented by expression can be found."),

	/**
	 * doc: validate duplicates between src/src-test and external (so there is e.g. file A.n4js that compiles to A.js
	 * that would be name conflict with A.js in external), spec: Component/Manifest/General-Contraint, 0: file, 1: path
	 */
	CLF_EXT_DUPLICATE_PATH_SRC_EXTERNAL(ERROR, "Duplicate external file {0}, has been already defined in {1}."),

	/** no parameters required */
	CLF_IN_DEFINITION_PRJ_NON_N4JS(WARNING,
			"Class declarations in definition projects should be annotated with @EcmaScript."),

	/** 0: annotation name, 1: type (interface or classifier) */
	CLF_EXT_PROVIDES_IMPL_ONLY_IN_DEFFILES(ERROR, "@{0} must only be used in external {1} in n4jsd files."),

	/** 0: annotation name, 1: type (interface or classifier) */
	CLF_EXT_PROVIDES_IMPL_ONLY_IN_INTERFACE_MEMBERS(ERROR, "@{0} must only be used in {1}."),

	/** 0: annotation name, 1: type (interface or classifier) */
	CLF_EXT_PROVIDES_IMPL_ONLY_IN_N4JS_INTERFACES(ERROR, "@{0} must only be used in n4js interfaces or classes."),

	/** 0: type name, 1: access modifier name */
	CLF_LOW_ACCESSOR_WITH_INTERNAL(ERROR, "A {0} with visibility {1} shouldn't be annotated with @Internal."),

	/** 0: 'extend' or 'implement', 1: type name, 2: description of members causing problems */
	CLF_NON_ACCESSIBLE_ABSTRACT_MEMBERS(ERROR,
			"Cannot {0} {1}: cannot implement one or more non-accessible abstract members: {2}."),

	/** no parameters required */
	CLF_MINIMAL_ACCESSIBILITY_IN_INTERFACES(ERROR, "Members of interfaces must not be declared private."),

	/** no parameters required */
	CLF_SPEC_WRONG_TYPE(ERROR, "Annotation @Spec may only be used with formal parameters of type ~i~this."),

	/** 0: name of field, 1: type of member, 2: type system error message */
	CLF_SPEC_WRONG_ADD_MEMBERTYPE(ERROR,
			"Type of structural member {0} of spec parameter must be a subtype of {1}: {2}."),

	/** no parameters required */
	CLF_SPEC_MULTIPLE(ERROR, "Only a single formal parameter in each constructor may be annotated with @Spec."),

	/** 0: name of the superfluous property, 1: the type not defining the property */
	CLF_SPEC_SUPERFLUOUS_PROPERTIES(WARNING,
			"{0} is not defined in {1}; it will not have any effect in the spec constructor."),

	/**
	 * 0: name of the superfluous property, 1: the type not defining the property, 2: name of variable the object
	 * literal is assigned to
	 */
	CLF_SUPERFLUOUS_PROPERTIES(WARNING, "{0} is not defined in {1}; it will not be accessible from {2}."),

	/** 0: name of the property, {1} built-in/provided by runtime interface */
	CLF_SPEC_BUILT_IN_OR_PROVIDED_BY_RUNTIME_OR_EXTENAL_WITHOUT_N4JS_ANNOTATION(WARNING,
			"{0} is a property of a built-in, provided by runtime, or external module with @EcmaScript annotation. Hence the interface {1} can not be initialized in a spec constructor."),

	/** 0:the cycle */
	CLF_INHERITANCE_CYCLE(ERROR, "Inheritance cycle detected: {0}."),

	/** no param */
	CLF_INTERNAL_BAD_WITH_PRIVATE_OR_PROJECT(ERROR, "@Internal is only allowed for public and protected."),

	/** no param */
	CLF_CANNOT_CALL_ABSTRACT_SUPER_METHOD(ERROR, "Cannot call super method since it is abstract."),

	/** no param */
	CLF_CANNOT_REFER_TO_DEFAULT_METHOD_WITH_SUPER(ERROR,
			"Cannot refer to default method of an implemented interface with super."),

	/** 0: name of polyfill, 1: 'class' or 'interface' including indefinite article */
	CLF_POLYFILL_EXTEND_MISSING(ERROR, "Polyfill {0} must explicitly extend {1}."),

	/** 0: name of polyfill */
	CLF_POLYFILL_NO_TOPLEVEL(ERROR, "Polyfill {0} can only extend top level class declaration."),

	/**
	 * 0: 'class' or 'interface', 1: name of correct classifier kind (without article), 2: name of correct classifier
	 * kind including indefinite article, 3: name of *incorrect* classifier kind including indefinite article
	 */
	CLF_POLYFILL_DIFFERENT_CLASSIFIER_KIND(ERROR, "Polyfill for {0} {1} must be {2}, not {3}."),

	/** 0: name of polyfill, 1: 'class' or 'interface', 2: name of extended class */
	CLF_POLYFILL_DIFFERENT_NAME(ERROR, "Name of polyfill {0} must equal name of filled {1} {2}."),

	/** 0: name of polyfill, 1: name polyfill's module, 2: name of filled class' module */
	CLF_POLYFILL_DIFFERENT_MODULE_SPECIFIER(ERROR,
			"Specifier {1} of module containing polyfill {0} must equal name of filled classes module specifier {2}."),

	/** 0: name of polyfill, 1: global/not global polyfill module, 2: not/empty global polyfill */
	CLF_POLYFILL_DIFFERENT_GLOBALS(ERROR, "Module containing polyfill {0} is {1}, but filled classes module is {2}."),

	/** 0: name of polyfill */
	CLF_POLYFILL_FILLED_NOT_PROVIDEDBYRUNTIME(ERROR, "Polyfill {0} cannot fill class not provided by runtime."),

	/** 0: name of polyfill */
	CLF_POLYFILL_NOT_PROVIDEDBYRUNTIME(ERROR, "Polyfill {0} must be provided by runtime."),

	/** 0: name of polyfill */
	CLF_POLYFILL_NO_IMPLEMENTS(ERROR, "Polyfill {0} must not implement any interfaces."),

	/** 0: name of polyfill */
	CLF_POLYFILL_NO_EXTENDS_ADDITIONAL(ERROR, "Polyfill {0} must not extend any additional interfaces."),

	/** 0: name of polyfill */
	CLF_POLYFILL_NOT_DIRECTLY_EXPORTED(ERROR, "Polyfill {0} must be exported."),

	/** 0: name of polyfill, 1: name of polyfill modifier, 2: name of filled modifier, 3: 'class' or 'interface' */
	CLF_POLYFILL_DIFFERENT_MODIFIER(ERROR,
			"Polyfill {0} cannot be declared {1}, must be defined {2} just as the filled {3}."),

	/** 0: name of polyfill, 1: type system error message */
	CLF_POLYFILL_CTOR_NOT_OVERRIDE_COMPATIBLE(ERROR,
			"Constructor of polyfill {0} must be override compatible with inherited constructor: {1}"),

	/** 0: name of polyfill, 1: 'class' or 'interface' */
	CLF_POLYFILL_DIFFERENT_TYPEPARS(ERROR, "Polyfill {0} must declare same type parameters as the filled {1}."),

	/** 0: name of polyfill */
	CLF_POLYFILL_INCOMPLETE_TYPEARGS(ERROR,
			"Polyfill {0} must pass all type parameters to type arguments (even optional ones)."),

	/** 0: name of polyfill, 1: type par, 2: type arg */
	CLF_POLYFILL_TYPEPARS_DIFFER_TYPEARGS(ERROR,
			"Polyfill {0} must pass type parameters to type arguments in same order and without modifications, but {1} differs from {2}."),

	/** 0: list of modules name with polyfills, 0: member name */
	CLF_POLYFILL_MULTIPOLYFILLS_MEMBER_CONFLICT(ERROR,
			"Polyfills in {0} provide member {1} - only one provider per member is allowed."),

	/** 0: name of polyfill */
	CLF_POLYFILL_STATIC_FILLED_TYPE_NOT_AWARE(ERROR,
			"For static polyfills, the module of the filled type {0} must be annotated with @StaticPolyfillAware."),

	/** 0: name of polyfill, 1: file extension (.n4js or .n4jsd) */
	CLF_POLYFILL_STATIC_DIFFERENT_VARIANT(ERROR,
			"Since static polyfill {0} is declared in an {1} file, the filled type must also be declared in an {1} file."),

	/** 0: string that is used as name for more than one literal */
	ENM_DUPLICTAE_LITERALS(ERROR, "Multiple literals with name {0}."),

	/** no parameters required */
	ENM_INVALID_VALUE_EXPRESSION(ERROR,
			"Only string literals and number literals are allowed as value of an enum literal."),

	/** 0: string with the name of the meta property */
	ENM_LITERALS_HIDE_META(ERROR, "EnumLiteral cannot have the same name as Enum meta property <{0}>."),

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
			"Cannot initialize field in interface. Only classes or interfaces implementing {1} can initialize {0}."),

	/** 0: property kind name, 1: structural /nominal /<blank> */
	ITF_NO_PROPERTY_BODY(ERROR, "{0} in {1}interfaces must not have a body."),

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
	ALI_CYCLIC_TYPE_ALIAS(ERROR, "Cyclic type alias declaration: {0}."),

	/** no parameters required */
	ALI_INVALID_MODIFIER(ERROR, "A type alias may be referenced {0} only if its aliased type may be referenced {0}."),

	/** 0: the string '{}' */
	ALI_INVALID_TYPE_ALIAS_IN_TYPE_TYPE_REF(ERROR,
			"A type alias may be used inside type{0} or constructor{0} only if its aliased type may be used at this location."),

	/** 0: passed parameter count, 1: expected parameter count */
	FUN_PARAM_COUNT(ERROR, "Passed parameter count {0} doesn't match with expected parameter count {1}."),

	/** no parameters required */
	FUN_BLOCK(WARNING,
			"Functions declarations should not be placed in blocks. Use a function expression or move the statement to the top of the outer function."),

	/** no parameters required */
	FUN_BODY(ERROR, "Functions have to have a body."),

	/** 0: element description, 1: conflicting category */
	FUN_NAME_RESERVED(ERROR, "{0} may be confused with {1}."),

	/** no parameters */
	FUN_NAME_MISSING(ERROR, "Function declarations must have a name."),

	/** 0: formal parameter */
	FUN_PARAM_OPTIONAL_WRONG_SYNTAX(ERROR, "Wrong syntax: Use {0}=undefined instead of ?."),

	/** no parameters */
	FUN_PARAM_INITIALIZER_ILLEGAL_FORWARD_REFERENCE(ERROR,
			"Illegal forward reference to formal parameter of same function."),

	/** no parameters */
	FUN_PARAM_INITIALIZER_ONLY_UNDEFINED_ALLOWED(ERROR,
			"Only 'undefined' allowed for initializers of default parameters in function types."),

	/** 0: formal parameter */
	/** 1: identifier */
	FUN_PARAM_INITIALIZER_ILLEGAL_REFERENCE_TO_BODY_VARIABLE(ERROR,
			"Initializer of parameter '{0}' cannot reference the identifier '{1}' declared in the body."),

	/** 0: formal parameter */
	FUN_PARAM_INITIALIZER_ILLEGAL_AWAIT_CALL(ERROR,
			"Illegal await-expression in initializer of formal parameter '{0}'."),

	/**
	 * 0: name of actually used generator type, 1: expected generator kind (synchronous or asynchronous), 2: name of
	 * expected generator type
	 */
	FUN_GENERATOR_RETURN_TYPE_MISMATCH(WARNING,
			"Type {0} is intended for {1} generator functions; consider using type {2} instead."),

	/** no parameters required */
	AST_SEPARATE_DEFAULT_EXPORT_WITHOUT_FROM(ERROR, "Missing from-clause in default re-export."),

	/** 0: element, 1: kind of value */
	AST_ELEMENT_MISUSED_AS_VALUE_OR_TYPE(ERROR, "{0} cannot be used as a {1}."),

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
	AST_CONST_HAS_NO_INITIALIZER(ERROR, "Const variable {0} must be provided with an initializer."),

	/** no parameters required */
	AST_STR_NO_OCTALS(ERROR, "octal literals and octal escape sequences are not allowed in strict mode."),

	/** 0: operand (decrement or increment) */
	AST_INVALID_OPERAND(ERROR, "Invalid {0} operand."),

	/** no parameters required */
	AST_EXP_INVALID_LHS_ASS(ERROR, "Invalid assignment left-hand side."),

	/** 0: identifier */
	AST_RESERVED_IDENTIFIER(ERROR, "{0} is a reserved identifier."),

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
			"Reference to variable {0} within the initializer expression of the declaration of {0}."),

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
	AST_TOP_LEVEL_STATEMENTS(ERROR, "Top-level statements are not supported in {0} files."),

	/** No parameters */
	AST_VAR_STMT_NO_DECL(ERROR, "A variable statement must at least contain one variable declaration."),

	/** 0: script annotation name */
	AST_SCRIPT_ANNO_INVALID_PLACEMENT(ERROR, "The script annotation @@{0} must be placed at the top of the module."),

	/** 0: Forbidden Type e.g. null / undefined */
	AST_BINARY_LOGICAL_EXPRESSION_MISSING_PART(ERROR, "The logical expression is missing the {0}."),

	/** no parameters required */
	AST_IMPORT_CALL_SPREAD(ERROR, "The spread operator is not allowed in import calls."),

	/** 0: Invalid parent e.g. && / || */
	AST_INVALID_COALESCE_PARENT(ERROR, "Nullish coalescing expressions cannot be contained within an {0} operation. "),

	/** 0: Invalid child e.g. && / || */
	AST_INVALID_COALESCE_CHILD(ERROR, "Nullish coalescing expressions cannot immediately contain an {0} operation."),

	/** 0: local variable name */
	CFG_LOCAL_VAR_UNUSED(WARNING, "The local variable {0} is never used"),

	/** 0: local variable name */
	CFG_USED_BEFORE_DECLARED(WARNING, "Variable {0} is used before it is declared"),

	/** 0: local variable name */
	/** 1: 'is' or 'may be' */
	/** 2: 'null' or 'undefined' */
	/** 3: reason (optional) */
	DFG_NULL_DEREFERENCE(WARNING, "Variable {0} {1} {2}{3}"),

	/** 0: shadowing object, 1: shadowed object */
	AST_NAME_SHADOW_WARN(WARNING, "{0} shadows {1}."),

	/** 0: shadowing object, 1: shadowed object */
	AST_NAME_SHADOW_ERR(ERROR, "{0} shadows {1}."),

	/** 0: hiding parameter 1: hidden parameter */
	AST_GLOBAL_NAME_SHADOW_ERR(ERROR, "{0} shadows {1} from global scope."),

	/** 0: name */
	AST_GLOBAL_JS_NAME_CONFLICT(ERROR, "Globally defined element named {0} must be defined in runtime environment."),

	/** 0: name */
	AST_GLOBAL_NAME_CONFLICT(ERROR, "Globally defined element must not be named {0}."),

	/** 0: duplicating object, 1: duplicated object */
	AST_NAME_DUPLICATE_ERR(ERROR, "{0} duplicates {1}."),

	/** 0: duplicating object, 1: duplicated object */
	AST_NAME_DUPLICATE_WARN(WARNING, "{0} duplicates {1}."),

	/** 0: grammar rule, 1: value */
	VCO_DOUBLE_NEGATIVE(ERROR, "{0}-value may not be negative (value: {1})."),

	/** no parameters required */
	VCO_DOUBLE_CONVERT_EMPTY_STR(ERROR, "Couldn't convert empty string to a double value."),

	/** 0: the string value */
	VCO_DOUBLE_CONVERT_STR(ERROR, "Couldn't convert {0} to a double value."),

	/** 0: grammar rule, 1: value */
	VCO_HEXINT_NEGATIVE(ERROR, "{0}-value may not be negative (value: {1})."),

	/** no parameters required */
	VCO_HEXINT_CONVERT_EMPTY_STR(ERROR, "Couldn't convert empty string to an hex int value."),

	/** 0: the string value */
	VCO_HEXINT_CONVERT_TOO_SHORT(ERROR, "Couldn't convert {0} to an int value."),

	/** 0: the string value */
	VCO_HEXINT_CONVERT_STR(ERROR, "Couldn't convert {0} to an hex int value."),

	/** 0: grammar rule, 1: value */
	VCO_BINARYINT_NEGATIVE(ERROR, "{0}-value may not be negative (value: {1})."),

	/** no parameters required */
	VCO_BINARYINT_CONVERT_EMPTY_STR(ERROR, "Couldn't convert empty string to a binary int value."),

	/** 0: the string value */
	VCO_BINARYINT_CONVERT_TOO_SHORT(ERROR, "Couldn't convert {0} to an int value."),

	/** 0: the string value */
	VCO_BINARYINT_CONVERT_STR(ERROR, "Couldn't convert {0} to a binary int value."),

	/** 0: grammar rule, 1: value */
	VCO_OCTALINT_NEGATIVE(ERROR, "{0}-value may not be negative (value: {1})."),

	/** no parameters required */
	VCO_OCTALINT_CONVERT_EMPTY_STR(ERROR, "Couldn't convert empty string to an octal int value."),

	/** 0: the string value */
	VCO_OCTALINT_CONVERT_TOO_SHORT(ERROR, "Couldn't convert {0} to an int value."),

	/** 0: the string value */
	VCO_OCTALINT_LEADING_ZEROS(ERROR, "Don't use extra leading zeros {0}."),

	/** 0: the string value */
	VCO_OCTALINT_CONVERT_STR(ERROR, "Couldn't convert {0} to an octal int value."),

	/** 0: grammar rule, 1: value */
	VCO_SCIINT_NEGATIVE(ERROR, "{0}-value may not be negative (value: {1})."),

	/** no parameters required */
	VCO_SCIINT_CONVERT_EMPTY_STR(ERROR, "Couldn't convert empty string to an scientific int value."),

	/** 0: the string value */
	VCO_SCIINT_CONVERT_STR(ERROR, "Couldn't convert {0} to an scientific int value."),

	/** 0: identifier, 1: position */
	VCO_IDENT_ESCAPE_SEQ(ERROR, "Illegal escape sequence in identifier {0} at position {1}."),

	/** 0: result, 1: identifier, 2: position */
	VCO_IDENT_ILLEGAL_CHAR_WITH_RESULT(ERROR, "Illegal character in identifier '{0}' ({1}) at position {2}."),

	/** 0: identifier, 1: position */
	VCO_IDENT_ILLEGAL_CHAR(ERROR, "Illegal character in identifier '{0}' at position {1}."),

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
	VCO_REGEX_ILLEGAL_ESCAPE(ERROR, "Illegal escape sequence in regular expression {0}"),

	/** no parameters required */
	VCO_REGEX_NAMED_GROUP(WARNING, "Named capture groups are not supported on all platforms."),

	/** no parameters required */
	VCO_NPE(ERROR,
			"A NullPointerException occurred. This indicates a missing value converter or a bug in its implementation."),

	/** 0: feature name */
	VCO_NULL_FEATURE(ERROR, "ValueConverter returned null for primitive feature {0}"),

	/** no parameters required */
	BIT_SYMBOL_INVALID_USE(ERROR,
			"Invalid use of 'Symbol': may only be used to create symbols (i.e. Symbol()) or to access built-in symbols (e.g. Symbol.iterator)."),

	/** no parameters required */
	BIT_SYMBOL_NOT_A_CTOR(ERROR, "Symbol is not a constructor, use Symbol() without new."),

	/** no parameters required */
	TYS_MISSING(ERROR, "Types model hasn't been built for this file. Please report this bug to a N4JS developer."),

	/** 0: expression */
	TYS_CANNOT_TYPE(ERROR, "cannot type {0}."),

	/** 0: left expression, 1: right expression */
	TYS_NO_SUBTYPE(ERROR, "{0} is not a subtype of {1}"),

	/** 0: expected type, 1: actual type */
	TYS_NO_SUPERTYPE_WRITE_ACCESS(ERROR, "expecting write-access for type {0} but {1} is not a super type of {0}."),

	/** no parameters required */
	TYS_NULL_OBJECT(ERROR, "passed null object to system at {0}."),

	/** no parameters required */
	TYS_VOID_AT_WRONG_LOCATION(ERROR,
			"Type 'void' may only be used to declare the return type of functions and methods."),

	/** 0: member type, 1: member name, 2: type name */
	TYS_MEMBER_NOT_IN_STRUCTURAL_TYPE_DEF_SITE(ERROR, "{0} {1} is not available for structurally defined type {2}."),

	/** 0: member type, 1: member name, 2: type name */
	TYS_MEMBER_NOT_IN_STRUCTURAL_TYPE_USE_SITE(ERROR, "{0} {1} is not available for structurally referenced type {2}."),

	/** 0: member type, 1: member name, 2: type name */
	TYS_MEMBER_NOT_IN_STRUCTURAL_FIELDS_TYPE_USE_SITE(ERROR,
			"{0} {1} is not available for fields-only-referenced type {2}."),

	/** no parameters required */
	TYS_INSTANCEOF_NOT_SUPPORTED_FOR_USE_SITE_STRUCTURAL(ERROR,
			"'instanceof' cannot be used with use site structural typing."),

	/** 0: type name */
	TYS_INSTANCEOF_NOT_SUPPORTED_FOR_BUILT_IN_INTERFACES(ERROR,
			"'instanceof' cannot be used with built-in interface {0}."),

	/** no parameters required */
	TYS_INSTANCEOF_NOT_SUPPORTED_FOR_PRIMITIVE_TYPES(ERROR, "'instanceof' cannot be used with primitive types."),

	/** 0: property name */
	TYS_PROPERTY_HAS_NO_SETTER(ERROR, "Property {0} has no setter."),

	/** 0: type name */
	TYS_PRIMITIVE_TYPE_DYNAMIC(ERROR, "Primitive type {0} must not be referenced dynamically."),

	/** no parameters required */
	TYS_COMPOUND_MISSING_GETTER(ERROR, "Missing getter on left-hand side of compound assignment."),

	/** no parameters required */
	TYS_NON_VOID_ASYNC(ERROR,
			"Internal error: Only Promise allowed as inferred return type of an async FunctionDefinition"),

	/** no parameters required */
	TYS_NON_THIS_ASYNC(ERROR, "The return type of an async function is not allowed to refer to the this-type."),

	/** 0: type name */
	TYS_FOR_IN_VAR_STRING(ERROR, "Type of for-in-loop variable must be a super type of string but {0} is not."),

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
	EXP_CALL_NOT_A_FUNCTION(ERROR, "Not a function or method: {0}."),

	/** 0: comma-separated list of conflicting callable type references */
	EXP_CALL_CONFLICT_IN_INTERSECTION(ERROR, "More than one callable type in intersection: {0}."),

	/** no parameters required */
	EXP_CALL_CLASS_CTOR(ERROR, "Cannot directly invoke class constructor functions; use 'new' instead."),

	/** 0: actual type */
	EXP_NEW_NOT_A_CTOR(ERROR, "Not a reference to a constructor: {0}."),

	/** 0: intersection type with conflicting ctors or construct signatures */
	EXP_NEW_CONFLICT_IN_INTERSECTION(ERROR, "More than one constructor or construct signature in intersection: {0}."),

	/** 0: type arg name of constructor, 1: static type */
	EXP_NEW_WILDCARD_NO_COVARIANT_CTOR(ERROR,
			"Cannot instantiate {0}, because {1} does not have a @CovariantConstructor."),

	/** 0: type arg name of constructor */
	EXP_NEW_WILDCARD_OR_TYPEVAR(ERROR, "Cannot instantiate {0}."),

	/** 0: abstract class, interface, 1: classifier name */
	EXP_NEW_CANNOT_INSTANTIATE(ERROR, "Cannot instantiate {0} {1}."),

	/** 0: number of expected type args, 1: number of actual type args */
	EXP_WRONG_NUMBER_OF_TYPEARGS(ERROR, "Incorrect number of type arguments: expected {0}, got {1}."),

	/**
	 * 0: class, interface, method, function 1: name of classifier/method/function, 2: number of expected type args, 3:
	 * number of actual type args
	 */
	EXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ELEMENT(ERROR,
			"Incorrect number of type arguments for {0} {1}: expected {2}, got {3}."),

	/** 0: maximum number of element types in an ArrayN */
	EXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ITERABLE_N_SYNTAX(ERROR,
			"The ArrayN types are available for a maximum of {0} element types only (i.e. type Array{0})."),

	/** 0: upper or lower, 1: covariant or contravariant */
	EXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG(ERROR,
			"Cannot use wildcard with {0} bound as argument to a {1} type parameter."),

	/** 0: covariant or contravariant, 1: covariant or contravariant */
	EXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG_IN_OUT(ERROR, "Cannot combine {0} on use site with {1} on definition site."),

	/** 0: expected num of params, 1: actual num of params */
	EXP_NUM_OF_ARGS_TOO_FEW(ERROR, "Incorrect number of arguments: expected {0}, got {1}."),

	/** 0: expected num of params, 1: actual num of params */
	EXP_NUM_OF_ARGS_TOO_MANY(ERROR, "Incorrect number of arguments: expected {0}, got {1}."),

	/** 0: from type ref, 1: target type ref */
	EXP_CAST_FAILED(ERROR, "Cannot cast from {0} to {1}"),

	/** 0: from type ref, 1: target type ref */
	EXP_CAST_UNNECESSARY(WARNING, "Unnecessary cast from {0} to {1}"),

	/** no parameter required */
	EXP_CAST_INVALID_TARGET(ERROR,
			"Can only cast to class, interface, enum, function, primitive, union or intersection types"),

	/** 0: recognized type, 2: result, e.g. NaN */
	EXP_MATH_OPERATION_RESULT_IS_CONSTANT(WARNING, "Operand {0} will always result in {1}."),

	/** 0: recognized type, 1: constant value */
	EXP_MATH_OPERAND_IS_CONSTANT(WARNING, "Operand of type {0} will be interpreted as {1}."),

	/** 0: recognized type, 2: result, e.g. NaN */
	EXP_MATH_TYPE_NOT_PERMITTED(ERROR, "Operand of type {0} cannot be converted to number."),

	/** 0: left type 1: rght type 2: result */
	EXP_WARN_CONSTANT_EQUALITY_TEST(WARNING,
			"Neither {0} is a subtype of {1} nor {1} is a subtype of {0}. The expression will always evaluate to {2}."),

	/** 0: Forbidden Type e.g. null / undefined */
	EXP_FORBIDDEN_TYPE_IN_BINARY_LOGICAL_EXPRESSION(ERROR,
			"{0} is not allowed on the left hand side of a logical expression."),

	/** 0: member description, 1: type of target, 2: declared this type (from @This annotation) */
	EXP_ACCESS_INVALID_TYPE_OF_TARGET(ERROR,
			"Target of property access not a subtype of the declared @This type of {0}: {1} is not a subtype of {2}."),

	/** 0: method name */
	EXP_METHOD_REF_UNATTACHED_FROM_RECEIVER(ERROR,
			"A reference to method {0} is created detached from a (correct) this-instance."),

	/** 0: await expression, 1 async annotation */
	EXP_MISPLACED_AWAIT(ERROR, "{0} is allowed only inside functions annotated with {1}."),

	/** no parameter required */
	EXP_MISSNG_AWAIT_FOR_ASYNC_TARGET(WARNING,
			"Calling async function without await, Promise should be made explicit."),

	/** 0: Suspicious expression, 1: boolean value, 2:left-hand/right-hand */
	EXP_WARN_DISPENSABLE_CONDITIONAL_EXPRESSION(WARNING,
			"Dispensable use of conditional expression. The expression '{0}' always evaluates to {1}, so only the {2} side will ever be evaluated."),

	/** no parameter required */
	EXP_AWAIT_NON_ASYNC(WARNING,
			"await should only be used on expressions of type Promise<?,?> since otherwise it has no effect."),

	/** 0: recognized expression */
	EXP_AWAIT_NON_ASYNC_SPECIAL(WARNING, "await should not be used on '{0}' since it has no effect here."),

	/** no parameter required */
	FUN_RETURNTYPE_VOID_FOR_SETTER_VIOLATED(ERROR, "Set accessors must not return anything."),

	/** 0: name of return type */
	FUN_MISSING_RETURN_EXPRESSION(ERROR, "Return statement must have an expression of type {0}"),

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
	FUN_DEAD_CODE_WITH_PREDECESSOR(WARNING, "Dead code. No execution possible after {0}."),

	/** no parameters required */
	FUN_SETTER_CANT_BE_VARIADIC(ERROR, "Variadic parameter is not allowed in setter declarations."),

	/** no parameters required */
	FUN_SETTER_CANT_BE_DEFAULT(ERROR, "Default parameter is not allowed in setter declarations."),

	/** no parameters required */
	FUN_PARAM_VARIADIC_ONLY_LAST(ERROR, "Only the last formal parameter can be variadic."),

	/** 0: name of parameter */
	FUN_PARAM_IMPLICIT_DEFAULT_PARAM(WARNING,
			"This parameter is changed to the default parameter '{0}=undefined' since it follows a default parameter."),

	/** no parameter required */
	FUN_PARAM_VARIADIC_WITH_INITIALIZER(ERROR, "Variadic parameters must not have a default initializer."),

	/** no parameter required */
	FUN_SINGLE_EXP_LAMBDA_IMPLICIT_RETURN_ALLOWED_UNLESS_VOID(ERROR,
			"An arrow-function is used in a context where its body is expected to have some value as opposed to being void."),

	/** 0: type parameter name */
	FUN_UNUSED_GENERIC_TYPE_PARAM(WARNING, "Type variable {0} not used in parameters or return type."),

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
			"Super constructor call must only be directly contained in constructor body, i.e. not nested in {0}."),

	/** 0: container in which super is nested (with line number). */
	KEY_SUP_CTOR_INVALID_EXPR_BEFORE(ERROR, "Super constructor call must not be preceded by {0}."),

	/** 0: super class name */
	KEY_SUP_REQUIRE_EXPLICIT_SUPERCTOR_CALL(ERROR, "Must explicitly invoke constructor of super class {0}."),

	/** no parameters required */
	KEY_SUP_CALL_NO_INDEXACCESS(ERROR, "Super member access may not be used with index access."),

	/** no parameters required */
	KEY_THIS_REJECTED_IN_TOP_LEVEL_LAMBDA(ERROR,
			"In a top-level arrow function, the 'this' keyword refers to nothing."),

	/** no parameters required */
	EXP_OPTIONAL_INVALID_PLACE(ERROR, "The optional modifier isn't allowed here."),

	/** 0: name of const variable */
	EXP_ASSIGN_CONST_VARIABLE(ERROR, "Const variable {0} is read-only."),

	/** 0: names of legal built-in symbol */
	EXP_INDEXED_ACCESS_SYMBOL_INVALID(ERROR, "Indexed access with built-in symbols is only allowed for {0}."),

	/** 0: name of built-in symbol, 1: name of expected receiver type */
	EXP_INDEXED_ACCESS_SYMBOL_WRONG_TYPE(ERROR,
			"Access of property Symbol.{0} only allowed for instances of {1}, immediate instances of Object, and dynamic types."),

	/** 0: name of built-in symbol, 1: name of receiver type */
	EXP_INDEXED_ACCESS_SYMBOL_READONLY(ERROR, "Access to property Symbol.{0} of an {1} is read-only."),

	/** no parameters required */
	EXP_INDEXED_ACCESS_ENUM(ERROR, "Indexed access is not allowed for enumerations."),

	/** no parameters required */
	EXP_INDEXED_ACCESS_FORBIDDEN(ERROR,
			"Indexed access is only allowed for strings, arrays and iterables and for immediate(!) instances of Object."),

	/** 0: index-key in indexed-access */
	EXP_INDEXED_ACCESS_COMPUTED_NOTFOUND(ERROR, "Member {0} not found."),

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
	EXP_COMPILE_TIME_MANDATORY(ERROR, "Not a compile-time expression: {0}."),

	/**
	 * 0: usage type ('invoke' or 'instantiate'), 1: name of interface with call/construct signature, 2: signature kind
	 * ('call' or 'construct')
	 */
	EXP_CALL_CONSTRUCT_SIG_OF_INTERFACE_DIRECTLY_USED(ERROR,
			"Cannot directly {0} interface {1}; its {2} signature applies to values of type {1}, not to {1} itself."),

	/** 0: type names, submessage -- full message is a concatenation of these messages */
	COMP_SUBMESSAGES(ERROR, "{1} in {0}."),

	/** 0: member name */
	UNI_UNCOMMON(ERROR, "Member {0} not present in all types of union or incompatible."),

	/** 0: member name, 1: list of types in union that do not contain a member of that name */
	UNI_MISSING(ERROR, "Member {0} not present in all types of union; missing from: {1}."),

	/** 0: either 'getters' or 'setters', 1: member name, 2: either 'read-only' or 'write-only' */
	UNI_INVALID_COMBINATION(ERROR, "Union combines fields and {0} with name {1} and therefore property {1} is {2}."),

	/** 0: member name */
	UNI_INVALID_COMBINATION_SETTER_VS_READ_ONLY_FIELD(ERROR,
			"Union combines fields and setters with name {0} but still write-access is not allowed because one or more fields are read-only (const or @Final)."),

	/** 0: member name, 1: list of kinds */
	UNI_MULTIPLE_KINDS(ERROR, "Member {0} not of same kind in all types of union: {1}."),

	/** 0: member name, 1: list of types */
	UNI_DIFFERENT_TYPES(ERROR, "Member {0} not of same type in all types of union: {1}."),

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
			"Member method {0} is in conflict with non-method members in types of intersection: {1}."),

	/** 0: member name */
	INTER_UNCOMMON(ERROR, "Member {0} is incompatible in types of intersection."),

	/** no parameters required */
	ANN__N4JS_NO_EFFECT(WARNING, "This annotation is deprecated and has no effect."),

	/** no parameters required */
	ANN__ONLY_IN_N4JS(ERROR, "Annotations are a N4JS feature and cannot be used in JS mode."),

	/** 0: annotation name */
	ANN_NOT_DEFINED(ERROR, "The annotation @{0} is not defined."),

	/** 0: annotation name, 1: expected number of arguments, 2: actual number of arguments */
	ANN_WRONG_NUMBER_OF_ARGUMENTS(ERROR, "Wrong number of annotation arguments: @{0} expects {1} but got {2}."),

	/** 0: annotation name, 1: expected type */
	ANN_WRONG_ARGUMENT_TYPE(ERROR, "The annotation @{0} expects a {1} here."),

	/** 0: annotation name */
	ANN_DISALLOWED_AT_LOCATION(ERROR, "The annotation @{0} is disallowed for this location."),

	/** 0: annotation name, 1: list of language variants */
	ANN_ONLY_ALLOWED_LOCATION_CONSTRUCTORS(ERROR,
			"The annotation @{0} may only be applied at a parameter of a constructor."),

	/** 0: name of annotation (without @) */
	ANN_NON_REPEATABLE(ERROR, "Duplicate annotation of non-repeatable type @{0}."),

	/** 0: name of annotation */
	ANN_UNNECESSARY(WARNING, "Unnecessary @{0}."),

	/** no parameters required */
	ANN_THIS_DISALLOWED_ON_STATIC_MEMBER_OF_INTERFACE(ERROR,
			"@This annotation not allowed on static members of interfaces."),

	/** 0: member description, 1: description of containing type, 2: required type */
	ANN_THIS_NOT_SUBTYPE_OF_CONTAINING_TYPE(ERROR, "Declared @This type of {0} in {1} must be a subtype of {2}."),

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
	ANN_DISALLOWED_IN_NONDEFINTION_FILE(ERROR, "The annotation @{0} can only be applied in definition files."),

	/** 0: annotation name */
	ANN_DISALLOWED_IN_NON_RUNTIME_COMPONENT(ERROR, "The annotation @{0} can only be applied in a runtime component."),

	/** 0: bug id */
	ANN_UNUSED_IDEBUG(ERROR,
			"No matching error found, apparently bug IDEBUG-{0} has been fixed or does not occur here."),

	/** 0: name of the violating annotation. */
	ANN_REQUIRES_TEST(ERROR, "Only methods annotated with @Test could be annotated with {0}."),

	/** 0: annotation name, 1: list of language variants */
	ANN_ONL_ALLOWED_IN_VARIANTS(ERROR, "The annotation @{0} may only be applied in {1} files."),

	/** 0: annotation name, 1: list of language variants */
	ANN_ONL_ALLOWED_AT_CLASSES_IN_N4JSD(ERROR, "The annotation @{0} may only be applied at classes in n4jsd files."),

	/** 0: annotation name, 1: list of language variants */
	ANN_DISALLOWED_ON_SHAPES(ERROR, "Only nominal interfaces can be annotated with @{0}."),

	/** 0: annotation name */
	ANN__TEST_ONLY_IN_TEST_SOURCES(WARNING,
			"Test annotation @{0} may only be used in test source folders (defined in package.json)."),

	/** 0: library name, 1: String of concatenated polyfilled things. */
	POLY_ERROR_IN_RUNTIMEDEPENDENCY(ERROR, "Erroneous library {0} provides contradicting polyfills for {1}."),

	/** 0: String of concatenated library names, 1: String of concatenated polyfilled things. */
	POLY_CLASH_IN_RUNTIMEDEPENDENCY(ERROR,
			"The libraries {0} provide polyfills for the same element {1} and cannot be used together."),

	/** 0: String of concatenated library names, 1: String of concatenated polyfilled things. */
	POLY_CLASH_IN_RUNTIMEDEPENDENCY_MULTI(ERROR,
			"The libraries {0} provide polyfills for the same elements {1} and cannot be used together."),

	/** 0: String of conflicting static-polyfilling modules. */
	POLY_CLASH_IN_STATIC_POLYFILL_MODULE(ERROR,
			"Only one module annotated with @@StaticPolyfillModule is allowed per project. Conflicting with {0}."),

	/** no param */
	POLY_STATIC_POLYFILL_MODULE_ONLY_FILLING_CLASSES(ERROR,
			"Only top-level classes annotated as StaticPolyfill are allowed in a module annotated with StaticPolyfillModule."),

	/** no param */
	POLY_IMPLEMENTING_INTERFACE_NOT_ALLOWED(ERROR,
			"The filling class cannot introduce additional interfaces; all interfaces must be declared on the filled class."),

	/** doc: detected dependency cycle 0:String describing the cycle. */
	PROJECT_DEPENDENCY_CYCLE(ERROR, "Dependency cycle of the projects: {0}."),

	/** 0: ID of the non-existing project. */
	NON_EXISTING_PROJECT(ERROR, "Project does not exist with project ID: {0}."),

	/** 0: ID of the non-existing project. */
	MISSING_YARN_WORKSPACE(ERROR,
			"Project depends on workspace project {0} which is missing in the node_modules folder. Either install project {0} or introduce a yarn workspace of both of the projects."),

	/** 0: human readable name of the obsolete feature block. */
	OBSOLETE_BLOCK(WARNING, "Obsolete {0} block."),

	/** 0: project project ID, 1: human readable name of the project type, 2: human readable name of the feature. */
	INVALID_PROJECT_TYPE_REF(WARNING, "Project {0} of type {1} cannot be declared among the {2}."),

	/** 0: library project ID */
	INVALID_API_PROJECT_DEPENDENCY(WARNING,
			"Library project {0} with an implementation ID cannot be declared among the dependencies of an API project."),

	/** 0: violating feature/block name, 1: actual project type name */
	INVALID_FEATURE_FOR_PROJECT_TYPE(WARNING, "{0} cannot be specified for {1} projects."),

	/** 0: name used in duplicate */
	DUPLICATE_PROJECT_REF(ERROR, "Duplicate project reference {0}."),

	/** 0: name of the containing folder, 1: name of the nested folder */
	OUTPUT_AND_SOURCES_FOLDER_NESTING(ERROR, "{0} must not be located inside {1}."),

	/** no parameters required */
	PROJECT_REFERENCES_ITSELF(ERROR, "Project cannot reference itself."),

	/** 0: the type of the deprecated project 1: alternatives */
	DEPRECATED_PROJECT_TYPE(WARNING, "Project type '{0}' is deprecated and will be removed soon. {1}"),

	/** no parameters are required. */
	MISMATCHING_TESTED_PROJECT_TYPES(WARNING, "Tested projects should have the same project type."),

	/**
	 * 0: current project implementation ID, 1: the violating project's ID, 2: the implementation ID of the violating
	 * project
	 */
	MISMATCHING_IMPLEMENTATION_ID(WARNING,
			"Implementation ID mismatch. Current project belongs to '{0}' implementation while {1} project belongs to '{2}' implementation."),

	/** 0: test library name */
	SRCTEST_NO_TESTLIB_DEP(ERROR, "Project with source folder of type test should depend on {0}."),

	/**
	 * 0: ID of the violating transitive external project dependency, 1: ID of the workspace project that is required by
	 * an external one.
	 */
	EXTERNAL_PROJECT_REFERENCES_WORKSPACE_PROJECT(WARNING,
			"Transitive external project dependency of project '{0}' requires a workspace project '{1}'. Current project setup could result in runtime oddities. It is highly recommended to import project '{0}' into workspace as well."),

	/** 0: project type of containing project */
	INVALID_FILE_TYPE_FOR_PROJECT_TYPE(ERROR, "An n4js file may not be contained in a project of type '{0}'."),

	/** 0: resource name */
	NO_PROJECT_FOUND(WARNING, "No project found for resource {0}."),

	/** 0: part of the specifier that contains a dot, 1: the module specifier */
	MOD_NAME_MUST_NOT_CONTAIN_DOTS(ERROR, "{0} of this module contain(s) the disallowed character '.' : '{1}'."),

	/** 0: dependency name, 1: required version, 2: present version */
	NO_MATCHING_VERSION(WARNING, "Project {0} is required in version {1}, but only version {2} is present."),

	/**
	 * doc: checks if the module specifier wildcard can be resolved to an existing resource (not applied for
	 * implementation provided by runtime), spec: Component/Manifest/ModuleSpecifier-Constraint, 0: the module specifier
	 */
	NON_EXISTING_MODULE_SPECIFIER(WARNING, "Module specifier {0} doesn't exist."),

	/**
	 * doc: checks for invalid wildcards, spec: Component/Manifest/ModuleSpecifierWildcardConstraints, 0: the invalid
	 * part of the wild card
	 */
	INVALID_WILDCARD(ERROR, "'{0}' isn't a valid character sequence in a wild card."),

	/**
	 * doc: disallow relative navigation, spec: Component/Manifest/ModuleSpecifierWildcardConstraints, no parameters
	 * required
	 */
	NO_RELATIVE_NAVIGATION(ERROR, "Relative navigation isn't allowed in a module specifier."),

	/**
	 * doc: disallow switching off semantic validation for n4js files, spec:
	 * Component/Manifest/ModuleSpecifier-Constraint, 0: module filter type (e.g. noValidate)
	 */
	DISALLOWED_NO_VALIDATE_FOR_N4JS(ERROR, "{0} paths shouldn't match n4js files."),

	/** 0: sub description, 1: extend/implement, 2: super description, 3: implements/extends */
	SYN_KW_EXTENDS_IMPLEMENTS_MIXED_UP(ERROR, "The {0} cannot {1} {2}, use '{3}'."),

	/** no parameters required */
	SYN_KW_EXTENDS_IMPLEMENTS_WRONG_ORDER(ERROR, "Extended class must be declared before implemented interfaces."),

	/** 0: class description, 1: keyword, 2: super description */
	CLF_WRONG_META_TYPE(ERROR, "The {0} cannot {1} {2}."),

	/** 0: modifier name, 1: type of element that may not have this modifier (e.g. 'class', 'interface', etc.) */
	SYN_MODIFIER_INVALID(ERROR, "Modifier {0} is not allowed on a {1}."),

	/** 0: modifier name */
	SYN_MODIFIER_DUPLICATE(ERROR, "Duplicate modifier {0}."),

	/** no parameters required */
	SYN_MODIFIER_ACCESS_SEVERAL(ERROR, "Only a single access modifier may be provided."),

	/** 0: modifiers in correct order */
	SYN_MODIFIER_BAD_ORDER(ERROR, "Modifiers should appear in this order: {0}."),

	/** 0: element {modifier|keyword}, 1: name of modifier or keyword element (e.g. public) */
	SYN_UNNECESSARY_ELEMENT(WARNING, "Unnecessary {0} {1}."),

	/** no parameters required */
	EXP_ONLY_TOP_LEVEL_ELEMENTS(ERROR, "Only top level elements can be exported."),

	/** 0: element kind, 1: element name */
	EXP_PRIVATE_ELEMENT(ERROR, "Private {0} {1} cannot be exported."),

	/** no parameters required */
	DESTRUCT_EMPTY_PATTERN(ERROR,
			"Empty destructuring pattern (disallowed for time being, since ES6 implementations have different semantics)."),

	/** 0: name of missing property, 1: type of value to be destructured */
	DESTRUCT_PROP_MISSING(ERROR,
			"Value to be destructured does not contain a property, field or getter named '{0}': {1}."),

	/** 0: name of property that returned AbstractDescriptionWithError, 1: error message */
	DESTRUCT_PROP_WITH_ERROR(ERROR, "Property named '{0}' is not readable: {1}."),

	/**
	 * 0: name of variable, 1: 'at index N' or 'of property NAME', 2: error message from type system (must not end in a
	 * dot!)
	 */
	DESTRUCT_TYPE_ERROR_VAR(ERROR, "Variable {0} cannot hold destructured value {1}: {2}."),

	/**
	 * 0: kind of destructuring pattern ('Array' or 'Object' or 'Nested array' or 'Nested object'), 1: 'destructured
	 * value at index N' or 'destructured value of property NAME', 2: error message from type system (must not end in a
	 * dot!)
	 */
	DESTRUCT_TYPE_ERROR_PATTERN(ERROR, "{0} destructuring pattern cannot be applied to {1}: {2}."),

	////// Dependency Injection
	/**
	 * 0: type name, 1: additional description identifying the place of the violating context (name of the
	 * member/parameter)
	 */
	DI_NOT_INJECTABLE(ERROR,
			"Type {0} is not injectable{1}: only user-defined, non-generic, nominally typed interfaces and classes are allowed."),

	/** no parameters are required */
	DI_VARARGS_NOT_INJECTABLE(ERROR, "Injection of parameters that are variadic or optional is not supported."),

	/** 0: type name */
	DI_MUST_BE_INJECTED(WARNING,
			"Type {0} must be injected, because it contains or inherits one or more members annotated with @Inject."),

	/** 0: annotation name, 1: name of required annotation on containing class */
	DI_ANN_ONLY_ON_CLASS_ANNOTATED_WITH(ERROR, "The annotation @{0} is only allowed on classes annotated with @{1}."),

	/** 0: annotation name, 1: name of required annotation on containing class */
	DI_ANN_ONLY_ON_METHOD_IN_CLASS_ANNOTATED_WITH(ERROR,
			"The annotation @{0} is only allowed on methods contained in a class annotated with @{1}."),

	/** 0: annotation name, 1: name of required annotation on argument class */
	DI_ANN_ARG_MUST_BE_ANNOTATED_WITH(ERROR, "Argument to annotation @{0} must be a class annotated with @{1}."),

	/** 0: the name of the violating annotation. */
	DI_ANN_BIND_SECOND_MUST_BE_SUBTYPE_FIRST(ERROR, "Second argument to @{0} must be a subtype of the first."),

	/** no parameters required */
	DI_ANN_PROVIDES_METHOD_MUST_RETURN_VALUE(ERROR, "A provider method must return a value."),

	/** no parameters required */
	DI_ANN_INTERFACE_INJECTION_NOT_SUPPORTED(ERROR, "Injection inside interfaces is not supported."),

	/** no parameters required */
	DI_ANN_INJECTOR_EXTENDS(ERROR, "Classes annotated with @GenerateInjector cannot extend other class."),

	/** 0: the binded type name */
	DI_ANN_BIND_SINGLETON_TARGET_SHOULD_BE_DEFINED_AS_SINGLETON(ERROR,
			"{0} can be defined as a singleton if it is annotated with @Singleton on the definition site."),

	/** no parameters required */
	DI_ANN_INJECTOR_CANNOT_BE_INJECTED_INTO_INJECTOR(ERROR,
			"Types annotated with @GenerateInjector cannot be injected. Use @WithParentInjector instead for creating nested injectors."),

	/** 0: the cyclic graph as a string */
	DI_ANN_USE_INJECTOR_CYCLE(ERROR, "A cycle was detected among the parent injectors: {0}."),

	/** 0: the name of the unavailable field */
	DI_FIELD_IS_NOT_INJECTED_YET(ERROR, "{0} is not yet injected at this point."),

	/** 0: super class type name, 1: violating type name */
	DI_CTOR_BREAKS_INJECTION_CHAIN(WARNING,
			"Constructor at super class {0} is annotated with @Inject. Omitting the @Inject annotation from constructor at class {1} could break injection chain."),

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
			"No binders are provided (third param for this callsite). The following binders are used by the injector and themselves require injection: {0}."),

	/** no parameters required */
	DI_ANN_INJECTOR_MISSING(ERROR, "An instance of N4Injector is required here."),

	/** 0: list of missing binders (as a list of type names) */
	DI_ANN_MISSING_NEEDED_BINDERS(ERROR,
			"Instances are missing for the following binders (they are used by the injector and themselves require injection): {0}."),

	/** no parameters required */
	DI_API_INJECTED(ERROR,
			"The class being instantiated (or one of its super-types) has been marked @Injected in an API project."),

	/** no parameters required */
	DI_ANN_INJECTED_NOT_APPLICABLE(ERROR, "@Injected annotates a class (abstract or not) defined in an API project."),

	////// JSX
	/** 0: name of opening JSX element, 1: name of closing JSX element */
	JSX_JSXELEMENT_OPENING_CLOSING_ELEMENT_NOT_MATCH(ERROR,
			"Opening element {0} does not match with closing element {1}."),

	/** 0: name of the type JSX element is binding to */
	JSX_REACT_ELEMENT_NOT_FUNCTION_OR_CLASS_ERROR(ERROR,
			"JSX element is expected to bind to either a function or class, but bind to type {0} instead."),

	/** No parameter */
	JSX_REACT_ELEMENT_CLASS_MUST_NOT_BE_ABSTRACT(ERROR, "JSX element class must not be abstract."),

	/** 0: name of the return type of the function that JSX element is binding to */
	JSX_REACT_ELEMENT_FUNCTION_NOT_REACT_ELEMENT_ERROR(ERROR,
			"Expecting a function returning a value of type {0} but the return type is {1}."),

	/** no argument */
	JSX_REACT_ELEMENT_CLASS_NOT_REACT_ELEMENT_ERROR(ERROR, "The referred class is not a subtype of React.Component"),

	/** 0: name of the function that JSX element is binding to */
	JSX_REACT_FUNCTIONAL_COMPONENT_CANNOT_START_WITH_LOWER_CASE(ERROR,
			"React functional component {0} cannot start with lower case."),

	/** 0: name of the class that JSX element is binding to */
	JSX_REACT_CLASS_COMPONENT_CANNOT_START_WITH_LOWER_CASE(ERROR,
			"React class component {0} cannot start with lower case."),

	/** 0: name of JSX element is binding to */
	JSX_JSXELEMENT_NOT_BIND_TO_REACT_COMPONENT(ERROR, "JSX element {0} does not bind to any valid React component."),

	/** 0: name of JSX element is binding to */
	JSX_TAG_UNKNOWN(WARNING, "Tag {0} is neither a known HTML tag nor an SVG tag."),

	/** 0: name of JSX property */
	JSX_JSXPROPERTY_ATTRIBUTE_NON_OPTIONAL_PROPERTY_NOT_SPECIFIED(ERROR,
			"Non-optional property {0} should be specified."),

	/**
	 * 0: The name of attribute in spread operator, 1: type of the attribute, 2: the declared type of the corresponding
	 * property in 'props'
	 */
	JSX_JSXSPREADATTRIBUTE_WRONG_SUBTYPE(ERROR, "Attribute {0} has wrong type because {1} not subtype of {2}."),

	/** 0: The name of attribute in spread operator, 1: name of JSX element */
	JSX_JSXSPREADATTRIBUTE_NOT_DECLARED_IN_PROPS(WARNING,
			"Attribute {0} is not a declared property in the props of {1}."),

	/** 0: The name of attribute in JSX property, 1: name of JSX element */
	JSX_JSXSPROPERTYATTRIBUTE_NOT_DECLARED_IN_PROPS(WARNING,
			"Attribute {0} is not a declared property in the props of {1}."),

	/** No parameter */
	JSX_JSXSPROPERTYATTRIBUTE_CHILDREN(WARNING,
			"Attribute 'children' will be overwritten by jsx child elements."),

	/** 0: JSX element in non-JSX file */
	JSX_JSXELEMENT_IN_NON_JSX_RESOURCE(ERROR, "JSX element is expected to be placed in JSX like resource, was {0}."),

	/** No parameter */
	JSX_REACT_NAMESPACE_NOT_ALLOWED(ERROR, "Namespace to react must be React."),

	/** No parameter */
	JSX_REACT_NOT_RESOLVED(ERROR, "Cannot resolve JSX implementation."),

	/** No parameter */
	JSX_NAME_CANNOT_BE_REACT(ERROR, "Element cannot be named React in N4JSX file."),

	////// THIRD PARTY
	/** no parameters required */
	THIRD_PARTY_BABEL_LET_CONST_IN_FUN_EXPR(WARNING,
			"This code is prone to Babel bug #6302. If you use Babel in your build pipeline, you should rename this let/const or the containing function expression."),

	////// N4JS package.json
	/** no parameters */
	PKGJ_MISSING_DEPENDENCY_N4JS_RUNTIME(ERROR,
			"Missing dependency to 'n4js-runtime' (mandatory for all N4JS projects of type library, application, test)."),

	/** no parameters */
	PKGJ_WRONG_DEPENDENCY_N4JS_RUNTIME(ERROR,
			"Dependency to 'n4js-runtime' should be defined below key 'dependencies', not 'devDependencies'."),

	/** 0: The package name without scope, as declared in the package.json, 1: project folder name */
	PKGJ_PACKAGE_NAME_MISMATCH(WARNING,
			"As a convention the package name '{0}' should match the name of the project folder '{1}' on the file system."),

	/** 0: The scope name, as declared in the package.json, 1: parent folder name */
	PKGJ_SCOPE_NAME_MISMATCH(WARNING,
			"As a convention the scope name '{0}' should match the name of the project folder's parent folder '{1}' on the file system."),

	/** 0: The project name without scope */
	PKGJ_INVALID_PROJECT_NAME(ERROR, "The name '{0}' is not a valid package name."),

	/** 0: The scope name */
	PKGJ_INVALID_SCOPE_NAME(ERROR, "The name '{0}' is not a valid scope name."),

	/** 0: Invalid source container type name */
	PKGJ_INVALID_SOURCE_CONTAINER_TYPE(ERROR, "Invalid source container type '{0}'."),

	/** 0: the non-existing path */
	PKGJ_NON_EXISTING_PATH(ERROR, "Path {0} does not exist."),

	/** no parameters */
	PKGJ_EMPTY_SOURCE_PATH(ERROR, "Source container paths must not be empty."),

	/** 0: the non-existing source container path */
	PKGJ_NON_EXISTING_SOURCE_PATH(WARNING, "Source container path {0} does not exist."),

	/** 0: the path 1: 'in'-clause with leading space (e.g. ' in external, test') */
	PKGJ_DUPLICATE_SOURCE_CONTAINER(WARNING, "Duplicate path '{0}' has already been declared as source container{1}."),

	/** 0: the container source path */
	PKGJ_NESTED_SOURCE_CONTAINER(ERROR,
			"A source container must not be nested within other source containers (nested in {0})"),

	/** no parameters */
	PKGJ_NO_OUTPUT_FOLDER(ERROR, "There is no output folder defined, so compilation isn't possible."),

	/** 0: the invalid path */
	PKGJ_INVALID_PATH(ERROR, "'{0}' is not a valid path."),

	/** 0: the absolute path */
	PKGJ_INVALID_ABSOLUTE_PATH(ERROR, "Path '{0}' must not be absolute."),

	/** 0: the non-directory path */
	PKGJ_EXPECTED_DIRECTORY_PATH(ERROR, "Path '{0}' does not point to a directory."),

	/** 0: the non-existing main module specifier */
	PKGJ_NON_EXISTING_MAIN_MODULE(ERROR, "Main module specifier {0} does not exist."),

	/** 0: invalid module filter type, 1: all valid module filter types */
	PKGJ_INVALID_MODULE_FILTER_TYPE(ERROR, "Invalid module filter type '{0}'. Valid filter types are {1}."),

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
			"The given source container '{0}' has not been declared as source container."),

	/** 0: the invalid part of a wildcard */
	PKGJ_INVALID_WILDCARD(ERROR, "'{0}' is not a valid character sequence in a wildcard."),

	/** no parameters */
	PKGJ_NO_RELATIVE_NAVIGATION(ERROR, "Relative navigation is not allowed in a module filter specifier."),

	/** 0: the module filter specifier */
	PKGJ_MODULE_FILTER_DOES_NOT_MATCH(WARNING, "Module filter '{0}' does not match any modules."),

	/** 0: module filter type (e.g. noValidate) */
	PKGJ_FILTER_NO_N4JS_MATCH(ERROR, "Module filters of type {0} must not match N4JS modules/files."),

	/** no parameters */
	PKGJ_APIIMPL_MISSING_IMPL_PROJECTS(ERROR,
			"When defining an implementation ID, you also have to define one or more API projects that are implemented by this project using property 'n4js.implementedProjects'."),

	/** no parameters */
	PKGJ_APIIMPL_REFLEXIVE(ERROR, "An implementation project may not implement itself."),

	/** no parameters required */
	PKGJ_APIIMPL_MISSING_IMPL_ID(ERROR,
			"When defining one or more implemented projects, you also have to define an implementation ID, using property 'implementationId'."),

	/** 0: invalid project type */
	PKGJ_INVALID_PROJECT_TYPE(ERROR, "Invalid project type '{0}'."),

	/** 0: project id 1: section label (e.g. required runtime libraries) */
	PKGJ_PROJECT_REFERENCE_MUST_BE_DEPENDENCY(ERROR,
			"The project reference {0} in {1} must also be declared as explicit project dependency in 'dependencies' or 'devDependencies'."),

	/** 0: message */
	PKGJ_SEMVER_ERROR(ERROR, "{0}"),

	/** 0: message */
	PKGJ_SEMVER_WARNING(WARNING, "{0}"),

	/** 0: version number string representation, 1: reason */
	PKGJ_INVALID_VERSION_NUMBER(ERROR, "Invalid version number '{0}': {1}."),

	/** 0: version constraint string representation, 1: reason */
	PKGJ_INVALID_VERSION_REQUIREMENT(WARNING, "Invalid version requirement '{0}': {1}. Will assume empty string."),

	/** 0: project type */
	PKGJ_PROJECT_TYPE_MANDATORY_OUTPUT_AND_SOURCES(ERROR,
			"Projects of type {0} must always declare an output folder and at least one source container."),

	/** no parameters */
	PKGJ_EMPTY_PROJECT_REFERENCE(ERROR, "A project reference must not be empty."),

	/** no parameters */
	PKGJ_EMPTY_INIT_MODULE(ERROR, "An init module specifier must not be empty."),

	/** 0: project type, 1: empty or 'not ', 2: property name */
	PKGJ_DEFINES_PROPERTY(ERROR, "A project of type '{0}' must {1}define the property '{2}'."),

	/** 0: property name */
	PKGJ_PROPERTY_UNKNOWN(WARNING, "Property '{0}' is unknown."),

	/** 0: implementation project name, 1: type definition project name */
	PKGJ_IMPL_PROJECT_IS_MISSING_FOR_TYPE_DEF(WARNING,
			"The implementation project {0} of type definition project {1} is missing from the dependencies section."),

	/** 0: 'Source' or 'Output code' */
	PKGJ_REWRITE_MODULE_SPECIFIERS__EMPTY_SPECIFIER(ERROR, "{0} module specifier must not be empty."),

	/** no parameters */
	PKGJ_REWRITE_MODULE_SPECIFIERS__INVALID_VALUE(ERROR,
			"String expected (i.e. the module specifier to use in the output code)."),

	/** no parameters */
	PKGJ_PNPM_WORKSPACES_OVERRIDE(WARNING,
			"This property is overridden by property 'packages' in file pnpm-workspaces.yaml."),

	/** 0: dependency cycle */
	LTD_ILLEGAL_LOADTIME_REFERENCE(ERROR,
			"Load-time references to the same or other modules are not allowed within a runtime dependency cycle (except in extends/implements clauses).{0}"),

	/**
	 * 0: name of load-time dependency target module, 1: comma-separated list of other load-time dependency source
	 * modules (including the prefix 'modules ' or 'module '), 2: dependency cycle
	 */
	LTD_LOADTIME_DEPENDENCY_CONFLICT(ERROR,
			"A load-time dependency target module {0} must only be imported once within the same runtime dependency cycle, but {0} is also imported by {1}.{2}"),

	/** 0: dependency cycle */
	LTD_LOADTIME_DEPENDENCY_CYCLE(ERROR,
			"Load-time dependency cycles are disallowed, because successful resolution by Javascript engine cannot be guaranteed.{0}"),

	/**
	 * 0: name of load-time dependency target module, 1: modules that could heal this reference (including the prefix
	 * 'one of the modules ' or 'module '), 2: dependency cycle
	 */
	LTD_REFERENCE_TO_LOADTIME_DEPENDENCY_TARGET(ERROR,
			"When importing modules from a runtime cycle, those that are the target of a load-time dependency (marked with * below) may only be imported after first importing one of the others. Thus, import of module {0} must be preceded by an import of {1}.{2}")

	;

	public final Severity severity;
	private final String msgTemplate;
	private final int argCount;

	private final Pattern ARG = Pattern.compile("\\{\\d\\}");

	IssueCodes(Severity severity, String msgTemplate) {
		this.severity = severity;
		this.msgTemplate = msgTemplate;
		if (msgTemplate == null) {
			this.argCount = 0;
		} else {
			Matcher matcher = ARG.matcher(msgTemplate);
			int maxArgCount = 0;
			while (matcher.find()) {
				String digitStr = matcher.group().substring(1, 2);
				int argPlace = Integer.parseInt(digitStr);
				maxArgCount = Math.max(maxArgCount, argPlace + 1);
			}
			this.argCount = maxArgCount;
		}
	}

	public String getMessage(Object... values) {
		Preconditions.checkArgument(argCount == 0 || (values != null && values.length == argCount), "Check arguments");
		if (values == null) {
			return msgTemplate;
		}
		String message = msgTemplate;
		for (int i = 0; i < values.length; i++) {
			message = message.replace("{" + i + "}", values[i].toString());
		}
		return message;
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
