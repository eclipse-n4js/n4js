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
package org.eclipse.n4js.npmexporter

import org.eclipse.n4js.N4JSInjectorProviderWithMockProject
import javax.inject.Inject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*

@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithMockProject)
public class NpmNamingconventionTest {

	@Inject
	private NpmExporter npmExporter;

	@Test
	def void namePatterns() {
		assertTrue( "simple name", npmExporter.holdsConsistentProjectId("abc", [] )  );
//		assertTrue( "prefixed name", npmExporter.holdsConsistentProjectId("@x/abc")  ); // allow ?

		assertFalse( "No uppercase",npmExporter.holdsConsistentProjectId("Abc",[] ) );
		assertFalse( "No uppercase", npmExporter.holdsConsistentProjectId("abC",[] ) );

		assertFalse( "leading underscore",npmExporter.holdsConsistentProjectId("_abc",[] ) );
		assertFalse( "leading dot", npmExporter.holdsConsistentProjectId(".abc",[] ) );

		assertFalse( "funny german letter",npmExporter.holdsConsistentProjectId("abßc",[] ) );
		assertFalse( "question mark upside-down",npmExporter.holdsConsistentProjectId("ab¿c",[] ) );

		assertFalse( "question mark", npmExporter.holdsConsistentProjectId("ab?c",[] ) );
//		assertFalse( "asterix",npmExporter.holdsConsistentProjectId("ab*c",[] ) ); // allow ?
		assertFalse( "slash",npmExporter.holdsConsistentProjectId("ab/c",[] ) );

		assertFalse( "tab",npmExporter.holdsConsistentProjectId("ab\tc",[] ) );
		assertFalse( "new line",npmExporter.holdsConsistentProjectId("ab\nc",[] ) );

		assertFalse( "hash mark",npmExporter.holdsConsistentProjectId("ab#c",[] ) );

		assertTrue( npmExporter.holdsConsistentProjectId("a23456789_",[] ) );

		assertTrue( "length of 124 char",npmExporter.holdsConsistentProjectId(
			"a23456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_"+ // 10x10
			"123456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_"+ // 10x10
			"123456789_1234"
		,[] ) );

		assertFalse( "length of 125 char",npmExporter.holdsConsistentProjectId(
			"a23456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_"+ // 10x10
			"123456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_123456789_"+ // 10x10
			"123456789_12345"
		,[] ) );

	}

}
