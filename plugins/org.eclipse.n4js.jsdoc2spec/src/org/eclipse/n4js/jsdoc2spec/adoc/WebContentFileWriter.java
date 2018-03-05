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
package org.eclipse.n4js.jsdoc2spec.adoc;

import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.DIR_ADOC_GEN;
import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.DIR_MODULES;
import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.DIR_PACKAGES;
import static org.eclipse.n4js.jsdoc2spec.adoc.FileSystem.SEP;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.n4js.jsdoc2spec.SpecFile;

/**
 * The online documentation is comprised of several html documents, mostly the module html documents. However, to be
 * able to navigate through the module documentation, listings are needed. The listings show (1) all available packages,
 * and also all available modules. Moreover (3), for each package a listing is needed that shows all its modules. These
 * listings are generated in this class.</br>
 * </br>
 * Since the listings are adoc files first and later compiled to html documents, some adjustments are done for the sake
 * of html layout and interactiveness:
 * <ul>
 * <li>The heading gets the role '.barHeader' which is also used in the stylesheet css file.
 * <li>The body gets the role '.barBody' which is also used in the stylesheet css file.
 * <li>Each link has the <code>window</code> property, since it's target website shall open in another frame.
 * </ul>
 */
class WebContentFileWriter {

	/////////////
	// classes //
	/////////////

	private static class PackageEntry implements Comparable<PackageEntry> {
		static final String NO_PACKAGE_NAME = "NO_PACKAGE";
		final String name;
		final int modulePackageCount;
		final SortedMap<String, ModuleEntry> modules = new TreeMap<>();
		final StringBuilder modulesStrb = new StringBuilder();

		PackageEntry(String name, int modulePackageCount) {
			String packageName = NO_PACKAGE_NAME;
			if (name != null && !name.isEmpty())
				packageName = name;
			this.name = packageName;
			this.modulePackageCount = modulePackageCount;
		}

		@Override
		public int compareTo(PackageEntry o) {
			return name.compareTo(o.name);
		}

		public int getPackageCount() {
			return modulePackageCount;
		}
	}

	private static class ModuleEntry implements Comparable<ModuleEntry> {
		final String name;
		final IndexEntry ie;

		ModuleEntry(String name, IndexEntry ie) {
			this.name = name;
			this.ie = ie;
		}

		@Override
		public int compareTo(ModuleEntry o) {
			return name.compareTo(o.name);
		}
	}

	/////////////
	// members //
	/////////////

	private final SortedMap<String, PackageEntry> allPackages = new TreeMap<>();
	private final StringBuilder allPackagesStrb = new StringBuilder();
	private final StringBuilder allModulesStrb = new StringBuilder();

	public void serialize(ArrayList<IndexEntry> entries) throws UnsupportedEncodingException {
		for (IndexEntry ie : entries) {
			if (ie.project.endsWith("tests"))
				continue; // don't include tests into the docu

			String packageName = ie.packageName;
			if (!allPackages.containsKey(packageName)) {
				int packageCount = ie.modulePackageCount;
				allPackages.put(packageName, new PackageEntry(packageName, packageCount));
			}
			PackageEntry pe = allPackages.get(packageName);

			String moduleName = ie.moduleName;
			if (!ie.isStaticPolyfillAware && !pe.modules.containsKey(moduleName)) {
				pe.modules.put(moduleName, new ModuleEntry(moduleName, ie));
			}
		}

		buildAllPackagesFile();
		buildAllModulesFile();
		for (PackageEntry pe : allPackages.values()) {
			buildPackageModulesFile(pe);
		}
	}

	public List<SpecFile> getSpecFiles(File rootDir) {
		File adocGenDir = new File(rootDir.getAbsolutePath() + SEP + DIR_ADOC_GEN);
		File packageDir = new File(adocGenDir.getAbsolutePath() + SEP + DIR_PACKAGES);

		List<SpecFile> specFiles = new ArrayList<>();

		generateAllPackagesFile(adocGenDir, specFiles);
		generateAllModulesFile(adocGenDir, specFiles);

		for (PackageEntry pe : allPackages.values()) {
			generatePackageModulesFile(packageDir, pe, specFiles);
		}

		return specFiles;
	}

	private void buildAllPackagesFile() throws UnsupportedEncodingException {
		// add the heading
		appendHeading(allPackagesStrb, "Packages");

		// add entries
		for (PackageEntry pe : allPackages.values()) {
			String moduleStyleName = pe.name.replace("/", ".");

			String url = "";
			url += DIR_PACKAGES + "/";
			url += pe.name + ".html";
			url = escapeURL(url);

			buildLink(allPackagesStrb, moduleStyleName, url, "packageModulesBar");
		}

		appendFooter(allPackagesStrb);
	}

	private void buildAllModulesFile() throws UnsupportedEncodingException {
		List<ModuleEntry> allModules = new ArrayList<>();
		for (PackageEntry pe : allPackages.values()) {
			allModules.addAll(pe.modules.values());
		}
		Collections.sort(allModules);

		// add the heading
		appendHeading(allModulesStrb, "All Modules");

		// add entries
		for (ModuleEntry me : allModules) {
			String url = "";
			url += DIR_MODULES + "/";
			url += me.ie.adocPath.replace(".adoc", ".html");
			url = escapeURL(url);

			buildLink(allModulesStrb, me.name, url, "moduleView");
		}

		appendFooter(allModulesStrb);
	}

	private void buildPackageModulesFile(PackageEntry pe) throws UnsupportedEncodingException {
		// add the heading
		appendHeadingModulePackages(pe);

		// add entries
		for (ModuleEntry me : pe.modules.values()) {
			String url = "";
			int packageCount = pe.getPackageCount();
			for (int i = 0; i < packageCount; i++)
				url += "../";
			url += DIR_MODULES + "/";
			url += me.ie.adocPath.replace(".adoc", ".html");
			url = escapeURL(url);

			buildLink(pe.modulesStrb, me.name, url, "moduleView");
		}

		appendFooter(pe.modulesStrb);
	}

	private void buildLink(StringBuilder strb, String label, String url, String target) {
		strb.append("* "); // it's a list
		strb.append("link:"); // it's link: '<a target="moduleView"...></a>'
		strb.append(url);
		strb.append("[" + label);
		strb.append(", window=\"" + target + "\"");
		strb.append("]");
		strb.append("\n");
	}

	static private void appendFooter(StringBuilder strb) {
		strb.append("\n--");
		strb.append("\n\n");
	}

	static private void appendHeadingModulePackages(PackageEntry pe) {
		appendHeading(pe.modulesStrb, pe.name.replace("/", "."), pe.getPackageCount());
	}

	static private void appendHeading(StringBuilder strb, String heading) {
		appendHeading(strb, heading, -1);
	}

	static private void appendHeading(StringBuilder strb, String heading, int upLinkDepth) {
		String basedir = "../";
		for (int i = 0; i < upLinkDepth; i++)
			basedir += "../";

		strb.append(":linkattrs:\n");
		strb.append(":docinfodir: ");
		strb.append(basedir);
		strb.append("headers/apiListings\n");
		strb.append(":linkcss:\n");
		strb.append(":stylesheet: ");
		strb.append(basedir);
		strb.append("styles/stdlib_api_listings.css\n");
		strb.append("\n");
		strb.append("[.barHeader]\n");
		strb.append("--\n");
		if (upLinkDepth >= 0) {
			strb.append("image:");
			strb.append(basedir);
			strb.append("images/up-arrow.png");
			strb.append("[link=\"");
			strb.append(basedir);
			strb.append(DIR_ADOC_GEN);
			strb.append("/allModules.html");
			strb.append("\", window=\"packageModulesBar\"");
			strb.append("]");
			strb.append("\n");
		}
		strb.append(heading);
		strb.append("\n--");
		strb.append("\n\n");
		strb.append("[.barBody]\n");
		strb.append("--\n");
	}

	private void generateAllPackagesFile(File rootDir, List<SpecFile> specFiles) {
		String fileName = "allPackages.adoc";
		String absFileName = rootDir + SEP + fileName;
		File indexFile = new File(absFileName);
		String indexContent = allPackagesStrb.toString();
		SpecFile scf = new SpecListingFile(indexFile, indexContent, "All Packages Overview");
		specFiles.add(scf);
	}

	private void generateAllModulesFile(File rootDir, List<SpecFile> specFiles) {
		String fileName = "allModules.adoc";
		String absFileName = rootDir + SEP + fileName;
		File indexFile = new File(absFileName);
		String indexContent = allModulesStrb.toString();
		SpecFile scf = new SpecListingFile(indexFile, indexContent, "All Modules Overview");
		specFiles.add(scf);
	}

	private void generatePackageModulesFile(File rootDir, PackageEntry pe, List<SpecFile> specFiles) {
		String fileName = pe.name + ".adoc";
		String absFileName = rootDir + SEP + fileName;
		File indexFile = new File(absFileName);
		String indexContent = pe.modulesStrb.toString();
		SpecFile scf = new SpecListingFile(indexFile, indexContent, "Modules Overview");
		specFiles.add(scf);
	}

	private String escapeURL(String fileName) throws UnsupportedEncodingException {
		String[] parts = fileName.split("/");
		String newFileName = URLEncoder.encode(parts[0], "UTF-8");
		for (int i = 1; i < parts.length; i++)
			newFileName += "/" + URLEncoder.encode(parts[i], "UTF-8");

		return newFileName;
	}
}
