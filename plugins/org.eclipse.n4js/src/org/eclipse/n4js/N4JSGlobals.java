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
package org.eclipse.n4js;

import static com.google.common.collect.Sets.newLinkedHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableCollection;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.n4js.naming.N4JSQualifiedNameConverter;
import org.eclipse.n4js.packagejson.projectDescription.ProjectType;
import org.eclipse.n4js.utils.UtilN4;
import org.eclipse.n4js.workspace.utils.N4JSProjectName;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;

/**
 * Global hook for static information about the current setup. Contains file extensions, library names, and other
 * "useful" strings.
 */
public final class N4JSGlobals {

	/** Maximum value of type 'int' in N4JS. */
	public static final int INT32_MAX_VALUE = Integer.MAX_VALUE;
	/** Minimum value of type 'int' in N4JS. */
	public static final int INT32_MIN_VALUE = Integer.MIN_VALUE;
	/** Same as #{@link #INT32_MAX_VALUE}, but as a {@link BigDecimal}. */
	public static final BigDecimal INT32_MAX_VALUE_BD = new BigDecimal(INT32_MAX_VALUE);
	/** Same as #{@link #INT32_MIN_VALUE}, but as a {@link BigDecimal}. */
	public static final BigDecimal INT32_MIN_VALUE_BD = new BigDecimal(INT32_MIN_VALUE);

	/**
	 * Files extension of JS source files (<b>not</b> including the separator dot).
	 */
	public static final String JS_FILE_EXTENSION = "js";

	/**
	 * Files extension of N4JS source files (<b>not</b> including the separator dot).
	 */
	public static final String N4JS_FILE_EXTENSION = "n4js";
	/**
	 * Files extension of JSX source files (<b>not</b> including the separator dot).
	 */
	public static final String JSX_FILE_EXTENSION = "jsx";

	/**
	 * Files extension of N4JSX source files (<b>not</b> including the separator dot).
	 */
	public static final String N4JSX_FILE_EXTENSION = "n4jsx";

	/**
	 * Files extension of N4JS definition files (<b>not</b> including the separator dot).
	 */
	public static final String N4JSD_FILE_EXTENSION = "n4jsd";

	/**
	 * Files extension of N4TS source files (<b>not</b> including the separator dot).
	 */
	public static final String N4TS_FILE_EXTENSION = "n4ts";
	/**
	 * Files extension of XT source files (<b>not</b> including the separator dot).
	 */
	public static final String XT_FILE_EXTENSION = "xt";

	/**
	 * Files extension of N4IDL source files (<b>not</b> including the separator dot).
	 */
	public static final String N4IDL_FILE_EXTENSION = "n4idl";

	/**
	 * Vendor ID
	 */
	public static final String VENDOR_ID = "org.eclipse.n4js";
	/**
	 * Mangelhaft
	 */
	public static final N4JSProjectName MANGELHAFT = new N4JSProjectName(VENDOR_ID + ".mangelhaft");
	/**
	 * Mangelhaft Assert
	 */
	public static final N4JSProjectName MANGELHAFT_ASSERT = new N4JSProjectName(MANGELHAFT + ".assert");

	/**
	 * Name of the npm package containing the mangelhaft command-line interface.
	 */
	public static final N4JSProjectName MANGELHAFT_CLI = new N4JSProjectName("n4js-mangelhaft-cli");

	/**
	 * Unmodifiable list containing {@link #N4JSD_FILE_EXTENSION},
	 * {@link #N4JS_FILE_EXTENSION},{@link #N4JSX_FILE_EXTENSION}, {@link #JS_FILE_EXTENSION},
	 * {@link #JSX_FILE_EXTENSION}.
	 */
	// TODO TODO IDE-2493 multiple languages topic
	public static final Collection<String> ALL_N4_FILE_EXTENSIONS = unmodifiableCollection(newLinkedHashSet(asList(
			N4JS_FILE_EXTENSION,
			N4JSD_FILE_EXTENSION,
			N4JSX_FILE_EXTENSION,
			JSX_FILE_EXTENSION,
			JS_FILE_EXTENSION)));

	/**
	 * Name of the N4JS Git repository, i.e. "n4js". Same as {@link UtilN4#N4JS_GIT_REPOSITORY_NAME}.
	 */
	public static final String N4JS_GIT_REPOSITORY_NAME = UtilN4.N4JS_GIT_REPOSITORY_NAME;

	/**
	 * Name of the top-level folder in the N4JS Git repository containing the main N4JS plugins.
	 */
	public static final String PLUGINS_FOLDER_NAME = "plugins";

	/**
	 * Name of the top-level folder in the N4JS Git repository containing the N4JS runtime code, test frame work, and
	 * other code shipped with the IDE.
	 * <p>
	 * NOTE: the actual projects are not contained directly in this folder but in a sub folder, see
	 * {@link #N4JS_LIBS_SOURCES_PATH}.
	 */
	public static final String N4JS_LIBS_FOLDER_NAME = "n4js-libs";

	/**
	 * Path to the folder in the N4JS Git repository containing the source code of the "n4js-libs", relative to the
	 * repository's root folder.
	 */
	public static final String N4JS_LIBS_SOURCES_PATH = N4JS_LIBS_FOLDER_NAME + "/" + "packages";

	/**
	 * Name of the npm package containing the N4JS bootstrap and runtime code, i.e. the code defining internal low-level
	 * functions such as {@code $makeClass()} and containing core runtime code such as the implementation of N4Injector.
	 * <p>
	 * It is expected that this npm package lives in the N4JS Git repository at path {@value #N4JS_LIBS_SOURCES_PATH},
	 * cf. {@link #N4JS_LIBS_SOURCES_PATH}.
	 */
	public static final N4JSProjectName N4JS_RUNTIME = new N4JSProjectName("n4js-runtime");

	/**
	 * Runtime for ECMA 402.
	 */
	public static final N4JSProjectName N4JS_RUNTIME_ECMA402 = new N4JSProjectName("n4js-runtime-ecma402");

	/**
	 * Runtime for node.js
	 */
	public static final N4JSProjectName N4JS_RUNTIME_NODE = new N4JSProjectName("n4js-runtime-node");

	/**
	 * Runtime for HTML5 DOM definitions, i.e. <code>window</code>, <code>document</code>, etc.
	 */
	public static final N4JSProjectName N4JS_RUNTIME_HTML5 = new N4JSProjectName("n4js-runtime-html5");

	/**
	 * Project types for which a dependency to the {@link #N4JS_RUNTIME n4js-runtime} is mandatory.
	 */
	public static final Set<ProjectType> PROJECT_TYPES_REQUIRING_N4JS_RUNTIME = ImmutableSet.of(
			ProjectType.LIBRARY,
			ProjectType.APPLICATION,
			ProjectType.TEST);

	/**
	 * Project types for which the generator is disabled.
	 */
	public static final Set<ProjectType> PROJECT_TYPES_WITHOUT_GENERATION = ImmutableSet.of(
			ProjectType.PLAINJS,
			ProjectType.DEFINITION,
			ProjectType.VALIDATION);

	/**
	 * Project types for which .d.ts generation will always be inactive, even if
	 *
	 * <pre>
	 * "generator": {
	 *     "d.ts": true
	 * }
	 * </pre>
	 *
	 * is given in the package.json file.
	 */
	public static final Set<ProjectType> PROJECT_TYPES_WITHOUT_DTS_GENERATION = ImmutableSet.of(
			ProjectType.PLAINJS,
			ProjectType.DEFINITION,
			ProjectType.VALIDATION,
			ProjectType.RUNTIME_ENVIRONMENT,
			ProjectType.RUNTIME_LIBRARY);

	/**
	 * The name of an npm command.
	 */
	public static final String NPM = "npm";
	/**
	 * Name of the npm scope of n4js definition projects.
	 */
	public static final String N4JSD_SCOPE = "@n4jsd";
	/**
	 * Name of the npm node_modules folder.
	 */
	public static final String NODE_MODULES = "node_modules";
	/**
	 * The name of NPM's package json file.
	 */
	public static final String PACKAGE_JSON = UtilN4.PACKAGE_JSON;

	/**
	 * Projects with a name ending in one of these suffixes are assumed to be API projects as defined by the API/Impl
	 * concept.
	 * <p>
	 * NOTE: normally API projects should be identified by a project type of {@link ProjectType#API API}. Use of these
	 * suffixes is only intended in temporary work-around implementations.
	 * <p>
	 * IMPORTANT: in addition to the direct references to this constant, another use of these suffixes is located in
	 * file {@code NodeTestRunner.n4js}.
	 */
	public static final String[] API_PROJECT_NAME_SUFFIXES = { ".api", "-api" };

	/**
	 * The name of the files storing each N4JS project's meta-information (serialized TModules, etc.).
	 */
	public static final String N4JS_PROJECT_STATE = ".n4js.projectstate";

	/**
	 * The name of the N4JS test catalog file.
	 */
	public static final String TEST_CATALOG = "test-catalog.json";

	/**
	 * The name of the tsconfig.json file.
	 */
	public static final String TS_CONFIG = "tsconfig.json";

	/**
	 * All project names of n4js libraries.
	 */
	public static final Set<N4JSProjectName> ALL_N4JS_LIBS = ImmutableSet.of(
			new N4JSProjectName("n4js-cli"),
			new N4JSProjectName("n4js-mangelhaft-cli"),
			N4JS_RUNTIME,
			N4JS_RUNTIME_ECMA402,
			new N4JSProjectName("n4js-runtime-es2015"),
			new N4JSProjectName("n4js-runtime-esnext"),
			new N4JSProjectName("n4js-runtime-fetch"),
			N4JS_RUNTIME_HTML5,
			N4JS_RUNTIME_NODE,
			new N4JSProjectName("n4js-runtime-node-tests"),
			new N4JSProjectName("n4js-runtime-v8"),
			new N4JSProjectName("org.eclipse.n4js.mangelhaft"),
			new N4JSProjectName("org.eclipse.n4js.mangelhaft.assert"),
			new N4JSProjectName("org.eclipse.n4js.mangelhaft.assert.test"),
			new N4JSProjectName("org.eclipse.n4js.mangelhaft.reporter.console"),
			new N4JSProjectName("org.eclipse.n4js.mangelhaft.reporter.ide"),
			new N4JSProjectName("org.eclipse.n4js.mangelhaft.reporter.ide.test"),
			new N4JSProjectName("org.eclipse.n4js.mangelhaft.reporter.xunit"),
			new N4JSProjectName("org.eclipse.n4js.mangelhaft.test"));

	/**
	 * The values of this map define TypeScript libraries to be included in the "lib" property of an auto-generated
	 * <code>tsconfig.json</code> file whenever the corresponding N4JS runtime library is declared as required runtime
	 * library in the containing project's <code>package.json</code> file.
	 */
	public static final ImmutableSetMultimap<N4JSProjectName, String> N4JS_DTS_LIB_CORRESPONDENCE = ImmutableSetMultimap
			.of(N4JS_RUNTIME_HTML5, "dom");

	/**
	 * String used to separate segments in the string representation of a {@link QualifiedName qualified name}.
	 *
	 * @see N4JSQualifiedNameConverter#DELIMITER
	 */
	public static final String QUALIFIED_NAME_DELIMITER = "/";

	/**
	 * Character used to separate the namespace name from the exported element's name when referring to an element via a
	 * namespace import. For example:
	 *
	 * <pre>
	 * import * as NS from "some/other/module"
	 *
	 * let c: NS.OtherClass;
	 * </pre>
	 */
	public static final char NAMESPACE_ACCESS_DELIMITER = '.';

	/**
	 * All HTML tags.
	 * <p>
	 * Source: <a href="http://www.w3schools.com/tags/">http://www.w3schools.com/tags/</a>.
	 */
	public static final Set<String> HTML_TAGS = new LinkedHashSet<>(Arrays.asList(
			"a", "abbr", "address", "area", "article", "aside", "audio",
			"b", "base", "bdi", "bdo", "blockquote", "body", "br", "button",
			"canvas", "caption", "cite", "code", "col", "colgroup",
			"datalist", "dd", "del", "details", "dfn", "dialog", "div", "dl", "dt",
			"em", "embed",
			"fieldset", "figcaption", "figure", "footer", "form",
			"h1", "h2", "h3", "h4", "h5", "h6", "head", "header", "hr", "html",
			"i", "iframe", "img", "input", "ins",
			"kbd", "keygen",
			"label", "legend", "li", "link",
			"main", "map", "mark", "menu", "menuitem", "meta", "meter",
			"nav", "noscript",
			"object", "ol", "optgroup", "option",
			"p", "param", "pre", "progress", "q", "rp", "rt", "ruby",
			"s", "samp", "script", "section", "select", "small", "source", "span",
			"strong", "style", "sub", "summary", "sup", "svg",
			"table", "tbody", "td", "textarea", "tfoot", "th", "thead", "time", "title", "tr", "track",
			"u", "ul", "use", "var", "video", "wbr"));

	/**
	 * All SVG tags.
	 * <p>
	 * Source: <a href=
	 * "https://developer.mozilla.org/en-US/docs/Web/SVG/Element">https://developer.mozilla.org/en-US/docs/Web/SVG/Element</a>.
	 */
	public static final Set<String> SVG_TAGS = new LinkedHashSet<>(Arrays.asList(
			"a", "altGlyph", "altGlyphDef", "altGlyphItem", "animate", "animateColor", "animateMotion",
			"animateTransform", "circle", "clipPath", "color-profile", "cursor", "defs", "desc", "discard", "ellipse",
			"feBlend", "feColorMatrix", "feComponentTransfer", "feComposite", "feConvolveMatrix", "feDiffuseLighting",
			"feDisplacementMap", "feDistantLight", "feDropShadow", "feFlood", "feFuncA", "feFuncB", "feFuncG",
			"feFuncR", "feGaussianBlur", "feImage", "feMerge", "feMergeNode", "feMorphology", "feOffset",
			"fePointLight", "feSpecularLighting", "feSpotLight", "feTile", "feTurbulence", "filter", "font",
			"font-face", "font-face-format", "font-face-name", "font-face-src", "font-face-uri", "foreignObject", "g",
			"glyph", "glyphRef", "hatch", "hatchpath", "hkern", "image", "line", "linearGradient", "marker", "mask",
			"mesh", "meshgradient", "meshpatch", "meshrow", "metadata", "missing-glyph", "mpath", "path", "pattern",
			"polygon", "polyline", "radialGradient", "rect", "script", "set", "solidcolor", "stop", "style", "svg",
			"switch", "symbol", "text", "textPath", "title", "tref", "tspan", "unknown", "use", "view", "vkern"));

	/**
	 * The preamble added to all output files generated by the N4JS {@code EcmaScriptTranspiler} and .d.ts generator.
	 */
	public static final String OUTPUT_FILE_PREAMBLE = "// Generated by N4JS transpiler; for copyright see original N4JS source file.\n";

	/**
	 * File name of {@code n4js-runtime/n4jsglobals.d.ts}.
	 */
	public static final String N4JS_GLOBALS_DTS = "n4jsglobals.d.ts";

	/**
	 * Mandatory import for every generated d.ts file used by the d.ts generator.
	 */
	public static final String IMPORT_N4JSGLOBALS = "import 'n4js-runtime'";

	private N4JSGlobals() {
		// private to prevent inheritance & instantiation.
	}
}
