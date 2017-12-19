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
package org.eclipse.n4js.xpect.config

import org.eclipse.n4js.ts.typeRefs.TypeRefsPackage
import org.eclipse.n4js.ts.types.MemberAccessModifier
import org.eclipse.n4js.ts.types.TClassifier
import org.eclipse.n4js.ts.types.TMethod
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.scoping.IScopeProvider
import org.eclipse.xpect.setup.XpectSetupComponent

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
 *
 * @param type
 *            - type to be resolved against the defined execution environment, e.g. "Object".
 * @param featureType
 *            - what features of the type should be queried, e.g. "methods"
 * @param visibility
 *            - visibility filter, e.g. "public","publicInternal","protected","protectedInternal","project","private".
 */
@XpectSetupComponent
class MemberList implements ValueList {

	static Logger logger = Logger.getLogger(MemberList)

	public String typename;
	public String featureType;
	public String visibility;

	/**
	 * @param type - type to be resolved against the defined execution environment.
	 * @param featureType - what features of the type should be queried, e.g. 'methods'
	 * @param visibility - visibility filter.
	 */
	new(String type, String featureType, String visibility) {
		this.typename = type;
		this.featureType = featureType;
		this.visibility = visibility
	}

	override toString() {
		'''Memberlist(o='«typename»',f='«featureType»',v='«visibility»')'''
	}

	/** Evaluate this description against a current environment. */
	override List<String> evaluate(XtextResource res) {

		// TODO generate List of things.
		logger.warn("just a dummy list in most cases. No impl yet.")

		// 1. Where - typename
		val scopeProv = res.resourceServiceProvider.get(IScopeProvider)
		val scope2 = scopeProv.getScope(res.contents.head,TypeRefsPackage.Literals.PARAMETERIZED_TYPE_REF__DECLARED_TYPE)
		var theType = scope2.getSingleElement( QualifiedName.create(typename)).EObjectOrProxy
		theType = EcoreUtil.resolve(theType,res)


		val TClassifier tClass = theType as TClassifier

		// 3. Can we see it ?
		val modVisibility = MemberAccessModifier::getByName(visibility) ?: MemberAccessModifier.UNDEFINED

		// 2. What - featureType
		val list = switch (featureType) {
			case "methods":
				tClass.ownedMembers.filter[it.method].map[it as TMethod].filter[! isStatic].filter[
					it.memberAccessModifier.compareTo(modVisibility) >= 0].map[it.name].toList
			default:
				#["toString", "toLocaleString", "prototype"]
		}

		return list
	}

}
