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
package org.eclipse.n4js.scoping.diagnosing;

import java.util.List;
import java.util.Optional;

import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4idl.migrations.MigrationLocator;
import org.eclipse.n4js.n4idl.versioning.MigrationUtils;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.TMigration;
import org.eclipse.n4js.ts.versions.MigratableUtils;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;

/**
 * A scoping diagnosis for the case that a matching migration candidate cannot be found for a given list of arguments.
 */
public class N4IDLNoMatchingMigrationCandidateDiagnosis extends ScopingDiagnosis<IdentifierRef> {

	@Inject
	private MigrationLocator migrationLocator;

	@Override
	DiagnosticMessage diagnose(QualifiedName name, IdentifierRef identifierRef) {
		final ParameterizedCallExpression migrateCall = (ParameterizedCallExpression) identifierRef.eContainer();

		final List<TypeRef> argumentTypeRefs = migrationLocator.getTypeRefsFromArguments(migrateCall.getArguments());
		final Iterable<TMigration> candidates = migrationLocator.getMigrationCandidates(migrateCall.getArguments());
		final Optional<TMigration> contextMigration = MigrationUtils.getTMigrationOf(identifierRef);

		final int contextTargetVersion = contextMigration.map(c -> c.getTargetVersion()).orElse(0);
		final String contextTargetVersionDescription = contextTargetVersion != 0
				? Integer.toString(contextTargetVersion) : "<unknown target version>";

		final String argumentsDescription = MigratableUtils.getMigrationArgumentsDescription(argumentTypeRefs);
		final String candidatesDescription = MigratableUtils.getMigrationCandidatesList(candidates);
		final String candidateClause = candidatesDescription.isEmpty()
				? "" // if there are no candidates, do not add candidates clause
				: "Candidates are:" + candidatesDescription; // otherwise list candidates

		final String message = IssueCodes.getMessageForIDL_MIGRATION_NO_MATCHING_CANDIDATE(argumentsDescription,
				contextTargetVersionDescription, candidateClause);

		return createMessage(IssueCodes.IDL_MIGRATION_NO_MATCHING_CANDIDATE, message);
	}

}
