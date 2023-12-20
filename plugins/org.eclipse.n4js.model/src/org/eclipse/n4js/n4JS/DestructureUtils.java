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
package org.eclipse.n4js.n4JS;

import org.eclipse.emf.ecore.EObject;

/**
 * Utilities for destructuring patterns
 */
public abstract class DestructureUtils {

	/**
	 * Tells if the given expression may form a destructuring pattern if it appears on the LHS of an assignment
	 * expression. This method does <b>not</b> check if the expression actually appears on the LHS of an assignment.
	 * <p>
	 * NOTE: does <b>not</b> check the entire supplementary syntax for destructuring assignments from Section 12.14.5
	 * "Destructuring Assignment" of the ECMA Script 6 specification. This is done by dedicated validations in
	 * ASTStructureValidator.
	 */
	public static boolean isObjectOrArrayLiteral(EObject expr) {
		return expr instanceof ObjectLiteral || expr instanceof ArrayLiteral;
	}

	/**
	 * Tells if the given assignment expression is a destructuring assignment, i.e. has an array or object destructuring
	 * pattern on its LHS.
	 * <p>
	 * NOTE: does <b>not</b> check the entire supplementary syntax for destructuring assignments from Section 12.14.5
	 * "Destructuring Assignment" of the ECMA Script 6 specification. This is done by dedicated validations in
	 * ASTStructureValidator.
	 */
	public static boolean isTopOfDestructuringAssignment(EObject eobj) {
		if (eobj instanceof AssignmentExpression) {
			AssignmentExpression ae = (AssignmentExpression) eobj;
			return isObjectOrArrayLiteral(ae.getLhs());
		}
		return false;
	}

	/** @return true iff the given object is of type {@link VariableBinding} and has a {@link BindingPattern}. */
	public static boolean isTopOfDestructuringVariableBinding(EObject eobj) {
		if (eobj instanceof VariableBinding) {
			VariableBinding vb = (VariableBinding) eobj;
			return vb.getPattern() != null;
		}
		return false;
	}

	/**
	 * Tells if the given for statement is a destructuring for statement, i.e. for cases like:
	 *
	 * <pre>
	 * for(var [a,b] of arr) {}
	 * for([a,b] of arr) {}
	 * </pre>
	 *
	 * Note that this method returns <code>false</code> for cases like ...
	 *
	 * <pre>
	 * for(var [a,b] = [2,4]; a<10; a++) {}
	 * </pre>
	 *
	 * because in such cases the destructuring is seen as part of the variable declaration, not the for statement.
	 */
	public static boolean isTopOfDestructuringForStatement(EObject eobj) {
		if (eobj instanceof ForStatement) {
			ForStatement fs = (ForStatement) eobj;
			boolean isDestructuringForStatement = false;
			isDestructuringForStatement = fs.isForIn() || fs.isForOf();
			isDestructuringForStatement &= containsDestructuringPattern(fs) // case: for(var [a,b] of arr) {}
					|| isObjectOrArrayLiteral(fs.getInitExpr());// case: for([a,b] of arr) {}
			return isDestructuringForStatement;
		}
		return false;
	}

	/**
	 * Checks the following methods:
	 * <ul>
	 * <li/>{@link #isTopOfDestructuringAssignment(EObject)}
	 * <li/>{@link #isTopOfDestructuringForStatement(EObject)}
	 * <li/>{@link #isTopOfDestructuringVariableBinding(EObject)}
	 * </ul>
	 *
	 * @return true iff one of other {@code isTop} methods returns true
	 */
	public static boolean isTopOfDestructuring(EObject eobj) {
		boolean isParentOfDestructuring = false;
		isParentOfDestructuring |= isTopOfDestructuringAssignment(eobj);
		isParentOfDestructuring |= isTopOfDestructuringVariableBinding(eobj);
		isParentOfDestructuring |= isTopOfDestructuringForStatement(eobj);
		return isParentOfDestructuring;
	}

	/**
	 * @return object of type {@link AssignmentExpression}, {@link ForStatement} or {@link VariableBinding} iff the
	 *         given {@link EObject} is part of a destructuring pattern. Otherwise returns null.
	 */
	public static EObject getTop(EObject eobj) {
		if (isTopOfDestructuring(eobj)) {
			return eobj;
		}
		EObject root = getRoot(eobj);
		if (root == null) {
			return null;
		}
		EObject parent = root.eContainer();
		EObject parent2 = parent.eContainer();
		if (parent2 instanceof ForStatement && isTopOfDestructuringForStatement(parent2)) {
			return parent2;
		}
		if (parent instanceof VariableBinding && isTopOfDestructuringVariableBinding(parent)) {
			return parent;
		}
		if (parent instanceof AssignmentExpression && isTopOfDestructuringAssignment(parent)) {
			return parent;
		}
		if (parent instanceof ForStatement && isTopOfDestructuringForStatement(parent)) {
			return parent;
		}
		return null;
	}

	/**
	 * @return the nearest parent object that fulfills {@link #isRepresentingElement(EObject)} for any given object
	 *         within a destructuring pattern, or null.
	 */
	public static EObject getRepresentingElement(EObject eobj) {
		if (isRepresentingElement(eobj)) {
			return eobj;
		}
		EObject currEO = eobj;
		int i = 0;
		while (currEO != null) {
			currEO = currEO.eContainer();
			if (isRepresentingElement(currEO)) {
				break;
			}
			if (i++ > 2) {
				currEO = null;
				break;
			}
		}
		if (currEO != null) {
			EObject parent = currEO.eContainer();
			if (currEO instanceof BindingElement && parent instanceof BindingProperty) {
				currEO = parent;
			}
		}
		return currEO;
	}

	/** @return the {@link DestructNode} for the given {@link EObject}, even when nested */
	public static DestructNode getCorrespondingDestructNode(EObject eobj) {
		EObject representing = getRepresentingElement(eobj);
		if (representing == null) {
			return null;
		}
		EObject top = getTop(eobj);
		if (top == null) {
			return null;
		}
		DestructNode dNode = DestructNode.unify(top);
		if (dNode == null) {
			return null;
		}
		dNode = dNode.findNodeForElement(representing);
		return dNode;
	}

	/** @return true iff the given AST node contains a variable declaration in form of a destructuring pattern. */
	public static boolean containsDestructuringPattern(VariableDeclarationContainer vdeclContainer) {
		for (VariableDeclarationOrBinding vdeclOrBinding : vdeclContainer.getVarDeclsOrBindings()) {
			if (vdeclOrBinding instanceof VariableBinding) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true iff given object is an array/object literal used as a destructuring pattern (i.e. on lhs of an
	 * assignment or in a for..in/of loop or it is a nested literal within such an array/object literal).
	 */
	public static boolean isArrayOrObjectLiteralUsedAsDestructuringPattern(EObject obj) {
		if (!(isObjectOrArrayLiteral(obj))) {
			return false;
		}

		final EObject root = getRoot(obj);
		final EObject parent = root.eContainer();
		if (parent instanceof AssignmentExpression)
			return ((AssignmentExpression) parent).getLhs() == root;

		if (parent instanceof ForStatement)
			return !((ForStatement) parent).isForPlain() && ((ForStatement) parent).getInitExpr() == root;

		return false;
	}

	/**
	 * Returns true iff given object is an array/object literal used <b>as input to</b> a destructuring pattern (i.e. as
	 * expression of a VariableBinding, on rhs of an assignment or in a for..in/of loop or it is a nested literal within
	 * such an array/object literal).
	 * <p>
	 * TODO this method does not properly support nesting yet, i.e. in code like
	 *
	 * <pre>
	 * var [string a, Array<?> arr, number b] = ["hello", [1,2,3], 42];
	 * </pre>
	 *
	 * it will return <code>true</code> for the nested array <code>[1,2,3]</code>, which is wrong.
	 */
	public static boolean isArrayOrObjectLiteralBeingDestructured(EObject obj) {
		if (!(obj instanceof ArrayLiteral || obj instanceof ObjectLiteral))
			return false;
		final EObject root = getRoot(obj);
		final EObject parent = root.eContainer();
		if (parent instanceof VariableBinding)
			return ((VariableBinding) parent).getExpression() == root;
		if (parent instanceof AssignmentExpression && isTopOfDestructuringAssignment(parent))
			return ((AssignmentExpression) parent).getRhs() == root;
		if (parent instanceof ForStatement && isTopOfDestructuringForStatement(parent)) {
			// reason why we require obj!=root below:
			// in code like "for([a,b] of [ ["hello",42], ["world",43] ]) {}" the top-level array literal after
			// the "of" is *NOT* an array being destructured; instead, it's the array being iterated over.
			return !((ForStatement) parent).isForPlain() && ((ForStatement) parent).getExpression() == root
					&& obj != root;
		}
		return false;
	}

	/**
	 * When given an AST node in a destructuring pattern, tells if the given node is the root of the containing
	 * destructuring pattern.
	 * <p>
	 * NOTE: this method assumes that the argument is actually part of a destructuring pattern; otherwise the return
	 * value is undefined.
	 */
	public static boolean isRoot(EObject nodeWithinDestructuringPattern) {
		return !isParentPartOfSameDestructuringPattern(nodeWithinDestructuringPattern);
	}

	/**
	 * When given an AST node in a destructuring pattern, returns the root ArrayLiteral, ObjectLiteral, or
	 * BindingPattern of the containing tree of nested array/object literals. Will return <code>obj</code> if it already
	 * is such a root.
	 * <p>
	 * Can also be used in array and object literals that are <b>not</b> actually used as destructuring patterns, i.e.
	 * that aren't located on the LHS of an AssignmentExpression.
	 * <p>
	 * NOTE: this method assumes that the argument is actually part of a destructuring pattern (or at least array/object
	 * literal); otherwise the return value is undefined.
	 */
	public static EObject getRoot(EObject nodeWithinDestructuringPattern) {
		EObject curr = nodeWithinDestructuringPattern;
		while (isParentPartOfSameDestructuringPattern(curr)) {
			curr = curr.eContainer();
		}
		if (curr instanceof ObjectLiteral || curr instanceof ArrayLiteral || curr instanceof BindingPattern)
			return curr;
		return null;
	}

	/** @return true iff the given {@link EObject} is located inside a destructuring pattern */
	public static boolean isInDestructuringPattern(EObject obj) {
		return isParentPartOfSameDestructuringPattern(obj);
	}

	/** @return true iff the given {@link EObject} can be {@link DestructNode#astElement} */
	public static boolean isRepresentingElement(EObject eobj) {
		boolean isRepresentingElement = false;
		isRepresentingElement |= eobj instanceof ArrayElement;
		// isRepresentingElement |= eobj instanceof VariableBinding; // FIXME: remove
		isRepresentingElement |= eobj instanceof BindingElement;
		isRepresentingElement |= eobj instanceof BindingPattern;
		isRepresentingElement |= eobj instanceof BindingProperty;
		isRepresentingElement |= eobj instanceof PropertyAssignment;
		return isRepresentingElement;
	}

	/** @return true iff the given {@link EObject} is instanceof specific destructuring pattern classes */
	public static boolean isPatternElement(EObject eobj) {
		boolean isDestrElem = false;
		isDestrElem = isDestrElem || eobj instanceof ArrayElement;
		isDestrElem = isDestrElem || eobj instanceof ArrayLiteral;
		isDestrElem = isDestrElem || eobj instanceof ObjectLiteral;
		isDestrElem = isDestrElem || eobj instanceof BindingElement;
		isDestrElem = isDestrElem || eobj instanceof BindingPattern;
		isDestrElem = isDestrElem || eobj instanceof BindingProperty;
		isDestrElem = isDestrElem || eobj instanceof PropertyAssignment;
		return isDestrElem;
	}

	private static boolean isParentPartOfSameDestructuringPattern(EObject obj) {
		final EObject parent = obj != null ? obj.eContainer() : null;

		if (isPatternElement(parent)) {
			return true;
		}
		if (parent instanceof AssignmentExpression) {
			AssignmentExpression ae = (AssignmentExpression) parent;
			if (obj == ae.getLhs()) {
				EObject parentParent = parent.eContainer();

				return (parentParent instanceof ArrayElement || parentParent instanceof PropertyAssignment);
			}
		}
		return false;
	}

}
