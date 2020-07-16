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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.lsp4j.ApplyWorkspaceEditParams;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.n4js.external.LibraryChange;
import org.eclipse.n4js.external.LibraryChange.LibraryChangeType;
import org.eclipse.n4js.external.NpmCLI;
import org.eclipse.n4js.ide.imports.ImportDescriptor;
import org.eclipse.n4js.ide.imports.ImportHelper;
import org.eclipse.n4js.ide.imports.ImportOrganizer;
import org.eclipse.n4js.ide.imports.ReferenceResolution;
import org.eclipse.n4js.ide.server.codeActions.ICodeActionAcceptor;
import org.eclipse.n4js.ide.server.codeActions.N4JSCodeActionService;
import org.eclipse.n4js.ide.server.codeActions.N4JSSourceActionProvider;
import org.eclipse.n4js.ide.xtext.server.ExecuteCommandParamsDescriber;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.n4js.ide.xtext.server.build.XBuildManager;
import org.eclipse.n4js.json.ide.codeActions.JSONCodeActionService;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.semver.SemverHelper;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.NPMVersionRequirement;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.smith.CollectedDataAccess;
import org.eclipse.n4js.smith.DataCollectorUtils;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
import org.eclipse.xtext.ide.server.UriExtensions;
import org.eclipse.xtext.ide.server.commands.IExecutableCommandService;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Provides commands for LSP clients
 */
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

	/**
	 * The command to enable the performance data collectors.
	 */
	public static final String ENABLE_PERFORMANCE_COLLECTOR = "n4js.performance.collector.enable";

	/**
	 * The command to disable the performance data collectors.
	 */
	public static final String DISABLE_PERFORMANCE_COLLECTOR = "n4js.performance.collector.disable";

	/**
	 * The command to dump the collected performance data.
	 */
	public static final String SHOW_PERFORMANCE_DATA = "n4js.performance.collector.show";

	/**
	 * The command to reset the collected performance data.
	 */
	public static final String RESET_PERFORMANCE_DATA = "n4js.performance.collector.reset";

	@Inject
	private XLanguageServerImpl lspServer;

	@Inject
	private N4JSCodeActionService codeActionService;

	@Inject
	private NpmCLI npmCli;

	@Inject
	private SemverHelper semverHelper;

	@Inject
	private ImportHelper importHelper;

	@Inject
	private ImportOrganizer importOrganizer;

	@Inject
	private UriExtensions uriExtensions;

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
	 * Enable the performance data collector.
	 *
	 * @param access
	 *            the language server access.
	 * @param cancelIndicator
	 *            the cancel indicator.
	 */
	@ExecutableCommandHandler(ENABLE_PERFORMANCE_COLLECTOR)
	public Void enablePerformanceDataCollector(ILanguageServerAccess access, CancelIndicator cancelIndicator) {
		CollectedDataAccess.setPaused(false);
		access.getLanguageClient()
				.logMessage(new MessageParams(MessageType.Log, "Enabled performance data collectors"));
		return null;
	}

	/**
	 * Disable the performance data collector.
	 *
	 * @param access
	 *            the language server access.
	 * @param cancelIndicator
	 *            the cancel indicator.
	 */
	@ExecutableCommandHandler(DISABLE_PERFORMANCE_COLLECTOR)
	public Void disablePerformanceDataCollector(ILanguageServerAccess access, CancelIndicator cancelIndicator) {
		CollectedDataAccess.setPaused(true);
		access.getLanguageClient()
				.logMessage(new MessageParams(MessageType.Log, "Disabled performance data collectors"));
		return null;
	}

	/**
	 * Visualize the collected performance data on the client.
	 *
	 * @param access
	 *            the language server access.
	 * @param cancelIndicator
	 *            the cancel indicator.
	 */
	@ExecutableCommandHandler(SHOW_PERFORMANCE_DATA)
	public Void showPerformanceDataCollector(ILanguageServerAccess access, CancelIndicator cancelIndicator) {
		access.getLanguageClient()
				.logMessage(new MessageParams(MessageType.Log, DataCollectorUtils.allDataToString("  ")));
		return null;
	}

	/**
	 * Visualize the collected performance data on the client and reset the data back to its initial state.
	 *
	 * @param access
	 *            the language server access.
	 * @param cancelIndicator
	 *            the cancel indicator.
	 */
	@ExecutableCommandHandler(RESET_PERFORMANCE_DATA)
	public Void resetPerformanceDataCollector(ILanguageServerAccess access, CancelIndicator cancelIndicator) {
		access.getLanguageClient()
				.logMessage(new MessageParams(MessageType.Log, DataCollectorUtils.allDataToString("  ")));
		access.getLanguageClient()
				.logMessage(new MessageParams(MessageType.Log, "Reset collected performance data"));
		CollectedDataAccess.resetAllData();
		return null;
	}

	/**
	 * Fix the issues of the same kind in the entire file.
	 */
	@ExecutableCommandHandler(COMPOSITE_FIX_FILE)
	public Void fixAllInFile(String title, String code, String fixId, CodeActionParams codeActionParams,
			ILanguageServerAccess access, CancelIndicator cancelIndicator) {

		String uriString = codeActionParams.getTextDocument().getUri();
		URI uri = uriExtensions.toUri(uriString);

		WorkspaceEdit edit = codeActionService.applyToFile(uri, code, fixId, cancelIndicator);
		access.getLanguageClient().applyEdit(new ApplyWorkspaceEditParams(edit, title));
		return null;
	}

	/**
	 * Fix the issues of the same kind in the entire project.
	 */
	@ExecutableCommandHandler(COMPOSITE_FIX_PROJECT)
	public Void fixAllInProject(String title, String code, String fixId, CodeActionParams codeActionParams,
			ILanguageServerAccess access, CancelIndicator cancelIndicator) {

		String uriString = codeActionParams.getTextDocument().getUri();
		URI uri = uriExtensions.toUri(uriString);

		WorkspaceEdit edit = codeActionService.applyToProject(uri, code, fixId, cancelIndicator);
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

		lspServer.getQueuedExecutorService().submitAndCancelPrevious(XBuildManager.class, "InstallNpm", (ci) -> {
			// FIXME: Use CliTools in favor of npmCli
			NPMVersionRequirement versionRequirement = semverHelper.parse(version);
			if (versionRequirement == null) {
				versionRequirement = SemverUtils.createEmptyVersionRequirement();
			}
			String normalizedVersion = SemverSerializer.serialize(versionRequirement);

			N4JSProjectName projectName = new N4JSProjectName(packageName);
			LibraryChange change = new LibraryChange(LibraryChangeType.Install, null, projectName, normalizedVersion);
			MultiStatus multiStatus = new MultiStatus("json", 1, null, null);
			FileURI targetProject = new FileURI(URI.createURI(fileUri)).getParent();
			npmCli.batchInstall(new NullProgressMonitor(), multiStatus, Arrays.asList(change), targetProject);

			MessageParams messageParams = new MessageParams();
			switch (multiStatus.getSeverity()) {
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
			for (IStatus child : multiStatus.getChildren()) {
				if (child.getSeverity() == multiStatus.getSeverity()) {
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
		return access.doRead(uriString, context -> {
			return doOrganizeImports(uriString, context, access.getLanguageClient(), cancelIndicator);
		}).join();
	}

	private Void doOrganizeImports(String uriString, ILanguageServerAccess.Context context,
			LanguageClient languageClient, CancelIndicator cancelIndicator) {

		Resource resource = context.getResource();
		if (!(resource instanceof N4JSResource)) {
			return null;
		}
		Script script = ((N4JSResource) resource).getScript();
		Document document = context.getDocument();

		// compute imports to be added for unresolved references
		List<ImportDescriptor> importsToBeAdded = new ArrayList<>();
		List<ReferenceResolution> resolutions = importHelper
				.findResolutionsForAllUnresolvedReferences(script, cancelIndicator);
		for (ReferenceResolution resolution : resolutions) {
			if (resolution.importToBeAdded != null) {
				importsToBeAdded.add(resolution.importToBeAdded);
			}
		}

		// organize all imports (existing and new ones)
		List<TextEdit> edits = importOrganizer.organizeImports(document, script, importsToBeAdded, cancelIndicator);

		WorkspaceEdit workspaceEdit = new WorkspaceEdit(Collections.singletonMap(uriString, edits));
		ApplyWorkspaceEditParams params = new ApplyWorkspaceEditParams(workspaceEdit, "Organize Imports");
		languageClient.applyEdit(params);
		return null;
	}
}
