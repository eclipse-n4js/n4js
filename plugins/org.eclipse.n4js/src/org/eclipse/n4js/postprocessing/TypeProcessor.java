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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.anyTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getCancelIndicator;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrapTypeInTypeRef;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.getDefinedTypeModelElement;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isASTNode;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isTypableNode;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isTypeModelElement;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.isEmpty;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.FieldAccessor;
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.TypeDefiningElement;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.typeRefs.OptionalFieldStrategy;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.service.OperationCanceledManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Processor for handling type inference during post-processing of an N4JS resource. Roughly corresponds to 'type'
 * judgment in Xsemantics, but handles also more complex cases, e.g. poly expressions.
 * <p>
 * Invoked from {@link ASTProcessor} and delegates to {@link PolyProcessor}s.
 */
@Singleton
public class TypeProcessor extends AbstractProcessor {

	@Inject
	private ASTProcessor astProcessor;
	@Inject
	private PolyProcessor polyProcessor;
	@Inject
	private DestructureProcessor destructureProcessor;
	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private OperationCanceledManager operationCanceledManager;

	/**
	 * If the given AST node is typable this method will infer its type and store the result in the given cache.
	 * <p>
	 * This method mainly checks if the given node is typable. Main processing is done in
	 * {@link #typeNode2(RuleEnvironment, TypableElement, ASTMetaInfoCache, int) typeNode2()}.
	 */
	public void typeNode(RuleEnvironment G, EObject node, ASTMetaInfoCache cache, int indentLevel) {
		if (isTypableNode(node)) {
			TypableElement nodeCasted = (TypableElement) node; // because #isTypableNode() returned true
			// we have a typable node
			if (DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(node)
					&& polyProcessor.isEntryPoint(nodeCasted)) {
				// special case: array or object literal being used as a destructuring pattern
				log(indentLevel, "ignored (array or object literal being used as a destructuring pattern)");
				destructureProcessor.typeDestructuringPattern(G, node, cache);

			} else {
				// standard case
				typeNode2(G, nodeCasted, cache, indentLevel);
			}
		} else {
			// not a typable node
			log(indentLevel, "ignored (not a typable node: "
					+ (node == null || node.eClass() == null ? null : node.eClass().getName()) + ")");
		}
	}

	/** Returns true iff the given expression is a poly expression */
	public boolean isPoly(Expression expr) {
		return polyProcessor.isPoly(expr);
	}

	/**
	 * Infers type of given AST node and stores the result in the given cache.
	 * <p>
	 * More precisely:
	 * <ol>
	 * <li>if given node is part of a poly expression:
	 * <ol>
	 * <li>if given node is the root of a tree of nested poly expressions (including the case that node is a poly
	 * expression without any nested poly expressions):<br>
	 * --> inference of entire tree of nested poly expressions AND storage of all results in cache is delegated to class
	 * {@link PolyProcessor}.
	 * <li>otherwise:<br>
	 * --> ignore this node ({@code PolyProcessor} will deal with it when processing the parent poly expression)
	 * </ol>
	 * <li>otherwise (standard case):<br>
	 * --> infer type of node by asking the TypeJudgment and store the result in the given cache.
	 * </ol>
	 */
	private void typeNode2(RuleEnvironment G, TypableElement node, ASTMetaInfoCache cache, int indentLevel) {
		try {
			if (polyProcessor.isResponsibleFor(node)) {
				if (polyProcessor.isEntryPoint(node)) {
					log(indentLevel, "asking PolyComputer ...");
					polyProcessor.inferType(G, (Expression) node, cache);
					// in this case, the polyComputer will store the type in the cache;
					// also, the poly computer is responsible for replacing all DeferredTypeRefs
					assertTrueIfRigid(cache, "poly computer did not replace DeferredTypeRef", () -> {
						EObject typeModelElem = getDefinedTypeModelElement(node);
						return typeModelElem == null
								|| isEmpty(filter(typeModelElem.eAllContents(), DeferredTypeRef.class));
					});
				} else {
					// we have a poly expression, but one that is nested in another poly expression
					// -> ignore here, because polyProcessor will deal with it when processing the parent poly
					// expression
					log(indentLevel,
							"deferred (nested in poly expression --> will be inferred during inference of outer poly expression)");

					cache.nonEntryPolyProcessorNodes.add(node);

					return; // return only required to avoid confusing logging of cache.getFailSafe(node) below
				}
			} else {
				// ordinary typing of typable AST nodes by asking the TypeJudgment
				log(indentLevel, "asking Xsemantics ...");
				TypeRef result = invokeTypeJudgmentToInferType(G, node);

				TypeRef resultAdjusted = adjustResultForLocationInAST(G, result, node);

				// in this case, we are responsible for storing the type in the cache
				// (Xsemantics does not know of the cache)
				checkCanceled(G);
				cache.storeType(node, resultAdjusted);
			}
		} catch (Throwable th) {
			operationCanceledManager.propagateIfCancelException(th);
			logException("exception while obtaining type from type system: " + th.getMessage(), th);
			th.printStackTrace();
			cache.storeType(node, TypeRefsFactory.eINSTANCE.createUnknownTypeRef());
		}

		log(indentLevel, cache.getTypeFailSafe(node));
	}

	private <T extends TypeRef> T adjustResultForLocationInAST(RuleEnvironment G, T typeRef, TypableElement astNode) {
		var result = typeRef;
		result = adjustForIndexSignatures(result, astNode);
		result = adjustForLocationDependentSpecialProperties(G, result, astNode);
		return result;
	}

	/**
	 * Poor man's support for index signatures (intended only for .d.ts but must also be checked in N4JS, because N4JS
	 * may contain classifiers that extend a classifier from .d.ts containing an index signature).
	 * <p>
	 * TODO IDE-3620 remove this method once index signatures are properly supported
	 */
	@SuppressWarnings("unchecked")
	private <T extends TypeRef> T adjustForIndexSignatures(T typeRef, TypableElement astNode) {
		EObject parent = astNode == null ? null : astNode.eContainer();
		if (parent instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) parent;
			if (astNode == ppae.getTarget() && !typeRef.isDynamic()) {
				if (typeRef instanceof ParameterizedTypeRef) {
					if (N4JSLanguageUtils.hasIndexSignature(typeRef)) {
						ParameterizedTypeRef typeRefCpy = TypeUtils.copy((ParameterizedTypeRef) typeRef);
						typeRefCpy.setDynamic(true);
						return (T) typeRefCpy;
					}
				}
			}
		}
		return typeRef;
	}

	/**
	 * Make sure that the value of the two location-dependent special properties <code>typeOfObjectLiteral</code> and
	 * <code>typeOfNewExpressionOrFinalNominal</code> in {@link ParameterizedTypeRef} correctly reflect the current
	 * location in the AST, i.e. the the location of the given <code>astNode</code>, no matter where the type reference
	 * in the given <code>result</code> stems from.
	 */
	@SuppressWarnings("unchecked")
	private <T extends TypeRef> T adjustForLocationDependentSpecialProperties(RuleEnvironment G, T result,
			TypableElement astNode) {
		T typeRef = result;
		if (typeRef instanceof ParameterizedTypeRef) {
			TypableElement astNodeSkipParen = N4JSASTUtils.skipParenExpressionDownward(astNode);
			OptionalFieldStrategy optionalFieldStrategy = N4JSLanguageUtils.calculateOptionalFieldStrategy(ts, G,
					astNodeSkipParen, typeRef);
			if (typeRef.getASTNodeOptionalFieldStrategy() != optionalFieldStrategy) {
				ParameterizedTypeRef typeRefCpy = TypeUtils.copy((ParameterizedTypeRef) typeRef);
				// TODO: also remember the cause for opt-field-strat!
				typeRefCpy.setASTNodeOptionalFieldStrategy(optionalFieldStrategy);
				return (T) typeRefCpy;
			}
		}
		return result;
	}

	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * This is the single, central method for obtaining the type of a typable element (AST node or TModule element).
	 * <b>It should never be invoked directly by client code!</b> Instead, client code should always call
	 * {@link N4JSTypeSystem#type(RuleEnvironment,TypableElement) N4JSTypeSystem#type()}.
	 * <p>
	 * The behavior of this method depends on the state the containing {@link N4JSResource} is in:
	 * <ul>
	 * <li>before post-processing has started:<br>
	 * -> simply initiate post-processing; once it's finished, return type from AST meta-info cache.
	 * <li>during post-processing:
	 * <ul>
	 * <li>in case of a backward reference:<br>
	 * -> simply return type from AST meta-info cache.
	 * <li>in case of a forward reference:<br>
	 * -> trigger forward-processing of the identifiable subtree below the given typable element, see
	 * {@link #getTypeOfForwardReference(RuleEnvironment,TypableElement,ASTMetaInfoCache) #getTypeOfForwardReference()},
	 * which delegates to
	 * {@link ASTProcessor#processSubtree_forwardReference( RuleEnvironment,TypableElement,ASTMetaInfoCache)
	 * ASTProcessor#processSubtree_forwardReference()}.
	 * </ul>
	 * <li>after post-processing has completed:<br>
	 * -> simply return type from AST meta-info cache.
	 * </ul>
	 * This overview is simplified, check the code for precise rules!
	 * <p>
	 * Only a single method delegates here (no one else should call this method):
	 * <ol>
	 * <li>{@link N4JSTypeSystem#type(RuleEnvironment,TypableElement)}
	 * </ol>
	 */
	public TypeRef getType(RuleEnvironment G, TypableElement objRaw) {

		if (objRaw == null) {
			// failing safely here; otherwise we would need preemptive null-checks wherever type inference is applied
			return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
		}

		TypableElement obj = objRaw;
		if (objRaw.eIsProxy()) {
			ResourceSet resSet = RuleEnvironmentExtensions.getContextResource(G).getResourceSet();
			obj = (TypableElement) EcoreUtil.resolve(objRaw, resSet);
		}

		Resource res = obj.eResource();
		if (res instanceof N4JSResource) {
			N4JSResource n4Res = (N4JSResource) res;

			if (n4Res.isFullyProcessed() && n4Res.getScript().eIsProxy()) {
				// special case: this is a resource loaded from the index!
				// -> for now, we entirely by-pass ASTMetaInfoCache and just directly wrap the type model element in a
				// TypeRef
				if (!isTypeModelElement(obj)) {
					throw new IllegalStateException("not a type model element: " + obj);
				}
				return invokeTypeJudgmentToInferType(G, obj); // obj is a type model element, so this will just wrap it
																// in a TypeRef (no actual inference)
			}

			// make sure post-processing on the containing N4JS resource is initiated
			n4Res.performPostProcessing(getCancelIndicator(G));

			// NOTE: at this point, we know: if *before* the above call to #performPostProcessing() ...
			// a) post-processing of 'res' had not been started yet: it will now be *completed*
			// b) post-processing of 'res' was in progress: it will now still be *in progress*
			// c) post-processing of 'res' was completed: it will now still be *completed*
			// See API doc of method PostProcessingAwareResource#performPostProcessing(CancelIndicator).

			// if post-processing is in progress AND 'obj' is a type model element AND it corresponds to an AST node
			// --> redirect processing to the AST node, in order to properly handle backward/forward references, esp.
			// forward processing of identifiable subtrees
			if (n4Res.isPostProcessing() && isTypeModelElement(obj)) {
				EObject astNodeToProcess = (obj instanceof SyntaxRelatedTElement)
						// NOTE: we've made sure above that we are *NOT* in a Resource loaded from the index!
						? ((SyntaxRelatedTElement) obj).getAstElement()
						: null;
				boolean isImplicitArgumentsVariable = (astNodeToProcess instanceof FunctionOrFieldAccessor)
						? ((FunctionOrFieldAccessor) astNodeToProcess).getImplicitArgumentsVariable() == obj
						: false;
				if (!isImplicitArgumentsVariable) {
					if (astNodeToProcess instanceof TypableElement) {
						// proceed with the corresponding AST node instead of the type model element
						obj = (TypableElement) astNodeToProcess;
					}
				}
			}

			// obtain type of 'obj'
			return getTypeInN4JSResource(G, n4Res, obj);

		} else {

			// obj not contained in an N4JSResource -> fall back to default behavior
			// can happen for:
			// - objects that are not contained in a Resource
			// - objects that are contained in a Resource but not an N4JSResource
			return invokeTypeJudgmentToInferType(G, obj);
		}
	}

	/** See {@link TypeProcessor#getType(RuleEnvironment, TypableElement)}. */
	private TypeRef getTypeInN4JSResource(RuleEnvironment G, N4JSResource res, TypableElement obj) {
		// obtain type of 'obj' depending on whether it's an AST node or type model element AND depending on current
		// load state of containing N4JS resource
		if (isTypeModelElement(obj)) {
			// for type model elements, we by-pass all caching ...
			return invokeTypeJudgmentToInferType(G, obj); // obj is a type model element, so this will just wrap it in a
															// TypeRef (no actual inference)
		} else if (isASTNode(obj) && isTypableNode(obj)) {
			// here we read from the cache (if AST node 'obj' was already processed) or forward-process 'obj'
			ASTMetaInfoCache cache = res.getASTMetaInfoCacheVerifyContext();
			if (!res.isPostProcessing() && !res.isFullyProcessed()) {
				// we have called #performPostProcessing() on the containing resource above, so this is "impossible"
				throw new IllegalStateException(
						"post-processing neither in progress nor completed after calling #performPostProcessing() in resource: "
								+ res.getURI());
			} else if (!cache.isPostProcessing() && !cache.isFullyProcessed()) {
				// "res.isProcessing() || res.isFullyProcessed()" but not "cache.isProcessing || cache.isFullyProcessed"
				// so: the post-processing flags are out of sync between the resource and cache
				// (HINT: if you get an exception here, this often indicates an accidental cache clear; use the
				// debug code in ASTMetaInfoCacheHelper to track creation/deletion of typing caches to investigate this)
				IllegalStateException e = new IllegalStateException(
						"post-processing flags out of sync between resource and cache (hint: this is often caused by an accidental cache clear!!)");
				e.printStackTrace(); // make sure we see this on the console (some clients eat up all exceptions!)
				throw e;
			} else if (cache.isPostProcessing()) {

				// while AST typing is in progress, just read from the cache we are currently filling
				TypeRef resultFromCache = cache.getTypeFailSafe(obj);

				if (resultFromCache == null) {
					// cache does not contain type for 'obj' (i.e. not processed yet)
					// -> we have a forward reference!
					log(0, "***** forward reference to: " + obj);

					return getTypeOfForwardReference(G, obj, cache);
				} else {
					// cache contains a type for 'obj' (i.e. it was already processed)
					// -> simply read from cache
					return resultFromCache;
				}
			} else if (cache.isFullyProcessed()) {
				return cache.getType(G, obj); // will throw exception in case of cache miss
			}
			// should not happen
			return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
		} else {
			// a non-typable AST node OR some entity in the TModule for which obj.isTypeModelElement returns false
			return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
		}
	}

	/** See {@link TypeProcessor#getType(RuleEnvironment, TypableElement)}. */
	private TypeRef getTypeOfForwardReference(RuleEnvironment G, TypableElement node, ASTMetaInfoCache cache) {
		assertTrueIfRigid(cache, "argument 'node' must be an AST node", isASTNode(node));

		// TODO improve handling of destructuring patterns in ASTProcessor/TypeProcessor
		// (this is a temporary hack to avoid many illegal forward references within destructuring patterns)
		if (destructureProcessor.isForwardReferenceWhileTypingDestructuringPattern(node)) {
			return destructureProcessor.handleForwardReferenceWhileTypingDestructuringPattern(G, node, cache);
		}

		boolean isLegal = astProcessor.processSubtree_forwardReference(G, node, cache);
		if (isLegal) {
			boolean isCyclicForwardReference = cache.astNodesCurrentlyBeingTyped.contains(node);
			if (isCyclicForwardReference) {
				// in case of a legal cyclic forward reference, we cannot obtain the type of 'node' in the usual
				// way by fully processing 'node' and its subtree, so we have to "guess" a type
				if (node instanceof VariableDeclaration || node instanceof N4FieldDeclaration ||
						node instanceof PropertyNameValuePair) {

					Expression expr = getExpressionOfVFP(node);
					if (expr instanceof N4ClassExpression) {
						return invokeTypeJudgmentToInferType(G, expr);
					}
					if (expr instanceof NewExpression) {
						Expression callee = ((NewExpression) expr).getCallee();
						if (callee instanceof N4ClassExpression) {
							TypeRef calleeType = invokeTypeJudgmentToInferType(G, callee);
							Type calleeTypeStaticType = tsh.getStaticType(G, (TypeTypeRef) calleeType);
							return TypeUtils.createTypeRef(calleeTypeStaticType);
						}
					}
					TypeRef declTypeRef = getDeclaredTypeRefOfVFP(node);
					return (declTypeRef != null) ? declTypeRef : anyTypeRef(G);
				} else if (node instanceof FieldAccessor) {
					TypeRef declTypeRef = ((FieldAccessor) node).getDeclaredTypeRef();
					return (declTypeRef != null) ? declTypeRef : anyTypeRef(G);
				} else if (node instanceof TypeDefiningElement) {
					return wrapTypeInTypeRef(G, ((TypeDefiningElement) node).getDefinedType());
				} else if (node instanceof Expression && node.eContainer() instanceof YieldExpression) {
					return invokeTypeJudgmentToInferType(G, node);
				} else {
					String msg = "handling of a legal case of cyclic forward references missing in TypeProcessor";
					logErr(msg);
					IllegalStateException e = new IllegalStateException(msg);
					e.printStackTrace();
					return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
				}
			} else if (astProcessor.isSemiCyclicForwardReferenceInForLoop(node, cache)) {
				// semi-cyclic forward reference to a variable declaration in a for in/of loop:
				// -> similar to cyclic variable declarations, we have to "guess" a type.
				TypeReferenceNode<TypeRef> declTypeRefNode = ((VariableDeclaration) node).getDeclaredTypeRefNode();
				TypeRef declTypeRefInAST = declTypeRefNode == null ? null : declTypeRefNode.getTypeRefInAST();
				if (declTypeRefInAST != null && declTypeRefNode != null) {
					if (declTypeRefNode.getCachedProcessedTypeRef() != null) {
						return declTypeRefNode.getCachedProcessedTypeRef();
					} else {
						return tsh.resolveTypeAliases(G, declTypeRefInAST);
					}
				}

				return anyTypeRef(G);
			} else {
				// in case of a legal, *non*-cyclic forward reference, we can assume that the subtree below 'node'
				// has now been processed, which means node's type is now in the typing cache
				return cache.getType(G, node);
			}
		} else {
			String msg = "*#*#*#*#*#* ILLEGAL FORWARD REFERENCE to " + node
					+ " in " + (node.eResource() == null ? null : node.eResource().getURI());
			logErr(msg);
			return TypeRefsFactory.eINSTANCE.createUnknownTypeRef();
		}
	}

	// ---------------------------------------------------------------------------------------------------------------

	private static Expression getExpressionOfVFP(EObject vfp) {
		if (vfp instanceof VariableDeclaration) {
			return ((VariableDeclaration) vfp).getExpression();
		} else if (vfp instanceof N4FieldDeclaration) {
			return ((N4FieldDeclaration) vfp).getExpression();
		} else if (vfp instanceof PropertyNameValuePair) {
			return ((PropertyNameValuePair) vfp).getExpression();
		}
		return null;
	}

	private static TypeRef getDeclaredTypeRefOfVFP(EObject vfp) {
		if (vfp instanceof VariableDeclaration) {
			return ((VariableDeclaration) vfp).getDeclaredTypeRef();
		} else if (vfp instanceof N4FieldDeclaration) {
			return ((N4FieldDeclaration) vfp).getDeclaredTypeRef();
		} else if (vfp instanceof PropertyNameValuePair) {
			return ((PropertyNameValuePair) vfp).getDeclaredTypeRef();
		}
		return null;
	}
}
