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
package org.eclipse.n4js.ui.editor.autoedit;

import static org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeToPartitionMapper.JS_DOC_PARTITION;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeToPartitionMapper.REG_EX_PARTITION;
import static org.eclipse.n4js.ui.editor.syntaxcoloring.TokenTypeToPartitionMapper.TEMPLATE_LITERAL_PARTITION;

import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider;
import org.eclipse.xtext.ui.editor.autoedit.MultiLineTerminalsEditStrategy;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;

import com.google.inject.Inject;
import com.google.inject.MembersInjector;

/**
 */
public class AutoEditStrategyProvider extends DefaultAutoEditStrategyProvider {

	@Inject
	private MembersInjector<MultiLineTerminalsEditStrategy> injector;

	@Override
	protected void configure(IEditStrategyAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.accept(partitionEndSkippingEditStrategy.get(), REG_EX_PARTITION);
	}

	@Override
	protected void configureIndentationEditStrategy(IEditStrategyAcceptor acceptor) {
		super.configureIndentationEditStrategy(acceptor);
		acceptor.accept(defaultIndentLineAutoEditStrategy.get(), JS_DOC_PARTITION);
		acceptor.accept(defaultIndentLineAutoEditStrategy.get(), REG_EX_PARTITION);
	}

	@Override
	protected void configureMultilineComments(IEditStrategyAcceptor acceptor) {
		// IAutoEditStrategy jsdoc = multiLineTerminals.newInstance("/**", " + ", " */");

		MultiLineTerminalsEditStrategy jsdoc = new JSDocEditStrategy("/**", " + ", " */");
		injector.injectMembers(jsdoc);

		IAutoEditStrategy multiline = multiLineTerminals.newInstance("/*", " * ", " */");
		IAutoEditStrategy singleline = singleLineTerminals.newInstance("/*", " */", new SupressingMLCommentPredicate());

		acceptor.accept(singleline, IDocument.DEFAULT_CONTENT_TYPE);

		acceptor.accept(jsdoc, IDocument.DEFAULT_CONTENT_TYPE);
		acceptor.accept(jsdoc, TerminalsTokenTypeToPartitionMapper.COMMENT_PARTITION);
		acceptor.accept(jsdoc, JS_DOC_PARTITION);
		acceptor.accept(jsdoc, REG_EX_PARTITION);

		acceptor.accept(multiline, IDocument.DEFAULT_CONTENT_TYPE);
		acceptor.accept(multiline, TerminalsTokenTypeToPartitionMapper.COMMENT_PARTITION);
		acceptor.accept(multiline, JS_DOC_PARTITION);
		acceptor.accept(singleLineTerminals.newInstance("/*", " */"), REG_EX_PARTITION);
		acceptor.accept(multiline, REG_EX_PARTITION);
	}

	@Override
	protected void configureCurlyBracesBlock(IEditStrategyAcceptor acceptor) {
		super.configureCurlyBracesBlock(acceptor);
		acceptor.accept(singleLineTerminals.newInstance("{", "}"), TEMPLATE_LITERAL_PARTITION);
		acceptor.accept(singleLineTerminals.newInstance("{", "}"), JS_DOC_PARTITION);
		acceptor.accept(singleLineTerminals.newInstance("{", "}", new IntStrategyPredicate()), REG_EX_PARTITION);
	}

	@Override
	protected void configureSquareBrackets(IEditStrategyAcceptor acceptor) {
		super.configureSquareBrackets(acceptor);
		acceptor.accept(singleLineTerminals.newInstance("[", "]"), REG_EX_PARTITION);
	}

	@Override
	protected void configureParenthesis(IEditStrategyAcceptor acceptor) {
		super.configureParenthesis(acceptor);
		acceptor.accept(singleLineTerminals.newInstance("(", ")"), JS_DOC_PARTITION);
		acceptor.accept(singleLineTerminals.newInstance("(", ")"), REG_EX_PARTITION);
	}

	@Override
	protected void configureStringLiteral(IEditStrategyAcceptor acceptor) {
		super.configureStringLiteral(acceptor);
		acceptor.accept(partitionInsert.newInstance("`", "`"), IDocument.DEFAULT_CONTENT_TYPE);
		// The following two are registered for the default content type, because on deletion
		// the command.offset is cursor-1, which is outside the partition of terminals.length = 1.
		// How crude is that?
		// Note that in case you have two string literals following each other directly, the deletion strategy wouldn't
		// apply.
		// One could add the same strategy for the STRING partition in addition to solve this
		acceptor.accept(partitionDeletion.newInstance("`", "`"), IDocument.DEFAULT_CONTENT_TYPE);
		acceptor.accept(partitionEndSkippingEditStrategy.get(), TEMPLATE_LITERAL_PARTITION);
	}
}
