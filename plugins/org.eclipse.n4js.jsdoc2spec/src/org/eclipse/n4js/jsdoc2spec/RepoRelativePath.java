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
package org.eclipse.n4js.jsdoc2spec;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.n4js.jsdoc2spec.adoc.SourceEntryFactory;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.projectModel.locations.FileURI;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.utils.Log;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.io.Files;

/**
 * Value object containing information about the repository relative location of a file, optionally with line number to
 * create a deep link.
 */
@Log
public class RepoRelativePath {
	private static final Logger LOGGER = Logger.getLogger(RepoRelativePath.class);

	/**
	 * Creates the RepoRelativePath from a given resource. Returns null, if resource is not contained in a repository.
	 * If a repository is found, the simple name of the origin is used.
	 */
	public static RepoRelativePath compute(FileURI uri, IN4JSCore n4jsCore) {
		Optional<? extends IN4JSProject> optProj = n4jsCore.findProject(uri.toURI());
		if (!optProj.isPresent()) {
			return null;
		}

		IN4JSProject project = optProj.get();

		Path pathFile = uri.toFileSystemPath();
		Path pathProj = project.getLocation().toFileSystemPath();
		String fileRelString = pathProj.relativize(pathFile).toString();
		// strip anchor part if present, i.e. path to type within the resource
		int anchorIndex = fileRelString.indexOf("#");
		if (anchorIndex >= 0)
			fileRelString = fileRelString.substring(0, anchorIndex);

		File file = pathProj.toAbsolutePath().resolve(fileRelString).toFile();
		if (!file.exists()) {
			return null;
		}

		File repoFolder = getRepoFolder(file);
		String repoName = getRepoName(repoFolder);
		String cloneFolder = repoFolder == null ? SourceEntryFactory.NO_FOLDER : repoFolder.getName();
		N4JSProjectName projName = project.getProjectName();
		String repoPath = getRepoPath(file.toString(), projName, cloneFolder);

		String projPath = '/' + fileRelString;
		if (File.separatorChar != '/') {
			projPath = projPath.replace(File.separatorChar, '/');
			repoPath = repoPath.replace(File.separatorChar, '/');
		}
		return new RepoRelativePath(repoName, repoPath, projName, projPath, -1);
	}

	private static File getRepoFolder(File file) {
		File currentFolder = file.getParentFile();
		while (currentFolder != null && currentFolder.isDirectory() && currentFolder.exists()) {
			String repoName = getRepoName(currentFolder);
			if (!Strings.isNullOrEmpty(repoName)) {
				return currentFolder;
			}
			currentFolder = currentFolder.getParentFile();
		}
		return currentFolder;
	}

	/**
	 * Tries to obtain repository name from the provided directory by reading git config in
	 * {@code currendDir/.git/config}
	 * <p>
	 * Git clone folder name might be different from git repository name
	 *
	 * @return string with repo name or {@code null}
	 */
	private static String getRepoName(File currentDir) {
		if (currentDir == null) {
			return "NO_REPO";
		}
		File gitFolder = new File(currentDir, ".git");
		if (!gitFolder.isDirectory()) {
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("No '.git' folder at " + currentDir.getAbsolutePath());
			return null;
		}

		File config = new File(gitFolder, "config");
		if (!config.isFile()) {
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("No 'config' file at " + gitFolder.getAbsolutePath());
			return null;
		}
		try {
			String configStr = Files.toString(config, Charset.defaultCharset());
			Config cfg = new Config();

			cfg.fromText(configStr);
			String originURL = cfg.getString("remote", "origin", "url");
			if (originURL != null && !originURL.isEmpty()) {
				int lastSlash = originURL.lastIndexOf('/');
				String repoName = null;
				if (lastSlash >= 0) {
					repoName = originURL.substring(lastSlash + 1);
				} else {
					repoName = originURL;
				}
				if (repoName.endsWith(".git")) {
					repoName = repoName.substring(0, repoName.length() - 4);
				}
				return repoName;
			}
		} catch (ConfigInvalidException | IOException e) {
			LOGGER.warn("Cannot read git config at " + config.getAbsolutePath(), e);
		}

		return null;
	}

	private static String getRepoPath(String absFileName, N4JSProjectName projName, String repoCloneName) {
		int startIdx = -1;
		String repoNameSlashes = "/" + repoCloneName + "/";
		String projNameSlashes = "/" + projName.getRawName() + "/";
		startIdx = absFileName.indexOf(repoNameSlashes) + repoNameSlashes.length();
		int endIdx = absFileName.indexOf(projNameSlashes);
		if (startIdx == -1 || endIdx == -1 || endIdx < startIdx) {
			return "";
		}
		String repoPath = absFileName.substring(startIdx, endIdx);
		return repoPath;
	}

	/**
	 * Simple name of repository.
	 */
	public final String repositoryName;
	/**
	 * Absolute path in repository (with leading slash). This may be empty; this happens if the project folder is a top
	 * level folder of the project.
	 */
	public final String pathInRepository;
	/**
	 * Name of the Eclipse project.
	 */
	public final N4JSProjectName projectName;
	/**
	 * Absolute path in Eclipse project.
	 */
	public final String pathInProject;
	/**
	 * Optionally a line number, -1 if no line number is available.
	 */
	public final int lineNumber;

	private RepoRelativePath(String repositoryName, String pathInRepository, N4JSProjectName projectName,
			String pathInProject,
			int lineNumber) {
		this.repositoryName = repositoryName;
		this.pathInRepository = pathInRepository;
		this.projectName = projectName;
		this.pathInProject = pathInProject;
		this.lineNumber = lineNumber;
	}

	/**
	 * Returns a (maybe new) RepoRelativePath with line number of the given test member.
	 */
	public RepoRelativePath withLine(SyntaxRelatedTElement testMember) {
		ICompositeNode node = NodeModelUtils.getNode(testMember.getAstElement());
		if (node != null) {
			final int line = node.getStartLine();
			final RepoRelativePath rrp = new RepoRelativePath(repositoryName, pathInRepository, projectName,
					pathInProject, line);
			return rrp;
		}
		return this;
	}

	/**
	 * Compares this rrp with another one.
	 */
	public int compareTo(RepoRelativePath rrp) {
		if (rrp == this) {
			return 0;
		}
		if (rrp == null) {
			return 1;
		}
		int d = repositoryName.compareTo(rrp.repositoryName);
		if (d != 0)
			return d;
		d = pathInRepository.compareTo(rrp.pathInRepository);
		if (d != 0)
			return d;
		d = projectName.compareTo(rrp.projectName);
		if (d != 0)
			return d;
		d = pathInProject.compareTo(rrp.pathInProject);
		if (d != 0)
			return d;
		d = lineNumber - rrp.lineNumber;
		return d;
	}

	@Override
	public int hashCode() {
		int hash = repositoryName.hashCode() * 31;
		hash += (hash + pathInRepository.hashCode()) * 31;
		hash += (hash + projectName.hashCode()) * 31;
		hash += (hash + pathInProject.hashCode()) * 31;
		return hash + lineNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RepoRelativePath) {
			return compareTo((RepoRelativePath) obj) == 0;
		}
		return false;
	}

	/**
	 * Returns the full path to the file
	 */
	public String getFullPath() {
		return repositoryName + pathInRepository + projectName + pathInProject;
	}

	@Override
	public String toString() {
		return "repo: " + repositoryName + ", pir: " + pathInRepository + ", proj: " + projectName + ", pip: "
				+ pathInProject + ":" + lineNumber;
	}

	/**
	 * <quote> A section begins with the name of the section in square brackets and continues until the next section
	 * begins. Section names are case-insensitive. Only alphanumeric characters, - and . are allowed in section names.
	 * Each variable must belong to some section, which means that there must be a section header before the first
	 * setting of a variable. Sections can be further divided into subsections. To begin a subsection put its name in
	 * double quotes, separated by space from the section name, in the section header.</quote>
	 * <a href="https://www.kernel.org/pub/software/scm/git/docs/git-config.html">[git-config]</a>
	 *
	 *
	 */

}
