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
import org.eclipse.n4js.n4JS.ArrowFunction
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
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.SetterDeclaration
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TStructSetter
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.utils.N4JSLanguageHelper
import org.eclipse.n4js.utils.nodemodel.HiddenLeafAccess
import org.eclipse.n4js.utils.nodemodel.HiddenLeafs
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.n4js.validation.helper.N4JSLanguageConstants
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.*
import static org.eclipse.n4js.validation.IssueCodes.*
import static org.eclipse.n4js.validation.helper.FunctionValidationHelper.*
import static org.eclipse.n4js.validation.helper.N4JSLanguageConstants.*
import static org.eclipse.n4js.validation.validators.StaticPolyfillValidatorExtension.*
import static org.eclipse.xtext.util.Strings.toFirstUpper

import static extension com.google.common.base.Strings.*
import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*
import static extension org.eclipse.n4js.utils.EcoreUtilN4.*
import org.eclipse.n4js.flowgraphs.analysers.DeadCodeVisitor
import org.eclipse.n4js.flowgraphs.analysers.DeadCodeVisitor.DeadCodeRegion
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyzer
import org.eclipse.n4js.validation.N4JSElementKeywordProvider
import com.google.common.base.Strings

/**
 */
class N4JSFunctionValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private ReturnOrThrowAnalysis returnOrThrowAnalysis

	@Inject
	private HiddenLeafAccess hla;

	@Inject
	private N4JSLanguageHelper languageHelper;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	@Inject
	private N4JSElementKeywordProvider keywordProvider;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Checks all flow graph related validations
	 */
	@Check
	def checkFlowGraphs(Script script) {
		// Note: The Flow Graph is NOT stored in the meta info cache. Hence, it is created here at use site.
		// In case the its creation is moved to the N4JSPostProcessor, care about an increase in memory consumption.
		val N4JSFlowAnalyzer flowAnalyzer = new N4JSFlowAnalyzer();
		flowAnalyzer.createGraphs(script);

		val dcf = new DeadCodeVisitor();

		flowAnalyzer.accept(dcf); // GH-120: comment-out this line to disable CFG

		internalCheckDeadCode(dcf);
	}

	// Req.107
	private def String internalCheckDeadCode(DeadCodeVisitor dcf) {
		val deadCodeRegions = dcf.getDeadCodeRegions();

		for (DeadCodeRegion deadCodeRegion : deadCodeRegions) {
			val String stmtDescription = getStatementDescription(deadCodeRegion);
			var String errCode = FUN_DEAD_CODE;
			var String msg = getMessageForFUN_DEAD_CODE();
			if (stmtDescription !== null) {
				msg = getMessageForFUN_DEAD_CODE_WITH_PREDECESSOR(stmtDescription);
				errCode = FUN_DEAD_CODE_WITH_PREDECESSOR;
			}
			addIssue(msg, deadCodeRegion.getContainer, deadCodeRegion.getOffset(), deadCodeRegion.getLength(), errCode);
		}
	}

	private def String getStatementDescription(DeadCodeRegion deadCodeRegion) {
		val reachablePred = deadCodeRegion.getReachablePredecessor();
		if (reachablePred === null)
			return null;
		
		val String keyword = keywordProvider.keyword(reachablePred);
		if (Strings.isNullOrEmpty(keyword)) {
			return reachablePred.eClass.name;
		}
		return keyword;
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
	def checkGetter(GetterDeclaration getterDeclaration) {
		holdsFunctionReturn(getterDeclaration)
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
	def checkFunctionReturn(FunctionDefinition functionDefinition) {
		if (!jsVariantHelper.requireCheckFunctionReturn(functionDefinition)) {
			return; // cf. 13.1
		}
		holdsFunctionReturn(functionDefinition as FunctionOrFieldAccessor);
	}

	/**
     * Return-Type checking.
     *
     * [N4JSSpec] 7.1.4 Return Statement
     *
     * Constraint 111
     *
     * @param functionDefinition of for Methods and Functions
     *
     */
	// @Check Disabled because setter still claim to be of type ANY.  IDE-666
	// IF setter claims to be of type VOID/NULL this method could serve as entry point for the
	// three checks above.
	private def boolean holdsFunctionReturn(FunctionOrFieldAccessor functionOrFieldAccessor) {
		// simple inference without context: we only need to check IF there is a return type declared (or inferred), we do not need the concrete type
		val inferredType = ts.tau(functionOrFieldAccessor)
		val TypeRef retTypeRef = switch inferredType {
			// note: order is important, because FunctionTypeRef IS a ParameterizedTypeRef as well
			FunctionTypeExprOrRef: getActualReturnTypeRef(functionOrFieldAccessor, inferredType)
			ParameterizedTypeRef: inferredType
			default: null
		}

		if (retTypeRef === null) return true; // probably consequential error

		// obtain the built-in void-Type
		val _void = newRuleEnvironment(functionOrFieldAccessor).voidType

		val isDeclaredVoid = if(functionOrFieldAccessor instanceof FieldAccessor) {
			TypeUtils.isOrContainsType(functionOrFieldAccessor.declaredTypeRef, _void)
		} else if(functionOrFieldAccessor instanceof FunctionDefinition) {
			TypeUtils.isOrContainsType(functionOrFieldAccessor.returnTypeRef, _void)
		} else false
		val isVoid = TypeUtils.isOrContainsType(retTypeRef, _void);
		val isNotVoid =  !isVoid ||
			(retTypeRef instanceof ComposedTypeRef && (retTypeRef as ComposedTypeRef).typeRefs.size>1);
		val isOptionalReturnType = functionOrFieldAccessor.isReturnValueOptional;


		val FunctionFullReport analysis = returnOrThrowAnalysis.exitBehaviourWithFullReport(functionOrFieldAccessor.body?.statements)

		if (isOptionalReturnType) {
			// anything goes!
			// (function may or may not leave with return statement; return statements may or may not have an expression)
			return true;
		}
		if (isVoid && isNotVoid) {
			return true; // union{any,void} --> everything is ok
		}
		if (isDeclaredVoid || (isVoid && !(functionOrFieldAccessor instanceof GetterDeclaration))) {
			// Only if the return type is explicitly set to void
			// 1. Search for all explicit return statements
			// 2. Implicit returns: ( No implicit return given )
			return holdsFunctionReturnsVoid(functionOrFieldAccessor, false)
		}

		// isNotVoid:
		return holdsFunctionReturnsSomething(functionOrFieldAccessor, retTypeRef, analysis)
	}

	private def TypeRef getActualReturnTypeRef(FunctionOrFieldAccessor functionOrFieldAccessor, FunctionTypeExprOrRef inferredType) {
		var typeRef = inferredType?.returnTypeRef;
		if(functionOrFieldAccessor instanceof FunctionDefinition) {
			val tFun = functionOrFieldAccessor.definedType;
			if (functionOrFieldAccessor.isAsync) {
				typeRef = getTypeArgumentOfReturnType(functionOrFieldAccessor, tFun, 0);
			}
			if (functionOrFieldAccessor.isGenerator) {
				typeRef = getTypeArgumentOfReturnType(functionOrFieldAccessor, tFun, 1);
			}
		}
		return typeRef;
	}

	private def getTypeArgumentOfReturnType(FunctionOrFieldAccessor functionOrFieldAccessor, Type tFun, int idx) {
		if (tFun instanceof TFunction) {
			val actualReturnTypeRef = tFun.returnTypeRef;
			if (actualReturnTypeRef.typeArgs.length > idx) {
				val tReturn = actualReturnTypeRef.typeArgs.get(idx);
				val ruleEnv = newRuleEnvironment(functionOrFieldAccessor);
				var typeRef = ts.resolveType(ruleEnv, tReturn);
				if (TypeUtils.isUndefined(typeRef)) {
					typeRef = newRuleEnvironment(functionOrFieldAccessor).voidTypeRef;
				}
				return typeRef;
			}
		}
		return null;
	}

	/**
	 * Given a function/method with returntype void, checking the lack of returns or presence of empty returns
	 *
	 * @param functionDefinition definition whith void-returntype
	 * @param _void precomputed builtin void type
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
	 *
	 * Constraint 111.3 Item 2 "all control flows must either end with a return or throw statement"
	 *
	 * @param functionDefinition functionDefinition with return type not void
	 * @param returnTypeRef only used for error message
	 */
	private def boolean holdsFunctionReturnsSomething(FunctionOrFieldAccessor functionOrFieldAccessor,
		TypeRef returnTypeRef, FunctionFullReport analysis) {

		if (functionOrFieldAccessor.body === null) {
			return true;
		}
		if (functionOrFieldAccessor instanceof N4MethodDeclaration) {
			if(functionOrFieldAccessor.isConstructor) {
				// we have a non-void constructor -> there will already be an error elsewhere -> avoid duplicate errors
				return true;
			}
		}

		// Constraint 111.3.a
		holdsAllReturnStatementsContainExpression(functionOrFieldAccessor, returnTypeRef);

		// ok, but maybe a control flow ends without a return or throw:

		// Constrain 111.3.b
		var bFoundControlFlowWOReturn = switch ( analysis.returnMode ) {
			case ReturnMode.noReturnsMode: isMissingReturnDisallowed(functionOrFieldAccessor)
			BreakOrContinue: false // should not happen.
			case ReturnMode.throwsMode: false // all fine - no check for throws.
			case ReturnMode.returnsMode: false // all fine - TS doing the check.
		}

		return ! bFoundControlFlowWOReturn;
	}

	/**
	 * Helper method. The argument does contain a control-flow path where no value is returned.
	 * However, before raising an error (due to breaking Constraint 111.3.a)
	 * it's necessary to check whether we're dealing with an arrow function of the single-expression variety,
	 * to which ES6 grants an implicit-return, thus allowing them.
	 */
	private def boolean isMissingReturnDisallowed(FunctionOrFieldAccessor accessor) {
		// ES6 arrow functions allow single-exprs to lack an explicit return
		if (accessor instanceof ArrowFunction) {
			if (accessor.isSingleExprImplicitReturn) {
				return false
			}
		}
		// Return statements are optional in generator functions
 		if (accessor instanceof FunctionDefinition) {
			if (accessor.isGenerator) {
				return false;
			}
		}
		// at least one leaking control-flow path out of Method w/o returning anything:
		val highlightFeature = switch accessor {
			FunctionDeclaration: N4JSPackage.Literals.FUNCTION_DECLARATION__NAME
			N4MethodDeclaration: N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME
			FunctionExpression: N4JSPackage.Literals.FUNCTION_EXPRESSION__NAME
			GetterDeclaration: N4JSPackage.Literals.GETTER_DECLARATION__DEFINED_GETTER
			default: null
		}
		val String msg = messageForFUN_MISSING_RETURN_OR_THROW_STATEMENT
		addIssue(msg, accessor, highlightFeature, FUN_MISSING_RETURN_OR_THROW_STATEMENT)
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
		val retsByEcore = if (functionOrFieldAccessor.body === null)
				#[]
			else
				functionOrFieldAccessor.body.getAllContentsFiltered(
					[! ( it instanceof Expression || it instanceof FunctionOrFieldAccessor)]).filter(ReturnStatement).
					toList()
		return retsByEcore
	}

	/**
	 * Assuring all return statements do have an expression
	 * @param returnTypeRef only used for error message
	 */
	private def boolean holdsAllReturnStatementsContainExpression(FunctionOrFieldAccessor definition, TypeRef returnTypeRef) {

		val retstatements = allReturnstatementsAsList(definition)
		var errorsFound = false;
		for (ReturnStatement rst : retstatements) {

			// check missing return-expression
			if (rst.expression === null) {
				val String msg = getMessageForFUN_MISSING_RETURN_EXPRESSION(returnTypeRef.typeRefAsString)
				addIssue(msg, rst, FUN_MISSING_RETURN_EXPRESSION)
				errorsFound = true;
			}

		// given return-expressions will be checked by xsemantics.
		// TODO what about null - tests with primitives (builtin types) expected ?
		}
		return errorsFound;

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
			if (TypeUtils.isOrContainsThisType(funDef.returnTypeRef)) {
				val message = messageForTYS_NON_THIS_ASYNC
				addIssue(message, funDef, TYS_NON_THIS_ASYNC)
			}
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
