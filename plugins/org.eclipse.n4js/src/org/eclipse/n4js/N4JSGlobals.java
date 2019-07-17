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

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.n4js.projectDescription.ProjectType;
import org.eclipse.n4js.projectModel.names.N4JSProjectName;

import com.google.common.collect.ImmutableSet;

/**
 * Global hook for static information about the current setup. Contains file extensions, library names, and other
 * "useful" strings.
 */
public final class N4JSGlobals {

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
	 * Project types for which a dependency to the {@link #N4JS_RUNTIME n4js-runtime} is mandatory.
	 */
	public static final Set<ProjectType> PROJECT_TYPES_REQUIRING_N4JS_RUNTIME = ImmutableSet.of(
			ProjectType.LIBRARY,
			ProjectType.APPLICATION,
			ProjectType.TEST);

	/**
	 * The name of an npm command.
	 */
	public static final String NPM = "npm";
	/**
	 * Name of the npm "node_modules" folder.
	 */
	public static final String NODE_MODULES = "node_modules";
	/**
	 * The name of NPM's package json file.
	 */
	public static final String PACKAGE_JSON = "package.json";

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

	private N4JSGlobals() {
		// private to prevent inheritance & instantiation.
	}
}
