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

	@Override
	protected Either<List<? extends Location>, List<? extends LocationLink>> implementation(ResourceTaskContext rtc,
			TextDocumentPositionParams position, CancelIndicator cancelIndicator) {

		URI uri = rtc.getURI();
		IN4JSProject project = core.findProject(uri).orNull();
		String targetFileName = resourceNameComputer.generateFileDescriptor(uri, JS_FILE_EXTENSION);
		List<Location> locations = new ArrayList<>();
		if (project != null && !Strings.isNullOrEmpty(targetFileName)) {
			String outputPath = project.getOutputPath();
			Path projectLocation = project.getLocation().toFileSystemPath();
			Path genFilePath = projectLocation.resolve(outputPath + "/" + targetFileName);
			Location location = new Location();
			location.setUri(genFilePath.toString());
			location.setRange(new Range(new Position(1, 1), new Position(1, 1)));
			locations.add(location);
		}
		return Either.forLeft(locations);
	}

}
