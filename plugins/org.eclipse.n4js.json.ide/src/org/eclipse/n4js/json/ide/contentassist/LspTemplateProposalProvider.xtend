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
package org.eclipse.n4js.json.ide.contentassist

import com.google.common.base.Preconditions
import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.eclipse.xtend2.lib.StringConcatenation
import org.eclipse.xtend2.lib.StringConcatenationClient
import org.eclipse.xtext.ide.editor.contentassist.AbstractIdeTemplateProposalProvider
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalPriorities

/**
 * Lsp specific interpretation of generic ide template proposals.
 */
class LspTemplateProposalProvider extends AbstractIdeTemplateProposalProvider {

	@Inject protected IdeContentProposalPriorities proposalPriorities

	protected def void acceptProposal(String name, String description, StringConcatenationClient template,
			ContentAssistContext context, IIdeContentProposalAcceptor acceptor, (ContentAssistEntry)=>void init) {
		val entry = createProposal(template, context, init)
		if (canAcceptProposal(entry, context)) {
			entry.label = name
			entry.description = description
			acceptor.accept(entry, proposalPriorities.getDefaultPriority(entry))
		}
	}

	override protected acceptProposal(String name, String description, StringConcatenationClient template,
		ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		acceptProposal(name, description, template, context, acceptor, false)
	}

	protected override ContentAssistEntry createProposal(StringConcatenationClient template,
		ContentAssistContext context, boolean adaptIndentation) {
		Preconditions.checkArgument(!adaptIndentation);
		return createProposal(template, context) []
	}
	
	def protected ContentAssistEntry createProposal(
		StringConcatenationClient template,
		ContentAssistContext context, (ContentAssistEntry)=>void init) {
		val result = new ContentAssistEntry
		result.prefix = context.prefix
		val stringConcat = new LspTemplateStringConcatenation(lineDelimiter)
		stringConcat.append(template)
		result.proposal = stringConcat.toString
		init.apply(result)
		result.kind = ContentAssistEntry.KIND_SNIPPET + ":" + result.kind
		return result
	}

	protected override String getLineDelimiter() {
		return '\n'
	}
	
	def protected variable() {
		return variable(null)
	}
	
	private static class LspTemplateStringConcatenation extends StringConcatenation {
		
		val Map<String, String> variables
		var int counter = 0 
		
		new(String lineDelimiter) {
			super(lineDelimiter)
			this.variables = new HashMap
		}
		
		override protected getStringRepresentation(Object object) {
			if (object instanceof Variable) {
				val varName = object.name
				if (varName.isNullOrEmpty) {
					counter++;
					return '''$«counter + variables.size»'''
				}
				return variables.computeIfAbsent(varName) ['''${«variables.size + 1 + counter»:«it»}''']
			} else if (object instanceof Cursor) {
				return "$0"
			} else {
				return super.getStringRepresentation(object)
			}
		}
		
		override newLineIfNotEmpty() {
			newLine()
		}
		
	}
}
