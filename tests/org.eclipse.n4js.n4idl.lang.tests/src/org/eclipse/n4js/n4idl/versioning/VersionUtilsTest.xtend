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
import java.util.List
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4idl.tests.helper.N4IDLParseHelper
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * N4JSX version of N4JS' AbstractParserTest
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
public class VersionUtilsTest extends Assert {
	@Inject extension private N4IDLParseHelper
	@Inject extension private ValidationTestHelper
	
	@Test
	public def void testVersionedTypeArgument() {
		Assert.assertEquals("Versioned array type argument", 1, VersionUtils.getVersion(makeTypeRef("Array<A#1>", #["A#1", "A#2"])));
		Assert.assertEquals("Versioned array type argument", 2, VersionUtils.getVersion(makeTypeRef("Array<A#2>", #["A#1", "A#2"])));
		
		Assert.assertEquals("Differently versioned type argument", 1, VersionUtils.getVersion(makeTypeRef("Iterable2<A#1, A#2>", #["A#1", "A#2"])));
		
		Assert.assertEquals("Primitive array type argument", 0, VersionUtils.getVersion(makeTypeRef("Array<int>", #[])));
		
		Assert.assertEquals("Nested array type argument", 2, VersionUtils.getVersion(makeTypeRef("Array<Array<A#2>>", #["A#1", "A#2"])));
	}
	
	@Test
	public def void testVersionedComposedTypeRef() {
		Assert.assertEquals("Versioned union type", 1, VersionUtils.getVersion(makeTypeRef("union{A#1, string}", #["A#1", "A#2"])));
		Assert.assertEquals("Versioned union type", 2, VersionUtils.getVersion(makeTypeRef("union{A#2, string}", #["A#1", "A#2"])));
		
		Assert.assertEquals("Versioned intersection type", 1, VersionUtils.getVersion(makeTypeRef("intersection{A#1, A#2}", #["A#1", "A#2"])));
		Assert.assertEquals("Versioned intersection type", 2, VersionUtils.getVersion(makeTypeRef("intersection{A#2, any}", #["A#1", "A#2"])));
	}
	
	@Test
	public def void testTypeTypeRef() {
		Assert.assertEquals("type{A#1} TypeRef", 1, VersionUtils.getVersion(makeTypeRef("type{A#1}", #["A#1", "A#2"])));
		Assert.assertEquals("type{A#2} TypeRef", 2, VersionUtils.getVersion(makeTypeRef("type{A#2}", #["A#1", "A#2"])));
		
		Assert.assertEquals("constructor{A#1} TypeRef", 1, VersionUtils.getVersion(makeTypeRef("constructor{A#1}", #["A#1", "A#2"])));
		Assert.assertEquals("constructor{A#2} TypeRef", 2, VersionUtils.getVersion(makeTypeRef("constructor{A#2}", #["A#1", "A#2"])));
	}
	
	
	/**
	 * Creates an N4IDL-module containing the given type expression, extracts the corresponding {@link TypeRef}
	 * element from the AST and returns it.
	 * 
	 * @param typeExpression A type expression as it may occur in a variable declaration
	 * @param existingTypes A list of class names (with version, e.g. "A#1") which are assumed to exist in the namespace of the module.
	 */
	private def TypeRef makeTypeRef(String typeExpression, List<String> existingTypes) {
		val module = '''
		«FOR t : existingTypes»
			class «t» {}
		«ENDFOR»
		@VersionAware
		function f() { var a : «typeExpression» }''';
		
		val script = module.parseN4IDL
		script.assertNoErrors
		
		val variableDeclaration = script.eAllContents.filter(VariableDeclaration).head
		
		if (variableDeclaration === null) {
			fail('''Failed to create TypeRef "«typeExpression»": The type expression did not yield a well-formed N4IDL module.''');
		}
		return variableDeclaration.declaredTypeRef
	} 
}
