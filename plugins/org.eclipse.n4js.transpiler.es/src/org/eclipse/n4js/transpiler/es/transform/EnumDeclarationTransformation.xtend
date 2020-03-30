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
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.N4JSLanguageConstants
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.N4EnumLiteral
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4JSFactory
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.TransformationDependency.RequiresAfter
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.assistants.ReflectionAssistant
import org.eclipse.n4js.transpiler.im.ImFactory
import org.eclipse.n4js.transpiler.im.SymbolTableEntry

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.transpiler.utils.TranspilerUtils.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Transforms {@link N4EnumDeclaration}s into a corresponding class declaration (for ordinary enums) or
 * removes them entirely (for <code>@StringBased</code> enums).
 */
@RequiresAfter(ClassDeclarationTransformation)
class EnumDeclarationTransformation extends Transformation {

	@Inject private ReflectionAssistant reflectionAssistant;
	@Inject private TypeAssistant typeAssistant;

	private SymbolTableEntry n4EnumSTE;


	override assertPreConditions() {
		assertFalse("only top-level enums are supported, for now",
			state.im.eAllContents.filter(N4EnumDeclaration).exists[!typeAssistant.isTopLevel(it)]);
	}

	override assertPostConditions() {
		 assertFalse("there should not be any N4EnumDeclarations in the intermediate model",
			 state.im.eAllContents.exists[it instanceof N4EnumDeclaration]);
	}

	override analyze() {
		n4EnumSTE = getSymbolTableEntryOriginal(state.G.n4EnumType, true);
		if (n4EnumSTE === null) {
			throw new IllegalStateException("could not find required members of built-in types");
		}
	}

	override transform() {
		collectNodes(state.im, N4EnumDeclaration, false).forEach[transformEnumDecl];
	}

	def private void transformEnumDecl(N4EnumDeclaration enumDecl) {
		if(enumDecl.isStringBased) {
			// declarations of string-based enums are simply removed
			// (they do not have a representation in the output code)
			val root = enumDecl.orContainingExportDeclaration;
			remove(root);
			return;
		}

		val classDecl = N4JSFactory.eINSTANCE.createN4ClassDeclaration;
		classDecl.name = enumDecl.name; // need to set name before creating a symbol table entry
		val classSTE = createSymbolTableEntryIMOnly(classDecl);

		val fieldsForLiterals = enumDecl.literals.map[convertLiteralToField(it, classSTE)];

		classDecl.declaredModifiers += enumDecl.declaredModifiers;  // reuse existing modifiers
		classDecl.superClassRef = ImFactory.eINSTANCE.createParameterizedTypeRef_IM => [declaredType_IM = n4EnumSTE];

		classDecl.ownedMembersRaw += createEnumConstructor();
		classDecl.ownedMembersRaw += fieldsForLiterals;
		classDecl.ownedMembersRaw += _N4FieldDecl(
			true,
			"literals",
			_ArrLit(fieldsForLiterals.map[
				_PropertyAccessExpr(classSTE, findSymbolTableEntryForElement(it, true))
			])
		);

		// add 'n4type' getter for reflection
		reflectionAssistant.addN4TypeGetter(enumDecl, classDecl);

		state.tracer.copyTrace(enumDecl, classDecl);

		replace(enumDecl, classDecl);
 	}

	def private N4MethodDeclaration createEnumConstructor() {
		// constructor(name, value) {
		//     super(name, value);
		// }
		val nameFpar = _Fpar("name");
		val valueFpar = _Fpar("value");
		val nameSTE = createSymbolTableEntryIMOnly(nameFpar);
		val valueSTE = createSymbolTableEntryIMOnly(valueFpar);
		return _N4MethodDecl(
			N4JSLanguageConstants.CONSTRUCTOR,
			#[ nameFpar, valueFpar ],
			#[
				_ExprStmnt(
					_CallExpr(_SuperLiteral, _IdentRef(nameSTE), _IdentRef(valueSTE))
				)
			]
		);
	}

	def private N4FieldDeclaration convertLiteralToField(N4EnumLiteral literal, SymbolTableEntry classSTE) {
		return _N4FieldDecl(
			true,
			literal.name,
			_NewExpr(
				_IdentRef(classSTE),
				_StringLiteral(literal.name),
				if (literal.value !== null) _StringLiteral(literal.value)
			)
		);
	}

	def private boolean isStringBased(N4EnumDeclaration enumDecl) {
		return AnnotationDefinition.STRING_BASED.hasAnnotation(enumDecl);
	}
}
