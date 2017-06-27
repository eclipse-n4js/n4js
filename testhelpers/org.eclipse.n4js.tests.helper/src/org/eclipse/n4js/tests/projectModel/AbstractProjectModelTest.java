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

import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 */
public abstract class AbstractProjectModelTest {

	/***/
	protected abstract AbstractProjectModelSetup createSetup();

	/***/
	public final String myProjectId = "myProject";
	/***/
	protected URI myProjectURI;

	/***/
	public final String libProjectId = "libProject";
	/***/
	protected URI libProjectURI;

	/***/
	public final String archiveProjectId = "archive";
	/***/
	protected URI archiveFileURI;

	private AbstractProjectModelSetup setup;

	/***/
	public void setMyProjectURI(URI myProjectURI) {
		this.myProjectURI = myProjectURI;
	}

	/***/
	public void setLibProjectURI(URI libProjectURI) {
		this.libProjectURI = libProjectURI;
	}

	/***/
	public void setArchiveFileURI(URI archiveFileURI) {
		this.archiveFileURI = archiveFileURI;
	}

	/***/
	@Before
	public void setUp() {
		setup = createSetup();
		createTempProjects();
		assertNotNull(myProjectURI);
		assertNotNull(libProjectURI);
		assertNotNull(archiveFileURI);
	}

	private void deleteTempProjects() {
		setup.deleteTempProjects();
	}

	private void createTempProjects() {
		setup.createTempProjects();
	}

	/***/
	@After
	public void tearDown() {
		deleteTempProjects();
		setup = null;
		myProjectURI = null;
		libProjectURI = null;
		archiveFileURI = null;
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testSetup() {
		assertEquals(myProjectId, myProjectURI.lastSegment());
		assertEquals(libProjectId, libProjectURI.lastSegment());
		assertEquals(archiveProjectId + ".nfar", archiveFileURI.lastSegment());
	}

}
