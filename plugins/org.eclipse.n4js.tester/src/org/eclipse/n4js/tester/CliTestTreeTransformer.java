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

import static java.util.Collections.emptyMap;

import java.util.Map;

import com.google.inject.Inject;

import org.eclipse.n4js.tester.domain.TestTree;
import org.eclipse.n4js.tester.internal.DefaultTestTreeTransformer;

/**
 * {@link TestTree Test tree} transformer for the CLI.
 * <p>
 * Unlike the {@link DefaultTestTreeTransformer default transformer} it transforms a {@link TestTree test tree} into a
 * raw list of test descriptor instances.
 */
public class CliTestTreeTransformer extends DefaultTestTreeTransformer {

	@Inject
	private DefaultTestTreeTransformer delegate;

	@Override
	public Object apply(final TestTree tree) {
		return apply(tree, emptyMap());
	}

	@Override
	public Object apply(final TestTree tree, final Map<String, Object> properties) {
		final MTestTree testTree = (MTestTree) delegate.apply(tree, properties);
		return testTree.getTestDescriptors();
	}

}
