/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.n4idl.tests.helper

import com.google.inject.Inject
import java.util.List
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Assert

/**
 * N4IDL test-helper to create valid {@link TypeRef} instances from a given 
 * type expression string.
 */
class N4IDLTypeRefTestHelper {
	@Inject extension private N4IDLParseHelper
	@Inject extension private ValidationTestHelper
	
	/**
	 * Creates an N4IDL-module containing the given type expression, extracts the corresponding {@link TypeRef}
	 * element from the AST and returns it.
	 * 
	 * @param typeExpression A type expression as it may occur in a variable declaration
	 * @param preamble A valid N4IDL preamble in which clients may declared additional testing types.
	 */
	def TypeRef makeTypeRef(String typeExpression, String preamble) {
		val expressionName = "typeExpressionDeclaration";
		
		val module = '''
		«preamble»
		@VersionAware
		function f() { var «expressionName» : «typeExpression» }''';
		
		val script = parseN4IDL(module);
		script.assertNoErrors
		
		val variableDeclaration = script.eAllContents.filter(VariableDeclaration).findFirst[decl | decl.name.equals(expressionName)];
		
		if (variableDeclaration === null) {
			Assert.fail('''Failed to create TypeRef "«typeExpression»": The type expression did not yield a well-formed N4IDL module:\n«module»''');
		}
		return variableDeclaration.declaredTypeRef
	}
	
	/**
	 * Creates an N4IDL-module containing the given type expression, extracts the corresponding {@link TypeRef}
	 * element from the AST and returns it.
	 * 
	 * @param typeExpression A type expression as it may occur in a variable declaration
	 * @param existingClasses A list of class names (with version, e.g. "A#1") which are assumed to exist in the namespace of the module.
	 */
	def TypeRef makeTypeRef(String typeExpression, List<String> existingClasses) {
		return makeTypeRef(typeExpression, classes(existingClasses));
	}
	
	/**
	 * Returns N4IDL code which declares the given list of classes (incl. version).
	 * 
	 * Example: <code> classes(#["A#1"]) -> "class A#1 {}" </code>
	 */
	public def String classes(String... classNames) {
		return '''«FOR e : classNames»
			class «e» {}
			«ENDFOR»''';
	}
}