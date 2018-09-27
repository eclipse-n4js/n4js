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

import com.google.inject.Inject
import com.google.inject.Provider
import java.util.ArrayList
import java.util.Collection
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.n4js.N4JSInjectorProviderWithIndex
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableStatement
import org.eclipse.n4js.resource.N4JSResource
import org.eclipse.n4js.scoping.N4JSScopeProvider
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TypesPackage
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import org.eclipse.xtext.resource.containers.FlatResourceSetBasedAllContainersState
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.util.OnChangeEvictingCache
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import static org.junit.Assert.*
import org.eclipse.n4js.scoping.utils.CanLoadFromDescriptionHelper

/**
 * @see N4JSScopeProvider
 */
@InjectWith(N4JSInjectorProviderWithIndex)
@RunWith(XtextRunner)
class CircularReferencesScopingTest implements N4Scheme {

	@Inject ResourceDescriptionsProvider resourceDescriptionsProvider
	@Inject Provider<XtextResourceSet> resourceSetProvider
	@Inject CanLoadFromDescriptionHelper canLoadFromDescriptionHelper;

	XtextResourceSet rs
	IResourceDescriptions resourceDescriptions
	URI brotherURI
	URI sisterURI
	URI childURI

	@Before
	def void setUp() {
		rs = resourceSetProvider.get

		brotherURI = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Brother.n4js"))
		sisterURI = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Sister.n4js"))
		childURI = rs.URIConverter.normalize(URI.createURI("src/org/eclipse/n4js/tests/scoping/Child.n4js"))

		rs.getResource(brotherURI, true).contents
		rs.getResource(sisterURI, true).contents
		rs.getResource(childURI, true).contents

		EcoreUtil.resolveAll(rs)

		rs.eAdapters += new EnumerableAllContainersState(rs, #[brotherURI, sisterURI, childURI])

		resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(rs);
		resourceDescriptions.allResourceDescriptions.forEach[ exportedObjects.forEach[ userDataKeys ]]
	}

	@After
	def void tearDown() {
		rs = null
		resourceDescriptions = null
		brotherURI = null
		sisterURI = null
		childURI = null
	}

	@Test
	def void testCircularDependencies() {
		assertEquals(3, resourceDescriptions.getExportedObjectsByType(TypesPackage.Literals.TMODULE).size)

		// unload the resources in the resource set but don't clear the cached information in the index
		for(var i = 0; i < rs.resources.size; i++) {
			val res = rs.resources.get(i) as XtextResource
			(res.cache as OnChangeEvictingCache).execWithoutCacheClear(res) [
				unload
				return null
			]
		}
		// clear the resource set but again keep the cached index data
		val resources = new ArrayList(rs.resources)
		resources.forEach[ eSetDeliver(false) ]
		rs.resources.clear
		resources.forEach[ eSetDeliver(true) ]

		assertEquals(3, resourceDescriptions.getExportedObjectsByType(TypesPackage.Literals.TMODULE).size)
		assertEquals(1, resourceDescriptions.getExportedObjects(TypesPackage.Literals.TMODULE,
			QualifiedName.create("org", "eclipse", "n4js", "tests", "scoping", "Sister"), false).size)
		val brother = rs.getResource(brotherURI, true)
		assertEquals(1, rs.resources.size)
		EcoreUtil.resolveAll(brother)
		assertEquals(brother.errors.toString,  0, brother.errors.size)
		assertEquals(3, rs.resources.filter[!resourceWithN4Scheme].size)

		val sister = rs.getResource(sisterURI, false) as N4JSResource
		val child = rs.getResource(childURI, false) as N4JSResource
		assertNotNull(sister)
		assertNotNull(child)
		if (!canLoadFromDescriptionHelper.loadFromSourceDeactivated) {
			// due to cyclic dependency, sister must be loaded from disk (not index)
			assertTrue(sister.loaded)
			assertFalse(sister.script.eIsProxy)
		}
		assertFalse(child.loaded) // no cyclic dependency, so child can be loaded from index (not from disk)
		assertTrue(child.script.eIsProxy)
		assertEquals(2, sister.contents.size)
		assertEquals(2, child.contents.size)

		sister.contents.head // use an iterator here internally to check for concurrent modifications on the contents list
		assertEquals(2, sister.contents.size)
		val exportedScript = sister.contents.last as TModule
		val exportedType = exportedScript.topLevelTypes.head as TClass
		val exportedMethod = exportedType.ownedMembers.head as TMethod
		val brotherType = (exportedMethod.returnTypeRef as ParameterizedTypeRef).declaredType as TClass
		val originalBrotherType = (brother.contents.last as TModule).topLevelTypes.head
		assertSame(originalBrotherType, brotherType)

		rs.resources.filter[!resourceWithN4Scheme].forEach [ assertEquals(2, contents.size) ]
	}

	@Test
	def void testLinksProperyResolved() {
		EcoreUtil.resolveAll(rs)

		assertEquals(rs.resources.map[ URI ].join('\n'), 3, rs.resources.filter[ !resourceWithN4Scheme ].size)
		rs.resources.filter[ !resourceWithN4Scheme ].forEach [
			assertEquals(errors.toString, 0, errors.size)
			assertEquals(contents.toString, 2, contents.size)
		]

		val sister = rs.getResource(sisterURI, false)
		val script = sister.contents.head as Script
		val sisterGetBrotherGetChild = (script.scriptElements.last as VariableStatement).varDecl.head.expression as ParameterizedPropertyAccessExpression
		val member = sisterGetBrotherGetChild.property
		assertEquals("Brother", (member.eContainer as TClass).name)
	}

}

/**
 * A container state implementation that works with a specific resource set but does
 * not reflect the resource set's content 1:1 but uses a dedicated list of available
 * resources instead. this allows to clear the resource set in a test but still have
 * the original resources available in the index.
 */
package class EnumerableAllContainersState extends FlatResourceSetBasedAllContainersState {

	Collection<URI> uris

	new(ResourceSet resourceSet, Collection<URI> uris) {
		super(resourceSet)
		this.uris = uris
	}

	override isEmpty(String containerHandle) {
		return false
	}

	override getContainedURIs(String containerHandle) {
		return uris
	}

}
