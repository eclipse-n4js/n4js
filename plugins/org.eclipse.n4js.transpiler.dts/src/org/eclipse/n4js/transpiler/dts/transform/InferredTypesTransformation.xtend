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
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Makes the inferred type of
 * <ol>
 * <li>variables on top level, and
 * <li>fields of classes and interfaces
 * </ol>
 * explicit in the intermediate model, so that {@code PrettyPrinterDts} can handle them.
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
		makeInferredTypesExplicit();
	}

	def private void makeInferredTypesExplicit() {
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
					makeInferredTypeExplicit(typedElem);
				}
			}
		}
	}

	def private void makeInferredTypeExplicit(TypedElement elemInIM) {
		if (elemInIM.declaredTypeRefNode !== null) {
			// type already provided explicitly, so nothing to do here
			return;
		}
		val elemInAST = state.tracer.getOriginalASTNode(elemInIM);
		if (elemInAST instanceof TypableElement) {
			val typeRef = ts.type(state.G, elemInAST);
			if (typeRef !== null) {
				val typeRefCpy = TypeUtils.copy(typeRef);
				val typeRefNode = _TypeReferenceNode(state, typeRefCpy);
				elemInIM.declaredTypeRefNode = typeRefNode;
			}
		} else if (elemInAST !== null) {
			throw new IllegalStateException("expected original AST node of typed element to be a typable element, but was: " + elemInAST.eClass.name);
		}
	}
}
