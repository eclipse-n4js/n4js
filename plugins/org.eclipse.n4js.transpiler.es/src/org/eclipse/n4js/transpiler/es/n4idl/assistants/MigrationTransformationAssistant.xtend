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
import java.util.Optional
import org.eclipse.n4js.n4JS.ArrayElement
import org.eclipse.n4js.n4JS.EqualityOperator
import org.eclipse.n4js.n4JS.Expression
import org.eclipse.n4js.n4JS.FunctionExpression
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.n4JS.Statement
import org.eclipse.n4js.n4JS.VariableStatementKeyword
import org.eclipse.n4js.n4idl.N4IDLGlobals
import org.eclipse.n4js.n4idl.migrations.MigrationSwitchComputer
import org.eclipse.n4js.n4idl.migrations.MigrationSwitchComputer.UnhandledTypeRefException
import org.eclipse.n4js.n4idl.migrations.TypeSwitchCondition
import org.eclipse.n4js.transpiler.TransformationAssistant
import org.eclipse.n4js.transpiler.im.SymbolTableEntry
import org.eclipse.n4js.transpiler.im.SymbolTableEntryInternal
import org.eclipse.n4js.transpiler.im.SymbolTableEntryOriginal
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.TypeTypeRef
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TMigratable
import org.eclipse.n4js.ts.types.TMigration
import org.eclipse.n4js.ts.types.TN4Classifier
import org.eclipse.n4js.ts.types.Type

import static org.eclipse.n4js.transpiler.TranspilerBuilderBlocks.*

/**
 * Transformation assistant for generating migration-support related ES code.
 */
class MigrationTransformationAssistant extends TransformationAssistant {
	
	@Inject
	private TypeSwitchTranspiler typeSwitchTranspiler;
	
	@Inject
	private MigrationSwitchComputer migrationSwitchComputer;
	
	private static val SWITCH_ARGUMENT = "migrationArguments";
	private static val MIGRATION_CANDIDATE_LIST = "migrationCandidates";
	
	/** 
	 * Creates the initializer statement for the static field which holds migrations associated with the given type declaration.
	 * 
	 * Returns an absent {@link Optional} if for the given type declaration, no migration support initializer 
	 * can be provided (e.g. non-migratable type).
	 * 
	 * @see N4IDLGlobals#MIGRATIONS_STATIC_FIELD
	 */
	public def Statement createMigrationSupportInitializer(SymbolTableEntry steClass, N4TypeDeclaration typeDecl) {
		val SymbolTableEntryOriginal originalSTE = if (steClass instanceof SymbolTableEntryOriginal) {
			steClass
		} else {
			state.steCache.mapOriginal.get(typeDecl);
		}
		val typeModelElement = originalSTE?.originalTarget;
		if (null === typeModelElement) {
			// without the type model class declaration we cannot go any further
			throw new IllegalStateException("Failed to generate migration meta-information for type " + typeDecl.name + ".");
		}
		
		if (!(typeModelElement instanceof TMigratable)) {
			// at this point we do expect migratable types only
			throw new IllegalStateException("Failed to generate migration meta-information for non-migratable type " + typeDecl.name + ".");
		}
		
		// <migration switch>
		val Expression migrationsFieldValue = makeMigrationSwitch(typeModelElement as TMigratable);
		// A.$migrations__n4
		val staticFieldAccess =_PropertyAccessExpr(_IdentRef(steClass), getSymbolTableEntryInternal(N4IDLGlobals.MIGRATIONS_STATIC_FIELD, true))
		
		// A.$migration__n4 = function() { <migration switch> }
		return _ExprStmnt(_AssignmentExpr(staticFieldAccess, migrationsFieldValue));
	}
	
	private def Expression makeMigrationSwitch(TMigratable migratable) {
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
		val migrationCandidatesSTE = getSymbolTableEntryInternal(MIGRATION_CANDIDATE_LIST, true);
		
		bodyStatements += _VariableStatement(VariableStatementKeyword.LET, _VariableDeclaration(MIGRATION_CANDIDATE_LIST, _ArrLit));
		
		// add one IfStatement per migration
		bodyStatements.addAll(migrations.map[makeIfStatement(it, switchParameter, migrationCandidatesSTE)].flatten);
		// return array of migration candidates (may be empty)
		bodyStatements += _ReturnStmnt(_IdentRef(migrationCandidatesSTE));
		
		return _FunExpr(false, null, #[_FormalParameter(SWITCH_ARGUMENT)], _Block(bodyStatements));
	}
	
	/** Create the if-statement which checks for argument conditions and returns the migration 
	 * if it matches. 
	 * 
	 * @param migration The migration.
	 * @param migrationParameters STE of the migration parameters (array).
	 * 
	 * */
	private def List<Statement> makeIfStatement(TMigration migration, SymbolTableEntryInternal migrationParameters,
		SymbolTableEntryInternal migrationCandidatesSTE) {
	
		val List<Expression> conditions = newArrayList
	
		// parameters.length == <migration.sourceTypeRefs.size>
		val parameterCountCondition = _EqualityExpr(
			_PropertyAccessExpr(_IdentRef(migrationParameters), getSymbolTableEntryInternal("length", true)), 
			EqualityOperator.EQ, 
			_NumericLiteral(migration.sourceTypeRefs.size)
		)
		conditions += parameterCountCondition;
		
		// compute switch conditions for migration parameters
		val parameterTypeConditions = migration.sourceTypeRefs.map[
				try {
					migrationSwitchComputer.compute(it)
				} catch (UnhandledTypeRefException e) {
					throw new IllegalStateException("Failed to compute migration source type switch for migration " + migration.name, e)
				}
		]
		
		// determine all {@link Type}s referenced in the switch conditions.
		val typeConditionTypes = parameterTypeConditions
			.map[c | c.subConditions.filter(TypeSwitchCondition) ].flatten
			.map[typeConditions | typeConditions.type ];
		
		// add imports (if not yet imported) for all types that occur in type conditions
		typeConditionTypes.forEach[i | addNamedImport(i, null) ]
		
		// transform switch conditions to Expressions and add them to conditions
		parameterTypeConditions.forEach[condition, index|
			val lhs = _IndexAccessExpr(_IdentRef(migrationParameters), _NumericLiteral(index))
			conditions += typeSwitchTranspiler.transform(condition, lhs);
		]
		
		return #[
			_SnippetAsStmnt("// (" + migration.sourceTypeRefs.map[typeRefAsString].join(", ") + ")"),
			_IfStmnt(
				_AND(conditions), // type switch based condition
				_PushArrayElement(migrationCandidatesSTE, _MigrationCandidateElement(migration))
			)
		];
	}
	
	private def Expression _MigrationCandidateElement(TMigration migration) {
		val migrationSTE = getSymbolTableEntryOriginal(migration, true);
		
		// map the migration source type refs to their runtime representation
		val sourceTypeArrayElements = migration.sourceTypeRefs
			.map[typeRef | _ParameterTypeArrayElement(typeRef)]
		
		// {migration: <migrationFunctionRef>, parameterTypes: [<sourceTypeArrayElements>] }
		return _ObjLit(
			"migration" -> _IdentRef(migrationSTE),
			"parameterTypes" -> _ArrLit(sourceTypeArrayElements)
		);
	}
	
	/** 
	 * Returns an {@link ArrayElement} which represents the given {@link Type} at runtime.
	 * 
	 * For instance, for {@link TN4Classifier}s that is an  instance of {@code type{Object}}
	 * and for primitive types that is a string {@code "primitive"}.
	 * 
	 * {@link TypeTypeRef}s are represented as plain object of the form <code>{type: <constructor/type ref>}</code>
	 * 
	 * Unhandled type reference types will throw an {@link IllegalStateException}.
	 */
	private def ArrayElement _ParameterTypeArrayElement(TypeRef typeRef) {
		switch (typeRef) {
			case typeRef.declaredType !== null:
				switch(typeRef.declaredType) {
					TN4Classifier:
						_ArrayElement(_IdentRef(getSymbolTableEntryOriginal(typeRef.declaredType, true)))
					PrimitiveType:
						_ArrayElement(_StringLiteral("primitive"))
				}
			TypeTypeRef:
						_ArrayElement(_ObjLit("type" -> _IdentRef(getSymbolTableEntryOriginal(getType(typeRef), true))))
			default:
				throw new IllegalStateException("Unhandled migration source type reference " + typeRef)
		}
	}
	
	/** Returns the type which is referred to by the given {@link TypeTypeRef}. */
	private def Type getType(TypeTypeRef ref) {
		val typeArg = ref.typeArg;
		if (typeArg instanceof TypeRef) {
			return typeArg.declaredType;
		}
		return null;
	}
	
	/** Creates a {@code <arraySTE>.push(<element>)} expression which adds the element expression to the array
	 * referenced by arraySTE. */
	private def Statement _PushArrayElement(SymbolTableEntry arraySTE, Expression element) {
		return _ExprStmnt(_CallExpr(
			_PropertyAccessExpr(_IdentRef(arraySTE), getSymbolTableEntryInternal("push", true)),
			element
		));
	}
}
