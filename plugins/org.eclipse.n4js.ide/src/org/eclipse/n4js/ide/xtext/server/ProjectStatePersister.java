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
package org.eclipse.n4js.ide.xtext.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl.EObjectInputStream;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl.EObjectOutputStream;
import org.eclipse.n4js.ide.validation.N4JSIssue;
import org.eclipse.n4js.ide.xtext.server.build.XIndexState;
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.build.Source2GeneratedMapping;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SerializableEObjectDescription;
import org.eclipse.xtext.resource.persistence.SerializableReferenceDescription;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.eclipse.xtext.validation.Issue;
import org.eclipse.xtext.workspace.IProjectConfig;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

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
		/** Hashes to indicate file changes */
		final public Map<URI, Collection<Issue>> validationIssues;

		PersistedState(XIndexState indexState, Map<URI, HashedFileContent> fileHashs,
				Map<URI, Collection<Issue>> validationIssues) {

			this.indexState = indexState;
			this.fileHashs = fileHashs;
			this.validationIssues = validationIssues;
		}
	}

	/**
	 * After the version, the stream contains a zipped, binary object stream with the following shape:
	 *
	 * <pre>
	 * - Language version as per {@link N4JSLanguageUtils#getLanguageVersion() N4JSLanguageUtils.getLanguageVersion}
	 * - Number #r of resource descriptions
	 * - #r times a serializable resource description as per {@link SerializableResourceDescription#writeExternal(java.io.ObjectOutput) SerializableResourceDescription.writeExternal}
	 * - A mapping of generated URIs as per {@link Source2GeneratedMapping#writeExternal(java.io.ObjectOutput) Source2GeneratedMapping.writeExternal}
	 * - Number #f of fingerprints per URI
	 * - #f times a fingerprint as per {@link HashedFileContent#write(java.io.ObjectOutput) HashedFileContent.write}
	 * - Number #vs of source files that have issues
	 * - #vs times:
	 * 	- source URI
	 * 	- Number #vi of issues of source
	 * 	- #vi times a validation issue as per {@link N4JSIssue#writeExternal(ObjectOutput) N4JSIssue.writeExternal}
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
	 * @param validationIssues
	 *            map of source files to issues
	 */
	public void writeProjectState(IProjectConfig project, XIndexState state,
			Collection<? extends HashedFileContent> files, Map<URI, ? extends Collection<Issue>> validationIssues) {
		try {
			File file = getDataFile(project);
			try (OutputStream nativeOut = Files.asByteSink(file).openBufferedStream()) {
				writeProjectState(nativeOut, N4JSLanguageUtils.getLanguageVersion(), state, files, validationIssues);

			} catch (IOException e) {
				if (file.isFile()) {
					file.delete();
				}
			}
		} catch (URISyntaxException e) {
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
			Collection<? extends HashedFileContent> files, Map<URI, ? extends Collection<Issue>> validationIssues)
			throws IOException {

		stream.write(CURRENT_VERSION);
		try (ObjectOutputStream output = new ObjectOutputStream(
				new BufferedOutputStream(new GZIPOutputStream(stream, 8192)))) {

			output.writeUTF(languageVersion);

			writeResourceDescriptions(state, output);

			writeFileMappings(state, output);

			writeFingerprints(files, output);

			writeValidationIssues(validationIssues, output);
		}
	}

	private void writeResourceDescriptions(XIndexState state, ObjectOutputStream output) throws IOException {
		ResourceDescriptionsData resourceDescriptionData = state.getResourceDescriptions();
		CustomEObjectOutputStream eObjectOutputStream = new CustomEObjectOutputStream(output);
		eObjectOutputStream.writeCompressedInt(resourceDescriptionData.getAllURIs().size());
		for (IResourceDescription description : resourceDescriptionData.getAllResourceDescriptions()) {
			if (description instanceof SerializableResourceDescription) {
				writeResourceDescription((SerializableResourceDescription) description, eObjectOutputStream);
			} else {
				throw new IOException("Unexpected type: " + description.getClass().getName());
			}
		}
		eObjectOutputStream.flush();
	}

	static class CustomEObjectOutputStream extends EObjectOutputStream {

		public CustomEObjectOutputStream(OutputStream outputStream) throws IOException {
			super(outputStream, Collections.emptyMap());
		}

		@Override
		protected void writeSignature() throws IOException {
			// skip
		}

		@Override
		protected void writeVersion() throws IOException {
			// skip
		}

		@Override
		protected EClassData writeEClass(EClass eClass) throws IOException {
			// make visible
			return super.writeEClass(eClass);
		}

		@Override
		protected EStructuralFeatureData writeEStructuralFeature(EStructuralFeature eStructuralFeature)
				throws IOException {
			// make visible
			return super.writeEStructuralFeature(eStructuralFeature);
		}
	}

	private void writeResourceDescription(SerializableResourceDescription description, CustomEObjectOutputStream output)
			throws IOException {
		// description.writeExternal(output);
		// relies on writeObject which is very slow

		output.writeURI(description.getURI());
		{
			List<SerializableEObjectDescription> objects = description.getDescriptions();
			output.writeCompressedInt(objects.size());
			for (SerializableEObjectDescription object : objects) {
				output.writeURI(object.getEObjectURI());
				output.writeEClass(object.getEClass());
				object.getQualifiedName().writeToStream(output);
				Map<String, String> userData = object.getUserData();
				if (userData != null) {
					output.writeCompressedInt(userData.size());
					for (Map.Entry<String, String> entry : userData.entrySet()) {
						output.writeString(entry.getKey());
						output.writeString(entry.getValue());
					}
				} else {
					output.writeCompressedInt(0);
				}
			}
		}
		{
			List<SerializableReferenceDescription> references = description.getReferences();
			output.writeCompressedInt(references.size());
			for (SerializableReferenceDescription reference : references) {
				output.writeURI(reference.getSourceEObjectUri());
				output.writeURI(reference.getTargetEObjectUri());
				output.writeURI(reference.getContainerEObjectURI());
				output.writeEStructuralFeature(reference.getEReference());
				output.writeCompressedInt(reference.getIndexInList() + 1);
			}
		}
		{
			List<QualifiedName> importedNames = IterableExtensions.toList(description.getImportedNames());
			output.writeCompressedInt(importedNames.size());
			for (QualifiedName importedName : importedNames) {
				importedName.writeToStream(output);
			}
		}
	}

	private void writeFileMappings(XIndexState state, ObjectOutputStream output) throws IOException {
		state.getFileMappings().writeExternal(output);
	}

	private void writeFingerprints(Collection<? extends HashedFileContent> files, ObjectOutputStream output)
			throws IOException {

		output.writeInt(files.size());
		for (HashedFileContent fingerprint : files) {
			fingerprint.write(output);
		}
	}

	private void writeValidationIssues(Map<URI, ? extends Collection<Issue>> validationIssues, ObjectOutput output)
			throws IOException {

		int numberSources = validationIssues.size();
		output.writeInt(numberSources);
		for (Map.Entry<URI, ? extends Collection<Issue>> srcIssues : validationIssues.entrySet()) {
			URI source = srcIssues.getKey();
			Collection<Issue> issues = srcIssues.getValue();

			output.writeUTF(source.toString());

			Collection<N4JSIssue> n4Issues = new ArrayList<>();
			for (Issue issue : issues) {
				if (issue instanceof N4JSIssue) {
					n4Issues.add((N4JSIssue) issue);
				} else {
					n4Issues.add(new N4JSIssue(issue));
				}
			}

			int numberIssues = n4Issues.size();
			output.writeInt(numberIssues);
			for (N4JSIssue issue : n4Issues) {
				issue.writeExternal(output);
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
		File file;
		try {
			file = getDataFile(project);
			try {
				if (file.isFile()) {
					try (InputStream nativeIn = Files.asByteSource(file).openBufferedStream()) {
						return readProjectState(nativeIn, N4JSLanguageUtils.getLanguageVersion());
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				if (file.isFile()) {
					file.delete();
				}
			}
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
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

			ResourceDescriptionsData resourceDescriptionsData = readResourceDescriptions(input);

			XSource2GeneratedMapping fileMappings = readFileMappings(input);

			Map<URI, HashedFileContent> fingerprints = readFingerprints(input);

			Map<URI, Collection<Issue>> validationIssues = readValidationIssues(input);

			XIndexState indexState = new XIndexState(resourceDescriptionsData, fileMappings);
			return new PersistedState(indexState, fingerprints, validationIssues);
		}
	}

	static class CustomEObjectInputStream extends EObjectInputStream {

		public CustomEObjectInputStream(InputStream outputStream) throws IOException {
			super(outputStream, Collections.emptyMap());
		}

		@Override
		protected void readSignature() throws IOException {
			// skip
		}

		@Override
		protected void readVersion() throws IOException {
			version = Version.VERSION_1_0;
		}

		protected EClass doReadEClass() throws IOException {
			return readEClass().eClass;
		}

		protected EReference doReadEReference() throws IOException {
			return (EReference) readEStructuralFeature().eStructuralFeature;
		}
	}

	private ResourceDescriptionsData readResourceDescriptions(ObjectInputStream input)
			throws IOException {

		List<IResourceDescription> descriptions = new ArrayList<>();
		CustomEObjectInputStream eObjectInput = new CustomEObjectInputStream(input);
		int size = eObjectInput.readCompressedInt();
		while (size > 0) {
			size--;
			descriptions.add(readResourceDescription(eObjectInput));
		}

		ResourceDescriptionsData resourceDescriptionsData = new ResourceDescriptionsData(descriptions);
		return resourceDescriptionsData;
	}

	private SerializableResourceDescription readResourceDescription(CustomEObjectInputStream input)
			throws IOException {
		SerializableResourceDescription description = new SerializableResourceDescription();

		description.setURI(input.readURI());
		{
			List<SerializableEObjectDescription> objects = new ArrayList<>();
			int size = input.readCompressedInt();
			while (size > 0) {
				size--;
				SerializableEObjectDescription object = new SerializableEObjectDescription();
				object.setEObjectURI(input.readURI());
				object.setEClass(input.doReadEClass());
				object.setQualifiedName(QualifiedName.createFromStream(input));
				int userDataSize = input.readCompressedInt();
				HashMap<String, String> userData = new HashMap<>();
				while (userDataSize > 0) {
					userDataSize--;
					userData.put(input.readString(), input.readString());
				}
				object.setUserData(userData);
				objects.add(object);
			}
			description.setDescriptions(objects);
		}
		{
			List<SerializableReferenceDescription> references = new ArrayList<>();
			int size = input.readCompressedInt();
			while (size > 0) {
				size--;
				SerializableReferenceDescription reference = new SerializableReferenceDescription();
				reference.setSourceEObjectUri(input.readURI());
				reference.setTargetEObjectUri(input.readURI());
				reference.setContainerEObjectURI(input.readURI());
				reference.setEReference(input.doReadEReference());
				reference.setIndexInList(input.readCompressedInt() - 1);
				references.add(reference);
			}
			description.setReferences(references);
		}
		{
			List<QualifiedName> importedNames = new ArrayList<>();
			int size = input.readCompressedInt();
			while (size > 0) {
				size--;
				importedNames.add(QualifiedName.createFromStream(input));
			}
			description.setImportedNames(importedNames);
		}

		return description;
	}

	private XSource2GeneratedMapping readFileMappings(ObjectInputStream input)
			throws IOException, ClassNotFoundException {

		XSource2GeneratedMapping fileMappings = new XSource2GeneratedMapping();
		fileMappings.readExternal(input);
		return fileMappings;
	}

	private Map<URI, HashedFileContent> readFingerprints(ObjectInputStream input) throws IOException {
		Map<URI, HashedFileContent> fingerprints = new HashMap<>();
		int size = input.readInt();
		while (size > 0) {
			size--;
			HashedFileContent hashFileContent = new HashedFileContent(input);
			fingerprints.put(hashFileContent.getUri(), hashFileContent);
		}
		return fingerprints;
	}

	private Map<URI, Collection<Issue>> readValidationIssues(ObjectInputStream input)
			throws IOException, ClassNotFoundException {

		Map<URI, Collection<Issue>> validationIssues = new LinkedHashMap<>();

		int numberOfSources = input.readInt();
		while (numberOfSources > 0) {
			numberOfSources--;

			URI source = URI.createURI(input.readUTF());
			validationIssues.put(source, new ArrayList<>());

			int numberOfIssues = input.readInt();
			while (numberOfIssues > 0) {
				numberOfIssues--;
				N4JSIssue issue = new N4JSIssue();
				issue.readExternal(input);
				validationIssues.get(source).add(issue);
			}
		}

		return validationIssues;
	}

	private File getDataFile(IProjectConfig project) throws URISyntaxException {
		URI fileName = getFileName(project);
		File file = new File(new java.net.URI(fileName.toString()));
		return file;
	}

	/** @return the file URI of the persisted index */
	public URI getFileName(IProjectConfig project) {
		URI rootPath = project.getPath();
		return new FileURI(rootPath).appendSegment(FILENAME).toURI();
	}
}
