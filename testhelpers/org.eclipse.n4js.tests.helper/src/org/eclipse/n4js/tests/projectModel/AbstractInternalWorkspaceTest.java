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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Set;

import org.eclipse.n4js.internal.InternalN4JSWorkspace;
import org.eclipse.n4js.projectDescription.ProjectDependency;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectModel.locations.SafeURI;
import org.junit.Test;

import com.google.common.collect.Sets;

/**
 */
public abstract class AbstractInternalWorkspaceTest<Loc extends SafeURI<Loc>> extends AbstractProjectModelTest<Loc> {

	/***/
	protected abstract InternalN4JSWorkspace<Loc> getWorkspace();

	@SuppressWarnings("javadoc")
	@Test
	public void testGetProjectDescription_01() {
		ProjectDescription description = getWorkspace().getProjectDescription(myProjectURI);
		assertNotNull(description);
		assertEquals(myProjectName.getRawName(), description.getProjectName());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetProjectDescription_02() {
		ProjectDescription description = getWorkspace().getProjectDescription(libProjectURI);
		assertNotNull(description);
		assertEquals(libProjectName.getRawName(), description.getProjectName());
	}

	@SuppressWarnings({ "javadoc" })
	@Test
	public void testGetProjectDescription_04() {
		final Loc doesNotExist = myProjectURI.appendPath("../" + myProjectName + "doesNotExist");
		final ProjectDescription description = getWorkspace().getProjectDescription(doesNotExist);
		assertNull("Expecting null project description for non-existing project. Was: " + description, description);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testDescriptionsAreSame() {
		ProjectDescription first = getWorkspace().getProjectDescription(myProjectURI);
		ProjectDescription second = getWorkspace().getProjectDescription(myProjectURI);
		assertSame(first, second);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetLocation_01() {
		ProjectDescription description = getWorkspace().getProjectDescription(myProjectURI);
		ProjectDependency dependency = description.getProjectDependencies().get(1);
		Loc resolvedLocation = getWorkspace().getLocation(dependency);
		assertEquals(libProjectURI, resolvedLocation);
	}

	@SuppressWarnings({ "javadoc", "unchecked" })
	@Test
	public void testGetFolderIterator_01() {
		Set<Loc> containedURIs = Sets.newHashSet(getWorkspace().getFolderIterator(
				myProjectURI.appendSegment("src")));
		Set<Loc> expectation = Sets.newHashSet(
				myProjectURI.appendSegments(new String[] { "src", "A.js" }),
				myProjectURI.appendSegments(new String[] { "src", "B.js" }),
				myProjectURI.appendSegments(new String[] { "src", "sub", "B.js" }),
				myProjectURI.appendSegments(new String[] { "src", "sub", "C.js" }),
				myProjectURI.appendSegments(new String[] { "src", "sub", "leaf", "D.js" }));
		assertEquals(expectation, containedURIs);
	}

	@SuppressWarnings({ "javadoc" })
	@Test
	public void testGetFolderIterator_02() {
		Set<Loc> containedURIs = Sets.newHashSet(getWorkspace().getFolderIterator(
				myProjectURI.appendSegment("doesNotExist")));
		Set<Loc> expectation = Sets.newHashSet();
		assertEquals(expectation, containedURIs);
	}

	@SuppressWarnings({ "javadoc", "unchecked" })
	@Test
	public void testGetFolderIterator_03() {
		Set<Loc> containedURIs = Sets.newHashSet(getWorkspace().getFolderIterator(
				myProjectURI.appendSegments(new String[] { "src", "sub" })));
		Set<Loc> expectation = Sets.newHashSet(
				myProjectURI.appendSegments(new String[] { "src", "sub", "B.js" }),
				myProjectURI.appendSegments(new String[] { "src", "sub", "C.js" }),
				myProjectURI.appendSegments(new String[] { "src", "sub", "leaf", "D.js" }));
		assertEquals(expectation, containedURIs);
	}
}
