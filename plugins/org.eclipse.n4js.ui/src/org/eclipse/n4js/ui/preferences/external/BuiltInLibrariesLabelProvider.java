/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ui.preferences.external;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.base.Strings.nullToEmpty;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.EXTERNAL_LIBRARY_NAMES;
import static org.eclipse.n4js.projectDescription.ProjectType.API;
import static org.eclipse.xtext.util.Strings.toFirstUpper;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.n4js.external.ExternalIndexSynchronizer;
import org.eclipse.n4js.external.ExternalLibraryWorkspace;
import org.eclipse.n4js.external.ShadowingInfoHelper;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.swt.graphics.Image;

/**
 * Simple label provider for external library locations.
 */
class BuiltInLibrariesLabelProvider extends LabelProvider implements IStyledLabelProvider {
	private final ExternalIndexSynchronizer indexSynchronizer;
	private final ShadowingInfoHelper shadowingInfoHelper;
	private final ExternalLibraryWorkspace externalLibraryWorkspace;

	public BuiltInLibrariesLabelProvider(ExternalIndexSynchronizer indexSynchronizer,
			ShadowingInfoHelper shadowingInfoHelper, ExternalLibraryWorkspace externalLibraryWorkspace) {
		this.indexSynchronizer = indexSynchronizer;
		this.shadowingInfoHelper = shadowingInfoHelper;
		this.externalLibraryWorkspace = externalLibraryWorkspace;
	}

	@Override
	public String getText(final Object element) {
		if (element instanceof URI) {
			return getCategoryText(element);
		} else if (element instanceof IN4JSProject) {
			return ((IN4JSProject) element).getProjectName();
		}
		return super.getText(element);
	}

	private String getCategoryText(final Object element) {
		final String externalLibId = ExternalLibraryPreferencePage.BUILT_IN_LIBS.get(element);
		if (!isNullOrEmpty(externalLibId)) {
			return EXTERNAL_LIBRARY_NAMES.get(externalLibId);
		}
		return new File((URI) element).getAbsolutePath();
	}

	@Override
	public Image getImage(final Object element) {
		if (element instanceof URI) {
			return ImageRef.LIB_PATH.asImage().orNull();
		} else if (element instanceof IN4JSProject) {
			return ImageRef.EXTERNAL_LIB_PROJECT.asImage().orNull();
		}
		return super.getImage(element);
	}

	@Override
	public StyledString getStyledText(final Object element) {
		if (element instanceof URI) {
			return new StyledString(getCategoryText(element));
		} else if (element instanceof IN4JSProject) {
			final IN4JSProject project = ((IN4JSProject) element);
			final String name = project.getProjectName();
			final ProjectType type = project.getProjectType();
			// for better visual representation MyProject @1.2.3 -> MyProject v1.2.3
			final String version = SemverSerializer.serialize(project.getVersion()).replaceFirst("@", "v");
			final String typeLabel = getProjectTypeLabel(type);
			final boolean inIndex = indexSynchronizer.isInIndex(project.getProjectDescriptionLocation().orNull());
			final String rootLocationName = getRootLocationName(project);

			Styler stylerName = inIndex ? null : StyledString.QUALIFIER_STYLER;
			Styler stylerType = inIndex ? StyledString.DECORATIONS_STYLER : StyledString.QUALIFIER_STYLER;
			StyledString string = new StyledString(name + " " + version, stylerName);
			string.append(typeLabel, stylerType);
			if (rootLocationName != null) {
				string.append(rootLocationName, StyledString.COUNTER_STYLER);
			}
			return string;
		}
		return new StyledString("unknown");
	}

	private String getRootLocationName(final IN4JSProject project) {
		String rootLocationName = null;
		List<IN4JSProject> shadowingProjects = shadowingInfoHelper.findShadowingProjects(project);
		if (!shadowingProjects.isEmpty()) {
			IN4JSProject shadowedProject = shadowingProjects.get(0);
			org.eclipse.emf.common.util.URI location = shadowedProject.getLocation();
			URI rootLocation = externalLibraryWorkspace.getRootLocationForResource(location);
			org.eclipse.emf.common.util.URI emfURI = org.eclipse.emf.common.util.URI.createURI(rootLocation.toString());
			rootLocationName = emfURI.lastSegment();
			if (rootLocationName.isEmpty() && emfURI.segmentCount() > 1) {
				rootLocationName = emfURI.segment(emfURI.segmentCount() - 2);
			}
			rootLocationName = " [shadowed by " + rootLocationName + "]";
		}
		return rootLocationName;
	}

	private String getProjectTypeLabel(final ProjectType projectType) {
		final String label;
		if (API.equals(projectType)) {
			label = API.getName();
		} else {
			label = toFirstUpper(nullToEmpty(projectType.getName()).replaceAll("_", " ").toLowerCase());
		}
		return " [" + label + "]";
	}

}
