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

import static org.eclipse.n4js.AnnotationDefinition.TEST_FIXME;
import static org.eclipse.n4js.AnnotationDefinition.TEST_IGNORE;
import static org.eclipse.n4js.AnnotationDefinition.TEST_METHOD;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.GENERIC_DECLARATION__TYPE_VARS;
import static org.eclipse.n4js.n4JS.N4JSPackage.Literals.PROPERTY_NAME_OWNER__DECLARED_NAME;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getDeclaredOrImplicitSuperType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectType;
import static org.eclipse.n4js.validation.IssueCodes.ANN_REQUIRES_TEST;
import static org.eclipse.n4js.validation.IssueCodes.AST_RESERVED_IDENTIFIER;
import static org.eclipse.n4js.validation.IssueCodes.CLF_ABSTRACT_BODY;
import static org.eclipse.n4js.validation.IssueCodes.CLF_ABSTRACT_FINAL;
import static org.eclipse.n4js.validation.IssueCodes.CLF_ABSTRACT_MISSING;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CALL_CONSTRUCT_SIG_BODY;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CALL_CONSTRUCT_SIG_DUPLICATE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CALL_CONSTRUCT_SIG_NOT_IN_N4JS;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CALL_CONSTRUCT_SIG_ONLY_IN_N4JSD;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CONSTRUCT_SIG_ONLY_IN_INTERFACE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CONSTRUCT_SIG_VOID_RETURN_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CTOR_ILLEGAL_MODIFIER;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CTOR_NO_TYPE_PARAMETERS;
import static org.eclipse.n4js.validation.IssueCodes.CLF_CTOR_RETURN_TYPE;
import static org.eclipse.n4js.validation.IssueCodes.CLF_DUP_MEMBER;
import static org.eclipse.n4js.validation.IssueCodes.CLF_INTERNAL_BAD_WITH_PRIVATE_OR_PROJECT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_MINIMAL_ACCESSIBILITY_IN_INTERFACES;
import static org.eclipse.n4js.validation.IssueCodes.CLF_MISSING_BODY;
import static org.eclipse.n4js.validation.IssueCodes.CLF_MISSING_CTOR_BODY;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NAME_DOLLAR;
import static org.eclipse.n4js.validation.IssueCodes.CLF_NO_FINAL_INTERFACE_MEMBER;
import static org.eclipse.n4js.validation.IssueCodes.CLF_STATIC_ABSTRACT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_VOID_ACCESSOR;
import static org.eclipse.n4js.validation.IssueCodes.ITF_CONSTRUCTOR_COVARIANCE;
import static org.eclipse.n4js.validation.IssueCodes.ITF_NO_PROPERTY_BODY;
import static org.eclipse.n4js.validation.IssueCodes.KEY_SUP_REQUIRE_EXPLICIT_SUPERCTOR_CALL;
import static org.eclipse.n4js.validation.IssueCodes.STRCT_ITF_CANNOT_CONTAIN_STATIC_MEMBERS;
import static org.eclipse.n4js.validation.IssueCodes.TYS_ADDITIONAL_STRUCTURAL_MEMBERS_ON_TYPE_VARS;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.groupBy;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.Block;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberAnnotationList;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TN4Classifier;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.VoidType;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;

/**
 * Validation of rules that apply to individual members of a classifier.
 * <p>
 *
 * Validation of rules about members:
 * <ul>
 * <li>if the rules require to take into account the other owned or inherited members of the containing classifier, then
 * the validation is contained in {@link N4JSClassifierValidator},
 * <li>if they can be checked by only looking at each member individually, then the validation is contained here.
 * </ul>
 */
@SuppressWarnings("javadoc")
public class N4JSMemberValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	@Inject
	private LazyOverrideAwareMemberCollector lazyOverrideAwareMemberCollector;

	@Inject
	private DeclMergingHelper declMergingHelper;

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

	@Check
	public void checkN4MemberDeclaration(N4MemberDeclaration n4Member) {
		if (n4Member instanceof N4MemberAnnotationList) {
			// parent already checked
			return;
		}
		TMember it = n4Member.getDefinedTypeElement();
		if (it == null) {
			return;
		}
		internalCheckNameStartsWithDollar(it);
		internalCheckAbstractAndFinal(it);
		internalCheckPrivateOrProjectWithInternalAnnotation(n4Member, it);
		internalCheckStaticMemberInShape(it);
	}

	/*
	 * This check was previously covered by ASTStructureValidator#validateName, but since computed property names are
	 * only set during post processing as of IDE-2468, we now have to perform validations for computed property names
	 * here.
	 */
	@Check
	public void checkComputedPropertyName(PropertyNameOwner owner) {
		if (owner.hasComputedPropertyName()) {
			String name = owner.getName();
			if (name != null) {
				if (!owner.isValidName()) {
					addIssue(owner, PROPERTY_NAME_OWNER__DECLARED_NAME, AST_RESERVED_IDENTIFIER.toIssueItem(name));
				}
			}
		}
	}

	@Check
	public void checkN4FieldDeclaration(N4FieldDeclaration n4Field) {
		if (n4Field instanceof N4MemberAnnotationList) {
			// parent already checked
			return;
		}
		TMember member = n4Field.getDefinedTypeElement();
		if (member == null) {
			return;
		}

		holdsMinimalMemberAccessModifier(member);
	}

	@Check
	public void checkN4MethodDeclaration(N4MethodDeclaration n4Method) {
		if (n4Method instanceof N4MemberAnnotationList) {
			// parent already checked
			return;
		}

		if (n4Method.isCallSignature() || n4Method.isConstructSignature()) {
			return; // checked below in a dedicated check method
		}

		// wrong parsed
		if (n4Method.getDefinedTypeElement() == null) {
			return;
		}

		TMethod tmethod = (TMethod) n4Method.getDefinedTypeElement();

		holdsAbstractAndBodyPropertiesOfMethod(tmethod);
		holdsConstructorConstraints(tmethod);
	}

	@Check
	public void checkCallConstructSignaturesInClassifier(N4ClassifierDefinition n4ClassifierDef) {
		List<N4MethodDeclaration> allCallSigs = toList(map(
				filter(n4ClassifierDef.getOwnedMembersRaw(), m -> m.isCallSignature()), m -> (N4MethodDeclaration) m));
		List<N4MethodDeclaration> allConstructSigs = toList(
				map(filter(n4ClassifierDef.getOwnedMembersRaw(), m -> m.isConstructSignature()),
						m -> (N4MethodDeclaration) m));

		for (N4MethodDeclaration callSig : allCallSigs) {
			holdsCallConstructSignatureConstraints(Either.forLeft(callSig), true, false, allCallSigs, allConstructSigs);
		}
		for (N4MethodDeclaration constructSig : allConstructSigs) {
			holdsCallConstructSignatureConstraints(Either.forLeft(constructSig), false, true, allCallSigs,
					allConstructSigs);
		}
	}

	@Check
	public void checkCallConstructSignaturesInStructTypeRef(StructuralTypeRef structTypeRefInAST) {
		List<TStructMethod> allCallSigs = toList(
				map(filter(structTypeRefInAST.getAstStructuralMembers(), m -> m.isASTCallSignature()),
						m -> (TStructMethod) m));
		List<TStructMethod> allConstructSigs = toList(
				map(filter(structTypeRefInAST.getAstStructuralMembers(), m -> m.isASTConstructSignature()),
						m -> (TStructMethod) m));

		for (TStructMethod callSig : allCallSigs) {
			holdsCallConstructSignatureConstraints(Either.forRight(callSig), true, false, allCallSigs,
					allConstructSigs);
		}
		for (TStructMethod constructSig : allConstructSigs) {
			holdsCallConstructSignatureConstraints(Either.forRight(constructSig), false, true, allCallSigs,
					allConstructSigs);
		}
	}

	@Check
	public void checkN4GetterDeclaration(N4GetterDeclaration n4Getter) {
		if (n4Getter instanceof N4MemberAnnotationList) {
			// parent already checked
			return;
		}

		// wrong parsed
		if (n4Getter.getDefinedGetter() == null) {
			return;
		}
		TGetter it = n4Getter.getDefinedGetter();

		holdsAbstractAndBodyPropertiesOfMethod(it);

		internalCheckGetterType(n4Getter);
	}

	@Check
	public void checkN4SetterDeclaration(N4SetterDeclaration n4Setter) {
		if (n4Setter instanceof N4MemberAnnotationList) {
			// parent already checked
			return;
		}
		if (n4Setter.getDefinedSetter() == null) {
			return;
		}
		TSetter it = n4Setter.getDefinedSetter();

		holdsAbstractAndBodyPropertiesOfMethod(it);
	}

	@Check
	public void checkN4StructuralWithOnTypeVariables(ParameterizedTypeRefStructural ptrsInAST) {
		if (!(ptrsInAST.getDeclaredType() instanceof TypeVariable))
			return;

		for (TStructMember sm : ptrsInAST.getAstStructuralMembers()) {
			addIssue(sm, TYS_ADDITIONAL_STRUCTURAL_MEMBERS_ON_TYPE_VARS.toIssueItem());
		}
	}

	private boolean holdsAbstractAndBodyPropertiesOfMethod(TMember accessorOrMethod) {
		return //
		holdsAbstractOrHasBody(accessorOrMethod) //
				&& holdsAbstractMethodMustHaveNoBody(accessorOrMethod) //
				&& holdsAbstractMethodMustNotBeStatic(accessorOrMethod) //
				&& holdsAbstractMemberContainedInAbstractClassifier(accessorOrMethod) //
				&& holdsMinimalMemberAccessModifier(accessorOrMethod);//
	}

	private void internalCheckNameStartsWithDollar(TMember member) {

		// don't validate this in external (i.e. n4jd) files
		if (!jsVariantHelper.requireCheckNameStartsWithDollar(member)) {
			return;
		}

		String name = member == null ? null : member.getName();

		// name may be null (invalid file), we do not need an NPE here
		if (member != null && name != null && name.startsWith("$")) {
			addIssue(member.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_NAME_DOLLAR.toIssueItem());
		}
	}

	private void internalCheckAbstractAndFinal(TMember member) {
		if (member.isFinal()) {
			if (member.isAbstract()) {
				addIssue(member.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_ABSTRACT_FINAL.toIssueItem(keywordProvider.keyword(member)));
			} else if (member.getContainingType() instanceof TInterface && !(member instanceof TMethod)) {
				addIssue(member.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_NO_FINAL_INTERFACE_MEMBER.toIssueItem());
			}
		}
	}

	private boolean internalCheckStaticMemberInShape(TMember member) {
		if (!member.isStatic()) {
			return true;
		}
		EObject parent = member.eContainer();
		if (parent instanceof TInterface) {
			if (((TInterface) parent).getTypingStrategy() == TypingStrategy.STRUCTURAL) {
				addIssue(member.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME,
						STRCT_ITF_CANNOT_CONTAIN_STATIC_MEMBERS.toIssueItem());
				return false;
			}
		}
		return true;
	}

	private boolean holdsConstructorConstraints(TMethod method) {
		if (method.isConstructor()) {
			if (!holdsConstructorInInterfaceDoesNotHaveBody(method)) {
				return false;
			}
			if (!holdsConstructorInInterfaceRequiresCovarianceAnnotation(method)) {
				return false;
			}
			if (!holdsConstructorNoReturnType(method)) {
				return false;
			}
			if (!holdsConstructorNoTypeParameters(method)) {
				return false;
			}
			var result = holdsConstructorModifiers(method);
			return holdsRequiredExplicitSuperCallIsFound(method) && result;
		}
		return true;
	}

	/**
	 * N4JS spec constraints 51.1
	 */
	private boolean holdsConstructorModifiers(TMethod constructor) {
		// ctor in interface may be abstract (actually it *must* be abstract)
		if ((constructor.isAbstract() && !(constructor.getContainingType() instanceof TInterface))
				|| constructor.isStatic() || constructor.isFinal() || getHasIllegalOverride(constructor)) {

			addIssue(constructor.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME,
					CLF_CTOR_ILLEGAL_MODIFIER.toIssueItem());
			return false;
		}
		return true;
	}

	/** @return true if not a static polyfill but has a {@code @Override} annotation. */
	private boolean getHasIllegalOverride(TMethod constructor) {
		return (!constructor.getContainingType().isStaticPolyfill()) &&
				AnnotationDefinition.OVERRIDE.hasAnnotation((N4MethodDeclaration) constructor.getAstElement());
	}

	/**
	 * Requirement 56 (Defining and Calling Constructors), #5.a
	 */
	private boolean holdsConstructorInInterfaceDoesNotHaveBody(TMethod constructor) {
		if (constructor.getContainingType() instanceof TInterface && !constructor.isHasNoBody()) {
			addIssue(constructor.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME,
					ITF_NO_PROPERTY_BODY.toIssueItem("Constructors", ""));
			return false;
		}
		return true;
	}

	/**
	 * Requirement 56 (Defining and Calling Constructors), #5.b
	 */
	private boolean holdsConstructorInInterfaceRequiresCovarianceAnnotation(TMethod constructor) {
		ContainerType<?> container = constructor.getContainingType();
		if (container instanceof TInterface
				&& !N4JSLanguageUtils.hasCovariantConstructor((TInterface) container, declMergingHelper)) {
			addIssue(constructor.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME,
					ITF_CONSTRUCTOR_COVARIANCE.toIssueItem());
			return false;
		}
		return true;
	}

	/**
	 * Requirement 56 (Defining and Calling Constructors), #6
	 */
	private boolean holdsConstructorNoReturnType(TMethod constructor) {
		N4MethodDeclaration constructorDecl = (N4MethodDeclaration) constructor.getAstElement();
		if (constructorDecl.getDeclaredReturnTypeRefInAST() != null) {
			addIssue(constructorDecl, FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_NODE,
					CLF_CTOR_RETURN_TYPE.toIssueItem());
			return false;
		}
		return true;
	}

	/**
	 * Requirement 56 (Defining and Calling Constructors), #8
	 */
	private boolean holdsConstructorNoTypeParameters(TMethod method) {
		if (method.getTypeVars().isEmpty()) {
			return true;
		}

		N4MethodDeclaration constructorDecl = (N4MethodDeclaration) method.getAstElement();
		Pair<Integer, Integer> offsetLength = findTypeVariablesOffset(constructorDecl);

		addIssue(constructorDecl, offsetLength.getKey(), offsetLength.getValue(),
				CLF_CTOR_NO_TYPE_PARAMETERS.toIssueItem());

		return false;
	}

	/**
	 * Determines the offset and length of the full list of type variable of the given {@link GenericDeclaration}.
	 *
	 * This does not include the delimiting characters '<' and '>'.
	 *
	 * @throws IllegalArgumentException
	 *             if the GenericDeclaration does not have any type variables.
	 */
	private Pair<Integer, Integer> findTypeVariablesOffset(GenericDeclaration genericDeclaration) {
		if (genericDeclaration.getTypeVars().isEmpty()) {
			throw new IllegalArgumentException(
					"Cannot determine offset of type variables for a GenericDeclaration without any type variables.");
		}

		List<INode> typeVariableNodes = NodeModelUtils.findNodesForFeature(genericDeclaration,
				GENERIC_DECLARATION__TYPE_VARS);
		INode firstTypeVariable = typeVariableNodes.get(0);
		INode lastTypeVariable = last(typeVariableNodes);

		return Pair.of(firstTypeVariable.getOffset(),
				lastTypeVariable.getOffset() + lastTypeVariable.getLength() - firstTypeVariable.getOffset());
	}

	/**
	 * N4JS spec constraints 44.3
	 */
	private boolean holdsRequiredExplicitSuperCallIsFound(TMethod constructor) {
		if (getBody(((N4MemberDeclaration) constructor.getAstElement())) != null) { // otherwise another validation will
																					// complain
			EObject type = constructor.eContainer();
			if (type instanceof TClass) { // otherwise another validation will complain
				RuleEnvironment G = newRuleEnvironment(constructor);
				TClassifier superClass = getDeclaredOrImplicitSuperType(G, (TClass) type);
				TMethod ctor = containerTypesHelper.fromContext(constructor).findConstructor(superClass);
				if (ctor != null && ctor != objectType(G).getOwnedCtor()) {
					if (ctor.getFpars().size() > 0) {
						boolean existsSuperCall = ((N4MethodDeclaration) constructor.getAstElement())
								.existsExplicitSuperCall();
						if (!existsSuperCall) {
							String className = ((IdentifiableElement) ctor.eContainer()).getName();
							addIssue(
									constructor.getAstElement(),
									PROPERTY_NAME_OWNER__DECLARED_NAME,
									KEY_SUP_REQUIRE_EXPLICIT_SUPERCTOR_CALL.toIssueItem(className));
							return false;
						} // else: ok so far, more checks on super call are performed in N4JSExpressionValidator
					}
				}
			}
		}
		return true;
	}

	private boolean holdsCallConstructSignatureConstraints(Either<N4MethodDeclaration, TStructMethod> methodInAST,
			boolean isCallSig, boolean isConstructSig,
			Collection<? extends EObject> allCallSigs, Collection<? extends EObject> allConstructSigs) {

		EObject astNode = (methodInAST.isLeft()) ? methodInAST.getLeft() : methodInAST.getRight();

		if (isCallSig || isConstructSig) {
			// constraint: only in .n4jsd files
			if (!jsVariantHelper.isExternalMode(astNode)) {
				addIssue(astNode, CLF_CALL_CONSTRUCT_SIG_ONLY_IN_N4JSD.toIssueItem());
				return false;
			}
			// constraint: only in shapes and EcmaScript classes
			N4ClassifierDefinition owner = (methodInAST.isLeft()) ? methodInAST.getLeft().getOwner() : null; // owners
																												// of
																												// TStructMethods
																												// are
																												// never
																												// annotated
																												// with
																												// @N4JS
			if (methodInAST.isLeft() && !N4JSLanguageUtils.isShapeOrEcmaScript(owner)) {
				addIssue(astNode, CLF_CALL_CONSTRUCT_SIG_NOT_IN_N4JS.toIssueItem());
				return false;
			}
			// constraint: must not have a body
			Block body = (methodInAST.isLeft()) ? methodInAST.getLeft().getBody() : null; // TStructMethods never have a
																							// body
			if (body != null) {
				addIssue(astNode, CLF_CALL_CONSTRUCT_SIG_BODY.toIssueItem());
				return false;
			}
			// constraint: not more than one call/construct signature per class
			boolean haveDuplicate = ((isCallSig) ? allCallSigs : allConstructSigs).size() >= 2;
			if (haveDuplicate) {
				String kind = (isCallSig) ? "call" : "construct";
				addIssue(astNode, CLF_CALL_CONSTRUCT_SIG_DUPLICATE.toIssueItem(kind));
				return false;
			}
			// constraint: private not allowed in interfaces
			TMember definedMember = (methodInAST.isLeft())
					? methodInAST.getLeft().getDefinedTypeElement()
					: methodInAST.getRight().getDefinedMember();
			boolean isPrivate = !holdsMinimalMemberAccessModifier(definedMember);
			if (isPrivate) {
				return false;
			}
		}
		if (isConstructSig) {
			// constraint: only in classes
			if (!(astNode.eContainer() instanceof N4InterfaceDeclaration
					|| astNode.eContainer() instanceof StructuralTypeRef)) {
				addIssue(astNode, CLF_CONSTRUCT_SIG_ONLY_IN_INTERFACE.toIssueItem());
				return false;
			}
			// constraint: must have non-void return type
			TFunction definedFunction = (methodInAST.isLeft())
					? methodInAST.getLeft().getDefinedFunction()
					: (TStructMethod) methodInAST.getRight().getDefinedMember();
			TypeRef returnTypeRef = definedFunction == null ? null : definedFunction.getReturnTypeRef();
			if (returnTypeRef == null || TypeUtils.isVoid(returnTypeRef)) {
				addIssue(astNode, CLF_CONSTRUCT_SIG_VOID_RETURN_TYPE.toIssueItem());
				return false;
			}
		}
		return true;
	}

	private boolean holdsAbstractOrHasBody(TMember member) {
		boolean requireCheckForMissingBody = jsVariantHelper.requireCheckForMissingBody(member);
		boolean memberIsAbstract = false;
		if (member instanceof TMethod) {
			memberIsAbstract = member.isAbstract();
		}
		if (member instanceof FieldAccessor) {
			memberIsAbstract = member.isAbstract();
		}
		if (requireCheckForMissingBody && !memberIsAbstract &&
				(getBody((N4MemberDeclaration) member.getAstElement())) == null) {
			if (member.isConstructor()) {
				addIssue(member.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_MISSING_CTOR_BODY.toIssueItem());
			} else {
				addIssue(member.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_MISSING_BODY.toIssueItem(keywordProvider.keyword(member), member.getName()));
			}
			return false;
		}
		return true;
	}

	/**
	 * Constraints 49: abstract methods/getters/setters must not be static and vice versa.
	 */
	private boolean holdsAbstractMethodMustNotBeStatic(TMember member) {
		EObject container = member.eContainer();
		if (container instanceof TN4Classifier) {
			boolean isStructural = ((TN4Classifier) container).getTypingStrategy() == TypingStrategy.STRUCTURAL;
			if (member.isAbstract() && member.isStatic() && !((TN4Classifier) container).isExternal()
					&& !isStructural) {
				addIssue(member.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_STATIC_ABSTRACT.toIssueItem(keywordProvider.keyword(member), member.getName()));
				return false;
			}
		}
		return true;
	}

	private boolean holdsAbstractMethodMustHaveNoBody(TMember member) {
		if (member.isAbstract() && getBody(((N4MemberDeclaration) member.getAstElement())) != null) {
			addIssue(member.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_ABSTRACT_BODY.toIssueItem());
			return false;
		}
		return true;
	}

	private boolean holdsAbstractMemberContainedInAbstractClassifier(TMember member) {
		if (member.isAbstract()) {
			TClassifier classifier = EcoreUtil2.getContainerOfType(member, TClassifier.class);
			if (classifier != null && !classifier.isAbstract()) {
				addIssue(member.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_ABSTRACT_MISSING
						.toIssueItem(keywordProvider.keyword(member), member.getName(), classifier.getName()));
				return false;
			}
		}
		return true;
	}

	/**
	 * Internally, internal project and internal private do not exist. (IDEBUG-658)
	 */
	private void internalCheckPrivateOrProjectWithInternalAnnotation(N4MemberDeclaration n4Member,
			TMember tmember) {
		if (AnnotationDefinition.INTERNAL.hasAnnotation(n4Member)) {
			MemberAccessModifier memberAccessModifier = tmember.getMemberAccessModifier();
			boolean hasPrivateModifier = (memberAccessModifier == MemberAccessModifier.PRIVATE);
			boolean hasProjectModifier = (memberAccessModifier == MemberAccessModifier.PROJECT);
			if (hasPrivateModifier || hasProjectModifier) {
				addIssue(tmember.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_INTERNAL_BAD_WITH_PRIVATE_OR_PROJECT.toIssueItem());
			}
		}
	}

	/**
	 * Constraint 44.3: Members of an interface must not be declared private or project
	 */
	private boolean holdsMinimalMemberAccessModifier(TMember member) {
		if (member.getContainingType() instanceof TInterface) {
			MemberAccessModifier memberAccessModifier = member.getMemberAccessModifier();
			if (memberAccessModifier == MemberAccessModifier.PRIVATE) {
				addIssue(member.getAstElement(), PROPERTY_NAME_OWNER__DECLARED_NAME,
						CLF_MINIMAL_ACCESSIBILITY_IN_INTERFACES.toIssueItem());
				return false;
			}
		}
		return true;
	}

	private void internalCheckGetterType(N4GetterDeclaration n4GetterDeclaration) {
		TypeRef declaredTypeRef = n4GetterDeclaration.getDeclaredTypeRef();
		Type getterType = declaredTypeRef == null ? null : declaredTypeRef.getDeclaredType();
		if (getterType != null && getterType instanceof VoidType) {
			addIssue(n4GetterDeclaration, PROPERTY_NAME_OWNER__DECLARED_NAME, CLF_VOID_ACCESSOR.toIssueItem());
		}
	}

	@Check
	public void checkDuplicateFieldsIn(ThisTypeRefStructural thisTypeRefStructInAST) {
		N4ClassifierDefinition n4ClassifierDefinition = EcoreUtil2.getContainerOfType(thisTypeRefStructInAST,
				N4ClassifierDefinition.class);
		if (n4ClassifierDefinition != null) {
			Type tClass = n4ClassifierDefinition.getDefinedType();
			if (tClass instanceof TClass) {
				internalCheckDuplicateFieldsIn((TClass) tClass, thisTypeRefStructInAST);
			}
		}
	}

	private void internalCheckDuplicateFieldsIn(TClass tclass, ThisTypeRefStructural thisTypeRefStructInAST) {
		boolean structFieldInitMode = thisTypeRefStructInAST
				.getTypingStrategy() == TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
		List<TMember> members = lazyOverrideAwareMemberCollector.collectAllMembers(tclass);
		Map<Pair<String, Boolean>, List<TMember>> membersByNameAndStatic = groupBy(members,
				m -> Pair.of(m.getName(), m.isStatic()));
		Map<Pair<String, Boolean>, List<TStructMember>> structuralMembersByNameAndStatic = groupBy(
				thisTypeRefStructInAST.getStructuralMembers(), sm -> Pair.of(sm.getName(), sm.isStatic()));
		for (Pair<String, Boolean> it : structuralMembersByNameAndStatic.keySet()) {
			if (membersByNameAndStatic.containsKey(it)) {
				TStructMember structuralFieldDuplicate = head(structuralMembersByNameAndStatic.get(it));
				TMember existingClassifierMember = headExceptNonExistantGetters(membersByNameAndStatic.get(it),
						structFieldInitMode);
				MemberAccessModifier memberAccessModifier = existingClassifierMember == null ? null
						: existingClassifierMember.getMemberAccessModifier();
				if (memberAccessModifier == MemberAccessModifier.PUBLIC) {
					int index = thisTypeRefStructInAST.getStructuralMembers().indexOf(structuralFieldDuplicate);
					addIssue(thisTypeRefStructInAST,
							TypeRefsPackage.Literals.STRUCTURAL_TYPE_REF__AST_STRUCTURAL_MEMBERS, index,
							CLF_DUP_MEMBER.toIssueItem(
									validatorMessageHelper.descriptionWithLine(structuralFieldDuplicate),
									validatorMessageHelper.descriptionWithLine(existingClassifierMember)));
				}
			}
		}
	}

	private TMember headExceptNonExistantGetters(Iterable<? extends TMember> members, boolean structFieldInitMode) {
		if (structFieldInitMode) {
			return head(filter(members, m -> !(m instanceof TGetter)));
		}
		return head(members);
	}

	/**
	 * A list of annotations that requires {@code @Test} annotation as well. Currently used by
	 * {@link #checkFixmeUsedWithTestAnnotation(N4MethodDeclaration)} validation method.
	 */
	public static List<AnnotationDefinition> ANNOTATIONS_REQUIRE_TEST = List.of(TEST_FIXME, TEST_IGNORE);

	/**
	 * Raises an issue if a method has any arbitrary annotation that requires {@Test} annotation as well but it does not
	 * have it. Currently:
	 * <ul>
	 * <li>{@code @Ignore} and</li>
	 * <li>{@code @Fixme}</li>
	 * </ul>
	 * requires {@code @Test} annotation.
	 */
	@Check
	public void checkFixmeUsedWithTestAnnotation(N4MethodDeclaration methodDecl) {
		for (AnnotationDefinition annotation : ANNOTATIONS_REQUIRE_TEST) {
			if (annotation.hasAnnotation(methodDecl) && !TEST_METHOD.hasAnnotation(methodDecl)) {
				addIssue(findFirst(methodDecl.getAnnotations(), ann -> Objects.equals(ann.getName(), annotation.name)),
						N4JSPackage.eINSTANCE.getAnnotation_Name(),
						ANN_REQUIRES_TEST.toIssueItem("@" + annotation.name));
			}
		}
	}

}
