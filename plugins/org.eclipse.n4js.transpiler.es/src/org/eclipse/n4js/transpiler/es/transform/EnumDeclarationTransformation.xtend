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
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4EnumDeclaration
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.assistants.BootstrapCallAssistant

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.transpiler.utils.TranspilerUtils.*

/**
 * Transforms {@link N4EnumDeclaration}s into a <code>$makeEnum</code> call (for ordinary enums) or removes them
 * entirely (for <code>@StringBased</code> enums).
 */
class EnumDeclarationTransformation extends Transformation {

	@Inject BootstrapCallAssistant bootstrapCallAssistant;
	@Inject TypeAssistant typeAssistant;


	override assertPreConditions() {
		assertFalse("only top-level enums are supported, for now",
			state.im.eAllContents.filter(N4EnumDeclaration).exists[!typeAssistant.isTopLevel(it)]);
	}

	override assertPostConditions() {
		 assertFalse("there should not be any N4EnumDeclarations in the intermediate model",
			 state.im.eAllContents.exists[it instanceof N4EnumDeclaration]);
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, N4EnumDeclaration, false).forEach[transformEnumDecl];
	}

	def private void transformEnumDecl(N4EnumDeclaration enumDecl) {
		if(enumDecl.isStringBased) {
			// declarations of string-based enums are simply removed
			// (they do not have a representation in the output code)
			remove(enumDecl);
		} else {
			val EObject varOrFunDecl = if (state.project.isUseES6Imports) createFunDecl(enumDecl) else createVarDecl(enumDecl);
			val makeEnumCall = bootstrapCallAssistant.createMakeEnumCall(enumDecl);
			state.tracer.copyTrace(enumDecl, makeEnumCall);

			var EObject root;
			if (varOrFunDecl instanceof VariableDeclaration) {
				replace(enumDecl, varOrFunDecl);
				state.info.markAsToHoist(varOrFunDecl);
				root = varOrFunDecl.eContainer.orContainingExportDeclaration;
			} else if (varOrFunDecl instanceof FunctionDeclaration) {
				replace(enumDecl, varOrFunDecl);
				root = varOrFunDecl.orContainingExportDeclaration;
			}
			insertAfter(root, makeEnumCall);
		}
 	}

	/**
	 * Creates declaration of the variable that will represent the enumeration.
	 */
	def private VariableDeclaration createVarDecl(N4EnumDeclaration enumDecl) {
		return _VariableDeclaration(enumDecl.name)=>[
			expression = _FunExpr(false, enumDecl.name, #[ _Fpar("name"), _Fpar("value") ],
				_SnippetAsStmnt('''
					this.name = name;
					this.value = value;
				''')
			);
		];
	}

	/**
	 * Same as {@link #createVarDecl(N4EnumDeclaration)}, but creates a function declaration
	 * instead of variable declaration with function expression as initializer.
	 */
	def private FunctionDeclaration createFunDecl(N4EnumDeclaration enumDecl) {
		return _FunDecl(enumDecl.name, #[ _Fpar("name"), _Fpar("value") ],
			_SnippetAsStmnt('''
				this.name = name;
				this.value = value;
			''')
		);
	}

	def private boolean isStringBased(N4EnumDeclaration enumDecl) {
		return AnnotationDefinition.STRING_BASED.hasAnnotation(enumDecl);
	}
}
