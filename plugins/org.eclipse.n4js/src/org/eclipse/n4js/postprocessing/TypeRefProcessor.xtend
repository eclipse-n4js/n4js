/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.postprocessing

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.n4JS.N4JSASTUtils
import org.eclipse.n4js.n4JS.TypeReferenceNode
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.utils.TypeUtils
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.EcoreUtilN4

/**
 * Processor for converting the raw type references provided by the parser into valid type references that
 * can be used internally.
 * <p>
 * Most type references created by the parser are already valid and can be used directly; the only exceptions
 * are:
 * <ul>
 * <li>{@link TypeRef#isAliasUnresolved() unresolved} references to type aliases must be converted to
 * {@link TypeRef#isAliasResolved() resolved} references to type aliases (note: type aliases in the TModule
 * are not handled here but in {@link TypeAliasProcessor}).
 * </ul>
 */
package class TypeRefProcessor extends AbstractProcessor {

	@Inject
	private TypeSystemHelper tsh;

	def void handleTypeRefs(RuleEnvironment G, EObject node, ASTMetaInfoCache cache) {
		for (TypeReferenceNode<?> typeRefNode : N4JSASTUtils.getContainedTypeReferenceNodes(node)) {
			val typeRefProcessed = doHandleTypeRef(G, typeRefNode.typeRefInAST);
			if (typeRefProcessed !== null) {
				EcoreUtilN4.doWithDeliver(false, [
					typeRefNode.cachedProcessedTypeRef = typeRefProcessed;
				], typeRefNode);
			}
		}
	}

	def private TypeRef doHandleTypeRef(RuleEnvironment G, TypeRef typeRef) {
		if (typeRef === null) {
			return null;
		}
		// note: we also have to resolve type aliases that might be nested below a non-alias TypeRef!
		var resolved = tsh.resolveTypeAliases(G, typeRef);
		if (resolved === typeRef) {
			// temporary tweak to ensure correctness of code base:
			// if nothing was resolved, we could directly use 'typeRef' as the value of property
			// TypeReferenceNode#cachedProcessedTypeRef; however, then the return value of operation
			// TypeReferenceNode#getTypeRef() would sometimes be contained in the AST and sometimes not;
			// by always creating a copy here, we force the entire code base to be able to cope with the
			// fact that the return value of TypeReferenceNode#getTypeRef() might not be contained in the
			// AST (similarly as type aliases were used everywhere in the code, for testing)
			resolved = TypeUtils.copy(typeRef);
		}
		return resolved;
	}
}
