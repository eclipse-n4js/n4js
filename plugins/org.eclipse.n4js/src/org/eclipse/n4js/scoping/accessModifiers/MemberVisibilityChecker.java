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
package org.eclipse.n4js.scoping.accessModifiers;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4TypeDefinition;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.typeRefs.UnionTypeExpression;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TObjectPrototype;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.util.AllSuperTypesCollector;
import org.eclipse.n4js.typesystem.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.TypeSystemHelper;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.util.Strings;

import com.google.inject.Inject;

/**
 * Implements the logic for accessibility checks for {@link TMember members} within a given context, e.g. for a concrete
 * expression.
 */
public class MemberVisibilityChecker {

	@Inject
	private TypeVisibilityChecker typeVisibilityChecker;
	@Inject
	private TypeSystemHelper tsh;

	/**
	 * Returns true if the given <i>enumType</i> is accessible in the given context
	 *
	 * @param context
	 *            usually a ParameterizedPropertyAccessExpression.
	 */
	public boolean isEnumLiteralVisible(EObject context, TypeRef enumType) {
		Resource contextResource = context.eResource();
		Type declaredReceiverType = getActualDeclaredReceiverType(context, enumType, contextResource.getResourceSet());
		if (typeVisibilityChecker.isVisible(contextResource, declaredReceiverType).visibility) {
			return true; // literals are public (i.e. visible across modules) by definition
		}
		return false;
	}

	/**
	 * Returns the MemberVisibility of the <i>member</i> of the <i>receiverType</> in the given <i>context</i>
	 *
	 * @param context
	 *            usually a ParameterizedPropertyAccessExpression.
	 */
	public MemberVisibility isVisible(EObject context, TypeRef receiverType, TMember member) {
		boolean supercall = context instanceof ParameterizedPropertyAccessExpression
				&& ((ParameterizedPropertyAccessExpression) context).getTarget() instanceof SuperLiteral;
		return isVisible(context, receiverType, member, supercall);
	}

	/**
	 * Returns the MemberVisibility of the <i>member</i> of the <i>receiverType</> in the given <i>context</i>
	 * <i>supercall</i> indicates if the target of the context is the super type.
	 */
	private MemberVisibility isVisible(EObject context, TypeRef receiverType, TMember member,
			boolean supercall) {
		// special case: union types
		if (receiverType instanceof UnionTypeExpression) {
			// note: we are here a bit more restrictive than required,
			// because we use the combined accessibility for all members
			for (TypeRef currUnitedTypeRef : ((UnionTypeExpression) receiverType).getTypeRefs())
				if (!isVisible(context, currUnitedTypeRef, member, supercall).visibility)
					return new MemberVisibility(false);
			return new MemberVisibility(true);
		}

		// standard case:
		Resource contextResource = context.eResource();
		N4TypeDefinition typeDefiningContainer = EcoreUtil2.getContainerOfType(context, N4TypeDefinition.class);
		Script script = EcoreUtil2.getContainerOfType(typeDefiningContainer != null ? typeDefiningContainer : context,
				Script.class);
		Type contextType = null;
		TModule contextModule = script.getModule();
		if (typeDefiningContainer != null) {
			contextType = typeDefiningContainer.getDefinedType();
		}
		Type declaredReceiverType = getActualDeclaredReceiverType(context, receiverType,
				contextResource.getResourceSet());
		if (declaredReceiverType != null
				&& typeVisibilityChecker.isVisible(contextResource, declaredReceiverType).visibility) {
			// check for local usage of locally defined member
			if (shortcutIsVisible(member, contextType, contextModule, declaredReceiverType)) {
				return new MemberVisibility(true);
			}

			return isVisible(contextModule, contextType, declaredReceiverType, member, supercall);
		}
		return new MemberVisibility(false);
	}

	/**
	 * Returns the MemberVisibility of the <i>member</i> of the <i>receiverType</> in the given <i>context</i> When
	 * checking for visibility of members to be overridden abstract members calculated as private are visible as well.
	 */
	public boolean isVisibleWhenOverriding(TModule contextModule, Type contextType, Type declaredReceiverType,
			TMember member) {
		if (member.getMemberAccessModifier() == MemberAccessModifier.PRIVATE) {
			return isModuleVisible(contextModule, member);
		}
		return isVisible(contextModule, contextType, declaredReceiverType, member, false).visibility;
	}

	/**
	 * Returns the MemberVisibility of the <i>member</i> of the <i>receiverType</> in the given <i>context</i>
	 */
	private MemberVisibility isVisible(TModule contextModule, Type contextType, Type declaredReceiverType,
			TMember member,
			boolean supercall) {

		int startIndex = member.getMemberAccessModifier().getValue();

		boolean visibility = false;
		String firstVisible = "PUBLIC";

		for (int i = startIndex; i < MemberAccessModifier.values().length; i++) {

			boolean visibilityForModifier = false;
			MemberAccessModifier modifier = MemberAccessModifier.get(i);

			switch (modifier) {

			case PRIVATE:
				visibilityForModifier = isModuleVisible(contextModule, member);
				break;
			case PROJECT:
				visibilityForModifier = isProjectVisible(contextModule, member);
				break;
			case PROTECTED_INTERNAL:
				visibilityForModifier = isProtectedInternalVisible(contextType, contextModule, declaredReceiverType,
						member,
						supercall);
				break;
			case PROTECTED:
				visibilityForModifier = isProtectedVisible(contextType, contextModule, declaredReceiverType, member,
						supercall);
				break;
			case PUBLIC_INTERNAL:
				visibilityForModifier = isPublicInternalVisible(contextType, contextModule, declaredReceiverType,
						member);
				break;
			case PUBLIC:
				visibilityForModifier = true;
				break;
			default:
				break;
			}

			// First modifier = element modifier
			if (i - startIndex < 1) {
				visibility = visibilityForModifier;
			}
			// First visible modifier = suggested element modifier
			if (visibilityForModifier) {
				firstVisible = modifier.getName();
				break;
			}
		}

		return new MemberVisibility(visibility, firstVisible);
	}

	/**
	 * Returns the actual receiver type, which usually simply is the declared type of the receiver type. However, in
	 * case of classifier references, enums, or structural type references, the actual receiver may be differently
	 * computed.
	 */
	private Type getActualDeclaredReceiverType(EObject context, TypeRef receiverType, ResourceSet resourceSet) {
		if (receiverType instanceof TypeTypeRef) {
			final RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(context);
			return tsh.getStaticType(G, (TypeTypeRef) receiverType);
		}
		if (receiverType instanceof ThisTypeRef) {
			ThisTypeRef thisTypeRef = (ThisTypeRef) receiverType;
			if (thisTypeRef.isUseSiteStructuralTyping()) {
				FunctionOrFieldAccessor foa = N4JSASTUtils.getContainingFunctionOrAccessor(thisTypeRef);
				N4ClassifierDefinition classifier = EcoreUtil2.getContainerOfType(foa, N4ClassifierDefinition.class);
				return classifier.getDefinedType();
			}
		}
		if (receiverType instanceof FunctionTypeExpression) {
			if (resourceSet == null)
				return null;
			// Change receiverType to implicit super class Function.
			BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(resourceSet);
			TObjectPrototype functionType = builtInTypeScope.getFunctionType();
			return functionType;
		}
		return receiverType.getDeclaredType();
	}

	private boolean shortcutIsVisible(TMember candidate, Type contextType, TModule contextModule, Type receiverType) {
		/* Members of the same type are always visible */
		if (receiverType == contextType && contextType == candidate.eContainer()) {
			return true;
		}
		/*
		 * object literals do not constrain accessibility
		 */
		if (receiverType instanceof TStructuralType) {
			return true;
		}

		/*
		 * Members of the same module are always visible
		 */
		if (contextModule == EcoreUtil2.getContainerOfType(candidate, TModule.class)) {
			return true;
		}
		return false;
	}

	/**
	 * @param contextType
	 *            not required for public visibility check, only context module and member are needed. Redeclared here
	 *            to keep is*Visible methods similar.
	 * @param declaredReceiverType
	 *            not required for public visibility check, only context module and member are needed. Redeclared here
	 *            to keep is*Visible methods similar.
	 */
	private boolean isPublicInternalVisible(Type contextType, TModule contextModule, Type declaredReceiverType,
			TMember member) {
		TModule receiverModule = EcoreUtil2.getContainerOfType(member, TModule.class);
		// receiverModule == null indicates either a follow-up problem or a built-in type
		return receiverModule == null || receiverModule == contextModule
				|| Strings.equal(contextModule.getVendorID(), receiverModule.getVendorID());
	}

	/**
	 * @param contextModule
	 *            not required for public visibility check, only contextType is needed. Redeclared here to keep
	 *            is*Visible methods similar.
	 * @param member
	 *            not required for public visibility check, only context module is needed. Redeclared here to keep
	 *            is*Visible methods similar.
	 */
	private boolean isProtectedVisible(Type contextType, TModule contextModule, Type declaredReceiverType,
			TMember member, boolean supercall) {
		if (isProjectVisible(contextModule, member))
			return true;
		if (contextType == declaredReceiverType) {
			return true;
		}
		return isInternalCheckProtectedVisibile(contextType, contextModule, declaredReceiverType, member, supercall);
	}

	/**
	 * Unified internal check for visibility focusing on protected-stage only. Used in
	 * {@link #isProtectedVisible(Type, TModule, Type, TMember, boolean)} and
	 * {@link #isProtectedInternalVisible(Type, TModule, Type, TMember, boolean)}
	 *
	 * <p>
	 * Preconditions:
	 * <ol>
	 * <li>contextType != declaredReceiverType
	 * </ol>
	 *
	 * @param contextModule
	 *            module where the access is issued.
	 * @param contextType
	 *            containing type where the access is issued, eg. the container-type of the method, can be null if
	 *            access happens on top-level
	 * @param member
	 *            member to access.
	 * @param declaredReceiverType
	 *            type on which the member will be accessed on. Not necessarily the definition type of the member but
	 *            one of it's sub-types.
	 * @param supercall
	 *            true if super-keyword was used.
	 */
	private boolean isInternalCheckProtectedVisibile(Type contextType, TModule contextModule,
			Type declaredReceiverType, TMember member, boolean supercall) {

		// Protected means, that the context-type is a sub-type of the receiver-type
		if (contextType == null) {
			// not type information available, maybe just parsing the script: context is relevant here.
			return false;
		}

		// contextType must be a super-type of declaredRecieverType:
		List<TClassifier> receiverSuperTypes = AllSuperTypesCollector.collect((TClassifier) declaredReceiverType);
		if (!receiverSuperTypes.contains(contextType)) {
			// Problem: if super-keyword was usesed, the call is still valid.
			if (!supercall) {
				return false;
			}
		}

		// and the member is part of a super-type (including default-method of implemented interfaces) of the
		// receiver-type
		TClassifier memberClsfContainer = EcoreUtil2.getContainerOfType(member, TClassifier.class);
		if (declaredReceiverType instanceof TClassifier &&
				receiverSuperTypes.contains(memberClsfContainer)) {
			// definition of member is done in the super-type-tree of the receiver.
			return true;
		}

		return false;
	}

	private boolean isProtectedInternalVisible(Type contextType, TModule contextModule, Type declaredReceiverType,
			TMember member, boolean supercall) {
		if (isProjectVisible(contextModule, member))
			return true;
		if (contextType == declaredReceiverType) {
			return checkVendorEquality(contextModule, member);
		}
		// apply same rules as for
		if (isInternalCheckProtectedVisibile(contextType, contextModule, declaredReceiverType, member, supercall)) {
			// check same vendor.
			return checkVendorEquality(contextModule, member);
		}

		return false;
	}

	private boolean checkVendorEquality(TModule contextModule, TMember member) {
		TModule memberModule = EcoreUtil2.getContainerOfType(member, TModule.class);
		// receiverModule == null indicates either a follow-up problem or a builtin type
		return memberModule == null || memberModule == contextModule
				|| Strings.equal(contextModule.getVendorID(), memberModule.getVendorID());
	}

	private boolean isProjectVisible(TModule contextModule, TMember member) {
		TModule memberModule = EcoreUtil2.getContainerOfType(member, TModule.class);
		// receiverModule == null indicates either a follow-up problem or a builtin type
		return memberModule == null || memberModule == contextModule
				|| Strings.equal(memberModule.getProjectName(), contextModule.getProjectName())
						&& Strings.equal(contextModule.getVendorID(), memberModule.getVendorID())
				|| typeVisibilityChecker.isTestedProjectOf(contextModule, memberModule);
	}

	private boolean isModuleVisible(TModule contextModule, TMember member) {
		TModule memberModule = EcoreUtil2.getContainerOfType(member, TModule.class);
		// receiverModule == null indicates either a follow-up problem or a builtin type
		return memberModule != null && memberModule == contextModule;
	}

	/**
	 * Check ctor-visibility in new-expressions.
	 *
	 * @param context
	 *            new-expression where the call is issued
	 * @param receiverType
	 *            type holding the constructor
	 * @param ctorMethod
	 *            the constructor method
	 *
	 * @return true if ctorMethod is accessible from context expression
	 */
	public boolean isConstructorVisible(NewExpression context, TypeRef receiverType, TMethod ctorMethod) {
		return isVisible(context, receiverType, ctorMethod, false).visibility;
	}

	/**
	 * Encapsulates visibility information as well as the minimally needed access modifier to make the element visible.
	 */
	public static class MemberVisibility {

		MemberVisibility(boolean visibility) {
			this(visibility, null);
		}

		MemberVisibility(boolean visibility, String suggestion) {
			this.visibility = visibility;
			this.accessModifierSuggestion = suggestion;
		}

		/**
		 * Visibility: true if visible
		 */
		final public boolean visibility;
		/**
		 * Access modifier needed to make the element visible.
		 */
		final public String accessModifierSuggestion;
	}
}
