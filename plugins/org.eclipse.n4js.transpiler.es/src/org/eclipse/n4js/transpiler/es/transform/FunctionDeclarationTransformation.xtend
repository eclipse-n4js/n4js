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
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.assistants.TypeAssistant

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 */
class FunctionDeclarationTransformation extends Transformation {

	@Inject private TypeAssistant typeAssistant;


	override assertPreConditions() {
		// always provide pre conditions
	}

	override assertPostConditions() {
		if (state.project.isUseES6Imports) {
			return;
		}
		assertFalse("there should not be any top-level FunctionDeclarations in the intermediate model",
			state.im.eAllContents.filter(FunctionDeclaration).exists[typeAssistant.isTopLevel(it)]);
	}

	override analyze() {
		// we could analyze something, store the information in a private field
		// and then access that information in method #transform() below
	}

	override transform() {
		if (state.project.isUseES6Imports) {
			return;
		}
		collectNodes(state.im, FunctionDeclaration, false).forEach[doTransform];
	}

	def private void doTransform(FunctionDeclaration funDecl) {
		val name = funDecl.name;

		val varDecl = _VariableDeclaration(name,
			_FunExpr(funDecl.async,
				name,
				funDecl.fpars,	// reusing existing fpars!
				funDecl.body	// reusing existing body!
			) => [
				generator = funDecl.generator;
				if(funDecl.annotationList !== null && funDecl.annotationList.annotations.size > 0) {
					annotationList = _ExprAnnoList( funDecl.annotationList.annotations ); // reusing annotations from original annotation-list.
				}
			]
		);

		replace(funDecl, varDecl);

		state.info.markAsToHoist(varDecl);
	}
}
