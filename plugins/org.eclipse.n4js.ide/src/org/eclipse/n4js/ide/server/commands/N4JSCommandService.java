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
package org.eclipse.n4js.ide.server.commands;

import static org.eclipse.n4js.external.LibraryChange.LibraryChangeType.Install;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.ApplyWorkspaceEditParams;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.n4js.external.LibraryChange;
import org.eclipse.n4js.external.NpmCLI;
import org.eclipse.n4js.ide.editor.contentassist.imports.ContentAssistEntryWithRef;
import org.eclipse.n4js.ide.editor.contentassist.imports.ImportHelper;
import org.eclipse.n4js.ide.server.codeActions.ICodeActionAcceptor;
import org.eclipse.n4js.ide.server.codeActions.N4JSCodeActionService;
import org.eclipse.n4js.ide.server.codeActions.N4JSSourceActionProvider;
import org.eclipse.n4js.ide.server.imports.ImportOrganizer;
import org.eclipse.n4js.ide.server.imports.ImportOrganizer.ImportRef;
import org.eclipse.n4js.ide.xtext.server.ExecuteCommandParamsDescriber;
import org.eclipse.n4js.ide.xtext.server.XDocument;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;
import org.eclipse.n4js.json.ide.codeActions.JSONCodeActionService;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2.Options;
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Provides commands for LSP clients
 */
@SuppressWarnings("restriction")
public class N4JSCommandService implements IExecutableCommandService, ExecuteCommandParamsDescriber {
	/**
	 * The rebuild command.
	 */
	public static final String N4JS_REBUILD = "n4js.rebuild";

	/**
	 * The organize imports command. This command will organize all imports of a given file. In addition, code
	 * completion and quick fixes are available for adding missing imports.
	 */
	public static final String N4JS_ORGANIZE_IMPORTS = "n4js.organizeImports";

	/**
	 * Composite fix that will resolve all issues of the same kind in the current file.
	 *
	 * Should not appear on the UI of the client.
	 */
	public static final String COMPOSITE_FIX_FILE = "n4js.composite.fix.file";

	/**
	 * Composite fix that will resolve all issues of the same kind in the current project.
	 *
	 * Should not appear on the UI of the client.
	 */
	public static final String COMPOSITE_FIX_PROJECT = "n4js.composite.fix.project";

	@Inject
	private XLanguageServerImpl lspServer;

	@Inject
	private XWorkspaceManager workspaceManager;

	@Inject
	private UriExtensions uriExtensions;

	@Inject
	private N4JSCodeActionService codeActionService;

	@Inject
	private NpmCLI npmCli;

	@Inject
	private SemverHelper semverHelper;

	@Inject
	private ImportHelper importHelper;

	/**
	 * Methods annotated as {@link ExecutableCommandHandler} will be registered as handlers for ExecuteCommand requests.
	 * The methods are found reflectively in this class. They must define a parameter list with at least the two
	 * trailing parameters: {@code ILanguageServerAccess access, CancelIndicator cancelIndicator}. All the leading
	 * parameters before the {@code ILanguageServerAccess} are passed by the framework. The parameter types are used to
	 * deserialize the {@link ExecuteCommandParams#getArguments() arguments} of the execute commands in a strongly typed
	 * way.
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@interface ExecutableCommandHandler {
		String value();
	}

	/** Executes LSP commands by calling the appropriate method using reflection. */
	private class CommandHandler {
		private final Method method;

		private CommandHandler(Method method) {
			this.method = method;
		}

		private Object execute(ExecuteCommandParams params, ILanguageServerAccess access,
				CancelIndicator cancelIndicator) {

			List<Object> actualArguments = new ArrayList<>();
			actualArguments.addAll(params.getArguments());
			actualArguments.add(access);
			actualArguments.add(cancelIndicator);
			try {
				return method.invoke(N4JSCommandService.this, actualArguments.toArray());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private Map<String, CommandHandler> handlers;
	private Map<String, Type[]> argumentTypes;

	@Override
	public List<String> initialize() {
		Method[] methods = getClass().getMethods();
		Map<String, CommandHandler> localHandlers = new HashMap<>();
		Map<String, Type[]> localArgumentTypes = new HashMap<>();

		for (Method method : methods) {
			ExecutableCommandHandler annotation = method.getAnnotation(ExecutableCommandHandler.class);
			if (annotation != null) {
				List<Type> parameterTypes = Arrays.asList(method.getGenericParameterTypes());
				// -2 since the last params are the ILanguageServerAccess and the CancelIndicator
				Type[] args = parameterTypes.subList(0, parameterTypes.size() - 2).toArray(Type[]::new);
				localArgumentTypes.put(annotation.value(), args);
				localHandlers.put(annotation.value(), new CommandHandler(method));
			}
		}
		this.handlers = localHandlers;
		this.argumentTypes = localArgumentTypes;
		return Lists.newArrayList(localHandlers.keySet());
	}

	@Override
	public Map<String, Type[]> argumentTypes() {
		return Preconditions.checkNotNull(argumentTypes);
	}

	@Override
	public Object execute(ExecuteCommandParams params, ILanguageServerAccess access, CancelIndicator cancelIndicator) {
		return handlers.get(params.getCommand()).execute(params, access, cancelIndicator);
	}

	/**
	 * Clean the state of all workspace projects and trigger a re-initialization (new build).
	 *
	 * @param access
	 *            the language server access.
	 * @param cancelIndicator
	 *            the cancel indicator.
	 */
	@ExecutableCommandHandler(N4JS_REBUILD)
	public Void rebuild(ILanguageServerAccess access, CancelIndicator cancelIndicator) {
		lspServer.clean();
		lspServer.reinitWorkspace();
		return null;
	}

	/**
	 * Fix the issues of the same kind in the entire file.
	 */
	@ExecutableCommandHandler(COMPOSITE_FIX_FILE)
	public Void fixAllInFile(String title, String code, String fixId, CodeActionParams codeActionParams,
			ILanguageServerAccess access, CancelIndicator cancelIndicator) {

		Options options = lspServer.toOptions(codeActionParams, cancelIndicator);
		WorkspaceEdit edit = codeActionService.applyToFile(code, fixId, options);
		access.getLanguageClient().applyEdit(new ApplyWorkspaceEditParams(edit, title));
		return null;
	}

	/**
	 * Fix the issues of the same kind in the entire project.
	 */
	@ExecutableCommandHandler(COMPOSITE_FIX_PROJECT)
	public Void fixAllInProject(String title, String code, String fixId, CodeActionParams codeActionParams,
			ILanguageServerAccess access, CancelIndicator cancelIndicator) {

		Options options = lspServer.toOptions(codeActionParams, cancelIndicator);
		WorkspaceEdit edit = codeActionService.applyToProject(code, fixId, options);
		access.getLanguageClient().applyEdit(new ApplyWorkspaceEditParams(edit, title));
		return null;
	}

	/**
	 * Install the given NPM into the workspace.
	 *
	 * @param cancelIndicator
	 *            not required.
	 */
	@ExecutableCommandHandler(JSONCodeActionService.INSTALL_NPM)
	public Void installNpm(
			String packageName,
			String version,
			String fileUri,
			ILanguageServerAccess access,
			CancelIndicator cancelIndicator) {

		lspServer.getRequestManager().runWrite("InstallNpm", () -> {
			// FIXME: Use CliTools in favor of npmCli
			NPMVersionRequirement versionRequirement = semverHelper.parse(version);
			if (versionRequirement == null) {
				versionRequirement = SemverUtils.createEmptyVersionRequirement();
			}
			String normalizedVersion = SemverSerializer.serialize(versionRequirement);

			N4JSProjectName projectName = new N4JSProjectName(packageName);
			LibraryChange change = new LibraryChange(Install, null, projectName, normalizedVersion);
			MultiStatus multiStatus = new MultiStatus("json", 1, null, null);
			FileURI targetProject = new FileURI(URI.createURI(fileUri)).getParent();
			npmCli.batchInstall(new NullProgressMonitor(), multiStatus, Arrays.asList(change), targetProject);

			return multiStatus;

		}, (ci, ms) -> {
			MessageParams messageParams = new MessageParams();
			switch (ms.getSeverity()) {
			case IStatus.INFO:
				messageParams.setType(MessageType.Info);
				break;
			case IStatus.WARNING:
				messageParams.setType(MessageType.Warning);
				break;
			case IStatus.ERROR:
				messageParams.setType(MessageType.Error);
				break;
			default:
				return null;
			}

			StringWriter sw = new StringWriter();
			PrintWriter printWriter = new PrintWriter(sw);
			for (IStatus child : ms.getChildren()) {
				if (child.getSeverity() == ms.getSeverity()) {
					printWriter.println(child.getMessage());
				}
			}
			printWriter.flush();
			messageParams.setMessage(sw.toString());
			access.getLanguageClient().showMessage(messageParams);
			return null;
		}).whenComplete((a, b) -> lspServer.reinitWorkspace());

		return null;
	}

	/**
	 * Command for {@link ImportOrganizer#organizeImports(Document, Script, Collection, CancelIndicator) organizing
	 * imports} in a single file. Triggered via the corresponding source action
	 * {@link N4JSSourceActionProvider#organizeImports(CodeActionParams, ICodeActionAcceptor) organizeImports}.
	 */
	@ExecutableCommandHandler(N4JS_ORGANIZE_IMPORTS)
	public Void organizeImports(String uriString, ILanguageServerAccess access, CancelIndicator cancelIndicator) {
		URI uri = uriExtensions.toUri(uriString);
		XtextResource resource = workspaceManager.getResource(uri);
		if (!(resource instanceof N4JSResource)) {
			return null;
		}
		Script script = ((N4JSResource) resource).getScript();
		XDocument document = workspaceManager.getDocument(resource);

		// compute imports to be added for unresolved references
		List<ContentAssistEntry> candidates = importHelper.findImportCandidatesForUnresolvedReferences(resource,
				document,
				cancelIndicator);
		List<ImportRef> importsToBeAdded = new ArrayList<>();
		for (ContentAssistEntry cae : candidates) {
			if (cae instanceof ContentAssistEntryWithRef) {
				importsToBeAdded.addAll(((ContentAssistEntryWithRef) cae).getImportRefs());
			}
		}

		// organize all imports (existing and new ones)
		List<TextEdit> edits = ImportOrganizer.organizeImports(document, script, importsToBeAdded, cancelIndicator);

		WorkspaceEdit workspaceEdit = new WorkspaceEdit(Collections.singletonMap(uriString, edits));
		ApplyWorkspaceEditParams params = new ApplyWorkspaceEditParams(workspaceEdit, "Organize Imports");
		access.getLanguageClient().applyEdit(params);
		return null;
	}
}
