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
package org.eclipse.n4js.tests.scoping;

import static org.eclipse.n4js.utils.Strings.join;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.findFirst;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.flatten;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.head;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.isEmpty;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.last;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.size;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.sort;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.tail;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toSet;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.head;
import static org.eclipse.xtext.xbase.lib.IteratorExtensions.last;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.ExpressionStatement;
import org.eclipse.n4js.n4JS.FunctionDeclaration;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.n4JS.N4MemberDeclaration;
import org.eclipse.n4js.n4JS.N4MethodDeclaration;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.resource.UserDataMapper;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypesPackage;
import org.eclipse.n4js.utils.Log;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Splitter;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @see N4JSScopeProvider
 */
@InjectWith(N4JSInjectorProvider.class) // for UI: N4JSUiIn...., is then a plugin test
@RunWith(XtextRunner.class)
@Log
public class N4JSScopingTest {

	@Inject
	Provider<XtextResourceSet> resourceSetProvider;

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Inject
	IScopeProvider scopeProvider;

	@Test
	public void testMiniLocalVariableScoping() throws Exception {
		Script testModel = parseHelper.parse("""
				var a=1;
				var b=a;
				""", URIUtils.toFileUri("Test.n4js"), resourceSetProvider.get());
		assertTrue(testModel.eResource().getErrors().isEmpty());

		VariableDeclaration a1Assignment = head(filter(filter(testModel.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "a") && vd.eContainer().eContainer() == testModel));
		assertNotNull("a at class level found", a1Assignment);

		VariableDeclaration b1Assignment = head(filter(filter(testModel.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "b")));
		assertNotNull("b at function level found", b1Assignment);

		assertTrue("b1Assignment.getExpression(): " + b1Assignment.getExpression(),
				b1Assignment.getExpression() instanceof IdentifierRef);
		assertTrue(b1Assignment.getExpression() instanceof IdentifierRef);
		IdentifierRef identB1 = (IdentifierRef) b1Assignment.getExpression();
		IScope b1ScopeIdentRef = scopeProvider.getScope(identB1, N4JSPackage.Literals.IDENTIFIER_REF__ID);
		assertElements(b1ScopeIdentRef, "Expected scope at b1 variable assignment for identifier scope", "a, b");
	}

	@Test
	public void testLocalScopingWithBinding() throws Exception {
		Script script = parseHelper.parse("""
				export class A {
					public methodOfA(): any
				}
				var a: A;
				a.methodOfA()
				"""); // only parsed, not linked

		// syntax ok?
		assertTrue(script.eResource().getErrors().isEmpty());
		EcoreUtil.resolveAll(script);

		N4ClassDeclaration classDeclaration = head(filter(script.eAllContents(), N4ClassDeclaration.class));
		assertNotNull(classDeclaration);

		Type type = classDeclaration.getDefinedType();
		assertTrue("class declaration should have a defined type", type instanceof TClass);
		TClass tClass = (TClass) type;

		assertEquals(classDeclaration, tClass.getAstElement());

		VariableDeclaration variableDeclaration = head(filter(script.eAllContents(), VariableDeclaration.class));
		assertTrue(variableDeclaration.getDeclaredTypeRefInAST() instanceof ParameterizedTypeRef);
		ParameterizedTypeRef ptr = (ParameterizedTypeRef) variableDeclaration.getDeclaredTypeRefInAST();
		assertEquals(tClass, ptr.getDeclaredType());
	}

	@Test
	public void testLocalScoping() throws Exception {
		Script script = parseHelper.parse("""
				class A {
					foo(): any {}
				}
				var a: A;
				a.foo();
				"""); // only parsed, not linked

		// syntax ok?
		assertTrue(script.eResource().getErrors().isEmpty());
		EcoreUtil.resolveAll(script);

		ParameterizedCallExpression ParameterizedCallExpression = (ParameterizedCallExpression) ((ExpressionStatement) last(
				script.getScriptElements())).getExpression();
		assertNotNull(ParameterizedCallExpression);
		assertEquals(0, ParameterizedCallExpression.getArguments().size());
		assertNotNull(ParameterizedCallExpression.getTarget());
		assertTrue(ParameterizedCallExpression.getTarget() instanceof ParameterizedPropertyAccessExpression);

		ParameterizedPropertyAccessExpression a_foo__ = (ParameterizedPropertyAccessExpression) ParameterizedCallExpression
				.getTarget();
		IdentifierRef a_ = (IdentifierRef) a_foo__.getTarget();
		assertNotNull(a_);
		assertNotNull(a_.getId());

		assertFalse(a_.getId().eIsProxy());

		IdentifiableElement _foo = a_foo__.getProperty();
		assertNotNull(_foo);
		assertFalse("Unexpected proxy", _foo.eIsProxy());

		N4MethodDeclaration methodDeclaration = head(filter(script.eAllContents(), N4MethodDeclaration.class));
		assertEquals("N4MethodDeclaration should be assigned to type", methodDeclaration, getAstElement(_foo));
	}

	protected EObject getAstElement(IdentifiableElement it) {
		if (it instanceof SyntaxRelatedTElement) {
			return ((SyntaxRelatedTElement) it).getAstElement();
		}
		throw new IllegalArgumentException(it.toString());
	}

	@Test
	public void testLocalScopingWithBindingAndLinking() throws Exception {
		Script program = parseHelper.parse("""
				class A {}
				var a: A;
				"""); // only parsed, not linked

		// syntax ok?
		assertTrue(program.eResource().getErrors().isEmpty());
		EcoreUtil.resolveAll(program);

		N4ClassDeclaration classDeclaration = head(filter(program.eAllContents(), N4ClassDeclaration.class));
		assertNotNull(classDeclaration);

		Type definedType = classDeclaration.getDefinedType();
		assertTrue("TClass as type expected", definedType instanceof TClass);
		TClass tClass = (TClass) definedType;
		assertEquals("A", tClass.getName());

		assertEquals(classDeclaration, tClass.getAstElement());

		VariableDeclaration varDecl = last(filter(program.eAllContents(), VariableDeclaration.class));
		ParameterizedTypeRef ptr = (ParameterizedTypeRef) varDecl.getDeclaredTypeRefInAST();
		assertEquals(definedType, ptr.getDeclaredType());
	}

	@Test
	public void testExport() throws Exception {

		XtextResourceSet rs = resourceSetProvider.get();
		Script program = parseHelper.parse("""
				export class A {
					foo(): any {
					}
				}
				""", URI.createURI("A.js"), rs);

		// syntax ok?
		assertTrue(program.eResource().getErrors().isEmpty());
		// linking ok?
		EcoreUtil.resolveAll(program.eResource());
		assertTrue(program.eResource().getErrors().isEmpty());

		N4ClassDeclaration classDeclaration = head(filter(program.eAllContents(), N4ClassDeclaration.class));
		assertNotNull(classDeclaration);

		Type definedType = classDeclaration.getDefinedType();
		assertTrue("TClass as type expected", definedType instanceof TClass);
		TClass tClass = (TClass) definedType;
		assertEquals(classDeclaration, tClass.getAstElement());

		IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider
				.getResourceDescriptions(program.eResource());
		assertFalse(isEmpty(resourceDescriptions.getAllResourceDescriptions()));
		assertFalse("Expected a type to be exported", isEmpty(head(
				resourceDescriptions.getAllResourceDescriptions()).getExportedObjects()));

		Iterable<IEObjectDescription> eods = head(resourceDescriptions.getAllResourceDescriptions())
				.getExportedObjects();
		IEObjectDescription scriptEod = head(eods);
		String serializedScript = scriptEod.getUserData(UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT);
		assertNotNull("No serialized script in user data", serializedScript);

		IEObjectDescription typeEod = head(tail(eods));
		assertEquals("A.!.A", typeEod.getQualifiedName().toString());
		serializedScript = typeEod.getUserData(UserDataMapper.USER_DATA_KEY_SERIALIZED_SCRIPT);
		assertNull("No serialized script in user data", serializedScript);
	}

	@Test
	public void testImportExportType() throws Exception {

		XtextResourceSet rs = resourceSetProvider.get();

		Script supplier = parseHelper.parse("""
				export class Supplier {
					foo(): Supplier {}
				}
				""", URI.createURI("org.eclipse.n4js/tests/scoping/Supplier.n4js"), rs);

		// syntax ok?
		assertTrue(supplier.eResource().getErrors().toString(), supplier.eResource().getErrors().isEmpty());
		String idFoo = head(filter(supplier.eAllContents(), N4MemberDeclaration.class)).getName();
		assertEquals("foo", idFoo);

		Script typeClient = parseHelper.parse("""
				import { Supplier } from "org.eclipse.n4js/tests/scoping/Supplier";
				var a: Supplier;
				""", URI.createURI("TypeClient.js"), rs);

		// syntax ok?
		assertTrue(typeClient.eResource().getErrors().isEmpty());

		ParameterizedTypeRef typeRefA = (ParameterizedTypeRef) head(last(filter(
				typeClient.getScriptElements(), VariableStatement.class)).getVarDecl()).getDeclaredTypeRefInAST();
		assertNotNull(typeRefA.getDeclaredType());
		assertFalse(typeRefA.getDeclaredType().eIsProxy());

		// TODO adapt after Xcore works
		assertFalse(((TClass) typeRefA.getDeclaredType()).getOwnedMembers().isEmpty());
	}

	@Test
	public void testImportExportAliasedType() throws Exception {

		XtextResourceSet rs = resourceSetProvider.get();

		Script supplier = parseHelper.parse("""
				export class Supplier {
					foo(): Supplier {}
				}
				""", URI.createURI("org.eclipse.n4js/tests/scoping/Supplier.n4js"), rs);

		// syntax ok?
		assertTrue(supplier.eResource().getErrors().toString(), supplier.eResource().getErrors().isEmpty());
		String idFoo = head(filter(supplier.eAllContents(), N4MemberDeclaration.class)).getName();
		assertEquals("foo", idFoo);

		Script typeClient = parseHelper.parse("""
				import { Supplier as MySupplier } from "org.eclipse.n4js/tests/scoping/Supplier";
				var a: MySupplier;
				""", URI.createURI("TypeClient.js"), rs);

		// syntax ok?
		assertTrue(typeClient.eResource().getErrors().isEmpty());

		ParameterizedTypeRef typeRefA = (ParameterizedTypeRef) head(
				last(filter(typeClient.getScriptElements(), VariableStatement.class)).getVarDecl())
						.getDeclaredTypeRefInAST();
		assertNotNull(typeRefA.getDeclaredType());
		assertFalse(typeRefA.getDeclaredType().eIsProxy());

		// TODO adapt after Xcore works
		assertFalse(((TClass) typeRefA.getDeclaredType()).getOwnedMembers().isEmpty());
	}

	@Test
	public void testImportExportN4MemberDeclaration1() throws Exception {

		XtextResourceSet rs = resourceSetProvider.get();

		Script supplier = parseHelper.parse("""
				export class Supplier {
					foo(): Supplier;
				}
				""", URI.createURI("Supplier.n4js"), rs);

		// syntax ok?
		assertTrue(supplier.eResource().getErrors().toString(), supplier.eResource().getErrors().isEmpty());
		String idFoo = head(filter(supplier.eAllContents(), N4MemberDeclaration.class)).getName();
		assertEquals("foo", idFoo);

		Script client = parseHelper.parse("""
				import Supplier from "org.eclipse.n4js/tests/scoping/Supplier";
				var a: Supplier;
				a;
				""", URI.createURI("Client.n4js"), rs);

		// syntax ok?
		assertTrue(supplier.eResource().getErrors().toString(), supplier.eResource().getErrors().isEmpty());
		EcoreUtil.resolveAll(rs);

		IdentifierRef idRefToA = (IdentifierRef) ((ExpressionStatement) last(client.getScriptElements()))
				.getExpression();
		assertNotNull(idRefToA);
		assertEquals("a", idRefToA.getId().getName());
	}

	@Test
	public void testImportExportN4MemberDeclaration() throws Exception {

		XtextResourceSet rs = resourceSetProvider.get();

		Script supplier = parseHelper.parse("""
				export class Supplier {
					foo(): Supplier  {}
				}
				""", URI.createURI("org.eclipse.n4js/tests/scoping/Supplier.n4js"), rs);

		// syntax ok?
		assertEquals(0, supplier.eResource().getErrors().size());
		N4MemberDeclaration fooDef = head(filter(supplier.eAllContents(), N4MemberDeclaration.class));

		Script client = parseHelper.parse("""
				import { Supplier } from "org.eclipse.n4js/tests/scoping/Supplier";
				var a: Supplier;
				a.foo();
				""", URI.createURI("Client.n4js"), rs);

		// syntax ok?
		assertTrue(supplier.eResource().getErrors().toString(), supplier.eResource().getErrors().isEmpty());
		EcoreUtil.resolveAll(rs);

		ExpressionStatement lastStatement = (ExpressionStatement) last(client.getScriptElements());
		ParameterizedCallExpression ParameterizedCallExpression = (ParameterizedCallExpression) lastStatement
				.getExpression();
		ParameterizedPropertyAccessExpression propertyAccess = (ParameterizedPropertyAccessExpression) ParameterizedCallExpression
				.getTarget();
		assertNotNull(propertyAccess);
		assertFalse("Unexpected proxy", propertyAccess.getProperty().eIsProxy());
		assertFalse("astElement is proxy", getAstElement(propertyAccess.getProperty()).eIsProxy());
		assertEquals(fooDef, getAstElement(propertyAccess.getProperty()));
	}

	@Test // TODO: why are real files needed here?
	public void testImportExportN4MemberDeclarationDeserialize() throws IOException {

		XtextResourceSet rs = resourceSetProvider.get();

		Resource supplierResource = rs.createResource(
				rs.getURIConverter().normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Supplier.n4js")));

		supplierResource.load(Collections.emptyMap());
		EcoreUtil.resolveAll(supplierResource);
		assertTrue(supplierResource.getErrors().isEmpty());
		assertEquals("supplier content count", 2, supplierResource.getContents().size());
		assertTrue("supplier content count at position one is Script",
				supplierResource.getContents().get(0) instanceof Script);
		assertTrue("supplier content count at position two is TModule",
				supplierResource.getContents().get(1) instanceof TModule);

		IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider
				.getResourceDescriptions(supplierResource);
		assertFalse("Test that the index has been filled", isEmpty(resourceDescriptions.getAllResourceDescriptions()));

		Iterable<IEObjectDescription> eoDescs = flatten(IterableExtensions
				.map(resourceDescriptions.getAllResourceDescriptions(), rd -> rd.getExportedObjects()));

		assertEquals("one EResourceDescription", 2 // exported with one name
													// BuiltInTypesScopeFilter.EXPECTED_PREDEFINED_TYPES
				, size(eoDescs));

		assertEquals("Stored user data",
				"""
						<?xml version="1.0" encoding="ASCII"?>
						<types:TModule xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:typeRefs="http://www.eclipse.org/n4js/ts/TypeRefs" xmlns:types="http://www.eclipse.org/n4js/ts/Types" simpleName="Supplier" qualifiedName="org/eclipse/n4js/tests/scoping/Supplier" packageName="org.eclipse.n4js.lang.tests" projectID="org.eclipse.n4js.lang.tests" vendorID="org.eclipse.n4js">
						  <exportDefinitions xsi:type="types:ElementExportDefinition" exportedName="Supplier" exportedElement="//@types.0"/>
						  <types xsi:type="types:TClass" name="Supplier" directlyExported="true" exportingExportDefinitions="//@exportDefinitions.0">
						    <ownedMembers xsi:type="types:TMethod" name="foo" hasNoBody="true" declaredMemberAccessModifier="public">
						      <astElement href="#/0/@scriptElements.0/@exportedElement/@ownedMembersRaw.0"/>
						      <returnTypeRef xsi:type="typeRefs:ParameterizedTypeRef" declaredType="//@types.0"/>
						    </ownedMembers>
						    <astElement href="#/0/@scriptElements.0/@exportedElement"/>
						  </types>
						  <astElement href="#/0"/>
						</types:TModule>
						""",
				UserDataMapper.getDeserializedModuleFromDescriptionAsString(head(eoDescs), supplierResource.getURI()));

		assertEquals("Separately stored md5 hash matches expectations",
				"5ef0928a4a8827880a4bdb03ff26f5fc",
				head(eoDescs).getUserData(UserDataMapper.USER_DATA_KEY_AST_MD5));

		TModule module = UserDataMapper.getDeserializedModuleFromDescription(head(eoDescs), supplierResource.getURI());
		assertEquals(
				"During deserialization of a TModule the astMD5 hash is recovered from the separate user data slot",
				"5ef0928a4a8827880a4bdb03ff26f5fc",
				module.getAstMD5());

		for (Resource res : rs.getResources()) {
			res.unload();
		}

		Resource clientResource = rs.createResource(URI.createURI("src/org/eclipse/n4js/tests/scoping/Client.n4js"));
		clientResource.load(Collections.emptyMap());
		assertTrue(clientResource.getErrors().isEmpty());
		assertEquals("client content count", 2, clientResource.getContents().size());// Script + TModule
		EObject clientObject = clientResource.getContents().get(0);
		assertTrue(clientObject instanceof Script);
		Script client = (Script) clientObject;

		// syntax ok?
		assertTrue(client.eResource().getErrors().isEmpty());
		assertTrue(clientResource.getErrors().isEmpty());

		ParameterizedCallExpression ParameterizedCallExpression = (ParameterizedCallExpression) (((ExpressionStatement) last(
				client.getScriptElements())).getExpression());
		assertNotNull(ParameterizedCallExpression);
		assertEquals(0, ParameterizedCallExpression.getArguments().size());
		assertNotNull(ParameterizedCallExpression.getTarget());
		assertTrue(ParameterizedCallExpression.getTarget() instanceof ParameterizedPropertyAccessExpression);
		ParameterizedPropertyAccessExpression propertyAccess = (ParameterizedPropertyAccessExpression) ParameterizedCallExpression
				.getTarget();
		assertNotNull(propertyAccess);
		assertFalse("Should not been loaded (1)", supplierResource.isLoaded());
		assertFalse("Unexpected proxy", propertyAccess.getProperty().eIsProxy());// trigger scoping
		assertTrue("Resource was loaded transparently due to index access in scope provider",
				supplierResource.isLoaded());
		assertEquals("foo", propertyAccess.getProperty().getName());
	}

	@Test
	public void testLocalVariableScoping() throws Exception {
		Script testModel = parseHelper.parse(
				"""
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
						""",
				URIUtils.toFileUri("Test.n4js"), resourceSetProvider.get());

		assertTrue(testModel.eResource().getErrors().isEmpty());

		VariableDeclaration a1Assignment = head(filter(filter(testModel.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "a") && vd.eContainer().eContainer() == testModel));
		assertNotNull("a at class level found", a1Assignment);

		FunctionDeclaration functionDeclaration = head(filter(filter(testModel.eAllContents(),
				FunctionDeclaration.class), fd -> Objects.equals(fd.getName(), "foo")));
		assertNotNull("functionDeclaration at class level found", functionDeclaration);

		VariableDeclaration a2Assignment = head(
				filter(filter(testModel.eAllContents(), VariableDeclaration.class), vd -> Objects.equals(vd.getName(),
						"a") && vd.eContainer().eContainer().eContainer() == functionDeclaration));
		assertNotNull("a at function level found", a2Assignment);

		VariableDeclaration b1Assignment = head(filter(filter(testModel.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "b1")));
		assertNotNull("b1 at function level found", b1Assignment);

		assertTrue("b1Assignment.getExpression(): " + b1Assignment.getExpression(),
				b1Assignment.getExpression() instanceof IdentifierRef);
		assertTrue(b1Assignment.getExpression() instanceof IdentifierRef);
		IdentifierRef identB1 = (IdentifierRef) b1Assignment.getExpression();
		IScope b1ScopeIdentRef = scopeProvider.getScope(identB1, N4JSPackage.Literals.IDENTIFIER_REF__ID);
		assertElements(b1ScopeIdentRef, "Expected scope at b1 variable assignment for identifier scope",
				"a, arguments, b1, b2, c, d, e, f, foo");

		assertEquals("Expected local a at b1", a2Assignment.getDefinedVariable(),
				findFirst(b1ScopeIdentRef.getAllElements(),
						elem -> Objects.equals(elem.getName().toString(), "a")).getEObjectOrProxy());

		VariableDeclaration cAssignment = head(filter(filter(testModel.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "c")));
		VariableDeclaration b2Assignment = head(filter(filter(testModel.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "b2")));
		assertNotNull("b2 at class level found", b2Assignment);
		assertTrue("b2Assignment.getExpression(): " + b2Assignment.getExpression(),
				b2Assignment.getExpression() instanceof IdentifierRef);
		assertTrue(b2Assignment.getExpression() instanceof IdentifierRef);
		IdentifierRef identB2 = (IdentifierRef) b2Assignment.getExpression();
		IScope b2ScopeIdentRef = scopeProvider.getScope(identB2, N4JSPackage.Literals.IDENTIFIER_REF__ID);
		assertElements(b2ScopeIdentRef, "Expected scope at b2 variable assignment for identifier scope",
				"a, b2, c, d, e, f, foo");
		assertEquals("Expected global a at b2", a1Assignment.getDefinedVariable(),
				findFirst(b2ScopeIdentRef.getAllElements(), elem -> Objects.equals(elem.getName().toString(), "a"))
						.getEObjectOrProxy());
		assertEquals("Expected c at b2 assigned", cAssignment.getDefinedVariable(),
				((IdentifierRef) b2Assignment.getExpression()).getId());

		VariableDeclaration dAssignment = head(filter(filter(testModel.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "d")));
		assertNotNull("d at class level found", dAssignment);
		assertTrue(dAssignment.getExpression() instanceof IdentifierRef);
		IdentifierRef identD = (IdentifierRef) dAssignment.getExpression();
		IScope dScopeIdentRef = scopeProvider.getScope(identD, N4JSPackage.Literals.IDENTIFIER_REF__ID);
		assertEquals("Expected d at d variable assignment for identifier scope", dAssignment.getDefinedVariable(),
				findFirst(dScopeIdentRef.getAllElements(), elem -> Objects.equals(elem.getName().toString(), "d"))
						.getEObjectOrProxy());
		assertEquals("Expected d at d assigned", dAssignment.getDefinedVariable(),
				(((IdentifierRef) dAssignment.getExpression()).getId()));

		VariableDeclaration eAssignment = head(filter(filter(testModel.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "e")));
		assertNotNull("e at class level found", eAssignment);
		assertTrue(eAssignment.getExpression() instanceof ParameterizedCallExpression);
		assertEquals("Expected foo at e assigned", functionDeclaration.getDefinedType(),
				((IdentifierRef) ((ParameterizedCallExpression) eAssignment.getExpression()).getTarget()).getId());

		VariableDeclaration fAssignment = head(filter(filter(testModel.eAllContents(), VariableDeclaration.class),
				vd -> Objects.equals(vd.getName(), "f")));
		assertNotNull("f at class level found", fAssignment);
		assertTrue(fAssignment.getExpression() instanceof IdentifierRef);
		assertEquals("Expected foo at f assigned", functionDeclaration.getDefinedType(),
				(((IdentifierRef) fAssignment.getExpression()).getId()));

		ExpressionStatement fooStatement = head(filter(filter(testModel.eAllContents(),
				ExpressionStatement.class),
				es -> es.eContainer().eContainer() == functionDeclaration && es.getExpression() instanceof IdentifierRef
						&& ((IdentifierRef) es.getExpression()).getId() == functionDeclaration.getDefinedType()));
		assertNotNull("fooStatement at function level found", fooStatement);

		ExpressionStatement fooCallStatement = head(filter(filter(testModel.eAllContents(),
				ExpressionStatement.class),
				es -> es.eContainer().eContainer() == functionDeclaration
						&& es.getExpression() instanceof ParameterizedCallExpression
						&& ((ParameterizedCallExpression) es.getExpression()).getTarget() instanceof IdentifierRef
						&& ((IdentifierRef) ((ParameterizedCallExpression) es.getExpression()).getTarget())
								.getId() == functionDeclaration.getDefinedType()));
		assertNotNull("fooCallStatement at function level found", fooCallStatement);
	}

	private void assertElements(IScope scope, String description, String expectationAsString) {
		Set<String> allElements = toSet(map(filter(scope.getAllElements(), elem ->
		// ignore fully qualified names produced by global scope provider used for export
		elem.getName().getSegmentCount() == 1
				&& (N4JSPackage.Literals.ABSTRACT_VARIABLE.isSuperTypeOf(elem.getEClass())
						|| TypesPackage.Literals.TVARIABLE.isSuperTypeOf(elem.getEClass())
						|| TypesPackage.Literals.TMEMBER.isSuperTypeOf(elem.getEClass())
						|| TypesPackage.Literals.TFUNCTION.isSuperTypeOf(elem.getEClass()))),
				elem -> elem.getName().toString()));
		List<String> allElementsSorted = sort(allElements);

		Splitter splitter = Splitter.on(",").trimResults();

		List<String> defaults = splitter.splitToList(
				"__proto__, global, Infinity, NaN, constructor, decodeURI, decodeURIComponent, encodeURI, encodeURIComponent, eval, hasOwnProperty, "
						+ "import, isFinite, isNaN, isPrototypeOf, parseFloat, parseInt, propertyIsEnumerable, toLocaleString, toString, undefined, valueOf");
		List<String> expectation = splitter.splitToList(expectationAsString);

		List<String> expectationAndDefaults = new ArrayList<>();
		expectationAndDefaults.addAll(expectation);
		expectationAndDefaults.addAll(defaults);
		expectationAndDefaults = sort(expectationAndDefaults);

		if (!allElements.containsAll(defaults)) {
			assertEquals(description, join(",", expectationAndDefaults), join(",", allElementsSorted));
		}
		if (!allElements.containsAll(expectation)) {
			assertEquals(description, join(",", expectationAndDefaults), join(",", allElementsSorted));
		}
		if (allElements.size() != expectation.size() + defaults.size()) {
			assertEquals(description, join(",", expectationAndDefaults), join(",", allElementsSorted));
		}
	}

	@Test
	public void testLocalVariableScoping__NegativeTest() throws Exception {
		XtextResourceSet rs = resourceSetProvider.get();
		URI negativeJS = rs.getURIConverter().normalize(
				URI.createURI("src/org/eclipse/n4js/tests/scoping/negative.n4js"));
		Resource negativeResource = rs.getResource(negativeJS, true);
		negativeResource.load(Collections.emptyMap());
		EcoreUtil.resolveAll(negativeResource); // to trigger linking (and by this scoping)
		assertEquals("expected error count", 1, negativeResource.getErrors().size());
		Diagnostic error = negativeResource.getErrors().get(0);
		assertEquals("expected error message", "Couldn't resolve reference to IdentifiableElement 'a'.",
				error.getMessage());
	}
}
