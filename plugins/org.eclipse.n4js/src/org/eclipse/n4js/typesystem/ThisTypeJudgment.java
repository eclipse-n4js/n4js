/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem;

import static org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.anyTypeRefDynamic;
import static org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.globalObjectTypeRef;
import static org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.isInBody_Of_StaticMethod;
import static org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.isInReturnDeclaration_Of_StaticMethod;
import static org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.undefinedTypeRef;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ArrowFunction;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.N4SetterDeclaration;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ThisTarget;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.utils.TypeExtensions;
import org.eclipse.n4js.ts.utils.TypeUtils;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xsemantics.runtime.RuleEnvironment;
import org.eclipse.xtext.EcoreUtil2;

import com.google.inject.Inject;

/**
 * Computes the this type at the given location. Since ECMAScript does not support lexical this typing, this basically
 * is a heuristic.
 */
public class ThisTypeJudgment extends AbstractJudgment {

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	/**
	 * Computes the this type at the given location. Since ECMAScript does not support lexical this typing, this
	 * basically is a heuristic.
	 */
	public JResult<TypeRef> apply(RuleEnvironment G, EObject location) {
		TypeRef resultValue = doApply(G, location);
		return resultValue != null
				? JResult.success(resultValue)
				: JResult.failure("thisTypeJudgment failed", false, null);
	}

	private TypeRef doApply(RuleEnvironment G, EObject location) {
		if (location instanceof ParameterizedTypeRef) {
			return TypeUtils.createBoundThisTypeRef((ParameterizedTypeRef) location);
		}
		final FunctionOrFieldAccessor containingFunctionOrAccessor = N4JSASTUtils
				.getContainingFunctionOrAccessor(location);
		if (containingFunctionOrAccessor instanceof ArrowFunction) {
			return doApply(G, containingFunctionOrAccessor);
		}

		// searching for declaredTypeRefs introduced through @This
		final IdentifiableElement containingTFunctionOrAccessor = containingFunctionOrAccessor != null
				? containingFunctionOrAccessor.getDefinedFunctionOrAccessor()
				: null;
		final TypeRef declaredThisType = TypeSystemHelper.declaredThisType(containingTFunctionOrAccessor);
		if (declaredThisType != null) {
			if (declaredThisType instanceof ParameterizedTypeRef) {
				return doApply(G, declaredThisType);
			}
			return declaredThisType;
		}

		final ThisTarget thisTarget = N4JSASTUtils.getProbableThisTarget(location);
		if (thisTarget instanceof ObjectLiteral) {
			// call rule, type may be created on the fly
			return ts.type(G, (ObjectLiteral) thisTarget).getValue(); // FIXME what about failures?
		} else if (thisTarget instanceof N4ClassifierDefinition) {
			Type thisTargetDEFTYPE = ((N4ClassifierDefinition) thisTarget).getDefinedType();

			// In case of static polyfill (filler), replace defined type with filled type:
			if (thisTarget instanceof N4ClassDeclaration) {
				final TClass clazz = ((N4ClassDeclaration) thisTarget).getDefinedTypeAsClass();
				if (clazz != null && clazz.isStaticPolyfill()) {
					final Type actualClazz = clazz.getSuperClassRef().getDeclaredType();
					if (actualClazz != null) {
						thisTargetDEFTYPE = actualClazz;
					}
				}
			}

			if (thisTargetDEFTYPE != null) {
				final FunctionDefinition containingFunction = N4JSASTUtils.getContainingFunction(location);
				if (containingFunction instanceof N4MethodDeclaration &&
						((N4MemberDeclaration) containingFunction).isStatic()) {
					if (isInReturnDeclaration_Of_StaticMethod(location, (N4MethodDeclaration) containingFunction)) {
						return doApply(G, TypeExtensions.ref(thisTargetDEFTYPE));
					} else if (isInBody_Of_StaticMethod(location, (N4MethodDeclaration) containingFunction)) {
						return TypeUtils.createClassifierBoundThisTypeRef(
								TypeUtils.createTypeTypeRef(TypeExtensions.ref(thisTargetDEFTYPE), false));
					} else {
						return TypeUtils.createConstructorTypeRef(thisTargetDEFTYPE);
					}
				} else {
					final N4FieldDeclaration n4Field = EcoreUtil2.getContainerOfType(location,
							N4FieldDeclaration.class);
					if (n4Field != null && n4Field.isStatic()) {
						// this case has been disallowed as of GHOLD-263
						return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
						// old:
						// return TypeUtils.createConstructorTypeRef(thisTargetDEFTYPE);
					} else {
						final N4GetterDeclaration n4Getter = EcoreUtil2.getContainerOfType(location,
								N4GetterDeclaration.class);
						if (n4Getter != null && n4Getter.isStatic()) {
							return TypeUtils.createConstructorTypeRef(thisTargetDEFTYPE);
						} else {
							final N4SetterDeclaration n4Setter = EcoreUtil2.getContainerOfType(location,
									N4SetterDeclaration.class);
							if (n4Setter != null && n4Setter.isStatic()) {
								return TypeUtils.createConstructorTypeRef(thisTargetDEFTYPE);
							} else {
								return doApply(G, TypeExtensions.ref(thisTargetDEFTYPE));
							}
						}
					}
				}
			} else {
				return anyTypeRefDynamic(G);
			}
		} else {
			// if (unrestricted.isActive(location)) {
			if (jsVariantHelper.hasGlobalObject(location)) {
				return globalObjectTypeRef(G);
			}
			return undefinedTypeRef(G);
		}
	}
}
