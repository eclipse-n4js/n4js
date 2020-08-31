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
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.build.HashedFileContent;
import org.eclipse.n4js.ide.xtext.server.build.ImmutableProjectState;
import org.eclipse.n4js.ide.xtext.server.build.ProjectStatePersister;
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.xtext.server.LSPIssue;
import org.eclipse.xtext.builder.builderState.BuilderStateFactory;
import org.eclipse.xtext.builder.builderState.impl.EObjectDescriptionImpl;
import org.eclipse.xtext.builder.builderState.impl.ResourceDescriptionImpl;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

/** */
@SuppressWarnings({ "restriction", "deprecation" })
public class ProjectStatePersisterTest {

	static final URI BASE_URI = new FileURI(new File(".").getAbsoluteFile()).toURI();

	ImmutableProjectState createProjectState() {
		return ImmutableProjectState.empty();
	}

	ImmutableProjectState createProjectState(ResourceDescriptionsData index, XSource2GeneratedMapping fileMappings,
			Map<URI, HashedFileContent> fileHashs, ListMultimap<URI, LSPIssue> validationIssues) {

		index = (index != null) ? index
				: new ResourceDescriptionsData(CollectionLiterals.<IResourceDescription> emptySet());
		fileMappings = (fileMappings != null) ? fileMappings : new XSource2GeneratedMapping();
		fileHashs = (fileHashs != null) ? fileHashs : Collections.emptyMap();
		validationIssues = (validationIssues != null) ? validationIssues : ImmutableListMultimap.of();
		return ImmutableProjectState.copyFrom(index, fileMappings, fileHashs, validationIssues);
	}

	/** */
	@Test
	public void testWriteAndReadNoData() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
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
		ProjectStatePersister testMe = new ProjectStatePersister();
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
		ProjectStatePersister testMe = new ProjectStatePersister();
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
		ProjectStatePersister testMe = new ProjectStatePersister() {
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
	 * A little bit of a flawed test since we modify the internals of the ImmutableProjectState behind its back.
	 */
	@Test
	public void testWriteAndReadWithData() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		ImmutableProjectState state = createProjectState();
		ResourceDescriptionsData index = (ResourceDescriptionsData) state.getResourceDescriptions();
		XSource2GeneratedMapping fileMappings = state.getFileMappings();
		ResourceDescriptionImpl resourceDescription = (ResourceDescriptionImpl) BuilderStateFactory.eINSTANCE
				.createResourceDescription();
		resourceDescription.setURI(URI.createURI("some:/uri"));
		EObjectDescriptionImpl objectDescription = (EObjectDescriptionImpl) BuilderStateFactory.eINSTANCE
				.createEObjectDescription();
		objectDescription.setName(QualifiedName.create("some", "name"));
		objectDescription.setFragment("some/object");
		objectDescription.setEClass(objectDescription.eClass());
		resourceDescription.getExportedObjects().add(objectDescription);
		index.addDescription(resourceDescription.getURI(),
				SerializableResourceDescription.createCopy(resourceDescription));
		URI sourceURI = URI.createURI("some:/source");
		URI targetURI = URI.createURI("some:/target");
		fileMappings.addSource2Generated(sourceURI, targetURI, "outputty");
		URI hashUri = URI.createURI("some:/hash");
		Map<URI, HashedFileContent> fingerprints = Collections.singletonMap(hashUri,
				new HashedFileContent(hashUri, 123));

		state = createProjectState(index, fileMappings, fingerprints, null);
		testMe.writeProjectState(BASE_URI, output, state);
		ByteArrayInputStream outputStream = new ByteArrayInputStream(output.toByteArray());
		ImmutableProjectState pState = testMe.readProjectState(BASE_URI, outputStream);

		Assert.assertEquals(fingerprints, pState.getFileHashes());
		List<URI> targets = pState.getFileMappings().getGenerated(sourceURI);
		Assert.assertEquals(1, targets.size());
		Assert.assertEquals(targetURI, targets.get(0));
		Assert.assertEquals("outputty", pState.getFileMappings().getOutputConfigName(targetURI));

		Set<URI> allIndexedUris = ((ResourceDescriptionsData) pState.getResourceDescriptions()).getAllURIs();
		Assert.assertEquals(1, allIndexedUris.size());
		Assert.assertTrue(allIndexedUris.contains(resourceDescription.getURI()));
	}

	/** */
	@Test
	public void testWriteAndReadValidationIssues() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
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

		ImmutableProjectState state = createProjectState(null, null, null, issueMap);
		testMe.writeProjectState(BASE_URI, output, state);
		byte[] bytes = output.toByteArray();
		ImmutableProjectState pState = testMe.readProjectState(BASE_URI, new ByteArrayInputStream(bytes));
		Assert.assertTrue(pState != null);
		Assert.assertEquals(issueMap, pState.getValidationIssues());
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
