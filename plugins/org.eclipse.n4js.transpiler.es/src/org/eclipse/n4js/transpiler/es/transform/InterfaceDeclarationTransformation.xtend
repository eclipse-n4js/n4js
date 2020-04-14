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
package org.eclipse.n4js.transpiler.es.transform

import com.google.inject.Inject
import java.util.Collections
import java.util.List
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.AdditiveExpression
import org.eclipse.n4js.n4JS.ArrayLiteral
import org.eclipse.n4js.n4JS.AwaitExpression
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression
import org.eclipse.n4js.n4JS.BinaryLogicalExpression
import org.eclipse.n4js.n4JS.BooleanLiteral
import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.CommaExpression
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionDefinition
import org.eclipse.n4js.n4JS.FunctionOrFieldAccessor
import org.eclipse.n4js.n4JS.GetterDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.MultiplicativeExpression
import org.eclipse.n4js.n4JS.N4ClassifierDeclaration
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSFactory
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.NullLiteral
import org.eclipse.n4js.n4JS.NumericLiteral
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.PromisifyExpression
import org.eclipse.n4js.n4JS.PropertyAssignment
import org.eclipse.n4js.n4JS.PropertyNameOwner
import org.eclipse.n4js.n4JS.PropertyNameValuePair
import org.eclipse.n4js.n4JS.SetterDeclaration
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.YieldExpression
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.assistants.ClassifierAssistant
import org.eclipse.n4js.transpiler.es.assistants.DelegationAssistant
import org.eclipse.n4js.transpiler.es.assistants.ReflectionAssistant
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.utils.N4JSLanguageUtils
import org.eclipse.n4js.utils.ResourceNameComputer

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.transpiler.utils.TranspilerUtils.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 */
class InterfaceDeclarationTransformation extends Transformation {

	@Inject private ClassifierAssistant classifierAssistant;
	@Inject private ReflectionAssistant reflectionAssistant;
	@Inject private DelegationAssistant delegationAssistant;
	@Inject private TypeAssistant typeAssistant;
	@Inject private ResourceNameComputer resourceNameComputer;


	override assertPreConditions() {
		typeAssistant.assertClassifierPreConditions();
	}

	override assertPostConditions() {
		// none
	}

	override analyze() {
		// ignore
	}

	override transform() {
		collectNodes(state.im, N4InterfaceDeclaration, false).forEach[transformInterfaceDecl];
	}

	def private void transformInterfaceDecl(N4InterfaceDeclaration ifcDecl) {
		val ifcSTE = findSymbolTableEntryForElement(ifcDecl, true);

		// add 'Symbol.hasInstance' function for supporting the 'instanceof' operator
		ifcDecl.ownedMembersRaw += createHasInstanceMethod(ifcDecl, ifcSTE);

		reflectionAssistant.addN4TypeGetter(ifcDecl, ifcDecl);

		val belowIfcDecl = newArrayList;
		belowIfcDecl += createStaticFieldInitializations(ifcDecl, ifcSTE);
		insertAfter(ifcDecl.orContainingExportDeclaration, belowIfcDecl);

		ifcDecl.ownedMembersRaw.removeIf[static && it instanceof N4FieldDeclaration];
		delegationAssistant.replaceDelegatingMembersByOrdinaryMembers(ifcDecl);

		val ifcObjLit = createInterfaceObject(ifcDecl, ifcSTE);
		val varDecl = _VariableDeclaration(ifcDecl.name, ifcObjLit);
		state.tracer.copyTrace(ifcDecl, varDecl);

		replace(ifcDecl, varDecl);
	}

	/** Creates an object literal for the object representing the interface of the given <code>ifcDecl</code>. */
	def private ObjectLiteral createInterfaceObject(N4InterfaceDeclaration ifcDecl, SymbolTableEntry ifcSTE) {
		val props = <PropertyAssignment>newArrayList;
		val extendedInterfaces = createDirectlyImplementedOrExtendedInterfacesArgument(ifcDecl);
		if (!extendedInterfaces.elements.empty) {
			val $extends = steFor_$extendsInterfaces.name;
			props += _PropertyGetterDecl(
						$extends,
						_ReturnStmnt(extendedInterfaces)
					);
		}
		props += createInstanceFieldDefaultsProperty(ifcDecl, ifcSTE);
		props += createInstanceMemberPropertiesExceptFields(ifcDecl, ifcSTE);
		props += createStaticMemberPropertiesExceptFields(ifcDecl, ifcSTE);

		return _ObjLit(props);
	}

	def private ArrayLiteral createDirectlyImplementedOrExtendedInterfacesArgument(N4ClassifierDeclaration typeDecl) {
		val interfaces = typeAssistant.getSuperInterfacesSTEs(typeDecl);

		// the return value of this method is intended for default method patching; for this purpose, we have to
		// filter out some of the directly implemented interfaces:
		val directlyImplementedInterfacesFiltered = interfaces.filter[ifcSTE|
			val tIfc = ifcSTE.originalTarget;
			if(tIfc instanceof TInterface) {
				return !TypeUtils.isBuiltIn(tIfc) // built-in types are not defined in Api/Impl projects -> no patching required
					&& !(typeAssistant.inN4JSD(tIfc) && !AnnotationDefinition.N4JS.hasAnnotation(tIfc)) // interface in .n4jsd file only patched in if marked @N4JS
			}
			return false;
		];

		return _ArrLit( directlyImplementedInterfacesFiltered.map[ _IdentRef(it) ] );
	}

	def private PropertyNameValuePair[] createInstanceFieldDefaultsProperty(N4InterfaceDeclaration ifcDecl, SymbolTableEntry ifcSTE) {
		// $fieldInits: {
		//     fieldName1: undefined,
		//     fieldName2: 42,
		//     fieldName3: () => <INIT_EXPRESSION>,
		//     ...
		// }
		val instanceFields = ifcDecl.ownedFields
			.filter[!static && name!==null]
			.toList;
		if (instanceFields.empty) {
			return #[];
		}
		return #[
			_PropertyNameValuePair(
				steFor_$fieldInits.name,
				_ObjLit(
					instanceFields.map[field|
						field.name -> if (field.hasNonTrivialInitExpression) {
							if (canSkipFunctionWrapping(field.expression)) {
								field.expression
							} else {
								_ArrowFunc(false, #[], field.expression.wrapInParenthesesIfNeeded)
							}
						} else {
							undefinedRef()
						}
					]
				)
			)
		];
	}

	def private PropertyNameValuePair[] createInstanceMemberPropertiesExceptFields(N4InterfaceDeclaration ifcDecl, SymbolTableEntry ifcSTE) {
		// $defaultMembers: {
		//     get getter() {
		//     },
		//     set setter(value) {
		//     },
		//     method() {
		//     },
		//     ...
		// }

		val instanceMembersExceptFields = ifcDecl.ownedMembers
			.filter[!static && !abstract && name!==null]
			.filter[!(it instanceof N4FieldDeclaration)]
			.toList;
		if (instanceMembersExceptFields.empty) {
			return #[];
		}
		return #[
			_PropertyNameValuePair(
				steFor_$defaultMembers.name,
				_ObjLit(
					instanceMembersExceptFields.map[convertMemberToProperty]
				)
			)
		];
	}

	def private PropertyAssignment[] createStaticMemberPropertiesExceptFields(N4InterfaceDeclaration ifcDecl, SymbolTableEntry ifcSTE) {
		// get staticGetter() {
		// },
		// set staticSetter(value) {
		// },
		// staticMethod() {
		// },
		// ...

		val staticMembersExceptFields = ifcDecl.ownedMembers
			.filter[static && !abstract && name!==null]
			.filter[!(it instanceof N4FieldDeclaration)]
			.toList;
		if (staticMembersExceptFields.empty) {
			return #[];
		}
		return staticMembersExceptFields.map[convertMemberToProperty];
	}

	/**
	 * Converts getter, setter, and method declarations to the corresponding declarations in object literals.
	 * Does not support conversion of field declarations!
	 */
	def private PropertyAssignment convertMemberToProperty(N4MemberDeclaration memberDecl) {
		// fields:
		if (memberDecl instanceof N4FieldDeclaration) {
			return _PropertyNameValuePair(
				memberDecl.declaredName, // reuse existing name
				memberDecl.expression ?: undefinedRef); // reuse existing initializer expression (if any)
		}
		// getters, setters, and methods:
		val result = switch(memberDecl) {
			GetterDeclaration: N4JSFactory.eINSTANCE.createPropertyGetterDeclaration
			SetterDeclaration: N4JSFactory.eINSTANCE.createPropertySetterDeclaration => [
				it.fpar = memberDecl.fpar; // reuse existing fpar
			]
			FunctionDefinition: N4JSFactory.eINSTANCE.createPropertyMethodDeclaration => [
				it.fpars += memberDecl.fpars; // reuse existing fpars
				it.generator = memberDecl.generator;
				it.declaredAsync = memberDecl.async;
			]
			default:
				throw new IllegalArgumentException("not a getter, setter, or method declaration")
		};
		result.declaredName = (memberDecl as PropertyNameOwner).declaredName; // reuse existing name
		result._lok = (memberDecl as FunctionOrFieldAccessor)._lok; // reuse existing LocalArgumentsVariable (if existent)
		result.body = (memberDecl as FunctionOrFieldAccessor).body; // reuse existing body
		if (!memberDecl.annotations.isEmpty) {
			result.annotationList = _PropertyAssignmentAnnotationList( memberDecl.annotations ) // reuse existing annotations
		}
		return result;
	}

	def private Expression wrapInParenthesesIfNeeded(Expression expr) {
		if (expr instanceof CommaExpression
			|| expr instanceof ObjectLiteral
			|| expr instanceof AwaitExpression
			|| expr instanceof YieldExpression
			|| expr instanceof PromisifyExpression) {
			return _Parenthesis(expr);
		}
		return expr;
	}

	def private boolean canSkipFunctionWrapping(Expression initExpression) {
		return switch(initExpression) {
			BooleanLiteral: true
			NullLiteral: true
			NumericLiteral: true
			StringLiteral: true
			UnaryExpression: {
				// WARNING: some unary operators have a side effect, so they have to be wrapped in a function!
				val isFreeOfSideEffect = switch(initExpression.op) {
					case INC: false
					case DEC: false
					case DELETE: false
					case POS: true
					case NEG: true
					case INV: true
					case NOT: true
					case TYPEOF: true
					case VOID: true
				}
				return isFreeOfSideEffect
					&& canSkipFunctionWrapping(initExpression.expression);
			}
			AdditiveExpression: {
				val isFreeOfSideEffect = switch(initExpression.op) {
					case ADD: true
					case SUB: true
				}
				return isFreeOfSideEffect
					&& canSkipFunctionWrapping(initExpression.lhs)
					&& canSkipFunctionWrapping(initExpression.rhs);
			}
			MultiplicativeExpression: {
				val isFreeOfSideEffect = switch(initExpression.op) {
					case TIMES: true
					case DIV: true
					case MOD: true
				}
				return isFreeOfSideEffect
					&& canSkipFunctionWrapping(initExpression.lhs)
					&& canSkipFunctionWrapping(initExpression.rhs);
			}
			BinaryBitwiseExpression: {
				val isFreeOfSideEffect = switch(initExpression.op) {
					case OR: true
					case XOR: true
					case AND: true
				}
				return isFreeOfSideEffect
					&& canSkipFunctionWrapping(initExpression.lhs)
					&& canSkipFunctionWrapping(initExpression.rhs);
			}
			BinaryLogicalExpression: {
				val isFreeOfSideEffect = switch(initExpression.op) {
					case OR: true
					case AND: true
				}
				return isFreeOfSideEffect
					&& canSkipFunctionWrapping(initExpression.lhs)
					&& canSkipFunctionWrapping(initExpression.rhs);
			}
			CastExpression: canSkipFunctionWrapping(initExpression.expression)
			ParenExpression: canSkipFunctionWrapping(initExpression.expression)
			IdentifierRef: {
				return initExpression.id === state.G.globalObjectScope.fieldUndefined;
			}
			default: false
		};
	}

	def protected List<Statement> createStaticFieldInitializations(N4InterfaceDeclaration ifcDecl, SymbolTableEntry ifcSTE) {
		return classifierAssistant.createStaticFieldInitializations(ifcDecl, ifcSTE, Collections.emptySet);
	}

	def private N4MethodDeclaration createHasInstanceMethod(N4InterfaceDeclaration ifcDecl, SymbolTableEntry ifcSTE) {
		val symbolObjectType = state.G.symbolObjectType;
		val symbolSTE = getSymbolTableEntryOriginal(symbolObjectType, true);
		val hasInstanceSTE = getSymbolTableEntryForMember(symbolObjectType, "hasInstance", false, true, true);
		val hasInstanceExpr = _PropertyAccessExpr(symbolSTE, hasInstanceSTE);
		val declaredName = _LiteralOrComputedPropertyName(hasInstanceExpr, N4JSLanguageUtils.SYMBOL_IDENTIFIER_PREFIX + "hasInstance");

		val ifcType = state.info.getOriginalDefinedType(ifcDecl);
		val fqn = resourceNameComputer.getFullyQualifiedTypeName(ifcType);

		val result = _N4MethodDecl(true, declaredName, #[ _Fpar("instance") ], _Block(
			_ReturnStmnt(
				_Snippet("instance && instance.constructor && instance.constructor.n4type "
					+ "&& instance.constructor.n4type.allImplementedInterfaces " // required because we cannot be sure "instance.constructor.n4type" is of type N4Classifier
					+ "&& instance.constructor.n4type.allImplementedInterfaces.indexOf('" + fqn + "') !== -1"
				)
			)
		));

		state.info.markAsHiddenFromReflection(result);

		return result;
	}
}
