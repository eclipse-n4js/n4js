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
package org.eclipse.n4js.transpiler.dts.transform;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._TypeReferenceNode;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;

import java.util.Collections;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.TypedElement;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.transpiler.Transformation;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Makes the inferred type of
 * <ol>
 * <li>variables on top level, and
 * <li>fields of classes and interfaces
 * </ol>
 * explicit in the intermediate model, so that {@code PrettyPrinterDts} can handle them.
 */
public class InferredTypesTransformation extends Transformation {

	@Inject
	private N4JSTypeSystem ts;

	@Override
	public void assertPreConditions() {
		// empty
	}

	@Override
	public void assertPostConditions() {
		// empty
	}

	@Override
	public void analyze() {
		// ignore
	}

	@Override
	public void transform() {
		makeInferredTypesExplicit();
	}

	private void makeInferredTypesExplicit() {
		for (ScriptElement rootElemRaw : getState().im.getScriptElements()) {
			ScriptElement rootElem = (rootElemRaw instanceof ExportDeclaration)
					? ((ExportDeclaration) rootElemRaw).getExportedElement()
					: rootElemRaw;

			// obtain those typed elements, that may have an inferred type
			// (e.g. variables and fields, but not getters, setters)
			Iterable<? extends TypedElement> typedElems = Collections.emptyList();
			if (rootElem instanceof VariableStatement) {
				// note: returns also variable declarations that are arbitrarily nested in a destructure pattern
				typedElems = ((VariableStatement) rootElem).getVarDecl();
			}
			if (rootElem instanceof FunctionDeclaration) {
				typedElems = ((FunctionDeclaration) rootElem).getFpars();
			}
			if (rootElem instanceof N4ClassifierDeclaration) {
				N4ClassifierDeclaration cd = (N4ClassifierDeclaration) rootElem;
				typedElems = Iterables.concat(
						cd.getOwnedFields(),
						IterableExtensions.flatMap(cd.getOwnedMethods(), m -> m.getFpars()),
						cd.getOwnedCtor() == null ? Collections.emptyList() : cd.getOwnedCtor().getFpars());
			}

			if (typedElems != null) {
				for (TypedElement typedElem : typedElems) {
					makeInferredTypeExplicit(typedElem);
				}
			}
		}
	}

	private void makeInferredTypeExplicit(TypedElement elemInIM) {
		if (elemInIM.getDeclaredTypeRefNode() != null) {
			// type already provided explicitly, so nothing to do here
			return;
		}

		// special handling for variadic fpars without declared type:
		// the type system always gives us the fpar's actual type that here includes the implicit array (i.e.
		// Array<any>),
		// but this would be inconsistent with the meaning of an explicitly declared type, so we simply use 'any' here
		// ...
		if (elemInIM instanceof FormalParameter) {
			FormalParameter fPar = (FormalParameter) elemInIM;

			if (fPar.isVariadic()) {
				fPar.setDeclaredTypeRefNode(_TypeReferenceNode(getState(), anyTypeRef(getState().G)));
				return;
			}
		}

		EObject elemInAST = getState().tracer.getOriginalASTNode(elemInIM);
		if (elemInAST instanceof TypableElement) {
			TypeRef typeRef = ts.type(getState().G, (TypableElement) elemInAST);
			if (typeRef != null) {
				TypeRef typeRefCpy = TypeUtils.copy(typeRef);
				TypeReferenceNode<TypeRef> typeRefNode = _TypeReferenceNode(getState(), typeRefCpy);
				elemInIM.setDeclaredTypeRefNode(typeRefNode);
			}
		} else if (elemInAST != null) {
			throw new IllegalStateException(
					"expected original AST node of typed element to be a typable element, but was: "
							+ elemInAST.eClass().getName());
		}
	}
}
