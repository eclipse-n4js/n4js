/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.dts.transform

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.dts.print.PrettyPrinterSwitchDts
import org.eclipse.n4js.transpiler.dts.utils.DtsUtils
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Makes the inferred type of
 * <ol>
 * <li>variables on top level, and
 * <li>fields of classes and interfaces
 * </ol>
 * explicit in the intermediate model, so that {@link PrettyPrinterSwitchDts} can handle them.
 */
class InferredTypesTransformation extends Transformation {

	@Inject
	private N4JSTypeSystem ts;

	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		state.im.scriptElements
			.map[if (it instanceof ExportDeclaration) it.exportedElement else it]
			.filter(VariableStatement)
			.flatMap[varDecl] // FIXME what about VariableBindings?
			.filter(VariableDeclaration)
			.forEach[handleInferredType];
	}

	def private void handleInferredType(VariableDeclaration varDecl) {
		val declTypeRefNode = varDecl.declaredTypeRefNode;
		if (declTypeRefNode !== null && state.info.getOriginalProcessedTypeRef(declTypeRefNode) !== null) {
			// type was provided explicitly in the source code, so ignore this one
			return;
		}

		if (declTypeRefNode === null) {
			makeInferredTypeExplicit(varDecl);
		}

		hideTypeIfUnavailable(varDecl);
	}

	def private void makeInferredTypeExplicit(VariableDeclaration varDecl) {
		val varDeclInAST = state.tracer.getOriginalASTNode(varDecl);
		if (varDeclInAST instanceof VariableDeclaration) {
			val typeRef = ts.type(state.G, varDeclInAST);
			if (typeRef !== null) {
				val typeRefCpy = TypeUtils.copy(typeRef);
				if (typeRefCpy instanceof ParameterizedTypeRef) {
					// FIXME explain why this is done:
					typeRefCpy.declaredTypeAsText = null;
				}

				val typeRefNode = _TypeReferenceNode(null);
				varDecl.declaredTypeRefNode = typeRefNode;
				state.info.setOriginalProcessedTypeRef(typeRefNode, typeRefCpy);
			}
		}
	}

	def private void hideTypeIfUnavailable(VariableDeclaration varDecl) {
		val typeRefNode = varDecl.declaredTypeRefNode;
		if (typeRefNode !== null) {
			val typeRef = state.info.getOriginalProcessedTypeRef(typeRefNode);
			if (typeRef !== null && !isAvailable(typeRef)) {
				state.info.setOriginalProcessedTypeRef(typeRefNode, state.G.anyTypeRef);
			}
		}
	}

	def private boolean isAvailable(TypeRef typeRef) {
		return TypeUtils.forAllTypeRefs(typeRef, ParameterizedTypeRef, true, [ ptr |
			return DtsUtils.getNameOfDeclaredTypeIfLocallyAvailable(ptr, state) !== null;
		], null);
	}
}
