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
package org.eclipse.n4js.typesbuilder;

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PropertyAssignment
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.PropertySetterDeclaration
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TStructField
import org.eclipse.n4js.ts.types.TStructGetter
import org.eclipse.n4js.ts.types.TStructMember
import org.eclipse.n4js.ts.types.TStructMethod
import org.eclipse.n4js.ts.types.TStructSetter
import org.eclipse.n4js.ts.types.TStructuralType
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.utils.TypeUtils

/**
 */
public class N4JSObjectLiteralTypesBuilder {

	@Inject extension N4JSTypesBuilderHelper
	@Inject extension N4JSFormalParameterTypesBuilder


	def package void createObjectLiteral(ObjectLiteral objectLiteral, TModule target, boolean preLinkingPhase) {
		val builtInTypeScope = BuiltInTypeScope.get(objectLiteral.eResource.resourceSet)
		val TStructuralType structType = TypesFactory.eINSTANCE.createTStructuralType
		objectLiteral.propertyAssignments.filter[name!==null || hasComputedPropertyName].forEach [
			val TStructMember typeModelElement = createTypeModelElement(structType, objectLiteral, it, builtInTypeScope, preLinkingPhase);
			if(typeModelElement!==null) {
				typeModelElement.astElement = it;
				structType.ownedMembers += typeModelElement
			}
		]

		structType.astElement = objectLiteral;
		objectLiteral.definedType = structType;

		target.internalTypes += structType
	}

	// TODO GH-1337 add support for spread operator
	private def dispatch TStructMember createTypeModelElement(TStructuralType structType, ObjectLiteral objectLiteral, PropertyAssignment assignment, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
	}

	/**
	 * Creates a TStructField.
	 */
	private def dispatch TStructField createTypeModelElement(TStructuralType structType, ObjectLiteral objectLiteral, PropertyNameValuePair nameValuePair, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		val TStructField field = TypesFactory.eINSTANCE.createTStructField
		field.setMemberName(nameValuePair);
		field.optional = nameValuePair.declaredOptional;
		if (nameValuePair.declaredTypeRefInAST !== null) {
			if (!preLinkingPhase) {
				field.typeRef = TypeUtils.copyWithProxies(nameValuePair.declaredTypeRefInAST)
			}
		}
		else if(nameValuePair.expression !== null) {
			field.typeRef = TypeUtils.createDeferredTypeRef;
		}
		else {
			field.typeRef = builtInTypeScope.anyTypeRef; // FIXME inconsistent with getter/setter case; should use DeferredTypeRef also if expression===null
		}
//		else {
//			// in all other cases:
//			// leave it to the corresponding xsemantics rule to infer the type (e.g. from the initializer expression, if given)
//			if(!preLinkingPhase) {
//				field.typeRef = TypeUtils.createComputedTypeRef([resolveAllComputedTypeRefsInTStructuralType(structType,objectLiteral,builtInTypeScope)])
//			}
//		}
		field.astElement = nameValuePair;
		nameValuePair.definedField = field;
		return field
	}

	/**
	 * Creates a TStructGetter.
	 */
	private def dispatch TStructGetter createTypeModelElement(TStructuralType structType, ObjectLiteral objectLiteral, PropertyGetterDeclaration getterDecl, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		val TStructGetter getter = TypesFactory.eINSTANCE.createTStructGetter
		getter.setMemberName(getterDecl);
		getter.optional = getterDecl.declaredOptional;
		if (getterDecl.declaredTypeRefInAST !== null) {
			if (!preLinkingPhase) {
				getter.typeRef = TypeUtils.copyWithProxies(getterDecl.declaredTypeRefInAST)
			}
		} else {
			getter.typeRef = TypeUtils.createDeferredTypeRef;
		}
		getter.astElement = getterDecl;
		getterDecl.definedGetter = getter;
		return getter
	}

	/**
	 * Creates a TStructSetter.
	 */
	private def dispatch TStructSetter createTypeModelElement(TStructuralType structType, ObjectLiteral objectLiteral, PropertySetterDeclaration setterDecl, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		val TStructSetter setter = TypesFactory.eINSTANCE.createTStructSetter
		setter.setMemberName(setterDecl);
		setter.optional = setterDecl.declaredOptional;
		// IMPORTANT: do not create the formal parameter with N4JSFormalParameterTypesBuilder#createFormalParameter()
		// because we here use improved type inference (the type of a getter/setter in an object literal is inferred
		// similarly to that of a name/value pair)
		val param = TypesFactory.eINSTANCE.createTFormalParameter
		if (setterDecl.fpar !== null) {
			param.name = setterDecl.fpar.name
			val fparDeclTypeRef = setterDecl.fpar.declaredTypeRefInAST;
			if(fparDeclTypeRef!==null) {
				if (!preLinkingPhase) {
					param.typeRef = TypeUtils.copyWithProxies(fparDeclTypeRef);
				}
			} else {
				param.typeRef = TypeUtils.createDeferredTypeRef;
			}
		} else {
			// broken AST
			param.typeRef = TypeUtils.createDeferredTypeRef;
			// (note: using UnknownTypeRef would make more sense, but PolyComputer expects this, because
			// setterDecl.declaredTypeRef===setterDecl?.fpar.declaredTypeRef===null, so setterDecl will be poly)
		}
		setter.fpar = param
		setter.astElement = setterDecl;
		setterDecl.definedSetter = setter;
		return setter
	}

	/**
	 * Creates a TStructMethod.
	 */
	private def dispatch TStructMethod createTypeModelElement(TStructuralType structType, ObjectLiteral objectLiteral, PropertyMethodDeclaration methodDecl, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		val TStructMethod result = TypesFactory.eINSTANCE.createTStructMethod;
		result.setMemberName(methodDecl);
		// IMPORTANT: do not create the formal parameters as above for the property setters but instead create them with
		// method N4JSFormalParameterTypesBuilder#createFormalParameter() (for consistency with methods in classes)
		result.fpars += methodDecl.fpars.map[createFormalParameter(builtInTypeScope, preLinkingPhase)];
		if (methodDecl.declaredReturnTypeRefInAST !== null) {
			if (!preLinkingPhase) {
				result.returnTypeRef = TypeUtils.copyWithProxies(methodDecl.declaredReturnTypeRefInAST);
			}
		} else {
			result.returnTypeRef = builtInTypeScope.voidTypeRef;
		}
		result.astElement = methodDecl;
		methodDecl.definedType = result;
		return result;
	}
}
