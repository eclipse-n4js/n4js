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

import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.types.TypingStrategy

public class N4JSInterfaceDeclarationTypesBuilder extends N4JSClassifierDeclarationTypesBuilder {
	
	def package boolean relinkTInterface(N4InterfaceDeclaration n4Interface, TModule target, boolean preLinkingPhase, int idx) {
		if (n4Interface.name === null) { // may be null due to syntax errors
			return false;
		}

		val TInterface interfaceType = target.topLevelTypes.get(idx) as TInterface
		interfaceType.relinkClassifierAndMembers(n4Interface, preLinkingPhase);
		return true;
	}

	def protected TInterface createTInterface(N4InterfaceDeclaration n4Interface, TModule target, boolean preLinkingPhase) {
		if (n4Interface.name === null) {
			return null;
		}

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
		interfaceType.addCopyOfTypeParameters(n4Interface, preLinkingPhase)
		interfaceType.addExtendedInterfaces(n4Interface, preLinkingPhase)

		interfaceType.addFields(n4Interface, preLinkingPhase)
		interfaceType.addMethods(n4Interface, preLinkingPhase)

		interfaceType.addGetters(n4Interface, preLinkingPhase)
		interfaceType.addSetters(n4Interface, preLinkingPhase)

		interfaceType.copyAnnotations(n4Interface, preLinkingPhase)

		interfaceType.astElement = n4Interface

		n4Interface.definedType = interfaceType

		target.topLevelTypes += interfaceType
		return interfaceType;
	}

	def private TInterface createTInterface(N4InterfaceDeclaration n4Interface) {
		val interfaceType = TypesFactory::eINSTANCE.createTInterface();
		interfaceType.name = n4Interface.name;
		interfaceType.exportedName = n4Interface.exportedName;
		interfaceType.external = n4Interface.external;
		
		VersionedTypesBuilderUtil.setTypeVersion(interfaceType, n4Interface);
		
		interfaceType
	}

	def private void addExtendedInterfaces(TInterface interfaceType, N4InterfaceDeclaration c, boolean preLinkingPhase) {
		if (!preLinkingPhase)
			addCopyOfReferences(interfaceType.superInterfaceRefs, c.superInterfaceRefs)
	}
}
