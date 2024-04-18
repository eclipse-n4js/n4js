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
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.undefinedTypeRef;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.toIterable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.ts.typeRefs.DeferredTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.EcoreUtilN4;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Deals with destructuring patterns during post processing of an N4JS resource (only the destructuring pattern; the
 * value to be destructured is handled normally by the other processors).
 * <p>
 * TODO clean up handling of destructuring patterns during AST traversal, IDE-1714
 */
@Singleton
class DestructureProcessor extends AbstractProcessor {

	@Inject
	private ASTProcessor astProcessor;
	@Inject
	private PolyProcessor polyProcessor;

	/**
	 * Temporary handling of destructuring patterns while typing the AST.
	 */
	void typeDestructuringPattern(RuleEnvironment G, EObject node, ASTMetaInfoCache cache) {
		// ArrayLiteral or ObjectLiteral, but plays role of a destructuring pattern
		// -> does not really have a type, but use UnknownTypeRef to avoid having
		// to deal with this special case whenever asking for type of an expression
		cache.storeType((TypableElement) node, undefinedTypeRef(G));
		// for object literals, some additional hacks are required ...
		if (node instanceof ObjectLiteral) {
			ObjectLiteral olit = (ObjectLiteral) node;
			// poly expressions in property name/value pairs expect to be processed as part of the outer poly expression
			// -> invoke poly processor for them
			// TODO GH-1337 add support for spread operator
			for (PropertyAssignment pa : olit.getPropertyAssignments()) {
				if (pa instanceof PropertyNameValuePair) {
					Expression expr = ((PropertyNameValuePair) pa).getExpression();
					if (expr != null && polyProcessor.isResponsibleFor(expr) && !polyProcessor.isEntryPoint(expr)) {
						polyProcessor.inferType(G, expr, cache);
					}
				}
			}
			// the defined type of the object literal may still have some DeferredTypeRefs -> remove them
			for (DeferredTypeRef dtr : toIterable(filter(
					olit.getDefinedType().eAllContents(), DeferredTypeRef.class))) {

				EcoreUtilN4.doWithDeliver(false, () -> EcoreUtil.replace(dtr, undefinedTypeRef(G)), dtr.eContainer());
			}
			// add types for property assignments
			for (PropertyAssignment pa : olit.getPropertyAssignments()) {
				cache.storeType(pa, undefinedTypeRef(G));
			}
		}
		// here we basically turn off the fail-fast approach within the destructuring pattern
		for (EObject elem : toIterable(node.eAllContents())) {
			if (elem instanceof ObjectLiteral || elem instanceof PropertyAssignment
					|| elem instanceof ArrayLiteral || elem instanceof ArrayElement) {

				if (cache.getTypeFailSafe((TypableElement) elem) == null) {
					cache.storeType((TypableElement) elem, undefinedTypeRef(G));
				}
			}
		}
	}

	/**
	 * Temporary handling of forward references within destructuring patterns.
	 */
	TypeRef handleForwardReferenceWhileTypingDestructuringPattern(RuleEnvironment G, TypableElement node,
			ASTMetaInfoCache cache) {

		EObject parent = node.eContainer();
		boolean isCyclicForwardReference = cache.astNodesCurrentlyBeingTyped.contains(node);
		if (isCyclicForwardReference) {
			if (parent instanceof VariableBinding && ((VariableBinding) parent).getExpression() == node) {
				// we get here when typing the second 'b' in 'var [a,b] = [0,b,2];'
				return anyTypeRef(G);
			} else if (parent instanceof ForStatement && ((ForStatement) parent).getExpression() == node) {
				// we get here when typing the second 'a' in 'for(var [a] of [[a]]) {}'
				return anyTypeRef(G);
			}
		}

		log(0, "===START of other identifiable sub-tree");
		RuleEnvironment G_fresh = RuleEnvironmentExtensions.wrap(G); // don't use a new, empty environment here
																		// (required for recursion guards)
		astProcessor.processSubtree(G_fresh, node, cache, 0); // note how we reset the indent level
		cache.forwardProcessedSubTrees.add(node);
		log(0, "===END of other identifiable sub-tree");
		return cache.getType(G, node);
	}

	boolean isForwardReferenceWhileTypingDestructuringPattern(EObject obj) {
		if (obj instanceof Expression) {
			EObject parent = obj.eContainer();
			if (parent instanceof ForStatement) {
				return DestructureUtils.isTopOfDestructuringForStatement(parent);
			}
			if (parent instanceof AssignmentExpression) {
				return DestructureUtils.isTopOfDestructuringAssignment(parent);
			}
			return parent instanceof VariableBinding
					|| parent instanceof BindingElement
					|| (parent instanceof VariableDeclaration && parent.eContainer() instanceof BindingElement);
		}
		return false;
	}
}
