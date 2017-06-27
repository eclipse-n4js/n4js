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
package org.eclipse.n4js.typesbuilder

import com.google.inject.Inject
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.utils.TypeUtils

package class N4JSFormalParameterTypesBuilder {
	@Inject extension N4JSTypesBuilderHelper

	def package TFormalParameter createFormalParameter(FormalParameter n4FormalParameter, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		return createFormalParameter(n4FormalParameter, null, builtInTypeScope, preLinkingPhase);
	}

	/**
	 * Creates a TFormalParameter for the given FormalParameter from the AST.
	 *
	 * @param defaultTypeRef  will be used in case there is no declared type for the formal parameter;
	 *                        this may be <code>null</code> and in this case <code>any</code> will be
	 *                        the formal parameter's actual type.
	 */
	def package TFormalParameter createFormalParameter(FormalParameter astFormalParameter, TypeRef defaultTypeRef, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		// note: we also build an fpar if astFormalParameter.name===null (otherwise the AST and types model
		// would have different number of formal parameters in case of a broken AST, messing up indices, etc.)
		val formalParameterType = TypesFactory::eINSTANCE.createTFormalParameter();
		formalParameterType.name = astFormalParameter.name;
		formalParameterType.variadic = astFormalParameter.variadic;
		formalParameterType.astInitializer = null;
		formalParameterType.hasInitializerAssignment = astFormalParameter.hasInitializerAssignment;
		setFormalParameterType(formalParameterType, astFormalParameter, defaultTypeRef, builtInTypeScope, preLinkingPhase)

		copyAnnotations(formalParameterType, astFormalParameter, preLinkingPhase)

		formalParameterType.astElement = astFormalParameter;
		astFormalParameter.definedTypeElement = formalParameterType;

		return formalParameterType;
	}

	/**
	 * @param formalParameterType the type system related parameter type to be set
	 * @param astFormalParameter the AST related parameter which is to be copied to the former
	 */
	def private void setFormalParameterType(TFormalParameter formalParameterType, FormalParameter astFormalParameter,
				TypeRef defaultTypeRef, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase
	) {
		setCopyOfReference(
			[ TypeRef copyOfDeclaredTypeRef |
				formalParameterType.typeRef = copyOfDeclaredTypeRef.orDefaultParameterType(defaultTypeRef, astFormalParameter, builtInTypeScope)
			], astFormalParameter.declaredTypeRef, preLinkingPhase)
	}

	def private TypeRef orDefaultParameterType(TypeRef copyOfDeclaredTypeRef, TypeRef defaultTypeRef,
		FormalParameter astFormalParameter, BuiltInTypeScope builtInTypeScope
	) {
		if (copyOfDeclaredTypeRef === null) {
			if (astFormalParameter.initializer !== null) {
				TypeUtils.createDeferredTypeRef
			} else if (defaultTypeRef === null) {
				builtInTypeScope.anyTypeRef
			} else {
				TypeUtils.copy(defaultTypeRef)
			}
		} else {
			copyOfDeclaredTypeRef
		}
	}
}
