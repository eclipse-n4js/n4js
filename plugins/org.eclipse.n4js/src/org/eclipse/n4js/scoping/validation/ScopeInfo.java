/**
 * Copyright (c) 2021 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.scoping.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.VariableEnvironmentElement;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.common.collect.ImmutableList;

/**
 * This class attaches additional information such as filters and validations for a given scope. One main use case is
 * the following situation: The given scope is a cached scope that refers to a location L such as block (i.e.
 * {@link VariableEnvironmentElement}) and filters/validators refer to a single location inside that L.
 */
public class ScopeInfo implements IScope /* LEGACY SUPPORT */ {
	/** The scope this info is about */
	protected final IScope scope;
	/** Validators for this scope */
	protected final ImmutableList<IScopeValidator> validators;

	/** Legacy support */
	private final IScope legacyDelegate;

	/** Constructor */
	public ScopeInfo(IScope scope, List<IScopeValidator> validators, IScope legacyDelegate) {
		this.scope = scope;
		this.validators = ImmutableList.copyOf(validators);
		this.legacyDelegate = legacyDelegate;
	}

	/** Constructor */
	public ScopeInfo(IScope scope, IScope legacyDelegate, IScopeValidator... validators) {
		this(scope, validators == null ? Collections.emptyList() : List.of(validators), legacyDelegate);
	}

	/** Returns the scope */
	public IScope getScope() {
		return scope;
	}

	/** Adds the given validator to a copy of this {@link ScopeInfo} */
	public ScopeInfo addValidator(IScopeValidator validator) {
		ArrayList<IScopeValidator> newValidators = new ArrayList<>(validators);
		newValidators.add(validator);
		return new ScopeInfo(scope, newValidators, legacyDelegate);
	}

	/** Returns true iff the given {@link IEObjectDescription} is evaluated as valid by all validators */
	public boolean isValid(IEObjectDescription objDescr) {
		if (objDescr == null) {
			return false;
		}
		for (IScopeValidator validator : validators) {
			if (!validator.isValid(objDescr)) {
				return false;
			}
		}
		return true;
	}

	/** Returns a list of all issues related to the given {@link IEObjectDescription} */
	public List<ScopeElementIssue> getIssues(IEObjectDescription objDescr) {
		if (objDescr == null) {
			return Collections.emptyList();
		}
		List<ScopeElementIssue> errors = new ArrayList<>(0);
		for (IScopeValidator validator : validators) {
			if (!validator.isValid(objDescr)) {
				ScopeElementIssue error = validator.getIssue(objDescr);
				if (error != null) {
					errors.add(error);
				}
			}
		}
		return errors;
	}

	@Override
	public IEObjectDescription getSingleElement(QualifiedName name) {
		return legacyDelegate.getSingleElement(name);
	}

	@Override
	public Iterable<IEObjectDescription> getElements(QualifiedName name) {
		return legacyDelegate.getElements(name);
	}

	@Override
	public IEObjectDescription getSingleElement(EObject object) {
		return legacyDelegate.getSingleElement(object);
	}

	@Override
	public Iterable<IEObjectDescription> getElements(EObject object) {
		return legacyDelegate.getElements(object);
	}

	@Override
	public Iterable<IEObjectDescription> getAllElements() {
		return legacyDelegate.getAllElements();
	}

}
