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
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.CatchBlock
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ForStatement
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.NamedImportSpecifier
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.PropertyGetterDeclaration
import org.eclipse.n4js.n4JS.PropertyMethodDeclaration
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.PropertySetterDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.SetterDeclaration
import org.eclipse.n4js.n4JS.ThisLiteral
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.YieldExpression
import org.eclipse.n4js.n4idl.versioning.MigrationUtils
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.types.TMigratable
import org.eclipse.n4js.ts.types.TMigration
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.utils.EcoreUtilN4
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.languages.N4LanguageUtils
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.util.CancelIndicator

import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * Main processor used during {@link N4JSPostProcessor post-processing} of N4JS resources. It controls the overall
 * work flow of processing the AST, but does not do any actual work; instead, it delegates to the other processors:
 * <ul>
 * <li>{@link TypeProcessor}, which delegates further to
 *     <ul>
 *     <li>{@link PolyProcessor}, which delegates further to
 *         <ul>
 *         <li>{@link PolyProcessor_ArrayLiteral}
 *         <li>{@link PolyProcessor_ObjectLiteral}
 *         <li>{@link PolyProcessor_FunctionExpression}
 *         <li>{@link PolyProcessor_CallExpression}
 *         </ul>
 *     <li>{@link DestructureProcessor}
 *     </ul>
 * <li>{@code TypeExpectedProcessor} (coming soon!)
 * <li>{@link TypeDeferredProcessor}
 * </ul>
 */
@Singleton
public class ASTProcessor extends AbstractProcessor {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private ComputedNameProcessor computedNameProcessor;
	@Inject
	private TypeProcessor typeProcessor;
	@Inject
	private TypeDeferredProcessor typeDeferredProcessor;
	@Inject
	private CompileTimeExpressionProcessor compileTimeExpressionProcessor;
	@Inject
	private JavaScriptVariantHelper variantHelper;

	/**
	 * Entry point for processing of the entire AST of the given resource.
	 * Will throw IllegalStateException if called more than once per N4JSResource.
	 * <p>
	 * This method performs some preparatory tasks (e.g., creating an instance of {@link ASTMetaInfoCache}) and ensures
	 * consistency by tracking the 'isProcessing' state with try/finally; for actual processing, this method delegates
	 * to method {@link #processAST(RuleEnvironment, Script, ASTMetaInfoCache)}.
	 *
	 * @param resource  may not be null.
	 * @param cancelIndicator  may be null.
	 */
	def public void processAST(N4JSResource resource, CancelIndicator cancelIndicator) {
		if (resource === null)
			throw new IllegalArgumentException("resource may not be null");

		// the following is required, because typing may have been initiated by resolution of a proxy
		// -> when traversing the AST, we will sooner or later try to resolve this same proxy, which would be
		// interpreted as a cyclic proxy resolution by method LazyLinkingResource#getEObject(String,Triple)
		resource.clearResolving();

		log(0, "### processing resource: " + resource.URI);

		val script = resource.script;
		val cache = resource.getASTMetaInfoCacheVerifyContext(); // we're during post-processing, so cache should be available now
		val G = resource.newRuleEnvironment;
		G.addCancelIndicator(cancelIndicator);
		try {
			processAST(G, script, cache, cancelIndicator);
		} finally {
			if (G.canceled) {
				log(0, "CANCELED by cancelIndicator");
			}

			if (isDEBUG_LOG_RESULT) {
				log(0, "### result for " + resource.URI);
				log(4, resource.script, cache);
			}
			log(0, "### done: " + resource.URI);
		}
	}

	/**
	 * First method to actually perform processing of the AST. This method defines the various processing phases.
	 * <p>
	 * There exists a single "main phase" where 95% of processing happens (entry point for this main phase is method
	 * {@link #processSubtree(RuleEnvironment, EObject, ASTMetaInfoCache, int)}), plus a number of smaller phases before
	 * and after that where some special handling is performed.
	 *
	 * @param resource  may not be null.
	 * @param cancelIndicator  may be null.
	 */
	def private void processAST(RuleEnvironment G, Script script, ASTMetaInfoCache cache, CancelIndicator cancelIndicator) {
		// phase 0: process compile-time expressions & computed property names (order is important)
		for(node : script.eAllContents.filter(Expression).toIterable) {
			compileTimeExpressionProcessor.evaluateCompileTimeExpression(G, node, cache, 0);
		}
		for(node : script.eAllContents.filter(LiteralOrComputedPropertyName).toIterable) {
			computedNameProcessor.processComputedPropertyName(G, node, cache, 0);
		}
		cache.flowInfo.createGraphs(script, [cancelIndicator.isCanceled]);
		cache.flowInfo.performForwardAnalysis([cancelIndicator.isCanceled]);

		// phase 1: main processing
		processSubtree(G, script, cache, 0);
		// phase 2: processing of postponed subtrees
		var EObject eObj;
		while ((eObj = cache.postponedSubTrees.poll) !== null) {
			// note: we need to allow adding more postponed subtrees inside this loop!
			processSubtree(G, eObj, cache, 0);
		}
		// phase 3: processing of LocalArgumentsVariable
		// (a LocalArgumentsVariable may be created on demand at any time, which means new AST nodes may appear
		// while processing the AST (see {@link FunctionOrFieldAccessor#getLocalArgumentsVariable()}); to support
		// these cases, we will now look for and process these newly created AST nodes:
		for (potentialContainer : cache.potentialContainersOfLocalArgumentsVariable) {
			val lav = potentialContainer._lok; // obtain the LocalArgumentsVariable without(!) triggering its on-demand creation
			if (lav!==null) {
				if (cache.getTypeFailSafe(lav)===null) { // only if not processed yet
					processSubtree(G, lav, cache, 0);
				}
			}
		}
	}

	/**
	 * Process given node and all of its direct and indirect children.
	 *
	 * @param node  the root of the subtree to process; must be an AST node.
	 */
	def package void processSubtree(RuleEnvironment G, EObject node, ASTMetaInfoCache cache, int indentLevel) {
		assertTrueIfRigid(cache, "argument 'node' must be an AST node", node.isASTNode);

		log(indentLevel, "processing: " + node.objectInfo);

		checkCanceled(G);

		// already done as part of a forward processing?
		if (cache.forwardProcessedSubTrees.contains(node)) {
			if (isDEBUG_LOG) {
				log(indentLevel, "(subtree already processed as a forward reference)");
				if(node instanceof TypableElement) {
					log(indentLevel, cache.getTypeFailSafe(node));
				}
			}
			return;
		}
		if (cache.postponedSubTrees.contains(node)) {
			// in case this happens, you can either:
			//  * not postpone this node, or
			//  * handle the postponed node later (not as part of a forward reference)
			throw new IllegalStateException("eager processing of postponed subtree");
		}

		if (!cache.astNodesCurrentlyBeingTyped.add(node)) {
			// this subtree is currently being processed
			// (can happen, for example, if we are processing a member (e.g. field) and during that processing we
			// encounter a reference to the containing class (e.g. in the initializer expression))
			if (isDEBUG_LOG) {
				log(indentLevel, "(subtree currently in progress - skipping)");
			}
			return;
		}

		try {
			// process node itself - part 1 (before child processing)
			processNode_preChildren(G, node, cache, indentLevel);

			// process the children
			val children = childrenToBeProcessed(G, node);
			for (child : children) {
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

	def private boolean isPostponedNode(EObject node) {
		return isPostponedInitializer(node)
		||	N4JSASTUtils.isBodyOfFunctionOrFieldAccessor(node);
	}

	/**
	 * Initializers are postponed iff:
	 * <ul>
	 * <li>Node is an initializer of a FormalParameter p,</li>
	 * <li>and p is part of a Poly FunctionExpression f,</li>
	 * <li>and p contains references to other FormalParameters of f, or f itself.</li>
	 * </ul>
	 */
	def private boolean isPostponedInitializer(EObject node) {
		var boolean isPostponedInitializer = false;
		val fpar = node.eContainer;
		if (fpar instanceof FormalParameter) {
			if (node instanceof Expression) {
				if (fpar.hasInitializerAssignment) {
					val funDef = fpar.eContainer;
					// IdentifierRef in Initializers can cause cyclic dependencies
					if (funDef instanceof FunctionExpression) {
						// Check if the initializer refers to other fpars
						val allFPars = funDef.fpars;
						val allRefs = EcoreUtilN4.getAllContentsOfTypeStopAt(fpar, IdentifierRef, N4JSPackage.Literals.FUNCTION_OR_FIELD_ACCESSOR__BODY);

						for (IdentifierRef ir : allRefs) {
							val id = ir.getId();
							val idRefCausesCyclDep =
								allFPars.contains(id) // f(p, q=p) {}
								|| id instanceof VariableDeclaration && (id as VariableDeclaration).expression === funDef; //  f(p, q=f(1)) {}
							if (idRefCausesCyclDep) {
								isPostponedInitializer = true;
							}
						}
					}
					// In ObjectLiterals, the ThisLiteral in Initializers can cause cyclic dependencies
					val thisLiteralCausesCyclDep =
						funDef instanceof PropertyMethodDeclaration // let o = { a:1, f(p=this.a) {} }
						|| funDef instanceof FunctionExpression && funDef.eContainer instanceof PropertyNameValuePair; // let o = {a:2, f: function(p=this.a) {}}
					if (thisLiteralCausesCyclDep) {
						val containsThisLiteral = EcoreUtilN4.containsContentsOfTypeStopAt(fpar, ThisLiteral, N4JSPackage.Literals.FUNCTION_OR_FIELD_ACCESSOR__BODY);
						if (containsThisLiteral) {
							isPostponedInitializer = true;
						}
					}
					// If this check is not sufficient, we have to add more checks here. Note: Setters never have initializers.
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
	def package boolean processSubtree_forwardReference(RuleEnvironment G, TypableElement node, ASTMetaInfoCache cache) {
		assertTrueIfRigid(cache, "argument 'node' must be an AST node", node.isASTNode);

		// is node a valid target for a forward reference (i.e. an identifiable subtree)?
		val valid = node.isIdentifiableSubtree || node.isExceptionCaseOfForwardReferencableSubtree;
		if (!valid) {
			val resource = node.eResource as XtextResource
			if (resource !== null) {
				assertTrueIfRigid(cache,
					"forward reference only allowed to identifiable subtrees; but was: " + node + " in\n" +
						resource.parseResult.rootNode.text, valid)
			} else {
				assertTrueIfRigid(cache, "forward reference only allowed to identifiable subtrees; but was: " + node, valid);
			}
		}

		val fromCache = cache.getTypeFailSafe(node);
		if (fromCache !== null) {
			// already processed, nothing else to do
			// note: this is not an error, we may have many forward references to the same identifiable subtree
			return true;
		}

		if (cache.astNodesCurrentlyBeingTyped.contains(node)) {
			// cyclic forward reference
			// legal cases of a cyclic reference
			val isCyclicForwardReference = cache.astNodesCurrentlyBeingTyped.contains(node);
			if (isCyclicForwardReference && (
				node instanceof VariableDeclaration
				|| node instanceof N4ClassifierDeclaration
				|| node instanceof N4FieldDeclaration
				|| (node instanceof PropertyNameValuePair && (node as PropertyNameValuePair).expression instanceof FunctionExpression)
				|| node instanceof PropertyGetterDeclaration || node instanceof PropertySetterDeclaration
				|| (node instanceof Expression && node.eContainer instanceof YieldExpression)
			)) {
				return true;
			}

			// illegal cyclic node inference
			val msg = "*#*#*#*#*#* illegal cyclic forward reference to " + node.objectInfo + " (resource: "
				+ node.eResource?.URI + ")";
			logErr(msg);
			return false;
		} else if (isSemiCyclicForwardReferenceInForLoop(node, cache)) {
			// semi-cyclic forward reference
			// (this is deemed legal for the same reason why 'var x = 1+x;' is treated as a legal forward reference)
			return true;
		}

		if (cache.forwardProcessedSubTrees.contains(node)) {
			// we saw above that the cache does not contain anything for node, so this is an error
			throw new IllegalStateException
		}

		// actually perform the forward processing
		log(0, "===START of identifiable sub-tree below " + node.objectInfo);
		val G_fresh = G.newRuleEnvironment; // use a new, empty environment here (but retain cancelIndicator!)
		processSubtree(G_fresh, node, cache, 0); // note how we reset the indent level
		cache.forwardProcessedSubTrees.add(node);
		log(0, "===END of identifiable sub-tree below " + node.objectInfo);

		return true;
	}


	// ---------------------------------------------------------------------------------------------------------------


	/**
	 * Top-down processing of AST nodes happens here, i.e. this method will see all AST nodes in a top-down order.
	 */
	def private void processNode_preChildren(RuleEnvironment G, EObject node, ASTMetaInfoCache cache, int indentLevel) {

		if (node instanceof FunctionOrFieldAccessor) {
			cache.potentialContainersOfLocalArgumentsVariable.add(node); // remember for later
		}

		if (node instanceof FunctionDefinition) {
			handleAsyncFunctionDefinition(G, node, cache);
			handleGeneratorFunctionDefinition(G, node, cache);
		}

		typeDeferredProcessor.handleDeferredTypeRefs_preChildren(G, node, cache);
	}

	/**
	 * Bottom-up processing of AST nodes happens here, i.e. this method will see all AST nodes in a bottom-up order.
	 */
	def private void processNode_postChildren(RuleEnvironment G, EObject node, ASTMetaInfoCache cache, int indentLevel) {

		typeDeferredProcessor.handleDeferredTypeRefs_postChildren(G, node, cache);

		typeProcessor.typeNode(G, node, cache, indentLevel);

		// references to other files via import statements:
		if (node instanceof NamedImportSpecifier) {
			val elem = node.importedElement;
			if(elem!==null) {
				// make sure to use the correct type system for the other file (using our type system as a fall back)
				val tsCorrect = N4LanguageUtils.getServiceForContext(elem, N4JSTypeSystem).orElse(ts);
				// we're not interested in the type here, but invoking the type system will let us reuse
				// all the logic from method TypeProcessor#getType() for handling references to other resources
				tsCorrect.type(G, elem);
			}
		}
		
		// register migrations with their source types
		if (node instanceof FunctionDeclaration && 
			AnnotationDefinition.MIGRATION.hasAnnotation(node as FunctionDeclaration)) {
			this.registerMigrationWithTypes((node as FunctionDeclaration).definedFunction as TMigration)
		}
	}


	// ---------------------------------------------------------------------------------------------------------------


	/**
	 * This method returns the direct children of 'obj' that are to be processed, <em>in the order in which they are to
	 * be processed</em>. By default, all direct children must be processed and the order is insignificant, so in the
	 * default case this method simply returns {@link EObject#eContents()}. However, this method implements special
	 * handling for some exception cases where the processing order is significant.
	 */
	def private List<EObject> childrenToBeProcessed(RuleEnvironment G, EObject obj) {
		return switch (obj) {
			SetterDeclaration: {
				// process formal parameter before body
				obj.eContents.bringToFront(obj.fpar)
			}
			FunctionDefinition: {
				// process formal parameters before body
				obj.eContents.bringToFront(obj.fpars)
			}
			CatchBlock: {
				// process catch variable before block
				obj.eContents.bringToFront(obj.catchVariable)
			}
			ForStatement: {
				// process expression before varDeclOrBindings
				obj.eContents.bringToFront(obj.expression)
			}
			ParameterizedCallExpression case MigrationUtils.isMigrateCall(obj): {
				// link and type migration arguments first 
				obj.eContents.bringToFront(obj.arguments)
			}
			Script case variantHelper.allowVersionedTypes(obj): {
				// For variants that support versioned types and therefore migrations,
				// process the migrations first, since information about signatures of 
				// all migrations will be required when processing migration calls.
				obj.eContents.bringToFront(obj.scriptElements
					.filter(FunctionDeclaration)
					.filter[MigrationUtils.isMigrationDefinition(it)])
			}
			default: {
				// standard case: order is insignificant (so we simply use the order provided by EMF)
				obj.eContents
			}
		};
	}


	// ---------------------------------------------------------------------------------------------------------------


	/**
	 * Normally, forward references are allowed only to {@link N4JSLanguageUtils#isIdentifiableSubtree(EObject)
	 * identifiable subtrees}. However, there are exception cases that are also allowed and this method returns
	 * <code>true</code> for those cases.
	 */
	def private static boolean isExceptionCaseOfForwardReferencableSubtree(EObject astNode) {
		isExpressionInForOf(astNode)
	}
	def private static boolean isExpressionInForOf(EObject astNode) {
		astNode instanceof Expression && astNode.eContainer instanceof ForStatement
			&& (astNode.eContainer as ForStatement).isForOf
			&& astNode.eContainingFeature===N4JSPackage.eINSTANCE.iterationStatement_Expression;
	}

	/**
	 * Returns true if we have a semi-cyclic reference to a variable declaration in a for in/of loop.
	 * For example:
	 * <pre>
	 * for(var x of foo(x)) {}
	 * </pre>
	 */
	def package boolean isSemiCyclicForwardReferenceInForLoop(EObject node, ASTMetaInfoCache cache) {
		if (node instanceof VariableDeclaration) {
			val parent = node.eContainer;
			if (parent instanceof ForStatement) {
				return (parent.forIn || parent.forOf) && cache.astNodesCurrentlyBeingTyped.contains(parent.expression);
			}
		}
		return false;
	}

	def private void resolveAndProcessReferencesInNode(EObject astNode, ASTMetaInfoCache cache) {
		for(eRef : astNode.eClass.EAllReferences) {
			if(!eRef.isContainment && !eRef.isContainer) { // only cross-references have proxies (in our case)
				val node = astNode.eGet(eRef, true);

				if (node instanceof EObject) {
					recordReferencesToLocalVariables(eRef, astNode, node, cache);
				}
			}
		}
	}
	
	/**
	 * Registers the given {@link TMigration} with the corresponding
	 * principal argument.
	 */
	def private void registerMigrationWithTypes(TMigration migration) {
		// skip invalid migrations
		if (null === migration) {
			return;
		}
		// ignore (do not register) generic migrations for now
		if (!migration.typeVars.empty) {
			return;
		}
		
		if (null !== migration.principalArgumentType) {
			registerMigrationWithType(migration, migration.principalArgumentType);
		}
	}
	
	def private void registerMigrationWithType(TMigration migration, TMigratable migratable) {
		EcoreUtilN4.doWithDeliver(false, [
					migratable.migrations += migration 
		], migratable)
	}

	def private recordReferencesToLocalVariables(EReference reference, EObject sourceNode, EObject targetNode,
		ASTMetaInfoCache cache) {

		// If targetNode is still a proxy its resolution failed,
		// therefore it should be skipped.
		if (targetNode.eIsProxy) {
			return;
		}
		// skip non-local references
		if (sourceNode.eResource !== targetNode.eResource) {
			return;
		}
		if (targetNode instanceof VariableDeclaration) {
			// don't save references to exported variable declarations
			if (targetNode instanceof ExportedVariableDeclaration) {
				return;
			}

			cache.storeLocalVariableReference(targetNode, sourceNode);
		}
	}

	def private <T> List<T> bringToFront(List<T> l, T... elements) {
		val result = new ArrayList(l);
		val elemSanitized = elements.filterNull.toList;
		result.removeAll(elemSanitized);
		result.addAll(0, elemSanitized);
		return result;
	}
}
