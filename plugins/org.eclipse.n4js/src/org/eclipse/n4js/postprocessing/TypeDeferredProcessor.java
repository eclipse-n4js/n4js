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
package org.eclipse.n4js.postprocessing;

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getContextResource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4GetterDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.SetterDeclaration;
import org.eclipse.n4js.n4JS.TypedElement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.ThisTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TFormalParameter;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TTypedElement;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.EcoreUtilN4;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Processor for handling {@link DeferredTypeRef}s, <b>except</b> those related to poly expressions, which are handled
 * by the {@link PolyProcessor}s.
 */
@Singleton
class TypeDeferredProcessor extends AbstractProcessor {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;

	void handleDeferredTypeRefs_preChildren(RuleEnvironment G, EObject astNode, ASTMetaInfoCache cache) {
		// DeferredTypeRefs related to poly expressions should not be handled here (poly computer responsible for this!)
		if (astNode instanceof N4MethodDeclaration) {
			N4MethodDeclaration md = (N4MethodDeclaration) astNode;
			TypeRef declReturnTypeRefInAST = md.getDeclaredReturnTypeRefNode() == null ? null
					: md.getDeclaredReturnTypeRefNode().getTypeRefInAST();
			if (md.isConstructor()) {
				TMethod tCtor = (TMethod) md.getDefinedType();
				if (null != tCtor) {
					assertTrueIfRigid(cache, "TMethod in TModule should be a constructor", tCtor.isConstructor());
					assertTrueIfRigid(cache, "return type of constructor in TModule should be a DeferredTypeRef",
							tCtor.getReturnTypeRef() instanceof DeferredTypeRef);
					TypeRef implicitReturnTypeRef = TypeRefsFactory.eINSTANCE.createThisTypeRefNominal();
					TypeRef boundThisTypeRef = tsh.bindAndSubstituteThisTypeRef(G, md, implicitReturnTypeRef);
					EcoreUtilN4.doWithDeliver(false, () -> {
						tCtor.setReturnValueMarkedOptional(true);
						tCtor.setReturnTypeRef(TypeUtils.copy(boundThisTypeRef));
					}, tCtor);
				}
			} else if (declReturnTypeRefInAST instanceof ThisTypeRef) {
				TMethod tMethod = (TMethod) md.getDefinedType();
				if (null != tMethod) {
					assertTrueIfRigid(cache, "return type of TMethod in TModule should be a DeferredTypeRef",
							tMethod.getReturnTypeRef() instanceof DeferredTypeRef);
					TypeRef boundThisTypeRef = tsh.bindAndSubstituteThisTypeRef(G, declReturnTypeRefInAST,
							declReturnTypeRefInAST);
					EcoreUtilN4.doWithDeliver(false, () -> tMethod.setReturnTypeRef(TypeUtils.copy(boundThisTypeRef)),
							tMethod);
				}
			}

		} else if (astNode instanceof N4GetterDeclaration) {
			N4GetterDeclaration gd = (N4GetterDeclaration) astNode;
			TypeRef declReturnTypeRefInAST = gd.getDeclaredTypeRefNode() == null ? null
					: gd.getDeclaredTypeRefNode().getTypeRefInAST();
			if (declReturnTypeRefInAST instanceof ThisTypeRef) {
				TGetter tGetter = gd.getDefinedGetter();
				assertTrueIfRigid(cache, "return type of TGetter in TModule should be a DeferredTypeRef",
						tGetter.getTypeRef() instanceof DeferredTypeRef);
				// G |~ methodDecl.returnTypeRef ~> boundThisTypeRef
				TypeRef boundThisTypeRef = tsh.getThisTypeAtLocation(G, declReturnTypeRefInAST);
				EcoreUtilN4.doWithDeliver(false, () -> tGetter.setTypeRef(TypeUtils.copy(boundThisTypeRef)), tGetter);
			}
		}
	}

	void handleDeferredTypeRefs_postChildren(RuleEnvironment G, EObject astNode, ASTMetaInfoCache cache) {
		// DeferredTypeRefs related to poly expressions should not be handled here (poly computer responsible for this!)
		if (astNode instanceof VariableDeclaration) {
			VariableDeclaration vd = (VariableDeclaration) astNode;
			TVariable tVariable = vd.getDefinedVariable();
			setTypeRef(vd, tVariable, false, G, cache);
		} else if (astNode instanceof N4FieldDeclaration) {
			N4FieldDeclaration fd = (N4FieldDeclaration) astNode;
			TField tField = fd.getDefinedField();
			setTypeRef(fd, tField, true, G, cache);
		} else if (astNode instanceof FormalParameter) {
			FormalParameter fp = (FormalParameter) astNode;
			EObject parent = astNode.eContainer();
			if (parent instanceof FunctionExpression) {
				// do nothing since its DeferredTypes are computed in PolyProcessor_FunctionExpression
			} else if (parent instanceof PropertyMethodDeclaration) {
				// do nothing since its DeferredTypes are computed in PolyProcessor_ObjectLiteral
			} else if (parent instanceof FunctionDefinition) {
				TFormalParameter tFPar = fp.getDefinedVariable(); // tFPar can be null if we have a broken AST
				if (tFPar != null && tFPar.getTypeRef() instanceof DeferredTypeRef) {
					setTypeRef(fp, tFPar, true, G, cache);
				}
			} else if (parent instanceof SetterDeclaration) {
				// do nothing since setters don't have Deferred Types (and cannot have a default initializer)
			} else {
				throw new IllegalArgumentException("Unsupported parent type of FormalParameter");
			}
		}
	}

	private <T extends TypableElement & TypedElement> void setTypeRef(T elemInAST, TTypedElement elemInTModule,
			boolean useContext,
			RuleEnvironment G, ASTMetaInfoCache cache) {

		if (elemInAST.getDeclaredTypeRef() == null) {
			if (elemInTModule != null) { // note: tte==null happens if obj.name==null (see types builder)
				assertTrueIfRigid(cache,
						"return type of " + elemInAST.getClass().getName() + " in TModule should be a DeferredTypeRef",
						elemInTModule.getTypeRef() instanceof DeferredTypeRef);

				RuleEnvironment G2 = G;
				if (useContext) {
					ParameterizedTypeRef context = null;
					if (elemInTModule.eContainer() instanceof ContainerType<?>) {
						context = TypeUtils.createTypeRef((ContainerType<?>) elemInTModule.eContainer());
					}
					G2 = ts.createRuleEnvironmentForContext(context, getContextResource(G));
				}
				// delegate to rule caseN4FieldDeclaration, etc.
				TypeArgument typeRef = invokeTypeJudgmentToInferType(G2, elemInAST);
				if (useContext) {
					typeRef = ts.substTypeVariables(G2, typeRef);
				}
				// this runs after TypeAliasProcessor, so we need to resolve here
				TypeArgument typeRefResolved = tsh.resolveTypeAliases(G, typeRef);
				TypeRef typeRefSane = tsh.sanitizeTypeOfVariableFieldPropertyParameter(G, typeRefResolved,
						!N4JSASTUtils.isImmutable(elemInAST));
				EcoreUtilN4.doWithDeliver(false, () -> elemInTModule.setTypeRef(TypeUtils.copy(typeRefSane)),
						elemInTModule);
			}
		}
	}
}
