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
package org.eclipse.n4js.binaries;

import static com.google.common.collect.FluentIterable.from;

import org.eclipse.n4js.binaries.nodejs.NodeJsBinary;
import org.eclipse.n4js.binaries.nodejs.YarnBinary;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Provides registered binaries.
 */
@Singleton
public class BinariesProvider {

	/**
	 * A list of {@link Binary binary} classes registered explicitly instead of via extension points to support the HLC
	 * and the Xpect output tests.
	 */
	private static final Iterable<Class<? extends Binary>> REGISTERED_BINARIES = ImmutableList
			.<Class<? extends Binary>> builder()
			.add(NodeJsBinary.class)
			.add(YarnBinary.class)
			.build();

	@Inject
	private Injector injector;

	/**
	 * Returns with an iterable of binaries that are registered to the application.
	 *
	 * @return an iterable of binaries that are available in the application.
	 */
	public Iterable<Binary> getRegisteredBinaries() {
		return from(REGISTERED_BINARIES).transform(clazz -> (Binary) injector.getInstance(clazz)).toList();
	}

}
