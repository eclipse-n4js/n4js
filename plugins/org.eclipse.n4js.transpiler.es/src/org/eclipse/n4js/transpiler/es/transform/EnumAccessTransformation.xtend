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

import java.util.Map
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ImportDeclaration
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TEnumLiteral
import org.eclipse.n4js.ts.types.TMember

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Transforms references to literals of <code>@StringBased</code> enums by replacing them with a plain string literal.
 * References to ordinary enums (i.e. not string-based) are not touched by this transformation.
 */
class EnumAccessTransformation extends Transformation {

	private final Map<TEnum, VariableDeclaration> literalsConstants = newHashMap; // need not be linked
	private TMember member_StringBasedEnum_literals;


	override assertPreConditions() {
		member_StringBasedEnum_literals = state.G.n4StringBasedEnumType.findOwnedMember("literals", false, true);
		assertNotNull("member of built-in type not found: StringBasedEnum#literals", member_StringBasedEnum_literals);
	}

	override assertPostConditions() {
	}

	override analyze() {
	}

	override transform() {
		collectNodes(state.im, ParameterizedPropertyAccessExpression_IM, true).forEach[transformEnumAccess];
	}

	def private void transformEnumAccess(ParameterizedPropertyAccessExpression_IM expr) {
		val propSTE = expr.property_IM;
		val prop = if (propSTE instanceof SymbolTableEntryOriginal) propSTE.originalTarget;
		switch prop {
			TEnumLiteral 									: resolveOriginalStringBasedEnum(expr).transformEnumLiteralAccess(expr, prop)
			case prop === member_StringBasedEnum_literals 	: resolveOriginalStringBasedEnum(expr).transformEnumLiteralsConstantAccess(expr)
		}
	}

	/**
	 * Assumes {@code originalEnum} was resolved to string based enum, e,g, <code>@StringBased enum Color {RED : "red", SOME}</code>,
	 * transforms access to <code>Color.RED</code> to <code>"red"</code> and <code>Color.SOME</code> to <code>"SOME"</code>.It is low level transformation, assumes
	 * caller did all necessary checks and did provide proper data. Replacement is applied only if none of the provided parameters is {@code null}.
	 *
	 * @param originalEnum string based enum from AST for which literal access is generated.
	 * @param expr expression which will have value generated.
	 * @param prop enum literal from which value is generated.
	 */
	private def transformEnumLiteralAccess(TEnum originalEnum, ParameterizedPropertyAccessExpression_IM expr,
		TEnumLiteral prop) {
		if (originalEnum !== null && expr !== null && prop !== null) {
			replace(expr, enumLiteralToStringLiteral(prop));
		}
	}

	/**
	 * Assumes {@code originalEnum} was resolved to string based enum, e,g, <code>@StringBased enum Color {RED : "red", SOME}</code>,
	 * transforms access to <code>Color.literals</code> to <code>["red", "SOME"</code>. It is low level transformation, assumes
	 * caller did all necessary checks and did provide proper data. Replacement is applied only if none of the provided parameters is {@code null}.
	 *
	 * @param originalEnum string based enum from AST for which literals access is generated.
	 * @param expr expression which will have values generated.
	 */
	private def transformEnumLiteralsConstantAccess(TEnum originalEnum, ParameterizedPropertyAccessExpression_IM expr) {
		if (originalEnum !== null && expr !== null) {
			replace(expr, getReferenceToLiteralsConstant(originalEnum));
		}
	}

	/**
	 * Resolves left hand side of the {@code ParameterizedPropertyAccessExpression_IM} to the original {@link AnnotationDefinition#STRING_BASED} enum.
	 *
	 * @return resolved original string based enum or null.
	 */
	def private TEnum resolveOriginalStringBasedEnum(ParameterizedPropertyAccessExpression_IM pex) {
		val targetEnumSTE = resolveOriginalExpressionTarget(pex.target)
		if (targetEnumSTE instanceof SymbolTableEntryOriginal) {
			val orginal = targetEnumSTE.originalTarget;
			if (orginal instanceof TEnum) {
				if (orginal.isStringBased) {
					return orginal;
				}
			}
		}
		return null;
	}

	/**
	 * Resolve target of the {@code ParameterizedPropertyAccessExpression_IM} when left hand side is an simple access or nested expression, e.g. for
	 * <ul>
	 *   <li> <code> Enum.EnumLiteral </code> </li>
	 *   <li> <code> Enum.literals </code> </li>
	 *   <li> <code> (((((Enum)))).EnumLiteral </code> </li>
	 *   <li> <code> (((((Enum)))).literals </code> </li>
	 * </ul>
	 *  resolves to {@code SymbolTableEntry} of the Enum.
	 */
	def private SymbolTableEntry resolveOriginalExpressionTarget(Expression ex){
		switch ex {
			IdentifierRef_IM 							: ex.rewiredTarget
			ParameterizedPropertyAccessExpression_IM 	: ex.property_IM
			ParenExpression								: resolveOriginalExpressionTarget(ex.expression)
			default 									: null
		}
	}

	def private getReferenceToLiteralsConstant(TEnum tEnum) {
		var vdecl = literalsConstants.get(tEnum);
		if (vdecl === null) {
			vdecl = createLiteralsConstant(tEnum);
			literalsConstants.put(tEnum, vdecl);
		}
		// note: always have to create a new reference
		return _IdentRef(findSymbolTableEntryForElement(vdecl, false));
	}

	def private VariableDeclaration createLiteralsConstant(TEnum tEnum) {
		val name = findUniqueNameForLiteralsConstant(tEnum);
		val vdecl = _VariableDeclaration(name, _ArrLit(tEnum.literals.map[enumLiteralToStringLiteral]));
		state.info.markAsToHoist(vdecl);
		val vstmnt = _VariableStatement(vdecl);
		val lastImport = state.im.scriptElements.filter(ImportDeclaration).last;
		if (lastImport !== null) {
			insertAfter(lastImport, vstmnt);
		} else if (!state.im.scriptElements.empty) {
			insertBefore(state.im.scriptElements.head, vstmnt);
		} else {
			state.im.scriptElements.add(vstmnt);
		}
		createSymbolTableEntryIMOnly(vdecl);
		return vdecl;
	}

	def private String findUniqueNameForLiteralsConstant(TEnum tEnum) {
		val names = literalsConstants.values.map[name].toSet;
		var newName = "$enumLiteralsOf" + (tEnum?.name ?: "Unnamed");
		if (names.contains(newName)) {
			var cnt = 1;
			while (names.contains(newName + cnt)) {
				cnt++;
			}
			newName = newName + cnt;
		}
		return newName;
	}

	def private boolean isStringBased(TEnum tEnum) {
		return AnnotationDefinition.STRING_BASED.hasAnnotation(tEnum);
	}

	def private StringLiteral enumLiteralToStringLiteral(TEnumLiteral enumLiteral) {
		return _StringLiteral(enumLiteral?.value ?: enumLiteral.name);
	}
}
