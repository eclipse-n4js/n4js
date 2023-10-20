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
package org.eclipse.n4js.tests.parser;

import org.junit.Test;

public class YieldExpressionParserTest extends AbstractParserTest {

	@Test
	public void testIDEBUG_722() {
		parseESSuccessfully("""
					var fibonacci = function* (numbers: number) {
					    var pre: number = 0, cur = 1;
					    while (numbers-- > 0) {
					        [ pre, cur ] = [ cur, pre + cur ];
					        yield cur;
					    }
					};
				""");
	}

}
