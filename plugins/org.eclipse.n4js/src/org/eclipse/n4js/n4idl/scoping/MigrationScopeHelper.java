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
import org.eclipse.n4js.n4idl.MigrationLocator;
import org.eclipse.n4js.n4idl.N4IDLGlobals;
import org.eclipse.n4js.n4idl.versioning.MigrationUtils;
import org.eclipse.n4js.resource.N4JSEObjectDescription;
import org.eclipse.n4js.ts.types.TMigration;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.SingletonScope;

import com.google.inject.Inject;

/**
 * A helper class to create N4IDL migration specific scopes.
 */
public class MigrationScopeHelper {

	@Inject
	private MigrationLocator migrationLocator;

	/** Returns a new migration scope for the given list of arguments and context. */
	public IScope migrationScope(List<Argument> arguments, EObject context) {
		final Optional<TMigration> contextMigration = MigrationUtils.getTMigrationOf(context);

		// only proceed with a valid context migration
		if (!contextMigration.isPresent()) {
			return IScope.NULLSCOPE;
		}
		final TMigration contextTMigration = contextMigration.get();
		final Optional<TMigration> targetMigration = migrationLocator.findMigration(arguments, contextTMigration);

		if (targetMigration.isPresent()) {
			return new SingletonScope(
					N4JSEObjectDescription.create(QualifiedName.create(N4IDLGlobals.MIGRATE_CALL_KEYWORD),
							targetMigration.get()),
					IScope.NULLSCOPE);
		} else {
			return IScope.NULLSCOPE;
		}
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
