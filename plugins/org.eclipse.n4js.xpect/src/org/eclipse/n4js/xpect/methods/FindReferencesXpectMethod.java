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
package org.eclipse.n4js.xpect.methods;

import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.TargetURICollector;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.junit.runner.RunWith;
import org.xpect.XpectImport;
import org.xpect.parameter.ParameterParser;
import org.xpect.runner.Xpect;
import org.xpect.runner.XpectRunner;
import org.xpect.xtext.lib.setup.XtextStandaloneSetup;
import org.xpect.xtext.lib.setup.XtextWorkspaceSetup;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.ts.findReferences.SimpleResourceAccess;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.xpect.methods.scoping.IN4JSCommaSeparatedValuesExpectation;
import org.eclipse.n4js.xpect.methods.scoping.N4JSCommaSeparatedValuesExpectation;

/**
 * This class provides a XPECT method to specify tests regarding the {@link IReferenceFinder}
 */
@SuppressWarnings("restriction")
@RunWith(XpectRunner.class)
@XpectImport({ XtextStandaloneSetup.class, XtextWorkspaceSetup.class })
public class FindReferencesXpectMethod {

	@Inject
	private Provider<TargetURIs> targetURISetProvider;

	@Inject
	private IReferenceFinder referenceFinder;

	@Inject
	private TargetURICollector collector;

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	/**
	 * This XPECT methods compares all computed references at a given EObject to the expected references. The expected
	 * references include the line number.
	 */
	@Xpect
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	public void findReferences(
			@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			EObject arg1) {

		EObject eObj = arg1;
		if (arg1 instanceof ParameterizedTypeRef)
			eObj = arg1.eContainer();
		if (arg1 instanceof LiteralOrComputedPropertyName)
			eObj = arg1.eContainer();

		Resource eResource = eObj.eResource();
		TargetURIs targets = targetURISetProvider.get();
		collector.add(eObj, targets);
		IResourceDescriptions index = resourceDescriptionsProvider.getResourceDescriptions(eResource);

		ArrayList<String> result = Lists.newArrayList();
		IReferenceFinder.Acceptor acceptor = new IReferenceFinder.Acceptor() {
			@Override
			public void accept(EObject src, URI srcURI, EReference eRef, int idx, EObject tgtOrProxy, URI tgtURI) {
				if (src instanceof PropertyNameOwner)
					src = ((PropertyNameOwner) src).getDeclaredName();

				String resultText = "(unknown reference)";
				ICompositeNode srcNode = NodeModelUtils.getNode(src);
				if (srcNode != null) {
					int line = srcNode.getStartLine();

					String text = NodeModelUtils.getTokenText(srcNode);
					if (src instanceof GenericDeclaration)
						text = ((GenericDeclaration) src).getDefinedType().getName();

					resultText = text + " - " + line;
				}

				result.add(resultText);
			}

			@Override
			public void accept(IReferenceDescription description) {
				throw new UnsupportedOperationException("Should not be called");
			}
		};

		SimpleResourceAccess resourceAccess = new SimpleResourceAccess(eResource.getResourceSet());
		referenceFinder.findAllReferences(targets, resourceAccess, index, acceptor, null);

		expectation.assertEquals(result);
	}

	// ICompositeNode srcNode = NodeModelUtils.getNode(src);
	// ICompositeNode tgtNode = NodeModelUtils.getNode(tgtOrProxy);
	//
	// System.out.println(NodeModelUtils.compactDump(srcNode, true));
	// String string = src.eClass().getName() + "." + eRef.getName();
	// if (srcNode == null)
	// result.add(srcURI.fragment() + " " + string);
	// else {
	// int line = srcNode.getStartLine();
	// List<INode> feature = NodeModelUtils.findNodesForFeature(src, eRef);
	// String text = feature.isEmpty() ? "" : feature.get(0).getText();
	// result.add(text + " - " + srcURI.fragment() + " " + string + " at line " + line);
	// }

}
