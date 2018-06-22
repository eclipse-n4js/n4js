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
package org.eclipse.n4js.transpiler.es.n4idl

import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.MigrationContextVariable
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4idl.versioning.MigrationUtils
import org.eclipse.n4js.transpiler.Transformation
import org.eclipse.n4js.transpiler.im.IdentifierRef_IM
import org.eclipse.n4js.transpiler.im.ParameterizedPropertyAccessExpression_IM
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.types.TMigration
import org.eclipse.xtext.EcoreUtil2

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*
import org.eclipse.n4js.n4idl.N4IDLGlobals

/**
 * N4IDL Migration declaration transformation which replaces all MigrationContext
 * references with 'this' references and replaces 'migrate' calls with migrate-calls 
 * on the context. 
 */
class N4IDLMigrationTransformation extends Transformation {
	
	public override void transform() {
		EcoreUtil2.getAllContentsOfType(getState().im, FunctionDeclaration)
				.filter[m | MigrationUtils.isMigrationDefinition(m)]
				.forEach[m | transformMigrationDeclaration(m) ]
	}
	
	private def transformMigrationDeclaration(FunctionDeclaration migrationDeclaration) {
		// replace 'MigrationContextVariable' references with 'this' literal
		EcoreUtil2.getAllContentsOfType(migrationDeclaration, IdentifierRef_IM)
			.forEach[identifier | transformIdentifierReference(identifier)];
		
		// replace migrate-calls with 'this.migrate' calls
		EcoreUtil2.getAllContentsOfType(migrationDeclaration, ParameterizedCallExpression)
			.forEach[callExpr | transformCallExpression(findTMigration(migrationDeclaration), callExpr)];
	}
	
	/**
	 * Obtains the type-model {@link TMigration} instance for the given migration declaration.
	 * 
	 * Raises an {@link IllegalStateException} if no {@link TMigration} can be obtained from
	 * the given {@link FunctionDeclaration}. 
	 */
	private def TMigration findTMigration(FunctionDeclaration migrationDeclaration) {
		val declarationSTE = state.steCache.mapNamedElement_2_STE.get(migrationDeclaration);
		
		if (!(declarationSTE instanceof SymbolTableEntryOriginal)) {
			throw new IllegalStateException("Failed to obtain SymbolTableEntryOriginal for migration declaration " + migrationDeclaration);
		}
		
		if (!((declarationSTE as SymbolTableEntryOriginal).originalTarget instanceof TMigration)) { 
			throw new IllegalStateException("Encountered malformed migration declaration in transpiler: " + migrationDeclaration);
		}
		
		return (declarationSTE as SymbolTableEntryOriginal).originalTarget as TMigration;
	}
	
	/**
	 * Replaces the given {@link IdentifierRef} with a {@code this.context} expression, 
	 * if it refers to a {@link MigrationContextVariable}.
	 */
	private def void transformIdentifierReference(IdentifierRef_IM ref) {
		val refSTE = ref.id_IM;
		
		if (refSTE instanceof SymbolTableEntryOriginal
			&& (refSTE as SymbolTableEntryOriginal).originalTarget instanceof MigrationContextVariable) {
			
			val contextSTE = getSymbolTableEntryInternal(N4IDLGlobals.MIGRATION_CONTROLLER_CONTEXT_PROPERTY_NAME, true);
			val contextAccess = _PropertyAccessExpr(_ThisLiteral, contextSTE);
			replace(ref, contextAccess)
		}
	}
	
	/**
	 * Transforms the given call expression, in case the call-expression refers to a migration (implicitly via {@code migrate}-call
	 * or explicitly via an explicit reference to a function annotated as {@code @Migration}).
	 */
	private def void transformCallExpression(TMigration contextMigration, ParameterizedCallExpression callExpression) {
		if (MigrationUtils.isMigrateCall(callExpression)) {
			transformMigrateCallExpression(contextMigration, callExpression);
		} else {
			val callTarget = callExpression.target;
			
			if (callTarget instanceof ParameterizedPropertyAccessExpression_IM) {
				if (refersToMigration(callTarget.rewiredTarget)) {
					transformExplicitMigrationCallExpression(contextMigration, callExpression);
				}
			}
			if (callTarget instanceof IdentifierRef_IM) {
				if (refersToMigration(callTarget.rewiredTarget)) {
					transformExplicitMigrationCallExpression(contextMigration, callExpression);
				}
			}
		}
	}
	
	/**
	 * Returns {@code true} iff the given {@code ste} refers to a {@link TMigration}.
	 */
	private def boolean refersToMigration(SymbolTableEntry ste) {
		if (ste instanceof SymbolTableEntryOriginal) {
			val originalTarget = ste.originalTarget;
			return originalTarget instanceof TMigration;
		}
		return false;
	}
	
	/**
	 * Transforms the given {@code migrate} call expression.
	 */
	private def void transformMigrateCallExpression(TMigration contextMigration, ParameterizedCallExpression callExpression) {
		val transpiledCall = _CallExpr()
		val migrateSTE = getSymbolTableEntryInternal(N4IDLGlobals.MIGRATION_CALL_IDENTIFIER, true);
		
		transpiledCall.target = _PropertyAccessExpr(_ThisLiteral, migrateSTE);
		transpiledCall.arguments.addAll(#[
			// first and only argument is an array of all original migrate call arguments
			_Argument(_ArrLit(callExpression.arguments.map[a | _ArrayElement(a.expression)]))
		]);
		replace(callExpression, transpiledCall);
	}
	
	/**
	 * Transforms explicit calls of migration functions to migrate-calls via the 
	 * migration controller (e.g. {@code migrateA(a.b)} -> {@code this.migrateWith(migrateA, [a.b])}). 
	 */
	private def void transformExplicitMigrationCallExpression(TMigration contextMigration, ParameterizedCallExpression callExpression) {
		val transpiledCall = _CallExpr()
		val migrateWithSTE = getSymbolTableEntryInternal(N4IDLGlobals.MIGRATION_CONTROLLER_MIGRATE_WITH_FUNCTION_NAME, true);
		
		transpiledCall.target = _PropertyAccessExpr(_ThisLiteral, migrateWithSTE);
		transpiledCall.arguments.addAll(#[
			// first argument is the target of the call-expression (the migration function)
			_Argument(callExpression.target),
			// second argument is an array of all original arguments
			_Argument(_ArrLit(callExpression.arguments.map[a | _ArrayElement(a.expression)]))
		]);
		replace(callExpression, transpiledCall);
	}
	
	public override void analyze() {
		// nothing to analyze
	}

	
	public override void assertPostConditions() {
		// nothing to assert
	}

	
	public override void assertPreConditions() {
		// nothing to assert
	}
}
