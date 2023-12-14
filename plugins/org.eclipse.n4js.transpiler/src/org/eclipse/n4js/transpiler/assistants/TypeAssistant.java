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
package org.eclipse.n4js.transpiler.assistants;

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks._PropertyAccessExpr;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.n4ObjectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.symbolObjectType;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.forall;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.AnnotationList;
import org.eclipse.n4js.n4JS.ExportDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.transpiler.AbstractTranspiler;
import org.eclipse.n4js.transpiler.InformationRegistry;
import org.eclipse.n4js.transpiler.TransformationAssistant;
import org.eclipse.n4js.transpiler.TranspilerState;
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM;
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal;
import org.eclipse.n4js.transpiler.utils.ConcreteMembersOrderedForTranspiler;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;

import com.google.inject.Inject;

/**
 */
public class TypeAssistant extends TransformationAssistant {

	@Inject
	private JavaScriptVariantHelper jsVariantHelper;

	/**
	 * Some assertions related to {@link N4ClassifierDeclaration}s that apply to several transformations and are
	 * therefore factored out into this helper method.
	 */
	public void assertClassifierPreConditions() {
		if (AbstractTranspiler.DEBUG_PERFORM_ASSERTIONS) {
			List<N4ClassifierDeclaration> allClassifierDecls = collectNodes(getState().im,
					N4ClassifierDeclaration.class, false);
			assertTrue("all classifier declarations must have an original defined type",
					forall(allClassifierDecls, cd -> getState().info.getOriginalDefinedType(cd) != null));

			assertTrue("all class declarations must have a superClassRef pointing to a TClass (if non-null)",
					forall(filterNull(map(filter(allClassifierDecls, N4ClassDeclaration.class),
							cd -> cd.getSuperClassRef())),
							scr -> {
								TypeRef tRef = getState().info.getOriginalProcessedTypeRef(scr);
								Type originalDeclType = tRef == null ? null : tRef.getDeclaredType();
								return originalDeclType instanceof TClass;
							}));

			assertTrue(
					"all classifier declarations must have all implementedOrExtendedInterfaceRefs pointing to a TInterface",
					forall(flatten(map(allClassifierDecls,
							cd -> cd.getImplementedOrExtendedInterfaceRefs())),
							ir -> {
								TypeRef tRef = getState().info.getOriginalProcessedTypeRef(ir);
								Type originalDeclType = tRef == null ? null : tRef.getDeclaredType();
								return originalDeclType instanceof TInterface;
							}));

		}
	}

	/**
	 * Same as {@link InformationRegistry#getOriginalProcessedTypeRef(TypeReferenceNode)}.
	 */
	public TypeRef getOriginalOrContainedTypeRef(TypeReferenceNode<?> typeRefNodeInIM) {
		TypeRef originalTypeRef = getState().info.getOriginalProcessedTypeRef(typeRefNodeInIM);
		if (originalTypeRef != null) {
			return originalTypeRef;
		}
		// note: typeRefNodeInIM.getTypeRefInAST() will always be 'null', so no point in using that
		return null;
	}

	/***/
	// keep aligned to following method!
	public SymbolTableEntryOriginal getOriginalDeclaredTypeSTE(TypeReferenceNode<?> typeRefNodeInIM) {
		TypeRef typeRef = getOriginalOrContainedTypeRef(typeRefNodeInIM);
		Type declType = typeRef == null ? null : typeRef.getDeclaredType();
		if (declType != null) {
			return getSymbolTableEntryOriginal(declType, true);
		}
		return null;
	}

	/***/
	// keep aligned to previous method!
	public Type getOriginalDeclaredType(TypeReferenceNode<?> typeRefNodeInIM) {
		TypeRef typeRef = getOriginalOrContainedTypeRef(typeRefNodeInIM);
		return typeRef == null ? null : typeRef.getDeclaredType();
	}

	/**
	 * Returns symbol table entry for super class of given class declaration.
	 */
	public SymbolTableEntryOriginal getSuperClassSTE(N4ClassDeclaration classDecl) {
		TypeReferenceNode<ParameterizedTypeRef> superClassRef = classDecl == null ? null : classDecl.getSuperClassRef();
		if (superClassRef != null) {
			SymbolTableEntryOriginal superClassSTE = getOriginalDeclaredTypeSTE(superClassRef);
			if (superClassSTE != null) {
				return superClassSTE;
			}
		}
		return getSymbolTableEntryOriginal(n4ObjectType(getState().G), true);
	}

	/**
	 * Returns super interfaces (i.e. implemented or extended interfaces) of given classifier.
	 */
	public List<SymbolTableEntryOriginal> getSuperInterfacesSTEs(N4ClassifierDeclaration classifierDecl) {
		List<TypeReferenceNode<ParameterizedTypeRef>> superIfcRefNodes;

		if (classifierDecl instanceof N4ClassDeclaration) {
			superIfcRefNodes = ((N4ClassDeclaration) classifierDecl).getImplementedInterfaceRefs();
		} else if (classifierDecl instanceof N4InterfaceDeclaration) {
			superIfcRefNodes = ((N4InterfaceDeclaration) classifierDecl).getSuperInterfaceRefs();
		} else {
			throw new IllegalStateException("unsupported subclass of N4ClassifierDeclaration: "
					+ (classifierDecl == null ? null : classifierDecl.getName()));
		}
		return toList(filterNull(map(superIfcRefNodes, si -> getOriginalDeclaredTypeSTE(si))));
	}

	/**
	 * Tells if the given classifier is declared on top level.
	 */
	public boolean isTopLevel(TypeDefiningElement typeDef) {
		EObject parent = typeDef.eContainer();
		while (parent instanceof ExportDeclaration || parent instanceof AnnotationList) {
			parent = parent.eContainer();
		}
		return parent instanceof Script;
	}

	/**
	 * Tells if the given type is defined in an N4JSD file.
	 * <p>
	 * WARNING: for interfaces it is not enough to check {@link TInterface#isExternal()}, for this purpose, because
	 * structural interfaces in N4JSD files need not be declared external!
	 */
	public boolean inN4JSD(Type type) {
		return jsVariantHelper.isExternalMode(type);
	}

	/**
	 * For a member name that represents a symbol, such as <code>#iterator</code>, this method will return a property
	 * access expression that will evaluate to the corresponding symbol, e.g. <code>Symbol.iterator</code>.
	 */
	public ParameterizedPropertyAccessExpression_IM getMemberNameAsSymbol(String memberName) {
		if (!memberName.startsWith(N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX)) {
			throw new IllegalArgumentException("given member name does not denote a symbol");
		}
		return _PropertyAccessExpr(
				getSymbolTableEntryOriginal(symbolObjectType(getState().G), true),
				getSymbolTableEntryInternal(memberName.substring(1), true));
	}

	/**
	 * Returns an instance of {@link ConcreteMembersOrderedForTranspiler} for the given classifier, using a cached
	 * instance if available.
	 */
	public ConcreteMembersOrderedForTranspiler getOrCreateCMOFT(TClassifier classifier) {
		ConcreteMembersOrderedForTranspiler cachedCMOFT = getState().info.getCachedCMOFT(classifier);
		if (cachedCMOFT != null) {
			return cachedCMOFT;
		} else {
			ConcreteMembersOrderedForTranspiler newCMOFT = ConcreteMembersOrderedForTranspiler.create(getState(),
					classifier);
			getState().info.cacheCMOFT(classifier, newCMOFT);
			return newCMOFT;
		}
	}

	/**
	 * From a given {@link FunctionDefinition} of the IM, this methods returns the {@link TypeRef} of the return type.
	 */
	public TypeRef getReturnTypeRef(TranspilerState state, FunctionDefinition funDef) {
		EObject astNode = state.tracer.getOriginalASTNode(funDef);
		if (astNode instanceof FunctionDefinition) {
			TFunction tFunction = ((FunctionDefinition) astNode).getDefinedFunction();
			if (tFunction != null) {
				TypeRef outerReturnTypeRef = tFunction.getReturnTypeRef();
				if (outerReturnTypeRef == null) {
					// If you get an exception here: a transformation might have created an async and/or generator
					// FunctionDefinition without the expected Promise<...> / [Async]Generator<...> return type
					// (therefore the above call to method #hasExpectedSpecialReturnType() returned false);
					// automatically deriving the outer from an inner return type is not supported for
					// FunctionDefinitions created by transformations!
					throw new IllegalStateException(
							"unable to obtain outer return type of function from TModule");
				}
				return outerReturnTypeRef;
			}
		}
		return null;
	}
}
