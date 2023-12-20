/**
 * Copyright (c) 2018 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.xtext.serializer;

import org.eclipse.xtext.serializer.analysis.ContextPDAProvider;
import org.eclipse.xtext.serializer.analysis.ContextTypePDAProvider;
import org.eclipse.xtext.serializer.analysis.GrammarConstraintProvider;
import org.eclipse.xtext.serializer.analysis.GrammarPDAProvider;
import org.eclipse.xtext.serializer.analysis.SemanticSequencerNfaProvider;
import org.eclipse.xtext.serializer.analysis.SyntacticSequencerPDAProvider;
import org.eclipse.xtext.serializer.sequencer.ContextFinder;

import com.google.inject.AbstractModule;

@SuppressWarnings("restriction")
public class SerializerPatchModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ContextFinder.class).to(SynchronizedContextFinder.class);
		bind(ContextPDAProvider.class).to(SynchronizedContextPDAProvider.class);
		bind(ContextTypePDAProvider.class).to(SynchronizedContextTypePDAProvider.class);
		bind(GrammarConstraintProvider.class).to(SynchronizedGrammarConstraintProvider.class);
		bind(GrammarPDAProvider.class).to(SynchronizedGrammarPDAProvider.class);
		bind(SemanticSequencerNfaProvider.class).to(SynchronizedSemanticSequencerNfaProvider.class);
		bind(SyntacticSequencerPDAProvider.class).to(SynchronizedSyntacticSequencerPDAProvider.class);
	}

}
