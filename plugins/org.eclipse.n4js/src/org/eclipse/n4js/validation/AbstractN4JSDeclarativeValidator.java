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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.addTypeMapping;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.AbstractVariable;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.BreakStatement;
import org.eclipse.n4js.n4JS.ContinueStatement;
import org.eclipse.n4js.n4JS.DebuggerStatement;
import org.eclipse.n4js.n4JS.DoStatement;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.IfStatement;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSMetaModelUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NamedElement;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ReturnStatement;
import org.eclipse.n4js.n4JS.SwitchStatement;
import org.eclipse.n4js.n4JS.ThrowStatement;
import org.eclipse.n4js.n4JS.TryStatement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.n4JS.WhileStatement;
import org.eclipse.n4js.n4JS.WithStatement;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.services.N4JSGrammarAccess;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.MemberType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Common base class for all N4JS validators. Provides some convenience methods and internal validations. The latter are
 * applied to AST nodes of different types and therefore have to be reused in different concrete subclasses (for
 * example,
 * {@link #internalCheckTypeArguments(List, List, Optional, boolean, IdentifiableElement, EObject, EStructuralFeature)}
 * is called from N4JSTypeValidator and N4JSExpressionValidator).
 */
@SuppressWarnings("javadoc")
public abstract class AbstractN4JSDeclarativeValidator extends AbstractMessageAdjustingN4JSValidator { // AbstractDeclarativeValidator
																										// {

	@Inject
	protected N4JSElementKeywordProvider keywordProvider;

	@Inject
	protected ValidatorMessageHelper validatorMessageHelper;

	@Inject
	private N4JSGrammarAccess grammarAccess;
	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private WorkspaceAccess workspaceAccess;
	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * @since 2.6
	 */
	@Override
	public MethodWrapperCancelable createMethodWrapper(
			AbstractDeclarativeValidator instanceToUse,
			Method method) {
		return new MethodWrapperCancelable(instanceToUse, method) {

			@Override
			public void invoke(State state) {
				operationCanceledManager.checkCanceled(getCancelIndicator(state));
				if (shouldInvoke(state)) {
					super.invoke(state);
				}
			}

			/**
			 * Reasons to skip invocation of a validation method:
			 * <ul>
			 * <li>no resource associated to the current object</li>
			 * <li>the resource in question shouldn't be validated</li>
			 * </ul>
			 */
			private boolean shouldInvoke(State state) {
				Resource resource = state.currentObject == null ? null : state.currentObject.eResource();
				if (resource != null) {
					if (!workspaceAccess.isNoValidate(resource, resource.getURI())) {
						return true;
					}
				}
				return false;
			}

			@Override
			public void handleInvocationTargetException(Throwable targetException, State state) {

				// ignore GuardException, check is just not evaluated if guard is false
				// ignore NullPointerException, as not having to check for NPEs all the time is a convenience feature
				super.handleInvocationTargetException(targetException, state);
				if (targetException instanceof NullPointerException) {
					Exceptions.sneakyThrow(targetException);
				}
			}
		};
	}

	/*
	 * ************************************************************** Internal Validations:
	 */
	/** Same as {@code #internalCheckTypeArguments()}, but accepts type arguments from AST. */
	protected void internalCheckTypeArgumentsNodes(List<? extends TypeVariable> typeVars,
			List<? extends TypeReferenceNode<?>> typeArgsNodes, boolean allowAutoInference,
			IdentifiableElement parameterizedElement,
			EObject source, EStructuralFeature feature) {

		List<TypeArgument> typeArgsInAST = toList(map(typeArgsNodes, tan -> tan.getTypeRefInAST())); // n.b.: resulting
																										// 'typeArgsInAST'
																										// may contain
																										// null values
																										// in case of
																										// syntax error
																										// (retain them
																										// to keep
																										// indices
																										// valid!)
		List<TypeRef> typeArgsProcessed = toList(map(typeArgsNodes, tan -> tan.getTypeRef()));
		internalCheckTypeArguments(typeVars, typeArgsInAST, Optional.of(typeArgsProcessed),
				allowAutoInference, parameterizedElement, source, feature);
	}

	/**
	 * Checks for (1) correct number of type arguments, (2) correct type of each type argument, and (3) consistency of
	 * use-site and definition-site variance (in case of wildcards).
	 * <p>
	 * Usually 'parameterizedElement' will be a TClass, TInterface, TMethod or TFunction. However, it might also be a
	 * TField, TGetter or TSetter to also support the corner cases of an invalid parameterized property access to a
	 * field, getter, setter in this method. It may be <code>null</code> if no type model element is available as the
	 * source of the type variables given in 'typeVars' (e.g. in case of a function type expression).
	 *
	 * @param typeVars
	 *            the type variables the provided type arguments are intended for.
	 * @param typeArgsInAST
	 *            the type arguments to check (expected to be contained in the AST).
	 * @param typeArgsResolvedOpt
	 *            if resolved versions of the 'typeArgsInAST' are available (e.g. as returned by
	 *            {@link TypeReferenceNode#getTypeRef()}), they should be passed in here; if absent, resolution will be
	 *            done on demand in this method.
	 * @param allowAutoInference
	 *            if true, it will be legal to provide no arguments, even if there are several type variables. Intended
	 *            for cases where inference of type arguments is supported.
	 * @param parameterizedElement
	 *            the element defining the type variables in 'typeVars' or <code>null</code> if not available (e.g. in
	 *            case of a FunctionTypeExpression). Will be used only for error reporting (to show a keyword and name,
	 *            e.g. "for method myMethod").
	 * @param source
	 *            the AST node where the parameterized access occurs; used to derive the region of the error.
	 * @param feature
	 *            the feature in 'source' where the parameterized access occurs; used to derive the region of the error.
	 */
	protected void internalCheckTypeArguments(List<? extends TypeVariable> typeVars,
			List<? extends TypeArgument> typeArgsInAST, Optional<List<? extends TypeArgument>> typeArgsResolvedOpt,
			boolean allowAutoInference, IdentifiableElement parameterizedElement,
			EObject source, EStructuralFeature feature) {

		int typeParameterCount = typeVars.size();
		int mandatoryTypeParameterCount = size(filter(typeVars, tv -> !tv.isOptional()));
		int typeArgumentCount = typeArgsInAST.size();

		// if the AST location supports auto-inference of type arguments, allow for
		// not providing any type arguments, even if paramType is actually parameterized
		if (allowAutoInference && typeArgumentCount == 0) {
			return; // success!
		}

		// check for correct number of type arguments
		boolean tooFew = typeArgumentCount < mandatoryTypeParameterCount;
		boolean tooMany = typeArgumentCount > typeParameterCount;
		if (tooFew || tooMany) {
			String expectationStr;
			if (mandatoryTypeParameterCount == typeParameterCount) {
				expectationStr = "" + typeParameterCount;
			} else if (tooFew) {
				expectationStr = "at least " + mandatoryTypeParameterCount;
			} else {
				expectationStr = "not more than " + typeParameterCount;
			}
			if (source instanceof ParameterizedTypeRef && ((ParameterizedTypeRef) source).isArrayNTypeExpression()) {
				String message = IssueCodes.EXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ITERABLE_N_SYNTAX
						.getMessage(BuiltInTypeScope.ITERABLE_N__MAX_LEN);
				addIssue(message, source, feature,
						IssueCodes.EXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ITERABLE_N_SYNTAX.name());
			} else if (parameterizedElement != null && parameterizedElement.getName() != null) {
				String message = IssueCodes.EXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ELEMENT.getMessage(
						keywordProvider.keyword(parameterizedElement),
						parameterizedElement.getName(), expectationStr, typeArgumentCount);
				addIssue(message, source, feature, IssueCodes.EXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ELEMENT.name());
			} else {
				String message = IssueCodes.EXP_WRONG_NUMBER_OF_TYPEARGS.getMessage(expectationStr, typeArgumentCount);
				addIssue(message, source, feature, IssueCodes.EXP_WRONG_NUMBER_OF_TYPEARGS.name());
			}
			return;
		}

		// check if type arguments adhere to type variables' bounds and variance (if any)
		int minTypeVariables = Math.min(typeParameterCount, typeArgumentCount);
		if (minTypeVariables != 0) {
			RuleEnvironment G = newRuleEnvironment(source);
			// preparation: create resolved type arguments, if not provided by caller

			List<TypeArgument> typeArgsResolved = new ArrayList<>(
					(typeArgsResolvedOpt.isPresent()) ? typeArgsResolvedOpt.get() : Collections.emptyList());
			for (int i = typeArgsResolved.size(); i < typeArgumentCount; i++) {
				TypeArgument typeArgumentInAST = typeArgsInAST.get(i);
				if (typeArgumentInAST != null) {
					typeArgsResolved.add(tsh.resolveTypeAliases(G, typeArgumentInAST));
				} else {
					typeArgsResolved.add(null); // syntax error; add 'null' to keep length and indices in sync between
												// typeArgsResolved and typeArgsInAST
				}
			}
			// preparation: create rule environment for type variable substitution in upper bounds
			RuleEnvironment G_subst = newRuleEnvironment(source);
			if (source instanceof ParameterizedPropertyAccessExpression) {
				TypeRef targetTypeRef = ts.type(G, ((ParameterizedPropertyAccessExpression) source).getTarget()); // note:
																													// not
																													// using
																													// G_subst
																													// here
				tsh.addSubstitutions(G_subst, targetTypeRef);
			}
			for (int i = 0; i < typeArgumentCount; i++) {
				addTypeMapping(G_subst, typeVars.get(i), typeArgsResolved.get(i));
			}
			// actually check provided type arguments
			for (int i = 0; i < minTypeVariables; i++) {
				TypeVariable typeParameter = typeVars.get(i);
				TypeArgument typeArgumentInAST = typeArgsInAST.get(i);
				TypeArgument typeArgumentResolved = typeArgsResolved.get(i);

				if (typeArgumentInAST != null) {
					internalCheckTypeArgument(typeParameter, typeArgumentInAST, typeArgumentResolved, G_subst);
				} else {
					// syntax error -> ignore
				}
			}
		}

		/*
		 * Temporary debug code to show a warning if type arguments are explicitly specified at a location that supports
		 * type argument inference (mostly property access and call expressions).
		 */
		// if(allowAutoInference && typeParameterCount>0 && typeArgumentCount>0) {
		// getMessageAcceptor().acceptWarning(
		// "**** hopefully unnecessary use of explicit type arguments ****",
		// source, feature, INSIGNIFICANT_INDEX, IssueCodes.EXP_WRONG_NUMBER_OF_TYPEARGS);
		// }
	}

	/** Actually check a single type argument. */
	private void internalCheckTypeArgument(TypeVariable typeParameter, TypeArgument typeArgumentInAST,
			TypeArgument typeArgumentResolved, RuleEnvironment G_subst) {
		// check consistency of use-site and definition-site variance
		if (typeArgumentInAST instanceof Wildcard) {
			Wildcard wc = (Wildcard) typeArgumentInAST;
			Variance defSiteVariance = typeParameter.getVariance();
			Variance useSiteVariance = null;
			if (wc.getDeclaredUpperBound() != null) {
				useSiteVariance = Variance.CO;
			} else if (wc.getDeclaredLowerBound() != null) {
				useSiteVariance = Variance.CONTRA;
			}
			if (defSiteVariance != Variance.INV
					&& useSiteVariance != null
					&& useSiteVariance != defSiteVariance) {
				// we've got an inconsistency!
				if (wc.isUsingInOutNotation()) {
					String message = IssueCodes.EXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG_IN_OUT.getMessage(
							useSiteVariance.getDescriptiveStringNoun(true),
							defSiteVariance.getDescriptiveStringNoun(true));
					addIssue(message, wc, IssueCodes.EXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG_IN_OUT.name());
				} else {
					String message = IssueCodes.EXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG.getMessage(
							(useSiteVariance == Variance.CO) ? "upper" : "lower",
							defSiteVariance.getDescriptiveString(true));
					addIssue(message, wc, IssueCodes.EXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG.name());
				}
			}
		}

		// check bounds
		boolean isExceptionCase = TypeUtils.isVoid(typeArgumentInAST); // in this case another validation will show an
																		// error (avoid duplicate messages)
		if (!isExceptionCase) {
			TypeRef typeParamUB = typeParameter.getDeclaredUpperBound() != null ? typeParameter.getDeclaredUpperBound()
					: N4JSLanguageUtils.getTypeVariableImplicitUpperBound(G_subst);
			TypeRef typeParamUBSubst = ts.substTypeVariables(G_subst, typeParamUB);
			TypeArgument typeArgSubst = ts.substTypeVariables(G_subst, typeArgumentResolved);
			Result result = ts.subtype(G_subst, typeArgSubst, typeParamUBSubst);
			if (result.isFailure()) {
				createTypeError(result, typeArgumentInAST);
			}
		}
	}

	/**
	 * Check whether every type parameter of a generic function or method declaration is actually used by that
	 * declaration.
	 *
	 * @param genericFunctionOrMethod
	 *            the generic function or method declaration to check.
	 */
	protected <T extends GenericDeclaration & FunctionDefinition> void internalCheckNoUnusedTypeParameters(
			T genericFunctionOrMethod) {
		if (genericFunctionOrMethod.getDefinedType() == null) {
			return;
		}

		TFunction functionType = (TFunction) genericFunctionOrMethod.getDefinedType();
		internalCheckNoUnusedTypeParameters(genericFunctionOrMethod, genericFunctionOrMethod.getTypeVars(),
				functionType.getTypeVars());
	}

	/**
	 * Check whether every type parameter of a generic function type expression is actually used by the declared
	 * function type.
	 *
	 * @param functionTypeExpInAST
	 *            the generic function type to check.
	 */
	protected void internalCheckNoUnusedTypeParameters(FunctionTypeExpression functionTypeExpInAST) {
		if (functionTypeExpInAST.getDeclaredType() == null) {
			return;
		}

		TFunction declaredType = functionTypeExpInAST.getDeclaredType();
		internalCheckNoUnusedTypeParameters(functionTypeExpInAST, functionTypeExpInAST.getOwnedTypeVars(),
				declaredType.getTypeVars());
	}

	/**
	 * Check whether every type variable that is present in two lists is references anywhere in a subtree. Thereby the
	 * first list contains the AST nodes for the type variables whereas the second list contains the corresponding
	 * elements from the type model. The AST nodes are required for attaching the generated issues to the correct nodes,
	 * whereas the type model elements are needed for the actual usage check.
	 *
	 * We assume that each pair of an actual type variable (AST node) and a declared type variable (type model element)
	 * that share an index in both lists have the same name. If the lists are not of equal length, then this assumption
	 * only applies to the elements up to, but not including, the length of the shorter list. In other words, we assume
	 * that one of the two given lists is a prefix of the other (given that we consider actual and declared type
	 * variables to be equal if they have the same name), and we only check this prefix.
	 *
	 * Finally we add an issue for each type variable that is found to be unreferenced.
	 *
	 * @param root
	 *            the root of the subtree to search for references to the given type variables
	 * @param actualTypeVarsInAST
	 *            the actual type variables from the AST; must be either an instance of {@link N4TypeVariable} (standard
	 *            case) or of {@link TypeVariable} (when validating type parameter declarations in the AST that are
	 *            nested inside a TypeRef).
	 * @param declaredTypeVars
	 *            the declared type variables from the type model
	 */
	private void internalCheckNoUnusedTypeParameters(EObject root, List<? extends EObject> actualTypeVarsInAST,
			List<TypeVariable> declaredTypeVars) {
		int typeVarCount = Math.min(actualTypeVarsInAST.size(), declaredTypeVars.size());
		if (typeVarCount == 1) {
			// Since this is a very common case, we want it to be as fast as possible. Therefore we don't
			// build a set of all reference type variables and do the check once directly.
			EObject actualTypeVarInAST = actualTypeVarsInAST.get(0);
			TypeVariable declaredTypeVar = declaredTypeVars.get(0);
			if (!TypeUtils.isOrContainsRefToTypeVar(root, declaredTypeVar)) {
				createUnusedTypeParameterIssue(actualTypeVarInAST);
			}
		} else if (typeVarCount > 1) {
			// In this case, we avoid repeatedly traversing the tree with the given root by getting a set of
			// all type variables it references up front and using that to perform our check.
			Set<TypeVariable> referencedTypeVars = TypeUtils.getReferencedTypeVars(root);
			for (int i = 0; i < typeVarCount; i++) {
				EObject actualTypeVarInAST = actualTypeVarsInAST.get(i);
				TypeVariable declaredTypeVar = declaredTypeVars.get(i);

				if (!referencedTypeVars.contains(declaredTypeVar)) {
					createUnusedTypeParameterIssue(actualTypeVarInAST);
				}
			}
		}
	}

	private void createUnusedTypeParameterIssue(EObject typeVarInAST) {
		String msg = IssueCodes.FUN_UNUSED_GENERIC_TYPE_PARAM
				.getMessage(N4JSASTUtils.getNameOfTypeVarInAST(typeVarInAST));
		addIssue(msg, typeVarInAST, N4JSASTUtils.getNameFeatureOfTypeVarInAST(typeVarInAST),
				IssueCodes.FUN_UNUSED_GENERIC_TYPE_PARAM.name());
	}

	/**
	 * Finds first inheritance cycle in the inheritance hierarchy above 'classifier'. Consequential errors are omitted,
	 * that is, if a class X extends A, and if A extends B and B extends A, then no error is reported for X as it is not
	 * part of the cycle itself.
	 *
	 * @return a non-empty list of TClassifiers forming a cyclic path in the inheritance hierarchy above 'classifier' or
	 *         <code>null</code> if no such cycle exists. Never returns a list with a length below 2.
	 */
	protected List<TClassifier> findCyclicInheritance(TClassifier classifier) {
		return UtilN4.findCycleInDirectedGraph(classifier, (it) -> {
			if (it instanceof TClass) {
				TClass tc = (TClass) it;
				// this case is only a performance tweak:
				// for classes we do not have to check the implemented interfaces, because interfaces cannot
				// extend classes; so all we could find via the implemented interfaces of a class are cycles
				// within the interfaces themselves not including 'it', but these kind of cycles will not be
				// returned by method #findCycleInDirectedGraph() anyway (see API doc)!
				if (tc.getSuperClassRef() != null && tc.getSuperClassRef().getDeclaredType() instanceof TClass)
					return List.of((TClassifier) tc.getSuperClassRef().getDeclaredType());
				else
					return Collections.emptyList();
			} else {
				// general case:
				return toList(
						filter(map(it.getSuperClassifierRefs(), scRef -> scRef.getDeclaredType()), TClassifier.class));
			}
		});
	}

	/*
	 * ************************************************************** Helper methods.
	 */
	/**
	 * Convenience method, returns true if given member is a constructor (method).
	 */
	static boolean isConstructor(TMember tMember) {
		return (tMember instanceof TMethod) ? tMember.isConstructor() : false;
	}

	public boolean isAbstract(TClassifier classifier) {
		if (classifier instanceof TClass) {
			return classifier.isAbstract();
		}

		if (classifier instanceof TInterface) {
			// TODO: review if we use property or validation
			/*
			 * Interfaces are abstract by default and this cannot be changed by user, therefore N4jS grammar doesn't
			 * allow 'abstract' keyword on N4InterfaceDeclaration Question is if we use properties only for things user
			 * can affect? if yes -> abstract property is not in interface type, and we depend on this validation if no
			 * -> abstract property must be added to interface type, types builder needs to be adjusted, this helper
			 * needs to query types for property
			 */
			return true;
		}
		return false;
	}

	public boolean isDeclaredAbstract(TMember member) {
		if (member instanceof TMethod) {
			return ((TMethod) member).isDeclaredAbstract();
		}
		if (member instanceof TGetter) {
			return ((TGetter) member).isDeclaredAbstract();
		}
		if (member instanceof TSetter) {
			return ((TSetter) member).isDeclaredAbstract();
		}
		return false;
	}

	public Block getBody(N4MemberDeclaration n4MemberDeclaration) {
		if (n4MemberDeclaration instanceof N4MethodDeclaration) {
			return ((N4MethodDeclaration) n4MemberDeclaration).getBody();
		}
		if (n4MemberDeclaration instanceof N4GetterDeclaration) {
			return ((N4GetterDeclaration) n4MemberDeclaration).getBody();
		}
		if (n4MemberDeclaration instanceof N4SetterDeclaration) {
			return ((N4SetterDeclaration) n4MemberDeclaration).getBody();
		}
		return null;
	}

	public boolean hasAnnotation(AnnotableElement elem, String annotationName) {
		return exists(elem.getAnnotations(), an -> Objects.equals(an.getName(), annotationName));
	}

	/*
	 * Provides default implementations for error, warning and info creation in order to throw exceptions as these
	 * methods are not to be called in N4JS. Instead, addIssue is to be used, the severity is retrieved from the message
	 * file, see NLSProcessor for details.
	 */
	@Override
	public void error(String message, EObject source, EStructuralFeature feature, int index, String code,
			String... issueData) {
		throw new UnsupportedOperationException("Don't use error(...) anymore, but addIssue");
	}

	@Override
	public void warning(String message, EObject source, EStructuralFeature feature, int index, String code,
			String... issueData) {
		throw new UnsupportedOperationException("Don't use warning(...) anymore, but addIssue");
	}

	@Override
	public void info(String message, EObject source, EStructuralFeature feature, int index, String code,
			String... issueData) {
		throw new UnsupportedOperationException("Don't use info(...) anymore, but addIssue");
	}

	/*
	 * Finds the closes name feature, either of the object itself, or of a sibling or parent. This is basically used for
	 * error markers.
	 */
	public Pair<? extends EObject, ? extends EStructuralFeature> findNameFeature(EObject eo) {
		if (eo instanceof NamedElement) {
			EStructuralFeature attribute = N4JSMetaModelUtils.getElementNameFeature(eo);
			if (attribute != null) {
				return Pair.of(eo, attribute);
			}
		}

		Pair<? extends EObject, ? extends EStructuralFeature> eObjectToNameFeature = null;
		if (eo instanceof ExportDeclaration) {
			ExportDeclaration ed = (ExportDeclaration) eo;
			eObjectToNameFeature = findNameFeature(ed.getExportedElement());
		} else if (eo instanceof IdentifiableElement) {
			eObjectToNameFeature = Pair.of(eo, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME);
		} else if (eo instanceof VariableStatement) {
			VariableDeclaration varDecl = head(((VariableStatement) eo).getVarDecl());
			eObjectToNameFeature = findNameFeature(varDecl);
		} else if (eo instanceof AbstractVariable<?>) {
			eObjectToNameFeature = Pair.of(eo, N4JSPackage.Literals.ABSTRACT_VARIABLE__NAME);
		} else if (eo instanceof N4TypeVariable) {
			eObjectToNameFeature = Pair.of(eo, N4JSPackage.Literals.N4_TYPE_VARIABLE__NAME);
		}

		return eObjectToNameFeature;
	}

	/*
	 * Returns range which can be used for error markers, this is usually only a fall back if no name feature can be
	 * used.
	 */
	public Pair<Integer, Integer> findOffsetAndLength(EObject eo) {
		ICompositeNode node = NodeModelUtils.findActualNodeFor(eo);
		INode childNode = null;
		if (eo instanceof Block) {
			childNode = findChildNode(node, grammarAccess.getBlockAccess().getLeftCurlyBracketKeyword_0_0_1());
		} else if (eo instanceof IfStatement) {
			childNode = findChildNode(node, grammarAccess.getIfStatementAccess().getIfKeyword_0());
		} else if (eo instanceof DoStatement) {
			childNode = findChildNode(node, grammarAccess.getDoStatementAccess().getDoKeyword_0());
		} else if (eo instanceof WhileStatement) {
			childNode = findChildNode(node, grammarAccess.getWhileStatementAccess().getWhileKeyword_0());
		} else if (eo instanceof ForStatement) {
			childNode = findChildNode(node, grammarAccess.getForStatementAccess().getForKeyword_1());
		} else if (eo instanceof ContinueStatement) {
			childNode = findChildNode(node, grammarAccess.getContinueStatementAccess().getContinueKeyword_1());
		} else if (eo instanceof BreakStatement) {
			childNode = findChildNode(node, grammarAccess.getBreakStatementAccess().getBreakKeyword_1());
		} else if (eo instanceof ReturnStatement) {
			childNode = findChildNode(node, grammarAccess.getReturnStatementAccess().getReturnKeyword_1());
		} else if (eo instanceof WithStatement) {
			childNode = findChildNode(node, grammarAccess.getWithStatementAccess().getWithKeyword_0());
		} else if (eo instanceof SwitchStatement) {
			childNode = findChildNode(node, grammarAccess.getSwitchStatementAccess().getSwitchKeyword_0());
		} else if (eo instanceof ThrowStatement) {
			childNode = findChildNode(node, grammarAccess.getThrowStatementAccess().getThrowKeyword_0());
		} else if (eo instanceof TryStatement) {
			childNode = findChildNode(node, grammarAccess.getTryStatementAccess().getTryKeyword_0());
		} else if (eo instanceof DebuggerStatement) {
			childNode = findChildNode(node, grammarAccess.getDebuggerStatementAccess().getDebuggerKeyword_1());
		}
		Pair<Integer, Integer> offsetAndLength;
		if (childNode != null) {
			offsetAndLength = Pair.of(childNode.getOffset(), childNode.getLength());
		} else {
			offsetAndLength = Pair.of(node.getOffset(), node.getLength());
		}
		return offsetAndLength;
	}

	/**
	 * Returns the offset and length for a list feature.
	 *
	 * That is the offset of the first list element and the length from the first feature node to the last feature node
	 * (cf. {@link NodeModelUtils#findNodesForFeature(EObject, EStructuralFeature)}).
	 */
	public Pair<Integer, Integer> findListFeatureOffsetAndLength(EObject obj, EStructuralFeature feature) {
		List<INode> nodes = NodeModelUtils.findNodesForFeature(obj, feature);

		if (nodes.isEmpty()) {
			return Pair.of(0, 0);
		}

		int start = nodes.get(0).getOffset();
		int end = nodes.get(0).getOffset() + nodes.get(nodes.size() - 1).getLength();
		return Pair.of(start, end - start);
	}

	/**
	 * Adds an issues, just as {@link #addIssue} but marks all nodes associated with the multi-value feature
	 * {@code feature} as an error.
	 *
	 * For multi-value features (such as list attributes), this results in all list elements to be marked as error.
	 */
	protected void addIssueToMultiValueFeature(String message, EObject source, EStructuralFeature feature,
			String issueCode, String... issueData) {
		Pair<Integer, Integer> offsetAndLength = findListFeatureOffsetAndLength(source, feature);
		this.addIssue(message, source, offsetAndLength.getKey(), offsetAndLength.getValue(), issueCode, issueData);
	}

	private INode findChildNode(ICompositeNode compositeNode, EObject grammarElement) {
		for (INode childNode : compositeNode.getChildren()) {
			if (childNode.getGrammarElement() == grammarElement) {
				return childNode;
			}
			if (childNode instanceof ICompositeNode) {
				INode foundNode = findChildNode((ICompositeNode) childNode, grammarElement);
				if (foundNode != null) {
					return foundNode;
				}
			}
		}
		return null;
	}

	/** Assumes that members have same name. */
	protected boolean isFieldAccessorPair(TMember member, TMember member2) {
		MemberType mt1 = member.getMemberType();
		MemberType mt2 = member2.getMemberType();
		return (mt1 == MemberType.GETTER && mt2 == MemberType.SETTER) ||
				(mt1 == MemberType.SETTER && mt2 == MemberType.GETTER);
	}

	/** Assumes that members have same name. */
	protected boolean isFieldAccessorPair(Iterable<TMember> members) {
		Iterator<TMember> iter = members.iterator();
		if (!iter.hasNext())
			return false;
		TMember m1 = iter.next();
		if (!iter.hasNext())
			return false;
		TMember m2 = iter.next();
		if (iter.hasNext())
			return false;
		return isFieldAccessorPair(m1, m2);
	}

	public void addIssue(EObject source, IssueCodes issueCode, Object... msgValues) {
		super.addIssue(issueCode.getMessage(msgValues), source, issueCode.name());
	}

	public void addIssue(EObject source, EStructuralFeature feature, IssueCodes issueCode, Object... msgValues) {
		super.addIssue(issueCode.getMessage(msgValues), source, feature, issueCode.name());
	}

	public void addIssue(EObject source, int offset, int length, IssueItem issueItem) {
		super.addIssue(issueItem.message, source, offset, length, issueItem.getID(), issueItem.data);
	}

	public void addIssue(EObject source, EStructuralFeature feature, int index, IssueItem issueItem) {
		super.addIssue(issueItem.message, source, feature, index, issueItem.getID(), issueItem.data);
	}

	public void addIssue(EObject source, EStructuralFeature feature, IssueItem issueItem) {
		super.addIssue(issueItem.message, source, feature, issueItem.getID(), issueItem.data);
	}

	public void addIssue(EObject source, IssueItem issueItem) {
		super.addIssue(issueItem.message, source, null, issueItem.getID(), issueItem.data);
	}
}
