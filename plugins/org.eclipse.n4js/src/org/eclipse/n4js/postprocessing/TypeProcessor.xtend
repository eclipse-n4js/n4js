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
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.DestructureUtils
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FieldAccessor
import org.eclipse.n4js.n4JS.N4ClassExpression
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.NewExpression
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.YieldExpression
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.xtext.service.OperationCanceledManager

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * Processor for handling type inference during post-processing of an N4JS resource. Roughly corresponds to
 * 'type' judgment in Xsemantics, but handles also more complex cases, e.g. poly expressions.
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
	private TypeSystemHelper tsh;
	@Inject
	private OperationCanceledManager operationCanceledManager;


	/**
	 * If the given AST node is typable this method will infer its type and store the result in the given cache.
	 * <p>
	 * This method mainly checks if the given node is typable. Main processing is done in
	 * {@link #typeNode2(RuleEnvironment, TypableElement, ASTMetaInfoCache, int) typeNode2()}.
	 */
	def public void typeNode(RuleEnvironment G, EObject node, ASTMetaInfoCache cache, int indentLevel) {
		if (node.isTypableNode) {
			val nodeCasted = node as TypableElement; // because #isTypableNode() returned true
			// we have a typable node
			if (DestructureUtils.isArrayOrObjectLiteralUsedAsDestructuringPattern(node)
				&& polyProcessor.isEntryPoint(nodeCasted)) {
				// special case: array or object literal being used as a destructuring pattern
				log(indentLevel, "ignored (array or object literal being used as a destructuring pattern)")
				destructureProcessor.typeDestructuringPattern(G, node, cache, indentLevel);

			} else {
				// standard case
				typeNode2(G, nodeCasted, cache, indentLevel);
			}
		} else {
			// not a typable node
			log(indentLevel, "ignored (not a typable node: " + node?.eClass?.name + ")")
		}
	}

	/**
	 * Infers type of given AST node and stores the result in the given cache.
	 * <p>
	 * More precisely:
	 * <ol>
	 * <li>if given node is part of a poly expression:
	 *     <ol>
	 *     <li>if given node is the root of a tree of nested poly expressions (including the case that node is a poly
	 *         expression without any nested poly expressions):<br>
	 *         --> inference of entire tree of nested poly expressions AND storage of all results in cache is delegated
	 *             to class {@link PolyProcessor}.
	 *     <li>otherwise:<br>
	 *         --> ignore this node ({@code PolyProcessor} will deal with it when processing the parent poly expression)
	 *     </ol>
	 * <li>otherwise (standard case):<br>
	 *     --> infer type of node by asking the TypeJudgment and store the result in the given cache.
	 * </ol>
	 */
	def private void typeNode2(RuleEnvironment G, TypableElement node, ASTMetaInfoCache cache, int indentLevel) {
		try {
			if (polyProcessor.isResponsibleFor(node)) {
				if (polyProcessor.isEntryPoint(node)) {
					log(indentLevel, "asking PolyComputer ...");
					polyProcessor.inferType(G, node as Expression, cache);
					// in this case, the polyComputer will store the type in the cache;
					// also, the poly computer is responsible for replacing all DeferredTypeRefs
					assertTrueIfRigid(cache, "poly computer did not replace DeferredTypeRef", [
						val typeModelElem = node.definedTypeModelElement;
						return typeModelElem === null || typeModelElem.eAllContents.filter(DeferredTypeRef).empty
					]);
				} else {
					// we have a poly expression, but one that is nested in another poly expression
					// -> ignore here, because polyProcessor will deal with it when processing the parent poly expression
					log(indentLevel,
						"deferred (nested in poly expression --> will be inferred during inference of outer poly expression)");

					return; // return only required to avoid confusing logging of cache.getFailSafe(node) below
				}
			} else {
				// ordinary typing of typable AST nodes by asking the TypeJudgment
				log(indentLevel, "asking Xsemantics ...");
				val result = invokeTypeJudgmentToInferType(G, node);

				val resultAdjusted = adjustResultForLocationInAST(G, result, N4JSASTUtils.skipParenExpressionDownward(node));

				// in this case, we are responsible for storing the type in the cache
				// (Xsemantics does not know of the cache)
				checkCanceled(G);
				cache.storeType(node, resultAdjusted);
			}
		} catch (Throwable th) {
			operationCanceledManager.propagateIfCancelException(th);
			logException("exception while obtaining type from type system: " + th.message, th);
			th.printStackTrace
			cache.storeType(node, TypeRefsFactory.eINSTANCE.createUnknownTypeRef);
		}

		log(indentLevel, cache.getTypeFailSafe(node));
	}

	/**
	 * Make sure that the value of the two location-dependent special properties <code>typeOfObjectLiteral</code> and
	 * <code>typeOfNewExpressionOrFinalNominal</code> in {@link ParameterizedTypeRef} correctly reflect the current
	 * location in the AST, i.e. the the location of the given <code>astNode</code>, no matter where the type reference
	 * in the given <code>result</code> stems from.
	 * <p>
	 * For more details see {@link TypeRef#isTypeOfObjectLiteral()}.
	 */
	def private <T extends TypeRef> T adjustResultForLocationInAST(RuleEnvironment G, T result, TypableElement astNode) {
		val typeRef = result;
		if (typeRef instanceof ParameterizedTypeRef) {
			val optionalFieldStrategy = N4JSLanguageUtils.
				calculateOptionalFieldStrategy(astNode, typeRef);
			if (typeRef.ASTNodeOptionalFieldStrategy !== optionalFieldStrategy) {
				val typeRefCpy = TypeUtils.copy(typeRef);
				typeRefCpy.ASTNodeOptionalFieldStrategy = optionalFieldStrategy;
				return typeRefCpy;
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
	 *     -> simply initiate post-processing; once it's finished, return type from AST meta-info cache.
	 * <li>during post-processing:
	 *     <ul>
	 *     <li>in case of a backward reference:<br>
	 *         -> simply return type from AST meta-info cache.
	 *     <li>in case of a forward reference:<br>
	 *         -> trigger forward-processing of the identifiable subtree below the given typable element, see
	 *         {@link #getTypeOfForwardReference(RuleEnvironment,TypableElement,ASTMetaInfoCache) #getTypeOfForwardReference()},
	 *         which delegates to {@link ASTProcessor#processSubtree_forwardReference(
	 *         RuleEnvironment,TypableElement,ASTMetaInfoCache) ASTProcessor#processSubtree_forwardReference()}.
	 *     </ul>
	 * <li>after post-processing has completed:<br>
	 *     -> simply return type from AST meta-info cache.
	 * </ul>
	 * This overview is simplified, check the code for precise rules!
	 * <p>
	 * Only a single method delegates here (no one else should call this method):
	 * <ol>
	 * <li>{@link N4JSTypeSystem#type(RuleEnvironment,TypableElement)}
	 * </ol>
	 */
	def public TypeRef getType(RuleEnvironment G, TypableElement objRaw) {

		if (objRaw === null) {
			// failing safely here; otherwise we would need preemptive null-checks wherever type inference is applied
			return TypeRefsFactory.eINSTANCE.createUnknownTypeRef;
		}

		var obj = if (objRaw.eIsProxy) {
				val resSet = RuleEnvironmentExtensions.getContextResource(G).resourceSet;
				EcoreUtil.resolve(objRaw, resSet) as TypableElement
			} else {
				objRaw
			};

		val res = obj.eResource;
		if (res instanceof N4JSResource) {

			if (res.isFullyProcessed && res.script.eIsProxy) {
				// special case: this is a resource loaded from the index!
				// -> for now, we entirely by-pass ASTMetaInfoCache and just directly wrap the type model element in a TypeRef
				if (!obj.isTypeModelElement) {
					throw new IllegalStateException("not a type model element: " + obj)
				}
				return invokeTypeJudgmentToInferType(G, obj); // obj is a type model element, so this will just wrap it in a TypeRef (no actual inference)
			}

			// make sure post-processing on the containing N4JS resource is initiated
			res.performPostProcessing(G.cancelIndicator);

			// NOTE: at this point, we know: if *before* the above call to #performPostProcessing() ...
			// a) post-processing of 'res' had not been started yet: it will now be *completed*
			// b) post-processing of 'res' was in progress: it will now still be *in progress*
			// c) post-processing of 'res' was completed: it will now still be *completed*
			// See API doc of method PostProcessingAwareResource#performPostProcessing(CancelIndicator).

			// if post-processing is in progress AND 'obj' is a type model element AND it corresponds to an AST node
			// --> redirect processing to the AST node, in order to properly handle backward/forward references, esp.
			// forward processing of identifiable subtrees
			if(res.isPostProcessing && obj.isTypeModelElement) {
				val astNodeToProcess = if (obj instanceof SyntaxRelatedTElement) {
						obj.astElement // NOTE: we've made sure above that we are *NOT* in a Resource loaded from the index!
					};
				if (astNodeToProcess instanceof TypableElement) {
					// proceed with the corresponding AST node instead of the type model element
					obj = astNodeToProcess;
				}
			}

			// obtain type of 'obj'
			return getTypeInN4JSResource(G, res, obj);

		} else {

			// obj not contained in an N4JSResource -> fall back to default behavior
			// can happen for:
			// - objects that are not contained in a Resource
			// - objects that are contained in a Resource but not an N4JSResource
			return invokeTypeJudgmentToInferType(G, obj);
		}
	}

	/** See {@link TypeProcessor#getType(RuleEnvironment,RuleApplicationTrace,TypableElement)}. */
	def private TypeRef getTypeInN4JSResource(RuleEnvironment G, N4JSResource res, TypableElement obj) {
		// obtain type of 'obj' depending on whether it's an AST node or type model element AND depending on current
		// load state of containing N4JS resource
		if (obj.isTypeModelElement) {
			// for type model elements, we by-pass all caching ...
			return invokeTypeJudgmentToInferType(G, obj); // obj is a type model element, so this will just wrap it in a TypeRef (no actual inference)
		} else if (obj.isASTNode && obj.isTypableNode) {
			// here we read from the cache (if AST node 'obj' was already processed) or forward-process 'obj'
			val cache = res.getASTMetaInfoCacheVerifyContext();
			if (!res.isPostProcessing && !res.isFullyProcessed) {
				// we have called #performPostProcessing() on the containing resource above, so this is "impossible"
				throw new IllegalStateException("post-processing neither in progress nor completed after calling #performPostProcessing() in resource: " + res.URI);
			} else if (!cache.isPostProcessing && !cache.isFullyProcessed) {
				// "res.isProcessing() || res.isFullyProcessed()" but not "cache.isProcessing || cache.isFullyProcessed"
				// so: the post-processing flags are out of sync between the resource and cache
				// (HINT: if you get an exception here, this often indicates an accidental cache clear; use the
				// debug code in ASTMetaInfoCacheHelper to track creation/deletion of typing caches to investigate this)
				val e = new IllegalStateException("post-processing flags out of sync between resource and cache (hint: this is often caused by an accidental cache clear!!)");
				e.printStackTrace // make sure we see this on the console (some clients eat up all exceptions!)
				throw e;
			} else if (cache.isPostProcessing) {

				// while AST typing is in progress, just read from the cache we are currently filling
				val resultFromCache = cache.getTypeFailSafe(obj);

				if (resultFromCache === null) {
					// cache does not contain type for 'obj' (i.e. not processed yet)
					// -> we have a forward reference!
					log(0, "***** forward reference to: " + obj);

					return getTypeOfForwardReference(G, obj, cache);
				} else {
					// cache contains a type for 'obj' (i.e. it was already processed)
					// -> simply read from cache
					return resultFromCache;
				}
			} else if (cache.isFullyProcessed) {
				return cache.getType(obj); // will throw exception in case of cache miss
			}
		} else {
			// a non-typable AST node OR some entity in the TModule for which obj.isTypeModelElement returns false
			return TypeRefsFactory.eINSTANCE.createUnknownTypeRef;
		}
	}

	/** See {@link TypeProcessor#getType(RuleEnvironment,RuleApplicationTrace,TypableElement)}. */
	def private TypeRef getTypeOfForwardReference(RuleEnvironment G, TypableElement node, ASTMetaInfoCache cache) {
		assertTrueIfRigid(cache, "argument 'node' must be an AST node", node.isASTNode);

		// TODO improve handling of destructuring patterns in ASTProcessor/TypeProcessor
		// (this is a temporary hack to avoid many illegal forward references within destructuring patterns)
		if (destructureProcessor.isForwardReferenceWhileTypingDestructuringPattern(node)) {
			return destructureProcessor.handleForwardReferenceWhileTypingDestructuringPattern(G, node, cache);
		}

		val isLegal = astProcessor.processSubtree_forwardReference(G, node, cache);
		if (isLegal) {
			val isCyclicForwardReference = cache.astNodesCurrentlyBeingTyped.contains(node);
			if (isCyclicForwardReference) {
				// in case of a legal cyclic forward reference, we cannot obtain the type of 'node' in the usual
				// way by fully processing 'node' and its subtree, so we have to "guess" a type
				if (node instanceof VariableDeclaration || node instanceof N4FieldDeclaration ||
					node instanceof PropertyNameValuePair) {

					val expr = node.expressionOfVFP;
					if (expr instanceof N4ClassExpression) {
						return invokeTypeJudgmentToInferType(G, expr);
					}
					if (expr instanceof NewExpression) {
						val callee = expr.callee;
						if (callee instanceof N4ClassExpression) {
							val calleeType = invokeTypeJudgmentToInferType(G, callee);
							val calleeTypeStaticType = tsh.getStaticType(G, calleeType as TypeTypeRef);
							return TypeUtils.createTypeRef(calleeTypeStaticType);
						}
					}
					val declTypeRef = node.declaredTypeRefOfVFP;
					return if (declTypeRef !== null) {
						declTypeRef
					} else {
						G.anyTypeRef
					};
				} else if (node instanceof FieldAccessor) {
					val declTypeRef = node.declaredTypeRef;
					return if (declTypeRef !== null) {
						declTypeRef
					} else {
						G.anyTypeRef
					};
				} else if (node instanceof TypeDefiningElement) {
					return wrapTypeInTypeRef(G, node.definedType);
				} else if (node instanceof Expression && node.eContainer instanceof YieldExpression) {
					return invokeTypeJudgmentToInferType(G, node);
				} else {
					val msg = "handling of a legal case of cyclic forward references missing in TypeProcessor";
					logErr(msg);
					val e = new IllegalStateException(msg);
					e.printStackTrace;
					return TypeRefsFactory.eINSTANCE.createUnknownTypeRef;
				}
			} else if (astProcessor.isSemiCyclicForwardReferenceInForLoop(node, cache)) {
				// semi-cyclic forward reference to a variable declaration in a for in/of loop:
				// -> similar to cyclic variable declarations, we have to "guess" a type.
				val declTypeRefNode = (node as VariableDeclaration).declaredTypeRefNode;
				return if (declTypeRefNode !== null) {
					declTypeRefNode.cachedProcessedTypeRef ?: tsh.resolveTypeAliases(G, declTypeRefNode.typeRefInAST)
				} else {
					G.anyTypeRef
				};
			} else {
				// in case of a legal, *non*-cyclic forward reference, we can assume that the subtree below 'node'
				// has now been processed, which means node's type is now in the typing cache
				return cache.getType(node);
			}
		} else {
			val msg = "*#*#*#*#*#* ILLEGAL FORWARD REFERENCE to " + node + " in " + node.eResource?.URI;
			logErr(msg);
			return TypeRefsFactory.eINSTANCE.createUnknownTypeRef;
		}
	}


	// ---------------------------------------------------------------------------------------------------------------


	def private static Expression getExpressionOfVFP(EObject vfp) {
		switch (vfp) {
			VariableDeclaration:
				vfp.expression
			N4FieldDeclaration:
				vfp.expression
			PropertyNameValuePair:
				vfp.expression
		}
	}

	def private static TypeRef getDeclaredTypeRefOfVFP(EObject vfp) {
		switch (vfp) {
			VariableDeclaration:
				vfp.declaredTypeRef
			N4FieldDeclaration:
				vfp.declaredTypeRef
			PropertyNameValuePair:
				vfp.declaredTypeRef
		}
	}
}
