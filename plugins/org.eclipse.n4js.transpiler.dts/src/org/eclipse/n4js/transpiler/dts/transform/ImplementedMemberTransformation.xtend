/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.transform

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4Modifier
import org.eclipse.n4js.n4JS.N4TypeVariable
import org.eclipse.n4js.n4JS.TypedElement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.im.TypeReferenceNode_IM
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.FieldAccessor
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMemberWithAccessModifier
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TSetter
import org.eclipse.n4js.ts.types.TTypedElement
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.util.NonSymetricMemberKey
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.ContainerTypesHelper
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * To each class declaration in the intermediate module, this transformation adds a member declaration
 * for all members of all directly or indirectly implemented interfaces, unless such a member is present
 * in the class already (owned or inherited).
 * <p>
 * In particular, this is necessary for two cases:
 * <ol>
 * <li>TypeScript cannot cope with abstract(!) classes that implement an interface I but do not themselves
 * declare all of I's members (at least as abstract).
 * <li>TypeScript does not know about default methods/getters/setters in interfaces; since implementing
 * classes in N4JS won't have an implementation for such default members, we would not export a declaration
 * for them without the below special handling.
 * </ol>
 */
class ImplementedMemberTransformation extends Transformation {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private TypeSystemHelper tsh;

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	private MemberCollector memberCollector;

	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// prepare a member collector for later use
		memberCollector = containerTypesHelper.fromContext(state.resource);
	}

	override transform() {
		collectNodes(state.im, N4ClassDeclaration, false).forEach[addMissingImplementedMembers];
	}

	private def void addMissingImplementedMembers(N4ClassDeclaration classDecl) {
		val tClass = state.info.getOriginalDefinedType(classDecl);
		if (tClass === null) {
			// an earlier transformation created a synthetic class without setting its original defined type
			throw new IllegalStateException("intermediate model contains a class declaration without original defined type");
		}
		val tSuperClass = tClass.superClass;

		val membersFromSuperClass = if (tSuperClass !== null) {
			memberCollector.allMembers(tSuperClass, false, true, true)
		} else {
			#[]
		};

		val membersOwnedAndFromSuperClassAsSet = (tClass.ownedMembers + membersFromSuperClass)
			.filter[!static]
			.map[NonSymetricMemberKey.of(it)]
			.toSet;

		val membersFromInterfaces = memberCollector.membersOfImplementedInterfacesForConsumption(tClass)
			.filter[!static]
			.filter[memberAccessModifier === MemberAccessModifier.PUBLIC] // non public interface members are not exported to .d.ts (cf. TrimForDtsTransformation)
			.toList;

		val membersToAmend = newLinkedHashMap;
		for (memberFromIfc : membersFromInterfaces) {
			val key = NonSymetricMemberKey.of(memberFromIfc);
			if (!membersOwnedAndFromSuperClassAsSet.contains(key)) {
				// note: in case of duplicates in list 'membersFromInterfaces' we want to use the *last* member,
				// because it might be more specialized than the earlier ones; therefore we here overwrite earlier
				// members with later ones if they have the same 'key':
				membersToAmend.put(key, memberFromIfc);
			}
		}

		if (membersToAmend.empty) {
			return;
		}

		// for adjusting the types of members coming from interfaces, we prepare a rule environment
		// with all implicit type variable bindings of tClass
		val G_tClass = state.G.wrap;
		tClass.superClassifierRefs.forEach[tsh.addSubstitutions(G_tClass, it)];

		for (TMember memberToAmend : membersToAmend.values) {
			val memberNew = addMember(classDecl, memberToAmend as TMemberWithAccessModifier, G_tClass);

			memberNew.declaredModifiers += N4Modifier.PUBLIC;
			val hasDefaultImpl = switch (memberToAmend) {
				TField: {
					// small inconsistency in N4JS: fields in interfaces need not be re-declared in implementing classes
					// even if they do not have an initializer expression; so in a sense they are all "default fields":
					true
					// this would be more consistent:
					// memberToAmend.hasExpression
				}
				FieldAccessor: !memberToAmend.hasNoBody
				TMethod: !memberToAmend.hasNoBody
			};
			if (!hasDefaultImpl) {
				memberNew.declaredModifiers += N4Modifier.ABSTRACT;
			}
		}
	}

	private def dispatch N4MemberDeclaration addMember(N4ClassDeclaration classDecl, TField fieldToAdd, RuleEnvironment G_tClass) {
		val fieldNew = _N4FieldDecl(false, fieldToAdd.name, null);
		classDecl.ownedMembersRaw += fieldNew;

		setDeclaredTypeRef(fieldNew, fieldToAdd, G_tClass);

		return fieldNew;
	}

	private def dispatch N4MemberDeclaration addMember(N4ClassDeclaration classDecl, TGetter getterToAdd, RuleEnvironment G_tClass) {
		val getterNew = _N4GetterDecl(_LiteralOrComputedPropertyName(getterToAdd.name), null);
		classDecl.ownedMembersRaw += getterNew;

		setDeclaredTypeRef(getterNew, getterToAdd.typeRef, G_tClass);

		return getterNew;
	}

	private def dispatch N4MemberDeclaration addMember(N4ClassDeclaration classDecl, TSetter setterToAdd, RuleEnvironment G_tClass) {
		val fpar = _Fpar("value");
		val setterNew = _N4SetterDecl(_LiteralOrComputedPropertyName(setterToAdd.name), fpar, null);
		classDecl.ownedMembersRaw += setterNew;

		setDeclaredTypeRef(fpar, setterToAdd.typeRef, G_tClass);

		return setterNew;
	}

	private def dispatch N4MemberDeclaration addMember(N4ClassDeclaration classDecl, TMethod methodToAdd, RuleEnvironment G_tClass) {
		val methodNew = _N4MethodDecl(methodToAdd.name);
		classDecl.ownedMembersRaw += methodNew;

		for (TypeVariable typeParam : methodToAdd.typeVars) {
			addTypeParameter(methodNew, typeParam, G_tClass);
		}

		for (TFormalParameter fpar : methodToAdd.fpars) {
			addFormalParameter(methodNew, fpar, G_tClass);
		}

		setReturnTypeRef(methodNew, methodToAdd.returnTypeRef, G_tClass);

		return methodNew;
	}

	private def void addTypeParameter(N4MethodDeclaration methodDecl, TypeVariable typeParamToAdd, RuleEnvironment G_tClass) {
		val typeParamNew = _N4TypeVariable(typeParamToAdd.name, typeParamToAdd.isDeclaredCovariant, typeParamToAdd.isDeclaredContravariant);
		methodDecl.typeVars += typeParamNew;

		setDeclaredUpperBound(typeParamNew, typeParamToAdd.declaredUpperBound, G_tClass);
	}

	private def void addFormalParameter(N4MethodDeclaration methodDecl, TFormalParameter fparToAdd, RuleEnvironment G_tClass) {
		val fparNew = _Fpar(fparToAdd.name, fparToAdd.variadic);
		fparNew.hasInitializerAssignment = fparToAdd.optional;
		methodDecl.fpars += fparNew;

		setDeclaredTypeRef(fparNew, fparToAdd, G_tClass);
	}

	private def void setDeclaredTypeRef(TypedElement elementInIM, TTypedElement elementInTModule, RuleEnvironment G_tClass) {
		val typeRef = elementInTModule.typeRef;
		setDeclaredTypeRef(elementInIM, typeRef, G_tClass);
	}

	private def void setDeclaredTypeRef(TypedElement elementInIM, TypeRef typeRef, RuleEnvironment G_tClass) {
		if (typeRef !== null) {
			elementInIM.declaredTypeRefNode = createLocalizedTypeRefNodeFor(typeRef, G_tClass);
		}
	}

	private def void setReturnTypeRef(FunctionDefinition funDef, TypeRef typeRef, RuleEnvironment G_tClass) {
		if (typeRef !== null) {
			funDef.declaredReturnTypeRefNode = createLocalizedTypeRefNodeFor(typeRef, G_tClass);
		}
	}

	private def void setDeclaredUpperBound(N4TypeVariable typeParam, TypeRef typeRef, RuleEnvironment G_tClass) {
		if (typeRef !== null) {
			typeParam.declaredUpperBoundNode = createLocalizedTypeRefNodeFor(typeRef, G_tClass);
		}
	}

	/**
	 * Converts the given type reference to the context defined by rule environment 'ruleEnvForSubstituion'
	 * (which will usually contain type variable bindings representing the context inside the class declaration
	 * passed to method {@link #addMissingImplementedMembers(N4ClassDeclaration)}) and creates a new
	 * {@link TypeReferenceNode_IM} for it.
	 */
	private def TypeReferenceNode_IM<TypeRef> createLocalizedTypeRefNodeFor(TypeRef typeRef, RuleEnvironment ruleEnvForSubstituion) {
		val typeRefSubst = ts.substTypeVariables(ruleEnvForSubstituion, typeRef);
		val typeRefSubstCpy = if (typeRefSubst === typeRef) TypeUtils.copy(typeRefSubst) else typeRefSubst;
		val typeRefNode = _TypeReferenceNode(state, typeRefSubstCpy);
		return typeRefNode;
	}
}
