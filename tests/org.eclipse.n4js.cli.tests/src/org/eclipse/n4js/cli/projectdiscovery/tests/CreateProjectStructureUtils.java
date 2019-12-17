/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.cli.projectdiscovery.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.n4js.N4JSGlobals;

/**
 * Creates a project structure from a description file
 */
public class CreateProjectStructureUtils {

	static class Folder {
		final Folder parent;
		final String folderName;
		final boolean isProject;
		final boolean isWorkingDir;
		final String yarnWorkspacesFolder;
		final String dependencies;

		Folder(Folder parent, String folderName, boolean isProject, boolean isWorkingDir, String yarnWorkspacesFolder,
				String dependencies) {
			this.parent = parent;
			this.folderName = folderName;
			this.isProject = isProject;
			this.isWorkingDir = isWorkingDir;
			this.yarnWorkspacesFolder = yarnWorkspacesFolder;
			this.dependencies = dependencies;
		}

		int getDepth() {
			return parent == null ? 0 : 1 + parent.getDepth();
		}

		String getPath() {
			return (parent == null) ? folderName : parent.getPath() + File.separator + folderName;
		}

		@Override
		public String toString() {
			String str = "--------------".substring(0, getDepth() + 1);
			str += isWorkingDir ? "*" : " ";
			str += folderName;
			if (isProject) {
				str += "[PROJECT";
				str += (yarnWorkspacesFolder == null) ? "" : " workspaces= " + yarnWorkspacesFolder + " ";
				str += (dependencies == null) ? "" : " dependencies= " + dependencies + " ";
				str += "]";
			}
			return str;
		}
	}

	static class ProjectDiscoveryTestData {
		final ArrayList<String> expectedProjects;
		final ArrayList<Folder> folders;
		final Folder workingDir;

		public ProjectDiscoveryTestData(ArrayList<String> expectedProjects, ArrayList<Folder> folders,
				Folder workingDir) {

			this.expectedProjects = expectedProjects;
			this.folders = folders;
			this.workingDir = workingDir;
			Collections.sort(this.expectedProjects);
		}
	}

	/** Parses the given '.pdt' file and returns a {@link ProjectDiscoveryTestData} */
	static public ProjectDiscoveryTestData readPDTFile(File file) {
		ArrayList<String> expectedProjects = null;
		ArrayList<Folder> folders = null;
		Folder workingDir = null;

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			for (String line = reader.readLine(); line != null; line = reader.readLine()) {

				if (line.isBlank()) {
					continue;
				}

				line = line.trim();

				if (line.startsWith("#")) {
					continue;
				}

				if (line.startsWith("FOLDERS")) {
					folders = readFOLDERS(reader);
				}

				if (line.startsWith("EXPECT")) {
					expectedProjects = readEXPECT(reader);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (expectedProjects == null || folders == null) {
			return null;
		}

		for (Folder folder : folders) {
			if (folder.isWorkingDir) {
				workingDir = folder;
			}
		}

		if (workingDir == null) {
			return null;
		}

		return new ProjectDiscoveryTestData(expectedProjects, folders, workingDir);
	}

	private static ArrayList<String> readEXPECT(BufferedReader reader) throws IOException {
		ArrayList<String> expectedProjects = new ArrayList<>();
		for (String line = reader.readLine(); line != null && !line.isBlank(); line = reader.readLine()) {
			if (line.startsWith("-")) {
				expectedProjects.add(line.substring(1).trim());
			}
		}
		return expectedProjects;
	}

	private static ArrayList<Folder> readFOLDERS(BufferedReader reader) throws IOException {
		ArrayList<Folder> folders = new ArrayList<>();
		ArrayList<Folder> parents = new ArrayList<>();
		for (String line = reader.readLine(); line != null && !line.isBlank(); line = reader.readLine()) {
			if (line.startsWith("-")) {
				Folder folder = readFolder(line.trim(), parents);
				folders.add(folder);
				if (folder.parent == null) {
					parents.clear();
					parents.add(folder);
				} else {
					int indexOfParent = parents.indexOf(folder.parent);
					parents.add(indexOfParent + 1, folder);
					for (int i = indexOfParent + 2; i < parents.size(); i++) {
						parents.remove(i);
					}
				}
			}
		}
		return folders;
	}

	private static Folder readFolder(String folderStr, List<Folder> parents) {
		int parentIndex = -2;
		while (folderStr.startsWith("-")) {
			parentIndex++;
			folderStr = folderStr.substring(1).trim();
		}

		Folder parent = (parentIndex < 0 || parents.size() <= parentIndex) ? null : parents.get(parentIndex);
		boolean isWorkingDir = false;
		if (folderStr.startsWith("*")) {
			isWorkingDir = true;
			folderStr = folderStr.substring(1).trim();
		}
		int folderNameEndIndex = folderStr.contains(" ") ? folderStr.indexOf(" ") : folderStr.length();
		String folderName = folderStr.substring(0, folderNameEndIndex).trim();
		String restLine = folderStr.substring(folderNameEndIndex).trim();

		boolean isProject = false;
		String yarnWorkspacesFolder = null;
		String dependencies = null;

		if (restLine.startsWith("[") && restLine.endsWith("]")) {
			restLine = restLine.substring(1, restLine.length() - 1).trim();
			if (restLine.startsWith("PROJECT")) {
				isProject = true;

				restLine = restLine.substring("PROJECT".length()).trim();
				if (restLine.startsWith("workspaces")) {
					restLine = restLine.substring("workspaces".length()).trim();
					if (restLine.startsWith("=")) {
						restLine = restLine.substring(1).trim();
						int startIndex = restLine.indexOf("[");
						int endIndex = restLine.indexOf("]");
						yarnWorkspacesFolder = restLine.substring(startIndex, endIndex + 1);

						restLine = restLine.substring(endIndex + 1).trim();
					}
				}

				if (restLine.startsWith("dependencies")) {
					restLine = restLine.substring("dependencies".length()).trim();
					if (restLine.startsWith("=")) {
						restLine = restLine.substring(1).trim();
						int startIndex = restLine.indexOf("{");
						int endIndex = restLine.indexOf("}");
						dependencies = restLine.substring(startIndex, endIndex + 1);

						restLine = restLine.substring(endIndex + 1).trim();
					}
				}
			}
		}

		return new Folder(parent, folderName, isProject, isWorkingDir, yarnWorkspacesFolder, dependencies);
	}

	/** Creates the folder structure specified by {@link ProjectDiscoveryTestData} in the given dir */
	public static void createFolderStructure(File dir, ProjectDiscoveryTestData pdtd) {
		for (Folder folder : pdtd.folders) {
			String folderPath = folder.getPath();
			File folderFile = new File(dir, folderPath);
			folderFile.mkdir();
			if (folder.isProject) {
				createPackageJson(folderFile, folder);
			}
		}
	}

	private static void createPackageJson(File folderFile, Folder folder) {
		String contents = "{";
		if (folder.yarnWorkspacesFolder == null) {
			String projectName = folder.folderName;
			if (folder.parent != null && folder.parent.folderName.startsWith("@")) {
				projectName = folder.parent.folderName + "/" + projectName;
			}
			contents += "\"name\": \"" + folder.folderName + "\", ";
			contents += "\"n4js\": {\"projectType\": \"library\"";
			contents += "}";
		} else {
			contents += "\"private\": true, \"workspaces\": " + folder.yarnWorkspacesFolder + "";
		}
		contents += (folder.dependencies == null) ? "" : ", \"dependencies\":" + folder.dependencies;
		contents += "}";

		File packageJson = new File(folderFile, N4JSGlobals.PACKAGE_JSON);

		try (PrintWriter printWriter = new PrintWriter(new FileWriter(packageJson))) {
			printWriter.println(contents);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
