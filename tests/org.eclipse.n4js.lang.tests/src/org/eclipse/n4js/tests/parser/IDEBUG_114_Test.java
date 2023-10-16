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

public class IDEBUG_114_Test extends AbstractParserTest {

	@Test
	public void testBug_121_01() {
		parseESSuccessfully("""
				var x = @This(any) function <T> () {
				}
				var y = function <T> () {
				}
				(@This(any) function <T> () {
				})
				(function <T> () {
				})
				""");
	}

	@Test
	public void testBug_121_02() {
		parseESSuccessfully("""
				var x = @This(any) function <T> f() {
				}
				var y = function <T> f() {
				}
				@This(any) function <T> f() {
				}
				function <T> f() {
				}
				""");
	}

	@Test
	public void testBug_114_01() {
		parseESSuccessfully("""
				@This(any)
				function <S extends string, T, P> len(s: S, t: T, p: P, a, n: number?, ...vas: string) : number {
					return (s+t+p+a+n+vas).length
				}
				(@This(any)
				function <S extends string, T, P> len(s: S, t: T, p: P, a, n: number?, ...vas: string) : number {
					return (s+t+p+a+n+vas).length
				})
				""");
	}

}
