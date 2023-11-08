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

import static org.eclipse.n4js.N4JSGlobals.IMPORT_N4JSGLOBALS;
import static org.eclipse.n4js.N4JSGlobals.OUTPUT_FILE_PREAMBLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.helper.CliTools;
import org.eclipse.n4js.cli.helper.CliTools.CliException;
import org.eclipse.n4js.cli.helper.N4jsLibsAccess;
import org.eclipse.n4js.cli.helper.ProcessResult;
import org.eclipse.n4js.flowgraphs.ControlFlowType;
import org.eclipse.n4js.flowgraphs.analysis.TraverseDirection;
import org.eclipse.n4js.ide.tests.helper.server.AbstractIdeTest;
import org.eclipse.n4js.ide.tests.helper.server.xt.XtMethodPattern.Match;
import org.eclipse.n4js.n4JS.ControlFlowElement;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.scoping.utils.QualifiedNameUtils;
import org.eclipse.n4js.tests.codegen.Folder;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.eclipse.xpect.runner.Xpect;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.XtextResource;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Class that provides / delegates of xt methods
 */
public class XtIdeTest extends AbstractIdeTest {

	static final File TSC_PROVIDER = new File("test-tscProvider");

	static final File TSC2 = new File(TSC_PROVIDER, N4JSGlobals.NODE_MODULES + "/.bin/tsc");
	static final File TSC = new File(TSC_PROVIDER, N4JSGlobals.NODE_MODULES + "/typescript/lib/tsc");

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
			.objOpt("from")
			.textOpt("direction", (Object[]) TraverseDirection.values()).build();

	static final XtMethodPattern PATTERN_ALLPATHS = XtMethodPattern.builder().keyword("allPaths")
			.objOpt("from")
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
	Set<String> suppressedIssues;

	@Override
	public File getProjectRoot() {
		String projectName = xtData.workspace.getProjects().get(0).getName();
		return testWorkspaceManager.getProjectRoot(projectName);
	}

	/**
	 * Call this before calling any other methods of {@link XtIdeTest}.
	 */
	public void initializeXtFile(Set<String> globallySuppressedIssues, XtFileData newXtData) {
		Preconditions.checkNotNull(newXtData);
		xtData = newXtData;

		testWorkspaceManager.createTestOnDisk(xtData.workspace);
		FileURI xtModule = getFileURIFromModuleName(xtData.workspace.moduleNameOfXtFile);

		Set<String> actuallySuppressedIssues = new HashSet<>(globallySuppressedIssues);
		actuallySuppressedIssues.removeAll(xtData.enabledIssues);
		actuallySuppressedIssues.addAll(xtData.disabledIssues);
		setSuppressedIssues(actuallySuppressedIssues);

		for (XtMethodData startupMethod : xtData.startupMethodData) {
			switch (startupMethod.name) {
			case "startAndWaitForLspServer":
				startAndWaitForLspServer();
				break;
			default:
				throw new IllegalArgumentException("Unknown method: " + startupMethod.name);
			}
		}
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

		ArrayList<XtMethodData> issueTests = new ArrayList<>();
		LOOP: for (XtMethodData testMethod : xtData.getTestMethodData()) {
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

		Collection<Diagnostic> issues = getIssuesInFile(xtModule);
		this.issueHelper = new XtMethodsIssues(xtData, issues, issueTests);
	}

	/** Sets the issues that are suppressed regarding issue related xt methods */
	public void setSuppressedIssues(Set<String> suppressedIssues) {
		this.suppressedIssues = suppressedIssues;
	}

	@Override
	protected Set<String> getIgnoredIssueCodes() {
		return suppressedIssues;
	}

	/**
	 * Delegates xt methods found in xt files to their implementations
	 */
	public void invokeTestMethod(XtMethodData testMethodData) throws Exception {
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
		case "ast":
			ast(testMethodData);
			break;
		case "accessModifier":
			accessModifier(testMethodData);
			break;
		case "binding":
			binding(testMethodData);
			break;
		case "compileResult":
			compileResult(testMethodData);
			break;
		case "completion":
			completion(testMethodData);
			break;
		case "definition":
			definition(testMethodData);
			break;
		case "elementKeyword":
			elementKeyword(testMethodData);
			break;
		case "exportedObjects":
			exportedObjects(testMethodData);
			break;
		case "findReferences":
			findReferences(testMethodData);
			break;
		case "formattedLines":
			formattedLines(testMethodData, xtData.preferences);
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
		case "output":
			output(testMethodData);
			break;
		case "generated_dts":
			generated_dts(testMethodData);
			break;
		case "renameRefactoring":
			renameRefactoring(testMethodData);
			break;
		case "scope":
			scope(testMethodData);
			break;
		case "scopeWithResource":
			scopeWithResource(testMethodData);
			break;
		case "scopeWithPosition":
			scopeWithPosition(testMethodData);
			break;
		case "expectedType":
			expectedType(testMethodData);
			break;
		case "type":
			type(testMethodData);
			break;
		case "typeArgs":
			typeArgs(testMethodData);
			break;
		case "typeWithAliasResolution":
			typeWithAliasResolution(testMethodData);
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
		case "typeSwitch":
		case "typeSwitchTypeRef":
		case "version":
			throw new IllegalArgumentException("Unsupported legacy method " + testMethodData.name);
		default:
			throw new IllegalArgumentException("Unknown method: " + testMethodData.name);
		}
	}

	/**
	 * Validates that there are no errors at the given location.
	 *
	 * <pre>
	 * // Xpect noerrors --&gt;
	 * </pre>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void noerrors(XtMethodData data) {
		issueHelper.getNoerrors(data);
	}

	/**
	 * Validates that there are no warnings at the given location.
	 *
	 * <pre>
	 * // Xpect nowarnings --&gt;
	 * </pre>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void nowarnings(XtMethodData data) {
		issueHelper.getNowarnings(data);
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
	public void errors(XtMethodData data) {
		issueHelper.getErrors(data);
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
	public void warnings(XtMethodData data) {
		issueHelper.getWarnings(data);
	}

	/**
	 * Checks the given input against the result of the auto code formatter running on this file
	 *
	 * <pre>
	 * // Xpect ast --&gt; &ltSERIALIZED AST&gt
	 * </pre>
	 */
	@Xpect
	public void ast(XtMethodData data) {
		EObject root = resource.getParseResult().getRootASTElement();
		String ast = xtMethods.serializeAst(root);
		assertEquals(data.expectation, ast);
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
	public void accessModifier(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "accessModifier", "at");
		String accessModifierStr = XtMethods.getAccessModifierString(ocr.getEObject());
		assertEquals(data.expectation, accessModifierStr);
	}

	/**
	 * Calls LSP endpoint 'completion'.
	 *
	 * <pre>
	 * // Xpect completion at '&ltLOCATION&gt' [apply '&ltPROPOSAL&gt'] --&gt; &ltCOMPLETIONS&gt
	 * // Xpect completion at '&ltLOCATION&gt' [contains [not] '&ltPROPOSAL&gt'] --&gt; &ltCOMPLETIONS&gt
	 * </pre>
	 *
	 * {@code apply}, {@code contains}, and {@code not} are optional.<br/>
	 * COMPLETIONS is a comma separated list.<br/>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void completion(XtMethodData data) throws InterruptedException, ExecutionException {
		Position position = eobjProvider.checkAndGetPosition(data, "completion", "at");
		FileURI uri = getFileURIFromModuleName(xtData.workspace.moduleNameOfXtFile);
		String applyId = eobjProvider.checkAndGetArgAfter(data, "completion", "at", "apply");
		String containsNotId = eobjProvider.checkAndGetArgAfter(data, "completion", "at", "contains not");
		String containsId = eobjProvider.checkAndGetArgAfter(data, "completion", "at", "contains");

		ensureOpenFile(xtData.workspace.moduleNameOfXtFile);
		CompletableFuture<Either<List<CompletionItem>, CompletionList>> future = callCompletion(
				uri.toString(), position.getLine(), position.getCharacter());
		Either<List<CompletionItem>, CompletionList> result = future.get();
		List<CompletionItem> items = result.isLeft() ? result.getLeft() : result.getRight().getItems();
		String allItemsStr = Strings.join("\n", item -> item.getLabel(), items);

		if (applyId == null && containsNotId == null && containsId == null) {
			List<String> ciItems = Lists.transform(items, ci -> ci.getLabel());
			assertEqualIterables(data.expectation, ciItems);

		} else if (applyId != null) {
			for (CompletionItem item : items) {
				if (Objects.equal(applyId, item.getFilterText())) {
					ArrayList<TextEdit> list = new ArrayList<>();
					List<TextEdit> addEdits = item.getAdditionalTextEdits();
					if (addEdits != null) {
						list.addAll(addEdits);
					}
					list.add(item.getTextEdit().getLeft());
					String actualSourceAfter = applyTextEdits(xtData.content, list);
					String[] diffRanges = Strings.diffRange(xtData.content, actualSourceAfter, true);
					assertEquals(data.expectationRaw, diffRanges[1].trim());

					return; // test passes
				}
			}
			fail(applyId + " not found in:\n" + allItemsStr);

		} else if (containsNotId != null) {
			for (CompletionItem item : items) {
				if (Objects.equal(containsId, item.getFilterText())) {
					fail(containsNotId + " found in:\n" + allItemsStr);
				}
			}
			return; // test passes

		} else if (containsId != null) {
			for (CompletionItem item : items) {
				if (Objects.equal(containsId, item.getFilterText())) {
					return; // test passes
				}
			}
			fail(containsId + " not found in:\n" + allItemsStr);
		}
	}

	/**
	 * Calls LSP endpoint 'definition'.
	 *
	 * <pre>
	 * // Xpect definition --&gt; &ltFILE AND RANGE&gt
	 * </pre>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void definition(XtMethodData data) throws InterruptedException, ExecutionException {
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
	public void elementKeyword(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "elementKeyword", "at");
		String elementKeywordStr = xtMethods.getElementKeywordString(ocr);
		assertEquals(data.expectation, elementKeywordStr);
	}

	/**
	 * Test all exported objects of a resource description
	 *
	 * <pre>
	 * // Xpect exportedObjects --&gt; &ltEXPORTED OBJECTS&gt
	 * </pre>
	 *
	 * EXPORTED OBJECTS is a comma separated list.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void exportedObjects(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "exportedObjects", null);
		List<String> exportedObjectsArray = xtMethods.getExportedObjectsString(ocr);
		assertEqualIterables(data.expectation, exportedObjectsArray);
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
	public void findReferences(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "findReferences", "at");
		List<String> findReferencesArray = xtMethods.getFindReferences(ocr);
		String expectation = data.expectation.replaceAll("\\s+", " ").replaceAll(",\\s*", ",\n");
		assertEqualIterables(expectation, findReferencesArray);
	}

	/**
	 * Checks the given input against the result of the auto code formatter running on this file
	 *
	 * <pre>
	 * // Xpect formattedLines --&gt; &ltFORMATTED LINES&gt
	 * </pre>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void formattedLines(XtMethodData data, Map<String, String> preferences) {
		String argLines = eobjProvider.checkAndGetArgAfter(data, "formattedLines");
		int lines = Integer.parseInt(argLines);
		xtMethods.getFormattedLines(data.offset, lines, preferences);
		// assertEquals(data.expectation, formattedLines);
		throw new IgnoreTestException();
	}

	/**
	 * <pre>
	 * // Xpect linkedName at '&ltLOCATION&gt' --&gt; &ltTYPE NAME&gt
	 * </pre>
	 */
	@Xpect
	public void linkedName(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "linkedName", "at");
		QualifiedName linkedName = xtMethods.getLinkedName(ocr);
		assertEquals(data.expectation, QualifiedNameUtils.toHumanReadableString(linkedName));
	}

	/**
	 * <pre>
	 * // Xpect linkedFragment at '&ltLOCATION&gt' --&gt; &ltFRAGMENT NAME&gt
	 * </pre>
	 */
	@Xpect
	public void linkedFragment(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "linkedFragment", "at");
		String fragmentName = xtMethods.getLinkedFragment(ocr);
		assertEquals(data.expectation, fragmentName);
	}

	/**
	 * Similar to {@link #linkedName(XtMethodData)} but concatenating the fully qualified name again instead of using
	 * the qualified name provider, as the latter may not create a valid name for non-globally available elements.
	 * <p>
	 * The qualified name created by retrieving all "name" properties of the target and its containers, using '/' as
	 * separator.
	 *
	 * <pre>
	 * // Xpect linkedPathname at '&ltLOCATION&gt' --&gt; &ltPATH NAME&gt
	 * </pre>
	 */
	@Xpect
	public void linkedPathname(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "linkedPathname", "at");
		String pathName = xtMethods.getLinkedPathname(ocr);
		assertEquals(data.expectation, pathName);
	}

	/**
	 * Performs a rename refactoring at a given location. Usage:
	 *
	 * <pre>
	 * // Xpect renameRefactoring at '&ltOLD_NAME&gt' to '&ltNEW_NAME&gt' resource '&ltRESOURCE_NAME&gt' --&gt;
	 * // &ltNEWCODE_OR_PROBLEMS&gt
	 * </pre>
	 *
	 * {@code resource} is optional.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void renameRefactoring(XtMethodData data) throws InterruptedException, ExecutionException {
		FileURI uri = getFileURIFromModuleName(xtData.workspace.moduleNameOfXtFile);
		Position pos = eobjProvider.checkAndGetPosition(data, "renameRefactoring", "at");
		String newName = eobjProvider.checkAndGetArgAfter(data, "renameRefactoring", "at", "to");
		Preconditions.checkState(newName != null);
		String optResource = eobjProvider.checkAndGetArgAfter(data, "renameRefactoring", "at", "to", "resource");
		if (com.google.common.base.Strings.isNullOrEmpty(optResource)) {
			optResource = uri.toString();
		}
		FileURI assertResource = getFileURIFromModuleName(optResource);

		try {
			WorkspaceEdit workspaceEdit = callRename(uri.toString(), pos.getLine(), pos.getCharacter(), newName);
			if (workspaceEdit == null) {
				fail("element cannot be renamed");
				return;
			}

			Map<FileURI, String> fileURI2ActualSourceBefore = new LinkedHashMap<>();
			Map<String, Map<String, String>> projectsModulesSourcesBefore = new LinkedHashMap<>();
			for (Project prj : xtData.workspace.getAllProjects()) {
				Map<String, String> modulesSourcesBefore = new LinkedHashMap<>();
				projectsModulesSourcesBefore.put(prj.getName(), modulesSourcesBefore);

				for (Folder srcFld : prj.getSourceFolders()) {
					for (org.eclipse.n4js.tests.codegen.Module mdl : srcFld.getModules()) {
						String moduleName = mdl.getName();
						String contents = mdl.getContents();
						modulesSourcesBefore.put(moduleName, contents);
						fileURI2ActualSourceBefore.put(getFileURIFromModuleName(moduleName), contents);
					}
				}
			}

			Set<FileURI> unknownURIs = new LinkedHashSet<>();
			Map<FileURI, String> fileURI2ActualSourceAfter = applyWorkspaceEdit(projectsModulesSourcesBefore,
					workspaceEdit, unknownURIs);

			if (!unknownURIs.isEmpty()) {
				fail("rename led to text edits in unknown URIs: " + Joiner.on(", ").join(unknownURIs));
			}

			String XPCT_PATTERN = "\\/\\*\\s+XPECT.+---(.|\\s)+---\\s*\\*\\/";

			for (FileURI file : fileURI2ActualSourceAfter.keySet()) {
				if (Objects.equal(assertResource, file)) {
					String contentsBefore = fileURI2ActualSourceBefore.get(file).replaceAll(XPCT_PATTERN, "");
					String contentsAfter = fileURI2ActualSourceAfter.get(file).replaceAll(XPCT_PATTERN, "");

					String[] diffRanges = Strings.diffRange(contentsBefore, contentsAfter, true);
					assertEquals(data.expectation, diffRanges[1].trim());
				}
			}

		} catch (ExecutionException ee) {
			if (ee.getCause() instanceof ResponseErrorException) {
				ResponseErrorException ree = (ResponseErrorException) ee.getCause();
				ResponseError re = ree.getResponseError();
				String errMsg = re.getCode() + ": " + re.getMessage();
				assertEquals(data.expectation, errMsg);
			} else {
				throw ee;
			}
		}
	}

	/**
	 * Compiles the current xt file and compares the generated file to the expected output
	 *
	 * <pre>
	 * // Xpect compileResult --&gt; &ltCOMPILE RESULT&gt
	 * </pre>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void compileResult(XtMethodData data) {
		FileURI fileUri = getGeneratedFileURI();
		String generedFileContent = getContentOfFileOnDisk(fileUri);
		assertEquals(data.expectationRaw, generedFileContent.trim());
	}

	/**
	 * Compiles and executes the current xt file and compares the output to the expected output
	 *
	 * <pre>
	 * // Xpect output --&gt; &ltOUTPUT&gt
	 * </pre>
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void output(XtMethodData data) {
		FileURI fileUri = getGeneratedFileURI();

		installN4JSRuntime();
		assertOutput(fileUri, data.expectationRaw);
	}

	private FileURI getGeneratedFileURI() {
		String moduleName = xtData.workspace.moduleNameOfXtFile;
		int idxStart = Math.max(moduleName.lastIndexOf("/") + 1, 0);
		int idxEnd = moduleName.lastIndexOf(".");
		String genModuleName = "src-gen/" + moduleName.substring(idxStart, idxEnd) + ".js";
		FileURI fileUri = getFileURIFromModuleName(genModuleName);
		return fileUri;
	}

	/**
	 * Compares the content of the generated .d.ts file with the expectation string.
	 *
	 * <pre>
	 * // XPECT generated_dts ---
	 * &lt;EXPECTED CONTENT OF GENERATED D.TS FILE&gt;
	 * // ---
	 * </pre>
	 */
	@Xpect
	public void generated_dts(XtMethodData data) throws IOException {
		String moduleName = xtData.workspace.moduleNameOfXtFile;
		int idxStart = Math.max(moduleName.lastIndexOf("/") + 1, 0);
		int idxEnd = moduleName.lastIndexOf(".");
		String genDtsFileName = moduleName.substring(idxStart, idxEnd) + ".d.ts";

		try {
			FileURI genDtsFileURI = getFileURIFromModuleName(genDtsFileName);
			String genDtsCode = Files.readString(genDtsFileURI.toPath());
			assertTrue(genDtsCode.startsWith(OUTPUT_FILE_PREAMBLE));
			String genDtsCodeTrimmedPreamble = genDtsCode.substring(OUTPUT_FILE_PREAMBLE.length()).trim();
			assertTrue(genDtsCodeTrimmedPreamble.startsWith(IMPORT_N4JSGLOBALS));
			String genDtsCodeTrimmed = genDtsCodeTrimmedPreamble.substring(IMPORT_N4JSGLOBALS.length()).trim();

			assertEquals(data.getUnescapeExpectationRaw(), genDtsCodeTrimmed);

			CliTools cliTools = new CliTools();
			ensureTSC(cliTools);

			List<Project> allProjectsWithGenerateDts = FluentIterable.from(xtData.workspace.getAllProjects())
					.filter(Project::isGenerateDts)
					.toList();
			assertFalse("no projects found with .d.ts generation turned on", allProjectsWithGenerateDts.isEmpty());

			for (Project project : allProjectsWithGenerateDts) {
				File workingDir = getProjectRoot(project.getName());

				// copy n4jsglobals.d.ts to output dir to make d.ts globals available
				Path n4jsGlobalsDTS = N4jsLibsAccess.getN4JSGlobalsDTS();
				Files.copy(n4jsGlobalsDTS, workingDir.toPath().resolve("src-gen/n4jsglobals.d.ts"));

				ProcessResult result;
				try {
					result = cliTools.nodejsRun(workingDir.toPath(), TSC2.getAbsoluteFile().toPath());
				} catch (CliException e) {
					throw new AssertionError("error while running tsc in working directory: " + workingDir, e);
				}
				assertFalse("TypeScript Error: " + result.getStdOut(), result.getStdOut().contains(": error "));
			}

		} catch (IllegalStateException e) {
			throw new RuntimeException("Could not find file " + genDtsFileName + "\nDid you set: "
					+ XtSetupParser.GENERATE_DTS + " in SETUP section?", e);
		}
	}

	private void ensureTSC(CliTools cliTools) {
		if (TSC_PROVIDER.isDirectory() && TSC2.isFile()) {
			return;
		}

		if (TSC_PROVIDER.exists()) {
			TSC_PROVIDER.delete();
		}
		TSC_PROVIDER.mkdirs();

		// npm install --prefix . typescript@4.3.2
		ProcessResult result = cliTools.npmRun(TSC_PROVIDER.toPath(), "install", "--prefix", ".", "typescript@4.3.2");

		assertEquals(0, result.getExitCode());
	}

	/**
	 * Checks that a given element is bound to something identified by (simple) qualified name. The given expression is
	 * tested, and within that we expect a property access or a direct identifiable element. The compared name is the
	 * simple qualified name, that is container (type) followed by elements name, without URIs of modules etc. Usage:
	 *
	 * <pre>
	 * // Xpect binding at '&ltLOCATION&gt' --&gt; &ltQUALIFIED NAME&gt
	 * </pre>
	 *
	 * The location is optional.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void binding(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "binding", "at");
		String bindingName = xtMethods.getBindingName(ocr);
		assertEquals(data.expectation, bindingName);
	}

	/**
	 * Checks the scope at a given location. Usage:
	 *
	 * <pre>
	 * // Xpect scope at '&ltLOCATION&gt' --&gt; &ltSCOPES&gt
	 * </pre>
	 *
	 * The location is optional.
	 *
	 * SCOPES is a comma separated list.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void scope(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "scope", "at");
		Set<String> scopeStr = xtMethods.getScopeString(ocr);
		assertEqualIterables(data.expectation, scopeStr);
	}

	/**
	 * Checks the elements and their resources/positions of the scope at a given location. Usage:
	 *
	 * <pre>
	 * // Xpect scopeWithPosition at '&ltLOCATION&gt' --&gt; &ltSCOPES WITH RESOURCE AND POSITION&gt
	 * </pre>
	 *
	 * The location is optional.
	 *
	 * SCOPES WITH RESOURCE AND POSITION is a comma separated list.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void scopeWithPosition(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "scopeWithPosition", "at");
		Set<String> scopeStr = xtMethods.getScopeWithPositionString(ocr);
		assertEqualIterables(data.expectation, scopeStr);
	}

	/**
	 * Checks the elements and their resources of the scope at a given location. Usage:
	 *
	 * <pre>
	 * // Xpect scopeWithResource at '&ltLOCATION&gt' --&gt; &ltSCOPES WITH RESOURCE&gt
	 * </pre>
	 *
	 * The location is optional.
	 *
	 * SCOPES WITH RESOURCE is a comma separated list.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void scopeWithResource(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "scopeWithResource", "at");
		Set<String> scopeStr = xtMethods.getScopeWithResourceString(ocr);
		assertEqualIterables(data.expectation, scopeStr);
	}

	/**
	 * Checks that an element/expression has a certain expected type (i.e. judgment expectedTypeIn). Usage:
	 *
	 * <pre>
	 * // Xpect expectedType at '&ltLOCATION&gt' --&gt; &ltTYPE&gt
	 * </pre>
	 *
	 * The location is optional.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void expectedType(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "expectedType", "at");
		String typeStr = xtMethods.getTypeString(ocr.getEObject(), true, false);
		assertEquals(data.expectation, typeStr);
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
	public void type(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "type", "of");
		String typeStr = xtMethods.getTypeString(ocr.getEObject(), false, false);
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
	public void typeArgs(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "typeArgs", "of");
		String typeArgStr = xtMethods.getTypeArgumentsString(ocr.getEObject());
		assertEquals(data.expectation, typeArgStr);
	}

	/**
	 * Same as {œcode type}, but includes resolution of type aliases.Usage:
	 *
	 * <pre>
	 * // Xpect typeWithAliasResolution of '&ltLOCATION&gt' --&gt; &ltTYPE&gt
	 * </pre>
	 *
	 * The location is optional.
	 */
	@Xpect // NOTE: This annotation is used only to enable validation and navigation of .xt files.
	public void typeWithAliasResolution(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "typeWithAliasResolution",
				"of");
		String typeStr = xtMethods.getTypeString(ocr.getEObject(), false, true);
		assertEquals(data.expectation, typeStr);
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
	public void allBranches(XtMethodData data) {
		Match match = PATTERN_ALLBRANCHES.match(data, eobjProvider);
		String direction = match.getText("direction");
		IEObjectCoveringRegion ocrReference = match.ocrReference;
		IEObjectCoveringRegion ocrFrom = match.getEObjectWithOffset("from");
		List<String> branchStrings = xtFlowgraphs.getAllBranches(ocrFrom, direction, ocrReference);
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
	public void allEdges(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "allEdges", "from");
		List<String> pathStrings = xtFlowgraphs.getAllEdges(ocr);
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
	public void allMergeBranches(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "allMergeBranches", null);
		List<String> edgeStrings = xtFlowgraphs.getAllMergeBranches(ocr);
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
	public void allPaths(XtMethodData data) {
		Match match = PATTERN_ALLPATHS.match(data, eobjProvider);
		String direction = match.getText("direction");
		IEObjectCoveringRegion ocrReference = match.ocrReference;
		IEObjectCoveringRegion ocrFrom = match.getEObjectWithOffset("from");
		List<String> pathStrings = xtFlowgraphs.getAllPaths(ocrFrom, direction, ocrReference);
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
	public void astOrder(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "astOrder", "of");
		List<String> astElements = xtFlowgraphs.getAstOrder(ocr);
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
	public void cfContainer(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "cfContainer", "of");
		String containerStr = xtFlowgraphs.getCfContainer(ocr);
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
	public void commonPreds(XtMethodData data) {
		Match match = PATTERN_COMMONPREDS.match(data, eobjProvider);
		IEObjectCoveringRegion ocrA = match.getEObjectWithOffset("of");
		IEObjectCoveringRegion ocrB = match.getEObjectWithOffset("and");
		List<String> commonPredStrs = xtFlowgraphs.getCommonPreds(ocrA, ocrB);
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
	public void instanceofguard(XtMethodData data) {
		IEObjectCoveringRegion ocr = eobjProvider.checkAndGetObjectCoveringRegion(data, "instanceofguard", "of");
		List<String> guardStrs = xtFlowgraphs.getInstanceofguard(ocr);
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
	public void path(XtMethodData data) {
		Match match = PATTERN_PATH.match(data, eobjProvider);
		IEObjectCoveringRegion ocrReference = match.ocrReference;
		IEObjectCoveringRegion ocrFrom = match.getEObjectWithOffset("from");
		IEObjectCoveringRegion ocrTo = match.getEObjectWithOffset("to");
		IEObjectCoveringRegion ocrNotTo = match.getEObjectWithOffset("notTo");
		IEObjectCoveringRegion ocrVia = match.getEObjectWithOffset("via");
		IEObjectCoveringRegion ocrNotVia = match.getEObjectWithOffset("notVia");

		assertTrue("Either 'to' or 'notTo' must be defined.", ocrTo != null || ocrNotTo != null);

		// No test assertions here: fails internally iff test fails
		xtFlowgraphs.getPath(ocrFrom, ocrTo, ocrNotTo, ocrVia, ocrNotVia, ocrReference);
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
	public void preds(XtMethodData data) {
		Match match = PATTERN_PREDS.match(data, eobjProvider);
		String type = match.getText("type");
		IEObjectCoveringRegion ocrAt = match.getEObjectWithOffset("at");
		List<String> predTexts = xtFlowgraphs.getPreds(type, ocrAt);
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
	public void succs(XtMethodData data) {
		Match match = PATTERN_SUCCS.match(data, eobjProvider);
		String type = match.getText("type");
		IEObjectCoveringRegion ocrAt = match.getEObjectWithOffset("at");
		List<String> succTexts = xtFlowgraphs.getSuccs(type, ocrAt);
		assertEqualIterables(data.expectation, succTexts);
	}

	private void assertEqualIterables(String expectation, Iterable<String> i2s) {
		List<List<String>> alts = new ArrayList<>();
		for (String s2 : i2s) {
			alts.add(Lists.newArrayList(s2));
		}
		assertEqualIterablesAlts(expectation, alts, true);
	}

	@SuppressWarnings("unused")
	private void assertEqualIterablesAlts(String expectation, Iterable<List<String>> i2s) {
		assertEqualIterablesAlts(expectation, i2s, true);
	}

	private void assertEqualIterablesAlts(String expectation, Iterable<List<String>> i2s, boolean replaceEmptySpace) {
		String[] elems1 = expectation.split("(?:\\s+|(?<=[^\\\\])),\\s*");
		for (int i = 0; i < elems1.length; i++) {
			elems1[i] = elems1[i].replace("\\,", ",");
			elems1[i] = elems1[i].replace("\\n", "\n");
			elems1[i] = elems1[i].replaceAll("\\s+", " ");
			elems1[i] = elems1[i].trim();
		}
		Set<String> expectElems = Sets.newHashSet(elems1);
		Set<String> expectMissingElems = new HashSet<>();
		for (Iterator<String> iter = expectElems.iterator(); iter.hasNext();) {
			String elem = iter.next();
			if (elem.startsWith("!")) {
				iter.remove();
				expectMissingElems.add(elem.substring(1).trim());
			}
		}
		boolean partialExpectation = elems1.length > 0
				&& (expectElems.contains("...") || !expectMissingElems.isEmpty());
		expectElems.remove("...");

		boolean hasAlts = false;
		List<String> noAlts2s = new ArrayList<>();
		Set<String> allAlts2s = new HashSet<>();
		for (List<String> alts : i2s) {
			hasAlts |= alts.size() > 1;

			if (replaceEmptySpace) {
				for (int idx = 0; idx < alts.size(); idx++) {
					String trimmedS = alts.get(idx).replaceAll("\\s+", " ").trim();
					alts.set(idx, trimmedS);
				}
			}
			noAlts2s.add(alts.get(0));
			allAlts2s.addAll(alts);
		}

		if (partialExpectation) {
			expectElems.removeAll(allAlts2s);
			expectMissingElems.retainAll(allAlts2s);

			assertTrue("Not found:\n    " + Strings.join("\n    ", expectElems) + "\n"
					+ "Among these actual elements:\n    " + Strings.join("\n    ", noAlts2s), expectElems.isEmpty());

			assertTrue("Expected missing, but found: " + Strings.join(", ", expectMissingElems),
					expectMissingElems.isEmpty());

		} else if (!hasAlts) {
			List<String> sorted1 = Lists.newArrayList(elems1);
			Collections.sort(sorted1);

			List<String> sorted2 = Lists.newArrayList(noAlts2s);
			Collections.sort(sorted2);
			String s1sorted = Strings.join(", ", sorted1);
			String s2sorted = Strings.join(", ", sorted2);
			assertEquals(s1sorted, s2sorted);
		} else {
			// !partialExpectation && hasAlts

			Set<String> remainingActuals = new HashSet<>(noAlts2s);
			Set<String> remainingExpects = new HashSet<>(expectElems);
			for (String expect : expectElems) {
				for (List<String> alts : i2s) {
					for (String alt : alts) {
						if (Objects.equal(alt, expect)) {
							remainingActuals.removeAll(alts);
							remainingExpects.remove(expect);
							break;
						}
					}
				}
			}
			assertTrue("Not found: \n    " + Strings.join("\n    ", remainingActuals) + "\n" + "\n"
					+ "Among these actual elements:\n    " + Strings.join("\n    ", noAlts2s),
					remainingActuals.isEmpty());

			assertTrue("Expected, but not found found: " + Strings.join(", ", remainingExpects),
					remainingExpects.isEmpty());
		}
	}

}
