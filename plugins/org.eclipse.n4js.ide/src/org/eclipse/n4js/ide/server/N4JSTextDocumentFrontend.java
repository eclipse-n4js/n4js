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
package org.eclipse.n4js.ide.server;

import static org.eclipse.n4js.N4JSGlobals.JS_FILE_EXTENSION;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.n4js.ide.xtext.server.ResourceTaskContext;
import org.eclipse.n4js.ide.xtext.server.TextDocumentFrontend;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.transpiler.sourcemap.MappingEntry;
import org.eclipse.n4js.transpiler.sourcemap.SourceMap;
import org.eclipse.n4js.transpiler.sourcemap.SourceMapFileLocator;
import org.eclipse.n4js.utils.ResourceNameComputer;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.common.base.Strings;
import com.google.inject.Inject;

/**
 * Extends {@link N4JSTextDocumentFrontend} to implement N4JS server capabilities.
 */
public class N4JSTextDocumentFrontend extends TextDocumentFrontend {

	@Inject
	private IN4JSCore core;

	@Inject
	private ResourceNameComputer resourceNameComputer;

	@Inject
	private SourceMapFileLocator sourceMapFileLocator;

	@Override
	protected Either<List<? extends Location>, List<? extends LocationLink>> implementation(ResourceTaskContext rtc,
			TextDocumentPositionParams positionParams, CancelIndicator cancelIndicator) {

		URI uri = rtc.getURI();
		IN4JSProject project = core.findProject(uri).orNull();
		String targetFileName = resourceNameComputer.generateFileDescriptor(uri, JS_FILE_EXTENSION);
		List<Location> locations = new ArrayList<>();
		if (project != null && !Strings.isNullOrEmpty(targetFileName)) {
			String outputPath = project.getOutputPath();
			Path projectLocation = project.getLocation().toFileSystemPath();
			Path genFilePath = projectLocation.resolve(outputPath + "/" + targetFileName);

			Range range = findRange(positionParams, genFilePath);
			Location location = new Location();
			location.setUri(genFilePath.toString());
			location.setRange(range);
			locations.add(location);
		}
		return Either.forLeft(locations);
	}

	private Range findRange(TextDocumentPositionParams positionParams, Path genFilePath) {
		try {
			File sourceMapFile = sourceMapFileLocator.resolveSourceMapFromGen(genFilePath);
			SourceMap sourceMap = SourceMap.loadAndResolve(sourceMapFile.toPath());
			Position position = positionParams.getPosition();
			MappingEntry mappingEntry = sourceMap.findMappingForSrcPosition(0, position.getLine(),
					position.getCharacter());

			Position startPos = new Position(mappingEntry.genLine, mappingEntry.genColumn);
			Position endPos = new Position(mappingEntry.genLine, mappingEntry.genColumn);
			return new Range(startPos, endPos);
		} catch (Exception e) {
			return new Range(new Position(), new Position());
		}
	}

}
