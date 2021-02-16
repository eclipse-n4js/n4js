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

import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TStructField
import org.eclipse.n4js.ts.types.TStructGetter
import org.eclipse.n4js.ts.types.TStructMethod
import org.eclipse.n4js.ts.types.TStructSetter
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.utils.TypeUtils

/**
 * Methods for creating types from TypeRefs are collected in this class.
 * <p>
 * The methods of this class might seem different from other createXYZ() methods in the types
 * builder package, in that they take a subclass of TypeRef and not a typical AST node element.
 * However, SturcturalTypeRefs and FunctionTypeExpressions are among those TypeRefs that
 * <em>may</em> appear in the AST and play the role of an AST node. The methods in this class
 * will only be invoked for such TypeRefs that appear in the AST, so these method are, in fact,
 * very similar to the other createXYZ() methods.
 */
public class N4JSTypesFromTypeRefBuilder {

	/**
	 * Creates a TStructuralType in the target module if the StructuralTypeRef has structural
	 * members defined (in the with-clause). For more details why this is required, see API
	 * doc of StructuralTypeRef.
	 */
	def package void createStructuralType(StructuralTypeRef structTypeRef, TModule target) {
		if (!structTypeRef.astStructuralMembers.empty) {

			val resSet = structTypeRef.eResource.resourceSet;
			if(resSet===null) {
				throw new IllegalArgumentException("structTypeRef must be contained in AST");
			}

			val builtInTypeScope = BuiltInTypeScope.get(resSet);
			val structType = TypesFactory.eINSTANCE.createTStructuralType;

			structType.ownedMembers.addAll(
				structTypeRef.astStructuralMembers.map [ currStructMember |
					val clone = TypeUtils.copyWithProxies(currStructMember);
					applyDefaults(builtInTypeScope, clone);
					clone.astElement = currStructMember;
					currStructMember.definedMember = clone;
					return clone;
				]);

			structTypeRef.structuralType = structType;

			target.internalTypes += structType;

		}
	}


	/**
	 * Creates a TFunction in the target module if the FunctionTypeExpression is generic.
	 * For more details why this is required, see API doc of FunctionTypeExpression.
	 */
	def package void createTFunction(FunctionTypeExpression fte, TModule target) {

		if (fte.generic) {

			val resSet = fte.eResource.resourceSet;
			if(resSet===null) {
				throw new IllegalArgumentException("fte must be contained in AST");
			}

			val builtInTypeScope = BuiltInTypeScope.get(resSet);
			val ft = TypesFactory.eINSTANCE.createTFunction();

			ft.typeVars.addAll(fte.typeVars.map[currTypeVar|TypeUtils.copyWithProxies(currTypeVar)]); // TODO support hyper links
			ft.fpars.addAll(fte.fpars.map[currFpar|
				val clone = TypeUtils.copyWithProxies(currFpar);
				applyDefaults(builtInTypeScope, clone);
				clone.astElement = currFpar;
				return clone;
			]);
			ft.returnTypeRef = TypeUtils.copyWithProxies(fte.returnTypeRef);
			ft.declaredThisType = TypeUtils.copyWithProxies(fte.declaredThisType);

			if(ft.returnTypeRef===null) {
				ft.returnTypeRef = builtInTypeScope.getAnyTypeRef();
			}

			fte.declaredType = ft;
			ft.astElement = fte;

			target.internalTypes += ft;

		}
	}


	def private dispatch void applyDefaults(BuiltInTypeScope builtInTypeScope, TStructField field) {
		if(field.typeRef===null) {
			field.typeRef = builtInTypeScope.getAnyTypeRef();
		}
	}
	def private dispatch void applyDefaults(BuiltInTypeScope builtInTypeScope, TStructGetter getter) {
		if(getter.typeRef===null) {
			getter.typeRef = builtInTypeScope.getAnyTypeRef();
		}
	}
	def private dispatch void applyDefaults(BuiltInTypeScope builtInTypeScope, TStructSetter setter) {
		// note: setter.fpar===null and setter.fpar.typeRef===null are disallowed by syntax, but
		// setting default types for these cases gives us better behavior in case of broken ASTs
		if(setter.fpar===null) {
			setter.fpar = TypesFactory.eINSTANCE.createTAnonymousFormalParameter();
		}
		applyDefaults(builtInTypeScope, setter.fpar);
	}
	def private dispatch void applyDefaults(BuiltInTypeScope builtInTypeScope, TStructMethod method) {
		method.fpars.forEach[applyDefaults(builtInTypeScope, it)];
		if(method.returnTypeRef===null) {
			method.returnTypeRef = builtInTypeScope.voidTypeRef
		}
	}
	def private dispatch void applyDefaults(BuiltInTypeScope builtInTypeScope, TFormalParameter fpar) {
		if(fpar.typeRef===null) {
			fpar.typeRef = builtInTypeScope.getAnyTypeRef();
		}
	}
}
