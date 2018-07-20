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
package org.eclipse.n4js.semver.ui;

import com.google.inject.Binder;
import com.google.inject.Provider;
import com.google.inject.name.Names;
import org.eclipse.n4js.semver.ide.contentassist.antlr.SemverParser;
import org.eclipse.n4js.semver.ide.contentassist.antlr.internal.InternalSemverLexer;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ide.LexerIdeBindings;
import org.eclipse.xtext.ide.editor.contentassist.antlr.IContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
import org.eclipse.xtext.parser.antlr.AntlrTokenDefProvider;
import org.eclipse.xtext.parser.antlr.ITokenDefProvider;
import org.eclipse.xtext.parser.antlr.LexerProvider;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.DefaultUiModule;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.IProposalConflictHelper;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AntlrProposalConflictHelper;
import org.eclipse.xtext.ui.editor.contentassist.antlr.DelegatingContentAssistContextFactory;
import org.eclipse.xtext.ui.shared.Access;

/**
 * Manual modifications go to {@link SemverUiModule}.
 */
@SuppressWarnings("all")
public abstract class AbstractSemverUiModule extends DefaultUiModule {

	public AbstractSemverUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.ImplicitFragment
	public Provider<? extends IAllContainersState> provideIAllContainersState() {
		return Access.getJavaProjectsState();
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends IProposalConflictHelper> bindIProposalConflictHelper() {
		return AntlrProposalConflictHelper.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public void configureContentAssistLexer(Binder binder) {
		binder.bind(Lexer.class)
			.annotatedWith(Names.named(LexerIdeBindings.CONTENT_ASSIST))
			.to(InternalSemverLexer.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public void configureHighlightingLexer(Binder binder) {
		binder.bind(org.eclipse.xtext.parser.antlr.Lexer.class)
			.annotatedWith(Names.named(LexerIdeBindings.HIGHLIGHTING))
			.to(org.eclipse.n4js.semver.parser.antlr.internal.InternalSemverLexer.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public void configureHighlightingTokenDefProvider(Binder binder) {
		binder.bind(ITokenDefProvider.class)
			.annotatedWith(Names.named(LexerIdeBindings.HIGHLIGHTING))
			.to(AntlrTokenDefProvider.class);
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends ContentAssistContext.Factory> bindContentAssistContext$Factory() {
		return DelegatingContentAssistContextFactory.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public Class<? extends IContentAssistParser> bindIContentAssistParser() {
		return SemverParser.class;
	}
	
	// contributed by org.eclipse.xtext.xtext.generator.parser.antlr.XtextAntlrGeneratorFragment2
	public void configureContentAssistLexerProvider(Binder binder) {
		binder.bind(InternalSemverLexer.class).toProvider(LexerProvider.create(InternalSemverLexer.class));
	}
	
}
