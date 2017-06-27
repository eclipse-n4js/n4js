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

import java.io.File;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.jsdoc2spec.RepoRelativePath;
import org.eclipse.n4js.jsdoc2spec.SpecTestInfo;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TModule;

/**
 * Builds {@link SourceEntry}s.
 */
public class SourceEntryFactory {
	/** Delimiter in index.idx files. */
	protected static final String DELIMITER = "#";
	static final String ORG_ECLIPSE_N4JS = "org.eclipse.n4js";
	static final String NO_REPO = "NO_REPO";
	static final String NO_PATH = "NO_PATH";
	static final String NO_FOLDER = "NO_FOLDER";
	static final String NO_PACKAGE = "NO_PACKAGE";

	/**
	 * Factory method used for test methods only.
	 */
	static SourceEntry create(SpecTestInfo info) {
		String module = info.testModuleSpec().toString();
		String element = info.testMethodTypeName();
		String delimiter = "#";
		String property = info.testMethodName();
		return newInstance(info.rrp, module, element, delimiter, property, null);
	}

	/**
	 * Factory method used for any source element.
	 */
	static SourceEntry create(RepoRelativePath rrp, IdentifiableElement idElement) {
		TModule containingModule = idElement.getContainingModule();
		if (containingModule == null)
			return null;
		String module = containingModule.getModuleSpecifier().toString();
		String elName = getElementName(idElement);
		String delimiter = getDelimiter(idElement);
		String property = getProperty(idElement);
		return newInstance(rrp, module, elName, delimiter, property, idElement);
	}

	private static String getElementName(IdentifiableElement ie) {
		String elName = ie.getName();
		if (ie instanceof TMember) {
			TMember tm = (TMember) ie;
			@SuppressWarnings("rawtypes")
			ContainerType ct = tm.getContainingType();
			elName = ct.getName();
		}
		return elName;
	}

	private static String getDelimiter(IdentifiableElement ie) {
		String delimiter = "";
		if (ie instanceof TMember) {
			TMember tm = (TMember) ie;
			delimiter = tm.isStatic() ? "@" : "#";
			if (tm.isGetter())
				delimiter += "<";
			if (tm.isSetter())
				delimiter += ">";
		}
		return delimiter;
	}

	private static String getProperty(IdentifiableElement ie) {
		String property = "";
		if (ie instanceof TMember) {
			property = ie.getName();
		}
		return property;
	}

	static SourceEntry newInstance(
			RepoRelativePath rrp,
			String moduleSpecifier,
			String element,
			String delimiter,
			String property,
			IdentifiableElement idElement) {

		String module = trim(moduleSpecifier);
		String repository = getRepository(rrp);
		String path = getPath(rrp, repository);
		String project = getProject(rrp);
		String folder = getFolder(rrp, module);
		String trueFolder = getTrueFolder(idElement, folder, module);
		String extension = getExtension(rrp);
		String packageName = getPackageName(module);
		String moduleName = getModuleName(module);
		int sourceLine = rrp.lineNumber;
		int modulePackageCount = StringCountUtils.countFolderDepth(module);
		String[] fileNames = getFileNames(rrp, module);
		String fileName = getFileName(fileNames);

		SourceEntry sed = new SourceEntry(
				repository,
				path,
				project,
				folder,
				trueFolder,
				module,
				extension,
				element,
				delimiter,
				property,
				packageName,
				moduleName,
				sourceLine,
				modulePackageCount,
				fileNames,
				fileName);

		return sed;
	}

	static private String getRepository(RepoRelativePath rrp) {
		if (rrp == null || rrp.repositoryName == null || rrp.repositoryName.isEmpty())
			return NO_REPO;
		return rrp.repositoryName;
	}

	static private String getPath(RepoRelativePath rrp, String repository) {
		if (repository.equals(NO_REPO))
			return NO_PATH;
		if (rrp.pathInRepository == null || rrp.pathInRepository.isEmpty())
			return NO_PATH;

		String tPath = trim(rrp.pathInRepository);
		return tPath;
	}

	static private String getProject(RepoRelativePath rrp) {
		String tProject = trim(rrp.projectName);
		return tProject;
	}

	static private String getFolder(RepoRelativePath rrp, String module) {
		return getFolderInUriString(rrp.pathInProject, module);
	}

	static private String getTrueFolder(IdentifiableElement idElement, String folder, String module) {
		if (idElement == null
				|| idElement.getContainingModule() == null
				|| !idElement.getContainingModule().isStaticPolyfillModule())
			return folder;

		Resource res = idElement.eResource();
		String trueResourceUri = res.getURI().toString();

		return getFolderInUriString(trueResourceUri, module);
	}

	static private String getFolderInUriString(String uriString, String module) {
		int idxProject = uriString.indexOf(ORG_ECLIPSE_N4JS);
		int idxFolder = uriString.indexOf("/", idxProject);
		int idxModule = uriString.indexOf(module, idxFolder);

		String tFolder = uriString.substring(idxFolder, idxModule);
		tFolder = trim(tFolder);
		if (tFolder.isEmpty())
			tFolder = NO_FOLDER;

		return tFolder;
	}

	/** Extracts the package name from a module. */
	static protected String getPackageName(String module) {
		String tPackageName = "";
		if (module.contains("/")) {
			int moduleNameStart = module.lastIndexOf("/");
			tPackageName = module.substring(0, moduleNameStart);
		}

		tPackageName = trim(tPackageName);
		if (tPackageName.isEmpty())
			tPackageName = NO_PACKAGE;

		return tPackageName;
	}

	/** Extracts the module name from a module. */
	static protected String getModuleName(String module) {
		String tModuleName = module;
		if (module.contains("/")) {
			int moduleNameStart = module.lastIndexOf("/");
			tModuleName = module.substring(moduleNameStart);
		}

		tModuleName = trim(tModuleName);
		return tModuleName;
	}

	static private String getExtension(RepoRelativePath rrp) {
		int extPointIdx = rrp.pathInProject.lastIndexOf(".");
		String extension = rrp.pathInProject.substring(extPointIdx);
		return extension;
	}

	/**
	 * Returns an array of directory/file names. These names will be later used when writing the adoc file.
	 */
	static private String[] getFileNames(RepoRelativePath rrp, String module) {
		String[] fNames = new String[4];
		String repository = getRepository(rrp);
		fNames[0] = repository + DELIMITER + getPath(rrp, repository).replace("/", ".");
		fNames[1] = getProject(rrp) + DELIMITER + getFolder(rrp, module).replace("/", ".");
		fNames[2] = getPackageName(module).replace("/", ".");
		fNames[3] = getModuleName(module) + ".adoc";
		return fNames;
	}

	/** Concatenates a file name from a path that is given as a list of folders, terminated by an adoc file name. */
	static protected String getFileName(String[] fileNames) {
		String fName = fileNames[0];
		fName += File.separator + fileNames[1];
		if (fileNames[2] != null && !fileNames[2].isEmpty())
			fName += File.separator + fileNames[2];
		fName += File.separator + fileNames[3];
		return fName;
	}

	/** Cuts off '/' at the beginning and end of a String. */
	static protected String trim(String s) {
		if (s.startsWith("/"))
			s = s.substring(1);
		if (s.endsWith("/"))
			s = s.substring(0, s.length() - 1);
		return s;
	}
}
