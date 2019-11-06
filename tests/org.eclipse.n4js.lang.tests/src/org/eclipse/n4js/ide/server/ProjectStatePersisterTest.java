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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ide.server.ProjectStatePersister.PersistedState;
import org.eclipse.n4js.ide.xtext.server.build.XIndexState;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.xtext.builder.builderState.BuilderStateFactory;
import org.eclipse.xtext.builder.builderState.impl.EObjectDescriptionImpl;
import org.eclipse.xtext.builder.builderState.impl.ResourceDescriptionImpl;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.persistence.SerializableResourceDescription;
import org.junit.Assert;
import org.junit.Test;

/** */
@SuppressWarnings("restriction")
public class ProjectStatePersisterTest {

	/** */
	@Test
	public void testWriteAndReadNoData() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();
		testMe.writeProjectState(output, languageVersion, new XIndexState(), Collections.emptyList());
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
	@Test(expected = StreamCorruptedException.class)
	public void testWriteAndReadCorruptedStream() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();
		testMe.writeProjectState(output, languageVersion, new XIndexState(), Collections.emptyList());
		byte[] bytes = output.toByteArray();
		bytes[12]++;
		PersistedState pState = testMe.readProjectState(new ByteArrayInputStream(bytes), languageVersion);
		Assert.assertTrue(pState.fileHashs.isEmpty());
	}

	/** */
	@Test
	public void testWriteAndReadFileVersionMismatch() throws IOException, ClassNotFoundException {
		ProjectStatePersister testMe = new ProjectStatePersister();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String languageVersion = N4JSLanguageUtils.getLanguageVersion();
		testMe.writeProjectState(output, languageVersion, new XIndexState(), Collections.emptyList());
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
		testMe.writeProjectState(output, languageVersion, new XIndexState(), Collections.emptyList());
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

		testMe.writeProjectState(output, languageVersion, index, fingerprints);
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
}
