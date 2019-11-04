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
package org.eclipse.n4js.generation

import com.google.inject.Binder
import org.eclipse.n4js.antlr.N4JSAntlrContentAssistGrammarGenerator
import org.eclipse.n4js.antlr.N4JSAntlrGrammarGenerator
import org.eclipse.xtext.xtext.generator.DefaultGeneratorModule
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrContentAssistGrammarGenerator
import org.eclipse.xtext.xtext.generator.parser.antlr.AntlrGrammarGenerator
import org.eclipse.xtext.serializer.analysis.SyntacticSequencerPDAProvider
import org.eclipse.n4js.serializer.StableOrderSyntacticSequencerPDAProvider

/**
 * Abstract base module for the language generators 
 */
abstract class AbstractPatchedGeneratorModule extends DefaultGeneratorModule {

	def configureAntlrGrammarGenerator(Binder binder) {
		binder.bind(AntlrGrammarGenerator).to(N4JSAntlrGrammarGenerator)
	}

	def configureAntlrContentAssistGrammarGenerator(Binder binder) {
		binder.bind(AntlrContentAssistGrammarGenerator).to(N4JSAntlrContentAssistGrammarGenerator)
	}

	def configurePatchedSerializerGenerator(Binder binder) {
		binder.bind(SyntacticSequencerPDAProvider).to(StableOrderSyntacticSequencerPDAProvider);
	}

}
