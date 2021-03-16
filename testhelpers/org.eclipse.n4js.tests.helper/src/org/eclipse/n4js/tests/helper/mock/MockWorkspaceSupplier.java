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
package org.eclipse.n4js.tests.helper.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.internal.lsp.N4JSProjectConfigSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshot;
import org.eclipse.n4js.internal.lsp.N4JSSourceFolderSnapshotForPackageJson;
import org.eclipse.n4js.internal.lsp.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.projectDescription.ProjectDescription;
import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectDescription.SourceContainerDescription;
import org.eclipse.n4js.projectDescription.SourceContainerType;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.semver.SemverUtils;
import org.eclipse.n4js.semver.Semver.VersionNumber;
import org.eclipse.n4js.utils.ProjectDescriptionLoader;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xtext.workspace.BuildOrderInfo;
import org.eclipse.n4js.xtext.workspace.ProjectConfigSnapshot;
import org.eclipse.n4js.xtext.workspace.WorkspaceConfigSnapshot;
import org.eclipse.xtext.xbase.lib.Pair;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Creates a workspace configuration for a mocked test workspace that does not exist on disk. It has two modes:
 * <ol>
 * <li>if a <code>package.json</code> file exist in the current working directory*, then a workspace configuration will
 * be created with a project derived from the information in that file (see method {@link #loadProjectDescription()}),
 * <li>otherwise, the a workspace configuration will be created with a project created from scratch using the
 * <code>TEST_PROJECT__*</code> constants in this class.
 * </ol>
 * * in JUnit tests this will be the root folder of the containing bundle.
 */
@Singleton
public class MockWorkspaceSupplier {

	/** Default {@link WorkspaceConfigSnapshot#getPath() path} used for the mocked workspace. */
	public static final FileURI TEST_WORKSPACE__PATH = new FileURI(URI.createURI("file:///test_workspace"));
	/** Default {@link ProjectConfigSnapshot#getName() name} used for the mocked project. */
	public static final String TEST_PROJECT__NAME = "TestProject";
	/** Default {@link N4JSProjectConfigSnapshot#getType() type} used for the mocked project. */
	public static final ProjectType TEST_PROJECT__TYPE = ProjectType.LIBRARY;
	/** Default {@link ProjectConfigSnapshot#getPath() path} used for the mocked project. */
	public static final FileURI TEST_PROJECT__PATH = TEST_WORKSPACE__PATH.appendSegment(TEST_PROJECT__NAME);
	/** Default {@link N4JSProjectConfigSnapshot#getVendorId() vendor ID} used for the the mocked project. */
	public static final String TEST_PROJECT__VENDOR_ID = "test.vendor.id";
	/** Default {@link N4JSProjectConfigSnapshot#getVendorName() vendor name} used for the the mocked project. */
	public static final String TEST_PROJECT__VENDOR_NAME = "Test Vendor Name";

	@Inject
	private ProjectDescriptionLoader projectDescriptionLoader;

	private final Supplier<N4JSWorkspaceConfigSnapshot> mockWorkspaceConfig = Suppliers.memoize(
			this::createWorkspaceConfig);

	/** Returns an {@link URI} contained in the source folder of the mocked test project. */
	public URI toTestProjectURI(String projectRelativePath) {
		return toTestProjectFileURI(projectRelativePath).toURI();
	}

	/** Returns a {@link FileURI} contained in the source folder of the mocked test project. */
	public FileURI toTestProjectFileURI(String projectRelativePath) {
		return getMockProjectSourceFolder().getPathAsFileURI().appendPath(projectRelativePath);
	}

	/** Never returns <code>null</code>. */
	public N4JSSourceFolderSnapshot getMockProjectSourceFolder() {
		N4JSSourceFolderSnapshot sourceFolder = FluentIterable.from(getMockProjectConfig().getSourceFolders())
				.filter(sf -> !(sf instanceof N4JSSourceFolderSnapshotForPackageJson))
				.first().orNull();
		if (sourceFolder == null) {
			throw new AssertionError("mocked test project does not have a source folder");
		}
		return sourceFolder;
	}

	/** Never returns <code>null</code>. */
	public N4JSProjectConfigSnapshot getMockProjectConfig() {
		Set<N4JSProjectConfigSnapshot> projects = getMockWorkspaceConfig().getProjects();
		if (projects.isEmpty()) {
			throw new AssertionError("mocked test workspace does not contain a project");
		}
		return projects.iterator().next();
	}

	/** Never returns <code>null</code>. */
	public N4JSWorkspaceConfigSnapshot getMockWorkspaceConfig() {
		return mockWorkspaceConfig.get();
	}

	/**
	 * Creates the configuration of the mocked test workspace.
	 * <p>
	 * The returned workspace configuration is expected to contain at least one project with at least one source folder
	 * that isn't a {@link N4JSSourceFolderSnapshotForPackageJson}. The corresponding folders and files are not expected
	 * to exist on disk.
	 * <p>
	 * Only invoked on demand and never more than once.
	 */
	protected N4JSWorkspaceConfigSnapshot createWorkspaceConfig() {
		N4JSProjectConfigSnapshot projectConfig = createProjectConfig();
		URI workspacePath = URIUtils.trimTrailingPathSeparator(projectConfig.getPath()).trimSegments(1);

		ImmutableBiMap<String, N4JSProjectConfigSnapshot> name2Project = ImmutableBiMap.of(projectConfig.getName(),
				projectConfig);
		ImmutableMap<URI, N4JSProjectConfigSnapshot> projectPath2Project = ImmutableMap.of(
				URIUtils.trimTrailingPathSeparator(projectConfig.getPath()), projectConfig);
		ImmutableMap.Builder<URI, N4JSProjectConfigSnapshot> sourceFolderPath2Project = ImmutableMap.builder();
		for (N4JSSourceFolderSnapshot sourceFolder : projectConfig.getSourceFolders()) {
			sourceFolderPath2Project.put(URIUtils.trimTrailingPathSeparator(sourceFolder.getPath()), projectConfig);
		}

		BuildOrderInfo buildOrderInfo = new BuildOrderInfo(ArrayListMultimap.create(), Collections.emptyList(),
				Collections.emptySet());

		return new N4JSWorkspaceConfigSnapshot(workspacePath, name2Project, projectPath2Project,
				sourceFolderPath2Project.build(), buildOrderInfo);
	}

	/** See {@link #createWorkspaceConfig()}. */
	protected N4JSProjectConfigSnapshot createProjectConfig() {
		// try to load a test package.json from disk, otherwise create a project configuration from default values
		Pair<FileURI, ProjectDescription> loadedOrCreated = loadProjectDescription().or(this::createProjectDescription);
		FileURI projectPath = loadedOrCreated.getKey();
		ProjectDescription pd = loadedOrCreated.getValue();
		List<N4JSSourceFolderSnapshot> sourceFolders = createSourceFolders(projectPath, pd);
		return new N4JSProjectConfigSnapshot(pd, projectPath.withTrailingPathDelimiter().toURI(), false, true,
				Collections.emptyList(), Collections.emptyList(), sourceFolders);
	}

	/** See {@link #createWorkspaceConfig()}. */
	protected List<N4JSSourceFolderSnapshot> createSourceFolders(FileURI projectPath, ProjectDescription pd) {
		List<N4JSSourceFolderSnapshot> sourceFolders = new ArrayList<>();
		for (SourceContainerDescription sc : pd.getSourceContainers()) {
			for (String path : sc.getPaths()) {
				sourceFolders.add(new N4JSSourceFolderSnapshot(path,
						projectPath.appendPath(path).withTrailingPathDelimiter().toURI(), sc.getSourceContainerType(),
						path));
			}
		}
		return sourceFolders;
	}

	/** See {@link #createWorkspaceConfig()}. */
	protected Optional<Pair<FileURI, ProjectDescription>> loadProjectDescription() {
		FileURI candidateProjectPath = new FileURI(new File("").getAbsoluteFile());
		ProjectDescription pd = projectDescriptionLoader.loadProjectDescriptionAtLocation(candidateProjectPath);
		return Optional.fromNullable(pd != null ? Pair.of(candidateProjectPath, pd) : null);
	}

	/** See {@link #createWorkspaceConfig()}. Only invoked if {@link #loadProjectDescription()} returns absent value. */
	protected Pair<FileURI, ProjectDescription> createProjectDescription() {
		VersionNumber versionNumber = SemverUtils.createVersionNumber(0, 0, 1);
		Iterable<SourceContainerDescription> sourceContainers = createSourceContainerDescriptions();
		ProjectDescription pd = ProjectDescription.builder()
				.setProjectName(TEST_PROJECT__NAME)
				.setProjectVersion(versionNumber)
				.setProjectType(TEST_PROJECT__TYPE)
				.setVendorId(TEST_PROJECT__VENDOR_ID)
				.setVendorName(TEST_PROJECT__VENDOR_NAME)
				.addSourceContainers(sourceContainers)
				.build();
		return Pair.of(TEST_PROJECT__PATH, pd);
	}

	/** See {@link #createWorkspaceConfig()}. */
	protected Iterable<SourceContainerDescription> createSourceContainerDescriptions() {
		return Collections.singleton(new SourceContainerDescription(SourceContainerType.SOURCE,
				Collections.singletonList("src")));
	}
}
