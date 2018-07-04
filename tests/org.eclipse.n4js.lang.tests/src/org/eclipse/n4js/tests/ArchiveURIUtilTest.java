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
package org.eclipse.n4js.tests;

import java.util.zip.ZipEntry;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.ArchiveURIUtil;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class ArchiveURIUtilTest {

	@SuppressWarnings("javadoc")
	@Test
	public void testArchiveURIForSingleSegment() {
		URI base = URI.createURI("file:/a/b/c.nfar");
		URI manifestURI = ArchiveURIUtil.createURI(base, IN4JSProject.PACKAGE_JSON);
		Assert.assertEquals("archive:file:/a/b/c.nfar!/package.json", manifestURI.toString());
	}

	@SuppressWarnings("javadoc")
	@Test
	public void testArchiveURIForZipEntry() {
		ZipEntry entry = new ZipEntry("a/b/c.js");
		URI base = URI.createURI("file:/a/b/c.nfar");
		URI manifestURI = ArchiveURIUtil.createURI(base, entry);
		Assert.assertEquals("archive:file:/a/b/c.nfar!/a/b/c.js", manifestURI.toString());
	}
}
