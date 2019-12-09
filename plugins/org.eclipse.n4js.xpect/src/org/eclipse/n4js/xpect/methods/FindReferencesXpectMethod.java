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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.GenericDeclaration;
import org.eclipse.n4js.n4JS.PropertyNameOwner;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.typeRefs.ParameterizedTypeRef;
import org.eclipse.n4js.utils.FindReferenceHelper;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.IEObjectCoveringRegion;
import org.eclipse.n4js.xpect.common.N4JSXpectRunner;
import org.eclipse.n4js.xpect.methods.scoping.IN4JSCommaSeparatedValuesExpectation;
import org.eclipse.n4js.xpect.methods.scoping.N4JSCommaSeparatedValuesExpectation;
import org.eclipse.xpect.XpectImport;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xpect.xtext.lib.setup.XtextStandaloneSetup;
import org.eclipse.xpect.xtext.lib.setup.XtextWorkspaceSetup;
import org.eclipse.xtext.findReferences.IReferenceFinder;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.junit.runner.RunWith;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * This class provides a Xpect method to specify tests regarding the {@link IReferenceFinder}
 */
@SuppressWarnings("restriction")
@RunWith(N4JSXpectRunner.class)
@XpectImport({ XtextStandaloneSetup.class, XtextWorkspaceSetup.class })
public class FindReferencesXpectMethod {

	@Inject
	private EObjectAtOffsetHelper offsetHelper;

	@Inject
	private FindReferenceHelper findReferenceHelper;

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
		EObject argEObj = offsetHelper.resolveElementAt((XtextResource) context.eResource(), offset.getOffset());
		// If not a cross-reference element, use context instead
		if (argEObj == null)
			argEObj = context;

		EObject eObj = argEObj;

		if (argEObj instanceof ParameterizedTypeRef)
			eObj = ((ParameterizedTypeRef) argEObj).getDeclaredType();

		List<EObject> refs = findReferenceHelper.findReferences(eObj, context.eResource().getResourceSet());
		ArrayList<String> result = Lists.newArrayList();
		for (EObject ref : refs) {
			if (ref instanceof PropertyNameOwner)
				ref = ((PropertyNameOwner) ref).getDeclaredName();

			ICompositeNode srcNode = NodeModelUtils.getNode(ref);
			int line = srcNode.getStartLine();

			String moduleName;
			if (ref.eResource() instanceof N4JSResource) {
				N4JSResource n4jsResource = (N4JSResource) ref.eResource();
				moduleName = n4jsResource.getModule().getQualifiedName();
			} else {
				moduleName = "(unknown resource)";
			}

			String text = NodeModelUtils.getTokenText(srcNode);
			if (ref instanceof GenericDeclaration)
				text = ((GenericDeclaration) ref).getDefinedType().getName();

			String resultText = moduleName + " - " + text + " - " + line;

			result.add(resultText);
		}

		expectation.assertEquals(result);
	}
}
