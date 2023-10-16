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
package org.eclipse.n4js.tests.findReferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.n4JS.N4ClassDeclaration;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.n4JS.ScriptElement;
import org.eclipse.n4js.n4JS.VariableDeclaration;
import org.eclipse.n4js.n4JS.VariableStatement;
import org.eclipse.n4js.tooling.findReferences.SimpleResourceAccess;
import org.eclipse.n4js.ts.typeRefs.TypeRef;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.TargetURICollector;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@SuppressWarnings("restriction")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class ReferenceFinderTest extends Assert {

	@Inject
	ParseHelper<Script> parseHelper;

	@Inject
	Provider<TargetURIs> targetURISetProvider;
	@Inject
	IReferenceFinder referenceFinder;
	@Inject
	TargetURICollector collector;
	@Inject
	ResourceDescriptionsProvider resourceDescriptionsProvider;

	// more tests in HeadlessReferenceFinderTest
	@Test
	public void testFindReferences_01() throws Exception {
		Script script = parseHelper.parse("""
				class C {}
				var c: C = null
				""");
		TargetURIs targets = targetURISetProvider.get();
		EList<ScriptElement> elems = script.getScriptElements();
		N4ClassDeclaration c = (N4ClassDeclaration) elems.get(0);
		collector.add(c, targets);
		IResourceDescriptions index = resourceDescriptionsProvider.getResourceDescriptions(script.eResource());
		List<EObject> result = new ArrayList<>();
		IReferenceFinder.Acceptor acceptor = new IReferenceFinder.Acceptor() {
			@Override
			public void accept(IReferenceDescription description) {
				throw new UnsupportedOperationException("Should not be called");
			}

			@Override
			public void accept(EObject source, URI sourceURI, EReference eReference, int _index, EObject targetOrProxy,
					URI targetURI) {
				result.add(source);
			}
		};
		referenceFinder.findAllReferences(targets, new SimpleResourceAccess(script.eResource().getResourceSet()), index,
				acceptor, null);
		assertEquals(1, result.size());
		VariableStatement varStmt = (VariableStatement) elems.get(elems.size() - 1);
		VariableDeclaration varDecl = varStmt.getVarDecl().get(0);
		TypeRef typeRef = varDecl.getDeclaredTypeRefInAST();
		assertTrue(result.contains(typeRef)); // type in the var decl
	}

}
