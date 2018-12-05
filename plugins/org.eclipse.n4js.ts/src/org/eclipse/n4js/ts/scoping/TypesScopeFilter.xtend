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
package org.eclipse.n4js.ts.scoping

import com.google.common.base.Predicate
import com.google.common.base.Predicates
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.xtext.resource.IEObjectDescription

/**
 * Poor mans filter to reduce number of elements, filtering out elements which are not valid in a certain context, although
 * more elaborated filtering may be needed for content assist.
 */
class TypesScopeFilter {

	def Predicate<? super IEObjectDescription> getTypesFilterCriteria(EObject context, EReference reference) {
		switch (context) {
			TypeRef:
				context.getTypesFilterCriteria(reference)
			default:
				context.getTypeReferenceFilterCriteria(reference)
		}
	}

	protected def getTypesFilterCriteria(TypeRef context, EReference reference) {
		if (reference === TypeRefsPackage.eINSTANCE.parameterizedTypeRef_AstNamespace) {
			return namespaceCriterion;
		}
		var EObject container = context
		var EReference containmentFeature = null
		while(container instanceof TypeRef) {
			containmentFeature = container.eContainingFeature as EReference
			container = container.eContainer
		}
		return container.getTypeReferenceFilterCriteria(containmentFeature)
	}

	protected def getTypeReferenceFilterCriteria(EObject container, EReference containmentFeature) {
		switch(container) {
			TClass:
				getInheritanceFilterCriteria(containmentFeature)
			TypeVariable:
				typeVariableBoundCriteria
			TField:
				fieldTypeCriteria
			TMethod:
				returnTypeCriteria
			TFormalParameter:
				parameterTypeCriteria
			TFunction:
				returnTypeCriteria
			default:
				Predicates.alwaysTrue
		}
	}

	protected def Predicate<? super IEObjectDescription> getNamespaceCriterion() {
		[
			TypesPackage.Literals.MODULE_NAMESPACE_VIRTUAL_TYPE.isSuperTypeOf(EClass)
		]
	}

	protected def Predicate<? super IEObjectDescription> getParameterTypeCriteria() {
		[
			!TypesPackage.Literals.TFUNCTION.isSuperTypeOf(EClass)
			&& TypesPackage.Literals.UNDEFINED_TYPE != EClass
			&& TypesPackage.Literals.NULL_TYPE != EClass
			&& TypesPackage.Literals.VOID_TYPE != EClass
		]
	}

	protected def Predicate<? super IEObjectDescription> getReturnTypeCriteria() {
		[
			!TypesPackage.Literals.TFUNCTION.isSuperTypeOf(EClass)
			&& TypesPackage.Literals.UNDEFINED_TYPE != EClass
			&& TypesPackage.Literals.NULL_TYPE != EClass
		]
	}

	protected def Predicate<? super IEObjectDescription> getFieldTypeCriteria() {
		[
			!TypesPackage.Literals.TFUNCTION.isSuperTypeOf(EClass)
			&& TypesPackage.Literals.UNDEFINED_TYPE != EClass
			&& TypesPackage.Literals.NULL_TYPE != EClass
			&& TypesPackage.Literals.VOID_TYPE != EClass
		]
	}

	protected def Predicate<? super IEObjectDescription> getTypeVariableBoundCriteria() {
		[ TypesPackage.Literals.TCLASSIFIER.isSuperTypeOf(EClass) ]
	}

	protected def Predicate<? super IEObjectDescription> getInheritanceFilterCriteria(EReference reference) {
		switch (reference) {
			case TypesPackage.Literals.TCLASS__SUPER_CLASS_REF:
				[ TypesPackage.Literals.TCLASS.isSuperTypeOf(EClass) ]
			case TypesPackage.Literals.TCLASS__IMPLEMENTED_INTERFACE_REFS:
				[ TypesPackage.Literals.TINTERFACE.isSuperTypeOf(EClass) ]
		}
	}
}
