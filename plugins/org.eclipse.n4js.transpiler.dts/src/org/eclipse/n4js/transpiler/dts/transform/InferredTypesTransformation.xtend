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

import com.google.common.collect.Iterables
import com.google.inject.Inject
import java.util.Collections
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.TypedElement
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.ts.types.TypableElement
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
			// (e.g. variables and fields, but not getters, setters)
			val typedElems = switch (rootElem) {
				VariableStatement: rootElem.varDecl // note: returns also variable declarations that are arbitrarily nested in a destructure pattern
				N4ClassifierDeclaration: {
					Iterables.concat(
						rootElem.ownedFields,
						rootElem.ownedMethods.flatMap[fpars],
						rootElem.ownedCtor === null ? Collections.emptyList : rootElem.ownedCtor.fpars
					);
				}
				FunctionDeclaration: rootElem.fpars
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

		// special handling for variadic fpars without declared type:
		// the type system always gives us the fpar's actual type that here includes the implicit array (i.e. Array<any>),
		// but this would be inconsistent with the meaning of an explicitly declared type, so we simply use 'any' here ...
		if (elemInIM instanceof FormalParameter) {
			if (elemInIM.variadic) {
				elemInIM.declaredTypeRefNode = _TypeReferenceNode(state, state.G.anyTypeRef);
				return;
			}
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
