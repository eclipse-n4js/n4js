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
package org.eclipse.n4js.validation.validators

import org.eclipse.n4js.n4JS.N4ClassifierDefinition
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.ContainerType
import org.eclipse.n4js.ts.types.TMember

/**
 * Helper class extracted from N4JSAllMemberValidator
 */
class FindClassifierInHierarchyUtils {

	def static Iterable<ParameterizedTypeRef> findSuperTypesWithMember(N4ClassifierDefinition classifier, TMember member) {
		return classifier.superClassifierRefs.map[typeRef].filter[typeRef|
			val declType = typeRef?.declaredType;
			return if (declType instanceof ContainerType<?>) declType.ownedMembers.contains(member) else false;
		];
	}
}
