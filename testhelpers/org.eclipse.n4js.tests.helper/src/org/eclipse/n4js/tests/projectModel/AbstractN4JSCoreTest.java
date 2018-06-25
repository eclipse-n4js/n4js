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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.junit.Test;

/**
 */
public abstract class AbstractN4JSCoreTest extends AbstractProjectModelTest {

	/***/
	protected abstract IN4JSCore getN4JSCore();

	@SuppressWarnings("javadoc")
	@Test
	public void testCreateProjectAndCheckExists_01() {
		IN4JSProject project = getN4JSCore().create(myProjectURI.trimSegments(1).appendSegment("doesNotExist"));
		assertNotNull(project);
		assertFalse(project.exists());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testCreateProjectAndCheckExists_02() {
		IN4JSProject project = getN4JSCore().create(myProjectURI);
		assertNotNull(project);
		assertTrue(project.exists());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testCreateYieldsDifferentInstances() {
		IN4JSProject first = getN4JSCore().create(myProjectURI);
		IN4JSProject second = getN4JSCore().create(myProjectURI);
		assertNotSame(first, second);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testCreateYieldsEqualsInstances_01() {
		IN4JSProject first = getN4JSCore().create(myProjectURI);
		IN4JSProject second = getN4JSCore().create(myProjectURI);
		assertEquals(first, second);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testCreateYieldsEqualsInstances_02() {
		URI doesNotExist = myProjectURI.trimSegments(1).appendSegment("doesNotExist");
		IN4JSProject first = getN4JSCore().create(doesNotExist);
		IN4JSProject second = getN4JSCore().create(doesNotExist);
		assertEquals(first, second);
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testProjectId_01() {
		IN4JSProject project = getN4JSCore().create(myProjectURI.trimSegments(1).appendSegment("doesNotExist"));
		assertNotNull(project);
		assertEquals("doesNotExist", project.getProjectId());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testProjectId_02() {
		IN4JSProject project = getN4JSCore().create(myProjectURI);
		assertNotNull(project);
		assertEquals(myProjectId, project.getProjectId());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testGetDepthOfLocation() {
		String[] emptySegments = { "", "", "" };
		URI uri0 = myProjectURI;
		URI uri1 = myProjectURI.appendSegment("someFile.txt");
		URI uri2 = myProjectURI.appendSegment("someFolder").appendSegment("someFile.txt");
		URI uriBad = myProjectURI.trimSegments(1).appendSegment("DoesNotExist").appendSegment("someFile.txt");
		assertEquals(0, getN4JSCore().getDepthOfLocation(uri0));
		assertEquals(0, getN4JSCore().getDepthOfLocation(uri0.appendSegments(emptySegments)));
		assertEquals(1, getN4JSCore().getDepthOfLocation(uri1));
		assertEquals(1, getN4JSCore().getDepthOfLocation(uri1.appendSegments(emptySegments)));
		assertEquals(2, getN4JSCore().getDepthOfLocation(uri2));
		assertEquals(2, getN4JSCore().getDepthOfLocation(uri2.appendSegments(emptySegments)));
		assertEquals(-1, getN4JSCore().getDepthOfLocation(uriBad));
		assertEquals(-1, getN4JSCore().getDepthOfLocation(uriBad.appendSegments(emptySegments)));
	}
}
