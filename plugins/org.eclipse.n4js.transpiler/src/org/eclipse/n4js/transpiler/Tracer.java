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
package org.eclipse.n4js.transpiler;

import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.assertIntermediateModelElement;
import static org.eclipse.n4js.transpiler.utils.TranspilerUtils.assertOriginalASTNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.transpiler.utils.TranspilerUtils;

/**
 * Simple implementation of a tracing utility class used in the transpiler to establish tracing links between the
 * original AST and the intermediate model.
 * <p>
 * Associations between the intermediate model and the original AST are n:1 and can be traversed in both directions.
 */
public class Tracer {

	private final Map<EObject, EObject> im2ast = new HashMap<>();
	private final Map<EObject, Set<EObject>> ast2im = new HashMap<>();

	/**
	 * Returns <code>true</code> iff no associations between intermediate model elements and original AST nodes have
	 * been defined in this {@code Tracer}.
	 */
	public boolean isEmpty() {
		return im2ast.isEmpty();
	}

	/** Tells if this tracer contains an IM-AST association for the given element in the intermediate model. */
	public boolean containsKey(EObject elementInIntermediateModel) {
		// note: do not assertIntermediateModelElement(), because intended to be used with "wrong" objects
		return im2ast.containsKey(elementInIntermediateModel);
	}

	/** Tells if this tracer contains an IM-AST association for the given original AST node. */
	public boolean containsValue(EObject originalASTNode) {
		// note: do not assertOriginalASTNode(), because intended to be used with "wrong" objects
		return im2ast.containsValue(originalASTNode);
	}

	/**
	 * Same as {@link #getOriginalASTNode(EObject)}, but makes sure that the original AST node is of the same type as
	 * the given element in the intermediate model. Returns <code>null</code> if no original AST node defined or if it
	 * is of wrong type. If second argument is <code>true</code>, then throws an exception instead of returning
	 * <code>null</code>.
	 * <p>
	 * This method is particularly useful in the {@link Transformation#analyze() analyze()} method of an AST
	 * transformation, because there the intermediate model is guaranteed to be in its initial state and this method
	 * should always return a non-null value.
	 */
	public <T extends EObject> T getOriginalASTNodeOfSameType(T elementInIM, boolean failFast) {
		final EObject original = getOriginalASTNode(elementInIM);
		if (original != null && original.eClass() == elementInIM.eClass()) {
			@SuppressWarnings("unchecked")
			final T originalCasted = (T) original;
			return originalCasted;
		}
		if (failFast) {
			if (original != null)
				throw new IllegalStateException("there was an original AST node, but of wrong type");
			throw new IllegalStateException("no original AST node found");
		}
		return null;
	}

	/** Same as {@link #getOriginalASTNode(EObject, boolean)} with a second argument of <code>false</code>. */
	public EObject getOriginalASTNode(EObject elementInIntermediateModel) {
		return getOriginalASTNode(elementInIntermediateModel, false);
	}

	/**
	 * Get the original AST node for the given element in the intermediate model or <code>null</code> if the given
	 * element is not associated with the original AST (e.g. for elements that were created on the fly by a
	 * transformation).
	 * <p>
	 * If <code>includeAncestorLinks</code> is <code>true</code> and the given element is not associated with an
	 * original AST node, then this method will return the original AST node of the closest ancestor for which such an
	 * association is defined.
	 * <p>
	 * Throws an exception if the given element is not an {@link TranspilerUtils#isIntermediateModelElement(EObject)
	 * element of an intermediate model}.
	 */
	public EObject getOriginalASTNode(EObject elementInIntermediateModel, boolean includeAncestorLinks) {
		assertIntermediateModelElement(elementInIntermediateModel);
		EObject originalASTNode;
		do {
			originalASTNode = im2ast.get(elementInIntermediateModel);
		} while (includeAncestorLinks
				&& originalASTNode == null
				&& (elementInIntermediateModel = elementInIntermediateModel.eContainer()) != null);
		return originalASTNode;
	}

	/**
	 * Same as {@link #getSingleIntermediateModelElement(EObject, boolean)}, but makes sure that the element is of the
	 * same type as the given original AST node. Returns <code>null</code> if no element in intermediate model defined
	 * or if it is of wrong type. If second argument is <code>true</code>, then throws an exception instead of returning
	 * <code>null</code>.
	 * <p>
	 * This method is particularly useful in the {@link Transformation#analyze() analyze()} method of an AST
	 * transformation, because there the intermediate model is guaranteed to be in its initial state and this method
	 * should always return a non-null value.
	 */
	public <T extends EObject> T getSingleIntermediateModelElementOfSameType(T originalASTNode, boolean failFast) {
		final EObject elemInIM = getSingleIntermediateModelElement(originalASTNode, failFast);
		if (elemInIM != null && elemInIM.eClass() == originalASTNode.eClass()) {
			@SuppressWarnings("unchecked")
			final T elemCasted = (T) elemInIM;
			return elemCasted;
		}
		if (failFast) {
			if (elemInIM != null)
				throw new IllegalStateException("there was an element in the intermediate model, but of wrong type");
			throw new IllegalStateException("no element in intermediate model found");
		}
		return null;
	}

	/**
	 * Convenience method. Same as {@link #getIntermediateModelElements(EObject)}, but only returns the first element
	 * found.
	 */
	public EObject getSingleIntermediateModelElement(EObject originalASTNode, boolean failFast) {
		final Set<EObject> elems = getIntermediateModelElements(originalASTNode);
		if (elems.size() == 1) {
			return elems.iterator().next();
		}
		if (failFast) {
			if (!elems.isEmpty())
				throw new IllegalStateException("several elements in intermediate model found");
			throw new IllegalStateException("no elements in intermediate model found");
		}
		return null;
	}

	/**
	 * Get all intermediate model elements associated with the given original AST node or an empty list if no such
	 * associations have been defined.
	 * <p>
	 * Throws an exception if the given node is not an {@link TranspilerUtils#isOriginalASTNode(EObject) original AST
	 * node}.
	 */
	public Set<EObject> getIntermediateModelElements(EObject originalASTNode) {
		assertOriginalASTNode(originalASTNode);
		final Set<EObject> l = ast2im.get(originalASTNode);
		return l != null ? l : Collections.emptySet();
	}

	/**
	 * Convenience method. Same as {@link #setOriginalASTNode(EObject, EObject)}, but for many elements at once.
	 */
	public void setOriginalASTNode(EObject[] elementsInIntermediateModel, EObject originalASTNode) {
		for (EObject elem : elementsInIntermediateModel)
			setOriginalASTNode(elem, originalASTNode);
	}

	/**
	 * Set the original AST node for the given element in the intermediate model.
	 * <p>
	 * Throws an exception if the given element is not an {@link TranspilerUtils#isIntermediateModelElement(EObject)
	 * element of an intermediate model} or if the given node is not an
	 * {@link TranspilerUtils#isOriginalASTNode(EObject) original AST node}.
	 */
	public void setOriginalASTNode(EObject elementInIntermediateModel, EObject originalASTNode) {
		assertIntermediateModelElement(elementInIntermediateModel);
		assertOriginalASTNode(originalASTNode);
		setOriginalASTNode_internal(elementInIntermediateModel, originalASTNode);
	}

	/* package */ void setOriginalASTNode_internal(EObject elementInIntermediateModel, EObject originalASTNode) {
		// 1) update AST->IM: remove old association (if any)
		unsetOriginalASTNode_internal(elementInIntermediateModel);
		// 2) update IM->AST
		im2ast.put(elementInIntermediateModel, originalASTNode);
		// 3) update AST->IM: add new association
		Set<EObject> l = ast2im.get(originalASTNode);
		if (l == null) {
			l = new HashSet<>();
			ast2im.put(originalASTNode, l);
		}
		l.add(elementInIntermediateModel);
	}

	/**
	 * Remove the association to the original AST for the given element in the intermediate model, if any (optional
	 * operation).
	 * <p>
	 * Throws an exception if the given element is not an {@link TranspilerUtils#isIntermediateModelElement(EObject)
	 * element of an intermediate model}.
	 */
	public void unsetOriginalASTNode(EObject elementInIntermediateModel) {
		assertIntermediateModelElement(elementInIntermediateModel);
		unsetOriginalASTNode_internal(elementInIntermediateModel);
	}

	/**
	 * Prevents memory retention in the {@link #im2ast} map. This method delegates updating the map in the other
	 * direction to {@link #unsetOriginalASTNode} which removes references to the discarded element from the AST->IM
	 * map.
	 * <p>
	 * For now it's assumed that only IM nodes can be discarded (unlike original AST nodes).
	 */
	public void discardIntermediateModelNode(EObject elementInIntermediateModel) {
		unsetOriginalASTNode(elementInIntermediateModel);
		im2ast.remove(elementInIntermediateModel);
	}

	private void unsetOriginalASTNode_internal(EObject elementInIntermediateModel) {
		final EObject oldOriginalASTNode = im2ast.get(elementInIntermediateModel);
		if (oldOriginalASTNode != null) {
			final Set<EObject> lOld = ast2im.get(oldOriginalASTNode);
			if (lOld != null)
				lOld.remove(elementInIntermediateModel);
		}
	}

	/**
	 * Copy tracing - if set - to one or more newly created IM-elements (they need not be contained in IM yet).
	 */
	public void copyTrace(EObject fromElementInIntermediateModel, EObject... toElementInIntermediateModel) {
		EObject originalASTNode = im2ast.get(fromElementInIntermediateModel);
		if (originalASTNode == null)
			return;
		for (EObject elemInIM : toElementInIntermediateModel)
			setOriginalASTNode_internal(elemInIM, originalASTNode);
	}
}
