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
package org.eclipse.n4js.scoping.accessModifiers;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.n4js.n4JS.Expression;
import org.eclipse.n4js.n4JS.IdentifierRef;
import org.eclipse.n4js.n4JS.ImportDeclaration;
import org.eclipse.n4js.n4JS.IndexedAccessExpression;
import org.eclipse.n4js.n4JS.NamedImportSpecifier;
import org.eclipse.n4js.n4JS.ParameterizedPropertyAccessExpression;
import org.eclipse.n4js.n4JS.Script;
import org.eclipse.n4js.scoping.utils.ExpressionExtensions;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.xtext.scoping.FilterWithErrorMarkerScope;
import org.eclipse.n4js.xtext.scoping.IEObjectDescriptionWithError;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

/**
 * Checks Constraints 61 (write-access to static data field and static setter), that is, a write access to a static
 * member must be done via direct classifier access.
 */
public class StaticWriteAccessFilterScope extends FilterWithErrorMarkerScope {

	/**
	 * Usually a ParameterizedPropertyAccessExpression
	 */
	private final EObject context;

	/**
	 * Set in {@link #isAccepted(IEObjectDescription)} and used in {@link #wrapFilteredDescription(IEObjectDescription)}
	 * .
	 */
	private String memberTypeName = "-not initialised-";

	/**
	 * Set in {@link #isAccepted(IEObjectDescription)} if possible and used in
	 * {@link #wrapFilteredDescription(IEObjectDescription)}.
	 */
	private String memberTypeAlias = null;

	/**
	 * Creates a new scope instance filtering parent.
	 *
	 * @param context
	 *            usually a ParameterizedPropertyAccessExpression.
	 */
	public StaticWriteAccessFilterScope(IScope parent, EObject context) {
		super(parent);
		this.context = context;
	}

	@Override
	protected IEObjectDescriptionWithError wrapFilteredDescription(IEObjectDescription result) {
		return new InvalidStaticWriteAccessDescription(result, memberTypeName, memberTypeAlias);
	}

	@Override
	protected boolean isAccepted(IEObjectDescription description) {
		EObject proxyOrInstance = description.getEObjectOrProxy();
		if (proxyOrInstance instanceof TMember && !proxyOrInstance.eIsProxy()) {

			TMember member = (TMember) proxyOrInstance;

			// check correct static access of fields or setters.
			// if member is const (i.e. not writeable), other error messages will be created anyway and we do not want
			// this particular message to hide the better one.
			if (member.isStatic() && member.isWriteable() /* i.e. (member.isField(), not const || member.isSetter()) */
					&& isWriteAccess()) {

				ContainerType<?> memberType = member.getContainingType();
				memberTypeName = memberType.getName();

				// Access only allowed for Direct access, so AST must be IdentifierRef.
				final boolean isTargetGivenByIdentifier = getTarget() instanceof IdentifierRef;
				if (!isTargetGivenByIdentifier) {
					// Not an IdentifierRef --> disallowed for write access.
					return false;
				}

				IdentifierRef idref = (IdentifierRef) getTarget();
				// this also covers aliased imports:
				if (idref.getId().getName().equals(memberTypeName)) {
					// correct name.
					return true;
				} else {
					// wrong name, disallowed
					// search for alias, for better error reporting.
					Script sc = EcoreUtil2.getContainerOfType(context, Script.class);
					Optional<NamedImportSpecifier> namedImport = sc.getScriptElements().stream()
							.filter(se -> se instanceof ImportDeclaration)
							.map(se -> (ImportDeclaration) se)
							.flatMap(idecl -> {
								return idecl.getImportSpecifiers().stream()
										.filter(is -> is instanceof NamedImportSpecifier)
										.map(is -> (NamedImportSpecifier) is);
							})
							.filter(s -> s.getImportedElement() == memberType)
							.findFirst();
					if (namedImport.isPresent()) {
						// if alias is present assign, otherwise null will be passed through
						memberTypeAlias = namedImport.get().getAlias();
					}

					return false;
				}

			}

		}
		return true;
	}

	private Expression getTarget() {
		if (context instanceof ParameterizedPropertyAccessExpression) {
			ParameterizedPropertyAccessExpression ppae = (ParameterizedPropertyAccessExpression) context;
			return ppae.getTarget();
		}
		if (context instanceof IndexedAccessExpression) {
			IndexedAccessExpression iae = (IndexedAccessExpression) context;
			return iae.getTarget();
		}
		return null;
	}

	private boolean isWriteAccess() {
		if (!canAppearInLHSPosition()) {
			return false;
		}
		// problems occur if on LHS of assignmentExpression or in writing unary expression
		return ExpressionExtensions.isLeftHandSide(context) || ExpressionExtensions.isIncOrDecTarget(context);
	}

	private boolean canAppearInLHSPosition() {
		return (context instanceof ParameterizedPropertyAccessExpression)
				|| (context instanceof IndexedAccessExpression);
	}
}
