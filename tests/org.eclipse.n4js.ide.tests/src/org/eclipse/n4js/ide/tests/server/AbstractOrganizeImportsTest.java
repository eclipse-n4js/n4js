/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.tests.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionContext;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.N4JSLanguageConstants;
import org.eclipse.n4js.ide.server.commands.N4JSCommandService;
import org.eclipse.n4js.ide.tests.server.AbstractOrganizeImportsTest.TestOrganizeImportsConfiguration;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.tests.codegen.Project;
import org.eclipse.n4js.utils.Strings;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.Assert;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Abstract base class for organize imports IDE tests.
 */
public abstract class AbstractOrganizeImportsTest extends AbstractStructuredIdeTest<TestOrganizeImportsConfiguration> {

	// NOTE: to test removal of unused/duplicate imports, etc. we here ignore quite a few imports-related issues:
	private final Set<String> IGNORED_ISSUE_CODES = Sets.newHashSet(Iterables.concat(
			N4JSLanguageConstants.DEFAULT_SUPPRESSED_ISSUE_CODES_FOR_TESTS,
			Sets.newHashSet(
					IssueCodes.IMP_DUPLICATE,
					IssueCodes.IMP_DUPLICATE_ALIAS,
					IssueCodes.IMP_DUPLICATE_NAMESPACE,
					IssueCodes.IMP_STMT_DUPLICATE_NAMED,
					IssueCodes.IMP_STMT_DUPLICATE_NAMESPACE,
					IssueCodes.IMP_AMBIGUOUS,
					IssueCodes.IMP_UNUSED_IMPORT,
					IssueCodes.IMP_UNRESOLVED)));

	/** Some default modules that export a number of classes, for use in the main module of organize imports tests. */
	protected final List<Pair<String, String>> defaultOrganizeImportsTestModules = Lists.newArrayList(
			Pair.of("A", Strings.fromLines(
					"export class A01 {}",
					"export class A02 {}",
					"export class A03 {}",
					"export class A04 {}")),
			Pair.of("B", Strings.fromLines(
					"export class B01 {}",
					"export class B02 {}",
					"export class B03 {}",
					"export class B04 {}")),
			Pair.of("C", Strings.fromLines(
					"export class C01 {}",
					"export class C02 {}",
					"export class C03 {}",
					"export class C04 {}")),
			Pair.of("Def", Strings.fromLines(
					"export class Def01 {}",
					"export class Def02 {}",
					"export class Def03 {}",
					"export class Def04 {}",
					"export default class DefCls {}")));

	/** Configuration of organize imports tests. */
	protected static class TestOrganizeImportsConfiguration {
		/** Issues expected before(!) organize imports is performed. */
		public final List<String> expectedIssues;
		/** Expected source code after(!) organize imports is performed. */
		public final CharSequence expectedCode;

		/** Creates an instance. */
		public TestOrganizeImportsConfiguration(List<String> expectedIssues, CharSequence expectedCode) {
			this.expectedIssues = expectedIssues;
			this.expectedCode = expectedCode;
		}
	}

	@Override
	protected Set<String> getIgnoredIssueCodes() {
		return IGNORED_ISSUE_CODES;
	}

	/** Same as {@link #test(CharSequence, List, CharSequence)}, but without expected issues. */
	protected void test(CharSequence code, CharSequence expectedCode) {
		test(code, Collections.emptyList(), expectedCode);
	}

	/**
	 * Test method for organize imports tests using the {@link #defaultOrganizeImportsTestModules}.
	 *
	 * @param code
	 *            source code before organize imports is performed.
	 * @param expectedIssues
	 *            expected issues in source code before organize imports is performed.
	 * @param expectedCode
	 *            expected source code after organize imports was performed.
	 */
	protected void test(CharSequence code, List<String> expectedIssues, CharSequence expectedCode) {
		String nameWithSelector = DEFAULT_MODULE_NAME + MODULE_SELECTOR;
		ArrayList<Pair<String, String>> allModule = Lists.newArrayList(Pair.of(nameWithSelector, code.toString()));
		allModule.addAll(defaultOrganizeImportsTestModules);
		test(allModule, new TestOrganizeImportsConfiguration(expectedIssues, expectedCode));
	}

	@Override
	protected void performTest(Project project, String moduleName, TestOrganizeImportsConfiguration config)
			throws Exception {
		FileURI uri = getFileURIFromModuleName(moduleName);

		if (config.expectedIssues.isEmpty()) {
			assertNoIssues();
		} else {
			assertIssues(Collections.singletonMap(uri, config.expectedIssues));
		}

		TextDocumentIdentifier id = new TextDocumentIdentifier(uri.toString());
		Range range = new Range(new Position(0, 0), new Position(0, 0));
		CodeActionContext context = new CodeActionContext();
		CodeActionParams params = new CodeActionParams(id, range, context);
		CompletableFuture<List<Either<Command, CodeAction>>> codeActionFuture = languageServer.codeAction(params);

		List<Either<Command, CodeAction>> result = codeActionFuture.join();
		Command organizeImportsCommand = result.stream()
				.map(e -> e.isLeft() ? e.getLeft() : e.getRight().getCommand())
				.filter(cmd -> cmd != null
						&& Objects.equals(cmd.getCommand(), N4JSCommandService.N4JS_ORGANIZE_IMPORTS))
				.findFirst().orElse(null);
		Assert.assertNotNull("code action for organize imports not found", organizeImportsCommand);

		ExecuteCommandParams execParams = new ExecuteCommandParams(
				organizeImportsCommand.getCommand(),
				organizeImportsCommand.getArguments());
		CompletableFuture<Object> execFuture = languageServer.executeCommand(execParams);
		execFuture.join();

		joinServerRequests();
		assertContentOfFileOnDisk(uri, config.expectedCode);
	}
}
