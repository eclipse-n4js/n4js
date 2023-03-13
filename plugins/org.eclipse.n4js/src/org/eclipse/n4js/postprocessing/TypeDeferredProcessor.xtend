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
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration
import org.eclipse.n4js.n4JS.SetterDeclaration
import org.eclipse.n4js.n4JS.TypedElement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TTypedElement
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.types.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.EcoreUtilN4

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.TypeReferenceNode
import org.eclipse.n4js.ts.types.TInterface

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

	def void handleDeferredTypeRefs_preChildren(RuleEnvironment G, EObject astNode, ASTMetaInfoCache cache) {
		// DeferredTypeRefs related to poly expressions should not be handled here (poly computer responsible for this!)
		switch (astNode) {
			N4MethodDeclaration: {
				val declReturnTypeRefInAST = astNode.declaredReturnTypeRefNode?.typeRefInAST;
				if (astNode.isConstructor) {
					val tCtor = astNode.definedType as TMethod;
					if (null !== tCtor) {
						assertTrueIfRigid(cache, "TMethod in TModule should be a constructor", tCtor.isConstructor);
						assertTrueIfRigid(cache, "return type of constructor in TModule should be a DeferredTypeRef",
							tCtor.returnTypeRef instanceof DeferredTypeRef);
						val implicitReturnTypeRef = TypeRefsFactory.eINSTANCE.createThisTypeRefNominal;
						val boundThisTypeRef = tsh.bindAndSubstituteThisTypeRef(G, astNode, implicitReturnTypeRef);
						EcoreUtilN4.doWithDeliver(false, [
							tCtor.returnValueMarkedOptional = true;
							tCtor.returnTypeRef = TypeUtils.copy(boundThisTypeRef);
						], tCtor);
					}
				} else if (declReturnTypeRefInAST instanceof ThisTypeRef) {
					val tMethod = astNode.definedType as TMethod;
					if (null !== tMethod) {
						assertTrueIfRigid(cache, "return type of TMethod in TModule should be a DeferredTypeRef",
							tMethod.returnTypeRef instanceof DeferredTypeRef);
						val boundThisTypeRef = tsh.bindAndSubstituteThisTypeRef(G, declReturnTypeRefInAST, declReturnTypeRefInAST);
						EcoreUtilN4.doWithDeliver(false, [
							tMethod.returnTypeRef = TypeUtils.copy(boundThisTypeRef);
						], tMethod);
					}
				}
			}
			N4GetterDeclaration: {
				val declReturnTypeRefInAST = astNode.declaredTypeRefNode?.typeRefInAST;
				if (declReturnTypeRefInAST instanceof ThisTypeRef) {
					val tGetter = astNode.definedGetter;
					assertTrueIfRigid(cache, "return type of TGetter in TModule should be a DeferredTypeRef",
						tGetter.typeRef instanceof DeferredTypeRef);
					val boundThisTypeRef = tsh.getThisTypeAtLocation(G, declReturnTypeRefInAST); // G |~ methodDecl.returnTypeRef ~> boundThisTypeRef
					EcoreUtilN4.doWithDeliver(false, [
						tGetter.typeRef = TypeUtils.copy(boundThisTypeRef);
					], tGetter);
				}
			}
		};
	}

	def void handleDeferredTypeRefs_postChildren(RuleEnvironment G, EObject astNode, ASTMetaInfoCache cache) {
		// DeferredTypeRefs related to poly expressions should not be handled here (poly computer responsible for this!)
		switch (astNode) {
			VariableDeclaration: {
				val tVariable = astNode.definedVariable;
				setTypeRef(astNode, tVariable, false, G, cache);
			}
			N4FieldDeclaration: {
				val tField = astNode.definedField;
				setTypeRef(astNode, tField, true, G, cache);
			}
			N4ClassDeclaration: {
				if (astNode.superClassRef !== null && astNode.superClassRef.typeRef instanceof ParameterizedTypeRef) {
					val superClassRef = astNode.superClassRef.typeRef as ParameterizedTypeRef;
					val superClass = superClassRef.declaredType;
					if (superClass instanceof TClass) {
						EcoreUtilN4.doWithDeliver(false, [
							superClass.subClassRefs.add(TypeUtils.createTypeRef(astNode.definedType));
						], superClass);
					}
				}
				if (astNode.implementedInterfaceRefs !== null) {
					for (TypeReferenceNode<?> trn : astNode.implementedInterfaceRefs) {
						if (trn.typeRef instanceof ParameterizedTypeRef) {
							val superInterfaceRef = trn.typeRef as ParameterizedTypeRef;
							val superInterface = superInterfaceRef.declaredType;
							if (superInterface instanceof TInterface) {
								EcoreUtilN4.doWithDeliver(false, [
									superInterface.subClassRefs.add(TypeUtils.createTypeRef(astNode.definedType));
								], superInterface);
							}
						}
					}
				}
			}
			N4InterfaceDeclaration: {
				if (astNode.superInterfaceRefs !== null) {
					for (TypeReferenceNode<?> trn : astNode.superInterfaceRefs) {
						if (trn.typeRef instanceof ParameterizedTypeRef) {
							val superInterfaceRef = trn.typeRef as ParameterizedTypeRef;
							val superInterface = superInterfaceRef.declaredType;
							if (superInterface instanceof TInterface) {
								EcoreUtilN4.doWithDeliver(false, [
									superInterface.subInterfaceRefs.add(TypeUtils.createTypeRef(astNode.definedType));
								], superInterface);
							}
						}
					}
				}
			}
			FormalParameter: {
				val parent = astNode.eContainer;
				switch (parent) {
					FunctionExpression: {
						// do nothing since its DeferredTypes are computed in PolyProcessor_FunctionExpression
					}
					PropertyMethodDeclaration: {
						// do nothing since its DeferredTypes are computed in PolyProcessor_ObjectLiteral
					}
					FunctionDefinition: {
						val tFPar = astNode.definedVariable; // tFPar can be null if we have a broken AST
						if (tFPar?.typeRef instanceof DeferredTypeRef) {
							setTypeRef(astNode, tFPar, true, G, cache);
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

	private def <T extends TypableElement & TypedElement> void setTypeRef(T elemInAST, TTypedElement elemInTModule, boolean useContext,
		RuleEnvironment G, ASTMetaInfoCache cache) {

		if (elemInAST.declaredTypeRef === null) {
			if (elemInTModule !== null) { // note: tte===null happens if obj.name===null (see types builder)
				assertTrueIfRigid(cache, "return type of "+ elemInAST.class.name +" in TModule should be a DeferredTypeRef",
					elemInTModule.typeRef instanceof DeferredTypeRef);

				var RuleEnvironment G2 = G;
				if (useContext) {
					var ParameterizedTypeRef context = null;
					if (elemInTModule.eContainer instanceof ContainerType<?>)
						context = TypeUtils.createTypeRef(elemInTModule.eContainer as ContainerType<?>);
					G2 = ts.createRuleEnvironmentForContext(context, G.contextResource);
				}
				var TypeArgument typeRef = invokeTypeJudgmentToInferType(G2, elemInAST); // delegate to rule caseN4FieldDeclaration, etc.
				if (useContext) {
					typeRef = ts.substTypeVariables(G2, typeRef);
				}
				val typeRefResolved = tsh.resolveTypeAliases(G, typeRef); // this runs after TypeAliasProcessor, so we need to resolve here
				val typeRefSane = tsh.sanitizeTypeOfVariableFieldPropertyParameter(G, typeRefResolved, !N4JSASTUtils.isImmutable(elemInAST));
				EcoreUtilN4.doWithDeliver(false, [
					elemInTModule.typeRef = TypeUtils.copy(typeRefSane);
				], elemInTModule);
			}
		}
	}
}
