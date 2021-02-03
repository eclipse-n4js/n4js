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
import org.eclipse.emf.ecore.EReference
import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4TypeVariable
import org.eclipse.n4js.n4JS.TypeRefAnnotationArgument
import org.eclipse.n4js.n4JS.TypeReferenceInAST
import org.eclipse.n4js.n4JS.TypedElement
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.EcoreUtilN4

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
		var EReference from = null;
		var EReference to = null;
		switch (node) {
			TypedElement: {
				from = N4JSPackage.Literals.TYPED_ELEMENT__DECLARED_TYPE_REF_IN_AST;
				to = N4JSPackage.Literals.TYPED_ELEMENT__DECLARED_TYPE_REF;
			}
			FunctionDefinition: {
				from = N4JSPackage.Literals.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF_IN_AST;
				to = N4JSPackage.Literals.FUNCTION_DEFINITION__DECLARED_RETURN_TYPE_REF;
			}
			N4TypeVariable: {
				from = N4JSPackage.Literals.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND_IN_AST;
				to = N4JSPackage.Literals.N4_TYPE_VARIABLE__DECLARED_UPPER_BOUND;
			}
			CastExpression: {
				from = N4JSPackage.Literals.CAST_EXPRESSION__TARGET_TYPE_REF_IN_AST;
				to = N4JSPackage.Literals.CAST_EXPRESSION__TARGET_TYPE_REF;
			}
			TypeRefAnnotationArgument: {
				from = N4JSPackage.Literals.TYPE_REF_ANNOTATION_ARGUMENT__TYPE_REF_IN_AST;
				to = N4JSPackage.Literals.TYPE_REF_ANNOTATION_ARGUMENT__TYPE_REF;
			}
			TypeReferenceInAST<?>: {
				from = N4JSPackage.Literals.TYPE_REFERENCE_IN_AST__TYPE_REF_IN_AST;
				to = N4JSPackage.Literals.TYPE_REFERENCE_IN_AST__TYPE_REF;
			}
		}

		if (from !== null && to !== null) {
			val typeRefInAST = node.eGet(from) as TypeRef;
			val resultTypeRef = if (typeRefInAST !== null) doHandleTypeRef(G, typeRefInAST) else null;
			if (resultTypeRef !== null) {
				val toAsVal = to;
				EcoreUtilN4.doWithDeliver(false, [
					node.eSet(toAsVal, resultTypeRef);
				], node);
			}
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
