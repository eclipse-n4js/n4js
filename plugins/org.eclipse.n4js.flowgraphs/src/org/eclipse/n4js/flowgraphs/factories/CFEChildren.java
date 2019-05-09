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

import static org.eclipse.n4js.flowgraphs.factories.NodeNames.LHS;
import static org.eclipse.n4js.flowgraphs.factories.NodeNames.RHS;

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
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.EqualityExpression;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.ExpressionAnnotationList;
import org.eclipse.n4js.n4JS.FunctionExpression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.JSXAttribute;
import org.eclipse.n4js.n4JS.JSXChild;
import org.eclipse.n4js.n4JS.JSXElement;
import org.eclipse.n4js.n4JS.JSXExpression;
import org.eclipse.n4js.n4JS.JSXFragment;
import org.eclipse.n4js.n4JS.JSXPropertyAttribute;
import org.eclipse.n4js.n4JS.JSXSpreadAttribute;
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

/**
 * All {@link Expression}s can have a set of children in the sense, that these children are also respected by the
 * control flow. This class provides the function {@link #get(ReentrantASTIterator, ControlFlowElement)} that returns
 * all control flow relevant sub-expressions of a given {@link Expression}.
 * <p/>
 * <b>Attention:</b> The order of {@link Node#astPosition}s is important, and thus the order of Node instantiation! In
 * case this order is inconsistent to {@link OrderedEContentProvider}, the assertion with the message
 * {@link ReentrantASTIterator#ASSERTION_MSG_AST_ORDER} is thrown.
 */
final class CFEChildren {

	/**
	 * Returns all control flow relevant sub-expressions of the given {@link Expression}.
	 */
	static List<Node> get(ReentrantASTIterator astIter, ControlFlowElement expr) {
		return new InternalExpressionChildren(astIter).doSwitch(expr);
	}

	static Node addDelegatingNode(ReentrantASTIterator astIter, List<Node> cfc, String name, ControlFlowElement cfe,
			ControlFlowElement delegate) {

		if (delegate != null) {
			DelegatingNode delegatingNode = DelegatingNodeFactory.create(astIter, name, cfe, delegate);
			cfc.add(delegatingNode);
			return delegatingNode;
		}
		return null;
	}

	static private class InternalExpressionChildren extends N4JSSwitch<List<Node>> {
		private final ReentrantASTIterator astIter;

		InternalExpressionChildren(ReentrantASTIterator astIter) {
			this.astIter = astIter;
		}

		Node addDelegatingNode(List<Node> cfc, String name, ControlFlowElement cfe,
				ControlFlowElement delegate) {

			return CFEChildren.addDelegatingNode(astIter, cfc, name, cfe, delegate);
		}

		Node addHelperNode(List<Node> cfc, String name, ControlFlowElement cfe) {
			Node node = new HelperNode(name, astIter.pos(), cfe);
			cfc.add(node);
			return node;
		}

		@Override
		public List<Node> caseAdditiveExpression(AdditiveExpression ae) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, "arg_1", ae, ae.getLhs());
			addDelegatingNode(cfc, "arg_2", ae, ae.getRhs());
			return cfc;
		}

		@Override
		public List<Node> caseAssignmentExpression(AssignmentExpression ae) {
			List<Node> cfc = new LinkedList<>();
			Expression lhs = ae.getLhs();
			addDelegatingNode(cfc, LHS, ae, lhs);
			addDelegatingNode(cfc, RHS, ae, ae.getRhs());
			return cfc;
		}

		@Override
		public List<Node> caseArrayLiteral(ArrayLiteral al) {
			List<Node> cfc = new LinkedList<>();
			for (ArrayElement aElem : al.getElements()) {
				int i = al.getElements().indexOf(aElem);
				String name = "arrayElem_" + i;
				Expression exp = aElem.getExpression();
				if (exp == null) {
					addHelperNode(cfc, name, al);
				} else {
					addDelegatingNode(cfc, name, al, exp);
				}
			}
			return cfc;
		}

		@Override
		public List<Node> caseAwaitExpression(AwaitExpression ae) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, NodeNames.EXPRESSION, ae, ae.getExpression());
			return cfc;
		}

		@Override
		public List<Node> caseBinaryBitwiseExpression(BinaryBitwiseExpression bbe) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, "arg_1", bbe, bbe.getLhs());
			addDelegatingNode(cfc, "arg_2", bbe, bbe.getRhs());
			return cfc;
		}

		@Override
		public List<Node> caseBindingElement(BindingElement be) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, "nestedPattern", be, be.getNestedPattern());
			addDelegatingNode(cfc, "declaration", be, be.getVarDecl());
			addDelegatingNode(cfc, "initializer", be, be.getExpression());
			return cfc;
		}

		@Override
		public List<Node> caseArrayBindingPattern(ArrayBindingPattern abp) {
			List<Node> cfc = new LinkedList<>();
			for (int i = 0; i < abp.getElements().size(); i++) {
				BindingElement be = abp.getElements().get(i);
				addDelegatingNode(cfc, "elem_" + i, abp, be);
			}
			return cfc;
		}

		@Override
		public List<Node> caseObjectBindingPattern(ObjectBindingPattern obp) {
			List<Node> cfc = new LinkedList<>();
			for (int i = 0; i < obp.getProperties().size(); i++) {
				BindingProperty bp = obp.getProperties().get(i);
				BindingElement be = bp.getValue();
				addDelegatingNode(cfc, "init_" + i, obp, be);
			}
			return cfc;
		}

		@Override
		public List<Node> caseCastExpression(CastExpression ce) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, NodeNames.EXPRESSION, ce, ce.getExpression());
			return cfc;
		}

		@Override
		public List<Node> caseCommaExpression(CommaExpression ce) {
			List<Node> cfc = new LinkedList<>();
			for (int i = 0; i < ce.getExprs().size(); i++) {
				Expression expr = ce.getExprs().get(i);
				addDelegatingNode(cfc, "expression_" + i, ce, expr);
			}
			return cfc;
		}

		@Override
		public List<Node> caseEqualityExpression(EqualityExpression ee) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, "arg_1", ee, ee.getLhs());
			addDelegatingNode(cfc, "arg_2", ee, ee.getRhs());
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
			addDelegatingNode(cfc, "target", iae, iae.getTarget());
			addDelegatingNode(cfc, "index", iae, iae.getIndex());
			return cfc;
		}

		@Override
		public List<Node> caseLiteral(Literal lit) {
			return Collections.emptyList();
		}

		@Override
		public List<Node> caseMultiplicativeExpression(MultiplicativeExpression me) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, "arg_1", me, me.getLhs());
			addDelegatingNode(cfc, "arg_2", me, me.getRhs());
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
			addDelegatingNode(cfc, "callee", ne, ne.getCallee());
			for (int i = 0; i < ne.getArguments().size(); i++) {
				Argument arg = ne.getArguments().get(i);
				addDelegatingNode(cfc, "arg_" + i, ne, arg.getExpression());
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

				if (pa instanceof PropertyNameValuePair) {
					PropertyNameValuePair pnvp = (PropertyNameValuePair) pa;
					LiteralOrComputedPropertyName locpn = pnvp.getDeclaredName();
					if (locpn != null) {
						addDelegatingNode(cfc, "declaredName_" + i, ol, locpn.getExpression());
					}
					addDelegatingNode(cfc, "expression_" + i, ol, pnvp.getExpression());
					if (pa instanceof PropertyNameValuePairSingleName) {
						PropertyNameValuePairSingleName pnvpsv = (PropertyNameValuePairSingleName) pa;
						addDelegatingNode(cfc, "identifierRef_" + i, ol, pnvpsv.getIdentifierRef());
					}
				}
			}
			return cfc;
		}

		@Override
		public List<Node> caseParenExpression(ParenExpression pe) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, NodeNames.EXPRESSION, pe, pe.getExpression());
			return cfc;
		}

		@Override
		public List<Node> caseParameterizedCallExpression(ParameterizedCallExpression pce) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, NodeNames.TARGET, pce, pce.getTarget());
			for (int i = 0; i < pce.getArguments().size(); i++) {
				Argument arg = pce.getArguments().get(i);
				addDelegatingNode(cfc, "arg_" + i, pce, arg.getExpression());
			}
			return cfc;
		}

		@Override
		public List<Node> caseParameterizedPropertyAccessExpression(ParameterizedPropertyAccessExpression ppae) {

			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, "target", ppae, ppae.getTarget());
			return cfc;
		}

		@Override
		public List<Node> casePostfixExpression(PostfixExpression pe) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, NodeNames.EXPRESSION, pe, pe.getExpression());
			return cfc;
		}

		@Override
		public List<Node> casePromisifyExpression(PromisifyExpression pe) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, NodeNames.EXPRESSION, pe, pe.getExpression());
			return cfc;
		}

		// ReferencingElementExpression_IM

		@Override
		public List<Node> caseRelationalExpression(RelationalExpression re) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, "arg_1", re, re.getLhs());
			addDelegatingNode(cfc, "arg_2", re, re.getRhs());
			return cfc;
		}

		@Override
		public List<Node> caseShiftExpression(ShiftExpression se) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, "arg_1", se, se.getLhs());
			addDelegatingNode(cfc, "arg_2", se, se.getRhs());
			return cfc;
		}

		@Override
		public List<Node> caseSuperLiteral(SuperLiteral sl) {
			return Collections.emptyList();
		}

		@Override
		public List<Node> caseTaggedTemplateString(TaggedTemplateString tts) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, "target", tts, tts.getTarget());
			addDelegatingNode(cfc, "template", tts, tts.getTemplate());
			return cfc;
		}

		@Override
		public List<Node> caseTemplateLiteral(TemplateLiteral tl) {
			List<Node> cfc = new LinkedList<>();
			for (int i = 0; i < tl.getSegments().size(); i++) {
				Expression segm = tl.getSegments().get(i);
				addDelegatingNode(cfc, "segment_" + i, tl, segm);
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
			addDelegatingNode(cfc, NodeNames.EXPRESSION, ue, ue.getExpression());
			return cfc;
		}

		@Override
		public List<Node> caseVariableBinding(VariableBinding vb) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, NodeNames.EXPRESSION, vb, vb.getExpression());
			addDelegatingNode(cfc, "pattern", vb, vb.getPattern());
			return cfc;
		}

		@Override
		public List<Node> caseYieldExpression(YieldExpression ye) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, NodeNames.EXPRESSION, ye, ye.getExpression());
			return cfc;
		}

		@Override
		public List<Node> caseJSXElement(JSXElement jsxel) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, "openTagName", jsxel, jsxel.getJsxElementName().getExpression());
			for (int i = 0; i < jsxel.getJsxAttributes().size(); i++) {
				JSXAttribute jsxAttr = jsxel.getJsxAttributes().get(i);
				addDelegatingNode(cfc, "attr_" + i, jsxel, jsxAttr);
			}
			for (int i = 0; i < jsxel.getJsxChildren().size(); i++) {
				JSXChild jsxChild = jsxel.getJsxChildren().get(i);
				if (jsxChild instanceof JSXElement) {
					JSXElement jsxElem = (JSXElement) jsxChild;
					addDelegatingNode(cfc, "child_" + i, jsxel, jsxElem);
				}
				if (jsxChild instanceof JSXExpression) {
					JSXExpression jsxEx = (JSXExpression) jsxChild;
					addDelegatingNode(cfc, "child_" + i, jsxel, jsxEx.getExpression());
				}
			}
			if (jsxel.getJsxClosingName() != null) {
				addDelegatingNode(cfc, "closeTagName", jsxel, jsxel.getJsxClosingName().getExpression());
			}
			return cfc;
		}

		@Override
		public List<Node> caseJSXFragment(JSXFragment jsxFrag) {
			List<Node> cfc = new LinkedList<>();

			for (int i = 0; i < jsxFrag.getJsxChildren().size(); i++) {
				JSXChild jsxChild = jsxFrag.getJsxChildren().get(i);
				if (jsxChild instanceof JSXElement) {
					JSXElement jsxElem = (JSXElement) jsxChild;
					addDelegatingNode(cfc, "child_" + i, jsxFrag, jsxElem);
				}
				if (jsxChild instanceof JSXExpression) {
					JSXExpression jsxEx = (JSXExpression) jsxChild;
					addDelegatingNode(cfc, "child_" + i, jsxFrag, jsxEx.getExpression());
				}
			}
			return cfc;
		}

		@Override
		public List<Node> caseJSXExpression(JSXExpression jsxEx) {
			List<Node> cfc = new LinkedList<>();
			// getDelegatingNode(cfc, EXPRESSION_NODE, jsxEx.getExpression()));
			return cfc;
		}

		@Override
		public List<Node> caseJSXSpreadAttribute(JSXSpreadAttribute jsxSA) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, NodeNames.EXPRESSION, jsxSA, jsxSA.getExpression());
			return cfc;
		}

		@Override
		public List<Node> caseJSXPropertyAttribute(JSXPropertyAttribute jsxPA) {
			List<Node> cfc = new LinkedList<>();
			addDelegatingNode(cfc, "value", jsxPA, jsxPA.getJsxAttributeValue());
			return cfc;
		}

	}

}
