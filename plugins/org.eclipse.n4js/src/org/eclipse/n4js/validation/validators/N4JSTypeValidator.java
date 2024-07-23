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

import static org.eclipse.n4js.ts.typeRefs.TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.functionType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getInconsistentSubstitutions;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getTypeMappingKeys;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.globalObjectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.recordInconsistentSubstitutions;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.voidType;
import static org.eclipse.n4js.validation.IssueCodes.AST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF;
import static org.eclipse.n4js.validation.IssueCodes.AST_NO_TYPE_ARGS_IN_CLASSIFIERTYPEREF;
import static org.eclipse.n4js.validation.IssueCodes.AST_THIS_WRONG_PLACE;
import static org.eclipse.n4js.validation.IssueCodes.BIT_SYMBOL_INVALID_USE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_IMPLEMENT_EXTEND_SAME_INTERFACE_INCONSISTENTLY;
import static org.eclipse.n4js.validation.IssueCodes.CLF_SPEC_SUPERFLUOUS_PROPERTIES;
import static org.eclipse.n4js.validation.IssueCodes.CLF_SUPERFLUOUS_PROPERTIES;
import static org.eclipse.n4js.validation.IssueCodes.CLF_UPPER_BOUND_FINAL;
import static org.eclipse.n4js.validation.IssueCodes.EXP_USE_OF_UNDEF_EXPR;
import static org.eclipse.n4js.validation.IssueCodes.INTER_ANY_USED;
import static org.eclipse.n4js.validation.IssueCodes.INTER_ONLY_ONE_CLASS_ALLOWED;
import static org.eclipse.n4js.validation.IssueCodes.INTER_REDUNDANT_SUPERTYPE;
import static org.eclipse.n4js.validation.IssueCodes.INTER_TYEPARGS_ONLY_ONE_CLASS_ALLOWED;
import static org.eclipse.n4js.validation.IssueCodes.INTER_WITH_ONE_GENERIC;
import static org.eclipse.n4js.validation.IssueCodes.TYP_TYPE_PARAM_DEFAULT_NOT_SUBTYPE_OF_BOUND;
import static org.eclipse.n4js.validation.IssueCodes.TYP_TYPE_PARAM_DEFAULT_REFERENCES_LATER_TYPE_PARAM;
import static org.eclipse.n4js.validation.IssueCodes.TYP_TYPE_PARAM_MANDATORY_AFTER_OPTIONAL;
import static org.eclipse.n4js.validation.IssueCodes.TYS_COMPOUND_MISSING_GETTER;
import static org.eclipse.n4js.validation.IssueCodes.TYS_MISSING;
import static org.eclipse.n4js.validation.IssueCodes.TYS_NO_SUPERTYPE_WRITE_ACCESS;
import static org.eclipse.n4js.validation.IssueCodes.TYS_PRIMITIVE_TYPE_DYNAMIC;
import static org.eclipse.n4js.validation.IssueCodes.TYS_STRUCTURAL_PRIMITIVE;
import static org.eclipse.n4js.validation.IssueCodes.UNI_ANY_USED;
import static org.eclipse.n4js.validation.IssueCodes.UNI_REDUNDANT_SUBTYPE;
import static org.eclipse.n4js.validation.IssueCodes.VIS_TYPE_PARAMETER_HIDES_TYPE;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.forall;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AssignmentOperator;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionAnnotationList;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.UnaryOperator;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.scoping.members.TypingStrategyFilter;
import org.eclipse.n4js.scoping.utils.ExpressionExtensions;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef;
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef;
import org.eclipse.n4js.ts.typeRefs.Wildcard;
import org.eclipse.n4js.ts.types.AnyType;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.VoidType;
import org.eclipse.n4js.ts.types.util.MemberList;
import org.eclipse.n4js.ts.types.util.Variance;
import org.eclipse.n4js.types.utils.TypeCompareHelper;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper.Newable;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;

import com.google.common.base.Optional;
import com.google.common.collect.ArrayListMultimap;
import com.google.inject.Inject;

/**
 * Class for validating the N4JS types.
 */
@SuppressWarnings("javadoc")
public class N4JSTypeValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private TypeCompareHelper typeCompareHelper;

	@Inject
	private N4JSScopeProvider n4jsScopeProvider;

	@Inject
	protected ContainerTypesHelper containerTypesHelper;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	@Inject
	private WorkspaceAccess workspaceAccess;

	/**
	 * NEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Validates all generic type variable declarations. Raises validation error for each type variable if any of their
	 * declared upper bound is a primitive type.
	 * <p>
	 * IDEBUG-185
	 *
	 * @param declaration
	 *            the generic declaration to check the upper bound declarations of its type variables.
	 */
	@Check
	public void checkGenericDeclarationType(GenericDeclaration declaration) {
		TClass functionType = functionType(newRuleEnvironment(declaration));

		for (N4TypeVariable typeVar : declaration.getTypeVars()) {
			TypeRef ub = typeVar.getDeclaredUpperBound();
			if (ub != null) {
				Type declType = ub.getDeclaredType();
				if ((declType instanceof ContainerType<?> || declType instanceof PrimitiveType) && declType.isFinal()) {
					if (declType == functionType) {
						// important exception (until function type expressions are supported as bounds):
						// class C<T extends Function> {} makes sense even though Function is final
						return;
					}
					TypeRef ubInAST = typeVar.getDeclaredUpperBoundNode().getTypeRefInAST(); // never 'null' because
																								// 'typeVar.declaredUpperBound'
																								// returned non-null
																								// value
					addIssue(ubInAST, PARAMETERIZED_TYPE_REF__DECLARED_TYPE,
							CLF_UPPER_BOUND_FINAL.toIssueItem(declType.getName(), typeVar.getName()));
				}
			}
		}
	}

	@Check
	public void checkTModuleCreated(Script script) {
		if (script.getModule() == null) {
			ICompositeNode rootNode = NodeModelUtils.getNode(script);
			if (rootNode != null) {
				addIssue(script, rootNode.getOffset(), rootNode.getLength(), TYS_MISSING.toIssueItem());
			}
		}
	}

	@Check
	public void checkParameterizedTypeRef(ParameterizedTypeRef paramTypeRefInAST) {
		Type declaredType = paramTypeRefInAST.getDeclaredType();
		if (declaredType == null || declaredType.eIsProxy()) {
			return;
		}

		boolean isInTypeTypeRef = paramTypeRefInAST.eContainer() instanceof TypeTypeRef
				|| (paramTypeRefInAST.eContainer() instanceof Wildcard
						&& paramTypeRefInAST.eContainer().eContainer() instanceof TypeTypeRef);

		if (isInTypeTypeRef) {
			internalCheckValidTypeInTypeTypeRef(paramTypeRefInAST);
		} else {
			internalCheckTypeArguments(declaredType.getTypeVars(), paramTypeRefInAST.getDeclaredTypeArgs(),
					Optional.absent(), false,
					declaredType, paramTypeRefInAST, TypeRefsPackage.eINSTANCE.getParameterizedTypeRef_DeclaredType());
		}
		internalCheckDynamic(paramTypeRefInAST);

		internalCheckStructuralPrimitiveTypeRef(paramTypeRefInAST);
	}

	/**
	 * Add an issue if explicit use of structural type operator with a primitive type is detected.
	 */
	private void internalCheckStructuralPrimitiveTypeRef(ParameterizedTypeRef typeRefInAST) {
		if (typeRefInAST.getTypingStrategy() != TypingStrategy.NOMINAL
				&& !N4JSLanguageUtils.mayBeReferencedStructurally(typeRefInAST.getDeclaredType())) {
			addIssue(typeRefInAST, TYS_STRUCTURAL_PRIMITIVE.toIssueItem());
		}
	}

	private void internalCheckValidTypeInTypeTypeRef(ParameterizedTypeRef paramTypeRefInAST) {
		// IDE-785 uses ParamterizedTypeRefs in ClassifierTypeRefs. Currently Type Arguments are not supported in
		// ClassifierTypeRefs, so
		// we actively forbid them here. Will be loosened for IDE-1310
		if (!paramTypeRefInAST.getDeclaredTypeArgs().isEmpty()) {
			addIssue(paramTypeRefInAST, AST_NO_TYPE_ARGS_IN_CLASSIFIERTYPEREF.toIssueItem());
		} else if (paramTypeRefInAST instanceof FunctionTypeRef) {
			addIssue(paramTypeRefInAST, AST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF.toIssueItem());
		} else if (paramTypeRefInAST.getDeclaredType() instanceof TFunction) {
			addIssue(paramTypeRefInAST, AST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF.toIssueItem());
		}
	}

	@Check
	public void checkThisTypeRef(ThisTypeRef thisTypeRefInAST) {
		if (thisTypeRefInAST instanceof BoundThisTypeRef) {
			// the below validations do not apply to BoundThisTypeRefs
			// (note: normally, BoundThisTypeRefs should never appear in the AST, anyway; but asserting this to be true
			// is not the job of this validation)
			return;
		}
		BoundThisTypeRef bttr = (BoundThisTypeRef) thisTypeRefInAST;
		if (!(isUsedStructurallyAsFormalParametersInTheConstructor(bttr)
				|| isUsedAtCovariantPositionInClassifierDeclaration(bttr)
				|| isUsedInVariableWithSyntaxError(bttr))) {
			addIssue(bttr, AST_THIS_WRONG_PLACE.toIssueItem());
		}
	}

	private boolean isUsedStructurallyAsFormalParametersInTheConstructor(ThisTypeRef thisTypeRefInAST) {
		if (thisTypeRefInAST.isUseSiteStructuralTyping()) {
			EObject node = thisTypeRefInAST.eContainer();
			if (node instanceof TypeReferenceNode<?>) {
				EObject fpar = node.eContainer();
				if (fpar instanceof FormalParameter) {
					EObject ctor = fpar.eContainer();
					if (ctor instanceof N4MethodDeclaration) {
						if (((N4MethodDeclaration) ctor).isConstructor()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean isUsedAtCovariantPositionInClassifierDeclaration(ThisTypeRef thisTypeRefInAST) {
		N4ClassifierDeclaration classifierDecl = EcoreUtil2.getContainerOfType(thisTypeRefInAST,
				N4ClassifierDeclaration.class);
		if (classifierDecl != null) {
			// exception: disallow for static members of interfaces
			if (classifierDecl instanceof N4InterfaceDeclaration) {
				N4MemberDeclaration memberDecl = EcoreUtil2.getContainerOfType(thisTypeRefInAST,
						N4MemberDeclaration.class);
				if (memberDecl != null && memberDecl.isStatic()) {
					return false;
				}
			}
			Variance varianceOfPos = N4JSLanguageUtils.getVarianceOfPosition(thisTypeRefInAST);
			if (varianceOfPos != null) {
				return varianceOfPos == Variance.CO;
			}
		}
		return false;
	}

	private boolean isUsedInVariableWithSyntaxError(ThisTypeRef thisTypeRefInAST) {
		EObject parent = thisTypeRefInAST.eContainer();
		if (parent instanceof TypeReferenceNode<?>) {
			EObject grandParent = parent.eContainer();
			if (grandParent instanceof VariableDeclaration) {
				return ((VariableDeclaration) grandParent).getName() == null;
			}
		}
		return false;
	}

	@Check
	public void checkSymbolReference(TypeRef typeRefInAST) {
		BuiltInTypeScope bits = BuiltInTypeScope.get(typeRefInAST.eResource().getResourceSet());
		RuleEnvironment G = newRuleEnvironment(typeRefInAST);
		TypeRef typeRef = tsh.resolveTypeAliasFlat(G, typeRefInAST);
		if (typeRef.getDeclaredType() == bits.getSymbolObjectType()) {
			// we have a type reference to 'Symbol'
			boolean isAllowed = isExtendsClauseInRuntimeLibrary(typeRefInAST);
			if (!isAllowed) {
				addIssue(typeRefInAST, BIT_SYMBOL_INVALID_USE.toIssueItem());
			}
		}
	}

	private boolean isExtendsClauseInRuntimeLibrary(TypeRef typeRefInAST) {
		if (typeRefInAST.eContainer() != null && typeRefInAST.eContainer()
				.eContainmentFeature() == N4JSPackage.Literals.N4_CLASS_DEFINITION__SUPER_CLASS_REF) {
			N4JSProjectConfigSnapshot project = workspaceAccess.findProjectContaining(typeRefInAST);
			if (project != null && project.getType() == ProjectType.RUNTIME_LIBRARY) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Constraints 08: primitive types except any must not be declared dynamic
	 */
	private void internalCheckDynamic(ParameterizedTypeRef refInAST) {
		if (refInAST.isDynamic()) {
			Type t = refInAST.getDeclaredType();
			if (!N4JSLanguageUtils.mayBeReferencedDynamically(t)) {
				addIssue(refInAST, TYS_PRIMITIVE_TYPE_DYNAMIC.toIssueItem(t.getName()));
			}
		}
	}

	@Check
	public void checkTypeHiddenByTypeVariable(GenericDeclaration genDecl) {
		// TODO reconsider this warning or its implementation; re-generating the scope can become quite expensive
		// (but note that moving this to the scoping code is not trivial, because warning has to be generated also
		// if no references to the type parameter are made!)
		if (!genDecl.getTypeVars().isEmpty()) {
			boolean staticAccess = genDecl instanceof N4MemberDeclaration && ((N4MemberDeclaration) genDecl).isStatic();
			IScope scope = n4jsScopeProvider.getTypeScope( // note: calling #getTypeScope() here, NOT #getScope()!
					genDecl.eContainer(), // use container, because we do not want to see type variables we are
											// currently validating
					staticAccess,
					false);

			for (N4TypeVariable it : genDecl.getTypeVars()) {
				if (!com.google.common.base.Strings.isNullOrEmpty(it.getName())) {
					IEObjectDescription hiddenTypeDscr = scope.getSingleElement(QualifiedName.create(it.getName()));
					EObject hiddenType = hiddenTypeDscr == null ? null : hiddenTypeDscr.getEObjectOrProxy();
					if (hiddenType instanceof Type &&
							!(IEObjectDescriptionWithError.isErrorDescription(hiddenTypeDscr))) {
						addIssue(it, VIS_TYPE_PARAMETER_HIDES_TYPE.toIssueItem(it.getName(),
								keywordProvider.keyword(hiddenType)));
					}
				}
			}
		}
	}

	/**
	 * This handles a special case that is not checked by method {@link #checkTypeMatchesExpectedType(Expression)}. In a
	 * compound assignment like += or *=, the left-hand side is both read from and written to. So we have to check 1)
	 * that the type for write access is correct and 2) the type of read access is correct. Usually these two types are
	 * the same, but in case of a getter/setter pair they can be different and need to be checked individually. Case 1)
	 * is taken care of by method {@link #checkTypeMatchesExpectedType(Expression)}. Case 2) is checked here in this
	 * method.
	 */
	@Check
	public void checkCompoundAssignmentGetterSetterClashOnLhs(AssignmentExpression assExpr) {
		if (assExpr.getOp() == null || assExpr.getOp() == AssignmentOperator.ASSIGN) {
			return; // following code is only required for compound assignments, i.e. +=, *=, etc.; not for =
		}
		Expression lhs = assExpr.getLhs();
		if (lhs instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) lhs;
			IdentifiableElement prop = ppae.getProperty();

			// in case of a getter/setter pair, scoping will here always give us the setter as property,
			// because we are on the left-hand side of an assignment (i.e. write access)
			if (prop instanceof TSetter) {

				// ok, we have the situation we are interested in (setter on LHS of compound assignment)
				// --> now check if there is a matching getter of correct type
				TSetter setter = (TSetter) prop;
				TypeRef ppaeTgt = ts.tau(ppae.getTarget());
				Type targetType = ppaeTgt == null ? null : ppaeTgt.getDeclaredType();
				if (targetType instanceof ContainerType<?>) {
					TMember m = containerTypesHelper.fromContext(assExpr).findMember((ContainerType<?>) targetType,
							setter.getName(),
							false, setter.isStatic());

					if (m == null) {

						// no getter at all
						addIssue(assExpr.getLhs(), TYS_COMPOUND_MISSING_GETTER.toIssueItem());
					} else if (m instanceof TGetter) {
						TGetter getter = (TGetter) m;
						RuleEnvironment G = newRuleEnvironment(assExpr);

						// must use .rhs in next line, because .lhs would give us the expected type for write access
						// (which is already checked by the generic method #checkTypeMatchesExpectedType()
						TypeRef expectedType = ts.expectedType(G, assExpr, assExpr.getRhs());
						TypeRef typeOfGetterRAW = TypeUtils.getMemberTypeRef(getter);
						if (expectedType != null && typeOfGetterRAW != null) {
							TypeRef typeOfGetter = ts.substTypeVariables(G, typeOfGetterRAW);
							if (typeOfGetter != null) {
								Result result = ts.subtype(G, typeOfGetter, expectedType);
								createTypeError(result, assExpr.getLhs());
							}
						}
					}
				}
			}
		}
	}

	@Check
	public void checkInconsistentInterfaceImplementationOrExtension(N4ClassifierDeclaration classifierDecl) {
		TClassifier tClassifier = (TClassifier) classifierDecl.getDefinedType();
		if (tClassifier == null) {
			return; // broken AST
		}
		RuleEnvironment G = newRuleEnvironment(classifierDecl);
		recordInconsistentSubstitutions(G);

		for (ParameterizedTypeRef it : tClassifier.getSuperClassifierRefs()) {
			tsh.addSubstitutions(G, it);
		}

		for (TypeVariable tv : getTypeMappingKeys(G)) {
			if (!tv.isDeclaredCovariant() && !tv.isDeclaredContravariant()) {
				TypeRef subst = ts.substTypeVariables(G, TypeUtils.createTypeRef(tv));
				if (subst instanceof UnknownTypeRef) {
					List<TypeRef> badSubst = getInconsistentSubstitutions(G, tv);
					if (!badSubst.isEmpty()) {
						if (!tsh.allEqualType(G, badSubst.toArray(new TypeRef[0]))) {
							String mode = (tClassifier instanceof TClass) ? "implement" : "extend";
							String ifcName = ((TInterface) tv.eContainer()).getName();
							String tvName = "invariant " + tv.getName();
							String typeRefsStr = Strings.join(", ", map(badSubst, tr -> tr.getTypeRefAsString()));
							IssueItem issueItem = CLF_IMPLEMENT_EXTEND_SAME_INTERFACE_INCONSISTENTLY.toIssueItem(mode,
									ifcName, tvName, typeRefsStr);
							addIssue(classifierDecl, N4JSPackage.eINSTANCE.getN4TypeDeclaration_Name(), issueItem);
						}
					}
				}
			}
		}
	}

	/**
	 * This tests ALL expressions, including expressions used on right hand side of assignments or property definitions.
	 * That is, there is no need to test field declarations or property declarations separately, as this will lead to
	 * duplicate error messages.
	 */
	@Check
	public void checkTypeMatchesExpectedType(Expression expression) {

		if (!jsVariantHelper.requireCheckTypeMatchesExpectedType(expression)) {
			return;
		}

		if (expression instanceof ExpressionAnnotationList) {
			// expressionAnnotationList occur on function- and class-expressions.
			// checking of the content is done in N4JSAnnotationValidation
			return;
		}

		RuleEnvironment G = newRuleEnvironment(expression);
		TypeRef inferredType = ts.type(G, expression);
		if (inferredType instanceof UnknownTypeRef) {
			return;
		}

		// use a fresh environment for expectations
		G = newRuleEnvironment(expression);

		TypeRef expectedTypeRef = ts.expectedType(G, expression.eContainer(), expression);
		if (expectedTypeRef != null) {

			if (expression instanceof ParenExpression) {
				TypeRef expectedTypeRefInner = ts.expectedType(G, expression,
						((ParenExpression) expression).getExpression());
				if (expectedTypeRefInner != null && ts.equaltypeSucceeded(G, expectedTypeRef, expectedTypeRefInner)) {
					// skip further checks to avoid double reporting of issues
					return;
				}
			}

			// for certain problems in single-expression arrow functions, we want a special error message
			ArrowFunction singleExprArrowFunction = N4JSASTUtils.getContainingSingleExpressionArrowFunction(expression);
			if (singleExprArrowFunction != null && TypeUtils.isVoid(inferredType)) {
				if (TypeUtils.isVoid(expectedTypeRef) || singleExprArrowFunction.isReturnValueOptional()) {
					return; // all good
				}
				if (singleExprArrowFunction.getDeclaredReturnTypeRefInAST() == null) { // show specialized error message
																						// only if return type of arrow
																						// function was inferred (i.e.
																						// not declared explicitly)
					addIssue(expression,
							IssueCodes.FUN_SINGLE_EXP_LAMBDA_IMPLICIT_RETURN_ALLOWED_UNLESS_VOID.toIssueItem());
					return;
				}
			}

			internalCheckUseOfUndefinedExpression(G, expression, expectedTypeRef, inferredType);

			boolean writeAccess = ExpressionExtensions.isLeftHandSide(expression);
			if (writeAccess) {

				// special case: write access
				Result result = ts.subtype(G, expectedTypeRef, inferredType);

				if (result.isFailure()) {
					// use custom error message, because otherwise it will be completely confusing
					IssueItem issueItem = TYS_NO_SUPERTYPE_WRITE_ACCESS
							.toIssueItem(expectedTypeRef.getTypeRefAsString(), inferredType.getTypeRefAsString());
					addIssue(expression, issueItem);
				}
			} else {

				// standard case: read access
				Result result = ts.subtype(G, inferredType, expectedTypeRef);
				// not working, as primitive types are not part of currently validated resource:
				// errorGenerator.generateErrors(this, result, expression)
				// so we create error here differently:
				boolean errorCreated = createTypeError(result, expression);

				if (!errorCreated) {
					internalCheckSuperfluousPropertiesInObjectLiteralRek(G, expectedTypeRef, expression);
				}
			}
		}
	}

	private void internalCheckSuperfluousPropertiesInObjectLiteralRek(RuleEnvironment G, TypeRef expectedTypeRef,
			Expression expression) {
		if (expression instanceof ObjectLiteral) {
			internalCheckSuperfluousPropertiesInObjectLiteral(G, expectedTypeRef, (ObjectLiteral) expression);

		} else if (expression instanceof ArrayLiteral) {
			List<TypeRef> expectedElemTypeRefs = tsh.extractIterableElementTypes(G, expectedTypeRef);
			if (!expectedElemTypeRefs.isEmpty()) {
				// we have Iterable, Array, IterableN, ArrayN or a subtype thereof
				TypeRef cachedLastElementTypeRefUB = null;
				int expectedElemTypeRefsCount = expectedElemTypeRefs.size();
				EList<ArrayElement> elems = ((ArrayLiteral) expression).getElements();
				int elemsCount = elems.size();
				for (int i = 0; i < elemsCount; i++) {
					Expression currElemExpr = elems.get(i) == null ? null : elems.get(i).getExpression();
					TypeRef currExpectedElemTypeRefUB;
					if (i < expectedElemTypeRefsCount - 1) {
						currExpectedElemTypeRefUB = ts.upperBoundWithReopenAndResolveTypeVars(G,
								expectedElemTypeRefs.get(i));
					} else {
						if (cachedLastElementTypeRefUB == null) {
							cachedLastElementTypeRefUB = ts.upperBoundWithReopenAndResolveTypeVars(G,
									expectedElemTypeRefs.get(expectedElemTypeRefsCount - 1));
						}
						currExpectedElemTypeRefUB = cachedLastElementTypeRefUB;
					}
					if (currElemExpr != null) {
						internalCheckSuperfluousPropertiesInObjectLiteralRek(G, currExpectedElemTypeRefUB,
								currElemExpr);
					}
				}
			}
		}
	}

	/**
	 * #225: always check for superfluous properties in object literal req-id IDE-22501
	 */
	private void internalCheckSuperfluousPropertiesInObjectLiteral(RuleEnvironment G, TypeRef typeRef,
			ObjectLiteral objectLiteral) {
		TypingStrategy typingStrategy = typeRef.getTypingStrategy();
		if (typingStrategy != TypingStrategy.NOMINAL && typingStrategy != TypingStrategy.DEFAULT) {
			if (typeRef.isDynamic()) {
				return;
			}

			Type type = typeRef.getDeclaredType();
			if (type == null && typeRef instanceof BoundThisTypeRef) {
				ParameterizedTypeRef actualThisTypeRef = ((BoundThisTypeRef) typeRef).getActualThisTypeRef();
				type = actualThisTypeRef == null ? null : actualThisTypeRef.getDeclaredType();
			}
			if (!(type instanceof ContainerType<?>)) {
				return;
			}

			EList<TStructMember> structuralMembers = typeRef.getStructuralMembers();
			if (structuralMembers.isEmpty() && type == RuleEnvironmentExtensions.objectType(G)) {
				return;
			}

			boolean isSpecArgument = isSpecArgument(G, objectLiteral);
			TypingStrategyFilter strategyFilter = new TypingStrategyFilter(typingStrategy,
					typingStrategy == TypingStrategy.STRUCTURAL_WRITE_ONLY_FIELDS,
					isSpecArgument);
			TypingStrategyFilter strategyFilterIncludeNotAccessible = new TypingStrategyFilter(typingStrategy,
					typingStrategy == TypingStrategy.STRUCTURAL_WRITE_ONLY_FIELDS,
					isSpecArgument, true);
			MemberList<TMember> allMembers = containerTypesHelper.fromContext(objectLiteral)
					.allMembers((ContainerType<?>) type);

			Set<String> expectedMembers = toSet(
					map(filter(allMembers, member -> strategyFilter.apply(member)), member -> member.getName()));

			Set<String> expectedMembersPlusNotAccessibles = toSet(
					map(filter(allMembers, member -> strategyFilterIncludeNotAccessible.apply(member)),
							member -> member.getName()));

			// These are available via the 'with' keyword add them to the accepted ones
			for (TStructMember member : typeRef.getStructuralMembers()) {
				expectedMembers.add(member.getName());
			}

			EObject container = objectLiteral;
			while (container instanceof ObjectLiteral || container instanceof ArrayLiteral
					|| container instanceof ArrayElement) {
				container = container.eContainer();
			}
			String lhsName = null;
			if (container instanceof VariableDeclaration) {
				lhsName = ((VariableDeclaration) container).getName();
			} else if (container instanceof AssignmentExpression) {
				Expression lhs = ((AssignmentExpression) container).getLhs();
				if (lhs instanceof IdentifierRef) {
					lhsName = ((IdentifierRef) lhs).getIdAsText();
				} else if (lhs instanceof ParameterizedPropertyAccessExpression) {
					lhsName = ((ParameterizedPropertyAccessExpression) lhs).getPropertyAsText();
				} else {
					lhsName = "references of type " + typeRef.getDeclaredType().getName();
				}
			} else if (container instanceof Argument) {
				lhsName = "the receiving parameter";
			} else {
				lhsName = "references of type " + typeRef.getDeclaredType().getName();
			}

			List<? extends TMember> inputMembers = ((ContainerType<?>) objectLiteral.getDefinedType())
					.getOwnedMembers();
			for (TMember property : inputMembers) {
				if (!expectedMembers.contains(property.getName())) {
					EObject astElement = property.getAstElement();
					EStructuralFeature feature = null;
					if (astElement instanceof PropertyNameValuePair) {
						PropertyNameValuePair pnvp = (PropertyNameValuePair) astElement;
						if (pnvp.getDeclaredName() != null) {
							astElement = pnvp.getDeclaredName();
						} else if (pnvp.getProperty() != null) {
							feature = N4JSPackage.eINSTANCE.getPropertyNameValuePair_Property();
						}
					}
					if (isSpecArgument) {
						IssueItem issueItem = CLF_SPEC_SUPERFLUOUS_PROPERTIES.toIssueItem(property.getName(),
								typeRef.getTypeRefAsString());
						addIssue(astElement, feature, issueItem);
					} else if (!expectedMembersPlusNotAccessibles.contains(property.getName())) {
						IssueItem issueItem = CLF_SUPERFLUOUS_PROPERTIES.toIssueItem(property.getName(),
								typeRef.getTypeRefAsString(), lhsName);
						addIssue(astElement, feature, issueItem);
					}
				}
			}
		}
	}

	private boolean isSpecArgument(RuleEnvironment G, ObjectLiteral objectLiteral) {
		EObject parent = objectLiteral == null ? null : objectLiteral.eContainer();
		EObject grandParent = parent == null ? null : parent.eContainer();
		if (!(parent instanceof Argument && grandParent instanceof NewExpression)) {
			return false;
		}
		Argument arg = (Argument) parent;
		NewExpression newExpr = (NewExpression) grandParent;
		// note: since the @Spec annotation may only be used in the constructor of a class,
		// we can here skip the handling of construct signatures (i.e. last argument is 'true'):
		Newable newableTypeRef = tsh.getNewableTypeRef(G, newExpr, true);
		TMethod ctor = newableTypeRef == null ? null : newableTypeRef.getCtorOrConstructSig();
		if (ctor == null) {
			return false;
		}
		int argIdx = newExpr.getArguments().indexOf(arg);
		TFormalParameter ctorFpar = ctor.getFparForArgIdx(argIdx);
		if (ctorFpar == null) {
			return false;
		}
		return AnnotationDefinition.SPEC.hasAnnotation(ctorFpar);
	}

	private void internalCheckUseOfUndefinedExpression(RuleEnvironment G, Expression expression,
			TypeRef expectedTypeRef, TypeRef actualTypeRef) {
		if (TypeUtils.isUndefined(actualTypeRef) && !TypeUtils.isUndefined(expectedTypeRef)) {
			EObject parent = expression.eContainer();
			if (!(parent instanceof ExpressionStatement) &&
					!(parent instanceof PropertyNameValuePair) &&
					!(parent instanceof UnaryExpression && ((UnaryExpression) parent).getOp() == UnaryOperator.VOID) &&
					!(expression instanceof UnaryExpression &&
							((UnaryExpression) expression).getOp() == UnaryOperator.VOID)
					&&
					!(expression instanceof ThisLiteral)) {

				TField undefinedField = (TField) globalObjectType(G).findOwnedMember("undefined", false, false);
				boolean isUndefinedLiteral = (expression instanceof IdentifierRef) &&
						((IdentifierRef) expression).getId() == undefinedField;
				if (!isUndefinedLiteral) {
					addIssue(expression, EXP_USE_OF_UNDEF_EXPR.toIssueItem());
				}
			}
		}
	}

	// TODO IDE-1010 Code-snippet with partial solution
	// @Check
	// public void checkApplyParameters(ParameterizedCallExpression callExpression) {
	//
	// val target = callExpression.target
	//
	// // check apply with types.
	// if (target instanceof ParameterizedPropertyAccessExpression) {
	// val prop = target.property
	// if (prop instanceof TMethod) {
	// if ("apply" == prop.name) {
	//
	// val func = callExpression.receiver
	// val thisArg = callExpression.arguments.head
	//
	// if (thisArg != null && func != null) {
	//
	// // apply called on a function or functionexpress or...
	// // infer type of this-Environment:
	// var G = typeInferencer.getConfiguredRuleEnvironment(thisArg);
	//
	// val inferredThisArgTypeResult = ts.type(G, thisArg);
	// if (inferredThisArgTypeResult.failed) return
	//
	// val inferredThisArgType = inferredThisArgTypeResult.value
	//
	// // infer Type of function we want to call:
	// val funcTypeResult = ts.type(G, func)
	// if (funcTypeResult.failed) return
	//
	// val funcType = funcTypeResult.value
	//
	// if (funcType instanceof FunctionTypeExprOrRef) {
	//
	// // if there is a declared this-type
	// val reqThisType = funcType.declaredThisType
	// if (reqThisType != null) {
	// val result = ts.subtypeTypeRef(G, inferredThisArgType, reqThisType);
	//
	// // report.
	// createError(result, callExpression)
	// }
	// }
	//
	// }
	//
	// }
	// }
	//
	// }
	//
	// }

	/**
	 * This validates a warning in chapter 4.10.1:<br/>
	 * <i>The use of the any type in a union type produces a warning.</i>
	 */
	@Check
	public void checkUnionTypeContainsNoAny(UnionTypeExpression ute) {
		checkComposedTypeRefContainsNoAny(ute, UNI_ANY_USED.toIssueItem(), true);
	}

	/**
	 * This validates a warning in chapter 4.10.2:<br/>
	 * <i>The use of the any type in an intersection type produces a warning.</i>
	 */
	@Check
	public void checkIntersectionTypeContainsNoAny(IntersectionTypeExpression ite) {
		checkComposedTypeRefContainsNoAny(ite, INTER_ANY_USED.toIssueItem(), false);
	}

	private void checkComposedTypeRefContainsNoAny(ComposedTypeRef ctr, IssueItem issueItem,
			boolean soleVoidAllowesAny) {
		RuleEnvironment G = newRuleEnvironment(ctr);
		AnyType anyType = anyType(G);
		VoidType voidType = voidType(G);
		EList<TypeRef> typeRefs = ctr.getTypeRefs();
		Iterable<TypeRef> anyTypeRefs = filter(typeRefs, it -> it.getDeclaredType() == anyType); // identity check on
																									// typeRefs is OK
																									// here
		boolean dontShowWarning = false;
		if (soleVoidAllowesAny) {
			boolean containsVoid = exists(typeRefs, it -> it.getDeclaredType() == voidType); // identity check on
																								// typeRefs is OK here
			dontShowWarning = containsVoid && size(anyTypeRefs) == 1;
		}

		if (!dontShowWarning) {
			for (TypeRef anyTR : anyTypeRefs) {
				addIssue(anyTR, issueItem);
			}
		}
	}

	/**
	 * This validates a warning in chapter 4.10.1:<br/>
	 * <i>The use of unnecessary subtypes in union types produces a warning.</i>
	 */
	@Check
	public void checkUnionHasUnnecessarySubtype(UnionTypeExpression ute) {
		List<TypeRef> tRefs = extractNonStructTypeRefs(ute);
		RuleEnvironment G = newRuleEnvironment(ute);
		AnyType anyType = anyType(G);
		tRefs.removeIf(it -> it.getDeclaredType() == anyType); // identity check on typeRefs is OK here
		if (tRefs.size() > 1) {
			List<TypeRef> intersectionTR = tsh.getSuperTypesOnly(G, tRefs.toArray(new TypeRef[0]));
			tRefs.removeAll(intersectionTR);

			for (TypeRef tClassR : tRefs) {
				addIssue(tClassR, UNI_REDUNDANT_SUBTYPE.toIssueItem());
			}
		}
	}

	/**
	 * Entry method for validating the containing types of an intersection type.
	 */
	@Check
	public void checkIntersectionType(IntersectionTypeExpression ite) {
		List<TypeRef> tClassRefs = extractNonStructTypeRefs(ite);

		if (tClassRefs.size() > 1) {
			RuleEnvironment G = newRuleEnvironment(ite);
			List<TypeRef> intersectionTR = tsh.getSubtypesOnly(G, tClassRefs.toArray(new TypeRef[0]));

			checkIntersectionTypeContainsMaxOneClass(G, intersectionTR, false);
			checkIntersectionHasUnnecessarySupertype(tClassRefs, intersectionTR);
		}
	}

	/**
	 * This validates constraint 25.2 ("Intersection Type") in chapter 4.10.2:<br/>
	 * <i>Only one class must be contained in the intersection type.</i><br/>
	 * <br/>
	 * Currently, only a warning is displayed.
	 */
	private void checkIntersectionTypeContainsMaxOneClass(RuleEnvironment G,
			List<TypeRef> intersectionTR, boolean covariantTypeArgValidation) {
		if (intersectionTR.size() > 1) {

			ArrayListMultimap<Type, TypeRef> byTypes = ArrayListMultimap.create();

			for (TypeRef tClassR : intersectionTR) {
				byTypes.put(tClassR.getDeclaredType(), tClassR);
			}

			if (byTypes.keySet().size() > 1) {
				if (covariantTypeArgValidation) {
					for (TypeRef tClassR : intersectionTR) {
						if (!(tClassR.eContainer() instanceof TypeVariable)) { // nested, type ref coming from def site
							addIssue(tClassR, INTER_TYEPARGS_ONLY_ONE_CLASS_ALLOWED.toIssueItem());
						}
					}
				} else {
					for (TypeRef tClassR : intersectionTR) {
						addIssue(tClassR, INTER_ONLY_ONE_CLASS_ALLOWED.toIssueItem());
					}
				}
			} else {
				Type type = head(byTypes.keys());
				if (type.isGeneric()) {
					List<TypeRef> ptrs = byTypes.get(type); // similar to intersectionTR

					if (allCovariantOrWildcardWithUpperBound(type.getTypeVars(), ptrs)) {
						int length = type.getTypeVars().size();
						for (int v = 0; v < length; v++) {
							int vIndex = v; // final
							// typerefs already simplified by initial call to this method:
							List<TypeRef> typeArgsPerVariable = extractNonStructTypeRefs(map(ptrs,

									ptr -> {
										TypeArgument ta = ptr.getDeclaredTypeArgs().get(vIndex);
										TypeRef upper = null;
										if (ta instanceof TypeRef) {
											upper = (TypeRef) ta;
										}
										if (upper == null && ta instanceof Wildcard) {
											upper = ((Wildcard) ta).getDeclaredUpperBound();
										}
										if (upper == null) {
											upper = type.getTypeVars().get(vIndex).getDeclaredUpperBound();
										}
										return upper;

									}));
							checkIntersectionTypeContainsMaxOneClass(G, typeArgsPerVariable, true);
						}

						// all type args use super:
					} else if (forall(ptrs, ptr -> forall(ptr.getDeclaredTypeArgs(), ta -> ta instanceof Wildcard &&
							((Wildcard) ta).getDeclaredLowerBound() != null))) {
						// all common super types, at least Object, as type arg would work! no warning.
					} else {
						// instantiation not possible except with undefined
						for (TypeRef tClassR : intersectionTR) {
							if (!(tClassR.eContainer() instanceof TypeVariable)) { // nested, type ref coming from def
																					// site
								addIssue(tClassR, INTER_WITH_ONE_GENERIC.toIssueItem());
							}
						}
					}
				}
			}
		}
	}

	private boolean allCovariantOrWildcardWithUpperBound(List<TypeVariable> typeVars, List<TypeRef> refs)
			throws IndexOutOfBoundsException {
		int length = typeVars.size();
		for (var i = 0; i < length; i++) {
			if (!typeVars.get(i).isDeclaredCovariant()) {
				for (TypeRef ref : refs) {
					TypeArgument ta = ref.getDeclaredTypeArgs().get(i);
					if (ta instanceof Wildcard) {
						if (((Wildcard) ta).getDeclaredUpperBound() == null) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * This validates a warning in chapter 4.10.2:<br/>
	 * <i>The use of unnecessary supertypes in intersection types produces a warning.</i>
	 */
	private void checkIntersectionHasUnnecessarySupertype(List<TypeRef> tClassRefs, List<TypeRef> intersectionTR) {
		tClassRefs.removeAll(intersectionTR);

		for (TypeRef tClassR : tClassRefs) {
			addIssue(tClassR, INTER_REDUNDANT_SUPERTYPE.toIssueItem());
		}
	}

	@Check
	public void checkTypeParameters(GenericDeclaration genDecl) {
		if (genDecl.getTypeVars().isEmpty()) {
			return; // nothing to check
		}
		if (holdsOptionalTypeParameterNotFollowedByMandatory(genDecl)) {
			if (holdsDefaultArgumentsContainValidReferences(genDecl)) {
				holdsDefaultArgumentsComplyToBounds(genDecl);
			}
		}
	}

	private boolean holdsOptionalTypeParameterNotFollowedByMandatory(GenericDeclaration genDecl) {
		var haveOptional = false;
		for (N4TypeVariable n4TypeParam : genDecl.getTypeVars()) {
			if (haveOptional && !n4TypeParam.isOptional()) {
				addIssue(n4TypeParam, N4JSPackage.Literals.N4_TYPE_VARIABLE__NAME,
						TYP_TYPE_PARAM_MANDATORY_AFTER_OPTIONAL.toIssueItem());
				return false;
			}
			haveOptional = haveOptional || n4TypeParam.isOptional();
		}
		return true;
	}

	/**
	 * Currently this method only checks for forward references in default arguments (e.g.
	 * <code>G&lt;T1=T2,T2=any> {}</code>). In the future, we might also check for cyclic default arguments.
	 */
	private boolean holdsDefaultArgumentsContainValidReferences(GenericDeclaration genDecl) {
		// find forward references to type parameters declared after the current type parameter
		Set<TypeVariable> badTypeVars = toSet(
				filterNull(map(genDecl.getTypeVars(), tv -> tv.getDefinedTypeVariable())));
		if (badTypeVars.size() < genDecl.getTypeVars().size()) {
			return true; // syntax error
		}
		List<ParameterizedTypeRef> forwardReferences = new ArrayList<>();
		for (N4TypeVariable n4TypeParam : genDecl.getTypeVars()) {
			TypeRef defaultArgInAST = n4TypeParam.getDeclaredDefaultArgumentNode() == null ? null
					: n4TypeParam.getDeclaredDefaultArgumentNode().getTypeRefInAST();
			if (defaultArgInAST != null) {
				TypeUtils.forAllTypeRefs(defaultArgInAST, ParameterizedTypeRef.class, true, false, null, (ptr) -> {
					Type declType = ptr.getDeclaredType();
					if (declType instanceof TypeVariable && badTypeVars.contains(declType)) {
						boolean isContainedInAST = EcoreUtil2.getContainerOfType(ptr, Script.class) != null;
						if (isContainedInAST) {
							forwardReferences.add(ptr);
						}
					}
					return true; // continue with traversal
				}, null);
			}
			// from now on, the current type variable may be referenced in the default argument of all following type
			// variables:
			badTypeVars.remove(n4TypeParam.getDefinedTypeVariable());
		}
		// create error markers
		if (!forwardReferences.isEmpty()) {
			for (ParameterizedTypeRef badRef : forwardReferences) {
				addIssue(badRef, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE,
						TYP_TYPE_PARAM_DEFAULT_REFERENCES_LATER_TYPE_PARAM.toIssueItem());
			}
			return false;
		}
		return true;
	}

	private boolean holdsDefaultArgumentsComplyToBounds(GenericDeclaration genDecl) {
		RuleEnvironment G = newRuleEnvironment(genDecl);
		var haveInvalidDefault = false;
		for (N4TypeVariable n4TypeParam : genDecl.getTypeVars()) {
			if (n4TypeParam.getName() != null) {
				TypeRef defaultArgInAST = n4TypeParam.getDeclaredDefaultArgumentNode() == null ? null
						: n4TypeParam.getDeclaredDefaultArgumentNode().getTypeRefInAST();
				TypeRef defaultArg = n4TypeParam.getDeclaredDefaultArgumentNode() == null ? null
						: n4TypeParam.getDeclaredDefaultArgumentNode().getTypeRef();
				TypeRef ub = n4TypeParam.getDeclaredUpperBound();
				if (defaultArgInAST != null && defaultArg != null && ub != null) {
					Result result = ts.subtype(G, defaultArg, ub);
					if (result.isFailure()) {
						IssueItem issueItem = TYP_TYPE_PARAM_DEFAULT_NOT_SUBTYPE_OF_BOUND
								.toIssueItem(n4TypeParam.getName(), result.getCompiledFailureMessage());
						addIssue(n4TypeParam, N4JSPackage.Literals.N4_TYPE_VARIABLE__DECLARED_DEFAULT_ARGUMENT_NODE,
								issueItem);
						haveInvalidDefault = true;
					}
				}
			}
		}
		return !haveInvalidDefault;
	}

	private List<TypeRef> extractNonStructTypeRefs(ComposedTypeRef ctr) {
		TreeSet<TypeRef> typeRefs = new TreeSet<>(typeCompareHelper.getTypeRefComparator());
		collectAndFlattenElementTypeRefs(ctr, typeRefs);
		return extractNonStructTypeRefs(typeRefs);
	}

	private void collectAndFlattenElementTypeRefs(ComposedTypeRef ctr, Collection<TypeRef> addHere) {
		EClass composedTypeKind = ctr.eClass();
		for (TypeRef tr : ctr.getTypeRefs()) {
			if (composedTypeKind.isInstance(tr)) {
				collectAndFlattenElementTypeRefs((ComposedTypeRef) tr, addHere);
			} else {
				addHere.add(tr);
			}
		}
	}

	private List<TypeRef> extractNonStructTypeRefs(Iterable<TypeRef> simplifiedTypeRefs) {
		List<TypeRef> tClassRefs = new LinkedList<>();
		for (TypeRef tR : simplifiedTypeRefs) {
			if (tR != null) { // may happen if argument has been a result of a computation
				Type type = tR.getDeclaredType();
				if (type instanceof TClass) {
					var isStructural = tR.isDefSiteStructuralTyping() || tR.isUseSiteStructuralTyping();
					if (!isStructural)
						tClassRefs.add(tR);
				}
			}
		}
		return tClassRefs;
	}

}
