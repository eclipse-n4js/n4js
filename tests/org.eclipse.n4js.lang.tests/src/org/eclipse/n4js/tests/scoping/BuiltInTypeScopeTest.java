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

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.scoping.builtin.GlobalObjectScope;
import org.eclipse.n4js.scoping.builtin.VirtualBaseTypeScope;
import org.eclipse.n4js.ts.scoping.builtin.BuiltInTypeScope;
import org.eclipse.n4js.ts.scoping.builtin.N4Scheme;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.PrimitiveType;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.inject.Inject;

/**
 */
@InjectWith(N4JSInjectorProvider.class)
@RunWith(XtextRunner.class)
public class BuiltInTypeScopeTest {
	@Inject
	XtextResourceSet resourceSet;
	@Inject
	ParseHelper<Script> parseHelper;

	@SuppressWarnings("javadoc")
	@Test
	public void testLoadingBuiltInTypes() {
		BuiltInTypeScope scope = BuiltInTypeScope.get(resourceSet);
		IEObjectDescription anyType = scope.getSingleElement(QualifiedName.create("any"));
		Assert.assertNotNull(anyType);
		String s = "";
		for (Resource resource : resourceSet.getResources()) {
			if (resource.getErrors().size() > 0) {
				for (Diagnostic d : resource.getErrors()) {
					s += "\n  " + d.getMessage() + " at " + resource.getURI() + ":" + d.getLine();
				}
			}
		}

		Assert.assertEquals("Resources definine built-in types must have no error.", "", s);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testResolveSuperTypeOfBuiltInType() {
		BuiltInTypeScope scope = BuiltInTypeScope.get(resourceSet);
		IEObjectDescription intDescription = scope.getSingleElement(QualifiedName.create("i18nKey")); // trigger loading
		PrimitiveType intType = (PrimitiveType) intDescription.getEObjectOrProxy();
		PrimitiveType assCompatType = intType.getAssignmentCompatible();
		Assert.assertFalse(assCompatType.eIsProxy());
		Assert.assertEquals("string", assCompatType.getName());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testResolveAllBuiltInTypes() {
		BuiltInTypeScope scope = BuiltInTypeScope.get(resourceSet);
		IEObjectDescription description = scope.getSingleElement(QualifiedName.create("any")); // trigger loading
		// assert that the built in resources are loaded into a delegate resource set
		Assert.assertEquals(0, resourceSet.getResources().size());

		EObject objectOrProxy = description.getEObjectOrProxy();
		Assert.assertFalse(objectOrProxy.eIsProxy());

		ResourceSet builtInResourceSet = objectOrProxy.eResource().getResourceSet();
		Assert.assertNotSame(resourceSet, builtInResourceSet);

		// trigger more loading
		GlobalObjectScope.get(resourceSet).getAllElements();
		VirtualBaseTypeScope.get(resourceSet).getAllElements();

		Assert.assertEquals(FluentIterable.from(builtInResourceSet.getResources()).transform(Resource::getURI)
				.join(Joiner.on('\n')), 7, builtInResourceSet.getResources().size());

		EcoreUtil.resolveAll(builtInResourceSet);
		Map<EObject, Collection<Setting>> unresolvedProxies = EcoreUtil.UnresolvedProxyCrossReferencer
				.find(builtInResourceSet);
		Assert.assertTrue(unresolvedProxies.toString(), unresolvedProxies.isEmpty());

		Assert.assertTrue(N4Scheme.isFromResourceWithN4Scheme(objectOrProxy));

		EObject loadedViaPrimary = resourceSet.getEObject(description.getEObjectURI(), false);
		Assert.assertSame(objectOrProxy, loadedViaPrimary);

	}

	@SuppressWarnings("javadoc")
	@Test
	public void testParsing() throws Exception {
		String content = "var name: any = 'global'";
		Script result = parseHelper.parse(content);
		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.getScriptElements().size());
		ScriptElement elem = result.getScriptElements().get(0);
		Assert.assertTrue(elem instanceof VariableStatement);
		VariableStatement stmt = (VariableStatement) elem;
		VariableDeclaration varDecl = stmt.getVarDecl().get(0);
		ParameterizedTypeRef typeRef = (ParameterizedTypeRef) varDecl.getDeclaredTypeRefInAST();
		Assert.assertFalse("Proxy URI: " + ((InternalEObject) typeRef.getDeclaredType()).eProxyURI(), typeRef
				.getDeclaredType().eIsProxy());
	}
}
