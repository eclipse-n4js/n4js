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

import org.eclipse.n4js.external.libraries.ExternalLibraryFolderUtils;

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
	 * Files extension of JSX source files (<b>not</b> including the separator dot). TODO: This will be OK when JSX is
	 * merged into N4JS.
	 */
	public static final String JSX_FILE_EXTENSION = "jsx";

	/**
	 * Files extension of N4JSX source files (<b>not</b> including the separator dot). TODO: This will be OK when N4JSX
	 * is merged into N4JS.
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
	public static final String MANGELHAFT = VENDOR_ID + ".mangelhaft";
	/**
	 * Mangelhaft Assert
	 */
	public static final String MANGELHAFT_ASSERT = MANGELHAFT + ".assert";

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
	 * {@link #SHIPPED_CODE_SOURCES_FOLDER_NAME}.
	 */
	public static final String N4JS_LIBS_FOLDER_NAME = "n4js-libs";

	/**
	 * Relative path to the folder in the N4JS Git repository containing the projects with the source code that will be
	 * shipped with the IDE, as part of the library manager. The path is relative to the root folder of the N4JS Git
	 * repository.
	 */
	public static final String SHIPPED_CODE_SOURCES_FOLDER_NAME = N4JS_LIBS_FOLDER_NAME + "/" + "packages";

	/**
	 * The name of an npm command.
	 */
	public static final String NPM = "npm";
	/**
	 * Name of the npm "node_modules" folder.
	 */
	public static final String NODE_MODULES = "node_modules";
	/**
	 * The name of an npm install command.
	 */
	public static final String NPM_INSTALL = NPM + " install";
	/**
	 * The name of NPM's package json file.
	 */
	public static final String PACKAGE_JSON = ExternalLibraryFolderUtils.PACKAGE_JSON;
	/**
	 * Name of the package.json fragment file from the "n4jsd" type definitions repository.
	 */
	public static final String PACKAGE_FRAGMENT_JSON = "package-fragment.json";

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
