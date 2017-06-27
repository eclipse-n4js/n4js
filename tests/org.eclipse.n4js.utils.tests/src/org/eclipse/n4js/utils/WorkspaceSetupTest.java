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
package org.eclipse.n4js.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.collect.Lists;

/**
 * This are meta tests checking that all projects are correctly configured.
 */
@RunWith(Parameterized.class)
public class WorkspaceSetupTest {

	/**
	 * Returns test data.
	 */
	@Parameters(name = "{0}")
	public static Collection<Object[]> data() throws Exception {
		File rootDir = findGitRepoRootDir();
		List<Object[]> result = Lists.newArrayList();
		collectProjects(rootDir, result);
		return result;
	}

	@SuppressWarnings("unused")
	private final String name;
	private final File project;
	private final File settingsDir;
	private final File resouresPrefs;
	private final File runtimePrefs;

	/** */
	public WorkspaceSetupTest(String name, File directory) {
		this.name = name;
		this.project = directory;
		this.settingsDir = new File(project, ".settings");
		this.resouresPrefs = new File(settingsDir, "org.eclipse.core.resources.prefs");
		this.runtimePrefs = new File(settingsDir, "org.eclipse.core.runtime.prefs");
	}

	/** */
	@Test
	public void testSettingsAvailable() {
		Assert.assertTrue(settingsDir.exists());
		Assert.assertTrue(resouresPrefs.getAbsolutePath(), resouresPrefs.exists());
		Assert.assertTrue(runtimePrefs.getAbsolutePath(), runtimePrefs.exists());
	}

	/** */
	@Test
	public void testEncoding() throws IOException {
		Assume.assumeTrue(resouresPrefs.exists());
		try (InputStream in = new FileInputStream(resouresPrefs)) {
			Properties props = new Properties();
			props.load(in);
			Assert.assertEquals("UTF-8", props.getProperty("encoding/<project>"));
		}
	}

	/** */
	@Test
	public void testLineDelimiter() throws IOException {
		Assume.assumeTrue(runtimePrefs.exists());
		try (InputStream in = new FileInputStream(runtimePrefs)) {
			Properties props = new Properties();
			props.load(in);
			Assert.assertEquals("\n", props.getProperty("line.separator"));
		}
	}

	private static void collectProjects(File rootDir, List<Object[]> result) {
		if (new File(rootDir, ".classpath").exists()) {
			result.add(new Object[] { rootDir.getName(), rootDir });
		} else {
			for (File child : rootDir.listFiles()) {
				if (child.isDirectory() && !isGitRepository(child) && !child.getName().startsWith(".")) {
					collectProjects(child, result);
				}
			}
		}
	}

	private static File findGitRepoRootDir() {
		File dir = new File(".").getAbsoluteFile();
		while (!isRepositoryRoot(dir)) {
			dir = dir.getParentFile();
			if (dir == null) {
				return null;
			}
		}
		return dir;
	}

	private static boolean isRepositoryRoot(File dir) {
		if (isGitRepository(dir)) {
			return new File(dir, "plugins").exists() && new File(dir, "features").exists();
		}
		return false;
	}

	private static boolean isGitRepository(File dir) {
		return new File(dir, ".git").exists();
	}

}
