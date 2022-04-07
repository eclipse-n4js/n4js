/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.tests.xtext.workspace;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.packagejson.projectDescription.ProjectDescription;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.packagejson.projectDescription.SourceContainerType;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.workspace.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.workspace.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.ProjectSet;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;

/**
 * Tests for class {@link ProjectSet}.
 */
public class ProjectSetTest {

	/**
	 * Tests the special cases described in {@link ProjectSet#update(Iterable, Iterable)}.
	 */
	@Test
	public void testRemoveAndRecreate() {
		String projectName = "TestProject";
		URI rootA = URI.createFileURI("///some/folder");
		URI rootB = URI.createFileURI("///another/folder");
		N4JSProjectConfigSnapshot projectBelowRootA = createProjectConfig(rootA.appendSegment(projectName));
		N4JSProjectConfigSnapshot projectBelowRootB = createProjectConfig(rootB.appendSegment(projectName));

		ProjectSet set1 = new ProjectSet(Collections.singleton(projectBelowRootA));
		ProjectSet set2 = set1.update(Collections.singleton(projectBelowRootB), Collections.singleton(projectName));

		assertEquals(1, set2.size());
		ProjectConfigSnapshot projectInSet2 = IterableExtensions.head(set2.getProjects());
		assertEquals(rootB.appendSegment(projectName), projectInSet2.getPath());
		assertEquals(projectBelowRootB, projectInSet2);
	}

	private N4JSProjectConfigSnapshot createProjectConfig(URI path) {
		String name = ProjectDescriptionUtils.deriveN4JSPackageNameFromURI(path);
		ProjectDescription pd = ProjectDescription.builder()
				.setPackageName(name)
				.setProjectType(ProjectType.LIBRARY)
				.setVersion(SemverUtils.createVersionNumber(0, 0, 1))
				.build();
		N4JSSourceFolderSnapshot srcFolder = new N4JSSourceFolderSnapshot("src", path.appendSegment("src"),
				SourceContainerType.SOURCE, "src");
		return new N4JSProjectConfigSnapshot(pd, path, false, true, Collections.emptyList(),
				Collections.singleton(srcFolder), Map.of());
	}
}
