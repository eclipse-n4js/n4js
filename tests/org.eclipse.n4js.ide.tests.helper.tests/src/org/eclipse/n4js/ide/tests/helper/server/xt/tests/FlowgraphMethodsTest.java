/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.helper.server.xt.tests;

import org.eclipse.n4js.ide.tests.helper.server.xt.XtIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtMethodData;
import org.junit.Test;

/**
 * Test for test methods:
 * <ul>
 * <li/>{@link XtIdeTest#allBranches(XtMethodData)}
 * <li/>{@link XtIdeTest#allEdges(XtMethodData)}
 * <li/>{@link XtIdeTest#allMergeBranches(XtMethodData)}
 * <li/>{@link XtIdeTest#allPaths(XtMethodData)}
 * <li/>{@link XtIdeTest#astOrder(XtMethodData)}
 * <li/>{@link XtIdeTest#cfContainer(XtMethodData)}
 * <li/>{@link XtIdeTest#instanceofguard(XtMethodData)}
 * <li/>{@link XtIdeTest#commonPreds(XtMethodData)}
 * <li/>{@link XtIdeTest#preds(XtMethodData)}
 * <li/>{@link XtIdeTest#succs(XtMethodData)}
 * </ul>
 */
public class FlowgraphMethodsTest extends AbstractXtParentRunnerTest {

	/***/
	@Test
	public void test() throws Exception {
		run("probands/FlowgraphMethods");

		assertFiles("AllBranches.n4js.xt\n"
				+ "AllEdges.n4js.xt\n"
				+ "AllMergeBranches.n4js.xt\n"
				+ "AllPaths.n4js.xt\n"
				+ "AstOrder.n4js.xt\n"
				+ "CfContainer.n4js.xt\n"
				+ "CommonPreds.n4js.xt\n"
				+ "Instanceofguards.n4js.xt\n"
				+ "Path.n4js.xt\n"
				+ "Preds.n4js.xt\n"
				+ "Succs.n4js.xt");
		assertTestStructure("org.eclipse.n4js.ide.tests.helper.server.xt.tests.XtTestSetupTestMockup\n"
				+ " + AllBranches.n4js.xt: probands/FlowgraphMethods\n"
				+ " ++ allBranches~0: from 'b0' 〔probands/FlowgraphMethods/AllBranches.n4js.xt〕(allBranches~0)\n"
				+ " ++ allBranches~1: from 'b0' 〔probands/FlowgraphMethods/AllBranches.n4js.xt〕(allBranches~1)\n"
				+ " + AllEdges.n4js.xt: probands/FlowgraphMethods\n"
				+ " ++ allEdges~0: from 'b0' 〔probands/FlowgraphMethods/AllEdges.n4js.xt〕(allEdges~0)\n"
				+ " ++ allEdges~1: from 'b0' 〔probands/FlowgraphMethods/AllEdges.n4js.xt〕(allEdges~1)\n"
				+ " + AllMergeBranches.n4js.xt: probands/FlowgraphMethods\n"
				+ " ++ allMergeBranches~0:  〔probands/FlowgraphMethods/AllMergeBranches.n4js.xt〕(allMergeBranches~0)\n"
				+ " + AllPaths.n4js.xt: probands/FlowgraphMethods\n"
				+ " ++ allPaths~0: from '\"a\"' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕(allPaths~0)\n"
				+ " ++ allPaths~1: from '\"c\"' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕(allPaths~1)\n"
				+ " ++ allPaths~2: from '\"a\"' direction 'Forward' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕(allPaths~2)\n"
				+ " ++ allPaths~3: from '\"c\"' direction 'Forward' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕(allPaths~3)\n"
				+ " ++ allPaths~4: from '\"c\"' direction 'Backward' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕(allPaths~4)\n"
				+ " ++ allPaths~5: from '\"d\"' direction 'Backward' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕(allPaths~5)\n"
				+ " ++ allPaths~6: from '\"d\"' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕(allPaths~6)\n"
				+ " + AstOrder.n4js.xt: probands/FlowgraphMethods\n"
				+ " ++ astOrder~0: of '+' 〔probands/FlowgraphMethods/AstOrder.n4js.xt〕(astOrder~0)\n"
				+ " + CfContainer.n4js.xt: probands/FlowgraphMethods\n"
				+ " ++ cfContainer~0: of 'b0' 〔probands/FlowgraphMethods/CfContainer.n4js.xt〕(cfContainer~0)\n"
				+ " ++ cfContainer~1: of 'b0' 〔probands/FlowgraphMethods/CfContainer.n4js.xt〕(cfContainer~1)\n"
				+ " + CommonPreds.n4js.xt: probands/FlowgraphMethods\n"
				+ " ++ commonPreds~0: of '\"c\"' and '\"d\"' 〔probands/FlowgraphMethods/CommonPreds.n4js.xt〕(commonPreds~0)\n"
				+ " ++ commonPreds~1: of '\"d\"' and '\"d\"' 〔probands/FlowgraphMethods/CommonPreds.n4js.xt〕(commonPreds~1)\n"
				+ " + Instanceofguards.n4js.xt: probands/FlowgraphMethods\n"
				+ " ++ instanceofguard~0: of 'a' 〔probands/FlowgraphMethods/Instanceofguards.n4js.xt〕(instanceofguard~0)\n"
				+ " + Path.n4js.xt: probands/FlowgraphMethods\n"
				+ " ++ path~0: from '\"a\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕(path~0)\n"
				+ " ++ path~1: from '\"a\"' to '\"d\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕(path~1)\n"
				+ " ++ path~2: from '\"c\"' to '\"d\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕(path~2)\n"
				+ " ++ path~3: from '\"a\"' notTo '\"c\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕(path~3)\n"
				+ " ++ path~4: from '\"a\"' to '\"d\"' via '\"c\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕(path~4)\n"
				+ " ++ path~5: from '\"a\"' to '\"d\"' notVia '\"c\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕(path~5)\n"
				+ " ++ path~6: from '\"a\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕(path~6)\n"
				+ " + Preds.n4js.xt: probands/FlowgraphMethods\n"
				+ " ++ preds~0: at '\"a\"' 〔probands/FlowgraphMethods/Preds.n4js.xt〕(preds~0)\n"
				+ " ++ preds~1: at '\"b\"' 〔probands/FlowgraphMethods/Preds.n4js.xt〕(preds~1)\n"
				+ " ++ preds~2: at '\"b\"' 〔probands/FlowgraphMethods/Preds.n4js.xt〕(preds~2)\n"
				+ " ++ preds~3: at '\"c\"' 〔probands/FlowgraphMethods/Preds.n4js.xt〕(preds~3)\n"
				+ " ++ preds~4: at '\"d\"' 〔probands/FlowgraphMethods/Preds.n4js.xt〕(preds~4)\n"
				+ " + Succs.n4js.xt: probands/FlowgraphMethods\n"
				+ " ++ succs~0: at '\"a\"' 〔probands/FlowgraphMethods/Succs.n4js.xt〕(succs~0)\n"
				+ " ++ succs~1: at '\"a\"' 〔probands/FlowgraphMethods/Succs.n4js.xt〕(succs~1)\n"
				+ " ++ succs~2: at '\"b\"' 〔probands/FlowgraphMethods/Succs.n4js.xt〕(succs~2)\n"
				+ " ++ succs~3: at '\"c\"' 〔probands/FlowgraphMethods/Succs.n4js.xt〕(succs~3)\n"
				+ " ++ succs~4: at '\"d\"' 〔probands/FlowgraphMethods/Succs.n4js.xt〕(succs~4)");

		assertResult("(allBranches~0)",
				"Passed: allBranches~0: from 'b0' 〔probands/FlowgraphMethods/AllBranches.n4js.xt〕");
		assertResult("(allBranches~1)",
				"Failed: allBranches~1: from 'b0' 〔probands/FlowgraphMethods/AllBranches.n4js.xt〕. expected:<[wrong expectation]> but was:<[B0: B -> new B() -> b0 : B = new B() -> var b0 : B = new B(); -> b0 -> b0.methodB -> b0.methodB() -> b0 -> b0.methodB -> b0.methodB()]>");
		assertResult("(allEdges~0)",
				"Passed: allEdges~0: from 'b0' 〔probands/FlowgraphMethods/AllEdges.n4js.xt〕");
		assertResult("(allEdges~1)",
				"Failed: allEdges~1: from 'b0' 〔probands/FlowgraphMethods/AllEdges.n4js.xt〕. expected:<[wrong expectation]> but was:<[B --> new B(), b0 --> b0.methodB, b0 --> b0.methodB, b0 : B = new B() --> var b0 : B = new B();, b0.methodB --> b0.methodB(), b0.methodB --> b0.methodB(), b0.methodB() --> b0, new B() --> b0 : B = new B(), var b0 : B = new B(); --> b0]>");
		assertResult("(allMergeBranches~0)",
				"Passed: allMergeBranches~0:  〔probands/FlowgraphMethods/AllMergeBranches.n4js.xt〕");
		assertResult("(allPaths~0)", "Passed: allPaths~0: from '\"a\"' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕");
		assertResult("(allPaths~1)", "Passed: allPaths~1: from '\"c\"' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕");
		assertResult("(allPaths~2)",
				"Passed: allPaths~2: from '\"a\"' direction 'Forward' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕");
		assertResult("(allPaths~3)",
				"Passed: allPaths~3: from '\"c\"' direction 'Forward' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕");
		assertResult("(allPaths~4)",
				"Passed: allPaths~4: from '\"c\"' direction 'Backward' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕");
		assertResult("(allPaths~5)",
				"Passed: allPaths~5: from '\"d\"' direction 'Backward' 〔probands/FlowgraphMethods/AllPaths.n4js.xt〕");
		assertResult("(astOrder~0)", "Passed: astOrder~0: of '+' 〔probands/FlowgraphMethods/AstOrder.n4js.xt〕");
		assertResult("(cfContainer~0)",
				"Passed: cfContainer~0: of 'b0' 〔probands/FlowgraphMethods/CfContainer.n4js.xt〕");
		assertResult("(cfContainer~1)",
				"Failed: cfContainer~1: of 'b0' 〔probands/FlowgraphMethods/CfContainer.n4js.xt〕. expected:<[wrong expectation]> but was:<[Script]>");
		assertResult("(commonPreds~0)",
				"Passed: commonPreds~0: of '\"c\"' and '\"d\"' 〔probands/FlowgraphMethods/CommonPreds.n4js.xt〕");
		assertResult("(commonPreds~1)",
				"Failed: commonPreds~1: of '\"d\"' and '\"d\"' 〔probands/FlowgraphMethods/CommonPreds.n4js.xt〕. expected:<[wrong expectation]> but was:<[\"d\"]>");
		assertResult("(instanceofguard~0)",
				"Passed: instanceofguard~0: of 'a' 〔probands/FlowgraphMethods/Instanceofguards.n4js.xt〕");
		assertResult("(path~0)",
				"Failed: path~0: from '\"a\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕. Either 'to' or 'notTo' must be defined.");
		assertResult("(path~1)",
				"Passed: path~1: from '\"a\"' to '\"d\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕");
		assertResult("(path~2)",
				"Passed: path~2: from '\"c\"' to '\"d\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕");
		assertResult("(path~3)",
				"Failed: path~3: from '\"a\"' notTo '\"c\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕. A path was found");
		assertResult("(path~4)",
				"Passed: path~4: from '\"a\"' to '\"d\"' via '\"c\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕");
		assertResult("(path~5)",
				"Passed: path~5: from '\"a\"' to '\"d\"' notVia '\"c\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕");
		assertResult("(path~6)",
				"Failed: path~6: from '\"a\"' 〔probands/FlowgraphMethods/Path.n4js.xt〕. Element not found: from");
		assertResult("(preds~0)", "Passed: preds~0: at '\"a\"' 〔probands/FlowgraphMethods/Preds.n4js.xt〕");
		assertResult("(preds~1)", "Passed: preds~1: at '\"b\"' 〔probands/FlowgraphMethods/Preds.n4js.xt〕");
		assertResult("(preds~2)",
				"Failed: preds~2: at '\"b\"' 〔probands/FlowgraphMethods/Preds.n4js.xt〕. expected:<[wrong expectation]> but was:<[\"a\"]>");
		assertResult("(preds~3)", "Passed: preds~3: at '\"c\"' 〔probands/FlowgraphMethods/Preds.n4js.xt〕");
		assertResult("(preds~4)", "Passed: preds~4: at '\"d\"' 〔probands/FlowgraphMethods/Preds.n4js.xt〕");
		assertResult("(succs~0)", "Passed: succs~0: at '\"a\"' 〔probands/FlowgraphMethods/Succs.n4js.xt〕");
		assertResult("(succs~1)",
				"Failed: succs~1: at '\"a\"' 〔probands/FlowgraphMethods/Succs.n4js.xt〕. expected:<[wrong expectation]> but was:<[\"b\"]>");
		assertResult("(succs~2)", "Passed: succs~2: at '\"b\"' 〔probands/FlowgraphMethods/Succs.n4js.xt〕");
		assertResult("(succs~3)", "Passed: succs~3: at '\"c\"' 〔probands/FlowgraphMethods/Succs.n4js.xt〕");
		assertResult("(succs~4)", "Passed: succs~4: at '\"d\"' 〔probands/FlowgraphMethods/Succs.n4js.xt〕");
	}

}
