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
package org.eclipse.n4js.tests.typesbuilder.extensions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;

public class ResourceAssertionsExtensions {

	public void assertASTResolved(String phase, N4JSResource newN4jsResource) {
		EObject astElement = ((SyntaxRelatedTElement) newN4jsResource.getContents().get(1)).getAstElement();
		assertFalse(phase + ": AST element no proxy when fetched from type", astElement.eIsProxy());
	}

	public void resolveAST(String phase, N4JSResource newN4jsResource) {
		EObject ast = newN4jsResource.getContents().get(0); // this resolves the AST if is was not resolved before;
		assertFalse(phase + ": AST no proxy after first access", ast.eIsProxy());
	}

	public void assertASTIsProxifed(String phase, N4JSResource newN4jsResource) {
		assertTrue(phase + ": AST is proxy",
				((BasicEList<? extends EObject>) newN4jsResource.getContents()).basicGet(0).eIsProxy());
	}

	public void assertResourceHasNoErrors(String phase, Resource testResource) {
		assertTrue(phase + ": " + testResource.getURI().trimFileExtension().lastSegment()
				+ " should have no errors but: " + testResource.getErrors().toString(),
				testResource.getErrors().isEmpty());
	}
}
