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
package org.eclipse.n4js.n4idl.migrations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4idl.migrations.TypeDistanceComputer.UnsupportedTypeDistanceOperandsException;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMigratable;
import org.eclipse.n4js.ts.types.TMigration;
import org.eclipse.n4js.ts.versions.VersionableUtils;
import org.eclipse.n4js.typesystem.N4JSTypeSystem;

import com.google.inject.Inject;

/**
 * Locator to find {@link TMigration}s that fit a given list of argument {@link TypeRef}s.
 */
public class MigrationLocator {
	@Inject
	private N4JSTypeSystem typeSystem;

	@Inject
	private TypeDistanceComputer typeDistanceComputer;

	/**
	 * Returns the {@link TMigration} instances which fit the given list of arguments the best (in terms of type
	 * distance).
	 *
	 * Returns an empty list if no compatible migration can be found.
	 *
	 * Returns more than one {@link TMigration} if there exist multiple equally-well matching migrations for the given
	 * list of arguments.
	 *
	 * @param arguments
	 *            The migration arguments
	 * @param contextMigration
	 *            The context migration from which the migration is invoked
	 */
	public List<TMigration> findMigration(List<Argument> arguments, TMigration contextMigration) {
		// we cannot find a migration for an empty list of arguments
		if (arguments.isEmpty()) {
			return Collections.emptyList();
		}

		final List<TypeRef> argumentTypeRefs = getTypeRefsFromArguments(arguments);
		final Iterable<TMigration> allMigrationCandidates = getAllMigrationCandidates(argumentTypeRefs);
		return selectMigrationCandidate(argumentTypeRefs, allMigrationCandidates, contextMigration);
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

	/**
	 * Returns a lazily initialized {@link Iterable} over all migration candidates for the given list of argument
	 * {@link TypeRef}s.
	 *
	 * @param argumentTypeRefs
	 *            The argument type references
	 */
	private Iterable<TMigration> getAllMigrationCandidates(List<TypeRef> argumentTypeRefs) {
		return () -> argumentTypeRefs.stream()
				// obtain all versioned sub-references from the given list of arguments
				.flatMap(ref -> VersionableUtils.streamVersionedSubReferences(ref))
				// get their declared types
				.map(ref -> ref.getDeclaredType())
				// make sure the current type is non-null and migratable
				.filter(type -> null != type).filter(type -> type instanceof TMigratable)
				// get all migrations for this migratable type (includes migrations of super-classifiers)
				.flatMap(migratable -> getAllMigrations((TMigratable) migratable))
				.iterator();
	}

	/**
	 * Returns a stream of all {@link TMigration}s associated with the given {@link TMigratable}.
	 *
	 * In case of classifiers, the stream will first yield the migrations associated with the closest classifier and
	 * then continue to also return the migration of super-classifiers.
	 *
	 * Otherwise, this method just returns migrations directly associated with the given migratable.
	 */
	private Stream<TMigration> getAllMigrations(TMigratable migratable) {
		// if the migratable may have super classifiers
		if (migratable instanceof TClassifier) {
			// return stream of migrations of this classifier followed by all migrations
			// of all migratable super-classifiers
			return Stream.concat(migratable.getMigrations().stream(),
					StreamSupport.stream(((TClassifier) migratable).getSuperClassifiers().spliterator(), false)
							.filter(classifier -> classifier instanceof TMigratable)
							.flatMap(superClassifier -> getAllMigrations((TMigratable) superClassifier)));
		} else {
			return migratable.getMigrations().stream();
		}
	}

	/**
	 * Returns a list of all migration candidates for the given list of migration arguments.
	 */
	public Iterable<TMigration> getMigrationCandidates(List<Argument> arguments) {
		if (arguments.isEmpty()) {
			return Collections.emptyList();
		}
		return getAllMigrationCandidates(getTypeRefsFromArguments(arguments));
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
	private List<TMigration> selectMigrationCandidate(List<TypeRef> arguments, Iterable<TMigration> candidates,
			TMigration contextMigration) {
		MigrationMatcher matcher = MigrationMatcher.emptyMatcher();

		for (TMigration migration : candidates) {
			try {
				// compute migration distance
				final double distance = typeDistanceComputer.computeDistance(arguments, migration.getSourceTypeRefs());

				// skip unrelated migrations
				if (distance == TypeDistanceComputer.MAX_DISTANCE) {
					continue;
				}

				matcher.match(migration, distance);

				// early-exit if we already found a perfect match
				if (matcher.hasPerfectMatch() && matcher.getAllMatches().size() == 1) {
					// There can always only be one perfect match, since otherwise the migration
					// declarations will conflict on the validation level.
					return Collections.singletonList(migration);
				}
			} catch (UnsupportedTypeDistanceOperandsException e) {
				// If we encounter invalid type distance operands
				// we fail-safe since we reside in scoping.
				// User-facing error markers for this case ought to be created in validation.
				continue;
			}
		}

		return matcher.getAllMatches();
	}
}
