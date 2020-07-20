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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.build.HashedFileContent;
import org.eclipse.n4js.ide.xtext.server.build.ProjectStatePersister;
import org.eclipse.n4js.ide.xtext.server.build.ProjectStatePersister.ProjectState;
import org.eclipse.n4js.ide.xtext.server.build.XSource2GeneratedMapping;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
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

/** */
@SuppressWarnings("restriction")
public class ProjectStatePersisterTest {

	ProjectState createProjectState() {
		return createProjectState(null, null, null, null);
	}

	ProjectState createProjectState(ResourceDescriptionsData index, XSource2GeneratedMapping fileMappings,
			Map<URI, HashedFileContent> fileHashs, Map<URI, ? extends Collection<LSPIssue>> validationIssues) {

		index = (index != null) ? index
				: new ResourceDescriptionsData(CollectionLiterals.<IResourceDescription> emptySet());
		fileMappings = (fileMappings != null) ? fileMappings : new XSource2GeneratedMapping();
		fileHashs = (fileHashs != null) ? fileHashs : Collections.emptyMap();
		validationIssues = (validationIssues != null) ? validationIssues : Collections.emptyMap();
		return new ProjectState(index, fileMappings, fileHashs, validationIssues);
	}

	/** */
	@Test
	public void testWriteAndReadNoData() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();
		ProjectState state = createProjectState();
		testMe.writeProjectState(output, languageVersion, state);
		AtomicBoolean didCall = new AtomicBoolean();
		ProjectState pState = testMe.readProjectState(new ByteArrayInputStream(output.toByteArray()),
				languageVersion);
		Assert.assertTrue(pState.fileMappings.getAllGenerated().isEmpty());
		Assert.assertTrue(pState.index.isEmpty());
		Assert.assertTrue(pState.fileHashs.isEmpty());
		didCall.set(true);
		Assert.assertTrue(didCall.get());
	}

	/** */
	@Test(expected = ZipException.class)
	public void testWriteAndReadCorruptedStream() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();
		ProjectState state = createProjectState();
		testMe.writeProjectState(output, languageVersion, state);
		byte[] bytes = output.toByteArray();
		bytes[12]++;
		testMe.readProjectState(new ByteArrayInputStream(bytes), languageVersion);
	}

	/** */
	@Test
	public void testWriteAndReadFileVersionMismatch() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();
		ProjectState state = createProjectState();
		testMe.writeProjectState(output, languageVersion, state);
		byte[] bytes = output.toByteArray();
		bytes[0]++;
		ProjectState pState = testMe.readProjectState(new ByteArrayInputStream(bytes), languageVersion);
		Assert.assertTrue(pState == null);
	}

	/** */
	@Test
	public void testWriteAndReadLangVersionMismatch() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();
		ProjectState state = createProjectState();
		testMe.writeProjectState(output, languageVersion, state);
		languageVersion += "XXX";
		ProjectState pState = testMe.readProjectState(new ByteArrayInputStream(output.toByteArray()),
				languageVersion);
		Assert.assertTrue(pState == null);
	}

	/** */
	@Test
	public void testWriteAndReadWithData() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();

		ProjectState state = createProjectState();
		ResourceDescriptionsData index = state.index;
		XSource2GeneratedMapping fileMappings = state.fileMappings;
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
		testMe.writeProjectState(output, languageVersion, state);
		ByteArrayInputStream outputStream = new ByteArrayInputStream(output.toByteArray());
		ProjectState pState = testMe.readProjectState(outputStream, languageVersion);

		Assert.assertEquals(fingerprints, pState.fileHashs);
		List<URI> targets = pState.fileMappings.getGenerated(sourceURI);
		Assert.assertEquals(1, targets.size());
		Assert.assertEquals(targetURI, targets.get(0));
		Assert.assertEquals("outputty", pState.fileMappings.getOutputConfigName(targetURI));

		Set<URI> allIndexedUris = pState.index.getAllURIs();
		Assert.assertEquals(1, allIndexedUris.size());
		Assert.assertTrue(allIndexedUris.contains(resourceDescription.getURI()));
	}

	/** */
	@Test
	public void testWriteAndReadValidationIssues() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();

		URI source1 = URI.createURI("some:/source1");
		URI source2 = URI.createURI("some:/source2");

		LSPIssue src1Issue1 = new LSPIssue();
		LSPIssue src2Issue1 = new LSPIssue();
		LSPIssue src2Issue2 = new LSPIssue();
		setValues(src1Issue1, "src1Issue1", 1, 1, Severity.ERROR);
		setValues(src2Issue1, "src2Issue1", 2, 1, Severity.WARNING);
		setValues(src2Issue2, "src2Issue2", 2, 2, Severity.INFO);

		// first set values of issues; then add issues to hash map
		Map<URI, List<LSPIssue>> issueMap = new HashMap<>();
		issueMap.put(source1, new ArrayList<>());
		issueMap.put(source2, new ArrayList<>());
		issueMap.get(source1).add(src1Issue1);
		issueMap.get(source2).add(src2Issue1);
		issueMap.get(source2).add(src2Issue2);

		ProjectState state = createProjectState(null, null, null, issueMap);
		testMe.writeProjectState(output, languageVersion, state);
		byte[] bytes = output.toByteArray();
		ProjectState pState = testMe.readProjectState(new ByteArrayInputStream(bytes), languageVersion);
		Assert.assertTrue(pState != null);
		Assert.assertEquals(issueMap, pState.validationIssues);
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
