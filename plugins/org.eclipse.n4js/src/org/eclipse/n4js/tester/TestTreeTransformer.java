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
package org.eclipse.n4js.tester;

import java.util.Map;

import org.eclipse.n4js.tester.domain.TestTree;

import com.google.common.base.Function;
import com.google.inject.ImplementedBy;

/**
 * Representation of a transformer converting a {@link TestTree test tree} instance into any arbitrary model instance.
 */
@ImplementedBy(DefaultTestTreeTransformer.class)
public interface TestTreeTransformer extends Function<TestTree, Object> {

	/**
	 * Transforms the {@link TestTree test tree} argument into any arbitrary object after applying on the transformation
	 * logic.
	 *
	 * @param tree
	 *            the test tree to transform.
	 * @return the transformed object.
	 */
	@Override
	Object apply(final TestTree tree);

	/**
	 * Transforms the {@link TestTree test tree} and the optional properties arguments into any arbitrary object after
	 * applying on the transformation logic.
	 *
	 * @param tree
	 *            the test tree to transform.
	 * @param properties
	 *            optional map of properties to transform. Can be {@code null}.
	 * @return the transformed object.
	 */
	Object apply(final TestTree tree, final Map<String, Object> properties);

}
