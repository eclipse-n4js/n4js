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

import com.google.common.base.Optional
import com.google.common.collect.ArrayListMultimap
import com.google.inject.Inject
import java.util.Collection
import java.util.LinkedList
import java.util.List
import java.util.TreeSet
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.Argument
import org.eclipse.n4js.n4JS.ArrayElement
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.AssignmentOperator
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ExpressionAnnotationList
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.GenericDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.n4JS.TypeReferenceNode
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.UnaryOperator
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.packagejson.projectDescription.ProjectType
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.scoping.members.TypingStrategyFilter
import org.eclipse.n4js.scoping.utils.ExpressionExtensions
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeRef
import org.eclipse.n4js.ts.typeRefs.IntersectionTypeExpression
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression
import org.eclipse.n4js.ts.typeRefs.UnknownTypeRef
import org.eclipse.n4js.ts.typeRefs.Wildcard
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.types.utils.TypeCompareHelper
import org.eclipse.n4js.types.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.n4js.workspace.WorkspaceAccess
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.ts.typeRefs.TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE
import static org.eclipse.n4js.validation.IssueCodes.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Class for validating the N4JS types.
 */
class N4JSTypeValidator extends AbstractN4JSDeclarativeValidator {

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
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	/**
	 * Validates all generic type variable declarations.
	 * Raises validation error for each type variable if any of their declared upper bound is a primitive type.
	 * <p>
	 * IDEBUG-185
	 *
	 * @param declaration the generic declaration to check the upper bound declarations of its type variables.
	 */
	@Check
	def checkGenericDeclarationType(GenericDeclaration declaration) {
		val functionType = newRuleEnvironment(declaration).functionType
		declaration.typeVars.filterNull.forEach [ typeVar |
			val ub = typeVar.declaredUpperBound;
			if (ub !== null) {
				val declType = ub.declaredType;
				if ((declType instanceof ContainerType<?> || declType instanceof PrimitiveType) && declType.final) {
					if (declType === functionType) {
						// important exception (until function type expressions are supported as bounds):
						// class C<T extends Function> {} makes sense even though Function is final
						return;
					}
					val ubInAST = typeVar.declaredUpperBoundNode.typeRefInAST; // never 'null' because 'typeVar.declaredUpperBound' returned non-null value
					val message = getMessageForCLF_UPPER_BOUND_FINAL(declType.name, typeVar.name);
					addIssue(message, ubInAST, PARAMETERIZED_TYPE_REF__DECLARED_TYPE, CLF_UPPER_BOUND_FINAL);
				}
			};
		];
	}

	@Check
	def checkTModuleCreated(Script script) {
		if (script.module === null) {
			val rootNode = NodeModelUtils.getNode(script)
			if (rootNode !== null) {
				addIssue(IssueCodes.getMessageForTYS_MISSING, script, rootNode.offset, rootNode.length, TYS_MISSING);
			}
		}
	}

	@Check
	def checkParameterizedTypeRef(ParameterizedTypeRef paramTypeRefInAST) {
		val declaredType = paramTypeRefInAST.declaredType;
		if (declaredType === null || declaredType.eIsProxy) {
			return;
		}

		val isInTypeTypeRef = paramTypeRefInAST.eContainer instanceof TypeTypeRef || (
			paramTypeRefInAST.eContainer instanceof Wildcard && paramTypeRefInAST.eContainer.eContainer instanceof TypeTypeRef);

		if (isInTypeTypeRef) {
			internalCheckValidTypeInTypeTypeRef(paramTypeRefInAST);
		} else {
			internalCheckTypeArguments(declaredType.typeVars, paramTypeRefInAST.declaredTypeArgs, Optional.absent, false,
				declaredType, paramTypeRefInAST, TypeRefsPackage.eINSTANCE.parameterizedTypeRef_DeclaredType);
		}
		internalCheckDynamic(paramTypeRefInAST);

		internalCheckStructuralPrimitiveTypeRef(paramTypeRefInAST);
	}

	/**
	 * Add an issue if explicit use of structural type operator with a primitive type is detected.
	 */
	def private void internalCheckStructuralPrimitiveTypeRef(ParameterizedTypeRef typeRefInAST) {
		if (typeRefInAST.typingStrategy != TypingStrategy.NOMINAL && !N4JSLanguageUtils.mayBeReferencedStructurally(typeRefInAST.declaredType)) {
			addIssue(IssueCodes.messageForTYS_STRUCTURAL_PRIMITIVE, typeRefInAST, TYS_STRUCTURAL_PRIMITIVE);
		}
	}

	def private void internalCheckValidTypeInTypeTypeRef(ParameterizedTypeRef paramTypeRefInAST) {
		// IDE-785 uses ParamterizedTypeRefs in ClassifierTypeRefs. Currently Type Arguments are not supported in ClassifierTypeRefs, so
		// we actively forbid them here. Will be loosened for IDE-1310
		if (!paramTypeRefInAST.declaredTypeArgs.isEmpty) {
			addIssue(IssueCodes.getMessageForAST_NO_TYPE_ARGS_IN_CLASSIFIERTYPEREF, paramTypeRefInAST,
				AST_NO_TYPE_ARGS_IN_CLASSIFIERTYPEREF)
		} else if (paramTypeRefInAST instanceof FunctionTypeRef) {
			addIssue(IssueCodes.getMessageForAST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF, paramTypeRefInAST,
				AST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF)
		} else if (paramTypeRefInAST.declaredType instanceof TFunction) {
			addIssue(IssueCodes.getMessageForAST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF, paramTypeRefInAST,
				AST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF)
		}
	}

	@Check
	def checkThisTypeRef(ThisTypeRef thisTypeRefInAST) {
		if (thisTypeRefInAST instanceof BoundThisTypeRef) {
			// the below validations do not apply to BoundThisTypeRefs
			// (note: normally, BoundThisTypeRefs should never appear in the AST, anyway; but asserting this to be true
			// is not the job of this validation)
			return;
		}
		if (!(thisTypeRefInAST.isUsedStructurallyAsFormalParametersInTheConstructor 
			|| thisTypeRefInAST.isUsedAtCovariantPositionInClassifierDeclaration 
			|| thisTypeRefInAST.isUsedInVariableWithSyntaxError)) {
			addIssue(IssueCodes.getMessageForAST_THIS_WRONG_PLACE, thisTypeRefInAST, IssueCodes.AST_THIS_WRONG_PLACE);
		}
	}

	def private boolean isUsedStructurallyAsFormalParametersInTheConstructor(ThisTypeRef thisTypeRefInAST) {
		if (thisTypeRefInAST.useSiteStructuralTyping) {
			val node = thisTypeRefInAST.eContainer;
			if (node instanceof TypeReferenceNode<?>) {
				val fpar = node.eContainer;
				if (fpar instanceof FormalParameter) {
					val ctor = fpar.eContainer;
					if (ctor instanceof N4MethodDeclaration) {
						if (ctor.isConstructor) {
							return true
						}
					}
				}
			}
		}
		return false
	}

	def private boolean isUsedAtCovariantPositionInClassifierDeclaration(ThisTypeRef thisTypeRefInAST) {
		val classifierDecl = EcoreUtil2.getContainerOfType(thisTypeRefInAST, N4ClassifierDeclaration);
		if (classifierDecl !== null) {
			// exception: disallow for static members of interfaces
			if (classifierDecl instanceof N4InterfaceDeclaration) {
				val memberDecl = EcoreUtil2.getContainerOfType(thisTypeRefInAST, N4MemberDeclaration);
				if (memberDecl !== null && memberDecl.static) {
					return false;
				}
			}
			val varianceOfPos = N4JSLanguageUtils.getVarianceOfPosition(thisTypeRefInAST);
			if (varianceOfPos !== null) {
				return varianceOfPos === Variance.CO;
			}
		}
		return false;
	}

	private def boolean isUsedInVariableWithSyntaxError(ThisTypeRef thisTypeRefInAST) {
		val parent = thisTypeRefInAST.eContainer
		if (parent instanceof TypeReferenceNode<?>) {
			val grandParent = parent.eContainer;
			if (grandParent instanceof VariableDeclaration) {
				return grandParent.name === null;
			}
		}
		return false;
	}

	@Check
	def checkSymbolReference(TypeRef typeRefInAST) {
		val bits = BuiltInTypeScope.get(typeRefInAST.eResource.resourceSet);
		val G = typeRefInAST.newRuleEnvironment;
		val typeRef = tsh.resolveTypeAliasFlat(G, typeRefInAST);
		if (typeRef.declaredType === bits.symbolObjectType) {
			// we have a type reference to 'Symbol'
			val isAllowed = isExtendsClauseInRuntimeLibrary(typeRefInAST);
			if (!isAllowed) {
				addIssue(IssueCodes.getMessageForBIT_SYMBOL_INVALID_USE, typeRefInAST, BIT_SYMBOL_INVALID_USE);
			}
		}
	}

	def private boolean isExtendsClauseInRuntimeLibrary(TypeRef typeRefInAST) {
		if (typeRefInAST.eContainer?.eContainmentFeature === N4JSPackage.Literals.N4_CLASS_DEFINITION__SUPER_CLASS_REF) {
			val project = workspaceAccess.findProjectContaining(typeRefInAST);
			if (project !== null && project.type === ProjectType.RUNTIME_LIBRARY) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Constraints 08: primitive types except any must not be declared dynamic
	 */
	def private internalCheckDynamic(ParameterizedTypeRef refInAST) {
		if (refInAST.dynamic) {
			val Type t = refInAST.declaredType;
			if (!N4JSLanguageUtils.mayBeReferencedDynamically(t)) {
				addIssue(IssueCodes.getMessageForTYS_PRIMITIVE_TYPE_DYNAMIC(t.name), refInAST,
					TYS_PRIMITIVE_TYPE_DYNAMIC);
			}
		}
	}

	@Check
	def checkTypeHiddenByTypeVariable(GenericDeclaration genDecl) {

		// TODO reconsider this warning or its implementation; re-generating the scope can become quite expensive
		// (but note that moving this to the scoping code is not trivial, because warning has to be generated also
		// if no references to the type parameter are made!)
		if (!genDecl.typeVars.empty) {
			val staticAccess = genDecl instanceof N4MemberDeclaration && (genDecl as N4MemberDeclaration).static;
			val scope = n4jsScopeProvider.getTypeScope( // note: calling #getTypeScope() here, NOT #getScope()!
				genDecl.eContainer, // use container, because we do not want to see type variables we are currently validating
				staticAccess);
			genDecl.typeVars.forEach [
				if (!it.name.nullOrEmpty) {
					val hiddenTypeDscr = scope.getSingleElement(QualifiedName.create(it.name));
					val hiddenType = hiddenTypeDscr?.getEObjectOrProxy;
					if (hiddenType instanceof Type &&
						!(IEObjectDescriptionWithError.isErrorDescription(hiddenTypeDscr))) {
						val message = getMessageForVIS_TYPE_PARAMETER_HIDES_TYPE(name, hiddenType.keyword);
						addIssue(message, it, VIS_TYPE_PARAMETER_HIDES_TYPE);
					}
				}
			]
		}
	}

	/**
	 * This handles a special case that is not checked by method {@link #checkTypeMatchesExpectedType(Expression)}.
	 * In a compound assignment like += or *=, the left-hand side is both read from and written to. So we have
	 * to check 1) that the type for write access is correct and 2) the type of read access is correct. Usually
	 * these two types are the same, but in case of a getter/setter pair they can be different and need to be
	 * checked individually. Case 1) is taken care of by method {@link #checkTypeMatchesExpectedType(Expression)}.
	 * Case 2) is checked here in this method.
	 */
	@Check
	def void checkCompoundAssignmentGetterSetterClashOnLhs(AssignmentExpression assExpr) {
		if (assExpr.op === null || assExpr.op === AssignmentOperator.ASSIGN)
			return; // following code is only required for compound assignments, i.e. +=, *=, etc.; not for =
		val Expression lhs = assExpr.lhs;
		if (lhs instanceof ParameterizedPropertyAccessExpression) {
			val prop = lhs.property;

			// in case of a getter/setter pair, scoping will here always give us the setter as property,
			// because we are on the left-hand side of an assignment (i.e. write access)
			if (prop instanceof TSetter) {

				// ok, we have the situation we are interested in (setter on LHS of compound assignment)
				// --> now check if there is a matching getter of correct type
				val TSetter setter = prop;
				val Type targetType = ts.tau(lhs.target)?.declaredType;
				if (targetType instanceof ContainerType<?>) {
					val TMember m = containerTypesHelper.fromContext(assExpr).findMember(targetType, setter.name,
						false, setter.static);
					if (m === null) {

						// no getter at all
						val message = messageForTYS_COMPOUND_MISSING_GETTER
						addIssue(message, assExpr.lhs, TYS_COMPOUND_MISSING_GETTER);
					} else if (m instanceof TGetter) {
						val TGetter getter = m;
						var G = assExpr.newRuleEnvironment;

						// must use .rhs in next line, because .lhs would give us the expected type for write access
						// (which is already checked by the generic method #checkTypeMatchesExpectedType()
						val expectedType = ts.expectedType(G, assExpr, assExpr.rhs);
						val TypeRef typeOfGetterRAW = TypeUtils.getMemberTypeRef(getter);
						if (expectedType !== null && typeOfGetterRAW !== null) {
							val TypeRef typeOfGetter = ts.substTypeVariables(G, typeOfGetterRAW);
							if (typeOfGetter !== null) {
								val result = ts.subtype(G, typeOfGetter, expectedType);
								createTypeError(result, assExpr.lhs);
							}
						}
					}
				}
			}
		}
	}

	@Check
	def void checkInconsistentInterfaceImplementationOrExtension(N4ClassifierDeclaration classifierDecl) {
		val tClassifier = classifierDecl.definedType as TClassifier;
		if (tClassifier === null)
			return; // broken AST
		val G = newRuleEnvironment(classifierDecl);
		G.recordInconsistentSubstitutions;
		tClassifier.superClassifierRefs.forEach[tsh.addSubstitutions(G, it)];
		for (tv : G.getTypeMappingKeys()) {
			if (!tv.declaredCovariant && !tv.declaredContravariant) {
				val subst = ts.substTypeVariables(G, TypeUtils.createTypeRef(tv));
				if (subst instanceof UnknownTypeRef) {
					val badSubst = G.getInconsistentSubstitutions(tv);
					if (!badSubst.empty) {
						if (!tsh.allEqualType(G, badSubst)) {
							val mode = if (tClassifier instanceof TClass) "implement" else "extend";
							val ifcName = (tv.eContainer as TInterface).name;
							val tvName = "invariant " + tv.name;
							val typeRefsStr = badSubst.map[typeRefAsString].join(", ");
							val message = getMessageForCLF_IMPLEMENT_EXTEND_SAME_INTERFACE_INCONSISTENTLY(mode,
								ifcName, tvName, typeRefsStr);
							addIssue(message, classifierDecl, N4JSPackage.eINSTANCE.n4TypeDeclaration_Name,
								CLF_IMPLEMENT_EXTEND_SAME_INTERFACE_INCONSISTENTLY);
						}
					}
				}
			}
		}
	}

	/**
	 * This tests ALL expressions, including expressions used on right hand side of assignments or property definitions. That is,
	 * there is no need to test field declarations or property declarations separately, as this will lead to duplicate error
	 * messages.
	 */
	@Check
	def void checkTypeMatchesExpectedType(Expression expression) {

		if (!jsVariantHelper.requireCheckTypeMatchesExpectedType(expression)) {
			return;
		}

		// expressionAnnotationList occur on function- and class-expressions.
		// checking of the content is done in N4JSAnnotationValidation
		if (expression instanceof ExpressionAnnotationList) {
			return
		}

		var G = expression.newRuleEnvironment;
		val inferredType = ts.type(G, expression);
		if (inferredType instanceof UnknownTypeRef) {
			return;
		}

		// use a fresh environment for expectations
		G = newRuleEnvironment(expression);

		val expectedTypeRef = ts.expectedType(G, expression.eContainer, expression);
		if (expectedTypeRef !== null) {

			// for certain problems in single-expression arrow functions, we want a special error message
			val singleExprArrowFunction = N4JSASTUtils.getContainingSingleExpressionArrowFunction(expression);
			if (singleExprArrowFunction !== null && TypeUtils.isVoid(inferredType)) {
				if (TypeUtils.isVoid(expectedTypeRef) || singleExprArrowFunction.isReturnValueOptional) {
					return; // all good
				}
				if (singleExprArrowFunction.declaredReturnTypeRefInAST === null) { // show specialized error message only if return type of arrow function was inferred (i.e. not declared explicitly)
					val message = IssueCodes.
						getMessageForFUN_SINGLE_EXP_LAMBDA_IMPLICIT_RETURN_ALLOWED_UNLESS_VOID();
					addIssue(message, expression,
						IssueCodes.FUN_SINGLE_EXP_LAMBDA_IMPLICIT_RETURN_ALLOWED_UNLESS_VOID);
					return;
				}
			}

			internalCheckUseOfUndefinedExpression(G, expression, expectedTypeRef, inferredType);

			val boolean writeAccess = ExpressionExtensions.isLeftHandSide(expression);
			if (writeAccess) {

				// special case: write access
				val result = ts.subtype(G, expectedTypeRef, inferredType);

				if (result.failure) {
					// use custom error message, because otherwise it will be completely confusing
					val message = getMessageForTYS_NO_SUPERTYPE_WRITE_ACCESS(expectedTypeRef.typeRefAsString,
						inferredType.typeRefAsString);
					addIssue(message, expression, TYS_NO_SUPERTYPE_WRITE_ACCESS)
				}
			} else {

				// standard case: read access
				val result = ts.subtype(G, inferredType, expectedTypeRef)
				// not working, as primitive types are not part of currently validated resource:
				// errorGenerator.generateErrors(this, result, expression)
				// so we create error here differently:
				val errorCreated = createTypeError(result, expression)

				if (! errorCreated) {
					internalCheckSuperfluousPropertiesInObjectLiteralRek(G, expectedTypeRef, expression);
				}
			}
		}
	}


	def void internalCheckSuperfluousPropertiesInObjectLiteralRek(RuleEnvironment G, TypeRef expectedTypeRef, Expression expression) {
		if (expression instanceof ObjectLiteral) {
			internalCheckSuperfluousPropertiesInObjectLiteral(expectedTypeRef, expression);

		} else if (expression instanceof ArrayLiteral) {
			val expectedElemTypeRefs = tsh.extractIterableElementTypes(G, expectedTypeRef);
			if (!expectedElemTypeRefs.empty) {
				// we have Iterable, Array, IterableN, ArrayN or a subtype thereof
				var cachedLastElementTypeRefUB = null as TypeRef;
				val expectedElemTypeRefsCount = expectedElemTypeRefs.size;
				val elems = expression.elements;
				val elemsCount = elems.size;
				for (var i = 0; i < elemsCount; i++) {
					val currElemExpr = elems.get(i)?.expression;
					val currExpectedElemTypeRefUB = if (i < expectedElemTypeRefsCount - 1) {
						ts.upperBoundWithReopenAndResolveTypeVars(G, expectedElemTypeRefs.get(i))
					} else {
						if (cachedLastElementTypeRefUB === null) {
							cachedLastElementTypeRefUB = ts.upperBoundWithReopenAndResolveTypeVars(G,
								expectedElemTypeRefs.get(expectedElemTypeRefsCount - 1));
						}
						cachedLastElementTypeRefUB
					};
					if (currElemExpr !== null) {
						internalCheckSuperfluousPropertiesInObjectLiteralRek(G, currExpectedElemTypeRefUB, currElemExpr);
					}
				}
			}
		}
	}


	 /**
	 * #225: always check for superfluous properties in object literal
	 * req-id IDE-22501
	 */
	def void internalCheckSuperfluousPropertiesInObjectLiteral(TypeRef typeRef, ObjectLiteral objectLiteral) {
		val typingStrategy = typeRef.typingStrategy;
		if (typingStrategy != TypingStrategy.NOMINAL && typingStrategy != TypingStrategy.DEFAULT) {
			if (typeRef.isDynamic) {
				return;
			}

			var type = typeRef.declaredType;
			if (type===null && typeRef instanceof BoundThisTypeRef) {
				type = (typeRef as BoundThisTypeRef).actualThisTypeRef?.declaredType;
			}
			if (! (type instanceof ContainerType<?>)) {
				return;
			}

			val G = RuleEnvironmentExtensions.newRuleEnvironment(objectLiteral);
			val structuralMembers = typeRef.structuralMembers;
			if (structuralMembers.isEmpty && type == RuleEnvironmentExtensions.objectType(G)) {
				return;
			}

			val isSpecArgument = isSpecArgument(objectLiteral);
			val strategyFilter = new TypingStrategyFilter(typingStrategy,
				typingStrategy === TypingStrategy.STRUCTURAL_WRITE_ONLY_FIELDS,
				isSpecArgument);
			val strategyFilterIncludeNotAccessible = new TypingStrategyFilter(typingStrategy,
				typingStrategy === TypingStrategy.STRUCTURAL_WRITE_ONLY_FIELDS,
				isSpecArgument, true);
			val expectedMembers = containerTypesHelper.fromContext(objectLiteral).allMembers(
				type as ContainerType<?>).filter[member|strategyFilter.apply(member)].map[member|member.name].toSet();
			val expectedMembersPlusNotAccessibles = containerTypesHelper.fromContext(objectLiteral).allMembers(
				type as ContainerType<?>).filter[member|strategyFilterIncludeNotAccessible.apply(member)].map[member|member.name].toSet();

			// These are available via the 'with' keyword add them to the accepted ones
			typeRef.structuralMembers.forEach[member|expectedMembers.add(member.name)];

			var EObject container = objectLiteral;
			while (container instanceof ObjectLiteral || container instanceof ArrayLiteral || container instanceof ArrayElement) {
				container = container.eContainer;
			}
			val lhsName = switch (container) {
				VariableDeclaration: container.name
				AssignmentExpression: {
					val lhs = container.lhs;
					switch (lhs) {
						IdentifierRef: lhs.idAsText
						ParameterizedPropertyAccessExpression: lhs.propertyAsText
						default: "references of type " + typeRef.declaredType.name
					}
				}
				Argument: "the receiving parameter"
				default: "references of type " + typeRef.declaredType.name
			};

			val inputMembers = (objectLiteral.definedType as ContainerType<?>).ownedMembers;
			for (property : inputMembers) {
				if (!expectedMembers.contains(property.name)) {
					var astElement = property.astElement;
					if (astElement instanceof PropertyNameValuePair) {
						astElement = astElement.declaredName;
					}
					if (isSpecArgument) {
						val message = getMessageForCLF_SPEC_SUPERFLUOUS_PROPERTIES(property.name, typeRef.typeRefAsString);
						addIssue(message, astElement, CLF_SPEC_SUPERFLUOUS_PROPERTIES);
					} else if (!expectedMembersPlusNotAccessibles.contains(property.name)) {
						val message = getMessageForCLF_SUPERFLUOUS_PROPERTIES(property.name, typeRef.typeRefAsString, lhsName);
						addIssue(message, astElement, CLF_SUPERFLUOUS_PROPERTIES);
					}
				}
			};
		}
	}

	def private boolean isSpecArgument(ObjectLiteral objectLiteral) {
		val parent = objectLiteral?.eContainer;
		val grandParent = parent?.eContainer;
		if (!(parent instanceof Argument && grandParent instanceof NewExpression)) {
			return false;
		}
		val arg = parent as Argument;
		val newExpr = grandParent as NewExpression;
		val G = newExpr.newRuleEnvironment;
		// note: since the @Spec annotation may only be used in the constructor of a class,
		// we can here skip the handling of construct signatures (i.e. last argument is 'true'):
		val ctor = tsh.getConstructorOrConstructSignature(G, newExpr, true);
		if (ctor === null) {
			return false;
		}
		val argIdx = newExpr.arguments.indexOf(arg);
		val ctorFpar = ctor.getFparForArgIdx(argIdx);
		if (ctorFpar === null) {
			return false;
		}
		return AnnotationDefinition.SPEC.hasAnnotation(ctorFpar);
	}

	def private void internalCheckUseOfUndefinedExpression(RuleEnvironment G, Expression expression,
		TypeRef expectedTypeRef, TypeRef actualTypeRef) {
		if (TypeUtils.isUndefined(actualTypeRef) && !TypeUtils.isUndefined(expectedTypeRef)) {
			val parent = expression.eContainer;
			if (!(parent instanceof ExpressionStatement) &&
				!(parent instanceof UnaryExpression && (parent as UnaryExpression).op === UnaryOperator.VOID) &&
				!(expression instanceof UnaryExpression &&
					(expression as UnaryExpression).op === UnaryOperator.VOID) &&
				!(expression instanceof ThisLiteral)) {

				val undefinedField = G.globalObjectType.findOwnedMember("undefined", false, false) as TField;
				val isUndefinedLiteral = if (expression instanceof IdentifierRef)
						expression.id === undefinedField;
				if (!isUndefinedLiteral) {
					addIssue(getMessageForEXP_USE_OF_UNDEF_EXPR, expression, EXP_USE_OF_UNDEF_EXPR);
				}
			}
		}
	}


//  TODO IDE-1010 Code-snippet with partial solution
//	@Check
//	def checkApplyParameters(ParameterizedCallExpression callExpression) {
//
//		val target = callExpression.target
//
//		// check apply with types.
//		if (target instanceof ParameterizedPropertyAccessExpression) {
//			val prop = target.property
//			if (prop instanceof TMethod) {
//				if ("apply" == prop.name) {
//
//					val func = callExpression.receiver
//					val thisArg = callExpression.arguments.head
//
//					if (thisArg !== null && func !== null) {
//
//						// apply called on a function or functionexpress or...
//						// infer type of this-Environment:
//						var G = typeInferencer.getConfiguredRuleEnvironment(thisArg);
//
//						val inferredThisArgTypeResult = ts.type(G, thisArg);
//						if (inferredThisArgTypeResult.failed) return
//
//						val inferredThisArgType = inferredThisArgTypeResult.value
//
//						// infer Type of function we want to call:
//						val funcTypeResult = ts.type(G, func)
//						if (funcTypeResult.failed) return
//
//						val funcType = funcTypeResult.value
//
//						if (funcType instanceof FunctionTypeExprOrRef) {
//
//							// if there is a declared this-type
//							val reqThisType = funcType.declaredThisType
//							if (reqThisType !== null) {
//								val result = ts.subtypeTypeRef(G, inferredThisArgType, reqThisType);
//
//								// report.
//								createError(result, callExpression)
//							}
//						}
//
//					}
//
//				}
//			}
//
//		}
//
//	}

	/**
	 * This validates a warning in chapter 4.10.1:<br/>
	 * <i>The use of the any type in a union type produces a warning.</i>
	 */
	@Check
	def void checkUnionTypeContainsNoAny(UnionTypeExpression ute) {
		checkComposedTypeRefContainsNoAny(ute, messageForUNI_ANY_USED, UNI_ANY_USED, true);
	}

	/**
	 * This validates a warning in chapter 4.10.2:<br/>
	 * <i>The use of the any type in an intersection type produces a warning.</i>
	 */
	@Check
	def void checkIntersectionTypeContainsNoAny(IntersectionTypeExpression ite) {
		checkComposedTypeRefContainsNoAny(ite, messageForINTER_ANY_USED, INTER_ANY_USED, false);
	}

	def private void checkComposedTypeRefContainsNoAny(ComposedTypeRef ctr, String msg, String issueCode,
		boolean soleVoidAllowesAny) {
		val G = ctr.newRuleEnvironment;
		val anyType = G.anyType;
		val voidType = G.voidType;
		val EList<TypeRef> typeRefs = ctr.getTypeRefs();
		val Iterable<TypeRef> anyTypeRefs = typeRefs.filter[it.getDeclaredType() === anyType]; // identity check on typeRefs is OK here
		var boolean dontShowWarning = false;
		if (soleVoidAllowesAny) {
			val boolean containsVoid = typeRefs.exists[it.getDeclaredType() === voidType]; // identity check on typeRefs is OK here
			dontShowWarning = containsVoid && anyTypeRefs.size() == 1;
		}

		if (!dontShowWarning) {
			for (TypeRef anyTR : anyTypeRefs) {
				addIssue(msg, anyTR, issueCode);
			}
		}
	}

	/**
	 * This validates a warning in chapter 4.10.1:<br/>
	 * <i>The use of unnecessary subtypes in union types produces a warning.</i>
	 */
	@Check
	def void checkUnionHasUnnecessarySubtype(UnionTypeExpression ute) {
		val List<TypeRef> tRefs = extractNonStructTypeRefs(ute);
		val G = ute.newRuleEnvironment;
		val anyType = G.anyType;
		tRefs.removeIf[it.getDeclaredType === anyType]; // identity check on typeRefs is OK here
		if (tRefs.size() > 1) {
			val List<TypeRef> intersectionTR = tsh.getSuperTypesOnly(G, tRefs);
			tRefs.removeAll(intersectionTR);

			for (TypeRef tClassR : tRefs) {
				val message = messageForUNI_REDUNDANT_SUBTYPE;
				addIssue(message, tClassR, UNI_REDUNDANT_SUBTYPE);
			}
		}
	}

	/**
	 * Entry method for validating the containing types of an intersection type.
	 */
	@Check
	def void checkIntersectionType(IntersectionTypeExpression ite) {
		val List<TypeRef> tClassRefs = extractNonStructTypeRefs(ite);

		if (tClassRefs.size() > 1) {
			val G = ite.newRuleEnvironment;
			val List<TypeRef> intersectionTR = tsh.getSubtypesOnly(G, tClassRefs);

			checkIntersectionTypeContainsMaxOneClass(G, intersectionTR, false);
			checkIntersectionHasUnnecessarySupertype(tClassRefs, intersectionTR);
		}
	}

	/**
	 * This validates constraint 25.2 ("Intersection Type") in chapter 4.10.2:<br/>
	 * <i>Only one class must be contained in the intersection type.</i><br/><br/>
	 * Currently, only a warning is displayed.
	 */
	def private void checkIntersectionTypeContainsMaxOneClass(RuleEnvironment G,
			List<TypeRef> intersectionTR, boolean covariantTypeArgValidation) {
		if (intersectionTR.size() > 1) {
			
			val ArrayListMultimap<Type, TypeRef> byTypes = ArrayListMultimap.create();
			
			for (TypeRef tClassR : intersectionTR) {
				byTypes.put(tClassR.declaredType, tClassR)
			}
			
			if (byTypes.keySet.size>1) {
				if (covariantTypeArgValidation) {
					val message = messageForINTER_TYEPARGS_ONLY_ONE_CLASS_ALLOWED;
					for (TypeRef tClassR : intersectionTR) {
						if (! (tClassR.eContainer instanceof TypeVariable)) { // nested, type ref coming from def site
							addIssue(message, tClassR, INTER_TYEPARGS_ONLY_ONE_CLASS_ALLOWED);
						}
					}
				} else {
					val message = messageForINTER_ONLY_ONE_CLASS_ALLOWED;
					for (TypeRef tClassR : intersectionTR) {
						addIssue(message, tClassR, INTER_ONLY_ONE_CLASS_ALLOWED);
					}
				}
			} else {
				val type = byTypes.keys.head;
				if (type.isGeneric) {
					val List<TypeRef> ptrs = byTypes.get(type); // similar to intersectionTR

					if (allCovariantOrWildcardWithUpperBound(type.typeVars, ptrs)) {
						val length = type.typeVars.length;
						for (var v=0; v<length; v++) {
							val vIndex = v; // final
							// typerefs already simplified by initial call to this method:
							val typeArgsPerVariable = 
								extractNonStructTypeRefs(ptrs.map[
									ptr|
									val ta = ptr.declaredTypeArgs.get(vIndex);
									var TypeRef upper;
									if (ta instanceof TypeRef) {
										upper = ta; 
									}
									if (upper===null && ta instanceof Wildcard) {
										upper = (ta as Wildcard).declaredUpperBound;
									}
									if (upper===null) {
										upper = type.typeVars.get(vIndex).declaredUpperBound;
									}
									return upper;
									
								]);
							checkIntersectionTypeContainsMaxOneClass(G, typeArgsPerVariable, true);
						}
						
						// all type args use super:
					} else if (ptrs.forall[ptr | ptr.declaredTypeArgs.forall(ta| ta instanceof Wildcard &&
							(ta as Wildcard).declaredLowerBound !== null)]) {
						// all common super types, at least Object, as type arg would work! no warning.
					} else {
						// instantiation not possible except with undefined
						val message = messageForINTER_WITH_ONE_GENERIC;
						for (TypeRef tClassR : intersectionTR) {
							if (! (tClassR.eContainer instanceof TypeVariable)) { // nested, type ref coming from def site
								addIssue(message, tClassR, INTER_WITH_ONE_GENERIC);
							}
						}
					}
				}	
			}
		}
	}
		
	private def boolean allCovariantOrWildcardWithUpperBound(List<TypeVariable> typeVars, List<TypeRef> refs) throws IndexOutOfBoundsException {
		val length = typeVars.length;
		for (var i=0; i<length; i++) {
			if (! typeVars.get(i).declaredCovariant) {
				for (TypeRef ref: refs) {
					val ta = ref.declaredTypeArgs.get(i);
					if (ta instanceof Wildcard) {
						if (ta.declaredUpperBound===null) {
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
	def private void checkIntersectionHasUnnecessarySupertype(List<TypeRef> tClassRefs, List<TypeRef> intersectionTR) {
		tClassRefs.removeAll(intersectionTR);

		for (TypeRef tClassR : tClassRefs) {
			val message = messageForINTER_REDUNDANT_SUPERTYPE;
			addIssue(message, tClassR, INTER_REDUNDANT_SUPERTYPE);
		}
	}

	@Check
	def void checkTypeParameters(GenericDeclaration genDecl) {
		if (genDecl.typeVars.empty) {
			return; // nothing to check
		}
		if (!N4JSLanguageUtils.isValidLocationForOptionalTypeParameter(genDecl, N4JSPackage.Literals.GENERIC_DECLARATION__TYPE_VARS)) {
			return; // avoid duplicate error messages
		}
		if (holdsOptionalTypeParameterNotFollowedByMandatory(genDecl)) {
			if (holdsDefaultArgumentsContainValidReferences(genDecl)) {
				holdsDefaultArgumentsComplyToBounds(genDecl);
			}
		}
	}

	def private boolean holdsOptionalTypeParameterNotFollowedByMandatory(GenericDeclaration genDecl) {
		var haveOptional = false;
		for (n4TypeParam : genDecl.typeVars) {
			if (haveOptional && !n4TypeParam.optional) {
				val message = messageForTYP_TYPE_PARAM_MANDATORY_AFTER_OPTIONAL;
				addIssue(message, n4TypeParam, TypesPackage.eINSTANCE.identifiableElement_Name, TYP_TYPE_PARAM_MANDATORY_AFTER_OPTIONAL);
				return false;
			}
			haveOptional = haveOptional || n4TypeParam.optional;
		}
		return true;
	}

	/**
	 * Currently this method only checks for forward references in default arguments (e.g. <code>G&lt;T1=T2,T2=any> {}</code>).
	 * In the future, we might also check for cyclic default arguments.
	 */
	def private boolean holdsDefaultArgumentsContainValidReferences(GenericDeclaration genDecl) {
		// find forward references to type parameters declared after the current type parameter
		val badTypeVars = genDecl.typeVars.map[definedTypeVariable].filterNull.toSet;
		if (badTypeVars.size < genDecl.typeVars.size) {
			return true; // syntax error
		}
		val forwardReferences = <ParameterizedTypeRef>newArrayList;
		for (n4TypeParam : genDecl.typeVars) {
			val defaultArgInAST = n4TypeParam.declaredDefaultArgumentNode?.typeRefInAST;
			if (defaultArgInAST !== null) {
				TypeUtils.forAllTypeRefs(defaultArgInAST, ParameterizedTypeRef, true, false, null, [ptr|
					val declType = ptr.declaredType;
					if (declType instanceof TypeVariable && badTypeVars.contains(declType)) {
						val isContainedInAST = EcoreUtil2.getContainerOfType(ptr, Script) !== null;
						if (isContainedInAST) {
							forwardReferences.add(ptr);
						}
					}
					return true; // continue with traversal
				], null);
			}
			// from now on, the current type variable may be referenced in the default argument of all following type variables:
			badTypeVars.remove(n4TypeParam.definedTypeVariable);
		}
		// create error markers
		if (!forwardReferences.empty) {
			for (badRef : forwardReferences) {
				val message = messageForTYP_TYPE_PARAM_DEFAULT_REFERENCES_LATER_TYPE_PARAM;
				addIssue(message, badRef, TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE, TYP_TYPE_PARAM_DEFAULT_REFERENCES_LATER_TYPE_PARAM);
			}
			return false;
		}
		return true;
	}

	def private boolean holdsDefaultArgumentsComplyToBounds(GenericDeclaration genDecl) {
		val G = genDecl.newRuleEnvironment;
		var haveInvalidDefault = false;
		for (n4TypeParam : genDecl.typeVars) {
			if (n4TypeParam.name !== null) {
				val defaultArgInAST = n4TypeParam.declaredDefaultArgumentNode?.typeRefInAST;
				val defaultArg = n4TypeParam.declaredDefaultArgumentNode?.typeRef;
				val ub = n4TypeParam.declaredUpperBound;
				if (defaultArgInAST !== null && defaultArg !== null && ub !== null) {
					val result = ts.subtype(G, defaultArg, ub);
					if (result.failure) {
						val message = getMessageForTYP_TYPE_PARAM_DEFAULT_NOT_SUBTYPE_OF_BOUND(n4TypeParam.name, result.compiledFailureMessage);
						addIssue(message, n4TypeParam, N4JSPackage.Literals.N4_TYPE_VARIABLE__DECLARED_DEFAULT_ARGUMENT_NODE, TYP_TYPE_PARAM_DEFAULT_NOT_SUBTYPE_OF_BOUND);
						haveInvalidDefault = true;
					}
				}
			}
		}
		return !haveInvalidDefault;
	}



	def private List<TypeRef> extractNonStructTypeRefs(ComposedTypeRef ctr) {
		val typeRefs = new TreeSet<TypeRef>(typeCompareHelper.getTypeRefComparator);
		collectAndFlattenElementTypeRefs(ctr, typeRefs);
		return extractNonStructTypeRefs(typeRefs);
	}

	def private void collectAndFlattenElementTypeRefs(ComposedTypeRef ctr, Collection<TypeRef> addHere) {
		val composedTypeKind = ctr.eClass;
		for (tr : ctr.typeRefs) {
			if (composedTypeKind.isInstance(tr)) {
				collectAndFlattenElementTypeRefs(tr as ComposedTypeRef, addHere);
			} else {
				addHere += tr;
			}
		}
	}

	def private List<TypeRef> extractNonStructTypeRefs(Iterable<TypeRef> simplifiedTypeRefs) {
		val List<TypeRef> tClassRefs = new LinkedList();
		for (TypeRef tR : simplifiedTypeRefs) {
			if (tR!==null) { // may happen if argument has been a result of a computation
				val Type type = tR.getDeclaredType();
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
