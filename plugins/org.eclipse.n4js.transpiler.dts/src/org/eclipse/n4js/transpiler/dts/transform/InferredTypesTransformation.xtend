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
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.TypedElement
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.dts.utils.DtsUtils
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import org.eclipse.n4js.transpiler.dts.print.PrettyPrinterDts

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
		for (rootElemRaw : state.im.scriptElements) {
			val rootElem = if (rootElemRaw instanceof ExportDeclaration) rootElemRaw.exportedElement else rootElemRaw;

			// obtain those typed elements, that may have an inferred type
			// (e.g. variables and fields, but not getters, setters, fpars)
			val typedElems = switch (rootElem) {
				VariableStatement: rootElem.varDecl // FIXME what about VariableBindings?
				N4ClassifierDeclaration: rootElem.ownedFields
			};

			if (typedElems !== null) {
				for (typedElem : typedElems) {
					handleInferredType(typedElem);
				}
			}
		}
	}

	def private void handleInferredType(TypedElement elemInIM) {
		val declTypeRefNode = elemInIM.declaredTypeRefNode;
		if (declTypeRefNode !== null && state.info.getOriginalProcessedTypeRef(declTypeRefNode) !== null) {
			// type was provided explicitly in the source code, so ignore this one
			return;
		}

		if (declTypeRefNode === null) {
			makeInferredTypeExplicit(elemInIM);
		}

		hideTypeIfUnavailable(elemInIM);
	}

	def private void makeInferredTypeExplicit(TypedElement elemInIM) {
		val elemInAST = state.tracer.getOriginalASTNode(elemInIM);
		if (elemInAST instanceof TypableElement) {
			val typeRef = ts.type(state.G, elemInAST);
			if (typeRef !== null) {
				val typeRefCpy = TypeUtils.copy(typeRef);
				if (typeRefCpy instanceof ParameterizedTypeRef) {
					// FIXME explain why this is done:
					typeRefCpy.declaredTypeAsText = null;
				}

				val typeRefNode = _TypeReferenceNode(null);
				elemInIM.declaredTypeRefNode = typeRefNode;
				state.info.setOriginalProcessedTypeRef(typeRefNode, typeRefCpy);
			}
		} else if (elemInAST !== null) {
			throw new IllegalStateException("expected original AST node of typed element to be a typable element, but was: " + elemInAST.eClass.name);
		}
	}

	def private void hideTypeIfUnavailable(TypedElement elem) {
		val typeRefNode = elem.declaredTypeRefNode;
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
