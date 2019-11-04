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
package org.eclipse.n4js.xpect.ui.results;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceLocator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.n4js.xpect.ui.N4IDEXpectUIPlugin;
import org.eclipse.n4js.xpect.ui.results.XpectFileContentsUtil.XpectFileContentAccess;
import org.eclipse.n4js.xpect.ui.runner.N4IDEXpectFileNameUtil;
import org.eclipse.swt.graphics.Image;
import org.junit.runner.Description;

/**
 * Provides icons and display names for test results view {@link N4IDEXpectView}
 */
class XpectLabelProvider extends LabelProvider {
	private final Map<ImageDescriptor, Image> imageCache = new HashMap<>();

	private final ExecutionResults executionStatus;

	XpectLabelProvider(ExecutionResults testTreeExecutionStatus) {
		executionStatus = testTreeExecutionStatus;
	}

	@Override
	public String getText(Object element) {

		if (element instanceof Description == false) {
			return "";
		}

		Description desc = ((Description) element);

		if (desc.isSuite()) {
			return N4IDEXpectFileNameUtil.getSuiteName(desc);
		}

		if (desc.isTest()) {
			return N4IDEXpectFileNameUtil.getTestName(desc);
		}

		return "";
	}

	@Override
	public Image getImage(Object element) {
		ImageDescriptor descriptor = getImageDescriptor(element);

		if (descriptor == null) {
			return null;
		}

		// obtain the cached image corresponding to the descriptor
		Image image = imageCache.get(descriptor);
		if (image == null) {
			image = descriptor.createImage();
			imageCache.put(descriptor, image);
		}
		return image;

	}

	/**
	 * get icon based on item type (test/suite) and its status (pass/failed/exception/skip/in progress...)
	 */
	private ImageDescriptor getImageDescriptor(Object element) throws RuntimeException {
		ImageDescriptor descriptor = null;
		if (element instanceof Description == false) {
			String msg = "Unknown type of element in tree of type " + element.getClass().getName();
			Exception e = new RuntimeException(msg);
			N4IDEXpectUIPlugin.logError("cannot obtain image descriptor, fallback to default", e);
			return getImageDescriptor("n4_logo.png");
		}

		Description desc = (Description) element;

		if (desc.isTest()) {
			descriptor = getTestImageDescriptor(executionStatus.getStatus(desc));
		} else if (desc.isSuite()) {
			descriptor = getSuiteImageDescriptor(desc, executionStatus.getStatus(desc));
		} else {
			descriptor = getImageDescriptor("n4_logo.png");
		}
		return descriptor;
	}

	/** obtain {@link ImageDescriptor} for test} */
	private ImageDescriptor getTestImageDescriptor(ExecutionStatus st) {
		ImageDescriptor descriptor;
		switch (st) {
		case PENDING:
			descriptor = getImageDescriptor("test.png");
			break;
		case STARTED:
			descriptor = getImageDescriptor("test_running.png");
			break;
		case IGNORED:
			descriptor = getImageDescriptor("test_ignored.png");
			break;
		case PASSED:
			descriptor = getImageDescriptor("test_pass.png");
			// TODO check fixme for single test
			break;
		case FAILED:
			descriptor = getImageDescriptor("test_fail.png");
			break;
		case ERROR:
			descriptor = getImageDescriptor("test_error.png");
			break;

		default:
			descriptor = getImageDescriptor("n4_logo.png");
			break;
		}
		return descriptor;
	}

	/** obtain {@link ImageDescriptor} for suite} */
	private ImageDescriptor getSuiteImageDescriptor(Description desc, ExecutionStatus st) {
		ImageDescriptor descriptor;
		switch (st) {
		case PENDING:
			descriptor = getImageDescriptor("testsuite.png");
			break;
		case STARTED:
			descriptor = getImageDescriptor("testsuite_running.png");
			break;
		case IGNORED:
			descriptor = getImageDescriptor("testsuite_ignored.png");
			break;
		case PASSED:
			descriptor = getImageDescriptor("testsuite_pass.png");
			Optional<XpectFileContentAccess> fileContentAccess = XpectFileContentsUtil
					.getXpectFileContentAccess(desc);
			if (fileContentAccess.isPresent()) {
				if (fileContentAccess.get().containsFixme()) {
					descriptor = getImageDescriptor("testsuite_fixme.png");
				}
			}
			break;
		case FAILED:
			descriptor = getImageDescriptor("testsuite_fail.png");
			break;
		case ERROR:
			descriptor = getImageDescriptor("testsuite_error.png");
			break;

		default:
			descriptor = getImageDescriptor("n4_logo.png");
			break;
		}
		return descriptor;
	}

	public static ImageDescriptor getImageDescriptor(String name) {
		String iconPath = "icons/";
		return ResourceLocator.imageDescriptorFromBundle(N4IDEXpectUIPlugin.PLUGIN_ID, iconPath + name).orElse(null);
	}

	@Override
	public void dispose() {
		for (Iterator<Image> i = imageCache.values().iterator(); i.hasNext();) {
			i.next().dispose();
		}
		imageCache.clear();
	}

}
