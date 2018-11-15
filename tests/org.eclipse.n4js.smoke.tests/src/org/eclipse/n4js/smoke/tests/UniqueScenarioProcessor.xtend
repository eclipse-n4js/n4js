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
package org.eclipse.n4js.smoke.tests

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Collections
import java.util.Set
import java.util.concurrent.ConcurrentHashMap
import org.eclipse.xtext.testing.smoketest.ScenarioProcessor

/**
 */
abstract class UniqueScenarioProcessor extends ScenarioProcessor {

	private MessageDigest messageDigest;
	private Set<BigInteger> seen;

	new() throws RuntimeException {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			seen = Collections.newSetFromMap(new ConcurrentHashMap<BigInteger, Boolean>());
		} catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e)
		}
	}

	override processFile(String data) throws Exception {
		val hash = (messageDigest.clone() as MessageDigest).digest(data.getBytes("UTF-8"));
		if (seen.add(new BigInteger(hash))) {
			doProcessFile(data)
		}
	}

	def void doProcessFile(String string) throws Exception

}
