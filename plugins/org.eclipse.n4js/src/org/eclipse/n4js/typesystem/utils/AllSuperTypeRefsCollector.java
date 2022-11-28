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
package org.eclipse.n4js.typesystem.utils;

import java.util.List;

import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.typeRefs.TypeRefsFactory;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.utils.DeclMergingHelper;

import com.google.common.collect.Lists;

/**
 */
public class AllSuperTypeRefsCollector extends AbstractCompleteHierarchyTraverser<List<ParameterizedTypeRef>> {

	private final List<ParameterizedTypeRef> result;

	/**
	 * Creates a new collector.
	 *
	 * @param typeRef
	 *            the type to start with.
	 */
	public AllSuperTypeRefsCollector(ParameterizedTypeRef typeRef, DeclMergingHelper declMergingHelper) {
		super(typeRef, declMergingHelper);
		result = Lists.newArrayList();
	}

	@Override
	protected List<ParameterizedTypeRef> doGetResult() {
		return result;
	}

	@Override
	protected boolean releaseGuard(ParameterizedTypeRef typeRef) {
		return true;
	}

	@Override
	protected void doProcess(ContainerType<?> containerType) {
		// if (containerType instanceof TClass) {
		// TClass casted = (TClass) containerType;
		// ParameterizedTypeRef superType = casted.getSuperClassRef();
		// result.addAll(casted.getImplementedInterfaceRefs());
		// if (superType != null) {
		// result.add(superType);
		// }
		// } else if (containerType instanceof TInterface) {
		// TInterface casted = (TInterface) containerType;
		// result.addAll(getSuperTypes(casted ));
		// }
		if (bottomType != containerType) {
			ParameterizedTypeRef typeRef = getCurrentTypeRef();
			if (typeRef == null) {
				typeRef = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
				typeRef.setDeclaredType(containerType);
			}
			result.add(typeRef);
		}
	}

	@Override
	protected void doProcess(PrimitiveType currentType) {
		PrimitiveType assignmentCompatible = currentType.getAssignmentCompatible();
		if (assignmentCompatible != null) {
			ParameterizedTypeRef typeRef = TypeRefsFactory.eINSTANCE.createParameterizedTypeRef();
			typeRef.setDeclaredType(assignmentCompatible);
			result.add(typeRef);
		}
	}

	/**
	 * Convenience method to create a new instance of {@link AllSuperTypeRefsCollector} and immediately return its
	 * result.
	 *
	 * @param typeRef
	 *            the type ref to start with.
	 * @return transitive closure of all super classes, consumed roles and implemented interfaces.
	 */
	public static final List<ParameterizedTypeRef> collect(ParameterizedTypeRef typeRef,
			DeclMergingHelper declMergingHelper) {
		return new AllSuperTypeRefsCollector(typeRef, declMergingHelper).getResult();
	}
}
