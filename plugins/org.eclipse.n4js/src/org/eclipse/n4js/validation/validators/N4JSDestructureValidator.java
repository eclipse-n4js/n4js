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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.n4js.n4JS.DestructNode.arePositional;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.iterableTypeRef;
import static org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.objectTypeRef;
import static org.eclipse.n4js.utils.UtilN4.trimPrefix;
import static org.eclipse.n4js.utils.UtilN4.trimSuffix;
import static org.eclipse.n4js.validation.IssueCodes.DESTRUCT_PROP_MISSING;
import static org.eclipse.n4js.validation.IssueCodes.DESTRUCT_PROP_WITH_ERROR;
import static org.eclipse.n4js.validation.IssueCodes.DESTRUCT_TYPE_ERROR_PATTERN;
import static org.eclipse.n4js.validation.IssueCodes.DESTRUCT_TYPE_ERROR_VAR;
import static org.eclipse.n4js.validation.IssueCodes.VIS_ILLEGAL_MEMBER_ACCESS;
import static org.eclipse.n4js.validation.IssueCodes.VIS_WRONG_READ_WRITE_ACCESS;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.n4js.n4JS.ArrayBindingPattern;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.BindingPattern;
import org.eclipse.n4js.n4JS.BindingProperty;
import org.eclipse.n4js.n4JS.DestructNode;
import org.eclipse.n4js.n4JS.DestructureUtils;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ForStatement;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ObjectBindingPattern;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.VariableBinding;
import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.types.utils.TypeUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.n4js.typesystem.utils.Result;
import org.eclipse.n4js.typesystem.utils.RuleEnvironment;
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions;
import org.eclipse.n4js.utils.DestructureHelper;
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.n4js.validation.IssueItem;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.EValidatorRegistrar;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.inject.Inject;

/**
 * Validations within destructuring patterns.
 */
@SuppressWarnings("javadoc")
public class N4JSDestructureValidator extends AbstractN4JSDeclarativeValidator {

	@Inject
	private N4JSTypeSystem ts;
	@Inject
	private DestructureHelper destructureHelper;

	/**
	 * NEEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	@Override
	public void register(EValidatorRegistrar registrar) {
		// nop
	}

	@Check
	public void checkNoEmptyPattern_Binding(BindingPattern pattern) {
		boolean isEmpty = false;
		if (pattern instanceof ArrayBindingPattern) {
			isEmpty = ((ArrayBindingPattern) pattern).getElements().isEmpty();
		}
		if (pattern instanceof ObjectBindingPattern) {
			isEmpty = ((ObjectBindingPattern) pattern).getProperties().isEmpty();
		}
		if (isEmpty) {
			addIssue(pattern, IssueCodes.DESTRUCT_EMPTY_PATTERN.toIssueItem());
		}
	}

	@Check
	public void checkNoEmptyPattern_Assignment(AssignmentExpression expr) {
		if (DestructureUtils.isTopOfDestructuringAssignment(expr)) {
			Expression lhs = expr.getLhs();
			boolean empty = false;
			if (lhs instanceof ArrayLiteral) {
				empty = ((ArrayLiteral) lhs).getElements().isEmpty();
			}
			if (lhs instanceof ObjectLiteral) {
				empty = isEmpty(filter(((ObjectLiteral) lhs).getPropertyAssignments(), PropertyNameValuePair.class));
			}
			if (empty) {
				addIssue(lhs, IssueCodes.DESTRUCT_EMPTY_PATTERN.toIssueItem());
			}
		}
	}

	@Check
	public void checkTypesInDestructPatternInVariableBinding(VariableBinding binding) {
		internal_checkDestructPattern(DestructNode.unify(binding), binding);
	}

	@Check
	public void checkTypesInDestructPatternInAssignmentExpression(AssignmentExpression expr) {
		internal_checkDestructPattern(DestructNode.unify(expr), expr);
	}

	@Check
	public void checkTypesInDestructPatternInForInOfStatement(ForStatement stmnt) {
		internal_checkDestructPattern(DestructNode.unify(stmnt), stmnt);
	}

	private void internal_checkDestructPattern(DestructNode rootNode, EObject contextObject) {
		if (rootNode == null) {
			// not a destructuring pattern
			// (an assignment expression without array/object literal on LHS, a broken AST, etc.)
			// -> ignore
			return;
		}
		RuleEnvironment G = RuleEnvironmentExtensions.newRuleEnvironment(contextObject);
		Map<DestructNode, TypeRef> valueTypePerNode = new HashMap<>();
		destructureHelper.buildValueTypesMap(G, rootNode, Optional.absent(), valueTypePerNode, contextObject);
		internal_checkDestructNode(G, null, rootNode, null, valueTypePerNode, contextObject);
	}

	/**
	 * @param parentNode
	 *            parent node of {@code node} or <code>null</code> if {@code node} is a root.
	 * @param parentMemberScope
	 *            member scope of parentNode or <code>null</code> if {@code node} is a root.
	 */
	private void internal_checkDestructNode(RuleEnvironment G, DestructNode parentNode, DestructNode node,
			IScope parentMemberScope, Map<DestructNode, TypeRef> valueTypePerNode, EObject contextObject) {
		// check currNode
		var isValid = holdsValidPropertyAccessInDestructNode(G, parentNode, node, parentMemberScope, valueTypePerNode)
				&& holdsCorrectTypeInDestructNode(G, parentNode, node, valueTypePerNode);

		// continue with children (but only if node is valid)
		if (isValid && node.nestedNodes != null && node.nestedNodes.length != 0) {
			TypeRef valueTypeRef = valueTypePerNode.get(node);
			if (valueTypeRef != null) {
				IScope memberScope = createMemberScope(node, valueTypeRef, contextObject);
				for (DestructNode childNode : node.nestedNodes) {
					if (childNode != null) {
						internal_checkDestructNode(G, node, childNode, memberScope, valueTypePerNode, contextObject);
					}
				}
			}
		}
	}

	private boolean holdsValidPropertyAccessInDestructNode(RuleEnvironment G, DestructNode parentNode,
			DestructNode node, IScope parentMemberScope, Map<DestructNode, TypeRef> valueTypePerNode) {
		if (node.propName != null && parentMemberScope != null) {
			// property names in object destructuring patterns constitute a property access without
			// a PropertyAccessExpression in the AST -> make sure property exists & is visible
			AtomicReference<AbstractDescriptionWithError> mDescRef = new AtomicReference<>();
			TypeRef propTypeRef = destructureHelper.getPropertyTypeForNode(G, valueTypePerNode.get(parentNode),
					parentMemberScope, node.propName, mDescRef);
			String errMsg = (mDescRef.get() == null) ? "" : mDescRef.get().getMessage();
			if (errMsg.length() > 0) {
				EObject astElement = node.astElement;
				String issueCode = mDescRef.get().getIssueCode();
				if (Set.of(VIS_ILLEGAL_MEMBER_ACCESS.name(), VIS_WRONG_READ_WRITE_ACCESS.name()).contains(issueCode)) {
					if (astElement instanceof BindingProperty
							&& !((BindingProperty) astElement).isSingleNameBinding()) {
						// handled elsewhere: var {fieldPublic: a, fieldPrivate: b} = cls;
						return true;
					}
					if (astElement instanceof PropertyNameValuePair
							&& ((PropertyNameValuePair) astElement).getProperty() != null) {
						// handled elsewhere: {fieldPublic: a, fieldPrivate: b} = cls;
						return true;
					}
				}

				Pair<EObject, EStructuralFeature> astNodeOfPropName = node.getEObjectAndFeatureForPropName();
				addIssue(astNodeOfPropName.getKey(), astNodeOfPropName.getValue(),
						DESTRUCT_PROP_WITH_ERROR.toIssueItem(node.propName, trimSuffix(errMsg.toString().trim(), ".")));
				return false;
			} else if (propTypeRef == null) {
				Pair<EObject, EStructuralFeature> astNodeOfPropName = node.getEObjectAndFeatureForPropName();
				TypeRef typeRef = valueTypePerNode.get(parentNode);
				addIssue(astNodeOfPropName.getKey(), astNodeOfPropName.getValue(), DESTRUCT_PROP_MISSING
						.toIssueItem(node.propName, typeRef == null ? null : typeRef.getTypeRefAsString()));
				return false;
			}
		}
		return true;
	}

	/**
	 * @param parentNode
	 *            parent node of {@code node} or <code>null</code> if {@code node} is a root.
	 */
	private boolean holdsCorrectTypeInDestructNode(RuleEnvironment G, DestructNode parentNode, DestructNode node,
			Map<DestructNode, TypeRef> valueTypePerNode) {
		TypeRef valueTypeRef = valueTypePerNode.get(node);
		if (valueTypeRef == null) {
			// valueTypeRef==null is ok, means "no type expectation" or "unknown type"
			return true;
		}
		if (node.varDecl != null || node.varRef != null) {
			// binding target is a newly declared or existing variable
			if (node.varDecl != null && node.varDecl.getDeclaredTypeRefInAST() == null) {
				// variable declared within pattern _without_ an explicitly declared type
				// -> ignore this case
				// (such a variable does not introduce any type expectation; instead,
				// its type is inferred from the type of the value to be destructured)
			} else {
				// variable declared within pattern _with_ an explicitly declared type OR
				// existing variable referenced from within a pattern
				// -> check if it can hold the corresponding value
				TypeRef variableTypeRef = null;
				if (node.varDecl != null) {
					variableTypeRef = ts.type(G, node.varDecl);
				} else if (node.varRef != null) {
					variableTypeRef = ts.type(G, node.varRef);
				}

				// exception case: if we have a defaultExpr AND it is of wrong type
				// -> suppress further checking to avoid confusing duplicate error message
				if (node.defaultExpr != null) {
					TypeRef defaultExprTypeRef = ts.type(G, node.defaultExpr);
					boolean isOfCorrectType = (defaultExprTypeRef != null)
							&& ts.subtypeSucceeded(G, defaultExprTypeRef, variableTypeRef);
					if (!isOfCorrectType) {
						return false;
					}
				}

				Result result = ts.subtype(G, valueTypeRef, variableTypeRef);
				if (result.isFailure()) {
					String varName = "<unnamed>";
					if (node.varDecl != null && node.varDecl.getName() != null) {
						varName = node.varDecl.getName();
					} else if (node.varRef != null && node.varRef.getId() != null
							&& node.varRef.getId().getName() != null) {
						varName = node.varRef.getId().getName();
					}
					String elemDesc = (node.isPositional())
							? "at index " + Arrays.asList(parentNode.nestedNodes).indexOf(node)
							: "of property '" + node.propName + "'";
					String tsMsg = trimSuffix(trimPrefix(result.getFailureMessage(), "failed: "), ".");
					IssueItem issueItem = DESTRUCT_TYPE_ERROR_VAR.toIssueItem(varName, elemDesc, tsMsg);
					if (node.varDecl != null) {
						addIssue(node.varDecl, N4JSPackage.eINSTANCE.getAbstractVariable_Name(), issueItem);
					} else {
						addIssue(node.varRef, issueItem);
					}
					return false;
				}
			}
		} else if (node.nestedNodes != null) {
			// binding target is a top-level or nested pattern
			// -> assert that we have an Iterable<?> or an Object depending on array/object destructuring
			// (keep consistent with Xsemantics rule 'expectedTypeOfRightSideInVariableBinding' and rule
			// 'expectedTypeOfOperandInAssignmentExpression' that are doing the same thing but on top-level)
			boolean isPositional = arePositional(Arrays.asList(node.nestedNodes));
			TypeRef expectedTypeRef = (isPositional)
					? iterableTypeRef(G, TypeRefsFactory.eINSTANCE.createWildcard())
					: objectTypeRef(G);
			Result result = ts.subtype(G, autoboxIfPrimitive(valueTypeRef), expectedTypeRef);

			if (result.isFailure()) {
				String patternKind;
				if (isPositional) {
					patternKind = (parentNode != null) ? "Nested array" : "Array";
				} else {
					patternKind = (parentNode != null) ? "Nested object" : "Object";
				}

				String elemDesc;
				if (parentNode != null) {
					if (node.isPositional()) {
						elemDesc = "destructured value at index " + Arrays.asList(parentNode.nestedNodes).indexOf(node);
					} else {
						elemDesc = "destructured value of property '" + node.propName + "'";
					}
				} else {
					elemDesc = "a value of type '" + valueTypeRef.getTypeRefAsString() + "'";
				}

				String tsMsg = trimSuffix(trimPrefix(result.getFailureMessage(), "failed: "), ".");
				IssueItem issueItem = DESTRUCT_TYPE_ERROR_PATTERN.toIssueItem(patternKind, elemDesc, tsMsg);
				EObject astElem = node.astElement;

				if (astElem instanceof PropertyNameValuePair) {
					addIssue(astElem, N4JSPackage.eINSTANCE.getPropertyNameValuePair_Expression(), issueItem);
				} else if (astElem instanceof BindingProperty) {
					addIssue(astElem, N4JSPackage.eINSTANCE.getBindingProperty_Value(), issueItem);
				} else if (astElem instanceof VariableBinding) {
					addIssue(astElem, N4JSPackage.eINSTANCE.getVariableBinding_Pattern(), issueItem);
				} else if (astElem instanceof AssignmentExpression) {
					addIssue(astElem, N4JSPackage.eINSTANCE.getAssignmentExpression_Lhs(), issueItem);
				} else if (astElem instanceof ForStatement) {
					ForStatement fs = (ForStatement) astElem;
					if (!fs.getVarDeclsOrBindings().isEmpty()) {
						addIssue(astElem, N4JSPackage.eINSTANCE.getVariableDeclarationContainer_VarDeclsOrBindings(),
								issueItem);
					} else if (fs.getInitExpr() != null) {
						addIssue(astElem, N4JSPackage.eINSTANCE.getForStatement_InitExpr(), issueItem);
					} else if (fs.getExpression() != null) {
						addIssue(astElem, N4JSPackage.eINSTANCE.getIterationStatement_Expression(), issueItem);
					} else {
						addIssue(astElem, issueItem);
					}
				} else {
					addIssue(astElem, issueItem);
				}

				return false;
			}
		}
		return true;
	}

	/**
	 * Create a member scope for looking up property names of the <em>children</em> of {@code node}, i.e. the
	 * {@code propName} attribute of the {@code nestedNodes} of {@code node}. Will return <code>null</code> if node does
	 * not have nested nodes or if they are positional (because then property look-up does not make sense).
	 */
	private IScope createMemberScope(DestructNode node, TypeRef valueTypeRef, EObject contextObject) {
		if (node.nestedNodes != null && node.nestedNodes.length == 0
				&& !arePositional(Arrays.asList(node.nestedNodes))) {
			// also check visibility
			return destructureHelper.createMemberScopeForPropertyAccess(valueTypeRef, contextObject, true);
		} else {
			return null;
		}
	}

	private TypeRef autoboxIfPrimitive(TypeRef typeRef) {
		Type declType = typeRef.getDeclaredType();
		if (declType instanceof PrimitiveType) {
			TClassifier autoboxedType = ((PrimitiveType) declType).getAutoboxedType();
			if (autoboxedType != null) {
				return TypeUtils.createTypeRef(autoboxedType);
			}
		}
		return typeRef;
	}
}
