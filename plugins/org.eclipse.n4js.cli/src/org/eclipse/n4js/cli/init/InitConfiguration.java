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
package org.eclipse.n4js.cli.init;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.n4js.cli.N4jscException;
import org.eclipse.n4js.cli.N4jscExitCode;
import org.eclipse.n4js.cli.init.InitResources.ExampleFile;
import org.eclipse.n4js.cli.init.InitResources.FileHelloWorldTest;
import org.eclipse.n4js.cli.init.InitResources.PackageJsonContents;
import org.eclipse.n4js.cli.init.InitResources.YarnPackageJsonContents;
import org.eclipse.n4js.utils.JsonUtils;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

/**
 * This configuration describes the setup that shall be created on disk
 */
public class InitConfiguration {
	static final String NL = System.lineSeparator();

	Path yarnRoot;
	Path workspacesDir;
	Path projectRoot;
	YarnPackageJsonContents yarnPackageJson;
	PackageJsonContents packageJson;
	Collection<ExampleFile> files = new ArrayList<>();

	/** @return true iff the example test is created */
	public boolean hasScript(String name) {
		if (isWorkspaces()) {
			return yarnPackageJson.scripts.containsKey(name);
		} else {
			return packageJson.scripts.containsKey(name);
		}
	}

	/** @return true iff the example test is created */
	public boolean hasTest() {
		if (isWorkspaces()) {
			return yarnPackageJson.scripts.containsKey("");
		}
		return files.stream().anyMatch(f -> new FileHelloWorldTest().getName().equals(f.getName()));
	}

	/** @return true iff this configuration is related to a yarn workspace */
	public boolean isWorkspaces() {
		return yarnPackageJson != null;
	}

	/** Heuristic based approach to identify the first workspaces directory */
	public String getWorkspacesDirectory() throws N4jscException {
		Preconditions.checkState(isWorkspaces());

		if (yarnPackageJson.workspaces == null || yarnPackageJson.workspaces.length == 0) {
			throw new N4jscException(N4jscExitCode.INIT_ERROR_WORKING_DIR,
					"Yarn workspace does not specify a workspaces property entry at " + packageJson.toString());
		}
		String workspacesEntry = yarnPackageJson.workspaces[0];

		int lastIndexOfSlash = workspacesEntry.lastIndexOf("/");
		int lastIndexOfAsterisk = workspacesEntry.lastIndexOf("*");

		while (true) {
			boolean slashBeforeAsterisk = lastIndexOfSlash < lastIndexOfAsterisk;
			if (slashBeforeAsterisk) {
				lastIndexOfAsterisk = workspacesEntry.lastIndexOf("*", lastIndexOfSlash);
				if (lastIndexOfAsterisk == -1) {
					lastIndexOfAsterisk = Integer.MAX_VALUE;
					break;
				}
			} else {
				lastIndexOfSlash = workspacesEntry.lastIndexOf("/", lastIndexOfAsterisk);
				if (lastIndexOfSlash == -1) {
					lastIndexOfSlash = Integer.MAX_VALUE;
					break;
				}
			}
			boolean newSlashBeforeAsterisk = lastIndexOfSlash < lastIndexOfAsterisk;
			if (newSlashBeforeAsterisk == slashBeforeAsterisk) {
				break;
			}
		}

		int lastIndex = Math.min(lastIndexOfSlash, lastIndexOfAsterisk);
		return workspacesEntry.substring(0, lastIndex);
	}

	@Override
	public String toString() {
		if (packageJson.exists) {
			return toStringToN4JS();
		}

		if (isWorkspaces()) {
			return toStringYarn();
		}
		return toStringSingleProject();
	}

	private String toStringToN4JS() {
		String str = "";

		Gson gson = JsonUtils.createGson();
		str += "modified contents of package.json" + NL;
		str += gson.toJson(packageJson) + NL;

		return str;
	}

	private String toStringSingleProject() {
		String str = "";

		str += "FILE TREE" + NL;
		str += projectRoot + NL;
		for (ExampleFile eFile : files) {
			str += " ├── " + eFile.getDir() + NL;
			str += " │  └── " + eFile.getName() + NL;
		}
		str += " └── package.json" + NL;
		str += NL;

		Gson gson = JsonUtils.createGson();
		str += packageJson.exists ? (packageJson.hasModifications() ? "MODIFIED " : "UNMODIFIED") : "";
		str += "CONTENTS OF package.json" + NL;
		str += gson.toJson(packageJson) + NL;

		return str;
	}

	private String toStringYarn() {
		String str = "";

		str += "FILE TREE" + NL;
		str += yarnRoot + NL;
		str += " ├── " + yarnRoot.relativize(workspacesDir) + NL;
		str += " │  └── " + workspacesDir.relativize(projectRoot) + NL;
		for (ExampleFile eFile : files) {
			str += " │     ├── " + eFile.getDir() + NL;
			str += " │     │  └── " + eFile.getName() + NL;
		}
		str += " │     └── package.json" + NL;
		str += " └── package.json" + NL;
		str += NL;

		Gson gson = JsonUtils.createGson();
		str += packageJson.exists ? (packageJson.hasModifications() ? "MODIFIED " : "UNMODIFIED") : "";
		str += "CONTENTS OF package.json at" + yarnRoot.relativize(projectRoot) + NL;
		str += NL;

		str += gson.toJson(packageJson) + NL;
		str += yarnPackageJson.exists ? (yarnPackageJson.hasModifications() ? "MODIFIED " : "UNMODIFIED") : "";
		str += "CONTENTS OF package.json at" + yarnRoot + NL;
		str += gson.toJson(yarnPackageJson) + NL;

		return str;

	}

}
