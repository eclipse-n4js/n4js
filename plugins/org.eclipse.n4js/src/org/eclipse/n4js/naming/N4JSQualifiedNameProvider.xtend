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
package org.eclipse.n4js.naming

import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.JSON.JSONStringLiteral
import org.eclipse.n4js.json.model.utils.JSONModelUtils
import org.eclipse.n4js.n4JS.ExportDeclaration
import org.eclipse.n4js.n4JS.FunctionDeclaration
import org.eclipse.n4js.n4JS.N4TypeDeclaration
import org.eclipse.n4js.n4JS.N4TypeVariable
import org.eclipse.n4js.n4JS.Script
import org.eclipse.n4js.n4JS.VariableDeclaration
import org.eclipse.n4js.packagejson.PackageJsonProperties
import org.eclipse.n4js.ts.scoping.N4TSQualifiedNameProvider
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMember
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeAlias
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.utils.ProjectDescriptionUtils
import org.eclipse.xtext.naming.QualifiedName

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension org.eclipse.n4js.utils.N4JSLanguageUtils.*

/**
 * Calculates the fully qualified name for the passed in objects.
 * <p>
 * Be very careful when changing anything here as the FQN affects a lot of concepts, including scoping and even typing.
 * That is, elements are often handled differently if they have a qualified name or not.
 */
class N4JSQualifiedNameProvider extends N4TSQualifiedNameProvider {

	/** Last segment of fully qualified names for the root {@link JSONDocument} of package.json files. */
	public static final String PACKAGE_JSON_SEGMENT = "!package_json";

	/**
	 * For the root element (Script) the resource qualified name is used.
	 * For all other elements the resource qualified name plus the simple
	 * name of the element is used as qualified name.
	 * Exceptions are IdentifiableElement, N4ClassExpression and FunctionExpression
	 * for which only the simple name is returned.
	 * For a ExportDeclaration the qualified name of its contained element is returned.
	 * For a TModule the qualified name is just converted from dots to slashes.
	 */
	override QualifiedName getFullyQualifiedName(EObject it) {
		switch (it) {
			Script:
				module.fullyQualifiedName
			TModule:
				if (qualifiedName !== null) {
					fqnTModule(it)
				}
			N4TypeDeclaration:
				if (name !== null) fqnTypeDeclaration(it)
			FunctionDeclaration:
				if (name !== null && it.eContainer instanceof ExportDeclaration) rootContainer.fullyQualifiedName?.append(name)
			VariableDeclaration:
				if (name !== null && it.eContainer instanceof ExportDeclaration) rootContainer.fullyQualifiedName?.append(name)
			N4TypeVariable:
				null
			TClass:
				if (name !== null) fqnTClassifier(it)
			TInterface:
				if (name !== null) fqnTClassifier(it)
			TEnum:
				if (name !== null) rootContainer.fullyQualifiedName?.append(exportedName ?: name)
			TypeAlias:
				if (name !== null && it.exported) rootContainer.fullyQualifiedName?.append(exportedName ?: name)
			TFunction:
				if (name !== null && it.exported) rootContainer.fullyQualifiedName?.append(exportedName)
			TVariable:
				if (name !== null && it.exported) rootContainer.fullyQualifiedName?.append(exportedName)
			ExportDeclaration:
				exportedElement?.getFullyQualifiedName
			TypeVariable:
				null
			Type:
				if (name !== null) QualifiedName.create(name)
			TMember:
				null // either null or a real qualified name, but not the simple name! since they cannot be accessed via FQN, we return null
			IdentifiableElement: // including TFormalParameter, and Variable with CatchVariable, FormalParameter, LocalArgumentsVariable
				null
			JSONDocument:
				fqnJSONDocument(it)
			default:
				null
		}
	}



	private def QualifiedName fqnTModule(TModule module) {

		if ( module.qualifiedName.length != 0 && ! AnnotationDefinition.GLOBAL.hasAnnotation(module)) {
			var plainQN = converter.toQualifiedName(module.qualifiedName);
			if( module.isStaticPolyfillModule ) {
				return prepend(MODULE_POLYFILL_SEGMENT, plainQN)
			}
			return plainQN
		} else {
			return QualifiedName.create(GLOBAL_NAMESPACE_SEGMENT)
		}

	}
	private def QualifiedName fqnTypeDeclaration(N4TypeDeclaration typeDecl) {
		var prefix = typeDecl.rootContainer.fullyQualifiedName;
		if ( typeDecl.isPolyfill || typeDecl.isStaticPolyfill )
		{
			prefix = append(prefix, POLYFILL_SEGMENT);
		}
		val fqn = append(prefix, typeDecl.exportedName ?: typeDecl.name);
		return fqn;
	}

	private def QualifiedName fqnTClassifier(TClassifier tClassifier) {
		var prefix = tClassifier.rootContainer.fullyQualifiedName;
		if (tClassifier.polyfill) {
			prefix = append(prefix, POLYFILL_SEGMENT);
		}
		val fqn = append(prefix, tClassifier.exportedName ?: tClassifier.name);
		return fqn;
	}

	private def QualifiedName fqnJSONDocument(JSONDocument document) {
		val res = document.eResource;
		val uri = res?.URI;
		if (uri === null || uri.lastSegment != N4JSGlobals.PACKAGE_JSON) {
			return null; // not a package.json file -> no qualified name
		}
		// (1) try to get projectName from the given document
		var String projectName = null;
		val content = document.content;
		if (content instanceof JSONObject) {
			val value = JSONModelUtils.getProperty(content, PackageJsonProperties.NAME.name).orElse(null);
			projectName = if (value instanceof JSONStringLiteral) value.value else null;
		}
		// (2) if unsuccessful, take projectName from the URI
		if (projectName === null) {
			projectName = ProjectDescriptionUtils.deriveN4JSProjectNameFromURI(uri.trimSegments(1));
		}
		// create qualified name from projectName
		if (projectName !== null && !projectName.isEmpty) {
			val fqnBase = converter.toQualifiedName(projectName);
			if (fqnBase !== null) {
				return fqnBase.append(PACKAGE_JSON_SEGMENT)
			}
		}
		return null; // failed
	}
}
