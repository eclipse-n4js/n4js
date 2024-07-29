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
package org.eclipse.n4js.validation.validators;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;

import org.eclipse.n4js.n4JS.N4ClassifierDefinition;
import org.eclipse.n4js.n4JS.TypeReferenceNode;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.Type;

/**
 * Helper class extracted from N4JSAllMemberValidator
 */
public class FindClassifierInHierarchyUtils {

	/***/
	public static Iterable<TypeReferenceNode<ParameterizedTypeRef>> findSuperTypesWithMember(
			N4ClassifierDefinition classifier, TMember member) {

		return filter(classifier.getSuperClassifierRefs(), typeRefNode -> {
			TypeRef tref = typeRefNode == null ? null : typeRefNode.getTypeRef();
			Type declType = tref == null ? null : tref.getDeclaredType();
			return (declType instanceof ContainerType<?>)
					? ((ContainerType<?>) declType).getOwnedMembers().contains(member)
					: false;
		});
	}
}
