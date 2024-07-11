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
package org.eclipse.n4js.utils;

import static org.eclipse.n4js.n4JS.DestructNode.arePositional;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.arrayTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iterableNTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iterableTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectType;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.wrap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.DestructNode;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.n4JS.TypedElement;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.postprocessing.ASTProcessor;
import org.eclipse.n4js.scoping.accessModifiers.VisibilityAwareMemberScope;
import org.eclipse.n4js.scoping.members.MemberScopingHelper;
import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.ts.typeRefs.TypeArgument;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.InferenceVariable;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TGetter;
import org.eclipse.n4js.ts.types.TStructField;
import org.eclipse.n4js.ts.types.TStructMember;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.TypesFactory;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.constraints.InferenceContext;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Helper for dealing with destructuring patterns. For more details on destructuring patterns, see documentation of
 * class {@link DestructNode}.
 */
@Singleton
public class DestructureHelper {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private TypeSystemHelper tsh;
	@Inject
	private MemberScopingHelper memberScopingHelper;

	/**
	 * Infers the type of a variable declaration within a destructuring pattern from the value to be destructured and/or
	 * the default value given in the pattern.
	 * <p>
	 * Returns <code>null</code> if <code>vdecl</code> has an explicitly declared type or in case of error.
	 */
	public TypeRef getTypeOfVariableDeclarationInDestructuringPattern(RuleEnvironment G, VariableDeclaration vdecl) {
		if (vdecl.getDeclaredTypeRef() != null) {
			return null;
		}

		EObject root = DestructureUtils.getRoot(vdecl.eContainer());
		if (root == null) {
			return null;
		}

		EObject rootParent = root.eContainer();
		if (rootParent instanceof VariableBinding) {

			EObject rootParent2 = rootParent.eContainer();
			boolean isLocatedUnderForInOf = rootParent2 instanceof ForStatement
					&& DestructureUtils.isTopOfDestructuringForStatement(rootParent2);

			DestructNode rootNode = (isLocatedUnderForInOf)
					? DestructNode.unify((ForStatement) rootParent2)
					: DestructNode.unify(rootParent);

			if (rootNode != null) {

				Set<EObject> astNodesToConsider = EcoreUtilN4.getAllPredecessorsUpTo(vdecl, root);
				astNodesToConsider.add(vdecl);

				Map<DestructNode, TypeRef> valueTypePerNode = new HashMap<>();
				buildValueTypesMap(G, rootNode, Optional.of(astNodesToConsider), valueTypePerNode,
						((VariableBinding) rootParent).getPattern());
				DestructNode node = rootNode.stream()
						.filter(it -> it.getVariableDeclaration() == vdecl)
						.findFirst()
						.orElse(null);
				return valueTypePerNode.get(node);
			}
		}

		return null;
	}

	/**
	 * For a unified destructuring pattern created with one of the unify() methods in {@link DestructNode}, this method
	 * will return a mapping of the pattern's nodes to their value type, i.e. to the type of their value to be
	 * destructured. The returned map might not contain key/value pairs for all nodes (in case of error) but won't
	 * contain <code>null</code> values.
	 *
	 * @param G
	 *            rule environment (used for type inference).
	 * @param rootNode
	 *            root of the destructuring pattern.
	 * @param astNodesToConsider
	 *            if present, only {@link DestructNode}s are processed that have a {@link DestructNode#astElement
	 *            corresponding AST node} contained in this set or do not have a corresponding AST node at all; if
	 *            absent, all {@code DestructNode}s are processed. The given root node is always processed.
	 * @param addHere
	 *            types for each node will be added here.
	 * @param contextObj
	 *            context object (used for member scoping and obtaining the containing resource or resource set).
	 */
	public void buildValueTypesMap(RuleEnvironment G, DestructNode rootNode, Optional<Set<EObject>> astNodesToConsider,
			Map<DestructNode, TypeRef> addHere, EObject contextObj) {

		if (rootNode == null || rootNode.defaultExpr == null) {
			return;
		}
		// in the root node, the defaultExpr is the main value to be destructured and
		// the node's type is simply the type of that value (nothing needs to be destructured yet)
		var valueTypeRef = ts.type(G, rootNode.defaultExpr);
		valueTypeRef = ts.upperBoundWithReopenAndResolveBoth(G, valueTypeRef);
		// special case: ForStatement
		// we might have something like for([a,b] of expr){} (in which case rootNode.defaultExpr points to the 'expr')
		if (rootNode.defaultExpr.eContainer() instanceof ForStatement) {
			// valueTypeRef is currently the type of 'expr' and thus something like Iterable<T>
			// -> we are interested in the T
			valueTypeRef = tsh.extractIterableElementType(G, valueTypeRef, false);
		}
		// now, valueTypeRef is the type of the root value to be destructured by rootNode.nestedNodes (or null if
		// invalid)
		if (valueTypeRef != null) {
			addHere.put(rootNode, valueTypeRef);
			DestructNode[] nestedNodes = rootNode.nestedNodes;
			if (nestedNodes != null && nestedNodes.length != 0) {
				buildValueTypesMap(G, nestedNodes, valueTypeRef, astNodesToConsider, addHere, contextObj);
			}
		}
	}

	private void buildValueTypesMap(RuleEnvironment G, DestructNode[] nodes, TypeRef valueTypeRef,
			Optional<Set<EObject>> astNodesToConsider, Map<DestructNode, TypeRef> addHere, EObject contextObj) {

		// for all non-root nodes, we have to destructure the type of the value,
		// depending on whether we are in an array or object destructuring (sub-)pattern
		if (arePositional(Arrays.asList(nodes))) {
			// positional
			List<TypeRef> elementTypeRefs = tsh.extractIterableElementTypes(G, valueTypeRef);
			if (!elementTypeRefs.isEmpty()) {
				int maxIdx = elementTypeRefs.size() - 1;
				for (var idx = 0; idx < nodes.length; idx++) {
					DestructNode currNode = nodes[idx];
					if (isToBeConsidered(currNode, astNodesToConsider)) {
						TypeRef currValueTypeRef = elementTypeRefs.get(Math.min(idx, maxIdx));
						addTypeAndContinueWithChildren(G, currNode, currValueTypeRef, astNodesToConsider, addHere,
								contextObj);
					}
				}
			}
		} else {
			// non-positional
			IScope memberScope = createMemberScopeForPropertyAccess(valueTypeRef, contextObj, false); // do not check
																										// visibility
			for (DestructNode currNode : nodes) {
				if (isToBeConsidered(currNode, astNodesToConsider)) {
					TypeRef currValueTypeRef = getPropertyTypeForNode(G, valueTypeRef, memberScope, currNode.propName,
							null);
					if (currValueTypeRef != null) {
						addTypeAndContinueWithChildren(G, currNode, currValueTypeRef, astNodesToConsider, addHere,
								contextObj);
					}
				}
			}
		}
	}

	/**
	 * Create a new member scope for use with method
	 * {@link #getPropertyTypeForNode(RuleEnvironment, TypeRef, IScope, String, AtomicReference)
	 * #getPropertyTypeForNode()}. Do not use the scope returned by this method for any other purpose (use methods in
	 * {@link MemberScopingHelper} instead)!
	 * <p>
	 * This is only provided as a separate method to avoid creating the same member scope over and over in case of
	 * multiple invocations of {@code #getPropertyTypeForNode()}.
	 *
	 * @param receiverTypeRef
	 *            type of the value to be destructured.
	 * @param contextObj
	 *            context object used for (a) obtaining context resource and (b) visibility checking.
	 * @param checkVisibility
	 *            if true, the member scope will be wrapped in a {@link VisibilityAwareMemberScope}; if false, method
	 *            {@link #getPropertyTypeForNode(RuleEnvironment, TypeRef, IScope, String, AtomicReference)} will
	 *            <b>never</b> return INVISIBLE_MEMBER.
	 */
	public IScope createMemberScopeForPropertyAccess(TypeRef receiverTypeRef, EObject contextObj,
			boolean checkVisibility) {
		boolean structFieldInitMode = receiverTypeRef
				.getTypingStrategy() == TypingStrategy.STRUCTURAL_FIELD_INITIALIZER;
		return memberScopingHelper.createMemberScopeAllowingNonContainedMembers(receiverTypeRef, contextObj,
				checkVisibility, false, structFieldInitMode);
	}

	/**
	 * Returns type of a property within an object destructuring pattern or <code>null</code> if property does not
	 * exist. In case the property exists but is not available (e.g. not visible), an error message is appended to given
	 * StringBuffer 'errorMessage' (optional).
	 *
	 * @param parentValueTypeRef
	 *            value type of the parent node.
	 * @param parentMemberScope
	 *            a member scope as returned by method
	 *            {@link #createMemberScopeForPropertyAccess(TypeRef,EObject,boolean)}.
	 * @param propName
	 *            name of property to look up.
	 */
	public TypeRef getPropertyTypeForNode(RuleEnvironment G, TypeRef parentValueTypeRef, IScope parentMemberScope,
			String propName, AtomicReference<AbstractDescriptionWithError> mDescRef) {
		if (parentValueTypeRef == null || parentMemberScope == null) {
			return null;
		}
		IEObjectDescription mDesc = parentMemberScope.getSingleElement(QualifiedName.create(propName));
		if (mDesc instanceof AbstractDescriptionWithError) {
			if (mDescRef != null) {
				mDescRef.set((AbstractDescriptionWithError) mDesc);
			}
		}
		EObject m = mDesc == null ? null : mDesc.getEObjectOrProxy();
		if (m != null && !m.eIsProxy()) {
			TypeRef result = null;
			if (m instanceof TField) {
				result = ((TField) m).getTypeRef();
			}
			if (m instanceof TGetter) {
				result = ((TGetter) m).getTypeRef();
			}
			if (result != null) {
				// substitute type variables in 'result'
				RuleEnvironment G2 = wrap(G);
				tsh.addSubstitutions(G2, parentValueTypeRef);
				TypeRef resultSubst = ts.substTypeVariables(G2, result);
				return resultSubst;
			}
		}
		return null;
	}

	/**
	 * Following code factored out from #buildTypesMap(), because it is common to the positional and non-positional
	 * case. IMPORTANT: this method must also be called if valueTypeRef==null, because there might be a default
	 * expression in 'currNode' that provides a type.
	 */
	private void addTypeAndContinueWithChildren(RuleEnvironment G, DestructNode currNode, TypeRef valueTypeRef,
			Optional<Set<EObject>> astNodesToConsider, Map<DestructNode, TypeRef> addHere, EObject contextObj) {

		TypeRef actualValueTypeRef = (currNode.rest)
				? arrayTypeRef(G, valueTypeRef) // wrap in Array<> if rest operator used
				: valueTypeRef;
		TypeRef currTypeRef = mergeWithTypeOfDefaultExpression(G, actualValueTypeRef, currNode);
		if (currTypeRef != null) {
			// add type of currNode
			addHere.put(currNode, currTypeRef);
			// continue with children of currNode
			if (currNode.nestedNodes != null && currNode.nestedNodes.length != 0) {
				// TODO should do this also if currTypeRef==null (because lower-level default expressions may provide
				// further types)
				buildValueTypesMap(G, currNode.nestedNodes, currTypeRef, astNodesToConsider, addHere, contextObj);
			}
		}
	}

	/**
	 * Infers type of the default expression of 'currNode' and merges it with the given valueTypeRef. Both the given
	 * value type and inferred expression type may be null and then this returns null.
	 */
	private TypeRef mergeWithTypeOfDefaultExpression(RuleEnvironment G, TypeRef valueTypeRef, DestructNode node) {
		if (node.defaultExpr instanceof ObjectLiteral || node.defaultExpr instanceof ArrayLiteral) {
			// Example: const { prop = {} } = new C1(); // type should be C2; with: class C1 { prop : C2 }
			return valueTypeRef;
		}
		TypeRef exprTypeRef = null;
		if (node.defaultExpr != null) {
			exprTypeRef = ts.type(G, node.defaultExpr);
		}
		if (valueTypeRef != null && exprTypeRef != null) {
			// we have to merge the two types ...
			// (the small optimization with the subtype checks should be done by #createUnionType(), but isn't)
			if (ts.subtypeSucceeded(G, valueTypeRef, exprTypeRef)) {
				return exprTypeRef;
			} else if (ts.subtypeSucceeded(G, exprTypeRef, valueTypeRef)) {
				return valueTypeRef;
			} else {
				return tsh.createUnionType(G, valueTypeRef, exprTypeRef);
			}
		} else if (valueTypeRef != null) {
			return valueTypeRef;
		} else if (exprTypeRef != null) {
			return exprTypeRef;
		}
		return null;
	}

	private boolean isToBeConsidered(DestructNode node, Optional<Set<EObject>> astNodesToConsider) {
		return node != null
				&& (node.astElement == null || !astNodesToConsider.isPresent()
						|| astNodesToConsider.get().contains(node.astElement));
	}

	/**
	 * Return the expected type of a poly expression if it is used in a destructure pattern and null otherwise.
	 */
	public TypeRef calculateExpectedType(Expression rootPoly, RuleEnvironment G, InferenceContext infCtx) {
		// In case of destructure pattern, we can calculate the expected type based on the structure of the destructure
		// pattern.
		DestructNode rootDestructNode = null;
		if (rootPoly.eContainer() instanceof VariableBinding) {
			rootDestructNode = DestructNode.unify((VariableBinding) rootPoly.eContainer());
		} else if (rootPoly.eContainer() instanceof AssignmentExpression) {
			rootDestructNode = DestructNode.unify((AssignmentExpression) rootPoly.eContainer());
		} else if (rootPoly.eContainer() instanceof ForStatement) {
			rootDestructNode = DestructNode.unify((ForStatement) rootPoly.eContainer());
		}
		if (rootDestructNode == null) {
			return null;
		}
		return calculateExpectedType(rootDestructNode, G, infCtx);
	}

	/**
	 * Calculate expected type of a destructure pattern based on its structure.
	 */
	private TypeRef calculateExpectedType(DestructNode destructNode, RuleEnvironment G, InferenceContext infCtx) {
		List<TypeArgument> elementTypes = new ArrayList<>();
		List<TStructMember> elementMembers = new ArrayList<>();
		int elemCount = destructNode.nestedNodes.length;
		for (DestructNode nestedNode : destructNode.nestedNodes) {
			TypeRef elemExpectedType = (nestedNode.nestedNodes != null && nestedNode.nestedNodes.length > 0)
					? // Recursively calculate the expected type of the nested child
					calculateExpectedType(nestedNode, G, infCtx)
					:
					// Extract type of leaf node
					createTypeFromLeafDestructNode(nestedNode, G);

			if (nestedNode.propName != null) {
				// We are dealing with object literals, hence create TStructMembers to construct a
				// ParameterizedTypeRefStructural.
				TStructField field = TypesFactory.eINSTANCE.createTStructField();
				field.setName(nestedNode.propName);
				if (elemExpectedType != null) {
					field.setTypeRef(TypeUtils.copyIfContained(elemExpectedType));
				} else {
					// If the expected type is not specified, the expected type is arbitrary hence return a new
					// inference variable.
					InferenceVariable iv = infCtx.newInferenceVariable();
					field.setTypeRef(TypeUtils.createTypeRef(iv));
				}
				elementMembers.add(field);
			} else {
				if (elemExpectedType != null) {
					elementTypes.add(elemExpectedType);
				} else {
					// If the expected type is not specified, the expected type is arbitrary hence return a new
					// inference variable.
					InferenceVariable iv = infCtx.newInferenceVariable();
					elementTypes.add(TypeUtils.createTypeRef(iv));
				}
			}
		}

		TypeRef retTypeRef = null;

		if (elementMembers.size() > 0) {
			if (elementTypes.size() > 0) {
				throw new IllegalStateException(
						"elementTypes and elementMembers can not both contain elements at the same time.");
			}
			retTypeRef = TypeUtils.createParameterizedTypeRefStructural(objectType(G), TypingStrategy.STRUCTURAL,
					elementMembers.toArray(new TStructMember[0]));
		} else if (elementTypes.size() > 0) {
			if (elemCount == 1) {
				retTypeRef = arrayTypeRef(G, elementTypes.get(0));
			} else if (elemCount > 1) {
				retTypeRef = iterableNTypeRef(G, elemCount, elementTypes.toArray(new TypeArgument[0]));
			}
		}
		// Wrap the expected type in an Iterable type in case of ForStatement
		// Note that we wrap the type into an Iterable type so that when a constraint G<out IV> <: Iterable<...> is
		// created,
		// we would like to reduce it to IV <:..
		if (retTypeRef != null && DestructureUtils.isTopOfDestructuringForStatement(destructNode.astElement)) {
			retTypeRef = iterableTypeRef(G, retTypeRef);
		}
		return retTypeRef;
	}

	/** Create expected type for a leaf DestructNode */
	private TypeRef createTypeFromLeafDestructNode(DestructNode leafNode, RuleEnvironment G) {
		VariableDeclaration varDecl = leafNode.varDecl;
		IdentifierRef varRef = leafNode.varRef;
		if (varDecl != null) {
			// If it is a variable declaration, simply retrieve the declared type
			TypeRef declTypeRef = getDeclaredTypeRefOfVarDecl(G, varDecl);
			if (declTypeRef != null) {
				return declTypeRef;
			}
		} else if (varRef != null) {
			// It is a variable reference, retrieve the (declared or inferred) type of the variable
			IdentifiableElement id = varRef.getId();
			if (id instanceof VariableDeclaration || id instanceof TVariable) {
				return ts.type(G, id);
			}
		}
		// In case the expected type does not exist, simply return null
		return null;
	}

	/**
	 * This is invoked from {@code PolyProcessor#inferType(RuleEnvironment, Expression, ASTMetaInfoCache)} and in case
	 * of for-of loops it may inspect its variable declaration before {@code TypeRefProcessor} has processed that
	 * variable declaration (see order defined for ForStatement in {@link ASTProcessor#childrenToBeProcessed(EObject)}).
	 * Therefore, we cannot rely on {@link TypedElement#getDeclaredTypeRef()} here.
	 */
	private TypeRef getDeclaredTypeRefOfVarDecl(RuleEnvironment G, VariableDeclaration varDecl) {
		TypeReferenceNode<TypeRef> typeRefNode = varDecl.getDeclaredTypeRefNode();
		if (typeRefNode != null) {
			TypeRef cached = typeRefNode.getCachedProcessedTypeRef();
			if (cached != null) {
				return cached;
			}
			TypeRef inAST = typeRefNode.getTypeRefInAST();
			if (inAST != null) {
				return tsh.resolveTypeAliases(G, inAST);
			}
		}
		return null;
	}
}
