/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xpect.methods

import com.google.inject.Inject
import junit.framework.AssertionFailedError
import org.eclipse.n4js.n4idl.N4IDLGlobals
import org.eclipse.n4js.ts.types.TMigration
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter
import org.eclipse.xpect.XpectImport
import org.eclipse.xpect.expectation.IStringExpectation
import org.eclipse.xpect.expectation.StringExpectation
import org.eclipse.xpect.parameter.ParameterParser
import org.eclipse.xpect.runner.Xpect
import org.eclipse.xpect.xtext.lib.util.XtextOffsetAdapter.ICrossEReferenceAndEObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.scoping.IScopeProvider

/**
 * Provides X!PECT methods for testing the static linking of 'migrate' calls
 * in N4IDL migrations.
 */
@XpectImport(N4JSOffsetAdapter)
class MigrateScopeXpectMethod {
	@Inject private IScopeProvider scopeProvider;

	/**
	 * Tests the target migration name of the 'migrate' call at the given offset.
	 */
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	@Xpect
	public def void migrateScope(
			@StringExpectation IStringExpectation expectation,
			ICrossEReferenceAndEObject arg1) {
		if (expectation === null)
			throw new IllegalStateException("No expectation specified, add '--> <type switch string representation>'");

		val scope = scopeProvider.getScope(arg1.getEObject(), arg1.getCrossEReference());
		val description = scope.getSingleElement(QualifiedName.create(N4IDLGlobals.MIGRATE_CALL_KEYWORD))
		val migration = description.EObjectOrProxy;
		
		if (!(migration instanceof TMigration)) {
			throw new AssertionFailedError("The statically linked function of the migrate calls is not a migration:" + migration);
		}
		
		expectation.assertEquals(String.format("\"%s\"", (migration as TMigration).name));
	}
}
