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
import java.util.List
import org.eclipse.n4js.n4JS.AdditiveExpression
import org.eclipse.n4js.n4JS.AwaitExpression
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression
import org.eclipse.n4js.n4JS.BinaryLogicalExpression
import org.eclipse.n4js.n4JS.BooleanLiteral
import org.eclipse.n4js.n4JS.CastExpression
import org.eclipse.n4js.n4JS.CommaExpression
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.MultiplicativeExpression
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.NullLiteral
import org.eclipse.n4js.n4JS.NumericLiteral
import org.eclipse.n4js.n4JS.ObjectLiteral
import org.eclipse.n4js.n4JS.ParenExpression
import org.eclipse.n4js.n4JS.PromisifyExpression
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.StringLiteral
import org.eclipse.n4js.n4JS.UnaryExpression
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.YieldExpression
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.assistants.BootstrapCallAssistant
import org.eclipse.n4js.transpiler.im.SymbolTableEntry

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.transpiler.utils.TranspilerUtils.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*

/**
 */
class InterfaceDeclarationTransformation extends Transformation {

	@Inject BootstrapCallAssistant bootstrapCallAssistant;
	@Inject TypeAssistant typeAssistant;


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

		val varDecl = createVarDecl(ifcDecl);
		val fieldDefaults = createInstanceFieldDefaultsObject(ifcDecl, ifcSTE);
		val staticFieldInits = createStaticFieldInitializations(ifcSTE, ifcDecl);
		val memberDefs = bootstrapCallAssistant.createInterfaceMemberDefinitionSection(ifcDecl);
		val makeIfcCall = bootstrapCallAssistant.createMakeInterfaceCall(ifcDecl);

		state.tracer.copyTrace(ifcDecl, fieldDefaults);
		state.tracer.copyTrace(ifcDecl, staticFieldInits);
		state.tracer.copyTrace(ifcDecl, memberDefs);
		state.tracer.copyTrace(ifcDecl, makeIfcCall);

		replace(ifcDecl, varDecl);
		val root = varDecl.eContainer.orContainingExportDeclaration;
		insertAfter(root, fieldDefaults + staticFieldInits + memberDefs + #[makeIfcCall]);
	}

	/**
	 * Creates declaration of the variable that will represent the interface.
	 */
	def private VariableDeclaration createVarDecl(N4InterfaceDeclaration ifcDecl) {
		return _VariableDeclaration(ifcDecl.name)=>[
			expression = _ObjLit();
		];
	}

	def private Statement[] createInstanceFieldDefaultsObject(N4InterfaceDeclaration ifcDecl, SymbolTableEntry ifcSTE) {
		// I.$fieldDefaults = {
		//     fieldName1: undefined,
		//     fieldName2: () => <INIT_EXPRESSION>,
		//     ...
		// };
		val $fieldDefaultsSTE = steFor_$fieldDefaults;
		val fields = ifcDecl.ownedFields.filter[!static].toList;
		if (fields.empty) {
			return #[];
		}
		return #[
			_ExprStmnt(
				_AssignmentExpr(
					_PropertyAccessExpr(ifcSTE, $fieldDefaultsSTE),
					_ObjLit(
						fields.filter[!name.isNullOrEmpty].map[field|
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
			)
		];
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

	/**
	 * Creates a new list of statements to initialize the static fields of the given {@code ifcDecl}.
	 * 
	 * Clients of this method may modify the returned list.
	 */
	def protected List<Statement> createStaticFieldInitializations(SymbolTableEntry ifcSTE, N4InterfaceDeclaration ifcDecl) {
		// for an interface 'I' with a static field 'field' we here create something like:
		// I.field = "initial value";
		return ifcDecl.ownedMembers.filter(N4FieldDeclaration).filter[static].filter[expression!==null]
			// create an initialization statement per static field
			.<N4FieldDeclaration, Statement>map[fieldDecl|
			_ExprStmnt(_AssignmentExpr()=>[
				lhs = _PropertyAccessExpr(ifcSTE, findSymbolTableEntryForElement(fieldDecl,true));
				rhs = fieldDecl.expression;
			]);
		].toList;
	}
}
