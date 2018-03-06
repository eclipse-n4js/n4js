/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.transpiler.es.n4idl.assistants

import com.google.inject.Inject
import java.util.List
import org.eclipse.n4js.n4JS.EqualityOperator
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4idl.MigrationSwitchComputer
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal
import org.eclipse.n4js.ts.types.TMigratable
import org.eclipse.n4js.ts.types.TMigration

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Transformation assistant for generating migration-support related ES code.
 */
class MigrationSwitchAssistant extends TransformationAssistant {
	
	@Inject
	private TypeSwitchTranspiler typeSwitchTranspiler;
	
	@Inject
	private MigrationSwitchComputer migrationSwitchComputer;
	
	private static val SWITCH_ARGUMENT = "migrationArguments";
	
	def Expression makeMigrationSwitch(TMigratable migratable) {
		val migrationsByTargetVersion = migratable.migrations.groupBy[targetVersion];
		
		// object literal which maps target version to the corresponding
		// migration switch function
		return _ObjLit(migrationsByTargetVersion.entrySet.map[ entry |
			Pair.of(
				Integer.toString(entry.key), // target version 
				makeMigrationSwitchFunction(entry.value) as Expression // switch function
			)
		].toList);
	}
	
	private def FunctionExpression makeMigrationSwitchFunction(List<TMigration> migrations) {
		val List<Statement> bodyStatements = newArrayList();
		
		val switchParameter = getSymbolTableEntryInternal(SWITCH_ARGUMENT, true);
		
		// add one IfStatement per migration
		bodyStatements.addAll(migrations.map[makeIfStatement(it, switchParameter)].flatten);
		// return null if no switch has triggered
		bodyStatements += _ReturnStmnt(_NULL);
		
		return _FunExpr(false, null, #[_FormalParameter(SWITCH_ARGUMENT)], _Block(bodyStatements));
	}
	
	/** Create the if-statement which checks for argument conditions and returns the migration 
	 * if it matches. 
	 * 
	 * @param migration The migration.
	 * @param migrationParameters STE of the migration parameters (array).
	 * 
	 * */
	private def List<Statement> makeIfStatement(TMigration migration, SymbolTableEntryInternal migrationParameters) {
		val migrationSTE = state.steCache.mapOriginal.get(migration);
	
		val List<Expression> conditions = newArrayList
	
		// parameters.length == <migration.sourceTypeRefs.size>
		val parameterCountCondition = _EqualityExpr(
			_PropertyAccessExpr(_IdentRef(migrationParameters), getSymbolTableEntryInternal("length", true)), 
			EqualityOperator.EQ, 
			_NumericLiteral(migration.sourceTypeRefs.size)
		)
		conditions += parameterCountCondition;
		
		// compute switch conditions for migration parameters
		val parameterTypeConditions = migration.sourceTypeRefs
			.map[migrationSwitchComputer.compute(it)]
		
		// transform switch conditions to Expressions and add them to conditions
		parameterTypeConditions.forEach[condition, index|
			val lhs = _IndexAccessExpr(_IdentRef(migrationParameters), _NumericLiteral(index))
			conditions += typeSwitchTranspiler.transform(condition, lhs);
		]
		
		
		return #[
			_SnippetAsStmnt("// (" + migration.sourceTypeRefs.map[typeRefAsString].join(", ") + ")"),
			_IfStmnt(
				_AND(conditions), // switch based condition
				_ReturnStmnt(_IdentRef(migrationSTE))
			)
		];
	}
}
