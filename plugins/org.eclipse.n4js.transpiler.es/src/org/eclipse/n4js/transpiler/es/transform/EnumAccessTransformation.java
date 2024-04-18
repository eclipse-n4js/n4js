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

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._ArrLit;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._IdentRef;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableDeclaration;
import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._VariableStatement;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4NumberBasedEnumType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4StringBasedEnumType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.Literal;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntry;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils.EnumKind;

/**
 * Transforms references to literals of <code>@StringBased</code> enums by replacing them with a plain string literal.
 * References to ordinary enums (i.e. not string-based) are not touched by this transformation.
 */
public class EnumAccessTransformation extends Transformation {

	private final Map<TEnum, VariableDeclaration> literalsConstants = new HashMap<>(); // need not be linked
	private TMember member_NumberBasedEnum_literals;
	private TMember member_StringBasedEnum_literals;

	@Override
	public void assertPreConditions() {
		assertNotNull("member of built-in type not found: NumberBasedEnum#literals", member_NumberBasedEnum_literals);
		assertNotNull("member of built-in type not found: StringBasedEnum#literals", member_StringBasedEnum_literals);
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void analyze() {
		member_NumberBasedEnum_literals = n4NumberBasedEnumType(getState().G).findOwnedMember("literals", false, true);
		member_StringBasedEnum_literals = n4StringBasedEnumType(getState().G).findOwnedMember("literals", false, true);
	}

	@Override
	public void transform() {
		for (ParameterizedPropertyAccessExpression_IM ppae : collectNodes(getState().im,
				ParameterizedPropertyAccessExpression_IM.class, true)) {
			transformEnumAccess(ppae);
		}
	}

	private void transformEnumAccess(ParameterizedPropertyAccessExpression_IM expr) {
		SymbolTableEntry propSTE = expr.getProperty_IM();
		if (propSTE instanceof SymbolTableEntryOriginal) {
			IdentifiableElement prop = ((SymbolTableEntryOriginal) propSTE).getOriginalTarget();
			if (prop instanceof TEnumLiteral) {
				transformEnumLiteralAccess(resolveOriginalNumberOrStringBasedEnum(expr), expr, (TEnumLiteral) prop);
			}
			if (prop == member_StringBasedEnum_literals || prop == member_NumberBasedEnum_literals)
				transformEnumLiteralsConstantAccess(resolveOriginalNumberOrStringBasedEnum(expr), expr);
		}

	}

	/**
	 * Assumes {@code originalEnum} was resolved to string based enum, e,g,
	 * <code>@StringBased enum Color {RED : "red", SOME}</code>, transforms access to <code>Color.RED</code> to
	 * <code>"red"</code> and <code>Color.SOME</code> to <code>"SOME"</code>.It is low level transformation, assumes
	 * caller did all necessary checks and did provide proper data. Replacement is applied only if none of the provided
	 * parameters is {@code null}.
	 *
	 * @param originalEnum
	 *            string based enum from AST for which literal access is generated.
	 * @param expr
	 *            expression which will have value generated.
	 * @param prop
	 *            enum literal from which value is generated.
	 */
	private void transformEnumLiteralAccess(TEnum originalEnum, ParameterizedPropertyAccessExpression_IM expr,
			TEnumLiteral prop) {
		if (originalEnum != null && expr != null && prop != null) {
			replace(expr, TranspilerUtils.enumLiteralToNumericOrStringLiteral(prop));
		}
	}

	/**
	 * Assumes {@code originalEnum} was resolved to string based enum, e,g,
	 * <code>@StringBased enum Color {RED : "red", SOME}</code>, transforms access to <code>Color.literals</code> to
	 * <code>["red", "SOME"</code>. It is low level transformation, assumes caller did all necessary checks and did
	 * provide proper data. Replacement is applied only if none of the provided parameters is {@code null}.
	 *
	 * @param originalEnum
	 *            string based enum from AST for which literals access is generated.
	 * @param expr
	 *            expression which will have values generated.
	 */
	private void transformEnumLiteralsConstantAccess(TEnum originalEnum,
			ParameterizedPropertyAccessExpression_IM expr) {
		if (originalEnum != null && expr != null) {
			replace(expr, getReferenceToLiteralsConstant(originalEnum));
		}
	}

	/**
	 * Resolves left hand side of the {@code ParameterizedPropertyAccessExpression_IM} to the original
	 * {@link AnnotationDefinition#STRING_BASED} enum.
	 *
	 * @return resolved original string based enum or null.
	 */
	private TEnum resolveOriginalNumberOrStringBasedEnum(ParameterizedPropertyAccessExpression_IM pex) {
		SymbolTableEntry targetEnumSTE = resolveOriginalExpressionTarget(pex.getTarget());
		if (targetEnumSTE instanceof SymbolTableEntryOriginal) {
			IdentifiableElement original = ((SymbolTableEntryOriginal) targetEnumSTE).getOriginalTarget();
			if (original instanceof TEnum) {
				if (N4JSLanguageUtils.getEnumKind((TEnum) original) != EnumKind.Normal) {
					return (TEnum) original;
				}
			}
		}
		return null;
	}

	/**
	 * Resolve target of the {@code ParameterizedPropertyAccessExpression_IM} when left hand side is an simple access or
	 * nested expression, e.g. for
	 * <ul>
	 * <li><code> Enum.EnumLiteral </code></li>
	 * <li><code> Enum.literals </code></li>
	 * <li><code> (((((Enum)))).EnumLiteral </code></li>
	 * <li><code> (((((Enum)))).literals </code></li>
	 * </ul>
	 * resolves to {@code SymbolTableEntry} of the Enum.
	 */
	private SymbolTableEntry resolveOriginalExpressionTarget(Expression ex) {
		if (ex instanceof IdentifierRef_IM) {
			return ((IdentifierRef_IM) ex).getRewiredTarget();
		}
		if (ex instanceof ParameterizedPropertyAccessExpression_IM) {
			return ((ParameterizedPropertyAccessExpression_IM) ex).getProperty_IM();
		}
		if (ex instanceof ParenExpression) {
			return resolveOriginalExpressionTarget(((ParenExpression) ex).getExpression());
		}
		return null;
	}

	private IdentifierRef_IM getReferenceToLiteralsConstant(TEnum tEnum) {
		var vdecl = literalsConstants.get(tEnum);
		if (vdecl == null) {
			vdecl = createLiteralsConstant(tEnum);
			literalsConstants.put(tEnum, vdecl);
		}
		// note: always have to create a new reference
		return _IdentRef(findSymbolTableEntryForElement(vdecl, false));
	}

	private VariableDeclaration createLiteralsConstant(TEnum tEnum) {
		String name = findUniqueNameForLiteralsConstant(tEnum);

		List<Literal> literals = toList(map(
				tEnum.getLiterals(), l -> TranspilerUtils.enumLiteralToNumericOrStringLiteral(l)));
		VariableDeclaration vdecl = _VariableDeclaration(name, _ArrLit(literals.toArray(new Literal[0])));
		VariableStatement vstmnt = _VariableStatement(vdecl);
		ImportDeclaration lastImport = last(filter(getState().im.getScriptElements(), ImportDeclaration.class));
		if (lastImport != null) {
			insertAfter(lastImport, vstmnt);
		} else if (!getState().im.getScriptElements().isEmpty()) {
			insertBefore(getState().im.getScriptElements().get(0), vstmnt);
		} else {
			getState().im.getScriptElements().add(vstmnt);
		}
		createSymbolTableEntryIMOnly(vdecl);
		return vdecl;
	}

	private String findUniqueNameForLiteralsConstant(TEnum tEnum) {
		Set<String> names = toSet(map(literalsConstants.values(), lc -> lc.getName()));
		var newName = "$enumLiteralsOf" + (tEnum != null && tEnum.getName() != null ? tEnum.getName() : "Unnamed");
		if (names.contains(newName)) {
			int cnt = 1;
			while (names.contains(newName + cnt)) {
				cnt++;
			}
			newName = newName + cnt;
		}
		return newName;
	}
}
