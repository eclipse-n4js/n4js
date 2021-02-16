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
package org.eclipse.n4js.ide.tests.helper.server.xt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtMethodPattern.Match;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.XtextResource;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 *
 */
public class XtIdeTest extends AbstractIdeTest {

	static final XtMethodPattern PATTERN_PREDS = XtMethodPattern.builder().keyword("preds")
			.textOpt("type", (Object[]) ControlFlowType.values())
			.objMan("at").build();

	static final XtMethodPattern PATTERN_SUCCS = XtMethodPattern.builder().keyword("succs")
			.textOpt("type", (Object[]) ControlFlowType.values())
			.objMan("at").build();

	static final XtMethodPattern PATTERN_PATH = XtMethodPattern.builder().keyword("path")
			.objMan("from")
			.objOpt("to")
			.objOpt("notTo")
			.objOpt("via")
			.objOpt("notVia").build();

	static final XtMethodPattern PATTERN_ALLBRANCHES = XtMethodPattern.builder().keyword("allBranches")
			.objMan("from")
			.textOpt("direction", (Object[]) TraverseDirection.values()).build();

	static final XtMethodPattern PATTERN_ALLPATHS = XtMethodPattern.builder().keyword("allPaths")
			.objMan("from")
			.textOpt("direction", (Object[]) TraverseDirection.values()).build();

	static final XtMethodPattern PATTERN_COMMONPREDS = XtMethodPattern.builder().keyword("commonPreds")
			.objMan("of")
			.objMan("and").build();

	@Inject
	private XtMethods xtMethods;

	@Inject
	private XtMethodsFlowgraphs xtFlowgraphs;

	XtFileData xtData;
	XtMethodsIssues issueHelper;
	XtextResource resource;
	XtResourceEObjectAccessor eobjProvider;

	/**
	 */
	public void initializeXtFile(XtFileData newXtData) throws IOException {
		Preconditions.checkNotNull(newXtData);
		xtData = newXtData;

		cleanupTestDataFolder();
		testWorkspaceManager.createTestOnDisk(xtData.workspace);

		for (MethodData startupMethod : xtData.startupMethodData) {
			switch (startupMethod.name) {
			case "startAndWaitForLspServer":
				startAndWaitForLspServer();
				break;
			default:
				throw new IllegalArgumentException("Unknown method: " + startupMethod.name);
			}
		}
		FileURI xtModule = getFileURIFromModuleName(xtData.workspace.moduleNameOfXtFile);

		languageServer.getResourceTaskManager().runInTemporaryContext(xtModule.toURI(), "test", false,
				(context, ci) -> {
					resource = context.getResource();
					Preconditions.checkNotNull(resource);
					eobjProvider = new XtResourceEObjectAccessor(xtData, resource);
					if (resource instanceof N4JSResource) {
						N4JSResource n4Res = ((N4JSResource) resource);
						n4Res.resolveLazyCrossReferences(ci);
					}
					return null;
				}).join();

		ArrayList<MethodData> issueTests = new ArrayList<>();
		LOOP: for (MethodData testMethod : xtData.getTestMethodData()) {
			switch (testMethod.name) {
			case "nowarnings":
			case "noerrors":
			case "warnings":
			case "errors":
				issueTests.add(testMethod);
				break;
			default:
				// test method data is sorted: issue related tests methods come first
				break LOOP;
			}
		}

		this.issueHelper = new XtMethodsIssues(xtData, getIssuesInFile(xtModule), issueTests);
	}

	/**
	 */
	public void invokeTestMethod(MethodData testMethodData) throws InterruptedException, ExecutionException {
		switch (testMethodData.name) {
		// 1st pass test methods
		case "nowarnings": {
			nowarnings(testMethodData);
			break;
		}
		case "noerrors": {
			noerrors(testMethodData);
			break;
		}
		case "warnings": {
			warnings(testMethodData);
			break;
		}
		case "errors": {
			errors(testMethodData);
			break;
		}
		// 2nd pass test methods
		case "accessModifier":
			accessModifier(testMethodData);
			break;
		case "definition":
			definition(testMethodData);
			break;
		case "elementKeyword":
			elementKeyword(testMethodData);
			break;
		case "findReferences":
			findReferences(testMethodData);
			break;
		case "linkedName":
			linkedName(testMethodData);
			break;
		case "linkedFragment":
			linkedFragment(testMethodData);
			break;
		case "linkedPathname":
			linkedPathname(testMethodData);
			break;
		case "type":
			type(testMethodData);
			break;
		case "typeArgs":
			typeArgs(testMethodData);
			break;
		// flow graph test methods
		case "allBranches":
			allBranches(testMethodData);
			break;
		case "allEdges":
			allEdges(testMethodData);
			break;
		case "allMergeBranches":
			allMergeBranches(testMethodData);
			break;
		case "allPaths":
			allPaths(testMethodData);
			break;
		case "astOrder":
			astOrder(testMethodData);
			break;
		case "cfContainer":
			cfContainer(testMethodData);
			break;
		case "commonPreds":
			commonPreds(testMethodData);
			break;
		case "instanceofguard":
			instanceofguard(testMethodData);
			break;
		case "path":
			path(testMethodData);
			break;
		case "preds":
			preds(testMethodData);
			break;
		case "succs":
			succs(testMethodData);
			break;
		// unsupported test methods
		case "migration":
		case "typeSwitch":
		case "typeSwitchTypeRef":
		case "version":
			throw new IllegalArgumentException("Unsupported legacy method " + testMethodData.name);
		default:
			throw new IllegalArgumentException("Unknown method: " + testMethodData.name);
		}
	}

	/** 	 */
	public void teardown() throws IOException {
		deleteTestProject();
	}

	/**
	 * Validates that there are no errors at the given location.
	 *
	 * <pre>
	 * // Xpect noerrors --&gt;
	 * </pre>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void noerrors(MethodData data) {
		issueHelper.noerrors(data);
	}

	/**
	 * Validates that there are no warnings at the given location.
	 *
	 * <pre>
	 * // Xpect nowarnings --&gt;
	 * </pre>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void nowarnings(MethodData data) {
		issueHelper.nowarnings(data);
	}

	/**
	 * Compares expected errors at a given location to actual errors at that location. Multiple errors are separated by
	 * space.
	 *
	 * <pre>
	 * // Xpect errors --&gt; "&ltMESSAGE&gt" at "&ltLOCATION&gt"
	 * </pre>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void errors(MethodData data) {
		issueHelper.errors(data);
	}

	/**
	 * Compares expected warnings at a given location to actual warnings at that location. Multiple warnings are
	 * separated by space.
	 *
	 * <pre>
	 * // Xpect warnings --&gt; "&ltMESSAGE&gt" at "&ltLOCATION&gt"
	 * </pre>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void warnings(MethodData data) {
		issueHelper.warnings(data);
	}

	/**
	 * This xpect method can evaluate the accessibility of {@link TMember}s. For example, given a field of a class or a
	 * {@link ParameterizedPropertyAccessExpression}, the xpect methods returns their explicit or implicit declared
	 * accessibility such as {@code public} or {@code private}.
	 *
	 * <pre>
	 * // Xpect accessModifier at '&ltLOCATION&gt' --&gt; &ltACCESS MODIFIER&gt
	 * </pre>
	 *
	 * The location is optional.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void accessModifier(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "accessModifier", "at");
		String accessModifierStr = XtMethods.getAccessModifierString(ocr.getEObject());
		assertEquals(data.expectation, accessModifierStr);
	}

	/**
	 * Calls LSP endpoint 'definition'.
	 *
	 * <pre>
	 * // Xpect definition --&gt; &ltFILE AND RANGE&gt
	 * </pre>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void definition(MethodData data) throws InterruptedException, ExecutionException {
		Position position = eobjProvider.checkAndGetPosition(data, "definition", "at");
		FileURI uri = getFileURIFromModuleName(xtData.workspace.moduleNameOfXtFile);

		CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> future = callDefinition(
				uri.toString(), position.getLine(), position.getCharacter());

		Either<List<? extends Location>, List<? extends LocationLink>> definitions = future.get();

		String actualSignatureHelp = getStringLSP4J().toString4(definitions);
		assertEquals(data.expectation, actualSignatureHelp.trim());
	}

	/**
	 * Test the element keyword of an element. Examples of element keywords are getter, setter, field etc.
	 *
	 * <pre>
	 * // Xpect elementKeyword at '&ltLOCATION&gt' --&gt; &ltKEYWORD&gt
	 * </pre>
	 *
	 * The location is optional.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void elementKeyword(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "elementKeyword", "at");
		String elementKeywordStr = xtMethods.getElementKeywordString(ocr);
		assertEquals(data.expectation, elementKeywordStr);
	}

	/**
	 * Compares all computed references at a given EObject to the expected references. The expected references include
	 * the line number.
	 *
	 * <pre>
	 * // Xpect findReferences at '&ltLOCATION&gt' --&gt; &ltCOMMA SEPARATED REFERENCES&gt
	 * </pre>
	 *
	 * The location is optional.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void findReferences(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "findReferences", "at");
		List<String> findReferencesArray = xtMethods.getFindReferences(ocr);
		String expectation = data.expectation.replaceAll("\\s+", " ").replaceAll(",\\s*", ",\n");
		assertEquals(expectation, Strings.join(",\n", findReferencesArray));
	}

	/**
	 * TODO
	 */
	@Xpect
	public void formattedLines(@SuppressWarnings("unused") MethodData data) {
		// TODO
	}

	/**
	 * <pre>
	 * // Xpect linkedName at '&ltLOCATION&gt' --&gt; &ltTYPE NAME&gt
	 * </pre>
	 */
	@Xpect
	public void linkedName(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "linkedName", "at");
		QualifiedName linkedName = xtMethods.linkedName(ocr);
		assertEquals(data.expectation, linkedName.toString());
	}

	/**
	 * <pre>
	 * // Xpect linkedFragment at '&ltLOCATION&gt' --&gt; &ltFRAGMENT NAME&gt
	 * </pre>
	 */
	@Xpect
	public void linkedFragment(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "linkedFragment", "at");
		String fragmentName = xtMethods.linkedFragment(ocr);
		assertEquals(data.expectation, fragmentName);
	}

	/**
	 * Similar to {@link #linkedName(MethodData)} but concatenating the fully qualified name again instead of using the
	 * qualified name provider, as the latter may not create a valid name for non-globally available elements.
	 * <p>
	 * The qualified name created by retrieving all "name" properties of the target and its containers, using '/' as
	 * separator.
	 *
	 * <pre>
	 * // Xpect linkedPathname at '&ltLOCATION&gt' --&gt; &ltPATH NAME&gt
	 * </pre>
	 */
	@Xpect
	public void linkedPathname(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "linkedPathname", "at");
		String pathName = xtMethods.linkedPathname(ocr);
		assertEquals(data.expectation, pathName);
	}

	/**
	 * Checks that an element/expression has a certain type. Usage:
	 *
	 * <pre>
	 * // Xpect type of '&ltLOCATION&gt' --&gt; &ltTYPE&gt
	 * </pre>
	 *
	 * The location is optional.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void type(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "type", "of");
		String typeStr = xtMethods.getTypeString(ocr.getEObject(), false);
		assertEquals(data.expectation, typeStr);
	}

	/**
	 * Checks that a call expression to a generic function/method has the correct type arguments. Mostly intended for
	 * checking the automatically inferred type arguments in case of a non-parameterized call expression.
	 *
	 * <pre>
	 * class C {
	 *     &lt;S,T> m(p1: S, p2: T) {}
	 * }
	 * var c: C;
	 *
	 * // Xpect typeArgs of 'm' --> number, string
	 * c.m(42,"hello");
	 * </pre>
	 *
	 * Note that the offset denotes the target(!) of the call expression, not the call expression itself. Usually it is
	 * enough to provide the last IdentifierRef before the call expression's parentheses.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void typeArgs(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "typeArgs", "of");
		String typeArgStr = xtMethods.getTypeArgumentsString(ocr.getEObject());
		assertEquals(data.expectation, typeArgStr);
	}

	/**
	 * This xpect method can evaluate all branches from a given start code element. If no start code element is
	 * specified, the first code element of the containing function.
	 *
	 * <pre>
	 * // Xpect allBranches from '&ltLOCATION&gt' direction '&lt{@link TraverseDirection#values()}&gt' --&gt;
	 * // &ltBRANCHES&gt
	 * </pre>
	 *
	 * The direction is optional.
	 *
	 * BRANCHES is a comma separated list.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void allBranches(MethodData data) {
		Match match = PATTERN_ALLBRANCHES.match(data, eobjProvider);
		String direction = match.getText("direction");
		IEObjectCoveringRegion ocrReference = match.ocrReference;
		IEObjectCoveringRegion ocrFrom = match.getEObjectWithOffset("from");
		List<String> branchStrings = xtFlowgraphs.allBranches(ocrFrom, direction, ocrReference);
		assertEqualIterables(data.expectation, branchStrings);
	}

	/**
	 * This xpect method can evaluate all edges of the containing function.
	 *
	 * <pre>
	 * // Xpect allEdges from '&ltLOCATION&gt' --&gt; &ltEDGES&gt
	 * </pre>
	 *
	 * EDGES is a comma separated list.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void allEdges(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "allEdges", "from");
		List<String> pathStrings = xtFlowgraphs.allEdges(ocr);
		assertEqualIterables(data.expectation, pathStrings);
	}

	/**
	 * This xpect method can evaluate all branches that are merged at the given node name.
	 *
	 * <pre>
	 * // Xpect allMergeBranches --&gt; &ltMERGE BRANCHES&gt
	 * </pre>
	 *
	 * Note: Each 'allMergeBranch' test needs to have its own xt file.
	 *
	 * MERGE BRANCHES is a comma separated list.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void allMergeBranches(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "allMergeBranches", null);
		List<String> edgeStrings = xtFlowgraphs.allMergeBranches(ocr);
		assertEqualIterables(data.expectation, edgeStrings);
	}

	/**
	 * This xpect method can evaluate all paths from a given start code element. If no start code element is specified,
	 * the first code element of the containing function.
	 *
	 * <pre>
	 * // Xpect allPaths from '&ltLOCATION&gt' direction '&lt{@link TraverseDirection#values()}&gt' --&gt; &ltPATHS&gt
	 * </pre>
	 *
	 * The direction is optional.
	 *
	 * PATHS is a comma separated list.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void allPaths(MethodData data) {
		Match match = PATTERN_ALLPATHS.match(data, eobjProvider);
		String direction = match.getText("direction");
		IEObjectCoveringRegion ocrReference = match.ocrReference;
		IEObjectCoveringRegion ocrFrom = match.getEObjectWithOffset("from");
		List<String> pathStrings = xtFlowgraphs.allPaths(ocrFrom, direction, ocrReference);
		assertEqualIterables(data.expectation, pathStrings);
	}

	/**
	 * This xpect method can evaluate the control flow order of ast elements.
	 *
	 * <pre>
	 * // Xpect astOrder of '&ltLOCATION&gt' --&gt; &ltAST ORDER&gt
	 * </pre>
	 *
	 * AST ORDER is a comma separated list of ast elements.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void astOrder(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "astOrder", "of");
		List<String> astElements = xtFlowgraphs.astOrder(ocr);
		assertEqualIterables(data.expectation, astElements);
	}

	/**
	 * This xpect method can evaluate the control flow container of a given {@link ControlFlowElement}.
	 *
	 * <pre>
	 * // Xpect cfContainer of '&ltLOCATION&gt' --&gt; &ltCONTROL FLOW CONTAINER&gt
	 * </pre>
	 *
	 * CONTROL FLOW CONTAINER is the name of an ast element.
	 **/
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void cfContainer(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "cfContainer", "of");
		String containerStr = xtFlowgraphs.cfContainer(ocr);
		assertEquals(data.expectation, containerStr);
	}

	/**
	 * This xpect method can evaluate all common predecessors of two {@link ControlFlowElement}s.
	 *
	 * <pre>
	 * // Xpect cfContainer of '&ltLOCATION A&gt' and '&ltLOCATION B&gt' --&gt; &ltCOMMON PREDECESSORS&gt
	 * </pre>
	 *
	 * COMMON PREDECESSORS is a comma separated list.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void commonPreds(MethodData data) {
		Match match = PATTERN_COMMONPREDS.match(data, eobjProvider);
		IEObjectCoveringRegion ocrA = match.getEObjectWithOffset("of");
		IEObjectCoveringRegion ocrB = match.getEObjectWithOffset("and");
		List<String> commonPredStrs = xtFlowgraphs.commonPreds(ocrA, ocrB);
		assertEqualIterables(data.expectation, commonPredStrs);
	}

	/**
	 * This xpect method can evaluate the guards that do (not) hold on an expression.
	 *
	 * <pre>
	 * // Xpect instanceofguard of '&ltLOCATION&gt' --&gt; &ltGUARDS&gt
	 * </pre>
	 *
	 * GUARDS is a comma separated list.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void instanceofguard(MethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "instanceofguard", "of");
		List<String> guardStrs = xtFlowgraphs.instanceofguard(ocr);
		assertEqualIterables(data.expectation, guardStrs);
	}

	/**
	 * This xpect method can evaluate if the specified path exists or not.
	 *
	 * <pre>
	 * // Xpect path from '&ltLOCATION A&gt' to|notTo '&ltLOCATION Z&gt' via '&ltLOCATION M&gt' notVia '&ltLOCATION
	 * // N&gt' --&gt;
	 * </pre>
	 *
	 * Arguments 'to', 'notTo', 'via', 'notVia' are optional. Either 'to' or 'notTo' must be defined.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void path(MethodData data) {
		Match match = PATTERN_PATH.match(data, eobjProvider);
		IEObjectCoveringRegion ocrReference = match.ocrReference;
		IEObjectCoveringRegion ocrFrom = match.getEObjectWithOffset("from");
		IEObjectCoveringRegion ocrTo = match.getEObjectWithOffset("to");
		IEObjectCoveringRegion ocrNotTo = match.getEObjectWithOffset("notTo");
		IEObjectCoveringRegion ocrVia = match.getEObjectWithOffset("via");
		IEObjectCoveringRegion ocrNotVia = match.getEObjectWithOffset("notVia");

		assertTrue("Either 'to' or 'notTo' must be defined.", ocrTo != null || ocrNotTo != null);

		// No test assertions here: fails internally iff test fails
		xtFlowgraphs.path(ocrFrom, ocrTo, ocrNotTo, ocrVia, ocrNotVia, ocrReference);
	}

	/**
	 * This xpect method can evaluate the direct predecessors of a code element. The predecessors can be limited when
	 * specifying the edge type.
	 *
	 * <pre>
	 * // Xpect preds type '{@link ControlFlowType#values()}' at '&ltLOCATION&gt' --&gt; &ltPREDECESSORS&gt
	 * </pre>
	 *
	 * The type is optional. If given, the result is filtered accordingly. The type parameter does not work on self
	 * loops.
	 *
	 * PREDECESSORS is a comma separated list.
	 *
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void preds(MethodData data) {
		Match match = PATTERN_PREDS.match(data, eobjProvider);
		String type = match.getText("type");
		IEObjectCoveringRegion ocrAt = match.getEObjectWithOffset("at");
		List<String> predTexts = xtFlowgraphs.preds(type, ocrAt);
		assertEqualIterables(data.expectation, predTexts);
	}

	/**
	 * This xpect method can evaluate the direct successors of a code element. The successors can be limited when
	 * specifying the edge type.
	 *
	 * <pre>
	 * // Xpect succs type '{@link ControlFlowType#values()}' at '&ltLOCATION&gt' --&gt; &ltSUCCESSORS&gt
	 * </pre>
	 *
	 * The type is optional. If given, the result is filtered accordingly. The type parameter does not work on self
	 * loops.
	 *
	 * SUCCESSORS is a comma separated list.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void succs(MethodData data) {
		Match match = PATTERN_SUCCS.match(data, eobjProvider);
		String type = match.getText("type");
		IEObjectCoveringRegion ocrAt = match.getEObjectWithOffset("at");
		List<String> succTexts = xtFlowgraphs.succs(type, ocrAt);
		assertEqualIterables(data.expectation, succTexts);
	}

	private void assertEqualIterables(String s1, Iterable<String> i2s) {
		String[] elems1 = s1.split("\\s*,\\s*");
		List<String> sorted1 = Lists.newArrayList(elems1);
		Collections.sort(sorted1);
		List<String> sorted2 = Lists.newArrayList(i2s);
		Collections.sort(sorted2);
		String s1sorted = Strings.join(", ", sorted1);
		String s2sorted = Strings.join(", ", sorted2);
		assertEquals(s1sorted, s2sorted);
	}
}
