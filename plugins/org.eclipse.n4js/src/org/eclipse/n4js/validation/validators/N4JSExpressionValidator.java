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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.asyncIterableTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getPredefinedTypes;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iterableTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.nullType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.numberTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.symbolObjectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.voidType;
import static org.eclipse.n4js.validation.IssueCodes.BIT_SYMBOL_NOT_A_CTOR;
import static org.eclipse.n4js.validation.IssueCodes.CLF_INVALID_ACCESS_OF_STATIC_MEMBER_OF_INTERFACE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NO_THIS_IN_FIELD_OF_INTERFACE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NO_THIS_IN_STATIC_FIELD;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NO_THIS_IN_STATIC_MEMBER_OF_INTERFACE;
import static org.eclipse.n4js.validation.IssueCodes.EXP_ACCESS_INVALID_TYPE_OF_TARGET;
import static org.eclipse.n4js.validation.IssueCodes.EXP_ASSIGN_CONST_VARIABLE;
import static org.eclipse.n4js.validation.IssueCodes.EXP_AWAIT_NON_ASYNC;
import static org.eclipse.n4js.validation.IssueCodes.EXP_AWAIT_NON_ASYNC_SPECIAL;
import static org.eclipse.n4js.validation.IssueCodes.EXP_CALL_CLASS_CTOR;
import static org.eclipse.n4js.validation.IssueCodes.EXP_CALL_CONFLICT_IN_INTERSECTION;
import static org.eclipse.n4js.validation.IssueCodes.EXP_CALL_CONSTRUCT_SIG_OF_INTERFACE_DIRECTLY_USED;
import static org.eclipse.n4js.validation.IssueCodes.EXP_CALL_NOT_A_FUNCTION;
import static org.eclipse.n4js.validation.IssueCodes.EXP_CAST_FAILED;
import static org.eclipse.n4js.validation.IssueCodes.EXP_CAST_INVALID_TARGET;
import static org.eclipse.n4js.validation.IssueCodes.EXP_CAST_UNNECESSARY;
import static org.eclipse.n4js.validation.IssueCodes.EXP_COMPILE_TIME_MANDATORY;
import static org.eclipse.n4js.validation.IssueCodes.EXP_FORBIDDEN_TYPE_IN_BINARY_LOGICAL_EXPRESSION;
import static org.eclipse.n4js.validation.IssueCodes.EXP_INDEXED_ACCESS_COMPUTED_NOTFOUND;
import static org.eclipse.n4js.validation.IssueCodes.EXP_INDEXED_ACCESS_ENUM;
import static org.eclipse.n4js.validation.IssueCodes.EXP_INDEXED_ACCESS_FORBIDDEN;
import static org.eclipse.n4js.validation.IssueCodes.EXP_INDEXED_ACCESS_IMPL_RESTRICTION;
import static org.eclipse.n4js.validation.IssueCodes.EXP_INDEXED_ACCESS_SYMBOL_INVALID;
import static org.eclipse.n4js.validation.IssueCodes.EXP_INDEXED_ACCESS_SYMBOL_READONLY;
import static org.eclipse.n4js.validation.IssueCodes.EXP_INDEXED_ACCESS_SYMBOL_WRONG_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.EXP_MATH_OPERAND_IS_CONSTANT;
import static org.eclipse.n4js.validation.IssueCodes.EXP_MATH_OPERATION_RESULT_IS_CONSTANT;
import static org.eclipse.n4js.validation.IssueCodes.EXP_MATH_TYPE_NOT_PERMITTED;
import static org.eclipse.n4js.validation.IssueCodes.EXP_METHOD_REF_UNATTACHED_FROM_RECEIVER;
import static org.eclipse.n4js.validation.IssueCodes.EXP_MISPLACED_AWAIT;
import static org.eclipse.n4js.validation.IssueCodes.EXP_NEW_CANNOT_INSTANTIATE;
import static org.eclipse.n4js.validation.IssueCodes.EXP_NEW_CONFLICT_IN_INTERSECTION;
import static org.eclipse.n4js.validation.IssueCodes.EXP_NEW_NOT_A_CTOR;
import static org.eclipse.n4js.validation.IssueCodes.EXP_NEW_WILDCARD_NO_COVARIANT_CTOR;
import static org.eclipse.n4js.validation.IssueCodes.EXP_NEW_WILDCARD_OR_TYPEVAR;
import static org.eclipse.n4js.validation.IssueCodes.EXP_NUM_OF_ARGS_TOO_FEW;
import static org.eclipse.n4js.validation.IssueCodes.EXP_NUM_OF_ARGS_TOO_MANY;
import static org.eclipse.n4js.validation.IssueCodes.EXP_PROMISIFY_INVALID_USE;
import static org.eclipse.n4js.validation.IssueCodes.EXP_WARN_CONSTANT_EQUALITY_TEST;
import static org.eclipse.n4js.validation.IssueCodes.EXP_WARN_DISPENSABLE_CONDITIONAL_EXPRESSION;
import static org.eclipse.n4js.validation.IssueCodes.IMP_IMPORTED_ELEMENT_READ_ONLY;
import static org.eclipse.n4js.validation.IssueCodes.TYS_INSTANCEOF_NOT_SUPPORTED_FOR_BUILT_IN_INTERFACES;
import static org.eclipse.n4js.validation.IssueCodes.TYS_INSTANCEOF_NOT_SUPPORTED_FOR_USE_SITE_STRUCTURAL;
import static org.eclipse.n4js.validation.IssueCodes.VCO_TEMPLATE_IN_OPT_CHAIN;
import static org.eclipse.n4js.validation.IssueCodes.VIS_NEW_CANNOT_INSTANTIATE_INVISIBLE_CONSTRUCTOR;
import static org.eclipse.n4js.validation.IssueCodes.VIS_WRONG_READ_WRITE_ACCESS;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.forall;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.compileTime.CompileTimeEvaluationError;
import org.eclipse.n4js.compileTime.CompileTimeEvaluator.UnresolvedPropertyAccessError;
import org.eclipse.n4js.compileTime.CompileTimeValue;
import org.eclipse.n4js.compileTime.CompileTimeValue.ValueInvalid;
import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.AdditiveOperator;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.BooleanLiteral;
import org.eclipse.n4js.n4JS.CastExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.EqualityOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.ExpressionWithTarget;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.NumericLiteral;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PromisifyExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.RelationalOperator;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ShiftExpression;
import org.eclipse.n4js.n4JS.StringLiteral;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.TaggedTemplateString;
import org.eclipse.n4js.n4JS.ThisArgProvider;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.postprocessing.ASTMetaInfoUtils;
import org.eclipse.n4js.scoping.accessModifiers.MemberVisibilityChecker;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.scoping.builtin.N4Scheme;
import org.eclipse.n4js.scoping.members.MemberScopingHelper;
import org.eclipse.n4js.scoping.utils.ExpressionExtensions;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ExistentialTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExprOrRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.LiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.NumericLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.StringLiteralTypeRef;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.BuiltInType;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.ModuleNamespaceVirtualType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TExportableElement;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper.Callable;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper.Newable;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind;
import org.eclipse.n4js.utils.PromisifyHelper;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.eclipse.xtext.xbase.lib.BooleanExtensions;

import com.google.inject.Inject;

/**
 */
@SuppressWarnings("javadoc")
public class N4JSExpressionValidator extends AbstractN4JSDeclarativeValidator {
	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private ContainerTypesHelper containerTypesHelper;
	@Inject
	private MemberScopingHelper memberScopingHelper;
	@Inject
	private MemberVisibilityChecker memberVisibilityChecker;
	@Inject
	private PromisifyHelper promisifyHelper;
	@Inject
	private JavaScriptVariantHelper jsVariantHelper;
	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;
	@Inject
	private DeclMergingHelper declMergingHelper;

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

	@Check
	public void checkAwaitExpression(AwaitExpression awaitExpression) {
		if (!N4JSLanguageUtils.isValidLocationForAwait(awaitExpression)) {
			IssueItem issueItem = EXP_MISPLACED_AWAIT.toIssueItem("await", "async");
			addIssue(awaitExpression, issueItem);
		}

		if (awaitExpression.getExpression() == null) {
			// broken AST.
			return;
		}

		internalCheckAwaitingAPromise(awaitExpression);
	}

	private void internalCheckAwaitingAPromise(AwaitExpression awaitExpression) {
		Expression subExpr = awaitExpression.getExpression();
		if (subExpr == null) {
			return; // broken AST
		}
		TypeRef typeRef = ts.tau(subExpr, awaitExpression);
		if (typeRef == null) {
			return; // some error (probably broken AST)
		}
		RuleEnvironment G = newRuleEnvironment(awaitExpression);
		BuiltInTypeScope scope = getPredefinedTypes(G).builtInTypeScope;
		TypeRefsFactory einst = TypeRefsFactory.eINSTANCE;

		UnionTypeExpression union1 = einst.createUnionTypeExpression();
		union1.getTypeRefs().add(scope.getVoidTypeRef());
		union1.getTypeRefs().add(scope.getAnyTypeRef());
		UnionTypeExpression union2 = einst.createUnionTypeExpression();
		union2.getTypeRefs().add(scope.getVoidTypeRef());
		union2.getTypeRefs().add(scope.getAnyTypeRef());
		ParameterizedTypeRef promUni = TypeUtils.createPromiseTypeRef(scope, union1, union2);
		boolean stUnions = ts.subtypeSucceeded(G, typeRef, promUni);

		boolean isUndef = typeRef.getDeclaredType() == undefinedType(G);
		boolean isNull = typeRef.getDeclaredType() == nullType(G);
		if (!stUnions) {
			addIssue(awaitExpression, EXP_AWAIT_NON_ASYNC.toIssueItem());
		}
		if (isUndef || isNull) {
			addIssue(awaitExpression, EXP_AWAIT_NON_ASYNC_SPECIAL.toIssueItem(typeRef.getDeclaredType().getName()));
		}
	}

	@Check
	public void checkPropertyAccesssExpression(ParameterizedPropertyAccessExpression propAccessExpression) {
		if (propAccessExpression == null || propAccessExpression.getTarget() == null
				|| propAccessExpression.getProperty() == null) {
			return; // invalid AST
		}

		// check type arguments
		IdentifiableElement prop = propAccessExpression.getProperty();
		// else-case required for TField, TGetter, TSetter
		List<TypeVariable> typeVars = (prop instanceof Type) ? ((Type) prop).getTypeVars() : Collections.emptyList();
		internalCheckTypeArgumentsNodes(typeVars, propAccessExpression.getTypeArgs(), true, prop, propAccessExpression,
				N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression_Property());

		internalCheckTargetSubtypeOfDeclaredThisType(propAccessExpression);

		// check methods aren't assigned to variables or parameters
		internalCheckMethodReference(propAccessExpression);

		// check access to static members of interfaces
		internalCheckAccessToStaticMemberOfInterface(propAccessExpression);
	}

	private void internalCheckTargetSubtypeOfDeclaredThisType(ParameterizedPropertyAccessExpression propAccessExpr) {
		IdentifiableElement prop = propAccessExpr.getProperty();
		if (prop.eIsProxy()) {
			return; // unresolved reference
		}

		TypeRef declThisTypeRef = null;
		if (prop instanceof TMethod) {
			declThisTypeRef = ((TMethod) prop).getDeclaredThisType();
		}
		if (prop instanceof FieldAccessor) {
			declThisTypeRef = ((FieldAccessor) prop).getDeclaredThisType();
		}

		if (declThisTypeRef != null) {
			RuleEnvironment G = newRuleEnvironment(propAccessExpr);
			TypeRef targetTypeRef = ts.type(G, propAccessExpr.getTarget());
			if (!ts.subtypeSucceeded(G, targetTypeRef, declThisTypeRef)) {
				IssueItem issueItem = EXP_ACCESS_INVALID_TYPE_OF_TARGET.toIssueItem(
						validatorMessageHelper.description(prop),
						targetTypeRef.getTypeRefAsString(), declThisTypeRef.getTypeRefAsString());
				addIssue(propAccessExpr, N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression_Property(),
						issueItem);
			}
		}
	}

	/**
	 * Fixes IDE-1048, Method Assignment: Methods can't be assigned to variables.
	 *
	 * <p>
	 * If allowed, that variable could be used later to invoke the function it holds with a wrong this-instance (for
	 * example, with an instance of a superclass while the function, defined in a subclass, requires members private to
	 * that subclass).
	 *
	 * <p>
	 * To be safe, we warn on expressions out of which a method reference might escape and become assigned to a
	 * variable. An example where a method reference is consumed before escaping is <code>typeof method-ref-expr</code>,
	 * for which no warning is raised.
	 *
	 * @apiNote N4JSSpec, 5.2.1
	 *
	 */
	private void internalCheckMethodReference(ParameterizedPropertyAccessExpression propAccessExpression) {
		if (!jsVariantHelper.checkMethodReference(propAccessExpression)) {
			return;
		}
		IdentifiableElement prop = propAccessExpression.getProperty();
		if (!(prop instanceof TMethod)) {
			/*
			 * Other kinds of members (fields, getters; similarly for setters) need not be checked because when used in
			 * a property access they return the underlying value as opposed to a "member-reference".
			 */
			return;
		}
		TMethod method = (TMethod) prop;

		if (!method.isStatic() && "constructor".equals(method.getName())) {
			return; // constructor cannot be detached, cf. GHOLD-224
		}

		EObject enclosing = propAccessExpression.eContainer();
		/*
		 * Unless we find a good reason not to, we'll warn. Each such "good reason" is whitelisted (for example, 'typeof
		 * method-ref-expr'). The list isn't exhaustive, additions can be discussed in a new ticket that links to
		 * IDE-1048.
		 */
		boolean shouldWarn = true;
		if (enclosing instanceof ParameterizedCallExpression) {
			shouldWarn = ((ParameterizedCallExpression) enclosing).getTarget() != propAccessExpression;
		} else if (enclosing instanceof ParameterizedPropertyAccessExpression) {
			shouldWarn = false;
		} else if (enclosing instanceof UnaryExpression) {
			shouldWarn = ((UnaryExpression) enclosing).getOp() != UnaryOperator.TYPEOF;
		} else if (enclosing instanceof EqualityExpression) {
			shouldWarn = false;
		} else if (enclosing instanceof ExpressionStatement) {
			shouldWarn = false;
		} else if (enclosing instanceof TaggedTemplateString) {
			shouldWarn = false; // allow something like: new C().method``;
		}
		if (!shouldWarn) {
			return;
		}
		/*
		 * We're set to warn. However, we've left an (expensive) check for the end, which might overturn the decision so
		 * far. In case we can determine the body of the method being referred to (because it's final) and such body
		 * contains neither 'this' nor 'super' usages, ie in effect such method puts no requirements on the invocation
		 * context, then that method reference is allowed to escape.
		 */
		if (isMethodEffectivelyFinal(method) && method.isLacksThisOrSuperUsage()) {
			return;
		}
		// no more whitelist checks
		String message = EXP_METHOD_REF_UNATTACHED_FROM_RECEIVER.getMessage(method.getName());
		ParameterizedPropertyAccessExpression source = propAccessExpression;
		EReference feature = N4JSPackage.eINSTANCE.getParameterizedPropertyAccessExpression_Property();
		warning(message, source, feature, EXP_METHOD_REF_UNATTACHED_FROM_RECEIVER.name());
	}

	private boolean isMethodEffectivelyFinal(TMethod method) {
		if (method.isFinal() || method.getMemberAccessModifier() == MemberAccessModifier.PRIVATE) {
			return true;
		}
		ContainerType<?> containingType = method.getContainingType();
		// If the containing type is final all its method are assumed final too
		// Attention: containing type may be a UnionUypeExpression and thus the "containingType" method will return null
		if (containingType != null && containingType.isFinal()) {
			return true;
		}
		return false;
	}

	/**
	 * Static members of interfaces may only be accessed directly via the type name of the containing interface. This is
	 * required, because there is no inheritance of static members of interfaces.
	 */
	private void internalCheckAccessToStaticMemberOfInterface(
			ParameterizedPropertyAccessExpression propAccessExpr) {
		IdentifiableElement prop = propAccessExpr.getProperty();
		if (prop instanceof TMember) {
			TMember member = (TMember) prop;
			if (member.isStatic() && member.eContainer() instanceof TInterface) {
				Expression target = propAccessExpr.getTarget();
				IdentifierRef targetIdRef = (target instanceof IdentifierRef) ? (IdentifierRef) target : null;
				boolean isExceptionCase = target instanceof ThisLiteral; // avoid duplicate error messages
				if (targetIdRef != null && targetIdRef.getId() != member.eContainer() && !isExceptionCase) {
					addIssue(propAccessExpr, N4JSPackage.Literals.EXPRESSION_WITH_TARGET__TARGET,
							CLF_INVALID_ACCESS_OF_STATIC_MEMBER_OF_INTERFACE.toIssueItem());
				}
			}
		}
	}

	@Check
	public void checkCallExpression(ParameterizedCallExpression callExpression) {
		if (!jsVariantHelper.checkCallExpression(callExpression)) {
			return; // cf. 13.1
		}
		if (callExpression == null || callExpression.getTarget() == null) {
			return; // invalid AST
		}

		TypeRef typeRef = ts.tau(callExpression.getTarget());
		if (typeRef == null) {
			return; // invalid AST
		}
		if (typeRef instanceof UnknownTypeRef) {
			return; // suppress error message in case of UnknownTypeRef
		}

		// make sure target can be invoked
		RuleEnvironment G = newRuleEnvironment(callExpression);
		List<Callable> callables = tsh.getCallableTypeRefs(G, typeRef);
		int callablesCount = callables.size();
		boolean isCallable = callablesCount == 1;
		if (!isCallable && !(callExpression.getTarget() instanceof SuperLiteral)) {
			if (callablesCount > 1) {
				String callableTypeRefsAsStr = Strings.join(", ",
						map(callables, c -> c.getCallableTypeRef().getTypeRefAsString()));
				IssueItem issueItem = EXP_CALL_CONFLICT_IN_INTERSECTION.toIssueItem(callableTypeRefsAsStr);
				addIssue(callExpression.getTarget(), issueItem);
			} else if (tsh.isClassConstructorFunction(G, typeRef) || isClassifierTypeRefToAbstractClass(G, typeRef)) {
				addIssue(callExpression.getTarget(), EXP_CALL_CLASS_CTOR.toIssueItem());
			} else {
				Type staticType = (typeRef instanceof TypeTypeRef) ? tsh.getStaticType(G, (TypeTypeRef) typeRef) : null;
				if (staticType instanceof TInterface && tsh.getCallSignature(callExpression.eResource(),
						TypeUtils.createTypeRef(staticType)) != null) {
					IssueItem issueItem = EXP_CALL_CONSTRUCT_SIG_OF_INTERFACE_DIRECTLY_USED.toIssueItem("invoke",
							staticType.getName(), "call");
					addIssue(callExpression.getTarget(), issueItem);
				} else {
					IssueItem issueItem = EXP_CALL_NOT_A_FUNCTION.toIssueItem(typeRef.getTypeRefAsString());
					addIssue(callExpression.getTarget(), issueItem);
				}
			}
			return;
		}

		Callable callable = (callablesCount == 1) ? callables.get(0) : null;
		if (callable != null && callable.getSignatureTypeRef().isPresent()) {
			FunctionTypeExprOrRef sigTypeRef = callable.getSignatureTypeRef().get();

			// check type arguments
			internalCheckTypeArgumentsNodes(sigTypeRef.getTypeVars(), callExpression.getTypeArgs(), true,
					sigTypeRef.getDeclaredType(),
					callExpression, N4JSPackage.Literals.EXPRESSION_WITH_TARGET__TARGET);

			// check Calling async functions with missing await
			internalCheckCallingAsyncFunWithoutAwaitingForIt(sigTypeRef, callExpression);
		}
	}

	private boolean isClassifierTypeRefToAbstractClass(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef instanceof TypeTypeRef) {
			Type staticType = tsh.getStaticType(G, (TypeTypeRef) typeRef);
			if (staticType instanceof TClass) {
				return ((TClass) staticType).isAbstract();
			}
		}
		return false;
	}

	/**
	 * If the given function-type is async and not awaited-for, issue a warning unless the return-type (Promise) is made
	 * explicit. "Made explicit" either by:
	 * <ul>
	 * <li>the invocation is the RHS of a variable (declaration or assignment) where the LHS makes explicit the Promise
	 * type.</li>
	 * <li>the invocation is made at the top-level for its side-effects.</li>
	 * <li>the invocation is given as argument to {@code Promise.all()}, {@code Promise.race()}, or
	 * {@code Promise.resolve()}.</li>
	 * </ul>
	 * To clarify, a not-awaited-for call is perfectly valid, after all sometimes only the promise is of interest, but
	 * more commonly an await was forgotten.
	 */
	private void internalCheckCallingAsyncFunWithoutAwaitingForIt(FunctionTypeExprOrRef fteor,
			ParameterizedCallExpression callExpression) {
		RuleEnvironment G = newRuleEnvironment(callExpression);
		if (!N4JSLanguageUtils.isAsync(fteor, G)) {
			return;
		}
		// related: ExpressionExtensions.isPotentialEvalResult()
		EObject container = N4JSASTUtils.skipParenExpressionUpward(callExpression.eContainer());
		boolean isAwaitedFor = (container instanceof AwaitExpression);
		boolean isTopLevel = (container instanceof ExpressionStatement && container.eContainer() instanceof Script);
		if (isAwaitedFor || isTopLevel) {
			return;
		}
		boolean isPromiseExplict = false;
		if (container instanceof VariableDeclaration) {
			VariableDeclaration vd = (VariableDeclaration) container;
			isPromiseExplict = (vd.getExpression() == callExpression) && (vd.getDeclaredTypeRef() != null);
		} else if (container instanceof AssignmentExpression) {
			AssignmentExpression ae = (AssignmentExpression) container;
			isPromiseExplict = (ae.getRhs() == callExpression);
		} else if (isArgumentToPromiseUtilityMethod(callExpression, container, G)) {
			isPromiseExplict = true;
		}
		boolean shouldWarn = !isPromiseExplict;
		if (shouldWarn) {
			addIssue(callExpression.getTarget(), IssueCodes.EXP_MISSNG_AWAIT_FOR_ASYNC_TARGET.toIssueItem());
		}
	}

	/**
	 * Does the given AST-node occur as argument to {@code Promise.all()}, {@code Promise.race()}, or
	 * {@code Promise.resolve()} ?
	 */
	private boolean isArgumentToPromiseUtilityMethod(ParameterizedCallExpression asyncInvocation,
			EObject container, RuleEnvironment G) {
		EObject utilityCall = container;
		boolean isArrayElem = (container instanceof ArrayElement && container.eContainer() instanceof ArrayLiteral);
		if (isArrayElem) {
			utilityCall = container.eContainer().eContainer();
		}
		if (utilityCall instanceof Argument) {
			utilityCall = utilityCall.eContainer();
		}
		// let's see if 'container' stands for 'Promise.{all/race/resolve}(...,asyncInvocation,...)'
		if (utilityCall instanceof ParameterizedCallExpression) {
			ParameterizedCallExpression pce = (ParameterizedCallExpression) utilityCall;
			if (pce.getTarget() instanceof ParameterizedPropertyAccessExpression) {
				// let's see if 'utilityAccess' stands for 'Promise.{all/race/resolve}'
				ParameterizedPropertyAccessExpression utilityAccess = (ParameterizedPropertyAccessExpression) pce
						.getTarget();
				if (isPromiseUtilityPropertyAccess(utilityAccess, G)) {
					boolean isDirectArg = exists(pce.getArguments(), arg -> arg.getExpression() == asyncInvocation);
					if (isDirectArg) {
						return true;
					}
					String name = utilityAccess.getProperty().getName();
					if (isArrayElem && ("all".equals(name) || "race".equals(name))) {
						// let's see if 'callExpression' occurs as arg in
						// 'Promise.{all/race}([...,asyncInvocation,...])'
						boolean argOccursInArray = exists(pce.getArguments(),
								arg -> arg.getExpression() == container.eContainer());
						return argOccursInArray;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Does 'utilityAccess' stand for 'Promise.{all/race/resolve}' ?
	 */
	private boolean isPromiseUtilityPropertyAccess(ParameterizedPropertyAccessExpression utilityAccess,
			RuleEnvironment G) {
		IdentifiableElement invokedUtility = utilityAccess.getProperty();
		if (invokedUtility instanceof TMethod) {
			boolean isStaticUtility = ((TMethod) invokedUtility).isStatic();
			boolean hasNameOfInterest = (Set.of("all", "race", "resolve").contains(invokedUtility.getName()));
			if (isStaticUtility && hasNameOfInterest) {
				// let's see if utilityAccess.target denotes Promise
				BuiltInTypeScope tscope = RuleEnvironmentExtensions.getPredefinedTypes(G).builtInTypeScope;
				TypeRef tr = ts.type(G, utilityAccess.getTarget());
				if (tr instanceof TypeTypeRef) {
					TypeArgument str = ((TypeTypeRef) tr).getTypeArg();
					boolean isReceiverPromise = (str instanceof TypeRef) ? TypeUtils.isPromise((TypeRef) str, tscope)
							: false;
					return isReceiverPromise;
				}
			}
		}
		return false;
	}

	@Check
	public void checkNew(NewExpression newExpression) {
		if (!jsVariantHelper.requireCheckNewExpression(newExpression)) {
			return; // cf. 13.1
		}
		if (newExpression == null || newExpression.getCallee() == null) {
			return; // invalid AST
		}
		Expression callee = newExpression.getCallee();
		TypeRef typeRef = ts.tau(callee);
		if (typeRef == null) {
			return; // invalid AST
		}
		if (typeRef instanceof UnknownTypeRef) {
			return; // suppress error message in case of UnknownTypeRef
		}

		RuleEnvironment G = newRuleEnvironment(newExpression);
		// not even a TypeTypeRef?
		if (!(typeRef instanceof TypeTypeRef)) {
			if (typeRef.isDynamic()) {
				TypeTypeRef constrOfWildcard = TypeUtils.createTypeTypeRef(TypeUtils.createWildcard(), true);
				if (ts.subtypeSucceeded(G, typeRef, constrOfWildcard)
						|| ts.subtypeSucceeded(G, constrOfWildcard, typeRef)) {
					// don't bother with dynamic types that are either a sub- or supertype of constructor{?}
					return;
				}
			}
			List<Newable> newables = tsh.getNewableTypeRefs(G, typeRef, newExpression, false);
			if (newables.size() > 1) {
				String newableTypeRefsAsStr = Strings.join(", ",
						map(newables, n -> n.getNewableTypeRef().getTypeRefAsString()));
				addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(),
						EXP_NEW_CONFLICT_IN_INTERSECTION.toIssueItem(newableTypeRefsAsStr));
				return;
			} else if (newables.size() == 1) {
				// special success case; but perform some further checks
				Newable newable = newables.get(0);
				TypeRef newableTypeRef = newable.getNewableTypeRef();
				TMethod ctorOrConstructSig = newable.getCtorOrConstructSig();
				internalCheckConstructSignatureInvocation(newExpression, newableTypeRef, ctorOrConstructSig);
				return;
			}
			issueNotACtor(typeRef, newExpression);
			return;
		}
		// at least we have a TypeTypeRef, but there are still many cases in which
		// 'new' is not allowed --> check for those error cases now, showing more specific error messages ...
		TypeTypeRef classifierTypeRef = (TypeTypeRef) typeRef;
		TypeArgument typeArg = classifierTypeRef.getTypeArg();
		Type staticType = changeToCovariantUpperBoundIfTypeVar(tsh.getStaticType(G, classifierTypeRef));
		if (staticType != null && staticType.eIsProxy()) {
			return;
		}

		ParameterizedTypeRef ptr = classifierTypeRef.getOriginalAliasTypeRef();
		boolean isHollow = ptr != null && N4JSLanguageUtils.isHollowElement(ptr.getDeclaredType());
		if (isHollow) {
			// errors due to access to hollow elements is done in VeeScopeValidator
			return;
		}

		boolean isCtor = classifierTypeRef.isConstructorRef();
		boolean isDirectRef = callee instanceof IdentifierRef && ((IdentifierRef) callee).getId() == staticType;
		boolean isConcreteOrCovariant = !(typeArg instanceof Wildcard || typeArg instanceof ExistentialTypeRef
				|| typeArg instanceof ThisTypeRef)
				|| (staticType instanceof TClassifier
						&& N4JSLanguageUtils.hasCovariantConstructor((TClassifier) staticType, declMergingHelper));
		if (staticType == symbolObjectType(G)) {
			// error case #1: new Symbol()
			addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(),
					BIT_SYMBOL_NOT_A_CTOR.toIssueItem());
			return;
		} else if (!isCtor && staticType instanceof TInterface && isDirectRef) {
			// error case #2: trying to instantiate an interface
			TInterface tInterface = (TInterface) staticType;
			if (tInterface.getConstructSignature() != null) {
				// special case: trying to directly instantiate an interface with a construct signature
				IssueItem issueItem = EXP_CALL_CONSTRUCT_SIG_OF_INTERFACE_DIRECTLY_USED.toIssueItem("instantiate",
						staticType.getName(), "construct");
				addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(), issueItem);
				return;
			}
			IssueItem issueItem = EXP_NEW_CANNOT_INSTANTIATE.toIssueItem(keywordProvider.keyword(staticType),
					staticType.getName());
			addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(), issueItem);
			return;
		} else if (!isCtor && staticType instanceof TClass && ((TClass) staticType).isAbstract() && isDirectRef) {
			// error case #3: trying to instantiate an abstract class
			IssueItem issueItem = EXP_NEW_CANNOT_INSTANTIATE.toIssueItem("abstract class", staticType.getName());
			addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(), issueItem);
			return;
		} else if (isCtor && !isConcreteOrCovariant && staticType instanceof TClassifier) {
			// error case #4: trying to instantiate "constructor{? extends C}", with C not having @CovariantConstructor
			IssueItem issueItem = EXP_NEW_WILDCARD_NO_COVARIANT_CTOR.toIssueItem(typeArg.getTypeRefAsString(),
					staticType.getTypeAsString());
			addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(), issueItem);
			return;
		} else if (staticType instanceof TEnum) {
			// error case #5: trying to instantiate an enum
			IssueItem issueItem = EXP_NEW_CANNOT_INSTANTIATE.toIssueItem("enum", staticType.getName());
			addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(), issueItem);
			return;
		} else if (staticType instanceof TypeVariable) {
			// error case #6: trying to instantiate a type variable
			IssueItem issueItem = EXP_NEW_CANNOT_INSTANTIATE.toIssueItem("type variable", staticType.getName());
			addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(), issueItem);
			return;
		} else if (staticType == null || !isCtor || !isConcreteOrCovariant) {
			// remaining cases
			String name = typeRef.getTypeRefAsString();
			IssueItem issueItem = EXP_NEW_WILDCARD_OR_TYPEVAR.toIssueItem(name);
			addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(), issueItem);
			return;
		}

		// success case; but perform some further checks
		internalCheckTypeArgumentsNodes(staticType.getTypeVars(), newExpression.getTypeArgs(), false, staticType,
				newExpression,
				N4JSPackage.eINSTANCE.getNewExpression_Callee());

		if (staticType instanceof TClassifier) {
			internalCheckNewParameters(newExpression, (TClassifier) staticType);
		}
	}

	private void internalCheckConstructSignatureInvocation(NewExpression newExpression, TypeRef calleeTypeRef,
			TMethod constructSig) {
		if (holdsConstructSignatureIsAccessible(newExpression, calleeTypeRef, constructSig)) {

			internalCheckTypeArgumentsNodes(constructSig.getTypeVars(), newExpression.getTypeArgs(), false,
					constructSig, newExpression,
					N4JSPackage.eINSTANCE.getNewExpression_Callee());

			internalCheckNewParameters(newExpression, constructSig);
		}
	}

	private boolean holdsConstructSignatureIsAccessible(NewExpression newExpression, TypeRef calleeTypeRef,
			TMethod constructSig) {
		EObject container = constructSig == null ? null : constructSig.eContainer();
		if (container instanceof TInterface) { // avoid checking accessibility of construct signatures in
												// StructuralTypeRefs/TStructuralTypes (they're always public)
			if (!memberVisibilityChecker.isVisible(newExpression, calleeTypeRef, constructSig).visibility) {
				IssueItem issueItem = VIS_NEW_CANNOT_INSTANTIATE_INVISIBLE_CONSTRUCTOR
						.toIssueItem("construct signature", ((TInterface) container).getName());
				addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(), issueItem);
				return false;
			}
		}
		return true;
	}

	private Type changeToCovariantUpperBoundIfTypeVar(Type type) {
		if (type instanceof TypeVariable) {
			TypeRef ub = ((TypeVariable) type).getDeclaredUpperBound();
			if (ub instanceof ParameterizedTypeRef) {
				Type declType = ub.getDeclaredType();
				if (declType instanceof TClassifier) {
					// but only if declType has a covariant constructor:
					if (N4JSLanguageUtils.hasCovariantConstructor((TClassifier) declType, declMergingHelper)) {
						return declType;
					}
				}
			}
		}
		return type;
	}

	/** Helper to issue the error case of having a new-expression on a non-constructor element */
	private void issueNotACtor(TypeRef typeRef, NewExpression newExpression) {
		IssueItem issueItem = EXP_NEW_NOT_A_CTOR.toIssueItem(typeRef.getTypeRefAsString());
		addIssue(newExpression, N4JSPackage.eINSTANCE.getNewExpression_Callee(), issueItem);
	}

	/**
	 * Checks instanceof in combination with structural typing, other checks see
	 * org.eclipse.n4js.xsemantics.N4JSTypeSystem.expectedTypeInRelationalExpression
	 */
	@Check
	public void checkRelationalExpression(RelationalExpression relationalExpression) {
		if (relationalExpression.getRhs() != null && relationalExpression.getOp() == RelationalOperator.INSTANCEOF) {
			TypeRef typeRef = ts.tau(relationalExpression.getRhs());
			RuleEnvironment G = newRuleEnvironment(relationalExpression);
			if (typeRef instanceof TypeTypeRef) {
				Type staticType = tsh.getStaticType(G, (TypeTypeRef) typeRef);
				if (staticType instanceof TN4Classifier) {
					if (staticType instanceof TInterface && N4Scheme.isFromResourceWithN4Scheme(staticType)) {
						IssueItem issueItem = TYS_INSTANCEOF_NOT_SUPPORTED_FOR_BUILT_IN_INTERFACES
								.toIssueItem(staticType.getName());
						addIssue(relationalExpression, N4JSPackage.eINSTANCE.getRelationalExpression_Rhs(), issueItem);
					}
				}
			}

			Expression rhs = relationalExpression.getRhs();
			if (rhs instanceof UnaryExpression) {
				UnaryExpression ue = (UnaryExpression) rhs;
				if (ue.getOp().equals(UnaryOperator.INV)) {
					Expression innerExpression = ue.getExpression();
					TypeRef rhsTypeRef = ts.tau((innerExpression instanceof UnaryExpression)
							? ((UnaryExpression) innerExpression).getExpression()
							: innerExpression);

					if (!RuleEnvironmentExtensions.isNumeric(G, rhsTypeRef)) {
						addIssue(relationalExpression, N4JSPackage.eINSTANCE.getRelationalExpression_Rhs(),
								TYS_INSTANCEOF_NOT_SUPPORTED_FOR_USE_SITE_STRUCTURAL.toIssueItem());
					}
				}
			}
		}
	}

	/**
	 * IDE-737: properties in postfixExpressions need both of getter/setter. Getter is bound to the property-field in
	 * PropertyAccessExpression, the existence of a setter needs to be validated.
	 */
	@Check
	public boolean checkPostfixExpression(PostfixExpression postfixExpression) {
		Expression expression = postfixExpression.getExpression();
		return holdsWritabelePropertyAccess(expression)
				&& holdsLefthandsideNotConst(expression)
				&& holdsWritableIdentifier(expression);
	}

	/**
	 * IDE-731 / IDE-768 unary expressions of type ++ or -- need both of getter/setter. Cf. Constraint 69
	 */
	@Check
	public boolean checkUnaryExpressionWithWriteAccess(UnaryExpression unaryExpression) {
		if (UnaryOperator.DEC == unaryExpression.getOp() || UnaryOperator.INC == unaryExpression.getOp()) {
			return holdsWritabelePropertyAccess(unaryExpression.getExpression())
					&& holdsLefthandsideNotConst(unaryExpression.getExpression())
					&& holdsWritableIdentifier(unaryExpression.getExpression());
		}
		return false;
	}

	private boolean holdsWritabelePropertyAccess(Expression expression) {
		if (expression instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) expression;
			IdentifiableElement property = ppae.getProperty();
			if (property instanceof TGetter) {
				TGetter getter = (TGetter) property;
				// access through getter --> a matching setter is required:
				TypeRef propertyTargetType = ts.tau(ppae.getTarget());
				Type declaredType = propertyTargetType == null ? null : propertyTargetType.getDeclaredType();
				if (declaredType instanceof TClassifier) {
					MemberList<TMember> members = containerTypesHelper.fromContext(ppae)
							.members((TClassifier) declaredType);
					boolean setterExists = exists(filter(members, TSetter.class),
							m -> Objects.equals(m.getName(), getter.getName()));
					if (!setterExists) {
						addIssue(ppae, N4JSPackage.Literals.PARAMETERIZED_PROPERTY_ACCESS_EXPRESSION__PROPERTY,
								IssueCodes.TYS_PROPERTY_HAS_NO_SETTER.toIssueItem(getter.getName()));
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Ensures that imported elements get not reassigned any value.
	 *
	 * @returns true if validation hold, false if some issue was generated.
	 */
	private boolean holdsWritableIdentifier(Expression expression) {
		if (expression instanceof IdentifierRef) {
			IdentifiableElement id = ((IdentifierRef) expression).getId();
			if (id instanceof TExportableElement) {
				/* includes TClass, TVariable */
				TModule module = EcoreUtil2.getContainerOfType(expression, Script.class).getModule();
				if (id.getContainingModule() != module) {
					// imported variable, class, etc.
					addIssue(expression,
							IMP_IMPORTED_ELEMENT_READ_ONLY.toIssueItem(((IdentifierRef) expression).getIdAsText()));
					return false;
				}
			}
		} else if (expression instanceof ParenExpression) {
			// resolve parent-expressions wrapping simple identifiers:
			return holdsWritableIdentifier(((ParenExpression) expression).getExpression());
		} else if (expression instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) expression;
			// guard against broken models:
			if (ppae.getProperty() != null && !ppae.getProperty().eIsProxy()) {
				var target = N4JSASTUtils.skipParenExpressionDownward(ppae.getTarget());
				if (target instanceof IdentifierRef) {
					IdentifierRef ir = (IdentifierRef) target;
					IdentifiableElement id = ir.getId();
					// handle namespace imports:
					if (id instanceof ModuleNamespaceVirtualType) {
						ModuleNamespaceVirtualType mnvt = (ModuleNamespaceVirtualType) id;
						if (mnvt.getModule() != EcoreUtil2.getContainerOfType(ppae, Script.class).getModule()) {
							// naive approach for reporting : "target.idAsText+"."+expression.property.name;" results
							// in revealing the name of the default-exported element, but the user can only see
							// 'default' in the validated file
							// so we pick the actual written expression for the error-message generation from the AST:
							String importedElmentText = NodeModelUtils.getTokenText(
									NodeModelUtils.findActualNodeFor(ppae));

							addIssue(ppae, IMP_IMPORTED_ELEMENT_READ_ONLY.toIssueItem(importedElmentText));
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	@Check
	public void checkCallExpressionParameters(ParameterizedCallExpression callExpression) {
		if (!jsVariantHelper.checkCallExpression(callExpression)) {
			return; // cf. 13.1
		}

		Expression target = callExpression.getTarget();
		if (target != null) {
			TypeRef targetTypeRef = ts.tau(target); // no context, we only need the number of fpars
			RuleEnvironment G = newRuleEnvironment(callExpression);
			Callable callable = tsh.getCallableTypeRef(G, targetTypeRef);
			if (callable != null && callable.getSignatureTypeRef().isPresent()) {
				FunctionTypeExprOrRef signatureTypeRef = callable.getSignatureTypeRef().get();

				// obtain fpars from invoked function/method
				List<TFormalParameter> fpars = new ArrayList<>(signatureTypeRef.getFpars());

				// special case: invoking a promisified function
				// note: being very liberal in next lines to avoid duplicate error messages
				EObject parent = callExpression.eContainer();
				boolean isPromisified = (parent instanceof AwaitExpression &&
						promisifyHelper.isAutoPromisify((AwaitExpression) parent)) ||
						parent instanceof PromisifyExpression;
				if (isPromisified) {
					fpars.remove(fpars.size() - 1);
				}

				// check for correct number of arguments
				internalCheckNumberOfArguments(fpars, callExpression.getArguments(), callExpression);
			}
		}
	}

	private void internalCheckNewParameters(NewExpression newExpression, TClassifier staticType) {
		TMethod maybeConstructor = containerTypesHelper.fromContext(newExpression).findConstructor(staticType);
		if (maybeConstructor != null) {
			internalCheckNewParameters(newExpression, maybeConstructor);
		}
	}

	private void internalCheckNewParameters(NewExpression newExpression, TFunction ctor) {
		internalCheckNumberOfArguments(ctor.getFpars(), newExpression.getArguments(), newExpression);
	}

	private void internalCheckNumberOfArguments(List<TFormalParameter> fpars, List<Argument> args,
			Expression expr) {
		int cmp = compareNumberOfArgsWithNumberOfFPars(fpars, args);
		if (cmp < 0) { // too few
			addIssue(expr, EXP_NUM_OF_ARGS_TOO_FEW.toIssueItem(fpars.size(), args.size()));
		} else if (cmp > 0) { // too many
			addIssue(expr, EXP_NUM_OF_ARGS_TOO_MANY.toIssueItem(fpars.size(), args.size()));
		}
	}

	/**
	 * Compares number of arguments with number of formal parameter, taking optional and variadic parameters into
	 * consideration.
	 *
	 * @return -1 if to few arguments are found, 1 if too many arguments are found, 0 if number of arguments is
	 *         according to formal parameter list
	 */
	private int compareNumberOfArgsWithNumberOfFPars(List<TFormalParameter> fpars, List<Argument> args) {
		int argCount = args.size();
		int fparCount = fpars.size();

		if (fpars.size() == args.size()) {
			return 0;
		}
		if (argCount > fparCount) {
			if (fparCount == 0) {
				return 1; // too many
			}
			if (last(fpars).isVariadic()) {
				return 0;
			}
			return 1; // too many
		}

		// argCount < fparCount (and fparCount>0)
		if (fpars.get(argCount).isVariadicOrOptional()) {
			return 0; // the missing parameters are optional or variadic
		}
		return -1; // too few
	}

	/**
	 * IDE-731 / IDE-770 Cf. 6.1.14. Additive Expression, Constraint 73
	 */
	@Check
	public void checkAdditiveExpressionForNonADDs(AdditiveExpression ae) {
		if (ae.getOp() == AdditiveOperator.SUB) {
			doCheckMathOperandTypes(ae.getLhs(), ae.getRhs());
		} else {
			doCheckMathOperandTypeSymbol(ae.getLhs(), ae.getRhs());
		}
	}

	/**
	 * Note: Division by 0 may lead to infinity or NaN, depending on the value of the rhs. I.e. 0/0=NaN, but
	 * 1/0=Infinity. So we cannot infer from the type the result in these cases.
	 */
	@Check
	public void checkMultiplicativeExpression(MultiplicativeExpression me) {
		doCheckMathOperandTypes(me.getLhs(), me.getRhs());
	}

	@Check
	public void checkShiftExpression(ShiftExpression se) {
		doCheckMathOperandTypes(se.getLhs(), se.getRhs());
	}

	private void doCheckMathOperandTypes(Expression lhs, Expression rhs) {
		if (lhs == null || rhs == null)
			return;
		TypeRef tlhs = ts.tau(lhs);
		if (tlhs == null)
			return;
		TypeRef trhs = ts.tau(rhs);
		if (trhs == null)
			return;

		BuiltInTypeScope bits = BuiltInTypeScope.get(lhs.eResource().getResourceSet());

		if (tlhs.getDeclaredType() == bits.getUndefinedType()) {
			issueMathResultIsConstant("of type undefined", "NaN", lhs);
		}
		if (trhs.getDeclaredType() == bits.getUndefinedType()) {
			issueMathResultIsConstant("of type undefined", "NaN", rhs);
		}

		if (tlhs.getDeclaredType() == bits.getNullType()) {
			issueMathOperandIsConstant("null", "0", lhs);
		}
		if (trhs.getDeclaredType() == bits.getNullType()) {
			issueMathOperandIsConstant("null", "0", rhs);
		}
		if (tlhs.getDeclaredType() == bits.getSymbolType()) {
			issueMathOperandTypeNotPermitted("symbol", lhs);
		}
		if (trhs.getDeclaredType() == bits.getSymbolType()) {
			issueMathOperandTypeNotPermitted("symbol", rhs);
		}
	}

	private void doCheckMathOperandTypeSymbol(Expression lhs, Expression rhs) {
		if (lhs == null || rhs == null)
			return;
		TypeRef tlhs = ts.tau(lhs);
		if (tlhs == null)
			return;
		TypeRef trhs = ts.tau(rhs);
		if (trhs == null)
			return;

		BuiltInTypeScope bits = BuiltInTypeScope.get(lhs.eResource().getResourceSet());
		if (tlhs.getDeclaredType() == bits.getSymbolType()) {
			issueMathOperandTypeNotPermitted("symbol", lhs);
		}
		if (trhs.getDeclaredType() == bits.getSymbolType()) {
			issueMathOperandTypeNotPermitted("symbol", rhs);
		}
	}

	private void issueMathResultIsConstant(String operand, String constResult, Expression location) {
		addIssue(location, EXP_MATH_OPERATION_RESULT_IS_CONSTANT.toIssueItem(operand, constResult));
	}

	private void issueMathOperandIsConstant(String operandType, String constValue, Expression location) {
		addIssue(location, EXP_MATH_OPERAND_IS_CONSTANT.toIssueItem(operandType, constValue));
	}

	private void issueMathOperandTypeNotPermitted(String operandType, Expression location) {
		addIssue(location, EXP_MATH_TYPE_NOT_PERMITTED.toIssueItem(operandType));
	}

	/**
	 * IDE-731 / IDE-773 Cf. 6.1.17. Equality Expression
	 *
	 * In N4JS mode, a warning is created, if for a given expression lhs(’==’|’!=’) rhs, neither Γ |- upper(lhs) <:
	 * upper(rhs) nor Γ |- upper(rhs) <: upper(lhs), as the result is constant in these cases.
	 */
	@Check
	public void checkEqualityExpressionForConstantValues(EqualityExpression ee) {

		if (ee.getOp() == EqualityOperator.SAME || ee.getOp() == EqualityOperator.NSAME) {

			RuleEnvironment G = newRuleEnvironment(ee);

			TypeRef tlhs = ts.type(G, ee.getLhs());
			TypeRef trhs = ts.type(G, ee.getRhs());

			// we are only interested in upper bound here, cf. IDEBUG-260
			tlhs = ts.upperBoundWithReopen(G, tlhs);
			trhs = ts.upperBoundWithReopen(G, trhs);

			boolean leftSubOfRight = ts.subtypeSucceeded(G, tlhs, trhs);
			boolean rightSubOfLeft = ts.subtypeSucceeded(G, trhs, tlhs);

			Set<Type> tdLhs = computeDeclaredTypeS(tlhs);
			Set<Type> tdRhs = computeDeclaredTypeS(trhs);

			// DEBUGPrint(tdLhs, leftSubOfRight, rightSubOfLeft, tdRhs, tlhs, trhs, ee)
			// Cases for comparison:
			// * both sides with interface/roles --> out, nothing to deduce
			// * one side with interfaces/roles --> check Primitives, Enum, Function,
			// * no side with interfaces --> check for subtype-relationship otherwise issue warning.
			boolean leftROI = hasInterface(tdLhs);
			boolean rightROI = hasInterface(tdRhs);
			if (leftROI && rightROI) {
				// if one side is interface we cannot deduce any constant value.
				return;
			}
			if (leftROI || rightROI) {
				// one side is interface,
				// so only types with no possibility to subclass can be checked.
				// subclasses are allowed for
				if (leftROI) {
					// look at right side:
					if (isExtendable(tdRhs)) {
						// no statement possible.
						return;
					}
				} else {
					// look at left side:
					if (isExtendable(tdLhs)) {
						// no statement possible.
						return;
					}
				}
			}

			if (!(leftSubOfRight || rightSubOfLeft)) {
				// no subtype-relationship found, issue warning:
				addIssue(ee, EXP_WARN_CONSTANT_EQUALITY_TEST.toIssueItem(warningNameOf(tlhs), warningNameOf(trhs),
						ee.getOp() == EqualityOperator.NSAME));
			}
		}

	}

	private boolean isExtendable(Set<Type> types) {
		return exists(types, t -> isExtendable(t));
	}

	private boolean isExtendable(Type t) {
		return !(isNotExtendable(t));
	}

	private boolean isNotExtendable(Type t) {
		return t == null || t instanceof PrimitiveType || t instanceof TEnum || t instanceof BuiltInType
				|| t instanceof TFunction;
	}

	private boolean hasInterface(Set<Type> types) {
		return exists(types, t -> hasInterface(t));
	}

	/**
	 * true if type is a subclass of TInterface
	 */
	private boolean hasInterface(Type type) {
		return type instanceof TInterface;
	}

	private String warningNameOf(TypeRef typeRef) {
		if (typeRef instanceof TypeTypeRef || typeRef instanceof LiteralTypeRef) {
			return typeRef.getTypeRefAsString();
		} else {
			Set<Type> typeS = computeDeclaredTypeS(typeRef);
			return warningNameOf(typeS);
		}
	}

	private String warningNameOf(Set<Type> tset) {
		if (tset.size() == 1) {
			return warningNameOf(tset.iterator().next());
		}
		return "{ " + Strings.join(", ", t -> warningNameOf(t), tset) + " }";
	}

	private String warningNameOf(Type t) {
		String repr;
		if (t == null) {
			repr = "<type null>";
		} else {
			if (t.getName() == null) {
				repr = t.toString();
			} else {
				repr = t.getName();
			}
		}

		if (t instanceof TStructuralType) {
			return "'structural type'";
		}
		if (t instanceof TFunction) {
			return "function " + repr;
		}
		return repr;
	}

	private Set<Type> computeDeclaredTypeS(TypeRef tref) {

		if (tref instanceof ComposedTypeRef) {

			// TODO beware of recursion !
			Set<Type> retSet = new TreeSet<>((a, b) -> {
				if (a == null)
					return 1;
				else if (b == null)
					return -1;
				else {
					return Comparator.nullsLast(Comparator.<String> naturalOrder()).compare(a.getTypeAsString(),
							b.getTypeAsString());
				}
			});
			for (TypeRef it : ((ComposedTypeRef) tref).getTypeRefs()) {
				retSet.addAll(computeDeclaredTypeS(it));
			}
			return retSet;
		}
		if (tref instanceof BoundThisTypeRef) {
			return Collections.singleton(((BoundThisTypeRef) tref).getActualThisTypeRef().getDeclaredType());
		} else {
			return Collections.singleton(tref.getDeclaredType());
		}
	}

	/**
	 * Checking Constraints 79: <br>
	 * Constraints 79 (Binary Logical Expression Constraints): For a given binary logical expression e with e.lhs.type :
	 * L and e.rhs.type : R the following constraints must hold:
	 *
	 * <li>In N4JS mode L must not be undefined or null. IDE-775
	 */
	@Check
	public void checkBinaryLogicalExpression(BinaryLogicalExpression e) {
		if (e == null || e.getLhs() == null || e.getRhs() == null) {
			// wrong parsed, handled in org.eclipse.n4js.validation.ASTStructureValidator
			return;
		}

		RuleEnvironment G = newRuleEnvironment(e);

		doCheckForbiddenType(e.getLhs(), nullType(G), "null");

		doCheckForbiddenType(e.getLhs(), undefinedType(G), "undefined");
	}

	private void doCheckForbiddenType(Expression e, Type forbidden, String typeName) {
		if (forbidden != null) {
			Type theType = ts.tau(e).getDeclaredType();
			if (theType == forbidden) {
				addIssue(e, EXP_FORBIDDEN_TYPE_IN_BINARY_LOGICAL_EXPRESSION.toIssueItem(typeName));
			}
		}
	}

	/**
	 * Checking Constraint 80: <br>
	 *
	 * In N4JS mode a warning will be issued if e.expression evaluates to a constant value, that is e.expression in {
	 * false, true, null, undefined} or C in {void, undefined}
	 *
	 * IDE-776
	 */
	@Check
	public void checkConditionalExpression(ConditionalExpression e) {
		Expression expressionToCheck = e.getExpression();

		// wrong parsed
		if (expressionToCheck == null) {
			return;
		}

		RuleEnvironment G = newRuleEnvironment(e);

		Type declaredT = ts.tau(expressionToCheck).getDeclaredType();

		ConstBoolean cboolValue = ConstBoolean.NotPrecomputable;

		if (declaredT == nullType(G) || declaredT == voidType(G) || declaredT == undefinedType(G)) {

			// False-Way.
			cboolValue = ConstBoolean.CFalse;

		} else {
			cboolValue = evalConstantBooleanExpression(expressionToCheck);
		}

		// if not Precomputed back out:
		if (cboolValue == ConstBoolean.NotPrecomputable) {
			return;
		}

		String msg1 = "?!?";
		String msg2 = "?!?";
		if (cboolValue == ConstBoolean.CTrue) {
			msg1 = "true";
			msg2 = "left-hand";
		} else {
			msg1 = "false";
			msg2 = "right-hand";
		}
		IssueItem issueItem = EXP_WARN_DISPENSABLE_CONDITIONAL_EXPRESSION.toIssueItem(
				NodeModelUtils.findActualNodeFor(expressionToCheck).getText().trim(),
				msg1,
				msg2);
		addIssue(expressionToCheck, issueItem);

	}

	/**
	 * Checks the Expression to be always constant in evaluation with ECMA-Script 5.1, 2011, §9.2, p.42
	 * ToBooleanValue(e)
	 */
	private ConstBoolean evalConstantBooleanExpression(Expression e) {
		if (e instanceof BooleanLiteral) {
			if (((BooleanLiteral) e).isTrue()) {
				return ConstBoolean.CTrue;
			} else {
				return ConstBoolean.CFalse;
			}
		} else if (e instanceof NumericLiteral) {

			// false: +0, -0, or NaN;
			BigDecimal v = ((NumericLiteral) e).getValue();
			if (v.intValue() == 0) {
				return ConstBoolean.CFalse;
			} else {
				return ConstBoolean.CTrue;
			}
		} else if (e instanceof IdentifierRef) {
			IdentifierRef ir = (IdentifierRef) e;
			if (ir.getId() != null && "NaN".equals(ir.getId().getName())) {

				// It's a Not a Number:
				return ConstBoolean.CFalse;
			}
		} else if (e instanceof StringLiteral) {
			if (((StringLiteral) e).getValue().isEmpty()) {
				return ConstBoolean.CFalse;
			} else {
				return ConstBoolean.CTrue;
			}
		} else if (e instanceof ObjectLiteral) {

			// Object is always true:
			return ConstBoolean.CTrue;
		}

		// some simple simplification could be carried out like Parenthesis, BinaryLogicalExpression, ..... is it worth
		// the hassle?
		// Nothing known?
		return ConstBoolean.NotPrecomputable;
	}

	/**
	 * 5.5.1. Type Cast, Constraints 61 updated with IDE-928 (IDEBUG-56): Casting to TypeVars
	 */
	@Check
	public void checkCastExpression(CastExpression castExpression) {
		// avoid validating a broken AST
		if (castExpression.getExpression() == null
				|| castExpression.getTargetTypeRefNode() == null
				|| castExpression.getTargetTypeRefNode().getTypeRefInAST() == null) {
			return;
		}

		RuleEnvironment G = newRuleEnvironment(castExpression);

		TypeRef S = ts.upperBoundWithReopen(G, ts.tau(castExpression.getExpression(), castExpression));
		TypeRef T = ts.upperBoundWithReopen(G, castExpression.getTargetTypeRef());

		// avoid consequential errors
		if (S == null || T == null || T instanceof UnknownTypeRef || S instanceof UnknownTypeRef)
			return;

		// casts from dynamically typed variables introduce more type information. Hence do not show warning.
		if (BooleanExtensions.xor(S.isDynamic(), T.isDynamic())) {
			return;
		}

		if (ts.subtypeSucceeded(G, S, T)) { // Constraint 81.2 (Cast Validation At Compile-Time): 1
			IssueItem issueItem = EXP_CAST_UNNECESSARY.toIssueItem(S.getTypeRefAsString(), T.getTypeRefAsString());
			addIssue(castExpression, issueItem);
		} else {
			boolean specialChecks = (T.getDeclaredType() instanceof ContainerType<?>)
					|| (T.getDeclaredType() instanceof PrimitiveType)
					|| (T.getDeclaredType() instanceof TEnum)
					|| (T.getDeclaredType() instanceof TypeVariable)
					|| (T instanceof TypeTypeRef)
					|| (T instanceof UnionTypeExpression)
					|| (T instanceof FunctionTypeExpression)
					|| (T instanceof IntersectionTypeExpression)
					|| (T instanceof LiteralTypeRef);

			if (specialChecks) {
				internalCheckCastExpression(G, S, T, castExpression, true, false);
			} else {
				// Constraint 78 (Cast Validation At Compile-Time): 2
				addIssue(castExpression, EXP_CAST_INVALID_TARGET.toIssueItem());
			}
		}
	}

	private boolean isLiteralOfNumberStringBasedEnum(TypeRef candidateTypeRef, TEnum enumType) {
		EnumKind enumKind = N4JSLanguageUtils.getEnumKind(enumType);
		if (enumKind == EnumKind.NumberBased) {
			if (candidateTypeRef instanceof NumericLiteralTypeRef) {
				BigDecimal S_value = ((NumericLiteralTypeRef) candidateTypeRef).getValue();
				if (S_value != null && exists(filterNull(map(enumType.getLiterals(), l -> l.getValueNumber())),
						vn -> vn.compareTo(S_value) == 0)) {
					return true;
				}
			}
		} else if (enumKind == EnumKind.StringBased) {
			if (candidateTypeRef instanceof StringLiteralTypeRef) {
				String S_value = ((StringLiteralTypeRef) candidateTypeRef).getValue();
				if (S_value != null && exists(filterNull(map(enumType.getLiterals(), l -> l.getValueString())),
						it -> Objects.equals(it, S_value))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 5.5.1. Type Cast, Constraints 78 (Cast Validation At Compile-Time): 3 and 4
	 */
	private boolean internalCheckCastExpression(RuleEnvironment G, TypeRef S, TypeRef T,
			CastExpression castExpression, boolean addIssues, boolean actualSourceTypeIsCPOE) {

		if (T instanceof UnionTypeExpression) {
			UnionTypeExpression ute = (UnionTypeExpression) T;
			if (!exists(ute.getTypeRefs(),
					tr -> internalCheckCastExpression(G, S, tr, castExpression, false, actualSourceTypeIsCPOE))) {
				if (addIssues) {
					addIssue(castExpression,
							EXP_CAST_FAILED.toIssueItem(S.getTypeRefAsString(), T.getTypeRefAsString()));
				}
				return false;
			}
		} else if (T instanceof IntersectionTypeExpression) {
			IntersectionTypeExpression ite = (IntersectionTypeExpression) T;
			if (!forall(ite.getTypeRefs(),
					tr -> internalCheckCastExpression(G, S, tr, castExpression, false, actualSourceTypeIsCPOE))) {
				if (addIssues) {
					addIssue(castExpression,
							EXP_CAST_FAILED.toIssueItem(S.getTypeRefAsString(), T.getTypeRefAsString()));
				}
				return false;
			}
		} else if (S instanceof ComposedTypeRef) { // Constraint 78 (Cast Validation At Compile-Time): 5
			ComposedTypeRef ctr = (ComposedTypeRef) S;
			if (!exists(ctr.getTypeRefs(), tr -> internalCheckCastExpression(G, tr, T, castExpression, false,
					actualSourceTypeIsCPOE || (ctr instanceof IntersectionTypeExpression
							&& exists(ctr.getTypeRefs(), tr2 -> isCPOE(G, tr2)))))
					&& !exists(ctr.getTypeRefs(), tr -> ts.subtypeSucceeded(G, tr, T)) // one type in composed is a
																						// subtype of target
			) {
				if (addIssues) {
					addIssue(castExpression,
							EXP_CAST_FAILED.toIssueItem(S.getTypeRefAsString(), T.getTypeRefAsString()));
				}
				return false;
			}
		} else if ((T.getDeclaredType() instanceof TEnum
				&& isLiteralOfNumberStringBasedEnum(S, (TEnum) T.getDeclaredType()))
				|| (S.getDeclaredType() instanceof TEnum
						&& isLiteralOfNumberStringBasedEnum(T, (TEnum) S.getDeclaredType()))) {
			// allow casting numbers/strings to @NumberBased/@StringBased enums
			return true; // cast is ok
		} else if (castExpression.getExpression() instanceof ArrayLiteral && RuleEnvironmentExtensions.isArrayN(G, S)) {
			// GH-2615 FIXME: do some type checking of TypeArgs
			if (RuleEnvironmentExtensions.isArray(G, T)) {
				return true;
			}
			return true; // cast is ok
		} else if (canCheck(G, S, T, actualSourceTypeIsCPOE)) { // Constraint 81.3 (Cast Validation At Compile-Time):
			boolean castOK = ts.subtypeSucceeded(G, T, S);
			if (!castOK && S.isUseSiteStructuralTyping() && S instanceof StructuralTypeRef
					&& !((StructuralTypeRef) S).getStructuralMembers().isEmpty()) {
				// allow "~X with { ... } as Y" if "~X as Y" would be legal
				// Rationale: just because the source type has additional properties the cast should not be disallowed
				// (only necessary as a special case because the cast validation is too strict, at the moment)
				TypeRef S_withoutStructMembers = TypeUtils.copyPartial(S,
						TypeRefsPackage.Literals.STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS,
						TypeRefsPackage.Literals.STRUCTURAL_TYPE_REF__GEN_STRUCTURAL_MEMBERS,
						TypeRefsPackage.Literals.STRUCTURAL_TYPE_REF__STRUCTURAL_TYPE);
				castOK = ts.subtypeSucceeded(G, T, S_withoutStructMembers);
			}
			if (!castOK && (T instanceof ParameterizedTypeRef && S instanceof ParameterizedTypeRef)) {
				ParameterizedTypeRef ptrT = (ParameterizedTypeRef) T;
				ParameterizedTypeRef ptrS = (ParameterizedTypeRef) S;
				if (ptrS.getDeclaredType() == ptrT.getDeclaredType()) {
					int to = ptrS.getDeclaredTypeArgs().size();
					if (to == ptrT.getDeclaredTypeArgs().size()) {
						int i = 0;
						while (i < to && (

						ts.subtypeSucceeded(G, ptrT.getDeclaredTypeArgs().get(i), ptrS.getDeclaredTypeArgs().get(i)))) {
							i++;
						}
						if (i == to) {
							castOK = true;
						} else {
							i = 0;
							while (i < to &&
									ts.subtypeSucceeded(G, ptrS.getDeclaredTypeArgs().get(i),
											ptrT.getDeclaredTypeArgs().get(i))) {
								i++;
							}
							castOK = i == to;
						}

					}
				}
			}
			if (!castOK) {
				if (addIssues) {
					addIssue(castExpression,
							EXP_CAST_FAILED.toIssueItem(S.getTypeRefAsString(), T.getTypeRefAsString()));
				}
				return false;
			}
		} else if (T.getDeclaredType() instanceof TypeVariable &&
				((TypeVariable) T.getDeclaredType()).getDeclaredUpperBound() != null) {
			TypeVariable typeVariable = (TypeVariable) T.getDeclaredType();
			if (!internalCheckCastExpression(G, S, typeVariable.getDeclaredUpperBound(), castExpression, false,
					actualSourceTypeIsCPOE)) {
				if (addIssues) {
					addIssue(castExpression,
							EXP_CAST_FAILED.toIssueItem(S.getTypeRefAsString(), T.getTypeRefAsString()));
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * in case of an intersection type, S may be part of an intersection in which another element is a CPOE, i.e.
	 * concrete
	 */
	private boolean canCheck(RuleEnvironment G, TypeRef S, TypeRef T, boolean actualSourceTypeIsCPOE) {
		return T instanceof FunctionTypeExpression
				|| (T instanceof LiteralTypeRef || S instanceof LiteralTypeRef)
				|| ((actualSourceTypeIsCPOE || isCPOE(G, S)) && isCPOE(G, T))
				|| ((S.getDeclaredType() instanceof TInterface) && isActuallyFinal(T))
				|| (isActuallyFinal(S) && (T.getDeclaredType() instanceof TInterface))
				|| (S instanceof ParameterizedTypeRef
						&& T instanceof ParameterizedTypeRef
						&& TypeUtils.isRawSuperType(T.getDeclaredType(), S.getDeclaredType()));
	}

	private boolean isCPOE(RuleEnvironment G, TypeRef T) {
		Object d = null;
		if (T instanceof BoundThisTypeRef) {
			d = ((BoundThisTypeRef) T).getActualThisTypeRef().getDeclaredType();
		}
		if (T instanceof ParameterizedTypeRef) {
			d = T.getDeclaredType();
		}
		if (T instanceof TypeTypeRef) {
			d = tsh.getStaticType(G, (TypeTypeRef) T);
		}

		if (d != null) {
			boolean concreteMetaType = d instanceof TClass
					|| d instanceof TEnum
					|| d instanceof PrimitiveType;
			return concreteMetaType;
		}

		return false;

	}

	private boolean isActuallyFinal(TypeRef typeRef) {
		// IDEBUG-310: excluding TypeVariables here, because #isFinalByType() will return true for them but we
		// cannot treat them as "final" because we do not know what kind of type will be substituted for them
		return typeRef.isFinalByType() && !(typeRef.getDeclaredType() instanceof TypeVariable);
	}

	/**
	 * 7.1.7. Property Accessors, Constraints 69 (Index Access).
	 */
	@Check
	public void checkIndexedAccessExpression(IndexedAccessExpression indexedAccess) {
		if (!jsVariantHelper.requireCheckIndexedAccessExpression(indexedAccess)) {
			return;
		}

		// prepare target and index
		Expression target = indexedAccess.getTarget();
		Expression index = indexedAccess.getIndex();
		if (target == null || index == null) {
			return; // broken AST
		}
		if (index instanceof IdentifierRef) {
			IdentifierRef ir = (IdentifierRef) index;
			if (ir.getId() == null || ir.getId().eIsProxy()) {
				return; // avoid duplicate error messages
			}
		}
		if (index instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) index;
			if (ppae.getProperty() == null || ppae.getProperty().eIsProxy()) {
				return; // avoid duplicate error messages
			}
		}
		if (target instanceof SuperLiteral) {
			return; // avoid duplicate error messages
		}

		// prepare types of target and index
		RuleEnvironment G = newRuleEnvironment(indexedAccess);
		TypeRef targetTypeRefRaw = ts.type(G, target);
		if (targetTypeRefRaw instanceof UnknownTypeRef) {
			return; // saw an UnknownTypeRef -> so we are expected to suppress all follow-up errors
		}
		TypeRef targetTypeRef = ts.upperBoundWithReopenAndResolveBoth(G, targetTypeRefRaw);
		TypeRef indexTypeRef = ts.type(G, index);
		if (indexTypeRef instanceof UnknownTypeRef) {
			return; // saw an UnknownTypeRef -> so we are expected to suppress all follow-up errors
		}

		// prepare some information about the particular form of index access we have
		Type targetDeclType = targetTypeRef.getDeclaredType();
		boolean targetIsLiteralOfStringBasedEnum = targetDeclType instanceof TEnum
				&& N4JSLanguageUtils.getEnumKind((TEnum) targetDeclType) == EnumKind.StringBased;
		TMember accessedBuiltInSymbol = N4JSLanguageUtils.getAccessedBuiltInSymbol(G, index);
		Type accessedStaticType = (targetTypeRef instanceof TypeTypeRef)
				? tsh.getStaticType(G, (TypeTypeRef) targetTypeRef)
				: null;
		boolean indexIsNumeric = ts.subtypeSucceeded(G, indexTypeRef, numberTypeRef(G));
		CompileTimeValue indexValue = ASTMetaInfoUtils.getCompileTimeValue(index);

		// create issues depending on the collected information
		if (targetTypeRef.isDynamic()) {
			// allowed: indexing into dynamic receiver
		} else if (objectType(G) == targetDeclType && !(targetTypeRef.isUseSiteStructuralTyping())) {
			// TODO: remove special case, see GH-2022
			// allowed: index into exact-type Object instance (not subtype thereof)
		} else if (accessedStaticType instanceof TEnum) { // Constraints 69.2
			// disallowed: index access into an enum
			addIssue(indexedAccess, EXP_INDEXED_ACCESS_ENUM.toIssueItem());
		} else if (indexIsNumeric && (targetTypeRef.isArrayLike() || targetIsLiteralOfStringBasedEnum)) { // Constraints
																											// 69.3
			// allowed: index into array-like with a numeric index
		} else if (accessedBuiltInSymbol != null) {
			// we have something like: myObj[Symbol.iterator]
			// -> delegate to special method
			internalCheckIndexedAccessWithSymbol(G, indexedAccess, targetTypeRef, accessedBuiltInSymbol);
		} else if (N4JSLanguageUtils.hasIndexSignature(targetTypeRef)) {
			// allowed: index into type with index signature
			// TODO IDE-3620 ensure correct type of index value
		} else {
			// all other cases:
			// treat this as an ordinary member access where the member name is given as a compile-time expression
			if (indexValue.isValid()) {
				internalCheckComputedIndexedAccess(indexedAccess, targetTypeRef, indexValue, indexIsNumeric);
			} else {
				createIssuesForEvalErrors(((ValueInvalid) indexValue).getErrors());
			}
		}
	}

	/**
	 * In general computed-names are not allowed as index, unless it denotes a visible member by means of a
	 * string-literal.
	 */
	private void internalCheckComputedIndexedAccess(IndexedAccessExpression indexedAccess,
			TypeRef receiverTypeRef, CompileTimeValue indexValue, boolean indexIsNumeric) {

		String memberName = N4JSLanguageUtils.derivePropertyNameFromCompileTimeValue(indexValue);
		if (N4JSLanguageUtils.SYMBOL_ITERATOR_MANGLED.equals(memberName)) {
			// Implementation restriction: member name clashes with compiler-internal, synthetic, mangled name.
			addIssue(indexedAccess, EXP_INDEXED_ACCESS_IMPL_RESTRICTION.toIssueItem());
			return;
		}
		if (receiverTypeRef.isDynamic()) {
			// allowed: indexing into dynamic receiver, both via dot and indexing notations.
			return;
		}
		boolean checkVisibility = true;
		boolean staticAccess = (receiverTypeRef instanceof TypeTypeRef);
		boolean structFieldInitMode = receiverTypeRef
				.getTypingStrategy() == TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
		IScope scope = memberScopingHelper.createMemberScope(receiverTypeRef, indexedAccess, checkVisibility,
				staticAccess, structFieldInitMode);
		IEObjectDescription memberDesc = (memberName != null && !memberName.isEmpty())
				? scope.getSingleElement(qualifiedNameConverter.toQualifiedName(memberName))
				: null;
		EObject member = memberDesc == null ? null : memberDesc.getEObjectOrProxy();
		boolean isNonExistentMember = member == null || member.eIsProxy();
		if (isNonExistentMember) {
			if (indexIsNumeric) {
				addIssue(indexedAccess, EXP_INDEXED_ACCESS_FORBIDDEN.toIssueItem());
			} else {
				addIssue(indexedAccess, EXP_INDEXED_ACCESS_COMPUTED_NOTFOUND.toIssueItem(memberName));
			}
			return;
		}
		IEObjectDescriptionWithError errorDesc = IEObjectDescriptionWithError.getDescriptionWithError(memberDesc);
		if (errorDesc != null) {
			addIssue(errorDesc.getMessage(), indexedAccess, errorDesc.getIssueCode());
		}
	}

	private boolean internalCheckIndexedAccessWithSymbol(RuleEnvironment G,
			IndexedAccessExpression indexedAccess, TypeRef receiverTypeRef, TMember accessedBuiltInSymbol) {
		// check valid symbol (currently only 'iterator' and 'asyncIterator')
		TMember symbolIterator = symbolObjectType(G).findOwnedMember("iterator", false, true);
		TMember symbolAsyncIterator = symbolObjectType(G).findOwnedMember("asyncIterator", false, true);
		if (accessedBuiltInSymbol != symbolIterator && accessedBuiltInSymbol != symbolAsyncIterator) {
			IssueItem issueItem = EXP_INDEXED_ACCESS_SYMBOL_INVALID
					.toIssueItem("Symbol.iterator and Symbol.asyncIterator");
			addIssue(indexedAccess, N4JSPackage.Literals.INDEXED_ACCESS_EXPRESSION__INDEX, issueItem);
			return false;
		}
		// check valid receiver type (currently only for instance of [Async]Iterable and immediate(!) instances of
		// Object and dynamic types)
		TypeRef correspondingIterableTypeRef = (accessedBuiltInSymbol == symbolIterator)
				? iterableTypeRef(G, TypeRefsFactory.eINSTANCE.createWildcard())
				: asyncIterableTypeRef(G, TypeRefsFactory.eINSTANCE.createWildcard());
		boolean isIterable = ts.subtypeSucceeded(G, receiverTypeRef, correspondingIterableTypeRef);
		boolean isObjectImmediate = receiverTypeRef.getDeclaredType() == objectType(G) &&
				receiverTypeRef.getTypingStrategy() == TypingStrategy.NOMINAL;
		boolean isDynamic = receiverTypeRef.isDynamic();
		if (!(isIterable || isObjectImmediate || isDynamic)) {
			IssueItem issueItem = EXP_INDEXED_ACCESS_SYMBOL_WRONG_TYPE.toIssueItem(accessedBuiltInSymbol.getName(),
					correspondingIterableTypeRef.getDeclaredType().getName());
			addIssue(indexedAccess, N4JSPackage.Literals.INDEXED_ACCESS_EXPRESSION__INDEX, issueItem);
			return false;
		}
		// check valid access (currently read-only, except for immediate(!) instances of Object and dynamic types)
		if (!(isObjectImmediate || isDynamic)) {
			boolean writeAccess = ExpressionExtensions.isLeftHandSide(indexedAccess);
			if (writeAccess) {
				IssueItem issueItem = EXP_INDEXED_ACCESS_SYMBOL_READONLY.toIssueItem(accessedBuiltInSymbol.getName(),
						correspondingIterableTypeRef.getDeclaredType().getName());
				addIssue(indexedAccess, N4JSPackage.Literals.INDEXED_ACCESS_EXPRESSION__INDEX, issueItem);
				return false;
			}
		}
		return true;
	}

	@Check
	public boolean checkAssignmentExpression(AssignmentExpression assExpr) {
		Expression lhs = assExpr.getLhs();
		// GHOLD-119 imported elements
		return holdsLefthandsideNotConst(lhs) && holdsWritableIdentifier(lhs);
	}

	/** @return true if nothing was issued */
	private boolean holdsLefthandsideNotConst(Expression lhs) {
		if (lhs instanceof ParenExpression) {
			return holdsLefthandsideNotConst(((ParenExpression) lhs).getExpression());
		} else if (lhs instanceof IdentifierRef) {
			return holdsLefthandsideNotConst((IdentifierRef) lhs);
		}
		return true;
	}

	/** @return true if nothing was issued */
	private boolean holdsLefthandsideNotConst(IdentifierRef lhs) {
		IdentifiableElement id = lhs.getId();
		if (id instanceof VariableDeclaration) {
			VariableDeclaration vd = (VariableDeclaration) id;
			if (vd.isConst()) {
				addIssue(lhs, EXP_ASSIGN_CONST_VARIABLE.toIssueItem(vd.getName()));
				return false;
			}
		} else if (id instanceof TVariable) {
			TVariable tv = (TVariable) id;
			if (tv.isConst()) {
				addIssue(lhs, EXP_ASSIGN_CONST_VARIABLE.toIssueItem(tv.getName()));
				return false;
			}
		} else if (id instanceof TField) {
			TField tf = (TField) id;
			if (!tf.isWriteable()) {
				// note: this case can happen only when referring to globals in GlobalObject (see file global.n4jsd);
				// in all other cases of referencing a field, 'lhs' will be a PropertyAccessExpression (those cases
				// will be handled in class AbstractMemberScope as part of scoping)
				addIssue(lhs, VIS_WRONG_READ_WRITE_ACCESS.toIssueItem("built-in constant", tf.getName(), "read-only"));
				return false;
			}
		}

		return true;
	}

	@Check
	public void checkPromisify(PromisifyExpression promiExpr) {
		if (!promisifyHelper.isPromisifiableExpression(promiExpr.getExpression())) {
			addIssue(promiExpr, EXP_PROMISIFY_INVALID_USE.toIssueItem());
		}
	}

	/**
	 * Ensures that 'this' literals are located at a valid location.
	 */
	@Check
	public void checkThisLiteral(ThisLiteral thisLiteral) {
		ThisArgProvider context = EcoreUtil2.getContainerOfType(thisLiteral, ThisArgProvider.class);

		// cf. GHOLD-348
		while (context instanceof ArrowFunction) {
			context = EcoreUtil2.getContainerOfType(context.eContainer(), ThisArgProvider.class);
		}

		// 1) not in static members of interfaces
		if (context instanceof N4MemberDeclaration) {
			N4MemberDeclaration md = (N4MemberDeclaration) context;
			TMember tMember = md.getDefinedTypeElement();
			if (tMember != null && tMember.getContainingType() instanceof TInterface && tMember.isStatic()) {
				addIssue(thisLiteral, CLF_NO_THIS_IN_STATIC_MEMBER_OF_INTERFACE.toIssueItem());
				return;
			}
		}
		if (context instanceof N4FieldDeclaration) {
			N4FieldDeclaration fd = (N4FieldDeclaration) context;
			TMember tField = fd.getDefinedTypeElement();
			// 2) not in initializers of data fields in interfaces
			if (tField != null && tField.getContainingType() instanceof TInterface) {
				addIssue(thisLiteral, CLF_NO_THIS_IN_FIELD_OF_INTERFACE.toIssueItem());
				return;
			}
			// 3) not in initializers of static(!) data fields in classes
			if (tField != null && tField.getContainingType() instanceof TClass) {
				if (tField.isStatic()) {
					addIssue(thisLiteral, CLF_NO_THIS_IN_STATIC_FIELD.toIssueItem());
					return;
				}
			}
		}
	}

	@Check
	public void checkTaggedTemplateString(TaggedTemplateString ttString) {
		if (isInOptionalChaining(ttString)) {
			addIssue(ttString.getTemplate(), VCO_TEMPLATE_IN_OPT_CHAIN.toIssueItem());
		}
	}

	private boolean isInOptionalChaining(EObject start) {
		EObject expr = start;
		while (expr instanceof ExpressionWithTarget) {
			ExpressionWithTarget ewt = (ExpressionWithTarget) expr;
			if (ewt.isOptionalChaining()) {
				return true;
			}
			expr = ewt.getTarget();
		}
		return false;
	}

	@Check
	public void checkMandatoryCompileTimeExpression(Expression expr) {
		if (expr.eContainer() instanceof IndexedAccessExpression) {
			// special case: index access expressions
			// -> this is handled in a more fine-grained manner above by method #checkIndexedAccessExpression()
			return;
		}
		if (N4JSLanguageUtils.isMandatoryCompileTimeExpression(expr)) {
			CompileTimeValue evalResult = ASTMetaInfoUtils.getCompileTimeValue(expr);
			if (evalResult instanceof ValueInvalid) {
				if (isExpressionOfComputedPropertyNameInObjectLiteral(expr)) {
					// special case: in object literals, anything goes
					// (but show a warning)
					addIssue(expr, IssueCodes.EXP_COMPUTED_PROP_NAME_DISCOURAGED.toIssueItem());
					return;
				}
				createIssuesForEvalErrors(((ValueInvalid) evalResult).getErrors());
			}
		}
	}

	private boolean isExpressionOfComputedPropertyNameInObjectLiteral(Expression expr) {
		EObject exprParent = expr.eContainer();
		return exprParent instanceof LiteralOrComputedPropertyName
				&& exprParent.eContainer() instanceof PropertyAssignment
				&& exprParent.eContainer().eContainer() instanceof ObjectLiteral;
	}

	private void createIssuesForEvalErrors(List<CompileTimeEvaluationError> errors) {
		for (CompileTimeEvaluationError error : errors) {
			createIssueForEvalError(error);
		}
	}

	private void createIssueForEvalError(CompileTimeEvaluationError error) {
		String message;
		if (error instanceof UnresolvedPropertyAccessError) {
			// special case:
			// property of a ParameterizedPropertyAccessExpression was not found while evaluating the compile-time
			// expression (see location in CompileTimeExpressionProcessor where UnresolvedPropertyAccessError is
			// created)
			// -> in this case, CompileTimeExpressionProcessor could not provide a detailed error message, so we have
			// to come up with our own message:
			ParameterizedPropertyAccessExpression propAccessExpr = ((UnresolvedPropertyAccessError) error)
					.getAstNodeCasted();
			IdentifiableElement prop = propAccessExpr.getProperty();
			if (prop == null || prop.eIsProxy()) {
				// property does not exist, which will cause the usual "Couldn't resolve ..." error
				// -> no additional error message required, here
				message = null;
			} else {
				// at this point, still quite a few cases are left, but to distinguish between them would require
				// additional information in the TModule, which is not worth it; so we go with a fairly generic
				// message, here:
				message = "reference must point to a directly owned field (i.e. not inherited, consumed, or polyfilled) and the field must not have a computed name";
			}
		} else {
			// standard case:
			// -> CompileTimeExpressionProcessor provided an error message
			message = error.message;
		}
		EObject astNode = error.astNode;
		EStructuralFeature feature = error.feature;
		if (message != null && astNode != null) { // feature may be null, that is ok!
			addIssue(astNode, feature, EXP_COMPILE_TIME_MANDATORY.toIssueItem(message));
		}
	}
}

enum ConstBoolean {
	CTrue, CFalse, NotPrecomputable
}
