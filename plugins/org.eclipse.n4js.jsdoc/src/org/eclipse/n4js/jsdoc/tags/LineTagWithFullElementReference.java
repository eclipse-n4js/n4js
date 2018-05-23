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
package org.eclipse.n4js.jsdoc.tags;

import static org.eclipse.n4js.jsdoc.JSDocCompletionHint.CompletionKind.FQMEMBER;

import org.eclipse.n4js.jsdoc.DescriptionParser;
import org.eclipse.n4js.jsdoc.JSDocCharScanner;
import org.eclipse.n4js.jsdoc.JSDocCompletionHint;
import org.eclipse.n4js.jsdoc.dom.DomFactory;
import org.eclipse.n4js.jsdoc.dom.FullMemberReference;
import org.eclipse.n4js.jsdoc.dom.Tag;
import org.eclipse.n4js.jsdoc.dom.TagTitle;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.Type;

/**
 * Base type for tag which consists of a full type or member reference. This class contains a parser accepting
 * references to even members, subclasses have to add validation for restricting the reference.
 */
public abstract class LineTagWithFullElementReference extends AbstractLineTagDefinition {

	/**
	 * Key for reference.
	 */
	public final static String REF = "REF";

	/**
	 * @param title
	 *            Title of tag
	 */
	public LineTagWithFullElementReference(String title) {
		setTitles(title);
	}

	@Override
	public Tag parse(TagTitle title, JSDocCharScanner scanner, DescriptionParser descriptionParser) {
		FullMemberReference ref = parseReference(scanner, scanner.length());

		Tag tag = createLineTag(title);
		addValue(tag, REF, ref);
		return tag;
	}

	@Override
	public JSDocCompletionHint completionHint(JSDocCharScanner scanner) {
		int start = scanner.offset();
		scanner.rewindToWS();
		scanner.next();
		FullMemberReference ref = parseReference(scanner, start);

		String prefix = ref.toString();
		char lastChar = scanner.charAt(start);
		if (!(Character.isWhitespace(lastChar) || prefix.endsWith(Character.toString(lastChar)))) {
			prefix += lastChar;
		}

		return new JSDocCompletionHint(FQMEMBER, prefix, ref);
	}

	private FullMemberReference parseReference(JSDocCharScanner scanner, int maxOffset) {
		scanner.skipWS();
		int start = scanner.offset();
		StringBuilder[] segments = new StringBuilder[3];
		for (int i = 0; i < segments.length; i++) {
			segments[i] = new StringBuilder();
		}
		int segment = 0;
		boolean foundStatic = false;
		char prev = 0;
		while (scanner.offset() <= maxOffset && scanner.hasNext() && !Character.isWhitespace(scanner.peek())) {
			char c = scanner.next();
			if ((c == '.' || c == '#') //
					&& !(segment == 2 && (prev == '.' || prev == '#'))) { // be aware of symbols starting with #
				segment++;
				if (segment > 2) {
					break;
				}
				if (c == '#') {
					if (segment == 2) {
						foundStatic = true;
					} else {
						break; // error
					}
				}
			} else {
				segments[segment].append(c);
			}
			prev = c;
		}
		FullMemberReference ref = DomFactory.eINSTANCE.createFullMemberReference();
		ref.setRange(start, scanner.offset());
		ref.setModuleName(segments[0].toString());
		ref.setTypeName(segments[1].toString());
		ref.setMemberName(segments[2].toString());
		ref.setStaticMember(foundStatic);
		return ref;
	}

	@SuppressWarnings("javadoc")
	public static FullMemberReference createReferenceFromType(TMember member) {
		FullMemberReference ref = DomFactory.eINSTANCE.createFullMemberReference();
		Type type = member.getContainingType();
		ref.setRange(-1, -1);
		ref.setModuleName(type.getContainingModule().getModuleSpecifier());
		ref.setTypeName(type.getName());
		ref.setStaticMember(member.isStatic());
		ref.setMemberName(member.getName());
		return ref;
	}

}
