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
package org.eclipse.n4js.n4mf.utils.parsing;

import java.io.StringReader;
import java.util.Random;

import org.eclipse.emf.common.util.URI;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.parser.antlr.N4MFParser;
import org.eclipse.n4js.n4mf.utils.N4MFConstants;
import org.eclipse.n4js.utils.languages.N4LanguageUtils;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.XtextFactory;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.parser.ParseException;

/**
 * Utility for creating N4MF model elements from string values. Reuses actual N4MF parser.Note that returned elements
 * are created in artificial way and may not be valid according to N4MF validations or other expectations for proper
 * manifest files.
 *
 */
public class ManifestValuesParsingUtil {

	/**
	 * Creates instance of {@link ProjectDescription} from provided value or null if it cannot be created.
	 *
	 * @throws ParseException
	 *             when provided data has parse error
	 */
	public static ParserResults<ProjectDescription> parseProjectDescription(String projectDependency) throws Exception {
		return parse(ProjectDescription.class.getSimpleName(), projectDependency);
	}

	/**
	 * Creates instance of {@link ProjectDependency} from provided value or null if it cannot be created.
	 *
	 * @throws ParseException
	 *             when provided data has parse error
	 */
	public static ParserResults<ProjectDependency> parseDependency(String projectDependency) throws Exception {
		return parse(ProjectDependency.class.getSimpleName(), projectDependency);
	}

	/**
	 * Creates instance of {@link DeclaredVersion} from provided value or null if it cannot be created.
	 */
	public static ParserResults<DeclaredVersion> parseDeclaredVersion(String declaredVersion) {
		return parse(DeclaredVersion.class.getSimpleName(), declaredVersion);
	}

	@SuppressWarnings("unchecked")
	private static <T> ParserResults<T> parse(String parseRuleName, String versionNo) {
		final ParserRule parserRule = XtextFactory.eINSTANCE.createParserRule();
		parserRule.setName(parseRuleName);
		final N4MFParser parser = getService(N4MFParser.class, getRandomURI());
		final IParseResult result = parser.parse(parserRule, new StringReader(versionNo));
		final ParserResults<T> res = new ParserResults<>();
		res.ast = (T) result.getRootASTElement();
		result.getSyntaxErrors().forEach(n -> {
			SyntaxErrorMessage syntaxErrorMessage = n.getSyntaxErrorMessage();
			res.addErrors(syntaxErrorMessage.getMessage());
		});
		return res;
	}

	private static URI getRandomURI() {
		String name = "__synthetic_" + new Random().nextInt(Integer.MAX_VALUE) + "."
				+ N4MFConstants.N4MF_FILE_EXTENSION;
		return URI.createURI(name);
	}

	private static <S> S getService(Class<S> serviceType, URI uri) {
		return N4LanguageUtils
				.getServiceForContext(uri, serviceType)
				.orElseThrow(
						() -> new RuntimeException("Cannot obtain " + serviceType + " for syntactic URI :" + uri));
	}
}
