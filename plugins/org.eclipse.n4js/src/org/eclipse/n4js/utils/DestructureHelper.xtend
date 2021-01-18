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
package org.eclipse.n4js.utils

import com.google.common.base.Optional
import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.ArrayList
import java.util.Map
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.DestructNode
import org.eclipse.n4js.n4JS.DestructureUtils
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.ForStatement
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareMemberScope
import org.eclipse.n4js.scoping.members.MemberScopingHelper
import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.TField
import org.eclipse.n4js.ts.types.TGetter
import org.eclipse.n4js.ts.types.TStructMember
import org.eclipse.n4js.ts.types.TypesFactory
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.constraints.InferenceContext
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScope

import static extension org.eclipse.n4js.n4JS.DestructNode.arePositional
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 * Helper for dealing with destructuring patterns. For more details on destructuring patterns,
 * see documentation of class {@link DestructNode}.
 */
@Singleton
class DestructureHelper {

	@Inject private N4JSTypeSystem ts;
	@Inject private TypeSystemHelper tsh;
	@Inject private MemberScopingHelper memberScopingHelper;

	/**
	 * Infers the type of a variable declaration within a destructuring pattern from the value to be
	 * destructured and/or the default value given in the pattern.
	 * <p>
	 * Returns <code>null</code> if <code>vdecl</code> has an explicitly declared type or in case of error.
	 */
	public def TypeRef getTypeOfVariableDeclarationInDestructuringPattern(RuleEnvironment G, VariableDeclaration vdecl) {
		if(vdecl.declaredTypeRef!==null)
			return null;

		val root = DestructureUtils.getRoot(vdecl.eContainer);
		if(root===null)
			return null;

		val rootParent = root.eContainer;
		if(rootParent instanceof VariableBinding) {

			val rootParent2 = rootParent.eContainer;
			val isLocatedUnderForInOf = rootParent2 instanceof ForStatement
					&& DestructureUtils.isTopOfDestructuringForStatement(rootParent2);

			val rootNode = if(isLocatedUnderForInOf) {
				DestructNode.unify(rootParent2 as ForStatement)
			} else {
				DestructNode.unify(rootParent);
			};

			if(rootNode!==null) {

				val astNodesToConsider = EcoreUtilN4.getAllPredecessorsUpTo(vdecl, root);
				astNodesToConsider.add(vdecl);

				val valueTypePerNode = newHashMap;
				buildValueTypesMap(G, rootNode, Optional.of(astNodesToConsider), valueTypePerNode, rootParent.pattern);
				val node = rootNode.stream().filter[variableDeclaration===vdecl].findFirst.orElse(null);
				return valueTypePerNode.get(node);
			}
		}

		return null;
	}


	/**
	 * For a unified destructuring pattern created with one of the unify() methods in {@link DestructNode}, this method will
	 * return a mapping of the pattern's nodes to their value type, i.e. to the type of their value to be destructured.
	 * The returned map might not contain key/value pairs for all nodes (in case of error) but won't contain <code>null</code>
	 * values.
	 *
	 * @param G  rule environment (used for type inference).
	 * @param rootNode  root of the destructuring pattern.
	 * @param astNodesToConsider  if present, only {@link DestructNode}s are processed that have a {@link DestructNode#astElement
	 *                            corresponding AST node} contained in this set or do not have a corresponding AST node at all;
	 *                            if absent, all {@code DestructNode}s are processed. The given root node is always processed.
	 * @param addHere  types for each node will be added here.
	 * @param contextObj  context object (used for member scoping and obtaining the containing resource or resource set).
	 */
	public def void buildValueTypesMap(RuleEnvironment G, DestructNode rootNode, Optional<Set<EObject>> astNodesToConsider,
		Map<DestructNode,TypeRef> addHere, EObject contextObj) {

		if(rootNode?.defaultExpr===null) {
			return;
		}
		// in the root node, the defaultExpr is the main value to be destructured and
		// the node's type is simply the type of that value (nothing needs to be destructured yet)
		var valueTypeRef = ts.type(G,rootNode.defaultExpr);
		valueTypeRef = ts.upperBoundWithReopenAndResolve(G, valueTypeRef, true, true);
		// special case: ForStatement
		// we might have something like for([a,b] of expr){} (in which case rootNode.defaultExpr points to the 'expr')
		if(rootNode.defaultExpr.eContainer instanceof ForStatement) {
			// valueTypeRef is currently the type of 'expr' and thus something like Iterable<T>
			// -> we are interested in the T
			valueTypeRef = tsh.extractIterableElementType(G, valueTypeRef, false);
		}
		// now, valueTypeRef is the type of the root value to be destructured by rootNode.nestedNodes (or null if invalid)
		if(valueTypeRef!==null) {
			addHere.put(rootNode, valueTypeRef);
			val nestedNodes = rootNode.nestedNodes;
			if(nestedNodes!==null && !nestedNodes.empty) {
				buildValueTypesMap(G, nestedNodes, valueTypeRef, astNodesToConsider, addHere, contextObj);
			}
		}
	}

	private def void buildValueTypesMap(RuleEnvironment G, DestructNode[] nodes, TypeRef valueTypeRef,
		Optional<Set<EObject>> astNodesToConsider, Map<DestructNode,TypeRef> addHere, EObject contextObj) {

		// for all non-root nodes, we have to destructure the type of the value,
		// depending on whether we are in an array or object destructuring (sub-)pattern
		if(nodes.arePositional) {
			// positional
			val elementTypeRefs = tsh.extractIterableElementTypes(G,valueTypeRef);
			if(!elementTypeRefs.empty) {
				val maxIdx = elementTypeRefs.size-1;
				for(var idx=0;idx<nodes.size;idx++) {
					val currNode = nodes.get(idx);
					if (isToBeConsidered(currNode, astNodesToConsider)) {
						val currValueTypeRef = elementTypeRefs.get(Math.min(idx,maxIdx));
						addTypeAndContinueWithChildren(G, currNode, currValueTypeRef, astNodesToConsider, addHere, contextObj);
					}
				}
			}
		}
		else {
			// non-positional
			val memberScope = createMemberScopeForPropertyAccess(valueTypeRef, contextObj, false); // do not check visibility
			for(currNode : nodes) {
				if (isToBeConsidered(currNode, astNodesToConsider)) {
					val currValueTypeRef = getPropertyTypeForNode(G, valueTypeRef, memberScope, currNode.propName, null);
					if(currValueTypeRef!==null) {
						addTypeAndContinueWithChildren(G, currNode, currValueTypeRef, astNodesToConsider, addHere, contextObj);
					}
				}
			}
		}
	}

	/**
	 * Create a new member scope for use with method {@link #getPropertyTypeForNode(RuleEnvironment, TypeRef, IScope,
	 * String, StringBuffer) #getPropertyTypeForNode()}. Do not use the scope returned by this method for any other
	 * purpose (use methods in {@link MemberScopingHelper} instead)!
	 * <p>
	 * This is only provided as a separate method to avoid creating the same member scope over and over in case of
	 * multiple invocations of {@code #getPropertyTypeForNode()}.
	 *
	 * @param valueTypeRef
	 *               type of the value to be destructured.
	 * @param contextObj
	 *               context object used for (a) obtaining context resource and (b) visibility checking.
	 * @param checkVisibility
	 *               if true, the member scope will be wrapped in a {@link VisibilityAwareMemberScope}; if
	 *               false, method {@link getPropertyTypeForNode(IScope,String)} will <b>never</b> return
	 *               {@link #INVISIBLE_MEMBER}.
	 */
	public def IScope createMemberScopeForPropertyAccess(TypeRef receiverTypeRef, EObject contextObj, boolean checkVisibility) {
		val structFieldInitMode = receiverTypeRef.typingStrategy === TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
		return memberScopingHelper.createMemberScopeAllowingNonContainedMembers(receiverTypeRef, contextObj,
			checkVisibility, false, structFieldInitMode);
	}

	/**
	 * Returns type of a property within an object destructuring pattern or <code>null</code> if property does not exist.
	 * In case the property exists but is not available (e.g. not visible), an error message is appended to given
	 * StringBuffer 'errorMessage' (optional).
	 *
	 * @param parentValueTypeRef
	 *              value type of the parent node.
	 * @param parentMemberScope
	 *              a member scope as returned by method {@link #createMemberScopeForPropertyAccess(TypeRef,EObject,boolean)}.
	 * @param propName
	 *              name of property to look up.
	 * @param errorMessage
	 *              a string buffer where the error message will be stored in case the property exists but is not readable
	 *              or <code>null</code> if the caller is not interested in receiving error messages.
	 */
	public def TypeRef getPropertyTypeForNode(RuleEnvironment G, TypeRef parentValueTypeRef, IScope parentMemberScope, String propName, StringBuffer errorMessage) {
		if(parentValueTypeRef===null || parentMemberScope===null) {
			return null;
		}
		val mDesc = parentMemberScope.getSingleElement(QualifiedName.create(propName));
		if(mDesc instanceof AbstractDescriptionWithError) {
			if(errorMessage!==null) {
				errorMessage.append(mDesc.message);
			}
		}
		val m = mDesc?.getEObjectOrProxy();
		if(m!==null && !m.eIsProxy) {
			val result = switch(m) {
				TField: m.typeRef
				TGetter: m.declaredTypeRef
			};
			if(result!==null) {
				// substitute type variables in 'result'
				val G2 = G.wrap;
				tsh.addSubstitutions(G2, parentValueTypeRef);
				val resultSubst = ts.substTypeVariables(G2, result);
				return resultSubst;
			}
		}
		return null;
	}

	/**
	 * Following code factored out from #buildTypesMap(), because it is common to the positional
	 * and non-positional case. IMPORTANT: this method must also be called if valueTypeRef===null,
	 * because there might be a default expression in 'currNode' that provides a type.
	 */
	private def void addTypeAndContinueWithChildren(RuleEnvironment G, DestructNode currNode, TypeRef valueTypeRef,
		Optional<Set<EObject>> astNodesToConsider, Map<DestructNode,TypeRef> addHere, EObject contextObj) {

		val actualValueTypeRef = if(currNode.rest) {
			G.arrayTypeRef(valueTypeRef) // wrap in Array<> if rest operator used
		} else {
			valueTypeRef
		};
		val currTypeRef = mergeWithTypeOfDefaultExpression(G,actualValueTypeRef,currNode);
		if(currTypeRef!==null) {
			// add type of currNode
			addHere.put(currNode,currTypeRef);
			// continue with children of currNode
			if(currNode.nestedNodes!==null && !currNode.nestedNodes.empty) {
				// TODO should do this also if currTypeRef===null (because lower-level default expressions may provide further types)
				buildValueTypesMap(G, currNode.nestedNodes, currTypeRef, astNodesToConsider, addHere, contextObj);
			}
		}
	}

	/**
	 * Infers type of the default expression of 'currNode' and merges it with the given valueTypeRef.
	 * Both the given value type and inferred expression type may be null and then this returns null.
	 */
	private def TypeRef mergeWithTypeOfDefaultExpression(RuleEnvironment G, TypeRef valueTypeRef, DestructNode node) {
		val exprTypeRef = if(node.defaultExpr!==null) ts.type(G, node.defaultExpr);
		if(valueTypeRef!==null && exprTypeRef!==null) {
			// we have to merge the two types ...
			// (the small optimization with the subtype checks should be done by #createUnionType(), but isn't)
			return if(ts.subtypeSucceeded(G,valueTypeRef,exprTypeRef)) {
				exprTypeRef
			} else if(ts.subtypeSucceeded(G,exprTypeRef,valueTypeRef)) {
				valueTypeRef
			} else {
				tsh.createUnionType(G, valueTypeRef, exprTypeRef)
			};
		}
		else if(valueTypeRef!==null) {
			return valueTypeRef;
		}
		else if(exprTypeRef!==null) {
			return exprTypeRef;
		}
		return null;
	}

	private def boolean isToBeConsidered(DestructNode node, Optional<Set<EObject>> astNodesToConsider) {
		return node !== null
			&& (node.astElement === null || !astNodesToConsider.isPresent || astNodesToConsider.get().contains(node.astElement));
	}


	/**
	 * Return the expected type of a poly expression if it is used in a destructure pattern and null otherwise.
	 */
	public def TypeRef calculateExpectedType(Expression rootPoly, RuleEnvironment G, InferenceContext infCtx) {
		// In case of destructure pattern, we can calculate the expected type based on the structure of the destructure pattern.
		val rootDestructNode = if (rootPoly.eContainer instanceof VariableBinding) {
				DestructNode.unify(rootPoly.eContainer as VariableBinding)
			} else if (rootPoly.eContainer instanceof AssignmentExpression) {
				DestructNode.unify(rootPoly.eContainer as AssignmentExpression)
			} else if (rootPoly.eContainer instanceof ForStatement) {
				DestructNode.unify(rootPoly.eContainer as ForStatement)
			} else {
				null
			};
		if (rootDestructNode === null) {
			return null;
		}
		return rootDestructNode.calculateExpectedType(G, infCtx);
	}

	/**
	 * Calculate expected type of a destructure pattern based on its structure.
	 */
	private def TypeRef calculateExpectedType(DestructNode destructNode, RuleEnvironment G, InferenceContext infCtx) {
		val elementTypes = new ArrayList<TypeArgument>();
		val elementMembers = new ArrayList<TStructMember>();
		val elemCount = destructNode.nestedNodes.size
		for (nestedNode : destructNode.nestedNodes) {
			val elemExpectedType = if (nestedNode.nestedNodes !== null && nestedNode.nestedNodes.size > 0) {
				// Recursively calculate the expected type of the nested child
				calculateExpectedType(nestedNode, G, infCtx)
			} else {
				// Extract type of leaf node
				nestedNode.createTypeFromLeafDestructNode(G)
			}

			if (nestedNode.propName !== null) {
				// We are dealing with object literals, hence create TStructMembers to construct a ParameterizedTypeRefStructural.
				val field = TypesFactory.eINSTANCE.createTStructField
				field.name = nestedNode.propName;
				field.typeRef = if (elemExpectedType !== null) {
					elemExpectedType
				} else {
					// If the expected type is not specified, the expected type is arbitrary hence return a new inference variable.
					val iv = infCtx.newInferenceVariable;
					TypeUtils.createTypeRef(iv)
				}
				elementMembers.add(field)
			} else {
				if (elemExpectedType !== null) {
					elementTypes.add(elemExpectedType)
				} else {
					elementTypes.add(TypeRefsFactory.eINSTANCE.createWildcard)
				}
			}
		}


		var retTypeRef = if (elementMembers.size > 0) {
			if (elementTypes.size > 0) {
				 throw new IllegalStateException("elementTypes and elementMembers can not both contain elements at the same time.")
			}
			TypeUtils.createParameterizedTypeRefStructural(G.objectType, TypingStrategy.STRUCTURAL, elementMembers)
		} else if (elementTypes.size > 0) {
			if (elemCount == 1) {
				 G.arrayTypeRef(elementTypes.get(0))
			} else if (elemCount > 1){
				G.iterableNTypeRef(elemCount, elementTypes);
			} else {
				null
			}
		} else {
			null
		}
		// Wrap the expected type in an Iterable type in case of ForStatement
		// Note that we wrap the type into an Iterable type so that when a constraint G<out IV> <: Iterable<...> is created,
		// we would like to reduce it to IV <:..
		if (retTypeRef !== null && destructNode.astElement.eContainer instanceof ForStatement) {
			retTypeRef = G.iterableTypeRef(retTypeRef)
		}
		return retTypeRef;
	}

	/** Create expected type for a leaf DestructNode */
	private def createTypeFromLeafDestructNode(DestructNode leafNode, RuleEnvironment G) {
		val varDecl = leafNode.varDecl
		val varRef = leafNode.varRef
		if (varDecl !== null) {
			// If it is a variable declaration, simply retrieve the declared type
			var declaredTypeRef = varDecl.declaredTypeRef;
			if (declaredTypeRef !== null) {
				return declaredTypeRef
			}
		} else if (varRef !== null) {
			// It is a variable reference, retrieve the declared type of the variable
			if (varRef.id instanceof VariableDeclaration && (varRef.id as VariableDeclaration).declaredTypeRef !== null) {
				return (varRef.id as VariableDeclaration).declaredTypeRef
			}
		}
		// In case the expected type does not exist, simply return null
		return null
	}
}
