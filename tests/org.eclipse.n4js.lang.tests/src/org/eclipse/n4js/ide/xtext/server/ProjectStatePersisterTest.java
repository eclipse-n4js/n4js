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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.build.HashedFileContent;
import org.eclipse.n4js.ide.xtext.server.build.ImmutableProjectState;
import org.eclipse.n4js.ide.xtext.server.build.ProjectStatePersister;
import org.eclipse.n4js.ide.xtext.server.build.ProjectStatePersister.URITransformer;
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.xtext.builder.builderState.BuilderStateFactory;
import org.eclipse.xtext.builder.builderState.impl.EObjectDescriptionImpl;
import org.eclipse.xtext.builder.builderState.impl.ResourceDescriptionImpl;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;

/** */
@SuppressWarnings({ "restriction", "deprecation" })
public class ProjectStatePersisterTest {

	static final URI BASE_URI = new FileURI(new File(".").getAbsoluteFile()).toURI();

	private static Level oldLevel = null;

	/***/
	@BeforeClass
	public static void suppressNoisyLogging() {
		Logger rootLogger = Logger.getRootLogger();
		oldLevel = rootLogger.getLevel();
		rootLogger.setLevel(Level.WARN);
	}

	/***/
	@AfterClass
	public static void restoreLogLevel() {
		Logger.getRootLogger().setLevel(Objects.requireNonNull(oldLevel));
	}

	ImmutableProjectState createProjectState() {
		return ImmutableProjectState.empty();
	}

	ImmutableProjectState createProjectState(ResourceDescriptionsData index, XSource2GeneratedMapping fileMappings,
			Map<URI, HashedFileContent> fileHashs, ListMultimap<URI, LSPIssue> validationIssues,
			Map<String, Boolean> dependencies) {

		index = (index != null) ? index
				: new ResourceDescriptionsData(CollectionLiterals.<IResourceDescription> emptySet());
		fileMappings = (fileMappings != null) ? fileMappings : new XSource2GeneratedMapping();
		fileHashs = (fileHashs != null) ? fileHashs : Collections.emptyMap();
		validationIssues = (validationIssues != null) ? validationIssues : ImmutableListMultimap.of();
		dependencies = (dependencies != null) ? dependencies : ImmutableMap.of();
		return ImmutableProjectState.copyFrom(index, fileMappings, fileHashs, validationIssues, dependencies);
	}

	/** */
	@Test
	public void testWriteAndReadNoData() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister(null, new URITransformer());
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImmutableProjectState state = createProjectState();
		testMe.writeProjectState(BASE_URI, output, state);
		AtomicBoolean didCall = new AtomicBoolean();
		ImmutableProjectState pState = testMe.readProjectState(BASE_URI,
				new ByteArrayInputStream(output.toByteArray()));
		Assert.assertTrue(pState.getFileMappings().getAllGenerated().isEmpty());
		Assert.assertTrue(pState.getResourceDescriptions().isEmpty());
		Assert.assertTrue(pState.getFileHashes().isEmpty());
		didCall.set(true);
		Assert.assertTrue(didCall.get());
	}

	/** */
	@Test(expected = ZipException.class)
	public void testWriteAndReadCorruptedStream() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister(null, new URITransformer());
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImmutableProjectState state = createProjectState();
		testMe.writeProjectState(BASE_URI, output, state);
		byte[] bytes = output.toByteArray();
		bytes[12]++;
		testMe.readProjectState(BASE_URI, new ByteArrayInputStream(bytes));
	}

	/** */
	@Test
	public void testWriteAndReadFileVersionMismatch() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister(null, new URITransformer());
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImmutableProjectState state = createProjectState();
		testMe.writeProjectState(BASE_URI, output, state);
		byte[] bytes = output.toByteArray();
		bytes[0]++;
		ImmutableProjectState pState = testMe.readProjectState(BASE_URI, new ByteArrayInputStream(bytes));
		Assert.assertTrue(pState == null);
	}

	/** */
	@Test
	public void testWriteAndReadLangVersionMismatch() throws IOException, ClassNotFoundException {
		AtomicReference<String> languageVersion = new AtomicReference<>("1");
		ProjectStatePersister testMe = new ProjectStatePersister(null, new URITransformer()) {
			@Override
			public String getLanguageVersion() {
				return languageVersion.get();
			}
		};
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		ImmutableProjectState state = createProjectState();
		testMe.writeProjectState(BASE_URI, output, state);
		languageVersion.set("2");
		ImmutableProjectState pState = testMe.readProjectState(BASE_URI,
				new ByteArrayInputStream(output.toByteArray()));
		Assert.assertTrue(pState == null);
	}

	/**
	 * calls Test {@link #writeAndReadWithDataForFileScheme(String)} with scheme {@code other}
	 */
	@Test
	public void testWriteAndReadWithDataForOtherScheme() throws IOException, ClassNotFoundException {
		writeAndReadWithDataForFileScheme("other:/");
	}

	/**
	 * calls Test {@link #writeAndReadWithDataForFileScheme(String)} with scheme {@code file}
	 */
	@Test
	public void testWriteAndReadWithDataForFileScheme() throws IOException, ClassNotFoundException {
		writeAndReadWithDataForFileScheme("file://");
	}

	/**
	 * A little bit of a flawed test since we modify the internals of the ImmutableProjectState behind its back.
	 */
	private void writeAndReadWithDataForFileScheme(String scheme) throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister(null, new URITransformer());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		ImmutableProjectState state = createProjectState();
		ResourceDescriptionsData index = (ResourceDescriptionsData) state.getResourceDescriptions();
		XSource2GeneratedMapping fileMappings = state.getFileMappings();
		ResourceDescriptionImpl resourceDescription = (ResourceDescriptionImpl) BuilderStateFactory.eINSTANCE
				.createResourceDescription();
		resourceDescription.setURI(URI.createURI(scheme + "uri"));
		URI uri = resourceDescription.getURI();
		EObjectDescriptionImpl objectDescription = (EObjectDescriptionImpl) BuilderStateFactory.eINSTANCE
				.createEObjectDescription();
		objectDescription.setName(QualifiedName.create("some", "name"));
		objectDescription.setFragment("some/object#/1");
		objectDescription.setEClass(objectDescription.eClass());
		resourceDescription.getExportedObjects().add(objectDescription);
		index.addDescription(uri, SerializableResourceDescription.createCopy(resourceDescription));
		URI sourceURI = URI.createURI(scheme + "source");
		URI targetURI = URI.createURI(scheme + "target");
		fileMappings.addSource2Generated(sourceURI, targetURI, "outputty");
		URI hashUri = URI.createURI(scheme + "hash");
		Map<URI, HashedFileContent> fingerprints = Collections.singletonMap(hashUri,
				new HashedFileContent(hashUri, 123));

		state = createProjectState(index, fileMappings, fingerprints, null, null);
		testMe.writeProjectState(BASE_URI, output, state);
		ByteArrayInputStream outputStream = new ByteArrayInputStream(output.toByteArray());
		ImmutableProjectState pState = testMe.readProjectState(BASE_URI, outputStream);

		Assert.assertEquals(fingerprints, pState.getFileHashes());
		List<URI> targets = pState.getFileMappings().getGenerated(sourceURI);
		Assert.assertEquals(1, targets.size());
		Assert.assertEquals(targetURI, targets.get(0));
		Assert.assertEquals("outputty", pState.getFileMappings().getOutputConfigName(targetURI));

		ResourceDescriptionsData resourceDescriptions2 = (ResourceDescriptionsData) pState.getResourceDescriptions();
		Set<URI> allIndexedUris2 = resourceDescriptions2.getAllURIs();
		IResourceDescription resourceDescription2 = resourceDescriptions2.getResourceDescription(uri);
		IEObjectDescription expObjDescr2 = resourceDescription2.getExportedObjects().iterator().next();

		Assert.assertEquals(1, allIndexedUris2.size());
		Assert.assertTrue(allIndexedUris2.contains(uri));
		IEObjectDescription expObjDescr = resourceDescription.getExportedObjects().iterator().next();
		Assert.assertEquals(expObjDescr.getEObjectURI(), expObjDescr2.getEObjectURI());
	}

	/** */
	@Test
	public void testWriteAndReadValidationIssues() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister(null, new URITransformer());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		URI source1 = URI.createURI("some:/source1");
		URI source2 = URI.createURI("some:/source2");

		LSPIssue src1Issue1 = new LSPIssue();
		LSPIssue src2Issue1 = new LSPIssue();
		LSPIssue src2Issue2 = new LSPIssue();
		setValues(src1Issue1, "src1Issue1", 1, 1, Severity.ERROR);
		setValues(src2Issue1, "src2Issue1", 2, 1, Severity.WARNING);
		setValues(src2Issue2, "src2Issue2", 2, 2, Severity.INFO);

		// first set values of issues; then add issues to hash map
		ListMultimap<URI, LSPIssue> issueMap = ArrayListMultimap.create();
		issueMap.put(source1, src1Issue1);
		issueMap.put(source2, src2Issue1);
		issueMap.put(source2, src2Issue2);

		ImmutableProjectState state = createProjectState(null, null, null, issueMap, null);
		testMe.writeProjectState(BASE_URI, output, state);
		byte[] bytes = output.toByteArray();
		ImmutableProjectState pState = testMe.readProjectState(BASE_URI, new ByteArrayInputStream(bytes));
		Assert.assertTrue(pState != null);
		Assert.assertEquals(issueMap, pState.getValidationIssues());
	}

	/***/
	@Test
	public void testWriteAndReadDependencies() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister(null, new URITransformer());
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		Map<String, Boolean> dependencies = Map.of(
				"dep1", true,
				"dep2", false);

		ImmutableProjectState state = createProjectState(null, null, null, null, dependencies);
		testMe.writeProjectState(BASE_URI, output, state);
		byte[] bytes = output.toByteArray();
		ImmutableProjectState pState = testMe.readProjectState(BASE_URI, new ByteArrayInputStream(bytes));

		Assert.assertEquals(2, pState.getDependencies().size());
		Assert.assertEquals(dependencies.keySet(), pState.getDependencies().keySet());
		Assert.assertEquals(dependencies.entrySet(), pState.getDependencies().entrySet());
	}

	private void setValues(LSPIssue issue, String varName, int srcNo, int issueNo, Severity severity) {
		int prefix = srcNo * 100 + issueNo * 10;
		issue.setCode(varName + ".code");
		issue.setColumn(prefix++);
		issue.setColumnEnd(prefix++);
		issue.setLength(prefix++);
		issue.setLineNumber(prefix++);
		issue.setLineNumberEnd(prefix++);
		issue.setMessage(varName + ".message");
		issue.setOffset(prefix++);
		issue.setSeverity(severity);
		issue.setSyntaxError(false);
		issue.setUriToProblem(URI.createURI("some:/" + varName + "/uriToProblem"));
	}
}
