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
import static org.eclipse.jface.viewers.StyledString.DECORATIONS_STYLER;
import static org.eclipse.n4js.external.libraries.ExternalLibrariesActivator.EXTERNAL_LIBRARY_NAMES;
import static org.eclipse.n4js.projectDescription.ProjectType.API;
import static org.eclipse.xtext.util.Strings.toFirstUpper;

import java.io.File;
import java.net.URI;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.semver.model.SemverSerializer;
import org.eclipse.n4js.ui.ImageDescriptorCache.ImageRef;
import org.eclipse.swt.graphics.Image;

/**
 * Simple label provider for external library locations.
 */
class BuiltInLibrariesLabelProvider extends LabelProvider implements IStyledLabelProvider {

	private static final String BUILT_IN_SUFFIX = " [Built-in]";

	@Override
	public String getText(final Object element) {
		if (element instanceof URI) {
			final String externalLibId = ExternalLibraryPreferencePage.BUILT_IN_LIBS.get(element);
			if (!isNullOrEmpty(externalLibId)) {
				return EXTERNAL_LIBRARY_NAMES.get(externalLibId) + BUILT_IN_SUFFIX;
			}
			return new File((URI) element).getAbsolutePath();
		} else if (element instanceof IN4JSProject) {
			return ((IN4JSProject) element).getProjectId();
		}
		return super.getText(element);
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
		StyledString string = new StyledString(nullToEmpty(getText(element)));
		if (element instanceof URI) {
			final String text = string.getString();
			if (text.endsWith(BUILT_IN_SUFFIX)) {
				string.setStyle(text.lastIndexOf(BUILT_IN_SUFFIX), BUILT_IN_SUFFIX.length(), DECORATIONS_STYLER);
			}
		} else if (element instanceof IN4JSProject) {
			final IN4JSProject project = ((IN4JSProject) element);
			final ProjectType type = project.getProjectType();
			String version = SemverSerializer.serialize(project.getVersion());
			// for better visual representation MyProject @1.2.3 -> MyProject v1.2.3
			version = version.replaceFirst("@", "v");
			final String typeLabel = getProjectTypeLabel(type);
			string = new StyledString(string.getString() + " " + version + typeLabel);
			string.setStyle(string.getString().lastIndexOf(typeLabel), typeLabel.length(), DECORATIONS_STYLER);
		}
		return string;
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
