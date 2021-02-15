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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.AbstractStructuredIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtFileData.MethodData;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.xpect.parameter.ParameterParser;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.XtextResource;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 *
 */
public class XtIdeTest extends AbstractIdeTest {
	static final String CURSOR = AbstractStructuredIdeTest.CURSOR_SYMBOL;

	static final XtPattern PATTERN_PREDS = XtPattern.builder().keyword("preds")
			.argOpt("type", (Object[]) ControlFlowType.values())
			.argMan("at").build();

	static final XtPattern PATTERN_SUCCS = XtPattern.builder().keyword("succs")
			.argOpt("type", (Object[]) ControlFlowType.values())
			.argMan("at").build();

	static final XtPattern PATTERN_PATH = XtPattern.builder().keyword("path")
			.argMan("from")
			.argOpt("to")
			.argOpt("notTo")
			.argOpt("via")
			.argOpt("notVia").build();

	static final XtPattern PATTERN_ALLBRANCHES = XtPattern.builder().keyword("allBranches")
			.argMan("from")
			.argOpt("direction", (Object[]) TraverseDirection.values()).build();

	static final XtPattern PATTERN_ALLPATHS = XtPattern.builder().keyword("allPaths")
			.argMan("from")
			.argOpt("direction", (Object[]) TraverseDirection.values()).build();

	static final XtPattern PATTERN_COMMONPREDS = XtPattern.builder().keyword("commonPreds")
			.argMan("of")
			.argMan("and").build();

	@Inject
	private XtMethods xtMethods;

	@Inject
	private XtMethodsFlowgraphs xtFlowgraphs;

	XtFileData xtData;
	XtMethodsIssues issueHelper;
	XtextResource resource;

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
		case "astOrder":
			astOrder(testMethodData);
			break;
		case "preds":
			preds(testMethodData);
			break;
		case "succs":
			succs(testMethodData);
			break;
		case "path":
			path(testMethodData);
			break;
		case "allMergeBranches":
			allMergeBranches(testMethodData);
			break;
		case "allBranches":
			allBranches(testMethodData);
			break;
		case "allPaths":
			allPaths(testMethodData);
			break;
		case "allEdges":
			allEdges(testMethodData);
			break;
		case "commonPreds":
			commonPreds(testMethodData);
			break;
		case "cfContainer":
			cfContainer(testMethodData);
			break;
		case "instanceofguard":
			instanceofguard(testMethodData);
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
	 * The location (at) is optional.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void accessModifier(MethodData data) {
		int offset = getOffset(data, "accessModifier", "at");
		EObject eObject = XtMethods.getEObject(resource, offset, 0);
		String accessModifierStr = XtMethods.getAccessModifierString(eObject);
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
		Position position = getPosition(data, "definition", "at");
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
	 * The location (at) is optional.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void elementKeyword(MethodData data) {
		int offset = getOffset(data, "elementKeyword", "at");
		EObject eObject = XtMethods.getEObject(resource, offset, 0);
		String elementKeywordStr = xtMethods.getElementKeywordString(eObject, offset);
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
	 * The location (at) is optional.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void findReferences(MethodData data) {
		int offset = getOffset(data, "findReferences", "at");
		EObject eObject = XtMethods.getEObject(resource, offset, 0);
		List<String> findReferencesArray = xtMethods.getFindReferences(eObject, offset);
		String expectation = data.expectation.replaceAll("\\s+", " ").replaceAll(",\\s*", ",\n");
		assertEquals(expectation, Strings.join(",\n", findReferencesArray));
	}

	/**
	 *
	 */
	@Xpect
	public void formattedLines(MethodData data) {
		// TODO
	}

	/**
	 * <pre>
	 * // Xpect linkedName at '&ltLOCATION&gt' --&gt; &ltTYPE NAME&gt
	 * </pre>
	 */
	@Xpect
	public void linkedName(MethodData data) {
		int offset = getOffset(data, "linkedName", "at");
		EObject eObject = XtMethods.getEObject(resource, offset, 0);
		QualifiedName linkedName = xtMethods.linkedName(eObject, offset);
		assertEquals(data.expectation, linkedName.toString());
	}

	/**
	 * <pre>
	 * // Xpect linkedFragment at '&ltLOCATION&gt' --&gt; &ltFRAGMENT NAME&gt
	 * </pre>
	 */
	@Xpect
	public void linkedFragment(MethodData data) {
		int offset = getOffset(data, "linkedFragment", "at");
		EObject eObject = XtMethods.getEObject(resource, offset, 0);
		String fragmentName = xtMethods.linkedFragment(eObject, offset);
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
		int offset = getOffset(data, "linkedPathname", "at");
		EObject eObject = XtMethods.getEObject(resource, offset, 0);
		String pathName = xtMethods.linkedPathname(eObject, offset);
		assertEquals(data.expectation, pathName);
	}

	/**
	 * Checks that an element/expression has a certain type. Usage:
	 *
	 * <pre>
	 * // Xpect type of '&ltLOCATION&gt' --&gt; &ltTYPE&gt
	 * </pre>
	 *
	 * The location (of) is optional.
	 *
	 * The location is identified by the offset. Note that there are different implementations of IEObjectOwner, and we
	 * need IEStructuralFeatureAndEObject, while ICrossEReferenceAndEObject or IEAttributeAndEObject would not work in
	 * all cases (as not all eobjects we test have cross references or attributes, but feature is the join of both).
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void type(MethodData data) {
		int offset = getOffset(data, "type", "of");
		EObject eObject = XtMethods.getEObject(resource, offset, 0);
		String typeStr = xtMethods.getTypeString(eObject, false);
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
		int offset = getOffset(data, "typeArgs", "of");
		EObject eObject = XtMethods.getEObject(resource, offset, 0);
		String typeArgStr = xtMethods.getTypeArgumentsString(eObject);
		assertEquals(data.expectation, typeArgStr);
	}

	/**
	 * This xpect method can evaluate the direct predecessors of a code element. The predecessors can be limited when
	 * specifying the edge type.
	 * <p>
	 * <b>Attention:</b> The type parameter <i>does not</i> work on self loops!
	 */
	@ParameterParser(syntax = "('of' arg2=OFFSET)?")
	@Xpect
	public void astOrder(MethodData data) {
		IEObjectCoveringRegion ocr = getObjectCoveringRegion(data, "astOrder", "of");
		List<String> astElements = xtFlowgraphs.astOrder(ocr);
		assertEqualIterables(data.expectation, astElements);
	}

	/**
	 * This xpect method can evaluate the direct predecessors of a code element. The predecessors can be limited when
	 * specifying the edge type.
	 * <p>
	 * <b>Attention:</b> The type parameter <i>does not</i> work on self loops!
	 */
	@ParameterParser(syntax = "('type' arg1=STRING)? ('at' arg2=OFFSET)?")
	@Xpect
	public void preds(MethodData data) {
		PATTERN_PREDS.match(data.getMethodNameWithArgs());
		String type = PATTERN_PREDS.get("type");
		String at = PATTERN_PREDS.get("at");
		IEObjectCoveringRegion ocrAt = getObjectCoveringRegion(data, at);
		List<String> predTexts = xtFlowgraphs.preds(type, ocrAt);
		assertEqualIterables(data.expectation, predTexts);
	}

	/**
	 * This xpect method can evaluate the direct successors of a code element. The successors can be limited when
	 * specifying the edge type.
	 * <p>
	 * <b>Attention:</b> The type parameter <i>does not</i> work on self loops!
	 */
	@ParameterParser(syntax = "('type' arg1=STRING)? ('at' arg2=OFFSET)?")
	@Xpect
	public void succs(MethodData data) {
		PATTERN_SUCCS.match(data.getMethodNameWithArgs());
		String type = PATTERN_SUCCS.get("type");
		String at = PATTERN_SUCCS.get("at");
		IEObjectCoveringRegion ocrAt = getObjectCoveringRegion(data, at);
		List<String> succTexts = xtFlowgraphs.succs(type, ocrAt);
		assertEqualIterables(data.expectation, succTexts);
	}

	/** This xpect method can evaluate if the tested element is a transitive predecessor of the given element. */
	@ParameterParser(syntax = "'from' arg0=OFFSET ('to' arg1=OFFSET)? ('notTo' arg2=OFFSET)? ('via' arg3=OFFSET)? ('notVia' arg4=OFFSET)? ('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg5=OFFSET)?")
	@Xpect
	public void path(MethodData data) {
		PATTERN_PATH.match(data.getMethodNameWithArgs());
		String from = PATTERN_PATH.get("from");
		String to = PATTERN_PATH.get("to");
		String notTo = PATTERN_PATH.get("notTo");
		String via = PATTERN_PATH.get("via");
		String notVia = PATTERN_PATH.get("notVia");
		IEObjectCoveringRegion ocrReference = getObjectCoveringRegion(data, null);
		IEObjectCoveringRegion ocrFrom = getObjectCoveringRegion(data, from);
		IEObjectCoveringRegion ocrTo = getObjectCoveringRegion(data, to);
		IEObjectCoveringRegion ocrNotTo = getObjectCoveringRegion(data, notTo);
		IEObjectCoveringRegion ocrVia = getObjectCoveringRegion(data, via);
		IEObjectCoveringRegion ocrNotVia = getObjectCoveringRegion(data, notVia);

		// No test assertions here: fails internally iff test fails
		xtFlowgraphs.path(ocrFrom, ocrTo, ocrNotTo, ocrVia, ocrNotVia, ocrReference);
	}

	/**
	 * This xpect method can evaluate all branches that are merged at the given node name.
	 */
	@ParameterParser(syntax = "('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg1=OFFSET)?")
	@Xpect
	public void allMergeBranches(MethodData data) {
		IEObjectCoveringRegion ocr = getObjectCoveringRegion(data, "allMergeBranches", null);
		List<String> edgeStrings = xtFlowgraphs.allMergeBranches(ocr);
		assertEqualIterables(data.expectation, edgeStrings);
	}

	/**
	 * This xpect method can evaluate all branches from a given start code element. If no start code element is
	 * specified, the first code element of the containing function.
	 */
	@ParameterParser(syntax = "('from' arg1=OFFSET)? ('direction' arg2=STRING)? ('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg3=OFFSET)?")
	@Xpect
	public void allBranches(MethodData data) {
		PATTERN_ALLBRANCHES.match(data.getMethodNameWithArgs());
		String from = PATTERN_ALLBRANCHES.get("from");
		String direction = PATTERN_ALLBRANCHES.get("direction");
		IEObjectCoveringRegion ocrReference = getObjectCoveringRegion(data, null);
		IEObjectCoveringRegion ocrFrom = getObjectCoveringRegion(data, from);
		List<String> branchStrings = xtFlowgraphs.allBranches(ocrFrom, direction, ocrReference);
		assertEqualIterables(data.expectation, branchStrings);
	}

	/**
	 * This xpect method can evaluate all paths from a given start code element. If no start code element is specified,
	 * the first code element of the containing function.
	 */
	@ParameterParser(syntax = "('from' arg1=OFFSET)? ('direction' arg2=STRING)? ('pleaseNeverUseThisParameterSinceItExistsOnlyToGetAReferenceOffset' arg3=OFFSET)?")
	@Xpect
	public void allPaths(MethodData data) {
		PATTERN_ALLPATHS.match(data.getMethodNameWithArgs());
		String from = PATTERN_ALLPATHS.get("from");
		String direction = PATTERN_ALLPATHS.get("direction");
		IEObjectCoveringRegion ocrReference = getObjectCoveringRegion(data, null);
		IEObjectCoveringRegion ocrFrom = getObjectCoveringRegion(data, from);
		List<String> pathStrings = xtFlowgraphs.allPaths(ocrFrom, direction, ocrReference);
		assertEqualIterables(data.expectation, pathStrings);
	}

	/** This xpect method can evaluate all edges of the containing function. */
	@ParameterParser(syntax = "('from' arg1=OFFSET)?")
	@Xpect
	public void allEdges(MethodData data) {
		IEObjectCoveringRegion ocr = getObjectCoveringRegion(data, "allEdges", "from");
		List<String> pathStrings = xtFlowgraphs.allEdges(ocr);
		assertEqualIterables(data.expectation, pathStrings);
	}

	/** This xpect method can evaluate all common predecessors of two {@link ControlFlowElement}s. */
	@ParameterParser(syntax = "'of' arg1=OFFSET 'and' arg2=OFFSET")
	@Xpect
	public void commonPreds(MethodData data) {
		PATTERN_COMMONPREDS.match(data.getMethodNameWithArgs());
		String of = PATTERN_COMMONPREDS.get("of");
		String and = PATTERN_COMMONPREDS.get("and");
		IEObjectCoveringRegion ocrA = getObjectCoveringRegion(data, of);
		IEObjectCoveringRegion ocrB = getObjectCoveringRegion(data, and);
		List<String> commonPredStrs = xtFlowgraphs.commonPreds(ocrA, ocrB);
		assertEqualIterables(data.expectation, commonPredStrs);
	}

	/** This xpect method can evaluate the control flow container of a given {@link ControlFlowElement}. */
	@ParameterParser(syntax = "('of' arg1=OFFSET)?")
	@Xpect
	public void cfContainer(MethodData data) {
		IEObjectCoveringRegion ocr = getObjectCoveringRegion(data, "cfContainer", "of");
		String containerStr = xtFlowgraphs.cfContainer(ocr);
		assertEquals(data.expectation, containerStr);
	}

	/** This xpect method can evaluate the control flow container of a given {@link ControlFlowElement}. */
	@ParameterParser(syntax = "('of' arg1=OFFSET)?")
	@Xpect
	public void instanceofguard(MethodData data) {
		IEObjectCoveringRegion ocr = getObjectCoveringRegion(data, "instanceofguard", "of");
		List<String> guardStrs = xtFlowgraphs.instanceofguard(ocr);
		assertEqualIterables(data.expectation, guardStrs);
	}

	private Position getPosition(MethodData data, String checkArg1, String optionalDelimiter) {
		int offset = getOffset(data, checkArg1, optionalDelimiter);
		Position position = xtData.getPosition(offset);
		return position;
	}

	private IEObjectCoveringRegion getObjectCoveringRegion(int offset) {
		EObject eObject = XtMethods.getEObject(resource, offset, 0);
		return new EObjectCoveringRegion(eObject, offset);
	}

	private IEObjectCoveringRegion getObjectCoveringRegion(MethodData data, String optionalLocationStr) {
		int offset = getOffset(data, optionalLocationStr);
		EObject eObject = XtMethods.getEObject(resource, offset, 0);
		return new EObjectCoveringRegion(eObject, offset);
	}

	private IEObjectCoveringRegion getObjectCoveringRegion(MethodData data, String checkArg1, String optionalLocation) {
		int offset = getOffset(data, checkArg1, optionalLocation);
		EObject eObject = XtMethods.getEObject(resource, offset, 0);
		return new EObjectCoveringRegion(eObject, offset);
	}

	private int getOffset(MethodData data, String checkArg1, String optionalLocation) {
		Preconditions.checkArgument(data.name.equals(checkArg1));
		String optionalLocationStr = null;
		if (data.args.length > 1) {
			Preconditions.checkArgument(data.args[0].equals(optionalLocation));
			Preconditions.checkArgument(data.args[1].startsWith("'"));
			Preconditions.checkArgument(data.args[1].endsWith("'"));
			optionalLocationStr = data.args[1].substring(1, data.args[1].length() - 1);
		}
		return getOffset(data, optionalLocationStr);
	}

	private int getOffset(MethodData data, String optionalLocationStr) {
		int offset;
		if (optionalLocationStr != null) {
			int relOffset = optionalLocationStr.contains(CURSOR) ? optionalLocationStr.indexOf(CURSOR) : 0;
			String locationStr = optionalLocationStr.replace(CURSOR, "");
			int absOffset = skipCommentsAndWhitespace(xtData.content, locationStr, data.offset);
			offset = absOffset + relOffset;
		} else {
			offset = skipCommentsAndWhitespace(xtData.content, null, data.offset);
		}
		return offset;
	}

	private int skipCommentsAndWhitespace(String content, String str, int startOffset) {
		int offset = skipWhitespace(content, startOffset);

		while (offset < content.length()) {

			if (content.startsWith("//", offset)) {
				offset = minusToMax(content.indexOf("\n", offset));
				offset = skipWhitespace(content, offset);
			} else if (content.startsWith("/*", offset)) {
				offset = minusToMax(content.indexOf("*/", offset));
				offset = skipWhitespace(content, offset);
			} else if (str != null) {
				int oMatch = content.indexOf(str, offset);
				int oSLComment = minusToMax(content.indexOf("//", offset));
				int oMLComment = minusToMax(content.indexOf("/*", offset));
				if (oMatch < oSLComment && oMatch < oMLComment) {
					return oMatch;
				}
				offset = oSLComment < oMLComment ? oSLComment : oMLComment;

			} else {
				return offset;
			}
		}

		return -1;
	}

	private int skipWhitespace(String content, int startOffset) {
		int offset = startOffset - 1;

		while (++offset < content.length()) {
			boolean startsWithWhiteSpace = false;
			startsWithWhiteSpace |= content.startsWith(" ", offset);
			startsWithWhiteSpace |= content.startsWith("\t", offset);
			startsWithWhiteSpace |= content.startsWith("\r", offset);
			startsWithWhiteSpace |= content.startsWith("\n", offset);
			if (!startsWithWhiteSpace) {
				break;
			}
		}

		return minusToMax(offset);
	}

	private int minusToMax(int offset) {
		return offset < 0 ? Integer.MAX_VALUE : offset;
	}

	private void assertEqualIterables(Iterable<?> i1, Iterable<?> i2) {
		assertEquals(Strings.join(", ", i1), Strings.join(", ", i2));
	}

	private <F> void assertEqualIterables(Iterable<String> i1, Iterable<F> i2, Function<F, String> toString) {
		Iterable<String> s2 = Iterables.transform(i2, toString::apply);
		assertEqualIterables(i1, s2);
	}

	private <F> void assertEqualIterables(String s1, Iterable<F> i2, Function<F, String> toString) {
		Iterable<String> i2s = Iterables.transform(i2, toString::apply);
		assertEqualIterables(s1, i2s);
	}

	private void assertEqualIterables(String s1, Iterable<String> i2s) {
		String s2 = Strings.join(", ", i2s);
		assertEquals(s1, s2);
	}
}
