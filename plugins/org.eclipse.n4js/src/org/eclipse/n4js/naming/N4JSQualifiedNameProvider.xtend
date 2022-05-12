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

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.N4JSGlobals
import org.eclipse.n4js.json.JSON.JSONDocument
import org.eclipse.n4js.json.JSON.JSONObject
import org.eclipse.n4js.json.JSON.JSONStringLiteral
import org.eclipse.n4js.json.model.utils.JSONModelUtils
import org.eclipse.n4js.packagejson.PackageJsonProperties
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy
import org.eclipse.n4js.scoping.builtin.BuiltInTypeScope
import org.eclipse.n4js.scoping.utils.PolyfillUtils
import org.eclipse.n4js.scoping.utils.QualifiedNameUtils
import org.eclipse.n4js.ts.types.IdentifiableElement
import org.eclipse.n4js.ts.types.TClass
import org.eclipse.n4js.ts.types.TEnum
import org.eclipse.n4js.ts.types.TFunction
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TMethod
import org.eclipse.n4js.ts.types.TModule
import org.eclipse.n4js.ts.types.TNamespace
import org.eclipse.n4js.ts.types.TVariable
import org.eclipse.n4js.ts.types.Type
import org.eclipse.n4js.ts.types.TypeAlias
import org.eclipse.n4js.ts.types.TypeVariable
import org.eclipse.n4js.utils.ProjectDescriptionUtils
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName

/**
 * See {@link #getFullyQualifiedName(EObject)}.
 */
class N4JSQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {

	/** The injected qualified name converter. */
	@Inject
	protected IQualifiedNameConverter converter;

	/** Segment used for globally available elements. */
	public static String GLOBAL_NAMESPACE_SEGMENT = "#";

	/** Segment used to separate the module specifier from the element name in a {@link QualifiedName}. */
	public static String MODULE_CONTENT_SEGMENT = "!";

	/** Last segment of fully qualified names for the root {@link JSONDocument} of package.json files. */
	public static final String PACKAGE_JSON_SEGMENT = "!package_json";

	/**
	 * The qualified name computed here is used when adding type model elements to the Xtext index / global scope
	 * (see {@link N4JSResourceDescriptionStrategy#createEObjectDescriptions(EObject, IAcceptor) here} for details).
	 * <p>
	 * Since project names are not included, these names are not globally unique. However, they are unique among
	 * the elements of a single project.
	 */
	override QualifiedName getFullyQualifiedName(EObject it) {
		switch (it) {
			TModule:
				if (qualifiedName !== null) fqnTModule(it)
			TNamespace:
				if (name !== null) it.contextPrefix?.append(name)
			TClass:
				if (name !== null) it.contextPrefix?.adjustIfPolyfill(it)?.append(name)
			TInterface:
				if (name !== null) it.contextPrefix?.adjustIfPolyfill(it)?.append(name)
			TEnum:
				if (name !== null) it.contextPrefix?.append(name)
			TypeAlias:
				if (name !== null) it.contextPrefix?.append(name)
			TFunction case !(it instanceof TMethod):
				if (name !== null) it.contextPrefix?.append(name)
			TVariable:
				if (name !== null) it.contextPrefix?.append(name)
			TypeVariable:
				null
			Type case !(it instanceof TMethod):
				if (name !== null) it.contextPrefix?.append(name)

			JSONDocument:
				fqnJSONDocument(it)

			default: // including TMember, TFormalParameter
				null
		}
	}

	private def QualifiedName fqnTModule(TModule module) {
		val isGlobal = AnnotationDefinition.GLOBAL.hasAnnotation(module);
		if (isGlobal
			// primitives act like global types, but their module does not contain @@Global:
			|| BuiltInTypeScope.isPrimitivesResource(module.eResource)) {
			return QualifiedName.create(GLOBAL_NAMESPACE_SEGMENT)
		}
		var plainQN = converter.toQualifiedName(module.qualifiedName);
		if (module.isStaticPolyfillModule) {
			plainQN = QualifiedNameUtils.prepend(PolyfillUtils.MODULE_POLYFILL_SEGMENT, plainQN);
		}
		return plainQN
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
			projectName = ProjectDescriptionUtils.deriveN4JSPackageNameFromURI(uri.trimSegments(1));
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

	private def QualifiedName contextPrefix(IdentifiableElement elem) {
		val segments = newArrayList;

		var curr = elem.eContainer;
		while (curr !== null) {
			if (curr instanceof TModule) {
				segments += MODULE_CONTENT_SEGMENT;
				return QualifiedNameUtils.append(curr.fqnTModule, segments.reverseView);
			} else if (curr instanceof TNamespace) {
				val name = curr.name;
				if (name === null) {
					return null;
				}
				segments += name;
			}
			curr = curr.eContainer;
		}
		return null;
	}

	private def QualifiedName adjustIfPolyfill(QualifiedName qn, Type type) {
		if (type.polyfill) {
			return QualifiedNameUtils.append(qn, PolyfillUtils.POLYFILL_SEGMENT);
		}
		return qn;
	}
}
