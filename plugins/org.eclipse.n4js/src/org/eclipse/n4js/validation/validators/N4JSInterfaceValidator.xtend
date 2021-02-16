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
package org.eclipse.n4js.validation.validators

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueCodes
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.validation.IssueCodes.*

import static extension org.eclipse.n4js.validation.validators.StaticPolyfillValidatorExtension.*

/**
 */
class N4JSInterfaceValidator extends AbstractN4JSDeclarativeValidator {

	/**
	 * NEEDED
	 *
	 * when removed check methods will be called twice once by N4JSValidator, and once by
	 * AbstractDeclarativeN4JSValidator
	 */
	override register(EValidatorRegistrar registrar) {
		// nop
	}

	public static val STATIC_MEMBER_IN_ROLE = "STATIC_MEMBER_IN_ROLE";

	@Check
	def checkN4InterfaceDeclaration(N4InterfaceDeclaration n4Interface) {

		// wrong parsed
		if (n4Interface.definedType === null) {
			return
		}

		holdsNoCyclicInheritance(n4Interface)

		n4Interface.internalCheckExtendedInterfaces
		n4Interface.internalCheckNotFinal
		n4Interface.internalCheckNoFieldInitizializer
		n4Interface.internalCheckNotInStaticPolyfillModule(this) // IDE-1735
	}

	// publish this method.
	public override void addIssue(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData) {
				super.addIssue(message,source,feature,issueCode,issueData)
	}


	def private internalCheckNotFinal(N4InterfaceDeclaration n4InterfaceDeclaration) {
		if (AnnotationDefinition.FINAL.hasAnnotation(n4InterfaceDeclaration)) {
			val msg = IssueCodes.getMessageForITF_NO_FINAL
			addIssue(msg, n4InterfaceDeclaration, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME,
				IssueCodes.ITF_NO_FINAL)
		}
	}

	// cf. IDEBUG-174
	def private getInternalCheckNoFieldInitizializer(N4InterfaceDeclaration n4Interface) {
		if (n4Interface.typingStrategy === TypingStrategy.STRUCTURAL) {
			n4Interface.ownedFields.forEach [ f |
				if (f.expression !== null) {
					addIssue(IssueCodes.getMessageForITF_NO_FIELD_INITIALIZER(f.name, n4Interface.name), f.expression,
						IssueCodes.ITF_NO_FIELD_INITIALIZER)
				}
			]
		}
	}

	def private internalCheckExtendedInterfaces(N4InterfaceDeclaration n4Interface) {
		n4Interface.superInterfaceRefs.forEach[
			val extendedTypeRef = it.typeRef;
			val extendedType = extendedTypeRef?.declaredType
			// note: in case extendedType.name===null, the type reference is completely invalid and other, more appropriate error messages have been created elsewhere
			if(extendedType !== null && extendedType.name !== null) {

				// extended type must be an interface
				if (!(extendedType instanceof TInterface)) {
					if (extendedType instanceof PrimitiveType) {
						val message = getMessageForCLF_EXTENDS_PRIMITIVE_GENERIC_TYPE(extendedType.name);
						addIssue(message, it, null, CLF_EXTENDS_PRIMITIVE_GENERIC_TYPE)
					} else {
						val message = IssueCodes.getMessageForCLF_WRONG_META_TYPE(n4Interface.description, "extend",
							extendedType.description);
						addIssue(message, it, null, IssueCodes.CLF_WRONG_META_TYPE)
					}
				}
			} else if (extendedTypeRef !== null && extendedTypeRef.isAliasResolved) {
				// not all aliases are illegal after "extends", but if we get to this point we have an illegal case:
				val message = getMessageForCLF_WRONG_META_TYPE(n4Interface.description, "extend", extendedTypeRef.internalGetTypeRefAsString);
				addIssue(message, it, null, CLF_WRONG_META_TYPE);
			}
		]
	}

	def private boolean holdsNoCyclicInheritance(N4InterfaceDeclaration n4InterfaceDeclaration) {
		val ifc = n4InterfaceDeclaration.definedType as TInterface;
		val cycle = findCyclicInheritance(ifc);
		if(cycle!==null) {
			val miscreant = n4InterfaceDeclaration.superInterfaceRefs.findFirst[typeRef?.declaredType===cycle.get(1)];
			val message = IssueCodes.getMessageForCLF_INHERITANCE_CYCLE(cycle.map[name].join(", "));
			addIssue(message, miscreant, IssueCodes.CLF_INHERITANCE_CYCLE);
			return false;
		}
		return true;
	}
}
