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
import java.nio.file.Path;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.n4js.projectModel.IN4JSCore;
import org.eclipse.n4js.projectModel.IN4JSProject;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.Files;

import com.google.common.base.Optional;

/**
 * Value object containing information about the repository relative location of a file, optionally with line number to
 * create a deep link.
 */
public class RepoRelativePath {

	/**
	 * Creates the RepoRelativePath from a given resource. Returns null, if resource is not contained in a repository.
	 * If a repository is found, the simple name of the origin is used.
	 */
	public static RepoRelativePath compute(Resource resource, IN4JSCore n4jsCore) {
		URI uri = resource.getURI();
		Optional<? extends IN4JSProject> optProj = n4jsCore.findProject(uri);
		if (optProj.isPresent()) {
			final String mesos = "mesos";
			IN4JSProject project = optProj.get();
			Path path = project.getLocationPath();

			String uriFileString = uri.toString();
			String uriProjString = project.getLocation().toString();
			String fileRelString = uriFileString.substring(uriProjString.length());

			String absFileName = path.toAbsolutePath() + fileRelString;
			File file = new File(absFileName);
			if (!file.exists()) {
				return null;
			}
			File f = file.getParentFile();
			while (f != null && f.isDirectory() && f.exists()) {
				File[] files = f.listFiles((File pathname) -> pathname.isDirectory()
						&& ".git".equals(pathname.getName()));
				if (files.length > 0) {
					String repoName = null;

					boolean hadExCIE = false;
					boolean prOrigin = false;
					boolean hadLastS = false;
					boolean repoHafG = false;
					File config = new File(files[0], "config");
					if (config.exists()) {
						try {
							String configStr = Files.readFileIntoString(config.getAbsolutePath());
							Config cfg = new Config();

							cfg.fromText(configStr);
							String originURL = cfg.getString("remote", "origin", "url");
							if (originURL != null && !originURL.isEmpty()) {
								prOrigin = true;
								int lastSlash = originURL.lastIndexOf('/');
								if (lastSlash >= 0) {
									hadLastS = true;
									repoName = originURL.substring(lastSlash + 1);
								} else {
									repoName = originURL;
								}
								if (repoName.endsWith(".git")) {
									repoHafG = true;
									repoName = repoName.substring(0, repoName.length() - 4);
								}
							}
						} catch (ConfigInvalidException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							hadExCIE = true;
						}
					}
					boolean nullRepo = false;
					if (repoName == null) {
						repoName = f.getName();
						nullRepo = true;
					}

					String projName = project.getProjectId();
					String projPath = fileRelString;
					String repoPath = getRepoPath(absFileName, projName, repoName);

					if (File.separatorChar != '/') {
						projPath = projPath.replace(File.separatorChar, '/');
						repoPath = repoPath.replace(File.separatorChar, '/');
					}

					if (repoName.contains(mesos) || repoPath.contains(mesos) || projName.contains(mesos)
							|| projPath.contains(mesos)) {
						System.out.println("========");
						System.out.println("o.O found mesos");
						System.out.println(" repoName " + repoName);
						System.out.println(" repoPath " + repoPath);
						System.out.println(" projName " + projName);
						System.out.println(" projPath " + projPath);
						System.out.println("------");
						System.out.println(" nullRepo " + nullRepo);
						System.out.println(" hadExCIE " + hadExCIE);
						System.out.println(" prOrigin " + prOrigin);
						System.out.println(" hadLastS " + hadLastS);
						System.out.println(" repoHafG " + repoHafG);

					}

					return new RepoRelativePath(repoName, repoPath, projName, projPath, -1);

				} else {
					f = f.getParentFile();
				}

			}
		}
		System.out.println("o.O null RepoRelativePath");
		return null;
	}

	private static String getRepoPath(String absFileName, String projName, String repoName) {
		int startIdx = -1;
		String repoNameSlashes = "/" + repoName + "/";
		String projNameSlashes = "/" + projName + "/";
		startIdx = absFileName.indexOf(repoNameSlashes) + repoNameSlashes.length();
		int endIdx = absFileName.indexOf(projNameSlashes);
		if (startIdx == -1 || endIdx == -1) {
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
	 * Absolute path in repository (with leading slash).
	 */
	public final String pathInRepository;
	/**
	 * Name of the Eclipse project.
	 */
	public final String projectName;
	/**
	 * Absolute path in Eclipse project.
	 */
	public final String pathInProject;
	/**
	 * Optionally a line number, -1 if no line number is available.
	 */
	public final int lineNumber;

	private RepoRelativePath(String repositoryName, String pathInRepository, String projectName, String pathInProject,
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
