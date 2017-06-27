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
package org.eclipse.n4js.n4mf.formatting2

import com.google.inject.Inject
import org.eclipse.n4js.n4mf.ModuleFilterSpecifier
import org.eclipse.n4js.n4mf.ProjectDescription
import org.eclipse.n4js.n4mf.SimpleProjectDescription
import org.eclipse.n4js.n4mf.services.N4MFGrammarAccess
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.Assignment
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.IFormattableDocument
import org.eclipse.xtext.formatting2.IHiddenRegionFormatter

class N4MFFormatter extends AbstractFormatter2 {

	static val (IHiddenRegionFormatter)=>void NO_SPACE = [noSpace]
	static val (IHiddenRegionFormatter)=>void ONE_SPACE = [oneSpace]
	static val (IHiddenRegionFormatter)=>void NO_NEW_LINES = [newLines = 0]
	static val (IHiddenRegionFormatter)=>void NEW_LINE = [newLines = 1]
	static val (IHiddenRegionFormatter)=>void INDENT = [indent]

	@Inject extension N4MFGrammarAccess

	def dispatch void format(ProjectDescription it, extension IFormattableDocument document) {
		(#[ it ] + eAllContents.toIterable).forEach[
			commonFormat(document)
		]

		allSemanticRegions.filter[ grammarElement.eContainer instanceof Assignment].forEach[
			switch k : nextSemanticRegion.grammarElement {
				Keyword case k.value.length > 1 && k !== moduleFilterSpecifierAccess.inKeyword_1_0:
					append(NEW_LINE)
			}
		]
	}

	def void commonFormat(EObject it, extension IFormattableDocument document) {
		val region = regionFor
		region.keywordPairs('{', '}').forEach[
			interior(
				key.append(NEW_LINE),
				if (value.nextSemanticRegion !== null)
					value.prepend(NEW_LINE).append(NEW_LINE)
				else
					value.prepend(NEW_LINE).append(NO_SPACE),
				INDENT
			)
		]

		region.keywords(".", "-").forEach[ surround(NO_SPACE) ]
		region.keywords(",", ":").filter[ grammarElement !== simpleProjectDescriptionAccess.colonKeyword_0_1 ].forEach[ prepend(NO_SPACE).append(ONE_SPACE) ]

		region.keywords(",").filter[ grammarElement !== versionConstraintAccess.commaKeyword_0_2_0_0].forEach[ append(NEW_LINE) ]
		region.keywords("(", "[").forEach[ append(NO_SPACE) ]
		region.keywords(")", "]").forEach[ prepend(NO_SPACE) ]

		switch it {
			SimpleProjectDescription:
				region.keyword(simpleProjectDescriptionAccess.colonKeyword_0_1).surround(NO_SPACE)
			ModuleFilterSpecifier:
				region.keyword(moduleFilterSpecifierAccess.inKeyword_1_0).surround(NO_NEW_LINES)
		}
	}
}
