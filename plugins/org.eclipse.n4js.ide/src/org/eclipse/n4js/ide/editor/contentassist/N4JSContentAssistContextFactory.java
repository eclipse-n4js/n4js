/**
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.editor.contentassist;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.N4JSMetaModelUtils;
import org.eclipse.n4js.smith.Measurement;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory;
import org.eclipse.xtext.ide.editor.contentassist.antlr.PartialContentAssistContextFactory;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;

/**
 * {@link ContentAssistContextFactory} with data collection and support for ASI.
 *
 * Overrides the default {@link org.eclipse.xtext.ide.editor.contentassist.antlr.ContentAssistContextFactory} to use the
 * API that is introduced by the {@link CustomN4JSParser}.
 *
 * All overridden methods basically override the inherited behavior to use the production parser's node model rather
 * than a bogus own lexer.
 *
 * @see CustomN4JSParser#getFollowElements(IParseResult, int, int, boolean)
 */
public class N4JSContentAssistContextFactory extends PartialContentAssistContextFactory {

	@Inject
	private ContentAssistDataCollectors dataCollectors;

	@Override
	@SuppressWarnings("hiding")
	protected Multimap<EObject, AbstractElement> computeCurrentModel(EObject currentModel, INode lastCompleteNode,
			Collection<AbstractElement> followElements) {
		List<AbstractElement> promisingFollowElements = followElements.stream()
				.filter(this::isPromisingFollowElement).collect(Collectors.toList());
		return super.computeCurrentModel(currentModel, lastCompleteNode, promisingFollowElements);
	}

	/**
	 * GH-2167: always processing all follow elements leads to ~600ms being spent in method
	 * {@link ContentAssistContextFactory#canBeCalledAfter(AbstractRule, EObject, String, EObject) #canBeCalledAfter()}
	 * in some important use cases (e.g. in case of content assist on top level with a prefix of at least 1 character).
	 * By only including assignments with a cross-reference to certain {@link EClass}es this delay can be avoided almost
	 * entirely.
	 */
	private boolean isPromisingFollowElement(AbstractElement element) {
		if (element instanceof Assignment) {
			AbstractElement terminal = ((Assignment) element).getTerminal();
			if (terminal instanceof CrossReference) {
				org.eclipse.xtext.TypeRef type = ((CrossReference) terminal).getType();
				EClassifier classifier = type != null ? type.getClassifier() : null;
				if (classifier != null) {
					return N4JSMetaModelUtils.isContributingContentAssistProposals(classifier);
				}
			}
		} else if (element instanceof Keyword) {
			// The cost for enabling all keywords would be ~220ms (in the investigated use cases; could be even higher
			// in some cases; will certainly be lower in other cases). As a compromise, we could use a list of keywords
			// we want to include, but for now we simply disable them all:
			return false;
		}
		return false;
	}

	@Override
	protected ContentAssistContext[] doCreateContexts(int offset) {
		try (Measurement m = dataCollectors.dcCreateContexts().getMeasurement()) {
			return super.doCreateContexts(offset);
		}
	}
}
