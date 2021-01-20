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
package org.eclipse.n4js.ide.server.codeActions;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionContext;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.server.commands.N4JSCommandService;
import org.eclipse.n4js.ide.xtext.server.ResourceTaskManager;
import org.eclipse.n4js.ide.xtext.server.TextDocumentFrontend;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.ide.xtext.server.issues.LSPIssueToLSPDiagnosticConverter;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.service.OperationCanceledManager;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Infrastructure for LSP code actions
 */
@SuppressWarnings({ "restriction", "deprecation" })
@Singleton
public class N4JSCodeActionService implements ICodeActionService2 {

	final Class<?>[] quickfixProviders = { N4JSQuickfixProvider.class };
	final Class<?>[] sourceActionProviders = { N4JSSourceActionProvider.class };

	private static class TextEditCollector implements ICodeActionAcceptor {
		private final Map<String, List<TextEdit>> allEdits;

		private TextEditCollector() {
			this.allEdits = new HashMap<>();
		}

		@Override
		public void acceptQuickfixCommand(QuickfixContext context, String title, String commandID,
				Object... arguments) {

			throw new UnsupportedOperationException(
					"TODO implement composite post-text-edit-apply actions if necessary");
		}

		@Override
		public void acceptQuickfixCodeAction(QuickfixContext context, String title, List<TextEdit> textEdits) {
			String uriString = context.options.getCodeActionParams().getTextDocument().getUri();
			allEdits.computeIfAbsent(uriString, ignore -> new ArrayList<>()).addAll(textEdits);
		}

		@Override
		public void acceptSourceAction(String title, String kind, String commandId, Object... arguments) {
			throw new IllegalStateException(
					"cannot create source actions with code action acceptor " + TextEditCollector.class);
		}
	}

	private static abstract class CodeActionImplementation {
		final Object instance;
		final Method method;
		final String id;

		private CodeActionImplementation(Object instance, Method method) {
			this.instance = instance;
			this.method = method;
			this.id = method.getName();
		}
	}

	private static class QuickFixImplementation extends CodeActionImplementation {
		final boolean multiFix;

		private QuickFixImplementation(Object instance, final Method method, boolean multiFix) {
			super(instance, method);
			this.multiFix = multiFix;
		}

		void compute(String code, Options options, ICodeActionAcceptor acceptor) {
			XtextResource resource = options.getResource();
			if (!(resource instanceof N4JSResource)) {
				return;
			}

			QuickfixContext context = new QuickfixContext((N4JSResource) resource, code, options);

			try {
				method.invoke(instance, context, acceptor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static class SourceActionImplementation extends CodeActionImplementation {

		private SourceActionImplementation(Object instance, Method method) {
			super(instance, method);
		}

		void compute(Options options, ICodeActionAcceptor acceptor) {
			CodeActionParams params = options.getCodeActionParams();

			try {
				method.invoke(instance, params, acceptor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static class MultiQuickfixAcceptor implements ICodeActionAcceptor {
		final String quickfixId;
		final CodeActionAcceptor acceptor;

		private MultiQuickfixAcceptor(String quickfixId, CodeActionAcceptor acceptor) {
			this.quickfixId = quickfixId;
			this.acceptor = acceptor;
		}

		@Override
		public void acceptQuickfixCodeAction(QuickfixContext context, String title, List<TextEdit> textEdits) {
			acceptor.acceptQuickfixCodeAction(context, title, textEdits);
			acceptor.acceptQuickfixCommand(context, title + " (entire file)",
					N4JSCommandService.COMPOSITE_FIX_FILE,
					title,
					context.issueCode,
					quickfixId,
					context.options.getCodeActionParams());
			acceptor.acceptQuickfixCommand(context, title + " (entire project)",
					N4JSCommandService.COMPOSITE_FIX_PROJECT,
					title,
					context.issueCode,
					quickfixId,
					context.options.getCodeActionParams());
		}

		@Override
		public void acceptQuickfixCommand(QuickfixContext context, String title, String commandID,
				Object... arguments) {

			acceptor.acceptQuickfixCommand(context, title, commandID, arguments);
		}

		@Override
		public void acceptSourceAction(String title, String kind, String commandId, Object... arguments) {
			throw new IllegalStateException(
					"cannot create source actions with code action acceptor " + MultiQuickfixAcceptor.class);
		}
	}

	@Inject
	private XLanguageServerImpl languageServer;

	@Inject
	private TextDocumentFrontend textDocumentFrontend;

	@Inject
	private LSPIssueToLSPDiagnosticConverter diagnosticIssueConverter;

	@Inject
	private IN4JSCore n4jsCore;

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private OperationCanceledManager cancelManager;

	private final Multimap<String, QuickFixImplementation> quickfixMap = HashMultimap.create();
	private final List<SourceActionImplementation> sourceActions = new ArrayList<>();

	/**
	 * Using reflection, all classes of {@link #quickfixProviders} are inspected to find all methods that are annotated
	 * with {@link Fix}.
	 */
	@Inject
	public void init(Injector injector) {
		for (Class<?> quickFixProvider : quickfixProviders) {
			Object qpInstance = injector.getInstance(quickFixProvider);
			Method[] methods = quickFixProvider.getMethods();
			for (Method method : methods) {
				int modifiers = method.getModifiers();
				if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
					Fixes fixes = method.getAnnotation(Fixes.class);
					if (fixes != null) {
						for (Fix fix : fixes.value()) {
							acceptFix(fix, qpInstance, method);
						}
					}
					Fix fix = method.getAnnotation(Fix.class);
					if (fix != null) {
						acceptFix(fix, qpInstance, method);
					}
				}
			}
		}
		for (Class<?> sourceActionProvider : sourceActionProviders) {
			Object providerInstance = injector.getInstance(sourceActionProvider);
			Method[] methods = sourceActionProvider.getMethods();
			for (Method method : methods) {
				int modifiers = method.getModifiers();
				if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
					SourceAction ann = method.getAnnotation(SourceAction.class);
					if (ann != null) {
						sourceActions.add(new SourceActionImplementation(providerInstance, method));
					}
				}
			}
		}
	}

	private void acceptFix(Fix fix, Object instance, Method method) {
		String issueCode = fix.value();
		if (!Strings.isNullOrEmpty(issueCode)) {
			QuickFixImplementation impl = new QuickFixImplementation(instance, method, fix.multiFix());
			quickfixMap.put(issueCode, impl);
		}
	}

	@Override
	public List<Either<Command, CodeAction>> getCodeActions(Options options) {
		CodeActionAcceptor acceptor = new CodeActionAcceptor();

		List<Diagnostic> diagnostics = null;
		if (options.getCodeActionParams() != null && options.getCodeActionParams().getContext() != null) {
			diagnostics = options.getCodeActionParams().getContext().getDiagnostics();
		}
		if (diagnostics == null) {
			diagnostics = Collections.emptyList();
		}

		for (Diagnostic diag : diagnostics) {
			cancelManager.checkCanceled(options.getCancelIndicator());
			findQuickfixes(diag.getCode(), options, acceptor);
		}

		findSourceActions(options, acceptor);

		cancelManager.checkCanceled(options.getCancelIndicator());
		return acceptor.getList();
	}

	/** Finds quick-fixes for the given issue code and adds these quick-fixes to the acceptor iff available. */
	public void findQuickfixes(String code, Options options, CodeActionAcceptor acceptor) {
		for (QuickFixImplementation qfix : quickfixMap.get(code)) {
			ICodeActionAcceptor accTmp = qfix.multiFix ? new MultiQuickfixAcceptor(qfix.id, acceptor) : acceptor;
			qfix.compute(code, options, accTmp);
		}
	}

	private void findSourceActions(Options options, CodeActionAcceptor acceptor) {
		for (SourceActionImplementation impl : sourceActions) {
			cancelManager.checkCanceled(options.getCancelIndicator());
			impl.compute(options, acceptor);
		}
	}

	/**
	 * Applies all fixes of the same kind to the file with the given URI.
	 */
	public WorkspaceEdit applyToFile(URI uri, String issueCode, String fixId, CancelIndicator cancelIndicator) {

		WorkspaceEdit result = new WorkspaceEdit();
		QuickFixImplementation quickfix = findOriginatingQuickfix(issueCode, fixId);
		if (quickfix == null) {
			return result;
		}

		Map<String, List<TextEdit>> edits = doApplyToFile(uri, issueCode, quickfix, cancelIndicator);
		result.setChanges(edits);
		return result;
	}

	/**
	 * Applies all fixes of the same kind to the project containing the given URI.
	 */
	public WorkspaceEdit applyToProject(URI uri, String issueCode, String fixId, CancelIndicator cancelIndicator) {

		WorkspaceEdit result = new WorkspaceEdit();
		QuickFixImplementation quickfix = findOriginatingQuickfix(issueCode, fixId);
		if (quickfix == null) {
			return result;
		}

		Optional<? extends IN4JSProject> project = n4jsCore.findProject(uri);
		if (!project.isPresent()) {
			return result;
		}
		List<URI> urisInProject = Lists.newArrayList(
				IterableExtensions.flatMap(project.get().getSourceContainers(), sc -> sc));

		Map<String, List<TextEdit>> allEdits = new HashMap<>();
		for (URI currURI : urisInProject) {
			Map<String, List<TextEdit>> edits = doApplyToFile(currURI, issueCode, quickfix, cancelIndicator);
			allEdits.putAll(edits);
		}
		result.setChanges(allEdits);
		return result;
	}

	/** Applies given quick fix to file with given URI and waits for and returns the resulting edits. */
	protected Map<String, List<TextEdit>> doApplyToFile(URI uri, String issueCode, QuickFixImplementation quickfix,
			CancelIndicator cancelIndicator) {

		TextEditCollector collector = new TextEditCollector();
		TextDocumentIdentifier textDocId = new TextDocumentIdentifier(uriExtensions.toUriString(uri));

		ResourceTaskManager resourceTaskManager = languageServer.getResourceTaskManager();
		resourceTaskManager.<Void> runInTemporaryContext(uri, "doApplyToFile", false, cancelIndicator, (ofc, ci) -> {
			XtextResource res = ofc.getResource();
			List<? extends LSPIssue> issues = ofc.resolveAndValidateResource(ci);

			XDocument doc = ofc.getDocument();
			for (LSPIssue issue : issues) {
				if (issueCode.equals(issue.getCode())) {
					Options newOptions = createOptions(res, doc, textDocId, issue, ci);
					quickfix.compute(issueCode, newOptions, collector);
				}
			}
			return null;
		}).join();

		return collector.allEdits;
	}

	private Options createOptions(XtextResource res, XDocument doc, TextDocumentIdentifier docIdentifier,
			LSPIssue issue, CancelIndicator cancelIndicator) {
		Diagnostic diagnostic = diagnosticIssueConverter.toDiagnostic(issue);
		CodeActionContext context = new CodeActionContext(Collections.singletonList(diagnostic));
		CodeActionParams codeActionParams = new CodeActionParams(docIdentifier, diagnostic.getRange(), context);
		Options newOptions = textDocumentFrontend.toOptions(codeActionParams, doc, res, cancelIndicator);
		return newOptions;
	}

	private QuickFixImplementation findOriginatingQuickfix(String issueCode, String fixId) {
		for (QuickFixImplementation quickfix : quickfixMap.get(issueCode)) {
			if (quickfix.id.equals(fixId)) {
				return quickfix;
			}
		}
		return null;
	}

}
