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
package org.eclipse.n4js.xpect.config;

import static org.eclipse.xtext.xbase.lib.IterableExtensions.filter;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.map;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.toList;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage;
import org.eclipse.n4js.ts.types.MemberAccessModifier;
import org.eclipse.n4js.ts.types.TClassifier;
import org.eclipse.n4js.ts.types.TMethod;
import org.eclipse.xpect.setup.XpectSetupComponent;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.IScopeProvider;

/**
 * This Component generates a visibility-filtered list of feature names from a type. The type is loaded when evaluating
 * this component using type-information from the currently loaded script.
 * <p>
 * This Component can be part of {@link VarDef}
 * <p>
 * The MemberList takes three String-arguments in the constructor:
 * <code>MemberList "myType" "myFeatureType" "myVisibility" { }</code>
 * <p>
 * Example:<br>
 * <code>MemberList  "Object" "methods" "public" {}</code> <br/>
 *
 * will evaluate to <br>
 * {@code "constructor","hasOwnPorperty","isPrototypeOf",...}<br>
 * when evaluated in a ECMAScript 5 execution environment
 * <p>
 * See {@link StringList} {@link VarDef} {@link Config}
 * <ul>
 * <li/>type - type to be resolved against the defined execution environment, e.g. "Object".
 * <li/>featureType - what features of the type should be queried, e.g. "methods"
 * <li/>visibility - visibility filter, e.g.
 * "public","publicInternal","protected","protectedInternal","project","private".
 * </ul>
 */
@XpectSetupComponent
public class MemberList implements ValueList {

	static Logger logger = Logger.getLogger(MemberList.class);

	/** type to be resolved against the defined execution environment, e.g. "Object". */
	public String typename;
	/** what features of the type should be queried, e.g. "methods" */
	public String featureType;
	/** - visibility filter, e.g. "public","publicInternal","protected","protectedInternal","project","private". */
	public String visibility;

	/**
	 * @param type
	 *            - type to be resolved against the defined execution environment.
	 * @param featureType
	 *            - what features of the type should be queried, e.g. 'methods'
	 * @param visibility
	 *            - visibility filter.
	 */
	public MemberList(String type, String featureType, String visibility) {
		this.typename = type;
		this.featureType = featureType;
		this.visibility = visibility;
	}

	@Override
	public String toString() {
		return "Memberlist(o='%s',f='%s',v='%s')".formatted(typename, featureType, visibility);
	}

	/** Evaluate this description against a current environment. */
	@Override
	public List<String> evaluate(XtextResource res) {

		// TODO generate List of things.
		logger.warn("just a dummy list in most cases. No impl yet.");

		// 1. Where - typename
		IScopeProvider scopeProv = res.getResourceServiceProvider().get(IScopeProvider.class);
		IScope scope2 = scopeProv.getScope(res.getContents().get(0),
				TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE);
		EObject theType = scope2.getSingleElement(QualifiedName.create(typename)).getEObjectOrProxy();
		theType = EcoreUtil.resolve(theType, res);

		TClassifier tClass = (TClassifier) theType;

		// 3. Can we see it ?
		MemberAccessModifier mvis = MemberAccessModifier.getByName(visibility);
		MemberAccessModifier modVisibility = mvis == null ? MemberAccessModifier.UNDEFINED : mvis;

		// 2. What - featureType
		switch (featureType) {
		case "methods":
			return toList(map(filter(map(filter(tClass.getOwnedMembers(), m -> m.isMethod()), m -> (TMethod) m),
					m -> !m.isStatic() &&
							m.getMemberAccessModifier().compareTo(modVisibility) >= 0),
					m -> m.getName()));

		default:
			return List.of("toString", "toLocaleString", "prototype");
		}
	}

}
