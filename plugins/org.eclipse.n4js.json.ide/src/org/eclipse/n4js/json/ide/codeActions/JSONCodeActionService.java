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
package org.eclipse.n4js.json.ide.codeActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.json.JSON.NameValuePair;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.ide.server.Document;
import org.eclipse.xtext.ide.server.codeActions.ICodeActionService2;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;

/**
 * Implements quickfixes for package.json files, e.g. install missing npms.
 */
@SuppressWarnings("restriction")
public class JSONCodeActionService implements ICodeActionService2 {

	/**
	 * Install an NPM into the workspace.
	 *
	 * Should not appear on the UI of the client.
	 */
	public static final String INSTALL_NPM = "json.install.npm";

	@Inject
	private EObjectAtOffsetHelper eObjectAtOffsetHelper;

	@Override
	public List<Either<Command, CodeAction>> getCodeActions(Options options) {
		List<Either<Command, CodeAction>> result = new ArrayList<>();
		if (options.getCodeActionParams() != null && options.getCodeActionParams().getContext() != null) {
			List<Diagnostic> diagnostics = options.getCodeActionParams().getContext().getDiagnostics();
			for (Diagnostic diag : diagnostics) {
				if (IssueCodes.NON_EXISTING_PROJECT.equals(diag.getCode())) {
					Command cmd = createInstallNpmCommand(options, diag);
					if (cmd != null) {
						result.add(Either.forLeft(cmd));
					}
				}
			}
		}
		return result;
	}

	private Command createInstallNpmCommand(Options options, Diagnostic diag) {
		EObject element = getEObject(options, diag);
		if (element instanceof NameValuePair) {
			NameValuePair pair = (NameValuePair) element;
			String projectName = pair.getName();
			String version = JSONModelUtils.asStringOrNull(pair.getValue());
			return new Command("Install npm package to workspace", INSTALL_NPM,
					Arrays.asList(projectName, version, options.getCodeActionParams().getTextDocument().getUri()));
		}
		return null;
	}

	private EObject getEObject(Options options, Diagnostic diag) {
		Document doc = options.getDocument();
		XtextResource resource = options.getResource();
		return getEObject(doc, resource, diag.getRange());
	}

	private EObject getEObject(Document doc, XtextResource resource, Range range) {
		Position start = range.getStart();
		int startOffset = doc.getOffSet(start);
		return eObjectAtOffsetHelper.resolveContainedElementAt(resource, startOffset);
	}
}
