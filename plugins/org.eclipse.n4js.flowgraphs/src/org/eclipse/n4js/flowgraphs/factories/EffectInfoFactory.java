/**
 * Copyright (c) 2017 Marcus Mews.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marcus Mews - Initial API and implementation
 */
package org.eclipse.n4js.flowgraphs.factories;

@SuppressWarnings("javadoc")
public class EffectInfoFactory {
	// TODO GH-235

	// public static void buildEffectInfo(Graph graph) {
	// for (JavaFeature feature : graph.javaFeatures.getAllFeatures()) {
	// if (feature instanceof Expression) {
	// Expression expr = (Expression) feature;
	//
	// EffectInfo info = null;
	// info = buildEffectInfo(graph.javaFeatures, expr);
	// graph.effectMap.put(expr, info);
	// }
	// }
	// }
	//
	// static EffectInfo buildEffectInfo(JavaFeatures featureMap, Expression expression) {
	// EffectInfo ei = new EffectInfo();
	//
	// boolean isReference = false;
	// isReference |= (expression.type == ExpressionType.DotSelector);
	// isReference |= (expression.type == ExpressionType.ArraySelector);
	// isReference |= (expression.type == ExpressionType.Name);
	// isReference |= (expression.type == ExpressionType.VariableReference);
	// if (isReference) {
	// ei.references = getSymbol(featureMap, expression);
	// }
	//
	// ei.seReads.addAll(getSymbols(featureMap, expression.seReads));
	// ei.seWrites.addAll(getSymbols(featureMap, expression.seWrites));
	//
	// if (expression instanceof AssignmentExpression) {
	// AssignmentExpression ae = (AssignmentExpression) expression;
	// Symbol symbol = getSymbol(featureMap, ae.leftHandSide);
	// // ei.seWrites.clear();
	// if (ae.isTopInStatement) {
	// ei.assigned = symbol;
	// if (ae.integrateRHS()) {
	// EffectInfo rhsEI = buildEffectInfo(featureMap, ae.rightHandSide);
	// ei.seReads.addAll(rhsEI.seReads);
	// ei.seWrites.addAll(rhsEI.seWrites);
	// ei.seWrites.remove(symbol);
	// }
	// } else {
	// ei.seWrites.add(symbol);
	// }
	// }
	//
	// if (expression instanceof VariableDeclarator) {
	// VariableDeclarator vd = (VariableDeclarator) expression;
	// Symbol declSymbol = getSymbol(featureMap, vd.nameExpression);
	//
	// if (vd.init == null)
	// ei.declares = declSymbol;
	//
	// if (vd.init != null) {
	// if (declSymbol != null)
	// ei.seWrites.add(declSymbol);
	//
	// if (!vd.init.type.isExtractable()) {
	// EffectInfo eiInit = buildEffectInfo(featureMap, vd.init);
	// ei.references = eiInit.references;
	// ei.seReads.addAll(eiInit.seReads);
	// ei.seWrites.addAll(eiInit.seWrites);
	// }
	// }
	// }
	//
	// /**
	// * Method Calls and Constructor Calls are of type MethodCall
	// */
	// boolean someMethod = false;
	// someMethod |= expression.type == ExpressionType.MethodCall;
	// someMethod |= expression.type == ExpressionType.Creator;
	// if (someMethod) {
	// switch (AliasStrategy.setting) {
	// case SimpleRelaxed:
	// // no method effects
	// break;
	// case SimpleStrict:
	// addMethodEffectsSS(featureMap, expression, ei);
	// break;
	// case Wala:
	// // TODO
	// break;
	// default:
	// break;
	// }
	// }
	//
	// ei.validate();
	// return ei;
	// }
	//
	// private static void addMethodEffectsSS(JavaFeatures featureMap, Expression expression, EffectInfo ei) {
	// Symbol s = getSymbol(featureMap, expression);
	// if (s != null) {
	// ei.seReads.add(s);
	// ei.seWrites.add(s);
	// }
	// }
	//
	// public static Symbol getSymbol(JavaFeatures featureMap, Expression expression) {
	// Object token = Symbols.getSymbolToken(expression.astNode);
	// if (token == null)
	// throw new RuntimeException("Symbol token missing.");
	//
	// Symbol s = featureMap.getSymbolForToken(token);
	// if (s == null) {
	// s = new SymbolWithToken(token);
	// featureMap.putSymbol(s.getToken(), s);
	// }
	// return s;
	// }
	//
	// private static List<Symbol> getSymbols(JavaFeatures featureMap, List<Expression> expressions) {
	// List<Symbol> symbols = new LinkedList<>();
	// for (Expression expr : expressions) {
	// Symbol symbol = getSymbol(featureMap, expr);
	// if (symbol != null)
	// symbols.add(symbol);
	// }
	// return symbols;
	// }
}
