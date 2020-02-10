/** 
 * Copyright (c) 2020 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 * NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.json.ide.contentassist

import com.google.inject.Inject
import java.util.List
import java.util.Set
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.json.JSON.JSONArray
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.JSON.JSONStringLiteral
import org.eclipse.n4js.packagejson.PackageJsonProperties
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistContext
import org.eclipse.xtext.ide.editor.contentassist.ContentAssistEntry
import org.eclipse.xtext.ide.editor.contentassist.IIdeContentProposalAcceptor
import org.eclipse.xtext.ide.editor.contentassist.IPrefixMatcher

/** 
 */
class JSONIdeTemplateProposalProvider extends LspTemplateProposalProvider {

	@Inject IPrefixMatcher prefixMatcher;

	def void createNameValueProposals(ContentAssistContext context, IIdeContentProposalAcceptor acceptor) {
		var EObject model = context.getCurrentModel()
		var List<String> namePath = CompletionUtils.getJsonPathNames(model)
		var Set<String> alreadyUsedNames = CompletionUtils.getAlreadyUsedNames(model)
		var List<PackageJsonProperties> pathProps = PackageJsonProperties.valuesOfPath(namePath)
		for (PackageJsonProperties pathProp : pathProps) {
			val String name = pathProp.name
			var label = name
			if (!alreadyUsedNames.contains(name) && isMatchingPrefix(context, name)) {
				if (context.prefix.startsWith('"')) {
					label = '''"«name»"'''
				}
				if (pathProp.valueType === JSONStringLiteral) {
					acceptProposal(label, pathProp.description, '''
					"«name»": "«variable»"«cursor»''', context, acceptor) [
						it.kind = ContentAssistEntry.KIND_FIELD
					]
				}
				if (pathProp.valueType === JSONArray) {
					acceptProposal(label, pathProp.description, '''
					"«name»": [
						«variable»
					]«cursor»''', context, acceptor) [
						it.kind = ContentAssistEntry.KIND_VALUE
					]
				}
				if (pathProp.valueType === JSONObject) {
					acceptProposal(label, pathProp.description, '''
					"«name»": {
						«variable»
					}«cursor»''', context, acceptor) [
						it.kind = ContentAssistEntry.KIND_CLASS
					]
				}
			}
		}
		if (pathProps.isEmpty) {
			acceptProposal("<value>", "Generic name value pair", '''
			"«variable("name")»": "«variable»"«cursor»''', context, acceptor) [
				it.kind = ContentAssistEntry.KIND_PROPERTY
			]
			acceptProposal("<array>", "Generic name array pair", '''
			"«variable("name")»": [
				«variable»
			]«cursor»''', context, acceptor) [
				it.kind = ContentAssistEntry.KIND_VALUE
			]
			acceptProposal("<object>", "Generic name object pair", '''
			"«variable("name")»": {
				«variable»
			}«cursor»''', context, acceptor) [
				it.kind = ContentAssistEntry.KIND_CLASS
			]
		}
	}

	def private boolean isMatchingPrefix(ContentAssistContext context, String name) {
		return prefixMatcher.isCandidateMatchingPrefix(name, context.getPrefix()) ||
			prefixMatcher.isCandidateMatchingPrefix('"' + name, context.getPrefix());
	}
}
