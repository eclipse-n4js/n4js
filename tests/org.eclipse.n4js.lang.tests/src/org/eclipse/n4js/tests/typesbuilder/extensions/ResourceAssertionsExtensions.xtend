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
package org.eclipse.n4js.tests.typesbuilder.extensions

import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource

import static org.junit.Assert.*

/**
 */
class ResourceAssertionsExtensions {

	def void assertASTResolved(String phase, N4JSResource newN4jsResource) {
		val astElement = (newN4jsResource.contents.get(1) as SyntaxRelatedTElement).astElement
		assertFalse(phase + ": AST element no proxy when fetched from type", astElement.eIsProxy)
	}

	def void resolveAST(String phase, N4JSResource newN4jsResource) {
		val ast = newN4jsResource.contents.get(0); // this resolves the AST if is was not resolved before
		assertFalse(phase + ": AST no proxy after first access", ast.eIsProxy)
	}

	def void assertASTIsProxifed(String phase, N4JSResource newN4jsResource) {
		assertTrue(phase + ": AST is proxy", (newN4jsResource.contents as BasicEList<? extends EObject>).basicGet(0).eIsProxy)
	}

	def void assertResourceHasNoErrors(String phase, Resource testResource) {
		assertTrue(phase + ": " + testResource.URI.trimFileExtension.lastSegment + " should have no errors but: " + testResource.errors.toString, testResource.errors.empty)
	}
}
