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
package org.eclipse.n4js.validation.validators

import com.google.inject.Inject
import java.util.Collection
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import java.util.function.Function
import java.util.stream.Stream
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.FormalParameter
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.n4idl.migrations.MigrationSwitchComputer
import org.eclipse.n4js.n4idl.migrations.MigrationSwitchComputer.UnhandledTypeRefException
import org.eclipse.n4js.n4idl.migrations.SwitchCondition
import org.eclipse.n4js.n4idl.versioning.MigrationUtils
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.ComposedTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.typeRefs.VersionedParameterizedTypeRef
import org.eclipse.n4js.ts.types.TFormalParameter
import org.eclipse.n4js.ts.types.TMigratable
import org.eclipse.n4js.ts.types.TMigration
import org.eclipse.n4js.ts.versions.MigratableUtils
import org.eclipse.n4js.ts.versions.VersionableUtils
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.utils.collections.Collections2
import org.eclipse.n4js.utils.collections.Iterables2
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.n4js.validation.JavaScriptVariantHelper
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2

/**
 * Validates N4IDL migration declarations.
 */
class N4IDLMigrationValidator extends AbstractN4JSDeclarativeValidator {
	
	@Inject
	private JavaScriptVariantHelper variantHelper;
	
	@Inject
	private N4JSTypeSystem typeSystem;
	
	@Inject
	private MigrationSwitchComputer switchComputer;
	
	/**
	 * NEEDED
	 *
	 * When removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator.
	 */
	override void register(EValidatorRegistrar registrar) {
		// nop
	}

	/** Validates migration declarations (function declarations annotated as {@code @Migration}. */
	@Check
	public def void checkMigration(FunctionDeclaration functionDeclaration) {
		// this validation only applies for variants which support versioned types
		if (!variantHelper.allowVersionedTypes(functionDeclaration)) {
			return;
		}
		
		// do not validate a broken AST
		if (functionDeclaration === null || functionDeclaration.name === null) {
			return;
		}
		
		// skip invalid FunctionDeclarations
		if (null === functionDeclaration.body) {
			return;
		}
		
		// this validation only applies for migrations
		if (!MigrationUtils.isMigrationDefinition(functionDeclaration)) {
			return;
		}
		
		// make sure the type model fulfills expectations
		if (!(functionDeclaration.definedFunction instanceof TMigration)) {
			throw new IllegalStateException("FunctionDeclaration " + functionDeclaration.name + " does not refer to a valid TMigration type model instance");
		}
		
		val migration = functionDeclaration.definedFunction as TMigration;
		
		if (holdsExplicitlyDeclaresReturnType(functionDeclaration, migration) 
			&& holdsMigrationHasSourceAndTargetTypes(migration)
			&& holdsNoComposedSourceAndTargetTypes(migration)) {
			
			// only validate source and target version for non-generic migrations 
			if (migration.typeVars.empty) {
				holdsHasPrincipalArgumentType(migration)
				&& holdsMigrationHasValidSourceAndTargetVersion(migration)
				&& holdsMigrationHasVersionExclusiveSourceAndTargetVersion(migration)
				&& holdsMigrationHasDifferentSourceAndTargetVersions(functionDeclaration, migration);
			}
				
			return;
		}
	}
	
	/** Checks that the #targetTypeRefs of TMigration aren't inferred but explicitly declared. */
	private def boolean holdsExplicitlyDeclaresReturnType(FunctionDeclaration migrationDeclaration, TMigration tMigration) {
		if (null === migrationDeclaration.returnTypeRef) {
			addIssue(IssueCodes.messageForIDL_MIGRATION_MUST_EXPLICITLY_DECLARE_RETURN_TYPE, migrationDeclaration,
				N4JSPackage.Literals.FUNCTION_DECLARATION__NAME, IssueCodes.IDL_MIGRATION_MUST_EXPLICITLY_DECLARE_RETURN_TYPE);
				return false;
		}
		return true;
	}
	
	/** Checks that the given migration has at least one source type and one (non-void) target type. */
	private def boolean holdsMigrationHasSourceAndTargetTypes(TMigration migration) {
		if (migration.sourceTypeRefs.empty || migration.targetTypeRefs.empty 
			|| (migration.targetTypeRefs.size == 1 && migration.targetTypeRefs.head.isVoidTypeRef)) {
			addIssue(IssueCodes.messageForIDL_MIGRATION_MUST_DECLARE_IN_AND_OUTPUT, migration.astElement,
				N4JSPackage.Literals.FUNCTION_DECLARATION__NAME, IssueCodes.IDL_MIGRATION_MUST_DECLARE_IN_AND_OUTPUT);
			return false;
		}
		
		// check that the migration does not declare an explicit {@code MigrationContext} parameter
		migration.fpars
			.filter[p | isMigrationContextTypeRef(p.typeRef)]
			.forEach[p |
				addIssueToMigrationTypeRef(IssueCodes.messageForIDL_MIGRATION_NO_EXPLICIT_CONTEXT_PARAMETER, p.typeRef,
					IssueCodes.IDL_MIGRATION_NO_EXPLICIT_CONTEXT_PARAMETER);
			]
		
		
		return true;
	}
	
	/** Checks that the source and target types of the given migration do not contain any {@link ComposedTypeRef}. */
	private def boolean holdsNoComposedSourceAndTargetTypes(TMigration migration) {
		val sourceTypesHoldNoComposedTypes = migration.sourceTypeRefs.allHold[ref | holdsIsNotComposedTypeRef(migration, ref)]
		val targetTypesHoldNoComposedTypes = migration.targetTypeRefs.allHold[ref | holdsIsNotComposedTypeRef(migration, ref)]
		
		return sourceTypesHoldNoComposedTypes && targetTypesHoldNoComposedTypes;
	}
	
	/** Returns {@code true} iff the {@code constraintChecker} function returns true for all elements in {@code elements}. 
	 * Always executes {@code constraintChecker} for all {@code elements}.*/
	private def <T> boolean allHold(Iterable<T> elements, Function<T, Boolean> constraintChecker) {
		return elements.fold(true, [previousResult, element | return constraintChecker.apply(element) && previousResult; ]);
	}
	
	/** Checks that the given migration has a valid principal argument type (non-null). */
	private def boolean holdsHasPrincipalArgumentType(TMigration migration) {
		if (null === migration.principalArgumentType) {
			addIssueToMultiValueFeature(IssueCodes.messageForIDL_MIGRATION_HAS_PRINCIPAL_ARGUMENT, migration.astElement,
				N4JSPackage.Literals.FUNCTION_DEFINITION__FPARS, IssueCodes.IDL_MIGRATION_HAS_PRINCIPAL_ARGUMENT);
			return false;
		}
		return true;
	}
	
	/** Checks that the given {@link TypeRef} is not an {@link ComposedTypeRef}. */
	private def boolean holdsIsNotComposedTypeRef(TMigration migration, TypeRef typeRef) {
		if (typeRef instanceof ComposedTypeRef) {
			addIssueToMigrationTypeRef(IssueCodes.messageForIDL_MIGRATION_SIGNATURE_NO_COMPOSED_TYPES, typeRef,
				IssueCodes.IDL_MIGRATION_SIGNATURE_NO_COMPOSED_TYPES);
			return false
		}
		return true;
	}
	
	/** Checks that the given migration source and target types allow to infer a source and target version. */
	private def boolean holdsMigrationHasValidSourceAndTargetVersion(TMigration migration) {
		if (migration.sourceVersion == 0) {
			val message = IssueCodes.getMessageForIDL_MIGRATION_VERSION_CANNOT_BE_INFERRED("source", migration.name);
			addIssueToMultiValueFeature(message, migration.astElement, N4JSPackage.Literals.FUNCTION_DEFINITION__FPARS, 
				IssueCodes.IDL_MIGRATION_VERSION_CANNOT_BE_INFERRED);
			return false;
		}
		
		if (migration.targetVersion == 0) {
			val message = IssueCodes.getMessageForIDL_MIGRATION_VERSION_CANNOT_BE_INFERRED("target", migration.name);
			addIssue(message, migration.astElement, N4JSPackage.Literals.FUNCTION_DEFINITION__RETURN_TYPE_REF,
				IssueCodes.IDL_MIGRATION_VERSION_CANNOT_BE_INFERRED);
			return false;
		}
		
		return true;
	}
	
	/** 
	 * Checks that the source and target types all are exclusively of one version (one source version, one target version).
	 * 
	 * Does nothing for migrations with {@TMigration#hasDeclaredSourceAndTargetVersion}.
	 */
	private def boolean holdsMigrationHasVersionExclusiveSourceAndTargetVersion(TMigration migration) {
		// do not validate explicitly declared source and target version
		if (migration.hasDeclaredSourceAndTargetVersion) {
			return true;
		}
		
		if (!isVersionExclusive(migration.sourceTypeRefs)) {
			val message = IssueCodes.getMessageForIDL_MIGRATION_AMBIGUOUS_VERSION("source", migration.name);
			addIssueToMultiValueFeature(message, migration.astElement, N4JSPackage.Literals.FUNCTION_DEFINITION__FPARS,
				IssueCodes.IDL_MIGRATION_VERSION_CANNOT_BE_INFERRED);
			return false;
		}
		
		if (!isVersionExclusive(migration.targetTypeRefs)) {
			val message = IssueCodes.getMessageForIDL_MIGRATION_AMBIGUOUS_VERSION("target", migration.name);
			addIssue(message, migration.astElement, N4JSPackage.Literals.FUNCTION_DEFINITION__RETURN_TYPE_REF,
				IssueCodes.IDL_MIGRATION_VERSION_CANNOT_BE_INFERRED);
			return false;
		}
		
		return true;
	}
	
	private def boolean holdsMigrationHasDifferentSourceAndTargetVersions(FunctionDeclaration declaration, TMigration migration) {
		if (migration.sourceVersion == migration.targetVersion) {
			val msg = IssueCodes.getMessageForIDL_MIGRATION_SAME_SOURCE_AND_TARGET_VERSION(migration.name, migration.sourceVersion);
			
			// Depending on where the source and target version are declared,
			// add an issue to the @Migration annotation or to the migration name
			if (migration.hasDeclaredSourceAndTargetVersion) {
				val migrationAnno = AnnotationDefinition.MIGRATION.getAnnotation(declaration);
				addIssueToMultiValueFeature(msg, migrationAnno, 
					N4JSPackage.Literals.ANNOTATION__ARGS,
					IssueCodes.IDL_MIGRATION_SAME_SOURCE_AND_TARGET_VERSION);
				return false;
			} else {
				addIssue(msg, declaration, 
					N4JSPackage.Literals.FUNCTION_DECLARATION__NAME,
					IssueCodes.IDL_MIGRATION_SAME_SOURCE_AND_TARGET_VERSION);
				return false;
			}
		}
		
		return true;
	}
	
	/** Returns {@code true} iff the given type reference refers to the built-in {@code void} type. */
	private def boolean isVoidTypeRef(TypeRef typeRef) {
		val builtInTypes = BuiltInTypeScope.get(typeRef.eResource.resourceSet);
		return typeRef.declaredType == builtInTypes.voidType;
	}
	
	/** Returns {@code true} iff the given type reference refers to the built-in {@code MigrationContext} type. */
	private def boolean isMigrationContextTypeRef(TypeRef typeRef) {
		val builtInTypes = BuiltInTypeScope.get(typeRef.eResource.resourceSet);
		return typeRef.declaredType == builtInTypes.migrationContextType;
	}

	@Check
	public def checkMigratableTypeDeclaration(N4TypeDeclaration typeDeclaration) {
		val declaredType = typeDeclaration.definedType;
		
		// do not validate a broken AST
		if (null === declaredType) {
			return;
		}
		
		// this validation only applies to {@link TMigratable} types
		if (!(declaredType instanceof TMigratable)) {
			return;
		}
		
		val migratable = declaredType as TMigratable;
		
		val migrationGroups = migratable.migrations
			// first, group the migrations by target version and source type count 
			.groupBy[targetVersion -> sourceTypeRefs.size]
			// filter out groups with invalid targetVersion, sourceTypeRef count and groups of one
			.filter[groupKey, migrations| 
				groupKey.key /* targetVersion */ > 0 
				&& groupKey.value /* sourceTypeRefs.size */ > 0 
				&& migrations.size > 0
			];
	
		// check remaining groups for being distinguishable by a type switch (adds issue otherwise)		
		migrationGroups.forEach[groupKey, migrations | holdsTypeSwitchDistinguishable(migrations)]	
	
	}
	
	private def RuleEnvironment ruleEnvironment(TypeRef ref) {
		return typeSystem.createRuleEnvironmentForContext(ref, ref.eResource);
	}
	
	/** Convenience method to enable the use of {@link Procedure2} when iterating over {@link Pair} streams. */
	private static def <T1, T2> void forPair(Stream<Pair<T1, T2>> stream, Procedure2<T1, T2> procedure) {
		stream.forEach[pair |
			procedure.apply(pair.key, pair.value);
		]
	}
	
	/** 
	 * Returns {@code true} iff all type references in left are equaltype (cf. {@link N4JSTypeSystem#equalType}) of 
	 * the corresponding type references in right. 
	 * 
	 * Always returns {@code false} if left holds a different number of type reference than right.
	 * */
	private def boolean areEqualTypes(RuleEnvironment ruleEnv, Iterable<TypeRef> left, Iterable<TypeRef> right) {
		if (left.size != right.size) {
			return false;
		}
		
		val firstNonSubtype = Iterables2.align(left, right).findFirst[ pair |
			val l = pair.key;
			val r = pair.value;
			val subtypingResult = typeSystem.equaltype(ruleEnv, l, r);
			
			return subtypingResult.failure;
		];
		
		// if no non-subtype can be found, left must be subtypes of right
		return firstNonSubtype === null;
	}
	
	/**
	 * Returns {@code true} iff the given list of migrations is distinguishable by type switches.
	 * 
	 * This method assumes that all of the given migrations already have the same number of source types.
	 */
	private def boolean holdsTypeSwitchDistinguishable(Iterable<TMigration> migrations) {
		// generalize the migration source type refs using {@link MigrationSwitchComputer#toSwitchRecognizableTypeRef}.
		val List<Pair<TMigration, List<TypeRef>>> migrationAndSwitchTypes = migrations
			.map[migration | migration -> migration.switchRecognizableSourceTypeRefs ].toList;
		
		val conflictGroups = new HashMap<TMigration, Set<TMigration>>();
		
		Collections2.pairs(migrationAndSwitchTypes).forPair[ m1, m2 |
			val tMigration1 = m1.key;
			val switchTypes1 = m1.value;
			
			val tMigration2 = m2.key;
			val switchTypes2 = m2.value;
			
			// skip migrations with invalid switch types
			if (switchTypes1.size == 0 || switchTypes2.size == 0) { return; }
			
			val ruleEnv = tMigration1.sourceTypeRefs.head.ruleEnvironment;
			
			// check whether the simplified SwitchCondition types are equal
			if (areEqualTypes(ruleEnv, switchTypes1, switchTypes2)) {
				// if so add a migration conflict group
				conflictGroups.addMigrationConflict(tMigration1, tMigration2);
			}
		];
		
		// add issues to all migrations of a conflict group
		conflictGroups.values.toSet.forEach[conflictGroup | addIssueMigrationConflict(conflictGroup)];
		
		// constraint is fulfilled if there are no conflicts
		return conflictGroups.empty;
	}
	
	/**
	 * Returns a list of switch-recognizable {@link TypeRef}s based on the source type refs of the given {@code migration}.
	 * 
	 * Returns an empty list if any of the given {@link TypeRef} cannot be handled by a {@link SwitchCondition} 
	 * (cf. {@link MigrationSwitchComputer#UnhandledTypeRefException}).
	 * 
	 * Adds issues for unsupported type refs using {@link #addUnhandledParameterTypeRefIssue}.
	 */
	private def List<TypeRef> getSwitchRecognizableSourceTypeRefs(TMigration migration) {
		val refs = migration.sourceTypeRefs.map[s | 
			try {
				switchComputer.toSwitchRecognizableTypeRef(s.ruleEnvironment, s)
			} catch (UnhandledTypeRefException e) {
				addUnsupportedParameterTypeRefIssue(migration.fpars.findFirst[par | par.typeRef == s]);
				return null
			}
		].filterNull.toList;
		
		if (refs.size != migration.sourceTypeRefs.size) {
			return #[];
		}
		return refs;
	}
	
	/** Adds an IDL_MIGRATION_UNSUPPORTED_PARAMETER_TYPE issue to the given {@link TFormalParameter}'s TypeRef. */
	private def void addUnsupportedParameterTypeRefIssue(TFormalParameter tParam) {
		// Obtain AST-model TypeRef from TFormalParameter.
		// This is ensured to not trigger any proxy resolution since the validation that uses this method 
		// only concerns elements local to the module the validated type declaration resides in. 
		val astTypeRef = (tParam.astElement as FormalParameter).declaredTypeRef;
		
		// skip composed type references, as there is validation for that case in {@link #checkMigration}.
		if (astTypeRef instanceof ComposedTypeRef) { return; }
		
		addIssue(IssueCodes.getMessageForIDL_MIGRATION_UNSUPPORTED_PARAMETER_TYPE(astTypeRef.typeRefAsString), 
			astTypeRef, IssueCodes.IDL_MIGRATION_UNSUPPORTED_PARAMETER_TYPE)
	}
	
	/**
	 * Adds a migration conflict to given map of conflictGroups.
	 * 
	 * That is, if m1 and m2 were not yet known to conflict, there corresponding set associated via conflictGroups
	 * are merged and re-assigned to their entries in the map.
	 * 
	 * Otherwise, this method does nothing.  
	 */
	private def void addMigrationConflict(Map<TMigration, Set<TMigration>> conflictGroups, TMigration m1, TMigration m2) {
		// merge conflict groups of migration 1 and 2
		val mergedGroup = conflictGroups.getOrDefault(m1, new HashSet(#[m1]))
		
		// if conflict group of m1 already contains m2, there is nothing to do
		if (mergedGroup.contains(m2)) { return; }

		// merge the conflict groups
		mergedGroup.addAll(conflictGroups.getOrDefault(m2, new HashSet(#[m2])));
		
		conflictGroups.put(m1, mergedGroup);
		conflictGroups.put(m2, mergedGroup);
	}
	
	/** Adds an {@link IssueCodes#IDL_MIGRATION_CONFLICT_WITH} issue for all given migrations,
	 * if the list of migration exceeds a length of 1. */
	private def void addIssueMigrationConflict(Iterable<TMigration> migrations) {
		// no conflict
		if (migrations.size <= 1) { return; }
		
		migrations.forEach[ m |
			val argumentDescription = MigratableUtils.getMigrationArgumentsDescription(m.sourceTypeRefs);
			val conflictingMigrationsDescription = listOrSingleMigrationDescription(migrations.filter[other | other != m]);

			val msg = IssueCodes.getMessageForIDL_MIGRATION_CONFLICT_WITH(m.name, argumentDescription, m.targetVersion, conflictingMigrationsDescription);
			addIssue(msg, m.astElement, N4JSPackage.Literals.FUNCTION_DECLARATION__NAME, IssueCodes.IDL_MIGRATION_CONFLICT_WITH);
		]
	}
	
	/** 
	 * Returns either a bullet-list description of multiple migrations, 
	 * or in the case of a single migration just the description of that migration. 
	 */
	private def String listOrSingleMigrationDescription(Iterable<TMigration> migrations) {
		if (migrations.size == 1) {
			return migrations.head.migrationAsString;
		} else {
			return migrations.map[m | "\n\t - " + m.migrationAsString].join(", ");
		}
	} 
	
	/**
	 * Returns {@code true} if the given list of {@link TypeRef}s do not mix mulitple
	 * model versions (Multiple {@link VersionedParameterizedTypeRef} with different versions).
	 */
	private def boolean isVersionExclusive(Collection<TypeRef> typeRefs) {
		val versions = typeRefs.stream
			.flatMap[ref | VersionableUtils.streamVersionedSubReferences(ref)]
			.mapToInt([versionedRef | versionedRef.version.intValue])
			.distinct.limit(2).toArray;
		
		return versions.size == 1;
	}
	
	/**
	 * Adds an issue to a migration source or target type ref.
	 * 
	 * Makes sure to only mark the relevant offset in the code (e.g. only the parameter declaration at the correct index).
	 */
	private def addIssueToMigrationTypeRef(String message, TypeRef typeRef, String issueCode) {
		val migration = EcoreUtil2.getContainerOfType(typeRef, TMigration);
		if (null === migration) {
			// if the given type reference is not contained by a migration, fall-back to EObject based issue marking 
			addIssue(message, typeRef, issueCode);
		}
		
		if (typeRef.eContainer instanceof TFormalParameter) {
			val parameterIndex = migration.fpars.indexOf(typeRef.eContainer);
			addIssue(message, migration.astElement, N4JSPackage.Literals.FUNCTION_DEFINITION__FPARS, parameterIndex, issueCode);
		} else if (typeRef.eContainer instanceof TMigration) {
			addIssue(message, migration.astElement, N4JSPackage.Literals.FUNCTION_DEFINITION__RETURN_TYPE_REF, issueCode);
		} else {
			// Unknown case. Fall-back to EObject based issue marking.
			addIssue(message, typeRef, issueCode); 
		}
	}	
}
