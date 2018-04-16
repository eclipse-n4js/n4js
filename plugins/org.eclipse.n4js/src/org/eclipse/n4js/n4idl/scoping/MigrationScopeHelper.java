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
package org.eclipse.n4js.n4idl.scoping;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.Variable;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.n4idl.migrations.MigrationLocator;
import org.eclipse.n4js.n4idl.versioning.MigrationUtils;
import org.eclipse.n4js.resource.N4JSEObjectDescription;
import org.eclipse.n4js.scoping.utils.IssueCodeBasedEObjectDescription;
import org.eclipse.n4js.scoping.utils.UnresolvableObjectDescription;
import org.eclipse.n4js.ts.types.TMigration;
import org.eclipse.n4js.ts.versions.MigratableUtils;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.SingletonScope;

import com.google.inject.Inject;

/**
 * A helper class to create N4IDL migration specific scopes.
 */
public class MigrationScopeHelper {

	@Inject
	private MigrationLocator migrationLocator;

	/** 
	 * Returns a new scope which contains the {@link TMigration}s that can be bound
	 * for the given list of arguments. 
	 * 
	 * @param context The context to create the scope for. 
	 * */
	public IScope migrationsScope(List<Argument> arguments, EObject context) {
		final Optional<TMigration> contextMigration = MigrationUtils.getTMigrationOf(context);

		// only proceed with a valid context migration
		if (!contextMigration.isPresent()) {
			return IScope.NULLSCOPE;
		}
		final TMigration contextTMigration = contextMigration.get();
		final List<TMigration> targetMigrations = migrationLocator.findMigration(arguments, contextTMigration);

		// if no matching migration can be found, we cannot link this migrate-call
		if (targetMigrations.isEmpty()) {
			// defensively return a UnresolvableObjectDescription instead of raising a linking issue
			return new SingletonScope(new UnresolvableObjectDescription(QualifiedName.create("migrate")),
					IScope.NULLSCOPE);
		}

		// create a description for the first migration match
		IEObjectDescription migrationDescription = N4JSEObjectDescription.create(
				QualifiedName.create(N4IDLGlobals.MIGRATE_CALL_KEYWORD),
				targetMigrations.get(0));

		if (targetMigrations.size() > 1) {
			// if we have found multiple candidates, wrap the description in an ambiguous-migrate-call error description
			migrationDescription = new IssueCodeBasedEObjectDescription(migrationDescription,
					IssueCodes.getMessageForIDL_MIGRATE_CALL_AMBIGUOUS(
							MigratableUtils.getMigrationCandidatesList(targetMigrations)),
					IssueCodes.IDL_MIGRATE_CALL_AMBIGUOUS, Severity.WARNING);
		}

		// return singleton scope which only contains the designated migration candidate
		return new SingletonScope(migrationDescription, IScope.NULLSCOPE);
	}

	/**
	 * A scope which additionally allows to access the current {@code MigrationContext} instance via the designated
	 * identifier {@code context}.
	 *
	 * @param declaration
	 *            The migration declaration for which the scope is created.
	 * @param parent
	 *            The parent scope.
	 */
	public IScope migrationContextAwareScope(FunctionDeclaration declaration, IScope parent) {
		Variable migrationContextVariable = declaration.getMigrationContextVariable();
		return new SingletonScope(
				N4JSEObjectDescription.create(QualifiedName.create(migrationContextVariable.getName()),
						migrationContextVariable),
				parent);
	}
}
