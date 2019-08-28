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
package org.eclipse.n4js.validation

import com.google.inject.Inject
import java.lang.reflect.Method
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.n4js.n4JS.AnnotableElement
import org.eclipse.n4js.n4JS.Block
import org.eclipse.n4js.n4JS.BreakStatement
import org.eclipse.n4js.n4JS.ContinueStatement
import org.eclipse.n4js.n4JS.DebuggerStatement
import org.eclipse.n4js.n4JS.DoStatement
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.ForStatement
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.GenericDeclaration
import org.eclipse.n4js.n4JS.IfStatement
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4JSFeatureUtils
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.n4JS.NamedElement
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.ReturnStatement
import org.eclipse.n4js.n4JS.SwitchStatement
import org.eclipse.n4js.n4JS.ThrowStatement
import org.eclipse.n4js.n4JS.TryStatement
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.n4JS.WhileStatement
import org.eclipse.n4js.n4JS.WithStatement
import org.eclipse.n4js.projectModel.IN4JSCore
import org.eclipse.n4js.services.N4JSGrammarAccess
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.MemberType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.UtilN4
import org.eclipse.n4js.validation.AbstractMessageAdjustingN4JSValidator.MethodWrapperCancelable
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.service.OperationCanceledManager
import org.eclipse.xtext.validation.AbstractDeclarativeValidator
import org.eclipse.xtext.validation.AbstractDeclarativeValidator.State

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Common base class for all N4JS validators. Provides some convenience methods and
 * internal validations. The latter are applied to AST nodes of different types and
 * therefore have to be reused in different concrete subclasses (for example,
 * {@link #internalCheckTypeArguments(Type,List,EObject,EStructuralFeature)}
 * is called from N4JSTypeValidator and N4JSExpressionValidator).
 */
public abstract class AbstractN4JSDeclarativeValidator extends AbstractMessageAdjustingN4JSValidator { // AbstractDeclarativeValidator {

	@Inject
	protected extension N4JSElementKeywordProvider keywordProvider;

	@Inject
	protected extension ValidatorMessageHelper validatorMessageHelper;

	@Inject
	private N4JSGrammarAccess grammarAccess
	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private IN4JSCore n4jsCore;
	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * @since 2.6
	 */
	override MethodWrapperCancelable createMethodWrapper(
		AbstractDeclarativeValidator instanceToUse,
		Method method
	) {
		return new MethodWrapperCancelable(instanceToUse, method) {

			override invoke(State state) {
				operationCanceledManager.checkCanceled(getCancelIndicator(state));
				if (shouldInvoke(state)) {
					super.invoke(state)
				}
			}

			/**
			 * Reasons to skip invocation of a validation method:
			 * <ul>
			 * <li>no resource associated to the current object</li>
			 * <li>the resource in question shouldn't be validated</li>
			 * </ul>
			 */
			private def boolean shouldInvoke(State state) {
				if (state.currentObject?.eResource !== null) {
					if (!n4jsCore.isNoValidate(state.currentObject.eResource.getURI())) {
						return true;
					}
				}
				return false;
			}

			override handleInvocationTargetException(Throwable targetException, State state) {

				// ignore GuardException, check is just not evaluated if guard is false
				// ignore NullPointerException, as not having to check for NPEs all the time is a convenience feature
				super.handleInvocationTargetException(targetException, state);
				if (targetException instanceof NullPointerException) {
					Exceptions.sneakyThrow(targetException)
				}
			}
		};
	}

	/* **************************************************************
	 * Internal Validations:
	 */
	/**
	 * Checks for (1) correct number of type arguments, (2) correct type of each type argument, and (3) consistency of
	 * use-site and definition-site variance (in case of wildcards).
	 * <p>
	 * Usually 'parameterizedElement' will be a TClass, TInterface, TMethod or TFunction.
	 * However, it might also be a TField, TGetter or TSetter to also support the corner cases
	 * of an invalid parameterized property access to a field, getter, setter in this method.
	 * It may be <code>null</code> if no type model element is available as the source of the
	 * type variables given in 'typeVars' (e.g. in case of a function type expression).
	 *
	 * @param typeVars  the type variables the provided type arguments are intended for.
	 * @param typeArgs  the type arguments to check.
	 * @param allowAutoInference  if true, it will be legal to provide no arguments, even if there are several
	 *                            type variables. Intended for cases where inference of type arguments is supported.
	 * @param parameterizedElement  the element defining the type variables in 'typeVars' or <code>null</code>
	 *                              if not available (e.g. in case of a FunctionTypeExpression). Will be used only
	 *                              for error reporting (to show a keyword and name, e.g. "for method myMethod").
	 * @param source   the AST node where the parameterized access occurs;
	 *                 used to derive the region of the error.
	 * @param feature  the feature in 'source' where the parameterized access occurs;
	 *                 used to derive the region of the error.
	 */
	def protected void internalCheckTypeArguments(List<? extends TypeVariable> typeVars,
		List<? extends TypeArgument> typeArgs, boolean allowAutoInference, IdentifiableElement parameterizedElement,
		EObject source, EStructuralFeature feature) {
		val typeParameterCount = typeVars.size
		val typeArgumentCount = typeArgs.size

		// if the AST location supports auto-inference of type arguments, allow for
		// not providing any type arguments, even if paramType is actually parameterized
		if (allowAutoInference && typeArgumentCount === 0) {
			return; // success!
		}

		// check for correct number of type arguments
		if (typeParameterCount !== typeArgumentCount) {
			if (source instanceof ParameterizedTypeRef && (source as ParameterizedTypeRef).isIterableTypeExpression) {
				val message = IssueCodes.
					getMessageForEXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ITERABLE_N_SYNTAX(BuiltInTypeScope.ITERABLE_N__MAX_LEN);
				addIssue(message, source, feature, IssueCodes.EXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ITERABLE_N_SYNTAX);
			} else if (parameterizedElement !== null && parameterizedElement.name !== null) {
				val message = IssueCodes.
					getMessageForEXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ELEMENT(parameterizedElement.keyword,
						parameterizedElement.name, typeParameterCount, typeArgumentCount);
				addIssue(message, source, feature, IssueCodes.EXP_WRONG_NUMBER_OF_TYPEARGS_FOR_ELEMENT);
			} else {
				val message = IssueCodes.
					getMessageForEXP_WRONG_NUMBER_OF_TYPEARGS(typeParameterCount, typeArgumentCount);
				addIssue(message, source, feature, IssueCodes.EXP_WRONG_NUMBER_OF_TYPEARGS);
			}
			return;
		}

		// check if type arguments adhere to type variables' bounds and variance (if any)
		val minTypeVariables = Math.min(typeParameterCount, typeArgumentCount);
		if (minTypeVariables !== 0) {
			// preparation: create rule environment for type variable substitution in upper bounds
			val G_subst = source.newRuleEnvironment;
			if(source instanceof ParameterizedPropertyAccessExpression) {
				val G = source.newRuleEnvironment;
				val targetTypeRef = ts.type(G, source.target); // note: not using G_subst here
				tsh.addSubstitutions(G_subst, targetTypeRef);
			}
			for (int i : 0 ..< typeArgs.size) {
				G_subst.addTypeMappingWithEnforcedCapture(typeVars.get(i), typeArgs.get(i), false);
			}
			// actually check provided type arguments
			for (int i : 0 ..< minTypeVariables) {
				val TypeVariable typeParameter = typeVars.get(i)
				val TypeArgument typeArgument = typeArgs.get(i);

				// check consistency of use-site and definition-site variance
				if(typeArgument instanceof Wildcard) {
					val defSiteVariance = typeParameter.variance;
					val useSiteVariance = if(typeArgument.declaredUpperBound!==null) {
						Variance.CO
					} else if(typeArgument.declaredLowerBound!==null) {
						Variance.CONTRA
					};
					if(defSiteVariance!==Variance.INV && useSiteVariance!==null
						&& useSiteVariance!==defSiteVariance) {
						// we've got an inconsistency!
						if(typeArgument.usingInOutNotation) {
							val message = IssueCodes.getMessageForEXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG_IN_OUT(
								useSiteVariance.getDescriptiveStringNoun(true),
								defSiteVariance.getDescriptiveStringNoun(true));
							addIssue(message, typeArgument, IssueCodes.EXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG_IN_OUT);
						} else {
							val message = IssueCodes.getMessageForEXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG(
								if(useSiteVariance===Variance.CO) "upper" else "lower",
								defSiteVariance.getDescriptiveString(true));
							addIssue(message, typeArgument, IssueCodes.EXP_INCONSISTENT_VARIANCE_OF_TYPE_ARG);
						}
					}
				}

				// check bounds
				val isExceptionCase = TypeUtils.isVoid(typeArgument); // in this case another validation will show an error (avoid duplicate messages)
				if(!isExceptionCase) {
					val typeParamUB = typeParameter.declaredUpperBound ?: N4JSLanguageUtils.getTypeVariableImplicitUpperBound(G_subst);
					val typeParamUBSubst = ts.substTypeVariablesWithoutCapture(G_subst, typeParamUB);
					val typeArgSubst = ts.substTypeVariablesWithoutCapture(G_subst, typeArgument);
					val result = ts.subtype(G_subst, typeArgSubst, typeParamUBSubst);
					if (result.failure) {
						createTypeError(result, typeArgument);
					}
				}
			}
		}

		/*
		 * Temporary debug code to show a warning if type arguments are explicitly specified at a location
		 * that supports type argument inference (mostly property access and call expressions).
		 */
//		if(allowAutoInference && typeParameterCount>0 && typeArgumentCount>0) {
//			getMessageAcceptor().acceptWarning(
//					"**** hopefully unnecessary use of explicit type arguments ****",
//					source, feature, INSIGNIFICANT_INDEX, IssueCodes.EXP_WRONG_NUMBER_OF_TYPEARGS);
//		}
	}

	/**
	 * Check whether every type parameter of a generic function or method declaration is actually used by that
	 * declaration.
	 *
	 * @param genericFunctionDeclaration the generic function or method declaration to check.
	 */
	protected def <T extends GenericDeclaration & FunctionDefinition> internalCheckNoUnusedTypeParameters(T genericFunctionOrMethod) {
		if (genericFunctionOrMethod.definedType === null)
			return;

		val TFunction functionType = genericFunctionOrMethod.definedType as TFunction;
		internalCheckNoUnusedTypeParameters(genericFunctionOrMethod, genericFunctionOrMethod.typeVars, functionType.typeVars);
	}

	/**
	 * Check whether every type parameter of a generic function type expression is actually used by the
	 * declared function type.
	 *
	 * @param functionTypeExp the generic function type to check.
	 */
	protected def internalCheckNoUnusedTypeParameters(FunctionTypeExpression functionTypeExp) {
		if (functionTypeExp.declaredType === null)
			return;

		val TFunction declaredType = functionTypeExp.declaredType;
		internalCheckNoUnusedTypeParameters(functionTypeExp, functionTypeExp.ownedTypeVars, declaredType.typeVars);
	}

	/**
	 * Check whether every type variable that is present in two lists is references anywhere in a subtree.
	 * Thereby the first list contains the AST nodes for the type variables whereas the second list contains
	 * the corresponding elements from the type model. The AST nodes are required for attaching the generated
	 * issues to the correct nodes, whereas the type model elements are needed for the actual usage check.
	 *
	 * We assume that each pair of an actual type variable (AST node) and a declared type variable (type model
	 * element) that share an index in both lists have the same name. If the lists are not of equal length,
	 * then this assumption only applies to the elements up to, but not including, the length of the shorter
	 * list. In other words, we assume that one of the two given lists is a prefix of the other (given that
	 * we consider actual and declared type variables to be equal if they have the same name), and we only
	 * check this prefix.
	 *
	 * Finally we add an issue for each type variable that is found to be unreferenced.
	 *
	 * @param root the root of the subtree to search for references to the given type variables
	 * @param actualTypeVars the actual type variables from the AST
	 * @param declaredTypeVars the declared type variables from the type model
	 */
	private def internalCheckNoUnusedTypeParameters(EObject root, EList<TypeVariable> actualTypeVars, EList<TypeVariable> declaredTypeVars) {
		val int typeVarCount = Math.min(actualTypeVars.size, declaredTypeVars.size)
		if (typeVarCount == 1) {
			// Since this is a very common case, we want it to be as fast as possible. Therefore we don't
			// build a set of all reference type variables and do the check once directly.
			val TypeVariable actualTypeVar = actualTypeVars.get(0)
			val TypeVariable declaredTypeVar = declaredTypeVars.get(0)
			if (!TypeUtils.isOrContainsRefToTypeVar(root, declaredTypeVar)) {
				addIssue(IssueCodes.getMessageForFUN_UNUSED_GENERIC_TYPE_PARAM(actualTypeVar.name), actualTypeVar, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME, IssueCodes.FUN_UNUSED_GENERIC_TYPE_PARAM);
			}
		} else if (typeVarCount > 1) {
			// In this case, we avoid repeatedly traversing the tree with the given root by getting a set of
			// all type variables it references up front and using that to perform our check.
			val referencedTypeVars = TypeUtils.getReferencedTypeVars(root);
			for (var int i = 0; i < typeVarCount; i++) {
				val TypeVariable actualTypeVar = actualTypeVars.get(i)
				val TypeVariable declaredTypeVar = declaredTypeVars.get(i)

				if (!referencedTypeVars.contains(declaredTypeVar)) {
					addIssue(IssueCodes.getMessageForFUN_UNUSED_GENERIC_TYPE_PARAM(actualTypeVar.name), actualTypeVar, TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME, IssueCodes.FUN_UNUSED_GENERIC_TYPE_PARAM);
				}
			}
		}
	}

	/**
	 * Finds first inheritance cycle in the inheritance hierarchy above 'classifier'. Consequential errors are
	 * omitted, that is, if a class X extends A, and if A extends B and B extends A, then no error is reported
	 * for X as it is not part of the cycle itself.
	 *
	 * @return a non-empty list of TClassifiers forming a cyclic path in the inheritance hierarchy above
	 *         'classifier' or <code>null</code> if no such cycle exists. Never returns a list with a length
	 *         below 2.
	 */
	def protected List<TClassifier> findCyclicInheritance(TClassifier classifier) {
		return UtilN4.findCycleInDirectedGraph(classifier,[
			if(it instanceof TClass) {
				// this case is only a performance tweak:
				// for classes we do not have to check the implemented interfaces, because interfaces cannot
				// extend classes; so all we could find via the implemented interfaces of a class are cycles
				// within the interfaces themselves not including 'it', but these kind of cycles will not be
				// returned by method #findCycleInDirectedGraph() anyway (see API doc)!
				if(it.superClassRef?.declaredType instanceof TClass)
					#[ it.superClassRef.declaredType as TClassifier ]
				else
					#[]
			}
			else {
				// general case:
				it.superClassifierRefs.map[declaredType].filter(TClassifier)
			}
		]);
	}

	/* **************************************************************
	 * Helper methods.
	 */
	/**
	 * Convenience method, returns true if given member is a constructor (method).
	 */
	static def isConstructor(TMember tMember) {
		return if (tMember instanceof TMethod) tMember.constructor else false
	}

	def isAbstract(TClassifier classifier) {
		switch (classifier) {
			TClass:
				classifier.abstract
			// TODO: review if we use property or validation
			/*
 * Interfaces are abstract by default and this cannot be changed by user,
 * therefore N4jS grammar doesn't allow 'abstract' keyword on N4InterfaceDeclaration
 * Question is if we use properties only for things user can affect?
 * if yes -> abstract property is not in interface type, and we depend on this validation
 * if no -> abstract property must be added to interface type, types builder needs to be adjusted,
 * 			this helper needs to query types for property
 */
			TInterface:
				true
			default:
				false
		}
	}

	def isDeclaredAbstract(TMember member) {
		switch (member) {
			TMethod: member.declaredAbstract
			TGetter: member.declaredAbstract
			TSetter: member.declaredAbstract
			default: false
		}
	}

	def getBody(N4MemberDeclaration n4MemberDeclaration) {
		switch (n4MemberDeclaration) {
			N4MethodDeclaration: n4MemberDeclaration.body
			N4GetterDeclaration: n4MemberDeclaration.body
			N4SetterDeclaration: n4MemberDeclaration.body
			default: null
		}
	}

	def hasAnnotation(AnnotableElement elem, String annotationName) {
		elem.annotations.exists[name == annotationName]
	}

	/*
	 * Provides default implementations for error, warning and info creation in order to throw exceptions as these methods
	 * are not to be called in N4JS. Instead, addIssue is to be used, the severity is retrieved from the message file, see
	 * NLSProcessor for details.
	 */
	def override error(String message, EObject source, EStructuralFeature feature, int index, String code,
		String... issueData) {
		throw new UnsupportedOperationException("Don't use error(...) anymore, but addIssue");
	}

	def override warning(String message, EObject source, EStructuralFeature feature, int index, String code,
		String... issueData) {
		throw new UnsupportedOperationException("Don't use warning(...) anymore, but addIssue");
	}

	def override info(String message, EObject source, EStructuralFeature feature, int index, String code,
		String... issueData) {
		throw new UnsupportedOperationException("Don't use info(...) anymore, but addIssue");
	}

	/*
	 * Finds the closes name feature, either of the object itself, or of a sibling or parent.
	 * This is basically used for error markers.
	 */
	def Pair<? extends EObject, ? extends EStructuralFeature> findNameFeature(EObject eo) {
		if (eo instanceof NamedElement) {
			val attribute = N4JSFeatureUtils.attributeOfNameFeature(eo);
			if (attribute !== null) {
				return eo -> attribute;
			}
		}

		var eObjectToNameFeature = if (eo instanceof ExportDeclaration) {
				findNameFeature(eo.exportedElement)
			} else if (eo instanceof IdentifiableElement) {
				eo -> TypesPackage.Literals.IDENTIFIABLE_ELEMENT__NAME
			} else if (eo instanceof VariableStatement) {
				val varDecl = eo.varDecl.head
				findNameFeature(varDecl)
			} else {
				null
			}

		return eObjectToNameFeature;
	}

	/*
	 * Returns range which can be used for error markers, this is usually only a fall back if no
	 * name feature can be used.
	 */
	def Pair<Integer, Integer> findOffsetAndLength(EObject eo) {
		val node = NodeModelUtils.findActualNodeFor(eo)
		val childNode = if (eo instanceof Block) {
				node.findChildNode(grammarAccess.blockAccess.leftCurlyBracketKeyword_0_0_1)
			} else if (eo instanceof IfStatement) {
				node.findChildNode(grammarAccess.ifStatementAccess.ifKeyword_0)
			} else if (eo instanceof DoStatement) {
				node.findChildNode(grammarAccess.doStatementAccess.doKeyword_0)
			} else if (eo instanceof WhileStatement) {
				node.findChildNode(grammarAccess.whileStatementAccess.whileKeyword_0)
			} else if (eo instanceof ForStatement) {
				node.findChildNode(grammarAccess.forStatementAccess.forKeyword_1)
			} else if (eo instanceof ContinueStatement) {
				node.findChildNode(grammarAccess.continueStatementAccess.continueKeyword_1)
			} else if (eo instanceof BreakStatement) {
				node.findChildNode(grammarAccess.breakStatementAccess.breakKeyword_1)
			} else if (eo instanceof ReturnStatement) {
				node.findChildNode(grammarAccess.returnStatementAccess.returnKeyword_1)
			} else if (eo instanceof WithStatement) {
				node.findChildNode(grammarAccess.withStatementAccess.withKeyword_0)
			} else if (eo instanceof SwitchStatement) {
				node.findChildNode(grammarAccess.switchStatementAccess.switchKeyword_0)
			} else if (eo instanceof ThrowStatement) {
				node.findChildNode(grammarAccess.throwStatementAccess.throwKeyword_0)
			} else if (eo instanceof TryStatement) {
				node.findChildNode(grammarAccess.tryStatementAccess.tryKeyword_0)
			} else if (eo instanceof DebuggerStatement) {
				node.findChildNode(grammarAccess.debuggerStatementAccess.debuggerKeyword_1)
			};
		val offsetAndLength = if (childNode !== null) {
				childNode.offset -> childNode.length
			} else {
				node.offset -> node.length
			};
		offsetAndLength
	}
	
	/**
	 * Returns the offset and length for a list feature.
	 * 
	 * That is the offset of the first list element and the length
	 * from the first feature node to the last feature node (cf. {@link NodeModelUtils#findNodeForFeature}).
	 */
	def Pair<Integer, Integer> findListFeatureOffsetAndLength(EObject obj, EStructuralFeature feature) {
		val nodes = NodeModelUtils.findNodesForFeature(obj, feature);
		
		if (nodes.empty) {
			return 0 -> 0;
		}
		
		val start = nodes.head.offset;
		val end = nodes.last.offset + nodes.last.length;
		return start -> end - start;
	}
	
	/**
	 * Adds an issues, just as {@link #addIssue} but marks all nodes associated with the multi-value feature {@code feature}
	 * as an error.
	 * 
	 * For multi-value features (such as list attributes), this results in all list elements to be marked as error.
	 */
	protected def void addIssueToMultiValueFeature(String message, EObject source, EStructuralFeature feature, String issueCode, String... issueData) {
		val offsetAndLength = findListFeatureOffsetAndLength(source, feature);
			this.addIssue(message, source, offsetAndLength.key, offsetAndLength.value, issueCode, issueData);
	}
	
	def private INode findChildNode(ICompositeNode compositeNode, EObject grammarElement) {
		for (childNode : compositeNode.children) {
			if (childNode.grammarElement == grammarElement) {
				return childNode
			}
			if (childNode instanceof ICompositeNode) {
				val foundNode = findChildNode(childNode, grammarElement)
				if (foundNode !== null) {
					return foundNode
				}
			}
		}
	}

	/** Assumes that members have same name.  */
	def protected boolean isFieldAccessorPair(TMember member, TMember member2) {
		val mt1 = member.memberType;
		val mt2 = member2.memberType;
		return (mt1 === MemberType.GETTER && mt2 === MemberType.SETTER) ||
			(mt1 === MemberType.SETTER && mt2 === MemberType.GETTER)
	}

	/** Assumes that members have same name.  */
	def protected boolean isFieldAccessorPair(Iterable<TMember> members) {
		val iter = members.iterator;
		if (!iter.hasNext) return false;
		val m1 = iter.next;
		if (!iter.hasNext) return false;
		val m2 = iter.next;
		if (iter.hasNext) return false;
		return isFieldAccessorPair(m1, m2)
	}

}
