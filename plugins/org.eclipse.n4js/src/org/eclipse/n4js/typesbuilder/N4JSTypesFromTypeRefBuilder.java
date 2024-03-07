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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression;
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructGetter;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TStructMethod;
import org.eclipse.n4js.ts.types.TStructSetter;
import org.eclipse.n4js.ts.types.TStructuralType;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.utils.N4JSLanguageUtils;

/**
 * Methods for creating types from TypeRefs are collected in this class.
 * <p>
 * The methods of this class might seem different from other createXYZ() methods in the types builder package, in that
 * they take a subclass of TypeRef and not a typical AST node element. However, SturcturalTypeRefs and
 * FunctionTypeExpressions are among those TypeRefs that <em>may</em> appear in the AST and play the role of an AST
 * node. The methods in this class will only be invoked for such TypeRefs that appear in the AST, so these method are,
 * in fact, very similar to the other createXYZ() methods.
 */
public class N4JSTypesFromTypeRefBuilder {

	/**
	 * Creates a TStructuralType in the target module if the StructuralTypeRef has structural members defined (in the
	 * with-clause). For more details why this is required, see API doc of StructuralTypeRef.
	 */
	void createStructuralType(StructuralTypeRef structTypeRef) {
		if (structTypeRef.getAstStructuralMembers().isEmpty()) {
			return;
		}

		ResourceSet resSet = structTypeRef.eResource().getResourceSet();
		if (resSet == null) {
			throw new IllegalArgumentException("structTypeRef must be contained in AST");
		}

		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(resSet);
		TStructuralType structType = TypesFactory.eINSTANCE.createTStructuralType();

		for (TStructMember memberInAST : structTypeRef.getAstStructuralMembers()) {
			TStructMember memberForTModule = createTStructMember(memberInAST, builtInTypeScope);
			if (memberInAST.isASTCallSignature()) {
				if (structType.getCallSignature() == null) {
					structType.setCallSignature((TStructMethod) memberForTModule);
				} else {
					// error case: duplicate call signatures
					// --> to avoid scoping from returning elements that are not contained in a resource (esp. in case
					// of
					// type parameters of generic call signatures), we have to add 'memberForTModule' as an ordinary
					// member
					structType.getOwnedMembers().add(memberForTModule);
				}
			} else if (memberInAST.isASTConstructSignature()) {
				if (structType.getConstructSignature() == null) {
					structType.setConstructSignature((TStructMethod) memberForTModule);
				} else {
					// error case: duplicate construct signatures
					// --> we have to add 'memberForTModule' as an ordinary member (see above)
					structType.getOwnedMembers().add(memberForTModule);
				}
			} else {
				structType.getOwnedMembers().add(memberForTModule);
			}
		}

		structTypeRef.setStructuralType(structType);
	}

	private <T extends TStructMember> T createTStructMember(T memberInAST, BuiltInTypeScope builtInTypeScope) {
		if (memberInAST == null) {
			return null;
		}
		T memberForModule = TypeUtils.copyWithProxies(memberInAST);
		applyDefaults(builtInTypeScope, memberForModule);
		memberForModule.setAstElement(memberInAST);
		memberInAST.setDefinedMember(memberForModule);
		return memberForModule;
	}

	/**
	 * Creates a TFunction in the target module if the FunctionTypeExpression is generic. For more details why this is
	 * required, see API doc of FunctionTypeExpression.
	 */
	void createTFunction(FunctionTypeExpression fte) {
		if (!fte.isGeneric()) {
			return;
		}

		ResourceSet resSet = fte.eResource().getResourceSet();
		if (resSet == null) {
			throw new IllegalArgumentException("fte must be contained in AST");
		}

		BuiltInTypeScope builtInTypeScope = BuiltInTypeScope.get(resSet);
		TFunction ft = TypesFactory.eINSTANCE.createTFunction();

		// TODO support hyper links
		ft.getTypeVars().addAll(toList(map(fte.getTypeVars(), currTypeVar -> TypeUtils.copyWithProxies(currTypeVar))));
		ft.getFpars().addAll(toList(map(fte.getFpars(), currFpar -> {
			TFormalParameter clone = TypeUtils.copyWithProxies(currFpar);
			applyDefaults(builtInTypeScope, (EObject) clone);
			clone.setAstElement(currFpar);
			return clone;
		})));
		ft.setReturnTypeRef(TypeUtils.copyWithProxies(fte.getReturnTypeRef()));
		ft.setDeclaredThisType(TypeUtils.copyWithProxies(fte.getDeclaredThisType()));

		if (ft.getReturnTypeRef() == null) {
			ft.setReturnTypeRef(builtInTypeScope.getAnyTypeRef());
		}

		fte.setDeclaredType(ft);
		ft.setAstElement(fte);
	}

	private void applyDefaults(BuiltInTypeScope builtInTypeScope, EObject getter) {
		if (getter instanceof TStructGetter) {
			applyDefaults(builtInTypeScope, (TStructGetter) getter);
			return;
		} else if (getter instanceof TStructMethod) {
			applyDefaults(builtInTypeScope, (TStructMethod) getter);
			return;
		} else if (getter instanceof TStructSetter) {
			applyDefaults(builtInTypeScope, (TStructSetter) getter);
			return;
		} else if (getter instanceof TStructField) {
			applyDefaults(builtInTypeScope, (TStructField) getter);
			return;
		} else if (getter instanceof TFormalParameter) {
			applyDefaults(builtInTypeScope, (TFormalParameter) getter);
			return;
		} else {
			throw new IllegalArgumentException("Unhandled parameter types: " +
					Arrays.asList(builtInTypeScope, getter).toString());
		}
	}

	private void applyDefaults(BuiltInTypeScope builtInTypeScope, TStructField field) {
		if (field.getTypeRef() == null) {
			field.setTypeRef(builtInTypeScope.getAnyTypeRef());
		}
	}

	private void applyDefaults(BuiltInTypeScope builtInTypeScope, TStructGetter getter) {
		if (getter.getTypeRef() == null) {
			getter.setTypeRef(builtInTypeScope.getAnyTypeRef());
		}
	}

	private void applyDefaults(BuiltInTypeScope builtInTypeScope, TStructSetter setter) {
		// note: setter.fpar==null and setter.fpar.getTypeRef()==null are disallowed by syntax, but
		// setting default types for these cases gives us better behavior in case of broken ASTs
		if (setter.getFpar() == null) {
			setter.setFpar(TypesFactory.eINSTANCE.createTAnonymousFormalParameter());
		}
		applyDefaults(builtInTypeScope, (EObject) setter.getFpar());
	}

	private void applyDefaults(BuiltInTypeScope builtInTypeScope, TStructMethod method) {
		if (method.isASTCallSignature()) {
			method.setName(N4JSLanguageUtils.CALL_SIGNATURE_NAME);
		}
		for (TFormalParameter it : method.getFpars()) {
			applyDefaults(builtInTypeScope, it);
		}
		if (method.getReturnTypeRef() == null) {
			method.setReturnTypeRef(builtInTypeScope.getVoidTypeRef());
		}
	}

	private void applyDefaults(BuiltInTypeScope builtInTypeScope, TFormalParameter fpar) {
		if (fpar.getTypeRef() == null) {
			fpar.setTypeRef(builtInTypeScope.getAnyTypeRef());
		}
	}
}
