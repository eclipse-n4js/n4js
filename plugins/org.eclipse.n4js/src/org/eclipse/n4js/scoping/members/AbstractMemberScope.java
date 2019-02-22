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
package org.eclipse.n4js.scoping.members;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.n4JS.N4JSASTUtils;
import org.eclipse.n4js.n4JS.extensions.ExpressionExtensions;
import org.eclipse.n4js.scoping.utils.UnsatisfiedRWAccessDescription;
import org.eclipse.n4js.scoping.utils.WrongStaticAccessDescription;
import org.eclipse.n4js.scoping.utils.WrongWriteAccessDescription;
import org.eclipse.n4js.ts.types.TField;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.ts.types.TSetter;
import org.eclipse.n4js.ts.types.TypingStrategy;
import org.eclipse.n4js.validation.JavaScriptVariantHelper;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractScope;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Base class for real {@link MemberScope}. It allows access to members of a container type, taking static access and
 * read- or write-access into account.
 */
public abstract class AbstractMemberScope extends AbstractScope {

	/**
	 * The context, usually a property access expression
	 */
	protected final EObject context;
	/**
	 * The context's resource
	 */
	protected final Resource contextResource;

	/**
	 * Flag indicating static access, used to filter members (or create erroneous descriptions)
	 */
	protected final boolean staticAccess;
	/**
	 * Flag indicating access of a member of a structural field initializer type, i.e. the receiver type reference has a
	 * typing strategy of {@link TypingStrategy#STRUCTURAL_FIELD_INITIALIZER}.
	 */
	protected final boolean structFieldInitMode;

	/**
	 * Java script variant helper used for determined the kind of constraints to check
	 */
	protected final JavaScriptVariantHelper jsVariantHelper;

	/**
	 * Creates member scope and context information, subclasses must handle concrete members.
	 *
	 * @param context
	 *            context from where the scope is to be retrieved, neither the context nor its resource must be null
	 * @param structFieldInitMode
	 *            see {@link #structFieldInitMode}.
	 */
	public AbstractMemberScope(IScope parent, EObject context,
			boolean staticAccess, boolean structFieldInitMode, JavaScriptVariantHelper jsVariantHelper) {
		super(parent, false);
		if (context == null || context.eResource() == null) {
			throw new NullPointerException("Cannot create member scope for context " + context
					+ " (null or its resource is null).");
		}
		this.context = context;
		this.contextResource = context.eResource();
		this.staticAccess = staticAccess;
		this.structFieldInitMode = structFieldInitMode;
		this.jsVariantHelper = jsVariantHelper;
	}

	/**
	 * Returns list of all members (owned and inherited). This list may contain duplicates, however duplicate handling
	 * is not implemented in this base class.
	 */
	protected abstract Collection<? extends TMember> getMembers();

	/**
	 * Returns a single element by name, read- or write access and static access.
	 */
	protected abstract TMember findMember(String name, boolean writeAccess,
			boolean isStaticAccess);

	@Override
	protected Iterable<IEObjectDescription> getAllLocalElements() {
		Iterable<? extends TMember> iMembers = Iterables.filter(getMembers(), new Predicate<TMember>() {

			@Override
			public boolean apply(TMember input) {
				return input.isStatic() == staticAccess;
			}
		});
		return Scopes.scopedElementsFor(iMembers);
	}

	@Override
	protected Iterable<IEObjectDescription> getLocalElementsByName(QualifiedName name) {
		IEObjectDescription single = getSingleLocalElementByName(name); // getSingleElement(name);
		if (single == null) {
			return Collections.emptyList();
		}
		return Collections.singletonList(single);
	}

	@Override
	protected IEObjectDescription getSingleLocalElementByName(QualifiedName name) {
		if (name.getSegmentCount() != 1) {
			return null;
		}
		final String nameAsString = name.getFirstSegment();

		// both read/write required
		if (ExpressionExtensions.isBothReadFromAndWrittenTo(context)) {
			TMember reader = findMember(nameAsString, false, staticAccess);
			TMember writer = findMember(nameAsString, true, staticAccess);
			if (null == reader && null == writer) {
				// will be caught as error "Could not resolve reference"
				return null;
			}
			if (null == reader) {
				return new UnsatisfiedRWAccessDescription(EObjectDescription.create(writer.getName(), writer), true);
			}
			if (null == writer) {
				return new UnsatisfiedRWAccessDescription(EObjectDescription.create(reader.getName(), reader), false);
			}
			// pick arbitrarily the setter
			return createSingleElementDescription(writer);
		}

		// either read or write requirement that moreover is satisfied
		final boolean accessForWriteOperation = ExpressionExtensions.isLeftHandSide(context);
		TMember existingMember = findMember(nameAsString, accessForWriteOperation, staticAccess);
		if (existingMember != null) {
			return createSingleElementDescription(existingMember);
		}

		// wrong read/write
		existingMember = findMember(nameAsString, !accessForWriteOperation, staticAccess);
		if (existingMember != null) {

			// allowed special case: writing in the ctor to a final field that lacks init value
			final boolean isAssOfFinalInCtor = N4JSASTUtils
					.isSemiLegalAssignmentToFinalFieldInCtor(context.eContainer(), existingMember);
			final boolean isLegalAssOfFinalInCtor = isAssOfFinalInCtor && !((TField) existingMember).isHasExpression();
			if (isLegalAssOfFinalInCtor) {
				return createSingleElementDescription(existingMember);
			}

			// allowed special case: accessing a setter for read operation in context of structural field init typing
			if (structFieldInitMode && !accessForWriteOperation && existingMember instanceof TSetter) {
				return createSingleElementDescription(existingMember);
			}

			// allowed special case: wrong read/write in a mode other than N4JS
			if (jsVariantHelper.allowWrongReadWrite(context)) { // cf. sec. 13.1
				return createSingleElementDescription(existingMember);
			}

			return new WrongWriteAccessDescription(
					EObjectDescription.create(existingMember.getName(), existingMember),
					accessForWriteOperation, isAssOfFinalInCtor);
		}

		// wrong static / non-static
		existingMember = findMember(nameAsString, accessForWriteOperation, !staticAccess);
		if (existingMember == null) {
			// if both read/write access and static access are wrong, we want to
			// complain (only) about "wrong static access" -> so include this case here
			existingMember = findMember(nameAsString, !accessForWriteOperation, !staticAccess);
		}
		if (existingMember != null) {
			boolean wrongStaticAccessDescription = true;

			if (this instanceof MemberScope) {
				MemberScope thiz = (MemberScope) this;
				wrongStaticAccessDescription = !thiz.type.isDynamizable();
			}

			if (wrongStaticAccessDescription) {
				return new WrongStaticAccessDescription(
						EObjectDescription.create(existingMember.getName(), existingMember),
						staticAccess);
			}
		}

		return null;
	}

	/**
	 * Creates a description for a successfully found element. Can be overridden by subclasses to add further
	 * validations and create erroneous descriptions. Default version simply creates a new description.
	 */
	protected IEObjectDescription createSingleElementDescription(TMember existingMember) {
		return EObjectDescription.create(existingMember.getName(), existingMember);
	}

	@Override
	protected Iterable<IEObjectDescription> getLocalElementsByEObject(EObject object, URI uri) {
		if (object instanceof TMember) {
			final TMember tMember = (TMember) object;
			final String name = tMember.getName();
			final TMember existing = findMember(name, tMember.isWriteable(), staticAccess);
			if (existing == object) {
				return Collections.singletonList(EObjectDescription.create(existing.getName(), existing));
			}
		}
		return Collections.emptyList();
	}

}
