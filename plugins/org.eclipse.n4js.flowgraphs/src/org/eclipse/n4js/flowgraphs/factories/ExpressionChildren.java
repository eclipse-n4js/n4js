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

/**
 * All {@link Expression}s can have a set of children in the sense, that these children are also respected by the
 * control flow. This class provides the function {@link #get(Expression)} that returns all control flow relevant
 * sub-expressions of a given {@link Expression}.
 */
final class ExpressionChildren {

	/**
	 * Returns all control flow relevant sub-expressions of the given {@link Expression}.
	 */
	static List<Expression> get(Expression expr) {
		return new InternalExpressionChildren().doSwitch(expr);
	}

	static private class InternalExpressionChildren extends N4JSSwitch<List<Expression>> {

		@Override
		public List<Expression> caseAdditiveExpression(AdditiveExpression ae) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(ae.getLhs());
			cfc.add(ae.getRhs());
			return cfc;
		}

		@Override
		public List<Expression> caseAssignmentExpression(AssignmentExpression ae) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(ae.getLhs());
			cfc.add(ae.getRhs());
			return cfc;
		}

		@Override
		public List<Expression> caseArrayLiteral(ArrayLiteral al) {
			List<Expression> cfc = new LinkedList<>();
			for (ArrayElement aElem : al.getElements()) {
				cfc.add(aElem.getExpression());
			}
			return cfc;
		}

		@Override
		public List<Expression> caseAwaitExpression(AwaitExpression ae) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(ae.getExpression());
			return cfc;
		}

		@Override
		public List<Expression> caseBinaryBitwiseExpression(BinaryBitwiseExpression bbe) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(bbe.getLhs());
			cfc.add(bbe.getRhs());
			return cfc;
		}

		@Override
		public List<Expression> caseBinaryLogicalExpression(BinaryLogicalExpression ble) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(ble.getLhs());
			cfc.add(ble.getRhs());
			return cfc;
		}

		@Override
		public List<Expression> caseCastExpression(CastExpression ce) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(ce.getExpression());
			return cfc;
		}

		@Override
		public List<Expression> caseCommaExpression(CommaExpression ce) {
			List<Expression> cfc = new LinkedList<>();
			cfc.addAll(ce.getExprs());
			return cfc;
		}

		@Override
		public List<Expression> caseConditionalExpression(ConditionalExpression ce) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(ce.getExpression());
			cfc.add(ce.getTrueExpression());
			cfc.add(ce.getFalseExpression());
			return cfc;
		}

		@Override
		public List<Expression> caseEqualityExpression(EqualityExpression ee) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(ee.getLhs());
			cfc.add(ee.getRhs());
			return cfc;
		}

		@Override
		public List<Expression> caseExpressionAnnotationList(ExpressionAnnotationList eal) {
			return Collections.emptyList();
		}

		@Override
		public List<Expression> caseFunctionExpression(FunctionExpression fe) {
			return Collections.emptyList();
		}

		@Override
		public List<Expression> caseIdentifierRef(IdentifierRef al) {
			return Collections.emptyList();
		}

		@Override
		public List<Expression> caseIndexedAccessExpression(IndexedAccessExpression iae) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(iae.getTarget());
			cfc.add(iae.getIndex());
			return cfc;
		}

		@Override
		public List<Expression> caseLiteral(Literal lit) {
			return Collections.emptyList();
		}

		@Override
		public List<Expression> caseMultiplicativeExpression(MultiplicativeExpression me) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(me.getLhs());
			cfc.add(me.getRhs());
			return cfc;
		}

		@Override
		public List<Expression> caseN4ClassExpression(N4ClassExpression n4ce) {
			// currently unsupported feature
			return Collections.emptyList();
		}

		@Override
		public List<Expression> caseNewExpression(NewExpression ne) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(ne.getCallee());
			for (Argument arg : ne.getArguments())
				cfc.add(arg.getExpression());
			return cfc;
		}

		@Override
		public List<Expression> caseNewTarget(NewTarget nt) {
			// currently unsupported feature
			return Collections.emptyList();
		}

		@Override
		public List<Expression> caseObjectLiteral(ObjectLiteral ol) {
			List<Expression> cfc = new LinkedList<>();
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
		public List<Expression> caseParenExpression(ParenExpression pe) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(pe.getExpression());
			return cfc;
		}

		@Override
		public List<Expression> caseParameterizedCallExpression(ParameterizedCallExpression pce) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(pce.getTarget());
			for (Argument arg : pce.getArguments())
				cfc.add(arg.getExpression());
			return cfc;
		}

		@Override
		public List<Expression> caseParameterizedPropertyAccessExpression(ParameterizedPropertyAccessExpression ppae) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(ppae.getTarget());
			return cfc;
		}

		@Override
		public List<Expression> casePostfixExpression(PostfixExpression pe) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(pe.getExpression());
			return cfc;
		}

		@Override
		public List<Expression> casePromisifyExpression(PromisifyExpression pe) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(pe.getExpression());
			return cfc;
		}

		// ReferencingElementExpression_IM

		@Override
		public List<Expression> caseRelationalExpression(RelationalExpression re) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(re.getLhs());
			cfc.add(re.getRhs());
			return cfc;
		}

		@Override
		public List<Expression> caseShiftExpression(ShiftExpression se) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(se.getLhs());
			cfc.add(se.getRhs());
			return cfc;
		}

		@Override
		public List<Expression> caseSuperLiteral(SuperLiteral sl) {
			return Collections.emptyList();
		}

		@Override
		public List<Expression> caseTaggedTemplateString(TaggedTemplateString tts) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(tts.getTarget());
			cfc.add(tts.getTemplate());
			return cfc;
		}

		@Override
		public List<Expression> caseTemplateLiteral(TemplateLiteral tl) {
			List<Expression> cfc = new LinkedList<>();
			for (Expression segm : tl.getSegments())
				cfc.add(segm);
			return cfc;
		}

		@Override
		public List<Expression> caseThisLiteral(ThisLiteral tl) {
			return Collections.emptyList();
		}

		@Override
		public List<Expression> caseUnaryExpression(UnaryExpression ue) {
			List<Expression> cfc = new LinkedList<>();
			cfc.add(ue.getExpression());
			return cfc;
		}

		@Override
		public List<Expression> caseYieldExpression(YieldExpression ye) {
			List<Expression> cfc = new LinkedList<>();
			if (ye.getExpression() != null)
				cfc.add(ye.getExpression());
			return cfc;
		}

	}

}