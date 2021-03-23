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
package org.eclipse.n4js.tests.projectModel.locations;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.workspace.locations.SafeURI;
import org.junit.Assert;
import org.junit.Test;

/**
 * Baseclass for tests with SafeURIs.
 */
@SuppressWarnings("javadoc")
public abstract class AbstractSafeURITest<U extends SafeURI<U>> {

	protected final U create(String withoutScheme) {
		Assert.assertNotNull(withoutScheme);
		return create(createRawURI(withoutScheme));
	}

	protected abstract URI createRawURI(String withoutScheme);

	protected final U create(URI withoutScheme) {
		Assert.assertNotNull(withoutScheme);
		U result = doCreate(withoutScheme);
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.toURI().scheme());
		return result;
	}

	protected abstract U doCreate(URI from);

	@Test
	public void testHashcode() {
		URI uri = createRawURI("/some/path");
		U safeURI = create(uri);
		Assert.assertEquals(uri.hashCode(), safeURI.hashCode());
	}

	@Test
	public void testEquals() {
		URI uri = createRawURI("/some/path");
		U first = create(uri);
		U second = create(uri);
		Assert.assertEquals(first, second);
	}

	@Test
	public void testToString() {
		URI uri = createRawURI("/some/path");
		U safeURI = create(uri);
		Assert.assertEquals(uri.toString(), safeURI.toString());
	}

	@Test
	public void testToURI() {
		URI uri = createRawURI("/some/path");
		U safeURI = create(uri);
		Assert.assertSame(uri, safeURI.toURI());
	}

	@Test
	public void testWithTrailingPathDelimiter_01() {
		URI uri = createRawURI("/some/path");
		U safeURI = create(uri).withTrailingPathDelimiter();
		Assert.assertEquals(uri.toString() + "/", safeURI.toString());
	}

	@Test
	public void testWithTrailingPathDelimiter_02() {
		URI uri = createRawURI("/some/path/");
		U safeURI = create(uri).withTrailingPathDelimiter();
		Assert.assertEquals(uri.toString(), safeURI.toString());
	}

	@Test
	public void testAppendSegment_01() {
		URI uri = createRawURI("/some/path");
		U safeURI = create(uri).appendSegment("segment");
		Assert.assertEquals(uri.toString() + "/segment", safeURI.toString());
	}

	@Test
	public void testAppendSegment_02() {
		URI uri = createRawURI("/some/path/");
		U safeURI = create(uri).appendSegment("segment");
		Assert.assertEquals(uri.toString() + "segment", safeURI.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAppendInvalidSegment_01() {
		create("/some/path/").appendSegment("seg/ment");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAppendInvalidSegment_02() {
		create("/some/path/").appendSegment(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAppendInvalidSegment_03() {
		create("/some/path/").appendSegment("");
	}

	@Test
	public void testAppendSegments_01() {
		URI uri = createRawURI("/some/path");
		U safeURI = create(uri).appendSegments("valid", "segment");
		Assert.assertEquals(uri.toString() + "/valid/segment", safeURI.toString());
	}

	@Test
	public void testAppendSegments_02() {
		URI uri = createRawURI("/some/path/");
		U safeURI = create(uri).appendSegments("valid", "segment");
		Assert.assertEquals(uri.toString() + "valid/segment", safeURI.toString());
	}

	@Test
	public void testAppendSegments_03() {
		URI uri = createRawURI("/some/path/");
		U safeURI = create(uri).appendSegments("trailing", "empty", "segment", "");
		Assert.assertEquals(uri.toString() + "trailing/empty/segment/", safeURI.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAppendInvalidSegments_01() {
		create("/some/path/").appendSegments("valid", "seg/ment");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAppendInvalidSegments_02() {
		create("/some/path/").appendSegments("valid", null);
	}

	@Test
	public void testAppendPath_01() {
		URI uri = createRawURI("/some/path");
		U safeURI = create(uri).appendPath("valid/segment");
		Assert.assertEquals(uri.toString() + "/valid/segment", safeURI.toString());
	}

	@Test
	public void testAppendPath_02() {
		URI uri = createRawURI("/some/path/");
		U safeURI = create(uri).appendPath("valid/segment");
		Assert.assertEquals(uri.toString() + "valid/segment", safeURI.toString());
	}

	@Test
	public void testAppendPath_03() {
		URI uri = createRawURI("/some/path/");
		U safeURI = create(uri).appendPath("trailing/empty/segment/");
		Assert.assertEquals(uri.toString() + "trailing/empty/segment/", safeURI.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAppendInvalidPath_01() {
		create("/some/path/").appendPath(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAppendInvalidPath_02() {
		create("/some/path/").appendPath("/");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAppendInvalidPath_03() {
		create("/some/path/").appendPath("//");
	}

	@Test
	public void testGetParent_01() {
		URI parent = createRawURI("/some");
		U safeURI = create("/some/path").getParent();
		Assert.assertEquals(parent.toString(), safeURI.toString());
	}

	@Test
	public void testGetParent_02() {
		URI parent = createRawURI("/some/path");
		U safeURI = create("/some/path/").getParent();
		Assert.assertEquals(parent.toString(), safeURI.toString());
	}
}
