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
package org.eclipse.n4js.transpiler.es.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._N4MemberDecl;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.assistants.TypeAssistant;
import org.eclipse.n4js.transpiler.es.assistants.DelegationAssistant;
import org.eclipse.n4js.transpiler.im.DelegatingMember;
import org.eclipse.n4js.transpiler.utils.ConcreteMembersOrderedForTranspiler;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.ts.types.util.AccessorTuple;

import com.google.inject.Inject;

/**
 * Handles some special cases where code has to be emitted for non-owned members, e.g. for members consumed by an
 * interface, <b>EXCEPT</b> for {@link StaticPolyfillTransformation static polyfills} and
 * {@link ApiImplStubGenerationTransformation API/Impl stubs} (those cases are handled in other transformations).
 * <p>
 * This transformation will add new "full" members or {@link DelegatingMember}s to the <code>ownedMembersRaw</code>
 * property of {@link ContainerType}s in the intermediate model.
 */
public class MemberPatchingTransformation extends Transformation {
	private final static Logger LOGGER = Logger.getLogger(MemberPatchingTransformation.class);

	@Inject
	private DelegationAssistant delegationAssistant;
	@Inject
	private TypeAssistant typeAssistant;

	@Override
	public void assertPreConditions() {
		typeAssistant.assertClassifierPreConditions();
	}

	@Override
	public void assertPostConditions() {
		// true
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		for (N4ClassifierDeclaration cd : collectNodes(getState().im, N4ClassifierDeclaration.class, false)) {
			if (cd instanceof N4InterfaceDeclaration) {
				transformClassifierDecl((N4InterfaceDeclaration) cd);

			} else if (cd instanceof N4ClassDeclaration) {
				transformClassifierDecl((N4ClassDeclaration) cd);
			}
		}
	}

	private void transformClassifierDecl(N4InterfaceDeclaration ifcDecl) {
		TInterface tIfc = getState().info.getOriginalDefinedType(ifcDecl);
		ConcreteMembersOrderedForTranspiler cmoft = typeAssistant.getOrCreateCMOFT(tIfc);

		// for interfaces we ALWAYS add delegates to ALL inherited members (except fields)
		for (TMember m : cmoft.ownedAndMixedInConcreteMembers) {
			if (!(m instanceof TField)) {
				boolean isInherited = m.getContainingType() != tIfc;
				if (isInherited) {
					DelegatingMember delegator = delegationAssistant.createDelegatingMember(tIfc, m);
					getState().info.markAsConsumedFromInterface(delegator);
					ifcDecl.getOwnedMembersRaw().add(delegator);
				}
			} else {
				// note: it is slightly inconsistent that we ignore fields here (also compare with method for classes);
				// but not required, because this is handled by the field initializer function created here:
				// InterfaceDeclarationTransformation#createInstanceFieldInitializationFunction(N4InterfaceDeclaration,
				// SymbolTableEntry)
			}
		}

		for (TField field : cmoft.fieldsPurelyMixedInNotOverridingAccessor) {
			if (cmoft.inlinedMembersFromShapes.contains(field)) {
				N4MemberDeclaration member = _N4MemberDecl(field);
				ifcDecl.getOwnedMembersRaw().add(member);
				getState().info.setOriginalDefinedMember(member, field);
			}
		}
	}

	private void transformClassifierDecl(N4ClassDeclaration classDecl) {
		TClass tClass = getState().info.getOriginalDefinedType(classDecl);
		ConcreteMembersOrderedForTranspiler cmoft = typeAssistant.getOrCreateCMOFT(tClass);

		// add delegates to methods consumed from a nominal interface
		List<TMethod> consumedMethods = toList(
				filter(filter(cmoft.ownedAndMixedInConcreteMembers, TMethod.class), m -> m.eContainer() != tClass));
		for (TMethod m : consumedMethods) {
			DelegatingMember member = delegationAssistant.createDelegatingMember(tClass, m);
			if (!isInStructuralInterface(m)) {
				getState().info.markAsConsumedFromInterface(member);
			}
			classDecl.getOwnedMembersRaw().add(member);
		}

		// add delegates to getters/setters consumed from a nominal interface
		for (AccessorTuple accTuple : cmoft.concreteAccessorTuples) {
			if (accTuple.getGetter() != null && accTuple.getGetter().getContainingType() != tClass
					&& accTuple.getInheritedGetter() == null) {
				TGetter g = accTuple.getGetter();
				DelegatingMember member = delegationAssistant.createDelegatingMember(tClass, g);
				if (!isInStructuralInterface(accTuple.getGetter())) {
					getState().info.markAsConsumedFromInterface(member);
				}
				classDecl.getOwnedMembersRaw().add(member);
			}
			if (accTuple.getSetter() != null && accTuple.getSetter().getContainingType() != tClass
					&& accTuple.getInheritedSetter() == null) {
				TSetter s = accTuple.getSetter();
				DelegatingMember member = delegationAssistant.createDelegatingMember(tClass, s);
				if (!isInStructuralInterface(accTuple.getSetter())) {
					getState().info.markAsConsumedFromInterface(member);
				}
				classDecl.getOwnedMembersRaw().add(member);
			}
		}

		// add fields consumed from a nominal interface
		for (TField field : cmoft.fieldsPurelyMixedInNotOverridingAccessor) {
			N4MemberDeclaration member = _N4MemberDecl(field);
			classDecl.getOwnedMembersRaw().add(member);
			getState().info.setOriginalDefinedMember(member, field);
			if (!cmoft.inlinedMembersFromShapes.contains(field)) {
				getState().info.markAsConsumedFromInterface(member);
			}
		}

		// add delegates to inherited fields/getters/setters shadowed by an owned setter XOR getter
		// NOTE: Partial shadowing in general is disallowed by validation. However, in incomplete
		// API-impl situation we still support this feature here to propagate generated stubs for
		// test reporting-purposes.
		// MOVED: the actual implementation moved to the {@link
		// org.eclipse.n4js.transpiler.es.transform.ApiImplStubGenerationTransformation} class
		// the following code will issue errors if such a 'forbidden' case is still encountered:
		for (AccessorTuple accTuple : cmoft.concreteAccessorTuples) {
			if (accTuple.getInheritedGetter() != null && accTuple.getGetter() == null && accTuple.getSetter() != null) {
				// an owned setter is shadowing an inherited getter -> delegate to the inherited getter
				LOGGER.error("Encountered an invalid getter shadowing. Setter " + accTuple.getSetter().getName() +
						" of classifier " + accTuple.getSetter().getContainingType() + "",
						new IllegalStateException(
								"Invalid shadowing of inherited getter. Getter should be implemented explicitly."));
			}
			if (accTuple.getInheritedSetter() != null && accTuple.getGetter() != null && accTuple.getSetter() == null) {
				// an owned getter is shadowing an inherited setter -> delegate to the inherited setter
				LOGGER.error(
						"Encountered an invalid inherited setter shadowing. Getter " + accTuple.getGetter().getName() +
								" of classifier " + accTuple.getGetter().getContainingType() + "",
						new IllegalStateException(
								"Invalid shadowing of inherited setter. Setter should be implemented explicitly."));
			}
		}
	}

	// Note: Structural interfaces do not appear in the output code. Hence they must be consumed by the
	// classes/interfaces that implement them.
	private boolean isInStructuralInterface(TMember member) {
		return member.eContainer() instanceof TInterface
				&& ((TInterface) member.eContainer()).getTypingStrategy() == TypingStrategy.STRUCTURAL;
	}
}
