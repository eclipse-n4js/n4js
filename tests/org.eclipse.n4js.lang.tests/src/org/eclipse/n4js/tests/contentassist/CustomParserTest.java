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
package org.eclipse.n4js.tests.contentassist;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.n4js.N4JSInjectorProvider;
import org.eclipse.n4js.ide.editor.contentassist.ContentAssistDataCollectors;
import org.eclipse.n4js.ide.editor.contentassist.ContentAssistTokenTypeMapper;
import org.eclipse.n4js.ide.editor.contentassist.CustomN4JSParser;
import org.eclipse.n4js.ide.editor.contentassist.ParamAwareEntryPointFinder;
import org.eclipse.n4js.ide.editor.contentassist.TokenSourceFactory;
import org.eclipse.xtext.ide.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.RequiredRuleNameComputer;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.antlr.IUnorderedGroupHelper;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.xbase.lib.util.ReflectExtensions;
import org.eclipse.xtext.xtext.RuleNames;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.Provider;

@SuppressWarnings("restriction")
@RunWith(XtextRunner.class)
@InjectWith(N4JSInjectorProvider.class)
public class CustomParserTest extends AbstractContentAssistParserTest implements Provider<IUnorderedGroupHelper> {

	@Inject
	IUnorderedGroupHelper unorderedGroupHelper;
	@Inject
	TokenSourceFactory tokenSourceFactory;
	@Inject
	ParamAwareEntryPointFinder entryPointFinder;
	@Inject
	ContentAssistTokenTypeMapper tokenMapper;
	@Inject
	RequiredRuleNameComputer ruleNameComputer;
	@Inject
	RuleNames ruleNames;

	@Inject
	ReflectExtensions reflExtensions;

	public CustomN4JSParser getParser() {
		CustomN4JSParser parser = new CustomN4JSParser();
		parser.setGrammarAccess(grammarAccess);
		parser.setNameMappings(nameMappings);
		parser.setTokenSourceFactory(tokenSourceFactory);
		parser.setDataCollectors(new ContentAssistDataCollectors(null));
		parser.setEntryPointFinder(entryPointFinder);
		parser.setUnorderedGroupHelper(this);
		parser.initializeTokenTypes(tokenMapper, grammarAccess);
		try {
			reflExtensions.set(parser, "requiredRuleNameComputer", ruleNameComputer);
			reflExtensions.set(parser, "ruleNames", ruleNames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		parser.initializeFor(ruleNames.getAllParserRules().iterator().next());
		return parser;
	}

	@Override
	public Collection<FollowElement> getFollowElements(INode node, int start, int end, boolean strict) {
		return getParser().getFollowElements(node, start, end, strict);
	}

	@Override
	public Collection<FollowElement> getFollowElements(FollowElement prev) {
		if (prev.getLookAhead() == 1) {
			return Collections.emptyList();
		} else {
			return getParser().getFollowElements(prev);
		}

	}

	@Override
	public IUnorderedGroupHelper get() {
		return unorderedGroupHelper;
	}

}
