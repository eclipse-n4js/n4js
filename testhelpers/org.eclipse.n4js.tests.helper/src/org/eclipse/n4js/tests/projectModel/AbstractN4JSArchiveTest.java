/**
 * Copyright (c) 2016 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.projectModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;

/**
 */
public abstract class AbstractN4JSArchiveTest extends AbstractProjectModelTest {

	/***/
	protected abstract IN4JSCore getN4JSCore();

	/***/
	protected IN4JSArchive getArchive() {
		IN4JSProject project = getN4JSCore().create(myProjectURI);
		assertEquals(1, project.getLibraries().size());
		return project.getLibraries().get(0);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetLocation() {
		IN4JSArchive archive = getArchive();
		assertEquals(archiveFileURI, archive.getLocation());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetArchiveName() {
		IN4JSArchive archive = getArchive();
		assertEquals(archiveProjectId + ".nfar", archive.getFileName());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetLibraryName() {
		IN4JSArchive archive = getArchive();
		assertEquals(archiveProjectId, archive.getProjectId());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetProject() {
		IN4JSArchive archive = getArchive();
		assertEquals(myProjectId, archive.getProject().getProjectId());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetSourceContainers_01() {
		IN4JSArchive archive = getArchive();
		ImmutableList<? extends IN4JSSourceContainer> sourceContainers = archive.getSourceContainers();
		assertEquals(1, sourceContainers.size());
		assertTrue(sourceContainers.get(0).isSource());
		assertEquals("src", sourceContainers.get(0).getRelativeLocation());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetSourceContainers_02() {
		IN4JSArchive archive = getArchive();
		ImmutableList<? extends IN4JSSourceContainer> first = archive.getSourceContainers();
		ImmutableList<? extends IN4JSSourceContainer> second = archive.getSourceContainers();
		assertEquals(first, second);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetLibraries() {
		IN4JSArchive archive = getArchive();
		archive.getReferencedLibraries();
		ImmutableList<? extends IN4JSArchive> dependencies = archive.getReferencedLibraries();
		assertEquals(0, dependencies.size());
	}
}
