/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.factories;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BinaryLogicalExpression;
import org.eclipse.n4js.n4JS.CastExpression;
import org.eclipse.n4js.n4JS.CommaExpression;
import org.eclipse.n4js.n4JS.ConditionalExpression;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionAnnotationList;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.Literal;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.NewTarget;
import org.eclipse.n4js.n4JS.ObjectLiteral;
import org.eclipse.n4js.n4JS.ParameterizedCallExpression;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.ParenExpression;
import org.eclipse.n4js.n4JS.PostfixExpression;
import org.eclipse.n4js.n4JS.PromisifyExpression;
import org.eclipse.n4js.n4JS.PropertyAssignment;
import org.eclipse.n4js.n4JS.PropertyNameValuePair;
import org.eclipse.n4js.n4JS.PropertyNameValuePairSingleName;
import org.eclipse.n4js.n4JS.RelationalExpression;
import org.eclipse.n4js.n4JS.ShiftExpression;
import org.eclipse.n4js.n4JS.SuperLiteral;
import org.eclipse.n4js.n4JS.TaggedTemplateString;
import org.eclipse.n4js.n4JS.TemplateLiteral;
import org.eclipse.n4js.n4JS.ThisLiteral;
import org.eclipse.n4js.n4JS.UnaryExpression;
import org.eclipse.n4js.n4JS.YieldExpression;
import org.eclipse.n4js.n4JS.util.N4JSSwitch;
import org.eclipse.n4js.n4jsx.n4JSX.JSXAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.JSXChild;
import org.eclipse.n4js.n4jsx.n4JSX.JSXElement;
import org.eclipse.n4js.n4jsx.n4JSX.JSXExpression;
import org.eclipse.n4js.n4jsx.n4JSX.JSXPropertyAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.JSXSpreadAttribute;
import org.eclipse.n4js.n4jsx.n4JSX.util.N4JSXSwitch;

/**
 * All {@link Expression}s can have a set of children in the sense, that these children are also respected by the
 * control flow. This class provides the function {@link #get(ControlFlowElement)} that returns all control flow
 * relevant sub-expressions of a given {@link Expression}.
 */
final class ExpressionChildren {

	/**
	 * Returns all control flow relevant sub-expressions of the given {@link Expression}.
	 */
	static List<ControlFlowElement> get(ControlFlowElement expr) {
		List<ControlFlowElement> n4jsxExpressionList = new InternalExpressionChildrenX().doSwitch(expr);
		if (n4jsxExpressionList != null) {
			return n4jsxExpressionList;
		}
		return new InternalExpressionChildren().doSwitch(expr);
	}

	static private class InternalExpressionChildren extends N4JSSwitch<List<ControlFlowElement>> {

		@Override
		public List<ControlFlowElement> caseAdditiveExpression(AdditiveExpression ae) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(ae.getLhs());
			cfc.add(ae.getRhs());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseAssignmentExpression(AssignmentExpression ae) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(ae.getLhs());
			cfc.add(ae.getRhs());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseArrayLiteral(ArrayLiteral al) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			for (ArrayElement aElem : al.getElements()) {
				cfc.add(aElem.getExpression());
			}
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseAwaitExpression(AwaitExpression ae) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(ae.getExpression());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseBinaryBitwiseExpression(BinaryBitwiseExpression bbe) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(bbe.getLhs());
			cfc.add(bbe.getRhs());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseBinaryLogicalExpression(BinaryLogicalExpression ble) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(ble.getLhs());
			cfc.add(ble.getRhs());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseCastExpression(CastExpression ce) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(ce.getExpression());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseCommaExpression(CommaExpression ce) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.addAll(ce.getExprs());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseConditionalExpression(ConditionalExpression ce) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(ce.getExpression());
			cfc.add(ce.getTrueExpression());
			cfc.add(ce.getFalseExpression());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseEqualityExpression(EqualityExpression ee) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(ee.getLhs());
			cfc.add(ee.getRhs());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseExpressionAnnotationList(ExpressionAnnotationList eal) {
			return Collections.emptyList();
		}

		@Override
		public List<ControlFlowElement> caseFunctionExpression(FunctionExpression fe) {
			return Collections.emptyList();
		}

		@Override
		public List<ControlFlowElement> caseIdentifierRef(IdentifierRef al) {
			return Collections.emptyList();
		}

		@Override
		public List<ControlFlowElement> caseIndexedAccessExpression(IndexedAccessExpression iae) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(iae.getTarget());
			cfc.add(iae.getIndex());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseLiteral(Literal lit) {
			return Collections.emptyList();
		}

		@Override
		public List<ControlFlowElement> caseMultiplicativeExpression(MultiplicativeExpression me) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(me.getLhs());
			cfc.add(me.getRhs());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseN4ClassExpression(N4ClassExpression n4ce) {
			// currently unsupported feature
			return Collections.emptyList();
		}

		@Override
		public List<ControlFlowElement> caseNewExpression(NewExpression ne) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(ne.getCallee());
			for (Argument arg : ne.getArguments())
				cfc.add(arg.getExpression());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseNewTarget(NewTarget nt) {
			// currently unsupported feature
			return Collections.emptyList();
		}

		@Override
		public List<ControlFlowElement> caseObjectLiteral(ObjectLiteral ol) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			for (PropertyAssignment pa : ol.getPropertyAssignments()) {
				if (pa instanceof PropertyNameValuePair) {
					PropertyNameValuePair pnvp = (PropertyNameValuePair) pa;
					if (pnvp.getExpression() != null) {
						cfc.add(pnvp.getExpression());
					}
					if (pa instanceof PropertyNameValuePairSingleName) {
						PropertyNameValuePairSingleName pnvpsv = (PropertyNameValuePairSingleName) pa;
						if (pnvpsv.getIdentifierRef() != null) {
							cfc.add(pnvpsv.getIdentifierRef());
						}
					}
				}
			}
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseParenExpression(ParenExpression pe) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(pe.getExpression());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseParameterizedCallExpression(ParameterizedCallExpression pce) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(pce.getTarget());
			for (Argument arg : pce.getArguments())
				cfc.add(arg.getExpression());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseParameterizedPropertyAccessExpression(
				ParameterizedPropertyAccessExpression ppae) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(ppae.getTarget());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> casePostfixExpression(PostfixExpression pe) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(pe.getExpression());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> casePromisifyExpression(PromisifyExpression pe) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(pe.getExpression());
			return cfc;
		}

		// ReferencingElementExpression_IM

		@Override
		public List<ControlFlowElement> caseRelationalExpression(RelationalExpression re) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(re.getLhs());
			cfc.add(re.getRhs());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseShiftExpression(ShiftExpression se) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(se.getLhs());
			cfc.add(se.getRhs());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseSuperLiteral(SuperLiteral sl) {
			return Collections.emptyList();
		}

		@Override
		public List<ControlFlowElement> caseTaggedTemplateString(TaggedTemplateString tts) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(tts.getTarget());
			cfc.add(tts.getTemplate());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseTemplateLiteral(TemplateLiteral tl) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			for (Expression segm : tl.getSegments())
				cfc.add(segm);
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseThisLiteral(ThisLiteral tl) {
			return Collections.emptyList();
		}

		@Override
		public List<ControlFlowElement> caseUnaryExpression(UnaryExpression ue) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(ue.getExpression());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseYieldExpression(YieldExpression ye) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			if (ye.getExpression() != null)
				cfc.add(ye.getExpression());
			return cfc;
		}

	}

	static private class InternalExpressionChildrenX extends N4JSXSwitch<List<ControlFlowElement>> {

		@Override
		public List<ControlFlowElement> caseJSXElement(JSXElement jsxel) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(jsxel.getJsxElementName().getExpression());
			for (JSXAttribute jsxAttr : jsxel.getJsxAttributes()) {
				cfc.add(jsxAttr);
			}
			for (JSXChild jsxChild : jsxel.getJsxChildren()) {
				if (jsxChild instanceof JSXElement) {
					JSXElement jsxElem = (JSXElement) jsxChild;
					cfc.add(jsxElem);
				}
				if (jsxChild instanceof JSXExpression) {
					JSXExpression jsxEx = (JSXExpression) jsxChild;
					cfc.add(jsxEx.getExpression());
				}
			}
			if (jsxel.getJsxClosingName() != null) {
				cfc.add(jsxel.getJsxClosingName().getExpression());
			}
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseJSXExpression(JSXExpression jsxEx) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(jsxEx.getExpression());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseJSXSpreadAttribute(JSXSpreadAttribute jsxSA) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(jsxSA.getExpression());
			return cfc;
		}

		@Override
		public List<ControlFlowElement> caseJSXPropertyAttribute(JSXPropertyAttribute jsxPA) {
			List<ControlFlowElement> cfc = new LinkedList<>();
			cfc.add(jsxPA.getJsxAttributeValue());
			return cfc;
		}

	}

}