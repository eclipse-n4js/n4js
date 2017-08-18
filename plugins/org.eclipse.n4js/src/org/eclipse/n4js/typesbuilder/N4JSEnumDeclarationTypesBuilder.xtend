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
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4EnumLiteral
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory

package class N4JSEnumDeclarationTypesBuilder {
	@Inject extension N4JSTypesBuilderHelper

	def package boolean linkTEnum(N4EnumDeclaration n4Enum, TModule target, boolean preLinkingPhase, int idx) {
		if (n4Enum.name === null) {
			return false;
		}

		val TEnum enumType = target.topLevelTypes.get(idx) as TEnum
		enumType.astElement = n4Enum
		n4Enum.definedType = enumType
		return true;
	}

	def package void createTEnum(N4EnumDeclaration n4Enum, TModule target, boolean preLinkingPhase) {
		if (n4Enum.name === null) {
			return;
		}

		val enumType = n4Enum.createTEnum
		enumType.setTypeAccessModifier(n4Enum)
		enumType.setProvidedByRuntime(n4Enum, preLinkingPhase)
		enumType.addLiterals(n4Enum, preLinkingPhase)
		enumType.copyAnnotations(n4Enum, preLinkingPhase)

		enumType.astElement = n4Enum

		n4Enum.definedType = enumType
		target.topLevelTypes += enumType
	}

	def private createTEnum(N4EnumDeclaration n4Enum) {
		val enumType = TypesFactory::eINSTANCE.createTEnum();
		enumType.name = n4Enum.name;
		enumType.exportedName = n4Enum.exportedName;
		enumType.external = n4Enum.external;
		enumType
	}

	def private addLiterals(TEnum enumType, N4EnumDeclaration n4Enum, boolean preLinkingPhase) {
		enumType.literals.addAll(n4Enum.literals.filter(typeof(N4EnumLiteral)).map [createEnumLiteral(preLinkingPhase)]);
	}

	def private createEnumLiteral(N4EnumLiteral it, boolean preLinkingPhase) {
		val enumLiteral = TypesFactory::eINSTANCE.createTEnumLiteral();
		enumLiteral.name = it.name;
		enumLiteral.value = it.value;
		enumLiteral.astElement = it;
		it.definedLiteral = enumLiteral
		enumLiteral;
	}
}
