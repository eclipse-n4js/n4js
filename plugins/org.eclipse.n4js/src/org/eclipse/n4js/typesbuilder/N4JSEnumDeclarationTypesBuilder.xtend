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
import java.math.BigInteger
import java.util.Map
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4EnumLiteral
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TEnumLiteral
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.utils.UtilN4

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

		val computedValues = computeDefaultValues(n4Enum);

		val enumType = n4Enum.createTEnum
		enumType.setTypeAccessModifier(n4Enum)
		enumType.setProvidedByRuntime(n4Enum, preLinkingPhase)
		enumType.addLiterals(n4Enum, computedValues, preLinkingPhase)
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

	def private void addLiterals(TEnum enumType, N4EnumDeclaration n4Enum, Map<N4EnumLiteral, String> computedValues, boolean preLinkingPhase) {
		enumType.literals.addAll(n4Enum.literals.filter(N4EnumLiteral).map[createEnumLiteral(computedValues, preLinkingPhase)]);
	}

	def private TEnumLiteral createEnumLiteral(N4EnumLiteral n4Literal, Map<N4EnumLiteral, String> computedValues, boolean preLinkingPhase) {
		val literal = TypesFactory::eINSTANCE.createTEnumLiteral();
		literal.name = n4Literal.name;
		literal.value = n4Literal.value ?: computedValues.get(n4Literal); // if this evaluates to 'null', TEnumLiteral#getValueOrDefault() will use 'name' as value
		literal.astElement = n4Literal;
		n4Literal.definedLiteral = literal
		return literal;
	}

	def private Map<N4EnumLiteral, String> computeDefaultValues(N4EnumDeclaration n4Enum) {
		val isNumberBased = AnnotationDefinition.NUMBER_BASED.hasAnnotation(n4Enum);
		if (!isNumberBased) {
			return emptyMap(); // only @NumberBased enums have computed default values
		}
		val result = newHashMap;
		val usedNumbers = n4Enum.literals.map[UtilN4.parseBigInteger(value)].filterNull.toSet;
		var next = BigInteger.valueOf(-1);
		var idx = 0;
		for (n4Literal : n4Enum.literals) {
			if (n4Literal.value === null) {
				do {
					next = next.add(BigInteger.ONE);
				} while(usedNumbers.contains(next));
				result.put(n4Literal, next.toString);
				idx++;
			}
		}
		return result;
	}
}
