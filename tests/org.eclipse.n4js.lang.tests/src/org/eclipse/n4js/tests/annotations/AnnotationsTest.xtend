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
package org.eclipse.n4js.tests.annotations

import com.google.inject.Inject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.Script
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 */
@RunWith(XtextRunner)
@InjectWith(N4JSInjectorProvider)
class AnnotationsTest {

	@Inject ParseHelper<Script> ph;

	def compile(String code) {
		ph.fileExtension = "n4js";
		var script = ph.parse(code)
		return script;
	}

	def findClassDeclaration(String code, String name) {
		return ( compile(code).eAllContents.filter(N4ClassDeclaration).filter[cd|cd.name == name].head);
	}

	def findMemberDeclaration(String code, String name) {
		return ( compile(code).eAllContents.filter(N4MemberDeclaration).filter[cd|cd.name == name].head);
	}

	@Test
	def void testBindAnnotationOnExportDeclaration() {
		val code = '''

export public interface ITires {}
export public class Fuel {}

export public class BiasPly implements ITires {}
export public class Gasoline extends Fuel {}

@Binder
@Bind(ITires, BiasPly)
@Bind(Fuel, Gasoline)
export public class OldificationBinder {}
	'''

		val classDecl = findClassDeclaration(code, "OldificationBinder");

		val bindings = AnnotationDefinition.BIND.getAllAnnotations(classDecl).toList.toArray
		Assert.assertEquals("incorrect AllAnnotations", 2, bindings.length)

		val ownedBindings = AnnotationDefinition.BIND.getAllOwnedAnnotations(classDecl).toList.toArray
		Assert.assertEquals("incorrect AllOwnedAnnotations", 2, ownedBindings.length)
	}

	@Test
	def void testBindAnnotationOnExportedElement() {
		val code = '''

export public interface ITires {}
export public class Fuel {}

export public class BiasPly implements ITires {}
export public class Gasoline extends Fuel {}

@Binder
export @Bind(ITires, BiasPly) @Bind(Fuel, Gasoline) public class OldificationBinder {}
	'''

		val classDecl = findClassDeclaration(code, "OldificationBinder");

		val bindings = AnnotationDefinition.BIND.getAllAnnotations(classDecl).toList.toArray
		Assert.assertEquals("incorrect AllAnnotations", 2, bindings.length)

		val ownedBindings = AnnotationDefinition.BIND.getAllOwnedAnnotations(classDecl).toList.toArray
		Assert.assertEquals("incorrect AllOwnedAnnotations", 2, ownedBindings.length)
	}

	@Test
	def void testTransitiveRepeatableAllPlaces() {
		val code = '''

@IDEBUG(1, "1")
@IDEBUG(2, "2")
export @IDEBUG(3, "3") @IDEBUG(4, "4") public class C {
    @IDEBUG(5, "5")
    @IDEBUG(6, "6")
    m(){}
}
	'''

		val memberDecl = findMemberDeclaration(code, "m");

		val bindings = AnnotationDefinition.IDEBUG.getAllAnnotations(memberDecl).toList.toArray
		Assert.assertEquals("incorrect AllAnnotations", 6, bindings.length)

		val ownedBindings = AnnotationDefinition.IDEBUG.getAllOwnedAnnotations(memberDecl).toList.toArray
		Assert.assertEquals("incorrect AllOwnedAnnotations", 2, ownedBindings.length)
	}

	@Test
	def void testTransitiveRepeatableExportDeclaration() {
		val code = '''

@Internal
export public class C {}
	'''

		val classDecl = findClassDeclaration(code, "C");

		val bindings = AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl).toList.toArray
		Assert.assertEquals("incorrect AllAnnotations", 1, bindings.length)

		val ownedBindings = AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl).toList.toArray
		Assert.assertEquals("incorrect AllOwnedAnnotations", 1, ownedBindings.length)
	}

	@Test
	def void testTransitiveRepeatableExportedElement() {
		val code = '''

export @Internal public class C {}
	'''

		val classDecl = findClassDeclaration(code, "C");

		val bindings = AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl).toList.toArray
		Assert.assertEquals("incorrect AllAnnotations", 1, bindings.length)

		val ownedBindings = AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl).toList.toArray
		Assert.assertEquals("incorrect AllOwnedAnnotations", 1, ownedBindings.length)
	}

	@Test
	def void testNonTransitiveNonRepeatableAllPLaces() {
		val code = '''

@Internal
export @Internal public class C {}
	'''

		val classDecl = findClassDeclaration(code, "C");

		val bindings = AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl).toList.toArray
		Assert.assertEquals("incorrect AllAnnotations", 1, bindings.length)

		val ownedBindings = AnnotationDefinition.INTERNAL.getAllAnnotations(classDecl).toList.toArray
		Assert.assertEquals("incorrect AllOwnedAnnotations", 1, ownedBindings.length)
	}

	@Test
	def void testNonTransitiveNonRepeatableNotOnMember() {
		val code = '''

@Internal
export @Internal public class C {
	m(){}
}
	'''

		val memberDecl = findMemberDeclaration(code, "m");

		val bindings = AnnotationDefinition.IDEBUG.getAllAnnotations(memberDecl).toList.toArray
		Assert.assertEquals("incorrect AllAnnotations", 0, bindings.length)

		val ownedBindings = AnnotationDefinition.IDEBUG.getAllOwnedAnnotations(memberDecl).toList.toArray
		Assert.assertEquals("incorrect AllOwnedAnnotations", 0, ownedBindings.length)
	}

	@Test
	def void testNonTransitiveNonRepeatableAllPlaces() {
		val code = '''

@Internal
export @Internal public class C {
	@Internal
	m(){}
}
	'''

		val memberDecl = findMemberDeclaration(code, "m");

		val bindings = AnnotationDefinition.INTERNAL.getAllAnnotations(memberDecl).toList.toArray
		Assert.assertEquals("incorrect AllAnnotations", 1, bindings.length)

		val ownedBindings = AnnotationDefinition.INTERNAL.getAllOwnedAnnotations(memberDecl).toList.toArray
		Assert.assertEquals("incorrect AllOwnedAnnotations", 1, ownedBindings.length)
	}

}
