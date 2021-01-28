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
package org.eclipse.n4js.validation.validators

import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FieldAccessor
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.GetterDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.n4JS.SetterDeclaration
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TStructSetter
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.N4JSLanguageHelper
import org.eclipse.n4js.utils.nodemodel.HiddenLeafAccess
import org.eclipse.n4js.utils.nodemodel.HiddenLeafs
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.N4JSLanguageConstants.*
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.*
import static org.eclipse.n4js.validation.IssueCodes.*
import static org.eclipse.n4js.validation.helper.FunctionValidationHelper.*
import static org.eclipse.n4js.validation.validators.StaticPolyfillValidatorExtension.*
import static org.eclipse.xtext.util.Strings.toFirstUpper

import static extension com.google.common.base.Strings.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static extension org.eclipse.n4js.utils.EcoreUtilN4.*

/**
 */
class N4JSFunctionValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private TypeSystemHelper tsh;

	@Inject
	private HiddenLeafAccess hla;

	@Inject
	private N4JSLanguageHelper languageHelper;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	/*
	 * "Also, an ExpressionStatement cannot start with the function keyword because that might make it ambiguous with a FunctionDeclaration."
	 * [ECMAScript] 12.4 Expression Statement, [N4JS] 8.2. Function Definition
	 *
	 * see also ECMAScript Test Suite Sbp_12.5_A9_T3.js
	 *
	 */
	@Check
	def checkFunctionExpressionInExpressionStatement(FunctionDeclaration functionDeclaration) {
		val container = functionDeclaration.eContainer
		if (container instanceof Block && jsVariantHelper.requireCheckFunctionExpressionInExpressionStatement(functionDeclaration)) {
			val msg = getMessageForFUN_BLOCK
			if (functionDeclaration.name !== null) {
				addIssue(msg, functionDeclaration, N4JSPackage.Literals.FUNCTION_DECLARATION__NAME, FUN_BLOCK)
			} else {
				addIssue(msg, container, functionDeclaration.eContainmentFeature, FUN_BLOCK)
			}
		}
	}

	/**
	 *
	 * TODO once ISSUE-666 is resolved this method could be dropped when
	 * the check is carried out with #checkFunctionReturn(FunctionOrFieldAccessor)
	 *
	 * Return-Type checking.
     *
     * [N4JSSpec] 7.1.4 Return Statement
     *
     * Constraint 111
	 *
	 * @see #checkFunctionReturn(FunctionOrFieldAccessor)
	 */
	@Check
	def checkSetter(SetterDeclaration setterDeclaration) {
		holdsFunctionReturnsVoid(setterDeclaration, true)
	}

	/**
	 * Given a function/method with return type void, checking the lack of returns or presence of empty returns
	 *
	 * @param functionDefinition definition with void-return-type
	 * @param _void precomputed built-in void type
	 * @param isSetter true for setter and therefore ensuring no return at all, false in case of ordinary function/methods
	 * 			where TS already does the job
	 */
	private def boolean holdsFunctionReturnsVoid(FunctionOrFieldAccessor functionOrFieldAccessor, boolean isSetter) {
		val retstatements = allReturnstatementsAsList(functionOrFieldAccessor)
		val _void = newRuleEnvironment(functionOrFieldAccessor).voidType

		// Constraint 111.2.
		for (rst : retstatements) {

			// ...no expression
			if (rst.expression !== null) {

				val expressionType = ts.tau(rst.expression)

				val actualType = if (expressionType instanceof ParameterizedTypeRef) {
						expressionType.declaredType
					} else {
						expressionType
					}

				// ... or the type of expression is void:
				if (actualType !== null && actualType != _void) {

					// Issue if isSetter.
					if (isSetter) {

						// Something else than void is returned -> Error
						val String msg = messageForFUN_RETURNTYPE_VOID_FOR_SETTER_VIOLATED
						addIssue(msg, rst, FUN_RETURNTYPE_VOID_FOR_SETTER_VIOLATED)
						return false;
					} else {
						// other cases are handled by type system (except setters).
						// that is, error message is created by type system: xx is not a subtype of void
					}
				}
			}
		}
		return true;
	}


	/**
	 * Method for checking whether the name of a function definition such as:
	 * <p>
	 * <ul>
	 * <li>{@link FunctionDeclaration <em>Function declaration</em>},</li>
	 * <li>{@link N4MethodDeclaration <em>Method declaration</em>} or</li>
	 * <li>{@link FunctionExpression <em>Function expression</em>}</li>
	 * </ul>
	 * conflicts with a reserved keyword or a future reserved keyword.
	 * In case of conflicts this method creates a validation if the name violates the constraint.
	 * </p>
	 * IDEBUG-287
	 *
	 * @param definition the function definition to validate in respect if its name.
	 */
 	@Check
	def checkFunctionName(FunctionDefinition definition) {
		val name = definition.name.nullToEmpty;
		val desc = validatorMessageHelper.description(definition);
		var errorMessage = "";

		// Disable function name validation against keywords if not N4JS file.
		if (jsVariantHelper.requireCheckFunctionName(definition)) {

			//IDEBUG-304 : allow keywords as member names
			if(definition instanceof N4MethodDeclaration === false){
				if (FUTURE_RESERVED_WORDS.contains(name)) {
					errorMessage = getMessageForFUN_NAME_RESERVED(desc, "future reserved word")
				}

				if (N4JSLanguageConstants.YIELD_KEYWORD != name && languageHelper.getECMAKeywords.contains(name)) {
					errorMessage = getMessageForFUN_NAME_RESERVED(desc, "keyword")
				}
			}
		}

		if (!errorMessage.nullOrEmpty) {
			var feature = switch (definition) {
				FunctionDeclaration: FUNCTION_DECLARATION__NAME
				N4MethodDeclaration: PROPERTY_NAME_OWNER__DECLARED_NAME
				FunctionExpression: FUNCTION_EXPRESSION__NAME
				default: null
			}
			addIssue(toFirstUpper(errorMessage), definition, feature, FUN_NAME_RESERVED);
		}

		return errorMessage.nullOrEmpty
	}

	/**
	 * Grab all returns in body that apply to this function/get/set. (Leave out returns of nested definitions)
	 * @param functionOrFieldAccessor body to inspect
	 * @return all return statements in a functionOrFieldAccessor
	 * 				(=function-definition or field accessor) (nested) but not in included functionExpressions, function definitions ....
	 */
	private def Iterable<ReturnStatement> allReturnstatementsAsList(FunctionOrFieldAccessor functionOrFieldAccessor) {
		val retsByEcore = if (functionOrFieldAccessor.body === null) {
				#[]
		} else {
				functionOrFieldAccessor.body
					.getAllContentsFiltered([ !(it instanceof Expression || it instanceof FunctionOrFieldAccessor) ])
					.filter(ReturnStatement)
					.toList()
		};
		return retsByEcore
	}


	/**
	 * Assuring all return statements do have an expression
	 * @param returnTypeRef only used for error message
	 */
	@Check
	def boolean checkReturnExpression(ReturnStatement retStmt) {
		val fofa = EcoreUtil2.getContainerOfType(retStmt, FunctionOrFieldAccessor);
		if (!jsVariantHelper.requireCheckFunctionReturn(fofa)) {
			// cf. 13.1
			return false;
		}

		if (fofa instanceof SetterDeclaration) {
			return false;
		}

		if (fofa === null) {
			return false;
		}

		if (fofa.isReturnValueOptional) {
			return false;
		}

		val _void = newRuleEnvironment(fofa).voidType;
		val returnTypeRef = tsh.getExpectedTypeOfFunctionOrFieldAccessor(null, fofa);
		val isDeclaredVoid = if (fofa instanceof FieldAccessor) {
				TypeUtils.isOrContainsType(fofa.declaredTypeRef, _void)
			} else if (fofa instanceof FunctionDefinition) {
				TypeUtils.isOrContainsType(returnTypeRef, _void)
			} else false;

		val isUndefined = TypeUtils.isUndefined(returnTypeRef);
		val isVoid = TypeUtils.isOrContainsType(returnTypeRef, _void);
		val isComposed = (returnTypeRef instanceof ComposedTypeRef && (returnTypeRef as ComposedTypeRef).typeRefs.size>1);
		val isGetter = fofa instanceof GetterDeclaration;

		if (!isGetter && (isDeclaredVoid || isVoid || isUndefined || isComposed)) {
			return false;
		}

		// check missing return-expression
		if (returnTypeRef !== null && retStmt.expression === null) {
			val String msg = getMessageForFUN_MISSING_RETURN_EXPRESSION(returnTypeRef.typeRefAsString);
			addIssue(msg, retStmt, FUN_MISSING_RETURN_EXPRESSION);
			return true;
		}

		// given return-expressions will be checked by xsemantics.
		// TODO what about null - tests with primitives (builtin types) expected ?
		return false;
	}

	/** additional check on top of {@link #checkFunctionName()} */
	@Check
	def checkFunctionDeclarationName(FunctionDeclaration functionDeclaration) {
		if( functionDeclaration.name === null ) {
			// Function declaration without name is only allowed for default-exported functions.
			val container = functionDeclaration.eContainer;
			if( container instanceof ExportDeclaration){
				if( container.isDefaultExport ) {
					// ECMAScript 2015 allows "export default" for anonymous function declarations.
					return;
				}
			}

			// not on "default export":
			// add message "function declarations must have a name"
			if( functionDeclaration.body !== null) {
				// mark up to closing parameter parenthesis
				val firstNode = NodeModelUtils.findActualNodeFor(functionDeclaration);
				val lastNode = NodeModelUtils.findActualNodeFor(functionDeclaration.body);
				val HiddenLeafs hLeafs = hla.getHiddenLeafsBefore(lastNode);

				val off = firstNode.offset;
				val len = hLeafs.offset - firstNode.offset;
				addIssue(messageForFUN_NAME_MISSING,functionDeclaration,off,len,FUN_NAME_MISSING);
			} else {
			  	// mark complete function.
				addIssue(messageForFUN_NAME_MISSING,functionDeclaration,FUN_NAME_MISSING);
			}

		}
	}

	@Check
	def checkFunctionDeclarationBody(FunctionDeclaration functionDeclaration) {
		if (functionDeclaration.body === null && functionDeclaration.definedType instanceof TFunction &&
			!(functionDeclaration.definedType as TFunction).external) {
			addIssue(getMessageForFUN_BODY, functionDeclaration, N4JSPackage.Literals.FUNCTION_DECLARATION__NAME,
				FUN_BODY)
		}
	}

	@Check
	def checkParameters(SetterDeclaration fun){
		val isVariadic = if( fun?.fpar !== null ) { fun.fpar.isVariadic } else { false }
		val hasInitializerAssignment = if( fun?.fpar !== null ) { fun.fpar.hasInitializerAssignment } else { false }
		internalCheckSetterParameters(fun.fpar, isVariadic, hasInitializerAssignment);
	}

	@Check
	def checkParameters(TStructSetter fun){
		val isVariadic = if( fun?.fpar !== null ) { fun.fpar.isVariadic } else { false }
		val hasInitializerAssignment = if( fun?.fpar !== null ) { fun.fpar.hasInitializerAssignment } else { false }
		internalCheckSetterParameters(fun.fpar, isVariadic, hasInitializerAssignment);
	}

	private def <T extends EObject> internalCheckSetterParameters(T fpar, boolean isVariadic, boolean hasInitializerAssignment){
		if (isVariadic) {
			val String msg = messageForFUN_SETTER_CANT_BE_VARIADIC
			addIssue(msg, fpar, FUN_SETTER_CANT_BE_VARIADIC)
		}
		if (hasInitializerAssignment) {
			val String msg = messageForFUN_SETTER_CANT_BE_DEFAULT
			addIssue(msg, fpar, FUN_SETTER_CANT_BE_DEFAULT)
		}
	}

	@Check
	def void checkOptionalModifier(FormalParameter fpar) {
		if(fpar.declaredTypeRef!==null && fpar.declaredTypeRef.followedByQuestionMark) {
			val String msg = getMessageForFUN_PARAM_OPTIONAL_WRONG_SYNTAX(fpar.name)
			addIssue(msg, fpar, FUN_PARAM_OPTIONAL_WRONG_SYNTAX)
		}
	}

	@Check
	def void checkOptionalModifierT(TFormalParameter fpar) {
		if(fpar.typeRef!==null && fpar.typeRef.followedByQuestionMark) {
			val String msg = getMessageForFUN_PARAM_OPTIONAL_WRONG_SYNTAX(fpar.typeRef?.declaredType?.name)
			addIssue(msg, fpar, FUN_PARAM_OPTIONAL_WRONG_SYNTAX)
		}
	}

	@Check
	def checkFormalParametersIn(FunctionTypeExpression fun) {
		// Optionals have a usable type
		internalCheckOptionalsHaveType(fun.fpars);
		// all other checks
		val issueConsumer = [String msg, String id, EObject eObj |
			addIssue(msg, eObj, id);
		];
		<TFormalParameter>internalCheckFormalParameters(fun.fpars, [variadic], [hasInitializerAssignment], [typeRef?.declaredType?.name], issueConsumer);
	}

	@Check
	def checkFormalParametersIn(TFunction fun) {
		// Optionals have a usable type
		internalCheckOptionalsHaveType(fun.fpars);
		// all other checks
		val issueConsumer = [String msg, String id, EObject eObj |
			addIssue(msg, eObj, id);
		];
		<TFormalParameter>internalCheckFormalParameters(fun.fpars, [variadic], [hasInitializerAssignment], [typeRef?.declaredType?.name], issueConsumer);
	}

	private def void internalCheckOptionalsHaveType(TFormalParameter[] fpars) {
		for (fp : fpars) {
			// only 'undefined' as identifier allowed
			if (fp.hasASTInitializer && !"undefined".equals(fp.astInitializer)) {
				addIssue( messageForFUN_PARAM_INITIALIZER_ONLY_UNDEFINED_ALLOWED, fp, FUN_PARAM_INITIALIZER_ONLY_UNDEFINED_ALLOWED )
			}
		}
	}

	@Check
	def checkFormalParametersIn(FunctionDefinition fun) {
		// 1. check if default parameter initializers could bind to identifiers within the body
		internalCheckInitializerBindings(fun);
		// 2. all other checks
		val issueConsumer = [String msg, String id, EObject eObj |
			addIssue(msg, eObj, id);
		];
		<FormalParameter>internalCheckFormalParameters(fun.fpars, [variadic], [hasInitializerAssignment], [name], issueConsumer);
	}

	private def internalCheckInitializerBindings(FunctionDefinition fun) {
		if (fun.body === null)
			return;

		val idRefs = fun.fpars.map[EcoreUtil2.eAllOfType(it, IdentifierRef)].flatten.iterator;
		val varDeclNamesInBody = EcoreUtil2.eAllOfType(fun.body, VariableDeclaration).map[it.name];

		while (idRefs.hasNext) {
			val idRef = idRefs.next();
			if (varDeclNamesInBody.contains(idRef.id.name)) {
				val fpar = EcoreUtil2.getContainerOfType(idRef, FormalParameter);
				val String msg = getMessageForFUN_PARAM_INITIALIZER_ILLEGAL_REFERENCE_TO_BODY_VARIABLE(fpar.name, idRef.id.name);
				addIssue(msg, idRef, FUN_PARAM_INITIALIZER_ILLEGAL_REFERENCE_TO_BODY_VARIABLE)
			}
		}
	}


	/** IDEBUG-211 invalid combination of undefined, variadic & omitting type */
	def holdsModifierOfParamsHaveType(EList<FormalParameter> list) {
		for(fp:list) {
			if(fp.definedTypeElement.hasInitializerAssignment) {
				if(fp.variadic) {
					addIssue(messageForFUN_PARAM_VARIADIC_WITH_INITIALIZER, fp, FUN_PARAM_VARIADIC_WITH_INITIALIZER)
				}
			}

		}
	}

	/** IDEBUG-211 invalid combination of undefined, variadic & omitting type */
	def holdsModifierOfParamsHaveTType(List<TFormalParameter> list) {
		for(fp:list) {
			if(fp.hasInitializerAssignment) {
				if(fp.variadic) {
					addIssue(messageForFUN_PARAM_VARIADIC_WITH_INITIALIZER, fp, FUN_PARAM_VARIADIC_WITH_INITIALIZER)
				}
			}
		}
	}

	/**
	 * IDE-1534 Only Promise allowed as inferred return type of an async {@link FunctionDefinition}
	 */
	@Check
	def checkNonVoidAsyncMethod(FunctionDefinition funDef) {
		if (funDef.isAsync && (null !== funDef.definedType)) {
			val TypeRef tfunctionRetType = (funDef.definedType as TFunction).getReturnTypeRef();
			if (TypeUtils.isVoid(tfunctionRetType)) {
				val message = messageForTYS_NON_VOID_ASYNC
				addIssue(message, funDef, TYS_NON_VOID_ASYNC)
			}
		}
	}

	/**
	 * IDE-1534 The return type of an async {@link FunctionDefinition} is not allowed to refer to the this-type.
	 */
	@Check
	def checkNoThisAsyncMethod(FunctionDefinition funDef) {
		if (funDef.isAsync) {
			if (TypeUtils.isOrContainsThisType(funDef.declaredReturnTypeRef)) {
				val message = messageForTYS_NON_THIS_ASYNC
				addIssue(message, funDef, TYS_NON_THIS_ASYNC)
			}
		}
	}

	@Check
	def checkGeneratorReturnTypeDeclaration(FunctionDefinition funDef) {
		if (!funDef.generator) {
			return;
		}
		val returnTypeRefInAST = funDef.declaredReturnTypeRefInAST;
		val returnTypeRef = funDef.declaredReturnTypeRef;
		if (returnTypeRef === null) {
			return;
		}
		val G = funDef.newRuleEnvironment;
		val returnTypeRefUB = ts.upperBoundWithReopenAndResolve(G, returnTypeRef);
		val async = funDef.async;
		val isGeneratorType = TypeUtils.isGenerator(returnTypeRefUB, G.builtInTypeScope);
		val isAsyncGeneratorType = TypeUtils.isAsyncGenerator(returnTypeRefUB, G.builtInTypeScope);
		if (async && isGeneratorType) {
			addIssue(getMessageForFUN_GENERATOR_RETURN_TYPE_MISMATCH(G.generatorType.name, "synchronous", G.asyncGeneratorType.name),
				returnTypeRefInAST, FUN_GENERATOR_RETURN_TYPE_MISMATCH);
		} else if (!async && isAsyncGeneratorType) {
			addIssue(getMessageForFUN_GENERATOR_RETURN_TYPE_MISMATCH(G.asyncGeneratorType.name, "asynchronous", G.generatorType.name),
				returnTypeRefInAST, FUN_GENERATOR_RETURN_TYPE_MISMATCH);
		}
	}

	// publish this method.
	public override void addIssue(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData) {
				super.addIssue(message,source,feature,issueCode,issueData)
	}

	/** IDE-1735 restrict content in filling modules. */
	@Check
	def checkFunctionDeclarationInStaticPolyfillModule(FunctionDeclaration functionDeclaration) {
		internalCheckNotInStaticPolyfillModule(functionDeclaration, this)
	}

	/**
	 * GHOLD-234 add warning for unused type variables in function declarations.
	 */
	@Check
	def checkNoUnusedTypeParameters(FunctionDeclaration functionDeclaration) {
		internalCheckNoUnusedTypeParameters(functionDeclaration);
	}

	/**
	 * GHOLD-234 add warning for unused type variables in function expressions.
	 */
	@Check
	def checkNoUnusedTypeParameters(FunctionExpression functionExpression) {
		internalCheckNoUnusedTypeParameters(functionExpression);
	}


	/**
	 * GHOLD-234 add warning for unused type variables in function type expressions.
	 */
	@Check
	def checkNoUnusedTypeParameters(FunctionTypeExpression functionTypeExp) {
		internalCheckNoUnusedTypeParameters(functionTypeExp);
	}
}
