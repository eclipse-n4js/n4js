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
package org.eclipse.n4js.tests.parser

import org.junit.Test

/**
 * Parser tests for N4 specific types. Test methods with suffix "example" are taken from the N4JS spec.
 */
class N4_4_12_4_FieldAccessorsTest extends AbstractParserTest {

	@Test
	def void testMethodDeclarations() {
		val script = parseHelper.parse('''
			public class A {

				f1(): void {}

				abstract f1(): void
				abstract f2(): void;

				@Internal
				public abstract f3(): void

				protected f4(): any { return null; }

				public f5(): any { return null; }

				@Internal
				public <T> f6(): T { return null; }

				@Internal
				public f7(p1: any, p2: any): any { return p1; }
				private <T> f8(p1: T, p2: T): void { }

				@Internal
				public f9(p1: any, p2: any?): void {}
				@Internal
				public f10(p1: any, p2: any): void {}
				@Internal
				public f11(p2: any?, p2: any?): void {}

				static s1(): void {}
				static s2(): any { return null; }
				@Internal
				public static <T> s3(): T { return null; }
			}
		''');

		assertTrue(script.eResource.errors.toString, script.eResource.errors.empty)
	}


}
