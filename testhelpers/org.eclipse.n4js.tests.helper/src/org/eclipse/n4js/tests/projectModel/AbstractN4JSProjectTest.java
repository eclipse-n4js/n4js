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

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import org.eclipse.n4js.projectModel.IN4JSArchive;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;

/**
 */
public abstract class AbstractN4JSProjectTest extends AbstractProjectModelTest {

	/***/
	protected abstract IN4JSCore getN4JSCore();

	@SuppressWarnings("javadoc")
	@Test
	public void testGetLocation_01() {
		IN4JSProject project = getN4JSCore().create(myProjectURI);
		assertEquals(myProjectURI, project.getLocation());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetLocation_02() {
		URI doesNotExist = myProjectURI.trimSegments(1).appendSegment("doesNotExist");
		IN4JSProject project = getN4JSCore().create(doesNotExist);
		assertEquals(doesNotExist, project.getLocation());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetSourceContainers_01() {
		IN4JSProject project = getN4JSCore().create(myProjectURI);
		ImmutableList<? extends IN4JSSourceContainer> sourceContainers = project.getSourceContainers();
		assertEquals(1, sourceContainers.size());
		assertEquals("src", sourceContainers.get(0).getRelativeLocation());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetSourceContainers_02() {
		URI doesNotExist = myProjectURI.trimSegments(1).appendSegment("doesNotExist");
		IN4JSProject project = getN4JSCore().create(doesNotExist);
		ImmutableList<? extends IN4JSSourceContainer> sourceContainers = project.getSourceContainers();
		assertEquals(0, sourceContainers.size());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetSourceContainers_03() {
		IN4JSProject project = getN4JSCore().create(myProjectURI);
		ImmutableList<? extends IN4JSSourceContainer> first = project.getSourceContainers();
		ImmutableList<? extends IN4JSSourceContainer> second = project.getSourceContainers();
		assertEquals(first, second);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetDependencies_01() {
		IN4JSProject project = getN4JSCore().create(myProjectURI);
		ImmutableList<? extends IN4JSProject> dependencies = project.getDependencies();
		assertEquals(1, dependencies.size());
		assertEquals(libProjectURI.lastSegment(), dependencies.get(0).getLocation().lastSegment());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetDependencies_02() {
		URI doesNotExist = myProjectURI.trimSegments(1).appendSegment("doesNotExist");
		IN4JSProject project = getN4JSCore().create(doesNotExist);
		ImmutableList<? extends IN4JSProject> dependencies = project.getDependencies();
		assertEquals(0, dependencies.size());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetDependencies_03() {
		IN4JSProject project = getN4JSCore().create(myProjectURI);
		ImmutableList<? extends IN4JSProject> first = project.getDependencies();
		ImmutableList<? extends IN4JSProject> second = project.getDependencies();
		assertEquals(first, second);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetLibraries_01() {
		IN4JSProject project = getN4JSCore().create(myProjectURI);
		ImmutableList<? extends IN4JSArchive> libraries = project.getLibraries();
		assertEquals(1, libraries.size());
		assertEquals(archiveFileURI, libraries.get(0).getLocation());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetLibraries_02() {
		URI doesNotExist = myProjectURI.trimSegments(1).appendSegment("doesNotExist");
		IN4JSProject project = getN4JSCore().create(doesNotExist);
		ImmutableList<? extends IN4JSArchive> libraries = project.getLibraries();
		assertEquals(0, libraries.size());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetLibraries_03() {
		IN4JSProject project = getN4JSCore().create(myProjectURI);
		ImmutableList<? extends IN4JSArchive> first = project.getLibraries();
		ImmutableList<? extends IN4JSArchive> second = project.getLibraries();
		assertEquals(first, second);
	}
}
