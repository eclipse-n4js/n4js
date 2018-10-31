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
package org.eclipse.n4js.validation.validators

import com.google.inject.Inject
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.ArrayBindingPattern
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.AssignmentExpression
import org.eclipse.n4js.n4JS.BindingPattern
import org.eclipse.n4js.n4JS.BindingProperty
import org.eclipse.n4js.n4JS.DestructNode
import org.eclipse.n4js.n4JS.DestructureUtils
import org.eclipse.n4js.n4JS.ForStatement
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.ObjectBindingPattern
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName
import org.eclipse.n4js.n4JS.VariableBinding
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.utils.DestructureHelper
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.validation.IssueCodes.*

import static extension org.eclipse.n4js.n4JS.DestructNode.arePositional
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static extension org.eclipse.n4js.utils.UtilN4.trimPrefix
import static extension org.eclipse.n4js.utils.UtilN4.trimSuffix

/**
 * Validations within destructuring patterns.
 */
class N4JSDestructureValidator extends AbstractN4JSDeclarativeValidator {

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
	override register(EValidatorRegistrar registrar) {
		// nop
	}


	@Check
	def public void checkNoEmptyPattern_Binding(BindingPattern pattern) {
		val isEmpty = switch(pattern) {
			ArrayBindingPattern: pattern.elements.empty
			ObjectBindingPattern: pattern.properties.empty
		};
		if(isEmpty) {
			val message = IssueCodes.messageForDESTRUCT_EMPTY_PATTERN;
			addIssue(message, pattern, IssueCodes.DESTRUCT_EMPTY_PATTERN);
		}
	}

	@Check
	def public void checkNoEmptyPattern_Assignment(AssignmentExpression expr) {
		if(DestructureUtils.isTopOfDestructuringAssignment(expr)) {
			val lhs = expr.lhs;
			val empty = switch(lhs) {
			ArrayLiteral: lhs.elements.empty
			ObjectLiteral: lhs.propertyAssignments.filter(PropertyNameValuePair).empty
			}
			if(empty) {
				val message = IssueCodes.messageForDESTRUCT_EMPTY_PATTERN;
				addIssue(message, lhs, IssueCodes.DESTRUCT_EMPTY_PATTERN);
			}
		}
	}

	@Check
	def void checkTypesInDestructPatternInVariableBinding(VariableBinding binding) {
		internal_checkDestructPattern(DestructNode.unify(binding), binding);
	}

	@Check
	def void checkTypesInDestructPatternInAssignmentExpression(AssignmentExpression expr) {
		internal_checkDestructPattern(DestructNode.unify(expr), expr);
	}

	@Check
	def void checkTypesInDestructPatternInForInOfStatement(ForStatement stmnt) {
		internal_checkDestructPattern(DestructNode.unify(stmnt), stmnt);
	}

	private def void internal_checkDestructPattern(DestructNode rootNode, EObject contextObject) {
		if(rootNode===null) {
			// not a destructuring pattern
			// (an assignment expression without array/object literal on LHS, a broken AST, etc.)
			// -> ignore
			return;
		}
		val G = RuleEnvironmentExtensions.newRuleEnvironment(contextObject);
		val valueTypePerNode = newHashMap;
		destructureHelper.buildValueTypesMap(G, rootNode, valueTypePerNode, contextObject);
		internal_checkDestructNode(G, null, rootNode, null, valueTypePerNode, contextObject);
	}

	/**
	 * @param parentNode
	 *             parent node of {@code node} or <code>null</code> if {@code node} is a root.
	 * @param parentMemberScope
	 *             member scope of parentNode or <code>null</code> if {@code node} is a root.
	 */
	private def void internal_checkDestructNode(RuleEnvironment G, DestructNode parentNode, DestructNode node, IScope parentMemberScope, Map<DestructNode,TypeRef> valueTypePerNode, EObject contextObject) {
		// check currNode
		var isValid = holdsValidPropertyAccessInDestructNode(G, parentNode, node, parentMemberScope, valueTypePerNode)
				&& holdsCorrectTypeInDestructNode(G, parentNode, node, valueTypePerNode);

		// continue with children (but only if node is valid)
		if(isValid && node.nestedNodes!==null && !node.nestedNodes.empty) {
			val valueTypeRef = valueTypePerNode.get(node);
			if(valueTypeRef!==null) {
				val memberScope = createMemberScope(node, valueTypeRef, contextObject);
				for(childNode : node.nestedNodes) {
					if(childNode!==null) {
						internal_checkDestructNode(G, node, childNode, memberScope, valueTypePerNode, contextObject);
					}
				}
			}
		}
	}

	private def boolean holdsValidPropertyAccessInDestructNode(RuleEnvironment G, DestructNode parentNode, DestructNode node, IScope parentMemberScope, Map<DestructNode,TypeRef> valueTypePerNode) {
		if(node.propName!==null && parentMemberScope!==null) {
			// property names in object destructuring patterns constitute a property access without
			// a PropertyAccessExpression in the AST -> make sure property exists & is visible
			val errMsg = new StringBuffer;
			val propTypeRef = destructureHelper.getPropertyTypeForNode(G, valueTypePerNode.get(parentNode), parentMemberScope, node.propName, errMsg);
			if(errMsg.length>0) {
				val msg = getMessageForDESTRUCT_PROP_WITH_ERROR(node.propName, errMsg.toString.trim.trimSuffix('.'));
				val astNodeOfPropName = node.getEObjectAndFeatureForPropName();
				addIssue(msg, astNodeOfPropName.key, astNodeOfPropName.value, DESTRUCT_PROP_WITH_ERROR);
				return false;
			}
			else if(propTypeRef===null) {
				val msg = getMessageForDESTRUCT_PROP_MISSING(node.propName, valueTypePerNode.get(parentNode)?.typeRefAsString);
				val astNodeOfPropName = node.getEObjectAndFeatureForPropName();
				addIssue(msg, astNodeOfPropName.key, astNodeOfPropName.value, DESTRUCT_PROP_MISSING);
				return false;
			}
		}
		return true;
	}

	/**
	 * @param parentNode
	 *             parent node of {@code node} or <code>null</code> if {@code node} is a root.
	 */
	private def boolean holdsCorrectTypeInDestructNode(RuleEnvironment G, DestructNode parentNode, DestructNode node, Map<DestructNode,TypeRef> valueTypePerNode) {
		val valueTypeRef = valueTypePerNode.get(node);
		if(valueTypeRef===null) {
			// valueTypeRef===null is ok, means "no type expectation" or "unknown type"
			return true;
		}
		if(node.varDecl!==null || node.varRef!==null) {
			// binding target is a newly declared or existing variable
			if(node.varDecl!==null && node.varDecl.declaredTypeRef===null) {
				// variable declared within pattern _without_ an explicitly declared type
				// -> ignore this case
				// (such a variable does not introduce any type expectation; instead,
				// its type is inferred from the type of the value to be destructured)
			}
			else {
				// variable declared within pattern _with_ an explicitly declared type OR
				// existing variable referenced from within a pattern
				// -> check if it can hold the corresponding value
				var variableTypeRef = if(node.varDecl!==null) {
					ts.type(G,node.varDecl).value
				} else if(node.varRef!==null) {
					ts.type(G,node.varRef).value
				};
				if(variableTypeRef!==null) {

					// exception case: if we have a defaultExpr AND it is of wrong type
					// -> suppress further checking to avoid confusing duplicate error message
					if(node.defaultExpr!==null) {
						val defaultExprTypeRef = ts.type(G,node.defaultExpr).value;
						val isOfCorrectType = if(defaultExprTypeRef!==null) ts.subtypeSucceeded(G,defaultExprTypeRef,variableTypeRef);
						if(!isOfCorrectType) {
							return false;
						}
					}

					val result = ts.subtype(G,valueTypeRef,variableTypeRef);
					if(result.failure) {
						val varName = node.varDecl?.name ?: node.varRef?.id?.name ?: "<unnamed>";
						var elemDesc = if(node.isPositional) {
							"at index "+parentNode.nestedNodes.indexOf(node)
						} else {
							"of property '"+node.propName+"'"
						};
						var tsMsg = result.failureMessage.trimPrefix('failed: ').trimSuffix('.');
						val msg = getMessageForDESTRUCT_TYPE_ERROR_VAR(varName, elemDesc, tsMsg);
						if(node.varDecl!==null) {
							addIssue(msg, node.varDecl, TypesPackage.eINSTANCE.identifiableElement_Name, DESTRUCT_TYPE_ERROR_VAR)
						} else {
							addIssue(msg, node.varRef, DESTRUCT_TYPE_ERROR_VAR);
						}
						return false;
					}
				}
			}
		}
		else if(node.nestedNodes!==null) {
			// binding target is a top-level or nested pattern
			// -> assert that we have an Iterable<?> or an Object depending on array/object destructuring
			// (keep consistent with Xsemantics rule 'expectedTypeOfRightSideInVariableBinding' and rule
			// 'expectedTypeOfOperandInAssignmentExpression' that are doing the same thing but on top-level)
			val isPositional = node.nestedNodes.arePositional;
			val expectedTypeRef = if(isPositional) {
				G.iterableTypeRef(TypeRefsFactory.eINSTANCE.createWildcard)
			} else {
				G.objectTypeRef
			};
			val result = ts.subtype(G,valueTypeRef.autoboxIfPrimitive,expectedTypeRef);
			if(result.failure) {
				val patternKind = if(isPositional) {
					if(parentNode!==null) "Nested array" else "Array"
				} else {
					if(parentNode!==null) "Nested object" else "Object"
				};
				var elemDesc = if(parentNode!==null) {
					if(node.isPositional) {
						"destructured value at index "+parentNode.nestedNodes.indexOf(node)
					} else {
						"destructured value of property '"+node.propName+"'"
					}
				} else {
					"a value of type '"+valueTypeRef.typeRefAsString+"'"
				};
				var tsMsg = result.failureMessage.trimPrefix('failed: ').trimSuffix('.');
				val msg = getMessageForDESTRUCT_TYPE_ERROR_PATTERN(patternKind, elemDesc, tsMsg);
				val astElem = node.astElement;
				switch(astElem) {
					PropertyNameValuePair case !(astElem instanceof PropertyNameValuePairSingleName):
						addIssue(msg, astElem, N4JSPackage.eINSTANCE.propertyNameValuePair_Expression, DESTRUCT_TYPE_ERROR_PATTERN)
					BindingProperty:
						addIssue(msg, astElem, N4JSPackage.eINSTANCE.bindingProperty_Value, DESTRUCT_TYPE_ERROR_PATTERN)
					default:
						addIssue(msg, astElem, DESTRUCT_TYPE_ERROR_PATTERN)
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * Create a member scope for looking up property names of the <em>children</em> of {@code node}, i.e. the {@code propName}
	 * attribute of the {@code nestedNodes} of {@code node}. Will return <code>null</code> if node does not have nested nodes
	 * or if they are positional (because then property look-up does not make sense).
	 */
	private def IScope createMemberScope(DestructNode node, TypeRef valueTypeRef, EObject contextObject) {
		if(!node.nestedNodes.empty && !node.nestedNodes.arePositional) {
			destructureHelper.createMemberScopeForPropertyAccess(valueTypeRef, contextObject, true); // also check visibility
		} else {
			null
		}
	}

	private def TypeRef autoboxIfPrimitive(TypeRef typeRef) {
		val declType = typeRef.declaredType;
		if(declType instanceof PrimitiveType) {
			if(declType.autoboxedType!==null) {
				return TypeUtils.createTypeRef(declType.autoboxedType);
			}
		}
		return typeRef;
	}
}
