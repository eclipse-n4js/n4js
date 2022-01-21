/**
 * Copyright (c) 2022 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.integration.tests.dts;

import static org.eclipse.n4js.tests.helper.git.GitUtils.hardReset;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.n4js.dts.DtsParseResult;
import org.eclipse.n4js.dts.DtsParser;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.StandardSystemProperty;
import com.google.common.base.Stopwatch;

/**
 *
 */
public class DtsParsesDefinitelyTypedTest {
	static final String WORKSPACE_ENV = "WORKSPACE";
	static final String DEFINITELY_TYPED_CHECKOUT_DIR_NAME = "checkout_definitely_typed_snapshot";

	/** Parse every d.ts-file in the definitely typed repository (except some) */
	@Test
	public void test() throws IOException {

		final String workspace = getSystemPropOrEnvironmentVar(WORKSPACE_ENV,
				StandardSystemProperty.USER_HOME.value());

		Path targetDir = Path.of(workspace, DEFINITELY_TYPED_CHECKOUT_DIR_NAME);

		hardReset("https://github.com/DefinitelyTyped/DefinitelyTyped.git", targetDir,
				"master", true, true);

		assertParseCounts(targetDir, 20000, 250, 0);
	}

	/**
	 * Returns the value of the Java system property with the given key, or if it does not exist the value of the
	 * environment variable with that name; if both do not exist, then {@code defaultValue} is returned.
	 */
	private static String getSystemPropOrEnvironmentVar(String key, String defaultValue) {
		final String value = System.getProperty(key);
		return value != null ? value : System.getenv().getOrDefault(key, defaultValue);
	}

	static final String EXCLUDES[] = { "carbon__icons", "carbon__pictograms" };

	static void assertParseCounts(Path repoRoot, int minPass, int maxFail, int maxError) throws IOException {
		Path typesFolder = repoRoot.resolve("types");

		List<Path> files = Files.walk(typesFolder, FileVisitOption.FOLLOW_LINKS)
				.filter(file -> {
					if (!file.toString().endsWith(".d.ts")) {
						return false;
					}
					String relPath = typesFolder.relativize(file).toString();
					for (String exclude : EXCLUDES) {
						if (exclude != null && !exclude.isBlank() && relPath.startsWith(exclude)) {
							return false;
						}
					}
					return true;
				})
				.collect(Collectors.toList());
		Collections.sort(files, (p1, p2) -> p1.toString().compareTo(p2.toString()));

		int filesCount = files.size();
		int pass = 0;
		int fail = 0;
		int error = 0;
		System.out.println("Processing " + filesCount + " files ...");
		Stopwatch sw = Stopwatch.createStarted();

		for (Path file : files) {
			try (BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile())))) {

				N4JSResource resource = new N4JSResource();
				resource.setURI(new FileURI(file).toURI());
				DtsParseResult parseResult = new DtsParser().parse(buf, resource);

				if (parseResult.hasSyntaxErrors()) {
					fail++;
				} else {
					pass++;
				}

			} catch (Exception e) {
				e.printStackTrace();

				error++;
			}
		}

		System.out.println("Done processing " + filesCount + " files in " + sw.elapsed(TimeUnit.SECONDS) + "s");
		System.out.println("  passed:  " + pass + " (" + percent(pass, filesCount) + ")");
		System.out.println("  failed:  " + fail + " (" + percent(fail, filesCount) + ")");
		System.out.println("  errors:  " + error + " (" + percent(error, filesCount) + ")");

		if (error > maxError) {
			Assert.fail("More errors detected than expected: " + error);
		}

		if (fail > maxFail) {
			Assert.fail("More failures detected than expected: " + fail);
		}

		if (pass < minPass) {
			Assert.fail("Less passes detected than expected: " + pass);
		}
	}

	static String percent(double n, double full) {
		return String.valueOf(Math.floor((n / full) * 1000d) / 10d);
	}
}
