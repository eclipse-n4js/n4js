/**
 * Copyright (c) 2019 NumberFour AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   NumberFour AG - Initial API and implementation
 */
package org.eclipse.n4js.ide.server.rename;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.n4js.n4JS.FormalParameter;
import org.eclipse.n4js.n4JS.FunctionDefinition;
import org.eclipse.n4js.n4JS.N4FieldDeclaration;
import org.eclipse.n4js.n4JS.N4JSPackage;
import org.eclipse.n4js.scoping.N4JSScopeProvider;
import org.eclipse.n4js.ts.types.ContainerType;
import org.eclipse.n4js.ts.types.SyntaxRelatedTElement;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TEnumLiteral;
import org.eclipse.n4js.ts.types.TMember;
import org.eclipse.n4js.utils.ContainerTypesHelper;
import org.eclipse.n4js.utils.ContainerTypesHelper.MemberCollector;
import org.eclipse.n4js.workspace.N4JSWorkspaceConfigSnapshot;
import org.eclipse.n4js.workspace.WorkspaceAccess;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;

import com.google.inject.Inject;

/**
 * Check additional conditions of rename refactoring in this class.
 */
public class N4JSRenameValidator {

	@Inject
	private N4JSScopeProvider scopeProvider;

	@Inject
	private ContainerTypesHelper containerTypesHelper;

	@Inject
	private WorkspaceAccess workspaceAccess;

	/**
	 * @param element
	 *            to be renamed
	 * @param references
	 *            referencedElements to be renamed
	 */
	public void check(String newName, EObject element, List<? extends EObject> references)
			throws ResponseErrorException {

		checkDuplicateName(element, newName);
		for (EObject ref : references) {
			checkDuplicateName(ref, newName);
		}
	}

	private void checkDuplicateName(EObject context, String newName) {
		if (context instanceof N4FieldDeclaration) {
			checkDuplicateMember((N4FieldDeclaration) context, newName);
		}

		if (context instanceof TEnumLiteral) {
			checkDuplicateEnum((TEnum) ((TEnumLiteral) context).eContainer(), newName);
		}

		if (context instanceof FormalParameter) {
			FormalParameter fpar = (FormalParameter) context;
			FunctionDefinition method = (FunctionDefinition) fpar.eContainer();
			checkDuplicateFormalParam(fpar, method.getFpars(), newName);
		}

		// Check name conflicts in variable environment scope using Scope for ContentAssist
		EObject astContext = null;
		if (context instanceof SyntaxRelatedTElement) {
			astContext = ((SyntaxRelatedTElement) context).getAstElement();
		} else {
			astContext = context;
		}

		IScope scope = scopeProvider.getScopeForContentAssist(astContext, N4JSPackage.Literals.IDENTIFIER_REF__ID);

		for (IEObjectDescription desc : scope.getAllElements()) {
			if (desc.getName().toString().equals(newName)) {
				throwResponseError(context, desc.getEObjectURI().trimFragment(), 10,
						"Another element in the same scope with name '" + newName + "' already exists");
			}
		}
	}

	/** Check duplicate enum literals */
	private void checkDuplicateEnum(TEnum enumeration, String newName) {
		boolean duplicateFound = enumeration.getLiterals().stream()
				.filter(literal -> literal.getName().equals(newName))
				.collect(Collectors.toList()).size() > 0;
		if (duplicateFound) {
			throwResponseError(enumeration, 11,
					"Another enum literal with name '" + newName + "' already exists.");
		}
	}

	/** Check duplicate formal parameters */
	private void checkDuplicateFormalParam(FormalParameter fpar, List<FormalParameter> fpars, String newName) {
		List<FormalParameter> fparsWithName = fpars.stream()
				.filter(fp -> (fp != fpar && fp.getName().equals(newName)))
				.collect(Collectors.toList());
		if (fparsWithName.size() > 0) {
			throwResponseError(fpar, 12,
					"Another formal parameter with name '" + newName + "' already exists.");
		}
	}

	/** Check duplicate members */
	private void checkDuplicateMember(N4FieldDeclaration member, String newName) {
		ContainerType<? extends TMember> container = member.getDefinedTypeElement().getContainingType();
		if (container != null) {
			MemberCollector memberCollector = this.containerTypesHelper.fromContext(container);
			List<TMember> membersWithName = memberCollector.members(container).stream()
					.filter(m -> m != member && m.getName().equals(newName))
					.collect(Collectors.toList());

			if (membersWithName.size() > 0) {
				throwResponseError(member, 13,
						"Another member with name '" + newName + "' already exists");
			}
		}
	}

	private void throwResponseError(EObject context, int code, String msg) {
		throwResponseError(context, context.eResource().getURI(), code, msg);
	}

	private void throwResponseError(EObject context, URI uri, int code, String msg) {
		String prefix = "Problem in " + trimPlatformPart(context, uri) + ": ";
		throw new ResponseErrorException(new ResponseError(code, prefix + msg, null));
	}

	private String trimPlatformPart(EObject context, URI uri) {
		N4JSWorkspaceConfigSnapshot workspaceConfig = workspaceAccess.getWorkspaceConfig(context);
		Path relPath = workspaceConfig.makeWorkspaceRelative(uri);
		return relPath.toString();
	}
}
