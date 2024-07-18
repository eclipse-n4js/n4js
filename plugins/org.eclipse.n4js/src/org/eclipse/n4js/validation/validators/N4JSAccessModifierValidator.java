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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.validation.IssueCodes.CLF_FIELD_CONST_FINAL;
import static org.eclipse.n4js.validation.IssueCodes.CLF_FIELD_CONST_MISSING_INIT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_FIELD_CONST_STATIC;
import static org.eclipse.n4js.validation.IssueCodes.CLF_FIELD_FINAL_MISSING_INIT;
import static org.eclipse.n4js.validation.IssueCodes.CLF_FIELD_FINAL_MISSING_INIT_IN_STATIC_POLYFILL;
import static org.eclipse.n4js.validation.IssueCodes.CLF_FIELD_FINAL_STATIC;
import static org.eclipse.n4js.validation.IssueCodes.CLF_FIELD_OPTIONAL_OLD_SYNTAX;
import static org.eclipse.n4js.validation.IssueCodes.CLF_LOW_ACCESSOR_WITH_INTERNAL;
import static org.eclipse.n4js.validation.IssueCodes.EXP_OPTIONAL_INVALID_PLACE;
import static org.eclipse.n4js.validation.IssueCodes.EXP_PRIVATE_ELEMENT;
import static org.eclipse.n4js.validation.IssueCodes.STRCT_ITF_MEMBER_MUST_BE_PUBLIC;
import static org.eclipse.n4js.validation.IssueCodes.UNSUPPORTED;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toIterable;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.AnnotableElement;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.ExportableElement;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ModifiableElement;
import org.eclipse.n4js.n4JS.ModifierUtils;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.NamedExportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceExportSpecifier;
import org.eclipse.n4js.n4JS.NamespaceImportSpecifier;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.ts.typeRefs.BoundThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRefStructural;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.AccessibleTypeElement;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAccessModifier;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.StaticPolyfillHelper;
import org.eclipse.n4js.utils.StructuralTypesHelper;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.inject.Inject;

/**
 */
@SuppressWarnings("javadoc")
public class N4JSAccessModifierValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	protected N4JSTypeSystem ts;

	@Inject
	protected ContainerTypesHelper containerTypesHelper;

	@Inject
	protected StructuralTypesHelper structuralTypesHelper;

	@Inject
	protected StaticPolyfillHelper staticPolyfillHelper;

	@Inject
	protected JavaScriptVariantHelper jsVariantHelper;

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
	public void checkDirectlyExportedWhenVisibilityHigherThanPrivateForType(TypeDefiningElement typeDefiningElement) {
		if (typeDefiningElement instanceof ObjectLiteral) {
			return; // does not apply to ObjectLiterals and their defined type TStructuralType
		}
		if (typeDefiningElement instanceof NamespaceImportSpecifier) {
			return; // does not apply to NamespaceImportSpecifier and their defined type ModuleNamespaceVirtualType
		}
		if (typeDefiningElement instanceof NamespaceExportSpecifier) {
			return; // does not apply to NamespaceExportSpecifier and their defined type ModuleNamespaceVirtualType
		}

		Type type = typeDefiningElement.getDefinedType();
		if (type instanceof AccessibleTypeElement) {
			doCheckDirectlyExportedWhenVisibilityHigherThanPrivate(typeDefiningElement, (AccessibleTypeElement) type);
		}
	}

	@Check
	public void checkDirectlyExportedWhenVisibilityHigherThanPrivateForVariable(VariableDeclaration varDecl) {
		TVariable tVar = varDecl.getDefinedVariable();
		doCheckDirectlyExportedWhenVisibilityHigherThanPrivate(varDecl, tVar);
	}

	private void doCheckDirectlyExportedWhenVisibilityHigherThanPrivate(EObject astNode, AccessibleTypeElement tElem) {
		if (!jsVariantHelper.requireCheckExportedWhenVisibilityHigherThanPrivate(astNode)) {
			return; // does not apply to plain JS and DTS files
		}

		if (tElem != null
				&& !tElem.isDirectlyExported()
				&& tElem.getTypeAccessModifier().ordinal() > TypeAccessModifier.PRIVATE.ordinal()) {

			// NOTE: we are using issue code UNSUPPORTED here, because the only reason for disallowing a visibility
			// higher than
			// private on non-exported types is that it is required/useful only with separate export declarations and
			// such export
			// declarations are UNSUPPORTED in N4JS(D) at the moment.
			IssueItem issueItem = UNSUPPORTED.toIssueItem(
					"non-exported " + keywordProvider.keyword(tElem) + " with a visibility higher than private");
			ILeafNode node = findModifierNode(astNode, tElem.getTypeAccessModifier());
			if (node != null) {
				addIssue(astNode, node.getOffset(), node.getLength(), issueItem);
			} else {
				Pair<? extends EObject, ? extends EStructuralFeature> eObjectToNameFeature = findNameFeature(astNode);
				addIssue(eObjectToNameFeature.getKey(), eObjectToNameFeature.getValue(), issueItem);
			}
		}
	}

	public ILeafNode findModifierNode(EObject eo, TypeAccessModifier taModifier) {
		EObject eo2 = (eo instanceof VariableDeclaration) ? ((VariableDeclaration) eo).getVariableDeclarationContainer()
				: eo;
		if (eo2 instanceof ModifiableElement) {
			ModifiableElement me = (ModifiableElement) eo2;
			for (int i = 0; i < me.getDeclaredModifiers().size(); i++) {
				N4Modifier dm = me.getDeclaredModifiers().get(i);
				if (Objects.equals(dm.getLiteral(), taModifier.getLiteral())) {
					ILeafNode node = ModifierUtils.getNodeForModifier(me, i);
					return node;
				}
			}
		}
		return null;
	}

	@Check
	public void checkExportedElementHasAccessibilityHigherThanPrivate(ExportDeclaration exportDecl) {
		if (exportDecl.getExportedElement() != null) {
			return; // does not apply to direct exports
		}
		for (NamedExportSpecifier exportSpecifier : exportDecl.getNamedExports()) {
			IdentifierRef idRef = exportSpecifier.getExportedElement();
			IdentifiableElement exportedElement = idRef == null ? null : idRef.getId();
			if (exportedElement != null && !exportedElement.eIsProxy()) {
				if (exportedElement instanceof AccessibleTypeElement) {
					TypeAccessModifier tam = ((AccessibleTypeElement) exportedElement).getTypeAccessModifier();
					if (!(tam.ordinal() > TypeAccessModifier.PRIVATE.ordinal())) {
						IssueItem issueItem = EXP_PRIVATE_ELEMENT.toIssueItem(keywordProvider.keyword(exportedElement),
								exportedElement.getName());
						addIssue(idRef, issueItem);
					}
				}
			}
		}
	}

	@Check
	public void checkInternalUsedOnlyWithPublicAndProtected(ExportableElement exportableElement) {
		EObject parent = exportableElement.eContainer();
		Annotation annotation = null;
		if (exportableElement instanceof AnnotableElement) {
			annotation = AnnotationDefinition.INTERNAL.getAnnotation((AnnotableElement) exportableElement);
		}
		if (annotation == null && parent instanceof ExportDeclaration) {
			annotation = AnnotationDefinition.INTERNAL.getAnnotation((ExportDeclaration) parent);

		}
		if (annotation != null) {
			TypeAccessModifier typeAccessModifier = null;
			if (exportableElement instanceof VariableStatement && exportableElement.isDirectlyExported()) {
				VariableStatement vs = (VariableStatement) exportableElement;
				VariableDeclaration vd = head(vs.getVarDecl());
				typeAccessModifier = vd == null || vd.getDefinedVariable() == null ? null
						: vd.getDefinedVariable().getTypeAccessModifier();
			} else if (exportableElement instanceof FunctionDeclaration) {
				FunctionDeclaration fd = (FunctionDeclaration) exportableElement;
				typeAccessModifier = fd.getDefinedType() == null ? null : fd.getDefinedType().getTypeAccessModifier();
			} else if (exportableElement instanceof TypeDefiningElement) {
				TypeDefiningElement tde = (TypeDefiningElement) exportableElement;
				typeAccessModifier = tde.getDefinedType() == null ? null : tde.getDefinedType().getTypeAccessModifier();
			}

			if (typeAccessModifier != null && typeAccessModifier.ordinal() <= TypeAccessModifier.PROJECT.ordinal()) {
				IssueItem issueItem = CLF_LOW_ACCESSOR_WITH_INTERNAL.toIssueItem(
						keywordProvider.keyword(exportableElement),
						keywordProvider.keyword(typeAccessModifier));
				Pair<? extends EObject, ? extends EStructuralFeature> eObjectToNameFeature = findNameFeature(
						exportableElement);
				addIssue(eObjectToNameFeature.getKey(), eObjectToNameFeature.getValue(), issueItem);
			}
		}
	}

	@Check
	public void checkTypeRefOptionalFlag(TypeRef typeRefInAST) {
		if (typeRefInAST.isFollowedByQuestionMark()) {
			EObject parent = typeRefInAST.eContainer();
			if (parent instanceof TypeReferenceNode<?>) {
				parent = parent.eContainer();
			}

			boolean isLegalUseOfOptional = isReturnTypeButNotOfAGetter(typeRefInAST, parent);

			if (!isLegalUseOfOptional) {

				if (parent instanceof TFormalParameter) {
					return; // avoid duplicate error messages
				} else if (parent instanceof N4FieldDeclaration || parent instanceof TField) {
					ICompositeNode node = NodeModelUtils.findActualNodeFor(typeRefInAST);
					if (node != null) {
						addIssue(typeRefInAST, node.getOffset(), node.getLength(),
								CLF_FIELD_OPTIONAL_OLD_SYNTAX.toIssueItem());
					}
				} else {
					ICompositeNode node = NodeModelUtils.findActualNodeFor(typeRefInAST);
					if (node != null) {
						addIssue(typeRefInAST, node.getOffset(), node.getLength(),
								EXP_OPTIONAL_INVALID_PLACE.toIssueItem());
					}
				}
			}
		}
	}

	private boolean isReturnTypeButNotOfAGetter(TypeRef typeRefInAST, EObject parent) {
		return (parent instanceof FunctionDefinition
				&& ((FunctionDefinition) parent).getDeclaredReturnTypeRefInAST() == typeRefInAST &&
				!(parent instanceof N4GetterDeclaration))

				|| (parent instanceof TFunction && ((TFunction) parent).getReturnTypeRef() == typeRefInAST
						&& !(parent instanceof TGetter))

				|| (parent instanceof FunctionTypeExpression
						&& ((FunctionTypeExpression) parent).getReturnTypeRef() == typeRefInAST);
	}

	@Check
	public void checkShapeMembers(N4MemberDeclaration n4member) {
		if (n4member.eContainer() instanceof N4InterfaceDeclaration
				&& ((N4InterfaceDeclaration) n4member.eContainer()).getTypingStrategy() == TypingStrategy.STRUCTURAL
				&& !n4member.getDeclaredModifiers().contains(N4Modifier.PUBLIC)
				&& !n4member.getDeclaredModifiers().contains(N4Modifier.PRIVATE) // see:
																					// CLF_MINIMAL_ACCESSIBILITY_IN_INTERFACES
				&& !((N4InterfaceDeclaration) n4member.eContainer()).getDeclaredModifiers()
						.contains(N4Modifier.PUBLIC)) {
			addIssue(n4member, N4JSPackage.eINSTANCE.getPropertyNameOwner_DeclaredName(),
					STRCT_ITF_MEMBER_MUST_BE_PUBLIC.toIssueItem());
		}
	}

	@Check
	public void checkFieldConstFinalValidCombinations(N4FieldDeclaration n4field) {
		if (n4field.isConst() && n4field.isDeclaredStatic()) {
			addIssue(n4field, N4JSPackage.eINSTANCE.getPropertyNameOwner_DeclaredName(),
					CLF_FIELD_CONST_STATIC.toIssueItem());
		} else if (n4field.isConst() && n4field.isFinal()) {
			addIssue(n4field, N4JSPackage.eINSTANCE.getPropertyNameOwner_DeclaredName(),
					CLF_FIELD_CONST_FINAL.toIssueItem());
		} else if (n4field.isFinal() && n4field.isDeclaredStatic()) {
			addIssue(n4field, N4JSPackage.eINSTANCE.getPropertyNameOwner_DeclaredName(),
					CLF_FIELD_FINAL_STATIC.toIssueItem());
		}
	}

	@Check
	public void checkFieldConstInitialization(N4FieldDeclaration n4field) {
		if (!jsVariantHelper.constantHasInitializer(n4field)) {
			return; // in .n4jsd --> anything goes
		}

		if (n4field.isConst() && n4field.getExpression() == null) {
			addIssue(n4field, N4JSPackage.eINSTANCE.getPropertyNameOwner_DeclaredName(),
					CLF_FIELD_CONST_MISSING_INIT.toIssueItem(n4field.getName()));
		}
	}

	/**
	 * 5.2.3.1, Constraint 58.3
	 */
	@Check
	public void checkFieldFinalInitialization(N4ClassifierDefinition n4classifier) {
		if (!jsVariantHelper.requireCheckFinalFieldIsInitialized(n4classifier)) {
			return; // in .n4jsd --> anything goes
		}

		Iterable<N4FieldDeclaration> finalFieldsWithoutInit = filter(n4classifier.getOwnedFields(),
				f -> f != null && f.isFinal() && f.getExpression() == null);
		if (isEmpty(finalFieldsWithoutInit)) {
			return; // nothing to check here
		}

		finalFieldsWithoutInit = filterFieldsInitializedViaSpecConstructor(n4classifier, finalFieldsWithoutInit);
		if (isEmpty(finalFieldsWithoutInit)) {
			return; // nothing to anymore
		}

		finalFieldsWithoutInit = filterFieldsInitializedExplicitlyInConstructor(n4classifier, finalFieldsWithoutInit);
		if (isEmpty(finalFieldsWithoutInit)) {
			return; // nothing to do anymore
		}

		// create error messages:
		boolean replacedByPolyfill = n4classifier.getOwnedCtor() != polyfilledOrOwnCtor(n4classifier);
		if (replacedByPolyfill) {
			for (N4FieldDeclaration fd : finalFieldsWithoutInit) {
				addIssue(fd, N4JSPackage.eINSTANCE.getPropertyNameOwner_DeclaredName(),
						CLF_FIELD_FINAL_MISSING_INIT_IN_STATIC_POLYFILL.toIssueItem(fd.getName()));
			}
		} else {
			for (N4FieldDeclaration fd : finalFieldsWithoutInit) {
				addIssue(fd, N4JSPackage.eINSTANCE.getPropertyNameOwner_DeclaredName(),
						CLF_FIELD_FINAL_MISSING_INIT.toIssueItem(fd.getName()));
			}
		}
	}

	private Iterable<N4FieldDeclaration> filterFieldsInitializedViaSpecConstructor(
			N4ClassifierDefinition n4classifier, Iterable<N4FieldDeclaration> finalFieldsWithoutInit) {

		if (null == n4classifier.getDefinedType()) {
			return Collections.emptyList();
		}

		N4MethodDeclaration ctor = polyfilledOrOwnCtor(n4classifier);
		TMethod tctor;
		if (ctor == null) {
			tctor = containerTypesHelper.fromContext(n4classifier).findConstructor(
					(ContainerType<?>) n4classifier.getDefinedType());
		} else {
			tctor = (TMethod) ctor.getDefinedTypeElement();
		}

		TFormalParameter specPar = tctor == null || tctor.getFpars() == null ? null
				: findFirst(tctor.getFpars(), fp -> AnnotationDefinition.SPEC.hasAnnotation(fp));
		TypeRef typeRef = specPar == null ? null : specPar.getTypeRef();
		if (typeRef instanceof ThisTypeRefStructural) {
			BoundThisTypeRef boundThisTypeRef = TypeUtils.createBoundThisTypeRefStructural(
					TypeUtils.createTypeRef(n4classifier.getDefinedType()), (ThisTypeRefStructural) typeRef);

			Iterable<TMember> structuralMembers = structuralTypesHelper.collectStructuralMembers(
					newRuleEnvironment(n4classifier),
					boundThisTypeRef,
					TypingStrategy.STRUCTURAL_FIELDS);
			Set<String> specMemberFieldName = toSet(map(structuralMembers, m -> m.getName()));

			return filter(finalFieldsWithoutInit, fd -> !specMemberFieldName.contains(fd.getName()));
		}
		return finalFieldsWithoutInit;
	}

	private Iterable<N4FieldDeclaration> filterFieldsInitializedExplicitlyInConstructor(
			N4ClassifierDefinition n4classifier, Iterable<N4FieldDeclaration> finalFieldsWithoutInit) {

		N4MethodDeclaration ctor = polyfilledOrOwnCtor(n4classifier);

		Collection<TField> finalFieldsAssignedInCtor;
		if (ctor == null || ctor.getBody() == null) {
			finalFieldsAssignedInCtor = Collections.emptyList();
		} else {
			finalFieldsAssignedInCtor = toSet(filterNull(
					map(flatten(map(toIterable(ctor.getBody().getAllStatements()), s -> toIterable(s.eAllContents()))),
							elem -> isAssignmentToFinalFieldInThis(elem))));
		}
		return filter(finalFieldsWithoutInit, fd -> !finalFieldsAssignedInCtor.contains(fd.getDefinedField()));
	}

	private TField isAssignmentToFinalFieldInThis(EObject astNode) {
		if (astNode instanceof AssignmentExpression) {
			Expression lhs = ((AssignmentExpression) astNode).getLhs();
			if (lhs instanceof ParameterizedPropertyAccessExpression) {
				ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) lhs;
				if (ppae.getTarget() instanceof ThisLiteral) {
					IdentifiableElement prop = ppae.getProperty();
					if (prop instanceof TField) {
						if (((TField) prop).isFinal()) {
							return (TField) prop;
						}
					}
				}
			}
		}
		return null;
	}

	/** fetch the statically polyfilled ctor, or the own one. returns null if both are not defined. */
	private N4MethodDeclaration polyfilledOrOwnCtor(N4ClassifierDefinition n4classifier) {
		// if static polyfill is available, then look for filled in ctor.
		boolean polyAware = n4classifier.getDefinedType().getContainingModule().isStaticPolyfillAware();
		N4ClassDeclaration polyfill = (polyAware)
				? staticPolyfillHelper.getStaticPolyfill(n4classifier.getDefinedType())
				: null;
		N4MethodDeclaration polyfillCtor = polyfill == null ? null : polyfill.getOwnedCtor();

		N4MethodDeclaration ctor = polyfillCtor != null ? polyfillCtor : n4classifier.getOwnedCtor();
		return ctor;
	}
}
