package org.eclipse.n4js.json.testsuite;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.n4js.json.JSONInjectorProvider;
import org.eclipse.n4js.json.JSONParseHelper;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.parser.ParseException;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.validation.ValidationTestHelper;
import org.eclipse.xtext.validation.Issue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.inject.Inject;

/**
 * Executes the JSON parser test suite to be found in the corresponding ZIP file
 * of this bundle.
 * 
 * <p>
 * The tests in the JSONTestSuite project are a directory of JSON files, each representing
 * a single test case. Each file is prefixed with either 'n_', 'y_' or 'i_'. The prefixes
 * indicate whether the test suite expects the corresponding JSON content to be parsed (and validated)
 * successfully by a JSON parser:
 * 
 * <ul>
 * 	<li>'n_' indicates that the parsing should fail.</li>
 * 	<li>'y_' indicates that the parsing should succeed.</li>
 * 	<li>'i_' indicates that the parser may fail or succeed based on implementation specific behavior.</li>
 * <ul>
 * 
 * In general, this test class uses the JSON parser to parse and validate all files to be found in 
 * the test suite, and asserts that they yield the expected parsing/validation result.
 * 
 *  In a few cases, we also declare exceptions (cf. {@link #EXCEPTIONS_SUCCESSFUL}, {@link #EXCEPTIONS_UNSUCCESSFUL} 
 *  and {@link #EXCEPTION_STACK_OVERFLOW}) where we modify those test expectations to accommodate for the
 *  specific behavior of our parser (see notes on theses constants for more details).
 * </p>
 */
@RunWith(XtextRunner.class)
@InjectWith(JSONInjectorProvider.class)
public class JSONTestSuite extends Assert {

	/**
	 * Archive containing JSON test suite test files.
	 */
	public static final String JSON_TEST_SUITE_ARCHIVE = "JSONTestSuite.zip";

	/** 
	 * These files are expected to not pass the JSON parser and validator, although the
	 * test suite would specifies them to pass.
	 */
	public static final Collection<String> EXCEPTIONS_UNSUCCESSFUL = new HashSet<>(
			Arrays.asList("test_parsing/i_number_huge_exp.json"));

	/** 
	 * These files are expected to pass the JSON parser and validator although
	 * the test suite specifies them not to pass.
	 */
	public static final Collection<String> EXCEPTIONS_SUCCESSFUL = new HashSet<>(Arrays.asList(
			// we allow empty JSON documents
			"test_parsing/n_single_space.json", "test_parsing/n_structure_no_data.json",
			"test_parsing/n_structure_UTF8_BOM_no_data.json", "test_parsing/n_structure_whitespace_formfeed.json",
			// exception for comments
			"test_parsing/n_object_trailing_comment.json", "test_parsing/n_object_trailing_comment_slash_open.json",
			"test_parsing/n_structure_object_with_comment.json"));
	
	/** These files are expected to fail due to a stack overflow exception (limits of a recursive decent parser). */
	public static final Collection<String> EXCEPTION_STACK_OVERFLOW = new HashSet<>(Arrays.asList(
			"test_parsing/n_structure_100000_opening_arrays.json",
			"test_parsing/n_structure_open_array_object.json"
	));
	
	@Inject
	private JSONParseHelper jsonParseHelper;
	@Inject
	private ValidationTestHelper validationTestHelper;

	@Test
	public void testJSONTestSuite() throws IOException, URISyntaxException {
		// obtain list of '.json' files to be found in the test suite ZIP file
		Collection<ZipEntry> jsonDataFromZippedRoot = getJSONDataFromZippedRoot(JSON_TEST_SUITE_ARCHIVE);

		// test each entry/file
		for (ZipEntry entry : jsonDataFromZippedRoot) {
			testEntry(entry);
		}
	}

	/**
	 * Parses and validates the JSON text to be found in {@code entry}.
	 * 
	 * Asserts that the JSON parser and validation reports issues on the
	 * corresponding JSON resource in accordance with the expectations the test
	 * suite asserts.
	 */
	private void testEntry(ZipEntry entry) throws IOException, URISyntaxException {
		final String jsonText = getContentsFromFileEntry(entry, JSON_TEST_SUITE_ARCHIVE);
		final boolean shouldValidate = computeTestExpectation(entry);

		try {
			final JSONDocument document = jsonParseHelper.parse(jsonText);
			final List<Issue> issues = validationTestHelper.validate(document);

			final boolean doesValidate = issues.stream().filter(i -> i.getSeverity() == Severity.ERROR).count() == 0;

			if (shouldValidate != doesValidate) {
				if (shouldValidate) {
					fail(entry.getName() + " is expected to validate but the JSON "
							+ "parser reports the following issues: " + getIssuesDescription(issues)
							+ "\n File content:\n" + jsonText);
				} else {
					fail(entry.getName() + " is expected to not validate but the "
							+ "JSON parser does not report any issues." + "\n File content:\n" + jsonText);
				}
			}

		} catch (Exception e) {
			// check whether a resource caused a stack overflow exception and whether
			// this is expected behavior (cf. EXCEPTION_STACK_OVERFLOW).
			if (e instanceof ParseException &&
				e.getCause() != null &&
				e.getCause().getCause() instanceof StackOverflowError) {
				
				if (EXCEPTION_STACK_OVERFLOW.contains(entry.getName())) {
					return;
				} else {
					fail("Parsing JSON resource " + entry.getName() + " caused a stack overflow, although not expected.");
				}
				
			}
			// otherwise let test fail with the thrown parse exception
			throw new AssertionError("Failed to parse JSON resource " + entry.getName() + ": ", e);
		}

	}

	/**
	 * Computes the test expectation of the test suite for running a JSON parser 
	 * on the JSON file that is represented by the given {@link ZipEntry}.
	 * 
	 * Considers the exceptions and filename prefixes.
	 * 
	 * Returns {@code true} if parsing and validation is expected to succeed.
	 * Returns {@code false} otherwise.
	 */
	private static boolean computeTestExpectation(ZipEntry entry) {
		final String filename = getFilename(entry);

		// check explicitly declared test expectations first
		if (EXCEPTIONS_SUCCESSFUL.contains(entry.getName())) {
			return true;
		}
		if (EXCEPTIONS_UNSUCCESSFUL.contains(entry.getName())) {
			return false;
		}

		// otherwise infer test expectations based on 'i_', 'n_' and 'y_' prefixes
		if (filename.startsWith("i_")) {
			return true;
		} else if (filename.startsWith("n_")) {
			return false;
		} else if (filename.startsWith("y_")) {
			return true;
		} else {
			throw new IllegalArgumentException(
					"Failed to compute JSON parser tests expectation " + "for test suite file " + entry.getName());
		}
	}

	/**
	 * Creates a textual description of the given list of issues, including severity
	 * and line number.
	 */
	private static String getIssuesDescription(List<Issue> issues) {
		return issues.stream()
				.map(i -> i.getSeverity().toString() + " at line " + i.getLineNumber() + ": " + i.getMessage())
				.collect(Collectors.joining(",\n"));
	}

	/**
	 * Determines the filename of the given {@link ZipEntry}.
	 */
	private static String getFilename(ZipEntry entry) {
		return new File(entry.getName()).toPath().getFileName().toString();
	}

	/**
	 * Returns the content of the given {@link ZipEntry} file as {@link String}.
	 * 
	 * @param entry
	 *            The zip entry to read.
	 * @param archiveFilename
	 *            The filename of the archive to read from.
	 */
	public static String getContentsFromFileEntry(final ZipEntry entry, String archiveFilename)
			throws IOException, URISyntaxException {
		final URL rootURL = Thread.currentThread().getContextClassLoader().getResource(archiveFilename);
		try (final ZipFile root = new ZipFile(new File(rootURL.toURI()));) {
			ByteSource byteSource = new ByteSource() {
				@Override
				public InputStream openStream() throws IOException {
					return root.getInputStream(entry);
				}
			};

			CharSource charSrc = byteSource.asCharSource(computeCharset(entry));
			return charSrc.read();
		}
	}

	/**
	 * Returns the list of JSON {@link ZipEntry}s that can be found in the archive at
	 * the given path.
	 */
	private static Collection<ZipEntry> getJSONDataFromZippedRoot(String zipFilename)
			throws IOException, URISyntaxException {

		URL rootURL = Thread.currentThread().getContextClassLoader().getResource(zipFilename);
		ZipFile root = new ZipFile(new File(rootURL.toURI()));
		Collection<ZipEntry> entries = new ArrayList<>();
		try {
			filterJSONFilesFromZippedFolder(root, zipFilename, entries);
		} finally {
			root.close();
		}

		return entries;
	}

	/**
	 * Filters the content of the given {@link ZipFile} for JSON files and adds
	 * them to {@code files}.
	 */
	private static void filterJSONFilesFromZippedFolder(final ZipFile file, final String resourceName,
			Collection<ZipEntry> files) {
		Enumeration<? extends ZipEntry> entries = file.entries();
		// skip directory entries and filter for json files only
		Iterator<? extends ZipEntry> fileEntries = Iterators.filter(Iterators.forEnumeration(entries),
				new Predicate<ZipEntry>() {
					@Override
					public boolean apply(ZipEntry input) {
						return !input.isDirectory() && input.getName().endsWith(".json");
					}
				});
		fileEntries.forEachRemaining(e -> files.add(e));
	}

	/**
	 * Computes the {@link Charset} to use when loading the given {@link ZipEntry}
	 * based on its filename.
	 * 
	 * Falls back to an UTF-8 decoding, if no other character set can be determined.
	 */
	private static Charset computeCharset(ZipEntry entry) {
		if (entry.getName().contains("UTF-16LE") || entry.getName().contains("utf16LE")) {
			return Charsets.UTF_16LE;
		} else if (entry.getName().contains("UTF-16BE") || entry.getName().contains("utf16BE")) {
			return Charsets.UTF_16BE;
		} else {
			return Charsets.UTF_8;
		}
	}
}
