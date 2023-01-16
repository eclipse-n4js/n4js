/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.typesystem.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.util.AbstractTypeHierachyTraverser;
import org.eclipse.n4js.ts.types.util.TypeUtils;
import org.eclipse.n4js.utils.DeclMergingHelper;
import org.eclipse.n4js.utils.DeclMergingUtils;

/**
 *
 */
public abstract class AbstractMergingHierachyTraverser<ResultT> extends AbstractTypeHierachyTraverser<ResultT> {
	private final N4JSResource contextResource;
	private final DeclMergingHelper declMergingHelper;

	/** Constructor */
	public AbstractMergingHierachyTraverser(ContainerType<?> type, N4JSResource contextResource,
			DeclMergingHelper declMergingHelper) {

		super(type);
		this.contextResource = contextResource;
		this.declMergingHelper = declMergingHelper;
	}

	/** Constructor */
	public AbstractMergingHierachyTraverser(ParameterizedTypeRef typeRef, N4JSResource contextResource,
			DeclMergingHelper declMergingHelper) {

		super(typeRef);
		this.contextResource = contextResource;
		this.declMergingHelper = declMergingHelper;
	}

	@Override
	protected List<ParameterizedTypeRef> getMergedTypeRefs(Type filledType) {
		if (!(filledType instanceof TClassifier)) {
			return Collections.emptyList();
		}

		List<ParameterizedTypeRef> mergedTypes = new ArrayList<>();
		if (DeclMergingUtils.mayBeMerged(filledType)) {
			ParameterizedTypeRef currentTypeRef = getCurrentTypeRef();
			if (currentTypeRef == null) {
				currentTypeRef = TypeUtils.createTypeRef(filledType);
			}
			mergedTypes.addAll(declMergingHelper.getMergedTypeRefs(contextResource, currentTypeRef));
		}

		return mergedTypes;
	}
}
