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
package org.eclipse.n4js.postprocessing

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument
import org.eclipse.n4js.n4JS.TypeReferenceInAST
import org.eclipse.n4js.n4JS.TypedElement
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper

/**
 * Processor for converting the raw type references provided by the parser into valid type references that
 * can be used internally.
 * <p>
 * Most type references created by the parser are already valid and can be used directly; the only exceptions
 * are:
 * <ul>
 * <li>{@link TypeRef#isAliasUnresolved() unresolved type aliases} must be converted to
 * {@link TypeRef#isAliasResolved() resolved type aliases}.
 * </ul>
 */
package class TypeRefProcessor extends AbstractProcessor {

	@Inject
	private TypeSystemHelper tsh;

	def void handleTypeRefs(RuleEnvironment G, EObject node, ASTMetaInfoCache cache) {
		if (node instanceof TypedElement) {
			node.declaredTypeRef = doHandleTypeRef(G, node.declaredTypeRefInAST);
		}
		if (node instanceof FunctionDefinition) {
			node.declaredReturnTypeRef = doHandleTypeRef(G, node.declaredReturnTypeRefInAST);
		}
		if (node instanceof CastExpression) {
			node.targetTypeRef = doHandleTypeRef(G, node.targetTypeRefInAST);
		}
		if (node instanceof TypeRefAnnotationArgument) {
			node.typeRef = doHandleTypeRef(G, node.typeRefInAST);
		}
		if (node instanceof TypeReferenceInAST) {
			node.typeRef = doHandleTypeRef(G, node.typeRefInAST);
		}
	}

	def private TypeRef doHandleTypeRef(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef === null) {
			return null;
		}
		// note: we also have to resolve type aliases that might be nested below a non-alias TypeRef!
		return tsh.resolveTypeAliases(G, typeRef);
	}
}
