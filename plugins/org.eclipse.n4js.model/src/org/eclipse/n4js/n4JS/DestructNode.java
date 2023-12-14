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

import static org.eclipse.xtext.xbase.lib.IterableExtensions.exists;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.ts.types.TypableElement;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Destructuring patterns can appear in very different forms within the AST and in different contexts. This helper class
 * is used to transform those heterogeneous representations into a single, uniform structure, that can be traversed more
 * easily.
 * <p>
 * All fields are optional, i.e. may be 'null'. At most one of 'varRef', 'varDecl' and 'nestedPattern' may be non-null;
 * if all three are 'null' the node is a padding node.
 *
 * <h2>Overview of Destructuring Patterns in the AST</h2> Different forms:
 * <ol>
 * <li>as a {@link BindingPattern} (may contain nested {@code BindingPattern}s).
 * <li>as an {@link ArrayLiteral} (may contain nested patterns in form of {@code ArrayLiteral}s or
 * {@code ObjectLiteral}s).
 * <li>as an {@link ObjectLiteral} (may contain nested patterns in form of {@code ArrayLiteral}s or
 * {@code ObjectLiteral}s).
 * </ol>
 * Different contexts:
 * <ol>
 * <li>within a {@link VariableStatement} (then contained in a {@link VariableBinding}, which is an alternative to a
 * {@link VariableDeclaration}).
 * <li>within an {@link AssignmentExpression} (then it appears as the left-hand side expression)
 * <li>within a {@link ForStatement} (only in for..in and for..of, because if used in plain for it is a use case of a
 * variable statement or assignment expression inside the for statement).
 * <li><b>NOT SUPPORTED YET</b>: in the context of lists of formal parameters or function argument lists
 * </ol>
 * The above 6 use cases have several special characteristics and constraints, most of which are unified in this class.
 * It might be possible to generate more unified patterns in the parser, but the above situation is more in line with
 * terminology in the ES6 specification.
 * <p>
 */
@Data
@SuppressWarnings("javadoc")
public class DestructNode {
	EObject astElement;
	String propName; // property name (iff in object destructuring pattern) or 'null' (iff in array destructuring
						// pattern)
	IdentifierRef varRef;
	VariableDeclaration varDecl;
	List<DestructNode> nestedNodes; // nested pattern that will be bound/assigned (or 'null' iff 'varName' is non-null)
	Expression defaultExpr;
	TypableElement assignedElem; // can be an Expression or an IdentifiableElement (in case of Getter/Setter/Method)
	boolean rest;

	public DestructNode(EObject astElement, String propName, IdentifierRef varRef, VariableDeclaration varDecl,
			List<DestructNode> nestedNodes, Expression defaultExpr, TypableElement assignedElem, boolean rest) {

		this.astElement = astElement;
		this.propName = propName;
		this.varRef = varRef;
		this.varDecl = varDecl;
		this.nestedNodes = Collections.unmodifiableList(nestedNodes);
		this.defaultExpr = defaultExpr;
		this.assignedElem = assignedElem;
		this.rest = rest;
	}

	public EObject getAstElement() {
		return astElement;
	}

	public String getPropName() {
		return propName;
	}

	public IdentifierRef getVarRef() {
		return varRef;
	}

	public VariableDeclaration getVarDecl() {
		return varDecl;
	}

	public List<DestructNode> getNestedNodes() {
		return nestedNodes;
	}

	public Expression getDefaultExpr() {
		return defaultExpr;
	}

	public TypableElement getAssignedElem() {
		return assignedElem;
	}

	public boolean isRest() {
		return rest;
	}

	/**
	 * Returns true if if receiving node belongs to a positional destructuring pattern (i.e. an array destructuring
	 * pattern).
	 */
	public boolean isPositional() {
		return propName == null;
	}

	/**
	 * Returns true if if the given nodes belong to a positional destructuring pattern (i.e. an array destructuring
	 * pattern).
	 */
	static public boolean arePositional(List<DestructNode> nodes) {
		return nodes != null && exists(nodes, n -> n.isPositional());
	}

	/**
	 * Returns true if if this is a padding node.
	 */
	public boolean isPadding() {
		return varRef == null && varDecl == null && nestedNodes == null;
	}

	/**
	 * If this node has a reference to a variable or a variable declaration, returns the variable's name,
	 * <code>null</code> otherwise.
	 */
	public String varName() {
		if (varRef != null) {
			return varRef.getId() == null ? null : varRef.getId().getName();
		} else if (varDecl != null) {
			return varDecl.getName();
		}
		return null;
	}

	/**
	 * Returns the variable declaration contained in this node's astElement or <code>null</code>.
	 */
	public VariableDeclaration getVariableDeclaration() {
		if (astElement instanceof BindingElement) {
			return ((BindingElement) astElement).getVarDecl();
		}
		if (astElement instanceof BindingProperty && ((BindingProperty) astElement).getValue() != null) {
			return ((BindingProperty) astElement).getValue().getVarDecl();
		}
		return null;
	}

	/**
	 * Returns the AST node and EStructuralFeature to be used when showing an error message on the receiving node's
	 * propName attribute. Intended for issue generation in validations.
	 */
	public Pair<EObject, EStructuralFeature> getEObjectAndFeatureForPropName() {
		if (propName != null) {
			if (astElement instanceof PropertyNameValuePairSingleName) {
				PropertyNameValuePairSingleName pnvpsn = (PropertyNameValuePairSingleName) astElement;
				if (pnvpsn.getExpression() instanceof AssignmentExpression) {
					return Pair.of(pnvpsn.getExpression(), N4JSPackage.eINSTANCE.getAssignmentExpression_Lhs());
				}
			}
			if (astElement instanceof PropertyNameValuePairSingleName) {
				return Pair.of(astElement, N4JSPackage.eINSTANCE.getPropertyNameValuePair_Expression());
			}
			if (astElement instanceof BindingProperty) {
				BindingProperty bp = (BindingProperty) astElement;

				if (bp.getDeclaredName() != null) {
					return Pair.of(bp, N4JSPackage.eINSTANCE.getPropertyNameOwner_DeclaredName());
				}
				if (bp.getProperty() != null) {
					return Pair.of(bp, N4JSPackage.eINSTANCE.getBindingProperty_Property());
				}
				if (bp.getValue() != null && bp.getValue().getVarDecl() != null
						&& bp.getValue().getVarDecl().getName() != null) {
					return Pair.of(bp.getValue().getVarDecl(), N4JSPackage.eINSTANCE.getAbstractVariable_Name());
				}
			}
			if (astElement instanceof PropertyNameValuePair
					&& ((PropertyNameValuePair) astElement).getProperty() != null) {

				return Pair.of(astElement, N4JSPackage.eINSTANCE.getPropertyNameValuePair_Property());
			}
			if (astElement instanceof PropertyNameOwner) {
				return Pair.of(astElement, N4JSPackage.eINSTANCE.getPropertyNameOwner_DeclaredName());
			}
		}
		return Pair.of(astElement, null);// show error on entire node
	}

	/**
	 * Returns the node with the given <code>astElement</code>.
	 */
	public DestructNode findNodeForElement(EObject pAstElement) {
		return findNodeOrParentForElement(pAstElement, false);
	}

	/**
	 * Returns the node with the given <code>astElement</code> or its parent node.
	 */
	public DestructNode findNodeOrParentForElement(EObject pAstElement, boolean returnParent) {
		EObject reprAstElem = pAstElement;
		if (pAstElement instanceof BindingElement && pAstElement.eContainer() instanceof BindingProperty) {
			reprAstElem = pAstElement.eContainer();
		}

		if (this.astElement == reprAstElem) {
			if (returnParent) {
				return null;
			}
			return this;
		}
		if (this.nestedNodes == null) {
			return null;
		}
		for (DestructNode nested : this.nestedNodes) {
			if (nested.astElement == reprAstElem) {
				if (returnParent) {
					return this;
				}
				return nested;
			}
			DestructNode resNested = nested.findNodeOrParentForElement(reprAstElem, returnParent);
			if (resNested != null) {
				return resNested;
			}
		}
		return null;
	}

	/**
	 * Returns stream of this node and all its descendants, i.e. directly and indirectly nested nodes.
	 */
	public Stream<DestructNode> stream() {
		if (nestedNodes == null || nestedNodes.size() == 0) {
			return Stream.of(this);
		} else {
			return Stream.concat(Stream.of(this), Stream.of(nestedNodes).flatMap(dn -> stream()));
		}
	}

	public static DestructNode unify(EObject eobj) {
		if (eobj instanceof VariableBinding) {
			return unify(eobj);
		}
		if (eobj instanceof AssignmentExpression) {
			return unify(eobj);
		}
		if (eobj instanceof ForStatement) {
			return unify(eobj);
		}
		return null;
	}

	/**
	 * Returns a unified copy of the given destructuring pattern or <code>null</code> if it is invalid. This is helpful
	 * because these patterns can appear in very different forms and locations within the AST.
	 */
	public static DestructNode unify(EObject astElem, EObject lhs, Expression rhs) {
		return new DestructNode(
				astElem, // astElement
				null, // propName
				null, // varRef
				null, // varDecl
				toEntries(lhs, rhs), // nestedNodes
				rhs, // defaultExpr
				rhs, // assignedExpr
				false // rest
		);
	}

	/**
	 * Returns a unified copy of the given destructuring pattern or <code>null</code> if it is invalid. This is helpful
	 * because these patterns can appear in very different forms and locations within the AST.
	 */
	public static DestructNode unify(VariableBinding binding) {
		if (binding != null && binding.getPattern() != null
		// note: binding.expression is mandatory in variable statements
		// but optional in for..in/of statements
				&& (binding.getExpression() != null || binding.eContainer() instanceof ForStatement)) {

			return unify(binding, binding.getPattern(), binding.getExpression());
		}
		return null;
	}

	/**
	 * Returns a unified copy of the given destructuring pattern or <code>null</code> if it is invalid. This is helpful
	 * because these patterns can appear in very different forms and locations within the AST.
	 */
	public static DestructNode unify(AssignmentExpression assignExpr) {
		if (assignExpr != null && assignExpr.getLhs() != null && assignExpr.getRhs() != null
				&& DestructureUtils.isTopOfDestructuringAssignment(assignExpr)) {

			return unify(assignExpr, assignExpr.getLhs(), assignExpr.getRhs());
		}
		return null;
	}

	/**
	 * Returns a unified copy of the given destructuring pattern or <code>null</code> if it is invalid. This is helpful
	 * because these patterns can appear in very different forms and locations within the AST.
	 */
	public static DestructNode unify(ForStatement forStmnt) {
		if (forStmnt != null && DestructureUtils.isTopOfDestructuringForStatement(forStmnt)) {
			Expression valueToBeDestructured;
			Expression defaultExpression;

			if (forStmnt.isForOf()) {
				valueToBeDestructured = firstArrayElement(forStmnt.getExpression());
				defaultExpression = forStmnt.getExpression();
			} else if (forStmnt.isForIn()) {
				StringLiteral slit1 = N4JSFactory.eINSTANCE.createStringLiteral();
				slit1.setValue("");
				valueToBeDestructured = slit1;

				StringLiteral slit2 = N4JSFactory.eINSTANCE.createStringLiteral();
				slit2.setValue("");
				defaultExpression = slit2;
			} else {
				// impossible because #isTopOfDestructuringForStatement() returned true
				throw new IllegalStateException();
			}

			if (DestructureUtils.containsDestructuringPattern(forStmnt)) {
				// case: for(var [a,b] of arr) {}
				VariableBinding binding = head(filter(forStmnt.getVarDeclsOrBindings(), VariableBinding.class));
				Expression rhs = firstArrayElement(forStmnt.getExpression());
				return new DestructNode(
						forStmnt, // astElement
						null, // propName
						null, // varRef
						null, // varDecl
						toEntries(binding.getPattern(), rhs), // nestedNodes
						defaultExpression, // defaultExpr
						valueToBeDestructured, // assignedExpr
						false // rest
				);
			} else if (DestructureUtils.isObjectOrArrayLiteral(forStmnt.getInitExpr())) {
				// case: for([a,b] of arr) {}
				return new DestructNode(
						forStmnt, // astElement
						null, // propName
						null, // varRef
						null, // varDecl
						toEntries(forStmnt.getInitExpr(), null), // nestedNodes
						defaultExpression, // defaultExpr
						defaultExpression, // assignedExpr
						false // rest
				);
			}
		}
		return null;
	}

	private static Expression firstArrayElement(Expression expr) {
		return (expr instanceof ArrayLiteral) ? ((ArrayLiteral) expr).getElements().get(0).getExpression() : expr;
	}

	private static List<DestructNode> toEntries(EObject pattern, TypableElement rhs) {
		Iterator<? extends EObject> patElemIter = null;
		if (pattern instanceof ArrayLiteral) {
			patElemIter = ((ArrayLiteral) pattern).getElements().iterator();
		} else if (pattern instanceof ObjectLiteral) {
			patElemIter = ((ObjectLiteral) pattern).getPropertyAssignments().iterator();
		} else if (pattern instanceof ArrayBindingPattern) {
			patElemIter = ((ArrayBindingPattern) pattern).getElements().iterator();
		} else if (pattern instanceof ObjectBindingPattern) {
			patElemIter = ((ObjectBindingPattern) pattern).getProperties().iterator();
		} else {
			return Collections.emptyList();
		}

		Iterator<? extends TypableElement> rhsElemIter = null;
		if (rhs instanceof ArrayLiteral) {
			rhsElemIter = ((ArrayLiteral) rhs).getElements().iterator();
		}
		if (rhs instanceof ObjectLiteral) {
			rhsElemIter = ((ObjectLiteral) rhs).getPropertyAssignments().iterator();
		}

		BasicEList<DestructNode> nestedDNs = new BasicEList<>();
		while (patElemIter.hasNext()) {
			EObject patElem = patElemIter.next();
			TypableElement litElem = (rhsElemIter == null) ? rhs : (rhsElemIter.hasNext()) ? rhsElemIter.next() : null;

			DestructNode nestedNode = null;
			if (patElem instanceof ArrayElement) {
				nestedNode = toEntry((ArrayElement) patElem, litElem);
			}
			if (patElem instanceof PropertyNameValuePair) {
				nestedNode = toEntry((PropertyNameValuePair) patElem, litElem);
			}
			if (patElem instanceof BindingElement) {
				nestedNode = toEntry((BindingElement) patElem, litElem);
			}
			if (patElem instanceof BindingProperty) {
				nestedNode = toEntry((BindingProperty) patElem, litElem);
			}

			if (nestedNode != null) {
				nestedDNs.add(nestedNode);
			}
		}
		return nestedDNs;
	}

	private static DestructNode toEntry(ArrayElement elem, TypableElement rhs) {
		TypableElement rhsExpr = (rhs instanceof ArrayElement) ? ((ArrayElement) rhs).getExpression() : rhs;
		Expression expr = elem.getExpression(); // note: ArrayPadding will return null for getExpression()
		if (expr instanceof AssignmentExpression) {
			AssignmentExpression ae = (AssignmentExpression) expr;
			return toEntry(elem, null, ae.getLhs(), ae.getRhs(), elem.isSpread(), rhsExpr);
		} else {
			return toEntry(elem, null, expr, null, elem.isSpread(), rhsExpr);
		}
	}

	private static DestructNode toEntry(PropertyNameValuePair pa, TypableElement rhs) {
		TypableElement rhsExpr = (rhs instanceof PropertyNameValuePair)
				? ((PropertyNameValuePair) rhs).getExpression()
				: rhs;
		Expression expr = pa.getExpression();
		if (expr instanceof AssignmentExpression) {
			AssignmentExpression ae = (AssignmentExpression) expr;
			return toEntry(pa, pa.getName(), ae.getLhs(), ae.getRhs(), false, rhsExpr);
		} else {
			return toEntry(pa, pa.getName(), expr, null, false, rhsExpr);
		}
	}

	private static DestructNode toEntry(BindingElement elem, TypableElement rhs) {
		TypableElement expr = (rhs instanceof ArrayElement) ? ((ArrayElement) rhs).getExpression() : rhs;

		if (elem.getVarDecl() != null) {
			return toEntry(elem, null, elem.getVarDecl(), elem.getVarDecl().getExpression(), elem.isRest(), expr);
		} else if (elem.getNestedPattern() != null) {
			return toEntry(elem, null, elem.getNestedPattern(), elem.getExpression(), elem.isRest(), expr);
		} else {
			return toEntry(elem, null, null, null, false, expr); // return dummy entry to not break indices
		}
	}

	private static DestructNode toEntry(BindingProperty prop, TypableElement rhs) {
		if (prop.getValue() != null && prop.getValue().getVarDecl() != null) {
			TypableElement expr = getPropertyAssignmentExpression(rhs);
			return toEntry(prop, prop.getName(), prop.getValue().getVarDecl(),
					prop.getValue().getVarDecl().getExpression(), false, expr);

		} else if (prop.getValue() != null && prop.getValue().getNestedPattern() != null) {
			TypableElement expr = getPropertyAssignmentExpression(rhs);
			return toEntry(prop, prop.getName(), prop.getValue().getNestedPattern(), prop.getValue().getExpression(),
					false, expr);

		} else {
			return toEntry(prop, null, null, null, false, rhs);
		}
	}

	/**
	 * @param bindingTarget
	 *            an IdentifierRef/VariableDeclaration or a nested pattern (which may be a BindingPattern, ArrayLiteral,
	 *            or ObjectLiteral)
	 */
	private static DestructNode toEntry(EObject astElement, String propName, EObject bindingTarget,
			Expression defaultExpr, boolean rest, TypableElement rhs) {

		if (bindingTarget == null) {
			// no target -> create a padding node
			return new DestructNode(astElement, propName, null, null, null, defaultExpr, null, rest);

		} else if (bindingTarget instanceof IdentifierRef) {
			return new DestructNode(astElement, propName, (IdentifierRef) bindingTarget, null, null, defaultExpr, rhs,
					rest);

		} else if (bindingTarget instanceof VariableDeclaration) {
			return new DestructNode(astElement, propName, null, (VariableDeclaration) bindingTarget, null, defaultExpr,
					rhs, rest);

		} else if (bindingTarget instanceof ArrayLiteral || bindingTarget instanceof ObjectLiteral ||
				bindingTarget instanceof BindingPattern) {
			return new DestructNode(astElement, propName, null, null, toEntries(bindingTarget, rhs), defaultExpr, rhs,
					rest);

		} else {
			// invalid binding target (probably a corrupt AST) -> create a padding node
			return new DestructNode(astElement, propName, null, null, null, defaultExpr, null, rest);
		}
	}

	/** @return the expression or function of the given PropertyAssignment */
	private static TypableElement getPropertyAssignmentExpression(TypableElement rhs) {
		if (rhs instanceof PropertyGetterDeclaration) {
			return ((PropertyGetterDeclaration) rhs).getDefinedFunctionOrAccessor();
		}
		if (rhs instanceof PropertySetterDeclaration) {
			return ((PropertySetterDeclaration) rhs).getDefinedFunctionOrAccessor();
		}
		if (rhs instanceof PropertyMethodDeclaration) {
			return ((PropertyMethodDeclaration) rhs).getDefinedFunctionOrAccessor();
		}
		if (rhs instanceof PropertyNameValuePair) {
			return ((PropertyNameValuePair) rhs).getExpression();
		}
		if (rhs instanceof PropertyAssignmentAnnotationList) {
			return null;
		}

		return rhs;
	}

	/** @return all {@link IdentifierRef} of variables that are written in the given assignment */
	public List<Expression> getAllDeclaredIdRefs() {
		List<Expression> idRefs = new LinkedList<>();
		Iterator<DestructNode> allNestedNodes = this.stream().iterator();

		while (allNestedNodes.hasNext()) {
			EObject eobj = allNestedNodes.next().astElement;
			if (eobj instanceof ArrayElement) {
				Expression expr = ((ArrayElement) eobj).getExpression();
				if (expr instanceof AssignmentExpression) {
					idRefs.add((((AssignmentExpression) expr).getLhs()));
				} else {
					idRefs.add(expr);
				}

			} else if (eobj instanceof PropertyNameValuePairSingleName) {
				idRefs.add(((PropertyNameValuePairSingleName) eobj).getIdentifierRef());

			} else if (eobj instanceof PropertyNameValuePair) {
				Expression expr = ((PropertyNameValuePair) eobj).getExpression();
				if (expr instanceof AssignmentExpression) {
					idRefs.add(((AssignmentExpression) expr).getLhs());
				} else {
					idRefs.add(expr);
				}
			}
		}
		return idRefs;
	}

	/**
	 * @return a pair where its key is the assigned EObject and its value is the default EObject to the given lhs AST
	 *         element
	 */
	public static Pair<EObject, EObject> getValueFromDestructuring(EObject nodeElem) {
		EObject node = nodeElem;
		EObject topNode = null;
		EObject dNodeElem = null;
		boolean breakSearch = false;

		while (!breakSearch) {
			EObject parent = node.eContainer();
			dNodeElem = getDNodeElem(dNodeElem, parent, node);
			topNode = getTopElem(topNode, parent);
			breakSearch = parent instanceof Statement;
			node = parent;
		}

		DestructNode dNode = null;
		if (topNode instanceof AssignmentExpression) {
			dNode = DestructNode.unify(topNode);
		} else if (topNode instanceof VariableBinding) {
			dNode = DestructNode.unify(topNode);
		} else if (topNode instanceof ForStatement) {
			dNode = DestructNode.unify(topNode);
		}

		if (dNode != null) {
			dNode = dNode.findNodeForElement(dNodeElem);
			if (dNode != null) {
				EObject assgnValue = dNode.assignedElem;
				EObject defaultValue = dNode.defaultExpr;
				return Pair.of(assgnValue, defaultValue);
			}
		}

		return null;
	}

	private static EObject getDNodeElem(EObject dNodeElem, EObject parent, EObject node) {
		if (dNodeElem != null) {
			return dNodeElem;
		}
		if (node instanceof BindingElement && parent instanceof BindingProperty) {
			return parent;
		}
		if (node instanceof BindingElement || node instanceof ArrayElement || node instanceof PropertyAssignment) {
			return node;
		}
		return null;
	}

	private static EObject getTopElem(EObject oldTopNode, EObject parent) {
		EObject newTopNode = null;
		if (parent instanceof ForStatement) {
			newTopNode = parent;
		}
		if (parent instanceof AssignmentExpression) {
			newTopNode = parent;
		}
		if (parent instanceof VariableBinding) {
			newTopNode = parent;
		}

		if (newTopNode != null) {
			return newTopNode;
		} else {
			return oldTopNode;
		}
	}

	public static List<Expression> getAllDeclaredIdRefs(EObject eobj) {
		DestructNode dnode = null;
		if (eobj instanceof ForStatement) {
			dnode = unify(eobj);
		}
		if (eobj instanceof VariableBinding) {
			dnode = unify(eobj);
		}
		if (eobj instanceof AssignmentExpression) {
			dnode = unify(eobj);
		}

		if (dnode == null) {
			return Collections.emptyList();
		}
		return dnode.getAllDeclaredIdRefs();
	}
}
