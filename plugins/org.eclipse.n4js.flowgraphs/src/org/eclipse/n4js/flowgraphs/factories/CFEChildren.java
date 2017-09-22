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
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * All {@link Expression}s can have a set of children in the sense, that these children are also respected by the
 * control flow. This class provides the function {@link #get(ControlFlowElement)} that returns all control flow
 * relevant sub-expressions of a given {@link Expression}.
 */
final class CFEChildren {

	/**
	 * Returns all control flow relevant sub-expressions of the given {@link Expression}.
	 */
	static List<Pair<String, ControlFlowElement>> get(ControlFlowElement expr) {
		List<Pair<String, ControlFlowElement>> n4jsxExpressionList = new InternalExpressionChildrenX().doSwitch(expr);
		if (n4jsxExpressionList != null) {
			return n4jsxExpressionList;
		}
		return new InternalExpressionChildren().doSwitch(expr);
	}

	static private class InternalExpressionChildren extends N4JSSwitch<List<Pair<String, ControlFlowElement>>> {

		@Override
		public List<Pair<String, ControlFlowElement>> caseAdditiveExpression(AdditiveExpression ae) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("arg_1", ae.getLhs()));
			cfc.add(Pair.of("arg_2", ae.getRhs()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseAssignmentExpression(AssignmentExpression ae) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			Expression lhs = ae.getLhs();
			cfc.add(Pair.of("lhs", lhs));
			cfc.add(Pair.of("rhs", ae.getRhs()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseArrayLiteral(ArrayLiteral al) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			for (ArrayElement aElem : al.getElements()) {
				int i = al.getElements().indexOf(aElem);
				cfc.add(Pair.of("arrayElem_" + i, aElem.getExpression()));
			}
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseAwaitExpression(AwaitExpression ae) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("expression", ae.getExpression()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseBinaryBitwiseExpression(BinaryBitwiseExpression bbe) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("arg_1", bbe.getLhs()));
			cfc.add(Pair.of("arg_2", bbe.getRhs()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseBindingElement(BindingElement be) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			if (be.getNestedPattern() != null) {
				cfc.add(Pair.of("nestedPattern", be.getNestedPattern()));
			}
			if (be.getVarDecl() != null) {
				cfc.add(Pair.of("declaration", be.getVarDecl()));
			}
			if (be.getExpression() != null) {
				cfc.add(Pair.of("initializer", be.getExpression()));
			}
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseArrayBindingPattern(ArrayBindingPattern abp) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			for (int i = 0; i < abp.getElements().size(); i++) {
				BindingElement be = abp.getElements().get(i);
				cfc.add(Pair.of("elem_" + i, be));
			}
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseObjectBindingPattern(ObjectBindingPattern obp) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			for (int i = 0; i < obp.getProperties().size(); i++) {
				BindingProperty bp = obp.getProperties().get(i);
				BindingElement be = bp.getValue();
				if (be != null) {
					cfc.add(Pair.of("init_" + i, be));
				}
			}
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseCastExpression(CastExpression ce) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("expression", ce.getExpression()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseCommaExpression(CommaExpression ce) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			for (int i = 0; i < ce.getExprs().size(); i++) {
				Expression expr = ce.getExprs().get(i);
				cfc.add(Pair.of("expression_" + i, expr));
			}
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseConditionalExpression(ConditionalExpression ce) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("condition", ce.getExpression()));
			cfc.add(Pair.of("then", ce.getTrueExpression()));
			cfc.add(Pair.of("else", ce.getFalseExpression()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseEqualityExpression(EqualityExpression ee) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("arg_1", ee.getLhs()));
			cfc.add(Pair.of("arg_2", ee.getRhs()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseExpressionAnnotationList(ExpressionAnnotationList eal) {
			return Collections.emptyList();
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseFunctionExpression(FunctionExpression fe) {
			return Collections.emptyList();
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseIdentifierRef(IdentifierRef al) {
			return Collections.emptyList();
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseIndexedAccessExpression(IndexedAccessExpression iae) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("target", iae.getTarget()));
			cfc.add(Pair.of("index", iae.getIndex()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseLiteral(Literal lit) {
			return Collections.emptyList();
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseMultiplicativeExpression(MultiplicativeExpression me) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("arg_1", me.getLhs()));
			cfc.add(Pair.of("arg_2", me.getRhs()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseN4ClassExpression(N4ClassExpression n4ce) {
			// currently unsupported feature
			return Collections.emptyList();
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseNewExpression(NewExpression ne) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("callee", ne.getCallee()));
			for (int i = 0; i < ne.getArguments().size(); i++) {
				Argument arg = ne.getArguments().get(i);
				cfc.add(Pair.of("arg_" + i, arg.getExpression()));
			}
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseNewTarget(NewTarget nt) {
			// currently unsupported feature
			return Collections.emptyList();
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseObjectLiteral(ObjectLiteral ol) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			for (int i = 0; i < ol.getPropertyAssignments().size(); i++) {
				PropertyAssignment pa = ol.getPropertyAssignments().get(i);

				if (pa instanceof PropertyNameValuePair) {
					PropertyNameValuePair pnvp = (PropertyNameValuePair) pa;
					LiteralOrComputedPropertyName locpn = pnvp.getDeclaredName();
					if (locpn != null && locpn.getExpression() != null) {
						cfc.add(Pair.of("declaredName_" + i, locpn.getExpression()));
					}
					if (pnvp.getExpression() != null) {
						cfc.add(Pair.of("expression_" + i, pnvp.getExpression()));
					}
					if (pa instanceof PropertyNameValuePairSingleName) {
						PropertyNameValuePairSingleName pnvpsv = (PropertyNameValuePairSingleName) pa;
						if (pnvpsv.getIdentifierRef() != null) {
							cfc.add(Pair.of("identifierRef_" + i, pnvpsv.getIdentifierRef()));
						}
					}
				}
			}
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseParenExpression(ParenExpression pe) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("expression", pe.getExpression()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseParameterizedCallExpression(ParameterizedCallExpression pce) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("target", pce.getTarget()));
			for (int i = 0; i < pce.getArguments().size(); i++) {
				Argument arg = pce.getArguments().get(i);
				cfc.add(Pair.of("arg_" + i, arg.getExpression()));
			}
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseParameterizedPropertyAccessExpression(
				ParameterizedPropertyAccessExpression ppae) {

			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("target", ppae.getTarget()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> casePostfixExpression(PostfixExpression pe) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("expression", pe.getExpression()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> casePromisifyExpression(PromisifyExpression pe) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("expression", pe.getExpression()));
			return cfc;
		}

		// ReferencingElementExpression_IM

		@Override
		public List<Pair<String, ControlFlowElement>> caseRelationalExpression(RelationalExpression re) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("arg_1", re.getLhs()));
			cfc.add(Pair.of("arg_2", re.getRhs()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseShiftExpression(ShiftExpression se) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("arg_1", se.getLhs()));
			cfc.add(Pair.of("arg_2", se.getRhs()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseSuperLiteral(SuperLiteral sl) {
			return Collections.emptyList();
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseTaggedTemplateString(TaggedTemplateString tts) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("target", tts.getTarget()));
			cfc.add(Pair.of("template", tts.getTemplate()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseTemplateLiteral(TemplateLiteral tl) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			for (int i = 0; i < tl.getSegments().size(); i++) {
				Expression segm = tl.getSegments().get(i);
				cfc.add(Pair.of("segment_" + i, segm));
			}
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseThisLiteral(ThisLiteral tl) {
			return Collections.emptyList();
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseUnaryExpression(UnaryExpression ue) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("expression", ue.getExpression()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseVariableBinding(VariableBinding vb) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			if (vb.getExpression() != null) {
				cfc.add(Pair.of("expression", vb.getExpression()));
			}
			if (vb.getPattern() != null) {
				cfc.add(Pair.of("pattern", vb.getPattern()));
			}
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseYieldExpression(YieldExpression ye) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			if (ye.getExpression() != null)
				cfc.add(Pair.of("expression", ye.getExpression()));
			return cfc;
		}

	}

	static private class InternalExpressionChildrenX extends N4JSXSwitch<List<Pair<String, ControlFlowElement>>> {

		@Override
		public List<Pair<String, ControlFlowElement>> caseJSXElement(JSXElement jsxel) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("openTagName", jsxel.getJsxElementName().getExpression()));
			for (int i = 0; i < jsxel.getJsxAttributes().size(); i++) {
				JSXAttribute jsxAttr = jsxel.getJsxAttributes().get(i);
				cfc.add(Pair.of("attr_" + i, jsxAttr));
			}
			for (int i = 0; i < jsxel.getJsxChildren().size(); i++) {
				JSXChild jsxChild = jsxel.getJsxChildren().get(i);
				if (jsxChild instanceof JSXElement) {
					JSXElement jsxElem = (JSXElement) jsxChild;
					cfc.add(Pair.of("child_" + i, jsxElem));
				}
				if (jsxChild instanceof JSXExpression) {
					JSXExpression jsxEx = (JSXExpression) jsxChild;
					cfc.add(Pair.of("child_" + i, jsxEx.getExpression()));
				}
			}
			if (jsxel.getJsxClosingName() != null) {
				cfc.add(Pair.of("closeTagName", jsxel.getJsxClosingName().getExpression()));
			}
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseJSXExpression(JSXExpression jsxEx) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("expression", jsxEx.getExpression()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseJSXSpreadAttribute(JSXSpreadAttribute jsxSA) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("expression", jsxSA.getExpression()));
			return cfc;
		}

		@Override
		public List<Pair<String, ControlFlowElement>> caseJSXPropertyAttribute(JSXPropertyAttribute jsxPA) {
			List<Pair<String, ControlFlowElement>> cfc = new LinkedList<>();
			cfc.add(Pair.of("value", jsxPA.getJsxAttributeValue()));
			return cfc;
		}

	}

}
