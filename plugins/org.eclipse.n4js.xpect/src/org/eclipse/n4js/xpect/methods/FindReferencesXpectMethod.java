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
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.findReferences.SimpleResourceAccess;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.IEObjectCoveringRegion;
import org.eclipse.n4js.xpect.methods.scoping.IN4JSCommaSeparatedValuesExpectation;
import org.eclipse.n4js.xpect.methods.scoping.N4JSCommaSeparatedValuesExpectation;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.findReferences.TargetURICollector;
import org.eclipse.xtext.findReferences.TargetURIs;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.junit.runner.RunWith;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.xtext.lib.setup.XtextStandaloneSetup;
import org.eclipse.xpect.xtext.lib.setup.XtextWorkspaceSetup;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * This class provides a Xpect method to specify tests regarding the {@link IReferenceFinder}
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

	@Inject
	private EObjectAtOffsetHelper offsetHelper;

	/**
	 * This Xpect methods compares all computed references at a given EObject to the expected references. The expected
	 * references include the line number.
	 */
	@Xpect
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	public void findReferences(
			@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset) {
		// When you write Xpect test methods, ALWAYS retrieve eObject via IEObjectCoveringRegion to get the right
		// eObject!
		// Do NOT use EObject arg1!
		EObject context = offset.getEObject();
		EObject argEObj = offsetHelper
				.resolveElementAt((XtextResource) context.eResource(), offset.getOffset());
		// If not a cross-reference element, use context instead
		if (argEObj == null)
			argEObj = context;

		EObject eObj = argEObj;

		if (argEObj instanceof ParameterizedTypeRef)
			eObj = ((ParameterizedTypeRef) argEObj).getDeclaredType();

		// Special handling for composed members
		List<EObject> realTargets = new ArrayList<>();
		if ((eObj instanceof TMember) && ((TMember) eObj).isComposed()) {
			// In case of composed member, add the constituent members instead.
			List<TMember> constituentMembers = ((TMember) eObj).getConstituentMembers();
			for (TMember constituentMember : constituentMembers) {
				realTargets.add(constituentMember);
			}
		} else {
			// Standard case
			realTargets.add(eObj);
		}

		Resource eResource = eObj.eResource();
		TargetURIs targets = targetURISetProvider.get();

		for (EObject realTarget : realTargets) {
			collector.add(realTarget, targets);
		}

		IResourceDescriptions index = resourceDescriptionsProvider.getResourceDescriptions(eResource);
		ArrayList<String> result = Lists.newArrayList();
		IReferenceFinder.Acceptor acceptor = new IReferenceFinder.Acceptor() {
			@Override
			public void accept(EObject src, URI srcURI, EReference eRef, int idx, EObject tgtOrProxy, URI tgtURI) {
				if (src instanceof PropertyNameOwner)
					src = ((PropertyNameOwner) src).getDeclaredName();

				ICompositeNode srcNode = NodeModelUtils.getNode(src);
				int line = srcNode.getStartLine();

				String moduleName;
				if (src.eResource() instanceof N4JSResource) {
					N4JSResource n4jsResource = (N4JSResource) src.eResource();
					moduleName = n4jsResource.getModule().getQualifiedName();
				} else {
					moduleName = "(unknown resource)";
				}

				String text = NodeModelUtils.getTokenText(srcNode);
				if (src instanceof GenericDeclaration)
					text = ((GenericDeclaration) src).getDefinedType().getName();

				String resultText = moduleName + " - " + text + " - " + line;

				result.add(resultText);
			}

			@Override
			public void accept(IReferenceDescription description) {
				// This method is only called in case of finding refs for primitives.
				// For instance, the method is called when a reference to a primitive type (e.g. string)
				// is found in primitive_ts.n4ts
				// We don't care about those in Xpect test.
			}
		};

		SimpleResourceAccess resourceAccess = new SimpleResourceAccess(eResource.getResourceSet());
		referenceFinder.findAllReferences(targets, resourceAccess, index, acceptor, null);

		expectation.assertEquals(result);
	}
}
