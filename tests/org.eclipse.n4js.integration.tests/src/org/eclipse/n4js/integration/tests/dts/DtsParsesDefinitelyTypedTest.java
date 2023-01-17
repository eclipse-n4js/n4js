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
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.dts.DtsParseResult;
import org.eclipse.n4js.dts.DtsParser;
import org.eclipse.n4js.dts.LoadResultInfoAdapter;
import org.eclipse.n4js.dts.NestedResourceAdapter;
import org.eclipse.n4js.resource.N4JSResource;
import org.eclipse.n4js.workspace.locations.FileURI;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.StandardSystemProperty;
import com.google.common.base.Stopwatch;
import com.google.common.collect.FluentIterable;

/**
 * Test for d.ts grammar
 */
public class DtsParsesDefinitelyTypedTest {

	static final String WORKSPACE_ENV = "WORKSPACE";
	static final String DEFINITELY_TYPED_CHECKOUT_DIR_NAME = "checkout_definitely_typed_snapshot";

	/**
	 * If non-<code>null</code>, loads list of files with known failures/errors from the given file and emits additional
	 * messages when encountering failures/errors in other files.
	 */
	private static final Path LOAD_BAD_FILES_FROM = null; // Path.of("/Users/MyName/Desktop/bad-dts-files.txt");
	/**
	 * If non-<code>null</code>, saves list of files with failures/errors to the given file.
	 */
	private static final Path SAVE_BAD_FILES_TO = null; // Path.of("/Users/MyName/Desktop/bad-dts-files.txt");

	/** Parse every d.ts-file in the definitely typed repository (except some) */
	@Test
	public void parseDefinitelyTyped() throws IOException {

		final String workspace = getSystemPropOrEnvironmentVar(WORKSPACE_ENV,
				StandardSystemProperty.USER_HOME.value());

		Path targetDir = Path.of(workspace, DEFINITELY_TYPED_CHECKOUT_DIR_NAME);

		hardReset("https://github.com/DefinitelyTyped/DefinitelyTyped.git", targetDir,
				"master", true, true);

		assertParseCounts(targetDir, 30000, 1000, 0);
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

		Set<Path> badFilesExpected;
		if (LOAD_BAD_FILES_FROM != null) {
			badFilesExpected = FluentIterable.from(Files.readString(LOAD_BAD_FILES_FROM).split("\\n"))
					.transform(s -> Path.of(s))
					.toSet();
		} else {
			badFilesExpected = null;
		}
		PrintWriter w;
		if (SAVE_BAD_FILES_TO != null) {
			Files.deleteIfExists(SAVE_BAD_FILES_TO);
			w = new PrintWriter(new BufferedWriter(new FileWriter(SAVE_BAD_FILES_TO.toFile())));
		} else {
			w = null;
		}

		class ResultCounts {
			int filesCount = files.size();
			int pass = 0;
			int fail = 0;
			int error = 0;
			int unexpectedBad = 0;
		}
		ResultCounts counts = new ResultCounts();
		System.out.println("Processing " + counts.filesCount + " files ...");
		Stopwatch sw = Stopwatch.createStarted();

		for (Path file : files) {
			Path srcRoot = file.getParent();
			while (srcRoot != null) {
				Path resolved = srcRoot.resolve("tsconfig.json");
				if (resolved.toFile().exists()) {
					break;
				}
				srcRoot = srcRoot.getParent();
			}

			System.out.println("Parsing: " + file);

			try (BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile())))) {

				parseFile(srcRoot, file, (result) -> {
					if (result.hasSyntaxErrors()) {
						counts.fail++;
						if (w != null) {
							w.println(file);
							w.flush();
						}
						if (badFilesExpected != null && !badFilesExpected.contains(file)) {
							counts.unexpectedBad++;
							System.out.println("UNEXPECTED FAILURE: " + file);
						}
					} else {
						counts.pass++;
					}
				});

			} catch (Exception e) {
				e.printStackTrace();

				counts.error++;
				if (w != null) {
					w.println(file);
					w.flush();
				}
				if (badFilesExpected != null && !badFilesExpected.contains(file)) {
					counts.unexpectedBad++;
					System.out.println("UNEXPECTED ERROR: " + file);
				}
			}
		}

		if (w != null) {
			w.close();
		}

		System.out.println("Done processing " + counts.filesCount + " files in " + sw.elapsed(TimeUnit.SECONDS) + "s");
		System.out.println("  passed:  " + counts.pass + " (" + percent(counts.pass, counts.filesCount) + ")");
		System.out.println("  failed:  " + counts.fail + " (" + percent(counts.fail, counts.filesCount) + ")");
		System.out.println("  errors:  " + counts.error + " (" + percent(counts.error, counts.filesCount) + ")");

		if (counts.error > maxError) {
			Assert.fail("More errors detected than expected: " + counts.error);
		}

		if (counts.fail > maxFail) {
			Assert.fail("More failures detected than expected: " + counts.fail);
		}

		if (counts.pass < minPass) {
			Assert.fail("Less passes detected than expected: " + counts.pass);
		}

		if (counts.unexpectedBad > 0) {
			Assert.fail("Encountered failure(s)/error(s) in " + counts.unexpectedBad + " unexpected files.");
		}
	}

	/**
	 * Parses the given file including all nested resources i.e. all declared modules
	 */
	static public void parseFile(Path root, Path file, Consumer<DtsParseResult> onResult) throws Exception {
		try (BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile())))) {

			N4JSResource resource = new N4JSResource();
			URI fileUri = new FileURI(file).toURI();
			resource.setURI(fileUri);
			DtsParseResult parseResult = new DtsParser().parse("test", root, buf, resource);

			onResult.accept(parseResult);

			LoadResultInfoAdapter resultInfoAdapter = LoadResultInfoAdapter.getOrInstall(resource);

			for (URI uri : resultInfoAdapter.getNewUris()) {
				N4JSResource newResource = new N4JSResource();
				newResource.setURI(uri);
				NestedResourceAdapter nestedResourceAdapter = resultInfoAdapter.getNestedResourceAdapter(uri);

				NestedResourceAdapter.update(newResource, nestedResourceAdapter);
				DtsParseResult newParseResult = new DtsParser().parse("test", root, null, newResource);

				onResult.accept(newParseResult);
			}
		}
	}

	static String percent(double n, double full) {
		return String.valueOf(Math.floor((n / full) * 1000d) / 10d);
	}
}
