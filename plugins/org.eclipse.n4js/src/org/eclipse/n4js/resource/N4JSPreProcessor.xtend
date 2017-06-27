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
package org.eclipse.n4js.resource

import com.google.inject.Singleton
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.validation.ASTStructureValidator
import org.eclipse.emf.ecore.EObject

/**
 * This class performs some early pre-processing of the AST. This happens after {@link N4JSLinker lazy linking} and
 * before {@link ASTStructureValidator AST structure validation}.
 */
@Singleton
package final class N4JSPreProcessor {

	/**
	 * Performs an early processing of the AST, e.g. initialization of transient helper values.
	 * <b>This method assumes that it is allowed to change the AST!</b> Thus, it should be invoked from an "exec without
	 * cache clear" handler, see {@code OnChangeEvictingCache#execWithoutCacheClear(N4JSResource,IUnitOfWork)}.
	 */
	def public void process(Script script, N4JSResource resource) {
		val resourceSet = resource.resourceSet;
		if( resourceSet === null ) {
			// null-safe exit - required in smoke test where Resources detached from a ResourceSet are used.
			return;
		}
		val builtInTypes = BuiltInTypeScope.get( resourceSet );
		for (node : resource.script.eAllContents.toIterable) {
			processNode(node, resource, builtInTypes);
		}
	}

	def private dispatch void processNode(EObject astNode, N4JSResource resource, BuiltInTypeScope builtInTypes) {
		// by default, do nothing
	}

	/**
	 * Support for new array type syntax:
	 * <pre>
	 * var arr: [string];
	 * </pre>
	 */
	def private dispatch void processNode(ParameterizedTypeRef typeRef, N4JSResource resource, BuiltInTypeScope builtInTypes) {
		if (typeRef.isArrayTypeLiteral) {
			typeRef.declaredType = builtInTypes.arrayType;
		}
	}
}
