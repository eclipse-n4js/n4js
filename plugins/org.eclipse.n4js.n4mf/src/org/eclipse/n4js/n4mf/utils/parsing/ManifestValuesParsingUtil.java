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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4mf.DeclaredVersion;
import org.eclipse.n4js.n4mf.ProjectDependency;
import org.eclipse.n4js.n4mf.ProjectDescription;
import org.eclipse.n4js.n4mf.parser.antlr.N4MFParser;
import org.eclipse.n4js.n4mf.services.N4MFGrammarAccess;
import org.eclipse.n4js.n4mf.utils.N4MFConstants;
import org.eclipse.n4js.utils.languages.N4LanguageUtils;
import org.eclipse.xtext.ParserRule;
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
	 * Creates instance of {@link ProjectDescription} from provided value. Never returns <code>null</code>, but the
	 * 'ast' property in the returned {@link ParserResults} may be <code>null</code> in case of error.
	 *
	 * @throws ParseException
	 *             when provided data has parse error
	 */
	public static ParserResults<ProjectDescription> parseProjectDescription(String manifestText) throws Exception {
		return parse(getGrammarAccess().getProjectDescriptionRule(), ProjectDescription.class, manifestText);
	}

	/**
	 * Creates instance of {@link ProjectDependency} from provided value. Never returns <code>null</code>, but the 'ast'
	 * property in the returned {@link ParserResults} may be <code>null</code> in case of error.
	 *
	 * @throws ParseException
	 *             when provided data has parse error
	 */
	public static ParserResults<ProjectDependency> parseDependency(String manifestText) throws Exception {
		return parse(getGrammarAccess().getProjectDependencyRule(), ProjectDependency.class, manifestText);
	}

	/**
	 * Creates instance of {@link DeclaredVersion} from provided value. Never returns <code>null</code>, but the 'ast'
	 * property in the returned {@link ParserResults} may be <code>null</code> in case of error.
	 */
	public static ParserResults<DeclaredVersion> parseDeclaredVersion(String manifestText) {
		return parse(getGrammarAccess().getDeclaredVersionRule(), DeclaredVersion.class, manifestText);
	}

	private static <T extends EObject> ParserResults<T> parse(ParserRule parserRule, Class<T> expectedTypeOfRoot,
			String manifestText) {
		final N4MFParser parser = getService(N4MFParser.class);
		final IParseResult result = parser.parse(parserRule, new StringReader(manifestText));
		final ParserResults<T> res = new ParserResults<>();
		final EObject root = result.getRootASTElement();
		if (root != null && expectedTypeOfRoot.isInstance(root)) {
			@SuppressWarnings("unchecked")
			final T rootCasted = (T) root;
			res.ast = rootCasted;
		}
		result.getSyntaxErrors().forEach(n -> {
			SyntaxErrorMessage syntaxErrorMessage = n.getSyntaxErrorMessage();
			res.addErrors(syntaxErrorMessage.getMessage());
		});
		return res;
	}

	private static N4MFGrammarAccess getGrammarAccess() {
		return getService(N4MFGrammarAccess.class);
	}

	private static <S> S getService(Class<S> serviceType) {
		return N4LanguageUtils
				.getServiceForContext(N4MFConstants.N4MF_FILE_EXTENSION, serviceType)
				.orElseThrow(
						() -> new RuntimeException("Cannot obtain " + serviceType + " for synthetic URI"));
	}
}
