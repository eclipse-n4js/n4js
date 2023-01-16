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

import org.eclipse.n4js.smith.Measurement;
import org.eclipse.n4js.smith.N4JSDataCollectors;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.utils.DeclMergingHelper;

import com.google.common.collect.Lists;

/**
 * Collects all declared super types, implicit super types or polyfills are ignored. The bottom type or its
 * merged/polyfilled types <b>are</b> included.
 */
public class AllSuperTypesCollector extends AbstractCompleteHierarchyTraverser<List<TClassifier>> {

	private final List<TClassifier> result;

	/**
	 * Creates a new collector.
	 *
	 * @param type
	 *            the type to start with.
	 */
	public AllSuperTypesCollector(ContainerType<?> type, DeclMergingHelper declMergingHelper) {
		super(type, declMergingHelper);
		result = Lists.newArrayList();
	}

	@Override
	protected Measurement getMeasurement() {
		return N4JSDataCollectors.dcTHT_AllSuperTypesCollector.getMeasurementIfInactive("HierarchyTraverser");
	}

	@Override
	protected List<TClassifier> doGetResult() {
		return result;
	}

	@Override
	protected void doProcess(ContainerType<?> containerType) {
		if (containerType instanceof TClassifier) {
			result.add((TClassifier) containerType);
		}
	}

	@Override
	protected void doProcess(PrimitiveType currentType) {
		// nothing to do in this case
	}

	/**
	 * Convenience method to create a new instance of {@link AllSuperTypesCollector} and immediately return its result.
	 *
	 * @param containerType
	 *            the type to start with.
	 * @return transitive closure of all super classes and implemented interfaces.
	 */
	public static final List<TClassifier> collect(ContainerType<?> containerType, DeclMergingHelper declMergingHelper) {
		return new AllSuperTypesCollector(containerType, declMergingHelper).getResult();
	}
}
