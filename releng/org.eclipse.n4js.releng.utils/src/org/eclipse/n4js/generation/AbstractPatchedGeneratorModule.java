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
package org.eclipse.n4js.generation;

import org.eclipse.n4js.antlr.N4JSAntlrContentAssistGrammarGenerator;
import org.eclipse.n4js.antlr.N4JSAntlrGrammarGenerator;
import org.eclipse.n4js.serializer.StableOrderSyntacticSequencerPDAProvider;
import org.eclipse.xtext.serializer.analysis.SyntacticSequencerPDAProvider;
import org.eclipse.xtext.xtext.generator.DefaultGeneratorModule;
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrContentAssistGrammarGenerator;
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammarGenerator;

import com.google.inject.Binder;

/**
 * Abstract base module for the language generators
 */
@SuppressWarnings("restriction")
abstract public class AbstractPatchedGeneratorModule extends DefaultGeneratorModule {

	/***/
	public void configureAntlrGrammarGenerator(Binder binder) {
		binder.bind(AntlrGrammarGenerator.class).to(N4JSAntlrGrammarGenerator.class);
	}

	/***/
	public void configureAntlrContentAssistGrammarGenerator(Binder binder) {
		binder.bind(AntlrContentAssistGrammarGenerator.class).to(N4JSAntlrContentAssistGrammarGenerator.class);
	}

	/***/
	public void configurePatchedSerializerGenerator(Binder binder) {
		binder.bind(SyntacticSequencerPDAProvider.class).to(StableOrderSyntacticSequencerPDAProvider.class);
	}

}
