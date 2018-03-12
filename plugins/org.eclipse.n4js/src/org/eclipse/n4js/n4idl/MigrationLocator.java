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
package org.eclipse.n4js.n4idl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4idl.versioning.VersionUtils;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TMigratable;
import org.eclipse.n4js.ts.types.TMigration;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;
import org.eclipse.xsemantics.runtime.RuleEnvironment;

import com.google.inject.Inject;

/**
 * Locator to find {@link TMigration}s that fit a given list of argument {@link TypeRef}s.
 */
public class MigrationLocator {
	@Inject
	private N4JSTypeSystem typeSystem;

	/**
	 * Types the return value of a {@code migrate} operator.
	 *
	 * @param arguments
	 *            The migration arguments
	 * @param contextMigration
	 *            The context migration from which the migration is invoked
	 */
	public Optional<TMigration> findMigration(List<Argument> arguments, TMigration contextMigration) {
		// we cannot find a migration for an empty list of arguments
		if (arguments.isEmpty()) {
			return Optional.empty();
		}

		final List<TypeRef> argumentTypeRefs = getTypeRefsFromArguments(arguments);

		return selectMigrationCandidate(argumentTypeRefs, migrationCandidates(argumentTypeRefs), contextMigration);
	}

	/**
	 * Returns the {@link TypeRef}s of the given list of {@link Argument}s.
	 */
	public List<TypeRef> getTypeRefsFromArguments(List<Argument> arguments) {
		return arguments.stream()
				.map(a -> typeSystem.tau(a.getExpression()))
				.filter(a -> a != null)
				.collect(Collectors.toList());
	}

	private Stream<TMigration> migrationCandidates(List<TypeRef> argumentTypeRefs) {
		return argumentTypeRefs.stream()
				.flatMap(ref -> VersionUtils.streamVersionedSubReferences(ref))
				.map(ref -> ref.getDeclaredType())
				.filter(type -> null != type)
				.filter(type -> type instanceof TMigratable)
				.flatMap(migratable -> ((TMigratable) migratable).getMigrations().stream());
	}

	/**
	 * Returns a list of all migration candidates for the given list of migration arguments.
	 */
	public List<TMigration> getMigrationCandidates(List<Argument> arguments) {
		if (arguments.isEmpty()) {
			return Collections.emptyList();
		}
		return migrationCandidates(getTypeRefsFromArguments(arguments)).collect(Collectors.toList());
	}

	/**
	 * Selects the {@link TMigration} that matches the given list of {@link TMigratable}s best.
	 *
	 * @param arguments
	 *            The list of migration arguments.
	 * @param candidates
	 *            The available list of migration candidates.
	 * @param contextMigration
	 *            The context {@link Resource} to perform subtype checks in
	 * @return The migration out of {@code candidates} that is most compatible. If no candidate is compatible, this
	 *         method returns and absent optional.
	 */
	private Optional<TMigration> selectMigrationCandidate(List<TypeRef> arguments, Stream<TMigration> candidates,
			TMigration contextMigration) {
		return candidates
				// filter candidates by target version
				.filter(migration -> migration.getTargetVersion() == contextMigration.getTargetVersion())
				// filter candidates by argument type compatibility
				.filter(migration -> migrationCompatible(migration, arguments, contextMigration.eResource()))
				.findFirst();
	}

	private boolean migrationCompatible(TMigration migration, List<TypeRef> arguments, Resource contextResource) {
		// if the argument count does not match, the migration can not be compatible
		if (arguments.size() != migration.getSourceTypeRefs().size()) {
			return false;
		}

		for (int argumentIndex = 0; argumentIndex < migration.getSourceTypeRefs().size(); argumentIndex++) {
			final TypeRef migrationTypeRef = migration.getSourceTypeRefs().get(argumentIndex);
			final TypeRef argumentTypeRef = arguments.get(argumentIndex);

			final RuleEnvironment ruleEnv = typeSystem.createRuleEnvironmentForContext(argumentTypeRef,
					contextResource);

			// if subtype-check for this argument fails, return false
			if (typeSystem.subtype(ruleEnv, argumentTypeRef, migrationTypeRef).failed()) {
				return false;
			}
		}

		return true;
	}
}