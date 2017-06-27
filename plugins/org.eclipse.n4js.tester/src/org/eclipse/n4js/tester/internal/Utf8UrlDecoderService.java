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
package org.eclipse.n4js.tester.internal;

import static com.google.common.base.Strings.nullToEmpty;
import static java.lang.String.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import com.google.inject.Singleton;

import org.eclipse.n4js.tester.UrlDecoderService;

/**
 * URL decoder service with {@link StandardCharsets#UTF_8 UTF-8} support.
 */
@Singleton
public class Utf8UrlDecoderService implements UrlDecoderService {

	@Override
	public String decode(final String toDecode) {
		try {
			return URLDecoder.decode(nullToEmpty(toDecode), valueOf(UTF_8));
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException("Error while decoding argument: " + toDecode, e);
		}
	}

}
