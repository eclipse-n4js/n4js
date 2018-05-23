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
package org.eclipse.n4js.jsdoc;

import org.eclipse.n4js.jsdoc.dom.ContentNode;
import org.eclipse.n4js.jsdoc.dom.FullMemberReference;

/**
 * JSDoc tags do support content assist without fully implementing content assist. They support completions by providing
 * hints for the real content assist tools.
 * <p>
 * The following information (or answer to the following questions) is provided:
 * <ol>
 * <li>Is completion possible at all at a given position?
 * <li>If completion is possible, what is to be completed. If there is no "active" line tag, the known line tags can be
 * proposed. Inside a linetag, there are several completion kinds known:
 * <ol>
 * <li>type name
 * <li>member name
 * <li>list of simple templates
 * <li>list of inline tags
 * </ol>
 * The simple templates are simply strings with cursor position (marked via the pipe character). A typical example would
 * be {@code <li>|</li>} in order to create a list item. Type and member names are usually fully qualified, that is,
 * including module specifier.
 */
public class JSDocCompletionHint {

	/**
	 * No completion hint.
	 */
	public final static JSDocCompletionHint NO_COMPLETION =
			new JSDocCompletionHint(CompletionKind.NOCOMPLETION, "", null);

	/**
	 * The JSDoc completion kind.
	 */
	public enum CompletionKind {
		/** No Completion is possible at given position */
		NOCOMPLETION,
		/** At the given position, inline tags are possible */
		INLINETAGS,
		/** At the given position, templates can be proposed */
		TEMPLATES,
		/** At the given position, a module specifier is expected */
		MODULESPEC,
		/**
		 * At the given position, a type is expected. The module of the type is either already prefixed, or stems from
		 * the imports. The type is assumed to be fully qualified.
		 */
		FQTYPE,
		/**
		 * At the given position, a method is expected. The type of the member is already prefixed. The member is
		 * assumed to be fully qualified.
		 */
		FQMEMBER
	}

	/**
	 * The kind of the hint.
	 */
	public final CompletionKind kind;

	/**
	 * The prefix, e.g. "{@code <l}" in case of a template proposal {@code <li>|</li>} activated at "{@code <l|}".
	 */
	public final String prefix;

	/**
	 * The node to be completed, set in constructor, may be null.
	 */
	public final ContentNode nodeToBeCompleted;

	/**
	 * Creates the hint, which is a combination of kind and prefix.
	 */
	public JSDocCompletionHint(CompletionKind kind, String prefix, ContentNode nodeToBeCompleted) {
		this.kind = kind;
		this.prefix = prefix;
		this.nodeToBeCompleted = nodeToBeCompleted;

	}

	@Override
	public String toString() {
		return kind + ", prefix: '" + prefix + "'";
	}

	/**
	 * Convenience method, returns {@link #nodeToBeCompleted} casted to a FullMemberReference if it is an instance of
	 * that type, otherwise null is returned.
	 *
	 * @return casted node or null
	 */
	public FullMemberReference nodeAsFullMemberReference() {
		if (nodeToBeCompleted instanceof FullMemberReference) {
			return (FullMemberReference) nodeToBeCompleted;
		}
		return null;
	}

	private char lastChar() {
		if (prefix.length() > 0) {
			return prefix.charAt(prefix.length() - 1);
		}
		return 0;
	}

	/**
	 * Helper for content assist, returns true if module name is set and a type name is already present.
	 */
	public boolean isModuleNameCompleted() {
		FullMemberReference ref = nodeAsFullMemberReference();
		if (ref != null && ref.moduleNameSet()) {
			if (lastChar() == '.') {
				return true;
			}
			return ref.typeNameSet();
		}
		return false;
	}

	/**
	 * Helper for content assist, returns true if type name is set and a member is already present. This also returns
	 * true if no module name is specified, as the type may be simply referenced.
	 */
	public boolean isTypeNameCompleted(boolean allowSimpleTypeRefs) {
		FullMemberReference ref = nodeAsFullMemberReference();
		if (ref != null) {
			if (allowSimpleTypeRefs) {
				if (ref.moduleNameSet() && !ref.getModuleName().contains("/")) { // simple: moduleName may be a typeName
					if (lastCharOneOf(".#")) {
						return true;
					}
					return ref.typeNameSet(); // simple: typeName then would be the memberName
				}
			}
			if (ref.typeNameSet()) {
				if (lastCharOneOf(".#")) {
					return true;
				}
				return ref.memberNameSet();
			}
		}
		return false;
	}

	private boolean lastCharOneOf(String chars) {
		return chars.indexOf(lastChar()) >= 0;
	}

	/**
	 * Returns true iff this is a simple type reference.
	 */
	public boolean isSimpleTypeRef() {
		FullMemberReference ref = nodeAsFullMemberReference();
		if (ref != null) {
			return ref.moduleNameSet() && !ref.typeNameSet();
		}
		return false;
	}

	/**
	 * Convenience method, returns true if hint is a ref to module, type or member.
	 */
	public boolean isTypeModelRef() {
		return (kind == CompletionKind.MODULESPEC || kind == CompletionKind.FQTYPE || kind == CompletionKind.FQMEMBER)
				&& nodeAsFullMemberReference() != null;

	}

}
