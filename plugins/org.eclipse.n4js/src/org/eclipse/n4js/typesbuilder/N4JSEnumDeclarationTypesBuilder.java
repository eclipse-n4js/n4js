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
package org.eclipse.n4js.typesbuilder;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.fold;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.math.BigDecimal;
import java.util.Set;

import org.eclipse.n4js.n4JS.N4EnumDeclaration;
import org.eclipse.n4js.n4JS.N4EnumLiteral;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind;

import com.google.inject.Inject;

@SuppressWarnings("javadoc")
public class N4JSEnumDeclarationTypesBuilder {

	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;

	boolean relinkTEnum(N4EnumDeclaration n4Enum, AbstractNamespace target,
			@SuppressWarnings("unused") boolean preLinkingPhase, int idx) {

		if (n4Enum.getName() == null) {
			return false;
		}

		TEnum enumType = (TEnum) target.getTypes().get(idx);
		_n4JSTypesBuilderHelper.ensureEqualName(n4Enum, enumType);

		relinkTEnumLiterals(n4Enum, enumType);

		enumType.setAstElement(n4Enum);
		n4Enum.setDefinedType(enumType);
		return true;
	}

	private int relinkTEnumLiterals(N4EnumDeclaration n4Enum, TEnum tEnum) {
		return fold(n4Enum.getLiterals(), 0, (idx, n4EnumLit) -> {
			if (relinkTEnumLiteral(n4EnumLit, tEnum, idx)) {
				return idx + 1;
			}
			return idx;
		});
	}

	private boolean relinkTEnumLiteral(N4EnumLiteral n4EnumLit, TEnum tEnum, int idx) {
		TEnumLiteral tEnumLit = tEnum.getLiterals().get(idx);
		_n4JSTypesBuilderHelper.ensureEqualName(n4EnumLit, tEnumLit);
		tEnumLit.setAstElement(n4EnumLit);
		n4EnumLit.setDefinedLiteral(tEnumLit);
		return true;
	}

	protected TEnum createTEnum(N4EnumDeclaration n4Enum, AbstractNamespace target, boolean preLinkingPhase) {
		if (n4Enum.getName() == null) {
			return null;
		}

		TEnum enumType = createTEnum(n4Enum);
		_n4JSTypesBuilderHelper.setTypeAccessModifier(enumType, n4Enum);
		_n4JSTypesBuilderHelper.setProvidedByRuntime(enumType, n4Enum, preLinkingPhase);
		addLiterals(enumType, n4Enum);
		_n4JSTypesBuilderHelper.copyAnnotations(enumType, n4Enum, preLinkingPhase);

		computeDefaultValues(enumType, n4Enum);

		enumType.setAstElement(n4Enum);
		n4Enum.setDefinedType(enumType);

		target.getTypes().add(enumType);

		return enumType;
	}

	private TEnum createTEnum(N4EnumDeclaration n4Enum) {
		TEnum enumType = TypesFactory.eINSTANCE.createTEnum();
		enumType.setName(n4Enum.getName());
		enumType.setExternal(n4Enum.isExternal());
		return enumType;
	}

	private void addLiterals(TEnum enumType, N4EnumDeclaration n4Enum) {
		enumType.getLiterals().addAll(toList(
				map(filter(n4Enum.getLiterals(), N4EnumLiteral.class), el -> createEnumLiteral(el))));
	}

	private TEnumLiteral createEnumLiteral(N4EnumLiteral n4Literal) {
		Object value = N4JSLanguageUtils.getEnumLiteralValue(n4Literal);

		TEnumLiteral tLiteral = TypesFactory.eINSTANCE.createTEnumLiteral();
		tLiteral.setName(n4Literal.getName());
		tLiteral.setValueString((value instanceof String) ? (String) value : null);
		tLiteral.setValueNumber((value instanceof BigDecimal) ? (BigDecimal) value : null);
		tLiteral.setAstElement(n4Literal);
		n4Literal.setDefinedLiteral(tLiteral);
		return tLiteral;
	}

	private void computeDefaultValues(TEnum enumType, N4EnumDeclaration n4Enum) {
		EnumKind enumKind = N4JSLanguageUtils.getEnumKind(n4Enum);
		if (enumKind == EnumKind.NumberBased) {
			// @NumberBased enums
			Set<BigDecimal> usedNumbers = toSet(filterNull(map(enumType.getLiterals(), l -> l.getValueNumber())));
			BigDecimal next = BigDecimal.ONE.negate();
			for (TEnumLiteral tLiteral : enumType.getLiterals()) {
				if (tLiteral.getValueNumber() != null) {
					next = tLiteral.getValueNumber();
				} else {
					do {
						next = next.add(BigDecimal.ONE);
					} while (usedNumbers.contains(next));
					tLiteral.setValueNumber(next);
					usedNumbers.add(next);
				}
			}
		} else {
			// ordinary and @StringBased enums
			for (TEnumLiteral lit : enumType.getLiterals()) {
				if (lit.getValueString() == null) {
					lit.setValueString(lit.getName());
				}
			}
		}
	}
}
