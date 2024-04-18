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

import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.types.utils.TypeUtils;

import com.google.inject.Inject;

class N4JSFormalParameterTypesBuilder {

	@Inject
	N4JSTypesBuilderHelper _n4JSTypesBuilderHelper;

	boolean relinkFormalParameter(FormalParameter astFormalParameter, TFunction functionType,
			BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase, int idx) {
		TFormalParameter formalParameterType = functionType.getFpars().get(idx);
		_n4JSTypesBuilderHelper.ensureEqualName(astFormalParameter, formalParameterType);

		formalParameterType.setAstElement(astFormalParameter);
		astFormalParameter.setDefinedVariable(formalParameterType);
		setFormalParameterType(formalParameterType, astFormalParameter, null, builtInTypeScope, preLinkingPhase);

		return true;
	}

	TFormalParameter createFormalParameter(FormalParameter n4FormalParameter, BuiltInTypeScope builtInTypeScope,
			boolean preLinkingPhase) {
		return createFormalParameter(n4FormalParameter, null, builtInTypeScope, preLinkingPhase);
	}

	/**
	 * Creates a TFormalParameter for the given FormalParameter from the AST.
	 *
	 * @param defaultTypeRef
	 *            will be used in case there is no declared type for the formal parameter; this may be <code>null</code>
	 *            and in this case <code>any</code> will be the formal parameter's actual type.
	 */
	TFormalParameter createFormalParameter(FormalParameter astFormalParameter, TypeRef defaultTypeRef,
			BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		// note: we also build an fpar if astFormalParameter.name===null (otherwise the AST and types model
		// would have different number of formal parameters in case of a broken AST, messing up indices, etc.)
		TFormalParameter formalParameterType = TypesFactory.eINSTANCE.createTFormalParameter();
		formalParameterType.setName(astFormalParameter.getName());
		formalParameterType.setVariadic(astFormalParameter.isVariadic());
		formalParameterType.setAstInitializer(null);
		formalParameterType.setHasInitializerAssignment(astFormalParameter.isHasInitializerAssignment());
		setFormalParameterType(formalParameterType, astFormalParameter, defaultTypeRef, builtInTypeScope,
				preLinkingPhase);

		_n4JSTypesBuilderHelper.copyAnnotations(formalParameterType, astFormalParameter, preLinkingPhase);

		formalParameterType.setAstElement(astFormalParameter);
		astFormalParameter.setDefinedVariable(formalParameterType);

		return formalParameterType;
	}

	/**
	 * @param formalParameterType
	 *            the type system related parameter type to be set
	 * @param astFormalParameter
	 *            the AST related parameter which is to be copied to the former
	 */
	private void setFormalParameterType(TFormalParameter formalParameterType, FormalParameter astFormalParameter,
			TypeRef defaultTypeRef, BuiltInTypeScope builtInTypeScope, boolean preLinkingPhase) {
		if (!preLinkingPhase) {
			TypeRef copy = TypeUtils.copyWithProxies(astFormalParameter.getDeclaredTypeRefInAST());
			formalParameterType.setTypeRef(copy != null ? copy
					: getDefaultParameterType(defaultTypeRef, astFormalParameter, builtInTypeScope));
		}
	}

	private TypeRef getDefaultParameterType(TypeRef defaultTypeRef,
			FormalParameter astFormalParameter, BuiltInTypeScope builtInTypeScope) {
		if (astFormalParameter.getInitializer() != null) {
			return TypeUtils.createDeferredTypeRef();
		} else if (defaultTypeRef == null) {
			return builtInTypeScope.getAnyTypeRef();
		} else {
			return TypeUtils.copy(defaultTypeRef);
		}
	}
}
