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
package org.eclipse.n4js.tests.bugs;

import static org.junit.Assert.assertTrue;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class for testing the {@link ImageDescriptor} and {@link Image} caching.
 */
public class GHOLD_170_ImageCaching_PluginUITest extends AbstractIDEBUG_Test {

	/**
	 * Asserts the test setup, namely that the {@link IWorkbench workbench} is running.
	 */
	@BeforeClass
	public static void assertWorkbenchIsRunning() {
		assertTrue("Expected running workbench.", PlatformUI.isWorkbenchRunning());
	}

	/**
	 * Checks the direct {@link ImageDescriptor image descriptor} caching.
	 */
	@Test
	public void testDirectImageDescriptorCaching() {
		final ImageDescriptor desc1 = ImageRef.LIB_PATH.asImageDescriptor().orNull();
		final ImageDescriptor desc2 = ImageRef.LIB_PATH.asImageDescriptor().orNull();
		assertTrue("Expected exactly same reference of image descriptors.", desc1 == desc2);
	}

	/**
	 * Checks the direct {@link Image image} caching.
	 */
	@Test
	public void testDirectImageCaching() {
		final Image img1 = ImageRef.LIB_PATH.asImage().orNull();
		final Image img2 = ImageRef.LIB_PATH.asImage().orNull();
		assertTrue("Expected exactly same reference of images.", img1 == img2);
	}

	/**
	 * Checks the indirect {@link ImageDescriptor image descriptor} caching.
	 */
	@Test
	public void testIndirectImageDescriptorCaching() {
		final Image img1 = ImageRef.LIB_PATH.asImage().orNull();
		final Image img2 = ImageRef.LIB_PATH.asImage().orNull();
		final ImageDescriptor desc1 = ImageDescriptor.createFromImage(img1);
		final ImageDescriptor desc2 = ImageDescriptor.createFromImage(img2);
		assertTrue("Expected different reference of image descriptors.", desc1 != desc2);
	}

	/**
	 * Checks the indirect {@link Image image} caching.
	 */
	@Test
	public void testIndirectImageCaching() {
		final ImageDescriptor desc1 = ImageRef.LIB_PATH.asImageDescriptor().orNull();
		final ImageDescriptor desc2 = ImageRef.LIB_PATH.asImageDescriptor().orNull();
		final Image img1 = desc1.createImage();
		final Image img2 = desc2.createImage();
		assertTrue("Expected different reference of images.", img1 != img2);
	}

}
