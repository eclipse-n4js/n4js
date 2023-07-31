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
package org.eclipse.n4js.transpiler.es.transform

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.assistants.DelegationAssistant
import org.eclipse.n4js.transpiler.im.DelegatingMember
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.utils.Log

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Handles some special cases where code has to be emitted for non-owned members, e.g. for members consumed by an
 * interface, <b>EXCEPT</b> for {@link StaticPolyfillTransformation static polyfills} and
 * {@link ApiImplStubGenerationTransformation API/Impl stubs} (those cases are handled in other transformations).
 * <p>
 * This transformation will add new "full" members or {@link DelegatingMember}s to the <code>ownedMembersRaw</code>
 * property of {@link ContainerType}s in the intermediate model.
 */
@Log
class MemberPatchingTransformation extends Transformation {
	@Inject private DelegationAssistant delegationAssistant;
	@Inject private TypeAssistant typeAssistant;


	override assertPreConditions() {
		typeAssistant.assertClassifierPreConditions();
	}
	override assertPostConditions() {
		// true
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, N4ClassifierDeclaration, false).forEach[transformClassifierDecl];
	}

	def private dispatch void transformClassifierDecl(N4InterfaceDeclaration ifcDecl) {
		val tIfc = state.info.getOriginalDefinedType(ifcDecl);
		val cmoft = typeAssistant.getOrCreateCMOFT(tIfc);

		// for interfaces we ALWAYS add delegates to ALL inherited members (except fields)
		for(TMember m : cmoft.ownedAndMixedInConcreteMembers) {
			if(!(m instanceof TField)) {
				val isInherited = m.containingType!==tIfc;
				if(isInherited){
					val delegator = delegationAssistant.createDelegatingMember(tIfc, m);
					state.info.markAsConsumedFromInterface(delegator);
					ifcDecl.ownedMembersRaw += delegator;
				}
			} else {
				// note: it is slightly inconsistent that we ignore fields here (also compare with method for classes);
				// but not required, because this is handled by the field initializer function created here:
				// InterfaceDeclarationTransformation#createInstanceFieldInitializationFunction(N4InterfaceDeclaration, SymbolTableEntry)
			}
		}
			
		for(field : cmoft.fieldsPurelyMixedInNotOverridingAccessor) {
			val member = _N4MemberDecl(field);
			ifcDecl.ownedMembersRaw += member;
			state.info.setOriginalDefinedMember(member, field);
			if (!cmoft.inlinedMembersFromShapes.contains(field)) {
				state.info.markAsConsumedFromInterface(member);
			}
		}
	}

	def private dispatch void transformClassifierDecl(N4ClassDeclaration classDecl) {
		val tClass = state.info.getOriginalDefinedType(classDecl);
		val cmoft = typeAssistant.getOrCreateCMOFT(tClass);

		// add delegates to methods consumed from a nominal interface
		val consumedMethods = cmoft.ownedAndMixedInConcreteMembers.filter(TMethod).filter[m|m.eContainer!==tClass].toList;
		for(m : consumedMethods) {
			val member = delegationAssistant.createDelegatingMember(tClass, m);
			if (!isInStructuralInterface(m)) {
				state.info.markAsConsumedFromInterface(member);
			}
			classDecl.ownedMembersRaw += member;
		}

		// add delegates to getters/setters consumed from a nominal interface
		for(accTuple : cmoft.concreteAccessorTuples) {
			if(accTuple.getter!==null && accTuple.getter.containingType!==tClass && accTuple.inheritedGetter===null) {
				val g = accTuple.getter;
				val member = delegationAssistant.createDelegatingMember(tClass, g);
				if (!isInStructuralInterface(accTuple.getter)) {
					state.info.markAsConsumedFromInterface(member);
				}
				classDecl.ownedMembersRaw += member;
			}
			if(accTuple.setter!==null && accTuple.setter.containingType!==tClass && accTuple.inheritedSetter===null) {
				val s = accTuple.setter;
				val member = delegationAssistant.createDelegatingMember(tClass, s);
				if (!isInStructuralInterface(accTuple.setter)) {
					state.info.markAsConsumedFromInterface(member);
				}
				classDecl.ownedMembersRaw += member;
			}
		}

		// add fields consumed from a nominal interface
		for(field : cmoft.fieldsPurelyMixedInNotOverridingAccessor) {
			val member = _N4MemberDecl(field);
			classDecl.ownedMembersRaw += member;
			state.info.setOriginalDefinedMember(member, field);
			if (!cmoft.inlinedMembersFromShapes.contains(field)) {
				state.info.markAsConsumedFromInterface(member);
			}
		}


		// add delegates to inherited fields/getters/setters shadowed by an owned setter XOR getter
		// NOTE: Partial shadowing in general is disallowed by validation. However, in incomplete
		// API-impl situation we still support this feature here to propagate generated stubs for
		// test reporting-purposes.
		// MOVED: the actual implementation moved to the {@link org.eclipse.n4js.transpiler.es.transform.ApiImplStubGenerationTransformation} class
		// the following code will issue errors if such a 'forbidden' case is still encountered:
		for(accTuple : cmoft.concreteAccessorTuples) {
			if(accTuple.inheritedGetter!==null && accTuple.getter===null && accTuple.setter!==null) {
				// an owned setter is shadowing an inherited getter -> delegate to the inherited getter
				logger.error("Encountered an invalid getter shadowing. Setter "+accTuple.setter.name+
					" of classifier "+accTuple.setter.containingType+"", new IllegalStateException("Invalid shadowing of inherited getter. Getter should be implemented explicitly."))
			}
			if(accTuple.inheritedSetter!==null && accTuple.getter!==null && accTuple.setter===null) {
				// an owned getter is shadowing an inherited setter -> delegate to the inherited setter
				logger.error("Encountered an invalid inherited setter shadowing. Getter "+accTuple.getter.name+
					" of classifier "+accTuple.getter.containingType+"", new IllegalStateException("Invalid shadowing of inherited setter. Setter should be implemented explicitly."))
			}
		}
	}
	
	// Note: Structural interfaces do not appear in the output code. Hence they must be consumed by the classes/interfaces that implement them.
	def private boolean isInStructuralInterface(TMember member) {
		return member.eContainer instanceof TInterface
				&& (member.eContainer as TInterface).typingStrategy === TypingStrategy.STRUCTURAL
	}
}
