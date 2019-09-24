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
import org.eclipse.n4js.n4JS.EqualityOperator
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.assistants.TypeAssistant
import org.eclipse.n4js.transpiler.es.assistants.BootstrapCallAssistant
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.ts.types.TInterface

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

import static extension org.eclipse.n4js.transpiler.utils.TranspilerUtils.*
import static extension org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions.*
import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

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
		val fieldInitFun = createInstanceFieldInitializationFunction(ifcDecl, ifcSTE);
		val staticFieldInits = createStaticFieldInitializations(ifcSTE, ifcDecl);
		val memberDefs = bootstrapCallAssistant.createInterfaceMemberDefinitionSection(ifcDecl);
		val makeIfcCall = bootstrapCallAssistant.createMakeInterfaceCall(ifcDecl);

		state.tracer.copyTrace(ifcDecl, staticFieldInits);
		state.tracer.copyTrace(ifcDecl, memberDefs);
		state.tracer.copyTrace(ifcDecl, makeIfcCall);

		replace(ifcDecl, varDecl);
		val root = varDecl.eContainer.orContainingExportDeclaration;
		insertAfter(root, #[fieldInitFun] + staticFieldInits + memberDefs + #[makeIfcCall]);

		state.info.markAsToHoist(varDecl);
	}

	/**
	 * Creates declaration of the variable that will represent the interface.
	 */
	def private VariableDeclaration createVarDecl(N4InterfaceDeclaration ifcDecl) {
		return _VariableDeclaration(ifcDecl.name)=>[
			expression = _ObjLit();
		];
	}

	def private ExpressionStatement createInstanceFieldInitializationFunction(N4InterfaceDeclaration ifcDecl, SymbolTableEntry ifcSTE) {
		// I.$fieldInit = function I_fieldInit(target, spec, mixinExclusion) {
		//     // ...
		// };
		val $fieldInitSTE = steFor_$fieldInit;
		return _ExprStmnt(_AssignmentExpr()=>[
			lhs = _PropertyAccessExpr(ifcSTE, $fieldInitSTE);
			// _fieldInit needs to be called via "call(this, ...)" to bind this correctly and avoid unnecessary rewrite of "this"
			rhs = _FunExpr(false, ifcDecl.name + '_fieldInit', #[ _Fpar("spec"), _Fpar("mixinExclusion") ])=>[
				body.statements += createInstanceFieldInitializations(ifcDecl);
				body.statements += createDelegationToFieldInitOfExtendedInterfaces(ifcDecl);
			];
		]);
	}

	def private Statement[] createInstanceFieldInitializations(N4InterfaceDeclaration ifcDecl) {
		// if(spec) {
		//      if(!(mixinExclusion.hasOwnProperty("field") || this.hasOwnProperty("field"))) {
		//     	    this.field = spec.field === undefined ? 42 : spec.field;
		//      }
		// } else {
		//     if(!(mixinExclusion.hasOwnProperty("field") || this.hasOwnProperty("field"))) {
		//         this.field = 42;
		//     }
		// }
		val fields = ifcDecl.ownedFields.filter[!static].toList;
		if(!fields.empty) {
			val hasOwnPropertySTE = getSymbolTableEntryForMember(state.G.objectType, "hasOwnProperty", false, false, true);
			val fieldInitsFromSpec = <Statement>newArrayList;
			val fieldInitsNormal = <Statement>newArrayList;
			for(field : fields) {
				val fieldSTE = findSymbolTableEntryForElement(field, true);
				// target.field = spec.field === undefined ? 42 : spec.field;
				val specStmnt = _ExprStmnt(_AssignmentExpr(
					_PropertyAccessExpr(_Snippet("this"), fieldSTE),
					_ConditionalExpr(
						_EqualityExpr(_PropertyAccessExpr(_Snippet("spec"), fieldSTE), EqualityOperator.SAME, _IdentRef(steFor_undefined)),
						{if(field.expression!==null) copy(field.expression) else undefinedRef()},
						_PropertyAccessExpr(_Snippet("spec"), fieldSTE)
					)
				));
				fieldInitsFromSpec += ifStmntMixinExclusionORtarget(hasOwnPropertySTE,fieldSTE,specStmnt);

				// if(!(mixinExclusion.hasOwnProperty("field") || target.hasOwnProperty("field"))) {target.field = 42;}
				val trueBody = _ExprStmnt(_AssignmentExpr(
					_PropertyAccessExpr(_Snippet("this"), fieldSTE),
					{if(field.expression!==null) copy(field.expression) else undefinedRef()}
				));
				fieldInitsNormal += ifStmntMixinExclusionORtarget(hasOwnPropertySTE,fieldSTE,trueBody);
			}
			// if(spec) {...} else {...}
			return #[_IfStmnt(
				_Snippet("spec"),
				_Block(fieldInitsFromSpec),
				_Block(fieldInitsNormal)
			)];
		}
		return #[];
	}

	def private ifStmntMixinExclusionORtarget(SymbolTableEntry hasOwnPropertySTE, SymbolTableEntry fieldSTE, Statement trueBody ){
		return _IfStmnt(
			_NOT(_Parenthesis(_OR(
				_CallExpr(_PropertyAccessExpr(_Snippet("mixinExclusion"), hasOwnPropertySTE), _StringLiteralForSTE(fieldSTE)),
				_CallExpr(_PropertyAccessExpr(_Snippet("this"), hasOwnPropertySTE), _StringLiteralForSTE(fieldSTE))
			))),
			trueBody
		);
	}


	def private Statement[] createDelegationToFieldInitOfExtendedInterfaces(N4InterfaceDeclaration ifcDecl) {
		// I.$fieldInit(target, spec, mixinExclusion);
		val result = newArrayList;
		val $fieldInitSTE = steFor_$fieldInit;
		val superIfcSTEs = typeAssistant.getSuperInterfacesSTEs(ifcDecl).filter [
			// regarding the cast to TInterface: see preconditions of ClassDeclarationTransformation
			// regarding the entire next line: generate $fieldInit call only if the interface is neither built-in nor provided by runtime nor external without @N4JS
			!(originalTarget as TInterface).builtInOrProvidedByRuntimeOrExternalWithoutN4JSAnnotation;
		];

		for(superIfcSTE : superIfcSTEs) {
			result += _ExprStmnt(_CallExpr(
				__NSSafe_PropertyAccessExpr(superIfcSTE, $fieldInitSTE, steFor_call()),
				_Snippet("this"), _Snippet("spec"), _Snippet("mixinExclusion")
			));
		}
		return result;
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
