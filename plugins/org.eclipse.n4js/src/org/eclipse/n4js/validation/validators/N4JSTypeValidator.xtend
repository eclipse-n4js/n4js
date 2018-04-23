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
import java.util.LinkedList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.AssignmentOperator
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ExpressionAnnotationList
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.GenericDeclaration
import org.eclipse.n4js.n4JS.GetterDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.n4JS.TypedElement
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.UnaryOperator
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.extensions.ExpressionExtensions
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
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
import org.eclipse.n4js.ts.types.AnyType
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
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.types.VoidType
import org.eclipse.n4js.ts.types.util.Variance
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.RuleEnvironmentExtensions
import org.eclipse.n4js.typesystem.TypeSystemHelper
import org.eclipse.n4js.typesystem.TypingStrategyFilter
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xsemantics.runtime.Result
import org.eclipse.xsemantics.runtime.RuleEnvironment
import org.eclipse.xsemantics.runtime.validation.XsemanticsValidatorErrorGenerator
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.ts.typeRefs.TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE
import static org.eclipse.n4js.validation.IssueCodes.*

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * Class for validating the N4JS types.
 */
class N4JSTypeValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private XsemanticsValidatorErrorGenerator errorGenerator;

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;

	@Inject
	private N4JSScopeProvider n4jsScopeProvider;

	@Inject
	protected ContainerTypesHelper containerTypesHelper;
	
	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

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
				if (declType instanceof ContainerType<?> && declType.final) {
					if (declType === functionType) {
						// important exception (until function type expressions are supported as bounds):
						// class C<T extends Function> {} makes sense even though Function is final
						return;
					}
					val message = getMessageForCLF_UPPER_BOUND_FINAL(declType.name, typeVar.name);
					addIssue(message, ub, PARAMETERIZED_TYPE_REF__DECLARED_TYPE, CLF_UPPER_BOUND_FINAL);
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
	def checkParameterizedTypeRef(ParameterizedTypeRef paramTypeRef) {
		val declaredType = paramTypeRef.declaredType;
		if (declaredType === null || declaredType.eIsProxy) {
			return;
		}

		// this validation might be removed in the future, see GHOLD-204
		if (declaredType instanceof TFunction) {
			if (!(paramTypeRef.eContainer instanceof TypeTypeRef)) { // avoid duplicate error message
				addIssue(getMessageForTYS_FUNCTION_DISALLOWED_AS_TYPE(), paramTypeRef, TYS_FUNCTION_DISALLOWED_AS_TYPE);
				return;
			}
		}

		val isInTypeTypeRef = paramTypeRef.eContainer instanceof TypeTypeRef || (
			paramTypeRef.eContainer instanceof Wildcard && paramTypeRef.eContainer.eContainer instanceof TypeTypeRef);

		internalCheckValidLocationForVoid(paramTypeRef);
		if (isInTypeTypeRef) {
			internalCheckValidTypeInTypeTypeRef(paramTypeRef);
		} else {
			internalCheckTypeArguments(declaredType.typeVars, paramTypeRef.typeArgs, false, declaredType, paramTypeRef,
				TypeRefsPackage.eINSTANCE.parameterizedTypeRef_DeclaredType);
		}
		internalCheckDynamic(paramTypeRef);

		internalCheckStructuralPrimitiveTypeRef(paramTypeRef);
	}

	/**
	 * Add an issue if explicit use of structural type operator with a primitive type is detected.
	 */
	def private void internalCheckStructuralPrimitiveTypeRef(ParameterizedTypeRef typeRef) {
		if (typeRef.declaredType instanceof PrimitiveType && typeRef.typingStrategy != TypingStrategy.NOMINAL) {
			addIssue(IssueCodes.messageForTYS_STRUCTURAL_PRIMITIVE, typeRef, TYS_STRUCTURAL_PRIMITIVE);
		}
	}

	/**
	 * Requirements 13, Void type.
	 */
	def private void internalCheckValidLocationForVoid(ParameterizedTypeRef typeRef) {
		if (typeRef.declaredType instanceof VoidType) {
			val isValidLocationForVoid = (
					typeRef.eContainer instanceof FunctionDefinition &&
				typeRef.eContainmentFeature === N4JSPackage.eINSTANCE.functionDefinition_ReturnTypeRef
				) || (
					typeRef.eContainer instanceof FunctionTypeExpression &&
				typeRef.eContainmentFeature === TypeRefsPackage.eINSTANCE.functionTypeExpression_ReturnTypeRef
				) || (
					typeRef.eContainer instanceof TFunction && typeRef.eContainmentFeature === TypesPackage.eINSTANCE.TFunction_ReturnTypeRef
				) || (
					// void is not truly allowed as the return type of a getter, but there's a separate validation for
					// that; so treat this case as legal here:
					typeRef.eContainer instanceof GetterDeclaration &&
				typeRef.eContainmentFeature === N4JSPackage.eINSTANCE.typedElement_DeclaredTypeRef
				);
			if (!isValidLocationForVoid) {
				addIssue(IssueCodes.getMessageForTYS_VOID_AT_WRONG_LOCATION, typeRef, TYS_VOID_AT_WRONG_LOCATION);
			}
		}
	}

	def private void internalCheckValidTypeInTypeTypeRef(ParameterizedTypeRef paramTypeRef) {
		// IDE-785 uses ParamterizedTypeRefs in ClassifierTypeRefs. Currently Type Arguments are not supported in ClassifierTypeRefs, so
		// we actively forbid them here. Will be loosened for IDE-1310
		if (! paramTypeRef.typeArgs.isEmpty) {
			addIssue(IssueCodes.getMessageForAST_NO_TYPE_ARGS_IN_CLASSIFIERTYPEREF, paramTypeRef,
				AST_NO_TYPE_ARGS_IN_CLASSIFIERTYPEREF)
		} else if (paramTypeRef instanceof FunctionTypeRef) {
			addIssue(IssueCodes.getMessageForAST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF, paramTypeRef,
				AST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF)
		} else if (paramTypeRef.declaredType instanceof TFunction) {
			addIssue(IssueCodes.getMessageForAST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF, paramTypeRef,
				AST_NO_FUNCTIONTYPEREFS_IN_CLASSIFIERTYPEREF)
		}
	}

	@Check
	def checkThisTypeRef(ThisTypeRef thisTypeRef) {
		if (thisTypeRef instanceof BoundThisTypeRef) {
			// the below validations do not apply to BoundThisTypeRefs
			// (note: normally, BoundThisTypeRefs should never appear in the AST, anyway; but asserting this to be true
			// is not the job of this validation)
			return;
		}
		if (!(thisTypeRef.isUsedStructurallyAsFormalParametersInTheConstructor 
			|| thisTypeRef.isUsedAtCovariantPositionInClassifierDeclaration 
			|| thisTypeRef.isUsedInVariableWithSyntaxError)) {
			addIssue(IssueCodes.getMessageForAST_THIS_WRONG_PLACE, thisTypeRef, IssueCodes.AST_THIS_WRONG_PLACE);
		}
	}

	def private boolean isUsedStructurallyAsFormalParametersInTheConstructor(ThisTypeRef thisTypeRef) {
		if (thisTypeRef.useSiteStructuralTyping) {
			val methodOrConstructor = thisTypeRef?.eContainer?.eContainer;
			if (methodOrConstructor instanceof N4MethodDeclaration) {
				if (methodOrConstructor !== null && methodOrConstructor.isConstructor) {
					return true
				}
			}
		}
		return false
	}

	def private boolean isUsedAtCovariantPositionInClassifierDeclaration(ThisTypeRef thisTypeRef) {
		val classifierDecl = EcoreUtil2.getContainerOfType(thisTypeRef, N4ClassifierDeclaration);
		if (classifierDecl !== null) {
			// exception: disallow for static members of interfaces
			if (classifierDecl instanceof N4InterfaceDeclaration) {
				val memberDecl = EcoreUtil2.getContainerOfType(thisTypeRef, N4MemberDeclaration);
				if (memberDecl !== null && memberDecl.static) {
					return false;
				}
			}
			val varianceOfPos = N4JSLanguageUtils.getVarianceOfPosition(thisTypeRef);
			if (varianceOfPos !== null) {
				return varianceOfPos === Variance.CO;
			}
		}
		return false;
	}

	private def boolean isUsedInVariableWithSyntaxError(ThisTypeRef ref) {
		val container = ref.eContainer
		if (container instanceof VariableDeclaration) {
			return container.name === null
		}
		return false
	}

	@Check
	def checkSymbolReference(TypeRef typeRef) {
		var bits = BuiltInTypeScope.get(typeRef?.eResource.resourceSet);
		if (typeRef.declaredType === bits.symbolObjectType) {
			// we have a type reference to 'Symbol'
			// -> the only allowed case is as target/receiver of a property access (i.e.: Symbol.iterator)
			val parent = typeRef.eContainer;
			if (!(parent instanceof ParameterizedPropertyAccessExpression) ||
				(parent as ParameterizedPropertyAccessExpression).target !== typeRef) {
				addIssue(IssueCodes.getMessageForBIT_SYMBOL_INVALID_USE, typeRef, BIT_SYMBOL_INVALID_USE);
			}
		}
	}

	/*
	 * Constraints 08: primitive types except any must not be declared dynamic
	 */
	def internalCheckDynamic(ParameterizedTypeRef ref) {
		if (ref.dynamic) {
			val Type t = ref.declaredType;
			if (t instanceof PrimitiveType && ! (t instanceof AnyType)) {
				addIssue(IssueCodes.getMessageForTYS_PRIMITIVE_TYPE_DYNAMIC(t.name), ref,
					TYS_PRIMITIVE_TYPE_DYNAMIC);
			}
		}
	}

	@Check
	def checkTypeHiddenByTypeVariable(GenericDeclaration genDecl) {

		// TODO reconsider this warning or its implementation; re-generating the scope can become quite expensive
		// (but note that moving this to the scoping code is not trivial, because warning has to be generated also
		// if not references to the type parameter are made!)
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
						!(AbstractDescriptionWithError.isErrorDescription_XTEND_MVN_BUG_HACK(hiddenTypeDscr))) {
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
						val expectedType = ts.expectedTypeIn(G, assExpr, assExpr.rhs).value;
						val TypeRef typeOfGetterRAW = TypeUtils.getMemberTypeRef(getter);
						if (expectedType !== null && typeOfGetterRAW !== null) {
							val TypeRef typeOfGetter = ts.substTypeVariablesInTypeRef(G, typeOfGetterRAW);
							if (typeOfGetter !== null) {
								val result = ts.subtype(G, typeOfGetter, expectedType);
								createError(result, assExpr.lhs);
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
				val subst = ts.substTypeVariables(G, TypeUtils.createTypeRef(tv)).value;
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
		if (createError(inferredType, expression)) {
			return;
		}

		// use a fresh environment for expectations
		G = newRuleEnvironment(expression);

		val expectedType = ts.expectedTypeIn(G, expression.eContainer, expression);
		if (expectedType.value !== null) {

			val expectedTyeRef = expectedType.value;

			// for certain problems in single-expression arrow functions, we want a special error message
			val singleExprArrowFunction = N4JSASTUtils.getContainingSingleExpressionArrowFunction(expression);
			if (singleExprArrowFunction !== null && TypeUtils.isVoid(inferredType.value)) {
				if (TypeUtils.isVoid(expectedTyeRef) || singleExprArrowFunction.isReturnValueOptional) {
					return; // all good
				}
				if (singleExprArrowFunction.returnTypeRef === null) { // show specialized error message only if return type of arrow function was inferred (i.e. not declared explicitly)
					val message = IssueCodes.
						getMessageForFUN_SINGLE_EXP_LAMBDA_IMPLICIT_RETURN_ALLOWED_UNLESS_VOID();
					addIssue(message, expression,
						IssueCodes.FUN_SINGLE_EXP_LAMBDA_IMPLICIT_RETURN_ALLOWED_UNLESS_VOID);
					return;
				}
			}

			internalCheckUseOfUndefinedExpression(G, expression, expectedTyeRef, inferredType.value);

			val boolean writeAccess = ExpressionExtensions.isLeftHandSide(expression);
			if (writeAccess) {

				// special case: write access
				val result = ts.subtype(G, expectedTyeRef, inferredType.value);

				if (result.failed) {
					// use custom error message, because otherwise it will be completely confusing
					val message = getMessageForTYS_NO_SUPERTYPE_WRITE_ACCESS(expectedTyeRef.typeRefAsString,
						inferredType.value.typeRefAsString);
					addIssue(message, expression, TYS_NO_SUPERTYPE_WRITE_ACCESS)
				}
			} else {

				// standard case: read access
				val result = ts.subtype(G, inferredType.value, expectedTyeRef)
				// not working, as primitive types are not part of currently validated resource:
				// errorGenerator.generateErrors(this, result, expression)
				// so we create error here differently:
				val errorCreated = createError(result, expression)

				if (! errorCreated && expression instanceof ObjectLiteral) {
					internalCheckSuperfluousPropertiesInObjectLiteral(expectedTyeRef, expression as ObjectLiteral);
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
	
			val strategyFilter = new TypingStrategyFilter(typingStrategy);
			val expectedMembers = containerTypesHelper.fromContext(objectLiteral).allMembers(
				type as ContainerType<?>).filter[member|strategyFilter.apply(member)].map[member|member.name].
				toSet();
	
			// These are available via the 'with' keyword add them to the accepted ones
			typeRef.structuralMembers.forEach[member|expectedMembers.add(member.name)];
	
			val inputMembers = (objectLiteral.definedType as ContainerType<?>).ownedMembers;
			for (property : inputMembers) {
				if (!expectedMembers.contains(property.name)) {
					var astElement = property.astElement;
					if (astElement instanceof PropertyNameValuePair) {
						astElement = astElement.declaredName;
					}
					val message = getMessageForCLF_SPEC_SUPERFLUOUS_PROPERTIES(property.name,
						typeRef.typeRefAsString);
					addIssue(message, astElement, CLF_SPEC_SUPERFLUOUS_PROPERTIES);
				}
			};
		}
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

	@Check
	def void checkBogusTypeReference(TypedElement te) {
		if (te.bogusTypeRef !== null) {
			addIssue(IssueCodes.getMessageForTYS_INVALID_TYPE_SYNTAX, te.bogusTypeRef, TYS_INVALID_TYPE_SYNTAX);
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
	def boolean createError(Result<?> result, EObject source) {
		if (result.failed) {
			errorGenerator.generateErrors(getMessageAcceptor(), result, source);
			return true;
		}
		false;
	}

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

			checkIntersectionTypeContainsMaxOneClass(ite, G, tClassRefs, intersectionTR);
			checkIntersectionHasUnnecessarySupertype(ite, G, tClassRefs, intersectionTR);
		}
	}

	/**
	 * This validates constraint 25.2 ("Intersection Type") in chapter 4.10.2:<br/>
	 * <i>Only one class must be contained in the intersection type.</i><br/><br/>
	 * Currently, only a warning is displayed.
	 */
	def private void checkIntersectionTypeContainsMaxOneClass(IntersectionTypeExpression ite, RuleEnvironment G,
		List<TypeRef> tClassRefs, List<TypeRef> intersectionTR) {
		if (intersectionTR.size() > 1) {
			for (TypeRef tClassR : intersectionTR) {
				val message = messageForINTER_ONLY_ONE_CLASS_ALLOWED;
				addIssue(message, tClassR, INTER_ONLY_ONE_CLASS_ALLOWED);
			}
		}
	}

	/**
	 * This validates a warning in chapter 4.10.2:<br/>
	 * <i>The use of unnecessary supertypes in intersection types produces a warning.</i>
	 */
	def private void checkIntersectionHasUnnecessarySupertype(IntersectionTypeExpression ite, RuleEnvironment G,
		List<TypeRef> tClassRefs, List<TypeRef> intersectionTR) {
		tClassRefs.removeAll(intersectionTR);

		for (TypeRef tClassR : tClassRefs) {
			val message = messageForINTER_REDUNDANT_SUPERTYPE;
			addIssue(message, tClassR, INTER_REDUNDANT_SUPERTYPE);
		}
	}

	def private List<TypeRef> extractNonStructTypeRefs(ComposedTypeRef ctr) {
		val List<TypeRef> tClassRefs = new LinkedList();
		val G = ctr.newRuleEnvironment;
		val List<TypeRef> tRefs = tsh.getSimplifiedTypeRefs(G, ctr);

		for (TypeRef tR : tRefs) {
			val Type type = tR.getDeclaredType();
			if (type instanceof TClass) {
				var isStructural = tR.isDefSiteStructuralTyping() || tR.isUseSiteStructuralTyping();
				if (!isStructural)
					tClassRefs.add(tR);
			}
		}
		return tClassRefs;
	}
}
	