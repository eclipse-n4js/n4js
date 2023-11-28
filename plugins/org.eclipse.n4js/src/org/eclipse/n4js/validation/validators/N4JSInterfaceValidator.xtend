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

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.n4js.AnnotationDefinition
import org.eclipse.n4js.n4JS.N4FieldDeclaration
import org.eclipse.n4js.n4JS.N4GetterDeclaration
import org.eclipse.n4js.n4JS.N4InterfaceDeclaration
import org.eclipse.n4js.n4JS.N4JSPackage
import org.eclipse.n4js.n4JS.N4MethodDeclaration
import org.eclipse.n4js.n4JS.N4SetterDeclaration
import org.eclipse.n4js.scoping.builtin.N4Scheme
import org.eclipse.n4js.ts.types.PrimitiveType
import org.eclipse.n4js.ts.types.TInterface
import org.eclipse.n4js.ts.types.TypingStrategy
import org.eclipse.n4js.typesystem.utils.RuleEnvironmentExtensions
import org.eclipse.n4js.validation.AbstractN4JSDeclarativeValidator
import org.eclipse.n4js.validation.IssueItem
import org.eclipse.xtext.validation.Check
import org.eclipse.xtext.validation.EValidatorRegistrar

import static org.eclipse.n4js.validation.IssueCodes.*

import static extension org.eclipse.n4js.validation.validators.StaticPolyfillValidatorExtension.*

/**
 */
class N4JSInterfaceValidator extends AbstractN4JSDeclarativeValidator implements PolyfillValidatorHost {

	@Inject
	private PolyfillValidatorFragment polyfillValidatorFragment;

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
		if (!(n4Interface.definedType instanceof TInterface)) {
			return;
		}

		if (polyfillValidatorFragment.holdsPolyfill(this, n4Interface, getCancelIndicator())) {

			holdsNoCyclicInheritance(n4Interface)

			n4Interface.internalCheckExtendedInterfaces
			n4Interface.internalCheckNotFinal
			n4Interface.internalCheckNoFieldInitizializer
			n4Interface.internalCheckNotInStaticPolyfillModule(this) // IDE-1735
		}
	}

	// publish this method.
	public override void addIssue(String message, EObject source, EStructuralFeature feature, String issueCode,
			String... issueData) {
		super.addIssue(message,source,feature,issueCode,issueData)
	}


	def private internalCheckNotFinal(N4InterfaceDeclaration n4InterfaceDeclaration) {
		if (AnnotationDefinition.FINAL.hasAnnotation(n4InterfaceDeclaration)) {
			addIssue(n4InterfaceDeclaration, N4JSPackage.Literals.N4_TYPE_DECLARATION__NAME, ITF_NO_FINAL.toIssueItem());
		}
	}

	// cf. IDEBUG-174
	def private getInternalCheckNoFieldInitizializer(N4InterfaceDeclaration n4Interface) {
		if (n4Interface.typingStrategy === TypingStrategy.STRUCTURAL) {
			for (member : n4Interface.ownedMembers) {
				if (member instanceof N4FieldDeclaration) {
					if (member.expression !== null) {
						addIssue(member.expression, ITF_NO_FIELD_INITIALIZER.toIssueItem(member.name, n4Interface.name));
					}
				}
				if (member instanceof N4MethodDeclaration) {
					if (member.body !== null) {
						if (member.isCallSignature || member.isConstructSignature) {
							// specialized validation: N4JSMemberValidator#holdsCallConstructSignatureConstraints()
						} else {
							addIssue(member.body, ITF_NO_PROPERTY_BODY.toIssueItem("Methods", "structural "));
						}
					}
				}
				if (member instanceof N4GetterDeclaration) {
					if (member.body !== null) {
						addIssue(member.body, ITF_NO_PROPERTY_BODY.toIssueItem("Getters", "structural "));
					}
				}
				if (member instanceof N4SetterDeclaration) {
					if (member.body !== null) {
						addIssue(member.body, ITF_NO_PROPERTY_BODY.toIssueItem("Setters", "structural "));
					}
				}
			}
		}
	}

	def private internalCheckExtendedInterfaces(N4InterfaceDeclaration n4Interface) {
		for (superIfc : n4Interface.superInterfaceRefs) {
			val extendedTypeRef = superIfc.typeRef;
			val extendedType = extendedTypeRef?.declaredType
			// note: in case extendedType.name===null, the type reference is completely invalid and other, more appropriate error messages have been created elsewhere
			if(extendedType !== null && extendedType.name !== null) {

				// extended type must be an interface
				if (extendedType instanceof TInterface) {
					if (n4Interface.typingStrategy === TypingStrategy.STRUCTURAL
						&& extendedType.typingStrategy !== TypingStrategy.STRUCTURAL
					) {
						addIssue(superIfc, null, STRCT_ITF_CANNOT_EXTEND_INTERFACE.toIssueItem());
					}
				} else {
					if (extendedType instanceof PrimitiveType) {
						if (!N4Scheme.isFromResourceWithN4Scheme(n4Interface)) { // primitive types may be extended in built-in types
							addIssue(superIfc, null, CLF_EXTENDS_PRIMITIVE_GENERIC_TYPE.toIssueItem(extendedType.name));
						}
					} else if (RuleEnvironmentExtensions.isAnyDynamic(RuleEnvironmentExtensions.newRuleEnvironment(extendedType), extendedTypeRef)) {
						// allow any+ as a supertype (motivated from type alias being any+ used in d.ts files
						
					} else {
						val IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(n4Interface.description, "extend", extendedType.description);
						addIssue(superIfc, null, issueItem);
					}
				}
			} else if (extendedTypeRef !== null && extendedTypeRef.isAliasResolved) {
				// not all aliases are illegal after "extends", but if we get to this point we have an illegal case:
				val IssueItem issueItem = CLF_WRONG_META_TYPE.toIssueItem(n4Interface.description, "extend", extendedTypeRef.typeRefAsStringWithAliasResolution);
				addIssue(superIfc, null, issueItem);
			}
		}
	}

	def private boolean holdsNoCyclicInheritance(N4InterfaceDeclaration n4InterfaceDeclaration) {
		val ifc = n4InterfaceDeclaration.definedType as TInterface;
		val cycle = findCyclicInheritance(ifc);
		if(cycle!==null) {
			val miscreant = n4InterfaceDeclaration.superInterfaceRefs.findFirst[typeRef?.declaredType===cycle.get(1)];
			val IssueItem issueItem = CLF_INHERITANCE_CYCLE.toIssueItem(cycle.map[name].join(", "));
			addIssue(miscreant, issueItem);
			return false;
		}
		return true;
	}
}
