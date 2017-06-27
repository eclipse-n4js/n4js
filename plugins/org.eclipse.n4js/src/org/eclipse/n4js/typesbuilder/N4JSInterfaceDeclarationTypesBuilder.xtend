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
package org.eclipse.n4js.typesbuilder

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypeAccessModifier
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.types.TypingStrategy
import java.util.List

package class N4JSInterfaceDeclarationTypesBuilder {
	@Inject extension N4JSTypesBuilderHelper
	@Inject extension N4JSFieldTypesBuilder
	@Inject extension N4JSMethodTypesBuilder
	@Inject extension N4JSGetterTypesBuilder
	@Inject extension N4JSSetterTypesBuilder

	def package createTInterface(N4InterfaceDeclaration n4Interface, TModule target, boolean preLinkingPhase) {
		if (n4Interface.name === null)
			return null

		val interfaceType = createTInterface(n4Interface);
		interfaceType.setTypeAccessModifier(n4Interface)

		interfaceType.setTypingStrategy(
			if (n4Interface.typingStrategy === TypingStrategy.DEFAULT) {
				TypingStrategy.DEFAULT
			} else { // STRUCTURAL_FIELD is not allowed on def site, but maybe we got a wrong input
				TypingStrategy.STRUCTURAL
			})

		interfaceType.setProvidedByRuntime(n4Interface, preLinkingPhase)
		interfaceType.declaredCovariantConstructor = n4Interface.isDeclaredCovariantConstructor;
		interfaceType.addTypeParameters(n4Interface, preLinkingPhase)
		interfaceType.addExtendedInterfaces(n4Interface, preLinkingPhase)

		interfaceType.addFields(n4Interface, preLinkingPhase)
		interfaceType.addMethods(n4Interface, preLinkingPhase)

		interfaceType.addGetters(n4Interface, preLinkingPhase)
		interfaceType.addSetters(n4Interface, preLinkingPhase)

		interfaceType.copyAnnotations(n4Interface, preLinkingPhase)

		interfaceType.astElement = n4Interface

		n4Interface.definedType = interfaceType

		target.topLevelTypes += interfaceType
	}

	def private TInterface createTInterface(N4InterfaceDeclaration n4Interface) {
		val interfaceType = TypesFactory::eINSTANCE.createTInterface();
		interfaceType.name = n4Interface.name;
		interfaceType.exportedName = n4Interface.exportedName;
		interfaceType.external = n4Interface.external;
		interfaceType
	}

	def private setTypeAccessModifier(TInterface interfaceType, N4InterfaceDeclaration n4Interface) {
		setTypeAccessModifier(n4Interface, [TypeAccessModifier modifier |
			interfaceType.declaredTypeAccessModifier = modifier
		], n4Interface.declaredModifiers, getAllAnnotations(n4Interface))
	}

	def private addTypeParameters(TInterface interfaceType, N4InterfaceDeclaration interfaceDecl, boolean preLinkingPhase) {
		addCopyOfReferences([ params | interfaceType.typeVars += params], interfaceDecl.typeVars, preLinkingPhase)
	}

	def private addExtendedInterfaces(TInterface interfaceType, N4InterfaceDeclaration c, boolean preLinkingPhase) {
		addCopyOfReferences([List<ParameterizedTypeRef> interfaces | interfaceType.superInterfaceRefs += interfaces], c.superInterfaceRefs, preLinkingPhase)
	}

	def private addFields(TInterface interfaceType, N4InterfaceDeclaration n4Interface, boolean preLinkingPhase) {
		interfaceType.ownedMembers.addAll(n4Interface.ownedMembers.filter(typeof(N4FieldDeclaration)).map[createField(interfaceType, preLinkingPhase)].filterNull);
	}

	def private addMethods(TInterface interfaceType, N4InterfaceDeclaration n4Interface, boolean preLinkingPhase) {
		interfaceType.ownedMembers.addAll(n4Interface.ownedMembers.filter(typeof(N4MethodDeclaration)).map [createMethod(preLinkingPhase)].filterNull);
	}

	def private addGetters(TInterface interfaceType, N4InterfaceDeclaration n4Interface, boolean preLinkingPhase) {
		// create also getters for all non private fields without explicit getter
		val n4Getters = n4Interface.ownedMembers.filter(N4GetterDeclaration)
		val getters = n4Getters.map[createGetter(interfaceType, preLinkingPhase)].filterNull
		interfaceType.ownedMembers.addAll(getters);
	}

	def private addSetters(TInterface interfaceType, N4InterfaceDeclaration n4Interface, boolean preLinkingPhase) {
		// create also getters for all non private fields without explicit getter
		val n4Setters = n4Interface.ownedMembers.filter(N4SetterDeclaration)
		val setters = n4Setters.map[createSetter(interfaceType, preLinkingPhase)].filterNull
		interfaceType.ownedMembers.addAll(setters);
	}
}
