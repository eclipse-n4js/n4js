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
package org.eclipse.n4js.ide.xtext.server.build;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ide.xtext.server.QueuedExecutorService;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.xtext.build.Source2GeneratedMapping;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SerializableEObjectDescription;
import org.eclipse.xtext.resource.persistence.SerializableReferenceDescription;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.eclipse.xtext.util.Tuples;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Allows to read / write the state of a compiled project to disk to sport incremental builds after a restart of the
 * compiler process. The first byte in the written binary file indicates the version of the file. The file format is
 * documented per version.
 */
@SuppressWarnings({ "restriction", "deprecation" })
@Singleton
public class ProjectStatePersister {
	private static final Logger LOG = LogManager.getLogger(ProjectStatePersister.class);

	/**
	 * After the version, the stream contains a zipped, data stream of the following shape:
	 *
	 * <pre>
	 * - Language version as per {@link #getLanguageVersion() getLanguageVersion}
	 * - Number #r of resource descriptions
	 * - #r times a serializable resource description as per {@link #writeResourceDescription(SerializableResourceDescription, URI, DataOutput)}
	 * - A mapping of generated URIs as per {@link Source2GeneratedMapping#writeExternal(java.io.ObjectOutput) Source2GeneratedMapping.writeExternal}
	 * - Number #f of fingerprints per URI
	 * - #f times a fingerprint as per {@link HashedFileContent#write(DataOutput) HashedFileContent.write}
	 * - Number #vs of source files that have issues
	 * - #vs times:
	 * 	- source URI
	 * 	- Number #vi of issues of source
	 * 	- #vi times a validation issue as per {@link LSPIssue#writeExternal(DataOutput) LSPIssue.writeExternal}
	 * - Number #d of dependencies of this project
	 * - #d times:
	 * 	- project name of dependency
	 * 	- a boolean telling whether the dependency project existed at the time the project state was computed.
	 * </pre>
	 *
	 * All URIs are relative to the project's base directory.
	 */
	private static final int VERSION_3 = 3;

	/** The current version of the persistence format. Increment to support backwards compatible deserialization. */
	private static final int CURRENT_VERSION = VERSION_3;

	@Inject
	private QueuedExecutorService queuedExecutorService;

	@Inject
	private URITransformer uriTransformer;

	/** Standard constructor */
	public ProjectStatePersister() {
		// empty
	}

	/** Constructor for manual setup and testing */
	public ProjectStatePersister(QueuedExecutorService queuedExecutorService, URITransformer uriTransformer) {
		this.queuedExecutorService = queuedExecutorService;
		this.uriTransformer = uriTransformer;
	}

	/** @return the simple name of the file with the project state. */
	public String getPersistedFileName() {
		return ".projectstate";
	}

	/**
	 * The language version changes (i.e. must change) iff any of the persisted languages changes the serialization of
	 * its persisted state.
	 *
	 * @return the version string to distinguish persisted files with different serialization
	 */
	public String getLanguageVersion() {
		return "1";
	}

	/**
	 * Write the index state and a hash of the project state to disk in order to allow loading it again.
	 *
	 * @param project
	 *            the project
	 * @param state
	 *            the state to be written
	 */
	public void writeProjectState(ProjectConfigSnapshot project, ImmutableProjectState state) {
		queuedExecutorService.submitAndCancelPrevious(Tuples.create(ProjectStatePersister.class, project.getName()),
				"writeProjectState", (cancelIndicator) -> {
					if (!cancelIndicator.isCanceled()) {
						URI baseURI = getBaseURI(project);
						File file = getDataFile(project);
						try (OutputStream nativeOut = Files.asByteSink(file).openBufferedStream()) {
							writeProjectState(baseURI, nativeOut, state);
						} catch (IOException e) {
							e.printStackTrace();
							if (file.isFile()) {
								file.delete();
							}
						}
					}
					return null;
				});
	}

	/**
	 * @param stream
	 *            the output stream. Will not be closed.
	 * @param state
	 *            the state to be written
	 * @throws IOException
	 *             if things go bananas.
	 */
	public void writeProjectState(URI baseURI, OutputStream stream, ImmutableProjectState state)
			throws IOException {

		String languageVersion = getLanguageVersion();
		LOG.info("write project state (file version " + languageVersion + ")");
		stream.write(CURRENT_VERSION);
		try (DataOutputStream output = new DataOutputStream(
				new BufferedOutputStream(new GZIPOutputStream(stream, 8192)))) {

			output.writeUTF(languageVersion);

			writeResourceDescriptions(state, baseURI, output);

			writeFileMappings(state, output);

			writeFingerprints(state, output);

			writeValidationIssues(state, baseURI, output);

			writeDependencies(state, output);
		}
	}

	private void writeResourceDescriptions(ImmutableProjectState state, URI baseURI, DataOutput output)
			throws IOException {
		Iterable<IResourceDescription> descriptions = state.getResourceDescriptions().getAllResourceDescriptions();
		output.writeInt(Iterables.size(descriptions));
		for (IResourceDescription description : descriptions) {
			if (description instanceof SerializableResourceDescription) {
				writeResourceDescription((SerializableResourceDescription) description, baseURI, output);
			} else {
				throw new IOException("Unexpected type: " + description.getClass().getName());
			}
		}
	}

	private void writeResourceDescription(SerializableResourceDescription description, URI baseURI, DataOutput output)
			throws IOException {

		// description.writeExternal(output);
		// relies on writeObject which is very slow

		output.writeUTF(uriTransformer.serialize(baseURI, description.getURI()));
		writeEObjectDescriptions(description, baseURI, output);
		writeReferenceDescriptions(description, baseURI, output);
		writeImportedNames(description, output);
	}

	private void writeImportedNames(SerializableResourceDescription resourceDescription, DataOutput output)
			throws IOException {

		List<QualifiedName> importedNames = IterableExtensions.toList(resourceDescription.getImportedNames());
		output.writeInt(importedNames.size());
		for (QualifiedName importedName : importedNames) {
			writeQualifiedName(importedName, output);
		}
	}

	private void writeReferenceDescriptions(SerializableResourceDescription resourceDescription, URI baseURI,
			DataOutput output) throws IOException {

		List<SerializableReferenceDescription> references = resourceDescription.getReferences();
		output.writeInt(references.size());
		for (SerializableReferenceDescription reference : references) {
			output.writeUTF(uriTransformer.serialize(baseURI, reference.getSourceEObjectUri()));
			output.writeUTF(uriTransformer.serialize(baseURI, reference.getTargetEObjectUri()));
			output.writeUTF(uriTransformer.serialize(baseURI, reference.getContainerEObjectURI()));
			output.writeUTF(uriTransformer.serialize(baseURI, EcoreUtil.getURI(reference.getEReference())));
			output.writeInt(reference.getIndexInList());
		}
	}

	private void writeEObjectDescriptions(SerializableResourceDescription resourceDescription, URI baseURI,
			DataOutput output) throws IOException {

		List<SerializableEObjectDescription> objects = resourceDescription.getDescriptions();
		output.writeInt(objects.size());
		for (SerializableEObjectDescription object : objects) {
			output.writeUTF(uriTransformer.serialize(baseURI, object.getEObjectURI()));
			output.writeUTF(uriTransformer.serialize(baseURI, EcoreUtil.getURI(object.getEClass())));
			QualifiedName qn = object.getQualifiedName();
			writeQualifiedName(qn, output);
			Map<String, String> userData = object.getUserData();
			if (userData != null) {
				output.writeInt(userData.size());
				for (Map.Entry<String, String> entry : userData.entrySet()) {
					output.writeUTF(entry.getKey());
					writeUserDataValue(entry.getValue(), output);
				}
			} else {
				output.writeInt(0);
			}
		}
	}

	private void writeUserDataValue(String value, DataOutput output) throws IOException {
		// User data tends to be very long but the value in output.writeUTF is written as a short
		// therefore we need to do it manually for this string
		byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
		output.writeInt(bytes.length);
		output.write(bytes);
	}

	private void writeQualifiedName(QualifiedName qualifiedName, DataOutput output) throws IOException {
		output.writeInt(qualifiedName.getSegmentCount());
		for (int i = 0, max = qualifiedName.getSegmentCount(); i < max; i++) {
			output.writeUTF(qualifiedName.getSegment(i));
		}
	}

	private void writeFileMappings(ImmutableProjectState state, DataOutputStream output) throws IOException {
		state.getFileMappings().writeExternal(output);
	}

	private void writeFingerprints(ImmutableProjectState state, DataOutput output) throws IOException {
		Collection<HashedFileContent> files = state.getFileHashes().values();
		output.writeInt(files.size());
		for (HashedFileContent fingerprint : files) {
			fingerprint.write(output);
		}
	}

	private void writeValidationIssues(ImmutableProjectState state, URI baseURI, DataOutput output)
			throws IOException {
		Set<URI> allSources = state.getValidationIssues().keySet();
		int numberSources = allSources.size();
		output.writeInt(numberSources);
		for (URI source : allSources) {
			Collection<? extends LSPIssue> issues = state.getValidationIssues().get(source);

			output.writeUTF(uriTransformer.serialize(baseURI, source));

			int numberIssues = issues.size();
			output.writeInt(numberIssues);
			for (LSPIssue issue : issues) {
				issue.writeExternal(output);
			}
		}
	}

	private void writeDependencies(ImmutableProjectState state, DataOutput output) throws IOException {
		Set<String> allDeps = state.getDependencies().keySet();
		int numberDeps = allDeps.size();
		output.writeInt(numberDeps);
		for (String depName : allDeps) {
			boolean depExists = state.getDependencies().get(depName);

			output.writeUTF(depName);
			output.writeBoolean(depExists);
		}
	}

	/**
	 * Read the index state as it was written to disk for the given project.
	 *
	 * @param project
	 *            the project
	 */
	public ImmutableProjectState readProjectState(ProjectConfigSnapshot project) {
		URI baseURI = getBaseURI(project);
		File file = getDataFile(project);
		try {
			if (file.isFile()) {
				try (InputStream nativeIn = Files.asByteSource(file).openBufferedStream()) {
					ImmutableProjectState result = readProjectState(baseURI, nativeIn);
					if (result == null && file.isFile()) {
						file.delete();
					}
					return result;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			if (file.isFile()) {
				file.delete();
			}
		}
		return null;
	}

	/**
	 * @param stream
	 *            the stream to read from.
	 * @throws IOException
	 *             if things go bananas.
	 * @throws ClassNotFoundException
	 *             if things go bananas.
	 */
	public ImmutableProjectState readProjectState(URI baseURI, InputStream stream)
			throws IOException, ClassNotFoundException {

		int version = stream.read();
		if (version != CURRENT_VERSION) {
			return null;
		}

		try (DataInputStream input = new DataInputStream(new BufferedInputStream(new GZIPInputStream(stream, 8192)))) {
			String languageVersion = input.readUTF();
			if (!getLanguageVersion().equals(languageVersion)) {
				return null;
			}
			ResourceDescriptionsData resourceDescriptionsData = readResourceDescriptions(baseURI, input);

			XSource2GeneratedMapping fileMappings = readFileMappings(input);

			ImmutableMap<URI, HashedFileContent> fingerprints = readFingerprints(input);

			ImmutableListMultimap<URI, LSPIssue> validationIssues = readValidationIssues(baseURI, input);

			ImmutableMap<String, Boolean> dependencies = readDependencies(input);

			return ImmutableProjectState.withoutCopy(resourceDescriptionsData, fileMappings, fingerprints,
					validationIssues, dependencies);
		}
	}

	private ResourceDescriptionsData readResourceDescriptions(URI baseURI, DataInput input) throws IOException {
		List<IResourceDescription> descriptions = new ArrayList<>();
		int size = input.readInt();
		while (size > 0) {
			size--;
			descriptions.add(readResourceDescription(baseURI, input));
		}
		return new ResourceDescriptionsData(descriptions);
	}

	private ENamedElement readEcoreElement(URI baseURI, DataInput input) throws IOException {
		URI uri = uriTransformer.deserialize(baseURI, input.readUTF());
		EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(uri.trimFragment().toString());
		if (ePackage != null) {
			Resource resource = ePackage.eResource();
			return (ENamedElement) resource.getEObject(uri.fragment());
		}
		return null;
	}

	private SerializableResourceDescription readResourceDescription(URI baseURI, DataInput input) throws IOException {
		SerializableResourceDescription result = new SerializableResourceDescription();
		result.setURI(uriTransformer.deserialize(baseURI, input.readUTF()));
		result.setDescriptions(readEObjectDescriptions(baseURI, input));
		result.setReferences(readReferenceDescriptions(baseURI, input));
		result.setImportedNames(readImportedNames(input));
		return result;
	}

	private List<QualifiedName> readImportedNames(DataInput input) throws IOException {
		int size = input.readInt();
		if (size == 0) {
			return Collections.emptyList();
		}
		List<QualifiedName> result = new ArrayList<>(size);
		while (size > 0) {
			size--;
			result.add(readQualifiedName(input));
		}
		return result;
	}

	private List<SerializableReferenceDescription> readReferenceDescriptions(URI baseURI, DataInput input)
			throws IOException {

		int size = input.readInt();
		if (size == 0) {
			return Collections.emptyList();
		}
		List<SerializableReferenceDescription> result = new ArrayList<>(size);
		while (size > 0) {
			size--;
			SerializableReferenceDescription reference = new SerializableReferenceDescription();
			reference.setSourceEObjectUri(uriTransformer.deserialize(baseURI, input.readUTF()));
			reference.setTargetEObjectUri(uriTransformer.deserialize(baseURI, input.readUTF()));
			reference.setContainerEObjectURI(uriTransformer.deserialize(baseURI, input.readUTF()));
			reference.setEReference((EReference) readEcoreElement(baseURI, input));
			reference.setIndexInList(input.readInt() - 1);
			result.add(reference);
		}
		return result;
	}

	private List<SerializableEObjectDescription> readEObjectDescriptions(URI baseURI, DataInput input)
			throws IOException {

		int size = input.readInt();
		if (size == 0) {
			return Collections.emptyList();
		}
		List<SerializableEObjectDescription> result = new ArrayList<>(size);
		while (size > 0) {
			size--;
			SerializableEObjectDescription object = new SerializableEObjectDescription();
			object.setEObjectURI(uriTransformer.deserialize(baseURI, input.readUTF()));
			object.setEClass((EClass) readEcoreElement(baseURI, input));
			object.setQualifiedName(readQualifiedName(input));
			int userDataSize = input.readInt();
			HashMap<String, String> userData = new HashMap<>();
			while (userDataSize > 0) {
				userDataSize--;
				String key = input.readUTF();
				userData.put(key, readUserDataValue(input));
			}
			object.setUserData(userData);
			result.add(object);
		}
		return result;
	}

	private String readUserDataValue(DataInput input) throws IOException {
		byte[] value = new byte[input.readInt()];
		input.readFully(value);
		return new String(value, StandardCharsets.UTF_8);
	}

	private QualifiedName readQualifiedName(DataInput input) throws IOException {
		int size = input.readInt();
		QualifiedName.Builder builder = new QualifiedName.Builder(size);
		while (size > 0) {
			size--;
			builder.add(input.readUTF());
		}
		return builder.build();
	}

	private XSource2GeneratedMapping readFileMappings(DataInput input) throws IOException {
		XSource2GeneratedMapping fileMappings = new XSource2GeneratedMapping();
		fileMappings.readExternal(input);
		return fileMappings;
	}

	private ImmutableMap<URI, HashedFileContent> readFingerprints(DataInput input) throws IOException {
		int size = input.readInt();
		ImmutableMap.Builder<URI, HashedFileContent> fingerprints = ImmutableMap.builderWithExpectedSize(size);
		while (size > 0) {
			size--;
			HashedFileContent hashFileContent = new HashedFileContent(input);
			fingerprints.put(hashFileContent.getUri(), hashFileContent);
		}
		return fingerprints.build();
	}

	private ImmutableListMultimap<URI, LSPIssue> readValidationIssues(URI baseURI, DataInput input)
			throws IOException {

		int numberOfSources = input.readInt();
		ImmutableListMultimap.Builder<URI, LSPIssue> validationIssues = ImmutableListMultimap.builder();
		while (numberOfSources > 0) {
			numberOfSources--;
			URI source = uriTransformer.deserialize(baseURI, input.readUTF());
			int numberOfIssues = input.readInt();
			while (numberOfIssues > 0) {
				numberOfIssues--;
				LSPIssue issue = new LSPIssue();
				issue.readExternal(input);
				validationIssues.put(source, issue);
			}
		}
		return validationIssues.build();
	}

	private ImmutableMap<String, Boolean> readDependencies(DataInput input) throws IOException {

		int numberOfDeps = input.readInt();
		ImmutableMap.Builder<String, Boolean> dependencies = ImmutableMap.builder();
		while (numberOfDeps > 0) {
			numberOfDeps--;
			String depName = input.readUTF();
			boolean depExists = input.readBoolean();
			dependencies.put(depName, depExists);
		}
		return dependencies.build();
	}

	private File getDataFile(ProjectConfigSnapshot project) {
		URI fileName = getFileName(project);
		File file = URIUtils.toFile(fileName);
		return file;
	}

	/** @return the file URI of the persisted index */
	public URI getFileName(ProjectConfigSnapshot project) {
		String fileName = getPersistedFileName();
		URI fileNameURI = URI.createFileURI(fileName);
		URI baseURI = getBaseURI(project);
		return new FileURI(fileNameURI.resolve(baseURI)).toURI();
	}

	private URI getBaseURI(ProjectConfigSnapshot project) {
		return project.getPath();
	}

	/** Helper class to serialize and deserizalize URIs */
	static public class URITransformer {
		enum URIPrefix {
			/** Unknown prefix. Used only in the {@link #parsePrefix(String)} */
			Unknown(null),
			/** URI that was relativized */
			Relative("R!"),
			/** URI that was not changed */
			Absolute("A!");

			String id;

			URIPrefix(String id) {
				if (id != null) {
					Preconditions.checkArgument(id.length() == 2);
					Preconditions.checkArgument(id.charAt(1) == '!');
				}
				this.id = id;
			}

			@Override
			public String toString() {
				return this.id;
			}
		}

		/** @return a string to be written to the project state file from a given absolute uri and base directory */
		String serialize(URI baseURI, URI absoluteURI) {
			if (absoluteURI.isFile() && !absoluteURI.isRelative()) {
				URI relativeURI = absoluteURI.deresolve(baseURI);
				return URIPrefix.Relative + relativeURI.toString();
			}
			return URIPrefix.Absolute + absoluteURI.toString();
		}

		/** @return a {@link URI} parsed from inside a project state file */
		URI deserialize(URI baseURI, String relativeString) {
			URIPrefix prefix = parsePrefix(relativeString);
			URI parsedURI = parseURI(relativeString);
			switch (prefix) {
			case Absolute:
				return parsedURI;
			case Relative:
				URI absoluteURI = parsedURI.resolve(baseURI);
				return new FileURI(absoluteURI).toURI();
			default:
				return parsedURI;
			}
		}

		/** @return the parsed prefix or {@link URIPrefix#Unknown} otherwise */
		URIPrefix parsePrefix(String str) {
			String prefixStr = str.substring(0, 2);
			for (URIPrefix prefix : URIPrefix.values()) {
				if (prefix != URIPrefix.Unknown) {
					if (prefix.id.equals(prefixStr)) {
						return prefix;
					}
				}
			}
			return URIPrefix.Unknown;
		}

		/** @return the URI */
		URI parseURI(String str) {
			String uriStr = str.substring(2);
			URI parsedURI = URI.createURI(uriStr);
			return parsedURI;
		}
	}

}
