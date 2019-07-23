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
package org.eclipse.n4js.xpect.ui.runner;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.xpect.ui.N4IDEXpectUIPlugin;
import org.eclipse.n4js.xpect.ui.runner.N4IDEXpectTestFilesCollector.N4IDEXpectTestURIProvider;
import org.eclipse.xpect.runner.IXpectURIProvider;
import org.eclipse.xpect.runner.XpectRunner;
import org.eclipse.xpect.runner.XpectTestFiles;
import org.eclipse.xpect.runner.XpectURIProvider;
import org.eclipse.xpect.util.IBundleInfo;
import org.eclipse.xpect.util.IBundleInfo.Registry;
import org.junit.runner.Description;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * Custom test file provider. Configures custom {@link IXpectURIProvider} that will be used by {@link XpectRunner}.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@XpectURIProvider(N4IDEXpectTestURIProvider.class)
public @interface N4IDEXpectTestFilesCollector {

	/**
	 * @deprecated Leftover from {@link XpectTestFiles}. should be removed.
	 */
	@Deprecated
	@SuppressWarnings("javadoc")
	public static enum FileRoot {
		CLASS, CURRENT, PROJECT, SOURCE
	}

	/**
	 * Custom {@link URI} collector for xt files in user project in product workspace.
	 */
	public static class N4IDEXpectTestURIProvider implements IXpectURIProvider {

		/** context in which we operate. */
		protected final N4IDEXpectTestFilesCollector ctx;
		/** java test class for which we will be executing tests */
		protected final Class<?> owner;
		/** Bundle holding test class. (not user project with test files) */
		protected IBundleInfo project;
		/** list of xt file locations. Needs to be provided before test execution is triggered. */
		private final List<String> testFilesLocations = Lists.newArrayList();

		/** remembers given test file location that will be executed later. */
		public void addTestFileLocation(String testFileDescriptor) {
			testFilesLocations.add(testFileDescriptor);
		}

		@SuppressWarnings("javadoc")
		protected N4IDEXpectTestURIProvider(Class<?> owner, N4IDEXpectTestFilesCollector ctx) {
			this.owner = owner;
			this.ctx = ctx;
		}

		/** create {@link URI} from {@link File#getAbsolutePath()} */
		protected URI createURI(File file) {
			return URIUtils.toFileUri(file);
		}

		/**
		 * Should return project related path, from top level source folder to file itself.
		 */
		@Override
		public URI deresolveToProject(URI uri) {
			return uri.deresolve(getProject().getRootURI());
		}

		/**
		 * Based on provided {@link Description} find raw location of the related xt file.
		 *
		 * @return String with file path of the xt file, eg
		 *         "C:\\Users\Administrator\workspace\project\src\testfile.n4js.xt"
		 */
		public String findRawLocation(Description description) {
			String filePath = N4IDEXpectFileNameUtil.getFilePath(description);

			List<String> testFilesRawLocations = testFilesLocations.stream()
					.filter(testFileLocation -> testFileLocation.endsWith(filePath))
					.collect(Collectors.toList());

			RuntimeException re = null;
			switch (testFilesRawLocations.size()) {
			case 0:
				re = new RuntimeException("unexpected number of files for provided description");
				N4IDEXpectUIPlugin.logError("no files mathching " + filePath + " found in known URIs", re);
				throw re;

			case 1:
				return URI.createURI(testFilesRawLocations.get(0)).toFileString();

			default:
				re = new RuntimeException("unexpected number of files for provided description");
				StringBuilder sb = new StringBuilder("multiple files matching " + filePath + " found");
				testFilesRawLocations.forEach(testFileLocation -> {
					sb.append("\n file : " + testFileLocation);
				});
				N4IDEXpectUIPlugin.logError(sb.toString(), re);
				throw re;
			}
		}

		/**
		 * collects and returns collection of {@link URI}s to xt files that will be used in test run.
		 */
		@Override
		public Collection<URI> getAllURIs() {
			List<URI> result = Lists.newArrayList();

			testFilesLocations.forEach(xtFileLocation -> {
				// TODO add checks for ctx.fileExtensions()
				// TODO add checks for ctx.baseDir
				URI xtFIleURI = URI.createURI(xtFileLocation);
				result.add(xtFIleURI);
			});

			return result;
		}

		/** resolve bundle holding this class. */
		protected IBundleInfo getBundle() {
			Registry registry = IBundleInfo.Registry.INSTANCE;
			return registry.getBundle(URIUtils.toFileUri(new File(".").getAbsolutePath()));
		}

		/** Should be bundle with used custom java test class */
		protected IBundleInfo getProject() {
			if (project == null)
				project = IBundleInfo.Registry.INSTANCE.getBundle(owner);
			return project;
		}

		@SuppressWarnings("javadoc")
		protected URI resolvePlatformResourceURI(URI uri) {
			List<String> segments = uri.segmentsList();
			if (segments.size() < 2)
				throw new RuntimeException("URI " + uri + " has an invalid format");
			String symbolicName = segments.get(1);
			IBundleInfo bundle = IBundleInfo.Registry.INSTANCE.getBundle(symbolicName);
			if (bundle == null)
				throw new RuntimeException("Bundle " + symbolicName + " not found.");
			URI uriInBundle = URI.createURI(Joiner.on('/').join(segments.subList(2, segments.size())));
			return uriInBundle.resolve(bundle.getRootURI());
		}

		@SuppressWarnings("javadoc")
		protected URI resolveProjectRelativeURI(URI uri) {
			List<String> segments = uri.segmentsList();
			URI uriInProject = URI.createURI(Joiner.on('/').join(segments));
			return uriInProject.resolve(getProject().getRootURI());
		}

		@Override
		public URI resolveURI(URI base, String newURI) {
			URI uri = URI.createURI(newURI);
			if (uri.isPlatformResource())
				return resolvePlatformResourceURI(uri);
			if (uri.hasAbsolutePath())
				return resolveProjectRelativeURI(uri);
			return uri.resolve(base);
		}
	}

}
