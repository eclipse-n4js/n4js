/**
 * Copyright (c) 2017 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.utils.languages;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.IResourceServiceProvider;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Utility methods for our custom Xtext languages (N4JS, N4JSX, N4IDL, ...) that are <em>language independent</em>, i.e.
 * applicable without change to all those languages.
 * <p>
 * Note that there are language-specific variants of this class, e.g. {@code N4JSLanguageUtils}.
 */
public class N4LanguageUtils {

	@SuppressWarnings("javadoc")
	public static final class ParseResult<T extends EObject> {
		/** The AST. May be <code>null</code> in case of error. */
		public final T ast;
		/** List of syntax error. May be empty but never <code>null</code>. */
		public final List<SyntaxErrorMessage> errors;

		public ParseResult(T ast, Iterable<SyntaxErrorMessage> errors) {
			this.ast = ast;
			this.errors = Lists.newArrayList(errors);
		}
	}

	/**
	 * Same as {@link #parseXtextLanguage(String, ParserRule, Class, String)}, but uses the Xtext language's default
	 * rule name (see {@code AbstractAntlrParser#getDefaultRuleName()}).
	 */
	public static <T extends EObject> ParseResult<T> parseXtextLanguage(String fileExtOfLanguage,
			Class<T> expectedTypeOfRoot, String source) {
		return parseXtextLanguage(fileExtOfLanguage, null, expectedTypeOfRoot, source);
	}

	/**
	 * Same as {@link #parseXtextLanguage(String, ParserRule, Class, Reader)}, but accepts a {@link String} instead of a
	 * {@link Reader}.
	 */
	public static <T extends EObject> ParseResult<T> parseXtextLanguage(String fileExtOfLanguage,
			ParserRule parserRuleOrNull, Class<T> expectedTypeOfRoot, String source) {
		return parseXtextLanguage(fileExtOfLanguage, parserRuleOrNull, expectedTypeOfRoot, new StringReader(source));
	}

	/**
	 * Parses the given string with the parser of the Xtext language denoted by the given file extension. In case of
	 * syntax errors, the returned parse result will have a non-empty list of {@link ParseResult#errors}.
	 */
	public static <T extends EObject> ParseResult<T> parseXtextLanguage(String fileExtOfLanguage,
			ParserRule parserRuleOrNull, Class<T> expectedTypeOfRoot, Reader sourceReader) {
		final IParser parser = getServiceForContext(fileExtOfLanguage, IParser.class)
				.orElseThrow(() -> new RuntimeException(
						"Cannot obtain Xtext parser for language with file extension: " + fileExtOfLanguage));
		final IParseResult result;
		if (parserRuleOrNull != null) {
			result = parser.parse(parserRuleOrNull, sourceReader);
		} else {
			result = parser.parse(sourceReader);
		}
		final Iterable<SyntaxErrorMessage> errors = Iterables.transform(result.getSyntaxErrors(),
				node -> node.getSyntaxErrorMessage());
		final EObject root = result.getRootASTElement();
		if (root != null && expectedTypeOfRoot.isInstance(root)) {
			@SuppressWarnings("unchecked")
			final T rootCasted = (T) root;
			return new ParseResult<>(rootCasted, errors);
		}
		return new ParseResult<>(null, errors);
	}

	/**
	 * Same as {@link #getServiceForContext(URI, Class)}, but accepts any {@link EObject} contained in an Xtext language
	 * resource. Returns <code>null</code> if the given context object is not contained in a {@link Resource}.
	 */
	public static <T> Optional<T> getServiceForContext(EObject context, Class<T> serviceType) {
		Objects.requireNonNull(context);
		Objects.requireNonNull(serviceType);
		final Resource res = context.eResource();
		final URI uri = res != null ? res.getURI() : null;
		return uri != null ? getServiceForContext(uri, serviceType) : Optional.empty();
	}

	/**
	 * Same as {@link #getServiceForContext(URI, Class)}, but accepts any {@link IFile} as context and converts it to
	 * the {@link URI}. Returns <code>null</code> if the given file object cannot be converted to the {@link Resource
	 * resource uri}.
	 *
	 * @see <a href="https://wiki.eclipse.org/EMF/FAQ#How_do_I_map_between_an_EMF_Resource_and_an_Eclipse_IFile.3F">How
	 *      do I map between an EMF Resource and an Eclipse IFile?</a>
	 */
	public static <T> Optional<T> getServiceForContext(IFile iFile, Class<T> serviceType) {
		return getServiceForContext((IResource) iFile, serviceType);
	}

	/**
	 * Same as {@link #getServiceForContext(URI, Class)}, but accepts any {@link IResource} as context and converts it
	 * to the {@link URI}. Returns <code>null</code> if the given file object cannot be converted to the {@link Resource
	 * resource uri}.
	 *
	 * @see <a href="https://wiki.eclipse.org/EMF/FAQ#How_do_I_map_between_an_EMF_Resource_and_an_Eclipse_IFile.3F">How
	 *      do I map between an EMF Resource and an Eclipse IFile?</a>
	 */
	public static <T> Optional<T> getServiceForContext(IResource iResource, Class<T> serviceType) {
		Objects.requireNonNull(iResource);
		Objects.requireNonNull(serviceType);
		final URI uri = URI.createPlatformResourceURI(iResource.getFullPath().toString(), true);
		return uri != null ? getServiceForContext(uri, serviceType) : Optional.empty();
	}

	/**
	 * Same as {@link #getServiceForContext(URI, Class)}, but accepts the file extension of an Xtext language as
	 * context.
	 */
	public static <T> Optional<T> getServiceForContext(String fileExtOfContextLanguage, Class<T> serviceType) {
		final String uriStr = "__synthetic_" + new Random().nextInt(Integer.MAX_VALUE) + "." + fileExtOfContextLanguage;
		final URI uri = URI.createURI(uriStr);
		return getServiceForContext(uri, serviceType);
	}

	/**
	 * Utility method for obtaining the correct instance of a language-specific service for the context identified by
	 * the given {@link URI}. For example, if the URI denotes an N4JS resource, this method will return the service
	 * instance for N4JS, if it denotes an N4JSX resource the service instance for N4JSX will be returned.
	 * <p>
	 * Using this method is like injecting a service with <code>@Inject</code> but makes sure that the correct service
	 * instance is used for the given context URI.
	 *
	 * @param uri
	 *            the URI of an Xtext language resource, e.g. an N4JS, N4JSX, or N4IDL resource.
	 * @param serviceType
	 *            the type of the service to obtain.
	 * @return the service instance or {@link Optional#empty()} if no Xtext resource service provide was found for the
	 *         given URI.
	 */
	public static <T> Optional<T> getServiceForContext(URI uri, Class<T> serviceType) {
		Objects.requireNonNull(uri);
		Objects.requireNonNull(serviceType);
		final IResourceServiceProvider serviceProvider = IResourceServiceProvider.Registry.INSTANCE
				.getResourceServiceProvider(uri);
		return serviceProvider != null ? Optional.ofNullable(serviceProvider.get(serviceType)) : Optional.empty();
	}
}
