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
package org.eclipse.n4js.n4JS

import java.util.Iterator
import java.util.LinkedList
import java.util.List
import java.util.stream.Stream
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.xtend.lib.annotations.Data

/**
 * Destructuring patterns can appear in very different forms within the AST and in different contexts.
 * This helper class is used to transform those heterogeneous representations into a single, uniform
 * structure, that can be traversed more easily.
 * 
 * All fields are optional, i.e. may be 'null'. At most one of 'varRef', 'varDecl' and 'nestedPattern'
 * may be non-null; if all three are 'null' the node is a padding node.
 * 
 * <h2>Overview of Destructuring Patterns in the AST</h2>
 * Different forms:
 * <ol>
 * <li>as a {@link BindingPattern} (may contain nested {@code BindingPattern}s).
 * <li>as an {@link ArrayLiteral} (may contain nested patterns in form of {@code ArrayLiteral}s or {@code ObjectLiteral}s).
 * <li>as an {@link ObjectLiteral} (may contain nested patterns in form of {@code ArrayLiteral}s or {@code ObjectLiteral}s).
 * </ol>
 * Different contexts:
 * <ol>
 * <li>within a {@link VariableStatement} (then contained in a {@link VariableBinding}, which is an alternative
 *     to a {@link VariableDeclaration}).
 * <li>within an {@link AssignmentExpression} (then it appears as the left-hand side expression)
 * <li>within a {@link ForStatement} (only in for..in and for..of, because if used in plain for it is a use case
 *     of a variable statement or assignment expression inside the for statement).
 * <li><b>NOT SUPPORTED YET</b>: in the context of lists of formal parameters or function argument lists
 * </ol>
 * The above 6 use cases have several special characteristics and constraints, most of which are unified in this class.
 * It might be possible to generate more unified patterns in the parser, but the above situation is more in line with
 * terminology in the ES6 specification.
 * <p>
 */
@Data
public class DestructNode {
	EObject astElement;
	String propName; // property name (iff in object destructuring pattern) or 'null' (iff in array destructuring pattern)
	IdentifierRef varRef;
	VariableDeclaration varDecl;
	DestructNode[] nestedNodes; // nested pattern that will be bound/assigned (or 'null' iff 'varName' is non-null)
	Expression defaultExpr;
	EObject assignedElem; // can be an Expression or an IdentifiableElement (in case of Getter/Setter/Method)
	boolean rest;

	/**
	 * Tells if receiving node belongs to a positional destructuring pattern
	 * (i.e. an array destructuring pattern).
	 */
	def boolean isPositional() {
		propName === null
	}

	/**
	 * Tells if the given nodes belong to a positional destructuring pattern
	 * (i.e. an array destructuring pattern).
	 */
	static def boolean arePositional(DestructNode[] nodes) {
		nodes !== null && nodes.exists[positional]
	}

	/**
	 * Tells if this is a padding node.
	 */
	def boolean isPadding() {
		varRef === null && varDecl === null && nestedNodes === null
	}

	/**
	 * If this node has a reference to a variable or a variable declaration,
	 * returns the variable's name, <code>null</code> otherwise.
	 */
	def String varName() {
		if (varRef !== null)
			varRef.id?.name
		else if (varDecl !== null)
			varDecl.name
	}

	/**
	 * Returns the variable declaration contained in this node's astElement or <code>null</code>.
	 */
	def VariableDeclaration getVariableDeclaration() {
		switch (astElement) {
			BindingElement:
				astElement.varDecl
			BindingProperty case astElement.value !== null:
				astElement.value.varDecl
		}
	}

	/**
	 * Returns the AST node and EStructuralFeature to be used when showing an error message
	 * on the receiving node's propName attribute. Intended for issue generation in validations.
	 */
	def Pair<EObject, EStructuralFeature> getEObjectAndFeatureForPropName() {
		if (propName !== null) {
			switch (astElement) {
				PropertyNameValuePairSingleName case astElement.expression instanceof AssignmentExpression:
					astElement.expression -> N4JSPackage.eINSTANCE.assignmentExpression_Lhs
				PropertyNameValuePairSingleName:
					astElement -> N4JSPackage.eINSTANCE.propertyNameValuePair_Expression
				BindingProperty case astElement.declaredName !== null:
					astElement -> N4JSPackage.eINSTANCE.propertyNameOwner_DeclaredName
				BindingProperty case astElement.value?.varDecl?.name !== null:
					astElement.value.varDecl -> TypesPackage.eINSTANCE.identifiableElement_Name
				PropertyNameOwner:
					astElement -> N4JSPackage.eINSTANCE.propertyNameOwner_DeclaredName
				default:
					astElement -> null // show error on entire node
			}
		} else {
			astElement -> null // show error on entire node
		}
	}

	/**
	 * Returns the node with the given <code>astElement</code>.
	 */
	def DestructNode findNodeForElement(EObject astElement) {
		stream.filter[it.astElement === astElement].findFirst.orElse(null)
	}

	/**
	 * Returns stream of this node and all its descendants, i.e. directly and indirectly nested nodes.
	 */
	def Stream<DestructNode> stream() {
		if (nestedNodes === null || nestedNodes.empty) {
			Stream.of(this)
		} else {
			Stream.concat(Stream.of(this), Stream.of(nestedNodes).flatMap[stream]);
		}
	}

	public static def DestructNode unify(EObject eobj) {
		return switch (eobj) {
			VariableBinding: unify(eobj)
			AssignmentExpression: unify(eobj)
			ForStatement: unify(eobj)
			default: null
		}
	}

	/**
	 * Returns a unified copy of the given destructuring pattern or <code>null</code> if it is invalid.
	 * This is helpful because these patterns can appear in very different forms and locations within the AST.
	 */
	public static def DestructNode unify(EObject lhs, Expression rhs) {
			new DestructNode(
				lhs, // astElement
				null, // propName
				null, // varRef
				null, // varDecl
				toEntries(lhs, rhs), // nestedNodes
				rhs, // defaultExpr
				rhs, // assignedExpr
				false // rest
			)
	}

	/**
	 * Returns a unified copy of the given destructuring pattern or <code>null</code> if it is invalid.
	 * This is helpful because these patterns can appear in very different forms and locations within the AST.
	 */
	public static def DestructNode unify(VariableBinding binding) {
		if (binding !== null && binding.pattern !== null // note: binding.expression is mandatory in variable statements but optional in for..in/of statements
			&& (binding.expression !== null || binding.eContainer instanceof ForStatement
		)) {
			unify(binding.pattern, binding.expression);
		}
	}

	/**
	 * Returns a unified copy of the given destructuring pattern or <code>null</code> if it is invalid.
	 * This is helpful because these patterns can appear in very different forms and locations within the AST.
	 */
	public static def DestructNode unify(AssignmentExpression expr) {
		if (expr !== null && expr.lhs !== null && expr.rhs !== null
			&& DestructureUtils.isTopOfDestructuringAssignment(expr)
		) {
			unify(expr.lhs, expr.rhs);
		}
	}

	/**
	 * Returns a unified copy of the given destructuring pattern or <code>null</code> if it is invalid.
	 * This is helpful because these patterns can appear in very different forms and locations within the AST.
	 */
	public static def DestructNode unify(ForStatement stmnt) {
		if (stmnt !== null && DestructureUtils.isTopOfDestructuringForStatement(stmnt)) {
			val valueToBeDestructured = if (stmnt.forOf) {
					stmnt.expression
				} else if (stmnt.forIn) {
					N4JSFactory.eINSTANCE.createStringLiteral
				} else {
					// impossible because #isDestructuringForStatement() returned true
					throw new IllegalStateException
				};

			if (DestructureUtils.containsDestructuringPattern(stmnt)) {
				// case: for(var [a,b] of arr) {}
				val binding = stmnt.varDeclsOrBindings.filter(VariableBinding).head;
				new DestructNode(
					binding, // astElement
					null, // propName
					null, // varRef
					null, // varDecl
					toEntries(binding.pattern, stmnt.expression), // nestedNodes
					valueToBeDestructured, // defaultExpr
					valueToBeDestructured, // assignedExpr
					false // rest
				)
			} else if (DestructureUtils.isObjectOrArrayLiteral(stmnt.getInitExpr())) {
				// case: for([a,b] of arr) {}
				new DestructNode(
					stmnt.initExpr, // astElement
					null, // propName
					null, // varRef
					null, // varDecl
					toEntries(stmnt.initExpr, null), // nestedNodes
					valueToBeDestructured, // defaultExpr
					valueToBeDestructured, // assignedExpr
					false // rest
				)
			}
		}
	}

	private static def DestructNode[] toEntries(EObject pattern, EObject rhs) {

		val Iterator<? extends EObject> patElemIter = switch (pattern) {
			ArrayLiteral:
				pattern.elements.iterator
			ObjectLiteral:
				pattern.propertyAssignments.iterator
			ArrayBindingPattern:
				pattern.elements.iterator
			ObjectBindingPattern:
				pattern.properties.iterator
		}

		var Iterator<? extends EObject> rhsElemIter = switch (rhs) {
			ArrayLiteral:
				rhs.elements.iterator
			ObjectLiteral:
				rhs.propertyAssignments.iterator
		}

		val nestedDNs = new BasicEList<DestructNode>();
		while (patElemIter.hasNext) {
			val patElem = patElemIter.next;
			val litElem = if (rhsElemIter === null) rhs else if (rhsElemIter.hasNext) rhsElemIter.next else null;

			val nestedNode = switch (patElem) {
				ArrayElement:
					toEntry(patElem, litElem)
				PropertyNameValuePair:
					toEntry(patElem, litElem)
				BindingElement:
					toEntry(patElem, litElem)
				BindingProperty:
					toEntry(patElem, litElem)
			}

			if (nestedNode !== null) {
				nestedDNs.add(nestedNode);
			}
		}
		return nestedDNs;
	}

	private static def DestructNode toEntry(ArrayElement elem, EObject rhs) {
		val EObject rhsExpr = if (rhs instanceof ArrayElement) rhs.expression else rhs;
		val expr = elem.expression; // note: ArrayPadding will return null for getExpression()
		if (expr instanceof AssignmentExpression)
			toEntry(elem, null, expr.lhs, expr.rhs, elem.spread, rhsExpr)
		else
			toEntry(elem, null, expr, null, elem.spread, rhsExpr)
	}

	private static def DestructNode toEntry(PropertyNameValuePair pa, EObject rhs) {
		val EObject rhsExpr = if (rhs instanceof PropertyNameValuePair) rhs.expression else rhs;
		val expr = pa.expression;
		if (expr instanceof AssignmentExpression)
			toEntry(pa, pa.name, expr.lhs, expr.rhs, false, rhsExpr)
		else
			toEntry(pa, pa.name, expr, null, false, rhsExpr)
	}

	private static def DestructNode toEntry(BindingElement elem, EObject rhs) {
		val EObject expr = if (rhs instanceof ArrayElement) rhs.expression else rhs;

		if (elem.varDecl !== null)
			toEntry(elem, null, elem.varDecl, elem.varDecl.expression, elem.rest, expr)
		else if (elem.nestedPattern !== null)
			toEntry(elem, null, elem.nestedPattern, elem.expression, elem.rest, expr)
		else
			toEntry(elem, null, null, null, false, expr) // return dummy entry to not break indices
	}

	private static def DestructNode toEntry(BindingProperty prop, EObject rhs) {
		if (prop.value?.varDecl !== null) {
			val expr = getPropertyAssignmentExpression(rhs);
			toEntry(prop, prop.name, prop.value.varDecl, prop.value.varDecl.expression, false, expr)

		} else if (prop.value?.nestedPattern !== null) {
			val expr = getPropertyAssignmentExpression(rhs);
			toEntry(prop, prop.name, prop.value.nestedPattern, prop.value.expression, false, expr)

		} else {
			toEntry(prop, null, null, null, false, rhs)
		}
	}

	/**
	 * @param bindingTarget
	 *              an IdentifierRef/VariableDeclaration or a nested pattern (which may be
	 *              a BindingPattern, ArrayLiteral, or ObjectLiteral)
	 */
	private static def DestructNode toEntry(EObject astElement, String propName, EObject bindingTarget,
		Expression defaultExpr, boolean rest, EObject rhs) {

		if (bindingTarget === null) {
			// no target -> create a padding node
			new DestructNode(astElement, propName, null, null, null, defaultExpr, null, rest)

		} else if (bindingTarget instanceof IdentifierRef) {
			new DestructNode(astElement, propName, bindingTarget, null, null, defaultExpr, rhs, rest)

		} else if (bindingTarget instanceof VariableDeclaration) {
			new DestructNode(astElement, propName, null, bindingTarget, null, defaultExpr, rhs, rest)

		} else if (bindingTarget instanceof ArrayLiteral || bindingTarget instanceof ObjectLiteral ||
			bindingTarget instanceof BindingPattern) {
			new DestructNode(astElement, propName, null, null, toEntries(bindingTarget, rhs), defaultExpr, rhs, rest)

		} else {
			// invalid binding target (probably a corrupt AST) -> create a padding node
			new DestructNode(astElement, propName, null, null, null, defaultExpr, null, rest)
		}
	}

	/** @return the expression or function of the given PropertyAssignment */
	private static def EObject getPropertyAssignmentExpression(EObject rhs) {
		switch (rhs) {
			PropertyGetterDeclaration:
				return rhs.definedFunctionOrAccessor
			PropertySetterDeclaration:
				return rhs.definedFunctionOrAccessor
			PropertyMethodDeclaration:
				return rhs.definedFunctionOrAccessor
			PropertyNameValuePair:
				return rhs.expression
			PropertyAssignmentAnnotationList:
				return null
			default:
				return rhs
		}
	}

	/** @return all {@link IdentifierRef} of variables that are written in the given assignment */
	public def List<Expression> getAllDeclaredIdRefs() {
		val List<Expression> idRefs = new LinkedList();
		val Iterator<DestructNode> allNestedNodes = this.stream().iterator();

		while (allNestedNodes.hasNext()) {
			val EObject eobj = allNestedNodes.next().getAstElement();
			if (eobj instanceof ArrayElement) {
				val Expression expr = eobj.getExpression();
				if (expr instanceof AssignmentExpression) {
					idRefs.add((expr.getLhs()));
				} else {
					idRefs.add(expr);
				}

			} else if (eobj instanceof PropertyNameValuePairSingleName) {
				val idRef = eobj.getIdentifierRef();
				if (idRef !== null) {
					idRefs.add(idRef);
				}

			} else if (eobj instanceof PropertyNameValuePair) {
				val Expression expr = eobj.getExpression();
				if (expr instanceof AssignmentExpression) {
					idRefs.add(expr.getLhs());
				} else {
					idRefs.add(expr);
				}
			}
		}
		return idRefs;
	}

	/** @return a pair where its key is the assigned EObject and its value is the default EObject to the given lhs AST element */
	public static def Pair<EObject, EObject> getValueFromDestructuring(EObject nodeElem) {
		var EObject node = nodeElem;
		var EObject topNode = null;
		var EObject dNodeElem = null;
		var boolean breakSearch = false;

		while (!breakSearch) {
			var EObject parent = node.eContainer();
			dNodeElem = getDNodeElem(dNodeElem, parent, node);
			topNode = getTopElem(topNode, parent);
			breakSearch = parent instanceof Statement;
			node = parent;
		}

		var DestructNode dNode = if (topNode instanceof AssignmentExpression) {
				DestructNode.unify(topNode);
			} else if (topNode instanceof VariableBinding) {
				DestructNode.unify(topNode);
			} else if (topNode instanceof ForStatement) {
				DestructNode.unify(topNode);
			} else {
				null;
			};

		if (dNode !== null) {
			dNode = dNode.findNodeForElement(dNodeElem);
			if (dNode !== null) {
				var EObject assgnValue = dNode.getAssignedElem();
				var EObject defaultValue = dNode.getDefaultExpr();
				return assgnValue -> defaultValue;
			}
		}

		return null;
	}

	private static def EObject getDNodeElem(EObject dNodeElem, EObject parent, EObject node) {
		if (dNodeElem !== null) {
			return dNodeElem;
		}
		if (node instanceof BindingElement && parent instanceof BindingProperty) {
			return parent;
		}
		if (node instanceof BindingElement || node instanceof ArrayElement || node instanceof PropertyAssignment) {
			return node;
		}
	}

	private static def EObject getTopElem(EObject oldTopNode, EObject parent) {
		val EObject newTopNode = switch (parent) {
			ForStatement:
				parent
			AssignmentExpression:
				parent
			VariableBinding:
				parent
			default:
				null
		};

		if (newTopNode !== null) {
			return newTopNode
		} else {
			oldTopNode;
		}
	}

	public static def List<Expression> getAllDeclaredIdRefs(EObject eobj) {
		val DestructNode dnode = switch (eobj) {
			ForStatement:
				unify(eobj)
			VariableBinding:
				unify(eobj)
			AssignmentExpression:
				unify(eobj)
			default:
				null
		};

		if (dnode === null) {
			return #[];
		}

		return dnode.allDeclaredIdRefs;
	}
}
