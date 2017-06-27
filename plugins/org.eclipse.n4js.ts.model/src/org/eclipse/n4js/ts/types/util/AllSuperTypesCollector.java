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
package org.eclipse.n4js.ts.types.util;

import java.util.List;

import com.google.common.collect.Lists;

import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TClassifier;

/**
 * Collects all declared super types, implicit super types or polyfills are ignored.
 */
public class AllSuperTypesCollector extends AbstractCompleteHierarchyTraverser<List<TClassifier>> {

	private final List<TClassifier> result;

	/**
	 * Creates a new collector.
	 *
	 * @param type
	 *            the type to start with.
	 */
	public AllSuperTypesCollector(ContainerType<?> type) {
		super(type);
		result = Lists.newArrayList();
	}

	@Override
	protected List<TClassifier> doGetResult() {
		return result;
	}

	@Override
	protected void doProcess(ContainerType<?> containerType) {
		if (containerType instanceof TClassifier)
			result.add((TClassifier) containerType);
	}

	/**
	 * Convenience method to create a new instance of {@link AllSuperTypesCollector} and immediately return its result.
	 *
	 * @param containerType
	 *            the type to start with.
	 * @return transitive closure of all super classes and implemented interfaces.
	 */
	public static final List<TClassifier> collect(ContainerType<?> containerType) {
		return new AllSuperTypesCollector(containerType).getResult();
	}
}
