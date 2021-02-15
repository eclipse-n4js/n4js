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
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.ts.typeRefs.Versionable
import org.eclipse.n4js.ts.types.TypableElement
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter
import org.eclipse.xpect.XpectImport
import org.eclipse.xpect.expectation.IStringExpectation
import org.eclipse.xpect.expectation.StringExpectation
import org.eclipse.xpect.parameter.ParameterParser
import org.eclipse.xpect.runner.Xpect
import org.eclipse.n4js.ide.tests.helper.server.xt.IEObjectCoveringRegion

/**
 * Provides X!PECT methods for testing versions
 */
@XpectImport(N4JSOffsetAdapter)
class ModelVersionXpectMethod {
	@Inject
	N4JSTypeSystem ts;
 
	/**
	 * Tests the version of a classifier declaration or type reference
	 */
	@ParameterParser(syntax = "('of' arg1=OFFSET)?")
	@Xpect
	public def void version(
			@StringExpectation IStringExpectation expectation,
			IEObjectCoveringRegion arg1) {
		if (expectation === null)
			throw new IllegalStateException("No expectation specified, add '--> version'");

		val EObject object = arg1.getEObject();
		val int actualVersion = getVersion(object);
		expectation.assertEquals(toString(actualVersion));
	}

	private def int getVersion(EObject object) {
		switch (object) {
			IdentifierRef: return getVersion(object.id)
			Type: return object.version
			Versionable: object.version
			TypableElement: ts.tau(object).version
			default: throw new IllegalArgumentException("Cannot determine version of " + object.eClass)
		}
	}

	private def String toString(int version) {
		if (version === Integer.MAX_VALUE)
			return "*";
		return Integer.toString(version);
	}
}
