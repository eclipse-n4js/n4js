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
package org.eclipse.n4js.postprocessing

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration
import org.eclipse.n4js.n4JS.SetterDeclaration
import org.eclipse.n4js.n4JS.TypedElement
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TTypedElement
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.TypeSystemHelper
import org.eclipse.n4js.utils.EcoreUtilN4

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * Processor for handling {@link DeferredTypeRef}s, <b>except</b> those related to poly expressions, which are handled
 * by the {@link PolyProcessor}s.
 */
@Singleton
package class TypeDeferredProcessor extends AbstractProcessor {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;

	def void handleDeferredTypeRefs_preChildren(RuleEnvironment G, EObject obj, ASTMetaInfoCache cache) {
		// DeferredTypeRefs related to poly expressions should not be handled here (poly computer responsible for this!)
		switch (obj) {
			N4MethodDeclaration: {
				val returnTypeRef = obj.returnTypeRef;
				if (obj.isConstructor) {
					val tCtor = obj.definedType as TMethod;
					if (null !== tCtor) {
						assertTrueIfRigid(cache, "TMethod in TModule should be a constructor", tCtor.isConstructor);
						assertTrueIfRigid(cache, "return type of constructor in TModule should be a DeferredTypeRef",
							tCtor.returnTypeRef instanceof DeferredTypeRef);
						val implicitReturnTypeRef = TypeRefsFactory.eINSTANCE.createThisTypeRef;
						val boundThisTypeRef = tsh.bindAndSubstituteThisTypeRef(G, obj, implicitReturnTypeRef);
						EcoreUtilN4.doWithDeliver(false, [
							tCtor.returnValueMarkedOptional = true;
							tCtor.returnTypeRef = TypeUtils.copy(boundThisTypeRef);
						], tCtor);
					}
				} else if (returnTypeRef instanceof ThisTypeRef) {
					val tMethod = obj.definedType as TMethod;
					if (null !== tMethod) {
						assertTrueIfRigid(cache, "return type of TMethod in TModule should be a DeferredTypeRef",
							tMethod.returnTypeRef instanceof DeferredTypeRef);
						val boundThisTypeRef = tsh.bindAndSubstituteThisTypeRef(G, returnTypeRef, returnTypeRef);
						EcoreUtilN4.doWithDeliver(false, [
							tMethod.returnTypeRef = TypeUtils.copy(boundThisTypeRef);
						], tMethod);
					}
				}
			}
			N4GetterDeclaration: {
				val returnTypeRef = obj.declaredTypeRef;
				if (returnTypeRef instanceof ThisTypeRef) {
					val tGetter = obj.definedGetter;
					assertTrueIfRigid(cache, "return type of TGetter in TModule should be a DeferredTypeRef",
						tGetter.declaredTypeRef instanceof DeferredTypeRef);
					val boundThisTypeRef = ts.thisTypeRef(G, returnTypeRef).value; // G |~ methodDecl.returnTypeRef ~> boundThisTypeRef
					EcoreUtilN4.doWithDeliver(false, [
						tGetter.declaredTypeRef = TypeUtils.copy(boundThisTypeRef);
					], tGetter);
				}
			}
		};
	}

	def void handleDeferredTypeRefs_postChildren(RuleEnvironment G, EObject obj, ASTMetaInfoCache cache) {
		// DeferredTypeRefs related to poly expressions should not be handled here (poly computer responsible for this!)
		switch (obj) {
			ExportedVariableDeclaration: {
				val tVariable = obj.definedVariable;
				setTypeRef(obj, tVariable, false, G, cache);
			}
			N4FieldDeclaration: {
				val tField = obj.definedField;
				setTypeRef(obj, tField, true, G, cache);
			}
			FormalParameter: {
				val parent = obj.eContainer;
				switch (parent) {
					FunctionExpression: {
						// do nothing since its DeferredTypes are computed in PolyProcessor_FunctionExpression
					}
					PropertyMethodDeclaration: {
						// do nothing since its DeferredTypes are computed in PolyProcessor_ObjectLiteral
					}
					FunctionDefinition: {
						val tFPar = obj.definedTypeElement; // tFPar can be null if we have a broken AST
						if (tFPar?.typeRef instanceof DeferredTypeRef) {
							setTypeRef(obj, tFPar, true, G, cache);
						}
					}
					SetterDeclaration: {
						// do nothing since setters don't have Deferred Types (and cannot have a default initializer)
					}
					default:
						throw new IllegalArgumentException("Unsupported parent type of FormalParameter")
				}
			}
		}
	}

	private def <T extends TypableElement & TypedElement> void setTypeRef(T typedElem, TTypedElement tte, boolean useContext,
		RuleEnvironment G, ASTMetaInfoCache cache
	) {
		if (typedElem.declaredTypeRef === null) {
			if (tte !== null) { // note: tte===null happens if obj.name===null (see types builder)
				assertTrueIfRigid(cache, "return type of "+ typedElem.class.name +" in TModule should be a DeferredTypeRef",
					tte.typeRef instanceof DeferredTypeRef);

				var RuleEnvironment G2 = G;
				if (useContext) {
					var ParameterizedTypeRef context = null;
					if (tte.eContainer instanceof ContainerType<?>)
						context = TypeUtils.createTypeRef(tte.eContainer as ContainerType<?>);
					G2 = ts.createRuleEnvironmentForContext(context, G.contextResource);
				}
				var TypeArgument fieldTypeRef = askXsemanticsForType(G2, null, typedElem).value; // delegate to Xsemantics rule typeN4FieldDeclaration
				if (useContext) {
					fieldTypeRef = ts.substTypeVariables(G2, fieldTypeRef).value;
				}
				val fieldTypeRefSane = tsh.sanitizeTypeOfVariableFieldProperty(G, fieldTypeRef);
				EcoreUtilN4.doWithDeliver(false, [
					tte.typeRef = TypeUtils.copy(fieldTypeRefSane);
				], tte);
			}
		}
	}
}
