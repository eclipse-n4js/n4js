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

import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.addCancelIndicator;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.getCancelIndicator;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.isCanceled;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.newRuleEnvironment;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isASTNode;
import static org.eclipse.n4js.utils.N4JSLanguageUtils.isIdentifiableSubtree;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filterNull;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toIterable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.Annotation;
import org.eclipse.n4js.n4JS.CatchBlock;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration;
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertySetterDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.SetterDeclaration;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.utils.EcoreUtilN4;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.StaticPolyfillHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Main processor used during {@link N4JSPostProcessor post-processing} of N4JS resources. It controls the overall work
 * flow of processing the AST, but does not do any actual work; instead, it delegates to the other processors:
 * <ul>
 * <li>{@link TypeProcessor}, which delegates further to
 * <ul>
 * <li>{@link PolyProcessor}, which delegates further to
 * <ul>
 * <li>{@link PolyProcessor_ArrayLiteral}
 * <li>{@link PolyProcessor_ObjectLiteral}
 * <li>{@link PolyProcessor_FunctionExpression}
 * <li>{@link PolyProcessor_CallExpression}
 * </ul>
 * <li>{@link DestructureProcessor}
 * </ul>
 * <li>{@code TypeExpectedProcessor} (coming soon!)
 * <li>{@link TypeDeferredProcessor}
 * </ul>
 */
@Singleton
public class ASTProcessor extends AbstractProcessor {

	@Inject
	private ComputedNameProcessor computedNameProcessor;
	@Inject
	private TypeProcessor typeProcessor;
	@Inject
	private TypeRefProcessor typeRefProcessor;
	@Inject
	private TypeDeferredProcessor typeDeferredProcessor;
	@Inject
	private CompileTimeExpressionProcessor compileTimeExpressionProcessor;
	@Inject
	private RuntimeDependencyProcessor runtimeDependencyProcessor;
	@Inject
	private StaticPolyfillHelper staticPolyfillHelper;

	/**
	 * Entry point for processing of the entire AST of the given resource. Will throw IllegalStateException if called
	 * more than once per N4JSResource.
	 * <p>
	 * This method performs some preparatory tasks (e.g., creating an instance of {@link ASTMetaInfoCache}) and ensures
	 * consistency by tracking the 'isProcessing' state with try/finally; for actual processing, this method delegates
	 * to method {@link #processAST(RuleEnvironment, Script, ASTMetaInfoCache)}.
	 *
	 * @param resource
	 *            may not be null.
	 * @param cancelIndicator
	 *            may be null.
	 */
	public void processAST(N4JSResource resource, CancelIndicator cancelIndicator) {
		if (resource == null)
			throw new IllegalArgumentException("resource may not be null");

		// the following is required, because typing may have been initiated by resolution of a proxy
		// -> when traversing the AST, we will sooner or later try to resolve this same proxy, which would be
		// interpreted as a cyclic proxy resolution by method LazyLinkingResource#getEObject(String,Triple)
		resource.clearResolving();

		log(0, "### processing resource: " + resource.getURI());

		Script script = resource.getScript();
		// we're during post-processing, so cache should be available now
		ASTMetaInfoCache cache = resource.getASTMetaInfoCacheVerifyContext();
		RuleEnvironment G = newRuleEnvironment(resource);
		addCancelIndicator(G, cancelIndicator);
		try {
			processAST(G, script, cache);
		} finally {
			if (isCanceled(G)) {
				log(0, "CANCELED by cancelIndicator");
			}

			if (isDEBUG_LOG_RESULT()) {
				log(0, "### result for " + resource.getURI());
				log(4, resource.getScript(), cache);
			}
			log(0, "### done: " + resource.getURI());
		}
	}

	/**
	 * First method to actually perform processing of the AST. This method defines the various processing phases.
	 * <p>
	 * There exists a single "main phase" where 95% of processing happens (entry point for this main phase is method
	 * {@link #processSubtree(RuleEnvironment, EObject, ASTMetaInfoCache, int)}), plus a number of smaller phases before
	 * and after that where some special handling is performed.
	 */
	private void processAST(RuleEnvironment G, Script script, ASTMetaInfoCache cache) {
		// phase 0: process compile-time expressions & computed property names (order is important)
		for (Expression node : toIterable(filter(script.eAllContents(), Expression.class))) {
			compileTimeExpressionProcessor.evaluateCompileTimeExpression(G, node, cache);
		}
		for (LiteralOrComputedPropertyName node : toIterable(
				filter(script.eAllContents(), LiteralOrComputedPropertyName.class))) {
			computedNameProcessor.processComputedPropertyName(node, cache);
		}

		// phase 1: main processing
		processSubtree(G, script, cache, 0);
		// phase 2: processing of postponed subtrees
		EObject eObj;
		while ((eObj = cache.postponedSubTrees.poll()) != null) {
			// note: we need to allow adding more postponed subtrees inside this loop!
			processSubtree(G, eObj, cache, 0);
		}
		// phase 3: store runtime and load-time dependencies in TModule
		runtimeDependencyProcessor.storeDirectRuntimeDependenciesInTModule(script, cache);
	}

	/**
	 * Process given node and all of its direct and indirect children.
	 *
	 * @param node
	 *            the root of the subtree to process; must be an AST node.
	 */
	void processSubtree(RuleEnvironment G, EObject node, ASTMetaInfoCache cache, int indentLevel) {
		assertTrueIfRigid(cache, "argument 'node' must be an AST node", isASTNode(node));

		log(indentLevel, "processing: " + getObjectInfo(node));

		checkCanceled(G);

		// already done as part of a forward processing?
		if (cache.forwardProcessedSubTrees.contains(node)) {
			if (isDEBUG_LOG()) {
				log(indentLevel, "(subtree already processed as a forward reference)");
				if (node instanceof TypableElement) {
					log(indentLevel, cache.getTypeFailSafe((TypableElement) node));
				}
			}
			return;
		}
		if (cache.postponedSubTrees.contains(node)) {
			// in case this happens, you can either:
			// * not postpone this node, or
			// * handle the postponed node later (not as part of a forward reference)
			throw new IllegalStateException("eager processing of postponed subtree");
		}

		if (!cache.astNodesCurrentlyBeingTyped.add(node)) {
			// this subtree is currently being processed
			// (can happen, for example, if we are processing a member (e.g. field) and during that processing we
			// encounter a reference to the containing class (e.g. in the initializer expression))
			if (isDEBUG_LOG()) {
				log(indentLevel, "(subtree currently in progress - skipping)");
			}
			return;
		}

		try {
			// process node itself - part 1 (before child processing)
			processNode_preChildren(G, node, cache);

			// process the children
			List<EObject> children = childrenToBeProcessed(node);
			for (EObject child : children) {
				if (isPostponedNode(child)) {
					// postpone
					cache.postponedSubTrees.add(child);
				} else {
					// process now
					processSubtree(G, child, cache, indentLevel + 1);
					checkCanceled(G);
				}
			}

			// process node itself - part 2 (after child processing)
			processNode_postChildren(G, node, cache, indentLevel);

			// we're done with this node, but make sure that all proxies have actually been resolved
			// (this is important mainly for two reasons: (1) post-processing is often triggered by a call to
			// N4JSResource#resolveLazyCrossReferences(CancelIndicator), so we have to guarantee that all lazy
			// cross-references are actually resolved; (2) the type system may not resolve all proxies and some
			// nodes are not typed at all (i.e. isTypableNode() returns false), so we have to enforce this here.

			// We also perform all processing, related to outgoing references from the current node at this point.
			resolveAndProcessReferencesInNode(node, cache);

		} finally {
			cache.astNodesCurrentlyBeingTyped.remove(node);
		}
	}

	private boolean isPostponedNode(EObject node) {
		return isPostponedInitializer(node)
				|| N4JSASTUtils.isBodyOfFunctionOrFieldAccessor(node);
	}

	/**
	 * Initializers are postponed iff:
	 * <ul>
	 * <li>Node is an initializer of a FormalParameter p,</li>
	 * <li>and p is part of a Poly FunctionExpression f,</li>
	 * <li>and p contains references to other FormalParameters of f, or f itself.</li>
	 * </ul>
	 */
	private boolean isPostponedInitializer(EObject node) {
		boolean isPostponedInitializer = false;
		EObject fp = node.eContainer();
		if (fp instanceof FormalParameter) {
			FormalParameter fpar = (FormalParameter) fp;
			if (node instanceof Expression) {
				if (fpar.isHasInitializerAssignment()) {
					EObject funDefObj = fpar.eContainer();
					// IdentifierRef in Initializers can cause cyclic dependencies
					if (funDefObj instanceof FunctionExpression) {
						FunctionExpression funDef = (FunctionExpression) funDefObj;
						// Check if the initializer refers to other fpars
						EList<FormalParameter> allFPars = funDef.getFpars();
						List<IdentifierRef> allRefs = EcoreUtilN4.getAllContentsOfTypeStopAt(fpar, IdentifierRef.class,
								N4JSPackage.Literals.FUNCTION_OR_FIELD_ACCESSOR__BODY);

						for (IdentifierRef ir : allRefs) {
							Object id = ir.getId();
							if (id instanceof SyntaxRelatedTElement) {
								id = ((SyntaxRelatedTElement) id)
										.eGet(TypesPackage.eINSTANCE.getSyntaxRelatedTElement_AstElement(), false);
							}
							boolean idRefCausesCyclDep = allFPars.contains(id) // f(p, q=p) {}
									|| id instanceof VariableDeclaration
											&& ((VariableDeclaration) id).getExpression() == funDef; // f(p, q=f(1)) {}
							if (idRefCausesCyclDep) {
								isPostponedInitializer = true;
							}
						}
					}
					// In ObjectLiterals, the ThisLiteral in Initializers can cause cyclic dependencies
					// TODO GH-1337 add support for spread operator
					boolean thisLiteralCausesCyclDep =
							// let o = { a:1, f(p=this.a) {} }
							funDefObj instanceof PropertyMethodDeclaration
									||
									// let o = {a:2, f: function(p=this.a) {}}
									funDefObj instanceof FunctionExpression
											&& funDefObj.eContainer() instanceof PropertyNameValuePair;

					if (thisLiteralCausesCyclDep) {
						boolean containsThisLiteral = EcoreUtilN4.containsContentsOfTypeStopAt(fpar, ThisLiteral.class,
								N4JSPackage.Literals.FUNCTION_OR_FIELD_ACCESSOR__BODY);
						if (containsThisLiteral) {
							isPostponedInitializer = true;
						}
					}
					// If this check is not sufficient, we have to add more checks here. Note: Setters never have
					// initializers.
				}
			}
		}
		return isPostponedInitializer;
	}

	/**
	 * Forward-process given node and all of its direct and indirect children.
	 * <p>
	 * Via this method, other processors can request a forward processing of some subtree. Does nothing if the given
	 * node was processed already, either as part of a forward reference or during normal processing.
	 *
	 * @return <code>true</code> iff the forward processing is legal, <code>false</code> otherwise.
	 */
	boolean processSubtree_forwardReference(RuleEnvironment G, TypableElement node, ASTMetaInfoCache cache) {
		assertTrueIfRigid(cache, "argument 'node' must be an AST node", isASTNode(node));

		// is node a valid target for a forward reference (i.e. an identifiable subtree)?
		boolean valid = isIdentifiableSubtree(node) || isExceptionCaseOfForwardReferencableSubtree(node);
		if (!valid) {
			XtextResource resource = (XtextResource) node.eResource();
			if (resource != null) {
				assertTrueIfRigid(cache,
						"forward reference only allowed to identifiable subtrees; but was: " + node + " in\n" +
								resource.getParseResult().getRootNode().getText(),
						valid);
			} else {
				assertTrueIfRigid(cache, "forward reference only allowed to identifiable subtrees; but was: " + node,
						valid);
			}
		}

		TypeRef fromCache = cache.getTypeFailSafe(node);
		if (fromCache != null) {
			// already processed, nothing else to do
			// note: this is not an error, we may have many forward references to the same identifiable subtree
			return true;
		}

		if (cache.astNodesCurrentlyBeingTyped.contains(node)) {
			// cyclic forward reference
			// legal cases of a cyclic reference
			// TODO GH-1337 add support for spread operator
			boolean isCyclicForwardReference = cache.astNodesCurrentlyBeingTyped.contains(node);
			if (isCyclicForwardReference && (node instanceof VariableDeclaration
					|| node instanceof N4ClassifierDeclaration
					|| node instanceof N4FieldDeclaration
					|| (node instanceof PropertyNameValuePair
							&& ((PropertyNameValuePair) node).getExpression() instanceof FunctionExpression)
					|| node instanceof PropertyGetterDeclaration || node instanceof PropertySetterDeclaration
					|| (node instanceof Expression && node.eContainer() instanceof YieldExpression))) {
				return true;
			}

			// illegal cyclic node inference
			String msg = "*#*#*#*#*#* illegal cyclic forward reference to " + getObjectInfo(node)
					+ " (resource: " + (node.eResource() == null ? "null" : node.eResource().getURI()) + ")";
			logErr(msg);
			return false;
		} else if (isSemiCyclicForwardReferenceInForLoop(node, cache)) {
			// semi-cyclic forward reference
			// (this is deemed legal for the same reason why 'var x = 1+x;' is treated as a legal forward reference)
			return true;
		}

		if (cache.forwardProcessedSubTrees.contains(node)) {
			// we saw above that the cache does not contain anything for node, so this is an error
			throw new IllegalStateException();
		}

		// actually perform the forward processing
		log(0, "==START of identifiable sub-tree below " + getObjectInfo(node));
		RuleEnvironment G_fresh = newRuleEnvironment(G); // use a new, empty environment here (but retain
															// cancelIndicator!)
		processSubtree(G_fresh, node, cache, 0); // note how we reset the indent level
		cache.forwardProcessedSubTrees.add(node);
		log(0, "==END of identifiable sub-tree below " + getObjectInfo(node));

		return true;
	}

	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * Top-down processing of AST nodes happens here, i.e. this method will see all AST nodes in a top-down order.
	 */
	private void processNode_preChildren(RuleEnvironment G, EObject node, ASTMetaInfoCache cache) {
		typeRefProcessor.handleTypeRefs(G, node, cache);

		if (node instanceof FunctionDefinition) {
			handleAsyncOrGeneratorFunctionDefinition(G, (FunctionDefinition) node);
		}

		typeDeferredProcessor.handleDeferredTypeRefs_preChildren(G, node, cache);
	}

	/**
	 * Bottom-up processing of AST nodes happens here, i.e. this method will see all AST nodes in a bottom-up order.
	 */
	private void processNode_postChildren(RuleEnvironment G, EObject node, ASTMetaInfoCache cache, int indentLevel) {

		typeDeferredProcessor.handleDeferredTypeRefs_postChildren(G, node, cache);

		typeProcessor.typeNode(G, node, cache, indentLevel);
		/*
		 * references to other files via import statements NOTE: for all imports except bare imports, the following is
		 * unnecessary, because post-processing of the target resource would be triggered automatically as soon as type
		 * inference is performed on an element imported from the target resource. However, by doing this eagerly up
		 * front, the overall flow of post-processing across multiple resources is a bit easier to understand/predict.
		 * This does not lead to any additional processing being done (it's just done a bit earlier), except in case of
		 * unused imports.
		 */
		if (node instanceof ImportDeclaration) {
			TModule targetModule = ((ImportDeclaration) node).getModule();
			if (targetModule != null && !targetModule.eIsProxy()) {
				Resource targetResource = targetModule.eResource();
				if (targetResource instanceof N4JSResource) {
					// trigger post-processing of target resource
					((N4JSResource) targetResource).performPostProcessing(getCancelIndicator(G));
				}
			}
		}

		if (node instanceof Annotation) {
			if (Objects.equals(((Annotation) node).getName(), AnnotationDefinition.STATIC_POLYFILL_AWARE.name)) {
				N4JSResource resSPoly = staticPolyfillHelper.getStaticPolyfillResource(node.eResource());
				if (resSPoly != null) {
					// trigger post-processing of poly filler
					resSPoly.performPostProcessing(getCancelIndicator(G));
				}
			}
		}

		runtimeDependencyProcessor.recordRuntimeReferencesInCache(node, cache);
	}

	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * This method returns the direct children of 'obj' that are to be processed, <em>in the order in which they are to
	 * be processed</em>. By default, all direct children must be processed and the order is insignificant, so in the
	 * default case this method simply returns {@link EObject#eContents()}. However, this method implements special
	 * handling for some exception cases where the processing order is significant.
	 */
	private List<EObject> childrenToBeProcessed(EObject obj) {
		if (obj instanceof SetterDeclaration) {
			// process formal parameter before body
			return bringToFront(obj.eContents(), ((SetterDeclaration) obj).getFpar());
		}
		if (obj instanceof FunctionDefinition) {
			// process formal parameters before body
			return bringToFront(obj.eContents(), ((FunctionDefinition) obj).getFpars().toArray(new EObject[0]));
		}
		if (obj instanceof CatchBlock) {
			// process catch variable before block
			return bringToFront(obj.eContents(), ((CatchBlock) obj).getCatchVariable());
		}
		if (obj instanceof ForStatement) {
			// process expression before varDeclOrBindings
			return bringToFront(obj.eContents(), ((ForStatement) obj).getExpression());
		}
		// standard case: order is insignificant (so we simply use the order provided by EMF)
		return obj.eContents();
	}

	// ---------------------------------------------------------------------------------------------------------------

	/**
	 * Normally, forward references are allowed only to {@link N4JSLanguageUtils#isIdentifiableSubtree(EObject)
	 * identifiable subtrees}. However, there are exception cases that are also allowed and this method returns
	 * <code>true</code> for those cases.
	 */
	private static boolean isExceptionCaseOfForwardReferencableSubtree(EObject astNode) {
		return isExpressionInForOf(astNode);
	}

	private static boolean isExpressionInForOf(EObject astNode) {
		return astNode instanceof Expression && astNode.eContainer() instanceof ForStatement
				&& ((ForStatement) astNode.eContainer()).isForOf()
				&& astNode.eContainingFeature() == N4JSPackage.eINSTANCE.getIterationStatement_Expression();
	}

	/**
	 * Returns true if we have a semi-cyclic reference to a variable declaration in a for in/of loop. For example:
	 *
	 * <pre>
	 * for(var x of foo(x)) {}
	 * </pre>
	 */
	boolean isSemiCyclicForwardReferenceInForLoop(EObject node, ASTMetaInfoCache cache) {
		if (node instanceof VariableDeclaration) {
			EObject parent = node.eContainer();
			if (parent instanceof ForStatement) {
				ForStatement fs = (ForStatement) parent;
				return (fs.isForIn() || fs.isForOf()) && cache.astNodesCurrentlyBeingTyped.contains(fs.getExpression());
			}
		}
		return false;
	}

	private void resolveAndProcessReferencesInNode(EObject astNode, ASTMetaInfoCache cache) {
		for (EReference eRef : astNode.eClass().getEAllReferences()) {
			if (!eRef.isContainment() && !eRef.isContainer()) { // only cross-references have proxies (in our case)
				Object node = astNode.eGet(eRef, true);

				if (node instanceof EObject) {
					recordReferencesToLocalVariables(eRef, astNode, (EObject) node, cache);
				}
			}
		}
	}

	private void recordReferencesToLocalVariables(EReference reference, EObject sourceNode, EObject target,
			ASTMetaInfoCache cache) {
		// skip reference Variable#definedVariable (it does not constitute a usage of the variable)
		if (reference == N4JSPackage.Literals.ABSTRACT_VARIABLE__DEFINED_VARIABLE) {
			return;
		}
		// If target is still a proxy its resolution failed, therefore it should be skipped.
		if (target.eIsProxy()) {
			return;
		}
		// skip non-local references
		if (sourceNode.eResource() != target.eResource()) {
			return;
		}
		if (target instanceof TVariable) {
			// don't record references to directly exported variables
			if (((TVariable) target).isDirectlyExported()) {
				return;
			}

			cache.storeLocalVariableReference((TVariable) target, sourceNode);
		}
	}

	private List<EObject> bringToFront(List<EObject> l, EObject... elements) {
		List<EObject> result = new ArrayList<>(l);
		List<? extends EObject> elemSanitized = toList(filterNull(Arrays.asList(elements)));
		result.removeAll(elemSanitized);
		result.addAll(0, elemSanitized);
		return result;
	}
}
