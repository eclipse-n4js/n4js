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
package org.eclipse.n4js.scoping.imports;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.n4JS.ImportSpecifier;
import org.eclipse.n4js.resource.ErrorAwareLinkingService;
import org.eclipse.n4js.scoping.utils.AbstractDescriptionWithError;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.validation.IssueCodes;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * If the {@link ErrorAwareLinkingService} obtains this thing from the scope, an error marker is produced on the
 * reference to indicate an ambiguous import.
 */
public class AmbiguousImportDescription extends AbstractDescriptionWithError {

	private final List<IdentifiableElement> elements;
	private final String issueCode;
	private final EObject context;
	private final List<ImportSpecifier> originatingImports;

	/**
	 * Wraps an existing description for a type with an ambiguous import error message.
	 */
	public AmbiguousImportDescription(IEObjectDescription delegate, String issueCode, EObject context) {
		super(delegate);
		this.issueCode = issueCode;
		this.context = context;
		elements = Lists.newLinkedList();
		originatingImports = Lists.newLinkedList();
	}

	@Override
	public String getMessage() {
		StringBuilder typeListStr = new StringBuilder();
		IdentifiableElement first = (IdentifiableElement) EcoreUtil.resolve(getEObjectOrProxy(), context);
		String typeIdent;
		if (first instanceof TVariable) {
			typeIdent = "variable";
		} else if (first instanceof TFunction) {
			typeIdent = "function";
		} else if (first instanceof Type) {
			typeIdent = "type";
		} else {
			typeIdent = "element";
		}
		TModule module = (TModule) first.eContainer();
		typeListStr.append(module.getQualifiedName());
		Set<IdentifiableElement> uniqueTypes = Sets.newLinkedHashSet(elements);
		uniqueTypes.remove(first);
		Iterator<IdentifiableElement> iter = uniqueTypes.iterator();
		while (iter.hasNext()) {
			IdentifiableElement type = iter.next();
			if (iter.hasNext()) {
				typeListStr.append(", ");
			} else {
				typeListStr.append(" and ");
			}
			typeListStr.append(((TModule) type.eContainer()).getQualifiedName());
		}
		if (this.issueCode == IssueCodes.IMP_AMBIGUOUS_WILDCARD) {
			return IssueCodes.getMessageForIMP_AMBIGUOUS_WILDCARD(typeIdent, getName(), typeListStr.toString());
		} else if (this.issueCode == IssueCodes.IMP_AMBIGUOUS) {
			return IssueCodes.getMessageForIMP_AMBIGUOUS(typeIdent, getName(), typeListStr.toString());
		} else if (this.issueCode == IssueCodes.IMP_DUPLICATE_NAMESPACE) {
			return IssueCodes.getMessageForIMP_DUPLICATE_NAMESPACE("stub", getName(), "stub");
		}
		return "Unknown ambiguous import issue: " + this.issueCode + " for " + context + ".";
	}

	/**
	 * @return wrapped elements
	 */
	public List<IdentifiableElement> getElements() {
		return elements;
	}

	/**
	 *
	 * @return all participating imports to this ambiguity.
	 */
	public List<ImportSpecifier> getOriginatingImports() {
		return originatingImports;
	}

	@Override
	public String getIssueCode() {
		return issueCode;
	}
}
