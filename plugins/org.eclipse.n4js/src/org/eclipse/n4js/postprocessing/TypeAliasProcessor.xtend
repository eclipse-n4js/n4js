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
import com.google.inject.Singleton
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.n4JS.ExportedVariableDeclaration
import org.eclipse.n4js.n4JS.TypeDefiningElement
import org.eclipse.n4js.ts.typeRefs.FunctionTypeExpression
import org.eclipse.n4js.ts.typeRefs.StructuralTypeRef
import org.eclipse.n4js.ts.typeRefs.TypeArgument
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TypeAlias
import org.eclipse.n4js.typesystem.utils.RuleEnvironment
import org.eclipse.n4js.typesystem.utils.TypeSystemHelper
import org.eclipse.n4js.utils.EcoreUtilN4

/**
 * Handles resolution of type aliases during post-processing, <b>but only in the <code>TModule</code></b>.
 * Handling of type alias resolution of type references contained in the AST is part of the responsibility
 * of {@link TypeRefProcessor}.
 * <p>
 * For details on type alias resolution, see {@link TypeRef#isAliasResolved()}.
 */
@Singleton
package class TypeAliasProcessor extends AbstractProcessor {

	@Inject
	private TypeSystemHelper tsh;

	def void handleTypeAlias(RuleEnvironment G, EObject astNode, ASTMetaInfoCache cache) {
		// Note: it would be great if we could resolve the declaredTypeRef of TypedElements
		// here (i.e. if 'obj' is a TypedElement); however, those type references are AST nodes,
		// so this would mean changing the AST, which isn't allowed.

		val defType = switch(astNode) {
			TypeDefiningElement:
				astNode.definedType
			StructuralTypeRef:
				astNode.structuralType
			FunctionTypeExpression:
				astNode.declaredType
			ExportedVariableDeclaration:
				astNode.definedVariable
		};
		if (defType !== null) {
			resolveAliasInTypeModelElement(G, defType);
		}
	}

	def private void resolveAliasInTypeModelElement(RuleEnvironment G, IdentifiableElement elem) {
		if (elem instanceof TypeAlias) {
			return; // do not resolve the 'actualTypeRef' property in type alias itself
		}
		val allNestedTypeArgs = newArrayList; // create list up-front to not confuse tree iterator when replacing nodes!
		val iter = elem.eAllContents;
		while (iter.hasNext) {
			val obj = iter.next;
			if (obj instanceof TypeArgument) {
				allNestedTypeArgs.add(obj);
				iter.prune();
			}
		}
		for (typeArg : allNestedTypeArgs) {
			val typeArgResolved = tsh.resolveTypeAliases(G, typeArg);
			if (typeArgResolved !== typeArg) {
				val containmentFeature = typeArg.eContainmentFeature;
				val isValidType = containmentFeature !== null
					&& containmentFeature.getEReferenceType().isSuperTypeOf(typeArgResolved.eClass);
				if (isValidType) {
					EcoreUtilN4.doWithDeliver(false, [
						EcoreUtil.replace(typeArg, typeArgResolved);
					], typeArg.eContainer);
				}
			}
		}
	}
}
