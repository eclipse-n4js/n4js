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
package org.eclipse.n4js.ts.versions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TMigratable;
import org.eclipse.n4js.ts.types.TMigration;

/**
 * Utility methods with regard to {@link TMigration}s and {@link TMigratable}s.
 */
public class MigratableUtils {

	/**
	 * Returns a user-faced description of a given list of {@link TMigration} candidates.
	 *
	 * This description may be used in error messages.
	 *
	 * Example: <code>
	 *  - (A#1) => (A#2)
	 *  - (Array<A#1>) => (Array<A#2>)
	 * </code>
	 *
	 */
	public static String getMigrationCandidatesList(Iterable<TMigration> candidates) {
		return StreamSupport.stream(candidates.spliterator(), false)
				.distinct()
				.sorted((m1, m2) -> m1.getName().compareTo(m2.getName()))
				.map(c -> "\n\t - " + c.getMigrationAsString())
				.collect(Collectors.joining());
	}

	/**
	 * Returns the principal argument (first migratable argument) of the given list of argument/parameter type
	 * references.
	 *
	 * Returns {@link Optional#empty()} if the given list of type refs contains no migratable argument.
	 */
	public static Optional<TMigratable> findPrincipalMigrationArgument(List<TypeRef> argumentTypeRefs) {
		return argumentTypeRefs.stream()
				.flatMap(s -> VersionableUtils.streamVersionedSubReferences(s))
				.map(ref -> ref.getDeclaredType()) // map to declared types
				// find first TMigratable type
				.filter(t -> t instanceof TMigratable).map(t -> ((TMigratable) t))
				.findFirst();
	}

	/**
	 * Returns a user-faced description of the given list of migration argument {@link TypeRef}s.
	 *
	 * The description may be used in error messages.
	 */
	public static String getMigrationArgumentsDescription(List<TypeRef> argumentTypeRefs) {
		return String.format("(%s)", argumentTypeRefs.stream()
				.map(ref -> ref.getTypeRefAsString())
				.collect(Collectors.joining(", ")));
	}
}
