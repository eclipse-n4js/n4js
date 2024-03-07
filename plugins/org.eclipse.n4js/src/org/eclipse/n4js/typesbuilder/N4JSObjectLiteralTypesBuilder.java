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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.Arrays;

import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.AbstractNamespace;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;

import com.google.inject.Inject;

/**
 */
public class N4JSObjectLiteralTypesBuilder {

	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;
	@Inject
	N4JSFormalParameterTypesBuilder _n4JSFormalParameterTypesBuilder;

	void createObjectLiteral(ObjectLiteral objectLiteral, AbstractNamespace target, boolean preLinkingPhase) {
		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(objectLiteral.eResource().getResourceSet());
		TStructuralType structType = TypesFactory.eINSTANCE.createTStructuralType();

		for (PropertyAssignment it : objectLiteral.getPropertyAssignments()) {
			if (it.getName() != null || it.hasComputedPropertyName()) {
				TStructMember typeModelElement = createTypeModelElement(it, builtInTypeScope, preLinkingPhase);
				if (typeModelElement != null) {
					typeModelElement.setAstElement(it);
					structType.getOwnedMembers().add(typeModelElement);
				}
			}
		}

		structType.setAstElement(objectLiteral);
		objectLiteral.setDefinedType(structType);

		target.getContainingModule().getInternalTypes().add(structType);
	}

	// TODO GH-1337 add support for spread operator
	private TStructMember createTypeModelElement(
			PropertyAssignment getterDecl, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {

		if (getterDecl instanceof PropertyGetterDeclaration) {
			return createTypeModelElement((PropertyGetterDeclaration) getterDecl,
					builtInTypeScope, preLinkingPhase);
		} else if (getterDecl instanceof PropertyMethodDeclaration) {
			return createTypeModelElement((PropertyMethodDeclaration) getterDecl,
					builtInTypeScope, preLinkingPhase);
		} else if (getterDecl instanceof PropertyNameValuePair) {
			return createTypeModelElement((PropertyNameValuePair) getterDecl,
					builtInTypeScope, preLinkingPhase);
		} else if (getterDecl instanceof PropertySetterDeclaration) {
			return createTypeModelElement((PropertySetterDeclaration) getterDecl,
					builtInTypeScope, preLinkingPhase);
		} else if (getterDecl != null) {
			return null;
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.asList(getterDecl, builtInTypeScope, preLinkingPhase).toString());
		}
	}

	/**
	 * Creates a TStructField.
	 */
	private TStructField createTypeModelElement(
			PropertyNameValuePair nameValuePair, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		TStructField field = TypesFactory.eINSTANCE.createTStructField();
		_n4JSTypesBuilderHelper.setMemberName(field, nameValuePair);
		field.setOptional(nameValuePair.isDeclaredOptional());
		if (nameValuePair.getDeclaredTypeRefInAST() != null) {
			if (!preLinkingPhase) {
				field.setTypeRef(TypeUtils.copyWithProxies(nameValuePair.getDeclaredTypeRefInAST()));
			}
		} else if (nameValuePair.getExpression() != null) {
			field.setTypeRef(TypeUtils.createDeferredTypeRef());
		} else {
			// FIXME inconsistent with getter/setter case; should use DeferredTypeRef also if expression===null
			field.setTypeRef(builtInTypeScope.getAnyTypeRef());
		}
		// else {
		// // in all other cases:
		// // leave it to the corresponding xsemantics rule to infer the type (e.g. from the initializer expression, if
		// given)
		// if(!preLinkingPhase) {
		// field.typeRef =
		// TypeUtils.createComputedTypeRef([resolveAllComputedTypeRefsInTStructuralType(structType,objectLiteral,builtInTypeScope)])
		// }
		// }
		field.setAstElement(nameValuePair);
		nameValuePair.setDefinedField(field);
		return field;
	}

	/**
	 * Creates a TStructGetter.
	 */
	private TStructGetter createTypeModelElement(PropertyGetterDeclaration getterDecl,
			@SuppressWarnings("unused") BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {

		TStructGetter getter = TypesFactory.eINSTANCE.createTStructGetter();
		_n4JSTypesBuilderHelper.setMemberName(getter, getterDecl);
		getter.setOptional(getterDecl.isDeclaredOptional());
		if (getterDecl.getDeclaredTypeRefInAST() != null) {
			if (!preLinkingPhase) {
				getter.setTypeRef(TypeUtils.copyWithProxies(getterDecl.getDeclaredTypeRefInAST()));
			}
		} else {
			getter.setTypeRef(TypeUtils.createDeferredTypeRef());
		}
		getter.setAstElement(getterDecl);
		getterDecl.setDefinedGetter(getter);
		return getter;
	}

	/**
	 * Creates a TStructSetter.
	 */
	private TStructSetter createTypeModelElement(PropertySetterDeclaration setterDecl,
			@SuppressWarnings("unused") BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {

		TStructSetter setter = TypesFactory.eINSTANCE.createTStructSetter();
		_n4JSTypesBuilderHelper.setMemberName(setter, setterDecl);
		setter.setOptional(setterDecl.isDeclaredOptional());
		// IMPORTANT: do not create the formal parameter with N4JSFormalParameterTypesBuilder#createFormalParameter()
		// because we here use improved type inference (the type of a getter/setter in an object literal is inferred
		// similarly to that of a name/value pair)
		TFormalParameter param = TypesFactory.eINSTANCE.createTFormalParameter();
		if (setterDecl.getFpar() != null) {
			param.setName(setterDecl.getFpar().getName());
			TypeRef fparDeclTypeRef = setterDecl.getFpar().getDeclaredTypeRefInAST();
			if (fparDeclTypeRef != null) {
				if (!preLinkingPhase) {
					param.setTypeRef(TypeUtils.copyWithProxies(fparDeclTypeRef));
				}
			} else {
				param.setTypeRef(TypeUtils.createDeferredTypeRef());
			}
			param.setAstElement(setterDecl.getFpar());
			setterDecl.getFpar().setDefinedVariable(param);
		} else {
			// broken AST
			param.setTypeRef(TypeUtils.createDeferredTypeRef());
			// (note: using UnknownTypeRef would make more sense, but PolyComputer expects this, because
			// setterDecl.declaredTypeRef===setterDecl?.fpar.declaredTypeRef===null, so setterDecl will be poly)
		}
		setter.setFpar(param);
		setter.setAstElement(setterDecl);
		setterDecl.setDefinedSetter(setter);
		return setter;
	}

	/**
	 * Creates a TStructMethod.
	 */
	private TStructMethod createTypeModelElement(
			PropertyMethodDeclaration methodDecl, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {

		TStructMethod result = TypesFactory.eINSTANCE.createTStructMethod();
		_n4JSTypesBuilderHelper.setMemberName(result, methodDecl);
		// IMPORTANT: do not create the formal parameters as above for the property setters but instead create them with
		// method N4JSFormalParameterTypesBuilder#createFormalParameter() (for consistency with methods in classes)
		result.getFpars().addAll(
				toList(map(methodDecl.getFpars(), fp -> _n4JSFormalParameterTypesBuilder.createFormalParameter(fp,
						builtInTypeScope, preLinkingPhase))));
		if (methodDecl.getDeclaredReturnTypeRefInAST() != null) {
			if (!preLinkingPhase) {
				result.setReturnTypeRef(TypeUtils.copyWithProxies(methodDecl.getDeclaredReturnTypeRefInAST()));
			}
		} else {
			result.setReturnTypeRef(builtInTypeScope.getVoidTypeRef());
		}
		result.setAstElement(methodDecl);
		methodDecl.setDefinedType(result);
		return result;
	}
}
