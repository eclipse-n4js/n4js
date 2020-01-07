/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.helper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.cli.compiler.N4jscLanguageClient;
import org.eclipse.n4js.ide.xtext.server.XWorkspaceManager;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Overwrites {@link N4jscLanguageClient} to collect all diagnostics of N4jsc for later evaluation in the test case.
 */
@Singleton
public class N4jscTestLanguageClient extends N4jscLanguageClient {
	@Inject
	XWorkspaceManager workspaceManager;

	Multimap<String, Diagnostic> issues = Multimaps.synchronizedMultimap(HashMultimap.create());
	Multimap<String, String> errors = Multimaps.synchronizedMultimap(HashMultimap.create());
	Multimap<String, String> warnings = Multimaps.synchronizedMultimap(HashMultimap.create());
	NavigableMap<Path, Set<File>> transpiledFiles = Collections.synchronizedNavigableMap(new TreeMap<>());
	Set<URI> deletedFiles = Collections.synchronizedSet(new HashSet<>());

	@Override
	public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
		super.publishDiagnostics(diagnostics);

		List<Diagnostic> issueList = diagnostics.getDiagnostics();
		if (issueList.isEmpty()) {
			return;
		}

		String uriString = issueSerializer.uri(diagnostics.getUri());
		for (Diagnostic diag : issueList) {
			String issueString = issueSerializer.diagnostics(diag);
			issues.put(uriString, diag);

			switch (diag.getSeverity()) {
			case Error:
				errors.put(uriString, issueString);
				break;
			case Warning:
				warnings.put(uriString, issueString);
				break;
			default:
				// ignore
				break;
			}
		}
	}

	@Override
	public void afterDelete(URI file) {
		deletedFiles.add(file);
	}

	@Override
	public void afterGenerate(URI source, URI generated) {
		super.afterGenerate(source, generated);
		if (generated.lastSegment().isBlank()) {
			generated = generated.trimSegments(1);
		}

		String fileName = generated.lastSegment();
		if (!fileName.endsWith(N4JSGlobals.JS_FILE_EXTENSION) && !fileName.endsWith(N4JSGlobals.JSX_FILE_EXTENSION)) {
			return;
		}

		Path folder = Paths.get(generated.trimSegments(1).toFileString());
		URI relGenerated = workspaceManager.makeWorkspaceRelative(generated);
		File relFile = new File(relGenerated.toFileString());
		transpiledFiles.computeIfAbsent(folder, f -> Collections.synchronizedSet(new HashSet<File>())).add(relFile);
		if (!transpiledFiles.containsKey(folder)) {
			transpiledFiles.put(folder, new HashSet<File>());
		}
		transpiledFiles.get(folder).add(relFile);
	}

	@Override
	public long getDeletionsCount() {
		return deletedFiles.size();
	}

}
