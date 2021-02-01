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
import java.util.stream.Collectors
import java.util.stream.IntStream
import junit.framework.AssertionFailedError
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.ts.typeRefs.TypeRef
import org.eclipse.n4js.typesystem.N4JSTypeSystem
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.junit.Assert

/**
 * N4IDL test-helper to create valid {@link TypeRef} instances from a given 
 * type expression string (e.g. "A#2") or expression strings (e.g. "new A#2()", "1", "true").
 */
class N4IDLTypeRefTestHelper {
	@Inject extension private N4IDLParseHelper;
	@Inject extension private ValidationTestHelper;
	@Inject private N4JSTypeSystem typeSystem;
	
	/**
	 * Creates an N4IDL-module containing the given type expression, extracts the corresponding {@link TypeRef}
	 * element from the AST and returns it.
	 * 
	 * @param typeExpression A type expression as it may occur in a variable declaration
	 * @param preamble A valid N4IDL preamble in which clients may declared additional testing types.
	 */
	def TypeRef makeTypeRef(String typeExpression, String preamble) {
		return makeTypeRefs(#[typeExpression], preamble).get(0);
	}
	
	/**
	 * Creates {@link TypeRef}s for all given type expressions.
	 * 
	 * All returned {@link TypeRef}s are extracted from the same module, thus they
	 * share the same type model instances.
	 */
	def List<TypeRef> makeTypeRefs(List<String> typeExpressions, String preamble) {
		// create a list of distinct variable names, one for each type expression in typeExpressions
		val List<Pair<String, String>> expressionVariables = typeExpressions.distinctNames("typeExpression");
		
		val module = '''
		«preamble»
		@VersionAware
		function f() {
			«FOR e : expressionVariables» 
				let «e.key» : «e.value»
			«ENDFOR»
		}''';
		
		return module.parseAndFindVariableDeclarations(expressionVariables.map[it.key].toList)
			.map[decl | decl.declaredTypeRef].toList;
	}
	
	/** 
	 * Parses the given module string as N4IDL and returns all {@link VariableDeclaration} whose names are
	 * specified in {@code variableDeclarations} in that order.
	 * 
	 * Raises an {@link AssertionFailedError} if the module is invalid N4IDL or the specified {@code variableDeclarations}
	 * cannot be found in the resulting AST.
	 */
	private def List<VariableDeclaration> parseAndFindVariableDeclarations(String module, List<String> variableDeclarations) {
		val script = parseN4IDL(module);
		script.assertNoIssues;
		
		// map of all variable declarations by name
		val variableDeclarationsByName = script.eAllContents.filter(VariableDeclaration).toSet
			.toMap([decl | decl.name], [decl | decl]);
		
		// find all declarations corresponding to variableDeclarations
		val resultingVariableDeclaration = variableDeclarations
			.map[name | variableDeclarationsByName.get(name)]
			.filterNull.toList;
			
		// make sure all requested declarations could be extracted
		if (resultingVariableDeclaration.size != variableDeclarations.size) {
			Assert.fail("Failed to find all specified variable declarations " + variableDeclarations + " in N4IDL module:\n " + module);
		}
		
		return resultingVariableDeclaration;
	}
	
	/**
	 * Creates distinct names for each of the given elements and returns a list of 
	 * (name -> element) pairs.
	 * 
	 * Uses the given base name as a prefix for all names (e.g. base name "v" yield
	 * distinct names "v0", "v1", "v2", etc.).
	 * 
	 * @param elements The elements to create distinct names for.
	 * @param baseName The base name to use for the name generation.
	 */
	def <T> List<Pair<String, T>> distinctNames(List<T> elements, String baseName) {
		return IntStream.range(0, elements.size).mapToObj[ index |
			return baseName + index -> elements.get(index)
		].collect(Collectors.toList)
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
	 * Creates {@link TypeRef}s for all value expressions (e.g. "new A#1()") and type expression (e.g. "A#1", "A#2")
	 * and returns them in the given order.
	 * 
	 * All {@link TypeRef}s are extracted from the same module and thus share the same
	 * type model instances. 
	 */
	def List<TypeRef> makeTypeRefs(List<String> valueExpressions, List<String> typeExpressions, String preamble) {
		val valueVariables = valueExpressions.distinctNames("value");
		val typeExpressionVariables = typeExpressions.distinctNames("type");
		
		val module = '''
		«preamble»
		@VersionAware
		function f() {
			«FOR v : valueVariables» 
				let «v.key» = «v.value»;
			«ENDFOR»
			«FOR t : typeExpressionVariables» 
				let «t.key» : «t.value»;
			«ENDFOR»
		}''';
		val allVariableNames = valueVariables.map[v | v.key] + typeExpressionVariables.map[t | t.key];
		val declarations = module.parseAndFindVariableDeclarations(allVariableNames.toList);
		
		// either extract the declared type or type the value expression using the type system
		return declarations.map[decl | 
			if (decl.declaredTypeRef !== null) {
				return decl.declaredTypeRef;
			} else {
				return typeSystem.tau(decl);
			}
		]
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
