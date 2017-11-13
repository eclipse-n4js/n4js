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

import org.eclipse.n4js.flowgraphs.model.DelegatingNode;
import org.eclipse.n4js.flowgraphs.model.HelperNode;
import org.eclipse.n4js.flowgraphs.model.Node;
import org.eclipse.n4js.n4JS.AdditiveExpression;
import org.eclipse.n4js.n4JS.Argument;
import org.eclipse.n4js.n4JS.ArrayBindingPattern;
import org.eclipse.n4js.n4JS.ArrayElement;
import org.eclipse.n4js.n4JS.ArrayLiteral;
import org.eclipse.n4js.n4JS.AssignmentExpression;
import org.eclipse.n4js.n4JS.AwaitExpression;
import org.eclipse.n4js.n4JS.BinaryBitwiseExpression;
import org.eclipse.n4js.n4JS.BindingElement;
import org.eclipse.n4js.n4JS.BindingProperty;
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
import org.eclipse.n4js.n4JS.LiteralOrComputedPropertyName;
import org.eclipse.n4js.n4JS.MultiplicativeExpression;
import org.eclipse.n4js.n4JS.N4ClassExpression;
import org.eclipse.n4js.n4JS.NewExpression;
import org.eclipse.n4js.n4JS.NewTarget;
import org.eclipse.n4js.n4JS.ObjectBindingPattern;
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
import org.eclipse.n4js.n4JS.VariableBinding;
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
final class CFEChildren {

	/**
	 * Returns all control flow relevant sub-expressions of the given {@link Expression}.
	 */
	static List<Node> get(ControlFlowElement expr) {
		List<Node> n4jsxExpressionList = new InternalExpressionChildrenX().doSwitch(expr);
		if (n4jsxExpressionList != null) {
			return n4jsxExpressionList;
		}
		return new InternalExpressionChildren().doSwitch(expr);
	}

	static DelegatingNode getDelegatingNode(String name, int id, ControlFlowElement cfe, ControlFlowElement delegate) {
		return new DelegatingNode(name, id, cfe, delegate);
	}

	static HelperNode getHelperNode(String name, int id, ControlFlowElement cfe) {
		return new HelperNode(name, id, cfe);
	}

	static private class InternalExpressionChildren extends N4JSSwitch<List<Node>> {

		@Override
		public List<Node> caseAdditiveExpression(AdditiveExpression ae) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("arg_1", 1, ae, ae.getLhs()));
			cfc.add(getDelegatingNode("arg_2", 2, ae, ae.getRhs()));
			return cfc;
		}

		@Override
		public List<Node> caseAssignmentExpression(AssignmentExpression ae) {
			List<Node> cfc = new LinkedList<>();
			Expression lhs = ae.getLhs();
			cfc.add(getDelegatingNode("lhs", 1, ae, lhs));
			cfc.add(getDelegatingNode("rhs", 2, ae, ae.getRhs()));
			return cfc;
		}

		@Override
		public List<Node> caseArrayLiteral(ArrayLiteral al) {
			List<Node> cfc = new LinkedList<>();
			for (ArrayElement aElem : al.getElements()) {
				int i = al.getElements().indexOf(aElem);
				String name = "arrayElem_" + i;
				Expression exp = aElem.getExpression();
				int id = i + 1;
				Node node = (exp == null) ? getHelperNode(name, id, al) : getDelegatingNode(name, id, al, exp);
				cfc.add(node);
			}
			return cfc;
		}

		@Override
		public List<Node> caseAwaitExpression(AwaitExpression ae) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("expression", 1, ae, ae.getExpression()));
			return cfc;
		}

		@Override
		public List<Node> caseBinaryBitwiseExpression(BinaryBitwiseExpression bbe) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("arg_1", 1, bbe, bbe.getLhs()));
			cfc.add(getDelegatingNode("arg_2", 2, bbe, bbe.getRhs()));
			return cfc;
		}

		@Override
		public List<Node> caseBindingElement(BindingElement be) {
			List<Node> cfc = new LinkedList<>();
			if (be.getNestedPattern() != null) {
				cfc.add(getDelegatingNode("nestedPattern", 1, be, be.getNestedPattern()));
			}
			if (be.getVarDecl() != null) {
				cfc.add(getDelegatingNode("declaration", 1, be, be.getVarDecl()));
			}
			if (be.getExpression() != null) {
				cfc.add(getDelegatingNode("initializer", 1, be, be.getExpression()));
			}
			return cfc;
		}

		@Override
		public List<Node> caseArrayBindingPattern(ArrayBindingPattern abp) {
			List<Node> cfc = new LinkedList<>();
			for (int i = 0; i < abp.getElements().size(); i++) {
				BindingElement be = abp.getElements().get(i);
				int id = i + 1;
				cfc.add(getDelegatingNode("elem_" + i, id, abp, be));
			}
			return cfc;
		}

		@Override
		public List<Node> caseObjectBindingPattern(ObjectBindingPattern obp) {
			List<Node> cfc = new LinkedList<>();
			for (int i = 0; i < obp.getProperties().size(); i++) {
				BindingProperty bp = obp.getProperties().get(i);
				BindingElement be = bp.getValue();
				if (be != null) {
					int id = i + 1;
					cfc.add(getDelegatingNode("init_" + i, id, obp, be));
				}
			}
			return cfc;
		}

		@Override
		public List<Node> caseCastExpression(CastExpression ce) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("expression", 1, ce, ce.getExpression()));
			return cfc;
		}

		@Override
		public List<Node> caseCommaExpression(CommaExpression ce) {
			List<Node> cfc = new LinkedList<>();
			for (int i = 0; i < ce.getExprs().size(); i++) {
				Expression expr = ce.getExprs().get(i);
				int id = i + 1;
				cfc.add(getDelegatingNode("expression_" + i, id, ce, expr));
			}
			return cfc;
		}

		@Override
		public List<Node> caseConditionalExpression(ConditionalExpression ce) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("condition", 1, ce, ce.getExpression()));
			cfc.add(getDelegatingNode("then", 2, ce, ce.getTrueExpression()));
			cfc.add(getDelegatingNode("else", 3, ce, ce.getFalseExpression()));
			return cfc;
		}

		@Override
		public List<Node> caseEqualityExpression(EqualityExpression ee) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("arg_1", 1, ee, ee.getLhs()));
			cfc.add(getDelegatingNode("arg_2", 2, ee, ee.getRhs()));
			return cfc;
		}

		@Override
		public List<Node> caseExpressionAnnotationList(ExpressionAnnotationList eal) {
			return Collections.emptyList();
		}

		@Override
		public List<Node> caseFunctionExpression(FunctionExpression fe) {
			return Collections.emptyList();
		}

		@Override
		public List<Node> caseIdentifierRef(IdentifierRef al) {
			return Collections.emptyList();
		}

		@Override
		public List<Node> caseIndexedAccessExpression(IndexedAccessExpression iae) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("target", 1, iae, iae.getTarget()));
			cfc.add(getDelegatingNode("index", 2, iae, iae.getIndex()));
			return cfc;
		}

		@Override
		public List<Node> caseLiteral(Literal lit) {
			return Collections.emptyList();
		}

		@Override
		public List<Node> caseMultiplicativeExpression(MultiplicativeExpression me) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("arg_1", 1, me, me.getLhs()));
			cfc.add(getDelegatingNode("arg_2", 2, me, me.getRhs()));
			return cfc;
		}

		@Override
		public List<Node> caseN4ClassExpression(N4ClassExpression n4ce) {
			// currently unsupported feature
			return Collections.emptyList();
		}

		@Override
		public List<Node> caseNewExpression(NewExpression ne) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("callee", 1, ne, ne.getCallee()));
			for (int i = 0; i < ne.getArguments().size(); i++) {
				Argument arg = ne.getArguments().get(i);
				int id = i + 2;
				cfc.add(getDelegatingNode("arg_" + i, id, ne, arg.getExpression()));
			}
			return cfc;
		}

		@Override
		public List<Node> caseNewTarget(NewTarget nt) {
			// currently unsupported feature
			return Collections.emptyList();
		}

		@Override
		public List<Node> caseObjectLiteral(ObjectLiteral ol) {
			List<Node> cfc = new LinkedList<>();
			for (int i = 0; i < ol.getPropertyAssignments().size(); i++) {
				PropertyAssignment pa = ol.getPropertyAssignments().get(i);

				int intPos = 1;
				if (pa instanceof PropertyNameValuePair) {
					PropertyNameValuePair pnvp = (PropertyNameValuePair) pa;
					LiteralOrComputedPropertyName locpn = pnvp.getDeclaredName();
					if (locpn != null && locpn.getExpression() != null) {
						cfc.add(getDelegatingNode("declaredName_" + i, intPos++, ol, locpn.getExpression()));
					}
					if (pnvp.getExpression() != null) {
						cfc.add(getDelegatingNode("expression_" + i, intPos++, ol, pnvp.getExpression()));
					}
					if (pa instanceof PropertyNameValuePairSingleName) {
						PropertyNameValuePairSingleName pnvpsv = (PropertyNameValuePairSingleName) pa;
						if (pnvpsv.getIdentifierRef() != null) {
							cfc.add(getDelegatingNode("identifierRef_" + i, intPos++, ol, pnvpsv.getIdentifierRef()));
						}
					}
				}
			}
			return cfc;
		}

		@Override
		public List<Node> caseParenExpression(ParenExpression pe) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("expression", 1, pe, pe.getExpression()));
			return cfc;
		}

		@Override
		public List<Node> caseParameterizedCallExpression(ParameterizedCallExpression pce) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("target", 1, pce, pce.getTarget()));
			for (int i = 0; i < pce.getArguments().size(); i++) {
				Argument arg = pce.getArguments().get(i);
				int id = i + 2;
				cfc.add(getDelegatingNode("arg_" + i, id, pce, arg.getExpression()));
			}
			return cfc;
		}

		@Override
		public List<Node> caseParameterizedPropertyAccessExpression(
				ParameterizedPropertyAccessExpression ppae) {

			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("target", 1, ppae, ppae.getTarget()));
			return cfc;
		}

		@Override
		public List<Node> casePostfixExpression(PostfixExpression pe) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("expression", 1, pe, pe.getExpression()));
			return cfc;
		}

		@Override
		public List<Node> casePromisifyExpression(PromisifyExpression pe) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("expression", 1, pe, pe.getExpression()));
			return cfc;
		}

		// ReferencingElementExpression_IM

		@Override
		public List<Node> caseRelationalExpression(RelationalExpression re) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("arg_1", 1, re, re.getLhs()));
			cfc.add(getDelegatingNode("arg_2", 2, re, re.getRhs()));
			return cfc;
		}

		@Override
		public List<Node> caseShiftExpression(ShiftExpression se) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("arg_1", 1, se, se.getLhs()));
			cfc.add(getDelegatingNode("arg_2", 2, se, se.getRhs()));
			return cfc;
		}

		@Override
		public List<Node> caseSuperLiteral(SuperLiteral sl) {
			return Collections.emptyList();
		}

		@Override
		public List<Node> caseTaggedTemplateString(TaggedTemplateString tts) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("target", 1, tts, tts.getTarget()));
			cfc.add(getDelegatingNode("template", 2, tts, tts.getTemplate()));
			return cfc;
		}

		@Override
		public List<Node> caseTemplateLiteral(TemplateLiteral tl) {
			List<Node> cfc = new LinkedList<>();
			for (int i = 0; i < tl.getSegments().size(); i++) {
				Expression segm = tl.getSegments().get(i);
				int id = i + 1;
				cfc.add(getDelegatingNode("segment_" + i, id, tl, segm));
			}
			return cfc;
		}

		@Override
		public List<Node> caseThisLiteral(ThisLiteral tl) {
			return Collections.emptyList();
		}

		@Override
		public List<Node> caseUnaryExpression(UnaryExpression ue) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("expression", 1, ue, ue.getExpression()));
			return cfc;
		}

		@Override
		public List<Node> caseVariableBinding(VariableBinding vb) {
			List<Node> cfc = new LinkedList<>();
			if (vb.getExpression() != null) {
				cfc.add(getDelegatingNode("expression", 1, vb, vb.getExpression()));
			}
			if (vb.getPattern() != null) {
				cfc.add(getDelegatingNode("pattern", 2, vb, vb.getPattern()));
			}
			return cfc;
		}

		@Override
		public List<Node> caseYieldExpression(YieldExpression ye) {
			List<Node> cfc = new LinkedList<>();
			if (ye.getExpression() != null)
				cfc.add(getDelegatingNode("expression", 1, ye, ye.getExpression()));
			return cfc;
		}

	}

	static private class InternalExpressionChildrenX extends N4JSXSwitch<List<Node>> {

		@Override
		public List<Node> caseJSXElement(JSXElement jsxel) {
			List<Node> cfc = new LinkedList<>();
			int intPos = 1;
			cfc.add(getDelegatingNode("openTagName", intPos++, jsxel, jsxel.getJsxElementName().getExpression()));
			for (int i = 0; i < jsxel.getJsxAttributes().size(); i++) {
				JSXAttribute jsxAttr = jsxel.getJsxAttributes().get(i);
				cfc.add(getDelegatingNode("attr_" + i, intPos++, jsxel, jsxAttr));
			}
			for (int i = 0; i < jsxel.getJsxChildren().size(); i++) {
				JSXChild jsxChild = jsxel.getJsxChildren().get(i);
				if (jsxChild instanceof JSXElement) {
					JSXElement jsxElem = (JSXElement) jsxChild;
					cfc.add(getDelegatingNode("child_" + i, intPos++, jsxel, jsxElem));
				}
				if (jsxChild instanceof JSXExpression) {
					JSXExpression jsxEx = (JSXExpression) jsxChild;
					cfc.add(getDelegatingNode("child_" + i, intPos++, jsxel, jsxEx.getExpression()));
				}
			}
			if (jsxel.getJsxClosingName() != null) {
				cfc.add(getDelegatingNode("closeTagName", intPos++, jsxel, jsxel.getJsxClosingName().getExpression()));
			}
			return cfc;
		}

		@Override
		public List<Node> caseJSXExpression(JSXExpression jsxEx) {
			List<Node> cfc = new LinkedList<>();
			// cfc.add(getDelegatingNode("expression", jsxEx.getExpression()));
			System.out.println("!");
			return cfc;
		}

		@Override
		public List<Node> caseJSXSpreadAttribute(JSXSpreadAttribute jsxSA) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("expression", 1, jsxSA, jsxSA.getExpression()));
			return cfc;
		}

		@Override
		public List<Node> caseJSXPropertyAttribute(JSXPropertyAttribute jsxPA) {
			List<Node> cfc = new LinkedList<>();
			cfc.add(getDelegatingNode("value", 1, jsxPA, jsxPA.getJsxAttributeValue()));
			return cfc;
		}

	}

}
