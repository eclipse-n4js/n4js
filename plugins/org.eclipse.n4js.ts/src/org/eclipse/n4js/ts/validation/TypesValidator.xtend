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
package org.eclipse.n4js.ts.validation

/**
 * Custom validation rules.
 * @see http://www.eclipse.org/Xtext/documentation.html#validation
 * @see <a name="N4JSSpec">[N4JSSpec]</a> N4JS Specification / NumberFour AG. Berlin, 2013 <a href="https://github.com/NumberFour/specs/">[GitHub]</a>
 */
class TypesValidator extends AbstractTypesValidator {

	public static val TYPES_ANY_IN_UNIONTYPE = "TYPES_ANY_IN_UNIONTYPE";
	public static val TYPES_VOID_IN_UNIONTYPE = "TYPES_VOID_IN_UNIONTYPE";
	public static val TYPES_VOID_IN_FPAR = "TYPES_VOID_IN_FPAR";
	public static val TYPES_DYNAMIC_RETURNTYPE = "TYPES_DYNAMIC_RETURNTYPE";

//	/**
//	 * UnionTypeExpression element type refs must be neither AnyType nor Void and
//	 * the type ref itself must not be dynamic
//	 * @see N4JSSpec §4.12
//	 */
//	@Check
//	def checkUnionTypeExpressionTypeRefs(UnionTypeExpression unionTypeExpression) {
//		var index = 0;
//		for (typeRef : unionTypeExpression.typeRefs) {
//			if (typeRef instanceof AnyRef) {
//				error("Any must not be an element of a union type", unionTypeExpression,
//					TypesPackage.eINSTANCE.composedTypeRef_TypeRefs, index, TYPES_ANY_IN_UNIONTYPE)
//			}
//			if (typeRef instanceof VoidRef) {
//				error("Void must not be an element of a union type", unionTypeExpression,
//					TypesPackage.Literals.COMPOSED_TYPE_REF__TYPE_REFS, index, TYPES_VOID_IN_UNIONTYPE)
//			}
//			index = index + 1;
//		}
//	}
//
//	/**
//	 * Formal parameter type must not be Void;
//	 * return type must not be dynamic
//	 * @see N4JSSpec §4.7
//	 */
//	@Check
//	def checkTFunctionFParTypeRefs(TFunction function) {
//		var index = 0;
//		for (typeRef : function.fparTypeRefs) {
//			if (typeRef instanceof VoidRef) {
//				error("Formal parameter must not be of type Void", function,
//					TypesPackage.Literals.TFUNCTION__FPAR_TYPE_REFS, index, TYPES_VOID_IN_FPAR)
//			}
//			index = index + 1;
//		}
//		if (function.returnTypeRef.dynamic) {
//			error("Cannot return a dynamic type", function,
//				TypesPackage.Literals.TFUNCTION__RETURN_TYPE_REF, TYPES_DYNAMIC_RETURNTYPE)
//		}
//	}

}
