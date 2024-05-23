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
package org.eclipse.n4js.smoke.tests;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.xtext.testing.smoketest.ScenarioProcessor;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

abstract public class UniqueScenarioProcessor extends ScenarioProcessor {

	private MessageDigest messageDigest;
	private Set<BigInteger> seen;

	/**
	 * Used to ensure thread-safe initialization of Xtext infrastructure.
	 * <p>
	 * Before this tweak, the smoke tests sometimes ran into an {@link OutOfMemoryError} during the parallel execution
	 * of {@code parseHelper.parse()} in the first N test cases, seemingly caused by a race during Guice injector
	 * creation and/or Guice object tree creation and possibly other parts of Xtext infrastructure initialization.
	 */
	private final Supplier<Void> initializer = Suppliers.memoize(() -> {
		// invoked only once; process some dummy source code to trigger initialization of all components
		try {
			doProcessFile("let x = 42;");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	});

	public UniqueScenarioProcessor() throws RuntimeException {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			seen = Collections.newSetFromMap(new ConcurrentHashMap<>());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void processFile(String data) throws Exception {
		initializer.get(); // ensure initialization
		byte[] hash = ((MessageDigest) messageDigest.clone()).digest(data.getBytes("UTF-8"));
		if (seen.add(new BigInteger(hash))) {
			doProcessFile(data);
		}
	}

	abstract public void doProcessFile(String string) throws Exception;

}
