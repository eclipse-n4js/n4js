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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.build.XIndexState;
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.build.Source2GeneratedMapping;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.eclipse.xtext.workspace.IProjectConfig;

import com.google.common.io.Files;

/**
 * Allows to read / write the state of a compiled project to disk to sport incremental builds after a restart of the
 * compiler process. The first byte in the written binary file indicates the version of the file. The file format is
 * documented per version.
 */
@SuppressWarnings("restriction")
public class ProjectStatePersister {

	/** Data holder class of project state */
	static public class PersistedState {
		/** Type index */
		final public XIndexState indexState;
		/** Hashes to indicate file changes */
		final public Map<URI, HashedFileContent> fileHashs;

		PersistedState(XIndexState indexState, Map<URI, HashedFileContent> fileHashs) {
			this.indexState = indexState;
			this.fileHashs = fileHashs;
		}
	}

	/**
	 * After the version, the stream contains a zipped, binary object stream with the following shape:
	 *
	 * <pre>
	 * Language version as per {@link N4JSLanguageUtils#getLanguageVersion() N4JSLanguageUtils.getLanguageVersion}
	 * Number #r of resource descriptions
	 * #r times a serializable resource description as per {@link SerializableResourceDescription#writeExternal(java.io.ObjectOutput) SerializableResourceDescription.writeExternal}
	 * A mapping of generated URIs as per {@link Source2GeneratedMapping#writeExternal(java.io.ObjectOutput) Source2GeneratedMapping.writeExternal}
	 * Number #f of fingerprints per URI
	 * #f times a fingerprint as per {@link HashedFileContent#write(java.io.ObjectOutput) HashedFileContent.write}
	 * </pre>
	 */
	private static final int VERSION_1 = 1;

	/**
	 * The current version of the persistence format. Increment to support backwards compatible deserialization.
	 */
	private static final int CURRENT_VERSION = VERSION_1;
	/**
	 * The simple name of the file with the project state.
	 */
	public static final String FILENAME = ".n4js.projectstate";

	/**
	 * Write the index state and a hash of the project state to disk in order to allow loading it again.
	 *
	 * @param project
	 *            the project
	 * @param state
	 *            the state to be written
	 */
	public void writeProjectState(IProjectConfig project, XIndexState state,
			Collection<? extends HashedFileContent> files) {
		try {
			File file = getDataFile(project);
			try (OutputStream nativeOut = Files.asByteSink(file).openBufferedStream()) {
				writeProjectState(nativeOut, N4JSLanguageUtils.getLanguageVersion(), state, files);
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param stream
	 *            the output stream. Will not be closed.
	 * @param languageVersion
	 *            the language version
	 * @param state
	 *            the state to be written
	 * @param files
	 *            the hashed file contents.
	 * @throws IOException
	 *             if things go bananas.
	 */
	public void writeProjectState(OutputStream stream, String languageVersion, XIndexState state,
			Collection<? extends HashedFileContent> files)
			throws IOException {

		stream.write(CURRENT_VERSION);
		try (ObjectOutputStream output = new ObjectOutputStream(
				new BufferedOutputStream(new GZIPOutputStream(stream, 8192)))) {

			output.writeUTF(languageVersion);

			ResourceDescriptionsData resourceDescriptionData = state.getResourceDescriptions();
			output.writeInt(resourceDescriptionData.getAllURIs().size());

			for (IResourceDescription description : resourceDescriptionData.getAllResourceDescriptions()) {
				if (description instanceof SerializableResourceDescription) {
					((SerializableResourceDescription) description).writeExternal(output);
				} else {
					throw new IOException("Unexpected type: " + description.getClass().getName());
				}
			}

			XSource2GeneratedMapping fileMappings = state.getFileMappings();
			fileMappings.writeExternal(output);
			output.writeInt(files.size());

			for (HashedFileContent fingerprint : files) {
				fingerprint.write(output);
			}
		}
	}

	/**
	 * Read the index state as it was written to disk for the given project.
	 *
	 * @param project
	 *            the project
	 */
	public PersistedState readProjectState(IProjectConfig project) {
		try {
			File file = getDataFile(project);
			if (file.isFile()) {
				try (InputStream nativeIn = Files.asByteSource(file).openBufferedStream()) {
					return readProjectState(nativeIn, N4JSLanguageUtils.getLanguageVersion());
				}
			}
		} catch (IOException | URISyntaxException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param stream
	 *            the stream to read from.
	 * @param expectedLanguageVersion
	 *            the language version as it is expected to be present in the stream
	 * @throws IOException
	 *             if things go bananas.
	 * @throws ClassNotFoundException
	 *             if things go bananas.
	 */
	public PersistedState readProjectState(InputStream stream, String expectedLanguageVersion)
			throws IOException, ClassNotFoundException {

		int version = stream.read();
		if (version != CURRENT_VERSION) {
			return null;
		}

		try (ObjectInputStream input = new ObjectInputStream(
				new BufferedInputStream(new GZIPInputStream(stream, 8192)))) {

			String languageVersion = input.readUTF();
			if (!expectedLanguageVersion.equals(languageVersion)) {
				return null;
			}

			List<IResourceDescription> descriptions = new ArrayList<>();
			int size = input.readInt();
			while (size > 0) {
				size--;
				SerializableResourceDescription description = new SerializableResourceDescription();
				description.readExternal(input);
				descriptions.add(description);
			}

			ResourceDescriptionsData resourceDescriptionsData = new ResourceDescriptionsData(descriptions);
			XSource2GeneratedMapping fileMappings = new XSource2GeneratedMapping();
			fileMappings.readExternal(input);
			Map<URI, HashedFileContent> fingerprints = new HashMap<>();
			size = input.readInt();

			while (size > 0) {
				size--;
				HashedFileContent hashFileContent = new HashedFileContent(input);
				fingerprints.put(hashFileContent.getUri(), hashFileContent);
			}

			XIndexState indexState = new XIndexState(resourceDescriptionsData, fileMappings);
			return new PersistedState(indexState, fingerprints);
		}
	}

	private File getDataFile(IProjectConfig project) throws URISyntaxException {
		URI fileName = getFileName(project);
		File file = new File(new java.net.URI(fileName.toString()));
		return file;
	}

	/** @return the file URI of the persisted index */
	public URI getFileName(IProjectConfig project) {
		URI rootPath = project.getPath();
		URI fileName = rootPath.appendSegment(FILENAME);
		return fileName;
	}
}
