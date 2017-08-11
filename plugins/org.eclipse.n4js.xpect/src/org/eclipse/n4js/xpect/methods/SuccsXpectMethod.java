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

import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.flowgraphs.N4JSFlowAnalyses;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter;
import org.eclipse.n4js.xpect.common.N4JSOffsetAdapter.IEObjectCoveringRegion;
import org.eclipse.n4js.xpect.methods.scoping.IN4JSCommaSeparatedValuesExpectation;
import org.eclipse.n4js.xpect.methods.scoping.N4JSCommaSeparatedValuesExpectation;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.xpect.XpectImport;
import org.xpect.parameter.ParameterParser;
import org.xpect.runner.Xpect;

import com.google.inject.Inject;

/**
 */
@XpectImport(N4JSOffsetAdapter.class)
public class SuccsXpectMethod {

	@Inject
	N4JSFlowAnalyses flowAnalyses;

	/**
	 * This xpect method can evaluate the accessibility of {@link TMember}s. For example, given a field of a class or a
	 * {@link ParameterizedPropertyAccessExpression}, the xpect methods returns their explicit or implicit declared
	 * accessibility such as {@code public} or {@code private}.
	 */
	@ParameterParser(syntax = "('at' arg1=OFFSET)?")
	@Xpect
	public void succs(
			@N4JSCommaSeparatedValuesExpectation IN4JSCommaSeparatedValuesExpectation expectation,
			IEObjectCoveringRegion offset) {

		EObject context = offset.getEObject();
		if (!(context instanceof ControlFlowElement)) {
			fail("Element '" + getText(context) + "' is not a control flow element");
		}

		ControlFlowElement cfe = (ControlFlowElement) context;
		List<ControlFlowElement> succs = flowAnalyses.getSuccessors(cfe);

		// for (ControlFlowElement node : succs) {
		// ICompositeNode actNode = NodeModelUtils.findActualNodeFor(node);
		// String text = actNode.getText().trim();
		// text = NodeModelUtils.getTokenText(actNode);
		// System.out.println(text);
		// System.out.println("#########");
		// }

		if (succs.isEmpty()) {
			fail("Element '" + getText(context) + "' has no successors");
		} else {
			List<String> succTexts = new LinkedList<>();
			for (ControlFlowElement succ : succs) {
				String succText = getText(succ);
				succTexts.add(succText);
			}
			expectation.assertEquals(succTexts);
		}
	}

	String getText(EObject eo) {
		ICompositeNode actualNode = NodeModelUtils.findActualNodeFor(eo);
		String text = NodeModelUtils.getTokenText(actualNode);
		return text;
	}

}
