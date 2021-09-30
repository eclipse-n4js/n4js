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
package org.eclipse.n4js.n4idl.versioning

import com.google.inject.Inject
import org.eclipse.n4js.N4JSInjectorProviderWithIssueSuppression
import org.eclipse.n4js.n4idl.tests.helper.N4IDLTypeRefTestHelper
import org.eclipse.n4js.ts.versions.VersionableUtils
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Unit tests wrt to class {@link VersionUtils}.
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProviderWithIssueSuppression)
public class VersionUtilsTest extends Assert {
	@Inject private extension N4IDLTypeRefTestHelper
	
	@Test
	public def void testVersionedTypeArgument() {
		Assert.assertEquals("Versioned array type argument", 1, VersionableUtils.getVersion(makeTypeRef("Array<A#1>", #["A#1", "A#2"])));
		Assert.assertEquals("Versioned array type argument", 2, VersionableUtils.getVersion(makeTypeRef("Array<A#2>", #["A#1", "A#2"])));
		
		Assert.assertEquals("Differently versioned type argument", 1, VersionableUtils.getVersion(makeTypeRef("Iterable2<A#1, A#2>", #["A#1", "A#2"])));
		
		Assert.assertEquals("Primitive array type argument", 0, VersionableUtils.getVersion(makeTypeRef("Array<int>", #[])));
		
		Assert.assertEquals("Nested array type argument", 2, VersionableUtils.getVersion(makeTypeRef("Array<Array<A#2>>", #["A#1", "A#2"])));
	}
	
	@Test
	public def void testVersionedComposedTypeRef() {
		val preamble = '''
		class A#1 {}
		class A#2 {}
		interface I#1 {}
		''';
		
		Assert.assertEquals("Versioned union type", 1, VersionableUtils.getVersion(makeTypeRef("union{A#1, string}", preamble)));
		Assert.assertEquals("Versioned union type", 2, VersionableUtils.getVersion(makeTypeRef("union{A#2, string}", preamble)));
		
		Assert.assertEquals("Versioned intersection type", 1, VersionableUtils.getVersion(makeTypeRef("intersection{A#1, I#2}", preamble)));
		Assert.assertEquals("Versioned intersection type", 2, VersionableUtils.getVersion(makeTypeRef("intersection{A#2, Iterable<?>}", preamble)));
	}
	
	@Test
	public def void testTypeTypeRef() {
		Assert.assertEquals("type{A#1} TypeRef", 1, VersionableUtils.getVersion(makeTypeRef("type{A#1}", #["A#1", "A#2"])));
		Assert.assertEquals("type{A#2} TypeRef", 2, VersionableUtils.getVersion(makeTypeRef("type{A#2}", #["A#1", "A#2"])));
		
		Assert.assertEquals("constructor{A#1} TypeRef", 1, VersionableUtils.getVersion(makeTypeRef("constructor{A#1}", #["A#1", "A#2"])));
		Assert.assertEquals("constructor{A#2} TypeRef", 2, VersionableUtils.getVersion(makeTypeRef("constructor{A#2}", #["A#1", "A#2"])));
	}
}
