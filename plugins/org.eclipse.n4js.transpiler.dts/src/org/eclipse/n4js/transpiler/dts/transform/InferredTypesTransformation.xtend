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
import java.util.ArrayList
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
import org.eclipse.n4js.workspace.WorkspaceAccess

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Makes the inferred type of
 * <ol>
 * <li>variables on top level, and
 * <li>fields of classes and interfaces
 * </ol>
 * explicit in the intermediate model, so that {@code PrettyPrinterSwitchDts} can handle them.
 */
class InferredTypesTransformation extends Transformation {

	@Inject
	private N4JSTypeSystem ts;

	@Inject
	private WorkspaceAccess workspaceAccess;

	override assertPreConditions() {
	}

	override assertPostConditions() {
	}

	override analyze() {
		// ignore
	}

	override transform() {
		for (rootElemRaw : new ArrayList(state.im.scriptElements)) {
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

		makeTypeAvailableOrReplaceByAny(elemInIM);
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

	def private void makeTypeAvailableOrReplaceByAny(TypedElement elem) {
		val typeRefNode = elem.declaredTypeRefNode;
		if (typeRefNode !== null) {
			val typeRef = state.info.getOriginalProcessedTypeRef(typeRefNode);
			if (typeRef !== null && !makeDeclTypeAvailable(typeRef)) {
				// it was not possible to make all declared types referenced by 'typeRef' available
				// --> so replace this entire type reference by 'any':
				state.info.setOriginalProcessedTypeRef(typeRefNode, state.G.anyTypeRef);
			}
		}
	}

	/** Returns <code>false</code> if one of the referred declared types could not be made available. */
	def private boolean makeDeclTypeAvailable(TypeRef typeRef) {
		return TypeUtils.forAllTypeRefs(typeRef, ParameterizedTypeRef, true, true, [ ptr |
			return makeDeclTypeAvailable(ptr);
		], null);
	}

	/** Returns <code>false</code> if the declared type could not be made available. */
	def private boolean makeDeclTypeAvailable(ParameterizedTypeRef ptr) {
		val isAlreadyAvailable = DtsUtils.getNameOfDeclaredTypeIfLocallyAvailable(ptr, state) !== null;
		if (isAlreadyAvailable) {
			// the element is already available, but we have to make sure its import won't be removed as unused
			// even if all other usages will be removed (e.g. if they are in expressions/statements):
			val declType = ptr.declaredType;
			val ste = if (declType !== null) getSymbolTableEntryOriginal(declType, false);
			val importSpecifier = ste?.importSpecifier;
			if (importSpecifier !== null) {
				state.info.markAsRetainedIfUnused(importSpecifier);
			}
			return true;
		}
		val declType = ptr.declaredType;
		if (declType.exported) {
			// no need to check accessibility modifiers in addition to #isExported(), because on TypeScript-side we bump up the accessibility
			val declTypeProject = workspaceAccess.findProjectContaining(declType);
			if (declTypeProject !== null) {
				if (declTypeProject === state.project
					|| state.project.dependencies.contains(declTypeProject.name)) {
					// the type reference points to an exported type in the same project or a project we directly depend on
					// --> we can add an import for this type
					val ste = addNamedImport(declType, null);
					// we cannot add an actual reference to the newly created import, so we have to tell SanitizeImportTransformation
					// to never remove it:
					state.info.markAsRetainedIfUnused(ste.importSpecifier);
					return true;
				}
			}
		}
		return false;
	}
}
