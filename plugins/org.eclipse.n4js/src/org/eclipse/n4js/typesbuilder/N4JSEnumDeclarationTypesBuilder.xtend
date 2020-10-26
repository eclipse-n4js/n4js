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
import java.math.BigDecimal
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4EnumLiteral
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TEnumLiteral
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind

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

		computeDefaultValues(enumType, n4Enum);

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
		enumType.literals.addAll(n4Enum.literals.filter(N4EnumLiteral).map[createEnumLiteral(preLinkingPhase)]);
	}

	def private TEnumLiteral createEnumLiteral(N4EnumLiteral n4Literal, boolean preLinkingPhase) {
		val value = N4JSLanguageUtils.getEnumLiteralValue(n4Literal);

		val tLiteral = TypesFactory::eINSTANCE.createTEnumLiteral();
		tLiteral.name = n4Literal.name;
		tLiteral.valueString = if (value instanceof String) value;
		tLiteral.valueNumber = if (value instanceof BigDecimal) value;
		tLiteral.astElement = n4Literal;
		n4Literal.definedLiteral = tLiteral
		return tLiteral;
	}

	def private void computeDefaultValues(TEnum enumType, N4EnumDeclaration n4Enum) {
		val enumKind = N4JSLanguageUtils.getEnumKind(n4Enum);
		if (enumKind === EnumKind.NumberBased) {
			// @NumberBased enums
			val usedNumbers = enumType.literals.map[valueNumber].filterNull.toSet;
			var next = BigDecimal.ONE.negate();
			for (tLiteral : enumType.literals) {
				if (tLiteral.valueNumber !== null) {
					next = tLiteral.valueNumber;
				} else {
					do {
						next = next.add(BigDecimal.ONE);
					} while(usedNumbers.contains(next));
					tLiteral.valueNumber = next;
					usedNumbers += next;
				}
			}
		} else {
			// ordinary and @StringBased enums
			enumType.literals.filter[valueString === null].forEach[valueString = name];
		}
	}
}
