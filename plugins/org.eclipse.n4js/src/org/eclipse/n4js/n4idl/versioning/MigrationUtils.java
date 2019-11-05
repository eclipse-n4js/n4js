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
package org.eclipse.n4js.n4idl.versioning;

import java.util.Iterator;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.AnnotationDefinition;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TMigration;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.collect.Iterables;

/**
 * Utility methods for the handling of N4IDL migrations and {@link TMigration}s.
 */
public class MigrationUtils {
	private MigrationUtils() {
		// non-instantiable utility class
	}

	/**
	 * Returns {@code true} if the given {@link FunctionDeclaration} is a migration declaration.
	 */
	public static boolean isMigrationDefinition(FunctionDefinition functionDef) {
		return functionDef instanceof FunctionDeclaration &&
				AnnotationDefinition.MIGRATION.hasAnnotation(functionDef);
	}

	/**
	 * Returns {@code true} if the given context element is contained in a migration declaration.
	 */
	public static boolean isInMigration(EObject context) {
		return MigrationUtils.getMigrationDeclaration(context).isPresent();
	}

	/**
	 * Returns the {@link FunctionDeclaration} container of the given context that is considered to be a migration
	 * declaration (annotated with {@code @Migration}).
	 *
	 * Returns an empty {@link Optional} if no such container exists.
	 */
	public static Optional<FunctionDeclaration> getMigrationDeclaration(EObject context) {
		// find all {@link FunctionDeclaration} containers
		Iterable<FunctionDeclaration> functionDeclarationContainers = Iterables
				.filter(EcoreUtil2.getAllContainers(context), FunctionDeclaration.class);
		// filter by {@code @Migration} annotation
		Iterable<FunctionDeclaration> migrationContainers = Iterables.filter(functionDeclarationContainers,
				MigrationUtils::isMigrationDefinition);

		Iterator<FunctionDeclaration> iterator = migrationContainers.iterator();
		return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
	}

	/**
	 * Returns the {@link TMigration} of the given context, assuming it is contained in a migration declaration
	 * (function declaration annotated with {@code @Migration}).
	 *
	 * Returns an empty {@link Optional} otherwise (e.g. context is not contained in a migration, broken AST).
	 */
	public static Optional<TMigration> getTMigrationOf(EObject context) {
		final Optional<FunctionDeclaration> migrationContainer = getMigrationDeclaration(context);

		if (migrationContainer.isPresent()) {
			final TFunction definedFunction = migrationContainer.get().getDefinedFunction();
			if (definedFunction instanceof TMigration) {
				return Optional.of((TMigration) definedFunction);
			}
		}

		return Optional.empty();
	}

	/**
	 * Returns {@code true} iff the given object is a {@link ParameterizedCallExpression} and a valid migrate call.
	 */
	public static boolean isMigrateCall(EObject object) {
		return object instanceof ParameterizedCallExpression
				&& ((ParameterizedCallExpression) object).getTarget() instanceof IdentifierRef
				&& N4IDLGlobals.MIGRATION_CALL_IDENTIFIER
						.equals(((IdentifierRef) ((ParameterizedCallExpression) object).getTarget()).getIdAsText());
	}

	/**
	 * Returns {@code true} iff the given {@link IdentifierRef} is a migrate call.
	 */
	public static boolean isMigrateCallIdentifier(IdentifierRef identifierRef) {
		return isMigrateCall(identifierRef.eContainer())
				&& identifierRef.eContainingFeature() == N4JSPackage.Literals.EXPRESSION_WITH_TARGET__TARGET;
	}
}
