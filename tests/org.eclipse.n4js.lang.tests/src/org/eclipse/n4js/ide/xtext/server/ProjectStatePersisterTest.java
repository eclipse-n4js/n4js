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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.xtext.server.ProjectStatePersister.PersistedState;
import org.eclipse.n4js.ide.xtext.server.build.XIndexState;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.builder.builderState.BuilderStateFactory;
import org.eclipse.xtext.builder.builderState.impl.EObjectDescriptionImpl;
import org.eclipse.xtext.builder.builderState.impl.ResourceDescriptionImpl;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/** */
@SuppressWarnings("restriction")
public class ProjectStatePersisterTest {

	/** */
	@Test
	public void testWriteAndReadNoData() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();
		testMe.writeProjectState(output, languageVersion, new XIndexState(), Collections.emptyList(),
				HashMultimap.create());
		AtomicBoolean didCall = new AtomicBoolean();
		PersistedState pState = testMe.readProjectState(new ByteArrayInputStream(output.toByteArray()),
				languageVersion);
		Assert.assertTrue(pState.indexState.getFileMappings().getAllGenerated().isEmpty());
		Assert.assertTrue(pState.indexState.getResourceDescriptions().isEmpty());
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
		testMe.writeProjectState(output, languageVersion, new XIndexState(), Collections.emptyList(),
				HashMultimap.create());
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
		testMe.writeProjectState(output, languageVersion, new XIndexState(), Collections.emptyList(),
				HashMultimap.create());
		byte[] bytes = output.toByteArray();
		bytes[0]++;
		PersistedState pState = testMe.readProjectState(new ByteArrayInputStream(bytes), languageVersion);
		Assert.assertTrue(pState == null);
	}

	/** */
	@Test
	public void testWriteAndReadLangVersionMismatch() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();
		testMe.writeProjectState(output, languageVersion, new XIndexState(), Collections.emptyList(),
				HashMultimap.create());
		languageVersion += "XXX";
		PersistedState pState = testMe.readProjectState(new ByteArrayInputStream(output.toByteArray()),
				languageVersion);
		Assert.assertTrue(pState == null);
	}

	/** */
	@Test
	public void testWriteAndReadWithData() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();

		XIndexState index = new XIndexState();
		ResourceDescriptionImpl resourceDescription = (ResourceDescriptionImpl) BuilderStateFactory.eINSTANCE
				.createResourceDescription();
		resourceDescription.setURI(URI.createURI("some:/uri"));
		EObjectDescriptionImpl objectDescription = (EObjectDescriptionImpl) BuilderStateFactory.eINSTANCE
				.createEObjectDescription();
		objectDescription.setName(QualifiedName.create("some", "name"));
		objectDescription.setFragment("some/object");
		objectDescription.setEClass(objectDescription.eClass());
		resourceDescription.getExportedObjects().add(objectDescription);
		index.getResourceDescriptions().addDescription(resourceDescription.getURI(),
				SerializableResourceDescription.createCopy(resourceDescription));
		URI sourceURI = URI.createURI("some:/source");
		URI targetURI = URI.createURI("some:/target");
		index.getFileMappings().addSource2Generated(sourceURI, targetURI, "outputty");
		Set<HashedFileContent> fingerprints = Collections
				.singleton(new HashedFileContent(URI.createURI("some:/hash"), 123));

		testMe.writeProjectState(output, languageVersion, index, fingerprints, HashMultimap.create());
		ByteArrayInputStream outputStream = new ByteArrayInputStream(output.toByteArray());
		PersistedState pState = testMe.readProjectState(outputStream, languageVersion);
		XIndexState indexState = pState.indexState;
		Collection<HashedFileContent> files = pState.fileHashs.values();

		Assert.assertEquals(fingerprints, new HashSet<>(files));
		List<URI> targets = indexState.getFileMappings().getGenerated(sourceURI);
		Assert.assertEquals(1, targets.size());
		Assert.assertEquals(targetURI, targets.get(0));
		Assert.assertEquals("outputty", indexState.getFileMappings().getOutputConfigName(targetURI));

		Set<URI> allIndexedUris = indexState.getResourceDescriptions().getAllURIs();
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
		Multimap<URI, LSPIssue> issueMap = HashMultimap.create();
		issueMap.put(source1, src1Issue1);
		issueMap.put(source2, src2Issue1);
		issueMap.put(source2, src2Issue2);

		testMe.writeProjectState(output, languageVersion, new XIndexState(), Collections.emptyList(), issueMap);
		byte[] bytes = output.toByteArray();
		PersistedState pState = testMe.readProjectState(new ByteArrayInputStream(bytes), languageVersion);
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
