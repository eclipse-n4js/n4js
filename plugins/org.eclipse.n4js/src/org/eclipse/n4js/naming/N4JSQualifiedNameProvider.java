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
package org.eclipse.n4js.naming;

import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.n4js.N4JSGlobals;
import org.eclipse.n4js.json.JSON.JSONDocument;
import org.eclipse.n4js.json.JSON.JSONObject;
import org.eclipse.n4js.json.JSON.JSONStringLiteral;
import org.eclipse.n4js.json.JSON.JSONValue;
import org.eclipse.n4js.json.model.utils.JSONModelUtils;
import org.eclipse.n4js.packagejson.PackageJsonProperties;
import org.eclipse.n4js.resource.N4JSResourceDescriptionStrategy;
import org.eclipse.n4js.scoping.ExportedElementsUtils;
import org.eclipse.n4js.scoping.utils.PolyfillUtils;
import org.eclipse.n4js.scoping.utils.QualifiedNameUtils;
import org.eclipse.n4js.ts.types.IdentifiableElement;
import org.eclipse.n4js.ts.types.TClass;
import org.eclipse.n4js.ts.types.TEnum;
import org.eclipse.n4js.ts.types.TFunction;
import org.eclipse.n4js.ts.types.TInterface;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.n4js.ts.types.TModule;
import org.eclipse.n4js.ts.types.TNamespace;
import org.eclipse.n4js.ts.types.TVariable;
import org.eclipse.n4js.ts.types.Type;
import org.eclipse.n4js.ts.types.TypeAlias;
import org.eclipse.n4js.ts.types.TypeVariable;
import org.eclipse.n4js.utils.N4JSLanguageUtils;
import org.eclipse.n4js.utils.ProjectDescriptionUtils;
import org.eclipse.n4js.utils.URIUtils;
import org.eclipse.n4js.workspace.utils.N4JSPackageName;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.xbase.lib.ListExtensions;

import com.google.common.base.Objects;
import com.google.inject.Inject;

/**
 * See {@link #getFullyQualifiedName(EObject)}.
 */
public class N4JSQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {

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
	 * The qualified name computed here is used when adding type model elements to the Xtext index / global scope (see
	 * {@link N4JSResourceDescriptionStrategy#createEObjectDescriptions(EObject, IAcceptor) here} for details).
	 * <p>
	 * Since project names are not included, these names are not globally unique. However, they are unique among the
	 * elements of a single project.
	 */
	@Override
	public QualifiedName getFullyQualifiedName(EObject eObj) {
		QualifiedName qn = computeQualifiedName(eObj);
		return adjustIfExportedViaNamespaceEquals(qn, eObj);
	}

//	@formatter:off
//	private def QualifiedName computeQualifiedName(EObject eObj) {
//		switch (eObj) {
//			TModule:
//				if (eObj.qualifiedName !== null) fqnTModule(eObj)
//			TNamespace:
//				if (eObj.name !== null) eObj.contextPrefix?.append(eObj.name)
//			TClass:
//				if (eObj.name !== null) eObj.contextPrefix?.adjustIfPolyfill(eObj)?.append(eObj.name)
//			TInterface:
//				if (eObj.name !== null) eObj.contextPrefix?.adjustIfPolyfill(eObj)?.append(eObj.name)
//			TEnum:
//				if (eObj.name !== null) eObj.contextPrefix?.append(eObj.name)
//			TypeAlias:
//				if (eObj.name !== null) eObj.contextPrefix?.append(eObj.name)
//			TFunction case !(eObj instanceof TMethod):
//				if (eObj.name !== null) eObj.contextPrefix?.append(eObj.name)
//			TVariable:
//				if (eObj.name !== null) eObj.contextPrefix?.append(eObj.name)
//			TypeVariable:
//				null
//			Type case !(eObj instanceof TMethod):
//				if (eObj.name !== null) eObj.contextPrefix?.append(eObj.name)
//
//			JSONDocument:
//				fqnJSONDocument(eObj)
//
//			default: // including TMember, TFormalParameter
//				null
//		}
//	}
//	@formatter:on

	private QualifiedName computeQualifiedName(EObject eObj) {
		String name = (eObj instanceof IdentifiableElement) ? ((IdentifiableElement) eObj).getName() : null;
		QualifiedName prefix = (eObj instanceof IdentifiableElement) ? contextPrefix((IdentifiableElement) eObj) : null;
		if (eObj instanceof TModule) {
			if (((TModule) eObj).getQualifiedName() == null) {
				return null;
			}
			return fqnTModule((TModule) eObj);
		} else if (eObj instanceof JSONDocument) {
			return fqnJSONDocument((JSONDocument) eObj);
		} else if (name != null && prefix != null) {
			if (eObj instanceof TNamespace) {
				return prefix.append(name);
			} else if (eObj instanceof TClass) {
				return adjustIfPolyfill(prefix, (TClass) eObj).append(name);
			} else if (eObj instanceof TInterface) {
				return adjustIfPolyfill(prefix, (TInterface) eObj).append(name);
			} else if (eObj instanceof TEnum) {
				return prefix.append(name);
			} else if (eObj instanceof TypeAlias) {
				return prefix.append(name);
			} else if (eObj instanceof TFunction && !(eObj instanceof TMethod)) {
				return prefix.append(name);
			} else if (eObj instanceof TVariable) {
				return prefix.append(name);
			} else if (eObj instanceof TypeVariable) {
				return null;
			} else if (eObj instanceof Type && !(eObj instanceof TMethod)) {
				return prefix.append(name);
			}
		}
		return null; // including TMember, TFormalParameter
	}

	private QualifiedName fqnTModule(TModule module) {
		if (N4JSLanguageUtils.isGlobal(module)) {
			return QualifiedName.create(GLOBAL_NAMESPACE_SEGMENT);
		}
		var plainQN = converter.toQualifiedName(module.getQualifiedName());
		if (module.isStaticPolyfillModule()) {
			plainQN = QualifiedNameUtils.prepend(PolyfillUtils.MODULE_POLYFILL_SEGMENT, plainQN);
		}
		return plainQN;
	}

	private QualifiedName fqnJSONDocument(JSONDocument document) {
		Resource res = document.eResource();
		URI uri = res == null ? null : res.getURI();
		if (uri == null || uri.lastSegment() != N4JSGlobals.PACKAGE_JSON) {
			return null; // not a package.json file -> no qualified name
		}
		// (1) try to get projectName from the given document
		String projectName = null;
		JSONValue content = document.getContent();
		if (content instanceof JSONObject) {
			JSONValue value = JSONModelUtils.getProperty((JSONObject) content, PackageJsonProperties.NAME.name)
					.orElse(null);

			projectName = (value instanceof JSONStringLiteral) ? ((JSONStringLiteral) value).getValue() : null;
		}
		// (2) if unsuccessful, take projectName from the URI
		if (projectName == null) {
			projectName = ProjectDescriptionUtils.deriveN4JSPackageNameFromURI(uri.trimSegments(1));
		}
		// create qualified name from projectName
		if (projectName != null && !projectName.isEmpty()) {
			QualifiedName fqnBase = converter.toQualifiedName(projectName);
			if (fqnBase != null) {
				return fqnBase.append(PACKAGE_JSON_SEGMENT);
			}
		}
		return null; // failed
	}

	private QualifiedName contextPrefix(IdentifiableElement elem) {
		ArrayList<String> segments = new ArrayList<>();

		EObject curr = elem.eContainer();
		while (curr != null) {
			if (curr instanceof TModule) {
				segments.add(MODULE_CONTENT_SEGMENT);
				return QualifiedNameUtils.append(fqnTModule((TModule) curr),
						ListExtensions.<String> reverseView(segments));

			} else if (curr instanceof TNamespace) {
				String name = ((TNamespace) curr).getName();
				if (name == null) {
					return null;
				}
				segments.add(name);
			}
			curr = curr.eContainer();
		}
		return null;
	}

	private QualifiedName adjustIfPolyfill(QualifiedName qn, Type type) {
		if (type.isPolyfill()) {
			return QualifiedNameUtils.append(qn, PolyfillUtils.POLYFILL_SEGMENT);
		}
		return qn;
	}

	private QualifiedName adjustIfExportedViaNamespaceEquals(QualifiedName qn, EObject elem) {
		if (qn == null || elem == null) {
			return null;
		}
		if (elem instanceof IdentifiableElement) {
			TModule tmodule = ((IdentifiableElement) elem).getContainingModule();
			if (tmodule != null && tmodule.isMainModule()
					&& N4JSGlobals.DTS_FILE_EXTENSION.equals(URIUtils.fileExtension(tmodule.eResource().getURI()))) {

				EObject currElem = elem;
				TNamespace rootNS = null;
				while (currElem != null) {
					if (currElem instanceof TNamespace) {
						rootNS = (TNamespace) currElem;
					}
					currElem = currElem.eContainer();
				}
				if (rootNS != null) {
					String exportEqualsArg = ExportedElementsUtils.getExportEqualsArg(tmodule);
					if (Objects.equal(exportEqualsArg, rootNS.getName())) {
						ArrayList<String> newSegments = new ArrayList<>(qn.getSegments());
						N4JSPackageName pckName = new N4JSPackageName(tmodule.getPackageName());
						newSegments.remove(2); // removes the exported namespace name
						newSegments.remove(0); // removes the module/file name
						newSegments.add(0, pckName.getPlainName()); // add the project plain name
						if (newSegments.size() <= 2) {
							newSegments.remove(1); // removes the MODULE_CONTENT_SEGMENT
						}
						return QualifiedName.create(newSegments);
					}
				}
			}
		}

		return qn;
	}
}
