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
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4EnumLiteral
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TEnumLiteral
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory

public class N4JSEnumDeclarationTypesBuilder {

	@Inject extension N4JSTypesBuilderHelper

	def package boolean relinkTEnum(N4EnumDeclaration n4Enum, TModule target, boolean preLinkingPhase, int idx) {
		if (n4Enum.name === null) {
			return false;
		}

		val TEnum enumType = target.topLevelTypes.get(idx) as TEnum
		ensureEqualName(n4Enum, enumType);

		relinkTEnumLiterals(n4Enum, enumType, preLinkingPhase);

		enumType.astElement = n4Enum
		n4Enum.definedType = enumType
		return true;
	}

	def private int relinkTEnumLiterals(N4EnumDeclaration n4Enum, TEnum tEnum, boolean preLinkingPhase) {
		return n4Enum.literals.fold(0) [ idx, n4EnumLit |
			if (relinkTEnumLiteral(n4EnumLit, tEnum, preLinkingPhase, idx)) {
				return idx + 1;
			}
			return idx;
		]
	}

	def private boolean relinkTEnumLiteral(N4EnumLiteral n4EnumLit, TEnum tEnum, boolean preLinkingPhase, int idx) {
		val tEnumLit = tEnum.literals.get(idx);
		ensureEqualName(n4EnumLit, tEnumLit);
		tEnumLit.astElement = n4EnumLit;
		n4EnumLit.definedLiteral = tEnumLit;
		return true;
	}

	def protected TEnum createTEnum(N4EnumDeclaration n4Enum, TModule target, boolean preLinkingPhase) {
		if (n4Enum.name === null) {
			return null;
		}

		val enumType = n4Enum.createTEnum
		enumType.setTypeAccessModifier(n4Enum)
		enumType.setProvidedByRuntime(n4Enum, preLinkingPhase)
		enumType.addLiterals(n4Enum, preLinkingPhase)
		enumType.copyAnnotations(n4Enum, preLinkingPhase)

		enumType.astElement = n4Enum

		n4Enum.definedType = enumType
		target.topLevelTypes += enumType
		
		VersionedTypesBuilderUtil.setTypeVersion(enumType, n4Enum);
		
		return enumType;
	}

	def private TEnum createTEnum(N4EnumDeclaration n4Enum) {
		val enumType = TypesFactory::eINSTANCE.createTEnum();
		enumType.name = n4Enum.name;
		enumType.exportedName = n4Enum.exportedName;
		enumType.external = n4Enum.external;
		enumType
	}

	def private void addLiterals(TEnum enumType, N4EnumDeclaration n4Enum, boolean preLinkingPhase) {
		enumType.literals.addAll(n4Enum.literals.filter(N4EnumLiteral).map[createEnumLiteral(n4Enum, it, preLinkingPhase)]);
	}

	def private TEnumLiteral createEnumLiteral(N4EnumDeclaration n4Enum, N4EnumLiteral n4Literal, boolean preLinkingPhase) {
		val literal = TypesFactory::eINSTANCE.createTEnumLiteral();
		literal.name = n4Literal.name;
		literal.value = n4Literal.value ?: getDefaultValue(n4Enum, n4Literal);
		literal.astElement = n4Literal;
		n4Literal.definedLiteral = literal
		return literal;
	}

	def private String getDefaultValue(N4EnumDeclaration n4Enum, N4EnumLiteral n4Literal) {
		if (AnnotationDefinition.NUMBER_BASED.hasAnnotation(n4Enum)) {
			val idx = n4Enum.literals.indexOf(n4Literal);
			return Integer.toString(idx);
		}
		return null;
	}
}
