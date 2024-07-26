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
package org.eclipse.n4js.validation.validators;

import static com.google.common.base.Strings.nullToEmpty;
import static org.eclipse.n4js.N4JSLanguageConstants.FUTURE_RESERVED_WORDS;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.FUNCTION_DECLARATION__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.FUNCTION_EXPRESSION__NAME;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.asyncGeneratorType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.generatorType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getBuiltInTypeScope;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.voidType;
import static org.eclipse.n4js.utils.EcoreUtilN4.getAllContentsFiltered;
import static org.eclipse.n4js.validation.IssueCodes.FUN_BLOCK;
import static org.eclipse.n4js.validation.IssueCodes.FUN_BODY;
import static org.eclipse.n4js.validation.IssueCodes.FUN_GENERATOR_RETURN_TYPE_MISMATCH;
import static org.eclipse.n4js.validation.IssueCodes.FUN_MISSING_RETURN_EXPRESSION;
import static org.eclipse.n4js.validation.IssueCodes.FUN_NAME_MISSING;
import static org.eclipse.n4js.validation.IssueCodes.FUN_NAME_RESERVED;
import static org.eclipse.n4js.validation.IssueCodes.FUN_PARAM_INITIALIZER_ILLEGAL_REFERENCE_TO_BODY_VARIABLE;
import static org.eclipse.n4js.validation.IssueCodes.FUN_PARAM_INITIALIZER_ONLY_UNDEFINED_ALLOWED;
import static org.eclipse.n4js.validation.IssueCodes.FUN_PARAM_OPTIONAL_WRONG_SYNTAX;
import static org.eclipse.n4js.validation.IssueCodes.FUN_PARAM_VARIADIC_WITH_INITIALIZER;
import static org.eclipse.n4js.validation.IssueCodes.FUN_RETURNTYPE_VOID_FOR_SETTER_VIOLATED;
import static org.eclipse.n4js.validation.IssueCodes.FUN_SETTER_CANT_BE_DEFAULT;
import static org.eclipse.n4js.validation.IssueCodes.FUN_SETTER_CANT_BE_VARIADIC;
import static org.eclipse.n4js.validation.IssueCodes.TYS_NON_THIS_ASYNC;
import static org.eclipse.n4js.validation.IssueCodes.TYS_NON_VOID_ASYNC;
import static org.eclipse.n4js.validation.helper.FunctionValidationHelper.internalCheckFormalParameters;
import static org.eclipse.n4js.validation.validators.StaticPolyfillValidatorExtension.internalCheckNotInStaticPolyfillModule;
import static org.eclipse.xtext.util.Strings.toFirstUpper;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toList;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.GetterDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.SetterDeclaration;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.VoidType;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.N4JSLanguageHelper;
import org.eclipse.n4js.utils.nodemodel.HiddenLeafAccess;
import org.eclipse.n4js.utils.nodemodel.HiddenLeafAccess.HiddenLeafs;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.inject.Inject;

/**
 */
@SuppressWarnings("javadoc")
public class N4JSFunctionValidator extends AbstractN4JSDeclarativeValidator {

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
	@Override
	public void register(EValidatorRegistrar registrar) {
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
	public void checkFunctionExpressionInExpressionStatement(FunctionDeclaration functionDeclaration) {
		EObject container = functionDeclaration.eContainer();
		if (container instanceof Block
				&& jsVariantHelper.requireCheckFunctionExpressionInExpressionStatement(functionDeclaration)) {
			if (functionDeclaration.getName() != null) {
				addIssue(functionDeclaration, N4JSPackage.Literals.FUNCTION_DECLARATION__NAME, FUN_BLOCK.toIssueItem());
			} else {
				addIssue(container, functionDeclaration.eContainmentFeature(), FUN_BLOCK.toIssueItem());
			}
		}
	}

	/**
	 *
	 * TODO once ISSUE-666 is resolved this method could be dropped when the check is carried out with
	 * #checkFunctionReturn(FunctionOrFieldAccessor)
	 *
	 * Return-Type checking.
	 *
	 * [N4JSSpec] 7.1.4 Return Statement
	 *
	 * Constraint 111
	 */
	@Check
	public void checkSetter(SetterDeclaration setterDeclaration) {
		holdsFunctionReturnsVoid(setterDeclaration, true);
	}

	/**
	 * Given a function/method with return type void, checking the lack of returns or presence of empty returns
	 *
	 * @param functionOrFieldAccessor
	 *            definition with void-return-type
	 * @param isSetter
	 *            true for setter and therefore ensuring no return at all, false in case of ordinary function/methods
	 *            where TS already does the job
	 */
	private boolean holdsFunctionReturnsVoid(FunctionOrFieldAccessor functionOrFieldAccessor, boolean isSetter) {
		Iterable<ReturnStatement> retstatements = allReturnstatementsAsList(functionOrFieldAccessor);
		VoidType _void = voidType(newRuleEnvironment(functionOrFieldAccessor));

		// Constraint 111.2.
		for (ReturnStatement rst : retstatements) {

			// ...no expression
			if (rst.getExpression() != null) {

				TypeRef expressionType = ts.tau(rst.getExpression());

				EObject actualType = (expressionType instanceof ParameterizedTypeRef)
						? ((ParameterizedTypeRef) expressionType).getDeclaredType()
						: expressionType;

				// ... or the type of expression is void:
				if (actualType != null && actualType != _void) {

					// Issue if isSetter.
					if (isSetter) {

						// Something else than void is returned -> Error
						addIssue(rst, FUN_RETURNTYPE_VOID_FOR_SETTER_VIOLATED.toIssueItem());
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
	 * conflicts with a reserved keyword or a future reserved keyword. In case of conflicts this method creates a
	 * validation if the name violates the constraint.
	 * </p>
	 * IDEBUG-287
	 *
	 * @param definition
	 *            the function definition to validate in respect if its name.
	 */
	@Check
	public boolean checkFunctionName(FunctionDefinition definition) {
		String name = nullToEmpty(definition.getName());
		String desc = validatorMessageHelper.description(definition);
		IssueItem issueItem = null;

		// Disable function name validation against keywords if not N4JS file.
		if (jsVariantHelper.requireCheckFunctionName(definition)) {

			// IDEBUG-304 : allow keywords as member names
			if (definition instanceof N4MethodDeclaration == false) {
				if (FUTURE_RESERVED_WORDS.contains(name)) {
					issueItem = FUN_NAME_RESERVED.toIssueItem(toFirstUpper(desc), "future reserved word");
				}

				if (N4JSLanguageConstants.YIELD_KEYWORD != name && languageHelper.getECMAKeywords().contains(name)) {
					issueItem = FUN_NAME_RESERVED.toIssueItem(toFirstUpper(desc), "keyword");
				}
			}
		}

		if (issueItem != null) {
			EStructuralFeature feature = null;
			if (definition instanceof FunctionDeclaration) {
				feature = FUNCTION_DECLARATION__NAME;
			}
			if (definition instanceof N4MethodDeclaration) {
				feature = PROPERTY_NAME_OWNER__DECLARED_NAME;
			}
			if (definition instanceof FunctionExpression) {
				feature = FUNCTION_EXPRESSION__NAME;
			}
			addIssue(definition, feature, issueItem);
			return false;
		}

		return true;
	}

	/**
	 * Grab all returns in body that apply to this function/get/set. (Leave out returns of nested definitions)
	 *
	 * @param functionOrFieldAccessor
	 *            body to inspect
	 * @return all return statements in a functionOrFieldAccessor (=function-definition or field accessor) (nested) but
	 *         not in included functionExpressions, function definitions ....
	 */
	private Iterable<ReturnStatement> allReturnstatementsAsList(FunctionOrFieldAccessor functionOrFieldAccessor) {
		Iterable<ReturnStatement> retsByEcore;
		if (functionOrFieldAccessor.getBody() == null) {
			retsByEcore = Collections.emptyList();
		} else {
			retsByEcore = toList(filter(getAllContentsFiltered(functionOrFieldAccessor.getBody(),
					it -> !(it instanceof Expression || it instanceof FunctionOrFieldAccessor)),
					ReturnStatement.class));
		}
		return retsByEcore;
	}

	/**
	 * Assuring all return statements do have an expression
	 *
	 * @param retStmt
	 *            only used for error message
	 */
	@Check
	public boolean checkReturnExpression(ReturnStatement retStmt) {
		FunctionOrFieldAccessor fofa = EcoreUtil2.getContainerOfType(retStmt, FunctionOrFieldAccessor.class);
		if (!jsVariantHelper.requireCheckFunctionReturn(fofa)) {
			// cf. 13.1
			return false;
		}

		if (fofa instanceof SetterDeclaration) {
			return false;
		}

		if (fofa == null) {
			return false;
		}

		if (fofa.isReturnValueOptional()) {
			return false;
		}

		VoidType _void = voidType(newRuleEnvironment(fofa));
		TypeRef returnTypeRef = tsh.getExpectedTypeOfFunctionOrFieldAccessor(null, fofa);
		boolean isDeclaredVoid = false;
		if (fofa instanceof FieldAccessor) {
			isDeclaredVoid = TypeUtils.isOrContainsType(((FieldAccessor) fofa).getDeclaredTypeRef(), _void);
		} else if (fofa instanceof FunctionDefinition) {
			isDeclaredVoid = TypeUtils.isOrContainsType(returnTypeRef, _void);
		}

		boolean isUndefined = TypeUtils.isUndefined(returnTypeRef);
		boolean isVoid = TypeUtils.isOrContainsType(returnTypeRef, _void);
		boolean isComposed = (returnTypeRef instanceof ComposedTypeRef
				&& ((ComposedTypeRef) returnTypeRef).getTypeRefs().size() > 1);
		boolean isGetter = fofa instanceof GetterDeclaration;

		if (!isGetter && (isDeclaredVoid || isVoid || isUndefined || isComposed)) {
			return false;
		}

		// check missing return-expression
		if (returnTypeRef != null && retStmt.getExpression() == null) {
			addIssue(retStmt, FUN_MISSING_RETURN_EXPRESSION.toIssueItem(returnTypeRef.getTypeRefAsString()));
			return true;
		}

		// given return-expressions will be checked by xsemantics.
		// TODO what about null - tests with primitives (builtin types) expected ?
		return false;
	}

	/** additional check on top of {@link #checkFunctionName(FunctionDefinition)} */
	@Check
	public void checkFunctionDeclarationName(FunctionDeclaration functionDeclaration) {
		if (functionDeclaration.getName() == null) {
			// Function declaration without name is only allowed for default-exported functions.
			EObject container = functionDeclaration.eContainer();
			if (container instanceof ExportDeclaration) {
				if (((ExportDeclaration) container).isDefaultExport()) {
					// ECMAScript 2015 allows "export default" for anonymous function declarations.
					return;
				}
			}

			// not on "default export":
			// add message "function declarations must have a name"
			if (functionDeclaration.getBody() != null) {
				// mark up to closing parameter parenthesis
				ICompositeNode firstNode = NodeModelUtils.findActualNodeFor(functionDeclaration);
				ICompositeNode lastNode = NodeModelUtils.findActualNodeFor(functionDeclaration.getBody());
				HiddenLeafs hLeafs = hla.getHiddenLeafsBefore(lastNode);

				int off = firstNode.getOffset();
				int len = hLeafs.getOffset() - firstNode.getOffset();
				addIssue(functionDeclaration, off, len, FUN_NAME_MISSING.toIssueItem());
			} else {
				// mark complete function.
				addIssue(functionDeclaration, FUN_NAME_MISSING.toIssueItem());
			}

		}
	}

	@Check
	public void checkFunctionDeclarationBody(FunctionDeclaration functionDeclaration) {
		if (functionDeclaration.getBody() == null && functionDeclaration.getDefinedType() instanceof TFunction &&
				!((TFunction) functionDeclaration.getDefinedType()).isExternal()) {
			addIssue(functionDeclaration, N4JSPackage.Literals.FUNCTION_DECLARATION__NAME, FUN_BODY.toIssueItem());
		}
	}

	@Check
	public void checkParameters(SetterDeclaration fun) {
		boolean isVariadic = fun.getFpar() != null && fun.getFpar().isVariadic();
		boolean hasInitializerAssignment = fun.getFpar() != null && fun.getFpar().isHasInitializerAssignment();
		internalCheckSetterParameters(fun.getFpar(), isVariadic, hasInitializerAssignment);
	}

	@Check
	public void checkParameters(TStructSetter fun) {
		boolean isVariadic = fun.getFpar() != null && fun.getFpar().isVariadic();
		boolean hasInitializerAssignment = fun.getFpar() != null && fun.getFpar().isHasInitializerAssignment();
		internalCheckSetterParameters(fun.getFpar(), isVariadic, hasInitializerAssignment);
	}

	private <T extends EObject> void internalCheckSetterParameters(T fpar, boolean isVariadic,
			boolean hasInitializerAssignment) {
		if (isVariadic) {
			addIssue(fpar, FUN_SETTER_CANT_BE_VARIADIC.toIssueItem());
		}
		if (hasInitializerAssignment) {
			addIssue(fpar, FUN_SETTER_CANT_BE_DEFAULT.toIssueItem());
		}
	}

	@Check
	public void checkOptionalModifier(FormalParameter fpar) {
		if (fpar.getDeclaredTypeRefInAST() != null && fpar.getDeclaredTypeRefInAST().isFollowedByQuestionMark()) {
			addIssue(fpar, FUN_PARAM_OPTIONAL_WRONG_SYNTAX.toIssueItem(fpar.getName()));
		}
	}

	@Check
	public void checkOptionalModifierT(TFormalParameter fpar) {
		if (fpar.getTypeRef() != null && fpar.getTypeRef() != null && fpar.getTypeRef().isFollowedByQuestionMark()) {
			String name = fpar.getTypeRef().getDeclaredType() == null ? null
					: fpar.getTypeRef().getDeclaredType().getName();
			addIssue(fpar, FUN_PARAM_OPTIONAL_WRONG_SYNTAX.toIssueItem(name));
		}
	}

	@Check
	public void checkFormalParametersIn(FunctionTypeExpression fun) {
		// Optionals have a usable type
		internalCheckOptionalsHaveType(fun.getFpars());
		// all other checks
		internalCheckFormalParameters(fun.getFpars().toArray(new TFormalParameter[0]),
				fp -> fp.isVariadic(),
				fp -> fp.isHasInitializerAssignment(),
				fp -> fp.getTypeRef() == null || fp.getTypeRef().getDeclaredType() == null ? null
						: fp.getTypeRef().getDeclaredType().getName(),
				(msg, id, eObj) -> addIssue(msg, eObj, id));
	}

	@Check
	public void checkFormalParametersIn(TFunction fun) {
		// Optionals have a usable type
		internalCheckOptionalsHaveType(fun.getFpars());
		// all other checks
		internalCheckFormalParameters(fun.getFpars().toArray(new TFormalParameter[0]),
				fp -> fp.isVariadic(),
				fp -> fp.isHasInitializerAssignment(),
				fp -> fp.getTypeRef() == null || fp.getTypeRef().getDeclaredType() == null ? null
						: fp.getTypeRef().getDeclaredType().getName(),
				(msg, id, eObj) -> addIssue(msg, eObj, id));
	}

	private void internalCheckOptionalsHaveType(List<TFormalParameter> fpars) {
		for (TFormalParameter fp : fpars) {
			// only 'undefined' as identifier allowed
			if (fp.hasASTInitializer() && !"undefined".equals(fp.getAstInitializer())) {
				addIssue(fp, FUN_PARAM_INITIALIZER_ONLY_UNDEFINED_ALLOWED.toIssueItem());
			}
		}
	}

	@Check
	public void checkFormalParametersIn(FunctionDefinition fun) {
		// 1. check if default parameter initializers could bind to identifiers within the body
		internalCheckInitializerBindings(fun);
		// 2. all other checks
		internalCheckFormalParameters(fun.getFpars().toArray(new FormalParameter[0]),
				fp -> fp.isVariadic(),
				fp -> fp.isHasInitializerAssignment(),
				fp -> fp.getName(),
				(msg, id, eObj) -> addIssue(msg, eObj, id));
	}

	private void internalCheckInitializerBindings(FunctionDefinition fun) {
		if (fun.getBody() == null) {
			return;
		}

		Iterator<IdentifierRef> idRefs = flatten(
				map(fun.getFpars(), it -> EcoreUtil2.eAllOfType(it, IdentifierRef.class))).iterator();
		Set<String> varDeclNamesInBody = toSet(
				map(EcoreUtil2.eAllOfType(fun.getBody(), VariableDeclaration.class), vd -> vd.getName()));

		while (idRefs.hasNext()) {
			IdentifierRef idRef = idRefs.next();
			if (varDeclNamesInBody.contains(idRef.getId().getName())) {
				FormalParameter fpar = EcoreUtil2.getContainerOfType(idRef, FormalParameter.class);
				addIssue(idRef, FUN_PARAM_INITIALIZER_ILLEGAL_REFERENCE_TO_BODY_VARIABLE.toIssueItem(fpar.getName(),
						idRef.getId().getName()));
			}
		}
	}

	/** IDEBUG-211 invalid combination of undefined, variadic & omitting type */
	private void holdsModifierOfParamsHaveType(EList<FormalParameter> list) {
		for (FormalParameter fp : list) {
			if (fp.getDefinedVariable().isHasInitializerAssignment()) {
				if (fp.isVariadic()) {
					addIssue(fp, FUN_PARAM_VARIADIC_WITH_INITIALIZER.toIssueItem());
				}
			}

		}
	}

	/** IDEBUG-211 invalid combination of undefined, variadic & omitting type */
	private void holdsModifierOfParamsHaveTType(List<TFormalParameter> list) {
		for (TFormalParameter fp : list) {
			if (fp.isHasInitializerAssignment()) {
				if (fp.isVariadic()) {
					addIssue(fp, FUN_PARAM_VARIADIC_WITH_INITIALIZER.toIssueItem());
				}
			}
		}
	}

	/**
	 * IDE-1534 Only Promise allowed as inferred return type of an async {@link FunctionDefinition}
	 */
	@Check
	public void checkNonVoidAsyncMethod(FunctionDefinition funDef) {
		if (funDef.isAsync() && (null != funDef.getDefinedType())) {
			TypeRef tfunctionRetType = ((TFunction) funDef.getDefinedType()).getReturnTypeRef();
			if (TypeUtils.isVoid(tfunctionRetType)) {
				addIssue(funDef, TYS_NON_VOID_ASYNC.toIssueItem());
			}
		}
	}

	/**
	 * IDE-1534 The return type of an async {@link FunctionDefinition} is not allowed to refer to the this-type.
	 */
	@Check
	public void checkNoThisAsyncMethod(FunctionDefinition funDef) {
		if (funDef.isAsync()) {
			if (TypeUtils.isOrContainsThisType(funDef.getDeclaredReturnTypeRef())) {
				addIssue(funDef, TYS_NON_THIS_ASYNC.toIssueItem());
			}
		}
	}

	@Check
	public void checkGeneratorReturnTypeDeclaration(FunctionDefinition funDef) {
		if (!funDef.isGenerator()) {
			return;
		}
		TypeRef returnTypeRefInAST = funDef.getDeclaredReturnTypeRefInAST();
		TypeRef returnTypeRef = funDef.getDeclaredReturnTypeRef();
		if (returnTypeRef == null) {
			return;
		}
		RuleEnvironment G = newRuleEnvironment(funDef);
		TypeRef returnTypeRefUB = ts.upperBoundWithReopenAndResolveTypeVars(G, returnTypeRef);
		boolean async = funDef.isAsync();
		boolean isGeneratorType = TypeUtils.isGenerator(returnTypeRefUB, getBuiltInTypeScope(G));
		boolean isAsyncGeneratorType = TypeUtils.isAsyncGenerator(returnTypeRefUB, getBuiltInTypeScope(G));
		if (async && isGeneratorType) {
			IssueItem issueItem = FUN_GENERATOR_RETURN_TYPE_MISMATCH.toIssueItem(generatorType(G).getName(),
					"synchronous", asyncGeneratorType(G).getName());
			addIssue(returnTypeRefInAST, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE, issueItem);
		} else if (!async && isAsyncGeneratorType) {
			IssueItem issueItem = FUN_GENERATOR_RETURN_TYPE_MISMATCH.toIssueItem(asyncGeneratorType(G).getName(),
					"asynchronous", generatorType(G).getName());
			addIssue(returnTypeRefInAST, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE, issueItem);
		}
	}

	// publish this method.
	@Override
	public void addIssue(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData) {
		super.addIssue(message, source, feature, issueCode, issueData);
	}

	/** IDE-1735 restrict content in filling modules. */
	@Check
	public void checkFunctionDeclarationInStaticPolyfillModule(FunctionDeclaration functionDeclaration) {
		internalCheckNotInStaticPolyfillModule(functionDeclaration, this);
	}

	/**
	 * GHOLD-234 add warning for unused type variables in function declarations.
	 */
	@Check
	public void checkNoUnusedTypeParameters(FunctionDeclaration functionDeclaration) {
		internalCheckNoUnusedTypeParameters(functionDeclaration);
	}

	/**
	 * GHOLD-234 add warning for unused type variables in function expressions.
	 */
	@Check
	public void checkNoUnusedTypeParameters(FunctionExpression functionExpression) {
		internalCheckNoUnusedTypeParameters(functionExpression);
	}

	/**
	 * GHOLD-234 add warning for unused type variables in function type expressions.
	 */
	@Check
	public void checkNoUnusedTypeParameters(FunctionTypeExpression functionTypeExp) {
		internalCheckNoUnusedTypeParameters(functionTypeExp);
	}
}
