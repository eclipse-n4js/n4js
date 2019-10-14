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
package org.eclipse.n4js.ide.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.build.IndexState;
import org.eclipse.xtext.build.Source2GeneratedMapping;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.workspace.IProjectConfig;

import com.google.common.io.Files;

/**
 * Allows to read / write the state of a compiled project to disk to sport incremental builds after a restart of the
 * compiler process.
 */
@SuppressWarnings("restriction")
public class ProjectStatePersister {

	private static final int VERSION = 1;
	/**
	 * The simple name of the file with the project state.
	 */
	public static final String FILENAME = ".n4js.projectstate";
	// TODO inject version helper to also write the compiler version to disk

	/**
	 * Write the index state and a hash of the project state to disk in order to allow loading it again.
	 *
	 * @param project
	 *            the project
	 * @param state
	 *            the state to be written
	 */
	public void writeProjectState(IProjectConfig project, IndexState state) {
		try {
			File file = getDataFile(project);
			try (ObjectOutputStream output = new ObjectOutputStream(Files.asByteSink(file).openBufferedStream())) {
				output.writeInt(VERSION);
				output.writeInt(state.getResourceDescriptions().getAllURIs().size());
				for (IResourceDescription description : state.getResourceDescriptions().getAllResourceDescriptions()) {
					if (description instanceof SerializableResourceDescription) {
						((SerializableResourceDescription) description).writeExternal(output);
					} else {
						throw new IOException("Unexpected type: " + description.getClass().getName());
					}
				}
				Source2GeneratedMapping fileMappings = state.getFileMappings();
				fileMappings.writeExternal(output);

			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read the index state as it was written to disk for the given project.
	 *
	 * @param project
	 *            the project
	 * @param result
	 *            the acceptor for the result.
	 */
	public void readProjectState(IProjectConfig project, IAcceptor<? super IndexState> result) {
		try {
			File file = getDataFile(project);
			if (file.exists() && file.isFile()) {
				try (ObjectInputStream input = new ObjectInputStream(Files.asByteSource(file).openBufferedStream())) {
					int version = input.readInt();
					if (version == VERSION) {
						List<IResourceDescription> descriptions = new ArrayList<>();
						int size = input.readInt();
						while (size > 0) {
							size--;
							SerializableResourceDescription description = new SerializableResourceDescription();
							description.readExternal(input);
							descriptions.add(description);
						}
						ResourceDescriptionsData resourceDescriptionsData = new ResourceDescriptionsData(descriptions);
						Source2GeneratedMapping fileMappings = new Source2GeneratedMapping();
						fileMappings.readExternal(input);
						result.accept(new IndexState(resourceDescriptionsData, fileMappings));
					}
				}
			}
		} catch (IOException | URISyntaxException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private File getDataFile(IProjectConfig project) throws URISyntaxException {
		URI fileName = getFileName(project);
		File file = new File(new java.net.URI(fileName.toString()));
		return file;
	}

	private URI getFileName(IProjectConfig project) {
		URI rootPath = project.getPath();
		URI fileName = rootPath.appendSegment(FILENAME);
		return fileName;
	}
}
