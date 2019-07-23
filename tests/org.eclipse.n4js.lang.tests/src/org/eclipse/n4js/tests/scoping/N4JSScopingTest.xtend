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
package org.eclipse.n4js.tests.scoping

import com.google.common.base.Splitter
import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.n4js.N4JSInjectorProvider
import org.eclipse.n4js.n4JS.ExpressionStatement
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.IdentifierRef
import org.eclipse.n4js.n4JS.N4ClassDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MemberDeclaration
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.ParameterizedCallExpression
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.resource.UserdataMapper
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.n4js.utils.Log
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.IScopeProvider
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.n4js.utils.URIUtils

/**
 * @see N4JSScopeProvider
 */
@InjectWith(N4JSInjectorProvider) // for UI: N4JSUiIn...., is then a plugin test
@RunWith(XtextRunner)
@Log
class N4JSScopingTest {

	@Inject Provider<XtextResourceSet> resourceSetProvider

	@Inject
	extension ParseHelper<Script>

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject IScopeProvider scopeProvider

	@Test
	def void testMiniLocalVariableScoping() {
		val testModel = '''
			var a=1;
			var b=a;
		'''.parse(URIUtils.toFileUri("Test.n4js"), resourceSetProvider.get)
		assertTrue(testModel.eResource.errors.empty)

		val a1Assignment = testModel.eAllContents.filter(VariableDeclaration).filter[
			name == "a" && eContainer?.eContainer == testModel].head;
		assertNotNull("a at class level found", a1Assignment)

		val b1Assignment = testModel.eAllContents.filter(VariableDeclaration).filter[name == "b"].head;
		assertNotNull("b at function level found", b1Assignment)

		assertTrue("b1Assignment.expression: " + b1Assignment.expression,
			b1Assignment.expression instanceof IdentifierRef)
		assertTrue(b1Assignment.expression instanceof IdentifierRef)
		val identB1 = b1Assignment.expression as IdentifierRef
		val b1ScopeIdentRef = scopeProvider.getScope(identB1, N4JSPackage.Literals.IDENTIFIER_REF__ID)
		assertElements(b1ScopeIdentRef, "Expected scope at b1 variable assignment for identifier scope", "a, b")
	}

	@Test
	def void testLocalScopingWithBinding() {
		val script = '''
			export class A {
				public methodOfA(): any
			}
			var a: A;
			a.methodOfA()
		'''.parse // only parsed, not linked

		// syntax ok?
		assertTrue(script.eResource.errors.empty)
		EcoreUtil.resolveAll(script)

		val classDeclaration = script.eAllContents.filter(N4ClassDeclaration).head;
		assertNotNull(classDeclaration);

		val type = classDeclaration.definedType
		assertTrue("class declaration should have a defined type", type instanceof TClass);
		val tClass = type as TClass

		assertEquals(classDeclaration, tClass.astElement)

		val variableDeclaration = script.eAllContents.filter(VariableDeclaration).head;
		assertTrue(variableDeclaration.declaredTypeRef instanceof ParameterizedTypeRef)
		val ptr = variableDeclaration.declaredTypeRef as ParameterizedTypeRef
		assertEquals(tClass, ptr.declaredType)
	}

	@Test
	def void testLocalScoping() {
		val script = '''
			class A {
				foo(): any {}
			}
			var a: A;
			a.foo();
		'''.parse // only parsed, not linked

		// syntax ok?
		assertTrue(script.eResource.errors.empty)
		EcoreUtil.resolveAll(script)

		val ParameterizedCallExpression = ((script.scriptElements.last as ExpressionStatement).expression as ParameterizedCallExpression);
		assertNotNull(ParameterizedCallExpression);
		assertEquals(0, ParameterizedCallExpression.arguments.size)
		assertNotNull(ParameterizedCallExpression.target)
		assertTrue(ParameterizedCallExpression.target instanceof ParameterizedPropertyAccessExpression)

		val a_foo__ = ParameterizedCallExpression.target as ParameterizedPropertyAccessExpression
		val a_ = a_foo__.target as IdentifierRef;
		assertNotNull(a_);
		assertNotNull(a_.id);

		assertFalse(a_.id.eIsProxy)

		val _foo = a_foo__.property;
		assertNotNull(_foo);
		assertFalse("Unexpected proxy", _foo.eIsProxy)

		val methodDeclaration = script.eAllContents.filter(N4MethodDeclaration).head;
		assertEquals("N4MethodDeclaration should be assigned to type", methodDeclaration, _foo.astElement)
	}

	protected def getAstElement(IdentifiableElement it) {
		switch it {
			SyntaxRelatedTElement: astElement
			default: throw new IllegalArgumentException(it.toString)
		}
	}

	@Test
	def void testLocalScopingWithBindingAndLinking() {
		val program = '''
			class A {}
			var a: A;
		'''.parse // only parsed, not linked

		// syntax ok?
		assertTrue(program.eResource.errors.empty)
		EcoreUtil.resolveAll(program)

		val classDeclaration = program.eAllContents.filter(N4ClassDeclaration).head;
		assertNotNull(classDeclaration);

		val definedType = classDeclaration.definedType
		assertTrue("TClass as type expected", definedType instanceof TClass);
		val tClass = definedType as TClass
		assertEquals("A", tClass.name);

		assertEquals(classDeclaration, tClass.astElement)

		val varDecl = program.eAllContents.filter(VariableDeclaration).last;
		var ptr = varDecl.declaredTypeRef as ParameterizedTypeRef;
		assertEquals(definedType, ptr.declaredType);
	}

	@Test
	def void testExport() {

		val rs = resourceSetProvider.get
		val program = '''
			export class A {
				foo(): any {
				}
			}
		'''.parse(URI.createURI("A.js"), rs)

		// syntax ok?
		assertTrue(program.eResource.errors.empty)
		// linking ok?
		EcoreUtil.resolveAll(program.eResource)
		assertTrue(program.eResource.errors.empty)

		val classDeclaration = program.eAllContents.filter(N4ClassDeclaration).head;
		assertNotNull(classDeclaration);

		val definedType = classDeclaration.definedType
		assertTrue("TClass as type expected", definedType instanceof TClass);
		val tClass = definedType as TClass
		assertEquals(classDeclaration, tClass.astElement)

		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(program.eResource());
		assertFalse(resourceDescriptions.allResourceDescriptions.empty);
		assertFalse("Expected a type to be exported",
			resourceDescriptions.allResourceDescriptions.head.exportedObjects.empty);

		val eods = resourceDescriptions.allResourceDescriptions.head.exportedObjects
		val scriptEod = eods.head;
		var serializedScript = scriptEod.getUserData(UserdataMapper.USERDATA_KEY_SERIALIZED_SCRIPT)
		assertNotNull("No serialized script in user data", serializedScript);

		val typeEod = eods.tail.head;
		assertEquals("A.A", typeEod.qualifiedName.toString);
		serializedScript = typeEod.getUserData(UserdataMapper.USERDATA_KEY_SERIALIZED_SCRIPT)
		assertNull("No serialized script in user data", serializedScript);
	}

	@Test
	def void testImportExportType() {

		val rs = resourceSetProvider.get

		val supplier = '''
			export class Supplier {
				foo(): Supplier {}
			}
		'''.parse(URI.createURI("org.eclipse.n4js/tests/scoping/Supplier.n4js"), rs)

		// syntax ok?
		assertTrue(supplier.eResource.errors.toString, supplier.eResource.errors.empty)
		val idFoo = supplier.eAllContents.filter(N4MemberDeclaration).head.name;
		assertEquals("foo", idFoo)

		val typeClient = '''
			import { Supplier } from "org.eclipse.n4js/tests/scoping/Supplier";
			var a: Supplier;
		'''.parse(URI.createURI("TypeClient.js"), rs)

		// syntax ok?
		assertTrue(typeClient.eResource.errors.empty)

		val typeRefA = typeClient.scriptElements.filter(VariableStatement).last.varDecl.head.declaredTypeRef as ParameterizedTypeRef;
		assertNotNull(typeRefA.declaredType);
		assertFalse(typeRefA.declaredType.eIsProxy)

		// TODO adapt after Xcore works
		assertFalse((typeRefA.declaredType as TClass).ownedMembers.empty);
	}


	@Test
	def void testImportExportAliasedType() {

		val rs = resourceSetProvider.get

		val supplier = '''
			export class Supplier {
				foo(): Supplier {}
			}
		'''.parse(URI.createURI("org.eclipse.n4js/tests/scoping/Supplier.n4js"), rs)

		// syntax ok?
		assertTrue(supplier.eResource.errors.toString, supplier.eResource.errors.empty)
		val idFoo = supplier.eAllContents.filter(N4MemberDeclaration).head.name;
		assertEquals("foo", idFoo)

		val typeClient = '''
			import { Supplier as MySupplier } from "org.eclipse.n4js/tests/scoping/Supplier";
			var a: MySupplier;
		'''.parse(URI.createURI("TypeClient.js"), rs)

		// syntax ok?
		assertTrue(typeClient.eResource.errors.empty)

		val typeRefA = typeClient.scriptElements.filter(VariableStatement).last.varDecl.head.declaredTypeRef as ParameterizedTypeRef;
		assertNotNull(typeRefA.declaredType);
		assertFalse(typeRefA.declaredType.eIsProxy)

		// TODO adapt after Xcore works
		assertFalse((typeRefA.declaredType as TClass).ownedMembers.empty);
	}

	@Test
	def void testImportExportN4MemberDeclaration1() {

		val rs = resourceSetProvider.get

		val supplier = '''
			export class Supplier {
				foo(): Supplier;
			}
		'''.parse(URI.createURI("Supplier.n4js"), rs)

		// syntax ok?
		assertTrue(supplier.eResource.errors.toString, supplier.eResource.errors.empty)
		val idFoo = supplier.eAllContents.filter(N4MemberDeclaration).head.name;
		assertEquals("foo", idFoo)

		val client = '''
			import Supplier from "org.eclipse.n4js/tests/scoping/Supplier";
			var a: Supplier;
			a;
		'''.parse(URI.createURI("Client.n4js"), rs)

		// syntax ok?
		assertTrue(supplier.eResource.errors.toString, supplier.eResource.errors.empty)
		EcoreUtil.resolveAll(rs)

		val idRefToA = ((client.scriptElements.last as ExpressionStatement).expression as IdentifierRef);
		assertNotNull(idRefToA)
		assertEquals("a", idRefToA.id.name);
	}

	@Test
	def void testImportExportN4MemberDeclaration() {

		val rs = resourceSetProvider.get

		val supplier = '''
			export class Supplier {
				foo(): Supplier  {}
			}
		'''.parse(URI.createURI("org.eclipse.n4js/tests/scoping/Supplier.n4js"), rs)

		// syntax ok?
		assertEquals(0, supplier.eResource.errors.size)
		val fooDef = supplier.eAllContents.filter(N4MemberDeclaration).head;

		val client = '''
			import { Supplier } from "org.eclipse.n4js/tests/scoping/Supplier";
			var a: Supplier;
			a.foo();
		'''.parse(URI.createURI("Client.n4js"), rs)

		// syntax ok?
		assertTrue(supplier.eResource.errors.toString, supplier.eResource.errors.empty)
		EcoreUtil.resolveAll(rs)

		val lastStatement = client.scriptElements.last as ExpressionStatement
		val ParameterizedCallExpression = lastStatement.expression as ParameterizedCallExpression
		val propertyAccess = ParameterizedCallExpression.target as ParameterizedPropertyAccessExpression
		assertNotNull(propertyAccess);
		assertFalse("Unexpected proxy", propertyAccess.property.eIsProxy);
		assertFalse("astElement is proxy", propertyAccess.property.astElement.eIsProxy)
		assertEquals(fooDef, propertyAccess.property.astElement);
	}

	@Test // TODO: why are real files needed here?
	def void testImportExportN4MemberDeclarationDeserialize() {

		var rs = resourceSetProvider.get

		val supplierResource = rs.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js"))

		supplierResource.load(emptyMap)
		EcoreUtil.resolveAll(supplierResource)
		assertTrue(supplierResource.errors.empty)
		assertEquals("supplier content count", 2, supplierResource.contents.size)
		assertTrue("supplier content count at position one is Script",
			supplierResource.contents.get(0) instanceof Script)
		assertTrue("supplier content count at position two is TModule",
			supplierResource.contents.get(1) instanceof TModule)

		val resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(supplierResource);
		assertFalse("Test that the index has been filled", resourceDescriptions.allResourceDescriptions.empty);

		val eoDescs = resourceDescriptions.allResourceDescriptions.map[exportedObjects].flatten

		assertEquals("one EResourceDescription", 2/* exported with one name  + BuiltInTypesScopeFilter.EXPECTED_PREDEFINED_TYPES*/,
			eoDescs.size)

		assertEquals("Stored user data",
			'''
				<?xml version="1.0" encoding="ASCII"?>
				<types:TModule xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:typeRefs="http://www.eclipse.org/n4js/ts/TypeRefs" xmlns:types="http://www.eclipse.org/n4js/ts/Types" qualifiedName="org/eclipse/n4js/tests/scoping/Supplier" projectName="org.eclipse.n4js.lang.tests" vendorID="org.eclipse.n4js">
				  <astElement href="#/0"/>
				  <topLevelTypes xsi:type="types:TClass" name="Supplier" exportedName="Supplier">
				    <ownedMembers xsi:type="types:TMethod" name="foo" hasNoBody="true" declaredMemberAccessModifier="public">
				      <astElement href="#/0/@scriptElements.0/@exportedElement/@ownedMembersRaw.0"/>
				      <returnTypeRef xsi:type="typeRefs:ParameterizedTypeRef" declaredType="//@topLevelTypes.0"/>
				    </ownedMembers>
				    <astElement href="#/0/@scriptElements.0/@exportedElement"/>
				  </topLevelTypes>
				</types:TModule>
			'''.toString,
			UserdataMapper.getDeserializedModuleFromDescriptionAsString(eoDescs.head, supplierResource.URI));

		assertEquals("Separately stored md5 hash matches expectations",
			"7db65ac965ae43f2b3673735d7296d9b", 
			eoDescs.head.getUserData(UserdataMapper.USERDATA_KEY_AST_MD5));

		val module = UserdataMapper.getDeserializedModuleFromDescription(eoDescs.head, supplierResource.URI);
		assertEquals("During deserialization of a TModule the astMD5 hash is recovered from the separate user data slot",
			"7db65ac965ae43f2b3673735d7296d9b",
			module.astMD5)

		rs.resources.forEach[it.unload];

		val clientResource = rs.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Client.n4js"))
		clientResource.load(emptyMap)
		assertTrue(clientResource.errors.empty)
		assertEquals("client content count", 2, clientResource.contents.size) // Script + TModule
		val clientObject = clientResource.contents.get(0)
		assertTrue(clientObject instanceof Script)
		val client = clientObject as Script

		// syntax ok?
		assertTrue(client.eResource.errors.empty)
		assertTrue(clientResource.errors.empty)

		val ParameterizedCallExpression = ((client.scriptElements.last as ExpressionStatement).expression as ParameterizedCallExpression);
		assertNotNull(ParameterizedCallExpression);
		assertEquals(0, ParameterizedCallExpression.arguments.size)
		assertNotNull(ParameterizedCallExpression.target)
		assertTrue(ParameterizedCallExpression.target instanceof ParameterizedPropertyAccessExpression)
		val propertyAccess = ParameterizedCallExpression.target as ParameterizedPropertyAccessExpression;
		assertNotNull(propertyAccess);
		assertFalse("Should not been loaded (1)", supplierResource.loaded)
		assertFalse("Unexpected proxy", propertyAccess.property.eIsProxy) // trigger scoping
		assertTrue("Resource was loaded transparently due to index access in scope provider", supplierResource.loaded)
		assertEquals("foo", propertyAccess.property.name);
	}

	@Test
	def void testLocalVariableScoping() {
		val testModel = '''
			var a=1;

			function foo() {
				(function(s){})('myArgument')
				var a=3;
				var b1 = a; // binds to local a (= 3)
				foo; // binds to function as identifier ref
				foo(); // binds to function as identifier ref contained in a call expression
			}
			var b2 = c; // binds to c declaration, even c is declared after b2 and c is contained in block doesn't lower visibility
			{
				var c = a; // = 1 // binds to global a (= 1)
			}
			var d = d; // binds to itself
			foo(); // binds to function as identifier ref contained in a call expression
			var e = foo() // binds to function as identifier ref contained in a call expression
			var f = foo // binds to function as identifier ref
		'''.parse(URIUtils.toFileUri("Test.n4js"), resourceSetProvider.get)

		assertTrue(testModel.eResource.errors.empty)

		val a1Assignment = testModel.eAllContents.filter(VariableDeclaration).filter[
			name == "a" && eContainer?.eContainer == testModel].head;
		assertNotNull("a at class level found", a1Assignment)

		val functionDeclaration = testModel.eAllContents.filter(FunctionDeclaration).filter[name == "foo"].head;
		assertNotNull("functionDeclaration at class level found", functionDeclaration)

		val a2Assignment = testModel.eAllContents.filter(VariableDeclaration).filter[
			name == "a" && eContainer?.eContainer?.eContainer == functionDeclaration].head;
		assertNotNull("a at function level found", a2Assignment)

		val b1Assignment = testModel.eAllContents.filter(VariableDeclaration).filter[name == "b1"].head;
		assertNotNull("b1 at function level found", b1Assignment)

		assertTrue("b1Assignment.expression: " + b1Assignment.expression,
			b1Assignment.expression instanceof IdentifierRef)
		assertTrue(b1Assignment.expression instanceof IdentifierRef)
		val identB1 = b1Assignment.expression as IdentifierRef
		val b1ScopeIdentRef = scopeProvider.getScope(identB1, N4JSPackage.Literals.IDENTIFIER_REF__ID)
		assertElements(b1ScopeIdentRef, "Expected scope at b1 variable assignment for identifier scope",
			"a, arguments, b1, b2, c, d, e, f, foo")

		assertEquals("Expected local a at b1", a2Assignment,
			b1ScopeIdentRef.allElements.findFirst[name.toString == "a"].EObjectOrProxy)

		val cAssignment = testModel.eAllContents.filter(VariableDeclaration).filter[name == "c"].head;
		val b2Assignment = testModel.eAllContents.filter(VariableDeclaration).filter[name == "b2"].head;
		assertNotNull("b2 at class level found", b2Assignment)
		assertTrue("b2Assignment.expression: " + b2Assignment.expression,
			b2Assignment.expression instanceof IdentifierRef)
		assertTrue(b2Assignment.expression instanceof IdentifierRef)
		val identB2 = b2Assignment.expression as IdentifierRef
		val b2ScopeIdentRef = scopeProvider.getScope(identB2, N4JSPackage.Literals.IDENTIFIER_REF__ID)
		b2ScopeIdentRef.assertElements("Expected scope at b2 variable assignment for identifier scope",
			"a, b2, c, d, e, f, foo")
		assertEquals("Expected global a at b2", a1Assignment,
			b2ScopeIdentRef.allElements.findFirst[name.toString == "a"].EObjectOrProxy)
		assertEquals("Expected c at b2 assigned", cAssignment, (b2Assignment.expression as IdentifierRef).id)

		val dAssignment = testModel.eAllContents.filter(VariableDeclaration).filter[name == "d"].head;
		assertNotNull("d at class level found", dAssignment)
		assertTrue(dAssignment.expression instanceof IdentifierRef)
		val identD = dAssignment.expression as IdentifierRef
		val dScopeIdentRef = scopeProvider.getScope(identD, N4JSPackage.Literals.IDENTIFIER_REF__ID)
		assertEquals("Expected d at d variable assignment for identifier scope", dAssignment,
			dScopeIdentRef.allElements.findFirst[name.toString == "d"].EObjectOrProxy)
		assertEquals("Expected d at d assigned", dAssignment, ((dAssignment.expression as IdentifierRef).id))

		val eAssignment = testModel.eAllContents.filter(VariableDeclaration).filter[name == "e"].head;
		assertNotNull("e at class level found", eAssignment)
		assertTrue(eAssignment.expression instanceof ParameterizedCallExpression)
		assertEquals("Expected foo at e assigned", functionDeclaration.definedType,
			((eAssignment.expression as ParameterizedCallExpression).target as IdentifierRef).id)

		val fAssignment = testModel.eAllContents.filter(VariableDeclaration).filter[name == "f"].head;
		assertNotNull("f at class level found", fAssignment)
		assertTrue(fAssignment.expression instanceof IdentifierRef)
		assertEquals("Expected foo at f assigned", functionDeclaration.definedType,
			((fAssignment.expression as IdentifierRef).id))

		val fooStatement = testModel.eAllContents.filter(ExpressionStatement).filter [
			eContainer?.eContainer == functionDeclaration && expression instanceof IdentifierRef &&
				(expression as IdentifierRef).id == functionDeclaration.definedType
		].head;
		assertNotNull("fooStatement at function level found", fooStatement)

		val fooCallStatement = testModel.eAllContents.filter(ExpressionStatement).filter [
			eContainer?.eContainer == functionDeclaration && expression instanceof ParameterizedCallExpression &&
				(expression as ParameterizedCallExpression).target instanceof IdentifierRef &&
				((expression as ParameterizedCallExpression).target as IdentifierRef).id ==
					functionDeclaration.definedType
		].head;
		assertNotNull("fooCallStatement at function level found", fooCallStatement)
	}

	def private void assertElements(IScope scope, String description, String expectationAsString) {
		val Set<QualifiedName> allElements = scope.allElements.filter [
			name.segmentCount == 1 // ignore fully qualified names produced by global scope provider used for export
			&& (
			N4JSPackage.Literals.VARIABLE.isSuperTypeOf(EClass) || TypesPackage.Literals.TMEMBER.isSuperTypeOf(EClass) ||
				TypesPackage.Literals.TFUNCTION.isSuperTypeOf(EClass))
		].map[name].toSet
		val extension splitter = Splitter.on(',').trimResults

		val defaults = ("__proto__, Infinity, NaN, constructor, decodeURI, decodeURIComponent, encodeURI, encodeURIComponent, eval, hasOwnProperty, " +
			"isFinite, isNaN, isPrototypeOf, parseFloat, parseInt, propertyIsEnumerable, toLocaleString, toString, undefined, valueOf").
				split().toList
		val expectation = expectationAsString.split().toList
		if (!allElements.containsAll(defaults)) {
			assertEquals((expectation + defaults).sort.toString, allElements.sort.toString)
		}
		if (!allElements.containsAll(expectation)) {
			assertEquals((expectation + defaults).sort.toString, allElements.sort.toString)
		}
		if (allElements.size != expectation.length + defaults.length) {
			assertEquals((expectation + defaults).sort.toString, allElements.sort.toString)
		}
	}

	@Test
	def void testLocalVariableScoping__NegativeTest() {
		var rs = resourceSetProvider.get
		val negativeJS = rs.URIConverter.normalize(
			URI.createURI("src/org/eclipse/n4js/tests/scoping/negative.n4js"))
		var negativeResource = rs.getResource(negativeJS, true)
		negativeResource.load(emptyMap)
		EcoreUtil.resolveAll(negativeResource) // to trigger linking (and by this scoping)
		assertEquals("expected error count", 1, negativeResource.errors.size)
		val error = negativeResource.errors.head
		assertEquals("expected error message", "Couldn't resolve reference to IdentifiableElement 'a'.", error.message)
	}
}
