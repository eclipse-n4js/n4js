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
package org.eclipse.n4js.ui.decorator;

import static org.eclipse.n4js.n4mf.ProjectType.API;
import static org.eclipse.n4js.n4mf.ProjectType.APPLICATION;
import static org.eclipse.n4js.n4mf.ProjectType.LIBRARY;
import static org.eclipse.n4js.n4mf.ProjectType.PROCESSOR;
import static org.eclipse.n4js.n4mf.ProjectType.RUNTIME_ENVIRONMENT;
import static org.eclipse.n4js.n4mf.ProjectType.RUNTIME_LIBRARY;
import static org.eclipse.n4js.n4mf.ProjectType.TEST;
import static org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef.PROJECT_TYPE_APP_DECOR;
import static org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef.PROJECT_TYPE_LIB_DECOR;
import static org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef.PROJECT_TYPE_PROC_DECOR;
import static org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef.PROJECT_TYPE_RE_DECOR;
import static org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef.PROJECT_TYPE_RL_DECOR;
import static org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef.PROJECT_TYPE_TEST_DECOR;
import static org.apache.log4j.Logger.getLogger;
import static org.eclipse.emf.common.util.URI.createPlatformResourceURI;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;

import org.eclipse.n4js.n4mf.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;

/**
 * Lightweight label decorator for decorating N4JS projects based on their project type.
 */
public class ProjectTypeLabelDecorator implements ILightweightLabelDecorator {

	private static final Logger LOGGER = getLogger(ProjectTypeLabelDecorator.class);

	private static final Map<ProjectType, ImageRef> IMAGE_REF_CACHE = ImmutableMap.<ProjectType, ImageRef> builder()
			.put(TEST, PROJECT_TYPE_TEST_DECOR)
			.put(APPLICATION, PROJECT_TYPE_APP_DECOR)
			.put(API, PROJECT_TYPE_APP_DECOR)
			.put(RUNTIME_ENVIRONMENT, PROJECT_TYPE_RE_DECOR)
			.put(RUNTIME_LIBRARY, PROJECT_TYPE_RL_DECOR)
			.put(LIBRARY, PROJECT_TYPE_LIB_DECOR)
			.put(PROCESSOR, PROJECT_TYPE_PROC_DECOR)
			.build();

	@Inject
	private IN4JSCore core;

	@Override
	public void addListener(final ILabelProviderListener listener) {
		// no listeners are supported
	}

	@Override
	public void dispose() {
		// nothing to dispose here
	}

	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	@Override
	public void removeListener(final ILabelProviderListener listener) {
		// no listeners are supported
	}

	@Override
	public void decorate(final Object element, final IDecoration decoration) {
		try {
			if (element instanceof IProject) {
				final URI uri = createPlatformResourceURI(((IProject) element).getName(), true);
				final IN4JSProject project = core.findProject(uri).orNull();
				if (null != project) {
					final ImageRef imageRef = IMAGE_REF_CACHE.get(project.getProjectType());
					if (null != imageRef) {
						final ImageDescriptor descriptor = imageRef.asImageDescriptor().orNull();
						if (null != descriptor) {
							decoration.addOverlay(descriptor);
						}
					}
				}
			}
		} catch (final Exception e) {
			// Exception should not propagate from here, otherwise the lightweight decorator stops working once till
			// next application startup.
			LOGGER.error("Error while trying to get decorator for " + element, e);
		}
	}
}
