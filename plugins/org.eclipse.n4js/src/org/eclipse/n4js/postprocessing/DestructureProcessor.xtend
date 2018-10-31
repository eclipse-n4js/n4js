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
import org.eclipse.n4js.n4JS.ArrayElement
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.BindingElement
import org.eclipse.n4js.n4JS.DestructureUtils
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ForStatement
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.typesystem.RuleEnvironmentExtensions
import org.eclipse.n4js.typesystem.utils.Result
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.utils.EcoreUtilN4

import static extension org.eclipse.n4js.typesystem.RuleEnvironmentExtensions.*

/**
 * Deals with destructuring patterns during post processing of an N4JS resource (only the destructuring pattern;
 * the value to be destructured is handled normally by the other processors).
 * <p>
 * TODO clean up handling of destructuring patterns during AST traversal, IDE-1714
 */
@Singleton
package class DestructureProcessor extends AbstractProcessor {

	@Inject
	private ASTProcessor astProcessor;
	@Inject
	private PolyProcessor polyProcessor;

	/**
	 * Temporary handling of destructuring patterns while typing the AST.
	 */
	def void typeDestructuringPattern(RuleEnvironment G, EObject node, ASTMetaInfoCache cache, int indentLevel) {
		// ArrayLiteral or ObjectLiteral, but plays role of a destructuring pattern
		// -> does not really have a type, but use UnknownTypeRef to avoid having
		// to deal with this special case whenever asking for type of an expression
		cache.storeType(node as TypableElement, Result.success(TypeRefsFactory.eINSTANCE.createUnknownTypeRef))
		// for object literals, some additional hacks are required ...
		if (node instanceof ObjectLiteral) {
			// poly expressions in property name/value pairs expect to be processed as part of the outer poly expression
			// -> invoke poly processor for them
			node.propertyAssignments //
			.filter(PropertyNameValuePair) //
			.map[expression] //
			.filterNull //
			.filter[polyProcessor.isResponsibleFor(it) && !polyProcessor.isEntryPoint(it)] //
			.forEach [
				polyProcessor.inferType(G, it, cache);
			];
			// the defined type of the object literal may still have some DeferredTypeRefs -> remove them
			node.definedType.eAllContents.filter(DeferredTypeRef).forEach [ dtr |
				EcoreUtilN4.doWithDeliver(false, [
					EcoreUtil.replace(dtr, TypeRefsFactory.eINSTANCE.createUnknownTypeRef);
				], dtr.eContainer);
			]
			// add types for property assignments
			node.propertyAssignments.forEach [
				cache.storeType(it, Result.success(TypeRefsFactory.eINSTANCE.createUnknownTypeRef));
			]
		}
		// here we basically turn off the fail-fast approach within the destructuring pattern
		node.eAllContents //
		.filter[it instanceof ObjectLiteral || it instanceof ArrayLiteral || it instanceof ArrayElement] //
		.filter[cache.getTypeFailSafe(it as TypableElement)===null] //
		.forEach[
			cache.storeType(it as TypableElement, Result.success(TypeRefsFactory.eINSTANCE.createUnknownTypeRef));
		]
	}

	/**
	 * Temporary handling of forward references within destructuring patterns.
	 */
	def Result<TypeRef> handleForwardReferenceWhileTypingDestructuringPattern(RuleEnvironment G, TypableElement node,
		ASTMetaInfoCache cache) {

		val parent = node.eContainer();
		val isCyclicForwardReference = cache.astNodesCurrentlyBeingTyped.contains(node);
		if(isCyclicForwardReference) {
			if(parent instanceof VariableBinding && (parent as VariableBinding).expression===node) {
				// we get here when typing the second 'b' in 'var [a,b] = [0,b,2];'
				return Result.success(G.anyTypeRef);
			} else if(parent instanceof ForStatement && (parent as ForStatement).expression===node) {
				// we get here when typing the second 'a' in 'for(var [a] of [[a]]) {}'
				return Result.success(G.anyTypeRef);
			}
		}

		log(0, "===START of other identifiable sub-tree");
		val G_fresh = RuleEnvironmentExtensions.wrap(G); // don't use a new, empty environment here (required for recursion guards)
		astProcessor.processSubtree(G_fresh, node, cache, 0); // note how we reset the indent level
		cache.forwardProcessedSubTrees.add(node);
		log(0, "===END of other identifiable sub-tree");
		return cache.getType(node);
	}

	def boolean isForwardReferenceWhileTypingDestructuringPattern(EObject obj) {
		if (obj instanceof Expression) {
			val parent = obj.eContainer;
			if (parent instanceof ForStatement) {
				return DestructureUtils.isTopOfDestructuringForStatement(parent);
			}
			if (parent instanceof AssignmentExpression) {
				return DestructureUtils.isTopOfDestructuringAssignment(parent)
			}
			return parent instanceof VariableBinding
				|| parent instanceof BindingElement
				|| (parent instanceof VariableDeclaration && parent.eContainer instanceof BindingElement)
		}
		return false;
	}
}
