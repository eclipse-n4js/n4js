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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.lsp4j.ApplyWorkspaceEditParams;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.n4js.ide.server.codeActions.N4JSCodeActionService;
import org.eclipse.n4js.ide.xtext.server.ExecuteCommandParamsDescriber;
import org.eclipse.n4js.ide.xtext.server.XLanguageServerImpl;
import org.eclipse.xtext.ide.server.ILanguageServerAccess;
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
	private N4JSCodeActionService codeActionService;

	/**
	 * Methods annotated as ExecutableCommandHandler will be registered as ...drumrolls... handlers for ExecuteCommand
	 * requests. The methods are found reflectively in this class. They must define a parameter list with at least two
	 * trailing parameters {@code ILanguageServerAccess access, CancelIndicator cancelIndicator}. All the leading
	 * parameters before the {@code ILanguageServerAccess} are passed by the framework. The parameter types are used to
	 * deserialize the {@link ExecuteCommandParams#getArguments() arguments} of the execute commands in a strongly types
	 * way.
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@interface ExecutableCommandHandler {
		String value();
	}

	/**
	 * The minimal interface that a command handler must fulfil.
	 */
	@FunctionalInterface
	interface IExecutableCommandHandler {
		Object execute(ExecuteCommandParams params, ILanguageServerAccess access, CancelIndicator cancelIndicator);
	}

	private Map<String, IExecutableCommandHandler> handlers;
	private Map<String, Type[]> argumentTypes;

	@Override
	public List<String> initialize() {
		Method[] methods = getClass().getMethods();
		Map<String, IExecutableCommandHandler> localHandlers = new HashMap<>();
		Map<String, Type[]> localArgumentTypes = new HashMap<>();
		for (Method method : methods) {
			ExecutableCommandHandler annotation = method.getAnnotation(ExecutableCommandHandler.class);
			if (annotation != null) {
				List<Type> parameterTypes = Arrays.asList(method.getGenericParameterTypes());
				// -2 since the last params are the ILanguageServerAccess and the CancelIndicator
				Type[] args = parameterTypes.subList(0, parameterTypes.size() - 2).toArray(Type[]::new);
				localArgumentTypes.put(annotation.value(), args);

				localHandlers.put(annotation.value(), (params, access, cancelIndicator) -> {
					List<Object> actualArguments = new ArrayList<>();
					actualArguments.addAll(params.getArguments());
					actualArguments.add(access);
					actualArguments.add(cancelIndicator);
					try {
						return method.invoke(this, actualArguments.toArray());
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				});
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
		lspServer.clean().thenRun(() -> lspServer.reinitWorkspace());
		return null;
	}

	/**
	 * Fix the issues of the same kind in the entire file.
	 */
	@ExecutableCommandHandler(COMPOSITE_FIX_FILE)
	public Void fixAllInFile(String title, String code, String fixId, CodeActionParams codeActionParams,
			ILanguageServerAccess access,
			CancelIndicator cancelIndicator) {
		WorkspaceEdit edit = codeActionService.applyToFile(code, fixId,
				lspServer.toOptions(codeActionParams, cancelIndicator));
		access.getLanguageClient().applyEdit(new ApplyWorkspaceEditParams(edit, title));
		return null;
	}

	/**
	 * Fix the issues of the same kind in the entire project.
	 */
	@ExecutableCommandHandler(COMPOSITE_FIX_PROJECT)
	public Void fixAllInProject(String title, String code, String fixId, CodeActionParams codeActionParams,
			ILanguageServerAccess access,
			CancelIndicator cancelIndicator) {
		WorkspaceEdit edit = codeActionService.applyToProject(code, fixId,
				lspServer.toOptions(codeActionParams, cancelIndicator));
		access.getLanguageClient().applyEdit(new ApplyWorkspaceEditParams(edit, title));
		return null;
	}

}
