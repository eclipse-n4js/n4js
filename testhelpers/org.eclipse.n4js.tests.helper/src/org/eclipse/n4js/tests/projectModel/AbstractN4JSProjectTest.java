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
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.IN4JSSourceContainer;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 */
public abstract class AbstractN4JSProjectTest<Loc extends SafeURI<Loc>> extends AbstractProjectModelTest<Loc> {

	/***/
	protected abstract IN4JSCore getN4JSCore();

	@SuppressWarnings("javadoc")
	@Test
	public void testGetLocation_01() {
		IN4JSProject project = getN4JSCore().create(myProjectURI.toURI());
		assertEquals(myProjectURI, project.getLocation());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetLocation_02() {
		URI doesNotExist = myProjectURI.appendPath("../doesNotExist").toURI();
		IN4JSProject project = getN4JSCore().create(doesNotExist);
		assertEquals(doesNotExist, project.getLocation().toURI());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetSourceContainers_01() {
		IN4JSProject project = getN4JSCore().create(myProjectURI.toURI());
		ImmutableList<? extends IN4JSSourceContainer> sourceContainers = project.getSourceContainers();
		assertEquals(1, sourceContainers.size());
		assertEquals("src", sourceContainers.get(0).getRelativeLocation());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetSourceContainers_02() {
		URI doesNotExist = myProjectURI.appendPath("../doesNotExist").toURI();
		IN4JSProject project = getN4JSCore().create(doesNotExist);
		ImmutableList<? extends IN4JSSourceContainer> sourceContainers = project.getSourceContainers();
		assertEquals(0, sourceContainers.size());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetSourceContainers_03() {
		IN4JSProject project = getN4JSCore().create(myProjectURI.toURI());
		ImmutableList<? extends IN4JSSourceContainer> first = project.getSourceContainers();
		ImmutableList<? extends IN4JSSourceContainer> second = project.getSourceContainers();
		assertEquals(first, second);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetDependencies_01() {
		IN4JSProject project = getN4JSCore().create(myProjectURI.toURI());
		ImmutableList<? extends IN4JSProject> dependencies = project.getDependencies();
		assertEquals(2, dependencies.size());
		assertEquals(libProjectURI.getName(), dependencies.get(1).getLocation().getName());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetDependencies_02() {
		URI doesNotExist = myProjectURI.appendPath("../doesNotExist").toURI();
		IN4JSProject project = getN4JSCore().create(doesNotExist);
		ImmutableList<? extends IN4JSProject> dependencies = project.getDependencies();
		assertEquals(0, dependencies.size());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetDependencies_03() {
		IN4JSProject project = getN4JSCore().create(myProjectURI.toURI());
		ImmutableList<? extends IN4JSProject> first = project.getDependencies();
		ImmutableList<? extends IN4JSProject> second = project.getDependencies();
		assertEquals(first, second);
	}

}
