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
package org.eclipse.n4js.tester.tests.testtree;

import com.google.inject.Binder;
import com.google.inject.Module;

import org.eclipse.n4js.tester.TestTreeTransformer;
import org.eclipse.n4js.tester.internal.DefaultTestTreeTransformer;

/**
 * Module to ensure that the {@link TestTreeTransformer} implementation is the {@link DefaultTestTreeTransformer}.
 */
public class DefaultTestTreeTransformerModule implements Module {

	@Override
	public void configure(final Binder binder) {
		binder.bind(TestTreeTransformer.class).to(DefaultTestTreeTransformer.class);
	}

}
