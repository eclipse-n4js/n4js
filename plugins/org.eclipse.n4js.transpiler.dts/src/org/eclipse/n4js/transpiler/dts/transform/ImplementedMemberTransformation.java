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
package org.eclipse.n4js.transpiler.dts.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._Fpar;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._LiteralOrComputedPropertyName;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4FieldDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4GetterDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4MethodDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4SetterDecl;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4TypeVariable;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._TypeReferenceNode;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.operator_plus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4Modifier;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.N4TypeVariable;
import org.eclipse.n4js.n4JS.TypedElement;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.im.TypeReferenceNode_IM;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.FieldAccessor;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TTypedElement;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.ts.types.util.NonSymetricMemberKey;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;

import com.google.inject.Inject;

/**
 * To each class declaration in the intermediate module, this transformation adds a member declaration for all members
 * of all directly or indirectly implemented interfaces, unless such a member is present in the class already (owned or
 * inherited).
 * <p>
 * In particular, this is necessary for two cases:
 * <ol>
 * <li>TypeScript cannot cope with abstract(!) classes that implement an interface I but do not themselves declare all
 * of I's members (at least as abstract).
 * <li>TypeScript does not know about default methods/getters/setters in interfaces; since implementing classes in N4JS
 * won't have an implementation for such default members, we would not export a declaration for them without the below
 * special handling.
 * </ol>
 */
public class ImplementedMemberTransformation extends Transformation {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private TypeSystemHelper tsh;

	@Override
	public void assertPreConditions() {
		// empty
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void analyze() {
		// empty
	}

	@Override
	public void transform() {
		for (N4ClassDeclaration cd : collectNodes(getState().im, N4ClassDeclaration.class, false)) {
			addMissingImplementedMembers(cd);
		}
	}

	private void addMissingImplementedMembers(N4ClassDeclaration classDecl) {
		TClass tClass = getState().info.getOriginalDefinedType(classDecl);
		if (tClass == null) {
			// an earlier transformation created a synthetic class without setting its original defined type
			throw new IllegalStateException(
					"intermediate model contains a class declaration without original defined type");
		}
		TClass tSuperClass = tClass.getSuperClass();

		List<TMember> membersFromSuperClass = (tSuperClass != null)
				? getState().memberCollector.allMembers(tSuperClass, false, true, true)
				: new ArrayList<>();

		Set<NonSymetricMemberKey> membersOwnedAndFromSuperClassAsSet = new HashSet<>();
		for (TMember member : operator_plus(tClass.getOwnedMembers(), membersFromSuperClass)) {
			if (!member.isStatic()) {
				membersOwnedAndFromSuperClassAsSet.add(NonSymetricMemberKey.of(member));
			}
		}

		List<TMember> membersFromInterfaces = new ArrayList<>();
		for (TMember member : getState().memberCollector.membersOfImplementedInterfacesForConsumption(tClass)) {
			if (!member.isStatic()
					&& member.getMemberAccessModifier() == MemberAccessModifier.PUBLIC) {
				// non public interface members are not exported to .d.ts (cf. TrimForDtsTransformation)
				membersFromInterfaces.add(member);
			}
		}

		Map<NonSymetricMemberKey, TMember> membersToAmend = new LinkedHashMap<>();
		for (TMember memberFromIfc : membersFromInterfaces) {
			NonSymetricMemberKey key = NonSymetricMemberKey.of(memberFromIfc);
			if (!membersOwnedAndFromSuperClassAsSet.contains(key)) {
				// note: in case of duplicates in list 'membersFromInterfaces' we want to use the *last* member,
				// because it might be more specialized than the earlier ones; therefore we here overwrite earlier
				// members with later ones if they have the same 'key':
				membersToAmend.put(key, memberFromIfc);
			}
		}

		if (membersToAmend.isEmpty()) {
			return;
		}

		// for adjusting the types of members coming from interfaces, we prepare a rule environment
		// with all implicit type variable bindings of tClass
		RuleEnvironment G_tClass = wrap(getState().G);
		for (ParameterizedTypeRef tRef : tClass.getSuperClassifierRefs()) {
			tsh.addSubstitutions(G_tClass, tRef);
		}

		for (TMember memberToAmend : membersToAmend.values()) {
			N4MemberDeclaration memberNew = addMember(classDecl, memberToAmend, G_tClass);

			memberNew.getDeclaredModifiers().add(N4Modifier.PUBLIC);
			boolean hasDefaultImpl = false;
			if (memberToAmend instanceof TField) {
				// small inconsistency in N4JS: fields in interfaces need not be re-declared in implementing classes
				// even if they do not have an initializer expression; so in a sense they are all "default fields":
				hasDefaultImpl = true;
				// this would be more consistent:
				// memberToAmend.hasExpression
			}
			if (memberToAmend instanceof FieldAccessor) {
				hasDefaultImpl = !((FieldAccessor) memberToAmend).isHasNoBody();
			}
			if (memberToAmend instanceof TMethod) {
				hasDefaultImpl = !((TMethod) memberToAmend).isHasNoBody();
			}
			if (!hasDefaultImpl) {
				memberNew.getDeclaredModifiers().add(N4Modifier.ABSTRACT);
			}
		}
	}

	private N4MemberDeclaration addMember(N4ClassDeclaration classDecl, TMember member, RuleEnvironment G_tClass) {
		if (member instanceof TField) {
			return addMember(classDecl, (TField) member, G_tClass);
		} else if (member instanceof TGetter) {
			return addMember(classDecl, (TGetter) member, G_tClass);
		} else if (member instanceof TSetter) {
			return addMember(classDecl, (TSetter) member, G_tClass);
		} else if (member instanceof TMethod) {
			return addMember(classDecl, (TMethod) member, G_tClass);
		}
		throw new IllegalArgumentException("Unhandled parameter types: " +
				Arrays.<Object> asList(classDecl, member, G_tClass).toString());
	}

	private N4MemberDeclaration addMember(N4ClassDeclaration classDecl, TField fieldToAdd, RuleEnvironment G_tClass) {
		N4FieldDeclaration fieldNew = _N4FieldDecl(false, fieldToAdd.getName(), null);
		classDecl.getOwnedMembersRaw().add(fieldNew);

		setDeclaredTypeRef(fieldNew, fieldToAdd, G_tClass);

		return fieldNew;
	}

	private N4MemberDeclaration addMember(N4ClassDeclaration classDecl, TGetter getterToAdd, RuleEnvironment G_tClass) {
		N4GetterDeclaration getterNew = _N4GetterDecl(_LiteralOrComputedPropertyName(getterToAdd.getName()), null);
		classDecl.getOwnedMembersRaw().add(getterNew);

		setDeclaredTypeRef(getterNew, getterToAdd.getTypeRef(), G_tClass);

		return getterNew;
	}

	private N4MemberDeclaration addMember(N4ClassDeclaration classDecl, TSetter setterToAdd, RuleEnvironment G_tClass) {
		FormalParameter fpar = _Fpar("value");
		N4SetterDeclaration setterNew = _N4SetterDecl(_LiteralOrComputedPropertyName(setterToAdd.getName()), fpar,
				null);
		classDecl.getOwnedMembersRaw().add(setterNew);

		setDeclaredTypeRef(fpar, setterToAdd.getTypeRef(), G_tClass);

		return setterNew;
	}

	private N4MemberDeclaration addMember(N4ClassDeclaration classDecl, TMethod methodToAdd, RuleEnvironment G_tClass) {
		N4MethodDeclaration methodNew = _N4MethodDecl(methodToAdd.getName());
		classDecl.getOwnedMembersRaw().add(methodNew);

		for (TypeVariable typeParam : methodToAdd.getTypeVars()) {
			addTypeParameter(methodNew, typeParam, G_tClass);
		}

		for (TFormalParameter fpar : methodToAdd.getFpars()) {
			addFormalParameter(methodNew, fpar, G_tClass);
		}

		setReturnTypeRef(methodNew, methodToAdd.getReturnTypeRef(), G_tClass);

		return methodNew;
	}

	private void addTypeParameter(N4MethodDeclaration methodDecl, TypeVariable typeParamToAdd,
			RuleEnvironment G_tClass) {
		N4TypeVariable typeParamNew = _N4TypeVariable(typeParamToAdd.getName(), typeParamToAdd.isDeclaredCovariant(),
				typeParamToAdd.isDeclaredContravariant());
		methodDecl.getTypeVars().add(typeParamNew);

		setDeclaredUpperBound(typeParamNew, typeParamToAdd.getDeclaredUpperBound(), G_tClass);
	}

	private void addFormalParameter(N4MethodDeclaration methodDecl, TFormalParameter fparToAdd,
			RuleEnvironment G_tClass) {
		FormalParameter fparNew = _Fpar(fparToAdd.getName(), fparToAdd.isVariadic());
		fparNew.setHasInitializerAssignment(fparToAdd.isOptional());
		methodDecl.getFpars().add(fparNew);

		setDeclaredTypeRef(fparNew, fparToAdd, G_tClass);
	}

	private void setDeclaredTypeRef(TypedElement elementInIM, TTypedElement elementInTModule,
			RuleEnvironment G_tClass) {
		TypeRef typeRef = elementInTModule.getTypeRef();
		setDeclaredTypeRef(elementInIM, typeRef, G_tClass);
	}

	private void setDeclaredTypeRef(TypedElement elementInIM, TypeRef typeRef, RuleEnvironment G_tClass) {
		if (typeRef != null) {
			elementInIM.setDeclaredTypeRefNode(createLocalizedTypeRefNodeFor(typeRef, G_tClass));
		}
	}

	private void setReturnTypeRef(FunctionDefinition funDef, TypeRef typeRef, RuleEnvironment G_tClass) {
		if (typeRef != null) {
			funDef.setDeclaredReturnTypeRefNode(createLocalizedTypeRefNodeFor(typeRef, G_tClass));
		}
	}

	private void setDeclaredUpperBound(N4TypeVariable typeParam, TypeRef typeRef, RuleEnvironment G_tClass) {
		if (typeRef != null) {
			typeParam.setDeclaredUpperBoundNode(createLocalizedTypeRefNodeFor(typeRef, G_tClass));
		}
	}

	/**
	 * Converts the given type reference to the context defined by rule environment 'ruleEnvForSubstituion' (which will
	 * usually contain type variable bindings representing the context inside the class declaration passed to method
	 * {@link #addMissingImplementedMembers(N4ClassDeclaration)}) and creates a new {@link TypeReferenceNode_IM} for it.
	 */
	private TypeReferenceNode_IM<TypeRef> createLocalizedTypeRefNodeFor(TypeRef typeRef,
			RuleEnvironment ruleEnvForSubstituion) {
		TypeRef typeRefSubst = ts.substTypeVariables(ruleEnvForSubstituion, typeRef);
		TypeRef typeRefSubstCpy = (typeRefSubst == typeRef) ? TypeUtils.copy(typeRefSubst) : typeRefSubst;
		TypeReferenceNode_IM<TypeRef> typeRefNode = _TypeReferenceNode(getState(), typeRefSubstCpy);
		return typeRefNode;
	}
}
